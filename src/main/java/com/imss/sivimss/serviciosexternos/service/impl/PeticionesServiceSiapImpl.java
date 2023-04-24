package com.imss.sivimss.serviciosexternos.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.imss.sivimss.serviciosexternos.service.PeticionesService;
import com.imss.sivimss.serviciosexternos.utils.MensajeResponseUtil;
import com.imss.sivimss.serviciosexternos.utils.ProviderServiceRestTemplate;
import com.imss.sivimss.serviciosexternos.utils.Response;


@Service
public class PeticionesServiceSiapImpl implements PeticionesService{
	
	private static final String NO_HAY_REGISTRO_SIAP = "79"; // No hay registro en SIAP.
	private static final String SERVICIO_SEPOMEX_NO_DISPONIBLE = "178"; // El servicio de SIAP no esta disponible.

	@Value("${endpoints.siap}")
	private String urlSiap;
	
	private ProviderServiceRestTemplate providerRestTemplate;

	public PeticionesServiceSiapImpl(ProviderServiceRestTemplate providerRestTemplate) {
		this.providerRestTemplate = providerRestTemplate;
	}


	@Override
	public Response<?> consultarServicioExterno(Object dato, Authentication authentication)
			throws IOException {
		Response<?>response=providerRestTemplate.consumirServicioExternoGet(urlSiap+"/"+dato);
		return MensajeResponseUtil.mensajeResponseExterno(response, NO_HAY_REGISTRO_SIAP, SERVICIO_SEPOMEX_NO_DISPONIBLE);
	}


}
