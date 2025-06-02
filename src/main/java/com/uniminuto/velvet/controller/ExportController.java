package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.entity.InventoryMovement;
import com.uniminuto.velvet.model.repository.InventoryMovementRepository;
import com.uniminuto.velvet.service.interfaces.ExportService;
import com.uniminuto.velvet.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;
    private final InventoryMovementRepository inventoryMovementRepository;
    private final JwtUtil jwtUtil;

    @GetMapping("/ventas")
    public void exportarVentas(
            HttpServletResponse response,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestHeader("Authorization") String token
    ) throws IOException {
        token = token.replace("Bearer ", "");
        String rol = jwtUtil.extractRole(token);
        Long locationId = null;

        if ("CAJERO".equalsIgnoreCase(rol)) {
            locationId = jwtUtil.extractLocationId(token);
        }

        ByteArrayInputStream stream = exportService.generarReporteVentas("PAGADA", locationId, fechaInicio, fechaFin);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=ventas.xlsx");
        IOUtils.copy(stream, response.getOutputStream());
    }

    @GetMapping("/ventas/listar")
    public List<InventoryMovement> listarVentas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestHeader("Authorization") String token
    ) {
        token = token.replace("Bearer ", "");
        String rol = jwtUtil.extractRole(token);
        Long locationId = "CAJERO".equalsIgnoreCase(rol) ? jwtUtil.extractLocationId(token) : null;

        // ✅ Conversión de LocalDate a LocalDateTime
        LocalDateTime startDate = fechaInicio != null ? fechaInicio.atStartOfDay() : LocalDateTime.MIN;
        LocalDateTime endDate = fechaFin != null ? fechaFin.atTime(LocalTime.MAX) : LocalDateTime.MAX;

        return inventoryMovementRepository.findVentasPorEstadoYSedeYFecha("PAGADA", locationId, startDate, endDate);
    }
}