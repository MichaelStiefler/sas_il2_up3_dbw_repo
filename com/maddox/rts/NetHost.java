// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetHost.java

package com.maddox.rts;

import java.io.IOException;
import java.util.List;

// Referenced classes of package com.maddox.rts:
//            NetObj, NetMsgGuaranted, NetMsgSpawn, NetMsgInput, 
//            NetEnv, Spawn, NetChannel, NetSpawn

public class NetHost extends com.maddox.rts.NetObj
{
    static class SPAWN
        implements com.maddox.rts.NetSpawn
    {

        public void netSpawn(int i, com.maddox.rts.NetMsgInput netmsginput)
        {
            try
            {
                java.lang.String s = netmsginput.read255();
                com.maddox.rts.NetHost anethost[] = null;
                int j = netmsginput.available() / netmsginput.netObjReferenceLen();
                if(j > 0)
                {
                    anethost = new com.maddox.rts.NetHost[j];
                    for(int k = 0; k < j; k++)
                        anethost[k] = (com.maddox.rts.NetHost)netmsginput.readNetObj();

                }
                new NetHost(netmsginput.channel(), i, s, anethost);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        }

        SPAWN()
        {
        }
    }


    public java.lang.String toString()
    {
        if(shortName != null)
            return shortName;
        else
            return super.toString();
    }

    public java.lang.String shortName()
    {
        return shortName;
    }

    public void setShortName(java.lang.String s)
    {
        if(isMaster())
        {
            shortName = s;
            if(isMirrored())
                try
                {
                    com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(s.length() + 2);
                    netmsgguaranted.writeByte(0);
                    netmsgguaranted.write255(s);
                    post(netmsgguaranted);
                }
                catch(java.lang.Exception exception)
                {
                    com.maddox.rts.NetObj.printDebug(exception);
                }
        }
    }

    public com.maddox.rts.NetHost[] path()
    {
        return path;
    }

    public java.lang.String fullName()
    {
        if(path == null)
            return shortName;
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < path.length; i++)
        {
            stringbuffer.append(path[i].shortName());
            stringbuffer.append('.');
        }

        stringbuffer.append(shortName);
        return stringbuffer.toString();
    }

    public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        if(isMirror() && netmsginput.channel() == masterChannel)
        {
            netmsginput.reset();
            byte byte0 = netmsginput.readByte();
            if(byte0 == 0)
            {
                shortName = netmsginput.read255();
                if(isMirrored())
                    post(new NetMsgGuaranted(netmsginput, 0));
                return true;
            }
            netmsginput.reset();
        }
        return false;
    }

    public void destroy()
    {
        if(isMirror())
        {
            int i = com.maddox.rts.NetEnv.hosts().indexOf(this);
            if(i >= 0)
                com.maddox.rts.NetEnv.hosts().remove(i);
        } else
        {
            com.maddox.rts.NetEnv.cur().host = null;
        }
        super.destroy();
    }

    public com.maddox.rts.NetMsgSpawn netReplicate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        com.maddox.rts.NetMsgSpawn netmsgspawn = new NetMsgSpawn(this);
        netmsgspawn.write255(shortName);
        if(isMirror())
        {
            if(path != null)
            {
                for(int i = 0; i < path.length; i++)
                    netmsgspawn.writeNetObj(path[i]);

            }
            netmsgspawn.writeNetObj(com.maddox.rts.NetEnv.host());
        }
        return netmsgspawn;
    }

    public NetHost(java.lang.String s)
    {
        super(null, -1);
        shortName = s;
        path = null;
        com.maddox.rts.NetEnv.cur().host = this;
    }

    public NetHost(com.maddox.rts.NetChannel netchannel, int i, java.lang.String s, com.maddox.rts.NetHost anethost[])
    {
        super(null, -1, netchannel, i);
        shortName = s;
        path = anethost;
        com.maddox.rts.NetEnv.hosts().add(this);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static final int MSG_NAME = 0;
    protected java.lang.String shortName;
    protected com.maddox.rts.NetHost path[];

    static 
    {
        com.maddox.rts.Spawn.add(com.maddox.rts.NetHost.class, new SPAWN());
    }
}
