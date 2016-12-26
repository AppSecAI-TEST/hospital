package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
