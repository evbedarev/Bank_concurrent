package unsynch;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private final double[] accounts;
    private Condition sufficientFunds;
    private Lock bankLock = new ReentrantLock();

    /**
     * Конструирует объект банка
     * @param countAcc Количество счетов
     * @param initialBalance Первоначальный остаток на каждом счете
     */
    public Bank(int countAcc, double initialBalance) {
        accounts = new double[countAcc];
        Arrays.fill(accounts,initialBalance);
        sufficientFunds = bankLock.newCondition();
    }

    /**
     * Переводит деньги с одного счета на другой
     * @param from Счет, с которого переводятся деньги
     * @param to Счет, на который переводятся деньги
     * @param amount Сумма перевода
     */
    public synchronized void transfer(int from, int to, double amount) {

        try {
            while (accounts[from] < amount)
                wait();
            System.out.println(Thread.currentThread().getName());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d \n", amount, from, to);
            accounts[to] += amount;
            notifyAll();
            System.out.printf(Thread.currentThread().getName() + " Total Balance: %10.2f\n", getTotalBalance());
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Получает сумму остатков на всех счетах
     * @return Возвращает общий баланс
     */

    public double getTotalBalance() {
        double sum = 0;
        for (double account: accounts)
            sum += account;
        return sum;
    }

    public int size() {
        return accounts.length;
    }

    public double getFrom (int from) {
        return accounts[from];
    }
}
