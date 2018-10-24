package com.seal.reactive.service;

import com.google.common.collect.Maps;
import com.seal.reactive.entity.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * @author: seal
 * @Description:
 * @company: xingfeiinc
 * @e-mail: linxiao@xingfeiinc.com
 * @date: 2018-10-22 15:24
 */
@Service
public class UserService {

    private Map<String, User> userMap = Maps.newConcurrentMap();

    public Flux<User> list() {
        return Flux.fromIterable(userMap.values());
    }
}
