package br.com.herbertleone.sistemadetransportadora.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Entity
@Data
public class Cidade {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotEmpty(message="O nome deve ser preenchido")
    private String nome;

    @Column
    @NotEmpty(message="A UF deve ser preenchida")
    private String uf;

    @Column(nullable = false)
    private BigDecimal taxa;
}
