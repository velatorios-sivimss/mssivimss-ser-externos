package com.imss.sivimss.serviciosexternos.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.serviciosexternos.model.request.CorreoRequest;
import com.imss.sivimss.serviciosexternos.utils.Response;

public interface PeticionesCorreoService {

	Response<Object> envioCorreoConToken(CorreoRequest dato, Authentication authentication) throws IOException;
	Response<Object> envioCorreoSinToken(CorreoRequest dato) throws IOException;
}
