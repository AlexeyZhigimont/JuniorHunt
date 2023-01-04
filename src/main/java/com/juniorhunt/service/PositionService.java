package com.juniorhunt.service;

import com.juniorhunt.repository.PositionRepository;
import org.springframework.stereotype.Service;

@Service
public class PositionService{
    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }
}
