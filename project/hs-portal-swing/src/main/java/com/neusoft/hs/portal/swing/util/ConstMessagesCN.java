package com.neusoft.hs.portal.swing.util;

import javax.swing.Icon;

public interface ConstMessagesCN {

	interface DialogTitles {
		String CLIENT_MODAL = "Adding client";
		String ADDRESS_MODAL = "Adding address";
		String PAYMENT_METHOD_MODAL = "Adding payment methods";
		String PAYMENT_MODAL = "Adding payments";
		String RESERVATION_MODAL = "Adding reservations";
		String ROOM_STATUS_MODAL = "Adding room statuses";
		String ROOM_TYPE_MODAL = "Adding room types";
		String ROOM_MODAL = "Adding rooms";
		String RATE_MODAL = "Adding rates";
		String ROOM_X_RESERVATION_MODAL = "Adding room reservations";

		String Login_MODAL = "登陆";
		String Register_MODAL = "创建患者";

	}

	interface Messages {
		String WINDOWS_STYLE_LOADING_ERROR_MESSAGE = "There was an error while loading windows look an feel: ";
		String ALERT_TILE = "Alert";
		String NON_ROW_SELECTED = "Non row has been selected";
		String INFORMATION_TITLE = "Information";
		String DELETE_ROW_ERROR = "Could not delete a row. Check if there are any connections between tables.";
		String RUNTIMEEXCEPTION = "内部异常";
	}

	interface Labels {
		String MAIN_MENU = "医院业务（RealOne Suite）";
		String Version = "版本";
		String BuildDate = "构建日期";
		String CLIENTS = "Clients";
		String RESERVATIONS = "Reservations";
		String ADDRESSES = "Addresses";
		String ADD_BTN = "增加";
		String CONFIRM_BTN = "确定";
		String CLOSE_BTN = "关闭";
		String ReCreate_BTN = "重新生成";
		String Create_BTN = "生成";
		String Sign_BTN = "签名";

		String REMOVE_BTN = "删除";

		String SURNAME = "Surname";
		String PESEL = "Pesel";
		String PHONE_NUMBER = "Phone number";
		String EMAIL = "E-mail";
		String ADDRESS = "Address";
		String STREET = "Street";
		String HOUSE_NUMBER = "House number";
		String FLAT_NUMBER = "Flat number";
		String CITY = "City";
		String POSTAL_CODE = "Post code";
		String RESERVATION_STATUS = "Reservation status";
		String RESERVATION_STATUSES = "Reservation statuses";
		String PAYMENT_METHODS = "Payment methods";
		String PAYMENTS = "Payments";
		String PAYMENT_METHOD = "Payment method";
		String VALUE = "Value";
		String DATE_OF_PAYMENT = "Date of payment";
		String PAYMENT = "Payment";
		String CLIENT = "Client";
		String FROM_DATE = "From";
		String TO_DATE = "To";
		String RESERVATION_DATE = "Reservation date";
		String AMOUNT = "Suma";
		String ROOM_STATUS = "Room status";
		String ROOM_STATUSES = "Room statuses";
		String ROOM_STATUS_DESCRIPTION = "Room status description";
		String ROOM_TYPE = "Room type";
		String ROOM_MULTIPLIER = "Multiplier";
		String ROOM_TYPES = "Room types";
		String ROOM_TYPE_DESCRIPTION = "Room type description";
		String ROOM_NUMBER_OF_PEOPLE = "Number of people";
		String ROOM_DESCRIPTION = "Description";
		String ROOMS = "Rooms";
		String ROOM = "Room";
		String BASIC_RATE = "Basic rate";
		String RATES = "Rates";
		String ROOM_X_RESERVATIONS = "Rooms reservations";
		String RESERVATION = "Reservation";
		String PRICE = "Price";
		String FORMS = "日常业务";
		String REPORTS = "统计业务";
		String PAYMENT_COUNT = "Payment coun";
		String NUMBER_OF_COUNT = "Number of count";
		String NUMBER_OF_RESERVATIONS = "Number of reservations";
		String CLIENT_RESERVATIONS = "Client's reservations";

		String Login_Btn = "登录";
		String CANCEL_BTN = "取消";
		String LogoutState = "未登录";

		String Login = "登录";
		String CreateOutPatientPlanRecord = "创建出诊计划";
		String Register = "送诊";
		String InPatientDept = "住院科室";
		String InPatientArea = "住院病区";
		String RespDoctor = "责任医生";
		String NAME = "名称";
		String Account = "账号";
		String InitAccount = "预存住院费";
		String ReceiveVisit = "接诊";
		String InitBlanace = "预存金额";
		String Bed = "床位";
		String RespNurse = "责任护士";
		String Logo = "主题介绍";

		String CreateOrder = "开立医嘱";
		String CreateMedicalRecord = "编写病历";
		String VerifyOrder = "核对医嘱";
		String SendOrderExecute = "发送医嘱执行条目";
		String FinishOrderExecute = "完成医嘱执行条目";
		String MaintainTreatment = "维护诊疗信息";
		String ArrangementMedicalRecord = "整理病历";
		String TransferMedicalRecord = "移交档案室";
		String QualityControl = "确认诊疗过程合格";
		String ArchiveMedicalRecord = "归档病历";

		String Order = "医嘱";

		String ID = "ID";
		String Name = "名称";
		String State = "状态";
		String Type = "类型";
		String Info = "信息";
		String Doctor = "医生";
		String Room = "诊室";
		String BelongDept = "所属部门";
		String ExecuteDept = "执行部门";
		String Operator = "操作人";
		String CreateDate = "创建时间";
		String ExecuteDate = "执行时间";

		String CarNumber = "身份证号";
		String Birthday = "生日";
		String Sex = "性别";
		String VisitName = "患者姓名";
		String IntoWardDate = "进入病房时间";

		String Visit = "患者";
		String OrderType = "医嘱类型";
		String PlanStartDate = "计划开始时间";
		String PlanEndDate = "计划结束时间";
		String PlaceType = "位置";

		String FrequencyType = "频次";

		String OrderUseMode = "用法";
		String Count = "数量";
		String Pharmacy = "药房";
		String ChargeItemName = "收费项目";
		String Amount = "单价";
		String Price = "价格";

		String VisitLog = "患者就诊日志";
		String ChargeBill = "收费单";
		String ChargeRecord = "收费记录";
		String OrderList = "医嘱单";
		String OrderExecuteList = "医嘱执行单";
		String Treatment = "诊疗信息";
		String MedicalRecord = "病历";
		String RunTest = "生成测试场景数据";

		String CurrentAllotNumber = "当前挂号值";
		String CurrentEncounterNumber = "当前就诊值";
		String MaxAllotNumber = "最大挂号值";

		String createInWardRecord = "创建入院记录";
		String createTemporaryOrderListMR = "创建临时医嘱单";
		String createInspectResultMR = "生成检查单病历";
		String transferMedicalRecord = "移交档案室";
		String pass = "通过";
		String archive = "归档";

		String position = "位置";
	}

	interface ValidationMessages {
		String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Not all required fields have been filled or filled data is incorrect";
		String PESEL_LENGTH_INCORRECT = "PESEL should contain 11 characters.";
		String DATE_FROM_MUST_BE_EARLIER_THAN_TO_DATE = "Date from must be earlier than date to";
	}
}
