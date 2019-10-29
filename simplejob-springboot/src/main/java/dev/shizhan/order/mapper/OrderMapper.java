package dev.shizhan.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import dev.shizhan.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yanglikun
 * @since 2019-10-22
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    int updateOrderStatus(long orderId, int newStatus, int oldStatus);

    List<Order> selectTimeOutList(LocalDateTime timeoutTime, int shardingTotalCount, int shardingItem);

    List<Order> selectListByTime(LocalDateTime time);
}
