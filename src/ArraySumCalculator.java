import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.abs;

public class ArraySumCalculator {
    private static final int THREAD_COUNT = 4;
    private static final int ARRAY_SIZE = 10000;
    private static final int TASK_COUNT = 100;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        double[] array = new double[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = Math.round(Math.random() * 100);
        }

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        Future<Double>[] futures = new Future[TASK_COUNT];

        int elementsPerTask = ARRAY_SIZE / TASK_COUNT;

        for (int i = 0; i < TASK_COUNT; i++) {
            final int start = i * elementsPerTask;
            final int end = (i == TASK_COUNT - 1) ? ARRAY_SIZE : start + elementsPerTask;

            futures[i] = executor.submit(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    double sum = 0;
                    for (int j = start; j < end; j++) {
                        sum += array[j];
                    }
                    return sum;
                }
            });
        }

        double totalSum = 0;
        for (Future<Double> future : futures) {
            totalSum += future.get();
        }

        executor.shutdown();
        System.out.println("Загальна сума: " + totalSum);
    }
}