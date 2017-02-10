package com.neusoft.hs.portal.swing;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.springframework.stereotype.Component;

@Component
public class MainFrame extends JFrame {

	private JButton formsBtn;
	private JButton reportsBtn;

	public MainFrame() {
		setFrameUp();
		initComponents();
		pack();
	}

	private void setFrameUp() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new GridLayout(1, 2, 20, 20));
	}

	private void initComponents() {
		formsBtn = new JButton("xxx");
		reportsBtn = new JButton("yyy");

		add(formsBtn);
		add(reportsBtn);
	}

	public JButton getFormsBtn() {
		return formsBtn;
	}

	public JButton getReportsBtn() {
		return reportsBtn;
	}

}
