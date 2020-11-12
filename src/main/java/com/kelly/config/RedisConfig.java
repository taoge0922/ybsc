package com.kelly.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.StreamMessageId;
import org.redisson.config.Config;

import java.util.Map;

public class RedisConfig {

    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setPassword("123456");
        RedissonClient client = Redisson.create(config);
        RedisMq<String,String,String> rm = new RedisMq<>(client,"testttt","gg","c2");
//        rm.createGroup();
        long id = rm.start((r)-> {
            if(r!=null&&r.size()>0){
                for (Map.Entry<StreamMessageId, Map<String, String>> entry : r.entrySet()) {
                    Map<String, String> m2 = entry.getValue();
                    for(Map.Entry<String,String> entry1:m2.entrySet()){
                        System.out.println("c2 read : Key = " + entry1.getKey() + ", Value = " + entry1.getValue());
                    }
                    rm.ackAndDel(entry.getKey());
                }
            }
        });

        RedisMq<String,String,String> rm2 = new RedisMq<>(client,"testttt","gg","c3");

        long id2 = rm.start((r)-> {
            if(r!=null&&r.size()>0){
                for (Map.Entry<StreamMessageId, Map<String, String>> entry : r.entrySet()) {
                    Map<String, String> m2 = entry.getValue();
                    for(Map.Entry<String,String> entry1:m2.entrySet()){
                        System.out.println("c3 read : Key = " + entry1.getKey() + ", Value = " + entry1.getValue());
                    }
                    rm2.ackAndDel(entry.getKey());
                }
            }
        });

//        for(int i=0;i<15;i++){
//            rm.addMsg("name","里斯"+i);
//            Thread.sleep(500);
//        }

        Thread.sleep(10000);
        RedisMq.stop(id);
        RedisMq.stop(id2);
    }
}
