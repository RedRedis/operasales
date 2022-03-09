package ru.learnup.vtb.operasales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learnup.vtb.operasales.entities.Premiere;

@Repository
public interface PremiereRepository extends JpaRepository<Premiere, Long> {

    Premiere findByName(String name);
}
