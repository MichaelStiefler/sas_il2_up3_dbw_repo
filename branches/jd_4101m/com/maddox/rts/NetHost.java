package com.maddox.rts;

import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;
import java.io.IOException;
import java.util.List;

public class NetHost extends NetObj
{
  public static final int MSG_NAME = 0;
  protected String shortName;
  protected NetHost[] path;

  public String toString()
  {
    if (this.shortName != null) {
      return this.shortName;
    }
    return super.toString();
  }

  private String getCleanedString(String paramString)
  {
    if ((Main.cur().netServerParams == null) || (!Main.cur().netServerParams.filterUserNames))
    {
      return paramString;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramString.length(); i++)
    {
      int j = paramString.charAt(i);
      if (!isCharValid(j))
        continue;
      localStringBuffer.append(paramString.charAt(i));
    }

    return localStringBuffer.toString();
  }

  private boolean isCharValid(int paramInt)
  {
    if ((paramInt >= 33) && (paramInt <= 160)) {
      return true;
    }

    if ((paramInt >= 1025) && (paramInt <= 1119)) {
      return true;
    }
    return (paramInt >= 1168) && (paramInt <= 1257);
  }

  public String shortName()
  {
    return this.shortName;
  }
  public void setShortName(String paramString) {
    paramString = getCleanedString(paramString);
    if (isMaster()) {
      this.shortName = paramString;
      if (isMirrored())
        try {
          NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted(paramString.length() + 2);
          localNetMsgGuaranted.writeByte(0);
          localNetMsgGuaranted.write255(paramString);
          post(localNetMsgGuaranted); } catch (Exception localException) {
          printDebug(localException);
        }
    }
  }

  public NetHost[] path()
  {
    return this.path;
  }

  public String fullName()
  {
    if (this.path == null)
      return this.shortName;
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < this.path.length; i++) {
      localStringBuffer.append(this.path[i].shortName());
      localStringBuffer.append('.');
    }
    localStringBuffer.append(this.shortName);
    return localStringBuffer.toString();
  }

  public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException
  {
    if ((isMirror()) && (paramNetMsgInput.channel() == this.masterChannel)) {
      paramNetMsgInput.reset();
      int i = paramNetMsgInput.readByte();
      if (i == 0) {
        this.shortName = paramNetMsgInput.read255();
        if (isMirrored())
          post(new NetMsgGuaranted(paramNetMsgInput, 0));
        return true;
      }
      paramNetMsgInput.reset();
    }
    return false;
  }

  public void destroy() {
    if (isMirror()) {
      int i = NetEnv.hosts().indexOf(this);
      if (i >= 0)
        NetEnv.hosts().remove(i);
    } else {
      NetEnv.cur().host = null;
    }
    super.destroy();
  }

  public NetMsgSpawn netReplicate(NetChannel paramNetChannel) throws IOException {
    NetMsgSpawn localNetMsgSpawn = new NetMsgSpawn(this);
    this.shortName = getCleanedString(this.shortName);
    localNetMsgSpawn.write255(this.shortName);
    if (isMirror()) {
      if (this.path != null) {
        for (int i = 0; i < this.path.length; i++)
          localNetMsgSpawn.writeNetObj(this.path[i]);
      }
      localNetMsgSpawn.writeNetObj(NetEnv.host());
    }
    return localNetMsgSpawn;
  }

  public NetHost(String paramString) {
    super(null, -1);

    this.shortName = getCleanedString(paramString);
    this.path = null;
    NetEnv.cur().host = this;
  }

  public NetHost(NetChannel paramNetChannel, int paramInt, String paramString, NetHost[] paramArrayOfNetHost)
  {
    super(null, -1, paramNetChannel, paramInt);

    this.shortName = getCleanedString(paramString);
    this.path = paramArrayOfNetHost;
    NetEnv.hosts().add(this);
  }

  static
  {
    Spawn.add(NetHost.class, new SPAWN());
  }

  static class SPAWN implements NetSpawn {
    public void netSpawn(int paramInt, NetMsgInput paramNetMsgInput) {
      try {
        String str = paramNetMsgInput.read255();
        NetHost[] arrayOfNetHost = null;
        int i = paramNetMsgInput.available() / NetMsgInput.netObjReferenceLen();
        if (i > 0) {
          arrayOfNetHost = new NetHost[i];
          for (int j = 0; j < i; j++)
            arrayOfNetHost[j] = ((NetHost)paramNetMsgInput.readNetObj());
        }
        new NetHost(paramNetMsgInput.channel(), paramInt, str, arrayOfNetHost); } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
    }
  }
}