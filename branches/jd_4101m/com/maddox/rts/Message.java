package com.maddox.rts;

public class Message
{
  public static final String ERR_BUSY = "message is busy";
  public static final int BUSY = 1;
  public static final int IN_CACHE = 2;
  public static final int CLEAR_AFTER_SEND = 4;
  public static final int NEXT_TICK = 8;
  public static final int CLEAR_TIME = 16;
  public static final int END_TICK_TIME = 32;
  public static final int REAL_TIME = 64;
  public static final int SYS_FLAGS = 7;
  protected int _flags;
  protected Object _listener;
  protected long _time;
  protected int _tickPos;
  protected long _queueStamp;
  protected Object _sender;

  public final boolean busy()
  {
    return (this._flags & 0x1) != 0;
  }

  public final boolean incache()
  {
    return (this._flags & 0x2) != 0;
  }

  public final boolean isRealTime()
  {
    return (this._flags & 0x40) != 0;
  }

  public final Object listener()
  {
    return this._listener;
  }

  public final long time()
  {
    return this._time;
  }

  public final int tickPos()
  {
    return this._tickPos;
  }

  public final Object sender()
  {
    return this._sender;
  }

  public final int flags()
  {
    return this._flags;
  }

  public final void setFlags(int paramInt)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._flags = (paramInt & 0xFFFFFFF8 | this._flags & 0x7);
  }

  public final void setNotCleanAfterSend()
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._flags &= -5;
  }

  public final void setListener(Object paramObject)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._listener = paramObject;
  }

  public final void setTime(long paramLong)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._time = paramLong;
  }

  public final void setTickPos(int paramInt)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._tickPos = paramInt;
  }

  public final void setSender(Object paramObject)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._sender = paramObject;
  }

  public Message()
  {
    this._flags = 4; this._listener = null; this._time = Time.current(); this._tickPos = 0; this._sender = null;
  }

  public void clean()
  {
    this._listener = null; this._sender = null; this._tickPos = 0;
    this._flags &= 7;
  }

  public void remove()
  {
    if ((this._flags & 0x1) == 0) return;
    if ((this._flags & 0x40) != 0) {
      if ((!RTSConf.cur.queueRealTime.remove(this)) && ((this._flags & 0x8) != 0))
        RTSConf.cur.queueRealTimeNextTick.remove(this);
    }
    else if ((!RTSConf.cur.queue.remove(this)) && ((this._flags & 0x8) != 0))
      RTSConf.cur.queueNextTick.remove(this);
  }

  public static Message current()
  {
    return RTSConf.cur.message;
  }

  public static long currentTime(boolean paramBoolean)
  {
    if (RTSConf.cur.message == null) {
      if (paramBoolean) return Time.currentReal();
      return Time.current();
    }
    if ((RTSConf.cur.message._flags & 0x40) != 0) {
      if (paramBoolean) return RTSConf.cur.message._time;
      return Time.fromReal(RTSConf.cur.message._time);
    }
    if (paramBoolean) return Time.toReal(RTSConf.cur.message._time);
    return RTSConf.cur.message._time;
  }

  public static long currentRealTime()
  {
    if (RTSConf.cur.message == null) {
      return Time.currentReal();
    }
    if ((RTSConf.cur.message._flags & 0x40) != 0) {
      return RTSConf.cur.message._time;
    }
    return Time.toReal(RTSConf.cur.message._time);
  }

  public static long currentGameTime()
  {
    if (RTSConf.cur.message == null) {
      return Time.current();
    }
    if ((RTSConf.cur.message._flags & 0x40) != 0) {
      return Time.fromReal(RTSConf.cur.message._time);
    }
    return RTSConf.cur.message._time;
  }

  private MessageQueue selectQueue()
  {
    if ((this._flags & 0x48) == 0) return RTSConf.cur.queue;
    if ((this._flags & 0x40) == 0) {
      return RTSConf.cur.queueNextTick;
    }
    if ((this._flags & 0x8) != 0) return RTSConf.cur.queueRealTimeNextTick;
    return RTSConf.cur.queueRealTime;
  }

  private MessageQueue selectQueue(int paramInt)
  {
    if ((paramInt & 0x48) == 0) return RTSConf.cur.queue;
    if ((paramInt & 0x40) == 0) {
      return RTSConf.cur.queueNextTick;
    }
    if ((paramInt & 0x8) != 0) return RTSConf.cur.queueRealTimeNextTick;
    return RTSConf.cur.queueRealTime;
  }

  public static long s2ms(int paramInt, double paramDouble)
  {
    long l = (paramInt & 0x40) != 0 ? Time.currentReal() : Time.current();
    return l + ()(paramDouble * 1000.0D + 0.5D);
  }

  public void post(Object paramObject1, long paramLong, int paramInt, Object paramObject2)
    throws MessageException
  {
    selectQueue().put(this, paramObject1, paramLong, paramInt, paramObject2);
  }

  public void post(Object paramObject1, double paramDouble, int paramInt, Object paramObject2)
    throws MessageException
  {
    selectQueue().put(this, paramObject1, s2ms(this._flags, paramDouble), paramInt, paramObject2);
  }

  public void post(int paramInt1, Object paramObject1, long paramLong, int paramInt2, Object paramObject2)
    throws MessageException
  {
    selectQueue(paramInt1).put(paramInt1, this, paramObject1, paramLong, paramInt2, paramObject2);
  }

  public void post(int paramInt1, Object paramObject1, double paramDouble, int paramInt2, Object paramObject2)
    throws MessageException
  {
    selectQueue(paramInt1).put(paramInt1, this, paramObject1, s2ms(paramInt1, paramDouble), paramInt2, paramObject2);
  }

  public void post(Object paramObject, long paramLong, int paramInt)
    throws MessageException
  {
    selectQueue().put(this, paramObject, paramLong, paramInt);
  }

  public void post(Object paramObject, double paramDouble, int paramInt)
    throws MessageException
  {
    selectQueue().put(this, paramObject, s2ms(this._flags, paramDouble), paramInt);
  }

  public void post(int paramInt1, Object paramObject, long paramLong, int paramInt2)
    throws MessageException
  {
    selectQueue(paramInt1).put(paramInt1, this, paramObject, paramLong, paramInt2);
  }

  public void post(int paramInt1, Object paramObject, double paramDouble, int paramInt2)
    throws MessageException
  {
    selectQueue(paramInt1).put(paramInt1, this, paramObject, s2ms(paramInt1, paramDouble), paramInt2);
  }

  public void post(Object paramObject1, long paramLong, Object paramObject2)
    throws MessageException
  {
    selectQueue().put(this, paramObject1, paramLong, paramObject2);
  }

  public void post(Object paramObject1, double paramDouble, Object paramObject2)
    throws MessageException
  {
    selectQueue().put(this, paramObject1, s2ms(this._flags, paramDouble), paramObject2);
  }

  public void post(int paramInt, Object paramObject1, long paramLong, Object paramObject2)
    throws MessageException
  {
    selectQueue(paramInt).put(paramInt, this, paramObject1, paramLong, paramObject2);
  }

  public void post(int paramInt, Object paramObject1, double paramDouble, Object paramObject2)
    throws MessageException
  {
    selectQueue(paramInt).put(paramInt, this, paramObject1, s2ms(paramInt, paramDouble), paramObject2);
  }

  public void post(Object paramObject1, int paramInt, Object paramObject2)
    throws MessageException
  {
    selectQueue().put(this, paramObject1, paramInt, paramObject2);
  }

  public void post(int paramInt1, Object paramObject1, int paramInt2, Object paramObject2)
    throws MessageException
  {
    selectQueue(paramInt1).put(paramInt1, this, paramObject1, paramInt2, paramObject2);
  }

  public void post(long paramLong, int paramInt, Object paramObject)
    throws MessageException
  {
    selectQueue().put(this, paramLong, paramInt, paramObject);
  }

  public void post(double paramDouble, int paramInt, Object paramObject)
    throws MessageException
  {
    selectQueue().put(this, s2ms(this._flags, paramDouble), paramInt, paramObject);
  }

  public void post(int paramInt1, long paramLong, int paramInt2, Object paramObject)
    throws MessageException
  {
    selectQueue(paramInt1).put(paramInt1, this, paramLong, paramInt2, paramObject);
  }

  public void post(int paramInt1, double paramDouble, int paramInt2, Object paramObject)
    throws MessageException
  {
    selectQueue(paramInt1).put(paramInt1, this, s2ms(paramInt1, paramDouble), paramInt2, paramObject);
  }

  public void post(long paramLong, Object paramObject)
    throws MessageException
  {
    selectQueue().put(this, paramLong, paramObject);
  }

  public void post(double paramDouble, Object paramObject)
    throws MessageException
  {
    selectQueue().put(this, s2ms(this._flags, paramDouble), paramObject);
  }

  public void post(int paramInt, long paramLong, Object paramObject)
    throws MessageException
  {
    selectQueue(paramInt).put(paramInt, this, paramLong, paramObject);
  }

  public void post(int paramInt, double paramDouble, Object paramObject)
    throws MessageException
  {
    selectQueue(paramInt).put(paramInt, this, s2ms(paramInt, paramDouble), paramObject);
  }

  public void post(int paramInt1, int paramInt2, Object paramObject)
    throws MessageException
  {
    selectQueue(paramInt1).put(paramInt1, this, paramInt2, paramObject);
  }

  public void post(long paramLong, int paramInt)
    throws MessageException
  {
    selectQueue().put(this, paramLong, paramInt);
  }

  public void post(double paramDouble, int paramInt)
    throws MessageException
  {
    selectQueue().put(this, s2ms(this._flags, paramDouble), paramInt);
  }

  public void post(int paramInt1, long paramLong, int paramInt2)
    throws MessageException
  {
    selectQueue(paramInt1).put(paramInt1, this, paramLong, paramInt2);
  }

  public void post(int paramInt1, double paramDouble, int paramInt2)
    throws MessageException
  {
    selectQueue(paramInt1).put(paramInt1, this, s2ms(paramInt1, paramDouble), paramInt2);
  }

  public void post(long paramLong)
    throws MessageException
  {
    selectQueue().put(this, paramLong);
  }

  public void post(double paramDouble)
    throws MessageException
  {
    selectQueue().put(this, s2ms(this._flags, paramDouble));
  }

  public void post(int paramInt, long paramLong)
    throws MessageException
  {
    selectQueue(paramInt).put(paramInt, this, paramLong);
  }

  public void post(int paramInt, double paramDouble)
    throws MessageException
  {
    selectQueue(paramInt).put(paramInt, this, s2ms(paramInt, paramDouble));
  }

  public void post(int paramInt1, int paramInt2)
    throws MessageException
  {
    selectQueue(paramInt1).put(paramInt1, this, paramInt2);
  }

  public void post(int paramInt)
    throws MessageException
  {
    selectQueue(paramInt).put(paramInt, this);
  }

  public void post(Object paramObject, long paramLong)
    throws MessageException
  {
    selectQueue().put(this, paramObject, paramLong);
  }

  public void post(Object paramObject, double paramDouble)
    throws MessageException
  {
    selectQueue().put(this, paramObject, s2ms(this._flags, paramDouble));
  }

  public void post(int paramInt, Object paramObject, long paramLong)
    throws MessageException
  {
    selectQueue(paramInt).put(paramInt, this, paramObject, paramLong);
  }

  public void post(int paramInt, Object paramObject, double paramDouble)
    throws MessageException
  {
    selectQueue(paramInt).put(paramInt, this, paramObject, s2ms(paramInt, paramDouble));
  }

  public void post(Object paramObject, int paramInt)
    throws MessageException
  {
    selectQueue().put(this, paramObject, paramInt);
  }

  public void post(int paramInt1, Object paramObject, int paramInt2)
    throws MessageException
  {
    selectQueue(paramInt1).put(paramInt1, this, paramObject, paramInt2);
  }

  public void post(Object paramObject1, Object paramObject2)
    throws MessageException
  {
    selectQueue().put(this, paramObject1, paramObject2);
  }

  public void post(int paramInt, Object paramObject1, Object paramObject2)
    throws MessageException
  {
    selectQueue(paramInt).put(paramInt, this, paramObject1, paramObject2);
  }

  public void post(Object paramObject)
    throws MessageException
  {
    selectQueue().put(this, paramObject);
  }

  public void post(int paramInt, Object paramObject)
    throws MessageException
  {
    selectQueue(paramInt).put(paramInt, this, paramObject);
  }

  public void post()
    throws MessageException
  {
    selectQueue().put(this);
  }

  public void send(Object paramObject1, long paramLong, int paramInt, Object paramObject2)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._listener = paramObject1; this._time = paramLong; this._tickPos = paramInt; this._sender = paramObject2;
    trySend();
  }

  public void send(Object paramObject, long paramLong, int paramInt)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._listener = paramObject; this._time = paramLong; this._tickPos = paramInt;
    trySend();
  }

  public void send(Object paramObject1, long paramLong, Object paramObject2)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._listener = paramObject1; this._time = paramLong; this._sender = paramObject2;
    trySend();
  }

  public void send(Object paramObject1, int paramInt, Object paramObject2)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._listener = paramObject1; this._tickPos = paramInt; this._sender = paramObject2;
    trySend();
  }

  public void send(long paramLong, int paramInt, Object paramObject)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._time = paramLong; this._tickPos = paramInt; this._sender = paramObject;
    trySend();
  }

  public void send(int paramInt, Object paramObject)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._tickPos = paramInt; this._sender = paramObject;
    trySend();
  }

  public void send(Object paramObject, long paramLong)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._listener = paramObject; this._time = paramLong;
    trySend();
  }

  public void send(Object paramObject, int paramInt)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._listener = paramObject; this._tickPos = paramInt;
    trySend();
  }

  public void send(Object paramObject1, Object paramObject2)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._listener = paramObject1; this._sender = paramObject2;
    trySend();
  }

  public void send(Object paramObject)
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    this._listener = paramObject;
    trySend();
  }

  public void send()
    throws MessageException
  {
    if ((this._flags & 0x1) != 0) throw new MessageException("message is busy");
    trySend();
  }

  public boolean invokeListener(Object paramObject)
  {
    return true;
  }

  protected final synchronized void trySend()
  {
    Message localMessage = RTSConf.cur.message;
    RTSConf.cur.message = this;
    RTSConf.cur.profile.countMessages += 1;
    try {
      sendTo(this._listener);
    } catch (Exception localException) {
      if ((this._flags & 0x6) != 0)
        clean();
      this._flags &= -2;
      if (!(localException instanceof NullPointerException))
        localException.printStackTrace();
    }
    RTSConf.cur.message = localMessage;
  }

  private final void sendTo(Object paramObject) {
    if ((paramObject instanceof Object[])) {
      sendToArray(paramObject);
      if ((this._flags & 0x6) != 0)
        clean();
      this._flags &= -2;
    } else {
      this._flags &= -2;
      sendToObject(paramObject);
      if (((this._flags & 0x1) == 0) && ((this._flags & 0x6) != 0))
      {
        clean();
      }
    }
  }

  private final void sendToArray(Object paramObject) {
    int j = ((Object[])(Object[])paramObject).length;
    for (int i = 0; i < j; i++) {
      Object localObject = ((Object[])(Object[])paramObject)[i];
      if (localObject != null)
        sendToObject(localObject);
    }
  }

  private final void sendToObject(Object paramObject)
  {
    Object localObject;
    if ((paramObject instanceof MessageComponent)) {
      localObject = ((MessageComponent)paramObject).getSwitchListener(this);
      if (localObject != null) {
        if (localObject == paramObject) {
          _send(localObject);
          return;
        }
        if ((localObject instanceof MessageProxy)) {
          localObject = ((MessageProxy)localObject).getListener(this);
          if ((localObject != null) && 
            (!_send(localObject)) && 
            ((localObject instanceof MessageListener))) {
            localObject = ((MessageListener)localObject).getParentListener(this);
            if (localObject != null)
              _send(localObject);
          }
        }
        else
        {
          sendToObject(localObject);
        }
      }
    } else if ((paramObject instanceof States)) {
      localObject = ((MessageProxy)paramObject).getListener(this);
      if ((localObject != null) && 
        (!_send(localObject)) && 
        ((localObject instanceof MessageListener))) {
        localObject = ((MessageListener)localObject).getParentListener(this);
        if (localObject != null) {
          _send(localObject);
        }
      }
    }
    else if ((paramObject instanceof MessageProxy)) {
      localObject = ((MessageProxy)paramObject).getListener(this);
      if (localObject != null)
        sendToObject(localObject);
    } else if ((!_send(paramObject)) && 
      ((paramObject instanceof MessageListener))) {
      localObject = ((MessageListener)paramObject).getParentListener(this);
      if (localObject != null)
        _send(localObject);
    }
  }

  private final boolean _send(Object paramObject)
  {
    if (((paramObject instanceof Destroy)) && (((Destroy)paramObject).isDestroyed())) {
      return true;
    }

    try
    {
      return invokeListener(paramObject);
    } catch (Exception localException) {
      localException.printStackTrace();
    }
    return true;
  }
}