package dev.shizhan.jobs;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import dev.autoconfiguration.ElasticJob;
import dev.shizhan.order.entity.Order;
import dev.shizhan.order.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yanglikun
 */
//@ElasticJob(
//        jobName = "my-ordertimeout-simple-job",
//        cron = "0/5 * * * * ?",
//        shardingTotalCount = 1,
//        overwrite = true
//)
@Slf4j
public class OrderTimeOutJob implements SimpleJob {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void execute(ShardingContext shardingContext) {
        int shardingTotalCount = shardingContext.getShardingTotalCount();
        int shardingItem = shardingContext.getShardingItem();
        LocalDateTime timeoutTime = LocalDateTime.now().minusSeconds(10);
        List<Order> list = orderMapper.selectTimeOutList(
                timeoutTime,
                shardingTotalCount,
                shardingItem
        );
        log.info("需要处理超时的订单数为:" + list.size());
        list.forEach(o -> {
            int i = orderMapper.updateOrderStatus(o.getId(), 3, 1);
            log.info("更新订单 {} 为超时,结果:{}", o.getId(), i);
        });
    }

}
