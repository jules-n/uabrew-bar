package com.julesn.uabrewbar.services.order;

import com.julesn.uabrewbar.domain.Order;
import com.julesn.uabrewbar.domain.Status;
import com.julesn.uabrewbar.persistence.order.OrderRepository;
import com.julesn.uabrewbar.persistence.sequence.SequenceRepository;
import com.julesn.uabrewbar.services.kafka.KafkaProducer;
import com.julesn.uabrewbar.services.rest.RestExchanger;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@Primary
public class OrderServiceImpl implements OrderService {

    @Setter(onMethod_ = {@Autowired})
    private KafkaProducer kafkaProducer;

    @Setter(onMethod_ = {@Autowired})
    private RestExchanger restExchanger;

    @Setter(onMethod_ = {@Autowired})
    private OrderRepository orderRepository;

    @Setter(onMethod_ = {@Autowired})
    private SequenceRepository sequenceRepository;

    @Setter(onMethod_ = {@Value("${spring.cloudkarafka.status-topic}")})
    private String statusTopic;

    @Setter(onMethod_ = {@Value("${uabrew.warehouse.host}")})
    private String warehouseHost;

    @Setter(onMethod_ = {@Value("${uabrew.warehouse.port}")})
    private String warehousePort;

    @Override
    public boolean updateStatus(Order order) {
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("bar", order.getBar());
        criteria.put("number", order.getNumber());
        Map<String, Object> data = new HashMap<>();
        criteria.put("status", order.getStatus());
        var isUpdated = orderRepository.updateByCriteria(criteria, data);
        if (isUpdated) {
           kafkaProducer.send(order, statusTopic);
           return true;
        }
        return false;
    }

    @Override
    public Order getOrderByNumber(String bar, Long number) {
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("bar", bar);
        criteria.put("number", number);
        return orderRepository.getByCriteria(criteria);
    }

    @Override
    public List<Order> getOrdersByStatus(String bar, Status status) {
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("bar", bar);
        criteria.put("status", status);
        return orderRepository.getListByCriteria(criteria);
    }

    @Override
    public boolean createOrder(Order order) throws Exception {
        Map<String, Integer> positions = new HashMap<>();
        order.getPositions().forEach(
                (k, v) -> {
                    positions.put(k.getName(),v);
                }
        );
        Boolean isEverythingIsEnough = restExchanger.fetch(warehouseHost, warehousePort, Boolean.class, List.of(order.getBar()), positions);
        if (!isEverythingIsEnough.booleanValue()) {
            throw new Exception("Not enough components");
        }
        order.setNumber(sequenceRepository.findMaxByCount());
        boolean wasIncremented = sequenceRepository.incrementCount();
        if(wasIncremented) {
            order.setStatus(Status.NEW);
            try {
                orderRepository.save(order);
                return true;
            } catch (Exception ex) {
                log.warn(ex);
                return true;
            }
        }
        return false;
    }

    public boolean transferMoney(Long number, String bar, float transaction) {
        var order = orderRepository.getByCriteria(Map.of("number", number, "bar", bar));
        if (order.getToPay() < transaction) {
            return false;
        }
        float paymentForDevs = transaction*0.01f;
        log.info("For devs: "+paymentForDevs);
        float forAFoU = order.getToPay()*0.03f;
        log.info("To Armed Forces of Ukraine: "+forAFoU);
        log.info("For bar: "+(order.getToPay() - paymentForDevs - forAFoU));
        return true;
    }
}
