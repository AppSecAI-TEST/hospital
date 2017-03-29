package com.neusoft.hs.domain.diagnosis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DiseaseDomainServiceImpl implements DiseaseDomainService {

	@Autowired
	private DiseaseRepo diseaseRepo;

	public void createDiseases(List<Disease> diseases) {
		diseaseRepo.save(diseases);
	}

	public void clearDiseases() {
		diseaseRepo.deleteAll();
	}

}
