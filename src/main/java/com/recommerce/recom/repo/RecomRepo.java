package com.recommerce.recom.repo;

import com.recommerce.recom.entity.RecomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecomRepo extends JpaRepository<RecomEntity, Long> {

    Optional<RecomEntity> findByUsername(String username);

    Optional<RecomEntity> findByEmail(String email);

}
