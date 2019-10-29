package dev.autoconfiguration;

import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.api.strategy.JobShardingStrategy;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author yanglikun
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface EDataflowJob {

    String jobName();

    String cron();

    int shardingTotalCount();

    boolean overwrite();

    boolean streamProcess();

    Class<? extends JobShardingStrategy> shardingStrategy() default MyCustomJobShardingStrategy.class;

    boolean jobEvent() default false;

    Class<? extends ElasticJobListener>[] jobListener() default {};

}
