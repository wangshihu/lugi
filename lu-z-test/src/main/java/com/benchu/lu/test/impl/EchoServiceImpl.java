package com.benchu.lu.test.impl;

import java.util.List;

import javax.management.relation.Role;

import com.benchu.lu.api.Lu;
import com.benchu.lu.test.service.EchoService;

/**
 * @author benchu
 * @version 2016/12/28.
 */
@Lu
public class EchoServiceImpl implements EchoService {

    @Override
    public byte echoByte(byte value) {
        return value;
    }

    @Override
    public byte[] echoBytes(byte[] value) {
        return value;
    }

    @Override
    public List<Byte> echoByteList(List<Byte> value) {
        return value;
    }

    @Override
    public boolean echoBoolean(boolean value) {
        return value;
    }

    @Override
    public List<Boolean> echoBooleanList(List<Boolean> value) {
        return value;
    }

    @Override
    public char echoChar(char value) {
        return value;
    }

    @Override
    public List<Character> echoCharacterList(List<Character> value) {
        return value;
    }

    @Override
    public Role echoEnum(Role value) {
        return value;
    }

    @Override
    public List<Role> echoEnumList(List<Role> value) {
        return value;
    }

    @Override
    public int echoInt(int value) {
        return value;
    }

    @Override
    public List<Integer> echoIntegerList(List<Integer> value) {
        return value;
    }

    @Override
    public short echoShort(short value) {
        return value;
    }

    @Override
    public List<Short> echoShortList(List<Short> value) {
        return value;
    }

    @Override
    public long echoLong(long value) {
        return value;
    }

    @Override
    public List<Long> echoLongList(List<Long> value) {
        return value;
    }

    @Override
    public double echoDouble(double value) {
        return value;
    }

    @Override
    public List<Double> echoDoubleList(List<Double> value) {
        return value;
    }

    @Override
    public String echoString(String value) {
        return value;
    }

    @Override
    public List<String> echoStringList(List<String> value) {
        return value;
    }
}
