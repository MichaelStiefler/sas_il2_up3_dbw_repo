package com.maddox.rts;

import com.maddox.rts.net.NetEmptyAddress;
import com.maddox.rts.net.NetEmptySocket;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map.Entry;

public class NetChannelOutStream extends NetChannel
  implements NetChannelStream
{
  private static final boolean DEBUG = false;
  OutputStream outStream;
  DataOutputStream outData;
  NetPacket flushPacket;
  NetMsgFiltered flushMsg;
  private boolean bCheckSpeed = true;

  protected void putMessageSpawn(NetMsgSpawn paramNetMsgSpawn)
    throws IOException
  {
    if (((paramNetMsgSpawn._sender instanceof NetChannelCallbackStream)) && (!((NetChannelCallbackStream)paramNetMsgSpawn._sender).netChannelCallback(this, paramNetMsgSpawn)))
    {
      return;
    }super.putMessageSpawn(paramNetMsgSpawn);
  }
  protected void putMessageDestroy(NetMsgDestroy paramNetMsgDestroy) throws IOException {
    if (((paramNetMsgDestroy._sender instanceof NetChannelCallbackStream)) && (!((NetChannelCallbackStream)paramNetMsgDestroy._sender).netChannelCallback(this, paramNetMsgDestroy)))
    {
      return;
    }super.putMessageDestroy(paramNetMsgDestroy);
  }
  public void _putMessage(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    super.putMessage(paramNetMsgGuaranted);
  }
  protected void putMessage(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    if (((paramNetMsgGuaranted._sender instanceof NetChannelCallbackStream)) && (!((NetChannelCallbackStream)paramNetMsgGuaranted._sender).netChannelCallback(this, paramNetMsgGuaranted)))
    {
      return;
    }super.putMessage(paramNetMsgGuaranted);
  }

  protected void computeMessageLen(NetMsgFiltered paramNetMsgFiltered, long paramLong) {
    computeMessageLen(paramNetMsgFiltered, Time.fromReal(paramNetMsgFiltered._time), paramLong);
  }

  public void destroy()
  {
    if ((this.state & 0x80000000) != 0) return;
    this.state |= -2147483648;

    while (!this.objects.isEmpty()) {
      localObject1 = this.objects.nextEntry(null);
      if (localObject1 == null) break;
      localObject2 = (NetObj)((HashMapIntEntry)localObject1).getValue();
      int i = ((HashMapIntEntry)localObject1).getKey();
      destroyNetObj((NetObj)localObject2);
      if (this.objects.containsKey(i)) {
        this.objects.remove(i);
      }
    }
    while (!this.mirrored.isEmpty()) {
      localObject1 = this.mirrored.nextEntry(null);
      if (localObject1 == null) break;
      localObject2 = (NetObj)((Map.Entry)localObject1).getKey();
      localObject2.countMirrors -= 1;
      this.mirrored.remove(localObject2);
      MsgNet.postRealDelChannel(localObject2, this);
    }

    Object localObject1 = NetEnv.cur().objects;
    Object localObject2 = ((HashMapInt)localObject1).nextEntry(null);
    while (localObject2 != null) {
      NetObj localNetObj = (NetObj)((HashMapIntEntry)localObject2).getValue();
      if (localNetObj.isCommon())
        MsgNet.postRealDelChannel(localNetObj, this);
      localObject2 = ((HashMapInt)localObject1).nextEntry((HashMapIntEntry)localObject2);
    }

    clearSortGuaranted();
    clearSendGMsgs();
    clearSendFMsgs();
    if (this.outStream != null) {
      try {
        this.outStream.close(); } catch (Exception localException) {
      }
      this.outStream = null;
    }
  }

  protected boolean update()
  {
    return this.state >= 0;
  }

  public void flush()
  {
    boolean bool = this.bCheckSpeed;
    this.bCheckSpeed = false;
    if (this.flushPacket == null) {
      this.flushPacket = new NetPacket(new byte[2048], 0);
      this.flushMsg = new NetMsgFiltered(this.flushPacket.getData());
    }
    while (sendPacket(this.flushMsg, this.flushPacket));
    this.bCheckSpeed = bool;
  }

  protected boolean sendPacket(NetMsgOutput paramNetMsgOutput, NetPacket paramNetPacket)
  {
    if (this.outStream == null) return false;
    long l = Time.current();
    int i = 0;

    int j = getSendPacketLen(l);
    if (l >= this.lastPacketSendTime + 65535L) {
      if (j < 20) j = 20;
      i = 1;
    }
    if (j <= 0) return false;
    int k = 3;

    j -= 7;
    if (j <= 0) return false;

    int m = 0;
    int n = computeSizeSendGMsgs(l);

    int i1 = computeSizeSendFMsgs(l);

    int i2 = filteredSortMsgs.size();
    Object localObject;
    for (int i3 = 0; i3 < i2; i3++) {
      localObject = (NetMsgFiltered)filteredSortMsgs.get(i3);
      if ((((NetMsgFiltered)localObject)._prior >= 1.0F) || 
        (((NetMsgFiltered)localObject)._sender == null) || (!((NetMsgFiltered)localObject)._sender.isMirror())) continue;
      ((NetMsgFiltered)localObject)._prior = 1.0F;
      filteredMinSizeMsgs += ((NetMsgFiltered)localObject)._len;
    }

    if ((i1 + n == 0) && (i == 0)) return false;
    if (i1 + n > j)
    {
      i2 = i1;
      i3 = n;
      if (i3 > j) i3 = j;
      i2 = j - i3;
      if (i2 < filteredMinSizeMsgs) {
        i2 = filteredMinSizeMsgs;

        j = i3 + i2;
        if (j > 1024) {
          j = 1024;
          if (i2 > j)
            i2 = j;
        }
      }
      i3 = j - i2;
      if (i3 < 0) {
        i3 = 0;
      }
      if ((i3 > 0) && (i3 < n)) {
        m = computeCountSendGMsgs(i3);
        i3 = guarantedSizeMsgs;
        if (j - i3 > i2) i2 = j - i3; 
      }
      else {
        m = computeCountSendGMsgs(i3);
        i3 = guarantedSizeMsgs;
      }
      i1 = i2;
      n = i3;
    }
    else {
      m = computeCountSendGMsgs(n);
      n = guarantedSizeMsgs;
    }

    i1 = fillFilteredArrayMessages(l, i1);
    if ((i1 + n == 0) && (i == 0)) return false;
    try
    {
      paramNetMsgOutput.clear();

      this.outData.writeShort((int)(l - this.lastPacketSendTime));
      this.outData.writeShort(k + i1 + n);
      sendTime(paramNetMsgOutput, this.sendSequenceNum++, l, k + i1 + n);

      paramNetMsgOutput.writeShort(7361);

      paramNetMsgOutput.writeByte(m);
      if (m > 0) {
        for (i2 = 0; i2 < m; i2++) {
          NetChannelGMsgOutput localNetChannelGMsgOutput = (NetChannelGMsgOutput)this.sendGMsgs.get(i2);
          putMessage(paramNetMsgOutput, localNetChannelGMsgOutput.objIndex, localNetChannelGMsgOutput.msg, localNetChannelGMsgOutput.iObjects);
          localObject = localNetChannelGMsgOutput.msg;
          ((NetMsgGuaranted)localObject).lockDec();
          if (((NetMsgGuaranted)localObject).isRequiredAsk())
            MsgNetAskNak.postGame(Time.current(), ((NetMsgGuaranted)localObject)._sender, true, (NetMsgGuaranted)localObject, this);
          localNetChannelGMsgOutput.msg = null;
        }
        this.sendGMsgs.removeRange(0, m);
      }

      i2 = filteredSortMsgs.size();
      for (int i4 = 0; i4 < i2; i4++) {
        localObject = (NetMsgFiltered)filteredSortMsgs.get(i4);
        putMessage(paramNetMsgOutput, l, (NetMsgFiltered)localObject, Time.fromReal(((NetMsgFiltered)localObject)._time));
        ((NetMsgFiltered)localObject).lockDec();
      }
      filteredSortMsgs.clear();

      i4 = CRC16.checksum(0, paramNetMsgOutput.data(), 0, paramNetMsgOutput.dataLength());
      paramNetMsgOutput.data()[0] = (byte)(i4 >>> 8 & 0xFF);
      paramNetMsgOutput.data()[1] = (byte)(i4 & 0xFF);

      this.outData.write(paramNetMsgOutput.data(), 0, paramNetMsgOutput.size()); } catch (Exception localException) {
      printDebug(localException);
    }return !this.bCheckSpeed;
  }

  protected boolean receivePacket(NetMsgInput paramNetMsgInput, long paramLong) throws IOException
  {
    return true;
  }

  public NetChannelOutStream(OutputStream paramOutputStream, int paramInt) {
    this.outStream = paramOutputStream;
    this.outData = new DataOutputStream(paramOutputStream);
    this.flags = paramInt;
    this.id = RTSConf.cur.netEnv.nextIdChannel(false);
    this.remoteId = 1;
    this.socket = new NetEmptySocket();
    this.remoteAddress = new NetEmptyAddress();
    this.remotePort = 0;
    this.nextPacketSendTime = (this.lastPacketSendTime = Time.current());

    this.state = 1;
  }

  public boolean isRequeredSendPacket(long paramLong) {
    return Time.current() > this.nextPacketSendTime;
  }

  protected int getSendPacketLen(long paramLong)
  {
    if (!this.bCheckSpeed) return 1024;
    if (paramLong < this.nextPacketSendTime)
      return 0;
    double d1 = 10.0D;
    double d2 = this.maxChSendSpeed * d1;
    if (d2 < 256.0D)
      d2 = 256.0D;
    if (d2 > 1024.0D) {
      d2 = 1024.0D;
    }
    return (int)d2;
  }

  protected void sendTime(NetMsgOutput paramNetMsgOutput, int paramInt1, long paramLong, int paramInt2) throws IOException {
    double d = 10.0D;
    if (this.maxChSendSpeed > 0.0D) {
      d = paramInt2 / this.maxChSendSpeed;
    }
    if (d < 10.0D) d = 10.0D;
    this.nextPacketSendTime = (paramLong + ()d);
    this.lastPacketSendTime = paramLong;
  }

  public void setCheckSpeed(boolean paramBoolean)
  {
    this.bCheckSpeed = paramBoolean;
  }

  public double getMaxSpeed() {
    return this.maxSendSpeed;
  }
  public void setMaxSpeed(double paramDouble) {
    if (paramDouble == this.maxSendSpeed) return;
    this.maxSendSpeed = paramDouble;
    this.maxChSendSpeed = paramDouble;
  }
}