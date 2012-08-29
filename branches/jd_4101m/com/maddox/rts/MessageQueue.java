package com.maddox.rts;

public final class MessageQueue
{
  private long stamp = 0L;
  private Message[] array;
  private int size = 0;

  public final synchronized void put(Message paramMessage, Object paramObject1, long paramLong, int paramInt, Object paramObject2)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject1; paramMessage._time = paramLong; paramMessage._tickPos = paramInt; paramMessage._sender = paramObject2;
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt1, Message paramMessage, Object paramObject1, long paramLong, int paramInt2, Object paramObject2)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject1; paramMessage._time = paramLong; paramMessage._tickPos = paramInt2; paramMessage._sender = paramObject2;
    paramMessage._flags = (paramInt1 & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized void put(Message paramMessage, Object paramObject, long paramLong, int paramInt)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject; paramMessage._time = paramLong; paramMessage._tickPos = paramInt;
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt1, Message paramMessage, Object paramObject, long paramLong, int paramInt2)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject; paramMessage._time = paramLong; paramMessage._tickPos = paramInt2;
    paramMessage._flags = (paramInt1 & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized void put(Message paramMessage, Object paramObject1, long paramLong, Object paramObject2)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject1; paramMessage._time = paramLong; paramMessage._sender = paramObject2;
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt, Message paramMessage, Object paramObject1, long paramLong, Object paramObject2)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject1; paramMessage._time = paramLong; paramMessage._sender = paramObject2;
    paramMessage._flags = (paramInt & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized void put(Message paramMessage, Object paramObject1, int paramInt, Object paramObject2)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject1; paramMessage._tickPos = paramInt; paramMessage._sender = paramObject2;
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt1, Message paramMessage, Object paramObject1, int paramInt2, Object paramObject2)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject1; paramMessage._tickPos = paramInt2; paramMessage._sender = paramObject2;
    paramMessage._flags = (paramInt1 & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized void put(Message paramMessage, long paramLong, int paramInt, Object paramObject)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._time = paramLong; paramMessage._tickPos = paramInt; paramMessage._sender = paramObject;
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt1, Message paramMessage, long paramLong, int paramInt2, Object paramObject)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._time = paramLong; paramMessage._tickPos = paramInt2; paramMessage._sender = paramObject;
    paramMessage._flags = (paramInt1 & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized void put(Message paramMessage, long paramLong, Object paramObject)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._time = paramLong; paramMessage._sender = paramObject;
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt, Message paramMessage, long paramLong, Object paramObject)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._time = paramLong; paramMessage._sender = paramObject;
    paramMessage._flags = (paramInt & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized void put(Message paramMessage, int paramInt, Object paramObject)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._tickPos = paramInt; paramMessage._sender = paramObject;
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt1, Message paramMessage, int paramInt2, Object paramObject)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._tickPos = paramInt2; paramMessage._sender = paramObject;
    paramMessage._flags = (paramInt1 & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized void put(Message paramMessage, long paramLong, int paramInt)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._time = paramLong; paramMessage._tickPos = paramInt;
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt1, Message paramMessage, long paramLong, int paramInt2)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._time = paramLong; paramMessage._tickPos = paramInt2;
    paramMessage._flags = (paramInt1 & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized void put(Message paramMessage, long paramLong)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._time = paramLong;
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt, Message paramMessage, long paramLong)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._time = paramLong;
    paramMessage._flags = (paramInt & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized void put(Message paramMessage, int paramInt)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._tickPos = paramInt;
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt1, Message paramMessage, int paramInt2)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._tickPos = paramInt2;
    paramMessage._flags = (paramInt1 & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized void put(Message paramMessage, Object paramObject, long paramLong)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject; paramMessage._time = paramLong;
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt, Message paramMessage, Object paramObject, long paramLong)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject; paramMessage._time = paramLong;
    paramMessage._flags = (paramInt & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized void put(Message paramMessage, Object paramObject, int paramInt)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject; paramMessage._tickPos = paramInt;
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt1, Message paramMessage, Object paramObject, int paramInt2)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject; paramMessage._tickPos = paramInt2;
    paramMessage._flags = (paramInt1 & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized void put(Message paramMessage, Object paramObject1, Object paramObject2)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject1; paramMessage._sender = paramObject2;
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt, Message paramMessage, Object paramObject1, Object paramObject2)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject1; paramMessage._sender = paramObject2;
    paramMessage._flags = (paramInt & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized void put(Message paramMessage, Object paramObject)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject;
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt, Message paramMessage, Object paramObject)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._listener = paramObject;
    paramMessage._flags = (paramInt & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized void put(Message paramMessage)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._flags |= 1;
    push_heap(paramMessage);
  }

  public final synchronized void put(int paramInt, Message paramMessage)
    throws MessageException
  {
    if ((paramMessage._flags & 0x1) != 0) throw new MessageException("message is busy");
    paramMessage._flags = (paramInt & 0xFFFFFFF8 | paramMessage._flags & 0x6 | 0x1);
    push_heap(paramMessage);
  }

  public final synchronized Message get(long paramLong)
  {
    if (this.size == 0)
      return null;
    Message localMessage = this.array[1];
    if (localMessage._time >= paramLong)
      return null;
    pop_heap();
    return localMessage;
  }

  public final synchronized Message peek(long paramLong)
  {
    if (this.size == 0)
      return null;
    Message localMessage = this.array[1];
    if (localMessage._time >= paramLong)
      return null;
    return localMessage;
  }

  public final synchronized Message peekByIndex(int paramInt) {
    if (this.size == 0)
      return null;
    paramInt++;
    if (paramInt > this.size)
      return null;
    return this.array[paramInt];
  }

  public final synchronized void move(MessageQueue paramMessageQueue)
  {
    synchronized (paramMessageQueue) {
      for (int i = 1; i <= this.size; i++) {
        Message localMessage = this.array[i];
        paramMessageQueue.push_heap(localMessage);
        this.array[i] = null;
      }
      this.size = 0;
    }
  }

  public final synchronized void move(MessageQueue paramMessageQueue, long paramLong)
  {
    synchronized (paramMessageQueue) {
      for (int i = 1; i <= this.size; i++) {
        Message localMessage = this.array[i];
        localMessage._time += paramLong;
        paramMessageQueue.push_heap(localMessage);
        this.array[i] = null;
      }
      this.size = 0;
    }
  }

  public final synchronized void moveFromNextTick(MessageQueue paramMessageQueue, boolean paramBoolean)
  {
    synchronized (paramMessageQueue)
    {
      int i;
      Message localMessage;
      if (paramBoolean)
        for (i = 1; i <= this.size; i++) {
          localMessage = this.array[i];
          if ((localMessage._flags & 0x10) != 0)
            localMessage._time = 0L;
          else if ((localMessage._flags & 0x20) != 0)
            localMessage._time = (Time.endReal() - 1L);
          else {
            localMessage._time = Time.currentReal();
          }
          paramMessageQueue.push_heap(localMessage);
          this.array[i] = null;
        }
      else {
        for (i = 1; i <= this.size; i++) {
          localMessage = this.array[i];
          if ((localMessage._flags & 0x10) != 0)
            localMessage._time = 0L;
          else if ((localMessage._flags & 0x20) != 0)
            localMessage._time = (Time.tickNext() - 1L);
          else {
            localMessage._time = Time.current();
          }
          paramMessageQueue.push_heap(localMessage);
          this.array[i] = null;
        }
      }
      this.size = 0;
    }
  }

  public final synchronized void clear()
  {
    for (int i = 1; i <= this.size; i++) {
      Message localMessage = this.array[i];
      localMessage._flags &= -2;
      if ((localMessage._flags & 0x6) != 0)
        localMessage.clean();
      this.array[i] = null;
    }

    this.size = 0;
  }

  public final synchronized boolean contains(Message paramMessage)
  {
    return indexOf(paramMessage) >= 0;
  }

  public final synchronized boolean remove(Message paramMessage)
  {
    int i = indexOf(paramMessage);
    if (i > 0) {
      paramMessage._flags &= -2;
      if ((paramMessage._flags & 0x6) != 0)
        paramMessage.clean();
      adjust_heap(i);
      return true;
    }
    return false;
  }

  public final synchronized Message[] toArray()
  {
    Message[] arrayOfMessage = new Message[this.size];
    System.arraycopy(this.array, 1, arrayOfMessage, 0, this.size);
    return arrayOfMessage;
  }

  public final synchronized Message[] toArray(Message[] paramArrayOfMessage)
  {
    if (paramArrayOfMessage.length < this.size)
      paramArrayOfMessage = new Message[this.size];
    System.arraycopy(this.array, 1, paramArrayOfMessage, 0, this.size);
    if (paramArrayOfMessage.length > this.size) {
      paramArrayOfMessage[this.size] = null;
    }
    return paramArrayOfMessage;
  }

  public final synchronized int size() {
    return this.size;
  }

  public final synchronized boolean isEmpty()
  {
    return this.size == 0;
  }

  public final synchronized void ensureCapacity(int paramInt)
  {
    int i = this.array.length - 1;
    paramInt++;
    if (paramInt > i) {
      Message[] arrayOfMessage = this.array;
      int j = i * 3 / 2 + 1;
      if (j < paramInt)
        j = paramInt;
      this.array = new Message[j];
      System.arraycopy(arrayOfMessage, 1, this.array, 1, this.size);
    }
  }

  public MessageQueue()
  {
    this(10);
  }

  public MessageQueue(int paramInt)
  {
    this.array = new Message[paramInt + 1];
  }

  private int indexOf(Message paramMessage)
  {
    for (int i = 1; i <= this.size; i++) {
      if (paramMessage == this.array[i])
        return i;
    }
    return -1;
  }

  private final boolean gt(Message paramMessage1, Message paramMessage2)
  {
    if (paramMessage1._time > paramMessage2._time)
      return true;
    if (paramMessage1._time < paramMessage2._time) {
      return false;
    }
    if (paramMessage1._tickPos > paramMessage2._tickPos)
      return true;
    if (paramMessage1._tickPos < paramMessage2._tickPos) {
      return false;
    }

    return paramMessage1._queueStamp > paramMessage2._queueStamp;
  }

  private final void push_heap(Message paramMessage)
  {
    paramMessage._queueStamp = (this.stamp++);
    ensureCapacity(++this.size);

    int i = this.size;
    int j = i / 2;
    while ((i > 1) && (gt(this.array[j], paramMessage))) {
      this.array[i] = this.array[j];
      i = j;
      j /= 2;
    }
    this.array[i] = paramMessage;
  }

  private final void pop_heap() {
    adjust_heap(1);
  }

  private final void adjust_heap(int paramInt) {
    if ((this.size > 1) && (paramInt < this.size)) {
      Message localMessage = this.array[this.size];
      int i = paramInt;
      int j = i / 2;
      int k = 1;
      while ((i > 1) && (gt(this.array[j], localMessage))) {
        this.array[i] = this.array[j];
        i = j;
        j /= 2;
        k = 0;
      }
      if (k != 0) {
        j = i * 2;

        while (j < this.size)
        {
          int m;
          if ((j + 1 < this.size) && (gt(this.array[j], this.array[(j + 1)])))
            m = j + 1;
          else {
            m = j;
          }
          if (!gt(localMessage, this.array[m])) break;
          this.array[i] = this.array[m];
          i = m;
          j = i * 2;
        }

      }

      this.array[i] = localMessage;
    }
    this.array[(this.size--)] = null;
  }
}