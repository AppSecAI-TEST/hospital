//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\platform\\print\\PrintDomainService.java

package com.neusoft.hs.platform.print;

import org.springframework.stereotype.Service;

@Service
public class PrintDomainService {

	/**
	 * @param printable
	 * @roseuid 592E6F5E0215
	 */
	public void print(Printable printable) {
		printable.getPrintData();
	}
}
