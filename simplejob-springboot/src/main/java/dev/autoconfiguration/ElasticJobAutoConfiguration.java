package dev.autoconfiguration;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yanglikun
 */
@Configuration
@ConditionalOnBean(ZookeeperRegistryCenter.class)
@AutoConfigureAfter(ZKAutoConfiguration.class)
public class ElasticJobAutoConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @PostConstruct
    public void init() {
        //获取所有加了ElasticJob的bean
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(ElasticJob.class);
        Set<SimpleJob> jobs = beansWithAnnotation.values()
                .stream()
                .filter(s -> s instanceof SimpleJob)
                .map(v -> (SimpleJob) v)
                .collect(Collectors.toSet());
        //构建job注册到zk
        for (SimpleJob job : jobs) {
            ElasticJob jobAnn = job.getClass().getAnnotation(ElasticJob.class);
            JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(jobAnn.jobName(), jobAnn.cron(), jobAnn.shardingTotalCount()).build();
            SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, job.getClass().getCanonicalName());
            LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(jobAnn.overwrite()).build();
            new JobScheduler(zookeeperRegistryCenter, simpleJobRootConfig).init();
        }

    }

}
