package handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2020/1/13
 * Time: 10:30 PM
 * Description: No Description
 * 在 EchoServerHandler 中，你仍然需要将传入消息回送给发送者，而 write()操作是异步的，直 到 channelRead()方法返回后可能仍然没有完成(如代码清单 2-1 所示)。为此，EchoServerHandler 扩展了 ChannelInboundHandlerAdapter，其在这个时间点上不会释放消息
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    public NettyServerHandler() {
        super();
    }

    @Override
    public boolean isSharable() {
        return true;
    }

    @Override
    public void channelRegistered( ChannelHandlerContext ctx ) throws Exception {
        super.channelRegistered(ctx);
    }

    /***
     * 处理 接收到的远程数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("Server received: "+ buf.toString(CharsetUtil.UTF_8));
        // write()操作是异步的，直 到 channelRead()方法返回后可能仍然没有完成。其在这个时间点上不会释放消息
        ctx.write(buf);//并将数据写到远程节点上
    }

    @Override
    public void channelReadComplete( ChannelHandlerContext ctx ) throws Exception {
        ChannelFuture future = ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
        future.addListener(ChannelFutureListener.CLOSE);

    }

    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception {
        cause.printStackTrace();;
        ctx.close();
    }
}
