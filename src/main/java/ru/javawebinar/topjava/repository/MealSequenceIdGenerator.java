package ru.javawebinar.topjava.repository;

import java.util.concurrent.atomic.AtomicInteger;

public final class MealSequenceIdGenerator {
    private static final AtomicInteger sequence = new AtomicInteger(1);
    
    private MealSequenceIdGenerator() {
    }
    
    public static int generate() {
        return sequence.getAndIncrement();
    }
}
