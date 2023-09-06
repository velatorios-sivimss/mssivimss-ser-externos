package com.imss.sivimss.serviciosexternos.controller;


import com.imss.sivimss.serviciosexternos.service.ReportePromotorService;
import com.imss.sivimss.serviciosexternos.utils.DatosRequest;
import com.imss.sivimss.serviciosexternos.utils.ProviderServiceRestTemplate;
import com.imss.sivimss.serviciosexternos.utils.Response;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/reportes")
public class ReportePromotorController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReportePromotorController.class);
    @Autowired
    private ReportePromotorService servicio;
    @Autowired
    private ProviderServiceRestTemplate providerRestTemplate;

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @GetMapping("/buscar-reportes/{tipoReporte}")
    public CompletableFuture<?> buscarReportes(@PathVariable Integer tipoReporte, Authentication authentication) throws  SQLException {
        Response<?> response = servicio.buscarReportes(tipoReporte, authentication);
        return CompletableFuture
                .supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
    }


    /**
     * fallbacks generico
     *
     * @return respuestas
     */
    private CompletableFuture<?> fallbackGenerico(@PathVariable Integer tipoReporte, Authentication authentication,
                                                  CallNotPermittedException e) throws IOException {
        Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
        return CompletableFuture
                .supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
    }

    private CompletableFuture<?> fallbackGenerico(@PathVariable Integer tipoReporte, Authentication authentication,
                                                  RuntimeException e) throws IOException {
        Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
        return CompletableFuture
                .supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
    }

    private CompletableFuture<?> fallbackGenerico(@PathVariable Integer tipoReporte, Authentication authentication,
                                                  NumberFormatException e) throws IOException {
        Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
        return CompletableFuture
                .supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
    }
}
