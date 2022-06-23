package com.julesn.uabrewbar.config;

import com.julesn.uabrewbar.domain.Order;
import com.julesn.uabrewbar.dtos.CreateOrderDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(orderToOrderDTOConverter);
        modelMapper.addConverter(createOrderDTOToOrder);
        return modelMapper;
    }

    private Converter<Order, com.julesn.uabrewbar.dtos.Order> orderToOrderDTOConverter = new AbstractConverter<Order, com.julesn.uabrewbar.dtos.Order>() {
        protected com.julesn.uabrewbar.dtos.Order convert(Order model) {
            var order = com.julesn.uabrewbar.dtos.Order.builder()
                    .bar(model.getBar())
                    .number(model.getNumber())
                    .place(model.getPlace())
                    .positions(model.getPositions())
                    .status(model.getStatus())
                    .client(model.getClient())
                    .toPay(model.getToPay())
                    .build();
            return order;
        }
    };

    private Converter<CreateOrderDTO, Order> createOrderDTOToOrder = new AbstractConverter<CreateOrderDTO, Order>() {
        protected Order convert(CreateOrderDTO model) {
            var order = Order.builder()
                    .place(model.getPlace())
                    .positions(model.getPositions())
                    .client(model.getClient())
                    .build();
            return order;
        }
    };
}
