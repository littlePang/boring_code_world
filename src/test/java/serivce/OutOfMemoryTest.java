package serivce;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by jaky on 11/15/16.
 */
public class OutOfMemoryTest {

    @Test
    public void oom() throws InterruptedException {
        List<String[]> list = Lists.newArrayList();
        for(;;) {
            list.add(new String[1]);
            TimeUnit.SECONDS.sleep(1);
        }

    }

}
