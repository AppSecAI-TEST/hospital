//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitRepo.java

package com.neusoft.hs.domain.patient;

import org.springframework.data.repository.PagingAndSortingRepository;

interface PatientRepo extends PagingAndSortingRepository<Patient, String> {

	Patient findByCardNumber(String cardNumber);

}
