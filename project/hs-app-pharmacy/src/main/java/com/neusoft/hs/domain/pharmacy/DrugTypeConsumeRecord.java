package com.neusoft.hs.domain.pharmacy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.order.DrugOrderExecute;
import com.neusoft.hs.domain.order.DrugOrderTypeApp;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "app_pharmacy_drug_type_consume_record")
public class DrugTypeConsumeRecord extends IdEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_type_id")
	private DrugType drugType;

	private Integer count;

	@Column(name = "create_date")
	private Date createDate;

	@Column(length = 32)
	private String type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_order_type_app_id")
	private DrugOrderTypeApp drugOrderTypeApp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_order_execute_id")
	private DrugOrderExecute drugOrderExecute;

	public DrugType getDrugType() {
		return drugType;
	}

	public void setDrugType(DrugType drugType) {
		this.drugType = drugType;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public DrugOrderTypeApp getDrugOrderTypeApp() {
		return drugOrderTypeApp;
	}

	public void setDrugOrderTypeApp(DrugOrderTypeApp drugOrderTypeApp) {
		this.drugOrderTypeApp = drugOrderTypeApp;
	}

	public DrugOrderExecute getDrugOrderExecute() {
		return drugOrderExecute;
	}

	public void setDrugOrderExecute(DrugOrderExecute drugOrderExecute) {
		this.drugOrderExecute = drugOrderExecute;
	}

}
