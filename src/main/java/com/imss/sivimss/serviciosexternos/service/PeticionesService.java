package com.imss.sivimss.serviciosexternos.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.serviciosexternos.utils.Response;

public interface PeticionesService {

	Response<?> consultarServicioExterno(Object dato, Authentication authentication) throws IOException;

}
