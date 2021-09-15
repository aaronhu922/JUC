package interview;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {

    public static void main(String[] args) {
        Data3 data3 = new Data3();
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                data3.PrintA();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                data3.PrintB();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                data3.PrintC();
            }
        }).start();
    }
}

class Data3 {
    private char chars = 'A';
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void PrintA() {
        lock.lock();
        try {
            while (chars != 'A') {
                //等待
                condition1.await();
            }
            for (int i = 0; i < 20; i++) {
                System.out.print(chars);
            }

            chars = 'B';
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void PrintB() {
        lock.lock();
        try {
            while (chars != 'B') {
                condition2.await();
            }
            for (int i = 0; i < 20; i++) {
                System.out.print(chars);
            }
            chars = 'C';
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void PrintC() {
        lock.lock();
        try {
            while (chars != 'C') {
                condition3.await();
            }
            for (int i = 0; i < 20; i++) {
                System.out.print(chars);
            }
            chars = 'A';
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

