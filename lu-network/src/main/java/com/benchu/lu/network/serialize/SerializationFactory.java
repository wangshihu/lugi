package com.benchu.lu.network.serialize;

import com.alibaba.fastjson.JSON;
import com.benchu.lu.common.CommonConstants;
import com.benchu.lu.network.message.SerializeType;

/**
 * @author benchu
 * @version on 15/10/28.
 */
public class SerializationFactory {
    public <T> T unSerialize(SerializeType serializeType, byte[] data, Class<T> clazz) {
        return JSON.parseObject(new String(data, CommonConstants.DEFAULT_CHARSET), clazz);
    }

    public byte[] serialize(SerializeType serializeType, Object object) {
        return JSON.toJSONString(object).getBytes(CommonConstants.DEFAULT_CHARSET);
    }

}
