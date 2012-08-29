// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetChannelInStream.java

package com.maddox.rts;

import com.maddox.rts.net.NetEmptyAddress;
import com.maddox.rts.net.NetEmptySocket;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

// Referenced classes of package com.maddox.rts:
//            NetChannel, NetObj, NetChannelCallbackStream, NetMsgInput, 
//            NetException, NetMsgGuaranted, NetChannelStream, MsgTimeOutListener, 
//            NetEnv, MsgNet, Time, MsgTimeOut, 
//            CRC16, RTSConf, NetMsgSpawn, NetMsgDestroy, 
//            NetMsgFiltered, NetMsgOutput, NetPacket

public class NetChannelInStream extends com.maddox.rts.NetChannel
    implements com.maddox.rts.NetChannelStream, com.maddox.rts.MsgTimeOutListener
{
    static class SyncMessage extends com.maddox.rts.NetObj
    {

        public SyncMessage(int i)
        {
            super(null, i);
        }
    }


    public void destroy()
    {
        if((state & 0x80000000) != 0)
            return;
        com.maddox.rts.NetObj netobj = (com.maddox.rts.NetObj)com.maddox.rts.NetEnv.cur().objects.get(9);
        if(netobj != null && !bDestroyGo)
        {
            bDestroyGo = true;
            netobj.msgNetDelChannel(this);
            bDestroyGo = false;
            if((state & 0x80000000) != 0)
                return;
        }
        state |= 0x80000000;
        do
        {
            if(objects.isEmpty())
                break;
            com.maddox.util.HashMapIntEntry hashmapintentry = objects.nextEntry(null);
            if(hashmapintentry == null)
                break;
            com.maddox.rts.NetObj netobj1 = (com.maddox.rts.NetObj)hashmapintentry.getValue();
            int i = hashmapintentry.getKey();
            com.maddox.rts.NetChannelInStream.destroyNetObj(netobj1);
            if(objects.containsKey(i))
                objects.remove(i);
        } while(true);
        do
        {
            if(mirrored.isEmpty())
                break;
            java.util.Map.Entry entry = mirrored.nextEntry(null);
            if(entry == null)
                break;
            com.maddox.rts.NetObj netobj2 = (com.maddox.rts.NetObj)entry.getKey();
            netobj2.countMirrors--;
            mirrored.remove(netobj2);
            com.maddox.rts.MsgNet.postRealDelChannel(netobj2, this);
        } while(true);
        com.maddox.util.HashMapInt hashmapint = com.maddox.rts.NetEnv.cur().objects;
        for(com.maddox.util.HashMapIntEntry hashmapintentry1 = hashmapint.nextEntry(null); hashmapintentry1 != null; hashmapintentry1 = hashmapint.nextEntry(hashmapintentry1))
        {
            com.maddox.rts.NetObj netobj3 = (com.maddox.rts.NetObj)hashmapintentry1.getValue();
            if(netobj3.isCommon())
                com.maddox.rts.MsgNet.postRealDelChannel(netobj3, this);
        }

        clearReceivedGMsgs();
        if(inStream != null)
        {
            try
            {
                inStream.close();
            }
            catch(java.lang.Exception exception) { }
            inStream = null;
        }
    }

    public boolean isGameTime()
    {
        return bGameTime;
    }

    public void setGameTime()
    {
        if(bGameTime)
        {
            return;
        } else
        {
            bGameTime = true;
            lastPacketTime = com.maddox.rts.Time.current();
            com.maddox.rts.MsgTimeOut.post(com.maddox.rts.Time.current(), this, null);
            return;
        }
    }

    public boolean isPause()
    {
        return bPause;
    }

    public void setPause(boolean flag)
    {
        if(bPause == flag)
        {
            return;
        } else
        {
            bPause = flag;
            return;
        }
    }

    protected boolean isEnableFlushReceivedGuarantedMsgs()
    {
        return !bPause;
    }

    protected boolean update()
    {
        if(state < 0)
            return false;
        if(bGameTime)
            return true;
        else
            return _update();
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(!_update())
        {
            return;
        } else
        {
            com.maddox.rts.MsgTimeOut.post(lastPacketTime, this, null);
            return;
        }
    }

    private boolean _update()
    {
        boolean flag;
        if(state < 0)
            return false;
        if(bPause)
            return true;
        if(bQueueSync)
        {
            if(receiveGMsgs.size() == 0)
                bQueueSync = false;
            else
                flushReceivedGuarantedMsgs();
            return true;
        }
        flag = false;
_L2:
        boolean flag1;
        if(bPause || bQueueSync)
            break MISSING_BLOCK_LABEL_513;
        if(!bGameTime)
            lastPacketTime = com.maddox.rts.Time.currentReal();
        else
        if(lastPacketTime > com.maddox.rts.Time.current())
            break MISSING_BLOCK_LABEL_513;
        if(packet.available() <= 0)
            break MISSING_BLOCK_LABEL_406;
        if(ng == 0)
        {
            int i = packet.readUnsignedShort();
            byte abyte0[] = packet.buf;
            int l = packet.pos - 2;
            int i1 = packet.available() + 2;
            abyte0[l + 0] = 28;
            abyte0[l + 1] = -63;
            int j1 = com.maddox.rts.CRC16.checksum(0, abyte0, l, i1);
            if(i != j1)
            {
                flag = false;
                throw new Exception("bad packet CRC");
            }
            ng = packet.readUnsignedByte();
        }
        flag1 = bGameTime;
        do
        {
            if(ng <= 0)
                break MISSING_BLOCK_LABEL_323;
            com.maddox.rts.NetMsgInput netmsginput = getMessage(packet);
            if(syncMessage == getMessageObj)
                bQueueSync = true;
            receiveGMsgSequenceNum_ = receiveGMsgSequenceNum_ + 1 & 0xffff;
            receivedGuarantedMsg(netmsginput, receiveGMsgSequenceNum_);
            ng--;
        } while(!bPause && flag1 == bGameTime);
        if(ng == 0)
            for(; packet.available() > 0; packet.readByte());
        return true;
        do
        {
            if(packet.available() <= 0)
                break;
            com.maddox.rts.NetMsgInput netmsginput1 = getMessage(packet, lastPacketTime);
            if(netmsginput1 != null)
            {
                statIn(true, getMessageObj, netmsginput1);
                if(!bGameTime)
                    com.maddox.rts.MsgNet.postReal(getMessageTime, getMessageObj, netmsginput1);
                else
                    com.maddox.rts.MsgNet.postGame(getMessageTime, getMessageObj, netmsginput1);
            }
        } while(true);
        flag = true;
        packet.setData(this, false, null, 0, 0);
        if(inData.available() != 0)
            break MISSING_BLOCK_LABEL_426;
        if(flag)
            break MISSING_BLOCK_LABEL_513;
        destroy();
        return false;
        try
        {
            int j = inData.readUnsignedShort();
            int k = inData.readUnsignedShort();
            inData.read(buf, 0, k);
            packet.setData(this, false, buf, 0, k);
            if(!bGameTime)
                break MISSING_BLOCK_LABEL_513;
            lastPacketTime += j;
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
            destroy(exception.toString());
            return false;
        }
        if(true) goto _L2; else goto _L1
_L1:
        return true;
    }

    protected void postReceivedGMsg(long l, com.maddox.rts.NetObj netobj, com.maddox.rts.NetMsgInput netmsginput)
    {
        statIn(false, netobj, netmsginput);
        boolean flag = !bGameTime;
        if((netobj instanceof com.maddox.rts.NetChannelCallbackStream) && !((com.maddox.rts.NetChannelCallbackStream)netobj).netChannelCallback(this, netmsginput))
            return;
        if(flag)
            com.maddox.rts.MsgNet.postReal(com.maddox.rts.Time.currentReal(), netobj, netmsginput);
        else
            com.maddox.rts.MsgNet.postGame(com.maddox.rts.Time.current(), netobj, netmsginput);
    }

    public NetChannelInStream(java.io.InputStream inputstream, int i)
    {
        bDestroyGo = false;
        packet = new NetMsgInput();
        buf = new byte[2048];
        receiveGMsgSequenceNum_ = 0;
        bGameTime = false;
        bPause = false;
        bQueueSync = false;
        ng = 0;
        inStream = inputstream;
        inData = new DataInputStream(inputstream);
        flags = i;
        id = com.maddox.rts.RTSConf.cur.netEnv.nextIdChannel(true);
        remoteId = 0;
        socket = new NetEmptySocket();
        remoteAddress = new NetEmptyAddress();
        remotePort = 0;
        lastPacketTime = com.maddox.rts.Time.currentReal();
        packet.setData(this, false, null, 0, 0);
        state = 1;
    }

    protected void putMessageSpawn(com.maddox.rts.NetMsgSpawn netmsgspawn)
        throws java.io.IOException
    {
        if(netmsgspawn._sender instanceof com.maddox.rts.NetChannelCallbackStream)
        {
            ((com.maddox.rts.NetChannelCallbackStream)netmsgspawn._sender).netChannelCallback(this, netmsgspawn);
            return;
        } else
        {
            throw new NetException("putMessageSpawn NOT supported");
        }
    }

    protected void putMessageDestroy(com.maddox.rts.NetMsgDestroy netmsgdestroy)
        throws java.io.IOException
    {
        if(netmsgdestroy._sender instanceof com.maddox.rts.NetChannelCallbackStream)
        {
            ((com.maddox.rts.NetChannelCallbackStream)netmsgdestroy._sender).netChannelCallback(this, netmsgdestroy);
            return;
        } else
        {
            throw new NetException("putMessageDestroy NOT supported");
        }
    }

    protected void putMessage(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        if(netmsgguaranted._sender instanceof com.maddox.rts.NetChannelCallbackStream)
        {
            ((com.maddox.rts.NetChannelCallbackStream)netmsgguaranted._sender).netChannelCallback(this, netmsgguaranted);
            return;
        } else
        {
            throw new NetException("putMessage NOT supported");
        }
    }

    public void startSortGuaranted()
    {
        throw new NetException("startSortGuaranted NOT supported");
    }

    public void stopSortGuaranted()
        throws java.io.IOException
    {
        throw new NetException("stopSortGuaranted NOT supported");
    }

    protected void putMessage(com.maddox.rts.NetMsgFiltered netmsgfiltered)
        throws java.io.IOException
    {
        throw new NetException("putMessage NOT supported");
    }

    public int gSendQueueLenght()
    {
        return 0;
    }

    public int gSendQueueSize()
    {
        return 0;
    }

    protected boolean sendPacket(com.maddox.rts.NetMsgOutput netmsgoutput, com.maddox.rts.NetPacket netpacket)
    {
        return false;
    }

    protected boolean receivePacket(com.maddox.rts.NetMsgInput netmsginput, long l)
        throws java.io.IOException
    {
        return true;
    }

    public int ping()
    {
        return 0;
    }

    public int pingTo()
    {
        return 0;
    }

    public int getMaxTimeout()
    {
        return 1000;
    }

    public void setMaxTimeout(int i)
    {
    }

    public int getCurTimeout()
    {
        return 0;
    }

    public boolean isRequeredSendPacket(long l)
    {
        return false;
    }

    public double getMaxSpeed()
    {
        return maxSendSpeed;
    }

    public void setMaxSpeed(double d)
    {
    }

    public static void sendSyncMsg(com.maddox.rts.NetChannel netchannel)
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            syncMessage.postTo(netchannel, netmsgguaranted);
        }
        catch(java.lang.Exception exception) { }
    }

    protected static void classInit()
    {
        if(syncMessage == null)
            syncMessage = new SyncMessage(8);
    }

    private static final boolean DEBUG = false;
    private boolean bDestroyGo;
    java.io.InputStream inStream;
    java.io.DataInputStream inData;
    com.maddox.rts.NetMsgInput packet;
    byte buf[];
    long lastPacketTime;
    int receiveGMsgSequenceNum_;
    boolean bGameTime;
    boolean bPause;
    boolean bQueueSync;
    int ng;
    private static com.maddox.rts.SyncMessage syncMessage;
}
