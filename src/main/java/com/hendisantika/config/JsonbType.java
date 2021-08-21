package com.hendisantika.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jsonb-postgresql
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 21/08/21
 * Time: 22.40
 */
public class JsonbType implements UserType {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class<?> returnedClass() {
        return Map.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.nullSafeEquals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names,
                              SharedSessionContractImplementor sharedSessionContractImplementor, Object owner) throws HibernateException, SQLException {
        Object obj = rs.getObject(names[0]);
        if (obj == null) {
            return null;
        }
        PGobject o = (PGobject) obj;
        if (o.getValue() != null) {
            try {
                // return JSON.parse(o.getValue(), Map.class);
                return mapper.readValue(o.getValue(), Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<String, Object>();
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index,
                            SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            try {
                // st.setObject(index, JSON.toJSON(value), Types.OTHER);
                st.setObject(index, mapper.writeValueAsString(value), Types.OTHER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object deepCopy(Object originalValue) throws HibernateException {
        if (originalValue != null) {
            try {
                // Replace with other JSON parser
                // return JSON.parse(JSON.toJSON(originalValue), returnedClass());
                return mapper.readValue(mapper.writeValueAsString(originalValue), returnedClass());
            } catch (IOException e) {
                throw new HibernateException("Failed to deep copy object", e);
            }
        }
        return null;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        Object copy = deepCopy(value);

        if (copy instanceof Serializable) {
            return (Serializable) copy;
        }
        throw new SerializationException(String.format("Cannot serialize '%s', %s is not Serializable.", value,
                value.getClass()), null);
    }


    @Override
    public Object assemble(Serializable cached, Object o) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }
}

