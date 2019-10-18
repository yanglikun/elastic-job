import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import dev.shizhan.job.MySimpleJob;
import org.junit.Test;

/**
 * @author yanglikun
 */
public class SimpleJobTest extends BaseTest {

    @Test
    public void test() {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder("ylk-simple-demo", "0/3 * * * * ?", 2).build();
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, MySimpleJob.class.getCanonicalName());
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build();

        ZookeeperRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("127.0.0.1:2181", "elastic-job"));
        zookeeperRegistryCenter.init();

        new JobScheduler(zookeeperRegistryCenter, simpleJobRootConfig).init();
    }

}
