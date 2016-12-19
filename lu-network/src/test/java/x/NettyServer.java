package x;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

	public static void main(String[] args) {
		
		try {
			System.out.println("����˿����ȴ��ͻ�������");
			new NettyServer().bing(7397);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void bing(int port) throws Exception{
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try {
			
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup);
			b.channel(NioServerSocketChannel.class);
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.childHandler(new ChildChannelHandler());
			
			// �󶨶˿�
			ChannelFuture f = b.bind(port).sync();
			
			// �ȴ�����˼����˿ڹر�
			f.channel().closeFuture().sync();
			
		} finally {
			
			// ���ŵ��˳�
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
		
	}
	
}
