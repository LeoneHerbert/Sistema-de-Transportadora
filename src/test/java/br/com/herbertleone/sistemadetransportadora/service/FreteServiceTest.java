package br.com.herbertleone.sistemadetransportadora.service;

import br.com.herbertleone.sistemadetransportadora.model.Frete;
import br.com.herbertleone.sistemadetransportadora.repository.FreteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FreteServiceTest {

    @Autowired
    private FreteService freteService;

    @Autowired
    private FreteRepository freteRepository;

    private Frete frete;

    @BeforeEach
    public void before() {
        frete = new Frete();
        frete.setDescricao("Processador AMD Ryzen 5 5600X");
        frete.setPeso(410);
        frete.setValor(new BigDecimal(50));
    }

    @Test
    public void insereComClienteNuloDeveLancarException()  {

        FreteException exception = assertThrows(FreteException.class,
                () -> {  frete.setCliente(null);
                    freteService.adiciona(frete );
                },
                "Deveria lançar um FreteException");

        assertTrue(exception.getMessage().contains("O cliente deve ser preenchido"));

    }

    @Test
    public void insereComCidadeNulaDeveLancarException()  {

        FreteException exception = assertThrows(FreteException.class,
                () -> {  frete.setCidade(null);
                    freteService.adiciona(frete );
                },
                "Deveria lançar um FreteException");

        assertTrue(exception.getMessage().contains("A cidade deve ser preenchida"));
    }


}
