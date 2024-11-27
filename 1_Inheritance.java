package org.example;

public class App 
{
    public static void main( String[] args )
    {

        Thread thread = new NewThread();

        thread.start();
    }

    private static class NewThread extends Thread {
        @Override
        public void run() {
            this.setName("My first thread");
            System.out.println("Stated new thread " + this.getName());
        }
    }
}
