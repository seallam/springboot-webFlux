package com.seal.reactive.heartbeat;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * @author: seal
 * @Description: Marshalling工厂
 * @company: xingfeiinc
 * @e-mail: linxiao@xingfeiinc.com
 * @date: 2018-10-30 14:30
 */
public class MarshallingCodeFactory {

    /**
     * @Description: 创建jboss Marshalling解码器和MarshallingDecoder
     * @author: seal
     * @date: 10/30/18
     * @return
     */
    public static MarshallingDecoder buildMarshallingDecoder() {
        // 首先通过Marshalling工具类的方法获取Marshalling实例,参数serial标识创建的是java序列化工厂对象
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        // 创建MarshallingConfiguration对象,配置了版本号为5
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        // 根据MarshallerFactory和configuration创建provider
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        // 构建netty的MarshallingDecoder对象,2个参数分别为provider和单个消息序列化后的最大长度
        return new MarshallingDecoder(provider, 1024 * 1024);
    }

    /**
     * @Description: 创建jboss Marshalling编码器MarshallingEncoder
     * @author: seal
     * @date: 10/30/18
     * @return
     */
    public static MarshallingEncoder buildMarshallingEncoder() {
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        // 构建netty的MarshallingEncoder对象,MarshallingEncoder用于实现序列化接口的pojo对象序列化为二进制数组
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        return new MarshallingEncoder(provider);
    }
}
