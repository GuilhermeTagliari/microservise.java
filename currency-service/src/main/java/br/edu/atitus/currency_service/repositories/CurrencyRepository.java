package br.edu.atitus.currency_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.atitus.currency_service.entities.CurrencyEntity;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long>{
	
	Optional<CurrencyEntity> findBySourceAndTarget(String source, String target);

}
