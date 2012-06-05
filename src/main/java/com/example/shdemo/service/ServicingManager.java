package com.example.shdemo.service;

import java.util.List;

import com.example.shdemo.domain.Xero;
import com.example.shdemo.domain.Service;

public interface ServicingManager {
	
	void addService(Service service);
	List<Service> getAllServices();
	void deleteService(Service service);
	Service findServiceByPhone(String phone);
	
	
	Long addNewXero(Xero xero);
	void deleteXero(Xero xero);
	List<Xero> getAvailableXeros();
	void disposeXero(Service service, Xero xero);
	Xero findXeroById(Long id);
	void updateXero(Xero x);
	Xero needService(int sheets);

	List<Xero> getOwnedXeros(Service service);
	void serviceXero(Long serviceId, Long xeroId);


}
