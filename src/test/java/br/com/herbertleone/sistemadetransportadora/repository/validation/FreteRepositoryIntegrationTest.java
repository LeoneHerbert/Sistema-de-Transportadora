package br.com.herbertleone.sistemadetransportadora.repository.validation;

import br.com.herbertleone.sistemadetransportadora.model.Cidade;
import br.com.herbertleone.sistemadetransportadora.model.Cliente;
import br.com.herbertleone.sistemadetransportadora.model.Frete;
import br.com.herbertleone.sistemadetransportadora.model.Frete;
import br.com.herbertleone.sistemadetransportadora.repository.CidadeRepository;
import br.com.herbertleone.sistemadetransportadora.repository.ClienteRepository;
import br.com.herbertleone.sistemadetransportadora.repository.FreteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class FreteRepositoryIntegrationTest {

    @Autowired
    private FreteRepository freteRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    private Frete frete;
    private Cidade cidade;
    private Cliente cliente;


    @BeforeEach
    public void start() {

        cidade = new Cidade();
        cidade.setNome("São Luís");
        cidade.setTaxa(new BigDecimal(10));
        cidade.setUf("MA");

        cliente = new Cliente();
        cliente.setNome("Herbert");
        cliente.setEndereco("Grand Park");
        cliente.setTelefone("981491256");

        cidadeRepository.save(cidade);
        clienteRepository.save(cliente);

        frete = new Frete();
        frete.setDescricao("Processador AMD Ryzen 5 5600X");
        frete.setPeso(410);
        frete.setValor(new BigDecimal(50));
        frete.setCidade(cidade);
        frete.setCliente(cliente);
    }

    @Test
    public void deveSalvarUmNovoFrete() {

        freteRepository.save(frete);
        List<Frete> fretes = freteRepository.findAll();

        Assertions.assertEquals(1, fretes.size());

        freteRepository.deleteAll();
    }

    @Test
    public void deveRemoverUmFrete() {

        freteRepository.save(frete);
        List<Frete> fretes = freteRepository.findAll();

        Assertions.assertEquals(1, fretes.size());

        freteRepository.deleteById(frete.getId());
        List<Frete> resultado = freteRepository.findAll();

        Assertions.assertEquals(0, resultado.size());
    }
}
