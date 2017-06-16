package com.neusoft.hs.portal.swing.ui.forms.order.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.order.OrderFrequencyType;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.ui.shared.model.DrugUseModeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderFrequencyTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.StringComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

@Component
public class CreateOrderPanel extends JPanel {

	private JTextField planStartDateTF;

	private JTextField placeTypeTF;

	private JTextField frequencyTypeTF;

	private JTextField orderUseModeTF;

	private JTextField countTF;

	private JComboBox<Visit> visitCB;
	private VisitComboBoxModel visitComboBoxModel;

	private JComboBox<OrderType> orderTypeCB;
	private OrderTypeComboBoxModel orderTypeComboBoxModel;

	private JComboBox<String> placeTypeCB;
	private StringComboBoxModel placeTypeComboBoxModel;

	private JComboBox<OrderFrequencyType> frequencyTypeCB;
	private OrderFrequencyTypeComboBoxModel frequencyTypeComboBoxModel;

	private JComboBox<DrugUseMode> orderUseModeCB;
	private DrugUseModeComboBoxModel orderUseModeComboBoxModel;

	private JButton confirmBtn;

	private static final int LAYOUT_ROWS = 4;
	private static final int LAYOUT_COLS = 2;
	private static final int HORIZONTAL_GAP = 0;
	private static final int VERTICAL_GAP = 20;
	private static final int TEXT_FIELD_COLUMNS = 20;

	@Autowired
	public CreateOrderPanel(VisitComboBoxModel visitComboBoxModel,
			OrderTypeComboBoxModel orderTypeComboBoxModel,
			StringComboBoxModel placeTypeComboBoxModel,
			OrderFrequencyTypeComboBoxModel frequencyTypeComboBoxModel,
			DrugUseModeComboBoxModel orderUseModeComboBoxModel) {
		this.visitComboBoxModel = visitComboBoxModel;
		this.orderTypeComboBoxModel = orderTypeComboBoxModel;
		this.placeTypeComboBoxModel = placeTypeComboBoxModel;
		this.frequencyTypeComboBoxModel = frequencyTypeComboBoxModel;
		this.orderUseModeComboBoxModel = orderUseModeComboBoxModel;

		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(Borders.createEmptyBorder());
		setLayout(new BorderLayout());
	}

	private void initComponents() {

		JPanel formPanl = new JPanel();
		formPanl.setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS,
				HORIZONTAL_GAP, VERTICAL_GAP));

		JLabel visitLbl = new JLabel(ConstMessagesEN.Labels.Visit);
		JLabel orderTypeLbl = new JLabel(ConstMessagesEN.Labels.OrderType);
		JLabel planStartDateLbl = new JLabel(
				ConstMessagesEN.Labels.PlanStartDate);
		JLabel placeTypeLbl = new JLabel(ConstMessagesEN.Labels.PlaceType);
		JLabel frequencyTypeLbl = new JLabel(
				ConstMessagesEN.Labels.FrequencyType);
		JLabel orderUseModeLbl = new JLabel(ConstMessagesEN.Labels.OrderUseMode);
		JLabel countLbl = new JLabel(ConstMessagesEN.Labels.Count);

		planStartDateTF = new JTextField(TEXT_FIELD_COLUMNS);
		countTF = new JTextField(TEXT_FIELD_COLUMNS);

		visitCB = new JComboBox<>(visitComboBoxModel);
		orderTypeCB = new JComboBox<>(orderTypeComboBoxModel);
		placeTypeCB = new JComboBox<>(placeTypeComboBoxModel);

		frequencyTypeCB = new JComboBox<>(frequencyTypeComboBoxModel);
		orderUseModeCB = new JComboBox<>(orderUseModeComboBoxModel);

		formPanl.add(visitLbl);
		formPanl.add(visitCB);

		formPanl.add(orderTypeLbl);
		formPanl.add(orderTypeCB);

		formPanl.add(planStartDateLbl);
		formPanl.add(planStartDateTF);

		formPanl.add(placeTypeLbl);
		formPanl.add(placeTypeCB);

		formPanl.add(frequencyTypeLbl);
		formPanl.add(frequencyTypeCB);

		formPanl.add(orderUseModeLbl);
		formPanl.add(orderUseModeCB);

		formPanl.add(countLbl);
		formPanl.add(countTF);

		add(formPanl, BorderLayout.SOUTH);

		JPanel buttonPanel = new JPanel();

		confirmBtn = new JButton(ConstMessagesEN.Labels.CONFIRM_BTN);
		buttonPanel.add(confirmBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public JButton getConfirmBtn() {
		return confirmBtn;
	}

}
