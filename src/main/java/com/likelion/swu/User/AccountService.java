package com.likelion.swu.User;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public Account processNewAccount(SignUpForm signupForm) {
        // TODO send email and confirm
        return saveNewAccount(signupForm);
    }
    private Account saveNewAccount(SignUpForm signupForm) {
        Account newAccount = Account.builder()
                .studentNumber(signupForm.getStudentNumber())
                .name(signupForm.getName())
                .major(signupForm.getMajor())
                .roles(Collections.singletonList("ROLE_USER"))
                .password(passwordEncoder.encode(signupForm.getPassword()))
                .build();

        return accountRepository.save(newAccount);
    }

    private void getAuthentication(String student_number, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(student_number, password);
        authenticationManager.authenticate(authenticationToken);
    }

    private String getJwtToken(Account account) {
        return jwtTokenProvider.createToken(account.getUsername(), account.getAuthorities()
                .stream().map(Object::toString).collect(Collectors.toList()));
    }

    public List<String> getSignUpErrorList(BindingResult bindingResult) {
        return bindingResult.getGlobalErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

    public String loginWithStudentNumberAndPassword(String student_number, String password) throws Exception {
        Account account = accountRepository.findByStudentNumber(student_number);
        if(account==null) {
            throw new AccountNotFoundException(String.format("Email[%s]를 찾을 수 없습니다", student_number));
        }
        if(!passwordEncoder.matches(password, account.getPassword())) {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }
        getAuthentication(student_number, password);
        return getJwtToken(account);
    }

    public ResponseEntity<Map<String, String>> getSuccessSignUpResponse(SignUpForm signupForm) throws Exception {
        Account newAccount = processNewAccount(signupForm);
        String jwtToken = loginWithStudentNumberAndPassword(signupForm.getStudentNumber(), signupForm.getPassword());

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("UserId", newAccount.getId().toString());
        responseBody.put("Token", jwtToken);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }



}
