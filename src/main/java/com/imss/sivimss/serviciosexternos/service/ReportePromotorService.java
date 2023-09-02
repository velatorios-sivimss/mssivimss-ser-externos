package com.imss.sivimss.serviciosexternos.service;


import com.imss.sivimss.serviciosexternos.utils.Response;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.sql.SQLException;

public interface ReportePromotorService {
    Response<?> buscarReportes(Integer tipoReporte, Authentication authentication) throws  SQLException;
}
