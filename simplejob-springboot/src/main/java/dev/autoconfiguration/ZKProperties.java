package dev.autoconfiguration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor
 * @author yanglikun
 */
@ConfigurationProperties(prefix = "ej.zk")
@Data
public class ZKProperties {

    private String serverLists;

    private String namespace;

}
