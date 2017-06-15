package com.neusoft.hs.portal.swing.ui.shared.controller;

import javax.swing.*;

import com.neusoft.hs.platform.exception.HsException;

import java.awt.event.ActionListener;

public abstract class AbstractFrameController {

    public abstract void prepareAndOpenFrame() throws HsException;

    protected void registerAction(JButton button, ActionListener listener) {
        button.addActionListener(listener);
    }

}
