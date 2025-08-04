package com.AOA.model;

import java.math.BigDecimal;

import com.AOA.enums.CarStatusType;
import com.AOA.enums.CurrencyType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "car")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car extends BaseEntity{
	

	@Column(name = "plate")
	private String plate;

	@Column(name = "brand")	
	private String brand;

	@Column(name = "model")
	private String model;

	@Column(name = "production_year")	
	private Integer productionYear;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "currency_type")
	@Enumerated(EnumType.STRING)
	private CurrencyType currencyType;

	@Column(name = "damage_price")
	private BigDecimal damagePrice;

	@Column(name = "car_status_type")
	@Enumerated(EnumType.STRING)
	private CarStatusType carStatusType;
}
