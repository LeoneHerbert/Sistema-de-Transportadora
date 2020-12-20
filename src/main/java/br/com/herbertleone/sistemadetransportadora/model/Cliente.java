package br.com.herbertleone.sistemadetransportadora.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotEmpty(message="O nome deve ser preenchido")
    private String nome;

    @Column
    @NotEmpty(message="O endereço deve ser preenchido")
    private String endereco;

    @Column
    @NotEmpty(message="O telefone deve ser preenchido")
    private String telefone;

}
