package com.winterbe.java8.samples.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author Benjamin Winterberg
 */
public class Atomic1 {

    private static final int NUM_INCREMENTS = 1000;

    private static AtomicInteger atomicInt = new AtomicInteger(0);

    public static void main(String[] args) {
        testIncrement();
        testAccumulate();
        testUpdate();
    }

    private static void testUpdate() {
        atomicInt.set(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> {
                    //i=0时，提交一个线程对atomicInt的值进行+2操作到线程池中；…… => 2000
                    Runnable task = () ->
                            atomicInt.updateAndGet(n -> n + 2);// 0（atomicInt的初始值） + 2 + 2 + …… + 2 共加了1000次，因此是2000
                    executor.submit(task);
                });

        ConcurrentUtils.stop(executor);

        System.out.format("Update: %d\n", atomicInt.get());
    }

    private static void testAccumulate() {
        atomicInt.set(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> {
                    //查看accumulateAndGet及applyAsInt的源码可知，此处是将i作为右操作数参与 + 符号运算中的
                    Runnable task = () ->
                            atomicInt.accumulateAndGet(i, (n, m) -> n + m); //accumulateAndGet : 0(newValue) + 0(i) + 1(i) + 2(i) + ……
                    executor.submit(task);
                });

        ConcurrentUtils.stop(executor);

        System.out.format("Accumulate: %d\n", atomicInt.get());
    }

    private static void testIncrement() {
        atomicInt.set(0); // atomicInt - 0(newValue)

        ExecutorService executor = Executors.newFixedThreadPool(2); //2个线程的线程池ExecutorService

        IntStream.range(0, NUM_INCREMENTS) //loop 循环序列流 - [0,1000)  --  for (int i = startInclusive; i < endExclusive ; i++) { ... }
                // i= 0时 return newValue = 0(newValue) + 1 =》i= 1时 return newValue = 1(newValue) + 1
                // i= 2时 return newValue =2(newValue) + 1 =》……=》i= 999时 return newValue = 999(newValue) + 1 ，也即 1000
                .forEach(i -> executor.submit(atomicInt::incrementAndGet));

        ConcurrentUtils.stop(executor);

        System.out.format("Increment: Expected=%d; Is=%d\n", NUM_INCREMENTS, atomicInt.get());
    }

}
