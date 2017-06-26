package com.neusoft.hs.portal.swing.ui.forms.login.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

import javax.swing.*;
import java.awt.*;

@Component
public class LoginFrame extends JDialog {

	private LoginFormPanel formPanel;
	private LoginFormBtnPanel formBtnPanel;

	@Autowired
	public LoginFrame(LoginFormPanel formPanel, LoginFormBtnPanel formBtnPanel) {
		this.formPanel = formPanel;
		this.formBtnPanel = formBtnPanel;
		setFrameUp();
		initComponents();
		pack();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.DialogTitles.Login_MODAL);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
	}

	private void initComponents() {
		add(formPanel, BorderLayout.CENTER);
		add(formBtnPanel, BorderLayout.SOUTH);
	}

	public LoginFormPanel getFormPanel() {
		return formPanel;
	}

	public LoginFormBtnPanel getFormBtnPanel() {
		return formBtnPanel;
	}
}
