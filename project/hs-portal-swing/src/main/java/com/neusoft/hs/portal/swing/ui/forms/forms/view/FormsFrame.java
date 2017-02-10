package com.neusoft.hs.portal.swing.ui.forms.forms.view;

import org.springframework.stereotype.Component;
import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;
import com.neusoft.hs.portal.swing.util.LookAndFeelUtils;

import javax.swing.*;
import java.awt.*;

@Component
public class FormsFrame extends JFrame {

	private JButton registerBtn;

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
		registerBtn = new JButton(ConstMessagesEN.Labels.REGISTERS);

		add(registerBtn);
	}

	public JButton getRegisterBtn() {
		return registerBtn;
	}

}
