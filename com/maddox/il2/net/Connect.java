// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Connect.java

package com.maddox.il2.net;

import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.rts.Finger;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgAddListener;
import com.maddox.rts.MsgNet;
import com.maddox.rts.MsgNetExtListener;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSocket;
import com.maddox.rts.Time;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.PrintStream;
import java.util.List;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.net:
//            NetBanned, NetServerParams, NetChannelListener

public class Connect
    implements com.maddox.rts.NetConnect, com.maddox.rts.MsgNetExtListener, com.maddox.rts.MsgTimeOutListener
{

    public void bindEnable(boolean flag)
    {
        bBindEnable = flag;
    }

    public boolean isBindEnable()
    {
        return bBindEnable;
    }

    private static java.lang.String badVersionMessage()
    {
        return "Server uses a different version of the game (4.09m).";
    }

    private void bindReceiveConnect(java.util.StringTokenizer stringtokenizer, com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetAddress netaddress, int i)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return;
        if(!stringtokenizer.hasMoreTokens())
            return;
        java.lang.String s = stringtokenizer.nextToken();
        if(!stringtokenizer.hasMoreTokens())
            return;
        java.lang.String s1 = stringtokenizer.nextToken();
        if(!stringtokenizer.hasMoreTokens())
            return;
        java.lang.String s2 = stringtokenizer.nextToken();
        if(!"FB_PF_v_4.09m".equals(s) && !"il2_r01_0f".equals(s))
        {
            java.lang.String s3 = "reject " + s1 + " " + s2 + " " + com.maddox.il2.net.Connect.badVersionMessage();
            com.maddox.rts.NetEnv.cur().postExtUTF((byte)32, s3, netsocket, netaddress, i);
            return;
        }
        if(banned.isExist(netaddress))
            return;
        int j = 0;
        try
        {
            j = java.lang.Integer.parseInt(s1);
        }
        catch(java.lang.Exception exception)
        {
            return;
        }
        int k = 0;
        try
        {
            k = java.lang.Integer.parseInt(s2);
        }
        catch(java.lang.Exception exception1)
        {
            return;
        }
        com.maddox.rts.NetChannel netchannel = null;
        java.util.List list = com.maddox.rts.NetEnv.channels();
        int l = list.size();
        for(int i1 = 0; i1 < l; i1++)
        {
            com.maddox.rts.NetChannel netchannel1 = (com.maddox.rts.NetChannel)list.get(i1);
            if(netchannel1.socket().equals(netsocket) && netchannel1.remoteId() == j && netchannel1.remoteAddress().equals(netaddress) && netchannel1.remotePort() == i)
                if(netchannel1.state() == 1 && netchannel1.getInitStamp() == k)
                {
                    netchannel = netchannel1;
                } else
                {
                    netchannel1.destroy("Reconnect user");
                    return;
                }
        }

        if(netchannel == null)
        {
            if(!isBindEnable() || netsocket.maxChannels == 0)
            {
                java.lang.String s4 = "reject " + s1 + " " + k + " connect disabled";
                com.maddox.rts.NetEnv.cur().postExtUTF((byte)32, s4, netsocket, netaddress, i);
                return;
            }
            if(netsocket.maxChannels <= netsocket.countChannels)
            {
                java.lang.String s5 = "reject " + s1 + " " + k + " limit connections = " + netsocket.maxChannels;
                com.maddox.rts.NetEnv.cur().postExtUTF((byte)32, s5, netsocket, netaddress, i);
                return;
            }
            int j1 = com.maddox.rts.NetEnv.hosts().size();
            if(!com.maddox.il2.game.Main.cur().netServerParams.isDedicated())
                j1++;
            if(j1 >= com.maddox.il2.game.Main.cur().netServerParams.getMaxUsers())
            {
                java.lang.String s7 = "reject " + s1 + " " + k + " limit users = " + com.maddox.il2.game.Main.cur().netServerParams.getMaxUsers();
                com.maddox.rts.NetEnv.cur().postExtUTF((byte)32, s7, netsocket, netaddress, i);
                return;
            }
            int k1 = com.maddox.rts.NetEnv.cur().nextIdChannel(true);
            netchannel = com.maddox.rts.NetEnv.cur().createChannel(1, k1, j, netsocket, netaddress, i, this);
            netchannel.setInitStamp(k);
            setChannel(netchannel, k1, j, k);
            netsocket.countChannels++;
            if(!"FB_PF_v_4.09m".equals(s))
                kickChannel(netchannel);
        }
        java.lang.String s6 = "connected " + s + " " + j + " " + k + " " + netchannel.id();
        com.maddox.rts.NetEnv.cur().postExtUTF((byte)32, s6, netsocket, netaddress, i);
    }

    private void kickChannel(java.lang.Object obj)
    {
        if(!(obj instanceof com.maddox.rts.NetChannel))
            return;
        com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)obj;
        if(netchannel.isDestroying())
            return;
        if(netchannel.isReady())
        {
            netchannel.destroy(com.maddox.il2.net.Connect.badVersionMessage());
            return;
        }
        if(netchannel.isIniting())
            new com.maddox.rts.MsgAction(64, 0.5D, netchannel) {

                public void doAction(java.lang.Object obj1)
                {
                    kickChannel(obj1);
                }

            }
;
    }

    public void join(com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetAddress netaddress, int i)
    {
        if(bJoin)
            return;
        joinSocket = netsocket;
        joinAddr = netaddress;
        joinPort = i;
        joinTimeOut = 30000L;
        joinId = com.maddox.rts.NetEnv.cur().nextIdChannel(false);
        joinStamp = com.maddox.rts.Time.raw();
        joinSocket.countChannels++;
        joinSend();
        bJoin = true;
        if(!ticker.busy())
            ticker.post(com.maddox.rts.Time.currentReal() + 500L);
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(obj != null && (obj instanceof com.maddox.rts.NetChannel))
        {
            msgTimeOutStep((com.maddox.rts.NetChannel)obj);
            return;
        }
        if(bJoin)
        {
            joinTimeOut -= 500L;
            if(joinTimeOut < 0L)
            {
                java.lang.System.out.println("socket join to " + joinAddr.getHostAddress() + ":" + joinPort + " failed: timeout");
                if(com.maddox.il2.game.Main.cur().netChannelListener != null)
                    com.maddox.il2.game.Main.cur().netChannelListener.netChannelCanceled("Connection attempt to remote host failed.  Reason: Timeout.");
                joinSocket.countChannels--;
                bJoin = false;
                return;
            }
            joinSend();
            ticker.post(com.maddox.rts.Time.currentReal() + 500L);
        }
    }

    public void joinBreak()
    {
        if(bJoin)
        {
            java.lang.System.out.println("socket join to " + joinAddr.getHostAddress() + ":" + joinPort + " breaked");
            if(com.maddox.il2.game.Main.cur().netChannelListener != null)
                com.maddox.il2.game.Main.cur().netChannelListener.netChannelCanceled("Connection attempt to remote host failed.  Reason: User Cancel.");
            joinSocket.countChannels--;
            bJoin = false;
        }
    }

    public boolean isJoinProcess()
    {
        return bJoin;
    }

    private void joinSend()
    {
        java.lang.String s = "connect FB_PF_v_4.09m " + joinId + " " + joinStamp;
        com.maddox.rts.NetEnv.cur().postExtUTF((byte)32, s, joinSocket, joinAddr, joinPort);
    }

    private void joinReceiveConnected(java.util.StringTokenizer stringtokenizer, com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetAddress netaddress, int i)
    {
        if(!bJoin)
            return;
        if(!netsocket.equals(joinSocket))
            return;
        if(!netaddress.equals(joinAddr))
            return;
        if(i != joinPort)
            return;
        if(!stringtokenizer.hasMoreTokens())
            return;
        java.lang.String s = stringtokenizer.nextToken();
        if(!"FB_PF_v_4.09m".equals(s))
            return;
        if(!stringtokenizer.hasMoreTokens())
            return;
        java.lang.String s1 = stringtokenizer.nextToken();
        int j = 0;
        try
        {
            j = java.lang.Integer.parseInt(s1);
        }
        catch(java.lang.Exception exception)
        {
            return;
        }
        if(j != joinId)
            return;
        if(!stringtokenizer.hasMoreTokens())
            return;
        java.lang.String s2 = stringtokenizer.nextToken();
        int k = 0;
        try
        {
            k = java.lang.Integer.parseInt(s2);
        }
        catch(java.lang.Exception exception1)
        {
            return;
        }
        if(k != joinStamp)
            return;
        if(!stringtokenizer.hasMoreTokens())
            return;
        java.lang.String s3 = stringtokenizer.nextToken();
        int l = 0;
        try
        {
            l = java.lang.Integer.parseInt(s3);
        }
        catch(java.lang.Exception exception2)
        {
            return;
        }
        java.lang.System.out.println("socket start connecting to " + joinAddr.getHostAddress() + ":" + joinPort);
        com.maddox.rts.NetChannel netchannel = com.maddox.rts.NetEnv.cur().createChannel(7, joinId, l, joinSocket, joinAddr, joinPort, this);
        netchannel.setInitStamp(k);
        setChannel(netchannel, l, joinId, k);
        bJoin = false;
    }

    private void joinReceiveReject(java.util.StringTokenizer stringtokenizer, com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetAddress netaddress, int i)
    {
        if(!bJoin)
            return;
        if(!netsocket.equals(joinSocket))
            return;
        if(!netaddress.equals(joinAddr))
            return;
        if(i != joinPort)
            return;
        if(!stringtokenizer.hasMoreTokens())
            return;
        java.lang.String s = stringtokenizer.nextToken();
        int j = 0;
        try
        {
            j = java.lang.Integer.parseInt(s);
        }
        catch(java.lang.Exception exception)
        {
            return;
        }
        if(j != joinId)
            return;
        if(!stringtokenizer.hasMoreTokens())
            return;
        java.lang.String s1 = stringtokenizer.nextToken();
        java.lang.String s2 = "???";
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        boolean flag = false;
        try
        {
            int k = java.lang.Integer.parseInt(s1);
            if(k != joinStamp)
                return;
        }
        catch(java.lang.Exception exception1)
        {
            stringbuffer.append(s1);
            stringbuffer.append(' ');
            s2 = s1;
        }
        if(stringtokenizer.hasMoreTokens())
        {
            for(; stringtokenizer.hasMoreTokens(); stringbuffer.append(' '))
                stringbuffer.append(stringtokenizer.nextToken());

            s2 = stringbuffer.toString();
        }
        java.lang.System.out.println("socket join to " + joinAddr.getHostAddress() + ":" + joinPort + " regect (" + s2 + ")");
        if(com.maddox.il2.game.Main.cur().netChannelListener != null)
            com.maddox.il2.game.Main.cur().netChannelListener.netChannelCanceled("Connection attempt to remote host rejected.  Reason: " + s2);
        joinSocket.countChannels--;
        bJoin = false;
    }

    public void msgNetExt(byte abyte0[], com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetAddress netaddress, int i)
    {
        if(abyte0 == null || abyte0.length < 2)
            return;
        if(abyte0[0] != 32)
            return;
        java.lang.String s = "";
        try
        {
            _netMsgInput.setData(null, false, abyte0, 1, abyte0.length - 1);
            s = _netMsgInput.readUTF();
        }
        catch(java.lang.Exception exception)
        {
            return;
        }
        java.util.StringTokenizer stringtokenizer = new StringTokenizer(s, " ");
        if(stringtokenizer.hasMoreTokens())
        {
            java.lang.String s1 = stringtokenizer.nextToken();
            if(s1.equals("connect"))
                bindReceiveConnect(stringtokenizer, netsocket, netaddress, i);
            else
            if(s1.equals("connected"))
                joinReceiveConnected(stringtokenizer, netsocket, netaddress, i);
            else
            if(s1.equals("reject"))
                joinReceiveReject(stringtokenizer, netsocket, netaddress, i);
            else
            if(s1.equals("rinfo"))
                receiveRequestInfo(stringtokenizer, netsocket, netaddress, i);
        }
    }

    public void msgRequest(java.lang.String s)
    {
        if(com.maddox.il2.game.Main.cur().netChannelListener != null)
            com.maddox.il2.game.Main.cur().netChannelListener.netChannelRequest(s);
    }

    public void channelCreated(com.maddox.rts.NetChannel netchannel)
    {
        if(!netchannel.isPublic())
        {
            java.lang.System.out.println("socket channel '" + netchannel.id() + "' created: " + netchannel.remoteAddress().getHostAddress() + ":" + netchannel.remotePort());
            return;
        }
        java.lang.System.out.println("socket channel '" + netchannel.id() + "' start creating: " + netchannel.remoteAddress().getHostAddress() + ":" + netchannel.remotePort());
        netchannel.startSortGuaranted();
        com.maddox.util.HashMapInt hashmapint = com.maddox.rts.NetEnv.cur().objects;
        for(com.maddox.util.HashMapIntEntry hashmapintentry = hashmapint.nextEntry(null); hashmapintentry != null; hashmapintentry = hashmapint.nextEntry(hashmapintentry))
        {
            com.maddox.rts.NetObj netobj = (com.maddox.rts.NetObj)hashmapintentry.getValue();
            if(!netchannel.isMirrored(netobj))
                com.maddox.rts.MsgNet.postRealNewChannel(netobj, netchannel);
        }

        netchannel.setStateInit(2);
        com.maddox.rts.MsgTimeOut.post(64, com.maddox.rts.Time.currentReal() + 1L, this, netchannel);
    }

    private void msgTimeOutStep(com.maddox.rts.NetChannel netchannel)
    {
        if(netchannel.isDestroying())
            return;
        int i = netchannel.state();
        switch(i)
        {
        case 2: // '\002'
            try
            {
                netchannel.stopSortGuaranted();
            }
            catch(java.lang.Exception exception)
            {
                netchannel.destroy("Cycle inits");
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                return;
            }
            netchannel.setStateInit(3);
            // fall through

        case 3: // '\003'
            if(com.maddox.il2.game.Main.cur().netServerParams == null)
            {
                com.maddox.rts.MsgTimeOut.post(64, com.maddox.rts.Time.currentReal() + 200L, this, netchannel);
                return;
            }
            netchannel.setStateInit(0);
            if(!com.maddox.rts.NetEnv.isServer())
                java.lang.System.out.println("socket channel '" + netchannel.id() + "', ip " + netchannel.remoteAddress().getHostAddress() + ":" + netchannel.remotePort() + ", is complete created");
            if(com.maddox.il2.game.Main.cur().netChannelListener != null)
                com.maddox.il2.game.Main.cur().netChannelListener.netChannelCreated(netchannel);
            return;

        default:
            return;
        }
    }

    public void channelNotCreated(com.maddox.rts.NetChannel netchannel, java.lang.String s)
    {
        java.lang.System.out.println("socket channel NOT created (" + s + "): " + netchannel.remoteAddress().getHostAddress() + ":" + netchannel.remotePort());
        if(com.maddox.il2.game.Main.cur().netChannelListener != null)
            com.maddox.il2.game.Main.cur().netChannelListener.netChannelCanceled("Connection attempt to remote host failed.  Reason: " + s);
    }

    public void channelDestroying(com.maddox.rts.NetChannel netchannel, java.lang.String s)
    {
        java.lang.System.out.println("socketConnection with " + netchannel.remoteAddress() + ":" + netchannel.remotePort() + " on channel " + netchannel.id() + " lost.  Reason: " + s);
        if(com.maddox.il2.game.Main.cur().netChannelListener != null)
            com.maddox.il2.game.Main.cur().netChannelListener.netChannelDestroying(netchannel, "The communication with the remote host is lost. Reason: " + s);
    }

    private void receiveRequestInfo(java.util.StringTokenizer stringtokenizer, com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetAddress netaddress, int i)
    {
        if(!stringtokenizer.hasMoreTokens())
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return;
        if(!isBindEnable())
            return;
        if(banned.isExist(netaddress))
            return;
        if(!com.maddox.il2.game.Main.cur().netServerParams.isMaster() && com.maddox.il2.game.Main.cur().netServerParams.masterChannel().userState == -1)
            return;
        java.lang.String s = stringtokenizer.nextToken();
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("ainfo");
        stringbuffer.append(' ');
        stringbuffer.append(s);
        stringbuffer.append(' ');
        stringbuffer.append("FB_PF_v_4.09m");
        stringbuffer.append(' ');
        stringbuffer.append(com.maddox.il2.game.Main.cur().netServerParams.isMaster() ? "1 " : "0 ");
        stringbuffer.append("" + (com.maddox.il2.game.Main.cur().netServerParams.getType() >> 4 & 7) + " ");
        stringbuffer.append(com.maddox.il2.game.Main.cur().netServerParams.isProtected() ? "1 " : "0 ");
        stringbuffer.append(com.maddox.il2.game.Main.cur().netServerParams.isDedicated() ? "1 " : "0 ");
        stringbuffer.append(com.maddox.il2.game.Main.cur().netServerParams.isCoop() ? "1 " : "0 ");
        stringbuffer.append(com.maddox.il2.game.Mission.isPlaying() ? "1 " : "0 ");
        stringbuffer.append(netsocket.maxChannels);
        stringbuffer.append(' ');
        stringbuffer.append(netsocket.countChannels);
        stringbuffer.append(' ');
        stringbuffer.append(com.maddox.il2.game.Main.cur().netServerParams.getMaxUsers());
        stringbuffer.append(' ');
        int j = com.maddox.rts.NetEnv.hosts().size();
        if(!com.maddox.il2.game.Main.cur().netServerParams.isDedicated())
            j++;
        stringbuffer.append(j);
        stringbuffer.append(' ');
        stringbuffer.append(com.maddox.il2.game.Main.cur().netServerParams.serverName());
        java.lang.String s1 = stringbuffer.toString();
        com.maddox.rts.NetEnv.cur().postExtUTF((byte)32, s1, netsocket, netaddress, i);
    }

    public Connect()
    {
        banned = new NetBanned();
        bBindEnable = false;
        bJoin = false;
        com.maddox.rts.MsgAddListener.post(64, com.maddox.rts.NetEnv.cur(), this, null);
        ticker = new MsgTimeOut();
        ticker.setNotCleanAfterSend();
        ticker.setFlags(64);
        ticker.setListener(this);
    }

    private void setChannel(com.maddox.rts.NetChannel netchannel, int i, int j, int k)
    {
        int l = k + i + j;
        if(l < 0)
            l = -l;
        int i1 = l % 16 + 12;
        int j1 = l % com.maddox.rts.Finger.kTable.length;
        if(i1 < 0)
            i1 = -i1 % 16;
        if(i1 < 10)
            i1 = 10;
        if(j1 < 0)
            j1 = -j1 % com.maddox.rts.Finger.kTable.length;
        byte abyte0[] = new byte[i1];
        for(int k1 = 0; k1 < i1; k1++)
            abyte0[k1] = com.maddox.rts.Finger.kTable[(j1 + k1) % com.maddox.rts.Finger.kTable.length];

        netchannel.swTbl = abyte0;
        for(int l1 = 0; l1 < 2; l1++)
            netchannel.crcInit[l1] = com.maddox.rts.Finger.kTable[(j1 + i1 + l1) % com.maddox.rts.Finger.kTable.length];

    }

    static final boolean bLog = false;
    static final long TIME_OUT = 500L;
    static final long FULL_TIME_OUT = 30000L;
    public static final java.lang.String PROMPT = "socket";
    public static final java.lang.String VERSION = "FB_PF_v_4.09m";
    static final java.lang.String CONNECT = "connect";
    static final java.lang.String CONNECTED = "connected";
    static final java.lang.String REJECT = "reject";
    static final java.lang.String REQUESTINFO = "rinfo";
    static final java.lang.String ANSWERINFO = "ainfo";
    public com.maddox.il2.net.NetBanned banned;
    boolean bBindEnable;
    boolean bJoin;
    int joinId;
    long joinTimeOut;
    com.maddox.rts.NetSocket joinSocket;
    com.maddox.rts.NetAddress joinAddr;
    int joinPort;
    int joinStamp;
    private static com.maddox.rts.NetMsgInput _netMsgInput = new NetMsgInput();
    private com.maddox.rts.MsgTimeOut ticker;


}
