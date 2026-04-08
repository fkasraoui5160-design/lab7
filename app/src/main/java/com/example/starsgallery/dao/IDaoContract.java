package com.example.starsgallery.dao;

import java.util.List;

public interface IDaoContract<E> {
    boolean insert(E element);
    boolean modify(E element);
    boolean remove(E element);
    E fetchById(int identifier);
    List<E> fetchAll();
}