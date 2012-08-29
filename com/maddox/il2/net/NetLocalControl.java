// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetLocalControl.java

package com.maddox.il2.net;

import com.maddox.il2.game.Main;
import com.maddox.rts.Finger;
import com.maddox.rts.NetControl;
import com.maddox.rts.NetControlReal;
import com.maddox.rts.NetObj;
import com.maddox.util.HashMapExt;
import com.maddox.util.NumberTokenizer;

// Referenced classes of package com.maddox.il2.net:
//            NetServerParams

public class NetLocalControl
    implements com.maddox.rts.NetControlReal
{
    static class User
    {

        public long nextPublicKey()
        {
            publicKey = (long)(java.lang.Math.random() * 1000000000D);
            return publicKey;
        }

        public boolean equals(java.lang.Object obj)
        {
            if(obj == null)
                return false;
            if(!(obj instanceof com.maddox.il2.net.User))
            {
                return false;
            } else
            {
                com.maddox.il2.net.User user = (com.maddox.il2.net.User)obj;
                return firstHost == user.firstHost && idChannel == user.idChannel;
            }
        }

        public int hashCode()
        {
            return idChannel;
        }

        public com.maddox.rts.NetObj firstHost;
        public int idChannel;
        public java.lang.String address;
        public int port;
        public int countConnect;
        public long publicKey;

        public User(com.maddox.rts.NetObj netobj, int i)
        {
            firstHost = netobj;
            idChannel = i;
        }
    }


    public void msgNewClient(com.maddox.rts.NetObj netobj, int i, java.lang.String s, int j)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams.isProtected())
        {
            com.maddox.il2.net.User user = new User(netobj, i);
            user.address = s;
            user.port = j;
            if(mapUsers.containsKey(user))
            {
                user = (com.maddox.il2.net.User)mapUsers.get(user);
                if(!user.address.equals(s))
                {
                    user.address = s;
                    user.port = j;
                    user.countConnect = 0;
                }
            } else
            {
                mapUsers.put(user, user);
            }
            control.doRequest(netobj, i, "SP " + user.nextPublicKey());
        } else
        {
            control.doAsk(netobj, i);
        }
    }

    public void msgAnswer(com.maddox.rts.NetObj netobj, int i, java.lang.String s)
    {
        if(!com.maddox.il2.game.Main.cur().netServerParams.isProtected())
            return;
        com.maddox.il2.net.User user = new User(netobj, i);
        user = (com.maddox.il2.net.User)mapUsers.get(user);
        if(user == null)
            return;
        com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(s);
        if("SP".equals(numbertokenizer.next("_")))
        {
            long l = 0L;
            try
            {
                l = java.lang.Long.parseLong(numbertokenizer.next("0"));
            }
            catch(java.lang.Exception exception) { }
            long l1 = com.maddox.rts.Finger.incLong(0L, "" + user.publicKey);
            l1 = com.maddox.rts.Finger.incLong(l1, com.maddox.il2.game.Main.cur().netServerParams.getPassword());
            if(l == l1)
            {
                mapUsers.remove(user);
                control.doAsk(netobj, i);
                return;
            }
        }
        user.countConnect++;
        if(user.countConnect == 3)
        {
            mapUsers.remove(user);
            control.doNak(netobj, i, "bad password");
            return;
        } else
        {
            user.publicKey = (long)java.lang.Math.random();
            control.doRequest(netobj, i, "SP " + user.nextPublicKey());
            return;
        }
    }

    public void destroy()
    {
        if(control != null)
        {
            control.destroy();
            control = null;
        }
    }

    public NetLocalControl()
    {
        mapUsers = new HashMapExt();
        control = new NetControl(this);
    }

    private com.maddox.rts.NetControl control;
    private com.maddox.util.HashMapExt mapUsers;
}
