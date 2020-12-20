package br.com.herbertleone.sistemadetransportadora.repository;

import br.com.herbertleone.sistemadetransportadora.model.Frete;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FreteRepository extends JpaRepository<Frete, Integer> {

   @Query(value = "From Frete where cliente_id = ?1")
   List<Frete> todos(Integer clienteId, Sort sort);

}
