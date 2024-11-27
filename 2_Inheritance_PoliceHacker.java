package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App
{
    final static int MAX_PASSWORD = 9999;

    public static void main( String[] args )
    {
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();

        HackerThread ascHacker = new AscendingHacker(vault);
        HackerThread descHacker = new DescendingHacker(vault);
        Thread police = new Police();

        threads.add(ascHacker);
        threads.add(descHacker);
        threads.add(police);

        for (Thread thread: threads) {
            thread.start();
        }
    }

    private static class Vault {

        int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread {

        protected Vault vault;

        public HackerThread(final Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getName());
            // Setting max priority
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }

    private static class AscendingHacker extends HackerThread {

        public AscendingHacker(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int i=0; i<= MAX_PASSWORD; i++) {
                if (vault.isCorrectPassword(i)) {
                    System.out.println("Password Guessed by Asc hacker " + i);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHacker extends HackerThread {

        public DescendingHacker(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int i = MAX_PASSWORD; i >= MAX_PASSWORD; i--) {
                if (vault.isCorrectPassword(i)) {
                    System.out.println("Password Guessed by Desc hacker " + i);
                    System.exit(0);
                }
            }
        }
    }

    private static class Police extends Thread {
        @Override
        public void run() {

            for (int i = 10; i >= 1; i--) {
                try {
                    System.out.println(i);
                    Thread.sleep(1000);

                    if (i == 1) {
                        System.out.println("Police wins");
                        // To close entire application
                        System.exit(0);
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
