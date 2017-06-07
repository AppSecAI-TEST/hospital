package com.neusoft.hs.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.cashier.CashierAppService;
import com.neusoft.hs.application.cost.CostAppService;
import com.neusoft.hs.application.inpatientdept.InPatientAppService;
import com.neusoft.hs.application.inspect.InspectAppService;
import com.neusoft.hs.application.medicalrecord.MedicalRecordAppService;
import com.neusoft.hs.application.order.OrderAppService;
import com.neusoft.hs.application.outpatientdept.OutPatientDeptAppService;
import com.neusoft.hs.application.pharmacy.ConfigureFluidAppService;
import com.neusoft.hs.application.recordroom.QualityControlAppService;
import com.neusoft.hs.application.register.RegisterAppService;
import com.neusoft.hs.application.registration.RegistrationAppService;
import com.neusoft.hs.application.treatment.TreatmentAppService;
import com.neusoft.hs.application.visit.VisitAppService;
import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.CostAdminDomainService;
import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.diagnosis.DiagnosisTreatmentItemSpec;
import com.neusoft.hs.domain.diagnosis.Disease;
import com.neusoft.hs.domain.diagnosis.DiseaseAdminDomainService;
import com.neusoft.hs.domain.inspect.InspectDept;
import com.neusoft.hs.domain.inspect.InspectDomainService;
import com.neusoft.hs.domain.inspect.InspectItem;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordAdminDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordType;
import com.neusoft.hs.domain.order.DrugOrderType;
import com.neusoft.hs.domain.order.EnterHospitalOrderType;
import com.neusoft.hs.domain.order.InfusionOrderUseModeToInPatient;
import com.neusoft.hs.domain.order.InfusionOrderUseModeToOutPatient;
import com.neusoft.hs.domain.order.InspectOrderType;
import com.neusoft.hs.domain.order.NursingOrderType;
import com.neusoft.hs.domain.order.OralOrderUseMode;
import com.neusoft.hs.domain.order.OrderAdminDomainService;
import com.neusoft.hs.domain.order.OrderDomainService;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderFrequencyType;
import com.neusoft.hs.domain.order.OrderFrequencyTypeDayOne;
import com.neusoft.hs.domain.order.OrderFrequencyTypeDayTwo;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.OutHospitalOrderType;
import com.neusoft.hs.domain.order.TemporaryOrderListTreatmentItemSpec;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.CommonDept;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientAreaDept;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Org;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.organization.OutPatientDept;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.organization.Unit;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanDomainService;
import com.neusoft.hs.domain.outpatientoffice.OutPatientRoom;
import com.neusoft.hs.domain.outpatientoffice.VoucherType;
import com.neusoft.hs.domain.patient.PatientAdminDomainService;
import com.neusoft.hs.domain.patient.PatientDomainService;
import com.neusoft.hs.domain.pharmacy.AssistMaterial;
import com.neusoft.hs.domain.pharmacy.ConfigureFluidBatch;
import com.neusoft.hs.domain.pharmacy.ConfigureFluidDomainService;
import com.neusoft.hs.domain.pharmacy.DispenseDrugWin;
import com.neusoft.hs.domain.pharmacy.DispensingDrugBatch;
import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.domain.pharmacy.DrugTypeSpec;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.DrugUseModeAssistMaterial;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.pharmacy.PharmacyAdminService;
import com.neusoft.hs.domain.pharmacy.PharmacyDomainService;
import com.neusoft.hs.domain.recordroom.RecordRoomDomainService;
import com.neusoft.hs.domain.registration.RegistrationDomainService;
import com.neusoft.hs.domain.treatment.CommonTreatmentItemSpec;
import com.neusoft.hs.domain.treatment.TreatmentAdminDomainService;
import com.neusoft.hs.domain.treatment.TreatmentDomainService;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.domain.visit.VisitNameTreatmentItemSpec;
import com.neusoft.hs.engine.visit.VisitFacade;

public class DataIniter {

	protected Org org;// 哈医大二院
	protected Dept dept111;// 住院处
	protected Dept dept222;// 住院收费处

	protected InspectDept dept444;// CT室
	protected InspectDept dept555;// 核磁检查室
	protected Dept dept666;// 病案室
	protected Dept dept777;// 门诊收费部门

	protected Dept deptaaa;// 门诊输液中心

	protected Pharmacy dept333;// 门诊西药房
	protected Pharmacy dept888;// 门诊中药房
	protected Pharmacy deptbbb;// 配液中心
	protected Pharmacy deptccc;// 住院西药房

	protected InPatientDept dept000;// 内泌五
	protected InPatientAreaDept dept000n;// 内泌五护士站

	protected OutPatientDept dept999;// 内分泌门诊

	protected OutPatientRoom room901;// 内分泌门诊一诊室

	protected DispenseDrugWin dispenseDrugWin01;// 门诊西药房摆药窗口01

	protected DispenseDrugWin dispenseDrugWin02;// 门诊西药房摆药窗口02

	protected Staff user101;// 住院处送诊人-曹操
	protected Staff user201;// 住院收费处-张飞
	protected Staff user301;// 门诊西药房摆药岗位-赵云
	protected Staff user303;// 门诊西药房发药岗位-吕布
	protected Staff user401;// CT室安排检查员-吕玲绮
	protected Staff user402;// CT室检查师-张合
	protected Staff user501;// 核磁检查室安排检查员-周瑜
	protected Staff user502;// 核磁检查室检查师-鲁肃
	protected Staff user601;// 质控岗位-刘备
	protected Staff user602;// 病案室岗位-孙权
	protected Staff user701;// 门诊收费部门收费员-魏延
	protected Staff user801;// 门诊中药房摆发药岗位-郭嘉
	protected Staff user901;// 儿科门诊挂号岗位-黄忠
	protected Nurse usera01;// 门诊输液中心护士-甘夫人

	protected Staff userb01;// 配液中心-摆药岗位-刘禅
	protected Staff userb02;// 配液中心-配液岗位-关羽
	protected Staff userb03;// 配液中心-发药岗位-诸葛亮

	protected Staff userc01;// 住院西药房摆药岗位-姜维
	protected Staff userc03;// 住院西药房发药岗位-庞统

	protected Staff user001;// 内泌五接诊护士-大乔
	protected Doctor user002;// 内泌五医生-貂蝉
	protected Nurse user003;// 内泌五护士-小乔

	protected ChargeItem bedChargeItem;// 床位费计费项目【暂时床位费只设一个计费项目】

	protected ChargeItem drugTypeSpec001ChargeItem;// 药品001计费项目（阿司匹林）

	protected ChargeItem drugTypeSpec002ChargeItem;// 药品002计费项目（头孢3）

	protected ChargeItem drugTypeSpec003ChargeItem;// 药品003计费项目(5%葡萄糖液)

	protected ChargeItem drugTypeSpec004ChargeItem;// 药品004计费项目(天花粉)

	protected ChargeItem drugTypeSpec005ChargeItem;// 药品005计费项目(葛根)

	protected ChargeItem drugTypeSpec006ChargeItem;// 药品006计费项目(生地黄)

	protected ChargeItem transportFluidMaterialChargeItem;// 输液材料费

	protected ChargeItem firstNursingChargeItem;// 一级护理计费项目

	protected ChargeItem secondNursingChargeItem;// 二级护理计费项目

	protected ChargeItem brainCTChargeItem;// 脑CT计费项目

	protected ChargeItem brainHCChargeItem;// 脑核磁计费项目

	protected ChargeItem ordinaryVoucherTypeChargeItem;// 普通挂号费计费项目

	protected DrugTypeSpec drugTypeSpec001;// 药品规格001

	protected DrugTypeSpec drugTypeSpec002;// 药品规格002

	protected DrugTypeSpec drugTypeSpec003;// 药品规格003

	protected DrugTypeSpec drugTypeSpec004;// 药品规格004

	protected DrugTypeSpec drugTypeSpec005;// 药品规格005

	protected DrugTypeSpec drugTypeSpec006;// 药品规格006

	protected DrugType drugType001;// 门诊西药房下的药品类型001（有库存属性）

	protected DrugType drugType002;// 门诊西药房下的药品类型002（有库存属性）

	protected DrugType drugType003;// 门诊西药房下的药品类型003（有库存属性）

	protected DrugType drugType004;// 门诊中药房下的药品类型004（有库存属性）

	protected DrugType drugType005;// 门诊中药房下的药品类型005（有库存属性）

	protected DrugType drugType006;// 门诊中药房下的药品类型006（有库存属性）

	protected DrugType drugType002p;// 配液中心下的药品类型002（有库存属性）

	protected DrugType drugType003p;// 配液中心下的药品类型003（有库存属性）

	protected DrugType drugType001I;// 住院西药房下的药品类型001（有库存属性）

	protected DrugOrderType drugOrderType001;// 药品医嘱类型001

	protected DrugOrderType drugOrderType002;// 药品医嘱类型002

	protected DrugOrderType drugOrderType003;// 药品医嘱类型003

	protected DrugOrderType drugOrderType004;// 药品医嘱类型004

	protected DrugOrderType drugOrderType005;// 药品医嘱类型005

	protected DrugOrderType drugOrderType006;// 药品医嘱类型006

	protected InspectItem brainCTInspectItem;// 脑CT检查项目

	protected InspectItem brainHCInspectItem;// 脑核磁检查项目

	protected EnterHospitalOrderType enterHospitalOrderType;// 入院医嘱类型

	protected NursingOrderType firstNursingOrderType;// 一级护理医嘱类型

	protected NursingOrderType secondNursingOrderType;// 二级护理医嘱类型

	protected OutHospitalOrderType leaveHospitalOrderType;// 出院医嘱类型

	protected InspectOrderType inspectOrderType;// 检查医嘱类型

	protected OralOrderUseMode oralOrderUseMode;// 口服用法

	protected InfusionOrderUseModeToInPatient infusionOrderUseModeToInPatient;// 住院输液用法

	protected InfusionOrderUseModeToOutPatient infusionOrderUseModeToOutPatient;// 门诊输液用法

	protected AssistMaterial transportFluidAssistMaterial;// 输液辅材

	protected DrugUseModeAssistMaterial everyOneOrderUseModeAssistMaterialToInPatient;// 按频次收费

	protected DrugUseModeAssistMaterial everyDayOrderUseModeAssistMaterialToInPatient;// 按天收费

	protected DrugUseModeAssistMaterial onlyOneOrderUseModeAssistMaterialToInPatient;// 只收一次

	protected DrugUseModeAssistMaterial everyOneOrderUseModeAssistMaterialToOutPatient;// 按频次收费

	protected OrderFrequencyType orderFrequencyType_0H;// 每天

	protected OrderFrequencyType orderFrequencyType_9H15H;// 每天2次/早9/下3

	protected OrderFrequencyType orderFrequencyType_11H;// 每天1次/早11

	protected TreatmentItemSpec mainDescribeTreatmentItemSpec;// 主诉

	protected TreatmentItemSpec visitNameTreatmentItemSpec;// 患者姓名

	protected TreatmentItemSpec temporaryOrderListTreatmentItemSpec;// 临时医嘱列表

	protected TreatmentItemSpec diagnosisTreatmentItemSpec;// 诊断

	protected MedicalRecordType outPatientRecordMedicalRecordType;// 门诊记录

	protected MedicalRecordType intoWardRecordMedicalRecordType;// 入院记录

	protected MedicalRecordType temporaryOrderListMedicalRecordType;// 临时医嘱单

	protected MedicalRecordType inspectResultMedicalRecordType;// 检查单

	protected VoucherType ordinaryVoucherType;// 普通号

	protected Disease hyperthyroidismDisease;// 甲状腺功能亢进（甲亢）

	protected Disease hypoglycemiaDisease;// 低血糖

	protected ConfigureFluidBatch morningConfigureFluidBatch;// 上午配液批次

	protected ConfigureFluidBatch afternoonConfigureFluidBatch;// 下午配液批次

	protected DispensingDrugBatch dayDispensingDrugBatch;// 一天一次住院摆药批次

	protected Map<ChoiceItem, Object> choices;

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
	protected OrganizationAdminDomainService organizationAdminDomainService;

	@Autowired
	protected UserAdminDomainService userAdminDomainService;

	@Autowired
	protected CostDomainService costDomainService;

	@Autowired
	protected CostAppService costAppService;

	@Autowired
	protected PharmacyDomainService pharmacyDomainService;

	@Autowired
	protected PharmacyAdminService pharmacyAdminService;

	@Autowired
	protected VisitDomainService visitDomainService;

	@Autowired
	protected VisitAppService visitAppService;

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
	protected RecordRoomDomainService recordRoomDomainService;

	@Autowired
	protected MedicalRecordTestService medicalRecordTestService;

	@Autowired
	protected PatientDomainService patientDomainService;

	@Autowired
	protected OutPatientPlanDomainService outPatientPlanDomainService;

	@Autowired
	protected RegistrationAppService registrationAppService;

	@Autowired
	protected RegistrationDomainService registrationDomainService;

	@Autowired
	protected OutPatientDeptAppService outPatientDeptAppService;

	@Autowired
	protected DiseaseAdminDomainService diseaseAdminDomainService;

	@Autowired
	protected VisitFacade visitFacade;

	@Autowired
	protected ConfigureFluidAppService configureFluidAppService;

	@Autowired
	private CostAdminDomainService costAdminDomainService;

	@Autowired
	private MedicalRecordAdminDomainService medicalRecordAdminDomainService;

	@Autowired
	private OrderAdminDomainService orderAdminDomainService;

	@Autowired
	private PatientAdminDomainService patientAdminDomainService;

	@Autowired
	private TreatmentAdminDomainService treatmentAdminDomainService;

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@Autowired
	private ConfigureFluidDomainService configureFluidDomainService;

	public void clone(DataIniter dataIniter) {
		org = dataIniter.org;

		dept111 = dataIniter.dept111;
		dept222 = dataIniter.dept222;
		dept333 = dataIniter.dept333;
		dept444 = dataIniter.dept444;
		dept555 = dataIniter.dept555;
		dept666 = dataIniter.dept666;
		dept777 = dataIniter.dept777;
		dept888 = dataIniter.dept888;
		dept000 = dataIniter.dept000;
		dept999 = dataIniter.dept999;
		deptaaa = dataIniter.deptaaa;
		deptbbb = dataIniter.deptbbb;
		deptccc = dataIniter.deptccc;

		dept000n = dataIniter.dept000n;

		room901 = dataIniter.room901;
		dispenseDrugWin01 = dataIniter.dispenseDrugWin01;
		dispenseDrugWin02 = dataIniter.dispenseDrugWin02;

		user101 = dataIniter.user101;
		user201 = dataIniter.user201;
		user301 = dataIniter.user301;
		user303 = dataIniter.user303;
		user401 = dataIniter.user401;
		user402 = dataIniter.user402;
		user501 = dataIniter.user501;
		user502 = dataIniter.user502;
		user601 = dataIniter.user601;
		user602 = dataIniter.user602;
		user701 = dataIniter.user701;
		user801 = dataIniter.user801;
		user901 = dataIniter.user901;
		usera01 = dataIniter.usera01;
		userb01 = dataIniter.userb01;
		userb02 = dataIniter.userb02;
		userb03 = dataIniter.userb03;
		userc01 = dataIniter.userc01;
		userc03 = dataIniter.userc03;

		user001 = dataIniter.user001;
		user002 = dataIniter.user002;
		user003 = dataIniter.user003;

		bedChargeItem = dataIniter.bedChargeItem;
		drugTypeSpec001ChargeItem = dataIniter.drugTypeSpec001ChargeItem;
		drugTypeSpec002ChargeItem = dataIniter.drugTypeSpec002ChargeItem;
		drugTypeSpec003ChargeItem = dataIniter.drugTypeSpec003ChargeItem;
		transportFluidMaterialChargeItem = dataIniter.transportFluidMaterialChargeItem;
		firstNursingChargeItem = dataIniter.firstNursingChargeItem;
		secondNursingChargeItem = dataIniter.secondNursingChargeItem;
		brainCTChargeItem = dataIniter.brainCTChargeItem;
		brainHCChargeItem = dataIniter.brainHCChargeItem;
		ordinaryVoucherTypeChargeItem = dataIniter.ordinaryVoucherTypeChargeItem;

		drugTypeSpec001 = dataIniter.drugTypeSpec001;
		drugTypeSpec002 = dataIniter.drugTypeSpec002;
		drugTypeSpec003 = dataIniter.drugTypeSpec003;
		drugTypeSpec004 = dataIniter.drugTypeSpec004;
		drugTypeSpec005 = dataIniter.drugTypeSpec005;
		drugTypeSpec006 = dataIniter.drugTypeSpec006;

		drugType001 = dataIniter.drugType001;
		drugType002 = dataIniter.drugType002;
		drugType003 = dataIniter.drugType003;

		drugType004 = dataIniter.drugType004;
		drugType005 = dataIniter.drugType005;
		drugType006 = dataIniter.drugType006;

		drugType002p = dataIniter.drugType002p;
		drugType003p = dataIniter.drugType003p;

		drugType001I = dataIniter.drugType001I;

		drugOrderType001 = dataIniter.drugOrderType001;
		drugOrderType002 = dataIniter.drugOrderType002;
		drugOrderType003 = dataIniter.drugOrderType003;
		drugOrderType004 = dataIniter.drugOrderType004;
		drugOrderType005 = dataIniter.drugOrderType005;
		drugOrderType006 = dataIniter.drugOrderType006;

		brainCTInspectItem = dataIniter.brainCTInspectItem;
		brainHCInspectItem = dataIniter.brainHCInspectItem;

		enterHospitalOrderType = dataIniter.enterHospitalOrderType;
		firstNursingOrderType = dataIniter.firstNursingOrderType;
		secondNursingOrderType = dataIniter.secondNursingOrderType;
		leaveHospitalOrderType = dataIniter.leaveHospitalOrderType;
		inspectOrderType = dataIniter.inspectOrderType;

		oralOrderUseMode = dataIniter.oralOrderUseMode;
		infusionOrderUseModeToInPatient = dataIniter.infusionOrderUseModeToInPatient;
		infusionOrderUseModeToOutPatient = dataIniter.infusionOrderUseModeToOutPatient;

		transportFluidAssistMaterial = dataIniter.transportFluidAssistMaterial;
		everyOneOrderUseModeAssistMaterialToInPatient = dataIniter.everyOneOrderUseModeAssistMaterialToInPatient;
		everyDayOrderUseModeAssistMaterialToInPatient = dataIniter.everyDayOrderUseModeAssistMaterialToInPatient;
		onlyOneOrderUseModeAssistMaterialToInPatient = dataIniter.onlyOneOrderUseModeAssistMaterialToInPatient;
		everyOneOrderUseModeAssistMaterialToOutPatient = dataIniter.everyOneOrderUseModeAssistMaterialToOutPatient;

		orderFrequencyType_0H = dataIniter.orderFrequencyType_0H;
		orderFrequencyType_9H15H = dataIniter.orderFrequencyType_9H15H;
		orderFrequencyType_11H = dataIniter.orderFrequencyType_11H;

		mainDescribeTreatmentItemSpec = dataIniter.mainDescribeTreatmentItemSpec;
		visitNameTreatmentItemSpec = dataIniter.visitNameTreatmentItemSpec;
		temporaryOrderListTreatmentItemSpec = dataIniter.temporaryOrderListTreatmentItemSpec;
		diagnosisTreatmentItemSpec = dataIniter.diagnosisTreatmentItemSpec;

		outPatientRecordMedicalRecordType = dataIniter.outPatientRecordMedicalRecordType;
		intoWardRecordMedicalRecordType = dataIniter.intoWardRecordMedicalRecordType;
		temporaryOrderListMedicalRecordType = dataIniter.temporaryOrderListMedicalRecordType;
		inspectResultMedicalRecordType = dataIniter.inspectResultMedicalRecordType;

		ordinaryVoucherType = dataIniter.ordinaryVoucherType;

		hyperthyroidismDisease = dataIniter.hyperthyroidismDisease;
		hypoglycemiaDisease = dataIniter.hypoglycemiaDisease;

		morningConfigureFluidBatch = dataIniter.morningConfigureFluidBatch;
		afternoonConfigureFluidBatch = dataIniter.afternoonConfigureFluidBatch;

		dayDispensingDrugBatch = dataIniter.dayDispensingDrugBatch;

		choices = dataIniter.choices;
	}

	public void clear() {
		// 清空挂号信息
		registrationDomainService.clearVoucher();
		// 清空门诊医生排班信息
		outPatientPlanDomainService.clearPlanRecord();
		// 清空病案
		recordRoomDomainService.clear();
		// 清空病历
		medicalRecordAdminDomainService.clear();
		// 清空处方
		pharmacyAdminService.clearPrescriptions();
		// 清空医嘱
		orderAdminDomainService.clearOrderTypeApps();
		// 清空组合医嘱
		orderAdminDomainService.clearCompsiteOrdes();
		// 清空医嘱类型
		orderAdminDomainService.clearOrderTypes();
		// 清空医嘱用法
		pharmacyAdminService.clearOrderUseModes();
		// 清空频次类型
		orderAdminDomainService.clearOrderFrequencyTypes();
		// 清空药品类型
		pharmacyAdminService.clearDrugTypes();
		// 清空药品规格
		pharmacyAdminService.clearDrugTypeSpecs();
		// 清空辅材
		pharmacyAdminService.clearAssistMaterial();
		// 清空检查项目
		inspectDomainService.clearInspectItems();
		// 清空挂号类型
		outPatientPlanDomainService.clearVoucherType();
		// 清空计费项目
		costAdminDomainService.clearChargeItems();
		// 清空诊疗项目信息
		treatmentAdminDomainService.clearTreatmentItems();
		// 清空诊疗项目规格
		treatmentAdminDomainService.clearTreatmentItemSpecs();
		// 清空疾病类型
		diseaseAdminDomainService.clearDiseases();
		// 清空成本记录
		costAdminDomainService.clearCostRecords();
		// 清空收费单
		costAdminDomainService.clearChargeBill();
		// 清空患者一次住院
		visitAdminDomainService.clear();
		// 清空患者
		patientAdminDomainService.clear();
		// 清空配液单
		configureFluidDomainService.clearConfigureFluidOrder();
		// 清空配液批次
		configureFluidDomainService.clearConfigureFluidBatch();
		// 清空住院药房摆药单
		pharmacyAdminService.clearDispensingDrugOrder();
		// 清空住院药房摆药批次
		pharmacyAdminService.clearDispensingDrugBatch();
		// 清空用户信息
		userAdminDomainService.clear();
		// 清空摆药窗口
		pharmacyAdminService.clearDispenseDrugWins();
		// 清空门诊诊室
		outPatientPlanDomainService.clearRoom();
		// 清空组织机构信息
		organizationAdminDomainService.clear();
	}

	@Transactional(rollbackFor = Exception.class)
	public void initData() {

		initOrgs();

		initRooms();

		initDispenseDrugWins();

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

		initVoucherTypes();

		initDiseases();

		initConfigureFluidBatchs();

		initDispensingDrugBatchs();
	}

	private void initOrgs() {

		List<Unit> units = new ArrayList<Unit>();

		org = new Org();
		org.setId("org000");
		org.setName("哈医大二院");

		units.add(org);

		dept111 = new CommonDept();
		dept111.setId("dept111");
		dept111.setName("住院处");
		dept111.setParent(org);
		dept111.setOrg(org);

		units.add(dept111);

		dept222 = new CommonDept();
		dept222.setId("dept222");
		dept222.setName("住院收费处");
		dept222.setParent(org);
		dept222.setOrg(org);

		units.add(dept222);

		dept333 = new Pharmacy();
		dept333.setId("dept333");
		dept333.setName("门诊西药房");
		dept333.setParent(org);
		dept333.setOrg(org);

		units.add(dept333);

		dept444 = new InspectDept();
		dept444.setId("dept444");
		dept444.setName("CT室");
		dept444.setParent(org);
		dept444.setOrg(org);

		units.add(dept444);

		dept555 = new InspectDept();
		dept555.setId("dept555");
		dept555.setName("核磁检查室");
		dept555.setParent(org);
		dept555.setOrg(org);

		units.add(dept555);

		dept666 = new CommonDept();
		dept666.setId("dept666");
		dept666.setName("病案室");
		dept666.setParent(org);
		dept666.setOrg(org);

		units.add(dept666);

		dept777 = new CommonDept();
		dept777.setId("dept777");
		dept777.setName("门诊收费部门");
		dept777.setParent(org);
		dept777.setOrg(org);

		units.add(dept777);

		dept888 = new Pharmacy();
		dept888.setId("dept888");
		dept888.setName("门诊中药房");
		dept888.setParent(org);
		dept888.setOrg(org);

		units.add(dept888);

		deptaaa = new Pharmacy();
		deptaaa.setId("deptaaa");
		deptaaa.setName("门诊输液中心");
		deptaaa.setParent(org);
		deptaaa.setOrg(org);

		units.add(deptaaa);

		deptbbb = new Pharmacy();
		deptbbb.setId("deptbbb");
		deptbbb.setName("配液中心");
		deptbbb.setParent(org);
		deptbbb.setOrg(org);

		units.add(deptbbb);

		deptccc = new Pharmacy();
		deptccc.setId("deptccc");
		deptccc.setName("住院西药房");
		deptccc.setParent(org);
		deptccc.setOrg(org);

		units.add(deptccc);

		dept000 = new InPatientDept();
		dept000.setId("dept000");
		dept000.setName("内泌五");
		dept000.setParent(org);
		dept000.setOrg(org);

		units.add(dept000);

		dept000n = new InPatientAreaDept();
		dept000n.setId("dept000n");
		dept000n.setName("内泌五护士站");
		dept000n.setParent(org);
		dept000n.setOrg(org);
		dept000n.addDept(dept000);

		units.add(dept000n);

		dept999 = new OutPatientDept();
		dept999.setId("dept999");
		dept999.setName("内分泌门诊");
		dept999.setParent(org);
		dept999.setOrg(org);

		units.add(dept999);

		organizationAdminDomainService.create(units);

		org.setInChargeDept(dept222);
		org.setOutChargeDept(dept777);

		organizationAdminDomainService.save(org);
	}

	private void initRooms() {

		List<OutPatientRoom> rooms = new ArrayList<OutPatientRoom>();

		room901 = new OutPatientRoom();
		room901.setId("room901");
		room901.setName("内分泌门诊一诊室");
		room901.setDept(dept999);

		rooms.add(room901);

		outPatientPlanDomainService.createRooms(rooms);
	}

	private void initDispenseDrugWins() {

		List<DispenseDrugWin> dispenseDrugWins = new ArrayList<DispenseDrugWin>();

		dispenseDrugWin01 = new DispenseDrugWin();
		dispenseDrugWin01.setId("dispenseDrugWin01");
		dispenseDrugWin01.setName("门诊西药房摆药窗口01");
		dispenseDrugWin01.setPharmacy(dept333);

		dispenseDrugWin01.setState(DispenseDrugWin.State_Normal);

		dispenseDrugWins.add(dispenseDrugWin01);

		dispenseDrugWin02 = new DispenseDrugWin();
		dispenseDrugWin02.setId("dispenseDrugWin02");
		dispenseDrugWin02.setName("门诊西药房摆药窗口01");
		dispenseDrugWin02.setPharmacy(dept333);

		dispenseDrugWin02.setState(DispenseDrugWin.State_Normal);

		dispenseDrugWins.add(dispenseDrugWin02);

		pharmacyAdminService.createDispenseDrugWins(dispenseDrugWins);
	}

	private void initUsers() {

		List<AbstractUser> users = new ArrayList<AbstractUser>();

		user101 = new Staff();

		user101.setId("staff101");
		user101.setName("住院处送诊人-曹操");
		user101.setDept(dept111);

		users.add(user101);

		user201 = new Staff();

		user201.setId("staff201");
		user201.setName("住院收费处-张飞");
		user201.setDept(dept222);

		users.add(user201);

		user301 = new Staff();

		user301.setId("staff301");
		user301.setName("门诊西药房摆药岗位-赵云");
		user301.setDept(dept333);

		users.add(user301);

		user303 = new Staff();

		user303.setId("staff303");
		user303.setName("门诊西药房发药岗位-郭嘉");
		user303.setDept(dept333);

		users.add(user303);

		user401 = new Staff();

		user401.setId("staff401");
		user401.setName("CT室安排检查员-吕玲绮");
		user401.setDept(dept444);

		users.add(user401);

		user402 = new Staff();

		user402.setId("staff402");
		user402.setName("CT室检查师-张合");
		user402.setDept(dept444);

		users.add(user402);

		user501 = new Staff();

		user501.setId("staff501");
		user501.setName("核磁检查室安排检查员-周瑜");
		user501.setDept(dept555);

		users.add(user501);

		user502 = new Staff();

		user502.setId("staff502");
		user502.setName("核磁检查室检查师-鲁肃");
		user502.setDept(dept555);

		users.add(user502);

		user601 = new Staff();

		user601.setId("staff601");
		user601.setName("质控岗位-刘备");
		user601.setDept(dept666);

		users.add(user601);

		user602 = new Staff();

		user602.setId("staff602");
		user602.setName("病案室岗位-孙权");
		user602.setDept(dept666);

		users.add(user602);

		user701 = new Staff();

		user701.setId("staff701");
		user701.setName("门诊收费部门收费员-魏延");
		user701.setDept(dept777);

		users.add(user701);

		user801 = new Staff();

		user801.setId("staff801");
		user801.setName("门诊中药房摆发药岗位-郭嘉");
		user801.setDept(dept888);

		users.add(user801);

		user901 = new Staff();

		user901.setId("staff901");
		user901.setName("内分泌门诊挂号岗位-黄忠");
		user901.setDept(dept999);

		users.add(user901);

		usera01 = new Nurse();

		usera01.setId("nursea01");
		usera01.setName("门诊输液中心护士-甘夫人");
		usera01.setDept(deptaaa);

		users.add(usera01);

		userb01 = new Staff();

		userb01.setId("staffb01");
		userb01.setName("配液中心-摆药岗位-刘禅");
		userb01.setDept(deptbbb);

		users.add(userb01);

		userb02 = new Staff();

		userb02.setId("staffb02");
		userb02.setName("配液中心-配液岗位-关羽");
		userb02.setDept(deptbbb);

		users.add(userb02);

		userb03 = new Staff();

		userb03.setId("staffb03");
		userb03.setName("配液中心-发药岗位-诸葛亮");
		userb03.setDept(deptbbb);

		users.add(userb03);

		userc01 = new Staff();

		userc01.setId("staffc01");
		userc01.setName("住院西药房摆药岗位-姜维");
		userc01.setDept(deptccc);

		users.add(userc01);

		userc03 = new Staff();

		userc03.setId("staffc03");
		userc03.setName("住院西药房发药岗位-庞统");
		userc03.setDept(deptccc);

		users.add(userc03);

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
		user003.setDept(dept000n);

		users.add(user003);

		userAdminDomainService.create(users);
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

		firstNursingChargeItem = new ChargeItem();
		firstNursingChargeItem.setId("firstNursingChargeItem");
		firstNursingChargeItem.setCode("firstNursingChargeItem");
		firstNursingChargeItem.setName("一级护理");
		firstNursingChargeItem.setPrice(22);
		firstNursingChargeItem.setChargingMode(ChargeItem.ChargingMode_Day);

		chargeItems.add(firstNursingChargeItem);

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

		drugTypeSpec004ChargeItem = new ChargeItem();
		drugTypeSpec004ChargeItem.setId("drugTypeSpec004ChargeItem");
		drugTypeSpec004ChargeItem.setCode("drugTypeSpec004ChargeItem");
		drugTypeSpec004ChargeItem.setName("天花粉");
		drugTypeSpec004ChargeItem.setPrice(5);
		drugTypeSpec004ChargeItem.setUnit("克");
		drugTypeSpec004ChargeItem
				.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(drugTypeSpec004ChargeItem);

		drugTypeSpec005ChargeItem = new ChargeItem();
		drugTypeSpec005ChargeItem.setId("drugTypeSpec005ChargeItem");
		drugTypeSpec005ChargeItem.setCode("drugTypeSpec005ChargeItem");
		drugTypeSpec005ChargeItem.setName("葛根");
		drugTypeSpec005ChargeItem.setPrice(3);
		drugTypeSpec005ChargeItem.setUnit("克");
		drugTypeSpec005ChargeItem
				.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(drugTypeSpec005ChargeItem);

		drugTypeSpec006ChargeItem = new ChargeItem();
		drugTypeSpec006ChargeItem.setId("drugTypeSpec006ChargeItem");
		drugTypeSpec006ChargeItem.setCode("drugTypeSpec006ChargeItem");
		drugTypeSpec006ChargeItem.setName("生地黄");
		drugTypeSpec006ChargeItem.setPrice(4);
		drugTypeSpec006ChargeItem.setUnit("克");
		drugTypeSpec006ChargeItem
				.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(drugTypeSpec006ChargeItem);

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

		ordinaryVoucherTypeChargeItem = new ChargeItem();
		ordinaryVoucherTypeChargeItem.setId("ordinaryVoucherTypeChargeItem");
		ordinaryVoucherTypeChargeItem.setCode("ordinaryVoucherTypeChargeItem");
		ordinaryVoucherTypeChargeItem.setName("普通挂号费");
		ordinaryVoucherTypeChargeItem.setPrice(7);
		ordinaryVoucherTypeChargeItem.setUnit("次");
		ordinaryVoucherTypeChargeItem
				.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(ordinaryVoucherTypeChargeItem);

		costAdminDomainService.create(chargeItems);
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

		drugTypeSpec004 = new DrugTypeSpec();
		drugTypeSpec004.setId("drugTypeSpec004");
		drugTypeSpec004.setName("天花粉");
		drugTypeSpec004.setChargeItem(drugTypeSpec004ChargeItem);

		drugTypeSpecs.add(drugTypeSpec004);

		drugTypeSpec005 = new DrugTypeSpec();
		drugTypeSpec005.setId("drugTypeSpec005");
		drugTypeSpec005.setName("葛根");
		drugTypeSpec005.setChargeItem(drugTypeSpec005ChargeItem);

		drugTypeSpecs.add(drugTypeSpec005);

		drugTypeSpec006 = new DrugTypeSpec();
		drugTypeSpec006.setId("drugTypeSpec006");
		drugTypeSpec006.setName("生地黄");
		drugTypeSpec006.setChargeItem(drugTypeSpec006ChargeItem);

		drugTypeSpecs.add(drugTypeSpec006);

		pharmacyAdminService.createDrugTypeSpecs(drugTypeSpecs);
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

		drugType004 = new DrugType();
		drugType004.setId("drugType004");
		drugType004.setDrugTypeSpec(drugTypeSpec004);
		drugType004.setPharmacy(dept888);
		drugType004.setStock(2000);

		drugTypes.add(drugType004);

		drugType005 = new DrugType();
		drugType005.setId("drugType005");
		drugType005.setDrugTypeSpec(drugTypeSpec005);
		drugType005.setPharmacy(dept888);
		drugType005.setStock(2000);

		drugTypes.add(drugType005);

		drugType006 = new DrugType();
		drugType006.setId("drugType006");
		drugType006.setDrugTypeSpec(drugTypeSpec006);
		drugType006.setPharmacy(dept888);
		drugType006.setStock(2000);

		drugTypes.add(drugType006);

		drugType002p = new DrugType();
		drugType002p.setId("drugType002p");
		drugType002p.setDrugTypeSpec(drugTypeSpec002);
		drugType002p.setPharmacy(deptbbb);
		drugType002p.setStock(1000);

		drugTypes.add(drugType002p);

		drugType003p = new DrugType();
		drugType003p.setId("drugType003p");
		drugType003p.setDrugTypeSpec(drugTypeSpec003);
		drugType003p.setPharmacy(deptbbb);
		drugType003p.setStock(2000);

		drugTypes.add(drugType003p);

		drugType001I = new DrugType();
		drugType001I.setId("drugType001I");
		drugType001I.setDrugTypeSpec(drugTypeSpec001);
		drugType001I.setPharmacy(deptccc);
		drugType001I.setStock(200);

		drugTypes.add(drugType001I);

		pharmacyAdminService.createDrugTypes(drugTypes);
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
		drugOrderType001.setDrugTypeSpec(drugTypeSpec001);

		orderTypes.add(drugOrderType001);

		drugOrderType002 = new DrugOrderType();
		drugOrderType002.setId("drugOrderType002");
		drugOrderType002.setCode("drugOrderType002");
		drugOrderType002.setName("头孢3");
		drugOrderType002.setDrugTypeSpec(drugTypeSpec002);

		orderTypes.add(drugOrderType002);

		drugOrderType003 = new DrugOrderType();
		drugOrderType003.setId("drugOrderType003");
		drugOrderType003.setCode("drugOrderType003");
		drugOrderType003.setName("5%葡萄糖液");
		drugOrderType003.setDrugTypeSpec(drugTypeSpec003);

		orderTypes.add(drugOrderType003);

		drugOrderType004 = new DrugOrderType();
		drugOrderType004.setId("drugOrderType004");
		drugOrderType004.setCode("drugOrderType004");
		drugOrderType004.setName("天花粉");
		drugOrderType004.setDrugTypeSpec(drugTypeSpec004);

		orderTypes.add(drugOrderType004);

		drugOrderType005 = new DrugOrderType();
		drugOrderType005.setId("drugOrderType005");
		drugOrderType005.setCode("drugOrderType005");
		drugOrderType005.setName("葛根");
		drugOrderType005.setDrugTypeSpec(drugTypeSpec005);

		orderTypes.add(drugOrderType005);

		drugOrderType006 = new DrugOrderType();
		drugOrderType006.setId("drugOrderType006");
		drugOrderType006.setCode("drugOrderType006");
		drugOrderType006.setName("生地黄");
		drugOrderType006.setDrugTypeSpec(drugTypeSpec006);

		orderTypes.add(drugOrderType006);

		enterHospitalOrderType = new EnterHospitalOrderType();
		enterHospitalOrderType.setId("enterHospitalOrderType");
		enterHospitalOrderType.setCode("enterHospitalOrderType");
		enterHospitalOrderType.setName("入院医嘱");

		orderTypes.add(enterHospitalOrderType);

		leaveHospitalOrderType = new OutHospitalOrderType();
		leaveHospitalOrderType.setId("leaveHospitalOrderType");
		leaveHospitalOrderType.setCode("leaveHospitalOrderType");
		leaveHospitalOrderType.setName("出院医嘱");

		orderTypes.add(leaveHospitalOrderType);

		secondNursingOrderType = new NursingOrderType();
		secondNursingOrderType.setId("secondNursingOrderType");
		secondNursingOrderType.setCode("secondNursingOrderType");
		secondNursingOrderType.setName(OrderExecute.Type_SecondNursing);
		secondNursingOrderType.setNursingType(OrderExecute.Type_SecondNursing);
		secondNursingOrderType.setChargeItem(secondNursingChargeItem);

		orderTypes.add(secondNursingOrderType);

		firstNursingOrderType = new NursingOrderType();
		firstNursingOrderType.setId("firstNursingOrderType");
		firstNursingOrderType.setCode("firstNursingOrderType");
		firstNursingOrderType.setName(OrderExecute.Type_FirstNursing);
		firstNursingOrderType.setNursingType(OrderExecute.Type_FirstNursing);
		firstNursingOrderType.setChargeItem(firstNursingChargeItem);

		orderTypes.add(firstNursingOrderType);

		inspectOrderType = new InspectOrderType();
		inspectOrderType.setId("brainCTInspectOrderType");
		inspectOrderType.setCode("brainCTInspectOrderType");
		inspectOrderType.setName("脑CT");

		orderTypes.add(inspectOrderType);

		orderAdminDomainService.createOrderTypes(orderTypes);
	}

	private void initOrderUseModes() {

		List<DrugUseMode> orderUseModes = new ArrayList<DrugUseMode>();

		oralOrderUseMode = new OralOrderUseMode();
		oralOrderUseMode.setId("oralOrderUseMode");
		oralOrderUseMode.setCode("oralOrderUseMode");
		oralOrderUseMode.setName("口服");

		orderUseModes.add(oralOrderUseMode);

		infusionOrderUseModeToInPatient = new InfusionOrderUseModeToInPatient();
		infusionOrderUseModeToInPatient
				.setId("infusionOrderUseModeToInPatient");
		infusionOrderUseModeToInPatient
				.setCode("infusionOrderUseModeToInPatient");
		infusionOrderUseModeToInPatient.setName("住院输液");

		orderUseModes.add(infusionOrderUseModeToInPatient);

		infusionOrderUseModeToOutPatient = new InfusionOrderUseModeToOutPatient();
		infusionOrderUseModeToOutPatient
				.setId("infusionOrderUseModeToOutPatient");
		infusionOrderUseModeToOutPatient
				.setCode("infusionOrderUseModeToOutPatient");
		infusionOrderUseModeToOutPatient.setName("门诊输液");

		orderUseModes.add(infusionOrderUseModeToOutPatient);

		pharmacyAdminService.createOrderUseModes(orderUseModes);

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

		pharmacyAdminService.createAssistMaterials(assistMaterials);
	}

	private void initOrderUseModeAssistMaterials() {

		everyOneOrderUseModeAssistMaterialToInPatient = new DrugUseModeAssistMaterial();
		everyOneOrderUseModeAssistMaterialToInPatient
				.setId("everyOneInPatient");
		everyOneOrderUseModeAssistMaterialToInPatient
				.setCode("everyOneInPatient");
		everyOneOrderUseModeAssistMaterialToInPatient
				.setAssistMaterial(transportFluidAssistMaterial);
		everyOneOrderUseModeAssistMaterialToInPatient
				.setOrderUseMode(infusionOrderUseModeToInPatient);
		everyOneOrderUseModeAssistMaterialToInPatient
				.setChargeMode(DrugUseModeAssistMaterial.everyOne);
		everyOneOrderUseModeAssistMaterialToInPatient
				.setSign(InfusionOrderUseModeToInPatient.transportFluid);

		everyDayOrderUseModeAssistMaterialToInPatient = new DrugUseModeAssistMaterial();
		everyDayOrderUseModeAssistMaterialToInPatient
				.setId("everyDayInPatient");
		everyDayOrderUseModeAssistMaterialToInPatient
				.setCode("everyDayInPatient");
		everyDayOrderUseModeAssistMaterialToInPatient
				.setAssistMaterial(transportFluidAssistMaterial);
		everyDayOrderUseModeAssistMaterialToInPatient
				.setOrderUseMode(infusionOrderUseModeToInPatient);
		everyDayOrderUseModeAssistMaterialToInPatient
				.setChargeMode(DrugUseModeAssistMaterial.everyDay);
		everyDayOrderUseModeAssistMaterialToInPatient
				.setSign(InfusionOrderUseModeToInPatient.transportFluid);

		onlyOneOrderUseModeAssistMaterialToInPatient = new DrugUseModeAssistMaterial();
		onlyOneOrderUseModeAssistMaterialToInPatient.setId("onlyOneInPatient");
		onlyOneOrderUseModeAssistMaterialToInPatient
				.setCode("onlyOneInPatient");
		onlyOneOrderUseModeAssistMaterialToInPatient
				.setAssistMaterial(transportFluidAssistMaterial);
		onlyOneOrderUseModeAssistMaterialToInPatient
				.setOrderUseMode(infusionOrderUseModeToInPatient);
		onlyOneOrderUseModeAssistMaterialToInPatient
				.setChargeMode(DrugUseModeAssistMaterial.onlyOne);
		onlyOneOrderUseModeAssistMaterialToInPatient
				.setSign(InfusionOrderUseModeToInPatient.transportFluid);

		everyOneOrderUseModeAssistMaterialToOutPatient = new DrugUseModeAssistMaterial();
		everyOneOrderUseModeAssistMaterialToOutPatient
				.setId("everyOneOutPatient");
		everyOneOrderUseModeAssistMaterialToOutPatient
				.setCode("everyOneOutPatient");
		everyOneOrderUseModeAssistMaterialToOutPatient
				.setAssistMaterial(transportFluidAssistMaterial);
		everyOneOrderUseModeAssistMaterialToOutPatient
				.setOrderUseMode(infusionOrderUseModeToOutPatient);
		everyOneOrderUseModeAssistMaterialToOutPatient
				.setChargeMode(DrugUseModeAssistMaterial.everyOne);
		everyOneOrderUseModeAssistMaterialToOutPatient
				.setSign(InfusionOrderUseModeToInPatient.transportFluid);
	}

	private void initOrderFrequencyTypes() {

		List<OrderFrequencyType> orderFrequencyTypes = new ArrayList<OrderFrequencyType>();

		orderFrequencyType_0H = new OrderFrequencyTypeDayOne(0);

		orderFrequencyTypes.add(orderFrequencyType_0H);

		orderFrequencyType_9H15H = new OrderFrequencyTypeDayTwo(9, 15);

		orderFrequencyTypes.add(orderFrequencyType_9H15H);

		orderFrequencyType_11H = new OrderFrequencyTypeDayOne(11);

		orderFrequencyTypes.add(orderFrequencyType_11H);

		orderAdminDomainService.createOrderFrequencyTypes(orderFrequencyTypes);

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

		diagnosisTreatmentItemSpec = new DiagnosisTreatmentItemSpec();
		diagnosisTreatmentItemSpec.setId("诊断");
		diagnosisTreatmentItemSpec.setName("诊断");

		treatmentItemSpecs.add(diagnosisTreatmentItemSpec);

		treatmentAdminDomainService
				.createTreatmentItemSpecs(treatmentItemSpecs);
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

		outPatientRecordMedicalRecordType = new MedicalRecordType();
		outPatientRecordMedicalRecordType.setId("门诊记录");
		outPatientRecordMedicalRecordType.setName("门诊记录");
		outPatientRecordMedicalRecordType.setNeedSign(false);
		outPatientRecordMedicalRecordType.setNeedCreate(false);

		items = new ArrayList<TreatmentItemSpec>();
		items.add(visitNameTreatmentItemSpec);
		items.add(mainDescribeTreatmentItemSpec);
		items.add(diagnosisTreatmentItemSpec);

		outPatientRecordMedicalRecordType.setItems(items);

		medicalRecordTypes.add(outPatientRecordMedicalRecordType);

		medicalRecordAdminDomainService
				.createMedicalRecordTypes(medicalRecordTypes);
	}

	private void initVoucherTypes() {

		List<VoucherType> voucherTypes = new ArrayList<VoucherType>();

		ordinaryVoucherType = new VoucherType();
		ordinaryVoucherType.setId("普通号");
		ordinaryVoucherType.setName("普通号");
		ordinaryVoucherType.setChargeItem(ordinaryVoucherTypeChargeItem);

		voucherTypes.add(ordinaryVoucherType);

		outPatientPlanDomainService.createVoucherTypes(voucherTypes);
	}

	private void initDiseases() {

		List<Disease> diseases = new ArrayList<Disease>();

		hyperthyroidismDisease = new Disease();
		hyperthyroidismDisease.setId("甲状腺功能亢进（甲亢）");
		hyperthyroidismDisease.setName("甲状腺功能亢进（甲亢）");

		diseases.add(hyperthyroidismDisease);

		hypoglycemiaDisease = new Disease();
		hypoglycemiaDisease.setId("低血糖");
		hypoglycemiaDisease.setName("低血糖");

		diseases.add(hypoglycemiaDisease);

		diseaseAdminDomainService.createDiseases(diseases);
	}

	private void initConfigureFluidBatchs() {

		List<ConfigureFluidBatch> batchs = new ArrayList<ConfigureFluidBatch>();

		morningConfigureFluidBatch = new ConfigureFluidBatch();
		morningConfigureFluidBatch.setId("上午配液");
		morningConfigureFluidBatch.setCode("上午配液");
		morningConfigureFluidBatch.setName("上午配液");
		morningConfigureFluidBatch.setBeginDate(0);
		morningConfigureFluidBatch.setEndDate(12);
		morningConfigureFluidBatch.setPlanExecuteDate(9);
		morningConfigureFluidBatch.setPharmacy(deptbbb);

		batchs.add(morningConfigureFluidBatch);

		afternoonConfigureFluidBatch = new ConfigureFluidBatch();
		afternoonConfigureFluidBatch.setId("下午配液");
		afternoonConfigureFluidBatch.setCode("下午配液");
		afternoonConfigureFluidBatch.setName("下午配液");
		afternoonConfigureFluidBatch.setBeginDate(12);
		afternoonConfigureFluidBatch.setEndDate(24);
		afternoonConfigureFluidBatch.setPlanExecuteDate(14);
		afternoonConfigureFluidBatch.setPharmacy(deptbbb);

		batchs.add(afternoonConfigureFluidBatch);

		configureFluidDomainService.createConfigureFluidBatchs(batchs);
	}

	private void initDispensingDrugBatchs() {
		
		List<DispensingDrugBatch> batchs = new ArrayList<DispensingDrugBatch>();

		dayDispensingDrugBatch = new DispensingDrugBatch();
		dayDispensingDrugBatch.setId("住院西药房摆药一天一次");
		dayDispensingDrugBatch.setCode("住院西药房摆药一天一次");
		dayDispensingDrugBatch.setName("住院西药房摆药一天一次");
		dayDispensingDrugBatch.setBeginDate(0);
		dayDispensingDrugBatch.setEndDate(24);
		dayDispensingDrugBatch.setPlanExecuteDate(10);
		dayDispensingDrugBatch.setPharmacy(deptccc);

		batchs.add(dayDispensingDrugBatch);

		pharmacyAdminService.createDispensingDrugBatchs(batchs);
	}

}
