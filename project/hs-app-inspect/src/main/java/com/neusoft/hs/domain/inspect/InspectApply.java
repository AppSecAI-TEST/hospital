//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\InspectApply.java

package com.neusoft.hs.domain.inspect;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.neusoft.hs.domain.order.Apply;

/**
 * 检查申请单
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "app_inspect_apply")
public class InspectApply extends Apply {

	@OneToMany(mappedBy = "inspectApply", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REMOVE })
	private List<InspectApplyItem> inspectApplyItems;

	@OneToMany(mappedBy = "inspectApply", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REMOVE })
	private List<InspectResult> inspectResults;

	public List<InspectApplyItem> getInspectApplyItems() {
		return inspectApplyItems;
	}

	public void setInspectApplyItems(List<InspectApplyItem> inspectApplyItems) {
		this.inspectApplyItems = inspectApplyItems;
	}

	public void addInspectApplyItem(InspectApplyItem inspectApplyItem) {
		if (this.inspectApplyItems == null) {
			this.inspectApplyItems = new ArrayList<InspectApplyItem>();
		}
		this.inspectApplyItems.add(inspectApplyItem);
		inspectApplyItem.setInspectApply(this);
	}

	public void addInspectResult(InspectResult result) {
		if (this.inspectResults == null) {
			this.inspectResults = new ArrayList<InspectResult>();
		}
		result.setInspectApply(this);
		this.inspectResults.add(result);
	}

	public List<InspectResult> getInspectResults() {
		return inspectResults;
	}

	public void setInspectResults(List<InspectResult> inspectResults) {
		this.inspectResults = inspectResults;
	}

}
