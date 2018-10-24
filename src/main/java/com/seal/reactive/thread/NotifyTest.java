package com.seal.reactive.thread;

/**
 * @author: seal
 * @Description:
 * @company: xingfeiinc
 * @e-mail: linxiao@xingfeiinc.com
 * @date: 2018-10-22 20:40
 */
public class NotifyTest {

    private String flag[] = {"true"};

    private class NotifyThread extends Thread {
        public NotifyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (flag) {
                flag[0] = "false";
                flag.notifyAll();
            }
        }
    }

    private class WaitThread extends Thread {
        public WaitThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (flag) {
                while (!flag[0].equals("false")) {
                    System.out.println(getName() + " begin waiting..");
                    long waitTime = System.currentTimeMillis();
                    try {
                        flag.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waitTime = System.currentTimeMillis() - waitTime;
                    System.out.println("wait time : " + waitTime);
                }
                System.out.println(getName() + "end waiting");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("main thread run");
        NotifyTest test = new NotifyTest();
        NotifyThread notifyThread = test.new NotifyThread("notify01");
        WaitThread waitThread01 = test.new WaitThread("waiter01");
        WaitThread waitThread02 = test.new WaitThread("waiter02");
        WaitThread waitThread03 = test.new WaitThread("waiter03");

        notifyThread.start();

        waitThread01.start();
        waitThread02.start();
        waitThread03.start();
    }
}
