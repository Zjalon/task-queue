package com.jalon;

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedTaskQueue<T> extends LinkedBlockingDeque<Runnable> {
    public LinkedTaskQueue() {
        super();
    }
}
