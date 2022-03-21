package ru.learnup.vtb.operasales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learnup.vtb.operasales.repositories.entities.PremiereEntity;

@Repository
public interface PremiereRepository extends JpaRepository<PremiereEntity, Long> {

    PremiereEntity findByName(String name);
}
