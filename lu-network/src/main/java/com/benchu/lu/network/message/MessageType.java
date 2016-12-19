package com.benchu.lu.network.message;

/**
 * @author benchu
 * @version on 15/10/21.
 */
public class MessageType<E> {

    private int id;
    private Class<E> clazz;

    public MessageType(int id, Class<E> clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    public int getId() {
        return id;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        MessageType<?> that = (MessageType<?>) o;
        return id == that.id && (clazz != null ? clazz.equals(that.clazz) : that.clazz == null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
        return result;
    }
}
