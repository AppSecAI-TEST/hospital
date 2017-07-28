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

import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

/**
 * 与患者一次就诊关联的一个具体诊疗项目
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_treatment_item")
public class TreatmentItem extends IdEntity implements Itemable {

	@NotEmpty(message = "名称不能为空")
	@Column(length = 128)
	private String name;

	@NotEmpty(message = "类型不能为空")
	@Column(length = 32)
	private String type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "treatment_spec_id")
	private TreatmentItemSpec treatmentItemSpec;

	@OneToMany(mappedBy = "item", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<TreatmentItemValue> values;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Column(name = "visit_name", length = 16)
	private String visitName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private AbstractUser creator;

	@Column(name = "creator_name", length = 32)
	private String creatorName;

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
		this.visitName = visit.getName();
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	public List<TreatmentItemValue> getValues() {
		return values;
	}

	public String getValueInfo() {
		StringBuffer info = new StringBuffer();
		info.append(values.size());
		info.append(":");

		for (TreatmentItemValue value : values) {
			info.append(value.toString());
			info.append("\n");
		}
		return info.toString();
	}

	public void setValues(List values) {
		this.values = values;
	}

	public void addValue(ItemValue value) {

		TreatmentItemValue treatmentItemValue = (TreatmentItemValue) value;

		if (this.values == null) {
			this.values = new ArrayList<TreatmentItemValue>();
		}
		this.values.add(treatmentItemValue);

		treatmentItemValue.setItem(this);
		treatmentItemValue.setVisit(visit);
		treatmentItemValue.setVisitName(visitName);
		treatmentItemValue.setTreatmentItemSpec(treatmentItemSpec);
	}

	@Override
	public void updateValue(ItemValue value) {
		// 删除原value
		if (this.values != null && this.values.size() >= 0) {
			for (ItemValue oldValue : this.values) {
				oldValue.delete();
			}
		}
		this.values = null;
		// 增加新value
		this.addValue(value);
	}

	public AbstractUser getCreator() {
		return creator;
	}

	public void setCreator(AbstractUser creator) {
		this.creator = creator;
		this.creatorName = creator.getName();
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
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

	public void delete() {
		this.getService(TreatmentItemRepo.class).delete(this);
	}

	public void doLoad() {
		for (TreatmentItemValue value : values) {
			value.doLoad();
		}
	}
}
