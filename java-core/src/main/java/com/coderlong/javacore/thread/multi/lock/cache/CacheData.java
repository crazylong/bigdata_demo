package com.coderlong.javacore.thread.multi.lock.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁使用案例
 * @author Long Qiong
 * @create 2019/4/17
 */
public class CacheData {
    Object data;
    volatile boolean cacheValid;
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    void processCacheData(){
        rwl.readLock().lock();
        if(!cacheValid){
            //Must release read lock before acquiring write lock
            rwl.readLock().unlock();
            rwl.writeLock().lock();
            try {
                //Recheck state because another thread might hava
                //acquired write lock and changed state before we did.
                if(!cacheValid){
                    data = "testData";
                    cacheValid = true;
                }

                //Downgrade by acquiring read lock before releasing write lock
                rwl.readLock().lock();
            } finally {
                rwl.writeLock().unlock();
            }
        }

        try{

            //use(data)
        } finally {
            rwl.readLock().unlock();
        }
    }
}

class CacheDemo{
    Map<String, Object> dataMap = new HashMap<>();
    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    volatile boolean cacheValid;
    public Object getData(String key){
        rwl.readLock().lock();
        Object data = null;
        try{
            data = dataMap.get(key);
            if(data == null){
                try{
                    rwl.readLock().unlock();
                    rwl.writeLock().lock();
                    data = "testData";
                    rwl.readLock().lock();
                } finally {
                    rwl.writeLock().unlock();
                }
                //rwl.readLock().lock();
            }
        } finally {
            rwl.readLock().unlock();
        }

        return data;
    }
}


