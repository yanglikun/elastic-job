package dev.autoconfiguration;

import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.api.strategy.JobShardingStrategy;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author yanglikun
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ElasticJob {

    String jobName();

    String cron();

    int shardingTotalCount();

    boolean overwrite();

    Class<? extends JobShardingStrategy> shardingStrategy() default AverageAllocationJobShardingStrategy.class;

    boolean jobEvent() default false;

    Class<? extends ElasticJobListener>[] jobListener() default {};

}
