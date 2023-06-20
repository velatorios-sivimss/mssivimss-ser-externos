package com.imss.sivimss.serviciosexternos.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imss.sivimss.serviciosexternos.model.request.CorreoRequest;
import com.imss.sivimss.serviciosexternos.service.CatalogosService;
import com.imss.sivimss.serviciosexternos.service.PeticionesCorreoService;
import com.imss.sivimss.serviciosexternos.service.PeticionesService;
import com.imss.sivimss.serviciosexternos.utils.AppConstantes;
import com.imss.sivimss.serviciosexternos.utils.LogUtil;
import com.imss.sivimss.serviciosexternos.utils.ProviderServiceRestTemplate;
import com.imss.sivimss.serviciosexternos.utils.Response;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;


@RestController
@RequestMapping("/catalogos/externos") 
public class PeticionesController {

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
	@Qualifier("peticionesServiceCorreoImpl")
	private PeticionesCorreoService peticionesServiceCorreo;
	
	@Autowired
	@Qualifier("peticionesServiceNSSImpl")
	private PeticionesService peticionesServiceNSS;
	
	
	@Autowired
	private CatalogosService catalogoService;

	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;

	@Autowired
	private LogUtil logUtil;
	private static final String CONSULTA = "consulta";
	private static final String ENVIO_CORREO = "envio correo";
	private static final String CATALOGO_DELEGACION = " CatalogoDelegacion ";
	private static final String CATALOGO_NIVEL = " CatalogoNivel ";
	
	
	@GetMapping("/consultar/rfc/{dato}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> obtenerRfc(@PathVariable String dato, Authentication authentication)
			throws IOException {
		Response<Object> response = peticionesServiceRfc.consultarServicioExterno(dato, authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo().intValue())));
	}

	@GetMapping("/consultar/curp/{dato}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> obtenerCurp(@PathVariable String dato, Authentication authentication)
			throws IOException {
		Response<Object> response = peticionesServiceCurp.consultarServicioExterno(dato, authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo().intValue())));
	}

	@GetMapping("/consultar/codigo-postal/{dato}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> obtenerCodigoPostal(@PathVariable String dato, Authentication authentication)
			throws IOException {
		Response<Object> response = peticionesServiceCodigoPostal.consultarServicioExterno(dato, authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo().intValue())));
	}

	/**
	 * Obtiene Delegacion
	 *
	 * @return
	 */
	@GetMapping("/consultar/delegaciones")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackCatalogoDelegacion")
	@Retry(name = "msflujo", fallbackMethod = "fallbackCatalogoDelegacion")
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
		Response<Object> response= peticionesServiceSiap.consultarServicioExterno(dato, authentication);		
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo().intValue())));
	}

	@GetMapping("/consultar/nss/{dato}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> obtenerNSS(@PathVariable String dato, Authentication authentication) throws IOException {

		Response<Object> response = peticionesServiceNSS.consultarServicioExterno(dato, authentication);		
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo().intValue())));	
	}
	
	@PostMapping("/enviar/correo")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackCorreo")
	@Retry(name = "msflujo", fallbackMethod = "fallbackCorreo")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> enviarCorreo(@RequestBody CorreoRequest request,Authentication authentication) throws IOException {

		Response<Object> response = peticionesServiceCorreo.envioCorreoConToken(request, authentication);		
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo().intValue())));	
	}
	

	@PostMapping("/enviar/correo/recuperacontrasenia")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackCorreo")
	@Retry(name = "msflujo", fallbackMethod = "fallbackCorreo")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> enviarCorreoContrasenia(@RequestBody CorreoRequest request,Authentication authentication) throws IOException {

		Response<Object> response = peticionesServiceCorreo.envioCorreoSinToken(request);		
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo().intValue())));	
	}
	
	
	
	
	
	/**
	 * 
	 * fallbacks consulta
	 * 
	 * @return respuestas
	 * @throws IOException 
	 */

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackconsultaPaginada(
			@RequestParam(defaultValue = AppConstantes.NUMERO_DE_PAGINA) Integer pagina,
			@RequestParam(defaultValue = AppConstantes.TAMANIO_PAGINA) Integer tamanio,
			@RequestParam(required = true) String servicio, @PathVariable Integer idFuncionalidad,
			Authentication authentication, CallNotPermittedException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA + " " + idFuncionalidad,authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	/**
	 * fallbacks catalogo delegacion
	 * 
	 * @return respuestas
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackCatalogoDelegacion(Authentication authentication, CallNotPermittedException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA + CATALOGO_DELEGACION,authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackCatalogoDelegacion(Authentication authentication, NumberFormatException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA + CATALOGO_DELEGACION,authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackCatalogoDelegacion(Authentication authentication, RuntimeException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA + CATALOGO_DELEGACION,authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	/**
	 * fallbacks catalogo velacion
	 * 
	 * @return respuestas
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackCatalogoVelacion(@PathVariable Integer idDelegacion,
			Authentication authentication, CallNotPermittedException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA + " " + idDelegacion,authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackCatalogoVelacion(@PathVariable Integer idDelegacion,
			Authentication authentication, RuntimeException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA + " " + idDelegacion,authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackCatalogoVelacion(@PathVariable Integer idDelegacion,
			Authentication authentication, NumberFormatException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA + " " + idDelegacion,authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	/**
	 * fallbacks catalogo nivel
	 * 
	 * @return respuestas
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackCatalogoNivel(Authentication authentication, CallNotPermittedException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA + CATALOGO_NIVEL ,authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackCatalogoNivel(Authentication authentication, RuntimeException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA + CATALOGO_NIVEL,authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackCatalogoNivel(Authentication authentication, NumberFormatException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA + CATALOGO_NIVEL ,authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	/**
	 * fallbacks catalogo nivel
	 * 
	 * @return respuestas
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackGenerico(@PathVariable String dato, Authentication authentication,
			CallNotPermittedException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA + " " + dato,authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackGenerico(@PathVariable String dato, Authentication authentication,
			RuntimeException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA + " " + dato,authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackGenerico(@PathVariable String dato, Authentication authentication,
			NumberFormatException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA + " " + dato,authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	

	/**
	 * fallbacks correo
	 * 
	 * @return respuestas
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackCorreo(@RequestBody CorreoRequest request, Authentication authentication,
			CallNotPermittedException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),ENVIO_CORREO + " " + request.toString(),authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackCorreo(@RequestBody CorreoRequest request, Authentication authentication,
			RuntimeException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),ENVIO_CORREO + " " + request.toString(),authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackCorreo(@RequestBody CorreoRequest request, Authentication authentication,
			NumberFormatException e) throws IOException {
		Response<Object> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),ENVIO_CORREO + " " + request.toString(),authentication, null);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
}
