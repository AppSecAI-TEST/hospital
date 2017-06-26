package com.neusoft.hs.portal.swing.util;

import javax.swing.*;

public class Notifications {

    public static void showFormValidationAlert(String message) {
    	if(message == null || message.length() == 0){
    		message = ConstMessagesCN.Messages.RUNTIMEEXCEPTION;
    	}
        JOptionPane.showMessageDialog(null,
                message,
                ConstMessagesCN.Messages.INFORMATION_TITLE,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showTableRowNotSelectedAlert() {
        JOptionPane.showMessageDialog(null,
                ConstMessagesCN.Messages.NON_ROW_SELECTED,
                ConstMessagesCN.Messages.ALERT_TILE,
                JOptionPane.ERROR_MESSAGE);
    }

    public static void showDeleteRowErrorMessage() {
        JOptionPane.showMessageDialog(null,
                ConstMessagesCN.Messages.DELETE_ROW_ERROR,
                ConstMessagesCN.Messages.ALERT_TILE,
                JOptionPane.ERROR_MESSAGE);
    }
}
