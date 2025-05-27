import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Bank {

    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;
    private final BlockingQueue<Transaction> transactionQueue;
    private Thread processorThread;
    private volatile boolean running = true;

    public Bank(int n, int initialBalance){
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        ntransacts = 0;
        transactionQueue = new LinkedBlockingQueue<>();
        startProcessingThread();
    }
    
    private void startProcessingThread() {
        processorThread = new Thread(() -> {
            try {
                while (running) {
                    Transaction transaction = transactionQueue.take();
                    processTransaction(transaction);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        processorThread.setDaemon(true);
        processorThread.start();
    }
    
    private synchronized void processTransaction(Transaction transaction) {
        int from = transaction.getFromAccount();
        int to = transaction.getToAccount();
        int amount = transaction.getAmount();
        
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0)
            test();
    }

    public void transfer(int from, int to, int amount)
            throws InterruptedException {
        Transaction transaction = new Transaction(from, to, amount);
        transactionQueue.put(transaction);
    }

    public synchronized void test(){
        int sum = 0;
        for (int i = 0; i < accounts.length; i++)
            sum += accounts[i];
        System.out.println("Transactions:" + ntransacts
                + " Sum: " + sum);
    }

    public int size(){
        return accounts.length;
    }
    
    public void shutdown() {
        running = false;
        processorThread.interrupt();
    }
}
