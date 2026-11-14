package com.example.bancario.service;

import com.example.bancario.dto.request.ClienteRequestDTO;
import com.example.bancario.dto.response.ClienteResponseDTO;
import com.example.bancario.model.Cliente;
import com.example.bancario.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    //buscar usuário por id
    public ClienteResponseDTO findById(Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado!"));
        return new ClienteResponseDTO(cliente);
    }

    //buscar todos os usuários
    public Page<ClienteResponseDTO> findAll(Pageable pageable){
        return clienteRepository.findAll(pageable)
                .map(cliente -> new ClienteResponseDTO(cliente));
    }
    @Transactional
    public ClienteResponseDTO update(Long id, ClienteRequestDTO clienteRequestDTO){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("Usuário não cadastrado!"));

        cliente.setNomeCompleto(clienteRequestDTO.nomeCompleto());
        cliente.setTelefone(clienteRequestDTO.telefone());
        cliente.setDataNascimento(clienteRequestDTO.dataNascimento());

        Cliente clienteSalvo = clienteRepository.save(cliente);

        return new ClienteResponseDTO(clienteSalvo);
    }

    public void delete(Long id){
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(()-> new UsernameNotFoundException("Usuário não cadastrado!"));

        clienteRepository.delete(cliente);
    }



}
