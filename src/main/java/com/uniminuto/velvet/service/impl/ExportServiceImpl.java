package com.uniminuto.velvet.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    public ByteArrayInputStream generarReporteVentas(String sede, LocalDate fechaInicio, LocalDate fechaFin) {
        List<InventoryMovement> movimientos = movementRepository.findByFilters(sede, fechaInicio, fechaFin);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Ventas");

            String[] headers = {"Código", "Nombre", "Valor Costo", "Valor Venta", "Ganancia", "Sede", "Cantidad"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (InventoryMovement mov : movimientos) {
                Row row = sheet.createRow(rowIdx++);
                var product = mov.getProduct();
                BigDecimal costo = mov.getUnitCost();
                BigDecimal venta = product.getSalePrice();
                BigDecimal ganancia = venta.subtract(costo);

                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(costo.doubleValue());
                row.createCell(3).setCellValue(venta.doubleValue());
                row.createCell(4).setCellValue(ganancia.doubleValue());
                row.createCell(5).setCellValue(mov.getInventoryStock().getLocation().getName());
                row.createCell(6).setCellValue(mov.getQuantity());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Error al generar Excel", e);
        }
    }
}