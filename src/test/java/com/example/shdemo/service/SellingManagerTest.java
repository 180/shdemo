package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Xero;
import com.example.shdemo.domain.Service;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class SellingManagerTest {

	@Autowired
	SellingManager serviceingManager;


	private final String MAKE1 = "Brother";
	private final String MAKE2 = "HP";
	private final String MODEL1 = "XR-1000";
	private final String MODEL2 = "SuperDuper";
	private final String TYPE1 = "Color";
	private final String TYPE2 = "Black-White";
	private final Integer SHEETS1 = 100;
	private final Integer SHEETS2 = 2;
	private final Integer SHEETS3 = 430;
	
	
	private final String NAME1 = "Xero Service";
	private final String NAME2 = "Zlota Raczka";
	private final String NAME3 = "Naprawa ABC";
	private final String PHONE1 = "501-501-501";
	private final String PHONE2 = "200-100-100";
	private final String PHONE3 = "808-300-100";
	
	@Test
	public void addServiceCheck() {

		List<Service> retrievedServices = serviceingManager.getAllServices();

		for (Service client : retrievedServices) {
			if (client.getPhone().equals(PHONE1)) {
				serviceingManager.deleteService(client);
			}
		}

		Service service = new Service();
		service.setName(NAME1);
		service.setPhone(PHONE1);

		serviceingManager.addService(service);

		Service retrievedService = serviceingManager.findServiceByPhone(PHONE1);

		assertEquals(NAME1, retrievedService.getName());
		assertEquals(PHONE1, retrievedService.getPhone());
	}

	@Test
	public void addXeroCheck() {

		Xero xero = new Xero();
		xero.setMake(MAKE1);
		xero.setModel(MODEL1);
		xero.setType(TYPE1);
		xero.setSheets(SHEETS1);

		Long xeroId = serviceingManager.addNewXero(xero);

		Xero retrievedXero = serviceingManager.findXeroById(xeroId);
		assertEquals(MAKE1, retrievedXero.getMake());
		assertEquals(MODEL1, retrievedXero.getModel());
		assertEquals(TYPE1, retrievedXero.getType());
		assertEquals(SHEETS1, retrievedXero.getSheets());

	}
	


	@Test
	public void serviceXeroCheck() {

		Service service = new Service();
		service.setName(NAME2);
		service.setPhone(PHONE2);

		serviceingManager.addService(service);

		Service retrievedService = serviceingManager.findServiceByPhone(PHONE2);

		Xero xero = new Xero();
		xero.setMake(MAKE2);
		xero.setModel(MODEL2);
		xero.setType(TYPE2);
		xero.setSheets(SHEETS2);

		Long xeroId = serviceingManager.addNewXero(xero);

		serviceingManager.serviceXero(retrievedService.getId(), xeroId);

		List<Xero> ownedXeros = serviceingManager.getOwnedXeros(retrievedService);

		assertEquals(1, ownedXeros.size());
		assertEquals(MAKE2, ownedXeros.get(0).getMake());
		assertEquals(MODEL2, ownedXeros.get(0).getModel());
		assertEquals(TYPE2, ownedXeros.get(0).getType());
		assertEquals(SHEETS2, ownedXeros.get(0).getSheets());
	}

	@Test
	public void disposeXeroCheck() {
		Service service = new Service();
		service.setName(NAME3);
		service.setPhone(PHONE3);

		serviceingManager.addService(service);
		
		Service retrievedService = serviceingManager.findServiceByPhone(PHONE3);
		
		Xero xero = new Xero();
		xero.setMake(MAKE2);
		xero.setModel(MODEL2);
		xero.setType(TYPE2);
		xero.setSheets(SHEETS2);
		
		Long xeroId = serviceingManager.addNewXero(xero);

		serviceingManager.serviceXero(retrievedService.getId(), xeroId);

		serviceingManager.disposeXero(retrievedService, xero);

		List<Xero> availableXeros = serviceingManager.getAvailableXeros();
		assertEquals(xero, availableXeros.get(1));

	}

}
