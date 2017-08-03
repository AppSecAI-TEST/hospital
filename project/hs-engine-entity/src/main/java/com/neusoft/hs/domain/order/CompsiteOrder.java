package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

/**
 * 组合医嘱
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_order_team")
public class CompsiteOrder extends IdEntity implements OrderCreateCommand {

	@OneToMany(mappedBy = "compsiteOrder", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REMOVE })
	private List<Order> orders;

	@OneToMany(mappedBy = "compsiteOrder", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REMOVE })
	private List<OrderExecute> orderExecutes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private Doctor creator;

	@Column(name = "create_date")
	private Date createDate;

	/**
	 * 增加医嘱
	 * 
	 * @param order
	 * @throws OrderException
	 */
	public void addOrder(Order order) throws OrderException {
		if (this.orders == null) {
			this.orders = new ArrayList<Order>();
		}
		if (this.orders.size() > 0) {
			this.orders.get(0).compsiteMatch(order);
		}
		this.orders.add(order);
		order.setCompsiteOrder(this);
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<OrderExecute> getOrderExecutes() {
		return orderExecutes;
	}

	public void setOrderExecutes(List<OrderExecute> orderExecutes) {
		this.orderExecutes = orderExecutes;
	}

	public Doctor getCreator() {
		return creator;
	}

	public void setCreator(Doctor creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String getPlaceType() {
		return this.orders.get(0).getPlaceType();
	}

	@Override
	public Visit getVisit() {
		return this.orders.get(0).getVisit();
	}

	@Override
	public void setVisit(Visit visit) {
		this.orders.get(0).setVisit(visit);
	}

	@Override
	public void check() throws OrderException, OrderExecuteException {
		for (Order order : this.orders) {
			order.check();
		}
		this.checkSelf();
	}

	/**
	 * 检查组合医嘱是否合乎业务规则
	 * 
	 * @throws OrderException
	 */
	public void checkSelf() throws OrderException {

		Set<Class<?>> types = new HashSet<Class<?>>();
		Set<Class<?>> orderTypes = new HashSet<Class<?>>();
		Set<OrderFrequencyType> frequencyTypes = new HashSet<OrderFrequencyType>();
		Set<Dept> executeDepts = new HashSet<Dept>();

		for (Order order : this.orders) {

			if (types.size() == 0) {
				types.add(order.getClass());
			} else if (!types.contains(order.getClass())) {
				throw new OrderException(null, "组合医嘱类型(临嘱长嘱)必须相同");
			}
			if (orderTypes.size() == 0) {
				orderTypes.add(order.getOrderType().getClass());
			} else if (!orderTypes.contains(order.getOrderType().getClass())) {
				throw new OrderException(null, "组合医嘱类型必须相同");
			}
			if (executeDepts.size() == 0) {
				executeDepts.add(order.getExecuteDept());
			} else if (!executeDepts.contains(order.getExecuteDept())) {
				throw new OrderException(null, "组合医嘱执行部门必须相同");
			}
			if (order instanceof LongOrder) {
				if (frequencyTypes.size() == 0) {
					frequencyTypes.add(((LongOrder) order).getFrequencyType());
				} else if (!frequencyTypes.contains(((LongOrder) order)
						.getFrequencyType())) {
					throw new OrderException(null, "组合医嘱频次必须相同");
				}
			}
		}
	}

	@Override
	public void save() {

		this.getService(CompsiteOrderRepo.class).save(this);

		for (Order order : this.orders) {
			order.save();
		}
	}
}
