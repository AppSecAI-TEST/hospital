package com.neusoft.hs.platform.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;

@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
public abstract class SuperEntity implements Serializable {

	public abstract String getId();

	protected <T> T getService(Class<T> className) {
		return (T) ApplicationContextUtil.getApplicationContext().getBean(
				className);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuperEntity other = (SuperEntity) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
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
