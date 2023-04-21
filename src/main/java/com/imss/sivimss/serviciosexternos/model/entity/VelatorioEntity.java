package com.imss.sivimss.serviciosexternos.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "SVC_VELATORIO")
public class VelatorioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_VELATORIO")
	private Integer idVelatorio;

	@Column(name = "NOM_VELATORIO")
	private String nomVelatorio;

}
