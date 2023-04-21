package com.imss.sivimss.serviciosexternos.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "SVC_ROL_FUNCIONALIDAD_PERMISO")
public class RolFuncionalidadPermisoEntity {

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "ID_ROL")
	private Integer rol;

	@Column(name = "ID_FUNCIONALIDAD")
	private Integer funcionalidad;

	@Column(name = "ID_PERMISO")
	private Integer permisos;

	@Column(name = "CVE_ESTATUS")
	private Integer estatus;

}
