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


	
	private static final String RFC_NO_VALIDO = "33"; // R.F.C. no valido.
	private static final String SERVICIO_SAT_NO_DISPONIBLE = "182"; // El servicio de SAT no esta disponible.
	
	@Value("${endpoints.sat}")
	private String urlSat;

	private ProviderServiceRestTemplate providerRestTemplate;

	public PeticionesServiceRfcImpl(ProviderServiceRestTemplate providerRestTemplate) {
		this.providerRestTemplate = providerRestTemplate;
	}

	@Override
	public Response<Object> consultarServicioExterno(Object dato, Authentication authentication)
			throws IOException {
		Response<Object>response=providerRestTemplate.consumirServicioExternoGet(urlSat+"/"+dato);
		
		return MensajeResponseUtil.mensajeResponseExterno(response, RFC_NO_VALIDO, SERVICIO_SAT_NO_DISPONIBLE);
	}


}
