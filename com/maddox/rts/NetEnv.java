package com.maddox.rts;

import com.maddox.rts.net.NetFileServer;
import com.maddox.rts.net.NetFileServerDef;
import com.maddox.rts.net.NetFileTransport;
import com.maddox.util.HashMapInt;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class NetEnv
  implements MsgTimeOutListener, MsgAddListenerListener, MsgRemoveListenerListener
{
  public static final int ADAPTER_READ_TICK_POS = 0;
  public static final int ADAPTER_WRITE_TICK_POS = 2147483646;
  public static final int ID_NULL = 0;
  public static final int ID_ASK_MESSAGE = 1;
  public static final int ID_NAK_MESSAGE = 2;
  public static final int ID_SPAWN = 3;
  public static final int ID_DESTROY = 4;
  public static final int ID_CHANNEL = 5;
  public static final int ID_FILE_TRANSPORT = 6;
  public static final int ID_FILE_SERVERDEF = 7;
  public static final int ID_STREAM_SYNC = 8;
  public static final int ID_KEYRECORD = 9;
  public static final int ID_BEGIN = 255;
  public static final int ID_END = 32767;
  public static final int MAX_LEN_MSG = 255;
  public NetConnect connect;
  public NetObj control;
  protected NetHost host;
  protected List hosts = new ArrayList();

  protected ArrayList channels = new ArrayList();

  protected HashMapInt indxChannels = new HashMapInt();

  public HashMapInt objects = new HashMapInt();

  protected int nextFreeID = 255;

  protected ArrayList udatedObjects = new ArrayList();

  protected ArrayList socketsNoBlock = new ArrayList();
  protected ArrayList socketsBlock = new ArrayList();

  protected ArrayList socketThreads = new ArrayList();
  public NetFileTransport fileTransport;
  public NetFileServer fileServerDef;
  private Object[] _updateObjects = new Object[1];
  private Object idReadStep = new Object();
  private Object idWriteStep = new Object();

  private ArrayList extPackets = new ArrayList();

  private boolean bActive = false;
  private int nextId = 0;
  private MsgTimeOut tickerRead;
  private MsgTimeOut tickerWrite;
  private Listeners listeners;
  private NetMsgInput msgInput;
  private NetMsgOutput msgOutput;
  private NetPacket packet;
  private List inputPackets;
  private List inputSockets;
  private ActionReceivedPacket inputAction;
  public static boolean bTestTransfer = false;
  public static float testSpeed = 0.0F;
  public static int testMinLag = 0;
  public static int testMaxLag = 0;
  public static float testDown = 0.0F;
  public static boolean testLag = false;

  public static boolean isServer()
  {
    if (!RTSConf.cur.netEnv.bActive)
      return true;
    return (RTSConf.cur.netEnv.control != null) && (RTSConf.cur.netEnv.control.isMaster());
  }

  public static boolean isActive()
  {
    return RTSConf.cur.netEnv.bActive;
  }

  public static void active(boolean paramBoolean) {
    RTSConf.cur.netEnv.bActive = paramBoolean;
    if (paramBoolean) {
      if (!RTSConf.cur.netEnv.tickerRead.busy())
        RTSConf.cur.netEnv.tickerRead.post();
      if (!RTSConf.cur.netEnv.tickerWrite.busy())
        RTSConf.cur.netEnv.tickerWrite.post();
    }
  }

  public static NetHost host()
  {
    return RTSConf.cur.netEnv.host;
  }

  public static List hosts()
  {
    return RTSConf.cur.netEnv.hosts;
  }

  public static List socketsBlock()
  {
    return RTSConf.cur.netEnv.socketsBlock;
  }

  public static List socketsNoBlock()
  {
    return RTSConf.cur.netEnv.socketsNoBlock;
  }

  public static List channels()
  {
    return RTSConf.cur.netEnv.channels;
  }

  public static NetChannel getChannel(int paramInt)
  {
    return (NetChannel)RTSConf.cur.netEnv.indxChannels.get(paramInt);
  }
  public static void addSocket(NetSocket paramNetSocket) {
    RTSConf.cur.netEnv._addSocket(paramNetSocket);
  }
  private void _addSocket(NetSocket paramNetSocket) { if (!paramNetSocket.isOpen()) {
      throw new NetException("Socket not opened");
    }
    if (paramNetSocket.isSupportedBlockOperation()) {
      NetSocketListener localNetSocketListener = new NetSocketListener(paramNetSocket);
      localNetSocketListener.setPriority(Thread.currentThread().getPriority() + 1);
      localNetSocketListener.start();
      this.socketThreads.add(localNetSocketListener);
      this.socketsBlock.add(paramNetSocket);
    } else {
      this.socketsNoBlock.add(paramNetSocket);
    } }

  public static boolean isExistExclusiveSocket()
  {
    return getExclusiveSocket() != null;
  }

  public static NetSocket getExclusiveSocket() {
    return RTSConf.cur.netEnv._getExclusiveSocket();
  }

  private NetSocket _getExclusiveSocket()
  {
    NetSocket localNetSocket;
    for (int i = 0; i < this.socketsNoBlock.size(); i++) {
      localNetSocket = (NetSocket)this.socketsNoBlock.get(i);
      if ((localNetSocket.isExclusive()) && (localNetSocket.isOpen()))
        return localNetSocket;
    }
    for (i = 0; i < this.socketsBlock.size(); i++) {
      localNetSocket = (NetSocket)this.socketsBlock.get(i);
      if ((localNetSocket.isExclusive()) && (localNetSocket.isOpen()))
        return localNetSocket;
    }
    return null;
  }

  public NetChannel createChannel(int paramInt1, int paramInt2, int paramInt3, NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt4, NetConnect paramNetConnect)
  {
    if (((paramInt1 & 0x4) != 0) && (this.control != null) && ((this.control instanceof NetControlLock)))
    {
      throw new NetException("Creating global channel is locked");
    }if (this.indxChannels.containsKey(paramInt2))
      throw new NetException("Created channel used bad ID");
    if ((this.socketsBlock.indexOf(paramNetSocket) == -1) && (this.socketsNoBlock.indexOf(paramNetSocket) == -1)) {
      throw new NetException("Socket NOT registered");
    }
    NetChannel localNetChannel = new NetChannel(paramInt1, paramInt2, paramInt3, paramNetSocket, paramNetAddress, paramInt4, paramNetConnect);
    this.indxChannels.put(paramInt2, localNetChannel);
    this.channels.add(localNetChannel);
    this.fileTransport.msgNetNewChannel(localNetChannel);
    return localNetChannel;
  }

  public void addChannel(NetChannel paramNetChannel)
  {
    if ((paramNetChannel.isGlobal()) && (this.control != null) && ((this.control instanceof NetControlLock)))
    {
      throw new NetException("Creating global channel is locked");
    }if (this.indxChannels.containsKey(paramNetChannel.id())) {
      throw new NetException("Created channel used bad ID");
    }
    this.indxChannels.put(paramNetChannel.id(), paramNetChannel);
    this.channels.add(paramNetChannel);
    this.fileTransport.msgNetNewChannel(paramNetChannel);
  }

  public void msgTimeOut(Object paramObject)
  {
    if (this.bActive)
    {
      int i;
      int j;
      if (paramObject == this.idReadStep)
      {
        i = this.channels.size();
        j = 0;
        Object localObject1;
        while (j < i) {
          localObject1 = (NetChannel)this.channels.get(j);
          if (((NetChannel)localObject1).update()) {
            j++;
          }
          else {
            this.channels.remove(j);
            this.indxChannels.remove(((NetChannel)localObject1).id);
            if (((NetChannel)localObject1).socket() != null)
              ((NetChannel)localObject1).socket().countChannels -= 1;
            i--;
          }

        }

        i = this.socketsBlock.size();
        j = 0;
        while (j < i) {
          localObject1 = (NetSocket)this.socketsBlock.get(j);
          if ((((NetSocket)localObject1).maxChannels > 0) || (((NetSocket)localObject1).countChannels > 0)) {
            j++;
          } else {
            NetSocketListener localNetSocketListener = (NetSocketListener)this.socketThreads.get(j);
            this.socketThreads.remove(j);
            localNetSocketListener.bDoRun = false;
            if (((NetSocket)localObject1).isOpen()) try {
                ((NetSocket)localObject1).close();
              } catch (Exception localException2) {
              } this.socketsBlock.remove(j);
            i--;
          }
        }

        i = this.socketsNoBlock.size();
        j = 0;
        while (j < i) {
          localObject1 = (NetSocket)this.socketsNoBlock.get(j);
          if ((((NetSocket)localObject1).maxChannels > 0) || (((NetSocket)localObject1).countChannels > 0)) {
            j++;
          } else {
            if (((NetSocket)localObject1).isOpen()) try {
                ((NetSocket)localObject1).close();
              } catch (Exception localException1) {
              } this.socketsNoBlock.remove(j);
            i--;
          }

        }

        i = this.socketsNoBlock.size();
        for (j = 0; j < i; j++) {
          localObject1 = (NetSocket)this.socketsNoBlock.get(j);
          this.packet.time = 0L;
          while (((NetSocket)localObject1).receive(this.packet)) {
            long l2 = this.packet.time;
            if (l2 == 0L)
              l2 = Time.real();
            int m = this.packet.getLength();
            if ((m > 1) && 
              (!receivedPacket((NetSocket)localObject1, this.packet, l2))) {
              receivedExtPacket((NetSocket)localObject1, this.packet);
            }
            this.packet.time = 0L;
          }
        }

        this.tickerRead.post();
      }
      else if (paramObject == this.idWriteStep)
      {
        i = 0;
        j = this.channels.size();
        long l1 = Time.real();
        for (int k = 0; k < j; k++) {
          NetChannel localNetChannel = (NetChannel)this.channels.get(k);
          if (localNetChannel.isRequeredSendPacket(l1)) {
            i = 1;
            break;
          }
        }

        if (i != 0)
        {
          k = this.udatedObjects.size();
          Object localObject2;
          if (k > 0) {
            this._updateObjects = this.udatedObjects.toArray(this._updateObjects);
            try {
              for (int n = 0; n < k; n++) {
                localObject2 = (NetUpdate)this._updateObjects[n];
                this._updateObjects[n] = null;
                if ((localObject2 instanceof NetObj)) {
                  NetObj localNetObj = (NetObj)localObject2;
                  if (localNetObj.isDestroyed())
                    continue;
                  if ((localNetObj.superObj != null) && ((localNetObj.superObj instanceof Destroy)) && (((Destroy)localNetObj.superObj).isDestroyed()))
                    continue;
                }
                else {
                  if (((localObject2 instanceof Destroy)) && (((Destroy)localObject2).isDestroyed()))
                    continue;
                }
                ((NetUpdate)localObject2).netUpdate();
              }
            } catch (Exception localException3) {
              System.out.println(localException3.getMessage());
              localException3.printStackTrace();
            }

          }

          for (int i1 = 0; i1 < j; i1++) { localObject2 = (NetChannel)this.channels.get(i1);
            while (((NetChannel)localObject2).sendPacket(this.msgOutput, this.packet)); }
        }
        sendExtPackets();

        this.tickerWrite.post();
      }
    }
  }

  protected void listenerReceivedPacket(NetSocket paramNetSocket, NetPacket paramNetPacket)
  {
    synchronized (this.inputAction) {
      this.inputPackets.add(paramNetPacket);
      this.inputSockets.add(paramNetSocket);
      this.inputAction.activate();
    }
  }

  private boolean receivedPacket(NetSocket paramNetSocket, NetPacket paramNetPacket, long paramLong)
  {
    if (paramNetPacket.getLength() <= 6) return false;
    this.msgInput.setData(null, false, paramNetPacket.getData(), paramNetPacket.getOffset(), paramNetPacket.getLength());
    try {
      NetChannel localNetChannel = (NetChannel)this.indxChannels.get(this.msgInput.readUnsignedShort());
      if (localNetChannel == null) return false;
      if ((!localNetChannel.remoteAddress().equals(paramNetPacket.getAddress())) || (localNetChannel.remotePort() != paramNetPacket.getPort()))
        return false;
      this.msgInput.reset();
      return localNetChannel.receivePacket(this.msgInput, paramLong);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }return false;
  }

  public void postExt(byte[] paramArrayOfByte, int paramInt1, int paramInt2, NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt3)
  {
    if (this.bActive)
      this.extPackets.add(new NetExtPacket(paramArrayOfByte, paramInt1, paramInt2, paramNetSocket, paramNetAddress, paramInt3)); 
  }

  public void postExtUTF(byte paramByte, String paramString, NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt) {
    if (this.bActive)
      try {
        this.msgOutput.clear();
        this.msgOutput.writeByte(paramByte);
        this.msgOutput.writeUTF(paramString);
        this.extPackets.add(new NetExtPacket(this.msgOutput.data(), 0, this.msgOutput.dataLength(), paramNetSocket, paramNetAddress, paramInt));
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
  }

  private void sendExtPackets()
  {
    int i = this.extPackets.size();
    for (int j = 0; j < i; j++) {
      NetExtPacket localNetExtPacket = (NetExtPacket)this.extPackets.get(j);
      try {
        this.msgOutput.clear();
        this.msgOutput.write(localNetExtPacket.getBuf());
        this.packet.setLength(this.msgOutput.dataLength());
        this.packet.setAddress(localNetExtPacket.getAddress());
        this.packet.setPort(localNetExtPacket.getPort());
        localNetExtPacket.getSocket().send(this.packet);
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }

      localNetExtPacket.clear();
    }
    this.extPackets.clear();
  }

  private void receivedExtPacket(NetSocket paramNetSocket, NetPacket paramNetPacket)
  {
    try {
      Object[] arrayOfObject = this.listeners.get();
      if (arrayOfObject != null) {
        byte[] arrayOfByte = new byte[paramNetPacket.getLength()];
        System.arraycopy(paramNetPacket.getData(), paramNetPacket.getOffset(), arrayOfByte, 0, paramNetPacket.getLength());
        MsgNetExt.postReal(Time.currentReal(), arrayOfObject, arrayOfByte, paramNetSocket, paramNetPacket.getAddress(), paramNetPacket.getPort());
      }
    }
    catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  public Object[] getListeners()
  {
    return this.listeners.get();
  }

  public void msgAddListener(Object paramObject1, Object paramObject2)
  {
    this.listeners.addListener(paramObject1);
  }

  public void msgRemoveListener(Object paramObject1, Object paramObject2)
  {
    this.listeners.removeListener(paramObject1);
  }

  public int nextIdChannel(boolean paramBoolean)
  {
    int i;
    do
    {
      i = this.nextId;
      if (paramBoolean) i |= 1;
      this.nextId = (this.nextId + 2 & 0xFFFF);
    }while (this.indxChannels.containsKey(i));
    return i;
  }

  protected NetEnv()
  {
    RTSConf.cur.netEnv = this;
    NetChannel.classInit();
    NetChannelInStream.classInit();
    this.tickerRead = new MsgTimeOut(this.idReadStep);
    this.tickerRead.setNotCleanAfterSend();
    this.tickerRead.setListener(this);
    this.tickerRead.setFlags(72);
    this.tickerRead.setTickPos(0);

    this.tickerWrite = new MsgTimeOut(this.idWriteStep);
    this.tickerWrite.setNotCleanAfterSend();
    this.tickerWrite.setListener(this);
    this.tickerWrite.setFlags(104);
    this.tickerWrite.setTickPos(2147483646);

    this.listeners = new Listeners();
    this.packet = new NetPacket(new byte[2048], 0);
    this.msgInput = new NetMsgInput();
    this.msgOutput = new NetMsgOutput(this.packet.getData());

    this.inputPackets = new ArrayList();
    this.inputSockets = new ArrayList();
    this.inputAction = new ActionReceivedPacket();

    new NetHost("NoName");
    this.fileTransport = new NetFileTransport(6);
    this.fileServerDef = new NetFileServerDef(7);
  }

  public static NetEnv cur()
  {
    return RTSConf.cur.netEnv;
  }

  class ActionReceivedPacket extends MsgAction
  {
    private long lastResTime = 0L;

    ActionReceivedPacket()
    {
    }

    public void doAction()
    {
      while (NetEnv.this.inputPackets.size() > 0) {
        NetPacket localNetPacket = (NetPacket)NetEnv.this.inputPackets.get(0);

        if (NetEnv.bTestTransfer) {
          int i = 0;
          if (NetEnv.testSpeed > 0.0F)
            i = (int)(localNetPacket.getLength() / (NetEnv.testSpeed / 1000.0F));
          if (NetEnv.testMaxLag > 0)
            i += NetEnv.testMinLag + (int)(Math.random() * (NetEnv.testMaxLag - NetEnv.testMinLag));
          if (i > 0) {
            if (localNetPacket.time + i < this.lastResTime) i = (int)(this.lastResTime - localNetPacket.time);
            if (Time.currentReal() < localNetPacket.time + i) {
              if (!busy())
                post(64, this, 0.005D);
              return;
            }
            if ((float)Math.random() < NetEnv.testDown) {
              NetEnv.this.inputPackets.remove(0);
              NetEnv.this.inputSockets.remove(0);
              continue;
            }
            localNetPacket.time += i;
            this.lastResTime = localNetPacket.time;
          }

        }

        NetEnv.this.inputPackets.remove(0);
        NetSocket localNetSocket = (NetSocket)NetEnv.this.inputSockets.get(0);
        NetEnv.this.inputSockets.remove(0);
        int j = localNetPacket.getLength();
        if ((j > 1) && (localNetSocket.isOpen()) && 
          (!NetEnv.this.receivedPacket(localNetSocket, localNetPacket, localNetPacket.time)))
          NetEnv.this.receivedExtPacket(localNetSocket, localNetPacket);
      }
    }

    public void activate()
    {
      if (!busy())
        post(64, this, 0.0D);
    }
  }
}