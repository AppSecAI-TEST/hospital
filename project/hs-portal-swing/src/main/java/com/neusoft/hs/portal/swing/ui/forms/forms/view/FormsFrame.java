package com.neusoft.hs.portal.swing.ui.forms.forms.view;

import org.springframework.stereotype.Component;
import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;
import com.neusoft.hs.portal.swing.util.LookAndFeelUtils;

import javax.swing.*;
import java.awt.*;

@Component
public class FormsFrame extends JFrame {

	private JButton loginBtn;

	private JButton registerBtn;

	private JButton cashierBtn;

	private JButton receiveBtn;

	public FormsFrame() {
		setFrameUp();
		initComponents();
		pack();
	}

	private void setFrameUp() {
		getRootPane().setBorder(Borders.createEmptyBorder());
		setTitle(ConstMessagesEN.Labels.FORMS);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		LookAndFeelUtils.setWindowsLookAndFeel();
		setLayout(new GridLayout(6, 2, 20, 20));
	}

	private void initComponents() {
		loginBtn = new JButton(ConstMessagesEN.Labels.Login);
		registerBtn = new JButton(ConstMessagesEN.Labels.Register);
		cashierBtn = new JButton(ConstMessagesEN.Labels.InitAccount);
		receiveBtn = new JButton(ConstMessagesEN.Labels.ReceiveVisit);

		add(loginBtn);
		add(registerBtn);
		add(cashierBtn);
		add(receiveBtn);
	}

	public JButton getLoginBtn() {
		return loginBtn;
	}

	public JButton getRegisterBtn() {
		return registerBtn;
	}

	public JButton getCashierBtn() {
		return cashierBtn;
	}

	public JButton getReceiveBtn() {
		return receiveBtn;
	}
}
