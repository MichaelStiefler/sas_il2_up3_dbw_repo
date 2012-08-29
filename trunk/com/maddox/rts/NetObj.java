package com.maddox.rts;

import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public abstract class NetObj
  implements Destroy, MsgNetListener
{
  protected Object superObj = null;

  protected int idLocal = -1;

  protected int idRemote = 0;

  protected NetChannel masterChannel = null;

  protected int countMirrors = 0;
  private static long timeStamp;

  public int idLocal()
  {
    return this.idLocal;
  }

  public int idRemote()
  {
    return this.idRemote;
  }

  public Object superObj()
  {
    return this.superObj;
  }
  public NetChannel masterChannel() {
    return this.masterChannel;
  }

  public void destroyOnlyLocal() {
    if (this.idLocal > 0) {
      NetEnv.cur().objects.remove(this.idLocal);
      this.idLocal |= -2147483648;
      if ((this instanceof NetUpdate)) {
        int i = NetEnv.cur().udatedObjects.indexOf(this);
        if (i >= 0) {
          NetEnv.cur().udatedObjects.remove(i);
        }
      }
      if (this.masterChannel != null) {
        this.masterChannel.objects.remove(this.idRemote);
        if ((this instanceof NetFilter))
          this.masterChannel.filterRemove((NetFilter)this);
      }
      if ((!isCommon()) && (this.countMirrors > 0)) {
        Object localObject = null;
        List localList = NetEnv.channels();
        int j = localList.size();
        for (int k = 0; k < j; k++) {
          NetChannel localNetChannel = (NetChannel)localList.get(k);
          if ((this.masterChannel != localNetChannel) && (localNetChannel.isMirrored(this))) {
            localNetChannel.mirrored.remove(this);
            this.countMirrors -= 1;
          }
        }
      }
    }
  }

  public void destroy()
  {
    if (this.idLocal > 0) {
      NetEnv.cur().objects.remove(this.idLocal);
      this.idLocal |= -2147483648;
      if ((this instanceof NetUpdate)) {
        int i = NetEnv.cur().udatedObjects.indexOf(this);
        if (i >= 0) {
          NetEnv.cur().udatedObjects.remove(i);
        }
      }
      if (this.masterChannel != null) {
        this.masterChannel.objects.remove(this.idRemote);
        if ((this instanceof NetFilter))
          this.masterChannel.filterRemove((NetFilter)this);
      }
      if ((!isCommon()) && (this.countMirrors > 0)) {
        NetMsgDestroy localNetMsgDestroy = null;
        List localList = NetEnv.channels();
        int j = localList.size();
        for (int k = 0; k < j; k++) {
          NetChannel localNetChannel = (NetChannel)localList.get(k);
          if ((this.masterChannel == localNetChannel) || (!localNetChannel.isMirrored(this))) continue;
          try {
            if (localNetMsgDestroy == null) localNetMsgDestroy = new NetMsgDestroy(this);
            localNetChannel.putMessageDestroy(localNetMsgDestroy);
          } catch (Exception localException) {
          }
        }
      }
    }
  }

  public boolean isDestroyed() {
    return this.idLocal < 0;
  }
  public boolean isMaster() {
    return this.idRemote == 0;
  }
  public boolean isMirror() {
    return this.idRemote != 0;
  }
  public boolean isCommon() {
    return (isMaster()) && ((this.idLocal & 0x7FFF) < 255);
  }
  public boolean isMirrored() {
    return this.countMirrors != 0;
  }
  public int countMirrors() {
    return this.countMirrors;
  }

  public int countNoMirrors() {
    List localList = NetEnv.channels();
    int i = localList.size();
    NetChannel localNetChannel1 = null;
    if (isMirror()) localNetChannel1 = this.masterChannel;
    int j = 0;
    for (int k = 0; k < i; k++) {
      NetChannel localNetChannel2 = (NetChannel)localList.get(k);
      if ((localNetChannel2 != localNetChannel1) && (localNetChannel2.isReady()) && (localNetChannel2.isPublic()) && (!localNetChannel2.isMirrored(this)))
        j++;
    }
    return j;
  }

  public void post(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    NetChannel localNetChannel = null;
    if (isMirror()) localNetChannel = this.masterChannel;
    postExclude(localNetChannel, paramNetMsgGuaranted);
  }

  public void postTo(NetChannel paramNetChannel, NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.checkLock();
    paramNetMsgGuaranted._sender = this;
    if ((paramNetMsgGuaranted instanceof NetMsgSpawn)) {
      if ((!paramNetChannel.isSortGuaranted()) && (paramNetChannel.isMirrored(this)))
        return;
      paramNetChannel.putMessageSpawn((NetMsgSpawn)paramNetMsgGuaranted);
    } else if ((paramNetMsgGuaranted instanceof NetMsgDestroy)) {
      if ((!paramNetChannel.isSortGuaranted()) && (!paramNetChannel.isMirrored(this)))
        return;
      paramNetChannel.putMessageDestroy((NetMsgDestroy)paramNetMsgGuaranted);
    } else {
      paramNetChannel.putMessage(paramNetMsgGuaranted);
    }
  }

  public void postExclude(NetChannel paramNetChannel, NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.checkLock();
    paramNetMsgGuaranted._sender = this;
    List localList = NetEnv.channels();
    int i = localList.size();
    NetChannel localNetChannel1;
    int k;
    NetChannel localNetChannel3;
    if ((paramNetMsgGuaranted instanceof NetMsgSpawn)) {
      localNetChannel1 = null;
      if (isMirror()) localNetChannel1 = this.masterChannel;
      for (k = 0; k < i; k++) {
        localNetChannel3 = (NetChannel)localList.get(k);
        if ((localNetChannel3 == paramNetChannel) || (localNetChannel3 == localNetChannel1) || (!localNetChannel3.isReady()) || (!localNetChannel3.isPublic()) || (localNetChannel3.isMirrored(this))) {
          continue;
        }
        localNetChannel3.putMessageSpawn((NetMsgSpawn)paramNetMsgGuaranted);
      }
    } else if ((paramNetMsgGuaranted instanceof NetMsgDestroy)) {
      localNetChannel1 = null;
      if (isMirror()) localNetChannel1 = this.masterChannel;
      for (k = 0; k < i; k++) {
        localNetChannel3 = (NetChannel)localList.get(k);
        if ((localNetChannel3 == paramNetChannel) || (localNetChannel3 == localNetChannel1) || (!localNetChannel3.isReady()) || (!localNetChannel3.isMirrored(this))) {
          continue;
        }
        localNetChannel3.putMessageDestroy((NetMsgDestroy)paramNetMsgGuaranted);
      }
    } else {
      for (int j = 0; j < i; j++) {
        NetChannel localNetChannel2 = (NetChannel)localList.get(j);
        if ((localNetChannel2 == paramNetChannel) || (!localNetChannel2.isReady()) || ((!localNetChannel2.isMirrored(this)) && (localNetChannel2 != this.masterChannel)))
          continue;
        localNetChannel2.putMessage(paramNetMsgGuaranted);
      }
    }
  }

  public void post(long paramLong, NetMsgFiltered paramNetMsgFiltered)
    throws IOException
  {
    postReal(Time.toReal(paramLong), paramNetMsgFiltered);
  }

  public void postReal(long paramLong, NetMsgFiltered paramNetMsgFiltered)
    throws IOException
  {
    NetChannel localNetChannel = null;
    if (isMirror()) localNetChannel = this.masterChannel;
    postRealExclude(paramLong, localNetChannel, paramNetMsgFiltered);
  }

  public void postTo(long paramLong, NetChannel paramNetChannel, NetMsgFiltered paramNetMsgFiltered)
    throws IOException
  {
    postRealTo(Time.toReal(paramLong), paramNetChannel, paramNetMsgFiltered);
  }

  public void postRealTo(long paramLong, NetChannel paramNetChannel, NetMsgFiltered paramNetMsgFiltered)
    throws IOException
  {
    paramNetMsgFiltered.checkLock();
    paramNetMsgFiltered._time = paramLong;
    paramNetMsgFiltered._timeStamp = (timeStamp++);
    paramNetMsgFiltered._sender = this;
    paramNetChannel.putMessage(paramNetMsgFiltered);
  }

  public void postExclude(long paramLong, NetChannel paramNetChannel, NetMsgFiltered paramNetMsgFiltered)
    throws IOException
  {
    postRealExclude(Time.toReal(paramLong), paramNetChannel, paramNetMsgFiltered);
  }

  public void postRealExclude(long paramLong, NetChannel paramNetChannel, NetMsgFiltered paramNetMsgFiltered)
    throws IOException
  {
    paramNetMsgFiltered.checkLock();
    paramNetMsgFiltered._time = paramLong;
    paramNetMsgFiltered._timeStamp = (timeStamp++);
    paramNetMsgFiltered._sender = this;
    List localList = NetEnv.channels();
    int i = localList.size();
    for (int j = 0; j < i; j++) {
      NetChannel localNetChannel = (NetChannel)localList.get(j);
      if ((localNetChannel == paramNetChannel) || (!localNetChannel.isReady()) || (!localNetChannel.isMirrored(this)))
        continue;
      localNetChannel.putMessage(paramNetMsgFiltered);
    }
  }

  public void msgNet(NetMsgInput paramNetMsgInput)
  {
    try
    {
      netInput(paramNetMsgInput); } catch (Exception localException) {
      printDebug(localException);
    }
  }

  public boolean netInput(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    return false;
  }

  public void msgNetNewChannel(NetChannel paramNetChannel)
  {
    try
    {
      if (paramNetChannel.isMirrored(this)) return;
      NetMsgSpawn localNetMsgSpawn = netReplicate(paramNetChannel);
      if (localNetMsgSpawn != null)
        postTo(paramNetChannel, localNetMsgSpawn); 
    } catch (Exception localException) {
      printDebug(localException);
    }
  }

  public NetMsgSpawn netReplicate(NetChannel paramNetChannel)
    throws IOException
  {
    return null;
  }

  public void msgNetDelChannel(NetChannel paramNetChannel)
  {
  }

  public static void tryReplicate(NetObj paramNetObj, boolean paramBoolean)
  {
    int i = paramNetObj.countNoMirrors();
    if (i == 0)
      return;
    List localList = NetEnv.channels();
    int j = localList.size();
    NetChannel localNetChannel1 = null;
    if (paramNetObj.isMirror()) localNetChannel1 = paramNetObj.masterChannel;
    for (int k = 0; k < j; k++) {
      NetChannel localNetChannel2 = (NetChannel)localList.get(k);
      if ((localNetChannel2 == localNetChannel1) || ((!localNetChannel2.isReady()) && ((localNetChannel2.isDestroying()) || (localNetChannel2.state() == 1))) || (!localNetChannel2.isPublic()) || (localNetChannel2.isMirrored(paramNetObj)))
      {
        continue;
      }
      if (paramBoolean) paramNetObj.msgNetNewChannel(localNetChannel2); else
        MsgNet.postRealNewChannel(paramNetObj, localNetChannel2);
    }
  }

  public NetObj(Object paramObject, int paramInt)
  {
    this.superObj = paramObject;
    if (paramInt == -1) {
      paramInt = (int)(Math.random() * 32511.0D + 255.0D);
      paramInt = NetEnv.cur().objects.findNotUsedKey(255, paramInt, 32767);
      if (paramInt == 32767)
        throw new NetException("Net local slots from 255 to 32767 is FULL");
    }
    else if (NetEnv.cur().objects.containsKey(paramInt)) {
      throw new NetException("Net local slot: " + paramInt + " is alredy used");
    }
    NetEnv.cur().objects.put(paramInt, this);
    this.idLocal = paramInt;
    if ((this instanceof NetUpdate))
      NetEnv.cur().udatedObjects.add(this);
    tryReplicate(this, false);
  }

  public NetObj(Object paramObject)
  {
    this.superObj = paramObject;
    int i = NetEnv.cur().objects.findNotUsedKey(255, NetEnv.cur().nextFreeID, 32767);
    if (i == 32767)
      throw new NetException("Net local slots from 255 to 32767 is FULL");
    NetEnv.cur().objects.put(i, this);
    this.idLocal = i;
    i++; if (i == 32767) i = 255;
    NetEnv.cur().nextFreeID = i;
    if ((this instanceof NetUpdate))
      NetEnv.cur().udatedObjects.add(this);
    tryReplicate(this, false);
  }

  public NetObj(Object paramObject, NetChannel paramNetChannel, int paramInt)
  {
    this.superObj = paramObject;
    int i = NetEnv.cur().objects.findNotUsedKey(255, NetEnv.cur().nextFreeID, 32767);
    if (i == 32767)
      throw new NetException("Net local slots from 255 to 32767 is FULL");
    NetEnv.cur().objects.put(i, this);
    this.idLocal = i;
    i++; if (i == 32767) i = 255;
    NetEnv.cur().nextFreeID = i;
    if ((this instanceof NetUpdate)) {
      NetEnv.cur().udatedObjects.add(this);
    }
    paramNetChannel.objects.put(paramInt, this);
    this.masterChannel = paramNetChannel;
    this.idRemote = paramInt;
    if ((this instanceof NetFilter))
      paramNetChannel.filterAdd((NetFilter)this);
    tryReplicate(this, false);
  }

  public NetObj(Object paramObject, int paramInt1, NetChannel paramNetChannel, int paramInt2)
  {
    this.superObj = paramObject;
    if (paramInt1 == -1) {
      paramInt1 = (int)(Math.random() * 32511.0D + 255.0D);
      paramInt1 = NetEnv.cur().objects.findNotUsedKey(255, paramInt1, 32767);
      if (paramInt1 == 32767)
        throw new NetException("Net local slots from 255 to 32767 is FULL");
    }
    else if (NetEnv.cur().objects.containsKey(paramInt1)) {
      throw new NetException("Net local slot: " + paramInt1 + " is alredy used");
    }
    NetEnv.cur().objects.put(paramInt1, this);
    this.idLocal = paramInt1;
    if ((this instanceof NetUpdate)) {
      NetEnv.cur().udatedObjects.add(this);
    }
    paramNetChannel.objects.put(paramInt2, this);
    this.masterChannel = paramNetChannel;
    this.idRemote = paramInt2;
    if ((this instanceof NetFilter))
      paramNetChannel.filterAdd((NetFilter)this);
    tryReplicate(this, false);
  }

  protected static void printDebug(Exception paramException)
  {
    System.out.println(paramException.getMessage());
    paramException.printStackTrace();
  }
}