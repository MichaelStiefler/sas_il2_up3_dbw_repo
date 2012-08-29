package com.maddox.rts;

import java.io.PrintStream;

public final class Time
{
  public static boolean bShowDiag = true;
  private static final long MAX_NATIVE_DELTA = 16777215L;
  private static final int MAX_TICK_LEN = 300;
  private int _typedTickCounter = 0;
  private int _newTickLen;
  private boolean bInLoop = false;

  private boolean bChangeSpeed = false;
  private double newChangedSpeed = 1.0D;

  private int tickCounter = 0;
  private long tick = 0L;
  protected int tickLen0 = 30;
  protected int tickLen = this.tickLen0;
  protected int tickConstLen = this.tickLen0;
  private long tickNext = this.tick + this.tickLen;
  protected float tickLenFms = 30.0F;
  protected float tickLenFs = 0.03F;
  protected float tickConstLenFms = 30.0F;
  protected float tickConstLenFs = 0.03F;
  protected int maxTimerTicksInRealTick = 5;
  private float tickOffset = 0.0F;
  private boolean bEnableChangePause0 = true;
  private boolean bEnableChangePause1 = true;
  private boolean bEnableChangeTickLen = true;
  private boolean bEnableChangeSpeed = false;
  private long real_time;
  private long time = 0L;
  private long base_real_time;
  private long base_time = 0L;
  protected double speed = 1.0D;
  private double speedCur = 1.0D;
  private long beginRealTime = 0L;
  private long beginTime = 0L;
  private long endRealTime = 0L;
  private long endTime = 0L;
  private boolean bPause = true;
  private boolean bRealOnly = false;

  public static boolean isPaused()
  {
    return RTSConf.cur.time.bPause;
  }
  public static boolean isRealOnly() {
    return RTSConf.cur.time.bRealOnly;
  }
  public static int tickCounter() {
    return RTSConf.cur.time.tickCounter;
  }
  public static long tick() {
    return RTSConf.cur.time.tick;
  }
  public static long tickNext() {
    return RTSConf.cur.time.tickNext;
  }
  public static int tickLen() {
    return RTSConf.cur.time.tickLen;
  }
  public static float tickLenFms() {
    return RTSConf.cur.time.tickLenFms;
  }
  public static float tickLenFs() {
    return RTSConf.cur.time.tickLenFs;
  }
  public static float tickOffset() {
    return RTSConf.cur.time._tickOffset(); } 
  protected float _tickOffset() { return this.tickOffset; }

  public static float tickOffset(long paramLong) {
    return RTSConf.cur.time._tickOffset(paramLong);
  }
  protected float _tickOffset(long paramLong) { if (paramLong <= this.tick)
      return 0.0F;
    if (paramLong >= this.tickNext) {
      return 1.0F;
    }
    return (float)(paramLong - this.tick) / this.tickLenFms;
  }

  public static long current()
  {
    if (RTSConf.cur == null)
      return 0L;
    return RTSConf.cur.time.time;
  }

  public static long currentReal()
  {
    return RTSConf.cur.time.real_time;
  }

  public static long end()
  {
    return RTSConf.cur.time._end(); } 
  protected long _end() { return this.bPause ? this.time : this.endTime;
  }

  public static long endReal()
  {
    return RTSConf.cur.time._endReal(); } 
  protected long _endReal() { return this.endRealTime; }

  public static int tickConstLen() {
    return RTSConf.cur.time.tickConstLen;
  }
  public static float tickConstLenFms() {
    return RTSConf.cur.time.tickConstLenFms;
  }
  public static float tickConstLenFs() {
    return RTSConf.cur.time.tickConstLenFs;
  }

  public void loopMessages()
  {
    if (this.bChangeSpeed) {
      _resetSpeed(this.newChangedSpeed);
      this.bChangeSpeed = false;
    }
    this.endRealTime = real();
    if (this.endRealTime == this.real_time)
      return;
    this.bInLoop = true;
    this.beginRealTime = this.real_time;
    this.beginTime = this.time;
    if (this.endRealTime - this.base_real_time > 16777215L) {
      this.base_time = this.beginTime;
      this.base_real_time = this.beginRealTime;
    }
    this.endTime = ()((this.endRealTime - this.base_real_time) * this.speed + 0.5D + this.base_time);

    int i = 0;
    if (this.bEnableChangeTickLen) {
      if ((this.endTime - this.beginTime) / this.tickLen0 > this.maxTimerTicksInRealTick) {
        this._newTickLen = (int)((this.endTime - this.beginTime) / this.maxTimerTicksInRealTick + 1L);

        if (this._newTickLen > 300) {
          this._newTickLen = 300;
          i = 1;
        }
        if ((!this.bPause) && (this._typedTickCounter + 20 < this.tickCounter)) {
          if (bShowDiag)
            System.err.println("Time overflow (" + this.tickCounter + "): tickLen " + this._newTickLen);
          this._typedTickCounter = this.tickCounter;
        }
      } else {
        this._newTickLen = ((this.tickLen0 + 2 * this.tickLen) / 3);
      }
    } else {
      this._newTickLen = this.tickLen0;
      i = 1;
    }

    if ((this.bEnableChangeSpeed) && (i != 0)) {
      if ((this.endTime - this.beginTime) / this.tickLen > this.maxTimerTicksInRealTick) {
        long l1 = this.endRealTime - this.beginRealTime;
        this.speedCur = (this.tickLen * this.maxTimerTicksInRealTick / (l1 * this.speed));
        if (this.speedCur >= 1.0D) { this.speedCur = 1.0D;
        } else {
          if (this.speed * this.speedCur < 0.05D)
            this.speedCur = (0.05D / this.speed);
          this.endTime = ()(l1 * this.speed * this.speedCur + 0.5D + this.beginTime);
          this.base_time = this.beginTime;
          this.base_real_time = this.beginRealTime;
          if ((!this.bPause) && 
            (bShowDiag))
            System.err.println("Time overflow (" + this.tickCounter + "): speed " + (float)this.speedCur);
        }
      }
      else {
        this.speedCur = 1.0D;
      }
    }
    else this.speedCur = 1.0D;

    RTSConf.cur.queueRealTimeNextTick.moveFromNextTick(RTSConf.cur.queueRealTime, true);
    while (true) {
      if (this.bPause)
      {
        localMessage1 = RTSConf.cur.queueRealTime.get(this.endRealTime);
        if (localMessage1 == null) {
          this.real_time = this.endRealTime;
          setCurrent(this.time, this.real_time);
          break;
        }
        if (localMessage1._time > this.real_time) {
          this.real_time = localMessage1._time;
          setCurrent(this.time, this.real_time);
        }
        localMessage1.trySend();

        if (!this.bPause) {
          this.endTime = ()((this.endRealTime - this.real_time) * this.speed * this.speedCur + 0.5D + this.beginTime);
          this.base_time = this.beginTime;
          this.base_real_time = this.real_time;
        }

        continue;
      }Message localMessage1 = null;
      synchronized (RTSConf.cur.queueRealTime) { synchronized (RTSConf.cur.queue)
        {
          localMessage1 = RTSConf.cur.queue.peek(this.endTime);
          Message localMessage2 = RTSConf.cur.queueRealTime.peek(this.endRealTime);

          if ((localMessage1 == null) && (localMessage2 == null)) {
            if (!_setCurrent(this.endTime, this.endRealTime))
              break;
          }
          else {
            long l2 = 0L;
            int j;
            if (localMessage2 != null) {
              l2 = _fromReal(localMessage2._time);
              if (l2 >= this.endTime)
                l2 = this.endTime - 1L;
              if (localMessage1 != null) {
                if ((localMessage1._time < l2) || ((localMessage1._time == l2) && (localMessage1._tickPos < localMessage2._tickPos)))
                {
                  j = 0;
                }
                else j = 1;
              }
              else
                j = 1;
            }
            else {
              j = 0;
            }

            if (j != 0) {
              if (l2 > this.time) {
                if (!_setCurrent(l2, localMessage2._time))
                  localMessage1 = RTSConf.cur.queueRealTime.get(this.endRealTime);
                else
                  localMessage1 = null;
              } else {
                if (localMessage2._time > this.real_time) {
                  this.real_time = localMessage2._time;
                  setCurrent(this.time, this.real_time);
                }
                localMessage1 = RTSConf.cur.queueRealTime.get(this.endRealTime);
              }
            } else {
              long l3 = _toReal(localMessage1._time);
              if (l3 >= this.endRealTime)
                l3 = this.endRealTime - 1L;
              if (localMessage1._time > this.time) {
                if (!_setCurrent(localMessage1._time, l3))
                  localMessage1 = RTSConf.cur.queue.get(this.endTime);
                else
                  localMessage1 = null;
              } else {
                if (l3 > this.real_time) {
                  this.real_time = localMessage2._time;
                  setCurrent(this.time, this.real_time);
                }
                localMessage1 = RTSConf.cur.queue.get(this.endTime);
              }
            }
          }
        } }
      if (localMessage1 != null) {
        localMessage1.trySend();
      }
    }
    this.bInLoop = false;
  }

  private boolean _setCurrent(long paramLong1, long paramLong2)
  {
    int i = 0;
    this.time = paramLong1;
    if (this.time >= this.tickNext) {
      this.time = (this.tick = this.tickNext);
      if (this.tickLen != this._newTickLen) {
        this.tickLen = this._newTickLen;
        this.tickNext += this.tickLen;
        this.tickLenFms = this.tickLen;
        this.tickLenFs = (this.tickLenFms / 1000.0F);
      } else {
        this.tickNext += this.tickLen;
      }
      RTSConf.cur.queueNextTick.moveFromNextTick(RTSConf.cur.queue, false);
      this.tickCounter += 1;
      paramLong2 = ()((this.time - this.base_time) / (this.speed * this.speedCur) + 0.5D + this.base_real_time);
      if (paramLong2 >= this.endRealTime)
        paramLong2 = this.endRealTime - 1L;
      i = 1;
    }
    if (paramLong2 > this.real_time)
      this.real_time = paramLong2;
    setCurrent(this.time, this.real_time);
    this.tickOffset = _tickOffset(this.time);
    return i;
  }

  private void _resetSpeed(double paramDouble)
  {
    this.speed = paramDouble;
    this.base_time = this.time;
    this.base_real_time = this.real_time;
  }

  protected void resetGameClear() {
    this.tickCounter = 0;
    this.tick = 0L;
    this.tickLen = this.tickLen0;
    this.tickNext = this.tickLen;
    this.tickLenFms = this.tickLen;
    this.tickLenFs = (this.tickLenFms / 1000.0F);
    this.time = 0L;
    this.tickOffset = 0.0F;
    this.base_time = 0L;
    this.real_time = (this.base_real_time = real());
    setCurrent(this.time, this.real_time);
    this.bPause = true;
  }

  protected void resetGameCreate()
  {
  }

  public final void setCurrent(long paramLong)
  {
    if (!this.bPause) return;
    this.base_time = (this.time = paramLong);
    this.tickCounter = (int)(this.time / this.tickLen0);
    this.tick = (this.tickCounter * this.tickLen0);
    this.tickNext = (this.tick + this.tickLen0);
    this.tickOffset = _tickOffset(this.time);
    setCurrent(this.time, this.real_time);
  }

  public static final void setPause(boolean paramBoolean)
  {
    RTSConf.cur.time._setPause(paramBoolean);
  }

  protected final void _setPause(boolean paramBoolean) {
    if (this.bPause == paramBoolean) return;
    this.bPause = paramBoolean;
    if ((!this.bInLoop) && (!this.bPause)) {
      this.base_time = this.time;
      this.base_real_time = this.real_time;
    }
  }

  public static final void setRealOnly(boolean paramBoolean)
  {
    RTSConf.cur.time._setRealOnly(paramBoolean);
  }

  protected final void _setRealOnly(boolean paramBoolean) {
    if (this.bRealOnly == paramBoolean) return;
    this.bRealOnly = paramBoolean;
    if ((this.bRealOnly) && (!this.bPause))
      _setPause(true);
  }

  public static void setSpeed(float paramFloat)
  {
    RTSConf.cur.time._setSpeed(paramFloat);
  }

  protected void _setSpeed(double paramDouble) {
    if (this.bInLoop) {
      if (this.bPause) {
        this.speed = paramDouble;
      } else {
        this.bChangeSpeed = true;
        this.newChangedSpeed = paramDouble;
      }
    } else {
      this.bChangeSpeed = false;
      _resetSpeed(paramDouble);
    }
  }

  public static float speed()
  {
    return (float)RTSConf.cur.time.speed;
  }
  public static float nextSpeed() {
    return RTSConf.cur.time._nextSpeed();
  }

  protected float _nextSpeed() {
    if (this.bChangeSpeed) return (float)this.newChangedSpeed;
    return (float)this.speed;
  }

  public static long fromReal(long paramLong)
  {
    return RTSConf.cur.time._fromReal(paramLong);
  }

  protected long _fromReal(long paramLong)
  {
    if (this.bPause)
      return this.time;
    long l = paramLong - this.base_real_time;
    if ((this.speed == 1.0D) && (this.speedCur == 1.0D)) return l + this.base_time;
    return ()(l * this.speed * this.speedCur + 0.5D + this.base_time);
  }

  public static long toReal(long paramLong)
  {
    return RTSConf.cur.time._toReal(paramLong);
  }

  protected long _toReal(long paramLong)
  {
    if (this.bPause)
      return this.real_time;
    long l = paramLong - this.base_time;
    if ((this.speed == 1.0D) && (this.speedCur == 1.0D)) return l + this.base_real_time;
    return ()(l / (this.speed * this.speedCur) + 0.5D + this.base_real_time);
  }

  public static long fromRaw(int paramInt)
  {
    return RTSConf.cur.time._fromReal(realFromRaw(paramInt));
  }

  public static long realFromRawClamp(int paramInt)
  {
    long l = realFromRaw(paramInt);
    if (l < RTSConf.cur.time.real_time) l = RTSConf.cur.time.real_time;
    if (l > RTSConf.cur.time.endRealTime) l = RTSConf.cur.time.endRealTime;
    return l;
  }

  public static native long realFromRaw(int paramInt);

  public static native void setSpeedReal(float paramFloat);

  public static native float speedReal();

  public static native long real();

  public static native int raw();

  private static native void setCurrent(long paramLong1, long paramLong2);

  public static boolean isEnableChangePause()
  {
    Time localTime = RTSConf.cur.time;
    return (localTime.bEnableChangePause0) && (localTime.bEnableChangePause1);
  }
  public boolean isEnableChangePause0() {
    return this.bEnableChangePause0;
  }
  public boolean isEnableChangePause1() {
    return this.bEnableChangePause1;
  }
  public static boolean isEnableChangeTickLen() {
    return RTSConf.cur.time.bEnableChangeTickLen;
  }
  public static boolean isEnableChangeSpeed() {
    return RTSConf.cur.time.bEnableChangeSpeed;
  }
  public void setEnableChangePause0(boolean paramBoolean) {
    this.bEnableChangePause0 = paramBoolean;
  }
  public void setEnableChangePause1(boolean paramBoolean) {
    this.bEnableChangePause1 = paramBoolean;
  }
  public void setEnableChangeTickLen(boolean paramBoolean) {
    this.bEnableChangeTickLen = paramBoolean;
  }
  public void setEnableChangeSpeed(boolean paramBoolean) {
    this.bEnableChangeSpeed = paramBoolean;
  }

  protected Time(int paramInt1, int paramInt2) {
    if (paramInt1 > 300)
      paramInt1 = 300;
    this.tickLen0 = paramInt1;
    this.tickLen = paramInt1;
    this.tickConstLen = paramInt1;
    this.tickNext = (this.tick + paramInt1);
    this.tickLenFms = paramInt1;
    this.tickConstLenFms = paramInt1;
    this.tickLenFs = (this.tickLenFms / 1000.0F);
    this.tickConstLenFs = (this.tickLenFms / 1000.0F);
    if (paramInt2 <= 0) paramInt2 = 1;
    this.maxTimerTicksInRealTick = paramInt2;
    this.real_time = real();
    this.base_real_time = this.real_time;
    this.beginRealTime = this.real_time;
    this.endRealTime = this.real_time;
    setCurrent(this.time, this.real_time);
  }

  protected void setMaxTimerTicksInRealTick(int paramInt) {
    if (paramInt <= 0) paramInt = 1;
    this.maxTimerTicksInRealTick = paramInt;
  }

  static
  {
    RTS.loadNative();
  }
}