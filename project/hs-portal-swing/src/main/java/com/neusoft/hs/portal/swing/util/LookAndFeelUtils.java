package com.neusoft.hs.portal.swing.util;

import javax.swing.*;

public class LookAndFeelUtils {

    public static void setWindowsLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    ConstMessagesCN.Messages.WINDOWS_STYLE_LOADING_ERROR_MESSAGE + e,
                    ConstMessagesCN.Messages.ALERT_TILE,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
