package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.OrderDetailDTO.*;
import com.uniminuto.velvet.service.interfaces.OrderDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/")
@Tag(name = "Detalles de Orden", description = "API para la gestión de detalles de órdenes")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/orders/{orderId}/details")
    @Operation(summary = "Crear un nuevo detalle de orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Detalle de orden creado exitosamente",
                    content = @Content(schema = @Schema(implementation = OrderDetailResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Orden o producto no encontrado")
    })
    public ResponseEntity<OrderDetailResponse> createOrderDetail(
            @PathVariable Long orderId,
            @Valid @RequestBody CreateOrderDetail createOrderDetail) {

        OrderDetailResponse createdDetail = orderDetailService.createOrderDetail(orderId, createOrderDetail);
        return new ResponseEntity<>(createdDetail, HttpStatus.CREATED);
    }

    @GetMapping("/order-details/{id}")
    @Operation(summary = "Obtener un detalle de orden por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle de orden encontrado",
                    content = @Content(schema = @Schema(implementation = OrderDetailResponse.class))),
            @ApiResponse(responseCode = "404", description = "Detalle de orden no encontrado")
    })
    public ResponseEntity<OrderDetailResponse> getOrderDetailById(@PathVariable Long id) {
        return orderDetailService.getOrderDetailById(id)
                .map(detail -> new ResponseEntity<>(detail, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/order-details")
    @Operation(summary = "Actualizar un detalle de orden existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle de orden actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = OrderDetailResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Detalle de orden, orden o producto no encontrado")
    })
    public ResponseEntity<OrderDetailResponse> updateOrderDetail(
            @Valid @RequestBody UpdateOrderDetail updateOrderDetail) {

        OrderDetailResponse updatedDetail = orderDetailService.updateOrderDetail(updateOrderDetail);
        return new ResponseEntity<>(updatedDetail, HttpStatus.OK);
    }

    @DeleteMapping("/order-details/{id}")
    @Operation(summary = "Eliminar un detalle de orden por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Detalle de orden eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Detalle de orden no encontrado")
    })
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        boolean deleted = orderDetailService.deleteOrderDetail(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/orders/{orderId}/details")
    @Operation(summary = "Obtener todos los detalles de una orden específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de detalles de orden obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    public ResponseEntity<List<OrderDetailResponse>> getOrderDetailsByOrderId(@PathVariable Long orderId) {
        List<OrderDetailResponse> details = orderDetailService.getOrderDetailsByOrderId(orderId);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @GetMapping("/orders/{orderId}/simple-details")
    @Operation(summary = "Obtener versión simplificada de los detalles de una orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista simplificada de detalles obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    public ResponseEntity<List<SimpleOrderDetail>> getSimpleOrderDetailsByOrderId(@PathVariable Long orderId) {
        List<SimpleOrderDetail> simpleDetails = orderDetailService.getSimpleOrderDetailsByOrderId(orderId);
        return new ResponseEntity<>(simpleDetails, HttpStatus.OK);
    }

    @GetMapping("/order-details/by-product")
    @Operation(summary = "Obtener estadísticas de ventas por producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content)
    })
    public ResponseEntity<List<OrderDetailsByProduct>> getOrderDetailsByProduct(
            @RequestParam(required = false) Long productId) {

        List<OrderDetailsByProduct> statistics = orderDetailService.getOrderDetailsByProduct(productId);
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @DeleteMapping("/orders/{orderId}/details/all")
    @Operation(summary = "Eliminar todos los detalles asociados a una orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Detalles eliminados exitosamente"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    public ResponseEntity<Void> deleteOrderDetailsByOrderId(@PathVariable Long orderId) {
        orderDetailService.deleteOrderDetailsByOrderId(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}