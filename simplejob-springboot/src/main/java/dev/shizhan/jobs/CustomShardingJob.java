package dev.shizhan.jobs;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import dev.autoconfiguration.ElasticJob;
import dev.autoconfiguration.MyCustomJobShardingStrategy;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanglikun
 */
//@ElasticJob(
//        jobName = "customsharding-job",
//        cron = "0/10 * * * * ?",
//        shardingTotalCount = 7,
//        overwrite = true,
//        shardingStrategy = MyCustomJobShardingStrategy.class
//)
@Slf4j
public class CustomShardingJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info(
                "总分片数:{},当前分片:{}",
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem()
        );
    }
}
