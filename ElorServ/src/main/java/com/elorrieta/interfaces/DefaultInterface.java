package com.elorrieta.interfaces;

import java.util.List;

public interface DefaultInterface<T> {
    T getById(int id);

    List<T> getAll();

    void add(T entity);

    void update(T entity);

    void delete(int id);

}
