import handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2020/1/13
 * Time: 10:29 PM
 * Description: No Description
 */
public class NettyServer {
    private int port;

    private String host;

    public int getPort() {
        return port;
    }

    public void setPort( int port ) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost( String host ) {
        this.host = host;
    }

    public NettyServer( String host, int port){
        this.host=host;
        this.port=port;
    }

    public static void main( String[] args ) throws InterruptedException {
        NettyServer nettyServer = new NettyServer("localhost",8090);
        nettyServer.start();
    }

    public void start() throws InterruptedException {
        //指定一个NioEventLoopGroup来接受和处理新的连接(因为是NIO传输)
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        //读/写数据
        EventLoopGroup childGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        try{
            bootstrap.group(parentGroup,childGroup)
                    .channel(NioServerSocketChannel.class)
                    //服务器绑定到这个地址，以监听新的连接请求
                    .localAddress(new InetSocketAddress(this.getPort()))
                    //当一个连接被接受时，一个新的socketChannel被创建，而ChannelInitializer会为这个channel绑定一个ChannelHandler
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel( SocketChannel socketChannel ) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            //调用sync，会将绑定的动作阻塞，直到绑定完成
            ChannelFuture future =bootstrap.bind().sync();

            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete( ChannelFuture channelFuture ) throws Exception {
                    System.out.println("=======启动成功======");
                }
            });
            //该应用程序将会阻塞，直到服务的channel关闭
            ChannelFuture closeFuture = future.channel().closeFuture().sync();
        }finally {//最终要记得关闭 group
            if(parentGroup!=null){
                parentGroup.shutdownGracefully().sync();

            }
            if(childGroup!=null){
                childGroup.shutdownGracefully().sync();

            }
        }


    }
}
