package com.neusoft.hs.engine.visit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.visit.VisitAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitFacadeImpl implements VisitFacade {

	@Autowired
	private VisitAppService visitAppService;

	@Autowired
	private UserAdminDomainService userAdminDomainService;

	@Override
	public Visit create(CreateVisitDTO createVisitDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void intoWard(ReceiveVisitDTO receiveVisitDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void leaveHospital(LeaveHospitalDTO leaveHospitalDTO) throws HsException {
		AbstractUser user = userAdminDomainService.find(leaveHospitalDTO
				.getOperatorId());
		if (user == null) {
			throw new HsException("operatorId=[" + leaveHospitalDTO.getOperatorId()
					+ "]不存在");
		}
		String visitId = leaveHospitalDTO.getVisitId();
		
		visitAppService.leaveHospital(visitId, user);
	}

	@Override
	public Visit find(String visitId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Visit findLastVisit(String cardNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Visit> listInPatientVisit(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Visit> findByStateAndDept(String state, String deptId,
			int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Visit> listVisit(String respDeptId, int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
