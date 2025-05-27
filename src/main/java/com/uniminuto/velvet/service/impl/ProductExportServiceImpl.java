package com.uniminuto.velvet.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.uniminuto.velvet.model.entity.InventoryMovement;
import com.uniminuto.velvet.model.entity.Product;
import com.uniminuto.velvet.model.repository.InventoryMovementRepository;
import com.uniminuto.velvet.service.interfaces.ProductExportService;
import com.uniminuto.velvet.util.JwtUtil;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductExportServiceImpl implements ProductExportService {

    private final InventoryMovementRepository inventoryMovementRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void exportToExcel(HttpServletResponse response, Long locationId, LocalDate startDate, LocalDate endDate, String token) throws IOException {
        // Eliminar "Bearer " si está presente
        token = token.replace("Bearer ", "");

        String role = jwtUtil.extractRole(token);
        Long userLocationId = jwtUtil.extractLocationId(token);

        if ("CAJERO".equalsIgnoreCase(role)) {
            locationId = userLocationId;
        }

        List<InventoryMovement> movements = inventoryMovementRepository.findByFilters(locationId, startDate, endDate);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=productos.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Productos");

            Row header = sheet.createRow(0);
            String[] columns = {"Código", "Nombre", "Valor costo", "Valor venta", "Ganancia", "Sede", "Cantidad"};
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }

            int rowIdx = 1;
            for (InventoryMovement m : movements) {
                Product p = m.getProduct();
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(p.getId());
                row.createCell(1).setCellValue(p.getName());
                row.createCell(2).setCellValue(p.getPurchasePrice().doubleValue());
                row.createCell(3).setCellValue(p.getSalePrice().doubleValue());
                row.createCell(4).setCellValue(p.getSalePrice().subtract(p.getPurchasePrice()).doubleValue());
                row.createCell(5).setCellValue(m.getInventoryStock().getLocation().getName());
                row.createCell(6).setCellValue(m.getQuantity());
            }

            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        }
    }
}