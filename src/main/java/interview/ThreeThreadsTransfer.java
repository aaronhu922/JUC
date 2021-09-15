package interview;

import java.util.concurrent.locks.LockSupport;

public class ThreeThreadsTransfer {
    static Thread t1 = null, t2 = null, t3 = null;

    public static void main(String[] args) throws Exception {


        t1 = new Thread(() -> {
            System.out.println("A");
            for(int i=1;i<=500;i++) {
                System.out.print(i+" ");
            }
            System.out.println(" ");
            LockSupport.unpark(t2); //叫醒T2
            LockSupport.park(); //T1阻塞
            System.out.println("A");
            for(int i=501;i<=1000;i++) {
                System.out.print(i+" ");
            }
            System.out.println(" ");
            LockSupport.unpark(t2); //叫醒T2

        }, "t1");

        t2 = new Thread(() -> {
            LockSupport.park(); //T2阻塞
            System.out.println("B");
            for(int i=1;i<=500;i++) {
                System.out.print(i+" ");
            }
            System.out.println(" ");
            LockSupport.unpark(t3); //叫醒T3
            LockSupport.park(); //T2阻塞
            System.out.println("B");
            for(int i=501;i<=1000;i++) {
                System.out.print(i+" ");
            }
            System.out.println(" ");
            LockSupport.unpark(t3); //叫醒T2

        }, "t2");

        t3 = new Thread(() -> {
            LockSupport.park(); //T2阻塞

            System.out.println("C");
            for(int i=1;i<=500;i++) {
                System.out.print(i+" ");
            }
            System.out.println(" ");
            LockSupport.unpark(t1); //叫醒T2
            LockSupport.park(); //T3阻塞
            System.out.println("C");
            for(int i=501;i<=1000;i++) {
                System.out.print(i+" ");
            }

        }, "t3");


        t1.start();
        t2.start();
        t3.start();
    }
}
