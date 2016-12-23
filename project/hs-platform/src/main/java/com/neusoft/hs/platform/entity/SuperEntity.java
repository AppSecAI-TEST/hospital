package com.neusoft.hs.platform.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.neusoft.hs.platform.bean.ApplicationContextUtil;

public abstract class SuperEntity implements Serializable {

	protected <T> T getService(Class<T> className) {
		return (T) ApplicationContextUtil.getApplicationContext().getBean(
				className);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
