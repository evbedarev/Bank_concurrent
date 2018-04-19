package unsynch;

import java.util.Arrays;

public class Bank {
    private final double[] accounts;

    /**
     * Конструирует объект банка
     * @param countAcc Количество счетов
     * @param initialBalance Первоначальный остаток на каждом счете
     */
    public Bank(int countAcc, double initialBalance) {
        accounts = new double[countAcc];
        Arrays.fill(accounts,initialBalance);
    }

    /**
     * Переводит деньги с одного счета на другой
     * @param from Счет, с которого переводятся деньги
     * @param to Счет, на который переводятся деньги
     * @param amount Сумма перевода
     */
    public void transfer(int from, int to, double amount) {
        if (accounts[from] < amount) return;
        System.out.println(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance: %10.2f\n", getTotalBalance());
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


}
