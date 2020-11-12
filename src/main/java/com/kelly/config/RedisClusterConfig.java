package com.kelly.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisClusterConfig {

    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers().addNodeAddress("redis://192.168.20.101:6371").addNodeAddress("redis://192.168.20.101:6372").addNodeAddress("redis://192.168.20.101:6373")
                .addNodeAddress("redis://192.168.20.101:6374").addNodeAddress("redis://192.168.20.101:6375")
                .addNodeAddress("redis://192.168.20.101:6376").setPassword("123456");
        RedissonClient client = Redisson.create(config);
        System.out.printf(client.getKeys().count()+"");
    }

}
