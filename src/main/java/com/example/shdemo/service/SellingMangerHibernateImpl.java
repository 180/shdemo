package com.example.shdemo.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Xero;
import com.example.shdemo.domain.Service;

@Component
@Transactional
public class SellingMangerHibernateImpl implements SellingManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addService(Service service) {
		service.setId(null);
		sessionFactory.getCurrentSession().persist(service);
	}
	
	@Override
	public void deleteService(Service service) {
		service = (Service) sessionFactory.getCurrentSession().get(Service.class,
				service.getId());
		
		// lazy loading here
		for (Xero xero : service.getXeros()) {
			xero.setServiced(false);
			sessionFactory.getCurrentSession().update(xero);
		}
		sessionFactory.getCurrentSession().delete(service);
	}
	
	@Override
	public void deleteXero(Xero xero) {
		xero = (Xero) sessionFactory.getCurrentSession().get(Xero.class,
				xero.getId());
		
		
		sessionFactory.getCurrentSession().delete(xero);
	}
	
	
	
	@Override
	public List<Xero> getOwnedXeros(Service service) {
		service = (Service) sessionFactory.getCurrentSession().get(Service.class,
				service.getId());
		// lazy loading here - try this code without (shallow) copying
		List<Xero> xeros = new ArrayList<Xero>(service.getXeros());
		return xeros;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Service> getAllServices() {
		return sessionFactory.getCurrentSession().getNamedQuery("service.all")
				.list();
	}

	@Override
	public Service findServiceByPhone(String phone) {
		return (Service) sessionFactory.getCurrentSession().getNamedQuery("service.byPhone").setString("phone", phone).uniqueResult();
	}


	@Override
	public Long addNewXero(Xero xero) {
		xero.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(xero);
	}

	@Override
	public void serviceXero(Long serviceId, Long xeroId) {
		Service service = (Service) sessionFactory.getCurrentSession().get(
				Service.class, serviceId);
		Xero xero = (Xero) sessionFactory.getCurrentSession()
				.get(Xero.class, xeroId);
		xero.setServiced(true);
		service.getXeros().add(xero);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Xero> getAvailableXeros() {
		return sessionFactory.getCurrentSession().getNamedQuery("xero.unserviced")
				.list();
	}
	@Override
	public void disposeXero(Service service, Xero xero) {

		service = (Service) sessionFactory.getCurrentSession().get(Service.class,
				service.getId());
		xero = (Xero) sessionFactory.getCurrentSession().get(Xero.class,
				xero.getId());

		Xero toRemove = null;
		for (Xero aXero : service.getXeros())
			if (aXero.getId().compareTo(xero.getId()) == 0) {
				toRemove = aXero;
				break;
			}

		if (toRemove != null)
			service.getXeros().remove(toRemove);

		xero.setServiced(false);
	}

	@Override
	public Xero findXeroById(Long id) {
		return (Xero) sessionFactory.getCurrentSession().get(Xero.class, id);
	}

}
