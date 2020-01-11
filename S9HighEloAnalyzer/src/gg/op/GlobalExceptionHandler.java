package gg.op;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("Thread " + t.getName() + " has thrown an exception! Quiting the Java VM now!");
        e.printStackTrace();
        System.exit(-1);
    }
}
