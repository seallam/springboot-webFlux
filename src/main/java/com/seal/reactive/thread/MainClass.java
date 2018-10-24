package com.seal.reactive.thread;

/**
 * @author: seal
 * @Description:
 * @company: xingfeiinc
 * @e-mail: linxiao@xingfeiinc.com
 * @date: 2018-10-22 21:21
 */
class Cust {

    private int totalAmount = 1000;

    public synchronized void withdrwl(int amount) {
        System.out.println("Total amount " + totalAmount + " withdrwling amount " + amount);
        if (this.totalAmount < amount) {
            System.out.println("not enogh amount..waiting for deposite..");
            try {
                wait();
            } catch (Exception e) {
            }
        }
        this.totalAmount -= amount;
        System.out.println("Withrawl successful..Remaining balance is " + totalAmount);

    }

    public synchronized void deposite(int amount) {
        System.out.println("Depositing amount " + amount);
        this.totalAmount += amount;
        System.out.println("deposit completed...and Now totalAmount is " + this.totalAmount);
        notify();
    }
}

class Depo implements Runnable {
    Cust c;
    int depo;

    Depo(Cust c, int depo) {
        this.c = c;
        this.depo = depo;
    }

    @Override
    public void run() {
        c.deposite(depo);
    }

}

class Withdrawl implements Runnable {
    Cust c;
    int with;

    Withdrawl(Cust c, int with) {
        this.c = c;
        this.with = with;
    }

    @Override
    public void run() {
        c.withdrwl(with);
    }

}

public class MainClass {

    public static void main(String[] args) {
        Cust c = new Cust();
        Thread w = new Thread(new Withdrawl(c, 2000));
        Thread d = new Thread(new Depo(c, 1000));
        w.start();
        d.start();


    }

}
