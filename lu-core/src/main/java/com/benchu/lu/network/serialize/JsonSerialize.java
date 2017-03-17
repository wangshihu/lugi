package com.benchu.lu.network.serialize;

import static com.alibaba.fastjson.serializer.SerializerFeature.DisableCircularReferenceDetect;
import static com.alibaba.fastjson.serializer.SerializerFeature.NotWriteDefaultValue;
import static com.alibaba.fastjson.serializer.SerializerFeature.QuoteFieldNames;
import static com.alibaba.fastjson.serializer.SerializerFeature.SkipTransientField;
import static com.alibaba.fastjson.serializer.SerializerFeature.SortField;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteClassName;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteEnumUsingToString;
import static com.benchu.lu.common.constans.CommonConstants.DEFAULT_CHARSET;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.benchu.lu.common.constans.CommonConstants;

/**
 * @author benchu
 * @version 2016/12/20.
 */
public class JsonSerialize {
    static {
        JSON.DEFAULT_TYPE_KEY = "javaType";
    }

    public byte[] serialize(Object obj) {

        return JSON.toJSONString(obj, QuoteFieldNames, SkipTransientField, SortField, WriteEnumUsingToString,
                                 NotWriteDefaultValue, WriteClassName, DisableCircularReferenceDetect)
                   .getBytes(DEFAULT_CHARSET);
    }

    public <E> E unSerialize(byte[] data, Class<E> clazz) {
        String value = new String(data, CommonConstants.DEFAULT_CHARSET);
        return JSON.parseObject(value, clazz, Feature.AllowArbitraryCommas, Feature.IgnoreNotMatch,
                                Feature.SortFeidFastMatch, Feature.DisableCircularReferenceDetect,
                                Feature.AutoCloseSource);
    }

}
