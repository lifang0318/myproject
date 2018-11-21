package cn.com.izj;

import cn.com.izj.codec.MessageDecoder;
import cn.com.izj.codec.MessageEncoder;
import cn.com.izj.handlers.IdleCheckHandler;
import cn.com.izj.handlers.ServerChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by GouBo on 2018/6/30 11:48.
 */
@Component
public class TcpStarter {

    private static Logger logger = LoggerFactory.getLogger(TcpStarter.class);

    @Value("${tcp.port:8888}")
    private int port;

    private NioEventLoopGroup serverWorkerGroup;
    private NioEventLoopGroup serverBossGroup;

    public TcpStarter() {
        serverBossGroup = new NioEventLoopGroup();
        serverWorkerGroup = new NioEventLoopGroup();
    }

    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(serverBossGroup, serverWorkerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new MessageDecoder());
                ch.pipeline().addLast(new MessageEncoder());
                ch.pipeline().addLast(new IdleCheckHandler());
                ch.pipeline().addLast(new ServerChannelHandler());
            }
        });

        try {
            bootstrap.bind(port).get();
            logger.info("tcp服务启动成功,监听端口:" + port);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void stop() {
        serverBossGroup.shutdownGracefully();
        serverWorkerGroup.shutdownGracefully();
    }

}
