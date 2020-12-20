package br.com.herbertleone.sistemadetransportadora.repository.validation;

import br.com.herbertleone.sistemadetransportadora.model.Cidade;
import br.com.herbertleone.sistemadetransportadora.repository.CidadeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CidadeRepositoryIntegrationTest {

    @Autowired
    private CidadeRepository cidadeRepository;

    private Cidade cidade;

    @BeforeEach
    public void start() {
        cidade = new Cidade();
        cidade.setNome("São Luís");
        cidade.setTaxa(new BigDecimal(10));
        cidade.setUf("MA");
    }

    @Test
    public void deveSalvarUmaNovaCidade() { 

        cidadeRepository.save(cidade);

        List<Cidade> cidades = cidadeRepository.findAll();
        Assertions.assertEquals(1, cidades.size() );

        cidadeRepository.deleteAll();
    }

    @Test
    public void deveRemoverUmaCidade() {

        cidadeRepository.save(cidade);
        List<Cidade> cidades = cidadeRepository.findAll();
        Assertions.assertEquals(1, cidades.size());

        cidadeRepository.deleteById(cidade.getId());
        List<Cidade> resultado = cidadeRepository.findAll();
        Assertions.assertEquals(0, resultado.size());
    }

}
