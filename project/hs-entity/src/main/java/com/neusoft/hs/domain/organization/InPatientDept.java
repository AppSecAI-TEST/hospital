//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\organization\\Dept.java

package com.neusoft.hs.domain.organization;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("InPatientDept")
public class InPatientDept extends Dept {

	@OneToMany(mappedBy = "dept", cascade = { CascadeType.REFRESH })
	private List<Nurse> nurses;

	@OneToMany(mappedBy = "dept", cascade = { CascadeType.REFRESH })
	private List<Doctor> doctors;

	@OneToMany(mappedBy = "belongDept", cascade = { CascadeType.REFRESH })
	private List<Order> orders;

	@OneToMany(mappedBy = "belongDept", cascade = { CascadeType.REFRESH })
	private List<OrderExecute> orderExecutes;

	@OneToMany(mappedBy = "respDept", cascade = { CascadeType.REFRESH })
	private List<Visit> visits;

	public InPatientDept() {
	}

	public InPatientDept(String id) {
		this.setId(id);
	}

	public List<Nurse> getNurses() {
		return nurses;
	}

	public void setNurses(List<Nurse> nurses) {
		this.nurses = nurses;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
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

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

}
