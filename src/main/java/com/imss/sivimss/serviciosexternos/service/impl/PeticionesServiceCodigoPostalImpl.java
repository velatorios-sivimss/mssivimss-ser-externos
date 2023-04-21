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
public class PeticionesServiceCodigoPostalImpl implements PeticionesService {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PeticionesServiceCodigoPostalImpl.class);
	

	@Value("${endpoints.sepomex}")
	private String urlSepomex;

	private ProviderServiceRestTemplate providerRestTemplate;

	public PeticionesServiceCodigoPostalImpl(ProviderServiceRestTemplate providerRestTemplate) {
		this.providerRestTemplate = providerRestTemplate;
	}

	@Override
	public Response<?> consultarServicioExterno(Object dato, Authentication authentication)
			throws IOException {
		Response<?> response = providerRestTemplate.consumirServicioExternoGet(urlSepomex + "/" + dato);
		return MensajeResponseUtil.mensajeResponseExterno(response, "185", "181");
	}

}
