//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitDomainService.java

package com.neusoft.hs.domain.visit;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.platform.exception.HsException;

public interface VisitDomainService {

	/**
	 * 创建患者一次就诊
	 * 
	 * @param createVisitVO
	 * @return
	 */
	public Visit create(CreateVisitVO createVisitVO);

	/**
	 * 进入病房
	 * 
	 * @param user
	 * @param receiveVisitVO
	 * @throws HsException
	 * @roseuid 584E135F0389
	 */
	public void intoWard(ReceiveVisitVO receiveVisitVO, AbstractUser user)
			throws HsException;

	/**
	 * 门诊离院
	 * 
	 * @param visitId
	 * @param user
	 * @throws HsException
	 */
	public void leaveHospital(Visit visit, AbstractUser user)
			throws VisitException;

	/**
	 * @param visitId
	 * @roseuid 584E03140020
	 */
	public Visit find(String visitId);

	public Visit findLastVisit(String cardNumber);

	public List<Visit> findByState(String state, Pageable pageable);

	public List<Visit> findByStateAndDept(String state, Dept dept,
			Pageable pageable);

	public List<Visit> listVisit(Dept respDept, Pageable pageable);

	public List<Visit> listInPatientVisit(Pageable pageable);

	public void clear();

	/**
	 * 自动切换患者一次就诊状态
	 * 
	 * @return
	 */
	public int changeVisitState();

}
