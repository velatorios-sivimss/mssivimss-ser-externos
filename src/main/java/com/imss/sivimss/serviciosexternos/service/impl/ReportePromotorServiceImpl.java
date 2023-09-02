package com.imss.sivimss.serviciosexternos.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.imss.sivimss.serviciosexternos.beans.ReportePromotor;
import com.imss.sivimss.serviciosexternos.model.InformacionReportesModel;
import com.imss.sivimss.serviciosexternos.model.response.ReportePromotorResponse;
import com.imss.sivimss.serviciosexternos.service.ReportePromotorService;
import com.imss.sivimss.serviciosexternos.utils.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportePromotorServiceImpl implements ReportePromotorService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReportePromotorServiceImpl.class);

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private Database database;

    private ResultSet rs;

    private Connection connection;

    private Statement statement;
    ReportePromotor reporte = new ReportePromotor();


    @Override
    public Response<?> buscarReportes(Integer tipoReporte, Authentication authentication) throws SQLException {
        Response<?> response = new Response<>();
        connection = database.getConnection();
        connection.setAutoCommit(false);
        statement = connection.createStatement();
        List<InformacionReportesModel> infoReportes = new ArrayList<>();
        rs = statement.executeQuery(reporte.buscarReportes(tipoReporte.toString()));
        while (rs.next()) {
            infoReportes.add(new InformacionReportesModel(rs.getInt(1), rs.getString(2)));
        }
        ReportePromotorResponse rp = new ReportePromotorResponse();
        rp.setReportes(infoReportes);
        response.setDatos(ConvertirGenerico.convertInstanceOfObject(rp));
        response.setError(false);
        response.setCodigo(200);
        response.setMensaje("");
        return response;
    }
}
