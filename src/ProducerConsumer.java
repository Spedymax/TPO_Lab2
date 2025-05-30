import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {
    private final BlockingQueue<String> buffer = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();

        Thread threadA = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    String item = "Обєкт " + i;
                    pc.buffer.put(item);
                    System.out.println("Додано: " + item);
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                while (true) {
                    String item = pc.buffer.take();
                    System.out.println("Потік В споживає: " + item);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                while (true) {
                    String item = pc.buffer.take();
                    System.out.println("Потік С споживає: " + item);
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
    }
}