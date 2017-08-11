package com.neusoft.hs.domain.order;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.application.organization.UserAdminAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.visit.TransferDeptVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.domain.visit.VisitException;

@Entity
@DiscriminatorValue("TransferDeptConfirm")
public class TransferDeptConfirmOrderExecute extends OrderExecute {

	public static final String RespDoctor = "RespDoctor";
	public static final String RespNurse = "RespNurse";
	public static final String Bed = "Bed";
	public static final String Area = "Area";

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {
		if (params == null) {
			throw new OrderExecuteException(this,
					"params需设置Key为[%s][%s][%s]的参数", RespDoctor, RespNurse, Bed);
		}

		if (!params.containsKey(RespDoctor)) {
			throw new OrderExecuteException(this, "params没有设置Key为[%s]的责任医生",
					RespDoctor);
		}
		if (!params.containsKey(RespNurse)) {
			throw new OrderExecuteException(this, "params没有设置Key为[%s]的责任护士",
					RespNurse);
		}
		if (!params.containsKey(Bed)) {
			throw new OrderExecuteException(this, "params没有设置Key为[%s]的床位号", Bed);
		}

		Visit visit = this.getVisit();

		TransferDeptVO transferDeptVO = new TransferDeptVO();
		transferDeptVO.setVisit(visit);
		transferDeptVO.setDept(this.getExecuteDept());
		transferDeptVO.setRespDoctor((Doctor) params.get(RespDoctor));
		transferDeptVO.setNurse((Nurse) params.get(RespNurse));
		transferDeptVO.setBed((String) params.get(Bed));

		if (!params.containsKey(Area)) {
			Nurse nurse = this.getService(UserAdminAppService.class).findNurse(
					transferDeptVO.getNurse().getId());
			transferDeptVO.setArea(nurse.getDept());
		} else {
			transferDeptVO.setArea((Dept) params.get(Area));
		}

		try {
			this.getService(VisitDomainService.class).transferDeptConfirm(
					transferDeptVO, user);
		} catch (VisitException e) {
			e.printStackTrace();
			throw new OrderExecuteException(this, e);
		}

	}

}
