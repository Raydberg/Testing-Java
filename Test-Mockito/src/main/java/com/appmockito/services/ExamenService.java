package com.appmockito.services;

import com.appmockito.models.Examen;

import java.util.Optional;

public interface ExamenService {
    Optional<Examen> findByName(String name);

    Examen findExamenByNameWithQuestion(String name);
    Examen save(Examen examen);
}
