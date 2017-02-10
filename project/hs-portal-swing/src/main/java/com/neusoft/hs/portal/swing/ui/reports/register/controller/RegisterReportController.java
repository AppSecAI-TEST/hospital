package com.neusoft.hs.portal.swing.ui.reports.register.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.register.RegisterAppService;
import com.neusoft.hs.application.register.RegisterCount;
import com.neusoft.hs.portal.swing.ui.reports.register.model.RegisterReportModel;
import com.neusoft.hs.portal.swing.ui.reports.register.view.RegisterReportTableFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;

@Controller
public class RegisterReportController extends AbstractFrameController {

    private RegisterReportTableFrame tableFrame;
    private RegisterReportModel tableModel;
    private RegisterAppService registerAppService;

    @Autowired
    public RegisterReportController(RegisterReportTableFrame tableFrame, RegisterReportModel tableModel, RegisterAppService registerAppService) {
        this.tableFrame = tableFrame;
        this.tableModel = tableModel;
        this.registerAppService = registerAppService;
    }

    public void prepareAndOpenFrame() {
        loadEntities();
        showTableFrame();
    }

    private void loadEntities() {
        tableModel.clear();
        List<RegisterCount> registerCount = registerAppService.getRegisterCount();
        tableModel.addEntities(registerCount);
    }

    private void showTableFrame() {
        tableFrame.setVisible(true);
    }


}
