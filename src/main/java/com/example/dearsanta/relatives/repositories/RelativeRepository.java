package com.example.dearsanta.relatives.repositories;

import com.example.dearsanta.relatives.models.Relative;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RelativeRepository extends JpaRepository<Relative, Long> {
    List<Relative> findByUserId(Long userId);
}
