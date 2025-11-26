package com.grupo_5.pub.Controller;

import com.grupo_5.pub.Model.Ingrediente;
import com.grupo_5.pub.Repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/ingredientes", produces = "application/xml")
public class IngredienteController {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    // -------------------------
    // LISTAR TODOS
    // -------------------------
    @GetMapping
    public List<Ingrediente> listar() {
        return ingredienteRepository.findAll();
    }

    // -------------------------
    // BUSCAR POR ID
    // -------------------------
    @GetMapping("/{id}")
    public Ingrediente buscarPorId(@PathVariable int id) {
        Optional<Ingrediente> ingrediente = ingredienteRepository.findById(id);
        return ingrediente.orElse(null);
    }

    // -------------------------
    // CRIAR INGREDIENTE
    // -------------------------
    @PostMapping
    public Ingrediente criar(@RequestBody Ingrediente ingrediente) {
        ingrediente.setId(null);

        return ingredienteRepository.save(ingrediente);
    }

    // -------------------------
    // ATUALIZAR INGREDIENTE
    // -------------------------
    @PutMapping("/{id}")
    public Ingrediente atualizar(@PathVariable int id, @RequestBody Ingrediente dadosAtualizados) {

        Optional<Ingrediente> op = ingredienteRepository.findById(id);
        if (op.isEmpty()) {
            return null;
        }

        Ingrediente ingrediente = op.get();

        ingrediente.setNome(dadosAtualizados.getNome());
        ingrediente.setUnidadeMedida(dadosAtualizados.getUnidadeMedida());
        ingrediente.setEstoqueAtual(dadosAtualizados.getEstoqueAtual());
        ingrediente.setEstoqueMinimo(dadosAtualizados.getEstoqueMinimo());

        return ingredienteRepository.save(ingrediente);
    }

    // -------------------------
    // DELETAR INGREDIENTE
    // -------------------------
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable int id) {
        if (!ingredienteRepository.existsById(id)) {
            return "<msg>Ingrediente não encontrado</msg>";
        }

        ingredienteRepository.deleteById(id);
        return "<msg>Ingrediente removido com sucesso</msg>";
    }

    // -------------------------
    // ENTRADA DE ESTOQUE (COMPRA)
    // -------------------------
    @PostMapping("/{id}/entrada/{quantidade}")
    public Ingrediente entradaEstoque(@PathVariable int id, @PathVariable double quantidade) {

        Optional<Ingrediente> op = ingredienteRepository.findById(id);
        if (op.isEmpty()) return null;

        Ingrediente ingrediente = op.get();

        BigDecimal qtdParaAdicionar = BigDecimal.valueOf(quantidade);
        BigDecimal novoSaldo = ingrediente.getEstoqueAtual().add(qtdParaAdicionar);

        ingrediente.setEstoqueAtual(novoSaldo);

        return ingredienteRepository.save(ingrediente);
    }

    // -------------------------
    // BAIXA DE ESTOQUE (CONSUMO)
    // -------------------------
    @PostMapping("/{id}/baixa/{quantidade}")
    public Ingrediente baixaEstoque(@PathVariable int id, @PathVariable double quantidade) {

        Optional<Ingrediente> op = ingredienteRepository.findById(id);
        if (op.isEmpty()) return null;

        Ingrediente ingrediente = op.get();

        // 1. Converte o double para BigDecimal
        BigDecimal qtdParaSubtrair = BigDecimal.valueOf(quantidade);

        // 2. Realiza a subtração usando .subtract()
        BigDecimal novoEstoque = ingrediente.getEstoqueAtual().subtract(qtdParaSubtrair);

        // 3. Verifica se ficou negativo usando .compareTo()
        // (compareTo retorna -1 se for menor, 0 se igual, 1 se maior)
        if (novoEstoque.compareTo(BigDecimal.ZERO) < 0) {
            ingrediente.setEstoqueAtual(BigDecimal.ZERO);
        } else {
            ingrediente.setEstoqueAtual(novoEstoque);
        }

        return ingredienteRepository.save(ingrediente);
    }

    // -------------------------
    // LISTAR ESTOQUE BAIXO
    // -------------------------
    @GetMapping("/baixo-estoque")
    public List<Ingrediente> listarAbaixoDoMinimo() {
        return ingredienteRepository.findByEstoqueAtualLessThanEstoqueMinimo();
    }
}