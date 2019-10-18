package dev.autoconfiguration;

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

}
