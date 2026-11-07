package com.example.bancario.controller;

import com.example.bancario.dto.ClienteRequestDTO;
import com.example.bancario.dto.ClienteResponseDTO;
import com.example.bancario.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {
    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> create(@RequestBody ClienteRequestDTO clienteDTO){
        ClienteResponseDTO clienteResponseDTO = clienteService.create(clienteDTO);
        log.info("Cadastrado cliente com telefone e id {}, {}", clienteDTO.telefone(), clienteDTO.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        clienteService.delete(id);
        log.info("Deletando cliente com id {}", id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> update(@PathVariable Long id,
                                                    @RequestBody ClienteRequestDTO clienteRequestDTO){
        ClienteResponseDTO clienteResponseDTO = clienteService.update(id, clienteRequestDTO);
        log.info("Atualizando cliente com telefone e id {}, {}", clienteRequestDTO.telefone(), clienteRequestDTO.id());
        return ResponseEntity.ok(clienteResponseDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id){
        ClienteResponseDTO clienteResponseDTO = clienteService.findById(id);
        log.info("Buscando cliente com id {}", id);
        return ResponseEntity.ok(clienteResponseDTO);

    }
    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<ClienteResponseDTO> clientesDTO = clienteService.findAll(pageable);
        log.info("Listando todos os clientes");
        return ResponseEntity.ok(clientesDTO);

    }
}
