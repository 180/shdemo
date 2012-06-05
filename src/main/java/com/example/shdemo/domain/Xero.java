package com.example.shdemo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "xero.unserviced", query = "SELECT x FROM Xero x WHERE x.serviced = false"),
		@NamedQuery(name = "xero.needService", query = "SELECT x FROM Xero x WHERE x.sheets < :sheets")
		
//		@NamedQueries(  {
//			//wszystkie osoby
//		@NamedQuery(name = "osoba.getAll", query = "from Osoba"),
//
//			// Osoby z danym imieniem
//		@NamedQuery(name = "osoba.poImieniu", query = "from Osoba o where o.imie=:"imie"),
//
//			//Osoby ktore posiadaja jakies samochody
//		@NamedQuery(name = "osoba.zmotoryzowani", query = "select o from Osoba o JOIN o.samochody s"),
//
//			// Osoby ktore posiadaja samochody o danej marce (make (pol. marka) jest parametrem)
//		@NamedQuery(name = "osoba.marka", query = "select o from Osoba o JOIN o.samochody s WHERE s.marka=:make"),
//		})
})
public class Xero {

	private Long id;
	private String make;
	private String model;
	private String type;
	private Integer sheets;
	private Boolean serviced = false;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Boolean getServiced() {
		return serviced;
	}

	public void setServiced(Boolean serviced) {
		this.serviced = serviced;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSheets() {
		return sheets;
	}

	public void setSheets(Integer sheets) {
		this.sheets = sheets;
	}
}
