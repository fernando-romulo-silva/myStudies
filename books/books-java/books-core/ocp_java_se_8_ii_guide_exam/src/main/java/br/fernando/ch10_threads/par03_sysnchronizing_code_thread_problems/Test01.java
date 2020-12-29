package br.fernando.ch10_threads.par03_sysnchronizing_code_thread_problems;

public class Test01 {

    // =========================================================================================================================================
    // Synchronizing Code, Thread Problems
    private static Account acct = new Account();

    static void test01_01() {
        AccountDanger01 r = new AccountDanger01();

        Thread one = new Thread(r);
        one.setName("Fred");

        Thread two = new Thread(r);
        two.setName("Lucy");

        one.start();
        two.start();
    }

    public static class AccountDanger01 implements Runnable {

        @Override
        public void run() {
            for (int x = 0; x < 5; x++) {
                makeWithdrawl(10);

                if (acct.getBalance() < 0) {
                    System.out.println("account is overdrawn!");
                }
            }
        }

        private void makeWithdrawl(int amt) {

            if (acct.getBalance() >= amt) {
                System.out.println(Thread.currentThread().getName() + " is going to withdraw.");

                try {
                    Thread.sleep(500);
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                acct.withdraw(amt);

                System.out.println(Thread.currentThread().getName() + " completes thw withdrawl.");
            } else {
                System.out.println("Not enough in account for " + Thread.currentThread().getName() + " to withdraw " + acct.getBalance());
            }
        }
    }

    static class Account {

        private int balance = 50;

        public int getBalance() {
            return balance;
        }

        public void withdraw(int amount) {
            balance = balance - amount;
        }
    }

    // =========================================================================================================================================
    // Preventing the Account Overdraw
    static void test01_02() {
        // So what can be done? The solution is actually quite simple. We must guarantee that the two steps of the withdrawal — checking the
        // balance and making the withdrawal — are never split apart

        AccountDanger02 r = new AccountDanger02();

        Thread one = new Thread(r);
        one.setName("Fred");

        Thread two = new Thread(r);
        two.setName("Lucy");

        one.start();
        two.start();

    }

    public static class AccountDanger02 implements Runnable {

        @Override
        public void run() {
            for (int x = 0; x < 5; x++) {
                makeWithdrawl(10);

                if (acct.getBalance() < 0) {
                    System.out.println("account is overdrawn!");
                }
            }
        }

        // Now we’ve guaranteed that once a thread (Lucy or Fred) starts the withdrawal
        // process by invoking makeWithdrawal(), the other thread cannot enter that method
        // until the first one completes the process by exiting the method
        private synchronized void makeWithdrawl(int amt) {

            if (acct.getBalance() >= amt) {
                System.out.println(Thread.currentThread().getName() + " is going to withdraw.");

                try {
                    Thread.sleep(500);
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                acct.withdraw(amt);

                System.out.println(Thread.currentThread().getName() + " completes thw withdrawl.");
            } else {
                System.out.println("Not enough in account for " + Thread.currentThread().getName() + " to withdraw " + acct.getBalance());
            }
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_02();
    }
}
