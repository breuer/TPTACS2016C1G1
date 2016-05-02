package com.g1.web.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class IRepository<T> {

    protected static final AtomicLong counter = new AtomicLong();

    private List<T> items = new ArrayList<T>();

    public IRepository<T> addItem(T item){
        items.add(item);
        return this;
    }

    public List<T> getItems() {
        return items;
    }

    protected T getItem(Predicate<T> predicate) {
        Optional<T> opt = items.stream().filter(predicate::test).findFirst();

        return opt.orElseGet(() -> null);
    }

    protected List<T> filterItems(Predicate<T> predicate){
        return items.stream().filter(predicate::test).collect(Collectors.toList());
    }

    public abstract AtomicLong getCounter();
}