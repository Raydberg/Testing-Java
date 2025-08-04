package com.appmockito.repositories;

import com.appmockito.models.Examen;

import java.util.List;

public interface ExamenRepository {
    List<Examen> findAll();
}
