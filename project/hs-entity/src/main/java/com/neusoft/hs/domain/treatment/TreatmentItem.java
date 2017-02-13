//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\entity\\treatment\\TreatmentItem.java

package com.neusoft.hs.domain.treatment;

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
	private TreatmentItemSpec treatmentSpec;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@OneToMany(mappedBy = "item", cascade = { CascadeType.ALL })
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

	public TreatmentItemSpec getTreatmentSpec() {
		return treatmentSpec;
	}

	public void setTreatmentSpec(TreatmentItemSpec treatmentSpec) {
		this.treatmentSpec = treatmentSpec;
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

}
