//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\TemporaryOrder.java

package com.neusoft.hs.domain.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.organization.AbstractUser;

@Entity
@DiscriminatorValue("Temporary")
public class TemporaryOrder extends Order {

	@Column(name = "execute_date")
	private Date executeDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "execute_user_id")
	private AbstractUser executeUser;

	@Override
	public void updateState(OrderExecute orderExecute) {
		this.setState(Order.State_Finished);
		this.setStateDesc("已完成");
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public AbstractUser getExecuteUser() {
		return executeUser;
	}

	public void setExecuteUser(AbstractUser executeUser) {
		this.executeUser = executeUser;
	}
}
