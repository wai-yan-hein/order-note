package com.wg.os;

import org.junit.BeforeClass;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest
class OsApplicationTests {

    @BeforeClass
    public static void setupHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }

    @org.junit.Test
    public void contextLoads() {
    }

}
