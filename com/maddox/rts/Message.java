// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Message.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            MessageException, MessageComponent, MessageProxy, MessageListener, 
//            States, Destroy, Time, RTSConf, 
//            MessageQueue, RTSProfile

public class Message
{

    public final boolean busy()
    {
        return (_flags & 1) != 0;
    }

    public final boolean incache()
    {
        return (_flags & 2) != 0;
    }

    public final boolean isRealTime()
    {
        return (_flags & 0x40) != 0;
    }

    public final java.lang.Object listener()
    {
        return _listener;
    }

    public final long time()
    {
        return _time;
    }

    public final int tickPos()
    {
        return _tickPos;
    }

    public final java.lang.Object sender()
    {
        return _sender;
    }

    public final int flags()
    {
        return _flags;
    }

    public final void setFlags(int i)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _flags = i & -8 | _flags & 7;
            return;
        }
    }

    public final void setNotCleanAfterSend()
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _flags &= -5;
            return;
        }
    }

    public final void setListener(java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _listener = obj;
            return;
        }
    }

    public final void setTime(long l)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _time = l;
            return;
        }
    }

    public final void setTickPos(int i)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _tickPos = i;
            return;
        }
    }

    public final void setSender(java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _sender = obj;
            return;
        }
    }

    public Message()
    {
        _flags = 4;
        _listener = null;
        _time = com.maddox.rts.Time.current();
        _tickPos = 0;
        _sender = null;
    }

    public void clean()
    {
        _listener = null;
        _sender = null;
        _tickPos = 0;
        _flags &= 7;
    }

    public void remove()
    {
        if((_flags & 1) == 0)
            return;
        if((_flags & 0x40) != 0)
        {
            if(!com.maddox.rts.RTSConf.cur.queueRealTime.remove(this) && (_flags & 8) != 0)
                com.maddox.rts.RTSConf.cur.queueRealTimeNextTick.remove(this);
        } else
        if(!com.maddox.rts.RTSConf.cur.queue.remove(this) && (_flags & 8) != 0)
            com.maddox.rts.RTSConf.cur.queueNextTick.remove(this);
    }

    public static com.maddox.rts.Message current()
    {
        return com.maddox.rts.RTSConf.cur.message;
    }

    public static long currentTime(boolean flag)
    {
        if(com.maddox.rts.RTSConf.cur.message == null)
            if(flag)
                return com.maddox.rts.Time.currentReal();
            else
                return com.maddox.rts.Time.current();
        if((com.maddox.rts.RTSConf.cur.message._flags & 0x40) != 0)
            if(flag)
                return com.maddox.rts.RTSConf.cur.message._time;
            else
                return com.maddox.rts.Time.fromReal(com.maddox.rts.RTSConf.cur.message._time);
        if(flag)
            return com.maddox.rts.Time.toReal(com.maddox.rts.RTSConf.cur.message._time);
        else
            return com.maddox.rts.RTSConf.cur.message._time;
    }

    public static long currentRealTime()
    {
        if(com.maddox.rts.RTSConf.cur.message == null)
            return com.maddox.rts.Time.currentReal();
        if((com.maddox.rts.RTSConf.cur.message._flags & 0x40) != 0)
            return com.maddox.rts.RTSConf.cur.message._time;
        else
            return com.maddox.rts.Time.toReal(com.maddox.rts.RTSConf.cur.message._time);
    }

    public static long currentGameTime()
    {
        if(com.maddox.rts.RTSConf.cur.message == null)
            return com.maddox.rts.Time.current();
        if((com.maddox.rts.RTSConf.cur.message._flags & 0x40) != 0)
            return com.maddox.rts.Time.fromReal(com.maddox.rts.RTSConf.cur.message._time);
        else
            return com.maddox.rts.RTSConf.cur.message._time;
    }

    private com.maddox.rts.MessageQueue selectQueue()
    {
        if((_flags & 0x48) == 0)
            return com.maddox.rts.RTSConf.cur.queue;
        if((_flags & 0x40) == 0)
            return com.maddox.rts.RTSConf.cur.queueNextTick;
        if((_flags & 8) != 0)
            return com.maddox.rts.RTSConf.cur.queueRealTimeNextTick;
        else
            return com.maddox.rts.RTSConf.cur.queueRealTime;
    }

    private com.maddox.rts.MessageQueue selectQueue(int i)
    {
        if((i & 0x48) == 0)
            return com.maddox.rts.RTSConf.cur.queue;
        if((i & 0x40) == 0)
            return com.maddox.rts.RTSConf.cur.queueNextTick;
        if((i & 8) != 0)
            return com.maddox.rts.RTSConf.cur.queueRealTimeNextTick;
        else
            return com.maddox.rts.RTSConf.cur.queueRealTime;
    }

    public static long s2ms(int i, double d)
    {
        long l = (i & 0x40) == 0 ? com.maddox.rts.Time.current() : com.maddox.rts.Time.currentReal();
        return l + (long)(d * 1000D + 0.5D);
    }

    public void post(java.lang.Object obj, long l, int i, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, obj, l, i, obj1);
    }

    public void post(java.lang.Object obj, double d, int i, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, obj, com.maddox.rts.Message.s2ms(_flags, d), i, obj1);
    }

    public void post(int i, java.lang.Object obj, long l, int j, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, obj, l, j, obj1);
    }

    public void post(int i, java.lang.Object obj, double d, int j, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, obj, com.maddox.rts.Message.s2ms(i, d), j, obj1);
    }

    public void post(java.lang.Object obj, long l, int i)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, obj, l, i);
    }

    public void post(java.lang.Object obj, double d, int i)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, obj, com.maddox.rts.Message.s2ms(_flags, d), i);
    }

    public void post(int i, java.lang.Object obj, long l, int j)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, obj, l, j);
    }

    public void post(int i, java.lang.Object obj, double d, int j)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, obj, com.maddox.rts.Message.s2ms(i, d), j);
    }

    public void post(java.lang.Object obj, long l, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, obj, l, obj1);
    }

    public void post(java.lang.Object obj, double d, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, obj, com.maddox.rts.Message.s2ms(_flags, d), obj1);
    }

    public void post(int i, java.lang.Object obj, long l, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, obj, l, obj1);
    }

    public void post(int i, java.lang.Object obj, double d, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, obj, com.maddox.rts.Message.s2ms(i, d), obj1);
    }

    public void post(java.lang.Object obj, int i, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, obj, i, obj1);
    }

    public void post(int i, java.lang.Object obj, int j, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, obj, j, obj1);
    }

    public void post(long l, int i, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, l, i, obj);
    }

    public void post(double d, int i, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, com.maddox.rts.Message.s2ms(_flags, d), i, obj);
    }

    public void post(int i, long l, int j, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, l, j, obj);
    }

    public void post(int i, double d, int j, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, com.maddox.rts.Message.s2ms(i, d), j, obj);
    }

    public void post(long l, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, l, obj);
    }

    public void post(double d, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, com.maddox.rts.Message.s2ms(_flags, d), obj);
    }

    public void post(int i, long l, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, l, obj);
    }

    public void post(int i, double d, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, com.maddox.rts.Message.s2ms(i, d), obj);
    }

    public void post(int i, int j, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, j, obj);
    }

    public void post(long l, int i)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, l, i);
    }

    public void post(double d, int i)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, com.maddox.rts.Message.s2ms(_flags, d), i);
    }

    public void post(int i, long l, int j)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, l, j);
    }

    public void post(int i, double d, int j)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, com.maddox.rts.Message.s2ms(i, d), j);
    }

    public void post(long l)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, l);
    }

    public void post(double d)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, com.maddox.rts.Message.s2ms(_flags, d));
    }

    public void post(int i, long l)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, l);
    }

    public void post(int i, double d)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, com.maddox.rts.Message.s2ms(i, d));
    }

    public void post(int i, int j)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, j);
    }

    public void post(int i)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this);
    }

    public void post(java.lang.Object obj, long l)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, obj, l);
    }

    public void post(java.lang.Object obj, double d)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, obj, com.maddox.rts.Message.s2ms(_flags, d));
    }

    public void post(int i, java.lang.Object obj, long l)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, obj, l);
    }

    public void post(int i, java.lang.Object obj, double d)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, obj, com.maddox.rts.Message.s2ms(i, d));
    }

    public void post(java.lang.Object obj, int i)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, obj, i);
    }

    public void post(int i, java.lang.Object obj, int j)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, obj, j);
    }

    public void post(java.lang.Object obj, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, obj, obj1);
    }

    public void post(int i, java.lang.Object obj, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, obj, obj1);
    }

    public void post(java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this, obj);
    }

    public void post(int i, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        selectQueue(i).put(i, this, obj);
    }

    public void post()
        throws com.maddox.rts.MessageException
    {
        selectQueue().put(this);
    }

    public void send(java.lang.Object obj, long l, int i, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _listener = obj;
            _time = l;
            _tickPos = i;
            _sender = obj1;
            trySend();
            return;
        }
    }

    public void send(java.lang.Object obj, long l, int i)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _listener = obj;
            _time = l;
            _tickPos = i;
            trySend();
            return;
        }
    }

    public void send(java.lang.Object obj, long l, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _listener = obj;
            _time = l;
            _sender = obj1;
            trySend();
            return;
        }
    }

    public void send(java.lang.Object obj, int i, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _listener = obj;
            _tickPos = i;
            _sender = obj1;
            trySend();
            return;
        }
    }

    public void send(long l, int i, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _time = l;
            _tickPos = i;
            _sender = obj;
            trySend();
            return;
        }
    }

    public void send(int i, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _tickPos = i;
            _sender = obj;
            trySend();
            return;
        }
    }

    public void send(java.lang.Object obj, long l)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _listener = obj;
            _time = l;
            trySend();
            return;
        }
    }

    public void send(java.lang.Object obj, int i)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _listener = obj;
            _tickPos = i;
            trySend();
            return;
        }
    }

    public void send(java.lang.Object obj, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _listener = obj;
            _sender = obj1;
            trySend();
            return;
        }
    }

    public void send(java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            _listener = obj;
            trySend();
            return;
        }
    }

    public void send()
        throws com.maddox.rts.MessageException
    {
        if((_flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            trySend();
            return;
        }
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        return true;
    }

    protected final synchronized void trySend()
    {
        com.maddox.rts.Message message = com.maddox.rts.RTSConf.cur.message;
        com.maddox.rts.RTSConf.cur.message = this;
        com.maddox.rts.RTSConf.cur.profile.countMessages++;
        try
        {
            sendTo(_listener);
        }
        catch(java.lang.Exception exception)
        {
            if((_flags & 6) != 0)
                clean();
            _flags &= -2;
            if(!(exception instanceof java.lang.NullPointerException))
                exception.printStackTrace();
        }
        com.maddox.rts.RTSConf.cur.message = message;
    }

    private final void sendTo(java.lang.Object obj)
    {
        if(obj instanceof java.lang.Object[])
        {
            sendToArray(obj);
            if((_flags & 6) != 0)
                clean();
            _flags &= -2;
        } else
        {
            _flags &= -2;
            sendToObject(obj);
            if((_flags & 1) == 0 && (_flags & 6) != 0)
                clean();
        }
    }

    private final void sendToArray(java.lang.Object obj)
    {
        int j = ((java.lang.Object[])obj).length;
        for(int i = 0; i < j; i++)
        {
            java.lang.Object obj1 = ((java.lang.Object[])obj)[i];
            if(obj1 != null)
                sendToObject(obj1);
        }

    }

    private final void sendToObject(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MessageComponent)
        {
            java.lang.Object obj1 = ((com.maddox.rts.MessageComponent)obj).getSwitchListener(this);
            if(obj1 != null)
            {
                if(obj1 == obj)
                {
                    _send(obj1);
                    return;
                }
                if(obj1 instanceof com.maddox.rts.MessageProxy)
                {
                    obj1 = ((com.maddox.rts.MessageProxy)obj1).getListener(this);
                    if(obj1 != null && !_send(obj1) && (obj1 instanceof com.maddox.rts.MessageListener))
                    {
                        obj1 = ((com.maddox.rts.MessageListener)obj1).getParentListener(this);
                        if(obj1 != null)
                            _send(obj1);
                    }
                } else
                {
                    sendToObject(obj1);
                }
            }
        } else
        if(obj instanceof com.maddox.rts.States)
        {
            java.lang.Object obj2 = ((com.maddox.rts.MessageProxy)obj).getListener(this);
            if(obj2 != null && !_send(obj2) && (obj2 instanceof com.maddox.rts.MessageListener))
            {
                obj2 = ((com.maddox.rts.MessageListener)obj2).getParentListener(this);
                if(obj2 != null)
                    _send(obj2);
            }
        } else
        if(obj instanceof com.maddox.rts.MessageProxy)
        {
            java.lang.Object obj3 = ((com.maddox.rts.MessageProxy)obj).getListener(this);
            if(obj3 != null)
                sendToObject(obj3);
        } else
        if(!_send(obj) && (obj instanceof com.maddox.rts.MessageListener))
        {
            java.lang.Object obj4 = ((com.maddox.rts.MessageListener)obj).getParentListener(this);
            if(obj4 != null)
                _send(obj4);
        }
    }

    private final boolean _send(java.lang.Object obj)
    {
        if((obj instanceof com.maddox.rts.Destroy) && ((com.maddox.rts.Destroy)obj).isDestroyed())
            return true;
        return invokeListener(obj);
        java.lang.Exception exception;
        exception;
        exception.printStackTrace();
        return true;
    }

    public static final java.lang.String ERR_BUSY = "message is busy";
    public static final int BUSY = 1;
    public static final int IN_CACHE = 2;
    public static final int CLEAR_AFTER_SEND = 4;
    public static final int NEXT_TICK = 8;
    public static final int CLEAR_TIME = 16;
    public static final int END_TICK_TIME = 32;
    public static final int REAL_TIME = 64;
    public static final int SYS_FLAGS = 7;
    protected int _flags;
    protected java.lang.Object _listener;
    protected long _time;
    protected int _tickPos;
    protected long _queueStamp;
    protected java.lang.Object _sender;
}
