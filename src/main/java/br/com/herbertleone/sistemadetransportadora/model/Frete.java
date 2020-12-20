package br.com.herbertleone.sistemadetransportadora.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Frete {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    @NotNull(message = "O cliente deve ser preenchido")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cidade_id")
    @NotNull(message = "A cidade deve ser preenchida")
    private Cidade cidade;

    @Column
    @NotEmpty(message="A descrição deve ser preenchida")
    private String descricao;

    @Column(nullable = false)
    private double peso;

    @Column(nullable = false)
    private BigDecimal valor;

}
