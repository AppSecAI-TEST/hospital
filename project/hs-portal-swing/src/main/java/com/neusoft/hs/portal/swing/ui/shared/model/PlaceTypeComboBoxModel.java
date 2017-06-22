package com.neusoft.hs.portal.swing.ui.shared.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.order.Order;

@Component
public class PlaceTypeComboBoxModel extends DefaultComboBoxModel<String> {

	public PlaceTypeComboBoxModel() {
		
		List<String> placeTypes = new ArrayList<String>();
		placeTypes.add(Order.PlaceType_InPatient);
		placeTypes.add(Order.PlaceType_OutPatient);

		this.addElements(placeTypes);
	}
}
