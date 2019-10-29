package dev.shizhan;

import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * @author yanglikun
 */
public class ReferenceTest {

    @Test
    public void test() throws InterruptedException {
        WeakReference<String> a = new WeakReference<String>("aa");
        System.out.println("第一次执行:" + a.get());
        System.gc();
        TimeUnit.SECONDS.sleep(2);
        System.gc();
        TimeUnit.SECONDS.sleep(2);
        System.gc();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第二次执行:" + a.get());
        }).start();
        TimeUnit.SECONDS.sleep(20);
    }

}
