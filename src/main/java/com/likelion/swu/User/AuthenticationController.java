package com.likelion.swu.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final AccountService accountService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth/signup")
    public ResponseEntity<Map<String, String>> submitSignUp(@Valid @RequestBody SignUpForm signupForm) throws Exception {
//        signUpFormValidator.validate(signupForm, bindingResult);
//        if(bindingResult.hasErrors()) {
//            List<String> errorList = accountService.getSignUpErrorList(bindingResult);
//            throw new AccountNotValidException(errorList, "Custom Validator work");
//        }
        return accountService.getSuccessSignUpResponse(signupForm);
    }

    @GetMapping("/auth/login")
    public ResponseEntity<String> logIn(@RequestParam String student_number, @RequestParam String password) throws Exception{
        return ResponseEntity.ok(accountService.loginWithStudentNumberAndPassword(student_number, password));
    }
}
