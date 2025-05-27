package com.uniminuto.velvet.service.interfaces;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

public interface ProductExportService {
    void exportToExcel(HttpServletResponse response, Long locationId, LocalDate startDate, LocalDate endDate, String token) throws IOException;
}