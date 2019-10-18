package dev.shizhan.domain;

import lombok.Data;

/**
 * @author yanglikun
 */
@Data
public class Order {
    private Long id;
    private Integer status;

    public Order(Long id, Integer status) {
        this.id = id;
        this.status = status;
    }
}
