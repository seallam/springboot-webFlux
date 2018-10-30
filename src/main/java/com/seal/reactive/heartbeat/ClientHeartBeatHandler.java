package com.seal.reactive.heartbeat;

import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author: seal
 * @Description:
 * @company: xingfeiinc
 * @e-mail: linxiao@xingfeiinc.com
 * @date: 2018-10-30 15:38
 */
public class ClientHeartBeatHandler extends ChannelInboundHandlerAdapter {

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private ScheduledFuture<?> heartBeat;

    private InetAddress address;

    private static final String SUCCESS_KEY = "auth_success_key";

    public void channelActive(ChannelHandlerContext ctx) throws UnknownHostException {
        address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        String key = "1234";
        String auth = ip + "," + key;
        ctx.writeAndFlush(auth);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof String) {
                String ret = (String) msg;
                if (SUCCESS_KEY.equals(ret)) {
                    this.heartBeat = this.executorService.scheduleWithFixedDelay(new HeartBeatTask(ctx), 0, 2, TimeUnit.SECONDS);
                    System.out.println(msg);
                }
            } else {
                System.out.println(msg);
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private class HeartBeatTask implements Runnable {
        private final ChannelHandlerContext ctx;

        private HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            try {
                RequestInfo info = new RequestInfo();
                info.setIp(address.getHostAddress());
                Sigar sigar = new Sigar();
                //cpu prec
                CpuPerc cpuPerc = sigar.getCpuPerc();
                HashMap<String, Object> cpuPercMap = Maps.newHashMap();
                cpuPercMap.put("combined", cpuPerc.getCombined());
                cpuPercMap.put("user", cpuPerc.getUser());
                cpuPercMap.put("sys", cpuPerc.getSys());
                cpuPercMap.put("wait", cpuPerc.getWait());
                cpuPercMap.put("idle", cpuPerc.getIdle());
                // memory
                Mem mem = sigar.getMem();
                HashMap<String, Object> memoryMap = Maps.newHashMap();
                memoryMap.put("total", mem.getTotal() / 1024L);
                memoryMap.put("used", mem.getUsed() / 1024L);
                memoryMap.put("free", mem.getFree() / 1024L);
                info.setCpuPercMap(cpuPercMap);
                info.setMemoryMap(memoryMap);
                ctx.writeAndFlush(info);
            } catch (SigarException e) {
                e.printStackTrace();
            }
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (heartBeat != null) {
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }
}
