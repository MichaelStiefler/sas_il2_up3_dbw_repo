// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MessageQueue.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            MessageException, Message, Time

public final class MessageQueue
{

    public final synchronized void put(com.maddox.rts.Message message, java.lang.Object obj, long l, int i, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._time = l;
            message._tickPos = i;
            message._sender = obj1;
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message, java.lang.Object obj, long l, int j, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._time = l;
            message._tickPos = j;
            message._sender = obj1;
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(com.maddox.rts.Message message, java.lang.Object obj, long l, int i)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._time = l;
            message._tickPos = i;
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message, java.lang.Object obj, long l, int j)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._time = l;
            message._tickPos = j;
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(com.maddox.rts.Message message, java.lang.Object obj, long l, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._time = l;
            message._sender = obj1;
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message, java.lang.Object obj, long l, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._time = l;
            message._sender = obj1;
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(com.maddox.rts.Message message, java.lang.Object obj, int i, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._tickPos = i;
            message._sender = obj1;
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message, java.lang.Object obj, int j, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._tickPos = j;
            message._sender = obj1;
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(com.maddox.rts.Message message, long l, int i, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._time = l;
            message._tickPos = i;
            message._sender = obj;
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message, long l, int j, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._time = l;
            message._tickPos = j;
            message._sender = obj;
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(com.maddox.rts.Message message, long l, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._time = l;
            message._sender = obj;
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message, long l, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._time = l;
            message._sender = obj;
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(com.maddox.rts.Message message, int i, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._tickPos = i;
            message._sender = obj;
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message, int j, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._tickPos = j;
            message._sender = obj;
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(com.maddox.rts.Message message, long l, int i)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._time = l;
            message._tickPos = i;
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message, long l, int j)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._time = l;
            message._tickPos = j;
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(com.maddox.rts.Message message, long l)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._time = l;
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message, long l)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._time = l;
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(com.maddox.rts.Message message, int i)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._tickPos = i;
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message, int j)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._tickPos = j;
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(com.maddox.rts.Message message, java.lang.Object obj, long l)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._time = l;
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message, java.lang.Object obj, long l)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._time = l;
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(com.maddox.rts.Message message, java.lang.Object obj, int i)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._tickPos = i;
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message, java.lang.Object obj, int j)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._tickPos = j;
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(com.maddox.rts.Message message, java.lang.Object obj, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._sender = obj1;
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message, java.lang.Object obj, java.lang.Object obj1)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._sender = obj1;
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(com.maddox.rts.Message message, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message, java.lang.Object obj)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._listener = obj;
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(com.maddox.rts.Message message)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._flags |= 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized void put(int i, com.maddox.rts.Message message)
        throws com.maddox.rts.MessageException
    {
        if((message._flags & 1) != 0)
        {
            throw new MessageException("message is busy");
        } else
        {
            message._flags = i & -8 | message._flags & 6 | 1;
            push_heap(message);
            return;
        }
    }

    public final synchronized com.maddox.rts.Message get(long l)
    {
        if(size == 0)
            return null;
        com.maddox.rts.Message message = array[1];
        if(message._time >= l)
        {
            return null;
        } else
        {
            pop_heap();
            return message;
        }
    }

    public final synchronized com.maddox.rts.Message peek(long l)
    {
        if(size == 0)
            return null;
        com.maddox.rts.Message message = array[1];
        if(message._time >= l)
            return null;
        else
            return message;
    }

    public final synchronized com.maddox.rts.Message peekByIndex(int i)
    {
        if(size == 0)
            return null;
        if(++i > size)
            return null;
        else
            return array[i];
    }

    public final synchronized void move(com.maddox.rts.MessageQueue messagequeue)
    {
        synchronized(messagequeue)
        {
            for(int i = 1; i <= size; i++)
            {
                com.maddox.rts.Message message = array[i];
                messagequeue.push_heap(message);
                array[i] = null;
            }

            size = 0;
        }
    }

    public final synchronized void move(com.maddox.rts.MessageQueue messagequeue, long l)
    {
        synchronized(messagequeue)
        {
            for(int i = 1; i <= size; i++)
            {
                com.maddox.rts.Message message = array[i];
                message._time += l;
                messagequeue.push_heap(message);
                array[i] = null;
            }

            size = 0;
        }
    }

    public final synchronized void moveFromNextTick(com.maddox.rts.MessageQueue messagequeue, boolean flag)
    {
        synchronized(messagequeue)
        {
            if(flag)
            {
                for(int i = 1; i <= size; i++)
                {
                    com.maddox.rts.Message message = array[i];
                    if((message._flags & 0x10) != 0)
                        message._time = 0L;
                    else
                    if((message._flags & 0x20) != 0)
                        message._time = com.maddox.rts.Time.endReal() - 1L;
                    else
                        message._time = com.maddox.rts.Time.currentReal();
                    messagequeue.push_heap(message);
                    array[i] = null;
                }

            } else
            {
                for(int j = 1; j <= size; j++)
                {
                    com.maddox.rts.Message message1 = array[j];
                    if((message1._flags & 0x10) != 0)
                        message1._time = 0L;
                    else
                    if((message1._flags & 0x20) != 0)
                        message1._time = com.maddox.rts.Time.tickNext() - 1L;
                    else
                        message1._time = com.maddox.rts.Time.current();
                    messagequeue.push_heap(message1);
                    array[j] = null;
                }

            }
            size = 0;
        }
    }

    public final synchronized void clear()
    {
        for(int i = 1; i <= size; i++)
        {
            com.maddox.rts.Message message = array[i];
            message._flags &= -2;
            if((message._flags & 6) != 0)
                message.clean();
            array[i] = null;
        }

        size = 0;
    }

    public final synchronized boolean contains(com.maddox.rts.Message message)
    {
        return indexOf(message) >= 0;
    }

    public final synchronized boolean remove(com.maddox.rts.Message message)
    {
        int i = indexOf(message);
        if(i > 0)
        {
            message._flags &= -2;
            if((message._flags & 6) != 0)
                message.clean();
            adjust_heap(i);
            return true;
        } else
        {
            return false;
        }
    }

    public final synchronized com.maddox.rts.Message[] toArray()
    {
        com.maddox.rts.Message amessage[] = new com.maddox.rts.Message[size];
        java.lang.System.arraycopy(array, 1, amessage, 0, size);
        return amessage;
    }

    public final synchronized com.maddox.rts.Message[] toArray(com.maddox.rts.Message amessage[])
    {
        if(amessage.length < size)
            amessage = new com.maddox.rts.Message[size];
        java.lang.System.arraycopy(array, 1, amessage, 0, size);
        if(amessage.length > size)
            amessage[size] = null;
        return amessage;
    }

    public final synchronized int size()
    {
        return size;
    }

    public final synchronized boolean isEmpty()
    {
        return size == 0;
    }

    public final synchronized void ensureCapacity(int i)
    {
        int j = array.length - 1;
        if(++i > j)
        {
            com.maddox.rts.Message amessage[] = array;
            int k = (j * 3) / 2 + 1;
            if(k < i)
                k = i;
            array = new com.maddox.rts.Message[k];
            java.lang.System.arraycopy(amessage, 1, array, 1, size);
        }
    }

    public MessageQueue()
    {
        this(10);
    }

    public MessageQueue(int i)
    {
        stamp = 0L;
        size = 0;
        array = new com.maddox.rts.Message[i + 1];
    }

    private int indexOf(com.maddox.rts.Message message)
    {
        for(int i = 1; i <= size; i++)
            if(message == array[i])
                return i;

        return -1;
    }

    private final boolean gt(com.maddox.rts.Message message, com.maddox.rts.Message message1)
    {
        if(message._time > message1._time)
            return true;
        if(message._time < message1._time)
            return false;
        if(message._tickPos > message1._tickPos)
            return true;
        if(message._tickPos < message1._tickPos)
            return false;
        return message._queueStamp > message1._queueStamp;
    }

    private final void push_heap(com.maddox.rts.Message message)
    {
        message._queueStamp = stamp++;
        ensureCapacity(++size);
        int i = size;
        for(int j = i / 2; i > 1 && gt(array[j], message); j /= 2)
        {
            array[i] = array[j];
            i = j;
        }

        array[i] = message;
    }

    private final void pop_heap()
    {
        adjust_heap(1);
    }

    private final void adjust_heap(int i)
    {
        if(size > 1 && i < size)
        {
            com.maddox.rts.Message message = array[size];
            int j = i;
            int k = j / 2;
            boolean flag;
            for(flag = true; j > 1 && gt(array[k], message); flag = false)
            {
                array[j] = array[k];
                j = k;
                k /= 2;
            }

            if(flag)
            {
                for(int l = j * 2; l < size; l = j * 2)
                {
                    int i1;
                    if(l + 1 < size && gt(array[l], array[l + 1]))
                        i1 = l + 1;
                    else
                        i1 = l;
                    if(!gt(message, array[i1]))
                        break;
                    array[j] = array[i1];
                    j = i1;
                }

            }
            array[j] = message;
        }
        array[size--] = null;
    }

    private long stamp;
    private com.maddox.rts.Message array[];
    private int size;
}
