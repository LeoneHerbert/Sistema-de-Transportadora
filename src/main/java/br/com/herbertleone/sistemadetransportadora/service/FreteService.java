package br.com.herbertleone.sistemadetransportadora.service;

import br.com.herbertleone.sistemadetransportadora.model.Frete;
import br.com.herbertleone.sistemadetransportadora.repository.FreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class FreteService {

    @Autowired
    private FreteRepository freteRepository;

    public Frete insereOuAltera(Frete frete) throws FreteException {
        try {
            frete.setValor(calculaValor(frete));
            return freteRepository.save(frete);
        } catch (ConstraintViolationException e) {
            throw new FreteException(e);
        }
    }

    public Optional<Frete> buscaFrete(Integer id){
        return freteRepository.findById(id);
    }

    public List<Frete> buscaFretes(){
        return freteRepository.findAll();
    }

    public void remove(Integer id) {
        freteRepository.deleteById(id);
    }

    public BigDecimal calculaValor(Frete frete) {
        return frete.getCidade().getTaxa().add(new BigDecimal(frete.getPeso() * 10));
    }

    public Frete freteDeMaiorValor() {
        List<Frete> fretes
                = freteRepository.todosOsFretesDoMaiorValorParaOMenorValor(Sort.by("valor").descending());
        return fretes.get(0);
    }
}