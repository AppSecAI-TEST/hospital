package com.neusoft.hs.portal.swing.ui.shared.controller;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import com.neusoft.hs.platform.exception.HsException;

public abstract class AbstractFrameController {

    public abstract void prepareAndOpenFrame() throws HsException;

    protected void registerAction(JButton button, ActionListener listener) {
        button.addActionListener(listener);
    }
    
    protected void registerAction(JComboBox comboBox, ItemListener listener) {
    	comboBox.addItemListener(listener);
    }

}
