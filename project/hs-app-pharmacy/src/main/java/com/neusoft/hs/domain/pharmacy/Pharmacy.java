//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\pharmacy\\Pharmacy.java

package com.neusoft.hs.domain.pharmacy;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.platform.util.DateUtil;

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

	public List<DrugTypeConsumeRecord> withhold(DrugTypeSpec drugTypeSpec,
			Integer count) throws PharmacyException {

		List<DrugType> theDrugTypes = this.getTheDrugTypes(drugTypeSpec);
		if (theDrugTypes == null || theDrugTypes.size() == 0) {
			throw new PharmacyException("药房[" + this.getName() + "]没有["
					+ drugTypeSpec.getName() + "]药品");
		}
		DrugType theDrugType = theDrugTypes.get(0);

		theDrugType.withhold(count);

		List<DrugTypeConsumeRecord> consumeRecords = new ArrayList<DrugTypeConsumeRecord>();

		DrugTypeConsumeRecord consumeRecord = new DrugTypeConsumeRecord();
		consumeRecord.setDrugType(theDrugType);
		consumeRecord.setCount(count);
		consumeRecord.setType(DrugTypeConsumeRecord.Type_Withhold);
		consumeRecord.setCreateDate(DateUtil.getSysDate());

		consumeRecords.add(consumeRecord);

		return consumeRecords;

	}

	public void unWithhold(List<DrugTypeConsumeRecord> consumeRecords)
			throws PharmacyException {
		for (DrugTypeConsumeRecord consumeRecord : consumeRecords) {
			consumeRecord.getDrugType().unWithhold(consumeRecord.getCount());
		}

	}

	public List<DrugTypeConsumeRecord> send(DrugTypeSpec drugTypeSpec, Integer count)
			throws PharmacyException {
		
		List<DrugType> theDrugTypes = this.getTheDrugTypes(drugTypeSpec);
		if (theDrugTypes == null || theDrugTypes.size() == 0) {
			throw new PharmacyException("药房[" + this.getName() + "]没有["
					+ drugTypeSpec.getName() + "]药品");
		}
		DrugType theDrugType = theDrugTypes.get(0);

		theDrugType.send(count);

		List<DrugTypeConsumeRecord> consumeRecords = new ArrayList<DrugTypeConsumeRecord>();

		DrugTypeConsumeRecord consumeRecord = new DrugTypeConsumeRecord();
		consumeRecord.setDrugType(theDrugType);
		consumeRecord.setCount(count);
		consumeRecord.setType(DrugTypeConsumeRecord.Type_Dispense);
		consumeRecord.setCreateDate(DateUtil.getSysDate());

		consumeRecords.add(consumeRecord);

		return consumeRecords;

	}

	public void unSend(List<DrugTypeConsumeRecord> consumeRecords)
			throws PharmacyException {
		for (DrugTypeConsumeRecord consumeRecord : consumeRecords) {
			consumeRecord.getDrugType().unSend(consumeRecord.getCount());
		}
	}

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

	public List<DrugType> getTheDrugTypes(DrugTypeSpec drugTypeSpec) {
		return this.getService(DrugTypeRepo.class).findByDrugTypeSpec(
				drugTypeSpec);
	}

}
