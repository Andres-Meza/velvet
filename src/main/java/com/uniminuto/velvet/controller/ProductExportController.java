package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.service.interfaces.ProductExportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
public class ProductExportController {

    private final ProductExportService productExportService;

    @GetMapping("/products")
    public void exportProductsToExcel(
            HttpServletResponse response,
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestHeader("Authorization") String token
    ) throws IOException {
        productExportService.exportToExcel(response, locationId, startDate, endDate, token);
    }
}
