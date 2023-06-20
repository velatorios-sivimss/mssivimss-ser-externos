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
public class PeticionesServiceNSSImpl implements PeticionesService {
	

	private static final String NUMERO_SEGURIDAD_SOCIAL_NO_EXISTE = "188"; // El Numero de Seguridad Social no existe.
	private static final String SERVICIO_NO_DISPONIBLE = "189"; // El servicio de Consulta de NSS no esta disponible.
	
	

	@Value("${endpoints.nss}")
	private String urlnss;
	
	private ProviderServiceRestTemplate providerRestTemplate;

	public PeticionesServiceNSSImpl(ProviderServiceRestTemplate providerRestTemplate) {
		this.providerRestTemplate = providerRestTemplate;
	}


	@Override
	public Response<Object> consultarServicioExterno(Object dato, Authentication authentication)
			throws IOException {
		Response<Object>response=providerRestTemplate.consumirServicioExternoGet(urlnss+"/"+dato);
		return MensajeResponseUtil.mensajeResponseExterno(response, NUMERO_SEGURIDAD_SOCIAL_NO_EXISTE, SERVICIO_NO_DISPONIBLE	);
	}


}
