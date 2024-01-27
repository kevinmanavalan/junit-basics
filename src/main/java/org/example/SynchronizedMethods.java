package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.log4j.BasicConfigurator;
import org.testng.annotations.Test;
//import org.testng.Assert.assertEquals;

import static org.testng.Assert.assertEquals;

public class SynchronizedMethods {

    private static int sum = 0;

    public  void calculate() {
        synchronized (this){setSum(getSum() + 1);}
    }
    public static int getSum(){
        return sum;
    }
    public static void setSum(int newSum){
        sum = newSum;
    }
    @Test
    public void givenMultiThread_whenNonSyncMethod() throws InterruptedException {
        BasicConfigurator.configure();
        ExecutorService service = Executors.newFixedThreadPool(3);
        SynchronizedMethods summation = new SynchronizedMethods();

        IntStream.range(0, 1000)
                .forEach(count -> service.submit(()-> summation.calculate()));
        service.awaitTermination(1000, TimeUnit.MILLISECONDS);

        assertEquals(1000, summation.getSum());
    }
}