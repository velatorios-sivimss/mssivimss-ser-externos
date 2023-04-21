package com.imss.sivimss.serviciosexternos.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.imss.sivimss.serviciosexternos.model.dto.DelegacionResponse;
import com.imss.sivimss.serviciosexternos.model.dto.NivelesResponse;
import com.imss.sivimss.serviciosexternos.model.dto.VelatorioResponse;
import com.imss.sivimss.serviciosexternos.repository.DelegacionRepository;
import com.imss.sivimss.serviciosexternos.repository.NivelRepository;
import com.imss.sivimss.serviciosexternos.repository.VelatorioRepository;
import com.imss.sivimss.serviciosexternos.service.CatalogosService;
import com.imss.sivimss.serviciosexternos.utils.AppConstantes;
import com.imss.sivimss.serviciosexternos.utils.Response;

@Service
public class CatalogosServiceImpl implements CatalogosService {

	@Autowired
	private DelegacionRepository catalogosRepository;
	@Autowired
	private VelatorioRepository velatorioRepository;
	@Autowired
	private NivelRepository nivelRepository;

	@Override
	@Cacheable("catalogoDelegaciones")
	public Response<List<DelegacionResponse>> buscarDelegaciones(Authentication authentication) throws IOException {

		Optional<List<DelegacionResponse>> delegaciones = catalogosRepository.buscarDelegacion();

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,
				delegaciones.orElse(Collections.emptyList()));
	}

	@Override
	@Cacheable("catalogoVeltario")
	public Response<List<VelatorioResponse>> buscarVelatorio(Integer idDelegacion, Authentication authentication)
			throws IOException {

		Optional<List<VelatorioResponse>> velatorios = velatorioRepository.buscarVelatorio(idDelegacion);

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,
				velatorios.orElse(Collections.emptyList()));
	}

	@Override
	@Cacheable("catalogoNivel")
	public Response<List<NivelesResponse>> buscarNiveles(Authentication authentication) throws IOException {
		Optional<List<NivelesResponse>> niveles = nivelRepository.obtenerNiveles();
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,
				niveles.orElse(Collections.emptyList()));
	}

}
