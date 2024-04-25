package org.example.netuser.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
public class NetUserServerHandler extends SimpleChannelInboundHandler<String> {

    static final List<Channel> channels = new ArrayList<>();

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        System.out.println("Client joined - " + ctx);
        channels.add(ctx.channel());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("Message received: " + msg);
        for (Channel c : channels) {
            c.writeAndFlush("SERVER: " + msg + '\n');
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("Closing connection for client - " + ctx);
        ctx.close();
    }
}
