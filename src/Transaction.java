public class Transaction {
    private final int fromAccount;
    private final int toAccount;
    private final int amount;

    public Transaction(int fromAccount, int toAccount, int amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public int getFromAccount() {
        return fromAccount;
    }

    public int getToAccount() {
        return toAccount;
    }

    public int getAmount() {
        return amount;
    }
}
