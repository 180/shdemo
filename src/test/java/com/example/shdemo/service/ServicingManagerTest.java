package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
public class ServicingManagerTest {

	@Autowired
	ServicingManager servicingManager;


	private final String MAKE1 = "Brother";
	private final String MAKE2 = "HP";
	private final String MODEL1 = "XR-1000";
	private final String MODEL2 = "SuperDuper";
	private final String TYPE1 = "Color";
	private final String TYPE2 = "Black-White";
	private final Integer SHEETS1 = 100;
	private final Integer SHEETS2 = 2;
	
	
	private final String NAME1 = "Xero Service";
	private final String NAME2 = "Zlota Raczka";
	private final String NAME3 = "Naprawa ABC";
	private final String PHONE1 = "501-501-501";
	private final String PHONE2 = "200-100-100";
	private final String PHONE3 = "808-300-100";
	private final String PHONE4 = "818-301-111";
	
	@Test
	public void addServiceCheck() {

		List<Service> retrievedServices = servicingManager.getAllServices();

		for (Service sv : retrievedServices) {
			if (sv.getPhone().equals(PHONE1)) {
				servicingManager.deleteService(sv);
			}
		}

		Service service = new Service();
		service.setName(NAME1);
		service.setPhone(PHONE1);

		servicingManager.addService(service);

		Service retrievedService = servicingManager.findServiceByPhone(PHONE1);

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

		Long xeroId = servicingManager.addNewXero(xero);

		Xero retrievedXero = servicingManager.findXeroById(xeroId);
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

		servicingManager.addService(service);

		Service retrievedService = servicingManager.findServiceByPhone(PHONE2);

		Xero xero = new Xero();
		xero.setMake(MAKE2);
		xero.setModel(MODEL2);
		xero.setType(TYPE2);
		xero.setSheets(SHEETS2);

		Long xeroId = servicingManager.addNewXero(xero);

		servicingManager.serviceXero(retrievedService.getId(), xeroId);

		List<Xero> ownedXeros = servicingManager.getOwnedXeros(retrievedService);

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

		servicingManager.addService(service);
		
		Service retrievedService = servicingManager.findServiceByPhone(PHONE3);
		
		Xero xero = new Xero();
		xero.setMake(MAKE2);
		xero.setModel(MODEL2);
		xero.setType(TYPE2);
		xero.setSheets(SHEETS2);
		
		Long xeroId = servicingManager.addNewXero(xero);

		servicingManager.serviceXero(retrievedService.getId(), xeroId);

		servicingManager.disposeXero(retrievedService, xero);

		List<Xero> availableXeros = servicingManager.getAvailableXeros();
		assertEquals(xero, availableXeros.get(1));

	}
	

	@Test
	public void checkUpdate() {
		Xero x1 = new Xero();
		x1.setMake(MAKE1);
		x1.setModel(MODEL1);
		x1.setType(TYPE1);
		x1.setSheets(SHEETS1);
		
		Long xeroId = servicingManager.addNewXero(x1);
		
		
		x1.setMake(MAKE2);
		x1.setModel(MODEL2);
		x1.setType(TYPE2);
		x1.setSheets(SHEETS2);
		servicingManager.updateXero(x1);
		
		
		Xero retrievedXero = servicingManager.findXeroById(xeroId);
		assertEquals(MAKE2, retrievedXero.getMake());
		assertEquals(MODEL2, retrievedXero.getModel());
		assertEquals(TYPE2, retrievedXero.getType());
		assertEquals(SHEETS2, retrievedXero.getSheets());
		
	}
	
	
	@Test
	public void checkDelete() {
		Xero x1 = new Xero();
		x1.setMake(MAKE1);
		x1.setModel(MODEL1);
		x1.setType(TYPE1);
		x1.setSheets(SHEETS1);
		
		Long xeroId = servicingManager.addNewXero(x1);
		
		servicingManager.deleteXero(x1);
		
		assertNull(servicingManager.findXeroById(xeroId));	
		
	}

}
