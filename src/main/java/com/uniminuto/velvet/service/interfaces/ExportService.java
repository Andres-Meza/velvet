package com.uniminuto.velvet.service.interfaces;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

public interface ExportService {
    ByteArrayInputStream generarReporteVentas(String statusName, Long locationId, LocalDate fechaInicio, LocalDate fechaFin);
}