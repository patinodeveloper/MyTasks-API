package my_tasks.auth.service;


import lombok.RequiredArgsConstructor;
import my_tasks.auth.dto.AuthResponse;
import my_tasks.auth.dto.LoginRequest;
import my_tasks.auth.dto.RegisterRequest;
import my_tasks.model.User;
import my_tasks.model.UserRole;
import my_tasks.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        User user = userRepository.findByUsername(request.username()).orElseThrow();

        String token = jwtService.getToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(UserRole.USER)
                .build();

        userRepository.save(user);

        return new AuthResponse(jwtService.getToken(user));
    }
}