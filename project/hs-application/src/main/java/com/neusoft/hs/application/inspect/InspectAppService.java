package com.neusoft.hs.application.inspect;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.Apply;
import com.neusoft.hs.domain.order.InspectApply;
import com.neusoft.hs.domain.order.InspectDomainService;
import com.neusoft.hs.domain.order.InspectItem;
import com.neusoft.hs.domain.order.InspectResult;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.InspectDept;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class InspectAppService {

	@Autowired
	private InspectDomainService inspectDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	public InspectApply find(String applyId) {
		return inspectDomainService.find(applyId);
	}

	public void arrange(String executeId, Date planExecuteDate)
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
		apply.setPlanExecuteDate(planExecuteDate);

		apply.save();
	}

	public void confirm(String executeId, Map<InspectItem, String> results,
			AbstractUser user) throws InspectException {

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

		List<InspectItem> inspectItems = inspectApply.getInspectItems();
		for (InspectItem item : results.keySet()) {
			if (!inspectItems.contains(item)) {
				throw new InspectException("检查项目[" + item.getCode() + "]不在申请单中");
			}
			InspectResult inspectResult = new InspectResult();
			inspectResult.setInspectDept((InspectDept) user.getDept());
			inspectResult.setInspectItem(item);
			inspectResult.setResult(results.get(item));
			inspectResult.setCreateDate(DateUtil.getSysDate());

			inspectApply.addInspectResult(inspectResult);
		}

		inspectDomainService.save(inspectApply);
	}
}
