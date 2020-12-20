package br.com.herbertleone.sistemadetransportadora.repository.query;

import br.com.herbertleone.sistemadetransportadora.model.Cidade;
import br.com.herbertleone.sistemadetransportadora.model.Cliente;
import br.com.herbertleone.sistemadetransportadora.model.Frete;
import br.com.herbertleone.sistemadetransportadora.repository.CidadeRepository;
import br.com.herbertleone.sistemadetransportadora.repository.ClienteRepository;
import br.com.herbertleone.sistemadetransportadora.repository.FreteRepository;
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
public class FreteRepositoryIntegrationQueryTest {

    @Autowired
    private FreteRepository freteRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void before() {
        Cidade cidade = new Cidade();
        cidade.setNome("São Luís");
        cidade.setTaxa(new BigDecimal(10));
        cidade.setUf("MA");

        Cliente cliente = new Cliente();
        cliente.setNome("Herbert");
        cliente.setEndereco("Grand Park");
        cliente.setTelefone("981491256");

        cidadeRepository.save(cidade);
        clienteRepository.save(cliente);

        Frete frete = new Frete();
        frete.setDescricao("Processador AMD Ryzen 5 5600X");
        frete.setPeso(410);
        frete.setValor(new BigDecimal(50));
        frete.setCidade(cidade);
        frete.setCliente(cliente);

        freteRepository.save(frete);
    }

    @AfterEach
    public void after() {
        freteRepository.deleteAll();
    }

    @Test
    public void deveRecuperarTodosOsFretesDeUmClienteOrdenadosPorValor() {

        List<Frete> fretes;
        fretes = freteRepository.todos(1, Sort.by("valor").ascending());
        Assertions.assertNotEquals(0, fretes.size());
    }
}
