package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.OrderDTO;
import com.uniminuto.velvet.model.dto.OrderDetailDTO;
import com.uniminuto.velvet.model.dto.OrderStatusDTO;
import com.uniminuto.velvet.model.entity.Location;
import com.uniminuto.velvet.model.entity.Order;
import com.uniminuto.velvet.model.entity.OrderDetail;
import com.uniminuto.velvet.model.entity.OrderStatus;
import com.uniminuto.velvet.model.entity.PaymentMethod;
import com.uniminuto.velvet.model.entity.Tables;
import com.uniminuto.velvet.model.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T13:54:52-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Order toEntity(OrderDTO.CreateOrder dto) {
        if ( dto == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.user( createOrderToUser( dto ) );
        order.location( createOrderToLocation( dto ) );
        order.table( createOrderToTables( dto ) );
        order.orderStatus( createOrderToOrderStatus( dto ) );
        order.paymentMethod( createOrderToPaymentMethod( dto ) );
        order.totalAmount( dto.getTotalAmount() );

        order.orderDate( java.time.LocalDateTime.now() );

        return order.build();
    }

    @Override
    public void updateOrderFromDto(OrderDTO.UpdateOrder dto, Order order) {
        if ( dto == null ) {
            return;
        }

        if ( order.getOrderStatus() == null ) {
            order.setOrderStatus( OrderStatus.builder().build() );
        }
        updateOrderToOrderStatus( dto, order.getOrderStatus() );
        if ( order.getPaymentMethod() == null ) {
            order.setPaymentMethod( PaymentMethod.builder().build() );
        }
        updateOrderToPaymentMethod( dto, order.getPaymentMethod() );
        if ( dto.getId() != null ) {
            order.setId( dto.getId() );
        }
        if ( dto.getTotalAmount() != null ) {
            order.setTotalAmount( dto.getTotalAmount() );
        }
        if ( dto.getCompletedDate() != null ) {
            order.setCompletedDate( dto.getCompletedDate() );
        }
        if ( order.getOrderDetails() != null ) {
            List<OrderDetail> list = updateOrderDetailListToOrderDetailList( dto.getOrderDetails() );
            if ( list != null ) {
                order.getOrderDetails().clear();
                order.getOrderDetails().addAll( list );
            }
        }
        else {
            List<OrderDetail> list = updateOrderDetailListToOrderDetailList( dto.getOrderDetails() );
            if ( list != null ) {
                order.setOrderDetails( list );
            }
        }
    }

    @Override
    public OrderDTO.DetailsOrder toDetailsDTO(Order entity) {
        if ( entity == null ) {
            return null;
        }

        OrderDTO.DetailsOrder.DetailsOrderBuilder detailsOrder = OrderDTO.DetailsOrder.builder();

        detailsOrder.locationId( entityLocationId( entity ) );
        detailsOrder.locationName( entityLocationName( entity ) );
        detailsOrder.userId( entityUserId( entity ) );
        detailsOrder.userName( entityUserName( entity ) );
        detailsOrder.userLastName( entityUserLastName( entity ) );
        detailsOrder.tableId( entityTableId( entity ) );
        detailsOrder.tableName( entityTableNumber( entity ) );
        detailsOrder.orderStatusName( entityOrderStatusName( entity ) );
        detailsOrder.paymentMethodName( entityPaymentMethodName( entity ) );
        detailsOrder.orderDetails( orderDetailMapper.toDetailResponseList( entity.getOrderDetails() ) );
        detailsOrder.id( entity.getId() );
        detailsOrder.orderNumber( entity.getOrderNumber() );
        detailsOrder.orderDate( entity.getOrderDate() );
        detailsOrder.completedDate( entity.getCompletedDate() );
        detailsOrder.totalAmount( entity.getTotalAmount() );
        detailsOrder.createdAt( entity.getCreatedAt() );
        detailsOrder.updatedAt( entity.getUpdatedAt() );

        return detailsOrder.build();
    }

    @Override
    public OrderDTO.SimpleOrder toSimpleDTO(Order entity) {
        if ( entity == null ) {
            return null;
        }

        OrderDTO.SimpleOrder.SimpleOrderBuilder simpleOrder = OrderDTO.SimpleOrder.builder();

        simpleOrder.locationId( entityLocationId( entity ) );
        simpleOrder.locationName( entityLocationName( entity ) );
        simpleOrder.userId( entityUserId( entity ) );
        simpleOrder.userName( entityUserName( entity ) );
        simpleOrder.userLastName( entityUserLastName( entity ) );
        simpleOrder.orderStatusName( entityOrderStatusName( entity ) );
        simpleOrder.id( entity.getId() );
        simpleOrder.orderNumber( entity.getOrderNumber() );
        simpleOrder.orderDate( entity.getOrderDate() );
        simpleOrder.totalAmount( entity.getTotalAmount() );

        return simpleOrder.build();
    }

    @Override
    public List<OrderDTO.SimpleOrder> toSimpleDTOList(List<Order> entities) {
        if ( entities == null ) {
            return null;
        }

        List<OrderDTO.SimpleOrder> list = new ArrayList<OrderDTO.SimpleOrder>( entities.size() );
        for ( Order order : entities ) {
            list.add( toSimpleDTO( order ) );
        }

        return list;
    }

    @Override
    public void updateOrderStatus(OrderStatusDTO.UpdateOrderStatus dto, Order order) {
        if ( dto == null ) {
            return;
        }

        if ( order.getOrderStatus() == null ) {
            order.setOrderStatus( OrderStatus.builder().build() );
        }
        updateOrderStatusToOrderStatus( dto, order.getOrderStatus() );
        if ( dto.getId() != null ) {
            order.setId( dto.getId() );
        }
    }

    @Override
    public void updatePaymentStatus(OrderDTO.UpdatePaymentStatus dto, Order order) {
        if ( dto == null ) {
            return;
        }

        if ( order.getPaymentMethod() == null ) {
            order.setPaymentMethod( PaymentMethod.builder().build() );
        }
        updatePaymentStatusToPaymentMethod( dto, order.getPaymentMethod() );
        if ( dto.getId() != null ) {
            order.setId( dto.getId() );
        }
        order.setPaid( dto.isPaid() );
    }

    protected User createOrderToUser(OrderDTO.CreateOrder createOrder) {
        if ( createOrder == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( createOrder.getUserId() );

        return user.build();
    }

    protected Location createOrderToLocation(OrderDTO.CreateOrder createOrder) {
        if ( createOrder == null ) {
            return null;
        }

        Location.LocationBuilder location = Location.builder();

        location.id( createOrder.getLocationId() );

        return location.build();
    }

    protected Tables createOrderToTables(OrderDTO.CreateOrder createOrder) {
        if ( createOrder == null ) {
            return null;
        }

        Tables.TablesBuilder tables = Tables.builder();

        tables.id( createOrder.getTableId() );

        return tables.build();
    }

    protected OrderStatus createOrderToOrderStatus(OrderDTO.CreateOrder createOrder) {
        if ( createOrder == null ) {
            return null;
        }

        OrderStatus.OrderStatusBuilder orderStatus = OrderStatus.builder();

        orderStatus.id( createOrder.getOrderStatusId() );

        return orderStatus.build();
    }

    protected PaymentMethod createOrderToPaymentMethod(OrderDTO.CreateOrder createOrder) {
        if ( createOrder == null ) {
            return null;
        }

        PaymentMethod.PaymentMethodBuilder paymentMethod = PaymentMethod.builder();

        paymentMethod.id( createOrder.getPaymentMethodId() );

        return paymentMethod.build();
    }

    protected void updateOrderToOrderStatus(OrderDTO.UpdateOrder updateOrder, OrderStatus mappingTarget) {
        if ( updateOrder == null ) {
            return;
        }

        if ( updateOrder.getOrderStatusId() != null ) {
            mappingTarget.setId( updateOrder.getOrderStatusId() );
        }
    }

    protected void updateOrderToPaymentMethod(OrderDTO.UpdateOrder updateOrder, PaymentMethod mappingTarget) {
        if ( updateOrder == null ) {
            return;
        }

        if ( updateOrder.getPaymentMethodId() != null ) {
            mappingTarget.setId( updateOrder.getPaymentMethodId() );
        }
    }

    protected OrderDetail updateOrderDetailToOrderDetail(OrderDetailDTO.UpdateOrderDetail updateOrderDetail) {
        if ( updateOrderDetail == null ) {
            return null;
        }

        OrderDetail.OrderDetailBuilder orderDetail = OrderDetail.builder();

        orderDetail.id( updateOrderDetail.getId() );
        orderDetail.quantity( updateOrderDetail.getQuantity() );
        orderDetail.unitPrice( updateOrderDetail.getUnitPrice() );
        orderDetail.productNotes( updateOrderDetail.getProductNotes() );

        return orderDetail.build();
    }

    protected List<OrderDetail> updateOrderDetailListToOrderDetailList(List<OrderDetailDTO.UpdateOrderDetail> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderDetail> list1 = new ArrayList<OrderDetail>( list.size() );
        for ( OrderDetailDTO.UpdateOrderDetail updateOrderDetail : list ) {
            list1.add( updateOrderDetailToOrderDetail( updateOrderDetail ) );
        }

        return list1;
    }

    private Long entityLocationId(Order order) {
        if ( order == null ) {
            return null;
        }
        Location location = order.getLocation();
        if ( location == null ) {
            return null;
        }
        Long id = location.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityLocationName(Order order) {
        if ( order == null ) {
            return null;
        }
        Location location = order.getLocation();
        if ( location == null ) {
            return null;
        }
        String name = location.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityUserId(Order order) {
        if ( order == null ) {
            return null;
        }
        User user = order.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityUserName(Order order) {
        if ( order == null ) {
            return null;
        }
        User user = order.getUser();
        if ( user == null ) {
            return null;
        }
        String name = user.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String entityUserLastName(Order order) {
        if ( order == null ) {
            return null;
        }
        User user = order.getUser();
        if ( user == null ) {
            return null;
        }
        String lastName = user.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    private Long entityTableId(Order order) {
        if ( order == null ) {
            return null;
        }
        Tables table = order.getTable();
        if ( table == null ) {
            return null;
        }
        Long id = table.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityTableNumber(Order order) {
        if ( order == null ) {
            return null;
        }
        Tables table = order.getTable();
        if ( table == null ) {
            return null;
        }
        String number = table.getNumber();
        if ( number == null ) {
            return null;
        }
        return number;
    }

    private String entityOrderStatusName(Order order) {
        if ( order == null ) {
            return null;
        }
        OrderStatus orderStatus = order.getOrderStatus();
        if ( orderStatus == null ) {
            return null;
        }
        String name = orderStatus.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String entityPaymentMethodName(Order order) {
        if ( order == null ) {
            return null;
        }
        PaymentMethod paymentMethod = order.getPaymentMethod();
        if ( paymentMethod == null ) {
            return null;
        }
        String name = paymentMethod.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected void updateOrderStatusToOrderStatus(OrderStatusDTO.UpdateOrderStatus updateOrderStatus, OrderStatus mappingTarget) {
        if ( updateOrderStatus == null ) {
            return;
        }

        if ( updateOrderStatus.getId() != null ) {
            mappingTarget.setId( updateOrderStatus.getId() );
        }
    }

    protected void updatePaymentStatusToPaymentMethod(OrderDTO.UpdatePaymentStatus updatePaymentStatus, PaymentMethod mappingTarget) {
        if ( updatePaymentStatus == null ) {
            return;
        }

        if ( updatePaymentStatus.getPaymentMethodId() != null ) {
            mappingTarget.setId( updatePaymentStatus.getPaymentMethodId() );
        }
    }
}
