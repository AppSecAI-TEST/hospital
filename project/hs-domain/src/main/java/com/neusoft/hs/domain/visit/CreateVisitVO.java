package com.neusoft.hs.domain.visit;

import java.util.Date;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientDept;

public class CreateVisitVO {

	private String cardNumber;

	private String name;

	private Date birthday;

	private String sex;

	private Doctor respDoctor;

	private InPatientDept respDept;

	private AbstractUser operator;

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

	public Doctor getRespDoctor() {
		return respDoctor;
	}

	public void setRespDoctor(Doctor respDoctor) {
		this.respDoctor = respDoctor;
	}

	public InPatientDept getRespDept() {
		return respDept;
	}

	public void setRespDept(InPatientDept respDept) {
		this.respDept = respDept;
	}

	public AbstractUser getOperator() {
		return operator;
	}

	public void setOperator(AbstractUser operator) {
		this.operator = operator;
	}

}
