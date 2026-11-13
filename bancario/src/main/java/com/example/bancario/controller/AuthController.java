package com.example.bancario.controller;

import com.example.bancario.config.TokenConfig;
import com.example.bancario.dto.request.LoginRequest;
import com.example.bancario.dto.request.RegisterClienteRequest;
import com.example.bancario.dto.response.LoginResponse;
import com.example.bancario.dto.response.RegisterResponse;
import com.example.bancario.model.Cliente;
import com.example.bancario.model.StatusConta;
import com.example.bancario.repository.ClienteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken clienteAndPass = new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.senha());
        Authentication authentication = authenticationManager.authenticate(clienteAndPass);
        Cliente cliente = (Cliente) authentication.getPrincipal();
        String token = tokenConfig.generateToken(cliente);
        return ResponseEntity.ok(new LoginResponse(token));
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterClienteRequest request) {
        Cliente novoCliente = new Cliente();
        novoCliente.setNomeCompleto(request.nomeCompleto());
        novoCliente.setLogin(request.login());
        novoCliente.setSenha(passwordEncoder.encode(request.senha()));
        novoCliente.setCpf(request.cpf());
        novoCliente.setTelefone(request.telefone());
        novoCliente.setDataNascimento(request.dataNascimento());
        novoCliente.setCriadoEm(LocalDateTime.now());
        novoCliente.setStatusConta(StatusConta.ATIVA);
        clienteRepository.save(novoCliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(novoCliente.getNomeCompleto(),
                novoCliente.getCriadoEm()));
    }
}
