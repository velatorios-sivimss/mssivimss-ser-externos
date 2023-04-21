package com.imss.sivimss.serviciosexternos.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({ "idDelegacion", "desDelegacion" })
public interface DelegacionResponse {

	Integer getIdDelegacion();

	String getDesDelegacion();

}
