package com.neusoft.hs.portal.swing.widget;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import com.neusoft.hs.platform.util.DateUtil;

public class SpinnerDate extends JSpinner {

	public SpinnerDate(String dateFormatPattern) {
		// 获得时间日期模型
		SpinnerDateModel model = new SpinnerDateModel();
		// 获得JSPinner对象
		this.setModel(model);
		this.setValue(DateUtil.getSysDate());
		// 设置时间格式
		JSpinner.DateEditor editor = new JSpinner.DateEditor(this,
				dateFormatPattern);
		this.setEditor(editor);
		this.setBounds(34, 67, 219, 22);
	}
}
