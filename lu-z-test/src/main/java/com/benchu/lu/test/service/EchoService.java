package com.benchu.lu.test.service;

import java.util.List;

import javax.management.relation.Role;

/**
 * @author benchu
 * @version 2016/12/28.
 */
public interface EchoService {

    byte echoByte(byte value);
    byte[] echoBytes(byte[] value);
    List<Byte> echoByteList(List<Byte> value);

    boolean echoBoolean(boolean value);
    List<Boolean> echoBooleanList(List<Boolean> value);


    char echoChar(char value);
    List<Character> echoCharacterList(List<Character> value);

    //enum
    Role echoEnum(Role value);
    List<Role> echoEnumList(List<Role> value);

    //int
    int echoInt(int value);
    List<Integer> echoIntegerList(List<Integer> value);

    //short
    short echoShort(short value);
    List<Short> echoShortList(List<Short> value);

    //long
    long echoLong(long value);
    List<Long> echoLongList(List<Long> value);

    //double
    double echoDouble(double value);
    List<Double> echoDoubleList(List<Double> value);

    //string
    String echoString(String value);
    List<String> echoStringList(List<String> value);

}
