package br.com.herbertleone.sistemadetransportadora.controller;

import br.com.herbertleone.sistemadetransportadora.model.Cidade;
import br.com.herbertleone.sistemadetransportadora.model.Cliente;
import br.com.herbertleone.sistemadetransportadora.model.Frete;
import br.com.herbertleone.sistemadetransportadora.repository.CidadeRepository;
import br.com.herbertleone.sistemadetransportadora.repository.ClienteRepository;
import br.com.herbertleone.sistemadetransportadora.repository.FreteRepository;
import br.com.herbertleone.sistemadetransportadora.service.FreteException;
import br.com.herbertleone.sistemadetransportadora.service.FreteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class FreteControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

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
    public void before() throws FreteException {
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

        freteService.insereOuAltera(frete);
    }

    @AfterEach
    public void end() {
        freteRepository.deleteAll();
        cidadeRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Test
    public void deveInserirUmFrete() {
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);

        ResponseEntity<Frete> resposta =
                testRestTemplate.exchange("/frete/inserir",HttpMethod.POST,httpEntity, Frete.class);

        assertEquals(HttpStatus.CREATED,resposta.getStatusCode());

        Frete resultado = resposta.getBody();

        assertNotNull(resultado.getId());
        assertEquals(frete.getCliente(), resultado.getCliente());
        assertEquals(frete.getPeso(), resultado.getPeso());
        assertEquals(frete.getDescricao(), resultado.getDescricao());
    }

    @Test
    public void naoDeveSalvarFreteComErroDeValidacao() {
        frete.setCidade(null);
        frete.setCliente(null);
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);

        ResponseEntity<List<String>> resposta =
                testRestTemplate.exchange("/frete/inserir",
                        HttpMethod.POST,httpEntity,
                        new ParameterizedTypeReference<List<String>>() {});

        assertEquals(HttpStatus.BAD_REQUEST,resposta.getStatusCode());
        assertTrue(resposta.getBody().contains("O cliente deve ser preenchido"));
        assertTrue(resposta.getBody().contains("A cidade deve ser preenchida"));
    }


    @Test
    public void deveInserirUmFreteComPostForEntity() {
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);
        ResponseEntity<Frete> resposta =
                testRestTemplate.postForEntity("/frete/inserir",httpEntity, Frete.class);

        assertEquals(HttpStatus.CREATED,resposta.getStatusCode());

        Frete resultado = resposta.getBody();

        assertNotNull(resultado.getId());
        assertEquals(frete.getCliente(), resultado.getCliente());
        assertEquals(frete.getPeso(), resultado.getPeso());
        assertEquals(frete.getDescricao(), resultado.getDescricao());
    }

    @Test
    public void deveInserirUmFreteComPostForObject() {
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);
        Frete resposta = testRestTemplate.postForObject("/frete/inserir",httpEntity, Frete.class);

        assertNotNull(resposta.getId());
        assertEquals(frete.getCliente(), resposta.getCliente());
        assertEquals(frete.getPeso(), resposta.getPeso());
        assertEquals(frete.getDescricao(), resposta.getDescricao());
    }

    @Test
    public void deveMostrarUmFrete() {

        ResponseEntity<Frete> resposta =
                testRestTemplate.exchange("/frete/{id}",HttpMethod.GET,null, Frete.class,frete.getId() );

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(resposta.getHeaders().getContentType(),
                MediaType.parseMediaType("application/json"));
        assertEquals(frete.getId(), resposta.getBody().getId());
    }

    @Test
    public void deveMostrarUmFreteComGetForEntity() {
        ResponseEntity<Frete> resposta =
                testRestTemplate.getForEntity("/frete/{id}", Frete.class,frete.getId());

        assertEquals(HttpStatus.OK, resposta.getStatusCode());

        assertEquals(resposta.getHeaders().getContentType(),
                MediaType.parseMediaType("application/json"));

        assertEquals(frete.getId(), resposta.getBody().getId());
    }

    @Test
    public void deveMostrarUmFreteComGetForObject() {
        Frete resposta =
                testRestTemplate.getForObject("/frete/{id}", Frete.class,frete.getId());
        assertEquals(frete.getId(), resposta.getId());
    }

    @Test
    public void deveMostrarFreteNaoEncontradoComGetForEntity() {
        ResponseEntity<Frete> resposta =
                testRestTemplate.getForEntity("/frete/{id}", Frete.class,100);

        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        assertNull(resposta.getBody());
    }

    @Test
    public void naoDeveMostrarFreteInexistente() {
        Frete resposta = testRestTemplate.getForObject("/frete/{id}", Frete.class,100);
        assertNull(resposta);
    }

        @Test
    public void deveMostrarFreteNaoEncontrado() {

        ResponseEntity<Frete> resposta =
                testRestTemplate.exchange("/frete/{id}",HttpMethod.GET,null, Frete.class,100 );

        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        assertNull(resposta.getBody());
    }

    @Test
    public void deveListarTodosOsFretes() {
        ResponseEntity<String> resposta =
                testRestTemplate.exchange("/frete/", HttpMethod.GET, null, String.class);

        System.out.println("######## " + resposta.getBody());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    public void deveAlterarUmFrete(){


        frete.setDescricao("NOTEBOOK");
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);

        ResponseEntity<Frete> resposta =
                testRestTemplate.exchange("/frete/alterar/{id}",HttpMethod.PUT,httpEntity
                        , Frete.class,frete.getId());

        assertEquals(HttpStatus.CREATED,resposta.getStatusCode());

        Frete resultado = resposta.getBody();

        assertNotNull(resultado.getId());
        assertEquals(frete.getCliente(), resultado.getCliente());
        assertEquals(frete.getPeso(), resultado.getPeso());
        assertEquals("NOTEBOOK", resultado.getDescricao());
    }

    @Test
    public void deveAlterarFreteComPut() {
        frete.setDescricao("NOTEBOOK");
        testRestTemplate.put("/frete/alterar/{id}",frete,frete.getId());

        Frete resultado = freteRepository.findById(frete.getId()).get();
        assertEquals(frete.getCliente(), resultado.getCliente());
        assertEquals(frete.getPeso(), resultado.getPeso());
        assertEquals("NOTEBOOK", resultado.getDescricao());
    }


    @Test
    public void deveRetornarMensagemDeErroQuandoAlterarFrete(){

        frete.setCliente(null);
        frete.setCidade(null);
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);
        ResponseEntity<List<String>> resposta =
                testRestTemplate.exchange("/frete/alterar/{id}",HttpMethod.PUT
                        ,httpEntity, new ParameterizedTypeReference<List<String>>() {},frete.getId());

        assertEquals(HttpStatus.BAD_REQUEST,resposta.getStatusCode());
        assertTrue(resposta.getBody().contains("O cliente deve ser preenchido"));
        assertTrue(resposta.getBody().contains("A cidade deve ser preenchida"));
    }

    @Test
    public void deveExcluirFrete() {


        ResponseEntity<Frete> resposta =
                testRestTemplate.exchange("/frete/remover/{id}",HttpMethod.DELETE,null
                        , Frete.class,frete.getId());

        assertEquals(HttpStatus.NO_CONTENT,resposta.getStatusCode());
        assertNull(resposta.getBody());
    }

    @Test
    public void deveExcluirFreteComMetodoDelete()  {

        testRestTemplate.delete("/frete/remover/"+frete.getId());

        final Optional<Frete> resultado = freteRepository.findById(frete.getId());
        assertEquals(Optional.empty(), resultado);
    }
}
