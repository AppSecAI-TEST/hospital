package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

	@OneToMany(mappedBy = "compsiteOrder", cascade = { CascadeType.REMOVE })
	private List<Order> orders;

	@OneToMany(mappedBy = "compsiteOrder", cascade = { CascadeType.ALL })
	private List<OrderExecute> orderExecutes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private Doctor creator;

	@Column(name = "create_date")
	private Date createDate;

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
	public void save() {

		this.getService(CompsiteOrderRepo.class).save(this);

		for (Order order : this.orders) {
			order.save();
		}
	}
}
