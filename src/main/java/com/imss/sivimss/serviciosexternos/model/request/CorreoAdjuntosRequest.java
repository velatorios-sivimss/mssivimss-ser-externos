package com.imss.sivimss.serviciosexternos.model.request;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@JsonIgnoreType(value = true)
public class CorreoAdjuntosRequest {

	private ArrayList<Object> adjuntos;
	private String nombreAdjunto;
	private String adjuntoBase64;
}
