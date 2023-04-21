package com.imss.sivimss.serviciosexternos.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "SVC_FUNCIONALIDAD_SERVICIO")
public class FuncionalidadServiciosEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SERVICIO")
	private Integer idServicio;

	@Column(name = "NOM_SERVICIO")
	private String nombre;

	@JsonBackReference
	@Basic
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PERMISO", referencedColumnName = "ID_PERMISO")
	private PermisosEntity permisos;

	@Column(name = "DES_URL")
	private String url;

	@Column(name = "CVE_ESTATUS")
	private Integer estatus;

	@JsonBackReference
	@Basic
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FUNCIONALIDAD", referencedColumnName = "ID_FUNCIONALIDAD")
	private FuncionalidadEntity funcionalidad;
}
