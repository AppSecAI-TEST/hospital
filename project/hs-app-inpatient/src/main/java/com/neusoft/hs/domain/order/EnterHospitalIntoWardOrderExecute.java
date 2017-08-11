package com.neusoft.hs.domain.order;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.domain.visit.ReceiveVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;

@Entity
@DiscriminatorValue("EnterHospitalIntoWard")
public class EnterHospitalIntoWardOrderExecute extends OrderExecute {

	public final static String Bed = "Bed";

	public final static String RespNurse = "RespNurse";

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {

		String bed = (String) params.get(Bed);
		if (bed == null) {
			throw new OrderExecuteException(this, "没有向params.[%s]设置床位号", Bed);
		}
		Nurse nurse = (Nurse) params.get(RespNurse);
		if (nurse == null) {
			throw new OrderExecuteException(this, "没有向params.[%s]设置责任护士",
					RespNurse);
		}
		//重新获取护士实体
		nurse = (Nurse) this.getService(UserAdminDomainService.class).find(
				nurse.getId());

		Visit visit = this.getVisit();
		ReceiveVisitVO receiveVisitVO = new ReceiveVisitVO();
		receiveVisitVO.setVisit(visit);
		receiveVisitVO.setBed(bed);
		receiveVisitVO.setNurse(nurse);
		try {
			this.getService(VisitDomainService.class).intoWard(receiveVisitVO,
					user);
		} catch (HsException e) {
			e.printStackTrace();
			throw new OrderExecuteException(this, e);
		}
	}
}
