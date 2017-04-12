package com.neusoft.hs.engine.cost;

import java.io.Serializable;

public class ChargeBillCreateDTO implements Serializable {

	private static final long serialVersionUID = 2417698891234881908L;

	private String visitId;

	private float balance;

	private String userId;

	public String getVisitId() {
		return visitId;
	}

	public void setVisitId(String visitId) {
		this.visitId = visitId;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
