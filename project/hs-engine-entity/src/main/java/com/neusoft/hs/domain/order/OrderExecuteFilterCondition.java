//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\engine\\entity\\order\\OrderExecuteFilterCondition.java

package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.neusoft.hs.domain.organization.Dept;

public class OrderExecuteFilterCondition {

	private Date begin;

	private Date end;

	private Dept executeDept;

	private List<? extends Dept> belongDepts;
	
	private List<String> types;

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Dept getExecuteDept() {
		return executeDept;
	}

	public void setExecuteDept(Dept executeDept) {
		this.executeDept = executeDept;
	}

	public List<? extends Dept> getBelongDepts() {
		return belongDepts;
	}

	public void setBelongDepts(List<? extends Dept> belongDepts) {
		this.belongDepts = belongDepts;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}
	
	public void addType(String type){
		if(this.types == null){
			this.types = new ArrayList<String>();
		}
		this.types.add(type);
	}

}
