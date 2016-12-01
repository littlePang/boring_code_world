package com.little.pang.boring.code.serivce.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jaky on 10/19/16.
 */
public class TicketSpinLock {
    private AtomicInteger ticketNum = new AtomicInteger(0);
    private AtomicInteger lockedTicketNum = new AtomicInteger(0);

    private ThreadLocal<Integer> currentThreadTicket = new ThreadLocal<Integer>();

    public void lock() {
        Integer currentThreadTicketNum = ticketNum.getAndIncrement();
        currentThreadTicket.set(currentThreadTicketNum);
        while (lockedTicketNum.get() != currentThreadTicketNum);
    }

    public void unlock() {
        // 当前线程为获取到目前票号的线程,才能解锁使下一个线程获得锁
        Integer currentTicket = currentThreadTicket.get();
        lockedTicketNum.compareAndSet(currentTicket, currentTicket + 1);
    }
}
