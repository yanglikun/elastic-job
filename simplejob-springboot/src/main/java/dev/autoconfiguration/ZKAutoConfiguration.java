package dev.autoconfiguration;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanglikun
 */
@Configuration
@ConditionalOnProperty(prefix = "ej.zk", name = {"server-lists", "namespace"})
@EnableConfigurationProperties(ZKProperties.class)
public class ZKAutoConfiguration {

    @Autowired
    private ZKProperties zkProperties;

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter() {
        return new ZookeeperRegistryCenter(
                new ZookeeperConfiguration(
                        zkProperties.getServerLists(),
                        zkProperties.getNamespace()
                )
        );
    }

}
