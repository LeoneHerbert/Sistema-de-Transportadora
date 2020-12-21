package br.com.herbertleone.sistemadetransportadora.controller;

import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;

import br.com.herbertleone.sistemadetransportadora.model.Frete;
import br.com.herbertleone.sistemadetransportadora.service.FreteException;
import br.com.herbertleone.sistemadetransportadora.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/frete")
public class FreteController {

    @Autowired
    private FreteService freteService;

    @GetMapping("/")
    public ResponseEntity<List<Frete>> fretes(){
        List<Frete> fretes = freteService.buscaFretes();
        return ResponseEntity.ok(fretes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Frete> frete(@PathVariable Integer id){
        return freteService.buscaFrete(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/inserir")
    public ResponseEntity<Frete> inserir(@RequestBody @Valid Frete frete) throws URISyntaxException, FreteException {
        frete = freteService.insereOuAltera(frete);
        return new ResponseEntity<>(frete, HttpStatus.CREATED);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Frete> alterar(@PathVariable Long id, @RequestBody @Valid Frete frete) throws URISyntaxException, FreteException {
        frete = freteService.insereOuAltera(frete);
        return new ResponseEntity<>(frete, HttpStatus.CREATED);
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Frete> remover(@PathVariable Integer id) {
        freteService.remove(id);
        return ResponseEntity.noContent().build();
    }

}