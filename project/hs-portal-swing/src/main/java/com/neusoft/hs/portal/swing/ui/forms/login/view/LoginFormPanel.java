package com.neusoft.hs.portal.swing.ui.forms.login.view;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.portal.swing.ui.forms.login.model.AbstractUserComboBoxModel;
import com.neusoft.hs.portal.swing.ui.forms.login.model.LoginInfo;
import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

@Component
public class LoginFormPanel extends JPanel {

	private static final int LAYOUT_ROWS = 3;
	private static final int LAYOUT_COLS = 2;
	private static final int HORIZONTAL_GAP = 0;
	private static final int VERTICAL_GAP = 20;
	private static final int TEXT_FIELD_COLUMNS = 20;

//	private JTextField accountTF;
	private JComboBox<AbstractUser> accountCB;
	private AbstractUserComboBoxModel abstractUserComboBoxModel;

	@Autowired
	public LoginFormPanel(AbstractUserComboBoxModel abstractUserComboBoxModel) {
		this.abstractUserComboBoxModel = abstractUserComboBoxModel;
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(Borders.createEmptyBorder());
		setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS, HORIZONTAL_GAP,
				VERTICAL_GAP));
	}

	private void initComponents() {
		JLabel nameLbl = new JLabel(ConstMessagesEN.Labels.Account);

		accountCB = new JComboBox<AbstractUser>(abstractUserComboBoxModel);

		add(nameLbl);
		add(accountCB);
	}

	public AbstractUser getEntityFromForm() {
		return abstractUserComboBoxModel.getSelectedItem();
	}

	public void clearForm() {
		//accountTF.setText("");
	}

}
