package com.neusoft.hs.domain.treatment;

import java.util.Date;
import java.util.List;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;

/**
 * 与患者一次就诊有关的项目
 * 
 * @author kingbox
 *
 */
public interface Itemable {

	public String getName();

	public List<? extends ItemValue> getValues();

	public void setValues(List<ItemValue> values);

	public void addValue(ItemValue value);

	public void save();

	public Visit getVisit();

	public Date getCreateDate();

	public void setCreateDate(Date date);

	public AbstractUser getCreator();

	public void setCreator(AbstractUser user);

}
