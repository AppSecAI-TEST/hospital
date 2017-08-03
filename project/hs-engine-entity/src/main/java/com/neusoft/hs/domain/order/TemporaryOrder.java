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
import com.neusoft.hs.platform.util.DateUtil;

/**
 * 临时医嘱条目
 * 
 * @author kingbox
 *
 */
@Entity
@DiscriminatorValue("Temporary")
public class TemporaryOrder extends Order {

	@Column(name = "execute_date")
	private Date executeDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "execute_user_id")
	private AbstractUser executeUser;

	public static final String Category = "T";

	@Override
	public void doFinish(OrderExecute orderExecute) {
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

	@Override
	protected void setCategory(OrderExecute orderExecute) {
		orderExecute.setOrderCategory(Category);
	}

	@Override
	protected void doExecute(OrderExecute orderExecute) {
		if (orderExecute.isMain()) {
			this.setExecuteDate(DateUtil.getSysDate());
			this.setExecuteUser(orderExecute.getActualExecutor());
		}
	}
}
