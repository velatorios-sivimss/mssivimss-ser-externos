package com.imss.sivimss.serviciosexternos.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermisosDto {

	private Integer idPermiso;

	private String descPermiso;

}
