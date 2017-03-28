package com.neusoft.hs.domain.visit;

import java.util.Date;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientDept;

public class CreateVisitVO {

	private String cardNumber;

	private String name;

	private Date birthday;

	private String sex;

	private String state;

	private Doctor respDoctor;

	private Dept dept;

	private AbstractUser operator;

	private boolean repeatVisit = false;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Doctor getRespDoctor() {
		return respDoctor;
	}

	public void setRespDoctor(Doctor respDoctor) {
		this.respDoctor = respDoctor;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public AbstractUser getOperator() {
		return operator;
	}

	public void setOperator(AbstractUser operator) {
		this.operator = operator;
	}

	public boolean isRepeatVisit() {
		return repeatVisit;
	}

	public void setRepeatVisit(boolean repeatVisit) {
		this.repeatVisit = repeatVisit;
	}
}
