package dev.shizhan.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanglikun
 */
@Slf4j
public class MySimpleJob implements SimpleJob {

    public void execute(ShardingContext sc) {
        log.info("分片项:{},总分片项:{}", sc.getShardingItem(), sc.getShardingTotalCount());
    }

}
