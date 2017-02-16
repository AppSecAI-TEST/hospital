//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\entity\\treatment\\TreatmentItem.java

package com.neusoft.hs.domain.treatment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_treatment_item")
public class TreatmentItem extends IdEntity {

	@NotEmpty(message = "名称不能为空")
	@Column(length = 128)
	private String name;

	@NotEmpty(message = "类型不能为空")
	@Column(length = 32)
	private String type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "treatment_spec_id")
	private TreatmentItemSpec treatmentItemSpec;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@OneToMany(mappedBy = "item", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<TreatmentItemValue> values;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private AbstractUser creator;

	@Column(name = "create_date")
	private Date createDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TreatmentItemSpec getTreatmentItemSpec() {
		return treatmentItemSpec;
	}

	public void setTreatmentItemSpec(TreatmentItemSpec treatmentItemSpec) {
		this.treatmentItemSpec = treatmentItemSpec;
		this.type = this.treatmentItemSpec.getName();
		this.name = this.treatmentItemSpec.getName();
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public List<TreatmentItemValue> getValues() {
		return values;
	}

	public void setValues(List<TreatmentItemValue> values) {
		this.values = values;
	}

	public void addValue(TreatmentItemValue value) {
		if (this.values == null) {
			this.values = new ArrayList<TreatmentItemValue>();
		}
		this.values.add(value);

		value.setItem(this);
		value.setVisit(visit);
		value.setTreatmentItemSpec(treatmentItemSpec);
	}

	public AbstractUser getCreator() {
		return creator;
	}

	public void setCreator(AbstractUser creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void save() {
		this.getService(TreatmentItemRepo.class).save(this);
	}

}
