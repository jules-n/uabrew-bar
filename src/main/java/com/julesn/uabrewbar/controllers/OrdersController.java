package com.julesn.uabrewbar.controllers;

import com.julesn.uabrewbar.domain.Order;
import com.julesn.uabrewbar.domain.Position;
import com.julesn.uabrewbar.domain.Status;
import com.julesn.uabrewbar.dtos.CreateOrderDTO;
import com.julesn.uabrewbar.services.order.OrderService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Setter(onMethod_ = {@Autowired})
    private OrderService orderService;

    @Setter(onMethod_ = {@Autowired})
    private ModelMapper modelMapper;


    /**update status
     *
     * @param bar Where order
     * @param number Which number of order
     * @param status New status of order
     * @return isUpdated
     */
    @PutMapping("{bar}/{number}/status")
    public ResponseEntity<Boolean> updateStatus(@PathVariable("bar") String bar, @PathVariable("number") Long number, Status status) {
        var order = Order.builder()
                .number(number)
                .status(status)
                .bar(bar)
                .build();
        return orderService.updateStatus(order) ? ResponseEntity.ok(true) :ResponseEntity.badRequest().build();
    }

    /**get order
     *
     * @param bar Tenant
     * @param number NumberOfOrder
     * @return OrderDTO
     */
    @GetMapping("{bar}/{number}")
    public ResponseEntity<com.julesn.uabrewbar.dtos.Order> getOrder(@PathVariable("bar") String bar, @PathVariable("number") Long number) {
        var order = orderService.getOrderByNumber(bar, number);
        if (order == null) {
            return ResponseEntity.badRequest().build();
        }
        var dto = modelMapper.map(order, com.julesn.uabrewbar.dtos.Order.class);
        return ResponseEntity.ok(dto);
    }

    /**get orders by status
     *
     * @param bar Tenant
     * @param status Needed condition
     * @return list of orders by status in specific bar
     */
    @GetMapping("{bar}")
    public ResponseEntity<List<com.julesn.uabrewbar.dtos.Order>> getOrders(@PathVariable("bar") String bar, @RequestAttribute Status status) {
        var orders = orderService.getOrdersByStatus(bar, status).stream().map(
                order -> modelMapper.map(order, com.julesn.uabrewbar.dtos.Order.class)
        ).collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    /**create order
     *
     * @param bar Tenant
     * @param dto
     * @return boolean
     */
    @PostMapping("{bar}")
    public ResponseEntity createOrder(@PathVariable("bar") String bar, @RequestBody CreateOrderDTO dto) {
        var order = modelMapper.map(dto, Order.class);
        order.setBar(bar);
        AtomicReference<Float> toPay = new AtomicReference<>((float) 0);
        dto.getPositions().entrySet().forEach(
                position -> toPay.updateAndGet(v -> new Float((float) (v + position.getKey().getPrice() * position.getValue())))
        );
        order.setToPay(toPay.get());
        try {
            orderService.createOrder(order);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("{bar}/{number}")
    public ResponseEntity<String> pay(@PathVariable("bar") String bar, @PathVariable("number") Long number, @RequestAttribute Float transaction) {
        return orderService.transferMoney(number, bar, transaction) ?
                ResponseEntity.ok("Thank you") : ResponseEntity.badRequest().body("Not enough");
    }

    @GetMapping("{bar}/{client}")
    public ResponseEntity<Set<String>> getClientsOrders(@PathVariable("bar") String bar, @PathVariable("client") String client) {
        Set<String> positions = orderService.getOrdersByUser(bar, client)
                .stream().map(order -> order.getPositions().keySet()).flatMap(Set::stream)
                .map(Position::getName).collect(Collectors.toSet());
        return ResponseEntity.ok(positions);
    }
}
