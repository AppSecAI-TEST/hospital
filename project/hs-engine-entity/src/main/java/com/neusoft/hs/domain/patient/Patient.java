//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\entity\\patient\\Patient.java

package com.neusoft.hs.domain.patient;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

/**
 * 对应真实世界中的一个患者
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_patient")
public class Patient extends IdEntity {

	@Column(name = "card_number", length = 64)
	private String cardNumber;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 16)
	private String name;

	private Date birthday;

	@Column(length = 16)
	private String sex;

	@Column(name = "create_date")
	private Date createDate;

	@JsonIgnore
	@OneToMany(mappedBy = "patient", cascade = { CascadeType.ALL })
	private List<Visit> visits;

	/**
	 * @roseuid 58AF8C400286
	 */
	public Patient() {

	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	public void save() {
		this.getService(PatientRepo.class).save(this);
	}

}
