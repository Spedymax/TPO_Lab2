public class ThreadState {
    private volatile String state = "s";

    public static void main(String[] args) {
        ThreadState example = new ThreadState();

        // Потік А
        Thread threadA = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(500);
                    synchronized (example) {
                        if (example.state.equals("s")) {
                            example.state = "z";
                        } else {
                            example.state = "s";
                        }
                        example.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Потік В
        Thread threadB = new Thread(() -> {
            try {
                while (true) {
                    synchronized (example) {
                        while (!example.state.equals("s")) {
                            example.wait();
                        }

                        // Зворотний відлік
                        for (int i = 500; i >= 0; i--) {
                            System.out.println("Відлік: " + i);
                            Thread.sleep(1);

                            // Перевіряємо чи змінився стан
                            if (example.state.equals("z")) {
                                System.out.println("Потік В призупинено");
                                break;
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        threadA.start();
        threadB.start();
    }
}