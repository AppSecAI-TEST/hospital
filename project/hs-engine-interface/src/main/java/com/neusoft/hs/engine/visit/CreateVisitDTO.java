package com.neusoft.hs.engine.visit;

import java.util.Date;

public class CreateVisitDTO {

	private String cardNumber;

	private String name;

	private Date birthday;

	private String sex;

	private String state;

	private String respDoctorId;

	private String deptId;

	private String operatorId;

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

	public String getRespDoctorId() {
		return respDoctorId;
	}

	public void setRespDoctorId(String respDoctorId) {
		this.respDoctorId = respDoctorId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public boolean isRepeatVisit() {
		return repeatVisit;
	}

	public void setRepeatVisit(boolean repeatVisit) {
		this.repeatVisit = repeatVisit;
	}
}
