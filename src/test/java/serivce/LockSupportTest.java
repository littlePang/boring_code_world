package serivce;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by jaky on 8/10/16.
 */
public class LockSupportTest {

    @Test
    public void park_test() throws InterruptedException {
        final Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);

                    System.out.println("thread1 park before");
                    LockSupport.park();
                    System.out.println("thread1 park after");

                    System.out.println("thread1 park again before");
                    LockSupport.park();
                    System.out.println("thread1 park again after");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

//        final Thread t3 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("thread3 part before");
//                LockSupport.park(t1);
//                System.out.println("thread3 part after");
//            }
//        });

        final Thread t2 = new Thread(new Runnable() {
            public void run() {
//                System.out.println("thread2 sleep before");
                try {
//                    TimeUnit.SECONDS.sleep(5);

//                    System.out.println("thread2 sleep after");
                    System.out.println("thread2 unpark before");
                    LockSupport.unpark(t1);
                    LockSupport.unpark(t1);
                    System.out.println("thread2 unpark after");
//                    TimeUnit.SECONDS.sleep(2);
//                    System.out.println("thread2 unpark again before");
//                    LockSupport.unpark(t3);
//                    System.out.println("thread2 unpark again after");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        t1.start();
        t2.start();
//        t3.start();
        TimeUnit.SECONDS.sleep(10);
    }

}
