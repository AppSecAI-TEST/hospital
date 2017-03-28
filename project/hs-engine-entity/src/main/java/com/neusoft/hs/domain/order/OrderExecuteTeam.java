package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 分解医嘱条目时的一组执行条目 当医嘱条目为长期医嘱时，该组代表一个频次下被分解的执行条目集合
 * 
 * @author kingbox
 *
 */
public class OrderExecuteTeam {

	private String id;

	private List<OrderExecute> executes = new ArrayList<OrderExecute>();

	public OrderExecuteTeam() {
		this.id = UUID.randomUUID().toString();
	}

	public void addOrderExecute(OrderExecute execute) {

		execute.setId(UUID.randomUUID().toString());
		execute.setTeamId(id);

		if (executes.size() == 0) {
			executes.add(execute);
		} else {
			OrderExecute previous = executes.get(executes.size() - 1);
			previous.setNextId(execute.getId());
			execute.setPreviousId(previous.getId());

			executes.add(execute);
		}
	}

	public List<OrderExecute> getExecutes() {
		return executes;
	}
}
