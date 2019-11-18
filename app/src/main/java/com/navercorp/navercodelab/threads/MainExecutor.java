package com.navercorp.navercodelab.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class MainExecutor  {
    static MainExecutor instance = null;

    public static MainExecutor getInstance() {

        return instance;
    }


    public ExecutorService createPool() {

        return Executors.newFixedThreadPool(4);
    }

    public ExecutorService createFJPool() {
        return ForkJoinPool.commonPool();
    }
}