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
    if (((paramNetMsgSpawn.jdField__sender_of_type_ComMaddoxRtsNetObj instanceof NetChannelCallbackStream)) && (!((NetChannelCallbackStream)paramNetMsgSpawn.jdField__sender_of_type_ComMaddoxRtsNetObj).netChannelCallback(this, paramNetMsgSpawn)))
    {
      return;
    }super.putMessageSpawn(paramNetMsgSpawn);
  }
  protected void putMessageDestroy(NetMsgDestroy paramNetMsgDestroy) throws IOException {
    if (((paramNetMsgDestroy.jdField__sender_of_type_ComMaddoxRtsNetObj instanceof NetChannelCallbackStream)) && (!((NetChannelCallbackStream)paramNetMsgDestroy.jdField__sender_of_type_ComMaddoxRtsNetObj).netChannelCallback(this, paramNetMsgDestroy)))
    {
      return;
    }super.putMessageDestroy(paramNetMsgDestroy);
  }
  public void _putMessage(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    super.putMessage(paramNetMsgGuaranted);
  }
  protected void putMessage(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    if (((paramNetMsgGuaranted.jdField__sender_of_type_ComMaddoxRtsNetObj instanceof NetChannelCallbackStream)) && (!((NetChannelCallbackStream)paramNetMsgGuaranted.jdField__sender_of_type_ComMaddoxRtsNetObj).netChannelCallback(this, paramNetMsgGuaranted)))
    {
      return;
    }super.putMessage(paramNetMsgGuaranted);
  }

  protected void computeMessageLen(NetMsgFiltered paramNetMsgFiltered, long paramLong) {
    computeMessageLen(paramNetMsgFiltered, Time.fromReal(paramNetMsgFiltered._time), paramLong);
  }

  public void destroy()
  {
    if ((this.jdField_state_of_type_Int & 0x80000000) != 0) return;
    this.jdField_state_of_type_Int |= -2147483648;

    while (!this.jdField_objects_of_type_ComMaddoxUtilHashMapInt.isEmpty()) {
      localObject1 = this.jdField_objects_of_type_ComMaddoxUtilHashMapInt.nextEntry(null);
      if (localObject1 == null) break;
      localObject2 = (NetObj)((HashMapIntEntry)localObject1).getValue();
      int i = ((HashMapIntEntry)localObject1).getKey();
      NetChannel.destroyNetObj((NetObj)localObject2);
      if (this.jdField_objects_of_type_ComMaddoxUtilHashMapInt.containsKey(i)) {
        this.jdField_objects_of_type_ComMaddoxUtilHashMapInt.remove(i);
      }
    }
    while (!this.jdField_mirrored_of_type_ComMaddoxUtilHashMapExt.isEmpty()) {
      localObject1 = this.jdField_mirrored_of_type_ComMaddoxUtilHashMapExt.nextEntry(null);
      if (localObject1 == null) break;
      localObject2 = (NetObj)((Map.Entry)localObject1).getKey();
      localObject2.countMirrors -= 1;
      this.jdField_mirrored_of_type_ComMaddoxUtilHashMapExt.remove(localObject2);
      MsgNet.postRealDelChannel(localObject2, this);
    }

    Object localObject1 = NetEnv.cur().jdField_objects_of_type_ComMaddoxUtilHashMapInt;
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
    return this.jdField_state_of_type_Int >= 0;
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
    if (l >= this.jdField_lastPacketSendTime_of_type_Long + 65535L) {
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

    int i2 = NetChannel.filteredSortMsgs.size();
    Object localObject;
    for (int i3 = 0; i3 < i2; i3++) {
      localObject = (NetMsgFiltered)NetChannel.filteredSortMsgs.get(i3);
      if ((((NetMsgFiltered)localObject)._prior >= 1.0F) || 
        (((NetMsgFiltered)localObject).jdField__sender_of_type_ComMaddoxRtsNetObj == null) || (!((NetMsgFiltered)localObject).jdField__sender_of_type_ComMaddoxRtsNetObj.isMirror())) continue;
      ((NetMsgFiltered)localObject)._prior = 1.0F;
      NetChannel.filteredMinSizeMsgs += ((NetMsgFiltered)localObject)._len;
    }

    if ((i1 + n == 0) && (i == 0)) return false;
    if (i1 + n > j)
    {
      i2 = i1;
      i3 = n;
      if (i3 > j) i3 = j;
      i2 = j - i3;
      if (i2 < NetChannel.filteredMinSizeMsgs) {
        i2 = NetChannel.filteredMinSizeMsgs;

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
        i3 = NetChannel.guarantedSizeMsgs;
        if (j - i3 > i2) i2 = j - i3; 
      }
      else {
        m = computeCountSendGMsgs(i3);
        i3 = NetChannel.guarantedSizeMsgs;
      }
      i1 = i2;
      n = i3;
    }
    else {
      m = computeCountSendGMsgs(n);
      n = NetChannel.guarantedSizeMsgs;
    }

    i1 = fillFilteredArrayMessages(l, i1);
    if ((i1 + n == 0) && (i == 0)) return false;
    try
    {
      paramNetMsgOutput.clear();

      this.outData.writeShort((int)(l - this.jdField_lastPacketSendTime_of_type_Long));
      this.outData.writeShort(k + i1 + n);
      sendTime(paramNetMsgOutput, this.sendSequenceNum++, l, k + i1 + n);

      paramNetMsgOutput.writeShort(7361);

      paramNetMsgOutput.writeByte(m);
      if (m > 0) {
        for (i2 = 0; i2 < m; i2++) {
          NetChannelGMsgOutput localNetChannelGMsgOutput = (NetChannelGMsgOutput)this.jdField_sendGMsgs_of_type_ComMaddoxRtsNetChannelArrayList.get(i2);
          putMessage(paramNetMsgOutput, localNetChannelGMsgOutput.objIndex, localNetChannelGMsgOutput.msg, localNetChannelGMsgOutput.iObjects);
          localObject = localNetChannelGMsgOutput.msg;
          ((NetMsgGuaranted)localObject).lockDec();
          if (((NetMsgGuaranted)localObject).isRequiredAsk())
            MsgNetAskNak.postGame(Time.current(), ((NetMsgGuaranted)localObject).jdField__sender_of_type_ComMaddoxRtsNetObj, true, (NetMsgGuaranted)localObject, this);
          localNetChannelGMsgOutput.msg = null;
        }
        this.jdField_sendGMsgs_of_type_ComMaddoxRtsNetChannelArrayList.removeRange(0, m);
      }

      i2 = NetChannel.filteredSortMsgs.size();
      for (int i4 = 0; i4 < i2; i4++) {
        localObject = (NetMsgFiltered)NetChannel.filteredSortMsgs.get(i4);
        putMessage(paramNetMsgOutput, l, (NetMsgFiltered)localObject, Time.fromReal(((NetMsgFiltered)localObject)._time));
        ((NetMsgFiltered)localObject).lockDec();
      }
      NetChannel.filteredSortMsgs.clear();

      int i5 = CRC16.checksum(0, paramNetMsgOutput.data(), 0, paramNetMsgOutput.dataLength());
      paramNetMsgOutput.data()[0] = (byte)(i5 >>> 8 & 0xFF);
      paramNetMsgOutput.data()[1] = (byte)(i5 & 0xFF);

      this.outData.write(paramNetMsgOutput.data(), 0, paramNetMsgOutput.size()); } catch (Exception localException) {
      NetChannel.printDebug(localException);
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
    this.jdField_nextPacketSendTime_of_type_Long = (this.jdField_lastPacketSendTime_of_type_Long = Time.current());

    this.jdField_state_of_type_Int = 1;
  }

  public boolean isRequeredSendPacket(long paramLong) {
    return Time.current() > this.jdField_nextPacketSendTime_of_type_Long;
  }

  protected int getSendPacketLen(long paramLong)
  {
    if (!this.bCheckSpeed) return 1024;
    if (paramLong < this.jdField_nextPacketSendTime_of_type_Long)
      return 0;
    double d1 = 10.0D;
    double d2 = this.jdField_maxChSendSpeed_of_type_Double * d1;
    if (d2 < 256.0D)
      d2 = 256.0D;
    if (d2 > 1024.0D) {
      d2 = 1024.0D;
    }
    return (int)d2;
  }

  protected void sendTime(NetMsgOutput paramNetMsgOutput, int paramInt1, long paramLong, int paramInt2) throws IOException {
    double d = 10.0D;
    if (this.jdField_maxChSendSpeed_of_type_Double > 0.0D) {
      d = paramInt2 / this.jdField_maxChSendSpeed_of_type_Double;
    }
    if (d < 10.0D) d = 10.0D;
    this.jdField_nextPacketSendTime_of_type_Long = (paramLong + ()d);
    this.jdField_lastPacketSendTime_of_type_Long = paramLong;
  }

  public void setCheckSpeed(boolean paramBoolean)
  {
    this.bCheckSpeed = paramBoolean;
  }

  public double getMaxSpeed() {
    return this.jdField_maxSendSpeed_of_type_Double;
  }
  public void setMaxSpeed(double paramDouble) {
    if (paramDouble == this.jdField_maxSendSpeed_of_type_Double) return;
    this.jdField_maxSendSpeed_of_type_Double = paramDouble;
    this.jdField_maxChSendSpeed_of_type_Double = paramDouble;
  }
}