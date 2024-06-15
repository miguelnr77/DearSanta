package com.example.dearsanta.list.repositories;

import com.example.dearsanta.list.models.GiftList;
import com.example.dearsanta.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftListRepository extends JpaRepository<GiftList, Long> {
    List<GiftList> findByUser(User user);
}
