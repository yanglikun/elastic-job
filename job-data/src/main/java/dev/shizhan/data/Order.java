package dev.shizhan.data;

import lombok.Data;

/**
 * @author yanglikun
 */
@Data
public class Order {
    private Long id;
    private OrderStatus status;

    public Order(Long id, OrderStatus status) {
        this.id = id;
        this.status = status;
    }
}
