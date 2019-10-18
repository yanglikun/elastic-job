package dev.shizhan.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.google.common.collect.Lists;
import dev.shizhan.domain.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author yanglikun
 */
@Slf4j
public class MyDataflowJob implements DataflowJob<Order> {

    private List<Order> db = Lists.newArrayList();

    {
        for (long i = 0; i < 100; i++) {
            db.add(new Order(i + 1, 0));
        }
    }

    public List<Order> fetchData(final ShardingContext shardingContext) {
        List<Order> list = db.stream()
                .filter(o -> o.getStatus() == 0)
                .filter(
                        o -> o.getId() % shardingContext.getShardingTotalCount() == shardingContext.getShardingItem()
                ).limit(10).collect(Collectors.toList());
        List<Long> ids = list.stream().map(Order::getId).collect(Collectors.toList());
        sleep();
        log.info("我是分片:{},我抓取的id是:{}", shardingContext.getShardingItem(), ids);
        return list;
    }

    public void processData(ShardingContext shardingContext, List<Order> data) {
        data.forEach(o -> o.setStatus(1));
        sleep();
        log.info(
                "我是分片:{},我处理的id是:{}",
                shardingContext.getShardingItem(),
                data.stream().map(Order::getId).collect(Collectors.toList())
        );
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
