package com.neusoft.hs.portal.swing.ui.forms.forms.view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.springframework.stereotype.Component;

import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;
import com.neusoft.hs.portal.swing.util.LookAndFeelUtils;

@Component
public class FormsFrame extends JFrame {

	private JButton loginBtn;

	private JButton registerBtn;

	private JButton cashierBtn;

	private JButton receiveBtn;

	private JButton createOrderBtn;

	private JButton verifyOrderBtn;

	private JButton sendOrderExecuteBtn;

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
		setLayout(new GridLayout(10, 2, 20, 20));
	}

	private void initComponents() {
		loginBtn = new JButton(ConstMessagesEN.Labels.Login);
		registerBtn = new JButton(ConstMessagesEN.Labels.Register);
		cashierBtn = new JButton(ConstMessagesEN.Labels.InitAccount);
		receiveBtn = new JButton(ConstMessagesEN.Labels.ReceiveVisit);
		createOrderBtn = new JButton(ConstMessagesEN.Labels.CreateOrder);
		verifyOrderBtn = new JButton(ConstMessagesEN.Labels.VerifyOrder);
		sendOrderExecuteBtn = new JButton(
				ConstMessagesEN.Labels.SendOrderExecute);

		add(loginBtn);
		add(registerBtn);
		add(cashierBtn);
		add(receiveBtn);
		add(createOrderBtn);
		add(verifyOrderBtn);
		add(sendOrderExecuteBtn);
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

	public JButton getCreateOrderBtn() {
		return createOrderBtn;
	}

	public JButton getVerifyOrderBtn() {
		return verifyOrderBtn;
	}

	public JButton getSendOrderExecuteBtn() {
		return sendOrderExecuteBtn;
	}
}
