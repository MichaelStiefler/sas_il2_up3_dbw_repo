package com.maddox.rts;

import com.maddox.il2.game.Mission;
import com.maddox.sound.AudioDevice;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

public class NetChannel
  implements Destroy
{
  private static final boolean DEBUG = false;
  public static boolean bCheckServerTimeSpeed = true;
  public static boolean bCheckClientTimeSpeed = false;
  public static int checkTimeSpeedInterval = 17;
  public static double checkTimeSpeedDifferense = 0.2D;
  protected int id;
  protected int remoteId;
  protected NetSocket socket;
  protected NetAddress remoteAddress;
  protected int remotePort;
  protected HashMapInt objects = new HashMapInt();

  protected HashMapExt mirrored = new HashMapExt();

  protected ArrayList filters = new ArrayList();
  public static final int REAL_TIME = 1;
  public static final int PUBLIC = 2;
  public static final int GLOBAL = 4;
  protected int flags;
  public static final int STATE_DESTROYED = -2147483648;
  public static final int STATE_DO_DESTROY = 1073741824;
  public static final int STATE_READY = 0;
  public static final int STATE_INITMASK = 1073741823;
  public int userState = -1;
  protected int state;
  private int initStamp;
  private boolean bSortGuaranted = false;
  public NetChannelStat stat;
  private long timeDestroyed;
  protected static final int MESSAGE_SEQUENCE_FULL = 65535;
  private static final int MESSAGE_SEQUENCE_FRAME = 1023;
  private static final int PACKET_SEQUENCE_FULL = 16383;
  private static NetMsgOutput _tmpOut = new NetMsgOutput();

  private ArrayList holdGMsgs = new ArrayList();

  protected NetChannelArrayList sendGMsgs = new NetChannelArrayList();

  private int sendGMsgSequenceNum = 0;

  protected HashMapInt receiveGMsgs = new HashMapInt();

  private int receiveGMsgSequenceNum = 0;
  private int receiveGMsgSequenceNumPosted = 0;

  private long lastTimeNakMessageSend = 0L;
  private static int firstIndxSendGMsgs;
  private static int sequenceNumSendGMsgs;
  protected static int guarantedSizeMsgs;
  private HashMapExt filteredTickMsgs = new HashMapExt();

  protected static ArrayList filteredSortMsgs = new ArrayList();
  private static int filteredSizeMsgs;
  protected static int filteredMinSizeMsgs;
  public byte[] crcInit = { 65, 3 };

  protected int sendSequenceNum = 0;

  private int receiveSequenceNum = 0;

  private int receiveSequenceMask = 1;
  protected static long getMessageTime;
  protected static NetObj getMessageObj;
  protected static int getMessageObjIndex;
  private long lastCheckTimeSpeed = 0L;
  private NetConnect connect;
  private String diagnosticMessage = "";

  private static NetChannelPriorComparator priorComparator = new NetChannelPriorComparator();
  private static NetChannelTimeComparator timeComparator = new NetChannelTimeComparator();
  public int statNumSendGMsgs;
  public int statSizeSendGMsgs;
  public int statNumSendFMsgs;
  public int statSizeSendFMsgs;
  public int statHSizeSendFMsgs;
  public int statNumFilteredMsgs;
  public int statSizeFilteredMsgs;
  public int statNumReseivedMsgs;
  public int statSizeReseivedMsgs;
  public int statHSizeReseivedMsgs;
  private static final int TIME_OFFSET_SUM = 256;
  private static final int TIME_PING_SUM_START = 4;
  private static final int TIME_PING_SUM = 2000;
  protected static final double MIN_SPEED_SEND = 0.1D;
  protected static final double MIN_TIME_SEND = 10.0D;
  protected static final double MIN_LEN_SEND = 256.0D;
  protected double maxSendSpeed;
  protected double maxChSendSpeed;
  NetChannelCycleHistory sendHistory = new NetChannelCycleHistory(64);

  private int maxTimeout = 131071;
  protected long lastPacketSendTime;
  protected long nextPacketSendTime;
  private int ping;
  private int pingTo;
  private double pingToSpeed;
  private long lastDownSendTime;
  private double lastDownSendSpeed;
  private double curPacketSendSpeed;
  private long lastPacketReceiveTime;
  private long lastPacketOkReceiveTime;
  private int lastPacketReceiveSequenceNum;
  private long lastPacketReceiveRemoteTime;
  private long remoteClockOffsetSum;
  private int receiveCountPackets = 0;
  private int pingSum;
  private int pingToSum;
  private int countPingSum;
  private boolean bCheckTimeSpeed = false;
  private long[] checkT = new long[32];
  private long[] checkR = new long[32];
  private int checkI = -1;
  private int checkC = 0;

  public byte[] swTbl = null;
  private static ChannelObj channelObj;
  private static SpawnMessage spawnMessage;
  private static DestroyMessage destroyMessage;
  private static AskMessage askMessage;
  private NetMsgFiltered askMessageOut = new NetMsgFiltered();
  private static NakMessage nakMessage;
  private NetMsgFiltered nakMessageOut = new NetMsgFiltered();

  private static boolean bClassInited = false;

  public int id()
  {
    return this.id;
  }

  public int remoteId()
  {
    return this.remoteId;
  }

  public NetSocket socket()
  {
    return this.socket;
  }

  public NetAddress remoteAddress()
  {
    return this.remoteAddress;
  }

  public int remotePort()
  {
    return this.remotePort;
  }

  public NetObj getMirror(int paramInt)
  {
    return (NetObj)(NetObj)this.objects.get(paramInt);
  }

  public boolean isInitRemote()
  {
    return (this.id & 0x1) == 1;
  }

  public boolean isMirrored(NetObj paramNetObj) {
    return this.mirrored.containsKey(paramNetObj);
  }

  public void setMirrored(NetObj paramNetObj)
  {
    if (!this.mirrored.containsKey(paramNetObj)) {
      this.mirrored.put(paramNetObj, null);
      paramNetObj.countMirrors += 1;
    }
  }

  public boolean isRealTime()
  {
    return (this.flags & 0x1) != 0;
  }

  public boolean isPublic()
  {
    return (this.flags & 0x2) != 0;
  }

  public boolean isGlobal()
  {
    return (this.flags & 0x4) != 0;
  }

  public void setInitStamp(int paramInt)
  {
    this.initStamp = paramInt;
  }
  public int getInitStamp() {
    return this.initStamp;
  }

  public int state() {
    return this.state;
  }

  public void setStateInit(int paramInt)
  {
    this.state = (this.state & 0xC0000000 | paramInt & 0x3FFFFFFF);
  }

  public boolean isReady()
  {
    return this.state == 0;
  }

  public boolean isIniting()
  {
    return (this.state & 0x3FFFFFFF) != 0;
  }

  public boolean isDestroyed()
  {
    return this.state < 0;
  }

  public boolean isDestroying()
  {
    return (this.state & 0xC0000000) != 0;
  }

  public boolean isSortGuaranted()
  {
    return this.bSortGuaranted;
  }
  public void startSortGuaranted() {
    this.bSortGuaranted = true;
  }

  public List filters()
  {
    return this.filters;
  }

  public void filterAdd(NetFilter paramNetFilter) {
    int i = this.filters.size();
    for (int j = 0; j < i; j++) {
      NetFilter localNetFilter = (NetFilter)this.filters.get(j);
      if (!localNetFilter.filterEnableAdd(this, paramNetFilter))
        return;
    }
    this.filters.add(paramNetFilter);
  }

  public void filterRemove(NetFilter paramNetFilter)
  {
    int i = this.filters.indexOf(this);
    if (i >= 0)
      this.filters.remove(i);
  }

  protected void statIn(boolean paramBoolean, NetObj paramNetObj, NetMsgInput paramNetMsgInput)
  {
    if (this.stat == null) return;
    int i = 0;
    int j = 0;
    int k = 0;
    if (paramNetMsgInput.buf != null) {
      k = paramNetMsgInput.available();
      i = k > 0 ? paramNetMsgInput.buf[0] : 0;
      j = k > 1 ? paramNetMsgInput.buf[1] : 0;
    }
    try {
      this.stat.inc(this, paramNetObj, paramBoolean, false, k, i, j); } catch (Exception localException) {
      printDebug(localException);
    }
  }
  protected void statOut(boolean paramBoolean, NetObj paramNetObj, NetMsgOutput paramNetMsgOutput) {
    if (this.stat == null) return;
    int i = 0;
    int j = 0;
    int k = 0;
    if (paramNetMsgOutput.buf != null) {
      k = paramNetMsgOutput.size();
      i = k > 0 ? paramNetMsgOutput.buf[0] : 0;
      j = k > 1 ? paramNetMsgOutput.buf[1] : 0;
    }
    try {
      this.stat.inc(this, paramNetObj, paramBoolean, true, k, i, j); } catch (Exception localException) {
      printDebug(localException);
    }
  }

  public void destroy(String paramString)
  {
    if ((this.state & 0xC0000000) != 0) return;
    this.diagnosticMessage = paramString;
    destroy();
  }

  public void destroy()
  {
    if ((this.state & 0xC0000000) != 0) return;
    this.timeDestroyed = (Time.currentReal() + 1000L);
    this.state |= 1073741824;
    if ((this.state & 0x3FFFFFFF) == 0) {
      this.connect.channelDestroying(this, this.diagnosticMessage);
    }

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

    channelObj.doDestroy(this);
  }

  protected boolean update()
  {
    if (this.state < 0) return false;
    if (((this.state & 0x40000000) != 0) && (Time.currentReal() > this.timeDestroyed)) {
      if ((this.state & 0x3FFFFFFF) != 0) {
        this.connect.channelNotCreated(this, this.diagnosticMessage);
        if ((NetEnv.cur().control != null) && ((NetEnv.cur().control instanceof NetControlLock)))
        {
          NetControlLock localNetControlLock = (NetControlLock)NetEnv.cur().control;
          if (localNetControlLock.channel() == this)
            localNetControlLock.destroy();
        }
      }
      this.state = -2147483648;
      clearSortGuaranted();
      clearSendGMsgs();
      clearReceivedGMsgs();
      clearSendFMsgs();
      return false;
    }
    flushReceivedGuarantedMsgs();
    if (isTimeout())
      destroy("Timeout.");
    return true;
  }

  private static boolean winLT(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 == paramInt2)
      return false;
    if (paramInt1 > paramInt2)
      return paramInt1 - paramInt2 < paramInt3 / 2;
    return paramInt1 + (paramInt3 - paramInt2 + 1) < paramInt3 / 2;
  }

  private static boolean winDownDelta(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt3 <= paramInt1) paramInt1 -= paramInt3; else
      paramInt1 = paramInt4 - (paramInt3 - paramInt1) + 1;
    if (paramInt2 == paramInt1)
      return true;
    return winLT(paramInt2, paramInt1, paramInt4);
  }

  private static boolean winUpDelta(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt3 <= paramInt2) paramInt2 -= paramInt3; else
      paramInt2 = paramInt4 - (paramInt3 - paramInt2) + 1;
    if (paramInt2 == paramInt1)
      return true;
    return winLT(paramInt1, paramInt2, paramInt4);
  }

  private static int winDeltaLT(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 <= paramInt1) return paramInt1 - paramInt2;
    return paramInt1 + (paramInt3 - paramInt2 + 1);
  }

  protected void putMessageSpawn(NetMsgSpawn paramNetMsgSpawn)
    throws IOException
  {
    if (this.state < 0)
      throw new NetException("Channel is destroyed");
    if (paramNetMsgSpawn.size() > 255)
      throw new IOException("Output message is very long");
    if ((this.state & 0x40000000) != 0) {
      throw new NetException("Channel is closed for spawning objects");
    }
    if ((this.bSortGuaranted) && (!isReferenceOk(paramNetMsgSpawn))) {
      holdGMsg(paramNetMsgSpawn);
      return;
    }

    NetChannelGMsgOutput localNetChannelGMsgOutput = setGMsg(paramNetMsgSpawn, 3);
    this.mirrored.put(paramNetMsgSpawn._sender, null);
    paramNetMsgSpawn._sender.countMirrors += 1;
  }

  protected void putMessageDestroy(NetMsgDestroy paramNetMsgDestroy) throws IOException {
    if (this.state < 0)
      throw new NetException("Channel is destroyed");
    if (paramNetMsgDestroy.size() > 255) {
      throw new IOException("Output message is very long");
    }
    if ((this.bSortGuaranted) && (!isReferenceOk(paramNetMsgDestroy))) {
      holdGMsg(paramNetMsgDestroy);
      return;
    }

    NetChannelGMsgOutput localNetChannelGMsgOutput = setGMsg(paramNetMsgDestroy, 4);
    this.mirrored.remove(paramNetMsgDestroy._sender);
    paramNetMsgDestroy._sender.countMirrors -= 1;
  }

  protected void putMessage(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    if (this.state < 0)
      throw new NetException("Channel is destroyed");
    if (paramNetMsgGuaranted.size() > 255) {
      throw new IOException("Output message is very long");
    }
    if ((this.bSortGuaranted) && (!isReferenceOk(paramNetMsgGuaranted))) {
      holdGMsg(paramNetMsgGuaranted);
      return;
    }

    int i = getIndx(paramNetMsgGuaranted._sender);
    if (i == -1) {
      throw new NetException("Put Guaranted message to NOT mirrored object [" + paramNetMsgGuaranted._sender + "] (" + id() + ")");
    }
    setGMsg(paramNetMsgGuaranted, i);
  }

  private NetChannelGMsgOutput setGMsg(NetMsgGuaranted paramNetMsgGuaranted, int paramInt) throws IOException {
    List localList = paramNetMsgGuaranted.objects();
    byte[] arrayOfByte = null;
    if (localList != null) {
      int i = localList.size();
      arrayOfByte = new byte[2 * i];
      _tmpOut.buf = arrayOfByte; _tmpOut.count = 0;
      for (int j = i - 1; j >= 0; j--) {
        NetObj localNetObj = (NetObj)localList.get(j);
        int k = getIndx(localNetObj);
        if (k == -1) {
          throw new NetException("Put Guaranted message referenced to NOT mirrored object [" + paramNetMsgGuaranted._sender + "] -> [" + localNetObj + "] (" + id() + ")");
        }
        _tmpOut.writeShort(k);
      }
    }
    NetChannelGMsgOutput localNetChannelGMsgOutput = new NetChannelGMsgOutput();
    this.sendGMsgSequenceNum = (this.sendGMsgSequenceNum + 1 & 0xFFFF);
    localNetChannelGMsgOutput.sequenceNum = this.sendGMsgSequenceNum;
    localNetChannelGMsgOutput.objIndex = paramInt;
    localNetChannelGMsgOutput.iObjects = arrayOfByte;
    localNetChannelGMsgOutput.timeLastSend = 0L;
    localNetChannelGMsgOutput.msg = paramNetMsgGuaranted;
    this.sendGMsgs.add(localNetChannelGMsgOutput);
    paramNetMsgGuaranted.lockInc();
    return localNetChannelGMsgOutput;
  }

  private boolean isReferenceOk(NetMsgGuaranted paramNetMsgGuaranted)
  {
    if ((!(paramNetMsgGuaranted instanceof NetMsgSpawn)) && (!(paramNetMsgGuaranted instanceof NetMsgDestroy)) && 
      (getIndx(paramNetMsgGuaranted._sender) == -1)) {
      return false;
    }
    List localList = paramNetMsgGuaranted.objects();
    if (localList == null) return true;
    int i = localList.size();
    for (int j = 0; j < i; j++) {
      NetObj localNetObj = (NetObj)localList.get(j);
      if (getIndx(localNetObj) == -1)
        return false;
    }
    return true;
  }
  private void holdGMsg(NetMsgGuaranted paramNetMsgGuaranted) {
    this.holdGMsgs.add(paramNetMsgGuaranted);
    paramNetMsgGuaranted.lockInc();
  }
  private void flushHoldedGMsgs() throws IOException {
    int i = 1;
    while (i != 0) {
      i = 0;
      int j = this.holdGMsgs.size();
      for (int k = 0; k < j; k++) {
        NetMsgGuaranted localNetMsgGuaranted = (NetMsgGuaranted)this.holdGMsgs.get(k);
        if (isReferenceOk(localNetMsgGuaranted)) {
          if ((localNetMsgGuaranted instanceof NetMsgSpawn)) putMessageSpawn((NetMsgSpawn)localNetMsgGuaranted);
          else if ((localNetMsgGuaranted instanceof NetMsgDestroy)) putMessageDestroy((NetMsgDestroy)localNetMsgGuaranted); else
            putMessage(localNetMsgGuaranted);
          localNetMsgGuaranted.lockDec();
          i = 1;
          this.holdGMsgs.remove(k);
          k--; j--;
        }
      }
    }
  }

  public void stopSortGuaranted() throws IOException {
    if (!this.bSortGuaranted) return;
    flushHoldedGMsgs();
    this.bSortGuaranted = false;
    int i = this.holdGMsgs.size();
    if (i > 0) {
      System.err.println("Channel '" + this.id + "' cycled guaranted messages dump:");
      for (int j = 0; j < i; j++) {
        NetMsgGuaranted localNetMsgGuaranted = (NetMsgGuaranted)this.holdGMsgs.get(j);
        localNetMsgGuaranted.lockDec();
        if (localNetMsgGuaranted.isRequiredAsk())
          MsgNetAskNak.postReal(Time.currentReal(), localNetMsgGuaranted._sender, false, localNetMsgGuaranted, this);
        System.err.println(" " + localNetMsgGuaranted.toString() + " (" + localNetMsgGuaranted._sender.toString() + ")");
      }
      this.holdGMsgs.clear();
      throw new IOException("Cycled guaranted messages");
    }
  }

  protected void clearSortGuaranted() {
    int i = this.holdGMsgs.size();
    for (int j = 0; j < i; j++) {
      NetMsgGuaranted localNetMsgGuaranted = (NetMsgGuaranted)this.holdGMsgs.get(j);
      localNetMsgGuaranted.lockDec();
      if (localNetMsgGuaranted.isRequiredAsk())
        MsgNetAskNak.postReal(Time.currentReal(), localNetMsgGuaranted._sender, false, localNetMsgGuaranted, this);
    }
    this.holdGMsgs.clear();
  }

  protected void putMessage(NetMsgFiltered paramNetMsgFiltered) throws IOException
  {
    if (this.state < 0)
      throw new NetException("Channel is destroyed");
    if (paramNetMsgFiltered.size() > 255) {
      throw new IOException("Output message is very long");
    }
    if (getIndx(paramNetMsgFiltered._sender) == -1) {
      return;
    }
    List localList = paramNetMsgFiltered.objects();
    if (localList != null) {
      for (int i = localList.size() - 1; i >= 0; i--) {
        int j = getIndx((NetObj)localList.get(i));
        if (j == -1) {
          return;
        }

      }

    }

    this.filteredTickMsgs.put(paramNetMsgFiltered, this);
    paramNetMsgFiltered.lockInc();
  }

  private int getIndx(NetObj paramNetObj) {
    if (paramNetObj == null) return 0;
    int i = paramNetObj.idLocal & 0x7FFF;
    if (paramNetObj.isMirror()) {
      if (paramNetObj.masterChannel == this) i = paramNetObj.idRemote; else
        i |= 32768;
    } else if (!paramNetObj.isCommon()) {
      i |= 32768;
    }
    if (((i & 0x8000) != 0) && (!this.mirrored.containsKey(paramNetObj)))
      return -1;
    return i;
  }

  protected boolean unLockMessage(NetMsgFiltered paramNetMsgFiltered)
  {
    if (this.filteredTickMsgs.remove(paramNetMsgFiltered) != null)
      paramNetMsgFiltered.lockDec();
    return !paramNetMsgFiltered.isLocked();
  }

  public int gSendQueueLenght()
  {
    return this.sendGMsgs.size();
  }
  public int gSendQueueSize() { int i = 0;
    int j = this.sendGMsgs.size();
    for (int k = 0; k < j; k++) {
      NetChannelGMsgOutput localNetChannelGMsgOutput = (NetChannelGMsgOutput)this.sendGMsgs.get(k);
      i += localNetChannelGMsgOutput.msg.size();
    }
    return i;
  }

  private void askMessageReceive(int paramInt)
  {
    int i = this.sendGMsgs.size();
    if (i == 0)
      return;
    if (i > 1023)
      i = 1023;
    NetChannelGMsgOutput localNetChannelGMsgOutput = (NetChannelGMsgOutput)this.sendGMsgs.get(0);
    if (winLT(localNetChannelGMsgOutput.sequenceNum, paramInt, 65535)) return;
    localNetChannelGMsgOutput = (NetChannelGMsgOutput)this.sendGMsgs.get(i - 1);
    if (winLT(paramInt, localNetChannelGMsgOutput.sequenceNum, 65535)) {
      System.err.println("Channel '" + this.id + "' reseived ask for NOT sended message " + paramInt + " " + localNetChannelGMsgOutput.sequenceNum);

      return;
    }
    int j = 0;
    while (j < i) {
      localNetChannelGMsgOutput = (NetChannelGMsgOutput)this.sendGMsgs.get(j++);
      NetMsgGuaranted localNetMsgGuaranted = localNetChannelGMsgOutput.msg;
      localNetMsgGuaranted.lockDec();
      if (localNetMsgGuaranted.isRequiredAsk())
        MsgNetAskNak.postReal(Time.currentReal(), localNetMsgGuaranted._sender, true, localNetMsgGuaranted, this);
      localNetChannelGMsgOutput.msg = null;
      if (localNetChannelGMsgOutput.sequenceNum == paramInt) break;
    }
    this.sendGMsgs.removeRange(0, j);
  }

  protected void clearSendGMsgs() {
    int i = this.sendGMsgs.size();
    if (i == 0)
      return;
    for (int j = 0; j < i; j++) {
      NetChannelGMsgOutput localNetChannelGMsgOutput = (NetChannelGMsgOutput)this.sendGMsgs.get(j);
      NetMsgGuaranted localNetMsgGuaranted = localNetChannelGMsgOutput.msg;
      localNetMsgGuaranted.lockDec();
      if (localNetMsgGuaranted.isRequiredAsk())
        MsgNetAskNak.postReal(Time.currentReal(), localNetMsgGuaranted._sender, false, localNetMsgGuaranted, this);
    }
    this.sendGMsgs.clear();
  }

  private void nakMessageReceive(int paramInt1, int paramInt2) {
    int i = this.sendGMsgs.size();
    if (i == 0)
      return;
    if (i > 1023)
      i = 1023;
    int j = 0;
    NetChannelGMsgOutput localNetChannelGMsgOutput = (NetChannelGMsgOutput)this.sendGMsgs.get(j);
    while ((paramInt2 > 0) && 
      (winLT(localNetChannelGMsgOutput.sequenceNum, paramInt1, 65535)))
    {
      paramInt2--;
      paramInt1 = paramInt1 + 1 & 0xFFFF;
    }
    if (paramInt2 == 0) return;
    while (i > 0) {
      localNetChannelGMsgOutput = (NetChannelGMsgOutput)this.sendGMsgs.get(j);
      if (localNetChannelGMsgOutput.sequenceNum == paramInt1) break;
      i--; j++;
    }
    if (i == 0) return;
    while ((i-- > 0) && (paramInt2-- > 0)) {
      localNetChannelGMsgOutput = (NetChannelGMsgOutput)this.sendGMsgs.get(j++);
      localNetChannelGMsgOutput.timeLastSend = 0L;
    }
  }

  private void tryNakMessageSend()
  {
    if (this.receiveGMsgs.size() == 0) return;
    if (this.lastTimeNakMessageSend + ping() * 3 / 2 > Time.currentReal()) return;
    this.lastTimeNakMessageSend = Time.currentReal();
    int i = this.receiveGMsgSequenceNum + 1 & 0xFFFF;
    int j = 0;
    for (; (j < 256) && 
      (!this.receiveGMsgs.containsKey(i)); j++)
    {
      i = i + 1 & 0xFFFF;
    }
    j--;
    nakMessage.send(this.receiveGMsgSequenceNum + 1 & 0xFFFF, j, this);
  }

  protected boolean receivedGuarantedMsg(NetMsgInput paramNetMsgInput, int paramInt)
  {
    if (this.receiveGMsgSequenceNum == paramInt) return true;
    if (winLT(this.receiveGMsgSequenceNum, paramInt, 65535)) return true;
    NetChannelGMsgInput localNetChannelGMsgInput;
    if ((this.receiveGMsgSequenceNum + 1 & 0xFFFF) == paramInt) {
      this.receiveGMsgSequenceNum = paramInt;
      if ((getMessageObj != null) && ((this.receiveGMsgSequenceNumPosted + 1 & 0xFFFF) == paramInt))
      {
        this.receiveGMsgSequenceNumPosted = paramInt;
        postReceivedGMsg(Time.currentReal(), getMessageObj, paramNetMsgInput);
      } else {
        localNetChannelGMsgInput = new NetChannelGMsgInput();
        localNetChannelGMsgInput.sequenceNum = paramInt;
        localNetChannelGMsgInput.objIndex = getMessageObjIndex;
        localNetChannelGMsgInput.msg = paramNetMsgInput;
        this.receiveGMsgs.put(paramInt, localNetChannelGMsgInput);
      }
      if (getMessageObj == spawnMessage) {
        addWaitSpawn(paramInt, paramNetMsgInput);
      }
      flushReceivedGuarantedMsgs();
      return true;
    }
    if (!this.receiveGMsgs.containsKey(paramInt)) {
      localNetChannelGMsgInput = new NetChannelGMsgInput();
      localNetChannelGMsgInput.sequenceNum = paramInt;
      localNetChannelGMsgInput.objIndex = getMessageObjIndex;
      localNetChannelGMsgInput.msg = paramNetMsgInput;
      this.receiveGMsgs.put(paramInt, localNetChannelGMsgInput);
      if (getMessageObj == spawnMessage)
        addWaitSpawn(paramInt, paramNetMsgInput);
    }
    return false;
  }

  private void addWaitSpawn(int paramInt, NetMsgInput paramNetMsgInput) {
    paramInt = paramInt + 1 & 0xFFFF | 0x10000;
    try
    {
      int i = paramNetMsgInput.readInt();
      int j = paramNetMsgInput.readUnsignedShort();
      paramNetMsgInput.reset();
      NetChannelGMsgInput localNetChannelGMsgInput = new NetChannelGMsgInput();
      localNetChannelGMsgInput.sequenceNum = paramInt;
      localNetChannelGMsgInput.objIndex = j;
      localNetChannelGMsgInput.msg = null;
      this.receiveGMsgs.put(paramInt, localNetChannelGMsgInput); } catch (Exception localException) {
      printDebug(localException);
    }
  }
  private void removeWaitSpawn(int paramInt) {
    HashMapIntEntry localHashMapIntEntry = null;
    while ((localHashMapIntEntry = this.receiveGMsgs.nextEntry(localHashMapIntEntry)) != null) {
      NetChannelGMsgInput localNetChannelGMsgInput = (NetChannelGMsgInput)localHashMapIntEntry.getValue();
      if ((localNetChannelGMsgInput.objIndex == paramInt) && ((localNetChannelGMsgInput.sequenceNum & 0x10000) != 0)) {
        this.receiveGMsgs.remove(localNetChannelGMsgInput.sequenceNum);
        return;
      }
    }
  }

  protected boolean isEnableFlushReceivedGuarantedMsgs() {
    return true;
  }
  protected void flushReceivedGuarantedMsgs() { long l = Time.currentReal();
    while ((isEnableFlushReceivedGuarantedMsgs()) && (this.receiveGMsgs.size() > 0)) {
      NetChannelGMsgInput localNetChannelGMsgInput = (NetChannelGMsgInput)this.receiveGMsgs.get(this.receiveGMsgSequenceNumPosted + 1 & 0xFFFF | 0x10000);

      if (localNetChannelGMsgInput != null)
      {
        int i = localNetChannelGMsgInput.objIndex;
        NetObj localNetObj2 = (NetObj)this.objects.get(i);
        if ((localNetObj2 == null) && (isExistSpawnPosted(i)))
          break;
        this.receiveGMsgs.remove(localNetChannelGMsgInput.sequenceNum);
      }
      else {
        localNetChannelGMsgInput = (NetChannelGMsgInput)this.receiveGMsgs.get(this.receiveGMsgSequenceNumPosted + 1 & 0xFFFF);
        if (localNetChannelGMsgInput == null) break;
        NetObj localNetObj1 = null;
        int j = localNetChannelGMsgInput.objIndex;
        if ((j & 0x8000) != 0) {
          j &= -32769;
          localNetObj1 = (NetObj)this.objects.get(j);
          if ((localNetObj1 == null) && (isExistSpawnPosted(j)))
            break;
        } else {
          localNetObj1 = (NetObj)NetEnv.cur().objects.get(j);
        }
        this.receiveGMsgSequenceNumPosted = localNetChannelGMsgInput.sequenceNum;
        this.receiveGMsgs.remove(this.receiveGMsgSequenceNumPosted);
        if (localNetObj1 != null) {
          postReceivedGMsg(l, localNetObj1, localNetChannelGMsgInput.msg);
        }
        localNetChannelGMsgInput.msg = null;
      }
    } }

  protected void postReceivedGMsg(long paramLong, NetObj paramNetObj, NetMsgInput paramNetMsgInput) {
    statIn(false, paramNetObj, paramNetMsgInput);
    MsgNet.postReal(paramLong, paramNetObj, paramNetMsgInput);
  }
  protected void clearReceivedGMsgs() {
    this.receiveGMsgs.clear();
  }

  private boolean isExistSpawnPosted(int paramInt) {
    try {
      MessageQueue localMessageQueue = RTSConf.cur.queueRealTime;
      synchronized (localMessageQueue) {
        int i = 0;
        while (true) {
          Message localMessage = localMessageQueue.peekByIndex(i++);
          if (localMessage == null)
            break;
          if ((!(localMessage instanceof MsgNet)) || 
            (localMessage.listener() != spawnMessage) || 
            (!(localMessage.sender() instanceof NetMsgInput))) continue;
          NetMsgInput localNetMsgInput = (NetMsgInput)localMessage.sender();
          int j = localNetMsgInput.readInt();
          int k = localNetMsgInput.readUnsignedShort();
          localNetMsgInput.reset();
          if (paramInt == k)
            return true; 
        }
      }
    } catch (Exception localException) {
      printDebug(localException);
    }return false;
  }

  protected int computeSizeSendGMsgs(long paramLong)
  {
    int i = this.sendGMsgs.size();
    if (i == 0) return 0;
    if (i > 1023)
      i = 1023;
    long l = paramLong - 2 * ping();
    int j = 0;
    for (; j < i; j++) {
      NetChannelGMsgOutput localNetChannelGMsgOutput1 = (NetChannelGMsgOutput)this.sendGMsgs.get(j);
      if (l > localNetChannelGMsgOutput1.timeLastSend) {
        sequenceNumSendGMsgs = localNetChannelGMsgOutput1.sequenceNum;
        firstIndxSendGMsgs = j;
        break;
      }
    }
    if (j == i) return 0;
    if (i > j + 128) i = j + 128;
    int k = 0;
    for (; j < i; j++) {
      NetChannelGMsgOutput localNetChannelGMsgOutput2 = (NetChannelGMsgOutput)this.sendGMsgs.get(j);
      if (l < localNetChannelGMsgOutput2.timeLastSend) break;
      computeMessageLen(localNetChannelGMsgOutput2.msg);
      k += localNetChannelGMsgOutput2.msg._len;
    }
    return k;
  }

  protected int computeCountSendGMsgs(int paramInt) {
    int i = this.sendGMsgs.size();
    if (i > 1023)
      i = 1023;
    int j = firstIndxSendGMsgs;
    int k = 0;
    guarantedSizeMsgs = 0;
    while ((j < i) && (paramInt > 0)) {
      NetChannelGMsgOutput localNetChannelGMsgOutput = (NetChannelGMsgOutput)this.sendGMsgs.get(j++);
      paramInt -= localNetChannelGMsgOutput.msg._len;
      if ((paramInt < 0) && (k > 0)) break;
      guarantedSizeMsgs += localNetChannelGMsgOutput.msg._len;
      k++;
    }
    return k;
  }

  protected int computeSizeSendFMsgs(long paramLong)
  {
    filteredSizeMsgs = 0;
    filteredMinSizeMsgs = 0;
    Map.Entry localEntry = this.filteredTickMsgs.nextEntry(null);
    while (localEntry != null) {
      NetMsgFiltered localNetMsgFiltered = (NetMsgFiltered)localEntry.getKey();
      computeMessageLen(localNetMsgFiltered, paramLong);
      if (localNetMsgFiltered.prior > 1.0F) {
        localNetMsgFiltered._prior = localNetMsgFiltered.prior;
        filteredMinSizeMsgs += localNetMsgFiltered._len;
        filteredSizeMsgs += localNetMsgFiltered._len;
        filteredSortMsgs.add(localNetMsgFiltered);
      }
      else {
        int i = this.filters.size();
        if (i > 0) {
          float f1 = -1.0F;
          for (int j = 0; j < i; j++) {
            float f2 = ((NetFilter)this.filters.get(j)).filterNetMessage(this, localNetMsgFiltered);
            if (f2 > 1.0F) f2 = 1.0F;
            if (f2 <= f1) continue; f1 = f2;
          }
          if (f1 < 0.0F)
            localNetMsgFiltered._prior = (localNetMsgFiltered.prior + 0.5F * ((float)Math.random() - 0.5F));
          else
            localNetMsgFiltered._prior = (f1 + 0.2F * ((float)Math.random() - 0.5F));
        } else {
          localNetMsgFiltered._prior = (localNetMsgFiltered.prior + 0.5F * ((float)Math.random() - 0.5F));
        }
        if (localNetMsgFiltered._prior < 0.0F) localNetMsgFiltered._prior = 0.0F;
        if (localNetMsgFiltered._prior > 1.0F) localNetMsgFiltered._prior = 1.0F;
        filteredSizeMsgs += localNetMsgFiltered._len;
        filteredSortMsgs.add(localNetMsgFiltered);
      }
      localEntry = this.filteredTickMsgs.nextEntry(localEntry);
    }
    this.filteredTickMsgs.clear();

    return filteredSizeMsgs;
  }

  protected void clearSendFMsgs() {
    Map.Entry localEntry = this.filteredTickMsgs.nextEntry(null);
    while (localEntry != null) {
      NetMsgFiltered localNetMsgFiltered = (NetMsgFiltered)localEntry.getKey();
      localNetMsgFiltered.lockDec();
      localEntry = this.filteredTickMsgs.nextEntry(localEntry);
    }
    this.filteredTickMsgs.clear();
  }

  protected int fillFilteredArrayMessages(long paramLong, int paramInt)
  {
    if (filteredSizeMsgs > paramInt)
    {
      Collections.sort(filteredSortMsgs, priorComparator);

      for (i = filteredSortMsgs.size() - 1; (i >= 0) && (filteredSizeMsgs > paramInt); i--) {
        NetMsgFiltered localNetMsgFiltered = (NetMsgFiltered)filteredSortMsgs.get(i);
        localNetMsgFiltered.lockDec();
        filteredSizeMsgs -= localNetMsgFiltered._len;
        filteredSortMsgs.remove(i);
      }
    }
    if (filteredSizeMsgs == 0) {
      return 0;
    }

    Collections.sort(filteredSortMsgs, timeComparator);

    int i = this.filters.size();
    if (i > 0) {
      int j = filteredSortMsgs.size();
      for (int k = 0; k < i; k++) {
        NetFilter localNetFilter = (NetFilter)this.filters.get(k);
        for (int m = 0; m < j; m++) {
          localNetFilter.filterNetMessagePosting(this, (NetMsgFiltered)filteredSortMsgs.get(m));
        }
      }
    }
    return filteredSizeMsgs;
  }

  protected boolean sendPacket(NetMsgOutput paramNetMsgOutput, NetPacket paramNetPacket)
  {
    long l = Time.real();
    if (isTimeout()) { destroy("Timeout."); return false; }
    int i = 0;
    tryNakMessageSend();

    int j = getSendPacketLen(l);
    if (l >= this.lastPacketSendTime + this.maxTimeout / 4) {
      if (j < 20) j = 20;
      i = 1;
    }
    if (j <= 0) return false;
    int k = 6;
    if (isRealTime()) {
      k += 6;
    }
    j -= k;
    if (j <= 0) return false;

    int m = 0;
    int n = computeSizeSendGMsgs(l);
    if (n > 0) {
      j -= 3;
      if (this.swTbl != null)
        j--;
    }
    if (j <= 0) return false;

    int i1 = computeSizeSendFMsgs(l);
    if ((i1 + n == 0) && (i == 0)) return false;
    int i2;
    int i3;
    if (i1 + n > j) {
      i2 = i1;
      i3 = n;
      if (i3 > j) i3 = j;
      i2 = j - i3;
      if (i2 < filteredMinSizeMsgs) i2 = filteredMinSizeMsgs;
      i3 = j - i2;
      if (i3 < 0) {
        if (n > 0) {
          j += 3;
          if (this.swTbl != null)
            j++;
        }
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
    } else {
      m = computeCountSendGMsgs(n);
    }

    i1 = fillFilteredArrayMessages(l, i1);
    if ((i1 + n == 0) && (i == 0)) return false;
    try
    {
      paramNetMsgOutput.clear();

      paramNetMsgOutput.writeShort(this.remoteId);
      paramNetMsgOutput.writeByte(this.crcInit[0]);
      paramNetMsgOutput.writeByte(this.crcInit[1]);

      this.sendSequenceNum = (this.sendSequenceNum + 1 & 0x3FFF);
      if (m > 0) this.sendSequenceNum |= 32768;

      if (isRealTime())
      {
        paramNetMsgOutput.writeShort(this.sendSequenceNum | 0x4000);

        sendTime(paramNetMsgOutput, this.sendSequenceNum, l, k + i1 + n);
      } else {
        paramNetMsgOutput.writeShort(this.sendSequenceNum);

        sendTime(paramNetMsgOutput, this.sendSequenceNum, l, k + i1 + n);
      }

      if (m > 0) {
        paramNetMsgOutput.writeShort(sequenceNumSendGMsgs);
        paramNetMsgOutput.writeByte(m - 1);
        if (this.swTbl != null)
          paramNetMsgOutput.writeByte(0);
        i2 = paramNetMsgOutput.dataLength();
        i3 = firstIndxSendGMsgs;
        while (m-- > 0) {
          NetChannelGMsgOutput localNetChannelGMsgOutput = (NetChannelGMsgOutput)this.sendGMsgs.get(i3++);
          localNetChannelGMsgOutput.timeLastSend = l;
          putMessage(paramNetMsgOutput, localNetChannelGMsgOutput.objIndex, localNetChannelGMsgOutput.msg, localNetChannelGMsgOutput.iObjects);
        }
        if (this.swTbl != null) {
          int i4 = paramNetMsgOutput.dataLength();
          int i5 = i4 - i2;
          if (i5 > 0) {
            if (i5 > 255) i5 = 255;
            paramNetMsgOutput.data()[(i2 - 1)] = (byte)i5;
            cdata(paramNetMsgOutput.data(), i2, i5);
          }
        }

      }

      i2 = filteredSortMsgs.size();
      for (i3 = 0; i3 < i2; i3++) {
        NetMsgFiltered localNetMsgFiltered = (NetMsgFiltered)filteredSortMsgs.get(i3);
        putMessage(paramNetMsgOutput, l, localNetMsgFiltered);
        localNetMsgFiltered.lockDec();
      }
      filteredSortMsgs.clear();

      i3 = CRC16.checksum(0, paramNetMsgOutput.data(), 0, paramNetMsgOutput.dataLength());
      paramNetMsgOutput.data()[2] = (byte)(i3 >>> 8 & 0xFF);
      paramNetMsgOutput.data()[3] = (byte)(i3 & 0xFF);

      paramNetPacket.setLength(paramNetMsgOutput.dataLength());
      paramNetPacket.setAddress(this.remoteAddress);
      paramNetPacket.setPort(this.remotePort);
      this.socket.send(paramNetPacket); } catch (Exception localException) {
      printDebug(localException);
    }return false;
  }

  protected boolean receivePacket(NetMsgInput paramNetMsgInput, long paramLong)
    throws IOException
  {
    paramNetMsgInput.readUnsignedShort();
    int i = paramNetMsgInput.readUnsignedShort();
    byte[] arrayOfByte = paramNetMsgInput.buf;
    int j = paramNetMsgInput.pos - 4;
    int k = paramNetMsgInput.available() + 4;
    arrayOfByte[(j + 2)] = this.crcInit[0];
    arrayOfByte[(j + 3)] = this.crcInit[1];

    int m = CRC16.checksum(0, arrayOfByte, j, k);
    if (i != m) {
      return false;
    }
    if (isTimeout()) { destroy("Timeout."); return true; }
    i = paramNetMsgInput.readUnsignedShort();
    boolean bool = false;
    j = (i & 0x8000) != 0 ? 1 : 0;
    k = 1;
    m = (i & 0x4000) != 0 ? 1 : 0;
    i &= 16383;
    if (this.receiveSequenceNum == i) return true;
    if (winLT(this.receiveSequenceNum, i, 16383))
    {
      if (j == 0) return true;
      int n = winDeltaLT(this.receiveSequenceNum, i, 16383);
      if (n > 31) return true;
      if ((1 << n & this.receiveSequenceMask) != 0) return true;
      k = 0;
      bool = true;
    } else {
      while (this.receiveSequenceNum != i) {
        this.receiveSequenceMask <<= 1;
        this.receiveSequenceNum = (this.receiveSequenceNum + 1 & 0x3FFF);
      }
      this.receiveSequenceMask |= 1;
    }
    long l = receiveTime(paramLong, paramNetMsgInput, i, m, bool);
    if (j != 0) {
      i1 = paramNetMsgInput.readUnsignedShort();
      int i2 = paramNetMsgInput.readUnsignedByte() + 1;
      if (this.swTbl != null) {
        i3 = paramNetMsgInput.readUnsignedByte();
        cdata(paramNetMsgInput.buf, paramNetMsgInput.pos, i3);
      }
      int i3 = 0;
      while ((i2-- > 0) && (paramNetMsgInput.available() > 0)) {
        NetMsgInput localNetMsgInput2 = getMessage(paramNetMsgInput);
        if (receivedGuarantedMsg(localNetMsgInput2, i1)) i3 = 1;
        i1 = i1 + 1 & 0xFFFF;
      }
      if (i3 != 0) {
        askMessage.send(this.receiveGMsgSequenceNum, this);
      }
    }
    int i1 = 1;
    if (k != 0) {
      while (paramNetMsgInput.available() > 0) {
        NetMsgInput localNetMsgInput1 = getMessage(paramNetMsgInput, l);
        if (localNetMsgInput1 != null) {
          statIn(true, getMessageObj, localNetMsgInput1);
          MsgNet.postReal(getMessageTime, getMessageObj, localNetMsgInput1);
        } else {
          i1 = 0;
        }
      }
    }
    if (i1 != 0)
      this.lastPacketOkReceiveTime = this.lastPacketReceiveTime;
    if (this.checkC >= checkTimeSpeedInterval)
      destroy("Timeout .");
    return true;
  }

  protected void putMessage(NetMsgOutput paramNetMsgOutput, int paramInt, NetMsgGuaranted paramNetMsgGuaranted, byte[] paramArrayOfByte)
    throws IOException
  {
    statOut(false, paramNetMsgGuaranted._sender, paramNetMsgGuaranted);
    paramNetMsgOutput.writeShort(paramInt);
    paramNetMsgOutput.writeByte(paramNetMsgGuaranted.size());

    if (paramNetMsgGuaranted.dataLength() > 0)
      paramNetMsgOutput.write(paramNetMsgGuaranted.data(), 0, paramNetMsgGuaranted.dataLength());
    if (paramArrayOfByte != null) {
      paramNetMsgOutput.write(paramArrayOfByte, 0, paramArrayOfByte.length);
    }
    this.statNumSendGMsgs += 1;
    this.statSizeSendGMsgs += paramNetMsgGuaranted.size();
  }

  protected void putMessage(NetMsgOutput paramNetMsgOutput, long paramLong, NetMsgFiltered paramNetMsgFiltered) throws IOException {
    putMessage(paramNetMsgOutput, paramLong, paramNetMsgFiltered, paramNetMsgFiltered._time);
  }

  protected void putMessage(NetMsgOutput paramNetMsgOutput, long paramLong1, NetMsgFiltered paramNetMsgFiltered, long paramLong2) throws IOException {
    statOut(true, paramNetMsgFiltered._sender, paramNetMsgFiltered);
    paramNetMsgOutput.writeShort(getIndx(paramNetMsgFiltered._sender));
    this.statHSizeSendFMsgs += 2;
    int i = 0;
    int j = paramNetMsgFiltered.size();
    int k;
    int m;
    if (j < 32)
    {
      i = j;
      if ((paramNetMsgFiltered.isIncludeTime()) && (isRealTime())) {
        k = (int)(paramLong2 - paramLong1);
        m = 0;
        if (k < 0) { k = -k; m = 128; }
        if ((k & 0xFFFFFF80) == 0)
        {
          paramNetMsgOutput.writeByte(i | 0x40);
          paramNetMsgOutput.writeByte(m | k);
          this.statHSizeSendFMsgs += 2;
        } else if ((k & 0xFFFF8000) == 0)
        {
          paramNetMsgOutput.writeByte(i | 0x80);
          paramNetMsgOutput.writeByte(k >> 8 & 0x7F | m);
          paramNetMsgOutput.writeByte(k & 0xFF);
          this.statHSizeSendFMsgs += 3;
        }
        else {
          paramNetMsgOutput.writeByte(i | 0xC0);
          if (k > 8388607) k = 8388607;
          paramNetMsgOutput.writeByte(k >> 16 & 0x7F | m);
          paramNetMsgOutput.writeByte(k >> 8 & 0xFF);
          paramNetMsgOutput.writeByte(k & 0xFF);
          this.statHSizeSendFMsgs += 4;
        }
      } else {
        paramNetMsgOutput.writeByte(i);
        this.statHSizeSendFMsgs += 1;
      }
    }
    else {
      i = 32;
      if ((paramNetMsgFiltered.isIncludeTime()) && (isRealTime())) {
        k = (int)(paramLong2 - paramLong1);
        if (k < 0) { k = -k; i |= 16; }
        if ((k & 0xFFFFFFF0) == 0)
        {
          paramNetMsgOutput.writeByte(i | 0x40 | k & 0xF);
          paramNetMsgOutput.writeByte(j);
          this.statHSizeSendFMsgs += 2;
        } else if ((k & 0xFFFFF000) == 0)
        {
          paramNetMsgOutput.writeByte(i | 0x80 | k >> 8 & 0xF);
          paramNetMsgOutput.writeByte(j);
          paramNetMsgOutput.writeByte(k & 0xFF);
          this.statHSizeSendFMsgs += 3;
        }
        else {
          if (k > 1048575) k = 1048575;
          paramNetMsgOutput.writeByte(i | 0xC0 | k >> 16 & 0xF);
          paramNetMsgOutput.writeByte(j);
          paramNetMsgOutput.writeByte(k >> 8 & 0xFF);
          paramNetMsgOutput.writeByte(k & 0xFF);
          this.statHSizeSendFMsgs += 4;
        }
      } else {
        paramNetMsgOutput.writeByte(i);
        paramNetMsgOutput.writeByte(j);
        this.statHSizeSendFMsgs += 2;
      }
    }

    if (paramNetMsgFiltered.dataLength() > 0) {
      paramNetMsgOutput.write(paramNetMsgFiltered.data(), 0, paramNetMsgFiltered.dataLength());
    }
    List localList = paramNetMsgFiltered.objects();
    if (localList != null) {
      for (m = localList.size() - 1; m >= 0; m--) {
        paramNetMsgOutput.writeShort(getIndx((NetObj)localList.get(m)));
      }
    }
    this.statNumSendFMsgs += 1;
    this.statSizeSendFMsgs += paramNetMsgFiltered.size();
  }

  private void computeMessageLen(NetMsgGuaranted paramNetMsgGuaranted) {
    paramNetMsgGuaranted._len = (3 + paramNetMsgGuaranted.size());
  }

  protected void computeMessageLen(NetMsgFiltered paramNetMsgFiltered, long paramLong) {
    computeMessageLen(paramNetMsgFiltered, paramNetMsgFiltered._time, paramLong);
  }

  protected void computeMessageLen(NetMsgFiltered paramNetMsgFiltered, long paramLong1, long paramLong2) {
    int i = 3;
    int j = paramNetMsgFiltered.size();
    i += j;
    int k;
    if (j < 32)
    {
      if ((paramNetMsgFiltered.isIncludeTime()) && (isRealTime())) {
        k = (int)(paramLong1 - paramLong2);
        if (k < 0) k = -k;
        if ((k & 0xFFFFFF80) == 0) i++;
        else if ((k & 0xFFFF8000) == 0) i += 2; else
          i += 3;
      }
    }
    else {
      i++;
      if ((paramNetMsgFiltered.isIncludeTime()) && (isRealTime())) {
        k = (int)(paramLong1 - paramLong2);
        if (k < 0) k = -k;
        if ((k & 0xFFFFFFF0) == 0) i += 0;
        else if ((k & 0xFFFFF000) == 0) i++; else
          i += 2;
      }
    }
    paramNetMsgFiltered._len = i;
  }

  protected NetMsgInput getMessage(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    int i = paramNetMsgInput.readUnsignedShort();
    getMessageObjIndex = i;
    NetObj localNetObj = null;
    if ((i & 0x8000) != 0) {
      i &= -32769;
      localNetObj = (NetObj)this.objects.get(i);
    } else {
      localNetObj = (NetObj)NetEnv.cur().objects.get(i);
    }
    int j = paramNetMsgInput.readUnsignedByte();
    getMessageObj = localNetObj;
    this.statHSizeReseivedMsgs += 3;

    NetMsgInput localNetMsgInput = new NetMsgInput();
    if (j > 0) {
      byte[] arrayOfByte = new byte[j];
      paramNetMsgInput.read(arrayOfByte);
      localNetMsgInput.setData(this, true, arrayOfByte, 0, j);
      this.statSizeReseivedMsgs += j;
    } else {
      localNetMsgInput.setData(this, true, null, 0, 0);
    }
    this.statNumReseivedMsgs += 1;
    return localNetMsgInput;
  }
  protected NetMsgInput getMessage(NetMsgInput paramNetMsgInput, long paramLong) throws IOException {
    int i = paramNetMsgInput.readUnsignedShort();
    getMessageObjIndex = i;
    NetObj localNetObj = null;
    if ((i & 0x8000) != 0) {
      i &= -32769;
      localNetObj = (NetObj)this.objects.get(i);
    } else {
      localNetObj = (NetObj)NetEnv.cur().objects.get(i);
    }
    int j = paramNetMsgInput.readUnsignedByte();
    this.statHSizeReseivedMsgs += 3;
    int k = (j & 0xC0) != 0 ? 1 : 0;
    int m = (j & 0x20) == 0 ? 1 : 0;
    long l = paramLong;
    int n;
    int i1;
    int i2;
    int i3;
    if (m != 0) {
      n = j & 0x1F;
      if (k != 0) {
        i1 = j >> 6;
        i2 = paramNetMsgInput.readUnsignedByte();
        this.statHSizeReseivedMsgs += 1;
        i3 = (i2 & 0x80) != 0 ? 1 : 0;
        i2 &= 127;
        while (true) { i1--; if (i1 <= 0) break;
          i2 = i2 << 8 | paramNetMsgInput.readUnsignedByte();
          this.statHSizeReseivedMsgs += 1;
        }
        l += i2;
      }
    } else {
      n = paramNetMsgInput.readUnsignedByte();
      this.statHSizeReseivedMsgs += 1;
      if (k != 0) {
        i1 = j >> 6;
        i2 = j & 0xF;
        i3 = (j & 0x10) != 0 ? 1 : 0;
        while (true) { i1--; if (i1 <= 0) break;
          i2 = i2 << 8 | paramNetMsgInput.readUnsignedByte();
          this.statHSizeReseivedMsgs += 1;
        }
        l += i2;
      }
    }
    getMessageTime = l;
    getMessageObj = localNetObj;
    if (localNetObj != null) {
      NetMsgInput localNetMsgInput = new NetMsgInput();
      if (n > 0) {
        byte[] arrayOfByte = new byte[n];
        paramNetMsgInput.read(arrayOfByte);
        localNetMsgInput.setData(this, false, arrayOfByte, 0, n);
      } else {
        localNetMsgInput.setData(this, false, null, 0, 0);
      }
      this.statSizeReseivedMsgs += n;
      this.statNumReseivedMsgs += 1;
      return localNetMsgInput;
    }

    paramNetMsgInput.skipBytes(n);
    this.statSizeReseivedMsgs += n;
    this.statNumReseivedMsgs += 1;
    return null;
  }

  protected static void printDebug(Exception paramException)
  {
    System.out.println(paramException.getMessage());
    paramException.printStackTrace();
  }

  protected NetChannel(int paramInt1, int paramInt2, int paramInt3, NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt4, NetConnect paramNetConnect) {
    this.flags = paramInt1;
    this.id = paramInt2;
    this.remoteId = paramInt3;
    this.socket = paramNetSocket;
    this.remoteAddress = paramNetAddress;
    this.remotePort = paramInt4;
    if ((paramInt2 & 0x1) != 0) this.bCheckTimeSpeed = bCheckServerTimeSpeed; else
      this.bCheckTimeSpeed = bCheckClientTimeSpeed;
    this.state = 1;
    this.lastPacketReceiveTime = Time.real();
    setMaxSendSpeed(paramNetSocket.getMaxSpeed());
    this.connect = paramNetConnect;
    if (!isInitRemote())
      channelObj.doRequestCreating(this);
  }

  private void doCheckTimeSpeed()
  {
    try
    {
      if (isDestroying()) return;
      if ((this.state == 0) && (Mission.isPlaying())) {
        int i = AudioDevice.getControl(611);
        long l1 = Time.real();
        long l2 = l1 - this.lastCheckTimeSpeed;

        if ((i >= 0) && (this.lastCheckTimeSpeed != 0L) && (l2 > 400L) && (l2 < 800L)) {
          double d1 = i / 44100.0D;
          double d2 = l2 / 1000.0D;
          if (Math.abs(1.0D - d1 / d2) > 0.04D) {
            new MsgAction(64, 10.0D, this) {
              public void doAction(Object paramObject) { NetChannel localNetChannel = (NetChannel)paramObject;
                if (!localNetChannel.isDestroying())
                  localNetChannel.destroy("Timeout .");
              }
            };
            return;
          }
        }
        this.lastCheckTimeSpeed = l1;
      }
      new MsgAction(64, 0.5D, this) {
        public void doAction(Object paramObject) { NetChannel localNetChannel = (NetChannel)paramObject;
          localNetChannel.doCheckTimeSpeed(); } } ;
    } catch (Exception localException) {
      printDebug(localException);
    }
  }

  protected NetChannel()
  {
  }

  protected void controlStartInit()
  {
    created();
  }

  private void created() {
    this.connect.channelCreated(this);
  }

  protected void setMaxSendSpeed(double paramDouble)
  {
    this.maxSendSpeed = paramDouble;
    if ((this.id & 0x1) == 1)
      this.maxChSendSpeed = (2.0D * this.maxSendSpeed / 3.0D);
    else
      this.maxChSendSpeed = (this.maxSendSpeed / 3.0D);
  }

  public int ping()
  {
    return this.ping;
  }
  public int pingTo() { return this.pingTo; }

  public int getMaxTimeout() {
    return this.maxTimeout;
  }
  public void setMaxTimeout(int paramInt) {
    if (paramInt < 0) paramInt = 1000;
    if (paramInt > 131071) paramInt = 131071;
    if (this.maxTimeout != paramInt) {
      this.maxTimeout = paramInt;
      channelObj.doSetTimeout(this, paramInt);
    }
  }

  public int getCurTimeout() {
    return (int)(Time.real() - this.lastPacketReceiveTime);
  }
  public int getCurTimeoutOk() {
    return (int)(Time.real() - this.lastPacketOkReceiveTime);
  }

  private boolean isTimeout() {
    if (!isRealTime()) return false;

    return getCurTimeout() >= getMaxTimeout();
  }

  public boolean isRequeredSendPacket(long paramLong)
  {
    return paramLong > this.nextPacketSendTime;
  }

  protected int getSendPacketLen(long paramLong)
  {
    if (paramLong < this.nextPacketSendTime) {
      return 0;
    }
    if (this.pingToSpeed > 0.1D) {
      this.curPacketSendSpeed = this.sendHistory.speed(this.nextPacketSendTime - this.ping, this.nextPacketSendTime, this.maxChSendSpeed);
      this.curPacketSendSpeed /= (this.pingToSpeed + 1.0D);
      this.lastDownSendTime = paramLong;
      this.lastDownSendSpeed = this.curPacketSendSpeed;
    }
    else if (this.pingToSpeed < -0.1D) {
      this.lastDownSendTime = paramLong;
      this.lastDownSendSpeed = this.curPacketSendSpeed;
    }
    else if ((this.ping > 0) && (this.lastDownSendTime > 0L)) {
      this.curPacketSendSpeed = (this.maxChSendSpeed - (this.maxChSendSpeed - this.lastDownSendSpeed) * Math.exp(-(paramLong - this.lastDownSendTime) / this.ping));
      if (this.curPacketSendSpeed == 0.99D * this.maxChSendSpeed) this.lastDownSendTime = 0L; 
    }
    else
    {
      this.curPacketSendSpeed = this.maxChSendSpeed;
    }

    if (this.curPacketSendSpeed < 0.1D) this.curPacketSendSpeed = 0.1D;
    double d1 = this.pingTo * 2 / 3;
    if (d1 < 10.0D) d1 = 10.0D;

    double d2 = this.curPacketSendSpeed * d1;
    int i = this.socket.getHeaderSize();
    if (d2 < 256.0D + i)
      d2 = 256.0D + i;
    if (d2 > this.socket.getMaxDataSize() + i) {
      d2 = this.socket.getMaxDataSize() + i;
    }
    return (int)d2 - i;
  }

  private void printDouble(double paramDouble) {
    if (paramDouble < 0.0D) { paramDouble = -paramDouble; System.out.print('-'); } else {
      System.out.print('+');
    }System.out.print((int)paramDouble + ".");
    int i = (int)((paramDouble - (int)paramDouble) * 100.0D);
    if (i < 10) System.out.print("0");
    System.out.print(i);
  }

  protected void sendTime(NetMsgOutput paramNetMsgOutput, int paramInt1, long paramLong, int paramInt2) throws IOException {
    int i = this.socket.getHeaderSize();
    double d = (paramInt2 + i) / this.curPacketSendSpeed;

    if (d < 10.0D) d = 10.0D;

    this.nextPacketSendTime = (paramLong + ()d);
    this.lastPacketSendTime = paramLong;

    if (isRealTime())
      this.sendHistory.put(paramInt1, paramInt2 + i, paramLong);
    else {
      return;
    }
    paramNetMsgOutput.writeShort((int)paramLong & 0xFFFF);
    int j = (int)(paramLong - this.lastPacketReceiveTime);
    paramNetMsgOutput.writeShort(j & 0xFFFF);
    int k = this.lastPacketReceiveSequenceNum & 0x3FFF;
    k |= ((int)paramLong >> 16 & 0x1) << 14;
    k |= (j >> 16 & 0x1) << 15;
    paramNetMsgOutput.writeShort(k);
  }

  public long remoteClockOffset()
  {
    if (this.receiveCountPackets > 256)
      return this.remoteClockOffsetSum / 256L;
    if (this.receiveCountPackets > 0) {
      return this.remoteClockOffsetSum / this.receiveCountPackets;
    }
    return 0L;
  }

  private long receiveTime(long paramLong, NetMsgInput paramNetMsgInput, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException
  {
    if (!paramBoolean1)
      return Time.currentReal();
    long l1 = paramLong;
    int i = paramNetMsgInput.readUnsignedShort();
    int j = paramNetMsgInput.readUnsignedShort();
    int k = paramNetMsgInput.readUnsignedShort();

    if (paramBoolean2) return paramLong;

    i |= (k >> 14 & 0x1) << 16;
    j |= (k >> 15 & 0x1) << 16;
    k &= 16383;
    long l2 = this.lastPacketReceiveRemoteTime & 0xFFFE0000 | i;
    while (l2 < this.lastPacketReceiveRemoteTime) l2 += 131072L;

    int m = this.sendHistory.getIndex(k);
    if (m >= 0) {
      this.receiveCountPackets += 1;
      long l3 = this.sendHistory.getTime(m);

      int n = (int)(paramLong - l3) - j;
      if (n < 0)
      {
        n = 0;
        j = (int)(paramLong - l3);
      }
      long l4 = (paramLong + l3) / 2L - (l2 - j / 2);

      if (this.receiveCountPackets > 256) {
        this.remoteClockOffsetSum = (this.remoteClockOffsetSum * 255L / 256L + l4);
        l4 = this.remoteClockOffsetSum / 256L;
        l5 = paramLong - (l2 + l4);
        if (l5 < 0L) { l4 = paramLong - l2; this.remoteClockOffsetSum = (256L * l4); }
        if (l5 > n) { l4 = paramLong - n - l2; this.remoteClockOffsetSum = (256L * l4); }
      } else {
        this.remoteClockOffsetSum += l4;
        l4 = this.remoteClockOffsetSum / this.receiveCountPackets;
        l5 = paramLong - (l2 + l4);
        if (l5 < 0L) { l4 = paramLong - l2; this.remoteClockOffsetSum = (this.receiveCountPackets * l4); }
        if (l5 > n) { l4 = paramLong - n - l2; this.remoteClockOffsetSum = (this.receiveCountPackets * l4);
        }
      }
      l1 = l2 + l4;
      long l5 = paramLong - l1;
      int i1 = n - (int)l5;

      int i2 = this.pingTo;
      if (this.receiveCountPackets > 4) {
        int i3 = 4;
        if (this.ping > 0) {
          i3 = (2000 + this.ping / 2) / this.ping;
          if (i3 < 4) i3 = 4;
        }
        this.pingSum = (this.pingSum * (i3 - 1) / this.countPingSum + n);
        this.pingToSum = (this.pingToSum * (i3 - 1) / this.countPingSum + i1);
        this.countPingSum = i3;
      } else {
        this.pingSum += n;
        this.pingToSum += i1;
        this.countPingSum = this.receiveCountPackets;
      }
      this.ping = (this.pingSum / this.countPingSum);
      this.pingTo = (this.pingToSum / this.countPingSum);
      if ((this.receiveCountPackets > 1) && (paramLong > this.lastPacketReceiveTime)) {
        this.pingToSpeed = ((this.pingTo - i2) / (paramLong - this.lastPacketReceiveTime));

        if (this.bCheckTimeSpeed) {
          if (this.checkI < 32) {
            if ((this.checkI < 0) || (paramLong - this.checkT[this.checkI] > 1000L)) {
              this.checkI += 1;
              this.checkT[(this.checkI & 0x1F)] = paramLong;
              this.checkR[(this.checkI & 0x1F)] = (l2 + (this.ping - this.pingTo));
            }
          } else if (paramLong - this.checkT[(this.checkI & 0x1F)] > 1000L) {
            this.checkI += 1;
            long l6 = this.checkT[(this.checkI & 0x1F)];
            long l7 = this.checkR[(this.checkI & 0x1F)];
            long l8 = l2 + (this.ping - this.pingTo);
            this.checkT[(this.checkI & 0x1F)] = paramLong;
            this.checkR[(this.checkI & 0x1F)] = l8;
            double d = Math.abs(1.0D - (l8 - l7) / (paramLong - l6));
            if (d > checkTimeSpeedDifferense)
              this.checkC += 1;
            else {
              this.checkC = 0;
            }
          }
        }
      }
    }
    this.lastPacketReceiveTime = paramLong;
    this.lastPacketReceiveRemoteTime = l2;
    this.lastPacketReceiveSequenceNum = paramInt;

    return l1;
  }

  private void cdata(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (this.swTbl == null) return;
    int i = paramInt2 % this.swTbl.length;
    if (paramInt1 + paramInt2 > paramArrayOfByte.length)
      paramInt2 = paramArrayOfByte.length - paramInt1;
    while (paramInt2 > 0) {
      paramArrayOfByte[paramInt1] = (byte)(paramArrayOfByte[paramInt1] ^ this.swTbl[i]);
      paramInt1++; paramInt2--;
      i = (i + 1) % this.swTbl.length;
    }
  }

  public double getMaxSpeed()
  {
    return this.maxSendSpeed;
  }

  public void setMaxSpeed(double paramDouble) {
    if (paramDouble == this.maxSendSpeed) return;
    if (this.socket.maxChannels == 0) {
      setMaxSendSpeed(paramDouble);
      this.socket.setMaxSpeed(paramDouble);
      channelObj.doSetSpeed(this, paramDouble);
      return;
    }if (paramDouble > this.socket.getMaxSpeed()) {
      paramDouble = this.socket.getMaxSpeed();
      if (paramDouble == this.maxSendSpeed) return;
    }
    setMaxSendSpeed(paramDouble);
    channelObj.doSetSpeed(this, paramDouble);
  }

  protected static void destroyNetObj(NetObj paramNetObj)
  {
    if (paramNetObj == null) return; try
    {
      Object localObject1 = paramNetObj;
      Object localObject2 = paramNetObj.superObj();
      if ((localObject2 != null) && ((localObject2 instanceof Destroy))) {
        localObject1 = (Destroy)localObject2;
        if (!((Destroy)localObject1).isDestroyed())
          ((Destroy)localObject1).destroy();
      }
      else if (!((Destroy)localObject1).isDestroyed()) {
        ((Destroy)localObject1).destroy();
      }
    } catch (Exception localException) {
      printDebug(localException);
    }
  }

  protected static void classInit()
  {
    if (bClassInited)
      return;
    channelObj = new ChannelObj(5);
    askMessage = new AskMessage(1);
    destroyMessage = new DestroyMessage(4);
    nakMessage = new NakMessage(2);
    spawnMessage = new SpawnMessage(3);
    bClassInited = true;
  }

  static class NakMessage extends NetObj
  {
    public void send(int paramInt1, int paramInt2, NetChannel paramNetChannel)
    {
      try
      {
        NetMsgFiltered localNetMsgFiltered = paramNetChannel.nakMessageOut;
        if (localNetMsgFiltered.isLocked()) localNetMsgFiltered.unLock(paramNetChannel);
        localNetMsgFiltered.clear();
        localNetMsgFiltered.prior = 1.1F;
        localNetMsgFiltered.writeShort(paramInt1);
        localNetMsgFiltered.writeByte(paramInt2 - 1);
        postRealTo(Time.currentReal(), paramNetChannel, localNetMsgFiltered); } catch (Exception localException) {
        NetChannel.printDebug(localException);
      }
    }

    public void msgNet(NetMsgInput paramNetMsgInput) {
      try {
        int i = paramNetMsgInput.readUnsignedShort();
        int j = paramNetMsgInput.readUnsignedByte() + 1;
        paramNetMsgInput.channel().nakMessageReceive(i, j); } catch (Exception localException) {
        NetChannel.printDebug(localException);
      }
    }
    public NakMessage(int paramInt) {
      super(paramInt);
    }
  }

  static class AskMessage extends NetObj
  {
    public void send(int paramInt, NetChannel paramNetChannel)
    {
      try
      {
        NetMsgFiltered localNetMsgFiltered = paramNetChannel.askMessageOut;
        if (localNetMsgFiltered.isLocked()) localNetMsgFiltered.unLock(paramNetChannel);
        localNetMsgFiltered.clear();
        localNetMsgFiltered.prior = 1.1F;
        localNetMsgFiltered.writeShort(paramInt);
        postRealTo(Time.currentReal(), paramNetChannel, localNetMsgFiltered); } catch (Exception localException) {
        NetChannel.printDebug(localException);
      }
    }

    public void msgNet(NetMsgInput paramNetMsgInput) {
      try {
        int i = paramNetMsgInput.readUnsignedShort();
        paramNetMsgInput.channel().askMessageReceive(i); } catch (Exception localException) {
        NetChannel.printDebug(localException);
      }
    }
    public AskMessage(int paramInt) {
      super(paramInt);
    }
  }

  static class DestroyMessage extends NetObj
  {
    public void msgNet(NetMsgInput paramNetMsgInput)
    {
      try
      {
        NetChannel.destroyNetObj(paramNetMsgInput.readNetObj()); } catch (Exception localException) {
        NetChannel.printDebug(localException);
      }
    }
    public DestroyMessage(int paramInt) {
      super(paramInt);
    }
  }

  static class SpawnMessage extends NetObj
  {
    public void msgNet(NetMsgInput paramNetMsgInput)
    {
      if (paramNetMsgInput.channel().isDestroying()) return; try
      {
        int i = paramNetMsgInput.readInt();
        int j = paramNetMsgInput.readUnsignedShort();
        paramNetMsgInput.channel().removeWaitSpawn(j);
        paramNetMsgInput.fixed();
        NetSpawn localNetSpawn = (NetSpawn)Spawn.get(i);
        localNetSpawn.netSpawn(j, paramNetMsgInput); } catch (Exception localException) {
        NetChannel.printDebug(localException);
      }
    }
    public SpawnMessage(int paramInt) {
      super(paramInt);
    }
  }

  static class ChannelObj extends NetObj
  {
    private static final int MSG_DESTROY = 0;
    private static final int MSG_REQUEST_CREATING = 1;
    private static final int MSG_ASK_CREATING = 2;
    private static final int MSG_SET_SPEED = 3;
    private static final int MSG_SET_TIMEOUT = 4;

    public void doSetSpeed(NetChannel paramNetChannel, double paramDouble)
    {
      try
      {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(3);
        localNetMsgGuaranted.writeInt((int)(paramDouble * 1000.0D));
        postTo(paramNetChannel, localNetMsgGuaranted); } catch (Exception localException) {
        NetChannel.printDebug(localException);
      }
    }
    public void doSetTimeout(NetChannel paramNetChannel, int paramInt) {
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(4);
        localNetMsgGuaranted.writeInt(paramInt);
        postTo(paramNetChannel, localNetMsgGuaranted); } catch (Exception localException) {
        NetChannel.printDebug(localException);
      }
    }
    public void doDestroy(NetChannel paramNetChannel) {
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(0);
        localNetMsgGuaranted.write255(paramNetChannel.diagnosticMessage);
        postTo(paramNetChannel, localNetMsgGuaranted); } catch (Exception localException) {
        NetChannel.printDebug(localException);
      }
    }
    public void doRequestCreating(NetChannel paramNetChannel) {
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(1);
        localNetMsgGuaranted.writeByte(paramNetChannel.flags);
        localNetMsgGuaranted.writeInt((int)(paramNetChannel.maxSendSpeed * 1000.0D));
        if ((paramNetChannel.flags & 0x4) != 0) {
          if (NetEnv.cur().control == null) {
            new NetControlLock(paramNetChannel);
            localNetMsgGuaranted.writeByte(0);
          } else {
            localNetMsgGuaranted.writeByte(1);
          }
        }
        postTo(paramNetChannel, localNetMsgGuaranted);
        if ((paramNetChannel.flags & 0x4) == 0)
          paramNetChannel.controlStartInit(); 
      } catch (Exception localException) {
        NetChannel.printDebug(localException); paramNetChannel.destroy();
      }
    }
    public void msgNet(NetMsgInput paramNetMsgInput) {
      try {
        NetChannel localNetChannel = paramNetMsgInput.channel();
        if (localNetChannel.isDestroying()) return;
        int i = paramNetMsgInput.readByte();
        switch (i) {
        case 0:
          if (paramNetMsgInput.available() > 0) localNetChannel.destroy(paramNetMsgInput.read255()); else
            localNetChannel.destroy();
          break;
        case 1:
          int j = paramNetMsgInput.readByte();
          double d1 = paramNetMsgInput.readInt() / 1000.0D;
          localNetChannel.flags = j;
          if ((j & 0x4) != 0) {
            int k = paramNetMsgInput.readByte() != 0 ? 1 : 0;
            if (k != 0) {
              if (NetEnv.cur().control == null) {
                new NetControlLock(localNetChannel);
                NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
                localNetMsgGuaranted.writeByte(2);
                postTo(localNetChannel, localNetMsgGuaranted);
              } else {
                if ((NetEnv.cur().control instanceof NetControlLock)) {
                  localNetChannel.destroy("Remote control slot is locked.");
                  break;
                }
                localNetChannel.destroy("Only TREE network structure is supported.");
                break;
              }
            } else {
              if (NetEnv.cur().control == null) {
                new NetControl(null); } else {
                if ((NetEnv.cur().control instanceof NetControlLock)) {
                  localNetChannel.destroy("Remote control slot is locked.");
                  break;
                }if (!(NetEnv.cur().control instanceof NetControl)) {
                  localNetChannel.destroy("Remote control slot is cracked.");
                  break;
                }
              }
              MsgNet.postRealNewChannel(NetEnv.cur().control, localNetChannel);
            }
          } else {
            localNetChannel.controlStartInit();
          }
          if (d1 > localNetChannel.maxSendSpeed)
            doSetSpeed(localNetChannel, localNetChannel.maxSendSpeed);
          else {
            localNetChannel.setMaxSendSpeed(d1);
          }
          break;
        case 2:
          MsgNet.postRealNewChannel(NetEnv.cur().control, localNetChannel);
          break;
        case 3:
          double d2 = paramNetMsgInput.readInt() / 1000.0D;
          localNetChannel.setMaxSpeed(d2);
          break;
        case 4:
          int m = paramNetMsgInput.readInt();
          localNetChannel.setMaxTimeout(m);
        }
      }
      catch (Exception localException)
      {
        NetChannel.printDebug(localException);
      }
    }
    public ChannelObj(int paramInt) {
      super(paramInt);
    }
  }
}