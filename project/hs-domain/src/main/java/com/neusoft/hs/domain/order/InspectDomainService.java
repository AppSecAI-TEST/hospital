package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.InspectDept;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class InspectDomainService {

	@Autowired
	private ApplyRepo applyRepo;

	@Autowired
	private InspectApplyItemRepo inspectApplyItemRepo;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;
	
	@Autowired
	private ApplicationContext applicationContext;

	public void arrange(String executeId, Date planExecuteDate)
			throws InspectException {
		OrderExecute orderExecute = orderExecuteDomainService.find(executeId);
		if (orderExecute == null) {
			throw new InspectException("医嘱执行条目[" + executeId + "]不存在");
		}

		InspectArrangeOrderExecute arrangeOrderExecute = (InspectArrangeOrderExecute) orderExecute;
		InspectApplyItem inspectApplyItem = arrangeOrderExecute
				.getInspectApplyItem();
		inspectApplyItem.setPlanExecuteDate(planExecuteDate);

		inspectApplyItem.save();
	}

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

		applyRepo.save(inspectApply);
	}

	public void cancel(String inspectApplyItemId, AbstractUser user) throws InspectException {

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

		applicationContext.publishEvent(new InspectApplyItemCanceledEvent(inspectApplyItem));
	}

	public InspectApply find(String applyId) {
		return (InspectApply) applyRepo.findOne(applyId);
	}

	public void save(InspectApply inspectApply) {
		applyRepo.save(inspectApply);
	}
}
