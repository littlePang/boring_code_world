package com.little.pang.boring.code.serivce.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by jaky on 10/19/16.
 */
public class MCSLock {

    private AtomicReference<Node> head = new AtomicReference<Node>();
    private AtomicReference<Node> tail = new AtomicReference<Node>();

    public void lock() {
        Node nowNode = new Node(Thread.currentThread());

        for(;;){

            Node t = tail.get();
            if (tail.compareAndSet(t, nowNode)) { // 将当前线程放入等待队列
                if (t == null) {// 如果节点前面没有其他等待锁的节点,则当前线程直接获取到锁
                    nowNode.locked = false;
                    head.compareAndSet(null, nowNode);
                } else { // 否则,将其放入等待队列中
                    t.next = nowNode;
                }
                break;
            }
        }

        while (nowNode.locked);
    }

    public void unlock() {

        Node nowLockOwner = head.get(); // 当前获得锁的线程必然是锁队列中第一个节点
        if (nowLockOwner == null || Thread.currentThread().equals(nowLockOwner.thread)) { // 只有获取锁的线程才能释放锁
            return;
        }

        // 从锁队列中剔除当前线程
        head.set(nowLockOwner.next);
        if (nowLockOwner.next != null) {
            nowLockOwner.next.locked = false;
        }
    }


    private static class Node {
        public volatile Node next;
        public volatile Thread thread;
        public volatile boolean locked = true;

        public Node(Thread thread) {
            this.thread = thread;
        }

        public static void main(String[] args) throws InterruptedException {
            final MCSLock lock = new MCSLock();
            for (int i = 0; i < 100; i++) {
                new Thread(new Runnable() {
                    public void run() {
                        System.out.println(Thread.currentThread().getName() + " start acquire lock");
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + " obtain lock");
                        lock.unlock();
                    }
                }, "mcs_lock_thread_"+i).start();
            }

            TimeUnit.SECONDS.sleep(10);
        }

    }
}
