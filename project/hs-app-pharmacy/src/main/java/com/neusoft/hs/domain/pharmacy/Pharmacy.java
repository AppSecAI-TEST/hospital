//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\pharmacy\\Pharmacy.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neusoft.hs.domain.organization.Dept;

/**
 * 药房 包含多种有库存的药品
 * 
 * @author kingbox
 *
 */
@Entity
@DiscriminatorValue("Pharmacy")
public class Pharmacy extends Dept {

	@JsonIgnore
	@OneToMany(mappedBy = "pharmacy", cascade = { CascadeType.ALL })
	private List<DrugType> drugTypes;

	@JsonIgnore
	@OneToMany(mappedBy = "pharmacy", cascade = { CascadeType.ALL })
	private List<DispenseDrugWin> dispenseDrugWins;

	public List<DrugType> getDrugTypes() {
		return drugTypes;
	}

	public void setDrugTypes(List<DrugType> drugTypes) {
		this.drugTypes = drugTypes;
	}

	public List<DispenseDrugWin> getDispenseDrugWins() {
		return dispenseDrugWins;
	}

	public void setDispenseDrugWins(List<DispenseDrugWin> dispenseDrugWins) {
		this.dispenseDrugWins = dispenseDrugWins;
	}
}
