package br.com.herbertleone.sistemadetransportadora.service;

import br.com.herbertleone.sistemadetransportadora.model.Frete;
import br.com.herbertleone.sistemadetransportadora.repository.FreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

@Service
public class FreteService {

    @Autowired
    private FreteRepository freteRepository;

    public void adiciona(Frete frete) throws FreteException {
        try {
            freteRepository.save(frete);
        } catch (ConstraintViolationException e) {
            throw new FreteException(e);
        }
    }

    public void remove(Integer id) {
        freteRepository.deleteById(id);
    }

}