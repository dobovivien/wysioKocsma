package com.example.wysioKocsma.hybernateType;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayType implements UserType {

    private final int[] arrayTypes = new int[]{Types.ARRAY};

    public int[] sqlTypes() {
        return arrayTypes;
    }

    public Class<List> returnedClass() {
        return List.class;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        return x == null ? y == null : x.equals(y);
    }

    public int hashCode(Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        if (names != null && names.length > 0 && rs != null && rs.getArray(names[0]) != null) {

            Object array = rs.getArray(names[0]).getArray();
            if (array instanceof Integer[]) {
                return Arrays.asList((Integer[]) array);
            }
            if (array instanceof Long[]) {
                return Arrays.asList((Long[]) array);
            }
        }

        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (st != null) {
            if (value != null) {
                List<Object> list = (List<Object>) value;
                if (list.size() > 0) {
                    if (list.get(0) instanceof Integer)   {
                        Integer[] castObject = list.toArray(new Integer[list.size()]);
                        Array array = session.connection().createArrayOf("int", castObject);
                        st.setArray(index, array);
                    }
                    if (list.get(0) instanceof Long)   {
                        Long[] castObject = list.toArray(new Long[list.size()]);
                        Array array = session.connection().createArrayOf("bigint", castObject);
                        st.setArray(index, array);
                    }
                } else {
                    st.setNull(index, arrayTypes[0]);
                }
            } else {
                st.setNull(index, arrayTypes[0]);
            }
        }
    }

    public Object deepCopy(Object value) throws HibernateException {
        if (value == null)
            return null;
        return new ArrayList((List)value);
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
