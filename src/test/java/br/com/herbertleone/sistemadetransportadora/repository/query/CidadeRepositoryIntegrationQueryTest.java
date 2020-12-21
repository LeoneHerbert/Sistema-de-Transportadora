package br.com.herbertleone.sistemadetransportadora.repository.query;

import br.com.herbertleone.sistemadetransportadora.model.Cidade;
import br.com.herbertleone.sistemadetransportadora.repository.CidadeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CidadeRepositoryIntegrationQueryTest {

    @Autowired
    private CidadeRepository cidadeRepository;

    @BeforeEach
    public void before() {
        Cidade cidade1 = new Cidade();
        cidade1.setNome("São Luís");
        cidade1.setTaxa(new BigDecimal(10));
        cidade1.setUf("MA");
        cidadeRepository.save(cidade1);

        Cidade cidade2 = new Cidade();
        cidade2.setNome("Salvador");
        cidade2.setTaxa(new BigDecimal(5));
        cidade2.setUf("BA");
        cidadeRepository.save(cidade2);

        Cidade cidade3 = new Cidade();
        cidade3.setNome("São Paulo");
        cidade3.setTaxa(new BigDecimal(15));
        cidade3.setUf("SP");
        cidadeRepository.save(cidade3);

        Cidade cidade4 = new Cidade();
        cidade4.setNome("Rio de Janeiro");
        cidade4.setTaxa(new BigDecimal(12));
        cidade4.setUf("RJ");
        cidadeRepository.save(cidade4);
    }

    @AfterEach
    public void after() {
        cidadeRepository.deleteAll();
    }


    @Test
    public void deveBuscarCidadePeloNome() {
        Cidade cidade = cidadeRepository.findByNome("Salvador");
        assertTrue(cidade.getNome().equals("Salvador"));
    }
}
