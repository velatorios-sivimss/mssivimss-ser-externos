package com.imss.sivimss.serviciosexternos.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MensajeResponseUtil {

	private static final Logger log = LoggerFactory.getLogger(MensajeResponseUtil.class);

	private MensajeResponseUtil() {
		super();
	}

	public static Response<?> mensajeResponseExterno(Response<?> respuestaGenerado, String numeroMensaje,
			String numeroMensajeError) {
		Integer codigo = respuestaGenerado.getCodigo();
		if (codigo == 400) {
			respuestaGenerado.setCodigo(200);
			respuestaGenerado.setMensaje(numeroMensaje);
		} else if (codigo == 404 || codigo == 500) {
			respuestaGenerado.setMensaje(numeroMensajeError);
		}
		return respuestaGenerado;
	}
}
