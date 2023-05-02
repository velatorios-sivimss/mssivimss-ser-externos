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
@Table(name = "SVC_TIPO_CORREO")
public class CorreoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TIPO_CORREO")
	private String idTipoCorreo;
	
	@Column(name = "ASUNTO")
	private String asunto;

	@Column(name = "CUERPO_CORREO")
	private String cuerpoCorreo;

}
