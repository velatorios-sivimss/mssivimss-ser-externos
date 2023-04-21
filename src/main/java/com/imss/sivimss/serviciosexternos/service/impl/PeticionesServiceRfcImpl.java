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
public class PeticionesServiceRfcImpl implements PeticionesService {


	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PeticionesServiceRfcImpl.class);
	
	@Value("${endpoints.sat}")
	private String urlSat;

	private ProviderServiceRestTemplate providerRestTemplate;

	public PeticionesServiceRfcImpl(ProviderServiceRestTemplate providerRestTemplate) {
		this.providerRestTemplate = providerRestTemplate;
	}

	@Override
	public Response<?> consultarServicioExterno(Object dato, Authentication authentication)
			throws IOException {
		Response<?> response = providerRestTemplate.consumirServicioExternoGet(urlSat + "/" + dato);

		return MensajeResponseUtil.mensajeResponseExterno(response, "33", "182");
	}

}
