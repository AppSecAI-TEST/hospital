package com.neusoft.hs.portal.swing.ui.forms.order.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTableModel;
import com.neusoft.hs.portal.swing.util.Borders;

@Component
public class OrderVerifyListPanel extends JPanel {

	private OrderTableModel orderTableModel;

	protected JTable table;

	@Autowired
	public OrderVerifyListPanel(OrderTableModel orderTableModel) {
		this.orderTableModel = orderTableModel;

		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(Borders.createEmptyBorder());
		setLayout(new BorderLayout());
	}

	private void initComponents() {

		table = new JTable(this.orderTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		add(paneWithTable, BorderLayout.CENTER);
	}

	public Order getSelectedOrder() throws UIException {
		if (this.table.getSelectedRow() == -1) {
			throw new UIException("请选择要核对的医嘱");
		}
		return orderTableModel.getOrder(this.table.getSelectedRow());
	}

}
