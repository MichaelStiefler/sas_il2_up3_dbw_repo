package com.maddox.rts.net;

import com.maddox.rts.Destroy;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgNetAskNakListener;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map.Entry;

public class NetFileTransport extends NetObj
  implements MsgNetAskNakListener
{
  private static final boolean bDEBUG = false;
  public static int MSG_DEFAULT_COUNT = 2;
  public static int MSG_DEFAULT_SIZE = 64;

  private int nextLocalId = 0;

  private HashMapExt chRequest = new HashMapExt();

  private HashMapExt chAnswer = new HashMapExt();

  private HashMapExt chMsg = new HashMapExt();

  private HashMapExt chMsgParam = new HashMapExt();

  private boolean bCheckDestroyed = false;
  private static final int ID_RA_SPAWN_BEGIN = 1;
  private static final int ID_RA_SPAWN_NAME = 2;
  private static final int ID_RA_DATA = 0;
  private static final int ID_RA_CANCEL = 3;
  private static final int ID_AR_DATA = 0;
  private static final int ID_AR_EXT_DATA = 1;
  private static final int ID_AR_SUCCESS = 2;
  private static final int ID_AR_DISCONNECT = 3;
  private static final int ID_AR_NOT_FOUND = 4;
  private static final int ID_AR_IO = 5;
  private int _curMaxMsgSize;

  private void DEBUG(String paramString)
  {
  }

  private void DEBUGA(int paramInt, String paramString)
  {
  }

  private void DEBUGR(int paramInt, String paramString)
  {
  }

  public static int MSG_FULL_SIZE(int paramInt)
  {
    return paramInt + 4;
  }

  public void messageSetParams(NetChannel paramNetChannel, int paramInt1, int paramInt2)
  {
    if (paramInt1 < 1) paramInt1 = 1;
    if (paramInt1 > 64) paramInt1 = 64;
    if (paramInt2 < 32) paramInt2 = 32;
    if (paramInt2 > 250) paramInt2 = 250;
    paramInt2 &= -2;
    MsgParam localMsgParam = (MsgParam)this.chMsgParam.get(paramNetChannel);
    if (localMsgParam == null)
      localMsgParam = new MsgParam();
    localMsgParam.count = paramInt1;
    localMsgParam.size = paramInt2;
    this.chMsgParam.put(paramNetChannel, localMsgParam);
  }

  public int messageCount(NetChannel paramNetChannel) {
    MsgParam localMsgParam = (MsgParam)this.chMsgParam.get(paramNetChannel);
    return localMsgParam.count;
  }

  public int messageSize(NetChannel paramNetChannel) {
    MsgParam localMsgParam = (MsgParam)this.chMsgParam.get(paramNetChannel);
    return localMsgParam.size;
  }

  public void doRequest(NetFileRequest paramNetFileRequest)
  {
    new Request(paramNetFileRequest, this.nextLocalId & 0xFFFFFFF);
    this.nextLocalId += 2;
  }
  public void doCancel(NetFileRequest paramNetFileRequest) {
    NetChannel localNetChannel = paramNetFileRequest.owner().masterChannel();
    ArrayList localArrayList = (ArrayList)this.chRequest.get(localNetChannel);
    int j = localArrayList.size();
    for (int i = 0; i < j; i++) {
      Request localRequest = (Request)localArrayList.get(i);
      if (localRequest.nreq.equals(paramNetFileRequest)) {
        localRequest.cancel();
        break;
      }
    }
    this.bCheckDestroyed = true;
    requestPostMsg(localNetChannel);
  }

  private void doAnswer(NetFileRequest paramNetFileRequest) {
    if (((paramNetFileRequest.server() instanceof Destroy)) && (((Destroy)paramNetFileRequest.server()).isDestroyed())) return;
    paramNetFileRequest.server().doAnswer(paramNetFileRequest);
  }

  public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException
  {
    NetChannel localNetChannel = paramNetMsgInput.channel();
    if (this.chAnswer.get(localNetChannel) == null) return true;
    int i = paramNetMsgInput.readInt();
    int j = i >> 28 & 0xF;
    i &= 268435455;
    int k = (i & 0x1) == 1 ? 1 : 0;
    i &= -2;
    ArrayList localArrayList;
    int n;
    int i1;
    Object localObject;
    if (k != 0) {
      DEBUGA(i, "input message...");
      if (j == 1)
      {
        DEBUG("spawn Answer");
        int m = paramNetMsgInput.readUnsignedByte();
        NetObj localNetObj = paramNetMsgInput.readNetObj();
        NetFileServer localNetFileServer = (NetFileServer)paramNetMsgInput.readNetObj();
        int i2 = paramNetMsgInput.readUnsignedShort();
        new Answer(localNetChannel, m, localNetObj, localNetFileServer, i, i2);
      } else {
        localArrayList = (ArrayList)this.chAnswer.get(localNetChannel);
        if (localArrayList == null) return true;
        n = localArrayList.size();
        for (i1 = 0; i1 < n; i1++) {
          localObject = (Answer)localArrayList.get(i1);
          if (((Answer)localObject).remoteId == i) {
            ((Answer)localObject).netInput(paramNetMsgInput, j);
            break;
          }
        }
      }
    } else {
      DEBUGR(i, "input message...");
      localArrayList = (ArrayList)this.chRequest.get(localNetChannel);
      if (localArrayList == null) return true;
      n = localArrayList.size();
      for (i1 = 0; i1 < n; i1++) {
        localObject = (Request)localArrayList.get(i1);
        if (((Request)localObject).localId == i) {
          ((Request)localObject).netInput(paramNetMsgInput, j);
          break;
        }
      }
    }
    doCheckDestroyed();
    requestPostMsg(paramNetMsgInput.channel());
    return true;
  }

  private NetMsgGuaranted getMsgOut(NetChannel paramNetChannel) {
    if (paramNetChannel.isDestroying()) return null;
    MsgParam localMsgParam = (MsgParam)this.chMsgParam.get(paramNetChannel);
    this._curMaxMsgSize = localMsgParam.size;
    int i = localMsgParam.count;
    ArrayList localArrayList = (ArrayList)this.chMsg.get(paramNetChannel);
    int j = localArrayList.size() - i;
    NetMsgGuaranted localNetMsgGuaranted;
    if (j > 0) {
      for (k = 0; k < localArrayList.size(); k++) {
        localNetMsgGuaranted = (NetMsgGuaranted)localArrayList.get(k);
        if (!localNetMsgGuaranted.isLocked()) {
          localArrayList.remove(k);
          k--;
          j--;
          if (j == 0) break;
        }
      }
    }
    else if (j < 0) {
      j = -j;
      for (k = 0; k < j; k++) {
        localNetMsgGuaranted = new NetMsgGuaranted(MSG_FULL_SIZE(this._curMaxMsgSize));
        localNetMsgGuaranted.setRequiredAsk(true);
        localArrayList.add(localNetMsgGuaranted);
      }
    }
    j = localArrayList.size();
    for (int k = 0; k < j; k++) {
      localNetMsgGuaranted = (NetMsgGuaranted)localArrayList.get(k);
      if (!localNetMsgGuaranted.isLocked())
        return localNetMsgGuaranted;
    }
    return null;
  }

  private void requestPostMsg(NetChannel paramNetChannel)
  {
    NetMsgGuaranted localNetMsgGuaranted = getMsgOut(paramNetChannel);
    if (localNetMsgGuaranted == null) return;
    new MsgAction(true, paramNetChannel) {
      public void doAction(Object paramObject) { NetChannel localNetChannel = (NetChannel)paramObject;
        if (localNetChannel.isDestroying()) return;
        NetMsgGuaranted localNetMsgGuaranted = NetFileTransport.this.getMsgOut(localNetChannel);
        if (localNetMsgGuaranted == null) return;
        NetFileTransport.this.doNetOutput(localNetMsgGuaranted, localNetChannel); } } ;
  }

  private void doNetOutput(NetMsgGuaranted paramNetMsgGuaranted, NetChannel paramNetChannel) {
    while (true) {
      paramNetMsgGuaranted = getMsgOut(paramNetChannel);
      if (paramNetMsgGuaranted == null) break;
      int i = this._curMaxMsgSize;
      boolean bool = false;
      try {
        paramNetMsgGuaranted.clear();
        ArrayList localArrayList = (ArrayList)this.chRequest.get(paramNetChannel);
        int j = localArrayList.size();
        for (int k = 0; k < j; k++) {
          Request localRequest = (Request)localArrayList.get(k);
          if ((bool = localRequest.netOutput(paramNetMsgGuaranted, i)))
            break;
        }
        int m;
        Answer localAnswer;
        if (!bool) {
          localArrayList = (ArrayList)this.chAnswer.get(paramNetChannel);
          j = localArrayList.size();
          for (m = 0; m < j; m++) {
            localAnswer = (Answer)localArrayList.get(m);
            if ((!localAnswer.nreq.server().isStateDataTransfer(localAnswer.nreq)) && 
              ((bool = localAnswer.netOutput(paramNetMsgGuaranted, i))))
              break;
          }
        }
        if (!bool) {
          localArrayList = (ArrayList)this.chAnswer.get(paramNetChannel);
          j = localArrayList.size();
          for (m = 0; m < j; m++) {
            localAnswer = (Answer)localArrayList.get(m);
            if ((bool = localAnswer.netOutput(paramNetMsgGuaranted, i)))
              break;
          }
        }
        if (bool)
          postTo(paramNetChannel, paramNetMsgGuaranted);
        doCheckDestroyed();
      } catch (Exception localException) {
        NetObj.printDebug(localException);
        requestPostMsg(paramNetChannel);
        bool = false;
      }
      if (!bool)
        break;
    }
  }

  public void msgNetAsk(NetMsgGuaranted paramNetMsgGuaranted, NetChannel paramNetChannel)
  {
    doNetOutput(paramNetMsgGuaranted, paramNetChannel);
  }

  public void msgNetNak(NetMsgGuaranted paramNetMsgGuaranted, NetChannel paramNetChannel)
  {
  }

  public void msgNetDelChannel(NetChannel paramNetChannel)
  {
    delChannel(paramNetChannel);
  }

  public void msgNetNewChannel(NetChannel paramNetChannel) {
    newChannel(paramNetChannel);
  }

  private void delChannel(NetChannel paramNetChannel) {
    ArrayList localArrayList = (ArrayList)this.chAnswer.get(paramNetChannel);
    int i;
    int j;
    Object localObject;
    if (localArrayList != null) {
      i = localArrayList.size();
      for (j = 0; j < i; j++) {
        localObject = (Answer)localArrayList.get(j);
        ((Answer)localObject).delChannel();
      }
      localArrayList.clear();
      this.chAnswer.remove(paramNetChannel);
    }

    localArrayList = (ArrayList)this.chRequest.get(paramNetChannel);
    if (localArrayList != null) {
      i = localArrayList.size();
      for (j = 0; j < i; j++) {
        localObject = (Request)localArrayList.get(j);
        ((Request)localObject).delChannel();
      }
      localArrayList.clear();
      this.chRequest.remove(paramNetChannel);
    }

    this.chMsg.remove(paramNetChannel);
    this.chMsgParam.remove(paramNetChannel);

    doCheckDestroyed();
  }

  private void newChannel(NetChannel paramNetChannel) {
    if (this.chRequest.containsKey(paramNetChannel)) return;
    if ((paramNetChannel instanceof NetChannelInStream)) return;
    this.chRequest.put(paramNetChannel, new ArrayList());
    this.chAnswer.put(paramNetChannel, new ArrayList());
    messageSetParams(paramNetChannel, MSG_DEFAULT_COUNT, MSG_DEFAULT_SIZE);
    this.chMsg.put(paramNetChannel, new ArrayList());
  }

  private void doCheckDestroyed() {
    if (!this.bCheckDestroyed) return;
    this.bCheckDestroyed = false;

    Map.Entry localEntry = this.chAnswer.nextEntry(null);
    ArrayList localArrayList;
    int i;
    Object localObject;
    while (localEntry != null) {
      localArrayList = (ArrayList)localEntry.getValue();
      for (i = 0; i < localArrayList.size(); i++) {
        localObject = (Answer)localArrayList.get(i);
        if (((Answer)localObject).isDestroyed()) {
          localArrayList.remove(i);
          i--;
        }
      }
      localEntry = this.chAnswer.nextEntry(localEntry);
    }

    localEntry = this.chRequest.nextEntry(null);
    while (localEntry != null) {
      localArrayList = (ArrayList)localEntry.getValue();
      for (i = 0; i < localArrayList.size(); i++) {
        localObject = (Request)localArrayList.get(i);
        if (((Request)localObject).isDestroyed()) {
          localArrayList.remove(i);
          i--;
        }
      }
      localEntry = this.chRequest.nextEntry(localEntry);
    }
  }

  public void destroy() {
    super.destroy();
  }

  public void typeStates(PrintStream paramPrintStream) {
    Map.Entry localEntry = this.chAnswer.nextEntry(null);
    ArrayList localArrayList;
    int i;
    Object localObject;
    while (localEntry != null) {
      localArrayList = (ArrayList)localEntry.getValue();
      for (i = 0; i < localArrayList.size(); i++) {
        localObject = (Answer)localArrayList.get(i);
        ((Answer)localObject).typeState(paramPrintStream);
      }
      localEntry = this.chAnswer.nextEntry(localEntry);
    }

    localEntry = this.chRequest.nextEntry(null);
    while (localEntry != null) {
      localArrayList = (ArrayList)localEntry.getValue();
      for (i = 0; i < localArrayList.size(); i++) {
        localObject = (Request)localArrayList.get(i);
        ((Request)localObject).typeState(paramPrintStream);
      }
      localEntry = this.chRequest.nextEntry(localEntry);
    }
  }

  public NetFileTransport(int paramInt) {
    super(null, paramInt);
  }

  public class Request
    implements Destroy
  {
    public Request parent;
    public NetChannel channel;
    public NetFileRequest nreq;
    public int localId;
    private static final int ST_INIT = 0;
    private static final int ST_SEND_NAME = 1;
    private static final int ST_SEND_DATA = 2;
    private static final int ST_SEND_CANCEL = 3;
    private static final int ST_WAIT_PARENT = 4;
    private static final int ST_DESTROY = -1;
    private int state = 0;
    private boolean bCanceled = false;

    private int strOffset = 0;

    public void typeState(PrintStream paramPrintStream)
    {
      String str = null;
      switch (this.state) { case 0:
        str = "Init"; break;
      case 4:
        str = "Wait Parent Request"; break;
      case 1:
        str = "Send Name"; break;
      case 2:
        str = "Send Data"; break;
      case 3:
        str = "Send Cancel"; break;
      case -1:
        str = "Destroy"; break;
      default:
        str = "UNKNOWN";
      }
      paramPrintStream.println("Request(" + this.localId + ") state = " + str);
      paramPrintStream.println("  request: " + this.nreq.toString());
      if (this.nreq._serverIn != null)
        paramPrintStream.println("  serverIn:  " + this.nreq.server().getStateInInfo(this.nreq));
      if (this.nreq._serverOut != null)
        paramPrintStream.println("  serverOut: " + this.nreq.server().getStateOutInfo(this.nreq));
    }

    public void netInput(NetMsgInput paramNetMsgInput, int paramInt) throws IOException {
      if (this.parent != null) return;
      if (isDestroyed()) return;
      if ((this.state == 3) || (this.state == 0) || (this.state == 4))
      {
        return;
      }
      int i = 1;
      int j = 1;
      switch (paramInt) {
      case 0:
        NetFileTransport.this.DEBUGR(this.localId, "get data");
        j = 0;
        i = this.nreq.server().getAnswerData(this.nreq, paramNetMsgInput);
        break;
      case 1:
        NetFileTransport.this.DEBUGR(this.localId, "get ext data");
        j = 0;
        i = this.nreq.server().getAnswerExtData(this.nreq, paramNetMsgInput);
        break;
      case 2:
        NetFileTransport.this.DEBUGR(this.localId, "get 'success'");
        i = 0;
        break;
      case 3:
        NetFileTransport.this.DEBUGR(this.localId, "get 'owner disconnect'");
        i = -1;
        break;
      case 4:
        NetFileTransport.this.DEBUGR(this.localId, "get 'not found'");
        i = -2;
        break;
      case 5:
        NetFileTransport.this.DEBUGR(this.localId, "get 'io error'");
        i = -3;
        break;
      default:
        return;
      }
      if (i == 1) {
        return;
      }
      if (j != 0)
        destroy();
      else {
        this.state = 3;
      }
      _doAnswer(i);
    }

    private void _doAnswer(int paramInt) {
      this.nreq.setState(paramInt);
      NetFileTransport.this.doAnswer(this.nreq);
      if (this.parent == null) {
        ArrayList localArrayList = (ArrayList)NetFileTransport.this.chRequest.get(this.channel);
        int i = localArrayList.size();
        for (int j = 0; j < i; j++) {
          Request localRequest = (Request)localArrayList.get(j);
          if ((localRequest.parent == this) && (!localRequest.isDestroyed())) {
            localRequest.destroy();
            localRequest.nreq.setState(paramInt);
            localRequest.nreq.setLocalFileName(this.nreq.localFileName());
            NetFileTransport.this.doAnswer(localRequest.nreq);
          }
        }
      }
    }

    public void delChannel() {
      if (isDestroyed()) return;
      destroy();
      _doAnswer(-1);
    }
    public void cancel() {
      if (isDestroyed()) return;
      this.bCanceled = true;
      if (this.parent == null) {
        int i = 0;
        ArrayList localArrayList = (ArrayList)NetFileTransport.this.chRequest.get(this.channel);
        int j = localArrayList.size();
        for (int k = 0; k < j; k++) {
          Request localRequest2 = (Request)localArrayList.get(k);
          if ((localRequest2.parent == this) && (!localRequest2.isDestroyed())) {
            i = 1;
            break;
          }
        }
        if (i == 0) {
          if (this.state == 0)
            destroy();
          else {
            this.state = 3;
          }
        }
        if (!this.nreq.isEnded()) {
          this.nreq.setState(-4);
          NetFileTransport.this.doAnswer(this.nreq);
        }
      } else {
        Request localRequest1 = this.parent;
        destroy();
        if (!this.nreq.isEnded()) {
          this.nreq.setState(-4);
          NetFileTransport.this.doAnswer(this.nreq);
        }
        if (localRequest1.bCanceled)
          localRequest1.cancel(); 
      }
    }

    public boolean netOutput(NetMsgGuaranted paramNetMsgGuaranted, int paramInt) throws IOException {
      if (this.parent != null) return false;
      switch (this.state) {
      case 4:
      default:
        return false;
      case 0:
        NetFileTransport.this.DEBUGR(this.localId, "send 'spawn begin'");
        writeId(paramNetMsgGuaranted, 1);
        paramNetMsgGuaranted.writeByte(this.nreq.prior());
        paramNetMsgGuaranted.writeNetObj(this.nreq.owner());
        paramNetMsgGuaranted.writeNetObj((NetObj)this.nreq.server());
        paramNetMsgGuaranted.writeShort(this.nreq.ownerFileName().length());
        this.state = 1;
        return true;
      case 1:
        NetFileTransport.this.DEBUGR(this.localId, "send 'name'");
        writeId(paramNetMsgGuaranted, 2);
        if (writeStr(paramNetMsgGuaranted, paramInt, this.nreq.ownerFileName()))
          this.state = 2;
        return true;
      case 2:
        boolean bool = this.nreq.server().sendRequestData(this.nreq, paramNetMsgGuaranted, paramInt, 0x0 | this.localId | 0x1);
        if (bool)
          NetFileTransport.this.DEBUGR(this.localId, "send data");
        return bool;
      case 3:
      }NetFileTransport.this.DEBUGR(this.localId, "send 'cancel'");
      writeId(paramNetMsgGuaranted, 3);
      destroy();
      return true;
    }

    private void writeId(NetMsgGuaranted paramNetMsgGuaranted, int paramInt) throws IOException {
      paramNetMsgGuaranted.writeInt(paramInt << 28 | this.localId | 0x1);
    }
    private boolean writeStr(NetMsgGuaranted paramNetMsgGuaranted, int paramInt, String paramString) throws IOException {
      int i = paramString.length() * 2;
      int j = 0;
      while ((j < paramInt) && (j + this.strOffset < i)) {
        int k = (j + this.strOffset) / 2;
        int m = paramString.charAt(k);
        paramNetMsgGuaranted.writeByte(m & 0xFF);
        paramNetMsgGuaranted.writeByte(m >> 8 & 0xFF);
        j += 2;
      }
      this.strOffset += j;
      return this.strOffset == i;
    }

    public boolean isDestroyed() {
      return this.state == -1;
    }
    public void destroy() { if (isDestroyed()) return;
      this.nreq.server().destroyIn(this.nreq);
      this.state = -1;
      NetFileTransport.access$202(NetFileTransport.this, true); }

    public Request(NetFileRequest paramInt, int arg3) {
      this.nreq = paramInt;
      int i;
      this.localId = i;
      this.channel = paramInt.owner().masterChannel();
      ArrayList localArrayList = (ArrayList)NetFileTransport.this.chRequest.get(this.channel);
      int k = localArrayList.size();
      Request localRequest;
      for (int j = 0; j < k; j++) {
        localRequest = (Request)localArrayList.get(j);
        if ((localRequest.parent == null) && (localRequest.nreq.equals(paramInt))) {
          this.parent = localRequest;
          break;
        }
      }
      for (j = 0; j < k; j++) {
        localRequest = (Request)localArrayList.get(j);
        if (paramInt.prior() > localRequest.nreq.prior()) {
          localArrayList.add(j, this);
          break;
        }
      }
      if (j == k)
        localArrayList.add(this);
      if (this.parent == null)
        NetFileTransport.this.requestPostMsg(this.channel);
      else
        this.state = 4;
    }
  }

  public class Answer
    implements NetFileClient, Destroy
  {
    public NetChannel channel;
    public NetFileRequest nreq;
    public int ownerFileNameLength;
    public int remoteId;
    private static final int ST_INIT = 0;
    private static final int ST_TRANSFER = 1;
    private static final int ST_DISCONNECT = 2;
    private static final int ST_NOT_FOUND = 3;
    private static final int ST_IO = 4;
    private static final int ST_DESTROY = -1;
    private int state = 0;

    public void typeState(PrintStream paramPrintStream) {
      String str = null;
      switch (this.state) { case 0:
        str = "Init"; break;
      case 1:
        str = "Transfer"; break;
      case 2:
        str = "Owner Disconnect"; break;
      case 3:
        str = "Not Found"; break;
      case 4:
        str = "IO Error"; break;
      case -1:
        str = "Destroy"; break;
      default:
        str = "UNKNOWN";
      }
      paramPrintStream.println("Answer(" + this.remoteId + ") state = " + str);
      paramPrintStream.println("  request: " + this.nreq.toString());
      if (this.nreq._serverIn != null)
        paramPrintStream.println("  serverIn:  " + this.nreq.server().getStateInInfo(this.nreq));
      if (this.nreq._serverOut != null)
        paramPrintStream.println("  serverOut: " + this.nreq.server().getStateOutInfo(this.nreq));
    }

    public void netInput(NetMsgInput paramNetMsgInput, int paramInt) throws IOException {
      if (isDestroyed()) return;
      if (paramInt == 3) {
        NetFileTransport.this.DEBUGA(this.remoteId, "input 'cancel'");
        delChannel();
        return;
      }
      switch (this.state) {
      case 0:
        if (paramInt != 2) break;
        String str = readStr(paramNetMsgInput);

        if (this.nreq.ownerFileName == null) this.nreq.ownerFileName = str; else
          this.nreq.ownerFileName += str;
        if (this.ownerFileNameLength != this.nreq.ownerFileName.length()) break;
        this.state = 1;
        this.nreq.doRequest(); break;
      case 1:
        if (paramInt != 0) break;
        NetFileTransport.this.DEBUGA(this.remoteId, "input data");
        this.nreq.server().getRequestData(this.nreq, paramNetMsgInput);
      }
    }

    public void delChannel()
    {
      destroy();
      if (((this.nreq.server() instanceof Destroy)) && (((Destroy)this.nreq.server()).isDestroyed())) return;
      if (this.nreq.state() == 1)
        this.nreq.doCancel(); 
    }

    public void netFileAnswer(NetFileRequest paramNetFileRequest) {
      switch (paramNetFileRequest.state()) {
      case 0:
      default:
        break;
      case -1:
        this.state = 2;
        break;
      case -2:
        this.state = 3;
        break;
      case -3:
        this.state = 4;
      }

      NetFileTransport.this.requestPostMsg(this.channel);
    }

    public boolean netOutput(NetMsgGuaranted paramNetMsgGuaranted, int paramInt) throws IOException {
      switch (this.state) {
      case 1:
        int i = this.nreq.server().getAnswerState(this.nreq, paramInt);
        switch (i) {
        case 0:
        default:
          break;
        case 1:
          NetFileTransport.this.DEBUGA(this.remoteId, "send data");
          this.nreq.server().sendAnswerData(this.nreq, paramNetMsgGuaranted, paramInt, 0x0 | this.remoteId);
          return true;
        case 2:
          NetFileTransport.this.DEBUGA(this.remoteId, "send ext data");
          this.nreq.server().sendAnswerData(this.nreq, paramNetMsgGuaranted, paramInt, 0x10000000 | this.remoteId);
          return true;
        case 3:
          NetFileTransport.this.DEBUGA(this.remoteId, "send 'success'");
          writeId(paramNetMsgGuaranted, 2);
          destroy();
          return true;
        case 4:
          NetFileTransport.this.DEBUGA(this.remoteId, "send 'io error 0'");
          writeId(paramNetMsgGuaranted, 5);
          destroy();
          return true;
        }

      case 2:
        NetFileTransport.this.DEBUGA(this.remoteId, "send 'owner disconnect'");
        writeId(paramNetMsgGuaranted, 3);
        destroy();
        return true;
      case 3:
        NetFileTransport.this.DEBUGA(this.remoteId, "send 'not found'");
        writeId(paramNetMsgGuaranted, 4);
        destroy();
        return true;
      case 4:
        NetFileTransport.this.DEBUGA(this.remoteId, "send 'io error 1'");
        writeId(paramNetMsgGuaranted, 5);
        destroy();
        return true;
      }

      return false;
    }
    private void writeId(NetMsgGuaranted paramNetMsgGuaranted, int paramInt) throws IOException {
      paramNetMsgGuaranted.writeInt(paramInt << 28 | this.remoteId);
    }
    private String readStr(NetMsgInput paramNetMsgInput) throws IOException {
      StringBuffer localStringBuffer = new StringBuffer();
      while (paramNetMsgInput.available() > 0) {
        int i = paramNetMsgInput.readUnsignedByte() | paramNetMsgInput.readUnsignedByte() << 8;
        localStringBuffer.append((char)i);
      }
      return localStringBuffer.toString();
    }
    public boolean isDestroyed() {
      return this.state == -1;
    }
    public void destroy() { if (isDestroyed()) return;
      this.nreq.server().destroyOut(this.nreq);
      this.state = -1;
      NetFileTransport.access$202(NetFileTransport.this, true);
    }

    public Answer(NetChannel paramInt1, int paramNetObj, NetObj paramNetFileServer, NetFileServer paramInt2, int paramInt3, int arg7)
    {
      this.channel = paramInt1;
      this.remoteId = paramInt3;
      int i;
      this.ownerFileNameLength = i;
      this.nreq = new NetFileRequest(this, paramInt2, null, paramNetObj, paramNetFileServer, null);

      ArrayList localArrayList = (ArrayList)NetFileTransport.this.chAnswer.get(paramInt1);
      int j = localArrayList.size();
      for (int k = 0; k < j; k++) {
        Answer localAnswer = (Answer)localArrayList.get(k);
        if (this.nreq.prior() > localAnswer.nreq.prior()) {
          localArrayList.add(k, this);
          return;
        }
      }
      localArrayList.add(this);
    }
  }

  static class MsgParam
  {
    int count;
    int size;
  }
}