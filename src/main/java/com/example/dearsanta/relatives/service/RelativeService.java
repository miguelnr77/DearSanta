package com.example.dearsanta.relatives.service;

import com.example.dearsanta.relatives.models.Relative;
import com.example.dearsanta.relatives.repositories.RelativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelativeService {

    @Autowired
    private RelativeRepository relativeRepository;

    public void addRelative(Relative relative) {
        relativeRepository.save(relative);
    }

    public void updateRelative(Relative relative) {
        relativeRepository.save(relative);
    }

    public void deleteRelative(Long id) {
        relativeRepository.deleteById(id);
    }

    public List<Relative> getRelativesByUserId(Long userId) {
        return relativeRepository.findByUserId(userId);
    }

    public List<Relative> findAll() {
        return relativeRepository.findAll();
    }
}
