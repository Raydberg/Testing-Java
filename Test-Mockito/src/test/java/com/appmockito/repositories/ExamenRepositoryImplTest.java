package com.appmockito.repositories;

import com.appmockito.models.Examen;
import com.appmockito.services.ExamenService;
import com.appmockito.services.ExamenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExamenRepositoryImplTest {

    ExamenService service;
    ExamenRepository examenRepository;
    QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        //Pasamos como parametro la interfaz que vamos a
        // querer simular en este caso nuestro repository
        this.examenRepository = mock(ExamenRepository.class);
        this.questionRepository = mock(QuestionRepository.class);
        this.service = new ExamenServiceImpl(examenRepository, questionRepository);
    }


    @Test
    void findExamenPorNombre() {

        when(examenRepository.findAll()).thenReturn(Data.EXAMENS);
        Optional<Examen> examen = service.findByName("Microservicios");
        assertTrue(examen.isPresent());
        assertEquals(4L, examen.orElseThrow().getId());
        assertEquals("Microservicios", examen.orElseThrow().getName());
    }

    @Test
    void findExamenPorNombreEmpty() {
        List<Examen> datos = Collections.emptyList();
        when(examenRepository.findAll()).thenReturn(datos);
        Optional<Examen> examen = service.findByName("Microservicios");
        assertFalse(examen.isPresent());
    }

    @Test
    void testPreguntasExamen() {
     when(examenRepository.findAll()).thenReturn(Data.EXAMENS);
     when(questionRepository.findQuestionByExamenId(1L)).thenReturn(Data.QUESTIONS);

    }

}