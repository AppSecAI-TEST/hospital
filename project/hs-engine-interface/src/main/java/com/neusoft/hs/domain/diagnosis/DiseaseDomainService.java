package com.neusoft.hs.domain.diagnosis;

import java.util.List;

public interface DiseaseDomainService {

	public void createDiseases(List<Disease> diseases);

	public void clearDiseases();

}
