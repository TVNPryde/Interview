package com.fyllo.interview.common;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class PersistentQueue {
    private final AtomicInteger COUNTER = new AtomicInteger(0);
    private final String QUEUE_PROPERTY = "queue.size";
    private final File persistedFile = new File("queue.properties");

    private static PersistentQueue queue;

    private PersistentQueue() {
        try {
            if (persistedFile.exists()) {
                InputStream input = new FileInputStream(persistedFile);
                Properties prop = new Properties();
                prop.load(input);

                COUNTER.set(Integer.parseInt(prop.getProperty(QUEUE_PROPERTY)));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("On start queue size = " + COUNTER.get());
    }

    public static PersistentQueue init() {
        if (queue == null) {
            queue = new PersistentQueue();
        }

        return queue;
    }

    public int getQueue() {
        return COUNTER.get();
    }

    public int increment() {
        COUNTER.incrementAndGet();
        persistQueue();

        return COUNTER.get();
    }

    public int decrement() {
        COUNTER.decrementAndGet();
        persistQueue();

        return COUNTER.get();
    }

    private void persistQueue() {
        try (OutputStream output = new FileOutputStream(persistedFile)) {
            Properties prop = new Properties();
            prop.setProperty(QUEUE_PROPERTY, COUNTER.toString());
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
