package ru.javawebinar.basejava;

public class Deadlock {
    public static void main(String[] args) {
        final String lock1 = "lock1";
        final String lock2 = "lock2";
        lock(lock1, lock2);
        lock(lock2, lock1);
    }

    private static void lock(String lock1, String lock2) {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + Thread.currentThread().getState());
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + ": holds " + lock1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
                System.out.println(Thread.currentThread().getName() + ": wait " + lock2);
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + ": holds " + lock2);
                }
            }
        });
        thread.start();
    }
}
