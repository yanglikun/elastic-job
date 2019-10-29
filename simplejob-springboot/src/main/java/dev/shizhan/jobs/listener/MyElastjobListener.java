package dev.shizhan.jobs.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanglikun
 */
@Slf4j
public class MyElastjobListener implements ElasticJobListener {

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        log.info("任务执行【前】,任务名称:{},taskId:{}", shardingContexts.getJobName(), shardingContexts.getTaskId());
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        log.info("任务执行【后】,任务名称:{},taskId:{}", shardingContexts.getJobName(), shardingContexts.getTaskId());
    }

}
