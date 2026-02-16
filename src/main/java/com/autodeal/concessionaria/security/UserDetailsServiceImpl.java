package com.autodeal.concessionaria.security;

import com.autodeal.concessionaria.iam.application.FuncionarioService;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final FuncionarioService funcionarioService;

    public UserDetailsServiceImpl(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        // Aqui vamos usar email como username
        return funcionarioService.buscarPorEmail(com.autodeal.concessionaria.shared.vo.Email.of(username))
                .map(funcionario -> User.builder()
                        .username(funcionario.getEmail().getValue())
                        .password(funcionario.getSenhaHash())
                        .authorities(funcionario.getPerfilAcesso().asGrantedAuthority())
                        .accountExpired(!funcionario.isAtivo())
                        .accountLocked(!funcionario.isAtivo())
                        .credentialsExpired(false)
                        .disabled(!funcionario.isAtivo())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado: " + username));
    }
}