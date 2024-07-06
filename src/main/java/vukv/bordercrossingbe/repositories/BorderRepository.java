package vukv.bordercrossingbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vukv.bordercrossingbe.domain.entities.border.Border;

import java.util.UUID;

@Repository
public interface BorderRepository extends JpaRepository<Border, UUID>, QuerydslPredicateExecutor<Border> {

}
