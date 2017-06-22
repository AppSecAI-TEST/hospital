package com.neusoft.hs.portal.swing.widget;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SpinnerNumber extends JSpinner {

	public SpinnerNumber() {
		// 获得数字模型
		SpinnerNumberModel model = new SpinnerNumberModel();
		// 获得JSPinner对象
		this.setModel(model);
		// 设置时间格式
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(this);
		this.setEditor(editor);
		this.setBounds(34, 67, 219, 22);
	}
}
