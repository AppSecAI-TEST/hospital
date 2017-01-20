//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\InspectApply.java

package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("Inspect")
public class InspectApply extends Apply {

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "domain_inspect_apply_inspect_item", joinColumns = { @JoinColumn(name = "inspect_apply_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "inspect_item_id", referencedColumnName = "id") })
	private List<InspectItem> inspectItems;

	public List<InspectItem> getInspectItems() {
		return inspectItems;
	}

	public void setInspectItems(List<InspectItem> inspectItems) {
		this.inspectItems = inspectItems;
	}
	
	public void addInspectItem(InspectItem inspectItem){
		if(this.inspectItems == null){
			this.inspectItems = new ArrayList<InspectItem>();
		}
		this.inspectItems.add(inspectItem);
		
	}

}
