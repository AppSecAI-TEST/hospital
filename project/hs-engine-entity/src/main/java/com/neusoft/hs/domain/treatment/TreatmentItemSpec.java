//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\entity\\treatment\\TreatmentItemSpec.java

package com.neusoft.hs.domain.treatment;

import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.organization.Role;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.SuperEntity;
import com.neusoft.hs.platform.util.DateUtil;

/**
 * 一个诊疗项目规格
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_treatment_spec")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "treatmentItemSpecCache")
public abstract class TreatmentItemSpec extends SuperEntity {
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 128)
	private String name;

	@Column(name = "should_create_interval_hour")
	private Integer shouldIntervalHour;

	@Column(name = "repeat_create")
	private boolean repeatCreate = false;

	@OneToMany(mappedBy = "treatmentItemSpec", cascade = { CascadeType.ALL })
	private List<TreatmentItem> treatmentItems;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resp_role_id")
	public Role respRole;

	public final static String TemporaryOrderList = "临时医嘱列表";

	public final static String Diagnosis = "诊断";

	public final static String VisitName = "患者姓名";

	public final static String MainDescribe = "主诉";

	/**
	 * 对于某一患者一次就诊该诊疗项目需要创建的时间
	 * 
	 * @param visit
	 * @return
	 * @throws TreatmentException
	 */
	public Date getShouldDate(Visit visit) throws TreatmentException {
		if (visit.getIntoWardDate() == null) {
			throw new TreatmentException("患者[%s]还没有入院", visit.getName());
		}
		if (this.shouldIntervalHour == null) {
			return null;
		} else {
			return DateUtil.addHour(visit.getIntoWardDate(),
					this.shouldIntervalHour);
		}
	}

	/**
	 * 获取指定患者一次就诊的该诊疗规格下的诊疗项目
	 * 
	 * @param visit
	 * @return
	 * @throws TreatmentException
	 */
	public abstract TreatmentItem getTheItem(Visit visit)
			throws TreatmentException;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getShouldIntervalHour() {
		return shouldIntervalHour;
	}

	public void setShouldIntervalHour(Integer shouldIntervalHour) {
		this.shouldIntervalHour = shouldIntervalHour;
	}

	public boolean isRepeatCreate() {
		return repeatCreate;
	}

	public void setRepeatCreate(boolean repeatCreate) {
		this.repeatCreate = repeatCreate;
	}

	public List<TreatmentItem> getTreatmentItems() {
		return treatmentItems;
	}

	public void setTreatmentItems(List<TreatmentItem> treatmentItems) {
		this.treatmentItems = treatmentItems;
	}

	public Role getRespRole() {
		return respRole;
	}

	public void setRespRole(Role respRole) {
		this.respRole = respRole;
	}
}
