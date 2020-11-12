package com.kelly.config;

import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.StreamMessageId;
import org.redisson.client.codec.StringCodec;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class RedisMq<K,V,R> {

    private RedissonClient client;
    private String topicname;
    private String groupname;
    private String clientname;

    private RStream<K,V>  stream;

    public RedisMq(RedissonClient client,String topicname){
        this.client = client;
        this.topicname = topicname;
        this.stream = client.getStream(topicname,new StringCodec());
    }

    public RedisMq(RedissonClient client,String topicname,String groupname ,String clientname){
        this(client,topicname);
        this.groupname = groupname;
        this.clientname = clientname;
    }


    public void addMsg(K key,V value){
        stream.add(key,value);
    }

    public void createGroup(){
        stream.createGroup(this.groupname);
    }

    public static void stop(long id){
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while(group!=null){
            Thread[] ts = new Thread[(int)(group.activeCount() * 1.2)];
            int cou = group.enumerate(ts,true);
            for(int i=0;i<cou;i++){
                if(id == ts[i].getId()) {
                    ts[i].interrupt();
                }
            }
            group = group.getParent();
        }
    }

    public long start(Consumer<Map<StreamMessageId, Map<K,V>>> consumer){
        Thread thread = new Thread(()->{
            try{
                while(!Thread.currentThread().isInterrupted()){
                    Map<StreamMessageId, Map<K,V>> result = stream.readGroup(groupname,clientname,1,2000, TimeUnit.MILLISECONDS);
                    consumer.accept(result);
                }
            }catch(Exception e){
            }

        });
        thread.start();
        return thread.getId();
    }

    public void ack(StreamMessageId id){
        stream.ack(groupname,id);
    }

    public void ackAndDel(StreamMessageId id){
        stream.ack(groupname,id);
        stream.remove(id);
    }

}
