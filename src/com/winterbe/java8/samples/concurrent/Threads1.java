package com.winterbe.java8.samples.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author Benjamin Winterberg
 */
public class Threads1 {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
      /*  String s1 = "ja";
        String s2 = "va";
        String s3 = "java";
        String s4 = s1 + s2; //编译器将+转换成StringBuilder，因此s4是一个StringBuilder的引用
        System.out.println(s3 == s4);//false
        System.out.println(s3.equals(s4));//true*/

        testczk1();

        int i = Integer.MAX_VALUE;
        System.out.println(i);
        System.out.println(i + 1 < i);
    }

    private static void test3() {
        Runnable runnable = () -> {
            try {
                System.out.println("Foo " + Thread.currentThread().getName() + " Time-" + System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(1);//TimeUnit类对Thread.sleep封装的sleep方法
                System.out.println("Bar " + Thread.currentThread().getName() + " Time-" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private static void test2() {
        Runnable runnable = () -> {
            try {
                System.out.println("Foo " + Thread.currentThread().getName() + " Time-" + System.currentTimeMillis());
                Thread.sleep(1000);//Thread.sleep暂停线程（不释放锁）
                System.out.println("Bar " + Thread.currentThread().getName() + " Time-" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private static void test1() {
        Runnable runnable = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        };

        runnable.run();

        Thread thread = new Thread(runnable);
        thread.start();//Thread实现了Runnable，且在start方法中调用本地方法start0，在VM中执行Thread的run方法（调用target.run方法，也即runnable的run方法）

        System.out.println("Done!");
    }


    private static void testczk1() {
        Thread t = new Thread() {
            public void run() {
                //加不加sleep方法t.run()的执行结果均是pong ping
                //加sleep方法t.start()执行结果ping pong，不加sleep方法t.start()执行结果ping pong或者pong ping
                /*try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                pong();
            }
        };
//        t.run(); //结果是pong ping run方法相当于普通方法的调用，仍然需要顺序执行且需要等待run方法执行完毕后才继续执行

        t.start();//结果是ping pong start方法只是启动线程无须等待run方法体代码执行完毕
        System.out.print("ping ");
    }
    static void pong() {
        System.out.print("pong ");
    }

}
