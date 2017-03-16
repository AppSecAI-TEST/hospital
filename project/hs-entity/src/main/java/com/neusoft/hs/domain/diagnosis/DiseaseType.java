//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\entity\\diagnosis\\DiseaseType.java

package com.neusoft.hs.domain.diagnosis;

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

import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "domain_disease_type")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "diseaseTypeCache")
public class DiseaseType extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 128)
	private String name;

	@OneToMany(mappedBy = "diseaseType", cascade = { CascadeType.ALL })
	private List<Disease> diseases;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private DiseaseType parent;

	@OneToMany(mappedBy = "parent", cascade = { CascadeType.ALL })
	private List<DiseaseType> children;

	/**
	 * @roseuid 58CA2B040027
	 */
	public DiseaseType() {

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

	public List<Disease> getDiseases() {
		return diseases;
	}

	public void setDiseases(List<Disease> diseases) {
		this.diseases = diseases;
	}

	public DiseaseType getParent() {
		return parent;
	}

	public void setParent(DiseaseType parent) {
		this.parent = parent;
	}

	public List<DiseaseType> getChildren() {
		return children;
	}

	public void setChildren(List<DiseaseType> children) {
		this.children = children;
	}

}
