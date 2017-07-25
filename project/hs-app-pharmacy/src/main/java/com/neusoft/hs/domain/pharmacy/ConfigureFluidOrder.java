//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\app\\pharmacy\\domain\\ConfigureFluidOrder.java

package com.neusoft.hs.domain.pharmacy;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.order.ConfigureFluidOrderExecute;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.InPatientAreaDept;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.print.Printable;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@Table(name = "app_pharmacy_configure_fluid_order")
public class ConfigureFluidOrder extends IdEntity implements Printable {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "batch_id")
	private ConfigureFluidBatch batch;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	private InPatientAreaDept area;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pharmacy_id")
	private Pharmacy pharmacy;

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@Column(name = "finish_date")
	private Date finishDate;

	@OneToMany(mappedBy = "fluidOrder", cascade = { CascadeType.REFRESH })
	private List<ConfigureFluidOrderExecute> executes;

	@Column(name = "execute_count")
	private int executeCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private AbstractUser creator;

	public static final String State_NeedExecute = "待执行";

	public static final String State_Executed = "已执行";

	public static final String State_Sended = "已送达";

	/**
	 * @roseuid 592E6E7C034E
	 */
	public void save() {
		this.getService(ConfigureFluidOrderRepo.class).save(this);
	}

	public ConfigureFluidBatch getBatch() {
		return batch;
	}

	public void setBatch(ConfigureFluidBatch batch) {
		this.batch = batch;
	}

	public InPatientAreaDept getArea() {
		return area;
	}

	public void setArea(InPatientAreaDept area) {
		this.area = area;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public AbstractUser getCreator() {
		return creator;
	}

	public void setCreator(AbstractUser creator) {
		this.creator = creator;
	}

	public List<ConfigureFluidOrderExecute> getExecutes() {
		return executes;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public int getExecuteCount() {
		return executeCount;
	}

	public void setExecuteCount(int executeCount) {
		this.executeCount = executeCount;
	}

	public void setExecutes(List<ConfigureFluidOrderExecute> executes) {
		this.executes = executes;
		this.executeCount = executes.size();
		
		this.executes.forEach(item -> {
			item.setFluidOrder(this);
		});
	}

	@Override
	public Map<String, Object> getPrintData() {
		// TODO Auto-generated method stub
		return null;
	}

	public void finish(AbstractUser user) {
		this.state = State_Executed;
		this.finishDate = DateUtil.getSysDate();
	}

}
