package com.uniminuto.velvet.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.uniminuto.velvet.model.entity.InventoryMovement;
import com.uniminuto.velvet.model.repository.InventoryMovementRepository;
import com.uniminuto.velvet.service.interfaces.ExportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {

    private final InventoryMovementRepository movementRepository;

    @Override
    public ByteArrayInputStream generarReporteVentas(String statusName, Long locationId, LocalDate fechaInicio, LocalDate fechaFin) {
        // ✅ Conversión a LocalDateTime
        LocalDateTime startDate = fechaInicio.atStartOfDay();
        LocalDateTime endDate = fechaFin.atTime(LocalTime.MAX);

        List<InventoryMovement> movimientos = movementRepository.findVentasPorEstadoYSedeYFecha(
                statusName, locationId, startDate, endDate
        );

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Ventas");

            String[] headers = {"Código", "Nombre", "Valor Costo", "Valor Venta", "Ganancia", "Sede", "Cantidad", "Fecha"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (InventoryMovement mov : movimientos) {
                var product = mov.getProduct();
                BigDecimal costo = mov.getUnitCost();
                BigDecimal venta = product.getSalePrice();
                BigDecimal ganancia = venta.subtract(costo);

                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(costo.doubleValue());
                row.createCell(3).setCellValue(venta.doubleValue());
                row.createCell(4).setCellValue(ganancia.doubleValue());
                row.createCell(5).setCellValue(mov.getInventoryStock().getLocation().getName());
                row.createCell(6).setCellValue(mov.getQuantity());
                row.createCell(7).setCellValue(mov.getMovementDate().toString());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Error al generar Excel", e);
        }
    }
}