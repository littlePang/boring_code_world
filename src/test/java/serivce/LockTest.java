package serivce;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jaky on 8/3/16.
 */
public class LockTest {

    ReentrantLock privateLock = new ReentrantLock();

    @Test
    public void lock_test() throws InterruptedException {
        Thread t1, t2;
        t1 = new Thread(new MyRunnable());
        t2 = new Thread(new MyRunnable());
        t1.start();
        t2.start();
//        TimeUnit.SECONDS.sleep(2);
        t1.interrupt();
        TimeUnit.SECONDS.sleep(10);

    }

    @Test
    public void lock_with_no_contention_test() {
        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();

    }

    @Test
    public void interrupt_test() throws InterruptedException {
        Thread t1 = new Thread(new InterruptRunbale(privateLock));
        privateLock.lock();
        t1.start();
        System.out.println(Thread.currentThread() + " wait");
        TimeUnit.SECONDS.sleep(3);
        System.out.println(Thread.currentThread() + " interrupt t1");
        t1.interrupt();
        System.out.println(Thread.currentThread() + " already interrupt t1");
        TimeUnit.SECONDS.sleep(3);
    }

    private static class MyRunnable implements Runnable {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        public void run() {
            try {
//                System.out.println(Thread.currentThread().getName() + " start");
//                System.out.println(Thread.currentThread().getName() + " lock before");
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " lock after");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " sleep down");

            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted");
            }

        }
    }

    private static class InterruptRunbale implements Runnable {

        private Lock lock;

        public InterruptRunbale(Lock lock) {
            this.lock = lock;
        }

        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " lock");
                lock.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + " lock interrupted");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
