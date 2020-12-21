package br.com.herbertleone.sistemadetransportadora.service;

import br.com.herbertleone.sistemadetransportadora.model.Cidade;
import br.com.herbertleone.sistemadetransportadora.model.Cliente;
import br.com.herbertleone.sistemadetransportadora.model.Frete;
import br.com.herbertleone.sistemadetransportadora.repository.CidadeRepository;
import br.com.herbertleone.sistemadetransportadora.repository.ClienteRepository;
import br.com.herbertleone.sistemadetransportadora.repository.FreteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FreteServiceTest {

    @Autowired
    private FreteService freteService;

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
    public void before() {
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
        frete.setCidade(cidade);
        frete.setCliente(cliente);
    }

    @Test
    public void insereComClienteNuloDeveLancarException()  {

        FreteException exception = assertThrows(FreteException.class,
                () -> {  frete.setCliente(null);
                    freteService.insereOuAltera(frete );
                },
                "Deveria lançar um FreteException");

        assertTrue(exception.getMessage().contains("O cliente deve ser preenchido"));

    }

    @Test
    public void insereComCidadeNulaDeveLancarException()  {

        FreteException exception = assertThrows(FreteException.class,
                () -> {  frete.setCidade(null);
                    freteService.insereOuAltera(frete );
                },
                "Deveria lançar um FreteException");

        assertTrue(exception.getMessage().contains("A cidade deve ser preenchida"));
    }

    @Test
    public void recuperaValorDoFreteCalculadoPeloPesoValorFixoETaxa() throws FreteException {
        freteService.insereOuAltera(frete);
        Optional<Frete> frete = freteRepository.findById(1);
        assertEquals(frete.get().getValor().doubleValue(), 4110);
    }

    @Test
    public void recuperaFreteDeMaiorValor() throws FreteException {
        freteService.insereOuAltera(frete);

        frete = new Frete();
        frete.setDescricao("SSD XPG NVME M.2 256GB");
        frete.setPeso(100);
        frete.setCidade(cidade);
        frete.setCliente(cliente);

        freteService.insereOuAltera(frete);

        Frete freteDeMaiorValor = freteService.freteDeMaiorValor();

        assertEquals(freteDeMaiorValor.getValor().doubleValue(), 4110);
    }
}
