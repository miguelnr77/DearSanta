package com.example.dearsanta.relatives.service;

import com.example.dearsanta.relatives.models.Relative;
import com.example.dearsanta.relatives.repositories.RelativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RelativeService {

    private final RelativeRepository relativeRepository;

    @Autowired
    public RelativeService(RelativeRepository relativeRepository) {
        this.relativeRepository = relativeRepository;
    }

    public List<Relative> getRelativesByUserId(Long userId) {
        return relativeRepository.findByUserId(userId);
    }

    public Relative saveRelative(Relative relative) {
        return relativeRepository.save(relative);
    }

    @Transactional
    public void deleteRelative(Long id) {
        Relative relative = relativeRepository.findById(id).orElse(null);
        if (relative != null) {
            relativeRepository.delete(relative);
        }
    }
}
