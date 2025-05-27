import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        String importantInfo[] = new String[5000];
        for (int i = 0; i < 5000; i++) {
            importantInfo[i] = String.valueOf(i + 1);
        }

        Random random = new Random();

        for (int i = 0;
             i < importantInfo.length;
             i++) {
            drop.put(importantInfo[i]);
        }
        drop.put("DONE");
    }
}