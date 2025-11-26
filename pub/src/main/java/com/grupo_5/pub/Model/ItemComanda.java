package com.grupo_5.pub.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item_comanda")
public class ItemComanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Mudado para Integer

    @ManyToOne
    @JoinColumn(name = "id_comanda")
    private Comanda comanda;

    @ManyToOne
    @JoinColumn(name = "id_bebida")
    private Bebida bebida;

    private Integer quantidade;
    private BigDecimal precoUnitarioRegistro;
    private BigDecimal valorItem;

    public ItemComanda() {}

    // --- GETTERS E SETTERS ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public Bebida getBebida() {
        return bebida;
    }

    public void setBebida(Bebida bebida) {
        this.bebida = bebida;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitarioRegistro() {
        return precoUnitarioRegistro;
    }

    public void setPrecoUnitarioRegistro(BigDecimal precoUnitarioRegistro) {
        this.precoUnitarioRegistro = precoUnitarioRegistro;
    }

    public BigDecimal getValorItem() {
        return valorItem;
    }

    public void setValorItem(BigDecimal valorItem) {
        this.valorItem = valorItem;
    }
}