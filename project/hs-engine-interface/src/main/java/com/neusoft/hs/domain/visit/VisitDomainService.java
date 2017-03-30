//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitDomainService.java

package com.neusoft.hs.domain.visit;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.platform.exception.HsException;

@FeignClient("engine-service")
public interface VisitDomainService {

	/**
	 * 创建患者一次就诊
	 * 
	 * @param createVisitVO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/visit/create")
	public Visit create(@RequestBody CreateVisitVO createVisitVO);

	/**
	 * 进入病房
	 * 
	 * @param user
	 * @param receiveVisitVO
	 * @throws HsException
	 * @roseuid 584E135F0389
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/visit/intoWard/{user}/user")
	public void intoWard(@RequestBody ReceiveVisitVO receiveVisitVO,
			@PathVariable("user") AbstractUser user) throws HsException;

	/**
	 * 门诊离院
	 * 
	 * @param visitId
	 * @param user
	 * @throws HsException
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/visit/leaveHospital/{user}/user")
	public void leaveHospital(@RequestBody Visit visit,
			@PathVariable("user") AbstractUser user) throws VisitException;

	/**
	 * @param visitId
	 * @roseuid 584E03140020
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/visit/{visitId}/id")
	public Visit find(@PathVariable("visitId") String visitId);

	@RequestMapping(method = RequestMethod.GET, value = "/visit/{cardNumber}/cardNumber")
	public Visit findLastVisit(@PathVariable("cardNumber") String cardNumber);

	@RequestMapping(method = RequestMethod.GET, value = "/visit/{state}/state")
	public List<Visit> findByState(@PathVariable("state") String state,
			Pageable pageable);

	@RequestMapping(method = RequestMethod.GET, value = "/visit/{state}/state/{dept}/dept")
	public List<Visit> findByStateAndDept(@PathVariable("state") String state,
			@RequestParam("dept") Dept dept, Pageable pageable);

	@RequestMapping(method = RequestMethod.GET, value = "/visit/{dept}/dept")
	public List<Visit> listVisit(@PathVariable("dept") Dept respDept,
			Pageable pageable);

	@RequestMapping(method = RequestMethod.GET, value = "/visit/list")
	public List<Visit> listInPatientVisit(Pageable pageable);

	@RequestMapping(method = RequestMethod.POST, value = "/visit/clear")
	public void clear();

	/**
	 * 自动切换患者一次就诊状态
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/visit/changeVisitState")
	public int changeVisitState();

}
