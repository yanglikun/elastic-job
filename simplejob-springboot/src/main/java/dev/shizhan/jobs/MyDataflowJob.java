package dev.shizhan.jobs;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import dev.shizhan.data.DB;
import dev.shizhan.data.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author yanglikun
 */
@Slf4j
//@EDataflowJob(
//        jobName = "my-dataflow-job",
//        cron = "0/20 * * * * ?",
//        shardingTotalCount = 2,
//        overwrite = true,
//        streamProcess = true
//)
public class MyDataflowJob implements DataflowJob<Order> {

    private DB db = DB.create(20);

    @Override
    public List<Order> fetchData(final ShardingContext shardingContext) {
        List<dev.shizhan.data.Order> orders = db.selectAllInit(3);
        List<dev.shizhan.data.Order> shouldDealList = orders.stream()
                                                            .filter(o -> o.getId() % shardingContext.getShardingTotalCount() == shardingContext
                                                                    .getShardingItem())
                                                            .collect(Collectors.toList());
        List<Long> ids = shouldDealList.stream().map(Order::getId).collect(Collectors.toList());
        sleep(1);
        log.info("我是分片:{},我抓取的id是:{}", shardingContext.getShardingItem(), ids);
        return shouldDealList;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Order> data) {
        sleep(1);
        data.forEach(o -> {
            db.update2Done(o.getId());
        });
        log.info(
                "我是分片:{},我处理的id是:{}",
                shardingContext.getShardingItem(),
                data.stream().map(Order::getId).collect(Collectors.toList())
        );
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
