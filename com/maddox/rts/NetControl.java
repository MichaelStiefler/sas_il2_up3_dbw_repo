// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetControl.java

package com.maddox.rts;

import com.maddox.util.HashMapInt;
import com.maddox.util.NumberTokenizer;
import java.util.List;

// Referenced classes of package com.maddox.rts:
//            NetObj, NetMsgGuaranted, NetChannel, NetControlReal, 
//            NetMsgSpawn, NetException, NetEnv, NetConnect, 
//            NetMsgInput, NetAddress, Spawn, NetControlLock, 
//            NetSpawn

public final class NetControl extends com.maddox.rts.NetObj
{
    static class SPAWN
        implements com.maddox.rts.NetSpawn
    {

        public void netSpawn(int i, com.maddox.rts.NetMsgInput netmsginput)
        {
            com.maddox.rts.NetChannel netchannel;
            netchannel = netmsginput.channel();
            if(com.maddox.rts.NetEnv.cur().control == null)
                break MISSING_BLOCK_LABEL_68;
            if(com.maddox.rts.NetEnv.cur().control instanceof com.maddox.rts.NetControlLock)
            {
                com.maddox.rts.NetControlLock netcontrollock = (com.maddox.rts.NetControlLock)com.maddox.rts.NetEnv.cur().control;
                if(netcontrollock.channel() == netchannel)
                {
                    netcontrollock.destroy();
                } else
                {
                    netchannel.destroy("Remote control slot is locked with other channel.");
                    return;
                }
                break MISSING_BLOCK_LABEL_75;
            }
            try
            {
                netchannel.destroy("Only TREE network structure is supported.");
                return;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
            break MISSING_BLOCK_LABEL_96;
            netchannel.destroy("Remote control slot is NOT locked with the channel created.");
            return;
            new NetControl(netchannel, i);
        }

        SPAWN()
        {
        }
    }


    public void doAnswer(java.lang.String s)
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(4);
            netmsgguaranted.writeInt(-1);
            netmsgguaranted.write255(s);
            netmsgguaranted.writeNetObj(null);
            postTo(masterChannel, netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.NetObj.printDebug(exception);
        }
    }

    public void doRequest(com.maddox.rts.NetObj netobj, int i, java.lang.String s)
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(3);
            if(netobj != null)
            {
                netmsgguaranted.writeInt(i);
                netmsgguaranted.write255(s);
                netmsgguaranted.writeNetObj(netobj);
                postTo(netobj.masterChannel(), netmsgguaranted);
            } else
            if(i != -1)
            {
                com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)com.maddox.rts.NetEnv.cur().indxChannels.get(i);
                netmsgguaranted.writeInt(-1);
                netmsgguaranted.writeNetObj(null);
                netmsgguaranted.write255(s);
                postTo(netchannel, netmsgguaranted);
            } else
            {
                com.maddox.rts.NetEnv.cur().connect.msgRequest(s);
            }
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.NetObj.printDebug(exception);
        }
    }

    public void doNak(com.maddox.rts.NetObj netobj, int i, java.lang.String s)
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(2);
            if(netobj != null)
            {
                netmsgguaranted.writeInt(i);
                netmsgguaranted.write255(s);
                netmsgguaranted.writeNetObj(netobj);
                postTo(netobj.masterChannel(), netmsgguaranted);
            } else
            if(i != -1)
            {
                com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)com.maddox.rts.NetEnv.cur().indxChannels.get(i);
                netmsgguaranted.writeInt(-1);
                netmsgguaranted.writeNetObj(null);
                netmsgguaranted.write255(s);
                postTo(netchannel, netmsgguaranted);
                netchannel.destroy(s);
            } else
            {
                masterChannel.destroy(s);
            }
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.NetObj.printDebug(exception);
        }
    }

    public void doAsk(com.maddox.rts.NetObj netobj, int i)
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(5);
            netmsgguaranted.writeByte(1);
            if(netobj != null)
            {
                netmsgguaranted.writeInt(i);
                netmsgguaranted.writeNetObj(netobj);
                postTo(netobj.masterChannel(), netmsgguaranted);
            } else
            if(i != -1)
            {
                com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)com.maddox.rts.NetEnv.cur().indxChannels.get(i);
                netmsgguaranted.writeInt(-1);
                netmsgguaranted.writeNetObj(null);
                postTo(netchannel, netmsgguaranted);
                netchannel.controlStartInit();
            } else
            {
                masterChannel.controlStartInit();
            }
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.NetObj.printDebug(exception);
        }
    }

    public void msgNet(com.maddox.rts.NetMsgInput netmsginput)
    {
        byte byte0;
        int i;
        com.maddox.rts.NetObj netobj;
        byte0 = netmsginput.readByte();
        i = netmsginput.readInt();
        netobj = netmsginput.readNetObj();
        if(!isMaster()) goto _L2; else goto _L1
_L1:
        if(netobj == null)
            i = netmsginput.channel().id();
        if(superObj() != null)
        {
            if(superObj() instanceof com.maddox.rts.NetControlReal)
            {
                com.maddox.rts.NetControlReal netcontrolreal = (com.maddox.rts.NetControlReal)superObj();
                switch(byte0)
                {
                case 0: // '\0'
                    java.lang.String s = null;
                    int j = 0;
                    if(netobj == null)
                    {
                        s = netmsginput.channel().remoteAddress().getHostAddress();
                        if(s == null)
                            s = "0";
                        j = netmsginput.channel().remotePort();
                    } else
                    {
                        com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(netmsginput.read255());
                        s = numbertokenizer.next("0");
                        j = numbertokenizer.next(0);
                    }
                    netcontrolreal.msgNewClient(netobj, i, s, j);
                    break;

                case 4: // '\004'
                    netcontrolreal.msgAnswer(netobj, i, netmsginput.read255());
                    break;
                }
            }
        } else
        {
            switch(byte0)
            {
            case 0: // '\0'
                doAsk(netobj, i);
                break;
            }
        }
          goto _L3
_L2:
        byte0;
        JVM INSTR tableswitch 0 4: default 633
    //                   0 268
    //                   1 484
    //                   2 531
    //                   3 582
    //                   4 401;
           goto _L4 _L5 _L6 _L7 _L8 _L9
_L4:
        break; /* Loop/switch isn't completed */
_L5:
        if(netobj == null)
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(byte0);
            netmsgguaranted.writeInt(netmsginput.channel().id());
            netmsgguaranted.writeNetObj(com.maddox.rts.NetEnv.host());
            java.lang.String s1 = netmsginput.channel().remoteAddress().getHostAddress();
            if(s1 == null)
                s1 = "0";
            java.lang.String s2 = s1 + " " + netmsginput.channel().remotePort();
            netmsgguaranted.write255(s2);
            postTo(masterChannel, netmsgguaranted);
        } else
        {
            postTo(masterChannel, new NetMsgGuaranted(netmsginput, 1));
        }
        break; /* Loop/switch isn't completed */
_L9:
        if(netobj == null)
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted(5);
            netmsgguaranted1.writeByte(byte0);
            netmsgguaranted1.writeInt(netmsginput.channel().id());
            netmsgguaranted1.write255(netmsginput.read255());
            netmsgguaranted1.writeNetObj(com.maddox.rts.NetEnv.host());
            postTo(masterChannel, netmsgguaranted1);
        } else
        {
            postTo(masterChannel, new NetMsgGuaranted(netmsginput, 1));
        }
        break; /* Loop/switch isn't completed */
_L6:
        if(netobj == null) goto _L11; else goto _L10
_L10:
        com.maddox.rts.NetEnv.cur();
        if(netobj != com.maddox.rts.NetEnv.host()) goto _L12; else goto _L11
_L11:
        doAsk(null, i);
        break; /* Loop/switch isn't completed */
_L12:
        postTo(netobj.masterChannel(), new NetMsgGuaranted(netmsginput, 1));
        break; /* Loop/switch isn't completed */
_L7:
        if(netobj == null) goto _L14; else goto _L13
_L13:
        com.maddox.rts.NetEnv.cur();
        if(netobj != com.maddox.rts.NetEnv.host()) goto _L15; else goto _L14
_L14:
        doNak(null, i, netmsginput.read255());
        break; /* Loop/switch isn't completed */
_L15:
        postTo(netobj.masterChannel(), new NetMsgGuaranted(netmsginput, 1));
        break; /* Loop/switch isn't completed */
_L8:
        if(netobj == null) goto _L17; else goto _L16
_L16:
        com.maddox.rts.NetEnv.cur();
        if(netobj != com.maddox.rts.NetEnv.host()) goto _L18; else goto _L17
_L17:
        doRequest(null, i, netmsginput.read255());
        break; /* Loop/switch isn't completed */
_L18:
        postTo(netobj.masterChannel(), new NetMsgGuaranted(netmsginput, 1));
        break; /* Loop/switch isn't completed */
        java.lang.Exception exception;
        exception;
        com.maddox.rts.NetObj.printDebug(exception);
_L3:
    }

    private void startCheckChannel()
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(0);
            netmsgguaranted.writeInt(-1);
            netmsgguaranted.writeNetObj(null);
            postTo(masterChannel, netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.NetObj.printDebug(exception);
        }
    }

    public void msgNetNewChannel(com.maddox.rts.NetChannel netchannel)
    {
        if(isMirror() && masterChannel == netchannel)
            return;
        if(netchannel.isGlobal() && !netchannel.isMirrored(this))
            try
            {
                com.maddox.rts.NetMsgSpawn netmsgspawn = new NetMsgSpawn(this);
                postTo(netchannel, netmsgspawn);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
    }

    public void destroy()
    {
        if(isMirror())
        {
            com.maddox.rts.NetEnv.cur();
            java.util.List list = com.maddox.rts.NetEnv.channels();
            int i = list.size();
            for(int j = 0; j < i; j++)
            {
                com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)list.get(j);
                if(netchannel.isGlobal())
                    netchannel.destroy("Server has left the game.");
            }

        }
        if(com.maddox.rts.NetEnv.cur().control == this)
            com.maddox.rts.NetEnv.cur().control = null;
        super.destroy();
    }

    public NetControl(java.lang.Object obj)
    {
        super(obj);
        if(com.maddox.rts.NetEnv.cur().control != null)
        {
            super.destroy();
            throw new NetException("net control slot alredy used");
        } else
        {
            com.maddox.rts.NetEnv.cur().control = this;
            return;
        }
    }

    public NetControl(com.maddox.rts.NetChannel netchannel, int i)
    {
        super(null, netchannel, i);
        if(com.maddox.rts.NetEnv.cur().control != null)
        {
            super.destroy();
            throw new NetException("net control slot alredy used");
        } else
        {
            com.maddox.rts.NetEnv.cur().control = this;
            startCheckChannel();
            return;
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static final int MSG_NEW_CLIENT = 0;
    public static final int MSG_ASK = 1;
    public static final int MSG_NAK = 2;
    public static final int MSG_REQUEST = 3;
    public static final int MSG_ANSWER = 4;

    static 
    {
        com.maddox.rts.Spawn.add(com.maddox.rts.NetControl.class, new SPAWN());
    }
}
