package org.example.netuser.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public final class NetUserClient {

    static final String HOST = "127.0.0.1";
    static final int PORT = 8001;

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new NetUserClientHandler());
                        }
                    });

            ChannelFuture f = b.connect(HOST, PORT).sync();

            String input = "Hello from client";
            Channel channel = f.sync().channel();
            channel.writeAndFlush(input);
            channel.flush();

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}

