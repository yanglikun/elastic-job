package dev.autoconfiguration;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.google.common.collect.ObjectArrays;
import dev.shizhan.jobs.listener.MyDistributeElastjobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Arrays;
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

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        dealSimpleJob();
        dealDataflowJob();
    }

    private void dealSimpleJob() {
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
            JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(jobAnn.jobName(), jobAnn.cron(), jobAnn
                    .shardingTotalCount()).build();
            SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, job.getClass()
                                                                                                                .getCanonicalName());
            LiteJobConfiguration.Builder builder = LiteJobConfiguration.newBuilder(simpleJobConfiguration);
            builder.overwrite(jobAnn.overwrite());
            builder.jobShardingStrategyClass(jobAnn.shardingStrategy()
                                                   .getCanonicalName());
            LiteJobConfiguration simpleJobRootConfig = builder
                    .build();

            ElasticJobListener[] elasticJobListeners = Arrays.stream(jobAnn.jobListener()).map(clz -> {
                try {
                    return clz.getConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).toArray(ElasticJobListener[]::new);

//            MyDistributeElastjobListener myDistributeElastjobListener = new MyDistributeElastjobListener(5000, 5000);
//            ElasticJobListener[] concat = ObjectArrays.concat(elasticJobListeners, myDistributeElastjobListener);

            if (jobAnn.jobEvent()) {
                JobEventRdbConfiguration jobEventRdbConfiguration = new JobEventRdbConfiguration(dataSource);
                new SpringJobScheduler(job, zookeeperRegistryCenter, simpleJobRootConfig, jobEventRdbConfiguration, elasticJobListeners).init();
            } else {
                new SpringJobScheduler(job, zookeeperRegistryCenter, simpleJobRootConfig, elasticJobListeners).init();
            }
        }
    }

    private void dealDataflowJob() {
        //获取所有加了ElasticJob的bean
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(EDataflowJob.class);
        Set<DataflowJob> jobs = beansWithAnnotation.values()
                                                   .stream()
                                                   .filter(s -> s instanceof DataflowJob)
                                                   .map(v -> (DataflowJob) v)
                                                   .collect(Collectors.toSet());
        //构建job注册到zk
        for (DataflowJob job : jobs) {
            EDataflowJob jobAnn = job.getClass().getAnnotation(EDataflowJob.class);
            JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(jobAnn.jobName(), jobAnn.cron(), jobAnn
                    .shardingTotalCount()).build();
            DataflowJobConfiguration simpleJobConfiguration = new DataflowJobConfiguration(jobCoreConfiguration, job.getClass()
                                                                                                                    .getCanonicalName(), jobAnn
                    .streamProcess());
            LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfiguration)
                                                                           .overwrite(jobAnn.overwrite())
                                                                           .jobShardingStrategyClass(jobAnn.shardingStrategy().getCanonicalName())
                                                                           .build();
            if (jobAnn.jobEvent()) {
                JobEventRdbConfiguration jobEventRdbConfiguration = new JobEventRdbConfiguration(dataSource);
                new SpringJobScheduler(job, zookeeperRegistryCenter, simpleJobRootConfig, jobEventRdbConfiguration).init();
            } else {
                new SpringJobScheduler(job, zookeeperRegistryCenter, simpleJobRootConfig).init();
            }
        }
    }

}
