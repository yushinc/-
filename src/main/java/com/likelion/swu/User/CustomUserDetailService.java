package com.likelion.swu.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;
    public UserDetails loadUserByUsername(String student_number)throws UsernameNotFoundException {
        Account account = accountRepository.findByStudentNumber(student_number);
        if(account==null) throw new UsernameNotFoundException(String.format("EMAIL : [%s]를 찾을 수 없습니다", student_number));

        return new AccountSecurityAdapter(account);
    }

}
