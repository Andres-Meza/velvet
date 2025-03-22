package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.OrderDetailDTO;
import com.uniminuto.velvet.model.entity.Order;
import com.uniminuto.velvet.model.entity.OrderDetail;
import com.uniminuto.velvet.model.entity.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T13:54:52-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class OrderDetailMapperImpl implements OrderDetailMapper {

    @Override
    public OrderDetail toEntity(OrderDetailDTO.CreateOrderDetail dto) {
        if ( dto == null ) {
            return null;
        }

        OrderDetail.OrderDetailBuilder orderDetail = OrderDetail.builder();

        orderDetail.order( createOrderDetailToOrder( dto ) );
        orderDetail.product( createOrderDetailToProduct( dto ) );
        orderDetail.quantity( dto.getQuantity() );
        orderDetail.unitPrice( dto.getUnitPrice() );
        orderDetail.productNotes( dto.getProductNotes() );

        return orderDetail.build();
    }

    @Override
    public void updateFromDto(OrderDetailDTO.UpdateOrderDetail dto, OrderDetail entity) {
        if ( dto == null ) {
            return;
        }

        if ( entity.getOrder() == null ) {
            entity.setOrder( Order.builder().build() );
        }
        updateOrderDetailToOrder( dto, entity.getOrder() );
        if ( entity.getProduct() == null ) {
            entity.setProduct( Product.builder().build() );
        }
        updateOrderDetailToProduct( dto, entity.getProduct() );
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getQuantity() != null ) {
            entity.setQuantity( dto.getQuantity() );
        }
        if ( dto.getUnitPrice() != null ) {
            entity.setUnitPrice( dto.getUnitPrice() );
        }
        if ( dto.getProductNotes() != null ) {
            entity.setProductNotes( dto.getProductNotes() );
        }

        calculateSubtotal( entity );
    }

    @Override
    public OrderDetailDTO.OrderDetailResponse toDetailResponse(OrderDetail entity) {
        if ( entity == null ) {
            return null;
        }

        OrderDetailDTO.OrderDetailResponse.OrderDetailResponseBuilder orderDetailResponse = OrderDetailDTO.OrderDetailResponse.builder();

        orderDetailResponse.orderId( entityOrderId( entity ) );
        orderDetailResponse.orderNumber( entityOrderOrderNumber( entity ) );
        orderDetailResponse.productId( entityProductId( entity ) );
        orderDetailResponse.productName( entityProductName( entity ) );
        orderDetailResponse.id( entity.getId() );
        orderDetailResponse.quantity( entity.getQuantity() );
        orderDetailResponse.unitPrice( entity.getUnitPrice() );
        orderDetailResponse.subtotal( entity.getSubtotal() );
        orderDetailResponse.productNotes( entity.getProductNotes() );

        return orderDetailResponse.build();
    }

    @Override
    public OrderDetailDTO.SimpleOrderDetail toSimpleDetail(OrderDetail entity) {
        if ( entity == null ) {
            return null;
        }

        OrderDetailDTO.SimpleOrderDetail.SimpleOrderDetailBuilder simpleOrderDetail = OrderDetailDTO.SimpleOrderDetail.builder();

        simpleOrderDetail.orderId( entityOrderId( entity ) );
        simpleOrderDetail.orderNumber( entityOrderOrderNumber( entity ) );
        simpleOrderDetail.productId( entityProductId( entity ) );
        simpleOrderDetail.productName( entityProductName( entity ) );
        simpleOrderDetail.id( entity.getId() );
        simpleOrderDetail.quantity( entity.getQuantity() );
        simpleOrderDetail.unitPrice( entity.getUnitPrice() );
        simpleOrderDetail.subtotal( entity.getSubtotal() );

        return simpleOrderDetail.build();
    }

    @Override
    public List<OrderDetailDTO.OrderDetailResponse> toDetailResponseList(List<OrderDetail> entities) {
        if ( entities == null ) {
            return null;
        }

        List<OrderDetailDTO.OrderDetailResponse> list = new ArrayList<OrderDetailDTO.OrderDetailResponse>( entities.size() );
        for ( OrderDetail orderDetail : entities ) {
            list.add( toDetailResponse( orderDetail ) );
        }

        return list;
    }

    @Override
    public List<OrderDetailDTO.SimpleOrderDetail> toSimpleDetailList(List<OrderDetail> entities) {
        if ( entities == null ) {
            return null;
        }

        List<OrderDetailDTO.SimpleOrderDetail> list = new ArrayList<OrderDetailDTO.SimpleOrderDetail>( entities.size() );
        for ( OrderDetail orderDetail : entities ) {
            list.add( toSimpleDetail( orderDetail ) );
        }

        return list;
    }

    @Override
    public OrderDetailDTO.OrderDetailsByProduct toOrderDetailsByProduct(OrderDetail entity) {
        if ( entity == null ) {
            return null;
        }

        OrderDetailDTO.OrderDetailsByProduct.OrderDetailsByProductBuilder orderDetailsByProduct = OrderDetailDTO.OrderDetailsByProduct.builder();

        orderDetailsByProduct.productId( entityProductId( entity ) );
        orderDetailsByProduct.productName( entityProductName( entity ) );
        orderDetailsByProduct.totalQuantity( entity.getQuantity() );
        orderDetailsByProduct.totalRevenue( entity.getSubtotal() );

        return orderDetailsByProduct.build();
    }

    protected Order createOrderDetailToOrder(OrderDetailDTO.CreateOrderDetail createOrderDetail) {
        if ( createOrderDetail == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.id( createOrderDetail.getOrderId() );

        return order.build();
    }

    protected Product createOrderDetailToProduct(OrderDetailDTO.CreateOrderDetail createOrderDetail) {
        if ( createOrderDetail == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( createOrderDetail.getProductId() );

        return product.build();
    }

    protected void updateOrderDetailToOrder(OrderDetailDTO.UpdateOrderDetail updateOrderDetail, Order mappingTarget) {
        if ( updateOrderDetail == null ) {
            return;
        }

        if ( updateOrderDetail.getOrderId() != null ) {
            mappingTarget.setId( updateOrderDetail.getOrderId() );
        }
    }

    protected void updateOrderDetailToProduct(OrderDetailDTO.UpdateOrderDetail updateOrderDetail, Product mappingTarget) {
        if ( updateOrderDetail == null ) {
            return;
        }

        if ( updateOrderDetail.getProductId() != null ) {
            mappingTarget.setId( updateOrderDetail.getProductId() );
        }
    }

    private Long entityOrderId(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }
        Order order = orderDetail.getOrder();
        if ( order == null ) {
            return null;
        }
        Long id = order.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityOrderOrderNumber(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }
        Order order = orderDetail.getOrder();
        if ( order == null ) {
            return null;
        }
        String orderNumber = order.getOrderNumber();
        if ( orderNumber == null ) {
            return null;
        }
        return orderNumber;
    }

    private Long entityProductId(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }
        Product product = orderDetail.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityProductName(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }
        Product product = orderDetail.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
