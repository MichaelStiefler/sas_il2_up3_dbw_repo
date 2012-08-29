// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetChannelOutStream.java

package com.maddox.rts;

import com.maddox.rts.net.NetEmptyAddress;
import com.maddox.rts.net.NetEmptySocket;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

// Referenced classes of package com.maddox.rts:
//            NetChannel, NetChannelCallbackStream, NetObj, NetPacket, 
//            NetMsgFiltered, NetChannelGMsgOutput, NetChannelStream, NetMsgSpawn, 
//            NetMsgDestroy, NetMsgGuaranted, Time, MsgNet, 
//            NetEnv, NetMsgOutput, NetChannelArrayList, MsgNetAskNak, 
//            CRC16, RTSConf, NetMsgInput

public class NetChannelOutStream extends com.maddox.rts.NetChannel
    implements com.maddox.rts.NetChannelStream
{

    protected void putMessageSpawn(com.maddox.rts.NetMsgSpawn netmsgspawn)
        throws java.io.IOException
    {
        if((netmsgspawn._sender instanceof com.maddox.rts.NetChannelCallbackStream) && !((com.maddox.rts.NetChannelCallbackStream)netmsgspawn._sender).netChannelCallback(this, netmsgspawn))
        {
            return;
        } else
        {
            super.putMessageSpawn(netmsgspawn);
            return;
        }
    }

    protected void putMessageDestroy(com.maddox.rts.NetMsgDestroy netmsgdestroy)
        throws java.io.IOException
    {
        if((netmsgdestroy._sender instanceof com.maddox.rts.NetChannelCallbackStream) && !((com.maddox.rts.NetChannelCallbackStream)netmsgdestroy._sender).netChannelCallback(this, netmsgdestroy))
        {
            return;
        } else
        {
            super.putMessageDestroy(netmsgdestroy);
            return;
        }
    }

    public void _putMessage(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        super.putMessage(netmsgguaranted);
    }

    protected void putMessage(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        if((netmsgguaranted._sender instanceof com.maddox.rts.NetChannelCallbackStream) && !((com.maddox.rts.NetChannelCallbackStream)netmsgguaranted._sender).netChannelCallback(this, netmsgguaranted))
        {
            return;
        } else
        {
            super.putMessage(netmsgguaranted);
            return;
        }
    }

    protected void computeMessageLen(com.maddox.rts.NetMsgFiltered netmsgfiltered, long l)
    {
        computeMessageLen(netmsgfiltered, com.maddox.rts.Time.fromReal(netmsgfiltered._time), l);
    }

    public void destroy()
    {
        if((state & 0x80000000) != 0)
            return;
        state |= 0x80000000;
        do
        {
            if(objects.isEmpty())
                break;
            com.maddox.util.HashMapIntEntry hashmapintentry = objects.nextEntry(null);
            if(hashmapintentry == null)
                break;
            com.maddox.rts.NetObj netobj = (com.maddox.rts.NetObj)hashmapintentry.getValue();
            int i = hashmapintentry.getKey();
            com.maddox.rts.NetChannelOutStream.destroyNetObj(netobj);
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
            com.maddox.rts.NetObj netobj1 = (com.maddox.rts.NetObj)entry.getKey();
            netobj1.countMirrors--;
            mirrored.remove(netobj1);
            com.maddox.rts.MsgNet.postRealDelChannel(netobj1, this);
        } while(true);
        com.maddox.util.HashMapInt hashmapint = com.maddox.rts.NetEnv.cur().objects;
        for(com.maddox.util.HashMapIntEntry hashmapintentry1 = hashmapint.nextEntry(null); hashmapintentry1 != null; hashmapintentry1 = hashmapint.nextEntry(hashmapintentry1))
        {
            com.maddox.rts.NetObj netobj2 = (com.maddox.rts.NetObj)hashmapintentry1.getValue();
            if(netobj2.isCommon())
                com.maddox.rts.MsgNet.postRealDelChannel(netobj2, this);
        }

        clearSortGuaranted();
        clearSendGMsgs();
        clearSendFMsgs();
        if(outStream != null)
        {
            try
            {
                outStream.close();
            }
            catch(java.lang.Exception exception) { }
            outStream = null;
        }
    }

    protected boolean update()
    {
        return state >= 0;
    }

    public void flush()
    {
        boolean flag = bCheckSpeed;
        bCheckSpeed = false;
        if(flushPacket == null)
        {
            flushPacket = new NetPacket(new byte[2048], 0);
            flushMsg = new NetMsgFiltered(flushPacket.getData());
        }
        while(sendPacket(flushMsg, flushPacket)) ;
        bCheckSpeed = flag;
    }

    protected boolean sendPacket(com.maddox.rts.NetMsgOutput netmsgoutput, com.maddox.rts.NetPacket netpacket)
    {
        if(outStream == null)
            return false;
        long l = com.maddox.rts.Time.current();
        boolean flag = false;
        int i = getSendPacketLen(l);
        if(l >= lastPacketSendTime + 65535L)
        {
            if(i < 20)
                i = 20;
            flag = true;
        }
        if(i <= 0)
            return false;
        byte byte0 = 3;
        if((i -= 7) <= 0)
            return false;
        int j = 0;
        int k = computeSizeSendGMsgs(l);
        int i1 = computeSizeSendFMsgs(l);
        int j1 = filteredSortMsgs.size();
        for(int j2 = 0; j2 < j1; j2++)
        {
            com.maddox.rts.NetMsgFiltered netmsgfiltered = (com.maddox.rts.NetMsgFiltered)filteredSortMsgs.get(j2);
            if(netmsgfiltered._prior < 1.0F && netmsgfiltered._sender != null && netmsgfiltered._sender.isMirror())
            {
                netmsgfiltered._prior = 1.0F;
                filteredMinSizeMsgs += netmsgfiltered._len;
            }
        }

        if(i1 + k == 0 && !flag)
            return false;
        if(i1 + k > i)
        {
            int k1 = i1;
            int k2 = k;
            if(k2 > i)
                k2 = i;
            k1 = i - k2;
            if(k1 < filteredMinSizeMsgs)
            {
                k1 = filteredMinSizeMsgs;
                i = k2 + k1;
                if(i > 1024)
                {
                    i = 1024;
                    if(k1 > i)
                        k1 = i;
                }
            }
            k2 = i - k1;
            if(k2 < 0)
                k2 = 0;
            if(k2 > 0 && k2 < k)
            {
                j = computeCountSendGMsgs(k2);
                k2 = guarantedSizeMsgs;
                if(i - k2 > k1)
                    k1 = i - k2;
            } else
            {
                j = computeCountSendGMsgs(k2);
                k2 = guarantedSizeMsgs;
            }
            i1 = k1;
            k = k2;
        } else
        {
            j = computeCountSendGMsgs(k);
            k = guarantedSizeMsgs;
        }
        i1 = fillFilteredArrayMessages(l, i1);
        if(i1 + k == 0 && !flag)
            return false;
        try
        {
            netmsgoutput.clear();
            outData.writeShort((int)(l - lastPacketSendTime));
            outData.writeShort(byte0 + i1 + k);
            sendTime(netmsgoutput, sendSequenceNum++, l, byte0 + i1 + k);
            netmsgoutput.writeShort(7361);
            netmsgoutput.writeByte(j);
            if(j > 0)
            {
                for(int l1 = 0; l1 < j; l1++)
                {
                    com.maddox.rts.NetChannelGMsgOutput netchannelgmsgoutput = (com.maddox.rts.NetChannelGMsgOutput)sendGMsgs.get(l1);
                    putMessage(netmsgoutput, netchannelgmsgoutput.objIndex, netchannelgmsgoutput.msg, netchannelgmsgoutput.iObjects);
                    com.maddox.rts.NetMsgGuaranted netmsgguaranted = netchannelgmsgoutput.msg;
                    netmsgguaranted.lockDec();
                    if(netmsgguaranted.isRequiredAsk())
                        com.maddox.rts.MsgNetAskNak.postGame(com.maddox.rts.Time.current(), netmsgguaranted._sender, true, netmsgguaranted, this);
                    netchannelgmsgoutput.msg = null;
                }

                sendGMsgs.removeRange(0, j);
            }
            int i2 = filteredSortMsgs.size();
            for(int l2 = 0; l2 < i2; l2++)
            {
                com.maddox.rts.NetMsgFiltered netmsgfiltered1 = (com.maddox.rts.NetMsgFiltered)filteredSortMsgs.get(l2);
                putMessage(netmsgoutput, l, netmsgfiltered1, com.maddox.rts.Time.fromReal(netmsgfiltered1._time));
                netmsgfiltered1.lockDec();
            }

            filteredSortMsgs.clear();
            int i3 = com.maddox.rts.CRC16.checksum(0, netmsgoutput.data(), 0, netmsgoutput.dataLength());
            netmsgoutput.data()[0] = (byte)(i3 >>> 8 & 0xff);
            netmsgoutput.data()[1] = (byte)(i3 & 0xff);
            outData.write(netmsgoutput.data(), 0, netmsgoutput.size());
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.NetChannelOutStream.printDebug(exception);
        }
        return !bCheckSpeed;
    }

    protected boolean receivePacket(com.maddox.rts.NetMsgInput netmsginput, long l)
        throws java.io.IOException
    {
        return true;
    }

    public NetChannelOutStream(java.io.OutputStream outputstream, int i)
    {
        bCheckSpeed = true;
        outStream = outputstream;
        outData = new DataOutputStream(outputstream);
        flags = i;
        id = com.maddox.rts.RTSConf.cur.netEnv.nextIdChannel(false);
        remoteId = 1;
        socket = new NetEmptySocket();
        remoteAddress = new NetEmptyAddress();
        remotePort = 0;
        nextPacketSendTime = lastPacketSendTime = com.maddox.rts.Time.current();
        state = 1;
    }

    public boolean isRequeredSendPacket(long l)
    {
        return com.maddox.rts.Time.current() > nextPacketSendTime;
    }

    protected int getSendPacketLen(long l)
    {
        if(!bCheckSpeed)
            return 1024;
        if(l < nextPacketSendTime)
            return 0;
        double d = 10D;
        double d1 = maxChSendSpeed * d;
        if(d1 < 256D)
            d1 = 256D;
        if(d1 > 1024D)
            d1 = 1024D;
        return (int)d1;
    }

    protected void sendTime(com.maddox.rts.NetMsgOutput netmsgoutput, int i, long l, int j)
        throws java.io.IOException
    {
        double d = 10D;
        if(maxChSendSpeed > 0.0D)
            d = (double)j / maxChSendSpeed;
        if(d < 10D)
            d = 10D;
        nextPacketSendTime = l + (long)d;
        lastPacketSendTime = l;
    }

    public void setCheckSpeed(boolean flag)
    {
        bCheckSpeed = flag;
    }

    public double getMaxSpeed()
    {
        return maxSendSpeed;
    }

    public void setMaxSpeed(double d)
    {
        if(d == maxSendSpeed)
        {
            return;
        } else
        {
            maxSendSpeed = d;
            maxChSendSpeed = d;
            return;
        }
    }

    private static final boolean DEBUG = false;
    java.io.OutputStream outStream;
    java.io.DataOutputStream outData;
    com.maddox.rts.NetPacket flushPacket;
    com.maddox.rts.NetMsgFiltered flushMsg;
    private boolean bCheckSpeed;
}
