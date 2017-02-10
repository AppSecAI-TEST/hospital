package com.neusoft.hs.portal.swing.ui.forms.login.view;

import org.springframework.stereotype.Component;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

import javax.swing.*;

@Component
public class LoginFormBtnPanel extends JPanel {

	private JButton loginBtn;
	private JButton cancelBtn;

	public LoginFormBtnPanel() {
		initComponents();
	}

	private void initComponents() {
		loginBtn = new JButton(ConstMessagesEN.Labels.Login_Btn);
		add(loginBtn);

		cancelBtn = new JButton(ConstMessagesEN.Labels.CANCEL_BTN);
		add(cancelBtn);
	}

	public JButton getLoginBtn() {
		return loginBtn;
	}

	public JButton getCancelBtn() {
		return cancelBtn;
	}

}
