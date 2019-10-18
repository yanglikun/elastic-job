package dev.shizhan;

import org.junit.After;

import java.util.concurrent.TimeUnit;

/**
 * @author yanglikun
 */
public class BaseTest {

    @After
    public void tearDown() throws InterruptedException {
        TimeUnit.HOURS.sleep(10);
    }

}
