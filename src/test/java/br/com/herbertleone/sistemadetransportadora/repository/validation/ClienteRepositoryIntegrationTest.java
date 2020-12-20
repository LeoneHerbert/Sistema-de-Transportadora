package br.com.herbertleone.sistemadetransportadora.repository.validation;

import br.com.herbertleone.sistemadetransportadora.model.Cidade;
import br.com.herbertleone.sistemadetransportadora.model.Cliente;
import br.com.herbertleone.sistemadetransportadora.model.Cliente;
import br.com.herbertleone.sistemadetransportadora.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ClienteRepositoryIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    @BeforeEach
    public void start() {
        cliente = new Cliente();
        cliente.setNome("Herbert");
        cliente.setEndereco("Grand Park");
        cliente.setTelefone("981491256");
    }


    @Test
    public void deveSalvarUmNovoCliente() {

        clienteRepository.save(cliente);
        List<Cliente> clientes = clienteRepository.findAll();

        Assertions.assertEquals(1, clientes.size() );

        clienteRepository.deleteAll();
    }

    @Test
    public void deveRemoverUmCliente() {

        clienteRepository.save(cliente);
        List<Cliente> clientes = clienteRepository.findAll();

        Assertions.assertEquals(1, clientes.size());

        clienteRepository.deleteById(cliente.getId());
        List<Cliente> resultado = clienteRepository.findAll();

        Assertions.assertEquals(0, resultado.size());
    }
}
