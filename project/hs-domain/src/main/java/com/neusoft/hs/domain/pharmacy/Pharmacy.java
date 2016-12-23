//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\pharmacy\\Pharmacy.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.neusoft.hs.domain.organization.Dept;

@Entity
@DiscriminatorValue("Pharmacy")
public class Pharmacy extends Dept {

	@OneToMany(mappedBy = "pharmacy", cascade = { CascadeType.ALL })
	private List<DrugType> drugTypes;

	public List<DrugType> getDrugTypes() {
		return drugTypes;
	}

	public void setDrugTypes(List<DrugType> drugTypes) {
		this.drugTypes = drugTypes;
	}

}
