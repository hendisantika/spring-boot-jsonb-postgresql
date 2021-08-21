package com.hendisantika.repository;

import com.hendisantika.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query(value = "select * from account where info ->> 'name' = :name", nativeQuery = true)
    List<Account> findByName(@Param("name") String name);
}
