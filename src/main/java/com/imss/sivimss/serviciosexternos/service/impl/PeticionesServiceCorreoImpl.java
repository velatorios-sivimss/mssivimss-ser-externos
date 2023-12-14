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
	public Response<Object> envioCorreoConToken(CorreoRequest correoRequest, Authentication authentication)
			throws IOException {
		String correo = getBodyMail(correoRequest);
		Response<Object> response = providerRestTemplate.consumirServicioCorreo(correo, urlCorreo);
		return MensajeResponseUtil.mensajeResponseExterno(response, ERROR_ENVIO_CORREO,
				SERVICIO_CORREO_NO_DISPONIBLE);
	}

	@Override
	public Response<Object> envioCorreoSinToken(CorreoRequest correoRequest)
			throws IOException {
		String correo = getBodyMail(correoRequest);
		Response<Object> response = providerRestTemplate.consumirServicioCorreo(correo, urlCorreo);
		return MensajeResponseUtil.mensajeResponseExterno(response, ERROR_ENVIO_CORREO,
				SERVICIO_CORREO_NO_DISPONIBLE);
	}
	
	private String getBodyMail(CorreoRequest correoRequest) {
		Optional<CorreoResponse> correo = correoRepository.buscarCuerpoCorreo(correoRequest.getTipoCorreo());
		String cuerpoCorreo = "";
		String asunto = "";
		if(correo.isPresent()) {
			asunto = correo.get().getAsunto();
			cuerpoCorreo = correo.get().getCuerpoCorreo().replace("reemplazarNombre", correoRequest.getNombre());
			cuerpoCorreo = cuerpoCorreo.replace("reemplazarCodigo", correoRequest.getCodigo());
		}
		return "{\"correoPara\": [\"" + correoRequest.getCorreoPara()+ "\"],"
				+ "\"asunto\": \"" + asunto + "\","
				+ "\"remitente\": \"" + remitente + "\","
				+ " \"cuerpoCorreo\": \"" + cuerpoCorreo + "\"}";
	}

	@Override
	public Response<Object> envioCorreoArchivoAdjunto(CorreoRequest correoRequest)
			throws IOException {
		String correo = getBodyMailArchivoAdjunto(correoRequest);
		Response<Object> response = providerRestTemplate.consumirServicioCorreo(correo, urlCorreo + "/");
		return MensajeResponseUtil.mensajeResponseExterno(response, ERROR_ENVIO_CORREO,
				SERVICIO_CORREO_NO_DISPONIBLE);
	}

	private String getBodyMailArchivoAdjunto(CorreoRequest correoRequest) {
		Optional<CorreoResponse> correo = correoRepository.buscarCuerpoCorreo(correoRequest.getTipoCorreo());
		String cuerpoCorreo = "";
		String asunto = "";
		if(correo.isPresent()) {
			asunto = correo.get().getAsunto();
			cuerpoCorreo = correo.get().getCuerpoCorreo().replace("reemplazarNombre", correoRequest.getNombre());
		}

        StringBuilder sbuilder = new StringBuilder();
		for (int i = 0; i < correoRequest.getAdjuntos().size(); i++ ) {
			sbuilder.append(" \"nombreAdjunto\":");
			sbuilder.append("\"" + correoRequest.getAdjuntos().get(i).getNombreAdjunto() + "\", ");
			sbuilder.append(" \"adjuntoBase64\":");
			sbuilder.append("\"" + correoRequest.getAdjuntos().get(i).getAdjuntoBase64() + "\"");
			if (i < correoRequest.getAdjuntos().size()-1)
				sbuilder.append("},{\n");
			}
			
		return "{ \"correoPara\": [\"" + correoRequest.getCorreoPara()+ "\"],"
				+ " \"asunto\": \"" + asunto + "\", "
				+ " \"cuerpoCorreo\": \"" + cuerpoCorreo + "\", "
				+ " \"remitente\": \"" + remitente + "\", "
				+ " \"adjuntos\": [ "
				+ "{ " + sbuilder.toString() + " }"
				+ " ] }";
	}
}
