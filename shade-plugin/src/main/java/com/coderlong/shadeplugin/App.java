package com.coderlong.shadeplugin;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class App {
    public static void main(String[] args) {
        String key = "hello";
        String value = "redis";
        System.out.println(value);

        JedisPoolConfig config = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379);

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
            System.out.println(jedis.get(key));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        jedisPool.close();
    }
}
