package com.neusoft.hs.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.cashier.CashierAppService;
import com.neusoft.hs.application.inpatientdept.InPatientAppService;
import com.neusoft.hs.application.inpatientdept.MedicalRecordAppService;
import com.neusoft.hs.application.inpatientdept.OrderAppService;
import com.neusoft.hs.application.inspect.InspectAppService;
import com.neusoft.hs.application.qualitycontrol.QualityControlAppService;
import com.neusoft.hs.application.register.RegisterAppService;
import com.neusoft.hs.application.treatment.TreatmentAppService;
import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.inpatientdept.LeaveHospitalOrderType;
import com.neusoft.hs.domain.inpatientdept.SecondNursingOrderType;
import com.neusoft.hs.domain.inspect.InspectDept;
import com.neusoft.hs.domain.inspect.InspectDomainService;
import com.neusoft.hs.domain.inspect.InspectItem;
import com.neusoft.hs.domain.inspect.InspectOrderType;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordType;
import com.neusoft.hs.domain.order.AssistMaterial;
import com.neusoft.hs.domain.order.OrderDomainService;
import com.neusoft.hs.domain.order.OrderFrequencyType;
import com.neusoft.hs.domain.order.OrderFrequencyType9H15H;
import com.neusoft.hs.domain.order.OrderFrequencyTypeDay;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.TemporaryOrderListTreatmentItemSpec;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Org;
import com.neusoft.hs.domain.organization.OrganizationDomainService;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.organization.Unit;
import com.neusoft.hs.domain.organization.UserDomainService;
import com.neusoft.hs.domain.pharmacy.DrugOrderType;
import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.domain.pharmacy.DrugTypeSpec;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.DrugUseModeAssistMaterial;
import com.neusoft.hs.domain.pharmacy.InfusionOrderUseMode;
import com.neusoft.hs.domain.pharmacy.OralOrderUseMode;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.pharmacy.PharmacyDomainService;
import com.neusoft.hs.domain.treatment.CommonTreatmentItemSpec;
import com.neusoft.hs.domain.treatment.TreatmentDomainService;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.domain.visit.VisitNameTreatmentItemSpec;

public class DataIniter {

	protected Org org;// 哈医大二院
	protected Dept dept111;// 住院处
	protected Dept dept222;// 收费处
	protected Pharmacy dept333;// 药房
	protected InspectDept dept444;// CT室
	protected InspectDept dept555;// 核磁检查室
	protected Dept dept666;//质控

	protected InPatientDept dept000;// 内泌五

	protected Staff user111;// 住院处送诊人-曹操
	protected Staff user222;// 收费处-张飞
	protected Staff user333;// 药房摆药岗位-赵云
	protected Staff user444;// 药房配液岗位-关羽
	protected Staff user555;// CT室安排检查员-吕玲绮
	protected Staff user666;// CT室检查师-张合
	protected Staff user777;// 核磁检查室安排检查员-周瑜
	protected Staff user888;// 核磁检查室检查师-鲁肃
	protected Staff user999;// 质控岗位-刘备

	protected Staff user001;// 内泌五接诊护士-大乔
	protected Doctor user002;// 内泌五医生-貂蝉
	protected Nurse user003;// 内泌五护士-小乔

	protected ChargeItem bedChargeItem;// 床位费计费项目【暂时床位费只设一个计费项目】

	protected ChargeItem drugTypeSpec001ChargeItem;// 药品001计费项目（阿司匹林）

	protected ChargeItem drugTypeSpec002ChargeItem;// 药品002计费项目（头孢3）

	protected ChargeItem drugTypeSpec003ChargeItem;// 药品003计费项目(5%葡萄糖液)

	protected ChargeItem transportFluidMaterialChargeItem;// 输液材料费

	protected ChargeItem secondNursingChargeItem;// 二级护理计费项目

	protected ChargeItem brainCTChargeItem;// 脑CT计费项目

	protected ChargeItem brainHCChargeItem;// 脑核磁计费项目

	protected DrugTypeSpec drugTypeSpec001;// 药品规格001

	protected DrugTypeSpec drugTypeSpec002;// 药品规格002

	protected DrugTypeSpec drugTypeSpec003;// 药品规格003

	protected DrugType drugType001;// 药房下的药品类型001（有库存属性）

	protected DrugType drugType002;// 药房下的药品类型002（有库存属性）

	protected DrugType drugType003;// 药房下的药品类型003（有库存属性）

	protected DrugOrderType drugOrderType001;// 药品医嘱类型001

	protected DrugOrderType drugOrderType002;// 药品医嘱类型002

	protected DrugOrderType drugOrderType003;// 药品医嘱类型003

	protected InspectItem brainCTInspectItem;// 脑CT检查项目

	protected InspectItem brainHCInspectItem;// 脑核磁检查项目

	protected SecondNursingOrderType secondNursingOrderType;// 二级护理医嘱类型

	protected LeaveHospitalOrderType leaveHospitalOrderType;// 出院医嘱类型

	protected InspectOrderType inspectOrderType;// 检查医嘱类型

	protected OralOrderUseMode oralOrderUseMode;// 口服用法

	protected InfusionOrderUseMode infusionOrderUseMode;// 输液用法

	protected AssistMaterial transportFluidAssistMaterial;// 输液辅材

	protected DrugUseModeAssistMaterial everyOneOrderUseModeAssistMaterial;// 按频次收费

	protected DrugUseModeAssistMaterial everyDayOrderUseModeAssistMaterial;// 按天收费

	protected DrugUseModeAssistMaterial onlyOneOrderUseModeAssistMaterial;// 只收一次

	protected OrderFrequencyType orderFrequencyType_Day;// 每天

	protected OrderFrequencyType orderFrequencyType_9H15H;// 每天2次/早9/下3

	protected TreatmentItemSpec mainDescribeTreatmentItemSpec;// 主诉

	protected TreatmentItemSpec visitNameTreatmentItemSpec;// 患者姓名

	protected TreatmentItemSpec temporaryOrderListTreatmentItemSpec;// 临时医嘱列表

	protected MedicalRecordType intoWardRecordMedicalRecordType;// 入院记录

	protected MedicalRecordType temporaryOrderListMedicalRecordType;// 临时医嘱单

	protected MedicalRecordType inspectResultMedicalRecordType;// 检查单

	protected Visit visit001;

	@Autowired
	protected RegisterAppService registerAppService;

	@Autowired
	protected CashierAppService cashierAppService;

	@Autowired
	protected InPatientAppService inPatientAppService;

	@Autowired
	protected OrderAppService orderAppService;

	@Autowired
	protected OrderExecuteAppService orderExecuteAppService;

	@Autowired
	protected InspectAppService inspectAppService;

	@Autowired
	protected InspectDomainService inspectDomainService;

	@Autowired
	protected OrganizationDomainService organizationDomainService;

	@Autowired
	protected UserDomainService userDomainService;

	@Autowired
	protected CostDomainService costDomainService;

	@Autowired
	protected PharmacyDomainService pharmacyDomainService;

	@Autowired
	protected VisitDomainService visitDomainService;

	@Autowired
	protected OrderDomainService orderDomainService;

	@Autowired
	protected MedicalRecordDomainService medicalRecordDomainService;

	@Autowired
	protected TreatmentDomainService treatmentDomainService;

	@Autowired
	protected TreatmentAppService treatmentAppService;

	@Autowired
	protected MedicalRecordAppService medicalRecordAppService;

	@Autowired
	protected QualityControlAppService qualityControlAppService;
	
	@Autowired
	protected MedicalRecordTestService medicalRecordTestService;

	public void clear() {
		// 清空病历
		medicalRecordDomainService.clear();
		// 清空医嘱用法
		pharmacyDomainService.clearOrderUseModes();
		// 清空医嘱类型
		orderDomainService.clearOrders();
		// 清空组合医嘱
		orderDomainService.clearCompsiteOrdes();
		// 清空频次类型
		orderDomainService.clearOrderFrequencyTypes();
		// 清空药品类型
		pharmacyDomainService.clearDrugTypes();
		// 清空药品规格
		pharmacyDomainService.clearDrugTypeSpecs();
		// 清空检查项目
		inspectDomainService.clearInspectItems();
		// 清空计费项目
		costDomainService.clearChargeItems();
		// 清空诊疗项目信息
		treatmentDomainService.clearTreatmentItems();
		// 清空诊疗项目规格
		treatmentDomainService.clearTreatmentItemSpecs();
		// 清空患者一次住院
		visitDomainService.clear();
		// 清空成本记录
		costDomainService.clearCostRecords();
		// 清空用户信息
		userDomainService.clear();
		// 清空组织机构信息
		organizationDomainService.clear();
	}

	@Transactional(rollbackFor = Exception.class)
	public void initData() {

		initOrgs();

		initUsers();

		initChargeItems();

		initDrugTypeSpecs();

		initDrugTypes();

		initInspectItems();

		initOrderTypes();

		initOrderUseModes();

		initAssistMaterials();

		initOrderUseModeAssistMaterials();

		initOrderFrequencyTypes();

		initTreatmentItemSpecs();

		initMedicalRecordTypes();
	}

	private void initOrgs() {

		List<Unit> units = new ArrayList<Unit>();

		org = new Org();
		org.setId("org000");
		org.setName("哈医大二院");

		units.add(org);

		dept111 = new Dept();
		dept111.setId("dept111");
		dept111.setName("住院处");
		dept111.setParent(org);

		units.add(dept111);

		dept222 = new Dept();
		dept222.setId("dept222");
		dept222.setName("收费处");
		dept222.setParent(org);

		units.add(dept222);

		dept333 = new Pharmacy();
		dept333.setId("dept333");
		dept333.setName("药房");
		dept333.setParent(org);

		units.add(dept333);

		dept444 = new InspectDept();
		dept444.setId("dept444");
		dept444.setName("CT室");
		dept444.setParent(org);

		units.add(dept444);

		dept555 = new InspectDept();
		dept555.setId("dept555");
		dept555.setName("核磁检查室");
		dept555.setParent(org);

		units.add(dept555);
		
		dept666 = new InspectDept();
		dept666.setId("dept666");
		dept666.setName("质控部门");
		dept666.setParent(org);

		units.add(dept666);

		dept000 = new InPatientDept();
		dept000.setId("dept000");
		dept000.setName("内泌五");
		dept000.setParent(org);

		units.add(dept000);

		organizationDomainService.create(units);
	}

	private void initUsers() {

		List<AbstractUser> users = new ArrayList<AbstractUser>();

		user111 = new Staff();

		user111.setId("staff111");
		user111.setName("住院处送诊人-曹操");
		user111.setDept(dept111);

		users.add(user111);

		user222 = new Staff();

		user222.setId("staff222");
		user222.setName("收费处-张飞");
		user222.setDept(dept222);

		users.add(user222);

		user333 = new Staff();

		user333.setId("staff333");
		user333.setName("药房摆药岗位-赵云");
		user333.setDept(dept333);

		users.add(user333);

		user444 = new Staff();

		user444.setId("staff444");
		user444.setName("药房配液岗位-关羽");
		user444.setDept(dept333);

		users.add(user444);

		user555 = new Staff();

		user555.setId("staff555");
		user555.setName("CT室安排检查员-吕玲绮");
		user555.setDept(dept444);

		users.add(user555);

		user666 = new Staff();

		user666.setId("staff666");
		user666.setName("CT室检查师-张合");
		user666.setDept(dept444);

		users.add(user666);

		user777 = new Staff();

		user777.setId("staff777");
		user777.setName("核磁检查室安排检查员-周瑜");
		user777.setDept(dept555);

		users.add(user777);

		user888 = new Staff();

		user888.setId("staff888");
		user888.setName("核磁检查室检查师-鲁肃");
		user888.setDept(dept555);

		users.add(user888);
		
		user999 = new Staff();

		user999.setId("staff666");
		user999.setName("质控岗位-刘备");
		user999.setDept(dept666);

		users.add(user999);

		user001 = new Staff();

		user001.setId("staff001");
		user001.setName("内泌五接诊护士-大乔");
		user001.setDept(dept000);

		users.add(user001);

		user002 = new Doctor();

		user002.setId("doctor002");
		user002.setName("内泌五医生-貂蝉");
		user002.setDept(dept000);

		users.add(user002);

		user003 = new Nurse();

		user003.setId("nurse003");
		user003.setName("内泌五护士-小乔");
		user003.setDept(dept000);

		users.add(user003);

		userDomainService.create(users);
	}

	private void initChargeItems() {

		List<ChargeItem> chargeItems = new ArrayList<ChargeItem>();

		bedChargeItem = new ChargeItem();
		bedChargeItem.setId("bedChargeItem");
		bedChargeItem.setCode("bedChargeItem");
		bedChargeItem.setName("床位费");
		bedChargeItem.setPrice(20F);
		bedChargeItem.setChargingMode(ChargeItem.ChargingMode_Day);

		chargeItems.add(bedChargeItem);

		drugTypeSpec001ChargeItem = new ChargeItem();
		drugTypeSpec001ChargeItem.setId("drugTypeSpec001ChargeItem");
		drugTypeSpec001ChargeItem.setCode("drugTypeSpec001ChargeItem");
		drugTypeSpec001ChargeItem.setName("阿司匹林");
		drugTypeSpec001ChargeItem.setPrice(30F);
		drugTypeSpec001ChargeItem.setUnit("盒");
		drugTypeSpec001ChargeItem
				.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(drugTypeSpec001ChargeItem);

		secondNursingChargeItem = new ChargeItem();
		secondNursingChargeItem.setId("secondNursingChargeItem");
		secondNursingChargeItem.setCode("secondNursingChargeItem");
		secondNursingChargeItem.setName("二级护理");
		secondNursingChargeItem.setPrice(8);
		secondNursingChargeItem.setChargingMode(ChargeItem.ChargingMode_Day);

		chargeItems.add(secondNursingChargeItem);

		drugTypeSpec002ChargeItem = new ChargeItem();
		drugTypeSpec002ChargeItem.setId("drugTypeSpec002ChargeItem");
		drugTypeSpec002ChargeItem.setCode("drugTypeSpec002ChargeItem");
		drugTypeSpec002ChargeItem.setName("头孢3");
		drugTypeSpec002ChargeItem.setPrice(120);
		drugTypeSpec002ChargeItem.setUnit("支");
		drugTypeSpec002ChargeItem
				.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(drugTypeSpec002ChargeItem);

		drugTypeSpec003ChargeItem = new ChargeItem();
		drugTypeSpec003ChargeItem.setId("drugTypeSpec003ChargeItem");
		drugTypeSpec003ChargeItem.setCode("drugTypeSpec003ChargeItem");
		drugTypeSpec003ChargeItem.setName("5%葡萄糖液");
		drugTypeSpec003ChargeItem.setPrice(15);
		drugTypeSpec003ChargeItem.setUnit("袋");
		drugTypeSpec003ChargeItem
				.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(drugTypeSpec003ChargeItem);

		transportFluidMaterialChargeItem = new ChargeItem();
		transportFluidMaterialChargeItem
				.setId("transportFluidMaterialChargeItem");
		transportFluidMaterialChargeItem
				.setCode("transportFluidMaterialChargeItem");
		transportFluidMaterialChargeItem.setName("输液材料费");
		transportFluidMaterialChargeItem.setPrice(7);
		transportFluidMaterialChargeItem.setUnit("件");
		transportFluidMaterialChargeItem
				.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(transportFluidMaterialChargeItem);

		brainCTChargeItem = new ChargeItem();
		brainCTChargeItem.setId("brainCTChargeItem");
		brainCTChargeItem.setCode("brainCTChargeItem");
		brainCTChargeItem.setName("脑CT");
		brainCTChargeItem.setPrice(150);
		brainCTChargeItem.setUnit("次");
		brainCTChargeItem.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(brainCTChargeItem);

		brainHCChargeItem = new ChargeItem();
		brainHCChargeItem.setId("brainHCChargeItem");
		brainHCChargeItem.setCode("brainHCChargeItem");
		brainHCChargeItem.setName("脑核磁");
		brainHCChargeItem.setPrice(350);
		brainHCChargeItem.setUnit("次");
		brainHCChargeItem.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(brainHCChargeItem);

		costDomainService.create(chargeItems);
	}

	private void initDrugTypeSpecs() {

		List<DrugTypeSpec> drugTypeSpecs = new ArrayList<DrugTypeSpec>();

		drugTypeSpec001 = new DrugTypeSpec();
		drugTypeSpec001.setId("drugTypeSpec001");
		drugTypeSpec001.setName("阿司匹林");
		drugTypeSpec001.setChargeItem(drugTypeSpec001ChargeItem);

		drugTypeSpecs.add(drugTypeSpec001);

		drugTypeSpec002 = new DrugTypeSpec();
		drugTypeSpec002.setId("drugTypeSpec002");
		drugTypeSpec002.setName("头孢3");
		drugTypeSpec002.setChargeItem(drugTypeSpec002ChargeItem);

		drugTypeSpecs.add(drugTypeSpec002);

		drugTypeSpec003 = new DrugTypeSpec();
		drugTypeSpec003.setId("drugTypeSpec003");
		drugTypeSpec003.setName("5%葡萄糖液");
		drugTypeSpec003.setChargeItem(drugTypeSpec003ChargeItem);
		drugTypeSpec003.setTransportFluidCharge(true);

		drugTypeSpecs.add(drugTypeSpec003);

		pharmacyDomainService.createDrugTypeSpecs(drugTypeSpecs);
	}

	private void initDrugTypes() {

		List<DrugType> drugTypes = new ArrayList<DrugType>();

		drugType001 = new DrugType();
		drugType001.setId("drugType001");
		drugType001.setDrugTypeSpec(drugTypeSpec001);
		drugType001.setPharmacy(dept333);
		drugType001.setStock(100);

		drugTypes.add(drugType001);

		drugType002 = new DrugType();
		drugType002.setId("drugType002");
		drugType002.setDrugTypeSpec(drugTypeSpec002);
		drugType002.setPharmacy(dept333);
		drugType002.setStock(1200);

		drugTypes.add(drugType002);

		drugType003 = new DrugType();
		drugType003.setId("drugType003");
		drugType003.setDrugTypeSpec(drugTypeSpec003);
		drugType003.setPharmacy(dept333);
		drugType003.setStock(3000);

		drugTypes.add(drugType003);

		pharmacyDomainService.createDrugTypes(drugTypes);
	}

	private void initInspectItems() {

		List<InspectItem> inspectItems = new ArrayList<InspectItem>();

		brainCTInspectItem = new InspectItem();
		brainCTInspectItem.setId("brainCTInspectItem");
		brainCTInspectItem.setCode("brainCTInspectItem");
		brainCTInspectItem.setName("脑CT");
		brainCTInspectItem.setChargeItem(brainCTChargeItem);

		inspectItems.add(brainCTInspectItem);

		brainHCInspectItem = new InspectItem();
		brainHCInspectItem.setId("brainHCInspectItem");
		brainHCInspectItem.setCode("brainHCInspectItem");
		brainHCInspectItem.setName("脑核磁");
		brainHCInspectItem.setChargeItem(brainHCChargeItem);

		inspectItems.add(brainHCInspectItem);

		inspectDomainService.createInspectItems(inspectItems);
	}

	private void initOrderTypes() {

		List<OrderType> orderTypes = new ArrayList<OrderType>();

		drugOrderType001 = new DrugOrderType();
		drugOrderType001.setId("drugOrderType001");
		drugOrderType001.setCode("drugOrderType001");
		drugOrderType001.setName("阿司匹林");
		drugOrderType001.setDrugType(drugType001);

		orderTypes.add(drugOrderType001);

		drugOrderType002 = new DrugOrderType();
		drugOrderType002.setId("drugOrderType002");
		drugOrderType002.setCode("drugOrderType002");
		drugOrderType002.setName("头孢3");
		drugOrderType002.setDrugType(drugType002);

		orderTypes.add(drugOrderType002);

		drugOrderType003 = new DrugOrderType();
		drugOrderType003.setId("drugOrderType003");
		drugOrderType003.setCode("drugOrderType003");
		drugOrderType003.setName("5%葡萄糖液");
		drugOrderType003.setDrugType(drugType003);

		orderTypes.add(drugOrderType003);

		leaveHospitalOrderType = new LeaveHospitalOrderType();
		leaveHospitalOrderType.setId("leaveHospitalOrderType");
		leaveHospitalOrderType.setCode("leaveHospitalOrderType");
		leaveHospitalOrderType.setName("出院医嘱");

		orderTypes.add(leaveHospitalOrderType);

		secondNursingOrderType = new SecondNursingOrderType();
		secondNursingOrderType.setId("secondNursingOrderType");
		secondNursingOrderType.setCode("secondNursingOrderType");
		secondNursingOrderType.setName("二级护理");
		secondNursingOrderType.setChargeItem(secondNursingChargeItem);

		orderTypes.add(secondNursingOrderType);

		inspectOrderType = new InspectOrderType();
		inspectOrderType.setId("brainCTInspectOrderType");
		inspectOrderType.setCode("brainCTInspectOrderType");
		inspectOrderType.setName("脑CT");

		orderTypes.add(inspectOrderType);

		orderDomainService.createOrderTypes(orderTypes);
	}

	private void initOrderUseModes() {

		List<DrugUseMode> orderUseModes = new ArrayList<DrugUseMode>();

		oralOrderUseMode = new OralOrderUseMode();
		oralOrderUseMode.setId("oralOrderUseMode");
		oralOrderUseMode.setCode("oralOrderUseMode");
		oralOrderUseMode.setName("口服");

		orderUseModes.add(oralOrderUseMode);

		infusionOrderUseMode = new InfusionOrderUseMode();
		infusionOrderUseMode.setId("infusionOrderUseMode");
		infusionOrderUseMode.setCode("infusionOrderUseMode");
		infusionOrderUseMode.setName("输液");

		orderUseModes.add(infusionOrderUseMode);

		pharmacyDomainService.createOrderUseModes(orderUseModes);

	}

	private void initAssistMaterials() {

		List<AssistMaterial> assistMaterials = new ArrayList<AssistMaterial>();

		transportFluidAssistMaterial = new AssistMaterial();
		transportFluidAssistMaterial.setId("infusionAssistMaterial");
		transportFluidAssistMaterial.setCode("infusionAssistMaterial");
		transportFluidAssistMaterial.setName("输液辅材");
		transportFluidAssistMaterial
				.setChargeItem(transportFluidMaterialChargeItem);

		assistMaterials.add(transportFluidAssistMaterial);

		orderDomainService.createAssistMaterials(assistMaterials);
	}

	private void initOrderUseModeAssistMaterials() {

		everyOneOrderUseModeAssistMaterial = new DrugUseModeAssistMaterial();
		everyOneOrderUseModeAssistMaterial.setId("everyOne");
		everyOneOrderUseModeAssistMaterial.setCode("everyOne");
		everyOneOrderUseModeAssistMaterial
				.setAssistMaterial(transportFluidAssistMaterial);
		everyOneOrderUseModeAssistMaterial
				.setOrderUseMode(infusionOrderUseMode);
		everyOneOrderUseModeAssistMaterial
				.setChargeMode(DrugUseModeAssistMaterial.everyOne);
		everyOneOrderUseModeAssistMaterial
				.setSign(InfusionOrderUseMode.transportFluid);

		everyDayOrderUseModeAssistMaterial = new DrugUseModeAssistMaterial();
		everyDayOrderUseModeAssistMaterial.setId("everyDay");
		everyDayOrderUseModeAssistMaterial.setCode("everyDay");
		everyDayOrderUseModeAssistMaterial
				.setAssistMaterial(transportFluidAssistMaterial);
		everyDayOrderUseModeAssistMaterial
				.setOrderUseMode(infusionOrderUseMode);
		everyDayOrderUseModeAssistMaterial
				.setChargeMode(DrugUseModeAssistMaterial.everyDay);
		everyDayOrderUseModeAssistMaterial
				.setSign(InfusionOrderUseMode.transportFluid);

		onlyOneOrderUseModeAssistMaterial = new DrugUseModeAssistMaterial();
		onlyOneOrderUseModeAssistMaterial.setId("onlyOne");
		onlyOneOrderUseModeAssistMaterial.setCode("onlyOne");
		onlyOneOrderUseModeAssistMaterial
				.setAssistMaterial(transportFluidAssistMaterial);
		onlyOneOrderUseModeAssistMaterial.setOrderUseMode(infusionOrderUseMode);
		onlyOneOrderUseModeAssistMaterial
				.setChargeMode(DrugUseModeAssistMaterial.onlyOne);
		onlyOneOrderUseModeAssistMaterial
				.setSign(InfusionOrderUseMode.transportFluid);
	}

	private void initOrderFrequencyTypes() {

		List<OrderFrequencyType> orderFrequencyTypes = new ArrayList<OrderFrequencyType>();

		orderFrequencyType_Day = new OrderFrequencyTypeDay();

		orderFrequencyTypes.add(orderFrequencyType_Day);

		orderFrequencyType_9H15H = new OrderFrequencyType9H15H();

		orderFrequencyTypes.add(orderFrequencyType_9H15H);

		orderDomainService.createOrderFrequencyTypes(orderFrequencyTypes);

	}

	private void initTreatmentItemSpecs() {
		List<TreatmentItemSpec> treatmentItemSpecs = new ArrayList<TreatmentItemSpec>();

		mainDescribeTreatmentItemSpec = new CommonTreatmentItemSpec();
		mainDescribeTreatmentItemSpec.setId("主诉");
		mainDescribeTreatmentItemSpec.setName("主诉");
		mainDescribeTreatmentItemSpec.setShouldIntervalHour(24);

		treatmentItemSpecs.add(mainDescribeTreatmentItemSpec);

		visitNameTreatmentItemSpec = new VisitNameTreatmentItemSpec();
		visitNameTreatmentItemSpec.setId("患者姓名");
		visitNameTreatmentItemSpec.setName("患者姓名");

		treatmentItemSpecs.add(visitNameTreatmentItemSpec);

		temporaryOrderListTreatmentItemSpec = new TemporaryOrderListTreatmentItemSpec();
		temporaryOrderListTreatmentItemSpec.setId("临时医嘱列表");
		temporaryOrderListTreatmentItemSpec.setName("临时医嘱列表");

		treatmentItemSpecs.add(temporaryOrderListTreatmentItemSpec);

		treatmentDomainService.createTreatmentItemSpecs(treatmentItemSpecs);
	}

	private void initMedicalRecordTypes() {
		List<TreatmentItemSpec> items;
		List<MedicalRecordType> medicalRecordTypes = new ArrayList<MedicalRecordType>();

		intoWardRecordMedicalRecordType = new MedicalRecordType();
		intoWardRecordMedicalRecordType.setId("入院记录");
		intoWardRecordMedicalRecordType.setName("入院记录");
		intoWardRecordMedicalRecordType.setNeedSign(true);
		intoWardRecordMedicalRecordType.setNeedCreate(true);

		items = new ArrayList<TreatmentItemSpec>();
		items.add(visitNameTreatmentItemSpec);
		items.add(mainDescribeTreatmentItemSpec);

		intoWardRecordMedicalRecordType.setItems(items);

		medicalRecordTypes.add(intoWardRecordMedicalRecordType);

		temporaryOrderListMedicalRecordType = new MedicalRecordType();
		temporaryOrderListMedicalRecordType.setId("临时医嘱单");
		temporaryOrderListMedicalRecordType.setName("临时医嘱单");
		temporaryOrderListMedicalRecordType.setNeedSign(false);
		temporaryOrderListMedicalRecordType.setNeedCreate(false);

		items = new ArrayList<TreatmentItemSpec>();
		items.add(visitNameTreatmentItemSpec);
		items.add(temporaryOrderListTreatmentItemSpec);

		temporaryOrderListMedicalRecordType.setItems(items);

		medicalRecordTypes.add(temporaryOrderListMedicalRecordType);

		inspectResultMedicalRecordType = new MedicalRecordType();
		inspectResultMedicalRecordType.setId("检查单");
		inspectResultMedicalRecordType.setName("检查单");
		inspectResultMedicalRecordType.setNeedSign(false);
		inspectResultMedicalRecordType.setNeedCreate(false);

		items = new ArrayList<TreatmentItemSpec>();
		items.add(visitNameTreatmentItemSpec);

		inspectResultMedicalRecordType.setItems(items);

		medicalRecordTypes.add(inspectResultMedicalRecordType);

		medicalRecordDomainService.createMedicalRecordTypes(medicalRecordTypes);

	}

}
