//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\TreatmentItemValue.java

package com.neusoft.hs.domain.treatment;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_treatment_item_value")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TreatmentItemValue extends IdEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private TreatmentItem item;

	public TreatmentItem getItem() {
		return item;
	}

	public void setItem(TreatmentItem item) {
		this.item = item;
	}
}
