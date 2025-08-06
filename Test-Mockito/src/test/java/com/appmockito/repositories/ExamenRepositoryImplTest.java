package com.appmockito.repositories;

import com.appmockito.models.Examen;
import com.appmockito.services.ExamenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ExamenRepositoryImplTest {
    @Mock
    QuestionRepository questionRepository;
    @Mock
    ExamenRepository examenRepository;
    //Indicar que cree la instancia e inyecte los repositorios
    @InjectMocks//-> crea la instancia e inyecta los dos mocks anteriores
    ExamenServiceImpl service;

    @BeforeEach
    void setUp() {
        //Habilitamos el uso de anotaciones para esta clase
        MockitoAnnotations.openMocks(this);
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
        when(questionRepository.findQuestionByExamenId(anyLong())).thenReturn(Data.QUESTIONS);
        Examen examen = service.findExamenByNameWithQuestion("Programacion 2");
        assertEquals(5, examen.getQuestions().size());
        assertTrue(examen.getQuestions().contains("aritmetica"));
    }

    @Test
    void testPreguntasExamenVerify() {
        when(examenRepository.findAll()).thenReturn(Data.EXAMENS);
        when(questionRepository.findQuestionByExamenId(anyLong())).thenReturn(Data.QUESTIONS);
        Examen examen = service.findExamenByNameWithQuestion("Programacion 2");
        assertEquals(5, examen.getQuestions().size());
        assertTrue(examen.getQuestions().contains("aritmetica"));
        //Verificamos que se llama el metodo del repository
        verify(examenRepository).findAll();
        verify(questionRepository).findQuestionByExamenId(anyLong());
    }
    @Test
    void testGuardarExamen(){
        when(examenRepository.save(any(Examen.class))).thenReturn();
    }
}