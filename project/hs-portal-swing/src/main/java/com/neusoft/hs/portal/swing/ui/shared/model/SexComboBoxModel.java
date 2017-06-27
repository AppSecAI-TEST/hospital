package com.neusoft.hs.portal.swing.ui.shared.model;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.hs.domain.patient.Patient;


public class SexComboBoxModel extends DefaultComboBoxModel<String> {

	public SexComboBoxModel() {
		
		List<String> sexes = new ArrayList<String>();
		sexes.add(null);
		sexes.add(Patient.Sex_Male);
		sexes.add(Patient.Sex_FeMale);

		this.addElements(sexes);
	}
}
