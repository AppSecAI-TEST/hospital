package com.neusoft.hs.domain.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DrugOrderTypeDAO {

	@Autowired
	private DrugOrderTypeRepo drugOrderTypeRepo;

	public DrugOrderType findDrugOrderType(String id) {
		return this.drugOrderTypeRepo.findOne(id);
	}

}
