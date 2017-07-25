package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderExecuteDAO {

	@Autowired
	private OrderExecuteRepo orderExecuteRepo;

	public OrderExecute find(String id) {
		return orderExecuteRepo.findOne(id);
	}

	/**
	 * 根据自定义条件查询执行条目列表
	 * 
	 * @param filter
	 * @param params
	 * @param user
	 * @param pageable
	 * @return
	 * @throws HsException
	 */
	public List<OrderExecute> find(OrderExecuteFilter filter,
			Map<String, Object> params, AbstractUser user, Pageable pageable)
			throws HsException {
		OrderExecuteFilterCondition condition = filter.createCondition(params,
				user);

		Date begin = condition.getBegin();
		Date end = condition.getEnd();
		List<String> types = condition.getTypes();
		String category = condition.getCategory();

		List<? extends Dept> belongDepts = condition.getBelongDepts();

		if (belongDepts.size() > 1) {
			if (types.size() > 1) {
				if (category == null) {
					return orderExecuteRepo
							.findByStateAndBelongDeptInAndTypeInAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing, belongDepts,
									types, begin, end, pageable);
				} else {
					return orderExecuteRepo
							.findByStateAndBelongDeptInAndTypeInAndOrderCategoryAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing, belongDepts,
									types, category, begin, end, pageable);
				}
			} else if (types.size() == 1) {
				if (category == null) {
					return orderExecuteRepo
							.findByStateAndBelongDeptInAndTypeAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing, belongDepts,
									types.get(0), begin, end, pageable);
				} else {
					return orderExecuteRepo
							.findByStateAndBelongDeptInAndTypeAndOrderCategoryAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing, belongDepts,
									types.get(0), category, begin, end,
									pageable);
				}
			} else {
				if (category == null) {
					return orderExecuteRepo
							.findByStateAndBelongDeptInAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing, belongDepts,
									begin, end, pageable);
				} else {
					return orderExecuteRepo
							.findByStateAndBelongDeptInAndOrderCategoryAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing, belongDepts,
									category, begin, end, pageable);
				}
			}
		} else if (belongDepts.size() > 1) {
			if (types.size() > 1) {
				if (category == null) {
					return orderExecuteRepo
							.findByStateAndBelongDeptAndTypeInAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing,
									belongDepts.get(0), types, begin, end,
									pageable);
				} else {
					return orderExecuteRepo
							.findByStateAndBelongDeptAndTypeInAndOrderCategoryAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing,
									belongDepts.get(0), types, category, begin,
									end, pageable);
				}
			} else if (types.size() == 1) {
				if (category == null) {
					return orderExecuteRepo
							.findByStateAndBelongDeptAndTypeAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing,
									belongDepts.get(0), types.get(0), begin,
									end, pageable);
				} else {
					return orderExecuteRepo
							.findByStateAndBelongDeptAndTypeAndOrderCategoryAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing,
									belongDepts.get(0), types.get(0), category,
									begin, end, pageable);
				}
			} else {
				throw new HsException("代码未编写");
			}
		} else {
			throw new HsException("代码未编写");
		}
	}
}
