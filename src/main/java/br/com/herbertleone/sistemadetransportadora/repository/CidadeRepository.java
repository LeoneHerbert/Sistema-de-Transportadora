package br.com.herbertleone.sistemadetransportadora.repository;

import br.com.herbertleone.sistemadetransportadora.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    Cidade findByNome(String nome);
}
