import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import dev.shizhan.job.MyDataflowJob;
import dev.shizhan.job.MySimpleJob;
import org.junit.Test;

/**
 * @author yanglikun
 */
public class DataflowJobTest extends BaseTest {

    @Test
    public void test() {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder("ylk-dataflow-demo", "0/5 * * * * ?", 2).build();
        DataflowJobConfiguration jobConfiguration = new DataflowJobConfiguration(jobCoreConfiguration, MyDataflowJob.class.getCanonicalName(), true);
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(jobConfiguration).overwrite(true).build();

        ZookeeperRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("127.0.0.1:2181", "elastic-job"));
        zookeeperRegistryCenter.init();

        new JobScheduler(zookeeperRegistryCenter, simpleJobRootConfig).init();
    }

}
