//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\entity\\treatment\\TreatmentItemSpec.java

package com.neusoft.hs.domain.treatment;

import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.organization.Role;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.SuperEntity;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@Table(name = "domain_treatment_spec")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "treatmentItemSpecCache")
public abstract class TreatmentItemSpec extends SuperEntity {
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 128)
	private String name;

	@Column(name = "should_create_interval_hour")
	private Integer shouldIntervalHour;

	@OneToMany(mappedBy = "treatmentItemSpec", cascade = { CascadeType.ALL })
	private List<TreatmentItem> treatmentItems;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resp_role_id")
	public Role respRole;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getShouldIntervalHour() {
		return shouldIntervalHour;
	}

	public void setShouldIntervalHour(Integer shouldIntervalHour) {
		this.shouldIntervalHour = shouldIntervalHour;
	}

	public Date getShouldDate(Visit visit) throws TreatmentException {
		if (visit.getIntoWardDate() == null) {
			throw new TreatmentException("患者[" + visit.getName() + "]还没有入院");
		}
		if (this.shouldIntervalHour == null) {
			return null;
		} else {
			return DateUtil.addHour(visit.getIntoWardDate(),
					this.shouldIntervalHour);
		}
	}

	public TreatmentItem getTheItem(Visit visit) {
		return this.getService(TreatmentItemRepo.class)
				.findByVisitAndTreatmentItemSpec(visit, this);
	}

	public List<TreatmentItem> getTreatmentItems() {
		return treatmentItems;
	}

	public void setTreatmentItems(List<TreatmentItem> treatmentItems) {
		this.treatmentItems = treatmentItems;
	}

	public Role getRespRole() {
		return respRole;
	}

	public void setRespRole(Role respRole) {
		this.respRole = respRole;
	}
}
