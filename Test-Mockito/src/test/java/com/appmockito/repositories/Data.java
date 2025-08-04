package com.appmockito.repositories;

import com.appmockito.models.Examen;

import java.util.Arrays;
import java.util.List;

public class Data {
    public final static List<Examen> EXAMENS = Arrays.asList(
            new Examen(1L, "Programacion 2"),
            new Examen(2L, "Pruebas de Software"),
            new Examen(3L, "Patrones de Diseño"),
            new Examen(4L, "Microservicios"),
            new Examen(5L, "Aplicaciones Multiplataforma"),
            new Examen(6L, "Arquitectura de Software")
    );

    public final static List<String> QUESTIONS = Arrays.asList(
            "aritmetica", "integrales", "derivadas", "trigonometria", "geometria"
    );

}
