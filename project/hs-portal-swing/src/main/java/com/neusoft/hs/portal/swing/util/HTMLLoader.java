package com.neusoft.hs.portal.swing.util;

import java.net.URL;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class HTMLLoader {

	public static JComponent createHTML(String path, String css) {
		JEditorPane text = new JEditorPane();
		text.setContentType("text/html;charset=GBK");
		text.setEditable(false);
		try {

			StyleSheet ss = new StyleSheet();

			if (css != null && css.length() > 0) {
				StyleSheet s1 = new StyleSheet();
				s1.importStyleSheet(new URL(null, "classpath:/" + css,
						new ClassPathURLStreamHandler()));
				ss.addStyleSheet(s1);
			}

			HTMLEditorKit kit = new HTMLEditorKit();
			ss.addStyleSheet(kit.getStyleSheet());

			kit.setStyleSheet(ss);
			text.setEditorKit(kit);

			text.setPage(new URL(null, "classpath:/" + path,
					new ClassPathURLStreamHandler()));
			text.setCaretPosition(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new JScrollPane(text);
	}

}
