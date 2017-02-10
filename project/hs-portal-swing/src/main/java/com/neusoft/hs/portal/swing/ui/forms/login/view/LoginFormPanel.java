package com.neusoft.hs.portal.swing.ui.forms.login.view;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.stereotype.Component;

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

	private JTextField accountTF;

	public LoginFormPanel() {
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

		accountTF = new JTextField(TEXT_FIELD_COLUMNS);

		add(nameLbl);
		add(accountTF);
	}

	public LoginInfo getEntityFromForm() {

		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setAccount(accountTF.getText());

		return loginInfo;
	}

	public void clearForm() {
		accountTF.setText("");
	}

}
