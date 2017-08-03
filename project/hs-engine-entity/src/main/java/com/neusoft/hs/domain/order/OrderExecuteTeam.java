package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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

	@OneToMany(mappedBy = "team", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REMOVE })
	private List<OrderExecute> executes;

	@Transient
	private int sequence = 0;

	public OrderExecuteTeam() {
	}

	/**
	 * 增加执行条目
	 * 
	 * @param execute
	 */
	public void addOrderExecute(OrderExecute execute) {

		if (executes == null || executes.size() == 0) {
			executes = new ArrayList<OrderExecute>();
		}
		if (!execute.isAlone()) {
			OrderExecute previous = findPrevious();
			if (previous != null) {
				previous.setNext(execute);
			}
			execute.setPrevious(previous);
			execute.setTeamSequence(sequence++);
		}
		execute.setTeam(this);

		executes.add(execute);
	}

	private OrderExecute findPrevious() {
		if (executes.size() > 0) {
			OrderExecute previous = null;
			for (int i = executes.size() - 1; i >= 0; i--) {
				if (!executes.get(i).isAlone()) {
					previous = executes.get(i);
					break;
				}
			}
			return previous;
		} else {
			return null;
		}
	}

	public List<OrderExecute> getExecutes() {
		return executes;
	}
}
