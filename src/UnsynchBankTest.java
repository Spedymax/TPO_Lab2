public class UnsynchBankTest {
    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) {
        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        Thread[] threads = new Thread[NACCOUNTS];
        
        for (int i = 0; i < NACCOUNTS; i++){
            TransferThread t = new TransferThread(b, i,
                    INITIAL_BALANCE);
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start();
            threads[i] = t;
        }
        
        // Додавання обробки завершення програми за ключовим вводом
        System.out.println("Натисніть Enter для завершення...");
        try {
            System.in.read();
            for (Thread t : threads) {
                t.interrupt();
            }
            b.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
