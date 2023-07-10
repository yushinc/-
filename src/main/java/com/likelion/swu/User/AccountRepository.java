package com.likelion.swu.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByStudentNumber(String student_number);
}
