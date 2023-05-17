package com.imss.sivimss.serviciosexternos.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.serviciosexternos.model.request.CorreoRequest;
import com.imss.sivimss.serviciosexternos.utils.DatosRequest;
import com.imss.sivimss.serviciosexternos.utils.Response;

public interface PeticionesPostService {

	Response<Object> consultarServicioExterno(DatosRequest dato, Authentication authentication) throws IOException;
	Response<Object> consultarServicioExterno(CorreoRequest dato, Authentication authentication) throws IOException;
}
