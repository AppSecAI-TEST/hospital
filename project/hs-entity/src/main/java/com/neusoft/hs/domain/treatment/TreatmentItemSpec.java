//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\entity\\treatment\\TreatmentItemSpec.java

package com.neusoft.hs.domain.treatment;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.organization.Role;
import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "domain_treatment_spec")
public class TreatmentItemSpec extends SuperEntity {
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 128)
	private String name;

	@NotEmpty(message = "类型不能为空")
	@Column(length = 32)
	private String type;

	@Column(name = "should_create_date")
	private Date shouldCreateDate;

	@OneToMany(mappedBy = "treatmentSpec", cascade = { CascadeType.ALL })
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getShouldCreateDate() {
		return shouldCreateDate;
	}

	public void setShouldCreateDate(Date shouldCreateDate) {
		this.shouldCreateDate = shouldCreateDate;
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
