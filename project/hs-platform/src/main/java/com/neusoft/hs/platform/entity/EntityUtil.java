package com.neusoft.hs.platform.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityUtil {

	public static List<String> listId(List<? extends SuperEntity> entitys) {
		List<String> ids = new ArrayList<String>();
		for (SuperEntity entity : entitys) {
			ids.add(entity.getId());
		}
		return ids;

	}

}
