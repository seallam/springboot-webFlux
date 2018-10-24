package com.seal.reactive.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author: seal
 * @Description:
 * @company: xingfeiinc
 * @e-mail: linxiao@xingfeiinc.com
 * @date: 2018-10-23 10:00
 */
public class CyclicBarrierTest {

    public static class StartGame implements Runnable {

        private int order;

        private CyclicBarrier barrier;

        public StartGame(int order, CyclicBarrier barrier) {
            this.order = order;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(order + "正在登录游戏");
                barrier.await();

                System.out.println(order + "正在选择角色");
                barrier.await();

                System.out.println(order + "正在加载游戏");
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(5, () -> {
            System.out.println("等待其他用户操作..");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("等待结束...");
        });
        Thread player01 = new Thread(new StartGame(1, barrier));
        Thread player02 = new Thread(new StartGame(2, barrier));
        Thread player03 = new Thread(new StartGame(3, barrier));
        Thread player04 = new Thread(new StartGame(4, barrier));
        Thread player05 = new Thread(new StartGame(5, barrier));

        player01.start();
        player02.start();
        player03.start();
        player04.start();
        player05.start();

    }
}
