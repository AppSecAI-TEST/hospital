package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.neusoft.hs.platform.entity.IdEntity;

/**
 * 分解医嘱条目时的一组执行条目 当医嘱条目为长期医嘱时，该组代表一个频次下被分解的执行条目集合
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_order_execute_team")
public class OrderExecuteTeam extends IdEntity {

	@OneToMany(mappedBy = "team", cascade = { CascadeType.ALL })
	private List<OrderExecute> executes;

	public OrderExecuteTeam() {
	}

	public void addOrderExecute(OrderExecute execute) {

		if (executes == null || executes.size() == 0) {
			executes = new ArrayList<OrderExecute>();
		}
		OrderExecute previous = null;
		if (executes.size() > 0) {
			previous = executes.get(executes.size() - 1);
			previous.setNext(execute);
		}
		execute.setPrevious(previous);
		execute.setTeam(this);

		executes.add(execute);

	}

	public List<OrderExecute> getExecutes() {
		return executes;
	}
}
