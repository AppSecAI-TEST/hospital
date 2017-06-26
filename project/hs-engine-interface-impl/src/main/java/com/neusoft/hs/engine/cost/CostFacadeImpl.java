package com.neusoft.hs.engine.cost;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.cost.CostAppService;
import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CostFacadeImpl implements CostFacade {

	@Autowired
	private CostAppService costAppService;

	@Autowired
	private CostDomainService costDomainService;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private UserAdminDomainService userAdminDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	@Override
	public List<Visit> getNeedInitAccount(int pageNumber, int pageSize) {
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return costDomainService.getNeedInitAccount(pageable);
	}

	@Override
	public ChargeBill createChargeBill(ChargeBillCreateDTO chargeBillCreateVO)
			throws HsException {

		Visit visit = visitDomainService.find(chargeBillCreateVO.getVisitId());
		if (visit == null) {
			throw new HsException("visitId=[" + chargeBillCreateVO.getVisitId()
					+ "]不存在");
		}

		AbstractUser user = userAdminDomainService.find(chargeBillCreateVO
				.getUserId());
		if (user == null) {
			throw new HsException("userId=[" + chargeBillCreateVO.getUserId()
					+ "]不存在");
		}

		return costDomainService.createChargeBill(visit,
				chargeBillCreateVO.getBalance(), user);
	}

	@Override
	public ChargeRecord addCost(ChargeBillAddCostDTO chargeBillAddCostVO)
			throws HsException {

		Visit visit = visitDomainService.find(chargeBillAddCostVO.getVisitId());
		if (visit == null) {
			throw new HsException("visitId=[%s]不存在",
					chargeBillAddCostVO.getVisitId());
		}

		AbstractUser user = userAdminDomainService.find(chargeBillAddCostVO
				.getUserId());
		if (user == null) {
			throw new HsException("userId=[" + chargeBillAddCostVO.getUserId()
					+ "]不存在");
		}

		return costDomainService.addCost(visit,
				chargeBillAddCostVO.getBalance(), user);
	}

	@Override
	public List<OrderExecute> getNeedBackChargeOrderExecutes(String userId,
			int pageNumber, int pageSize) throws HsException {
		Staff user = (Staff) userAdminDomainService.find(userId);
		if (user == null) {
			throw new HsException("userId=[%s]不存在", userId);
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return costDomainService.getNeedBackChargeOrderExecutes(user, pageable);
	}

	@Override
	public void unCharging(UnChargingDTO unChargingVO)
			throws OrderExecuteException {

		OrderExecute orderExecute = orderExecuteDomainService.find(unChargingVO
				.getExecuteId());
		if (orderExecute == null) {
			throw new OrderExecuteException(null, "orderExecuteId=[%s]不存在",
					unChargingVO.getExecuteId());
		}

		Nurse user = (Nurse) userAdminDomainService.find(unChargingVO
				.getUserId());
		if (user == null) {
			throw new OrderExecuteException(orderExecute, "userId=[%s]不存在",
					unChargingVO.getUserId());
		}

		costDomainService.unCharging(orderExecute, unChargingVO.isBackCost(),
				user);

	}
}
