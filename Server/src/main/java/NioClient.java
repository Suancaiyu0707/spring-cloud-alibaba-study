//import handler.NettyClientHandler;
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//
//import java.net.InetSocketAddress;
//
///**
// * Created with IntelliJ IDEA.
// * User: zhifang.xu
// * Date: 2020/1/14
// * Time: 9:40 PM
// * Description: No Description
// */
//public class NioClient {
//
//    private String remoteHost;
//
//    private int remotePort;
//
//    public NioClient(String remoteHost,int remotePort){
//        this.remoteHost = remoteHost;
//        this.remotePort = remotePort;
//    }
//
//
//    public void connect(){
//        //创建group用于处理客户端事件
//        EventLoopGroup group = new NioEventLoopGroup();
//
//        Bootstrap bootstrap = new Bootstrap();
//        try {
//            bootstrap.group(group)
//                    .channel(NioSocketChannel.class)
//                    .remoteAddress(new InetSocketAddress(remoteHost, remotePort))
//                    .handler(new ChannelInitializer <SocketChannel>() {
//                        @Override
//                        protected void initChannel( SocketChannel socketChannel ) throws Exception {
//                            socketChannel.pipeline().addLast(new NettyClientHandler());
//                        }
//                    });
//            //连接到远程节点，阻塞等待连接完成
//            ChannelFuture connectFuture = bootstrap.connect().sync();
//            //阻塞，直到channel关闭
//            connectFuture.channel().closeFuture().sync();
//        }catch (Exception e){
//
//        }finally {
//            //关闭线程，并且释放所有资源
//            if(group!=null){
//                try {
//                    group.shutdownGracefully().sync();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    public String getRemoteHost() {
//        return remoteHost;
//    }
//
//    public void setRemoteHost( String remoteHost ) {
//        this.remoteHost = remoteHost;
//    }
//
//    public int getRemotePort() {
//        return remotePort;
//    }
//
//    public void setRemotePort( int remotePort ) {
//        this.remotePort = remotePort;
//    }
//}
