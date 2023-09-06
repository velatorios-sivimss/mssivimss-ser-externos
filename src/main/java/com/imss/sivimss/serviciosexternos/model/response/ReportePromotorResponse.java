package com.imss.sivimss.serviciosexternos.model.response;


import com.imss.sivimss.serviciosexternos.model.InformacionReportesModel;
import lombok.Data;

import java.util.List;

@Data
public class ReportePromotorResponse {
    private List<InformacionReportesModel> reportes;
}
