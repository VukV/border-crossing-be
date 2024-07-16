package vukv.bordercrossingbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vukv.bordercrossingbe.domain.entities.bordercrossing.BorderCrossing;

import java.util.UUID;

@Repository
public interface BorderCrossingRepository extends JpaRepository<BorderCrossing, UUID>, QuerydslPredicateExecutor<BorderCrossing> {

}
