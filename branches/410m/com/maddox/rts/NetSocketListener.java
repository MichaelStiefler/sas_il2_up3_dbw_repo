// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetEnv.java

package com.maddox.rts;

import java.io.PrintStream;

// Referenced classes of package com.maddox.rts:
//            NetPacket, NetSocket, Time, RTSConf, 
//            NetEnv

class NetSocketListener extends java.lang.Thread
{

    public void run()
    {
        do
        {
            if(!bDoRun)
                break;
            try
            {
                packet.time = 0L;
                if(socket.receive(packet))
                {
                    int i = packet.getLength();
                    if(i > 1)
                    {
                        byte abyte0[] = new byte[packet.getLength()];
                        java.lang.System.arraycopy(packet.getData(), packet.getOffset(), abyte0, 0, packet.getLength());
                        com.maddox.rts.NetPacket netpacket = new NetPacket(abyte0, 0, abyte0.length, packet.getAddress(), packet.getPort());
                        netpacket.time = packet.time;
                        if(netpacket.time == 0L)
                            netpacket.time = com.maddox.rts.Time.real();
                        com.maddox.rts.RTSConf.cur.netEnv.listenerReceivedPacket(socket, netpacket);
                    }
                }
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        } while(true);
    }

    public NetSocketListener(com.maddox.rts.NetSocket netsocket)
    {
        bDoRun = true;
        socket = netsocket;
        netsocket.setSoTimeout(0);
        packet = new NetPacket(new byte[netsocket.getMaxDataSize()], 0);
    }

    public com.maddox.rts.NetSocket socket;
    public com.maddox.rts.NetPacket packet;
    public boolean bDoRun;
}
