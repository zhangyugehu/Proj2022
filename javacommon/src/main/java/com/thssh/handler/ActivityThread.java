package com.thssh.handler;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityThread {
    static Handler h;

    static final ExecutorService service = Executors.newCachedThreadPool();
    public static void main(String[] args) {
        Looper.prepareMainLooper();
        ActivityThread thread = new ActivityThread();
        thread.attach();
        Looper.loop();

    }

    static class H extends Handler {

        @Override
        public void handleMessage(Message msg) {
            System.out.println(Thread.currentThread().getName() + "[msg] what: " + msg.what + ", obj: " + msg.obj);
        }

    }

    private void attach() {
        h = new H();

        sendMessage(0, "hhhhahahaha");

        service.submit(() -> {
            while (true) {
                Random r = new Random();
                int sleep = (r.nextInt(10) + 1) * 800;
                int nextWhat = r.nextInt(1000);
                System.out.println(Thread.currentThread().getName() + " next [" + nextWhat + "] after " + sleep + "ms");
                Thread.sleep(sleep);
                byte[] bytes = new byte[100];
                r.nextBytes(bytes);
                sendMessage(nextWhat, null);
            }
        });

        System.out.println("attach end.");
    }

    private static void sendMessage(int what, Object obj) {
        Message message = new Message();
        message.what = what;
        message.obj = obj;
        h.sendMessage(message);
    }
}
