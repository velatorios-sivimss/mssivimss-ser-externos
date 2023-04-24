package com.imss.sivimss.serviciosexternos.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imss.sivimss.serviciosexternos.service.CatalogosService;
import com.imss.sivimss.serviciosexternos.service.PeticionesService;
import com.imss.sivimss.serviciosexternos.utils.AppConstantes;
import com.imss.sivimss.serviciosexternos.utils.ProviderServiceRestTemplate;
import com.imss.sivimss.serviciosexternos.utils.Response;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
@RequestMapping("/catalogos/externos") 
public class PeticionesController {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PeticionesController.class);
	@Autowired
	@Qualifier("peticionesServiceRfcImpl")
	private PeticionesService peticionesServiceRfc;

	@Autowired
	@Qualifier("peticionesServiceCurpImpl")
	private PeticionesService peticionesServiceCurp;

	@Autowired
	@Qualifier("peticionesServiceCodigoPostalImpl")
	private PeticionesService peticionesServiceCodigoPostal;

	@Autowired
	@Qualifier("peticionesServiceSiapImpl")
	private PeticionesService peticionesServiceSiap;
	

	@Autowired
	private CatalogosService catalogoService;

	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;

	@GetMapping("/consultar/rfc/{dato}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> obtenerRfc(@PathVariable String dato, Authentication authentication)
			throws IOException {
		Response<?> response = peticionesServiceRfc.consultarServicioExterno(dato, authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo().intValue())));
	}

	@GetMapping("/consultar/curp/{dato}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> obtenerCurp(@PathVariable String dato, Authentication authentication)
			throws IOException {
		Response<?> response = peticionesServiceCurp.consultarServicioExterno(dato, authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo().intValue())));
	}

	@GetMapping("/consultar/codigo-postal/{dato}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> obtenerCodigoPostal(@PathVariable String dato, Authentication authentication)
			throws IOException {
		Response<?> response = peticionesServiceCodigoPostal.consultarServicioExterno(dato, authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo().intValue())));
	}

	/**
	 * Obtiene Delegacion
	 *
	 * @return
	 */
	@GetMapping("/consultar/delegaciones")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackCatalogo")
	@Retry(name = "msflujo", fallbackMethod = "fallbackCatalogo")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> obtenerDelegacion(Authentication authentication) throws IOException {

		Response<?> response = catalogoService.buscarDelegaciones(authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo().intValue())));
	}

	/**
	 * Obtiene Velatorio
	 *
	 * @param idDelegacion
	 * @return
	 */
	@GetMapping("/consultar/velatorios/{idDelegacion}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackCatalogoVelacion")
	@Retry(name = "msflujo", fallbackMethod = "fallbackCatalogoVelacion")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> obtenerVelatorio(@PathVariable Integer idDelegacion, Authentication authentication)
			throws IOException {

		Response<?> response = catalogoService.buscarVelatorio(idDelegacion, authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo().intValue())));
	}

	/**
	 * Obtiene niveles
	 *
	 * @return
	 */
	@GetMapping("/consultar/nivel")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackCatalogoNivel")
	@Retry(name = "msflujo", fallbackMethod = "fallbackCatalogoNivel")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> obtenerNivel(Authentication authentication)
			throws IOException {

		Response<?> response = catalogoService.buscarNiveles(authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo().intValue())));
	}

	@GetMapping("/consultar/siap/{dato}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object>obtenerSiap(@PathVariable String dato,Authentication authentication) throws IOException{
		Response<?> response= peticionesServiceSiap.consultarServicioExterno(dato, authentication);		
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo().intValue())));
	}
	
	/**
	 * 
	 * fallbacks consulta
	 * 
	 * @return respuestas
	 */

	private CompletableFuture<Object> fallbackconsultaPaginada(
			@RequestParam(defaultValue = AppConstantes.NUMERO_DE_PAGINA) Integer pagina,
			@RequestParam(defaultValue = AppConstantes.TAMANIO_PAGINA) Integer tamanio,
			@RequestParam(required = true) String servicio, @PathVariable Integer idFuncionalidad,
			Authentication authentication, CallNotPermittedException e) {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	/**
	 * fallbacks catalogo delegacion
	 * 
	 * @return respuestas
	 */
	private CompletableFuture<?> fallbackCatalogo(Authentication authentication, CallNotPermittedException e) {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	private CompletableFuture<?> fallbackCatalogo(Authentication authentication, NumberFormatException e) {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	private CompletableFuture<?> fallbackCatalogo(Authentication authentication, RuntimeException e) {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	/**
	 * fallbacks catalogo velacion
	 * 
	 * @return respuestas
	 */
	private CompletableFuture<?> fallbackCatalogoVelacion(@PathVariable Integer idDelegacion,
			Authentication authentication, CallNotPermittedException e) {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	private CompletableFuture<?> fallbackCatalogoVelacion(@PathVariable Integer idDelegacion,
			Authentication authentication, RuntimeException e) {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	private CompletableFuture<?> fallbackCatalogoVelacion(@PathVariable Integer idDelegacion,
			Authentication authentication, NumberFormatException e) {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	/**
	 * fallbacks catalogo nivel
	 * 
	 * @return respuestas
	 */
	private CompletableFuture<?> fallbackCatalogoNivel(Authentication authentication, CallNotPermittedException e) {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	private CompletableFuture<?> fallbackCatalogoNivel(Authentication authentication, RuntimeException e) {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	private CompletableFuture<?> fallbackCatalogoNivel(Authentication authentication, NumberFormatException e) {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	/**
	 * fallbacks catalogo nivel
	 * 
	 * @return respuestas
	 */
	private CompletableFuture<?> fallbackGenerico(@PathVariable String dato, Authentication authentication,
			CallNotPermittedException e) {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	private CompletableFuture<?> fallbackGenerico(@PathVariable String dato, Authentication authentication,
			RuntimeException e) {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	private CompletableFuture<?> fallbackGenerico(@PathVariable String dato, Authentication authentication,
			NumberFormatException e) {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
}
