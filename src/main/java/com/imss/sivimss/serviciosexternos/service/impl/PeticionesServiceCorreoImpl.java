package com.imss.sivimss.serviciosexternos.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.imss.sivimss.serviciosexternos.model.dto.CorreoResponse;
import com.imss.sivimss.serviciosexternos.model.request.CorreoRequest;
import com.imss.sivimss.serviciosexternos.repository.CorreoRepository;
import com.imss.sivimss.serviciosexternos.service.PeticionesCorreoService;
import com.imss.sivimss.serviciosexternos.utils.MensajeResponseUtil;
import com.imss.sivimss.serviciosexternos.utils.ProviderServiceRestTemplate;
import com.imss.sivimss.serviciosexternos.utils.Response;

@Service
public class PeticionesServiceCorreoImpl implements PeticionesCorreoService {

	private static final String ERROR_ENVIO_CORREO = "173"; // Error en el envío del correo electrónico.Intenta nuevamente..
	private static final String SERVICIO_CORREO_NO_DISPONIBLE = "178"; // El servicio de Correo no esta disponible.

	@Value("${endpoints.correo}")
	private String urlCorreo;

	@Value("${correo.remitente}")
	private String remitente;
	
	
	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;

	@Autowired
	private CorreoRepository correoRepository;

	@Override
	public Response<?> consultarServicioExternoCorreo(CorreoRequest correoRequest, Authentication authentication)
			throws IOException {
		getBodyMail(correoRequest);		
		Response<Object> response = providerRestTemplate.consumirServicioCorreo(correoRequest, urlCorreo,
				authentication);
		return MensajeResponseUtil.mensajeResponseExterno(response, ERROR_ENVIO_CORREO,
				SERVICIO_CORREO_NO_DISPONIBLE);
	}
	
	
	private void getBodyMail(CorreoRequest correoRequest) {
		Optional<CorreoResponse> correo = correoRepository.buscarCuerpoCorreo(correoRequest.getTipoCorreo());
		correoRequest.setAsunto(correo.get().getAsunto());
		String cuerpoCorreo = correo.get().getCuerpoCorreo().replaceAll("reemplazarNombre", correoRequest.getNombre());
			   cuerpoCorreo = cuerpoCorreo.replaceAll("reemplazarCodigo", correoRequest.getCodigo());
		correoRequest.setCuerpoCorreo(cuerpoCorreo);
		correoRequest.setRemitente(remitente);
	}

}
