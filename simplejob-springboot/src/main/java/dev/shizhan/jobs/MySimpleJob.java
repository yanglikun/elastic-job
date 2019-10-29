package dev.shizhan.jobs;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import dev.autoconfiguration.ElasticJob;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author yanglikun
 */
@ElasticJob(
        jobName = "my-simple-job",
        cron = "0/10 * * * * ?",
        shardingTotalCount = 2,
        overwrite = true,
        jobEvent = false
//        jobListener = MyElastjobListener.class
)
@Slf4j
public class MySimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(
                "simpleJob,分片总数:{},当前分片:{}",
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem()
        );
    }
}
