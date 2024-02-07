package com.imss.sivimss.serviciosexternos.service.impl;

import java.io.IOException;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.imss.sivimss.serviciosexternos.beans.ReportePromotor;
import com.imss.sivimss.serviciosexternos.service.PeticionesService;
import com.imss.sivimss.serviciosexternos.utils.MensajeResponseUtil;
import com.imss.sivimss.serviciosexternos.utils.ProviderServiceRestTemplate;
import com.imss.sivimss.serviciosexternos.utils.Response;

import ch.qos.logback.classic.Logger;


@Service
public class PeticionesServiceCodigoPostalImpl implements PeticionesService {
	

	private static final String CODIGO_POSTAL_NO_EXISTE = "185"; // El codigo postal no existe.
	private static final String SERVICIO_SEPOMEX_NO_DISPONIBLE = "181"; // El servicio de SEPOMEX no esta disponible.
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReportePromotor.class);


	@Value("${endpoints.sepomex}")
	private String urlSepomex;
	
	private ProviderServiceRestTemplate providerRestTemplate;

	public PeticionesServiceCodigoPostalImpl(ProviderServiceRestTemplate providerRestTemplate) {
		this.providerRestTemplate = providerRestTemplate;
	}


	@Override
	public Response<Object> consultarServicioExterno(Object dato, Authentication authentication)
			throws IOException {
		String peticionSepomex = urlSepomex+"/"+dato;
		log.info(peticionSepomex);
		Response<Object>response=providerRestTemplate.consumirServicioExternoGet(peticionSepomex);
		return MensajeResponseUtil.mensajeResponseExterno(response, CODIGO_POSTAL_NO_EXISTE, SERVICIO_SEPOMEX_NO_DISPONIBLE	);
	}


}
