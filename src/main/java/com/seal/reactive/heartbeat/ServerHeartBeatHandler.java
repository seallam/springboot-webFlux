package com.seal.reactive.heartbeat;

import com.google.common.collect.Maps;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;

/**
 * @author: seal
 * @Description:
 * @company: xingfeiinc
 * @e-mail: linxiao@xingfeiinc.com
 * @date: 2018-10-30 15:00
 */
public class ServerHeartBeatHandler extends ChannelInboundHandlerAdapter {

    private static Map<String, String> AUTH_IP_MAP = Maps.newHashMap();
    private static final String SUCCESS_KEY = "auth_success_key";

    static {
        AUTH_IP_MAP.put("192.168.10.107", "1234");
    }

    private boolean auth(ChannelHandlerContext ctx, Object msg) {
        String[] ret = ((String) msg).split(",");
        String auth = AUTH_IP_MAP.get(ret[0]);
        if (auth != null && auth.equals(ret[1])) {
            ctx.writeAndFlush(SUCCESS_KEY);
            return true;
        } else {
            ctx.writeAndFlush("auth fail!").addListener(ChannelFutureListener.CLOSE);
            return false;
        }
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof String) {
            auth(ctx, msg);
        } else if (msg instanceof RequestInfo) {
            RequestInfo info = (RequestInfo) msg;
            System.out.println("--------------------------------------------");
            System.out.println("当前主机ip为: " + info.getIp());
            System.out.println("当前主机cpu情况: ");
            Map<String, Object> cpu = info.getCpuPercMap();
            System.out.println("总使用率: " + cpu.get("combined"));
            System.out.println("用户使用率: " + cpu.get("user"));
            System.out.println("系统使用率: " + cpu.get("sys"));
            System.out.println("等待率: " + cpu.get("wait"));
            System.out.println("空闲率: " + cpu.get("idle"));

            System.out.println("当前主机memory情况: ");
            Map<String, Object> memory = info.getMemoryMap();
            System.out.println("内存总量: " + memory.get("total"));
            System.out.println("当前内存使用量: " + memory.get("used"));
            System.out.println("当前内存剩余量: " + memory.get("free"));
            System.out.println("--------------------------------------------");

            ctx.writeAndFlush("info received!");
        } else {
            ctx.writeAndFlush("connect fail!").addListener(ChannelFutureListener.CLOSE);
        }
    }
}

