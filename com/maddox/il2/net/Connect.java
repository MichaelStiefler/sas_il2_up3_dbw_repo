package com.maddox.il2.net;

import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.rts.Finger;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgAddListener;
import com.maddox.rts.MsgNet;
import com.maddox.rts.MsgNetExtListener;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSocket;
import com.maddox.rts.Time;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.PrintStream;
import java.util.List;
import java.util.StringTokenizer;

public class Connect
  implements NetConnect, MsgNetExtListener, MsgTimeOutListener
{
  static final boolean bLog = false;
  static final long TIME_OUT = 500L;
  static final long FULL_TIME_OUT = 30000L;
  public static final String PROMPT = "socket";
  public static final String VERSION = "FB_PF_v_4.10.1m";
  static final String CONNECT = "connect";
  static final String CONNECTED = "connected";
  static final String REJECT = "reject";
  static final String REQUESTINFO = "rinfo";
  static final String ANSWERINFO = "ainfo";
  public NetBanned banned = new NetBanned();

  boolean bBindEnable = false;

  boolean bJoin = false;
  int joinId;
  long joinTimeOut;
  NetSocket joinSocket;
  NetAddress joinAddr;
  int joinPort;
  int joinStamp;
  private static NetMsgInput _netMsgInput = new NetMsgInput();
  private MsgTimeOut ticker;

  public void bindEnable(boolean paramBoolean)
  {
    this.bBindEnable = paramBoolean;
  }

  public boolean isBindEnable()
  {
    return this.bBindEnable;
  }

  private static String badVersionMessage() {
    return "Server uses a different version of the game (4.10.1m).";
  }

  private void bindReceiveConnect(StringTokenizer paramStringTokenizer, NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt)
  {
    if (Main.cur().netServerParams == null) return;
    if (!paramStringTokenizer.hasMoreTokens()) return;
    String str1 = paramStringTokenizer.nextToken();
    if (!paramStringTokenizer.hasMoreTokens()) return;
    String str2 = paramStringTokenizer.nextToken();
    if (!paramStringTokenizer.hasMoreTokens()) return;
    String str3 = paramStringTokenizer.nextToken();

    if ((!"FB_PF_v_4.10.1m".equals(str1)) && 
      (!"il2_r01_0f".equals(str1)))
    {
      String str4 = "reject " + str2 + " " + str3 + " " + badVersionMessage();

      NetEnv.cur().postExtUTF(32, str4, paramNetSocket, paramNetAddress, paramInt);
      return;
    }

    if (this.banned.isExist(paramNetAddress)) {
      return;
    }
    int i = 0;
    try { i = Integer.parseInt(str2); } catch (Exception localException1) {
      return;
    }
    int j = 0;
    try { j = Integer.parseInt(str3); } catch (Exception localException2) {
      return;
    }
    Object localObject1 = null;

    List localList = NetEnv.channels();
    int k = localList.size();
    Object localObject2;
    for (int m = 0; m < k; m++) {
      localObject2 = (NetChannel)localList.get(m);
      if ((!((NetChannel)localObject2).socket().equals(paramNetSocket)) || (((NetChannel)localObject2).remoteId() != i) || (!((NetChannel)localObject2).remoteAddress().equals(paramNetAddress)) || (((NetChannel)localObject2).remotePort() != paramInt))
      {
        continue;
      }
      if ((((NetChannel)localObject2).state() == 1) && (((NetChannel)localObject2).getInitStamp() == j)) {
        localObject1 = localObject2;
      }
      else
      {
        ((NetChannel)localObject2).destroy("Reconnect user");

        return;
      }

    }

    if (localObject1 == null)
    {
      String str5;
      if ((!isBindEnable()) || (paramNetSocket.maxChannels == 0)) {
        str5 = "reject " + str2 + " " + j + " connect disabled";

        NetEnv.cur().postExtUTF(32, str5, paramNetSocket, paramNetAddress, paramInt);
        return;
      }
      if (paramNetSocket.maxChannels <= paramNetSocket.countChannels) {
        str5 = "reject " + str2 + " " + j + " limit connections = " + paramNetSocket.maxChannels;

        NetEnv.cur().postExtUTF(32, str5, paramNetSocket, paramNetAddress, paramInt);
        return;
      }
      int n = NetEnv.hosts().size();
      if (!Main.cur().netServerParams.isDedicated())
        n++;
      if (n >= Main.cur().netServerParams.getMaxUsers()) {
        localObject2 = "reject " + str2 + " " + j + " limit users = " + Main.cur().netServerParams.getMaxUsers();

        NetEnv.cur().postExtUTF(32, (String)localObject2, paramNetSocket, paramNetAddress, paramInt);
        return;
      }

      int i1 = NetEnv.cur().nextIdChannel(true);
      localObject1 = NetEnv.cur().createChannel(1, i1, i, paramNetSocket, paramNetAddress, paramInt, this);
      ((NetChannel)localObject1).setInitStamp(j);
      setChannel((NetChannel)localObject1, i1, i, j);
      paramNetSocket.countChannels += 1;
      if (!"FB_PF_v_4.10.1m".equals(str1)) {
        kickChannel(localObject1);
      }
    }

    String str6 = "connected " + str1 + " " + i + " " + j + " " + ((NetChannel)localObject1).id();

    NetEnv.cur().postExtUTF(32, str6, paramNetSocket, paramNetAddress, paramInt);
  }

  private void kickChannel(Object paramObject) {
    if (!(paramObject instanceof NetChannel)) return;
    NetChannel localNetChannel = (NetChannel)paramObject;
    if (localNetChannel.isDestroying()) return;
    if (localNetChannel.isReady()) {
      localNetChannel.destroy(badVersionMessage());
      return;
    }
    if (localNetChannel.isIniting())
      new MsgAction(64, 0.5D, localNetChannel) {
        public void doAction(Object paramObject) {
          Connect.this.kickChannel(paramObject);
        }
      };
  }

  public void join(NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt)
  {
    if (this.bJoin) return;
    this.joinSocket = paramNetSocket;
    this.joinAddr = paramNetAddress;
    this.joinPort = paramInt;
    this.joinTimeOut = 30000L;
    this.joinId = NetEnv.cur().nextIdChannel(false);
    this.joinStamp = Time.raw();

    this.joinSocket.countChannels += 1;
    joinSend();

    this.bJoin = true;
    if (!this.ticker.busy())
      this.ticker.post(Time.currentReal() + 500L); 
  }

  public void msgTimeOut(Object paramObject) {
    if ((paramObject != null) && ((paramObject instanceof NetChannel))) {
      msgTimeOutStep((NetChannel)paramObject);
      return;
    }
    if (this.bJoin) {
      this.joinTimeOut -= 500L;
      if (this.joinTimeOut < 0L)
      {
        System.out.println("socket join to " + this.joinAddr.getHostAddress() + ":" + this.joinPort + " failed: timeout");

        if (Main.cur().netChannelListener != null) {
          Main.cur().netChannelListener.netChannelCanceled("Connection attempt to remote host failed.  Reason: Timeout.");
        }

        this.joinSocket.countChannels -= 1;
        this.bJoin = false;
        return;
      }
      joinSend();
      this.ticker.post(Time.currentReal() + 500L);
    }
  }

  public void joinBreak()
  {
    if (this.bJoin)
    {
      System.out.println("socket join to " + this.joinAddr.getHostAddress() + ":" + this.joinPort + " breaked");

      if (Main.cur().netChannelListener != null) {
        Main.cur().netChannelListener.netChannelCanceled("Connection attempt to remote host failed.  Reason: User Cancel.");
      }

      this.joinSocket.countChannels -= 1;
      this.bJoin = false;
    }
  }

  public boolean isJoinProcess()
  {
    return this.bJoin;
  }

  private void joinSend() {
    String str = "connect FB_PF_v_4.10.1m " + this.joinId + " " + this.joinStamp;

    NetEnv.cur().postExtUTF(32, str, this.joinSocket, this.joinAddr, this.joinPort);
  }

  private void joinReceiveConnected(StringTokenizer paramStringTokenizer, NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt)
  {
    if (!this.bJoin) return;
    if (!paramNetSocket.equals(this.joinSocket)) return;
    if (!paramNetAddress.equals(this.joinAddr)) return;
    if (paramInt != this.joinPort) return;

    if (!paramStringTokenizer.hasMoreTokens()) return;
    String str1 = paramStringTokenizer.nextToken();
    if (!"FB_PF_v_4.10.1m".equals(str1)) return;

    if (!paramStringTokenizer.hasMoreTokens()) return;
    String str2 = paramStringTokenizer.nextToken();
    int i = 0;
    try { i = Integer.parseInt(str2); } catch (Exception localException1) {
      return;
    }if (i != this.joinId) return;

    if (!paramStringTokenizer.hasMoreTokens()) return;
    String str3 = paramStringTokenizer.nextToken();
    int j = 0;
    try { j = Integer.parseInt(str3); } catch (Exception localException2) {
      return;
    }if (j != this.joinStamp) return;

    if (!paramStringTokenizer.hasMoreTokens()) return;
    String str4 = paramStringTokenizer.nextToken();
    int k = 0;
    try { k = Integer.parseInt(str4); } catch (Exception localException3) {
      return;
    }

    System.out.println("socket start connecting to " + this.joinAddr.getHostAddress() + ":" + this.joinPort);

    NetChannel localNetChannel = NetEnv.cur().createChannel(7, this.joinId, k, this.joinSocket, this.joinAddr, this.joinPort, this);

    localNetChannel.setInitStamp(j);
    setChannel(localNetChannel, k, this.joinId, j);
    this.bJoin = false;
  }

  private void joinReceiveReject(StringTokenizer paramStringTokenizer, NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt)
  {
    if (!this.bJoin) return;
    if (!paramNetSocket.equals(this.joinSocket)) return;
    if (!paramNetAddress.equals(this.joinAddr)) return;
    if (paramInt != this.joinPort) return;

    if (!paramStringTokenizer.hasMoreTokens()) return;
    String str1 = paramStringTokenizer.nextToken();
    int i = 0;
    try { i = Integer.parseInt(str1); } catch (Exception localException1) {
      return;
    }if (i != this.joinId) return;

    if (!paramStringTokenizer.hasMoreTokens()) return;
    String str2 = paramStringTokenizer.nextToken();
    String str3 = "???";
    StringBuffer localStringBuffer = new StringBuffer();

    int j = 0;
    try { j = Integer.parseInt(str2);
      if (j != this.joinStamp) return; 
    } catch (Exception localException2)
    {
      localStringBuffer.append(str2);
      localStringBuffer.append(' ');
      str3 = str2;
    }

    if (paramStringTokenizer.hasMoreTokens()) {
      while (paramStringTokenizer.hasMoreTokens()) {
        localStringBuffer.append(paramStringTokenizer.nextToken());
        localStringBuffer.append(' ');
      }
      str3 = localStringBuffer.toString();
    }

    System.out.println("socket join to " + this.joinAddr.getHostAddress() + ":" + this.joinPort + " regect (" + str3 + ")");

    if (Main.cur().netChannelListener != null) {
      Main.cur().netChannelListener.netChannelCanceled("Connection attempt to remote host rejected.  Reason: " + str3);
    }

    this.joinSocket.countChannels -= 1;
    this.bJoin = false;
  }

  public void msgNetExt(byte[] paramArrayOfByte, NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length < 2)) return;
    if (paramArrayOfByte[0] != 32) return;
    String str1 = "";
    try {
      _netMsgInput.setData(null, false, paramArrayOfByte, 1, paramArrayOfByte.length - 1);
      str1 = _netMsgInput.readUTF();
    } catch (Exception localException) {
      return;
    }

    StringTokenizer localStringTokenizer = new StringTokenizer(str1, " ");
    if (localStringTokenizer.hasMoreTokens()) {
      String str2 = localStringTokenizer.nextToken();

      if (str2.equals("connect"))
        bindReceiveConnect(localStringTokenizer, paramNetSocket, paramNetAddress, paramInt);
      else if (str2.equals("connected"))
        joinReceiveConnected(localStringTokenizer, paramNetSocket, paramNetAddress, paramInt);
      else if (str2.equals("reject"))
        joinReceiveReject(localStringTokenizer, paramNetSocket, paramNetAddress, paramInt);
      else if (str2.equals("rinfo"))
        receiveRequestInfo(localStringTokenizer, paramNetSocket, paramNetAddress, paramInt);
    }
  }

  public void msgRequest(String paramString)
  {
    if (Main.cur().netChannelListener != null)
      Main.cur().netChannelListener.netChannelRequest(paramString);
  }

  public void channelCreated(NetChannel paramNetChannel)
  {
    if (!paramNetChannel.isPublic())
    {
      System.out.println("socket channel '" + paramNetChannel.id() + "' created: " + paramNetChannel.remoteAddress().getHostAddress() + ":" + paramNetChannel.remotePort());

      return;
    }

    System.out.println("socket channel '" + paramNetChannel.id() + "' start creating: " + paramNetChannel.remoteAddress().getHostAddress() + ":" + paramNetChannel.remotePort());

    paramNetChannel.startSortGuaranted();
    HashMapInt localHashMapInt = NetEnv.cur().objects;
    HashMapIntEntry localHashMapIntEntry = localHashMapInt.nextEntry(null);
    while (localHashMapIntEntry != null) {
      NetObj localNetObj = (NetObj)localHashMapIntEntry.getValue();
      if (!paramNetChannel.isMirrored(localNetObj))
        MsgNet.postRealNewChannel(localNetObj, paramNetChannel);
      localHashMapIntEntry = localHashMapInt.nextEntry(localHashMapIntEntry);
    }
    paramNetChannel.setStateInit(2);
    MsgTimeOut.post(64, Time.currentReal() + 1L, this, paramNetChannel);
  }

  private void msgTimeOutStep(NetChannel paramNetChannel) {
    if (paramNetChannel.isDestroying()) return;
    int i = paramNetChannel.state();
    switch (i) {
    case 2:
      try {
        paramNetChannel.stopSortGuaranted();
      } catch (Exception localException) {
        paramNetChannel.destroy("Cycle inits");
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        return;
      }
      paramNetChannel.setStateInit(3);
    case 3:
      if (Main.cur().netServerParams == null)
      {
        MsgTimeOut.post(64, Time.currentReal() + 200L, this, paramNetChannel);
        return;
      }
      paramNetChannel.setStateInit(0);

      if (!NetEnv.isServer()) {
        System.out.println("socket channel '" + paramNetChannel.id() + "', ip " + paramNetChannel.remoteAddress().getHostAddress() + ":" + paramNetChannel.remotePort() + ", is complete created");
      }

      if (Main.cur().netChannelListener != null)
        Main.cur().netChannelListener.netChannelCreated(paramNetChannel);
      return;
    }
  }

  public void channelNotCreated(NetChannel paramNetChannel, String paramString)
  {
    System.out.println("socket channel NOT created (" + paramString + "): " + paramNetChannel.remoteAddress().getHostAddress() + ":" + paramNetChannel.remotePort());

    if (Main.cur().netChannelListener != null)
      Main.cur().netChannelListener.netChannelCanceled("Connection attempt to remote host failed.  Reason: " + paramString);
  }

  public void channelDestroying(NetChannel paramNetChannel, String paramString)
  {
    System.out.println("socketConnection with " + paramNetChannel.remoteAddress() + ":" + paramNetChannel.remotePort() + " on channel " + paramNetChannel.id() + " lost.  Reason: " + paramString);

    if (Main.cur().netChannelListener != null)
      Main.cur().netChannelListener.netChannelDestroying(paramNetChannel, "The communication with the remote host is lost. Reason: " + paramString);
  }

  private void receiveRequestInfo(StringTokenizer paramStringTokenizer, NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt)
  {
    if (!paramStringTokenizer.hasMoreTokens()) return;
    if (Main.cur().netServerParams == null) return;
    if (!isBindEnable()) return;
    if (this.banned.isExist(paramNetAddress))
      return;
    if ((!Main.cur().netServerParams.isMaster()) && (Main.cur().netServerParams.masterChannel().userState == -1))
    {
      return;
    }String str1 = paramStringTokenizer.nextToken();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("ainfo"); localStringBuffer.append(' ');
    localStringBuffer.append(str1); localStringBuffer.append(' ');
    localStringBuffer.append("FB_PF_v_4.10.1m"); localStringBuffer.append(' ');
    localStringBuffer.append(Main.cur().netServerParams.isMaster() ? "1 " : "0 ");
    localStringBuffer.append("" + (Main.cur().netServerParams.getType() >> 4 & 0x7) + " ");
    localStringBuffer.append(Main.cur().netServerParams.isProtected() ? "1 " : "0 ");
    localStringBuffer.append(Main.cur().netServerParams.isDedicated() ? "1 " : "0 ");
    localStringBuffer.append(Main.cur().netServerParams.isCoop() ? "1 " : "0 ");
    localStringBuffer.append(Mission.isPlaying() ? "1 " : "0 ");
    localStringBuffer.append(paramNetSocket.maxChannels); localStringBuffer.append(' ');
    localStringBuffer.append(paramNetSocket.countChannels); localStringBuffer.append(' ');
    localStringBuffer.append(Main.cur().netServerParams.getMaxUsers()); localStringBuffer.append(' ');
    int i = NetEnv.hosts().size();
    if (!Main.cur().netServerParams.isDedicated())
      i++;
    localStringBuffer.append(i); localStringBuffer.append(' ');
    localStringBuffer.append(Main.cur().netServerParams.serverName());

    String str2 = localStringBuffer.toString();

    NetEnv.cur().postExtUTF(32, str2, paramNetSocket, paramNetAddress, paramInt);
  }

  public Connect()
  {
    MsgAddListener.post(64, NetEnv.cur(), this, null);
    this.ticker = new MsgTimeOut();
    this.ticker.setNotCleanAfterSend();
    this.ticker.setFlags(64);
    this.ticker.setListener(this);
  }

  private void setChannel(NetChannel paramNetChannel, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt3 + paramInt1 + paramInt2;
    if (i < 0) i = -i;
    int j = i % 16 + 12;
    int k = i % Finger.kTable.length;
    if (j < 0)
      j = -j % 16;
    if (j < 10)
      j = 10;
    if (k < 0)
      k = -k % Finger.kTable.length;
    byte[] arrayOfByte = new byte[j];
    for (int m = 0; m < j; m++)
      arrayOfByte[m] = Finger.kTable[((k + m) % Finger.kTable.length)];
    paramNetChannel.swTbl = arrayOfByte;
    for (m = 0; m < 2; m++)
      paramNetChannel.crcInit[m] = Finger.kTable[((k + j + m) % Finger.kTable.length)];
  }
}