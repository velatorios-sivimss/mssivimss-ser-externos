package com.imss.sivimss.serviciosexternos.model.request;
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
public class CorreoRequest {

	private String correoPara;
	private String tipoCorreo;
	private String nombre;
	private String codigo;
	private String asunto;
	private String remitente;
	private String cuerpoCorreo;

}
