package interview;

public class PhilosopherEatingProblem {
    public static void main(String[] args) {
        Fork fork = new Fork();
        Philosopher p1 = new Philosopher(0, fork);
        Philosopher p2 = new Philosopher(1, fork);
        Philosopher p3 = new Philosopher(2, fork);
        Philosopher p4 = new Philosopher(3, fork);
        Philosopher p5 = new Philosopher(4, fork);
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
    }


}

class Philosopher extends Thread {
    int index = 0;
    Fork fork;

    public Philosopher(int index, Fork fork) {
        this.index = index;
        this.fork = fork;
    }

    @Override
    public void run() {
        while (true) {
            thinking();
            fork.takeForks(index);
            eating();
            fork.putForks(index);
        }

    }

    private void eating() {
        System.out.println("The philosopher " + index + " is eating!");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void thinking() {
        System.out.println("The philosopher " + index + " is thinking!");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Fork {
    boolean[] forks = new boolean[]{false, false, false, false, false};

    public synchronized void takeForks(int index) {
        while (forks[index] || forks[(index + 1) % 5]) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        forks[index] = true;
        forks[(index + 1) % 5] = true;
    }

    public synchronized void putForks(int index) {
        forks[index] = false;
        forks[(index + 1) % 5] = false;
        notifyAll();
    }
}
