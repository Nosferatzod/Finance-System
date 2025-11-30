package com.example.demo.service;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.dto.LoginUserDTO;
import com.example.demo.dto.RecoveryJwtTokenDTO;
import com.example.demo.models.Role;
import com.example.demo.models.RoleName;
import com.example.demo.models.User;
import com.example.demo.models.UserDetailsImpl;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository  roleRepository;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public RecoveryJwtTokenDTO authenticateUser(LoginUserDTO loginUserDTO){
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUserDTO.email(), loginUserDTO.password());
//        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        return new RecoveryJwtTokenDTO(jwtTokenService.generateToken(userDetails));
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginUserDTO.email(),
                loginUserDTO.password()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String token=jwtTokenService.generateToken(userDetails);
        return new RecoveryJwtTokenDTO(token);
    }

    @Transactional
    public void createUser(CreateUserDTO createUserDTO){
//        RoleName roleNameFromDTO=createUserDTO.role();
        RoleName roleNameFromDTO=RoleName.getRoleCustom();
        Role userRole=roleRepository.findByName(roleNameFromDTO)
                .orElseThrow(() -> new RuntimeException("Erro critico! Papel'"+roleNameFromDTO+"' nao encontrado no banco "));
        User newUser = User.builder()
                .name(createUserDTO.name())
                .cpf(createUserDTO.cpf())
                .email(createUserDTO.email())
                .password(passwordEncoder.encode(createUserDTO.password()))
                .roles(Collections.singletonList(userRole))
                .build();


    userRepository.save(newUser);
    }
}
