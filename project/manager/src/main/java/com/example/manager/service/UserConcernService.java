package com.example.manager.service;

import com.example.manager.domain.concern.ConcernVO;
import com.example.manager.repository.UserConcernRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConcernService {

    private final UserConcernRepository userConcernRepository;

    public void addConcern(String userId, ConcernVO category) {
        userConcernRepository.save(userId, category);
    }

    public void deleteConcern(String userId, ConcernVO category) {
        userConcernRepository.deleteConcern(userId, category);
    }
}
