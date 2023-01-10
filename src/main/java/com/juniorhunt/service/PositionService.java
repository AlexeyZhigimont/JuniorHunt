package com.juniorhunt.service;

import com.juniorhunt.model.Position;
import com.juniorhunt.repository.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {


    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public void save(String positionName) {
        final Position position = new Position(positionName);
        positionRepository.save(position);
    }

    public List<String> findAll() {
        return positionRepository.findAll().stream().map(Position::getPosition).toList();
    }
}
