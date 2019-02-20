package com.epam.spring.core.model;

public class Counter<T> {

    private T value;
    
    private String name;
    
    private int count;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Counter [value=" + value + ", name=" + name + ", count=" + count + "]";
    }


    
    }
