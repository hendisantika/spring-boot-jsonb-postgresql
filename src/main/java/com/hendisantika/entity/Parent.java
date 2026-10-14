package com.hendisantika.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jsonb-postgresql
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 21/08/21
 * Time: 22.49
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "parents")
public class Parent implements Serializable {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @Column(length = 32, nullable = false)
    private String name;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Child> children;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Bio bio;

    public Parent(String name, List<Child> children, Bio bio) {
        this.name = name;
        this.children = children;
        this.bio = bio;
    }
}
