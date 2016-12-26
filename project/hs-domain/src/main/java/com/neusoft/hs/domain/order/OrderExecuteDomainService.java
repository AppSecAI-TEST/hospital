//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderExecuteDomainService.java

package com.neusoft.hs.domain.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderExecuteDomainService {

	@Autowired
	private OrderExecuteRepo orderExecuteRepo;

	public List<OrderExecute> getNeedSendOrderExecutes(Nurse nurse,
			Pageable pageable) {
		return orderExecuteRepo.findByStateAndBelongDept(
				OrderExecute.State_NeedSend, nurse.getDept());
	}

	/**
	 * @param nurse
	 * @param executeId
	 * @throws OrderExecuteException
	 * @roseuid 584F6150022C
	 */
	public void send(String executeId, Nurse nurse)
			throws OrderExecuteException {
		OrderExecute execute = orderExecuteRepo.findOne(executeId);
		if (execute == null) {
			throw new OrderExecuteException(null, "executeId=[" + executeId
					+ "]不存在");
		}
		execute.send();
	}

	/**
	 * @roseuid 584F691702B2
	 */
	public int start() {
		return orderExecuteRepo.start(OrderExecute.State_Executing,
				OrderExecute.State_NeedExecute, ChargeBill.State_Normal,
				DateUtil.getSysDate());
	}

	/**
	 * @roseuid 584FB6AF013C
	 */
	public void finish() {

	}

	/**
	 * @roseuid 5850D8510101
	 */
	public void unCharging() {

	}

}
