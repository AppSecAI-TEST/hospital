package com.neusoft.hs.domain.inspect;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.Apply;
import com.neusoft.hs.domain.order.ApplyDomainService;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class InspectDomainService {

	@Autowired
	private InspectItemRepo inspectItemRepo;

	@Autowired
	private InspectApplyItemRepo inspectApplyItemRepo;

	@Autowired
	private InspectResultRepo inspectResultRepo;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	@Autowired
	private ApplyDomainService applyDomainService;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 安排检查
	 * 
	 * @param executeId
	 * @param planExecuteDate
	 * @param user
	 * @throws InspectException
	 */
	public void arrange(String executeId, Date planExecuteDate,
			AbstractUser user) throws InspectException {
		OrderExecute orderExecute = orderExecuteDomainService.find(executeId);
		if (orderExecute == null) {
			throw new InspectException("医嘱执行条目[" + executeId + "]不存在");
		}

		InspectArrangeOrderExecute arrangeOrderExecute = (InspectArrangeOrderExecute) orderExecute;
		InspectApplyItem inspectApplyItem = arrangeOrderExecute
				.getInspectApplyItem();
		inspectApplyItem.setPlanExecuteDate(planExecuteDate);

		inspectApplyItem.save();

		LogUtil.log(this.getClass(), "用户[{}]为患者一次就诊[{}]安排检查[{}]时间为[{}]",
				user.getId(), orderExecute.getVisit().getName(),
				inspectApplyItem.getId(), planExecuteDate);

	}

	/**
	 * 确认检查结果
	 * 
	 * @param executeId
	 * @param results
	 * @param user
	 * @throws InspectException
	 */
	public void confirm(String executeId,
			Map<InspectApplyItem, String> results, AbstractUser user)
			throws InspectException {

		OrderExecute orderExecute = orderExecuteDomainService.find(executeId);
		if (orderExecute == null) {
			throw new InspectException("医嘱执行条目[" + executeId + "]不存在");
		}
		Order order = orderExecute.getOrder();
		Apply apply = order.getApply();
		if (apply == null) {
			throw new InspectException("医嘱条目[" + order + "]不存在申请单");
		}

		InspectApply inspectApply = (InspectApply) apply;

		List<InspectApplyItem> inspectApplyItems = inspectApply
				.getInspectApplyItems();
		// lazy load
		List<String> inspectApplyItemIds = new ArrayList<String>();
		for (InspectApplyItem inspectApplyItem : inspectApplyItems) {
			inspectApplyItemIds.add(inspectApplyItem.getId());
		}
		for (InspectApplyItem item : results.keySet()) {
			if (!inspectApplyItemIds.contains(item.getId())) {
				throw new InspectException("检查项目["
						+ item.getInspectItem().getCode() + "]不在申请单中");
			}
			InspectResult inspectResult = new InspectResult();
			inspectResult.setInspectDept((InspectDept) user.getDept());
			inspectResult.setInspectApplyItem(item);
			inspectResult.setResult(results.get(item));
			inspectResult.setCreateDate(DateUtil.getSysDate());

			inspectApply.addInspectResult(inspectResult);
		}

		applyDomainService.save(inspectApply);

		LogUtil.log(this.getClass(), "用户[{}]为患者一次就诊[{}]确认检查结果[{}]",
				user.getId(), orderExecute.getVisit().getName(),
				inspectApply.getId());
	}

	/**
	 * 取消检查
	 * 
	 * @param inspectApplyItemId
	 * @param user
	 * @throws InspectException
	 */
	public void cancel(String inspectApplyItemId, AbstractUser user)
			throws InspectException {

		InspectApplyItem inspectApplyItem = inspectApplyItemRepo
				.findOne(inspectApplyItemId);

		if (inspectApplyItem.getState().equals(InspectApplyItem.State_Finished)) {
			throw new InspectException("检查项目["
					+ inspectApplyItem.getInspectItem().getCode() + "]已完成");
		}

		inspectApplyItem.setState(InspectApplyItem.State_Canceled);

		InspectArrangeOrderExecute arrange = inspectApplyItem
				.getInspectArrangeOrderExecute();
		if (arrange != null) {
			try {
				arrange.cancel();
			} catch (OrderExecuteException e) {
				throw new InspectException(e);
			}
		}
		InspectConfirmOrderExecute confirm = inspectApplyItem
				.getInspectConfirmOrderExecute();
		if (confirm != null) {
			try {
				confirm.cancel();
			} catch (OrderExecuteException e) {
				throw new InspectException(e);
			}
		}

		inspectApplyItem.save();

		applicationContext.publishEvent(new InspectApplyItemCanceledEvent(
				inspectApplyItem));

		LogUtil.log(this.getClass(), "用户[{}]为患者一次就诊[{}]取消检查项目[{}]",
				user.getId(), arrange.getVisit().getName(),
				inspectApplyItem.getId());
	}

	public void createInspectItems(List<InspectItem> inspectItems) {
		inspectItemRepo.save(inspectItems);
	}

	public List<InspectResult> findInspectResults(Visit visit) {
		return inspectResultRepo.findByVisit(visit);
	}

	public void clearInspectItems() {
		inspectItemRepo.deleteAll();
	}
}
