package de.amitc.springcloudstreamsnotifier.messaging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * HangoutsServiceTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HangoutsServiceTest {

    @Autowired
    HangoutsService hangoutsService;

    @Test
    public void testSending() {
        hangoutsService.sendToChat("ASD");
    }
}