package com.imss.sivimss.serviciosexternos.service;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.serviciosexternos.model.dto.DelegacionResponse;
import com.imss.sivimss.serviciosexternos.model.dto.NivelesResponse;
import com.imss.sivimss.serviciosexternos.model.dto.VelatorioResponse;
import com.imss.sivimss.serviciosexternos.utils.Response;

public interface CatalogosService {

	Response<List<DelegacionResponse>> buscarDelegaciones(Authentication authentication) throws IOException;

	Response<List<VelatorioResponse>> buscarVelatorio(Integer idVelatorio, Authentication authentication)
			throws IOException;

	Response<List<NivelesResponse>> buscarNiveles(Authentication authentication) throws IOException;

}
