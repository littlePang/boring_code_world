package com.little.pang.boring.code.serivce.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by jaky on 10/20/16.
 */
public class CLHLock {

    private AtomicReference<Node> head = new AtomicReference<Node>();
    private AtomicReference<Node> tail = new AtomicReference<Node>();

    public CLHLock() {
    }

    public void lock() {
        Node currentThreadNode = new Node(Thread.currentThread());

        for (;;) {
            Node t = tail.get();
            currentThreadNode.pre = t;
            if (tail.compareAndSet(t, currentThreadNode)) {
                if (t == null) {
                    head.compareAndSet(null, currentThreadNode);
                    return; // 当前节点为第一个申请锁的节点,无需等待其他节点释放锁
                } else {
                    t.next = currentThreadNode;
                    break;
                }
            }
        }

        // 等待前面的节点释放锁
        while (currentThreadNode.pre.locked) ;
    }

    public void unlock() {
        Node currentLockOwner = head.get();

        // 只有当前线程为持有锁的线程才能释放锁
        if (currentLockOwner == null || !Thread.currentThread().equals(currentLockOwner.thread)) {
            return;
        }

        // 释放当前线程持有的锁
        currentLockOwner.locked = false;

        // 从队列中剔除线程
        while (!head.compareAndSet(currentLockOwner, currentLockOwner.next));

        currentLockOwner.pre = null; // help GC

    }

    private static class Node {
         volatile Node pre;
         volatile Node next;
         volatile Thread thread;
         volatile boolean locked = true;

         Node(Thread thread) {
            this.thread = thread;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        final CLHLock lock = new CLHLock();
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " start acquire lock");
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " obtain lock");
                    lock.unlock();
                }
            }, "clh_lock_thread_"+i).start();
        }

        TimeUnit.SECONDS.sleep(10);
    }

}
