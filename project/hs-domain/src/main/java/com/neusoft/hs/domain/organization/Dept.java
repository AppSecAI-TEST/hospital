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
	private List<OrderExecute> orderExecutes;

	@OneToMany(mappedBy = "dept", cascade = { CascadeType.ALL })
	private List<ChargeRecord> chargeRecords;

	@OneToMany(mappedBy = "dept", cascade = { CascadeType.ALL })
	private List<Doctor> doctors;

	@OneToMany(mappedBy = "dept", cascade = { CascadeType.ALL })
	private List<Visit> visits;

}
