//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\app\\pharmacy\\domain\\ConfigureFluidOrder.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.neusoft.hs.domain.order.ConfigureFluidOrderExecute;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientAreaDept;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.print.Printable;

@Entity
@Table(name = "app_pharmacy_configure_fluid_order")
public class ConfigureFluidOrder extends IdEntity implements Printable{

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "batch_id")
	private ConfigureFluidBatch batch;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	private InPatientAreaDept area;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pharmacy_id")
	private Pharmacy pharmacy;

	@OneToMany(mappedBy = "fluidOrder", cascade = { CascadeType.REFRESH })
	private List<ConfigureFluidOrderExecute> executes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private AbstractUser creator;

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

	public void setExecutes(List<ConfigureFluidOrderExecute> executes) {
		this.executes = executes;

		this.executes.forEach(item -> {
			item.setFluidOrder(this);
		});
	}

	@Override
	public Map<String, Object> getPrintData() {
		// TODO Auto-generated method stub
		return null;
	}

}
