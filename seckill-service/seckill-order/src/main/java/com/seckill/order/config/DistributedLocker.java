package com.seckill.order.config;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.order.config.DistributedLocker
 ****/
public interface DistributedLocker {

    /****
     * 加锁,会一直循环加锁，直到拿到锁
     */
    RLock lock(String lockkey);

    /****
     * 加锁，指定超时时间
     */
    RLock lock(String lockkey,long timeout);

    /****
     * 加锁，指定超时时间和超时单位
     */
    RLock lock(String lockkey, long timeout, TimeUnit unit);


    /****
     * 加锁，指定超时时间和锁的释放时间
     */
    boolean tryLock(String lockkey,long timeout,long leasetime,TimeUnit unit);

    /****
     * 解锁
     */
    void unLock(String lockkey);

    /***
     * 解锁
     */
    void unLocke(RLock lock);
}
