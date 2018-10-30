package com.seal.reactive.heartbeat;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author: seal
 * @Description: request请求封装对象
 * @company: xingfeiinc
 * @e-mail: linxiao@xingfeiinc.com
 * @date: 2018-10-30 14:52
 */
public class RequestInfo implements Serializable {
    private static final long serialVersionUID = -7528277141582486883L;

    private String ip;

    private HashMap<String, Object> cpuPercMap;

    private HashMap<String, Object> memoryMap;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public HashMap<String, Object> getCpuPercMap() {
        return cpuPercMap;
    }

    public void setCpuPercMap(HashMap<String, Object> cpuPercMap) {
        this.cpuPercMap = cpuPercMap;
    }

    public HashMap<String, Object> getMemoryMap() {
        return memoryMap;
    }

    public void setMemoryMap(HashMap<String, Object> memoryMap) {
        this.memoryMap = memoryMap;
    }
}
