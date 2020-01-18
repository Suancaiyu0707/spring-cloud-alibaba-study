package handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.jboss.netty.util.CharsetUtil;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2020/1/14
 * Time: 9:26 PM
 * Description: No Description
 *
 * SimpleChannelInboundHandler和ChannelInboundHandler
 *  1）在客户端，当 channelRead0()方法完成时，你已经有了传入消息，并且已经处理完它了。
 *      当该方 法返回时，SimpleChannelInboundHandler 负责释放指向保存该消息的 ByteBuf 的内存引用。
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /***
     * 每当接收数据时，都会调用这个方法
     *      需要注 意的是，由服务器发送的消息可能会被分块接收。
     *      也就是说，如果服务器发送了 5 字节，那么不 能保证这 5 字节会被一次性接收。
     *      即使是对于这么少量的数据，channelRead0()方法也可能 会被调用两次，
     *      第一次使用一个持有 3 字节的 ByteBuf(Netty 的字节容器)，第二次使用一个 持有 2 字节的 ByteBuf。
     *      作为一个面向流的协议，TCP 保证了字节数组将会按照服务器发送它 们的顺序被接收。
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0( ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf ) throws Exception {
        System.out.println("Client received: "+byteBuf.toString(CharsetUtil.UTF_8));

    }

    /***
     * 将在一个连接建立时被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive( ChannelHandlerContext ctx ) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8));

    }

    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception {
        cause.printStackTrace();;
        ctx.close();
    }
}
