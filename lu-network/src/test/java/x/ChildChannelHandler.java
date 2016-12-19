package x;

import java.nio.charset.Charset;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel e) throws Exception {


		e.pipeline().addLast(new LineBasedFrameDecoder(1024));
		e.pipeline().addLast(new StringDecoder(Charset.forName("utf-8")));
		e.pipeline().addLast(new StringEncoder(Charset.forName("utf-8")));
		e.pipeline().addLast(new MyServerHanlder());

	}

}
