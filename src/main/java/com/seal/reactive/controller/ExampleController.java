package com.seal.reactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: seal
 * @Description:
 * @company: xingfeiinc
 * @e-mail: linxiao@xingfeiinc.com
 * @date: 2018-10-22 16:03
 */
@RestController
public class ExampleController {

    @PostMapping(path = "/", consumes = {MediaType.APPLICATION_JSON_VALUE,
            "!application/xml"}, produces = MediaType.TEXT_PLAIN_VALUE, headers = "X-Custom=Foo", params = "a!=alpha")
    public String example() {
        return "Hello World";
    }


}

