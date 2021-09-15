/**
 * 认识Callable，对Runnable进行了扩展
 * 对Callable的调用，可以有返回值
 */
package com.mashibing.juc.c_026_01_ThreadPool;

import java.util.concurrent.*;

public class T03_Callable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> c = new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);
                return "Hello Callable";
            }
        };

        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(c); //异步

        System.out.println(future.get());//阻塞
        System.out.println("submitted!");
        service.shutdown();
    }

}
