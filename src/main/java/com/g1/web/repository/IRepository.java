package com.g1.web.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class IRepository<T> {

    protected static final AtomicLong counter = new AtomicLong();

    static public String invalidOffsetMsg(int offset){
        String retval = "Offset must be >=0 (value: " + offset + ")";
        return retval;
    }

    static public String invalidLimitMsg(int limit){
        String retval = "Limit must be >=0 (value: "+limit+")";
        return retval;
    }

    private List<T> items = new ArrayList<T>();

    public IRepository<T> addItem(T item){
        items.add(item);
        return this;
    }

    public List<T> getItems() {
        return items;
    }

    public List<T> getItems(int offset, int limit) {
        if (offset < 0) throw new IllegalArgumentException(invalidOffsetMsg(offset));
        if (limit < 0) throw new IllegalArgumentException(invalidLimitMsg(limit));

        if (offset > 0) {
            if (offset >= items.size()) {
                return items.subList(0, 0); //empty list
            }
            if (limit > 0) {
                //apply offset and limit
                return items.subList(offset, Math.min(offset+limit, items.size()));
            } else {
                //apply just offset
                return items.subList(offset, items.size());
            }
        } else if (limit > 0) {
            //apply just limit
            return items.subList(0, Math.min(limit, items.size()));
        } else {
            return items.subList(0, items.size());
        }
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