package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.OrderDTO.CreateOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.DetailsOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.SimpleOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.UpdateOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.UpdateOrderStatus;
import com.uniminuto.velvet.model.dto.OrderDTO.UpdatePaymentStatus;
import com.uniminuto.velvet.service.interfaces.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v4/orders")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Order Controller", description = "API para gestionar órdenes")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Crear una nueva orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden creada exitosamente",
                    content = @Content(schema = @Schema(implementation = DetailsOrder.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Entidad relacionada no encontrada")
    })
    @PostMapping
    public ResponseEntity<DetailsOrder> createOrder(@Valid @RequestBody CreateOrder createOrderDTO) {
        log.info("REST request para crear una nueva orden");
        DetailsOrder result = orderService.createOrder(createOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Actualizar una orden existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = DetailsOrder.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @PutMapping
    public ResponseEntity<DetailsOrder> updateOrder(@Valid @RequestBody UpdateOrder updateOrderDTO) {
        log.info("REST request para actualizar la orden con ID: {}", updateOrderDTO.getId());
        DetailsOrder result = orderService.updateOrder(updateOrderDTO);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener una orden por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden encontrada",
                    content = @Content(schema = @Schema(implementation = DetailsOrder.class))),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DetailsOrder> getOrderById(@PathVariable Long id) {
        log.info("REST request para obtener la orden con ID: {}", id);
        DetailsOrder result = orderService.getOrderById(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener una orden por su número")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden encontrada",
                    content = @Content(schema = @Schema(implementation = DetailsOrder.class))),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<DetailsOrder> getOrderByNumber(@PathVariable String orderNumber) {
        log.info("REST request para obtener la orden con número: {}", orderNumber);
        DetailsOrder result = orderService.getOrderByNumber(orderNumber);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener todas las órdenes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de órdenes obtenida correctamente",
                    content = @Content(schema = @Schema(implementation = SimpleOrder.class)))
    })
    @GetMapping
    public ResponseEntity<List<SimpleOrder>> getAllOrders() {
        log.info("REST request para obtener todas las órdenes");
        List<SimpleOrder> result = orderService.getAllOrders();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener órdenes por ID de ubicación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de órdenes obtenida correctamente",
                    content = @Content(schema = @Schema(implementation = SimpleOrder.class))),
            @ApiResponse(responseCode = "404", description = "Ubicación no encontrada")
    })
    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<SimpleOrder>> getOrdersByLocationId(@PathVariable Long locationId) {
        log.info("REST request para obtener órdenes por ubicación ID: {}", locationId);
        List<SimpleOrder> result = orderService.getOrdersByLocationId(locationId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener órdenes por ID de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de órdenes obtenida correctamente",
                    content = @Content(schema = @Schema(implementation = SimpleOrder.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SimpleOrder>> getOrdersByUserId(@PathVariable Long userId) {
        log.info("REST request para obtener órdenes por usuario ID: {}", userId);
        List<SimpleOrder> result = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Actualizar el estado de una orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado de orden actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = DetailsOrder.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @PutMapping("/status")
    public ResponseEntity<DetailsOrder> updateOrderStatus(@Valid @RequestBody UpdateOrderStatus updateOrderStatusDTO) {
        log.info("REST request para actualizar el estado de la orden con ID: {}", updateOrderStatusDTO.getId());
        DetailsOrder result = orderService.updateOrderStatus(updateOrderStatusDTO);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Actualizar el estado de pago de una orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado de pago actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = DetailsOrder.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @PutMapping("/payment")
    public ResponseEntity<DetailsOrder> updatePaymentStatus(@Valid @RequestBody UpdatePaymentStatus updatePaymentStatusDTO) {
        log.info("REST request para actualizar el estado de pago de la orden con ID: {}", updatePaymentStatusDTO.getId());
        DetailsOrder result = orderService.updatePaymentStatus(updatePaymentStatusDTO);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Eliminar una orden por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Orden eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.info("REST request para eliminar la orden con ID: {}", id);
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}