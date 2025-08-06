package com.appmockito.repositories;


import java.util.List;

public interface QuestionRepository {
    List<String> findQuestionByExamenId(Long id);
    void saveMost(List<String> questios);
}
