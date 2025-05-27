package com.uniminuto.velvet.service.interfaces;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

public interface ExportService {
    ByteArrayInputStream generarReporteVentas(String sede, LocalDate fechaInicio, LocalDate fechaFin);
}