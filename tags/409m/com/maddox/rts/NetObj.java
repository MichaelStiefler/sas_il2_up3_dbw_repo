// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetObj.java

package com.maddox.rts;

import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.rts:
//            NetUpdate, NetFilter, NetChannel, NetMsgDestroy, 
//            NetMsgSpawn, NetException, Destroy, MsgNetListener, 
//            NetEnv, NetMsgGuaranted, Time, NetMsgFiltered, 
//            MsgNet, NetMsgInput

public abstract class NetObj
    implements com.maddox.rts.Destroy, com.maddox.rts.MsgNetListener
{

    public int idLocal()
    {
        return idLocal;
    }

    public int idRemote()
    {
        return idRemote;
    }

    public java.lang.Object superObj()
    {
        return superObj;
    }

    public com.maddox.rts.NetChannel masterChannel()
    {
        return masterChannel;
    }

    public void destroyOnlyLocal()
    {
        if(idLocal > 0)
        {
            com.maddox.rts.NetEnv.cur().objects.remove(idLocal);
            idLocal |= 0x80000000;
            if(this instanceof com.maddox.rts.NetUpdate)
            {
                int i = com.maddox.rts.NetEnv.cur().udatedObjects.indexOf(this);
                if(i >= 0)
                    com.maddox.rts.NetEnv.cur().udatedObjects.remove(i);
            }
            if(masterChannel != null)
            {
                masterChannel.objects.remove(idRemote);
                if(this instanceof com.maddox.rts.NetFilter)
                    masterChannel.filterRemove((com.maddox.rts.NetFilter)this);
            }
            if(!isCommon() && countMirrors > 0)
            {
                Object obj = null;
                java.util.List list = com.maddox.rts.NetEnv.channels();
                int j = list.size();
                for(int k = 0; k < j; k++)
                {
                    com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)list.get(k);
                    if(masterChannel != netchannel && netchannel.isMirrored(this))
                    {
                        netchannel.mirrored.remove(this);
                        countMirrors--;
                    }
                }

            }
        }
    }

    public void destroy()
    {
        if(idLocal > 0)
        {
            com.maddox.rts.NetEnv.cur().objects.remove(idLocal);
            idLocal |= 0x80000000;
            if(this instanceof com.maddox.rts.NetUpdate)
            {
                int i = com.maddox.rts.NetEnv.cur().udatedObjects.indexOf(this);
                if(i >= 0)
                    com.maddox.rts.NetEnv.cur().udatedObjects.remove(i);
            }
            if(masterChannel != null)
            {
                masterChannel.objects.remove(idRemote);
                if(this instanceof com.maddox.rts.NetFilter)
                    masterChannel.filterRemove((com.maddox.rts.NetFilter)this);
            }
            if(!isCommon() && countMirrors > 0)
            {
                com.maddox.rts.NetMsgDestroy netmsgdestroy = null;
                java.util.List list = com.maddox.rts.NetEnv.channels();
                int j = list.size();
                for(int k = 0; k < j; k++)
                {
                    com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)list.get(k);
                    if(masterChannel != netchannel && netchannel.isMirrored(this))
                        try
                        {
                            if(netmsgdestroy == null)
                                netmsgdestroy = new NetMsgDestroy(this);
                            netchannel.putMessageDestroy(netmsgdestroy);
                        }
                        catch(java.lang.Exception exception) { }
                }

            }
        }
    }

    public boolean isDestroyed()
    {
        return idLocal < 0;
    }

    public boolean isMaster()
    {
        return idRemote == 0;
    }

    public boolean isMirror()
    {
        return idRemote != 0;
    }

    public boolean isCommon()
    {
        return isMaster() && (idLocal & 0x7fff) < 255;
    }

    public boolean isMirrored()
    {
        return countMirrors != 0;
    }

    public int countMirrors()
    {
        return countMirrors;
    }

    public int countNoMirrors()
    {
        java.util.List list = com.maddox.rts.NetEnv.channels();
        int i = list.size();
        com.maddox.rts.NetChannel netchannel = null;
        if(isMirror())
            netchannel = masterChannel;
        int j = 0;
        for(int k = 0; k < i; k++)
        {
            com.maddox.rts.NetChannel netchannel1 = (com.maddox.rts.NetChannel)list.get(k);
            if(netchannel1 != netchannel && netchannel1.isReady() && netchannel1.isPublic() && !netchannel1.isMirrored(this))
                j++;
        }

        return j;
    }

    public void post(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        com.maddox.rts.NetChannel netchannel = null;
        if(isMirror())
            netchannel = masterChannel;
        postExclude(netchannel, netmsgguaranted);
    }

    public void postTo(com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.checkLock();
        netmsgguaranted._sender = this;
        if(netmsgguaranted instanceof com.maddox.rts.NetMsgSpawn)
        {
            if(!netchannel.isSortGuaranted() && netchannel.isMirrored(this))
                return;
            netchannel.putMessageSpawn((com.maddox.rts.NetMsgSpawn)netmsgguaranted);
        } else
        if(netmsgguaranted instanceof com.maddox.rts.NetMsgDestroy)
        {
            if(!netchannel.isSortGuaranted() && !netchannel.isMirrored(this))
                return;
            netchannel.putMessageDestroy((com.maddox.rts.NetMsgDestroy)netmsgguaranted);
        } else
        {
            netchannel.putMessage(netmsgguaranted);
        }
    }

    public void postExclude(com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.checkLock();
        netmsgguaranted._sender = this;
        java.util.List list = com.maddox.rts.NetEnv.channels();
        int i = list.size();
        if(netmsgguaranted instanceof com.maddox.rts.NetMsgSpawn)
        {
            com.maddox.rts.NetChannel netchannel1 = null;
            if(isMirror())
                netchannel1 = masterChannel;
            for(int k = 0; k < i; k++)
            {
                com.maddox.rts.NetChannel netchannel4 = (com.maddox.rts.NetChannel)list.get(k);
                if(netchannel4 != netchannel && netchannel4 != netchannel1 && netchannel4.isReady() && netchannel4.isPublic() && !netchannel4.isMirrored(this))
                    netchannel4.putMessageSpawn((com.maddox.rts.NetMsgSpawn)netmsgguaranted);
            }

        } else
        if(netmsgguaranted instanceof com.maddox.rts.NetMsgDestroy)
        {
            com.maddox.rts.NetChannel netchannel2 = null;
            if(isMirror())
                netchannel2 = masterChannel;
            for(int l = 0; l < i; l++)
            {
                com.maddox.rts.NetChannel netchannel5 = (com.maddox.rts.NetChannel)list.get(l);
                if(netchannel5 != netchannel && netchannel5 != netchannel2 && netchannel5.isReady() && netchannel5.isMirrored(this))
                    netchannel5.putMessageDestroy((com.maddox.rts.NetMsgDestroy)netmsgguaranted);
            }

        } else
        {
            for(int j = 0; j < i; j++)
            {
                com.maddox.rts.NetChannel netchannel3 = (com.maddox.rts.NetChannel)list.get(j);
                if(netchannel3 != netchannel && netchannel3.isReady() && (netchannel3.isMirrored(this) || netchannel3 == masterChannel))
                    netchannel3.putMessage(netmsgguaranted);
            }

        }
    }

    public void post(long l, com.maddox.rts.NetMsgFiltered netmsgfiltered)
        throws java.io.IOException
    {
        postReal(com.maddox.rts.Time.toReal(l), netmsgfiltered);
    }

    public void postReal(long l, com.maddox.rts.NetMsgFiltered netmsgfiltered)
        throws java.io.IOException
    {
        com.maddox.rts.NetChannel netchannel = null;
        if(isMirror())
            netchannel = masterChannel;
        postRealExclude(l, netchannel, netmsgfiltered);
    }

    public void postTo(long l, com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetMsgFiltered netmsgfiltered)
        throws java.io.IOException
    {
        postRealTo(com.maddox.rts.Time.toReal(l), netchannel, netmsgfiltered);
    }

    public void postRealTo(long l, com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetMsgFiltered netmsgfiltered)
        throws java.io.IOException
    {
        netmsgfiltered.checkLock();
        netmsgfiltered._time = l;
        netmsgfiltered._timeStamp = timeStamp++;
        netmsgfiltered._sender = this;
        netchannel.putMessage(netmsgfiltered);
    }

    public void postExclude(long l, com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetMsgFiltered netmsgfiltered)
        throws java.io.IOException
    {
        postRealExclude(com.maddox.rts.Time.toReal(l), netchannel, netmsgfiltered);
    }

    public void postRealExclude(long l, com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetMsgFiltered netmsgfiltered)
        throws java.io.IOException
    {
        netmsgfiltered.checkLock();
        netmsgfiltered._time = l;
        netmsgfiltered._timeStamp = timeStamp++;
        netmsgfiltered._sender = this;
        java.util.List list = com.maddox.rts.NetEnv.channels();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.rts.NetChannel netchannel1 = (com.maddox.rts.NetChannel)list.get(j);
            if(netchannel1 != netchannel && netchannel1.isReady() && netchannel1.isMirrored(this))
                netchannel1.putMessage(netmsgfiltered);
        }

    }

    public void msgNet(com.maddox.rts.NetMsgInput netmsginput)
    {
        try
        {
            netInput(netmsginput);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.NetObj.printDebug(exception);
        }
    }

    public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        return false;
    }

    public void msgNetNewChannel(com.maddox.rts.NetChannel netchannel)
    {
        if(netchannel.isMirrored(this))
            return;
        try
        {
            com.maddox.rts.NetMsgSpawn netmsgspawn = netReplicate(netchannel);
            if(netmsgspawn != null)
                postTo(netchannel, netmsgspawn);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.NetObj.printDebug(exception);
        }
        return;
    }

    public com.maddox.rts.NetMsgSpawn netReplicate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        return null;
    }

    public void msgNetDelChannel(com.maddox.rts.NetChannel netchannel)
    {
    }

    public static void tryReplicate(com.maddox.rts.NetObj netobj, boolean flag)
    {
        int i = netobj.countNoMirrors();
        if(i == 0)
            return;
        java.util.List list = com.maddox.rts.NetEnv.channels();
        int j = list.size();
        com.maddox.rts.NetChannel netchannel = null;
        if(netobj.isMirror())
            netchannel = netobj.masterChannel;
        for(int k = 0; k < j; k++)
        {
            com.maddox.rts.NetChannel netchannel1 = (com.maddox.rts.NetChannel)list.get(k);
            if(netchannel1 != netchannel && (netchannel1.isReady() || !netchannel1.isDestroying() && netchannel1.state() != 1) && netchannel1.isPublic() && !netchannel1.isMirrored(netobj))
                if(flag)
                    netobj.msgNetNewChannel(netchannel1);
                else
                    com.maddox.rts.MsgNet.postRealNewChannel(netobj, netchannel1);
        }

    }

    public NetObj(java.lang.Object obj, int i)
    {
        superObj = null;
        idLocal = -1;
        idRemote = 0;
        masterChannel = null;
        countMirrors = 0;
        superObj = obj;
        if(i == -1)
        {
            i = (int)(java.lang.Math.random() * 32511D + 255D);
            i = com.maddox.rts.NetEnv.cur().objects.findNotUsedKey(255, i, 32767);
            if(i == 32767)
                throw new NetException("Net local slots from 255 to 32767 is FULL");
        } else
        if(com.maddox.rts.NetEnv.cur().objects.containsKey(i))
            throw new NetException("Net local slot: " + i + " is alredy used");
        com.maddox.rts.NetEnv.cur().objects.put(i, this);
        idLocal = i;
        if(this instanceof com.maddox.rts.NetUpdate)
            com.maddox.rts.NetEnv.cur().udatedObjects.add(this);
        com.maddox.rts.NetObj.tryReplicate(this, false);
    }

    public NetObj(java.lang.Object obj)
    {
        superObj = null;
        idLocal = -1;
        idRemote = 0;
        masterChannel = null;
        countMirrors = 0;
        superObj = obj;
        int i = com.maddox.rts.NetEnv.cur().objects.findNotUsedKey(255, com.maddox.rts.NetEnv.cur().nextFreeID, 32767);
        if(i == 32767)
            throw new NetException("Net local slots from 255 to 32767 is FULL");
        com.maddox.rts.NetEnv.cur().objects.put(i, this);
        idLocal = i;
        if(++i == 32767)
            i = 255;
        com.maddox.rts.NetEnv.cur().nextFreeID = i;
        if(this instanceof com.maddox.rts.NetUpdate)
            com.maddox.rts.NetEnv.cur().udatedObjects.add(this);
        com.maddox.rts.NetObj.tryReplicate(this, false);
    }

    public NetObj(java.lang.Object obj, com.maddox.rts.NetChannel netchannel, int i)
    {
        superObj = null;
        idLocal = -1;
        idRemote = 0;
        masterChannel = null;
        countMirrors = 0;
        superObj = obj;
        int j = com.maddox.rts.NetEnv.cur().objects.findNotUsedKey(255, com.maddox.rts.NetEnv.cur().nextFreeID, 32767);
        if(j == 32767)
            throw new NetException("Net local slots from 255 to 32767 is FULL");
        com.maddox.rts.NetEnv.cur().objects.put(j, this);
        idLocal = j;
        if(++j == 32767)
            j = 255;
        com.maddox.rts.NetEnv.cur().nextFreeID = j;
        if(this instanceof com.maddox.rts.NetUpdate)
            com.maddox.rts.NetEnv.cur().udatedObjects.add(this);
        netchannel.objects.put(i, this);
        masterChannel = netchannel;
        idRemote = i;
        if(this instanceof com.maddox.rts.NetFilter)
            netchannel.filterAdd((com.maddox.rts.NetFilter)this);
        com.maddox.rts.NetObj.tryReplicate(this, false);
    }

    public NetObj(java.lang.Object obj, int i, com.maddox.rts.NetChannel netchannel, int j)
    {
        superObj = null;
        idLocal = -1;
        idRemote = 0;
        masterChannel = null;
        countMirrors = 0;
        superObj = obj;
        if(i == -1)
        {
            i = (int)(java.lang.Math.random() * 32511D + 255D);
            i = com.maddox.rts.NetEnv.cur().objects.findNotUsedKey(255, i, 32767);
            if(i == 32767)
                throw new NetException("Net local slots from 255 to 32767 is FULL");
        } else
        if(com.maddox.rts.NetEnv.cur().objects.containsKey(i))
            throw new NetException("Net local slot: " + i + " is alredy used");
        com.maddox.rts.NetEnv.cur().objects.put(i, this);
        idLocal = i;
        if(this instanceof com.maddox.rts.NetUpdate)
            com.maddox.rts.NetEnv.cur().udatedObjects.add(this);
        netchannel.objects.put(j, this);
        masterChannel = netchannel;
        idRemote = j;
        if(this instanceof com.maddox.rts.NetFilter)
            netchannel.filterAdd((com.maddox.rts.NetFilter)this);
        com.maddox.rts.NetObj.tryReplicate(this, false);
    }

    protected static void printDebug(java.lang.Exception exception)
    {
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
    }

    protected java.lang.Object superObj;
    protected int idLocal;
    protected int idRemote;
    protected com.maddox.rts.NetChannel masterChannel;
    protected int countMirrors;
    private static long timeStamp;
}
