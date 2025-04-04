package ru.javawebinar.basejava;

public class Deadlock {
    public static void main(String[] args) {
        final Object lock1 = new Object();
        final Object lock2 = new Object();

        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + Thread.currentThread().getState());
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + ": holds lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
                System.out.println(Thread.currentThread().getName() + ": wait lock2");
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + ": holds  lock1, lock2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + Thread.currentThread().getState());
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + ": holds lock2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
                System.out.println(Thread.currentThread().getName() + ": wait lock1");
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + ": holds  lock1, lock2");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
