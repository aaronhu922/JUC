package interview;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreeThreadsDemo1 {
    public static void main(String[] args) {
        ShareResource share = new ShareResource();
        new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                share.printAA(i);
            }
        }, "AA").start();

        new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                share.printBB(i);
            }
        }, "BB").start();

        new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                share.printCC(i);
            }
        }, "CC").start();
    }
}

//1. 创建资源类
//2. 定义标志位
//3. 创建lock锁

class ShareResource {
    private int flag = 1; //1 AA 2 BB 3 CC
    private Lock lock = new ReentrantLock();

    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void printAA(int loop) {
        lock.lock();
        try {
            while (flag != 1) {
                c1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :loop " + loop);
            }
            flag = 2;
            c2.signal();
//            c1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printBB(int loop) {
        lock.lock();
        try {
            while (flag != 2) {
                c2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :loop " + loop);
            }
            flag = 3;
            c3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printCC(int loop) {
        lock.lock();
        try {
            while (flag != 3) {
                c3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :loop " + loop);
            }
            flag = 1;
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}
