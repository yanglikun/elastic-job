package dev.autoconfiguration;

import com.dangdang.ddframe.job.lite.api.strategy.JobInstance;
import com.dangdang.ddframe.job.lite.api.strategy.JobShardingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 轮训获取
 * 2个服务器,7个分片： 0=[0,2,4,6],1=[1,3,5]
 * 3个服务器,7个分片: 0=[0,3,6], 1=[1,4],2=[2,5]
 *
 * @author yanglikun
 */
public class MyCustomJobShardingStrategy implements JobShardingStrategy {

    @Override
    public Map<JobInstance, List<Integer>> sharding(List<JobInstance> jobInstances, String jobName, int shardingTotalCount) {
        int instanceSize = jobInstances.size();
        Map<JobInstance, List<Integer>> retMap = new HashMap<>(instanceSize);
        for (int shardingIdx = 0; shardingIdx < shardingTotalCount; shardingIdx++) {
            JobInstance jobInstance = jobInstances.get(shardingIdx % instanceSize);
            List<Integer> list = retMap.get(jobInstance);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(shardingIdx);
            retMap.put(jobInstance, list);
        }
        return retMap;
    }

}
