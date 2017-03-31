package com.neusoft.hs.engine.cost;

import java.io.Serializable;

public class UnChargingVO implements Serializable{

	private static final long serialVersionUID = -3281157492029115760L;

	private String executeId;

	private boolean isBackCost;

	private String userId;

	public String getExecuteId() {
		return executeId;
	}

	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}

	public boolean isBackCost() {
		return isBackCost;
	}

	public void setBackCost(boolean isBackCost) {
		this.isBackCost = isBackCost;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
