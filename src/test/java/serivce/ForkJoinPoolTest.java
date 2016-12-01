package serivce;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Created by jaky on 8/9/16.
 */
public class ForkJoinPoolTest {

    @Test
    public void fork_join_task_test() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.execute(new ForkJoinTask<Object>() {
            private static final long serialVersionUID = -1161824538646146044L;

            @Override
            public Object getRawResult() {
                return null;
            }

            @Override
            protected void setRawResult(Object value) {

            }

            @Override
            protected boolean exec() {
                return false;
            }
        });
    }

}
