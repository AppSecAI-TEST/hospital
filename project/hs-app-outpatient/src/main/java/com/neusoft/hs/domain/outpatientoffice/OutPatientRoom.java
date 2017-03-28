//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\outpatientoffice\\OutPatientRoom.java

package com.neusoft.hs.domain.outpatientoffice;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.organization.OutPatientDept;
import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "domain_outpatient_room")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "outPatientRoomCache")
public class OutPatientRoom extends SuperEntity {
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 32)
	private String name;

	@OneToMany(mappedBy = "room", cascade = { CascadeType.ALL })
	private List<OutPatientPlanRecord> planRecords;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dept_id")
	private OutPatientDept dept;

	/**
	 * @roseuid 58B7C8C6037D
	 */
	public OutPatientRoom() {

	}

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

	public List<OutPatientPlanRecord> getPlanRecords() {
		return planRecords;
	}

	public void setPlanRecords(List<OutPatientPlanRecord> planRecords) {
		this.planRecords = planRecords;
	}

	public OutPatientDept getDept() {
		return dept;
	}

	public void setDept(OutPatientDept dept) {
		this.dept = dept;
	}
}
