package serivce;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jaky on 7/29/16.
 */
public class ThreadTest {

    private String password;

    public void methodTest(String name) {

    }

    @Test
    public void fieldTest() throws NoSuchMethodException {
        Class<ThreadTest> clazz = ThreadTest.class;
        for (Field field : clazz.getFields()) {
            System.out.println(field.getName());
        }

        Method methodTest = clazz.getMethod("methodTest", String.class);
        Class<?>[] parameterTypes = methodTest.getParameterTypes();

    }

    @Test
    public void thread_start_test() throws InterruptedException {
        Executors.defaultThreadFactory().newThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("这里会自动启动");
            }
        }).start();

        System.out.println("sleep");
        TimeUnit.SECONDS.sleep(5);
        System.out.println("sleep down");
    }

    @Test
    public void schedule_delay_time_test() throws InterruptedException {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

        final long time1 = System.currentTimeMillis();

        scheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" job1 "+(System.currentTimeMillis()-time1));
                System.out.println("我执行第一个任务了");
            }
        }, 10, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(2);

        System.out.println("time1 "+(System.currentTimeMillis()-time1));

        scheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" job2 "+(System.currentTimeMillis()-time1));
                System.out.println("我执行第二个任务了");
            }
        }, 3, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(10);

    }

    @Test
    public void reentrant_lock_Interruptibly_test() throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        for (int i = 0; i < 1; i++) {
            final  int ii = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("get lock "+ii);
                        lock.lockInterruptibly();
                        System.out.println("get condition "+ii);
                        condition.await();
                        System.out.println("第"+ii+"个线程 ");


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }




        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("lock lock");
                lock.lock();
                System.out.println("signal condition");
                condition.signal();
                lock.unlock();
            }
        }).start();

        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void interruptibly_lock_test() throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lockInterruptibly();
                    System.out.println("lock interruptiby after");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("print something");
                    System.out.println("print something after");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);

        System.out.println("main lock before");
        lock.lock();
        System.out.println("main lock after");
        TimeUnit.SECONDS.sleep(10);


    }

}
