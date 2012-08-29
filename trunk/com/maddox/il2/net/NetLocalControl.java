package com.maddox.il2.net;

import com.maddox.il2.game.Main;
import com.maddox.rts.Finger;
import com.maddox.rts.NetControl;
import com.maddox.rts.NetControlReal;
import com.maddox.rts.NetObj;
import com.maddox.util.HashMapExt;
import com.maddox.util.NumberTokenizer;

public class NetLocalControl
  implements NetControlReal
{
  private NetControl control;
  private HashMapExt mapUsers = new HashMapExt();

  public void msgNewClient(NetObj paramNetObj, int paramInt1, String paramString, int paramInt2)
  {
    if (Main.cur().netServerParams.isProtected()) {
      User localUser = new User(paramNetObj, paramInt1);
      localUser.address = paramString;
      localUser.port = paramInt2;
      if (this.mapUsers.containsKey(localUser)) {
        localUser = (User)this.mapUsers.get(localUser);
        if (!localUser.address.equals(paramString)) {
          localUser.address = paramString;
          localUser.port = paramInt2;
          localUser.countConnect = 0;
        }
      } else {
        this.mapUsers.put(localUser, localUser);
      }
      this.control.doRequest(paramNetObj, paramInt1, "SP " + localUser.nextPublicKey());
    } else {
      this.control.doAsk(paramNetObj, paramInt1);
    }
  }

  public void msgAnswer(NetObj paramNetObj, int paramInt, String paramString)
  {
    if (!Main.cur().netServerParams.isProtected()) return;
    User localUser = new User(paramNetObj, paramInt);
    localUser = (User)this.mapUsers.get(localUser);
    if (localUser == null) {
      return;
    }
    NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramString);

    if ("SP".equals(localNumberTokenizer.next("_"))) {
      long l1 = 0L;
      try {
        l1 = Long.parseLong(localNumberTokenizer.next("0")); } catch (Exception localException) {
      }
      long l2 = Finger.incLong(0L, "" + localUser.publicKey);
      l2 = Finger.incLong(l2, Main.cur().netServerParams.getPassword());
      if (l1 == l2) {
        this.mapUsers.remove(localUser);
        this.control.doAsk(paramNetObj, paramInt);
        return;
      }
    }

    localUser.countConnect += 1;
    if (localUser.countConnect == 3) {
      this.mapUsers.remove(localUser);
      this.control.doNak(paramNetObj, paramInt, "bad password");
      return;
    }
    localUser.publicKey = ()Math.random();
    this.control.doRequest(paramNetObj, paramInt, "SP " + localUser.nextPublicKey());
  }

  public void destroy() {
    if (this.control != null) {
      this.control.destroy();
      this.control = null;
    }
  }

  public NetLocalControl() {
    this.control = new NetControl(this);
  }

  static class User
  {
    public NetObj firstHost;
    public int idChannel;
    public String address;
    public int port;
    public int countConnect;
    public long publicKey;

    public long nextPublicKey()
    {
      this.publicKey = ()(Math.random() * 1000000000.0D);
      return this.publicKey;
    }

    public boolean equals(Object paramObject) {
      if (paramObject == null) return false;
      if (!(paramObject instanceof User)) return false;
      User localUser = (User)paramObject;
      return (this.firstHost == localUser.firstHost) && (this.idChannel == localUser.idChannel);
    }
    public int hashCode() { return this.idChannel; } 
    public User(NetObj paramNetObj, int paramInt) {
      this.firstHost = paramNetObj;
      this.idChannel = paramInt;
    }
  }
}