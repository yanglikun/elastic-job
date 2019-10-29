package dev.shizhan.order.mapper;

import com.alibaba.fastjson.JSON;
import dev.shizhan.DemoApplicationTests;
import dev.shizhan.order.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yanglikun
 */
@Slf4j
public class OrderMapperTest extends DemoApplicationTests {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void insertTest() {
        Order order = new Order();
        order.setUsername("name-1");
        order.setTotalAmount(10L);
        order.setCreated(LocalDateTime.now());
        order.setModified(LocalDateTime.now());
        orderMapper.insert(order);
    }

    @Test
    public void updateOrderStatusTest() {
        int oldStatus = 1;
        int newStatus = 3;
        long orderId = 2;
        int count = orderMapper.updateOrderStatus(orderId, newStatus, oldStatus);
        log.info("count->" + count);
    }

    @Test
    public void selectAllTest() {
        List<Order> orders = orderMapper.selectList(null);
        System.out.println(JSON.toJSONString(orders));
    }

    @Test
    public void selectAllTestByTime() {
        List<Order> orders = orderMapper.selectListByTime(LocalDateTime.now().minusSeconds(10));
        System.out.println(JSON.toJSONString(orders));
    }

}