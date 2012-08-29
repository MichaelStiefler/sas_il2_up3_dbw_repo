package com.maddox.rts;

import com.maddox.rts.net.NetEmptyAddress;
import com.maddox.rts.net.NetEmptySocket;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;

public class NetChannelInStream extends NetChannel
  implements NetChannelStream, MsgTimeOutListener
{
  private static final boolean DEBUG = false;
  private boolean bDestroyGo = false;
  InputStream inStream;
  DataInputStream inData;
  NetMsgInput packet = new NetMsgInput();
  byte[] buf = new byte[2048];
  long lastPacketTime;
  int receiveGMsgSequenceNum_ = 0;
  boolean bGameTime = false;
  boolean bPause = false;
  boolean bQueueSync = false;
  int ng = 0;
  private static SyncMessage syncMessage;

  public void destroy()
  {
    if ((this.jdField_state_of_type_Int & 0x80000000) != 0) return;

    NetObj localNetObj1 = (NetObj)NetEnv.cur().jdField_objects_of_type_ComMaddoxUtilHashMapInt.get(9);
    if ((localNetObj1 != null) && (!this.bDestroyGo)) {
      this.bDestroyGo = true;
      localNetObj1.msgNetDelChannel(this);
      this.bDestroyGo = false;
      if ((this.jdField_state_of_type_Int & 0x80000000) != 0) return;
    }
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
      NetObj localNetObj2 = (NetObj)((HashMapIntEntry)localObject2).getValue();
      if (localNetObj2.isCommon())
        MsgNet.postRealDelChannel(localNetObj2, this);
      localObject2 = ((HashMapInt)localObject1).nextEntry((HashMapIntEntry)localObject2);
    }
    clearReceivedGMsgs();
    if (this.inStream != null) {
      try {
        this.inStream.close(); } catch (Exception localException) {
      }
      this.inStream = null;
    }
  }

  public boolean isGameTime()
  {
    return this.bGameTime;
  }
  public void setGameTime() { if (this.bGameTime) return;
    this.bGameTime = true;
    this.lastPacketTime = Time.current();
    MsgTimeOut.post(Time.current(), this, null); }

  public boolean isPause() {
    return this.bPause;
  }
  public void setPause(boolean paramBoolean) { if (this.bPause == paramBoolean) return;
    this.bPause = paramBoolean; }

  protected boolean isEnableFlushReceivedGuarantedMsgs()
  {
    return !this.bPause;
  }

  protected boolean update() {
    if (this.jdField_state_of_type_Int < 0) return false;
    if (this.bGameTime) return true;
    return _update();
  }

  public void msgTimeOut(Object paramObject) {
    if (!_update()) return;
    MsgTimeOut.post(this.lastPacketTime, this, null);
  }

  private boolean _update() {
    if (this.jdField_state_of_type_Int < 0) return false;

    if (this.bPause) return true;

    if (this.bQueueSync) {
      if (this.receiveGMsgs.size() == 0)
        this.bQueueSync = false;
      else
        flushReceivedGuarantedMsgs();
      return true;
    }

    int i = 0;
    try {
      do {
        if (!this.bGameTime)
          this.lastPacketTime = Time.currentReal();
        else if (this.lastPacketTime > Time.current())
            break;
        if (this.packet.available() > 0)
        {
          Object localObject;
          if (this.ng == 0) {
            int j = this.packet.readUnsignedShort();
            localObject = this.packet.buf;
            int n = this.packet.pos - 2;
            int i1 = this.packet.available() + 2;
            localObject[(n + 0)] = 28; localObject[(n + 1)] = -63;
            int i2 = CRC16.checksum(0, localObject, n, i1);
            if (j != i2) {
              i = 0;
              throw new Exception("bad packet CRC");
            }
            this.ng = this.packet.readUnsignedByte();
          }
          boolean bool = this.bGameTime;
          while (this.ng > 0) {
            localObject = getMessage(this.packet);
            if (syncMessage == NetChannel.getMessageObj)
              this.bQueueSync = true;
            this.receiveGMsgSequenceNum_ = (this.receiveGMsgSequenceNum_ + 1 & 0xFFFF);
            receivedGuarantedMsg((NetMsgInput)localObject, this.receiveGMsgSequenceNum_);
            this.ng -= 1;
            if ((this.bPause) || (bool != this.bGameTime)) {
              if (this.ng == 0)
              {
                while (this.packet.available() > 0)
                  this.packet.readByte();
              }
              return true;
            }
          }
          while (this.packet.available() > 0) {
            localObject = getMessage(this.packet, this.lastPacketTime);
            if (localObject != null) {
              statIn(true, NetChannel.getMessageObj, (NetMsgInput)localObject);
              if (!this.bGameTime) MsgNet.postReal(NetChannel.getMessageTime, NetChannel.getMessageObj, (NetMsgInput)localObject); else
                MsgNet.postGame(NetChannel.getMessageTime, NetChannel.getMessageObj, (NetMsgInput)localObject);
            }
          }
          i = 1;
          this.packet.setData(this, false, null, 0, 0);
        }
        if (this.inData.available() == 0) {
          if (i != 0) break;
          destroy();
          return false;
        }

        int k = this.inData.readUnsignedShort();
        int m = this.inData.readUnsignedShort();
        this.inData.read(this.buf, 0, m);
        this.packet.setData(this, false, this.buf, 0, m);
        if (!this.bGameTime) {
          break;
        }
        this.lastPacketTime += k;

        if (this.bPause) break; 
      }while (!this.bQueueSync);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      destroy(localException.toString());
      return false;
    }
    return true;
  }
  protected void postReceivedGMsg(long paramLong, NetObj paramNetObj, NetMsgInput paramNetMsgInput) {
    statIn(false, paramNetObj, paramNetMsgInput);
    int i = !this.bGameTime ? 1 : 0;
    if (((paramNetObj instanceof NetChannelCallbackStream)) && (!((NetChannelCallbackStream)paramNetObj).netChannelCallback(this, paramNetMsgInput)))
    {
      return;
    }if (i != 0) MsgNet.postReal(Time.currentReal(), paramNetObj, paramNetMsgInput); else
      MsgNet.postGame(Time.current(), paramNetObj, paramNetMsgInput);
  }

  public NetChannelInStream(InputStream paramInputStream, int paramInt) {
    this.inStream = paramInputStream;
    this.inData = new DataInputStream(paramInputStream);
    this.flags = paramInt;
    this.id = RTSConf.cur.netEnv.nextIdChannel(true);
    this.remoteId = 0;
    this.socket = new NetEmptySocket();
    this.remoteAddress = new NetEmptyAddress();
    this.remotePort = 0;
    this.lastPacketTime = Time.currentReal();
    this.packet.setData(this, false, null, 0, 0);
    this.jdField_state_of_type_Int = 1;
  }

  protected void putMessageSpawn(NetMsgSpawn paramNetMsgSpawn) throws IOException
  {
    if ((paramNetMsgSpawn.jdField__sender_of_type_ComMaddoxRtsNetObj instanceof NetChannelCallbackStream)) {
      ((NetChannelCallbackStream)paramNetMsgSpawn.jdField__sender_of_type_ComMaddoxRtsNetObj).netChannelCallback(this, paramNetMsgSpawn);
      return;
    }
    throw new NetException("putMessageSpawn NOT supported");
  }

  protected void putMessageDestroy(NetMsgDestroy paramNetMsgDestroy) throws IOException {
    if ((paramNetMsgDestroy.jdField__sender_of_type_ComMaddoxRtsNetObj instanceof NetChannelCallbackStream)) {
      ((NetChannelCallbackStream)paramNetMsgDestroy.jdField__sender_of_type_ComMaddoxRtsNetObj).netChannelCallback(this, paramNetMsgDestroy);
      return;
    }
    throw new NetException("putMessageDestroy NOT supported");
  }

  protected void putMessage(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    if ((paramNetMsgGuaranted.jdField__sender_of_type_ComMaddoxRtsNetObj instanceof NetChannelCallbackStream)) {
      ((NetChannelCallbackStream)paramNetMsgGuaranted.jdField__sender_of_type_ComMaddoxRtsNetObj).netChannelCallback(this, paramNetMsgGuaranted);
      return;
    }
    throw new NetException("putMessage NOT supported");
  }

  public void startSortGuaranted() {
    throw new NetException("startSortGuaranted NOT supported");
  }

  public void stopSortGuaranted() throws IOException {
    throw new NetException("stopSortGuaranted NOT supported");
  }

  protected void putMessage(NetMsgFiltered paramNetMsgFiltered) throws IOException {
    throw new NetException("putMessage NOT supported");
  }
  public int gSendQueueLenght() { return 0; } 
  public int gSendQueueSize() { return 0; } 
  protected boolean sendPacket(NetMsgOutput paramNetMsgOutput, NetPacket paramNetPacket) {
    return false;
  }
  protected boolean receivePacket(NetMsgInput paramNetMsgInput, long paramLong) throws IOException {
    return true;
  }
  public int ping() {
    return 0;
  }
  public int pingTo() { return 0; } 
  public int getMaxTimeout() {
    return 1000;
  }
  public void setMaxTimeout(int paramInt) {
  }
  public int getCurTimeout() { return 0; } 
  public boolean isRequeredSendPacket(long paramLong) { return false; } 
  public double getMaxSpeed() {
    return this.maxSendSpeed;
  }

  public void setMaxSpeed(double paramDouble) {
  }

  public static void sendSyncMsg(NetChannel paramNetChannel) {
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      syncMessage.postTo(paramNetChannel, localNetMsgGuaranted);
    }
    catch (Exception localException)
    {
    }
  }

  protected static void classInit()
  {
    if (syncMessage == null)
      syncMessage = new SyncMessage(8);
  }

  static class SyncMessage extends NetObj
  {
    public SyncMessage(int paramInt)
    {
      super(paramInt);
    }
  }
}