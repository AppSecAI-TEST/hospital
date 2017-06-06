//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\pharmacy\\PharmacyDomainService.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.DispensingDrugOrderExecute;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.InPatientAreaDept;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.log.LogUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class PharmacyDomainService {

	@Autowired
	private DrugTypeSpecRepo drugTypeSpecRepo;

	@Autowired
	private PharmacyRepo pharmacyRepo;

	@Autowired
	private DispenseDrugWinRepo dispenseDrugWinRepo;

	@Autowired
	private DrugTypeRepo drugTypeRepo;

	@Autowired
	private DrugUseModeRepo orderUseModeRepo;

	@Autowired
	private DrugUseModeAssistMaterialRepo orderUseModeAssistMaterialRepo;

	@Autowired
	private PrescriptionRepo prescriptionRepo;

	@Autowired
	private DispensingDrugBatchRepo dispensingDrugBatchRepo;

	@Autowired
	private DispensingDrugOrderRepo dispensingDrugOrderRepo;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	@Autowired
	private ApplicationContext applicationContext;

	public List<DrugType> findByDrugTypeSpec(DrugTypeSpec drugTypeSpec) {
		return drugTypeRepo.findByDrugTypeSpec(drugTypeSpec);
	}

	public DrugTypeSpec findDrugTypeSpec(String drugTypeSpecId) {
		return drugTypeSpecRepo.findOne(drugTypeSpecId);
	}

	public List<Prescription> findPrescriptions(Visit visit) {
		return prescriptionRepo.findByVisit(visit);
	}

	public Prescription findThePrescription(Order order) {
		return prescriptionRepo.findByOrdersIn(order);
	}

	public DrugType findTheDrugType(String id) {
		return drugTypeRepo.findOne(id);
	}

	/**
	 * @param executes
	 * @param area
	 * @param batch
	 * @param pharmacy
	 * @roseuid 592E6DFF034D
	 */
	public DispensingDrugOrder createOrder(InPatientAreaDept area,
			DispensingDrugBatch batch,
			List<DispensingDrugOrderExecute> executes, AbstractUser user) {
		DispensingDrugOrder dispensingDrugOrder = new DispensingDrugOrder();

		dispensingDrugOrder.setExecutes(executes);
		dispensingDrugOrder.setCreator(user);
		dispensingDrugOrder.setArea(area);
		dispensingDrugOrder.setBatch(batch);
		dispensingDrugOrder.setPharmacy((Pharmacy) user.getDept());
		dispensingDrugOrder.setState(ConfigureFluidOrder.State_NeedExecute);

		dispensingDrugOrder.save();

		applicationContext.publishEvent(new DispensingDrugOrderCreatedEvent(
				dispensingDrugOrder));

		LogUtil.log(this.getClass(), "人员[{}]创建了住院病区[{}]批次为[{}]的摆药单[{}]",
				user.getId(), area.getName(), batch.getName(),
				dispensingDrugOrder.getId());

		return dispensingDrugOrder;

	}

	/**
	 * @param dispensingDrugOrder
	 * @throws OrderExecuteException
	 * @roseuid 5930F4D20354
	 */
	public void dispenseOrder(DispensingDrugOrder dispensingDrugOrder,
			AbstractUser user) throws OrderExecuteException {
		for (DispensingDrugOrderExecute execute : dispensingDrugOrder
				.getExecutes()) {
			orderExecuteDomainService.finish(execute, null, user);
		}

		dispensingDrugOrder.finish(user);

		applicationContext.publishEvent(new DispensingDrugOrderFinishedEvent(
				dispensingDrugOrder));

		LogUtil.log(this.getClass(), "人员[{}]将摆药单[{}]设置为已完成", user.getId(),
				dispensingDrugOrder.getId());

	}

	public void distributeOrder(DispensingDrugOrder dispensingDrugOrder,
			AbstractUser user) throws OrderExecuteException {
		for (DispensingDrugOrderExecute execute : dispensingDrugOrder
				.getExecutes()) {
			orderExecuteDomainService.finish(execute.getNext(), null, user);
		}

		dispensingDrugOrder.distribute(user);

		applicationContext
				.publishEvent(new DispensingDrugOrderDistributedEvent(
						dispensingDrugOrder));

		LogUtil.log(this.getClass(), "人员[{}]将摆药单[{}]设置为已发出", user.getId(),
				dispensingDrugOrder.getId());
	}

	public DispensingDrugOrder getDispensingDrugOrder(String id) {
		return this.dispensingDrugOrderRepo.findOne(id);
	}

}
