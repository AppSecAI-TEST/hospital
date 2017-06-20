package com.neusoft.hs.portal.swing.util;

import javax.swing.*;

public class Notifications {

    public static void showFormValidationAlert(String message) {
    	if(message == null || message.length() == 0){
    		message = ConstMessagesEN.Messages.RUNTIMEEXCEPTION;
    	}
        JOptionPane.showMessageDialog(null,
                message,
                ConstMessagesEN.Messages.INFORMATION_TITLE,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showTableRowNotSelectedAlert() {
        JOptionPane.showMessageDialog(null,
                ConstMessagesEN.Messages.NON_ROW_SELECTED,
                ConstMessagesEN.Messages.ALERT_TILE,
                JOptionPane.ERROR_MESSAGE);
    }

    public static void showDeleteRowErrorMessage() {
        JOptionPane.showMessageDialog(null,
                ConstMessagesEN.Messages.DELETE_ROW_ERROR,
                ConstMessagesEN.Messages.ALERT_TILE,
                JOptionPane.ERROR_MESSAGE);
    }
}
