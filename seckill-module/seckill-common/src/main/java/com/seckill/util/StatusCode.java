package com.seckill.util;

/**
 * 返回码
 */
public class StatusCode {
    public static final int OK = 20000;//成功
    public static final int ERROR = 20001;//失败
    public static final int LOGINERROR = 20002;//用户名或密码错误
    public static final int ACCESSERROR = 20003;//权限不足
    public static final int REMOTEERROR = 20004;//远程调用失败
    public static final int REPERROR = 20005;//重复操作
    public static final int NOTFOUNDERROR = 20006;//没有对应的抢购数据


    //库存递减状态码
    public static final int DECOUNT_1=1;    //递减成功
    public static final int DECOUNT_NUM=405;//库存不足
    public static final int DECOUNT_HOT=205;//商品是热卖商品
    public static final int DECOUNT_OK=200;//库存递减成功
    public static final int ORDER_QUEUE=202;//抢购商品正在排队
    public static final int ORDER_OK=200;//抢单成功

    //令牌无效
    public static final int TOKEN_ERROR=401;
}
