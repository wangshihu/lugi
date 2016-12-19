package x;

import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHanlder extends SimpleChannelInboundHandler {

	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		System.out.println(ctx.channel().localAddress().toString()+" channelActive");

	}

	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		System.out.println(ctx.channel().localAddress().toString()+" channelInactive");

	}


	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {


		System.out.println("\r\n===================== �����յ���Ϣ Start=====================");

		System.out.println(new Date().toString() +msg);

		System.out.println("===================== �����յ���Ϣ   End=====================");

		System.out.println("\r\n===================== �����յ���Ϣ Start=====================");

		ctx.writeAndFlush(new Date().toString()+" ������յ���" + msg);

		System.out.println("===================== �����յ���Ϣ End=====================");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("\r\n===================== �����յ���Ϣ Start=====================");

		System.out.println(new Date().toString() +msg);

		System.out.println("===================== �����յ���Ϣ   End=====================");

		System.out.println("\r\n===================== �����յ���Ϣ Start=====================");

		ctx.writeAndFlush(new Date().toString()+" ������յ���" + msg);

		System.out.println("===================== �����յ���Ϣ End=====================");
	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
		System.out.println("�쳣��Ϣ��\r\n"+cause.getMessage());
	}

	
	
}
