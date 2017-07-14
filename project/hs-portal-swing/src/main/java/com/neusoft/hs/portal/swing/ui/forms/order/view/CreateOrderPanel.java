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
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.ui.shared.model.DrugUseModeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderFrequencyTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.PharmacyComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.PlaceTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.widget.SpinnerDate;
import com.neusoft.hs.portal.swing.widget.SpinnerNumber;

@Component
public class CreateOrderPanel extends JPanel {

	SpinnerDate planStartDateSD;

	JTextField placeTypeTF;

	JTextField frequencyTypeTF;

	JTextField orderUseModeTF;

	SpinnerNumber countSN;

	JComboBox<Visit> visitCB;
	VisitComboBoxModel visitComboBoxModel;

	JComboBox<OrderType> orderTypeCB;
	OrderTypeComboBoxModel orderTypeComboBoxModel;

	JComboBox<OrderFrequencyType> frequencyTypeCB;
	OrderFrequencyTypeComboBoxModel frequencyTypeComboBoxModel;

	JComboBox<DrugUseMode> orderUseModeCB;
	DrugUseModeComboBoxModel orderUseModeComboBoxModel;

	JComboBox<Pharmacy> pharmacyCB;
	PharmacyComboBoxModel pharmacyComboBoxModel;

	JButton confirmBtn;
	
	JButton closeBtn;

	private static final int LAYOUT_ROWS = 4;
	private static final int LAYOUT_COLS = 2;
	private static final int HORIZONTAL_GAP = 0;
	private static final int VERTICAL_GAP = 20;
	private static final int TEXT_FIELD_COLUMNS = 20;

	@Autowired
	public CreateOrderPanel() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(Borders.createEmptyBorder());
		setLayout(new BorderLayout());
	}

	private void initComponents() {

		this.visitComboBoxModel = new VisitComboBoxModel();
		this.orderTypeComboBoxModel = new OrderTypeComboBoxModel();
		this.frequencyTypeComboBoxModel = new OrderFrequencyTypeComboBoxModel();
		this.orderUseModeComboBoxModel = new DrugUseModeComboBoxModel();
		this.pharmacyComboBoxModel = new PharmacyComboBoxModel();

		JPanel formPanl = new JPanel();
		formPanl.setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS,
				HORIZONTAL_GAP, VERTICAL_GAP));

		JLabel visitLbl = new JLabel(ConstMessagesCN.Labels.Visit);
		JLabel orderTypeLbl = new JLabel(ConstMessagesCN.Labels.OrderType);
		JLabel planStartDateLbl = new JLabel(
				ConstMessagesCN.Labels.PlanStartDate);
		JLabel placeTypeLbl = new JLabel(ConstMessagesCN.Labels.PlaceType);
		JLabel frequencyTypeLbl = new JLabel(
				ConstMessagesCN.Labels.FrequencyType);
		JLabel orderUseModeLbl = new JLabel(ConstMessagesCN.Labels.OrderUseMode);
		JLabel countLbl = new JLabel(ConstMessagesCN.Labels.Count);
		JLabel pharmacyLbl = new JLabel(ConstMessagesCN.Labels.Pharmacy);

		planStartDateSD = new SpinnerDate("yyyy-MM-dd");
		
		countSN = new SpinnerNumber();

		visitCB = new JComboBox<>(visitComboBoxModel);
		orderTypeCB = new JComboBox<>(orderTypeComboBoxModel);
	
		frequencyTypeCB = new JComboBox<>(frequencyTypeComboBoxModel);

		orderUseModeCB = new JComboBox<>(orderUseModeComboBoxModel);
		pharmacyCB = new JComboBox<>(pharmacyComboBoxModel);

		formPanl.add(visitLbl);
		formPanl.add(visitCB);

		formPanl.add(orderTypeLbl);
		formPanl.add(orderTypeCB);

		formPanl.add(planStartDateLbl);
		formPanl.add(planStartDateSD);

		formPanl.add(frequencyTypeLbl);
		formPanl.add(frequencyTypeCB);

		formPanl.add(orderUseModeLbl);
		formPanl.add(orderUseModeCB);

		formPanl.add(pharmacyLbl);
		formPanl.add(pharmacyCB);

		formPanl.add(countLbl);
		formPanl.add(countSN);

		add(formPanl, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		confirmBtn = new JButton(ConstMessagesCN.Labels.CONFIRM_BTN);
		buttonPanel.add(confirmBtn);
		
		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public VisitComboBoxModel getVisitComboBoxModel() {
		return visitComboBoxModel;
	}

	public OrderTypeComboBoxModel getOrderTypeComboBoxModel() {
		return orderTypeComboBoxModel;
	}

	public OrderFrequencyTypeComboBoxModel getFrequencyTypeComboBoxModel() {
		return frequencyTypeComboBoxModel;
	}

	public DrugUseModeComboBoxModel getOrderUseModeComboBoxModel() {
		return orderUseModeComboBoxModel;
	}

	public PharmacyComboBoxModel getPharmacyComboBoxModel() {
		return pharmacyComboBoxModel;
	}
	
	public JButton getCloseBtn() {
		return closeBtn;
	}

}
