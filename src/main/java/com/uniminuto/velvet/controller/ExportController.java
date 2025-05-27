package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.service.interfaces.ExportService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @GetMapping("/ventas")
    public void exportarVentas(
            HttpServletResponse response,
            @RequestParam(required = false) String sede,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestHeader("Authorization") String token
    ) throws IOException {
        String jwt = token.replace("Bearer ", "");
        Claims claims = Jwts.parser().setSigningKey("claveSecreta").parseClaimsJws(jwt).getBody();
        String rol = claims.get("rol", String.class);
        String sedeUsuario = claims.get("sede", String.class);

        if ("cajero".equalsIgnoreCase(rol)) {
            sede = sedeUsuario;
        }

        ByteArrayInputStream stream = exportService.generarReporteVentas(sede, fechaInicio, fechaFin);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=ventas.xlsx");
        IOUtils.copy(stream, response.getOutputStream());
    }
}