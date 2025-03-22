package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.OrderStatusDTO;
import com.uniminuto.velvet.model.entity.OrderStatus;
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
public class OrderStatusMapperImpl implements OrderStatusMapper {

    @Override
    public OrderStatus toEntity(OrderStatusDTO.CreateOrderStatus createOrderStatusDTO) {
        if ( createOrderStatusDTO == null ) {
            return null;
        }

        OrderStatus.OrderStatusBuilder orderStatus = OrderStatus.builder();

        orderStatus.name( createOrderStatusDTO.getName() );
        orderStatus.description( createOrderStatusDTO.getDescription() );

        return orderStatus.build();
    }

    @Override
    public void updateEntityFromDto(OrderStatusDTO.UpdateOrderStatus updateOrderStatusDTO, OrderStatus orderStatus) {
        if ( updateOrderStatusDTO == null ) {
            return;
        }

        orderStatus.setId( updateOrderStatusDTO.getId() );
        orderStatus.setName( updateOrderStatusDTO.getName() );
        orderStatus.setDescription( updateOrderStatusDTO.getDescription() );
    }

    @Override
    public OrderStatusDTO.DetailsOrderStatus toDetailsDTO(OrderStatus orderStatus) {
        if ( orderStatus == null ) {
            return null;
        }

        OrderStatusDTO.DetailsOrderStatus.DetailsOrderStatusBuilder detailsOrderStatus = OrderStatusDTO.DetailsOrderStatus.builder();

        detailsOrderStatus.id( orderStatus.getId() );
        detailsOrderStatus.name( orderStatus.getName() );
        detailsOrderStatus.description( orderStatus.getDescription() );

        detailsOrderStatus.orders( mapOrdersToStrings(orderStatus.getOrders()) );

        return detailsOrderStatus.build();
    }

    @Override
    public List<OrderStatusDTO.DetailsOrderStatus> toDetailsDTOList(List<OrderStatus> orderStatuses) {
        if ( orderStatuses == null ) {
            return null;
        }

        List<OrderStatusDTO.DetailsOrderStatus> list = new ArrayList<OrderStatusDTO.DetailsOrderStatus>( orderStatuses.size() );
        for ( OrderStatus orderStatus : orderStatuses ) {
            list.add( toDetailsDTO( orderStatus ) );
        }

        return list;
    }
}
