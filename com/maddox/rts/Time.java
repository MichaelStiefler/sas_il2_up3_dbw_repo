// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Time.java

package com.maddox.rts;

import java.io.PrintStream;

// Referenced classes of package com.maddox.rts:
//            RTSConf, MessageQueue, Message, RTS

public final class Time
{

    public static boolean isPaused()
    {
        return com.maddox.rts.RTSConf.cur.time.bPause;
    }

    public static boolean isRealOnly()
    {
        return com.maddox.rts.RTSConf.cur.time.bRealOnly;
    }

    public static int tickCounter()
    {
        return com.maddox.rts.RTSConf.cur.time.tickCounter;
    }

    public static long tick()
    {
        return com.maddox.rts.RTSConf.cur.time.tick;
    }

    public static long tickNext()
    {
        return com.maddox.rts.RTSConf.cur.time.tickNext;
    }

    public static int tickLen()
    {
        return com.maddox.rts.RTSConf.cur.time.tickLen;
    }

    public static float tickLenFms()
    {
        return com.maddox.rts.RTSConf.cur.time.tickLenFms;
    }

    public static float tickLenFs()
    {
        return com.maddox.rts.RTSConf.cur.time.tickLenFs;
    }

    public static float tickOffset()
    {
        return com.maddox.rts.RTSConf.cur.time._tickOffset();
    }

    protected float _tickOffset()
    {
        return tickOffset;
    }

    public static float tickOffset(long l)
    {
        return com.maddox.rts.RTSConf.cur.time._tickOffset(l);
    }

    protected float _tickOffset(long l)
    {
        if(l <= tick)
            return 0.0F;
        if(l >= tickNext)
            return 1.0F;
        else
            return (float)(l - tick) / tickLenFms;
    }

    public static long current()
    {
        if(com.maddox.rts.RTSConf.cur == null)
            return 0L;
        else
            return com.maddox.rts.RTSConf.cur.time.time;
    }

    public static long currentReal()
    {
        return com.maddox.rts.RTSConf.cur.time.real_time;
    }

    public static long end()
    {
        return com.maddox.rts.RTSConf.cur.time._end();
    }

    protected long _end()
    {
        return bPause ? time : endTime;
    }

    public static long endReal()
    {
        return com.maddox.rts.RTSConf.cur.time._endReal();
    }

    protected long _endReal()
    {
        return endRealTime;
    }

    public static int tickConstLen()
    {
        return com.maddox.rts.RTSConf.cur.time.tickConstLen;
    }

    public static float tickConstLenFms()
    {
        return com.maddox.rts.RTSConf.cur.time.tickConstLenFms;
    }

    public static float tickConstLenFs()
    {
        return com.maddox.rts.RTSConf.cur.time.tickConstLenFs;
    }

    public void loopMessages()
    {
        if(bChangeSpeed)
        {
            _resetSpeed(newChangedSpeed);
            bChangeSpeed = false;
        }
        endRealTime = com.maddox.rts.Time.real();
        if(endRealTime == real_time)
            return;
        bInLoop = true;
        beginRealTime = real_time;
        beginTime = time;
        if(endRealTime - base_real_time > 0xffffffL)
        {
            base_time = beginTime;
            base_real_time = beginRealTime;
        }
        endTime = (long)((double)(endRealTime - base_real_time) * speed + 0.5D + (double)base_time);
        boolean flag = false;
        if(bEnableChangeTickLen)
        {
            if((endTime - beginTime) / (long)tickLen0 > (long)maxTimerTicksInRealTick)
            {
                _newTickLen = (int)((endTime - beginTime) / (long)maxTimerTicksInRealTick + 1L);
                if(_newTickLen > 300)
                {
                    _newTickLen = 300;
                    flag = true;
                }
                if(!bPause && _typedTickCounter + 20 < tickCounter)
                {
                    if(bShowDiag)
                        java.lang.System.err.println("Time overflow (" + tickCounter + "): tickLen " + _newTickLen);
                    _typedTickCounter = tickCounter;
                }
            } else
            {
                _newTickLen = (tickLen0 + 2 * tickLen) / 3;
            }
        } else
        {
            _newTickLen = tickLen0;
            flag = true;
        }
        if(bEnableChangeSpeed && flag)
        {
            if((endTime - beginTime) / (long)tickLen > (long)maxTimerTicksInRealTick)
            {
                long l = endRealTime - beginRealTime;
                speedCur = (double)(tickLen * maxTimerTicksInRealTick) / ((double)l * speed);
                if(speedCur >= 1.0D)
                {
                    speedCur = 1.0D;
                } else
                {
                    if(speed * speedCur < 0.050000000000000003D)
                        speedCur = 0.050000000000000003D / speed;
                    endTime = (long)((double)l * speed * speedCur + 0.5D + (double)beginTime);
                    base_time = beginTime;
                    base_real_time = beginRealTime;
                    if(!bPause && bShowDiag)
                        java.lang.System.err.println("Time overflow (" + tickCounter + "): speed " + (float)speedCur);
                }
            } else
            {
                speedCur = 1.0D;
            }
        } else
        {
            speedCur = 1.0D;
        }
        com.maddox.rts.RTSConf.cur.queueRealTimeNextTick.moveFromNextTick(com.maddox.rts.RTSConf.cur.queueRealTime, true);
_L3:
        com.maddox.rts.Message message1;
        com.maddox.rts.Message message2;
label0:
        {
            if(bPause)
            {
                com.maddox.rts.Message message = com.maddox.rts.RTSConf.cur.queueRealTime.get(endRealTime);
                if(message == null)
                {
                    real_time = endRealTime;
                    com.maddox.rts.Time.setCurrent(time, real_time);
                    break; /* Loop/switch isn't completed */
                }
                if(message._time > real_time)
                {
                    real_time = message._time;
                    com.maddox.rts.Time.setCurrent(time, real_time);
                }
                message.trySend();
                if(!bPause)
                {
                    endTime = (long)((double)(endRealTime - real_time) * speed * speedCur + 0.5D + (double)beginTime);
                    base_time = beginTime;
                    base_real_time = real_time;
                }
                continue; /* Loop/switch isn't completed */
            }
            message1 = null;
            synchronized(com.maddox.rts.RTSConf.cur.queueRealTime)
            {
                synchronized(com.maddox.rts.RTSConf.cur.queue)
                {
                    message1 = com.maddox.rts.RTSConf.cur.queue.peek(endTime);
                    message2 = com.maddox.rts.RTSConf.cur.queueRealTime.peek(endRealTime);
                    if(message1 != null || message2 != null)
                        break label0;
                    if(_setCurrent(endTime, endRealTime))
                        break MISSING_BLOCK_LABEL_1088;
                }
            }
            break; /* Loop/switch isn't completed */
        }
        long l1 = 0L;
        boolean flag1;
        if(message2 != null)
        {
            l1 = _fromReal(message2._time);
            if(l1 >= endTime)
                l1 = endTime - 1L;
            if(message1 != null)
            {
                if(message1._time < l1 || message1._time == l1 && message1._tickPos < message2._tickPos)
                    flag1 = false;
                else
                    flag1 = true;
            } else
            {
                flag1 = true;
            }
        } else
        {
            flag1 = false;
        }
        if(flag1)
        {
            if(l1 > time)
            {
                if(!_setCurrent(l1, message2._time))
                    message1 = com.maddox.rts.RTSConf.cur.queueRealTime.get(endRealTime);
                else
                    message1 = null;
            } else
            {
                if(message2._time > real_time)
                {
                    real_time = message2._time;
                    com.maddox.rts.Time.setCurrent(time, real_time);
                }
                message1 = com.maddox.rts.RTSConf.cur.queueRealTime.get(endRealTime);
            }
        } else
        {
            long l2 = _toReal(message1._time);
            if(l2 >= endRealTime)
                l2 = endRealTime - 1L;
            if(message1._time > time)
            {
                if(!_setCurrent(message1._time, l2))
                    message1 = com.maddox.rts.RTSConf.cur.queue.get(endTime);
                else
                    message1 = null;
            } else
            {
                if(l2 > real_time)
                {
                    real_time = message2._time;
                    com.maddox.rts.Time.setCurrent(time, real_time);
                }
                message1 = com.maddox.rts.RTSConf.cur.queue.get(endTime);
            }
        }
        messagequeue1;
        JVM INSTR monitorexit ;
        messagequeue;
        JVM INSTR monitorexit ;
          goto _L1
        exception1;
        throw exception1;
_L1:
        if(message1 != null)
            message1.trySend();
        if(true) goto _L3; else goto _L2
_L2:
        bInLoop = false;
        return;
    }

    private boolean _setCurrent(long l, long l1)
    {
        boolean flag = false;
        time = l;
        if(time >= tickNext)
        {
            time = tick = tickNext;
            if(tickLen != _newTickLen)
            {
                tickLen = _newTickLen;
                tickNext += tickLen;
                tickLenFms = tickLen;
                tickLenFs = tickLenFms / 1000F;
            } else
            {
                tickNext += tickLen;
            }
            com.maddox.rts.RTSConf.cur.queueNextTick.moveFromNextTick(com.maddox.rts.RTSConf.cur.queue, false);
            tickCounter++;
            l1 = (long)((double)(time - base_time) / (speed * speedCur) + 0.5D + (double)base_real_time);
            if(l1 >= endRealTime)
                l1 = endRealTime - 1L;
            flag = true;
        }
        if(l1 > real_time)
            real_time = l1;
        com.maddox.rts.Time.setCurrent(time, real_time);
        tickOffset = _tickOffset(time);
        return flag;
    }

    private void _resetSpeed(double d)
    {
        speed = d;
        base_time = time;
        base_real_time = real_time;
    }

    protected void resetGameClear()
    {
        tickCounter = 0;
        tick = 0L;
        tickLen = tickLen0;
        tickNext = tickLen;
        tickLenFms = tickLen;
        tickLenFs = tickLenFms / 1000F;
        time = 0L;
        tickOffset = 0.0F;
        base_time = 0L;
        real_time = base_real_time = com.maddox.rts.Time.real();
        com.maddox.rts.Time.setCurrent(time, real_time);
        bPause = true;
    }

    protected void resetGameCreate()
    {
    }

    public final void setCurrent(long l)
    {
        if(!bPause)
        {
            return;
        } else
        {
            base_time = time = l;
            tickCounter = (int)(time / (long)tickLen0);
            tick = tickCounter * tickLen0;
            tickNext = tick + (long)tickLen0;
            tickOffset = _tickOffset(time);
            com.maddox.rts.Time.setCurrent(time, real_time);
            return;
        }
    }

    public static final void setPause(boolean flag)
    {
        com.maddox.rts.RTSConf.cur.time._setPause(flag);
    }

    protected final void _setPause(boolean flag)
    {
        if(bPause == flag)
            return;
        bPause = flag;
        if(!bInLoop && !bPause)
        {
            base_time = time;
            base_real_time = real_time;
        }
    }

    public static final void setRealOnly(boolean flag)
    {
        com.maddox.rts.RTSConf.cur.time._setRealOnly(flag);
    }

    protected final void _setRealOnly(boolean flag)
    {
        if(bRealOnly == flag)
            return;
        bRealOnly = flag;
        if(bRealOnly && !bPause)
            _setPause(true);
    }

    public static void setSpeed(float f)
    {
        com.maddox.rts.RTSConf.cur.time._setSpeed(f);
    }

    protected void _setSpeed(double d)
    {
        if(bInLoop)
        {
            if(bPause)
            {
                speed = d;
            } else
            {
                bChangeSpeed = true;
                newChangedSpeed = d;
            }
        } else
        {
            bChangeSpeed = false;
            _resetSpeed(d);
        }
    }

    public static float speed()
    {
        return (float)com.maddox.rts.RTSConf.cur.time.speed;
    }

    public static float nextSpeed()
    {
        return com.maddox.rts.RTSConf.cur.time._nextSpeed();
    }

    protected float _nextSpeed()
    {
        if(bChangeSpeed)
            return (float)newChangedSpeed;
        else
            return (float)speed;
    }

    public static long fromReal(long l)
    {
        return com.maddox.rts.RTSConf.cur.time._fromReal(l);
    }

    protected long _fromReal(long l)
    {
        if(bPause)
            return time;
        long l1 = l - base_real_time;
        if(speed == 1.0D && speedCur == 1.0D)
            return l1 + base_time;
        else
            return (long)((double)l1 * speed * speedCur + 0.5D + (double)base_time);
    }

    public static long toReal(long l)
    {
        return com.maddox.rts.RTSConf.cur.time._toReal(l);
    }

    protected long _toReal(long l)
    {
        if(bPause)
            return real_time;
        long l1 = l - base_time;
        if(speed == 1.0D && speedCur == 1.0D)
            return l1 + base_real_time;
        else
            return (long)((double)l1 / (speed * speedCur) + 0.5D + (double)base_real_time);
    }

    public static long fromRaw(int i)
    {
        return com.maddox.rts.RTSConf.cur.time._fromReal(com.maddox.rts.Time.realFromRaw(i));
    }

    public static long realFromRawClamp(int i)
    {
        long l = com.maddox.rts.Time.realFromRaw(i);
        if(l < com.maddox.rts.RTSConf.cur.time.real_time)
            l = com.maddox.rts.RTSConf.cur.time.real_time;
        if(l > com.maddox.rts.RTSConf.cur.time.endRealTime)
            l = com.maddox.rts.RTSConf.cur.time.endRealTime;
        return l;
    }

    public static native long realFromRaw(int i);

    public static native void setSpeedReal(float f);

    public static native float speedReal();

    public static native long real();

    public static native int raw();

    private static native void setCurrent(long l, long l1);

    public static boolean isEnableChangePause()
    {
        com.maddox.rts.Time time1 = com.maddox.rts.RTSConf.cur.time;
        return time1.bEnableChangePause0 && time1.bEnableChangePause1;
    }

    public boolean isEnableChangePause0()
    {
        return bEnableChangePause0;
    }

    public boolean isEnableChangePause1()
    {
        return bEnableChangePause1;
    }

    public static boolean isEnableChangeTickLen()
    {
        return com.maddox.rts.RTSConf.cur.time.bEnableChangeTickLen;
    }

    public static boolean isEnableChangeSpeed()
    {
        return com.maddox.rts.RTSConf.cur.time.bEnableChangeSpeed;
    }

    public void setEnableChangePause0(boolean flag)
    {
        bEnableChangePause0 = flag;
    }

    public void setEnableChangePause1(boolean flag)
    {
        bEnableChangePause1 = flag;
    }

    public void setEnableChangeTickLen(boolean flag)
    {
        bEnableChangeTickLen = flag;
    }

    public void setEnableChangeSpeed(boolean flag)
    {
        bEnableChangeSpeed = flag;
    }

    protected Time(int i, int j)
    {
        _typedTickCounter = 0;
        bInLoop = false;
        bChangeSpeed = false;
        newChangedSpeed = 1.0D;
        tickCounter = 0;
        tick = 0L;
        tickLen0 = 30;
        tickLen = tickLen0;
        tickConstLen = tickLen0;
        tickNext = tick + (long)tickLen;
        tickLenFms = 30F;
        tickLenFs = 0.03F;
        tickConstLenFms = 30F;
        tickConstLenFs = 0.03F;
        maxTimerTicksInRealTick = 5;
        tickOffset = 0.0F;
        bEnableChangePause0 = true;
        bEnableChangePause1 = true;
        bEnableChangeTickLen = true;
        bEnableChangeSpeed = false;
        time = 0L;
        base_time = 0L;
        speed = 1.0D;
        speedCur = 1.0D;
        beginRealTime = 0L;
        beginTime = 0L;
        endRealTime = 0L;
        endTime = 0L;
        bPause = true;
        bRealOnly = false;
        if(i > 300)
            i = 300;
        tickLen0 = i;
        tickLen = i;
        tickConstLen = i;
        tickNext = tick + (long)i;
        tickLenFms = i;
        tickConstLenFms = i;
        tickLenFs = tickLenFms / 1000F;
        tickConstLenFs = tickLenFms / 1000F;
        if(j <= 0)
            j = 1;
        maxTimerTicksInRealTick = j;
        real_time = com.maddox.rts.Time.real();
        base_real_time = real_time;
        beginRealTime = real_time;
        endRealTime = real_time;
        com.maddox.rts.Time.setCurrent(time, real_time);
    }

    protected void setMaxTimerTicksInRealTick(int i)
    {
        if(i <= 0)
            i = 1;
        maxTimerTicksInRealTick = i;
    }

    public static boolean bShowDiag = true;
    private static final long MAX_NATIVE_DELTA = 0xffffffL;
    private static final int MAX_TICK_LEN = 300;
    private int _typedTickCounter;
    private int _newTickLen;
    private boolean bInLoop;
    private boolean bChangeSpeed;
    private double newChangedSpeed;
    private int tickCounter;
    private long tick;
    protected int tickLen0;
    protected int tickLen;
    protected int tickConstLen;
    private long tickNext;
    protected float tickLenFms;
    protected float tickLenFs;
    protected float tickConstLenFms;
    protected float tickConstLenFs;
    protected int maxTimerTicksInRealTick;
    private float tickOffset;
    private boolean bEnableChangePause0;
    private boolean bEnableChangePause1;
    private boolean bEnableChangeTickLen;
    private boolean bEnableChangeSpeed;
    private long real_time;
    private long time;
    private long base_real_time;
    private long base_time;
    protected double speed;
    private double speedCur;
    private long beginRealTime;
    private long beginTime;
    private long endRealTime;
    private long endTime;
    private boolean bPause;
    private boolean bRealOnly;

    static 
    {
        com.maddox.rts.RTS.loadNative();
    }
}
