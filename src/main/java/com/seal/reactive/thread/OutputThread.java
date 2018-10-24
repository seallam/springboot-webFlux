package com.seal.reactive.thread;

/**
 * @author: seal
 * @Description:
 * @company: xingfeiinc
 * @e-mail: linxiao@xingfeiinc.com
 * @date: 2018-10-22 18:32
 */
public class OutputThread implements Runnable {

    private int num;
    private final Object lock;

    public OutputThread(int num, Object lock) {
        this.num = num;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (lock) {
                    lock.notifyAll();
                    lock.wait();
                    System.out.println(num);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class NotifyThread extends Thread {

        private Object lock;

        public NotifyThread(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {

        }
    }

    public static void main(String[] args) {
        final Object lock = new Object();

        Thread thread1 = new Thread(new OutputThread(1, lock));
        Thread thread2 = new Thread(new OutputThread(2, lock));
        Thread thread3 = new Thread(new OutputThread(3, lock));

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
