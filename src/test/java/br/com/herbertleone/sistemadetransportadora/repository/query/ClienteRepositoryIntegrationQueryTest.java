package br.com.herbertleone.sistemadetransportadora.repository.query;

import br.com.herbertleone.sistemadetransportadora.model.Cliente;
import br.com.herbertleone.sistemadetransportadora.repository.ClienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ClienteRepositoryIntegrationQueryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void before() {
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Herbert");
        cliente1.setEndereco("Calhau");
        cliente1.setTelefone("981491256");
        clienteRepository.save(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("David");
        cliente2.setEndereco("Maioba");
        cliente2.setTelefone("34324234");
        clienteRepository.save(cliente2);

        Cliente cliente3 = new Cliente();
        cliente3.setNome("Dieliton");
        cliente3.setEndereco("Maiobinha");
        cliente3.setTelefone("234324343");
        clienteRepository.save(cliente3);

        Cliente cliente4 = new Cliente();
        cliente4.setNome("Wesley");
        cliente4.setEndereco("Cohama");
        cliente4.setTelefone("342434344");
        clienteRepository.save(cliente4);
    }

    @AfterEach
    public void after() {
        clienteRepository.deleteAll();
    }


    @Test
    public void deveBuscarClientePeloTelefone() {
        Cliente cliente = clienteRepository.findByTelefone("981491256");
        assertTrue(cliente.getTelefone().equals("981491256"));
    }
}
