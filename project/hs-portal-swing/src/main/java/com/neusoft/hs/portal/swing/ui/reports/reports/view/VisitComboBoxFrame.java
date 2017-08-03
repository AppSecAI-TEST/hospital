package com.neusoft.hs.portal.swing.ui.reports.reports.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;

public abstract class VisitComboBoxFrame extends JFrame {

	protected JComboBox<Visit> visitCB;
	protected VisitComboBoxModel visitComboBoxModel;

	protected int pageNumber;

	protected JButton nextPageBtn;

	protected JButton closeBtn;

	protected static final int DEFAULT_WIDTH = 800;

	protected static final int DEFAULT_HEIGHT = 300;

	public VisitComboBoxModel getVisitComboBoxModel() {
		return visitComboBoxModel;
	}

	public JComboBox<Visit> getVisitCB() {
		return visitCB;
	}

	public JButton getNextPageBtn() {
		return nextPageBtn;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void nextPage() {
		this.pageNumber++;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}

}
