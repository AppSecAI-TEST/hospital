//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\organization\\Dept.java

package com.neusoft.hs.domain.organization;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("Dept")
public class Dept extends Unit {

	@OneToMany(mappedBy = "dept", cascade = { CascadeType.ALL })
	private List<Nurse> nurses;

	@OneToMany(mappedBy = "dept", cascade = { CascadeType.ALL })
	private List<Doctor> doctors;

	@OneToMany(mappedBy = "executeDept", cascade = { CascadeType.ALL })
	private List<OrderExecute> orderExecutes;

	@OneToMany(mappedBy = "chargeDept", cascade = { CascadeType.ALL })
	private List<ChargeRecord> chargeRecords;

	@OneToMany(mappedBy = "respDept", cascade = { CascadeType.ALL })
	private List<Visit> visits;

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

	public List<OrderExecute> getOrderExecutes() {
		return orderExecutes;
	}

	public void setOrderExecutes(List<OrderExecute> orderExecutes) {
		this.orderExecutes = orderExecutes;
	}

	public List<ChargeRecord> getChargeRecords() {
		return chargeRecords;
	}

	public void setChargeRecords(List<ChargeRecord> chargeRecords) {
		this.chargeRecords = chargeRecords;
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

}
