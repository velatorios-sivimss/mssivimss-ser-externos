package com.imss.sivimss.serviciosexternos.beans;



import com.imss.sivimss.serviciosexternos.utils.AppConstantes;
import com.imss.sivimss.serviciosexternos.utils.DatosRequest;
import com.imss.sivimss.serviciosexternos.utils.SelectQueryUtil;

import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.Map;

public class ReportePromotor {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReportePromotor.class);

    public String buscarReportes(String tipoReporte){
        SelectQueryUtil query = new SelectQueryUtil();
        query.select("ID_CONFIG_REPORTE AS idReporte","DES_REPORTE AS nombreReporte")
                .from("SVC_CONFIG_REPORTE")
                .where("IND_TIPO_REPORTE = " + tipoReporte);
        log.info(query.build());
        return query.build();
    }
}
