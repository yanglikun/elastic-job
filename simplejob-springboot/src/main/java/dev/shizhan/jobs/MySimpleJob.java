package dev.shizhan.jobs;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import dev.autoconfiguration.ElasticJob;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanglikun
 */
//@ElasticJob(
//        jobName = "my-simple-job",
//        cron = "0/3 * * * * ?",
//        shardingTotalCount = 3,
//        overwrite = true
//)
@Slf4j
public class MySimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info(
                "simpleJob,分片总数:{},当前分片:{}",
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem()
        );
    }
}
