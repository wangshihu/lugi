/*
 * Copyright 2011-2014 Mogujie Co.Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.benchu.lu.network.netty;

import com.benchu.lu.network.message.LuMessage;
import com.benchu.lu.network.serialize.LubboCodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyCodecAdaptor {

    private LubboCodec codec;

    ByteToMessageDecoder decoder() {
        return new LubboDecoder();
    }

    MessageToByteEncoder encoder() {
        return new LubboEncoder();
    }

    private class LubboEncoder extends MessageToByteEncoder<LuMessage> {

        @Override
        protected void encode(ChannelHandlerContext ctx, LuMessage msg, ByteBuf out) throws Exception {
           codec.encode(msg,out);
        }

    }

    private class LubboDecoder extends LengthFieldBasedFrameDecoder {

        private LubboDecoder() {
            // 写入的头部的长度值包含自身字段长度
            super(Integer.MAX_VALUE, 0, 4, -4, 0);
        }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
            ByteBuf frame = (ByteBuf) super.decode(ctx, in);
            if (frame != null) {
                return codec.decoder(frame);
            }
            return null;
        }

    }

    public NettyCodecAdaptor setCodec(LubboCodec codec) {

        this.codec = codec;
        return this;
    }
}
