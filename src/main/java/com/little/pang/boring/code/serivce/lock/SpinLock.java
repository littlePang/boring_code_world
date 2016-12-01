package com.little.pang.boring.code.serivce.lock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by jaky on 10/19/16.
 */
public class SpinLock {

    private AtomicReference<Thread> owner = new AtomicReference<Thread>();

    public void lock() {
        Thread currentThread = Thread.currentThread();
        while (!owner.compareAndSet(null, currentThread));
    }

    public void unlock() {
        owner.compareAndSet(Thread.currentThread(), null);
    }

    public static void main(String[] args) throws InterruptedException {
        final SpinLock lock = new SpinLock();
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " start acquire lock");
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " obtain lock");
                    lock.unlock();
                }
            }, "spin lock_thread_"+i).start();
        }

        TimeUnit.SECONDS.sleep(10);
    }

}
