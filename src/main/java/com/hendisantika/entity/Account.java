package com.hendisantika.entity;

import com.hendisantika.config.JsonbType;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jsonb-postgresql
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 21/08/21
 * Time: 22.42
 */
@Entity
@Data
@Accessors(chain = true)
@TypeDef(name = "JsonbType", typeClass = JsonbType.class)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "jsonb")
    @Type(type = "JsonbType")
    private Map<String, Object> info;
}

