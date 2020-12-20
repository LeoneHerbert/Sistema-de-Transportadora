package br.com.herbertleone.sistemadetransportadora.service;

public class FreteException extends Exception {

    public FreteException(Exception e) throws FreteException {
        super(e);
    }

}
