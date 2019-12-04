package com.navercorp.navercodelab.practice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface AbsTwoImage {
    ExecutorService executor = Executors.newFixedThreadPool(4);
}
