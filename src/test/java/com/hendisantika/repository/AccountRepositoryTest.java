package com.hendisantika.repository;

import com.hendisantika.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jsonb-postgresql
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 21/08/21
 * Time: 22.44
 */
@SpringBootTest
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    public void setUp() throws Exception {
        HashMap<String, Object> info = new HashMap<>();
        info.put("name", "Uzumaki Naruto");
        info.put("age", 3);
        info.put("tel", "110");
        info.put("Hobby", Arrays.asList("Reading", "Coding", "Browsing", "Watching Movies"));
        Account account = new Account().setInfo(info);
        accountRepository.save(account);
    }

    @Test
    public void findByName() {
        List<Account> accounts = accountRepository.findByName("Uzumaki Naruto");
        System.out.println(accounts);
    }
}
