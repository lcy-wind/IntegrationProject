package com.teamwork.integrationproject.utils.snowflake;

import org.springframework.beans.factory.annotation.Value;

/**
 * 雪花id生成器
 */
public class SnowFlake
{
    /**
     * 起始的时间戳
     */
    private final static long START_STMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数，一个时间戳最多产生4096个序列号
    private final static long MACHINE_BIT = 5;   //机器标识占用的位数，最大支持32台机器
    private final static long DATACENTER_BIT = 5;//数据中心占用的位数，最大支持32个数据中心

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    public long datacenterId;  //数据中心
    public long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳

    public long getDatacenterId()
    {
        return datacenterId;
    }

    public long getMachineId()
    {
        return machineId;
    }

    public SnowFlake(@Value("${snowflake.datacenter-id}") long datacenterId,
                     @Value("${snowflake.machine-id}") long machineId)
    {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0)
        {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0)
        {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个Id
     */
    public synchronized Long nextId()
    {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp)
        {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp)
        {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L)
            {
                currStmp = getNextMill();
            }
        }
        else
        {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill()
    {
        long mill = getNewstmp();
        while (mill <= lastStmp)
        {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp()
    {
        return System.currentTimeMillis();
    }

    //-------------------------------------------------------------------------
    //静态实例

    //private static SnowFlake snowFlake = new SnowFlake(0, 0);
    private volatile static SnowFlake snowFlake;

    /**
     * 初始化
     */
    public static void init(long datacenterId, long machineId)
    {
        snowFlake = new SnowFlake(datacenterId, machineId);
    }

    public static SnowFlake instant()
    {
        if (snowFlake == null)
            synchronized (SnowFlake.class)
            {
                if (snowFlake == null)
                {
                    snowFlake = new SnowFlake(0, 0);
                }
            }
        return snowFlake;
    }
}
