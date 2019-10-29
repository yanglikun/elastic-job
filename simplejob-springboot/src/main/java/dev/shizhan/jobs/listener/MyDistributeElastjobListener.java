package dev.shizhan.jobs.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanglikun
 */
@Slf4j
public class MyDistributeElastjobListener extends AbstractDistributeOnceElasticJobListener {

    public MyDistributeElastjobListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
    }

    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
        log.info("分布式listener任务执行【前】,任务名称:{},taskId:{}", shardingContexts.getJobName(), shardingContexts.getTaskId());
    }

    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
        log.info("分布式listener任务执行【后】,任务名称:{},taskId:{}", shardingContexts.getJobName(), shardingContexts.getTaskId());
    }

}
