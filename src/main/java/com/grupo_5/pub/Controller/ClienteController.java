package com.grupo_5.pub.Controller;

import com.grupo_5.pub.Model.Cliente;
import com.grupo_5.pub.Repository.ClienteRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes", produces = MediaType.APPLICATION_XML_VALUE)
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public Cliente salvar(@RequestBody Cliente cliente) {

        if (clienteRepository.existsByNome(cliente.getNome())) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.CONFLICT,
                    "Já existe um cliente com esse nome"
            );
        }

        if (clienteRepository.existsByContato(cliente.getContato())) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.CONFLICT,
                    "Já existe um cliente com esse contato"
            );
        }

        return clienteRepository.save(cliente);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public Cliente atualizar(@PathVariable Integer id, @RequestBody Cliente clienteAtualizado) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();

        cliente.setNome(clienteAtualizado.getNome());
        cliente.setContato(clienteAtualizado.getContato());

        return clienteRepository.save(cliente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        clienteRepository.deleteById(id);
    }
}
