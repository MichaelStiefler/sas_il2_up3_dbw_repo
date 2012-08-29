package com.maddox.rts.net;

import com.maddox.rts.Compress;
import com.maddox.rts.Destroy;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.util.HashMapExt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;

public class NetFileServerDef extends NetObj
  implements NetFileServer
{
  protected HashMapExt fileCache = new HashMapExt();
  private byte[] commonBuf;

  public int compressMethod()
  {
    return 2; } 
  public int compressBlockSize() { return 32768; } 
  public String primaryPath() { return null; } 
  public String alternativePath() { return null;
  }

  protected byte[] commonBuf(int paramInt)
  {
    if ((this.commonBuf == null) || (this.commonBuf.length < paramInt))
      this.commonBuf = new byte[paramInt];
    return this.commonBuf;
  }

  public void destroyIn(NetFileRequest paramNetFileRequest)
  {
    NetFileRequest localNetFileRequest = paramNetFileRequest;
    if ((localNetFileRequest._serverIn == null) || (!(localNetFileRequest._serverIn instanceof In)))
      return;
    ((In)localNetFileRequest._serverIn).destroy();
  }
  public void destroyOut(NetFileRequest paramNetFileRequest) {
    NetFileRequest localNetFileRequest = paramNetFileRequest;
    if ((localNetFileRequest._serverOut == null) || (!(localNetFileRequest._serverIn instanceof Out)))
      return;
    ((Out)localNetFileRequest._serverOut).destroy();
  }

  public String getStateInInfo(NetFileRequest paramNetFileRequest) {
    NetFileRequest localNetFileRequest = paramNetFileRequest;
    if ((localNetFileRequest._serverIn == null) || (!(localNetFileRequest._serverIn instanceof In)))
      return null;
    return ((In)localNetFileRequest._serverIn).toString();
  }
  public String getStateOutInfo(NetFileRequest paramNetFileRequest) {
    NetFileRequest localNetFileRequest = paramNetFileRequest;
    if ((localNetFileRequest._serverOut == null) || (!(localNetFileRequest._serverOut instanceof Out)))
      return null;
    return ((Out)localNetFileRequest._serverOut).toString();
  }

  public boolean isStateDataTransfer(NetFileRequest paramNetFileRequest) {
    NetFileRequest localNetFileRequest = paramNetFileRequest;
    if ((localNetFileRequest._serverOut == null) || (!(localNetFileRequest._serverOut instanceof Out)))
      return false;
    Out localOut = (Out)localNetFileRequest._serverOut;
    return (localOut.state == 3) || (localOut.state == 4);
  }

  public void doRequest(NetFileRequest paramNetFileRequest) {
    NetFileRequest localNetFileRequest = paramNetFileRequest;
    NetFile localNetFile1 = new NetFile(localNetFileRequest.owner(), localNetFileRequest.ownerFileName());
    NetFile localNetFile2 = (NetFile)this.fileCache.get(localNetFile1);
    if (localNetFile2 != null) {
      localNetFileRequest.setComplete(1.0F);
      localNetFileRequest.setLocalFileName(localNetFile2.localFileName);
      localNetFileRequest.setState(0);
      initServerOutData(localNetFileRequest);
      localNetFileRequest.doAnswer();
      return;
    }
    if (localNetFileRequest.owner().isMaster()) {
      String str = filePrimaryName(localNetFileRequest.ownerFileName());
      if (isFileExist(str)) {
        makeRequestComplete(localNetFileRequest, localNetFileRequest.ownerFileName(), Finger.file(0L, str, -1));
        localNetFileRequest.doAnswer();
      } else {
        localNetFileRequest.setState(-2);
        localNetFileRequest.doAnswer();
      }
    }
    else if ((localNetFileRequest.owner().masterChannel() instanceof NetChannelInStream)) {
      localNetFileRequest.setState(-2);
      localNetFileRequest.doAnswer();
    } else {
      localNetFileRequest.setState(1);
      localNetFileRequest._serverIn = new In();
      NetEnv.cur().fileTransport.doRequest(localNetFileRequest);
    }
  }

  protected void makeRequestComplete(NetFileRequest paramNetFileRequest, String paramString, long paramLong)
  {
    paramNetFileRequest.setLocalFileName(paramString);
    NetFile localNetFile = new NetFile(paramNetFileRequest.owner(), paramNetFileRequest.ownerFileName(), paramLong, paramNetFileRequest.localFileName());

    this.fileCache.put(localNetFile, localNetFile);
    paramNetFileRequest.setComplete(1.0F);
    paramNetFileRequest.setState(0);
    initServerOutData(paramNetFileRequest);
  }

  public void doCancel(NetFileRequest paramNetFileRequest) {
    NetFileRequest localNetFileRequest = paramNetFileRequest;
    if (localNetFileRequest.isEnded())
      return;
    localNetFileRequest.setState(-4);
    localNetFileRequest.doAnswer();
    if ((localNetFileRequest.owner().isDestroyed()) || (localNetFileRequest.owner().isMaster()))
      return;
    NetEnv.cur().fileTransport.doCancel(localNetFileRequest);
  }

  public void doAnswer(NetFileRequest paramNetFileRequest) {
    initServerOutData(paramNetFileRequest);
    paramNetFileRequest.doAnswer();
  }

  private void initServerOutData(NetFileRequest paramNetFileRequest) {
    if (paramNetFileRequest.state() != 0) return;
    if (paramNetFileRequest.client() == null) return;
    if (!(paramNetFileRequest.client() instanceof NetFileTransport.Answer)) return;
    if (((paramNetFileRequest.client() instanceof Destroy)) && (((Destroy)paramNetFileRequest.client()).isDestroyed()))
      return;
    paramNetFileRequest._serverOut = new Out();
  }

  protected In netIn(NetFileRequest paramNetFileRequest) {
    if (paramNetFileRequest._serverIn == null) return null;
    return (In)paramNetFileRequest._serverIn;
  }
  protected Out netOut(NetFileRequest paramNetFileRequest) {
    if (paramNetFileRequest._serverOut == null) return null;
    return (Out)paramNetFileRequest._serverOut;
  }

  public void getRequestData(NetFileRequest paramNetFileRequest, NetMsgInput paramNetMsgInput) throws IOException
  {
    NetFileRequest localNetFileRequest = paramNetFileRequest;
    Out localOut = netOut(localNetFileRequest);
    if (localOut == null) return;
    if (localOut.state != 1) return;
    int i = paramNetMsgInput.readInt();
    if (paramNetMsgInput.available() == 0) {
      localOut.ptr = i;
      if (compressMethod() != 0)
        localOut.state = 3;
      else
        localOut.state = 4;
    }
    else {
      long l = paramNetMsgInput.readLong();
      localOut.shortSize = i;
      localOut.shortFinger = Finger.file(0L, localNetFileRequest.localFullFileName(primaryPath(), alternativePath()), i);
      localOut.state = 2;
    }
  }

  public int getAnswerState(NetFileRequest paramNetFileRequest, int paramInt) {
    NetFileRequest localNetFileRequest = paramNetFileRequest;
    Out localOut = netOut(localNetFileRequest);
    if (localOut == null) return 0;
    switch (localOut.state) {
    case -1:
    case 1:
    default:
      return 0;
    case 0:
      localOut.size = fileLength(localNetFileRequest.localFullFileName(primaryPath(), alternativePath()));
      if (localOut.size == 0)
        return 4;
      localOut.finger = Finger.file(0L, localNetFileRequest.localFullFileName(primaryPath(), alternativePath()), -1);
      return 2;
    case 2:
      return 2;
    case 3:
      if (localOut.bufSize > 0)
        localOut.ptr += localOut.buf.length;
      if (localOut.ptr >= localOut.size) {
        localOut.ptr = localOut.size;
        return 3;
      }
      if (!localOut.openFile(localNetFileRequest.localFullFileName(primaryPath(), alternativePath())))
        return 4;
      try {
        int i = localOut.buf.length;
        int j = localOut.size - localOut.ptr;
        if (i > j)
          i = j;
        localOut.f.read(localOut.buf, 0, i);
        localOut.bufSize = Compress.code(compressMethod(), localOut.buf, i);
        localOut.bufCur = 0;
      } catch (Exception localException1) {
        NetObj.printDebug(localException1);
        return 4;
      }
      return 2;
    case 4:
    }
    if (localOut.size == localOut.ptr)
      return 3;
    if (!localOut.openFile(localNetFileRequest.localFullFileName(primaryPath(), alternativePath())))
      return 4;
    if (compressMethod() == 0)
    {
      localOut.bufSize = paramInt;
      if (localOut.bufSize > localOut.size - localOut.ptr)
        localOut.bufSize = (localOut.size - localOut.ptr);
      try {
        localOut.f.read(commonBuf(localOut.bufSize), 0, localOut.bufSize);
        localOut.ptr += localOut.bufSize;
      } catch (Exception localException2) {
        NetObj.printDebug(localException2);
        return 4;
      }
    }
    return 1;
  }

  public boolean sendAnswerData(NetFileRequest paramNetFileRequest, NetMsgGuaranted paramNetMsgGuaranted, int paramInt1, int paramInt2)
    throws IOException
  {
    NetFileRequest localNetFileRequest = paramNetFileRequest;
    Out localOut = netOut(localNetFileRequest);
    if (localOut == null) return false;
    switch (localOut.state) { case -1:
    case 1:
    default:
      return false;
    case 0:
      paramNetMsgGuaranted.writeInt(paramInt2);
      paramNetMsgGuaranted.writeInt(localOut.size);
      paramNetMsgGuaranted.writeLong(localOut.finger);
      localOut.state = 1;
      return true;
    case 2:
      paramNetMsgGuaranted.writeInt(paramInt2);
      paramNetMsgGuaranted.writeInt(localOut.shortSize);
      paramNetMsgGuaranted.writeLong(localOut.shortFinger);
      localOut.state = 1;
      return true;
    case 3:
      paramNetMsgGuaranted.writeInt(paramInt2);
      paramNetMsgGuaranted.writeInt(localOut.bufSize);
      localOut.state = 4;
      return true;
    case 4: }
    paramNetMsgGuaranted.writeInt(paramInt2);
    if (compressMethod() != 0) {
      int i = paramInt1;
      if (i > localOut.bufSize - localOut.bufCur)
        i = localOut.bufSize - localOut.bufCur;
      paramNetMsgGuaranted.write(localOut.buf, localOut.bufCur, i);
      localOut.bufCur += i;
      if (localOut.bufCur == localOut.bufSize)
        localOut.state = 3;
      return true;
    }
    paramNetMsgGuaranted.write(commonBuf(localOut.bufSize), 0, localOut.bufSize);
    return true;
  }

  public int getAnswerData(NetFileRequest paramNetFileRequest, NetMsgInput paramNetMsgInput)
    throws IOException
  {
    NetFileRequest localNetFileRequest = paramNetFileRequest;
    In localIn = netIn(localNetFileRequest);
    if (localIn == null) return 1;
    if (localIn.state != 4) return 1;
    int i = paramNetMsgInput.available();
    if (compressMethod() != 0) {
      paramNetMsgInput.read(localIn.buf, localIn.bufFill, i);
      localIn.bufFill += i;
      if (localIn.bufFill == localIn.bufSize) {
        int j = compressBlockSize();
        if (localIn.size - localIn.localSize < j)
          j = localIn.size - localIn.localSize;
        if (localIn.bufSize < j)
          Compress.decode(compressMethod(), localIn.buf, localIn.bufSize);
        try {
          localIn.f.write(localIn.buf, 0, j);
        } catch (Exception localException2) {
          NetObj.printDebug(localException2);
          return -3;
        }
        localIn.localSize += j;
        localIn.bufFill = 0;
      }
    } else {
      paramNetMsgInput.read(commonBuf(i), 0, i);
      try {
        localIn.f.write(commonBuf(i), 0, i);
      } catch (Exception localException1) {
        NetObj.printDebug(localException1);
        return -3;
      }
      localIn.localSize += i;
    }
    localNetFileRequest.setComplete(localIn.localSize / localIn.size);
    if (localIn.localSize == localIn.size) {
      localIn.destroy();
      makeRequestComplete(localNetFileRequest, localIn.localFileName, localIn.finger);
      return 0;
    }
    return 1;
  }

  public int getAnswerExtData(NetFileRequest paramNetFileRequest, NetMsgInput paramNetMsgInput) throws IOException {
    NetFileRequest localNetFileRequest = paramNetFileRequest;
    In localIn = netIn(localNetFileRequest);
    if (localIn == null) return 1;
    switch (localIn.state) { case -1:
    case 1:
    case 3:
    default:
      break;
    case 0:
      localIn.size = paramNetMsgInput.readInt();
      localIn.finger = paramNetMsgInput.readLong();
      String str = filePrimaryName(localNetFileRequest.ownerFileName());
      int i = fileLength(str);
      long l1;
      if ((i > 0) && 
        (localIn.size == i)) {
        l1 = Finger.file(0L, str, -1);
        if (l1 == localIn.finger) {
          makeRequestComplete(localNetFileRequest, localNetFileRequest.ownerFileName(), localIn.finger);
          return 0;
        }
      }
      localIn.fileName = fileAlternativeName(localNetFileRequest.ownerFileName(), localIn.finger, true);
      localIn.localFileName = fileAlternativeName(localNetFileRequest.ownerFileName(), localIn.finger, false);
      i = fileLength(localIn.fileName);
      if (i > 0) {
        if (i == localIn.size) {
          l1 = Finger.file(0L, localIn.fileName, -1);
          if (l1 == localIn.finger) {
            makeRequestComplete(localNetFileRequest, localIn.localFileName, localIn.finger);
            return 0;
          }
        } else {
          if (i >= localIn.size) break;
          localIn.localSize = i;
          localIn.localFinger = Finger.file(0L, localIn.fileName, -1);
          localIn.state = 1; break;
        }

      }

      localIn.state = 3;
      if (localIn.createFile()) break;
      return -3;
    case 2:
      int j = paramNetMsgInput.readInt();
      long l2 = paramNetMsgInput.readLong();
      if ((j != localIn.localSize) || (l2 != localIn.localFinger))
        localIn.localSize = 0;
      localIn.state = 3;
      if (localIn.createFile()) break;
      return -3;
    case 4:
      if (compressMethod() == 0) break;
      localIn.bufSize = paramNetMsgInput.readInt();
    }

    return 1;
  }

  public boolean sendRequestData(NetFileRequest paramNetFileRequest, NetMsgGuaranted paramNetMsgGuaranted, int paramInt1, int paramInt2)
    throws IOException
  {
    NetFileRequest localNetFileRequest = paramNetFileRequest;
    In localIn = netIn(localNetFileRequest);
    if (localIn == null) return false;
    switch (localIn.state) { case -1:
    case 0:
    case 2:
    case 4:
    default:
      return false;
    case 1:
      paramNetMsgGuaranted.writeInt(paramInt2);
      paramNetMsgGuaranted.writeInt(localIn.localSize);
      paramNetMsgGuaranted.writeLong(localIn.localFinger);
      localIn.state = 2;
      return true;
    case 3: }
    paramNetMsgGuaranted.writeInt(paramInt2);
    paramNetMsgGuaranted.writeInt(localIn.localSize);
    localIn.state = 4;
    return true;
  }

  public void msgNetDelChannel(NetChannel paramNetChannel)
  {
    Map.Entry localEntry = this.fileCache.nextEntry(null);
    ArrayList localArrayList = new ArrayList();
    while (localEntry != null) {
      NetFile localNetFile = (NetFile)localEntry.getKey();
      if ((localNetFile.owner.isDestroyed()) || (localNetFile.owner.masterChannel() == paramNetChannel))
      {
        localArrayList.add(localNetFile);
      }localEntry = this.fileCache.nextEntry(localEntry);
    }
    for (int i = 0; i < localArrayList.size(); i++)
      this.fileCache.remove(localArrayList.get(i));
    localArrayList.clear();
  }

  public void msgNetNewChannel(NetChannel paramNetChannel) {
  }

  protected boolean isFileExist(String paramString) {
    File localFile = new File(HomePath.toFileSystemName(paramString, 0));
    return localFile.exists();
  }

  protected int fileLength(String paramString) {
    File localFile = new File(HomePath.toFileSystemName(paramString, 0));
    return (int)localFile.length();
  }

  protected String filePrimaryName(String paramString) {
    String str = primaryPath();
    if ((str == null) || (str.length() == 0))
      return paramString;
    if ((paramString == null) || (paramString.length() == 0))
      return paramString;
    int i = str.charAt(str.length() - 1);
    if ((i == 47) || (i == 92)) {
      i = paramString.charAt(0);
      if ((i == 47) || (i == 92)) {
        return str.substring(0, str.length() - 1) + paramString;
      }
      return str + paramString;
    }
    return str + '/' + paramString;
  }

  protected String fileAlternativeName(String paramString, long paramLong, boolean paramBoolean)
  {
    String str1 = null;
    if (paramBoolean) {
      str1 = alternativePath();
      if ((str1 != null) && 
        (str1.length() == 0)) {
        str1 = null;
      }
    }
    String str2 = null;
    int i = paramString.lastIndexOf('.');
    if (i >= 0) {
      str2 = paramString.substring(i);
    }

    if (str1 != null) {
      int j = str1.charAt(str1.length() - 1);
      if ((j != 92) && (j != 47)) {
        if (str2 != null) {
          return str1 + "/" + paramLong + str2;
        }
        return str1 + "/" + paramLong;
      }
      if (str2 != null) {
        return str1 + paramLong + str2;
      }
      return str1 + paramLong;
    }

    if (str2 != null) {
      return "" + paramLong + str2;
    }
    return "" + paramLong;
  }

  public NetFileServerDef(int paramInt)
  {
    super(null, paramInt);
  }

  public class Out
    implements Destroy
  {
    public static final int ST_INIT = 0;
    public static final int ST_WAIT_COMMAND = 1;
    public static final int ST_SEND_FINGER = 2;
    public static final int ST_SEND_HEADER = 3;
    public static final int ST_SEND_DATA = 4;
    public static final int ST_DESTROY = -1;
    public int state = 0;
    public int size;
    public int shortSize;
    public long finger;
    public long shortFinger;
    public int ptr;
    public byte[] buf;
    public int bufSize;
    public int bufCur;
    public FileInputStream f;

    public Out()
    {
    }

    public boolean openFile(String paramString)
    {
      if (this.f != null) return true; try
      {
        this.f = new FileInputStream(HomePath.toFileSystemName(paramString, 0));
        if (this.ptr > 0)
          this.f.skip(this.ptr);
      } catch (Exception localException) {
        NetFileServerDef.access$200(localException);
        this.f = null;
        return false;
      }
      if (NetFileServerDef.this.compressMethod() != 0) {
        this.buf = new byte[NetFileServerDef.this.compressBlockSize()];
      }
      return true;
    }
    public boolean isDestroyed() {
      return this.state == -1;
    }
    public void destroy() { if (isDestroyed()) return;
      this.state = -1;
      if (this.f != null) {
        try {
          this.f.close();
        } catch (Exception localException) {
          NetFileServerDef.access$300(localException);
        }
        this.f = null;
      } }

    public String toString() {
      switch (this.state) { case 0:
        return "state Init";
      case 1:
        return "state WaitCommand";
      case 2:
        return "state Send Finger";
      case 3:
        return "state Send Header";
      case 4:
        return "state Transfer";
      case -1:
        return "state Destroy"; }
      return "state UNKNOWN";
    }
  }

  public class In
    implements Destroy
  {
    public static final int ST_INIT = 0;
    public static final int ST_REQUEST_FINGER = 1;
    public static final int ST_ANSWER_FINGER = 2;
    public static final int ST_REQUEST_FILE = 3;
    public static final int ST_TRANSFER = 4;
    public static final int ST_DESTROY = -1;
    public int state = 0;
    public String fileName;
    public String localFileName;
    public int size;
    public int localSize;
    public long finger;
    public long localFinger;
    public byte[] buf;
    public int bufSize;
    public int bufFill;
    public FileOutputStream f;

    public In()
    {
    }

    public boolean createFile()
    {
      try
      {
        if (this.localSize > 0)
          this.f = new FileOutputStream(HomePath.toFileSystemName(this.fileName, 0), true);
        else
          this.f = new FileOutputStream(HomePath.toFileSystemName(this.fileName, 0));
      } catch (Exception localException) {
        NetFileServerDef.access$000(localException);
        return false;
      }
      if (NetFileServerDef.this.compressMethod() != 0)
        this.buf = new byte[NetFileServerDef.this.compressBlockSize()];
      return true;
    }
    public boolean isDestroyed() {
      return this.state == -1;
    }
    public void destroy() { if (isDestroyed()) return;
      this.state = -1;
      if (this.f != null) {
        try {
          this.f.close();
        } catch (Exception localException) {
          NetFileServerDef.access$100(localException);
        }
        this.f = null;
      }
    }

    public String toString()
    {
      String str;
      if (this.fileName != null) str = this.fileName + " "; else
        str = "";
      switch (this.state) { case 0:
        return str + "state Init";
      case 1:
        return str + "state Request Finger";
      case 2:
        return str + "state Answer Finger";
      case 3:
        return str + "state Request File";
      case 4:
        return str + "state Transfer"; }
      return str + "state UNKNOWN";
    }
  }

  public class NetFile
  {
    public NetObj owner;
    public String ownerFileName;
    public long finger;
    public String localFileName;

    public int hashCode()
    {
      return this.owner.hashCode() + this.ownerFileName.hashCode();
    }
    public boolean equals(Object paramObject) {
      if (paramObject == this) return true;
      if (paramObject == null) return false;
      if (!(paramObject instanceof NetFile)) return false;
      NetFile localNetFile = (NetFile)paramObject;
      return (localNetFile.owner == this.owner) && (localNetFile.ownerFileName.equals(this.ownerFileName));
    }

    public NetFile(NetObj paramString, String arg3) {
      this(paramString, str, 0L, null);
    }

    public NetFile(NetObj paramString1, String paramLong, long arg4, String arg6) {
      this.owner = paramString1;
      this.ownerFileName = paramLong;
      this.finger = ???;
      Object localObject;
      this.localFileName = localObject;
    }
  }
}