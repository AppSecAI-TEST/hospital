//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DrugOrderType.java

package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.neusoft.hs.domain.pharmacy.DrugType;

@DiscriminatorValue("DrugOrderType")
public class DrugOrderType extends OrderType {
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_type_id")
	private DrugType drugType;

	/**
	 * @roseuid 58573EC402C3
	 */
	public DrugOrderType() {

	}
}
