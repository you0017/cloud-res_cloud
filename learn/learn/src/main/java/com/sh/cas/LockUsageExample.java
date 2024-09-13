package com.sh.cas;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockUsageExample {
    private final Lock lock = new ReentrantLock();
    private int counter = 0;

    public void incrementCounter() {
        lock.lock();
        try {
            counter++;
            System.out.println("Current value of counter: " + counter);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockUsageExample example = new LockUsageExample();
        Thread thread1 = new Thread(example::incrementCounter);
        Thread thread2 = new Thread(example::incrementCounter);
//        synchronized (example) {}
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}