import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.junit.Test;

/**
 * @author yanglikun
 */
public class ScriptJobTest extends BaseTest {

    @Test
    public void test() {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder("ylk-script-demo", "0/5 * * * * ?", 2).build();

        ScriptJobConfiguration jobConfiguration = new ScriptJobConfiguration(jobCoreConfiguration, "/export/servers/shell/myScript.sh");
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(jobConfiguration).overwrite(true).build();

        ZookeeperRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("127.0.0.1:2181", "elastic-job"));
        zookeeperRegistryCenter.init();

        new JobScheduler(zookeeperRegistryCenter, simpleJobRootConfig).init();
    }

}
