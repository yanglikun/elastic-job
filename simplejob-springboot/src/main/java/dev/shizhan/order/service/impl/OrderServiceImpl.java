package dev.shizhan.order.service.impl;

import dev.shizhan.order.entity.Order;
import dev.shizhan.order.mapper.OrderMapper;
import dev.shizhan.order.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yanglikun
 * @since 2019-10-22
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
