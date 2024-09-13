package com.sh.cas;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    private Lock lock = new ReentrantLock();

    public void performAction() {
        lock.lock();
        try {
            // 受锁保护的代码块
            System.out.println("Thread " + Thread.currentThread().getName() + " is in critical section.");
        } finally {
            lock.unlock();
        }
    }


    public void performActionWithTryLock() {
        if (lock.tryLock()) {
            try {
                // 成功获取锁后的代码
                System.out.println("Thread " + Thread.currentThread().getName() + " got the lock.");
            } finally {
                lock.unlock();
            }
        } else {
            // 未能获取锁时的处理
            System.out.println("Thread " + Thread.currentThread().getName() + " couldn't get the lock.");
        }
    }


    public void performActionWithInterruptibleLock() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            // 受锁保护的代码
            System.out.println("Thread " + Thread.currentThread().getName() + " is in critical section.");
        } finally {
            lock.unlock();
        }
    }

}