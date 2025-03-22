package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.PaymentMethodDTO;
import com.uniminuto.velvet.model.entity.PaymentMethod;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T13:54:52-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class PaymentMethodMapperImpl implements PaymentMethodMapper {

    @Override
    public PaymentMethod toEntity(PaymentMethodDTO.CreatePaymentMethod createPaymentMethod) {
        if ( createPaymentMethod == null ) {
            return null;
        }

        PaymentMethod.PaymentMethodBuilder paymentMethod = PaymentMethod.builder();

        paymentMethod.name( createPaymentMethod.getName() );
        paymentMethod.description( createPaymentMethod.getDescription() );

        return paymentMethod.build();
    }

    @Override
    public void updateEntityFromDto(PaymentMethodDTO.UpdatePaymentMethod updatePaymentMethod, PaymentMethod paymentMethod) {
        if ( updatePaymentMethod == null ) {
            return;
        }

        if ( updatePaymentMethod.getId() != null ) {
            paymentMethod.setId( updatePaymentMethod.getId() );
        }
        if ( updatePaymentMethod.getName() != null ) {
            paymentMethod.setName( updatePaymentMethod.getName() );
        }
        if ( updatePaymentMethod.getDescription() != null ) {
            paymentMethod.setDescription( updatePaymentMethod.getDescription() );
        }
    }

    @Override
    public PaymentMethodDTO.SimplePaymentMethod toSimpleDto(PaymentMethod paymentMethod) {
        if ( paymentMethod == null ) {
            return null;
        }

        PaymentMethodDTO.SimplePaymentMethod.SimplePaymentMethodBuilder simplePaymentMethod = PaymentMethodDTO.SimplePaymentMethod.builder();

        simplePaymentMethod.id( paymentMethod.getId() );
        simplePaymentMethod.name( paymentMethod.getName() );
        simplePaymentMethod.description( paymentMethod.getDescription() );

        return simplePaymentMethod.build();
    }

    @Override
    public PaymentMethodDTO.DetailsPaymentMethod toDetailsDto(PaymentMethod paymentMethod) {
        if ( paymentMethod == null ) {
            return null;
        }

        PaymentMethodDTO.DetailsPaymentMethod.DetailsPaymentMethodBuilder detailsPaymentMethod = PaymentMethodDTO.DetailsPaymentMethod.builder();

        detailsPaymentMethod.orders( orderSetToStringSet( paymentMethod.getOrders() ) );
        detailsPaymentMethod.id( paymentMethod.getId() );
        detailsPaymentMethod.name( paymentMethod.getName() );
        detailsPaymentMethod.description( paymentMethod.getDescription() );

        return detailsPaymentMethod.build();
    }
}
