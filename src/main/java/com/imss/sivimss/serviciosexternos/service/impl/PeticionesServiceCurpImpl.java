package com.imss.sivimss.serviciosexternos.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.imss.sivimss.serviciosexternos.service.PeticionesService;
import com.imss.sivimss.serviciosexternos.utils.MensajeResponseUtil;
import com.imss.sivimss.serviciosexternos.utils.ProviderServiceRestTemplate;
import com.imss.sivimss.serviciosexternos.utils.Response;

 
@Service
public class PeticionesServiceCurpImpl implements PeticionesService {

	
	private static final String CURP_NO_VALIDO = "34"; // CURP no valido.
	private static final String SERVICIO_RENAPO_NO_DISPONIBLE = "184"; // El servicio de RENAPO no esta disponible.
	
	@Value("${endpoints.renapo}")
	private String urlRenapo;

	private ProviderServiceRestTemplate providerRestTemplate;

	public PeticionesServiceCurpImpl(ProviderServiceRestTemplate providerRestTemplate) {
		this.providerRestTemplate = providerRestTemplate;
	}

	@Override
	public Response<Object> consultarServicioExterno(Object dato, Authentication authentication)
			throws IOException {
		Response<Object>response=providerRestTemplate.consumirServicioExternoGet(urlRenapo+"/"+dato);
		return  MensajeResponseUtil.mensajeResponseExterno(response, CURP_NO_VALIDO, SERVICIO_RENAPO_NO_DISPONIBLE);
	}

}
