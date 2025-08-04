package com.appmockito.services;

import com.appmockito.models.Examen;
import com.appmockito.repositories.ExamenRepository;
import com.appmockito.repositories.QuestionRepository;

import java.util.List;
import java.util.Optional;

public class ExamenServiceImpl implements ExamenService {
    private final ExamenRepository repository;
    private final QuestionRepository questionRepository;

    public ExamenServiceImpl(ExamenRepository repository, QuestionRepository questionRepository) {
        this.repository = repository;
        this.questionRepository = questionRepository;
    }

    @Override
    public Optional<Examen> findByName(String name) {
        return repository
                .findAll()
                .stream()
                .filter(e -> name.equals(e.getName()))
                .findFirst();
    }

    @Override
    public Examen findExamenByNameWithQuestion(String name) {
        /**
         * Map de Optional solo transforma si esta presente
         */
        return findByName(name).map(examen -> {
            List<String> questions = questionRepository.findQuestionByExamenId(examen.getId());
            examen.setQuestions(questions);
            return examen;
        }).orElseThrow();
    }
}
