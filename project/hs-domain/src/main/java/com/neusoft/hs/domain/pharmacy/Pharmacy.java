//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\pharmacy\\Pharmacy.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "domain_pharmacy")
public class Pharmacy extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 64)
	private String name;

	@OneToMany(mappedBy = "pharmacy", cascade = { CascadeType.ALL })
	private List<DrugType> drugTypes;

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

	public List<DrugType> getDrugTypes() {
		return drugTypes;
	}

	public void setDrugTypes(List<DrugType> drugTypes) {
		this.drugTypes = drugTypes;
	}

}
