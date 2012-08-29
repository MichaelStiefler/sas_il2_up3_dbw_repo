// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetFileTransport.java

package com.maddox.rts.net;

import com.maddox.rts.Destroy;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgNetAskNakListener;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;

// Referenced classes of package com.maddox.rts.net:
//            NetFileServer, NetFileRequest, NetFileClient

public class NetFileTransport extends com.maddox.rts.NetObj
    implements com.maddox.rts.MsgNetAskNakListener
{
    public class Request
        implements com.maddox.rts.Destroy
    {

        public void typeState(java.io.PrintStream printstream)
        {
            java.lang.String s = null;
            switch(state)
            {
            case 0: // '\0'
                s = "Init";
                break;

            case 4: // '\004'
                s = "Wait Parent Request";
                break;

            case 1: // '\001'
                s = "Send Name";
                break;

            case 2: // '\002'
                s = "Send Data";
                break;

            case 3: // '\003'
                s = "Send Cancel";
                break;

            case -1: 
                s = "Destroy";
                break;

            default:
                s = "UNKNOWN";
                break;
            }
            printstream.println("Request(" + localId + ") state = " + s);
            printstream.println("  request: " + nreq.toString());
            if(nreq._serverIn != null)
                printstream.println("  serverIn:  " + nreq.server().getStateInInfo(nreq));
            if(nreq._serverOut != null)
                printstream.println("  serverOut: " + nreq.server().getStateOutInfo(nreq));
        }

        public void netInput(com.maddox.rts.NetMsgInput netmsginput, int i)
            throws java.io.IOException
        {
            if(parent != null)
                return;
            if(isDestroyed())
                return;
            if(state == 3 || state == 0 || state == 4)
                return;
            int j = 1;
            boolean flag = true;
            switch(i)
            {
            case 0: // '\0'
                DEBUGR(localId, "get data");
                flag = false;
                j = nreq.server().getAnswerData(nreq, netmsginput);
                break;

            case 1: // '\001'
                DEBUGR(localId, "get ext data");
                flag = false;
                j = nreq.server().getAnswerExtData(nreq, netmsginput);
                break;

            case 2: // '\002'
                DEBUGR(localId, "get 'success'");
                j = 0;
                break;

            case 3: // '\003'
                DEBUGR(localId, "get 'owner disconnect'");
                j = -1;
                break;

            case 4: // '\004'
                DEBUGR(localId, "get 'not found'");
                j = -2;
                break;

            case 5: // '\005'
                DEBUGR(localId, "get 'io error'");
                j = -3;
                break;

            default:
                return;
            }
            if(j == 1)
                return;
            if(flag)
                destroy();
            else
                state = 3;
            _doAnswer(j);
        }

        private void _doAnswer(int i)
        {
            nreq.setState(i);
            doAnswer(nreq);
            if(parent == null)
            {
                java.util.ArrayList arraylist = (java.util.ArrayList)chRequest.get(channel);
                int j = arraylist.size();
                for(int k = 0; k < j; k++)
                {
                    com.maddox.rts.net.Request request = (com.maddox.rts.net.Request)arraylist.get(k);
                    if(request.parent == this && !request.isDestroyed())
                    {
                        request.destroy();
                        request.nreq.setState(i);
                        request.nreq.setLocalFileName(nreq.localFileName());
                        doAnswer(request.nreq);
                    }
                }

            }
        }

        public void delChannel()
        {
            if(isDestroyed())
            {
                return;
            } else
            {
                destroy();
                _doAnswer(-1);
                return;
            }
        }

        public void cancel()
        {
            if(isDestroyed())
                return;
            bCanceled = true;
            if(parent == null)
            {
                boolean flag = false;
                java.util.ArrayList arraylist = (java.util.ArrayList)chRequest.get(channel);
                int i = arraylist.size();
                for(int j = 0; j < i; j++)
                {
                    com.maddox.rts.net.Request request1 = (com.maddox.rts.net.Request)arraylist.get(j);
                    if(request1.parent != this || request1.isDestroyed())
                        continue;
                    flag = true;
                    break;
                }

                if(!flag)
                    if(state == 0)
                        destroy();
                    else
                        state = 3;
                if(!nreq.isEnded())
                {
                    nreq.setState(-4);
                    doAnswer(nreq);
                }
            } else
            {
                com.maddox.rts.net.Request request = parent;
                destroy();
                if(!nreq.isEnded())
                {
                    nreq.setState(-4);
                    doAnswer(nreq);
                }
                if(request.bCanceled)
                    request.cancel();
            }
        }

        public boolean netOutput(com.maddox.rts.NetMsgGuaranted netmsgguaranted, int i)
            throws java.io.IOException
        {
            if(parent != null)
                return false;
            switch(state)
            {
            case 4: // '\004'
            default:
                return false;

            case 0: // '\0'
                DEBUGR(localId, "send 'spawn begin'");
                writeId(netmsgguaranted, 1);
                netmsgguaranted.writeByte(nreq.prior());
                netmsgguaranted.writeNetObj(nreq.owner());
                netmsgguaranted.writeNetObj((com.maddox.rts.NetObj)nreq.server());
                netmsgguaranted.writeShort(nreq.ownerFileName().length());
                state = 1;
                return true;

            case 1: // '\001'
                DEBUGR(localId, "send 'name'");
                writeId(netmsgguaranted, 2);
                if(writeStr(netmsgguaranted, i, nreq.ownerFileName()))
                    state = 2;
                return true;

            case 2: // '\002'
                boolean flag = nreq.server().sendRequestData(nreq, netmsgguaranted, i, 0 | localId | 1);
                if(flag)
                    DEBUGR(localId, "send data");
                return flag;

            case 3: // '\003'
                DEBUGR(localId, "send 'cancel'");
                writeId(netmsgguaranted, 3);
                destroy();
                return true;
            }
        }

        private void writeId(com.maddox.rts.NetMsgGuaranted netmsgguaranted, int i)
            throws java.io.IOException
        {
            netmsgguaranted.writeInt(i << 28 | localId | 1);
        }

        private boolean writeStr(com.maddox.rts.NetMsgGuaranted netmsgguaranted, int i, java.lang.String s)
            throws java.io.IOException
        {
            int j = s.length() * 2;
            int k;
            for(k = 0; k < i && k + strOffset < j; k += 2)
            {
                int l = (k + strOffset) / 2;
                char c = s.charAt(l);
                netmsgguaranted.writeByte(c & 0xff);
                netmsgguaranted.writeByte(c >> 8 & 0xff);
            }

            strOffset += k;
            return strOffset == j;
        }

        public boolean isDestroyed()
        {
            return state == -1;
        }

        public void destroy()
        {
            if(isDestroyed())
            {
                return;
            } else
            {
                nreq.server().destroyIn(nreq);
                state = -1;
                bCheckDestroyed = true;
                return;
            }
        }

        public com.maddox.rts.net.Request parent;
        public com.maddox.rts.NetChannel channel;
        public com.maddox.rts.net.NetFileRequest nreq;
        public int localId;
        private static final int ST_INIT = 0;
        private static final int ST_SEND_NAME = 1;
        private static final int ST_SEND_DATA = 2;
        private static final int ST_SEND_CANCEL = 3;
        private static final int ST_WAIT_PARENT = 4;
        private static final int ST_DESTROY = -1;
        private int state;
        private boolean bCanceled;
        private int strOffset;

        public Request(com.maddox.rts.net.NetFileRequest netfilerequest, int i)
        {
            state = 0;
            bCanceled = false;
            strOffset = 0;
            nreq = netfilerequest;
            localId = i;
            channel = netfilerequest.owner().masterChannel();
            java.util.ArrayList arraylist = (java.util.ArrayList)chRequest.get(channel);
            int l = arraylist.size();
            for(int j = 0; j < l; j++)
            {
                com.maddox.rts.net.Request request = (com.maddox.rts.net.Request)arraylist.get(j);
                if(request.parent != null || !request.nreq.equals(netfilerequest))
                    continue;
                parent = request;
                break;
            }

            int k;
            for(k = 0; k < l; k++)
            {
                com.maddox.rts.net.Request request1 = (com.maddox.rts.net.Request)arraylist.get(k);
                if(netfilerequest.prior() <= request1.nreq.prior())
                    continue;
                arraylist.add(k, this);
                break;
            }

            if(k == l)
                arraylist.add(this);
            if(parent == null)
                requestPostMsg(channel);
            else
                state = 4;
        }
    }

    public class Answer
        implements com.maddox.rts.net.NetFileClient, com.maddox.rts.Destroy
    {

        public void typeState(java.io.PrintStream printstream)
        {
            java.lang.String s = null;
            switch(state)
            {
            case 0: // '\0'
                s = "Init";
                break;

            case 1: // '\001'
                s = "Transfer";
                break;

            case 2: // '\002'
                s = "Owner Disconnect";
                break;

            case 3: // '\003'
                s = "Not Found";
                break;

            case 4: // '\004'
                s = "IO Error";
                break;

            case -1: 
                s = "Destroy";
                break;

            default:
                s = "UNKNOWN";
                break;
            }
            printstream.println("Answer(" + remoteId + ") state = " + s);
            printstream.println("  request: " + nreq.toString());
            if(nreq._serverIn != null)
                printstream.println("  serverIn:  " + nreq.server().getStateInInfo(nreq));
            if(nreq._serverOut != null)
                printstream.println("  serverOut: " + nreq.server().getStateOutInfo(nreq));
        }

        public void netInput(com.maddox.rts.NetMsgInput netmsginput, int i)
            throws java.io.IOException
        {
            if(isDestroyed())
                return;
            if(i == 3)
            {
                DEBUGA(remoteId, "input 'cancel'");
                delChannel();
                return;
            }
            switch(state)
            {
            default:
                break;

            case 0: // '\0'
                if(i == 2)
                {
                    java.lang.String s = readStr(netmsginput);
                    if(nreq.ownerFileName == null)
                        nreq.ownerFileName = s;
                    else
                        nreq.ownerFileName = nreq.ownerFileName + s;
                    if(ownerFileNameLength == nreq.ownerFileName.length())
                    {
                        state = 1;
                        nreq.doRequest();
                    }
                }
                break;

            case 1: // '\001'
                if(i == 0)
                {
                    DEBUGA(remoteId, "input data");
                    nreq.server().getRequestData(nreq, netmsginput);
                }
                break;
            }
        }

        public void delChannel()
        {
            destroy();
            if((nreq.server() instanceof com.maddox.rts.Destroy) && ((com.maddox.rts.Destroy)nreq.server()).isDestroyed())
                return;
            if(nreq.state() == 1)
                nreq.doCancel();
        }

        public void netFileAnswer(com.maddox.rts.net.NetFileRequest netfilerequest)
        {
            switch(netfilerequest.state())
            {
            case -1: 
                state = 2;
                break;

            case -2: 
                state = 3;
                break;

            case -3: 
                state = 4;
                break;
            }
            requestPostMsg(channel);
        }

        public boolean netOutput(com.maddox.rts.NetMsgGuaranted netmsgguaranted, int i)
            throws java.io.IOException
        {
            switch(state)
            {
            case 1: // '\001'
                int j = nreq.server().getAnswerState(nreq, i);
                switch(j)
                {
                case 1: // '\001'
                    DEBUGA(remoteId, "send data");
                    nreq.server().sendAnswerData(nreq, netmsgguaranted, i, 0 | remoteId);
                    return true;

                case 2: // '\002'
                    DEBUGA(remoteId, "send ext data");
                    nreq.server().sendAnswerData(nreq, netmsgguaranted, i, 0x10000000 | remoteId);
                    return true;

                case 3: // '\003'
                    DEBUGA(remoteId, "send 'success'");
                    writeId(netmsgguaranted, 2);
                    destroy();
                    return true;

                case 4: // '\004'
                    DEBUGA(remoteId, "send 'io error 0'");
                    writeId(netmsgguaranted, 5);
                    destroy();
                    return true;
                }
                break;

            case 2: // '\002'
                DEBUGA(remoteId, "send 'owner disconnect'");
                writeId(netmsgguaranted, 3);
                destroy();
                return true;

            case 3: // '\003'
                DEBUGA(remoteId, "send 'not found'");
                writeId(netmsgguaranted, 4);
                destroy();
                return true;

            case 4: // '\004'
                DEBUGA(remoteId, "send 'io error 1'");
                writeId(netmsgguaranted, 5);
                destroy();
                return true;
            }
            return false;
        }

        private void writeId(com.maddox.rts.NetMsgGuaranted netmsgguaranted, int i)
            throws java.io.IOException
        {
            netmsgguaranted.writeInt(i << 28 | remoteId);
        }

        private java.lang.String readStr(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            java.lang.StringBuffer stringbuffer = new StringBuffer();
            int i;
            for(; netmsginput.available() > 0; stringbuffer.append((char)i))
                i = netmsginput.readUnsignedByte() | netmsginput.readUnsignedByte() << 8;

            return stringbuffer.toString();
        }

        public boolean isDestroyed()
        {
            return state == -1;
        }

        public void destroy()
        {
            if(isDestroyed())
            {
                return;
            } else
            {
                nreq.server().destroyOut(nreq);
                state = -1;
                bCheckDestroyed = true;
                return;
            }
        }

        public com.maddox.rts.NetChannel channel;
        public com.maddox.rts.net.NetFileRequest nreq;
        public int ownerFileNameLength;
        public int remoteId;
        private static final int ST_INIT = 0;
        private static final int ST_TRANSFER = 1;
        private static final int ST_DISCONNECT = 2;
        private static final int ST_NOT_FOUND = 3;
        private static final int ST_IO = 4;
        private static final int ST_DESTROY = -1;
        private int state;

        public Answer(com.maddox.rts.NetChannel netchannel, int i, com.maddox.rts.NetObj netobj, com.maddox.rts.net.NetFileServer netfileserver, int j, int k)
        {
            state = 0;
            channel = netchannel;
            remoteId = j;
            ownerFileNameLength = k;
            nreq = new NetFileRequest(this, netfileserver, null, i, netobj, null);
            java.util.ArrayList arraylist = (java.util.ArrayList)chAnswer.get(netchannel);
            int l = arraylist.size();
            for(int i1 = 0; i1 < l; i1++)
            {
                com.maddox.rts.net.Answer answer = (com.maddox.rts.net.Answer)arraylist.get(i1);
                if(nreq.prior() > answer.nreq.prior())
                {
                    arraylist.add(i1, this);
                    return;
                }
            }

            arraylist.add(this);
        }
    }

    static class MsgParam
    {

        int count;
        int size;

        MsgParam()
        {
        }
    }


    private void DEBUG(java.lang.String s)
    {
    }

    private void DEBUGA(int i, java.lang.String s)
    {
    }

    private void DEBUGR(int i, java.lang.String s)
    {
    }

    public static int MSG_FULL_SIZE(int i)
    {
        return i + 4;
    }

    public void messageSetParams(com.maddox.rts.NetChannel netchannel, int i, int j)
    {
        if(i < 1)
            i = 1;
        if(i > 64)
            i = 64;
        if(j < 32)
            j = 32;
        if(j > 250)
            j = 250;
        j &= -2;
        com.maddox.rts.net.MsgParam msgparam = (com.maddox.rts.net.MsgParam)chMsgParam.get(netchannel);
        if(msgparam == null)
            msgparam = new MsgParam();
        msgparam.count = i;
        msgparam.size = j;
        chMsgParam.put(netchannel, msgparam);
    }

    public int messageCount(com.maddox.rts.NetChannel netchannel)
    {
        com.maddox.rts.net.MsgParam msgparam = (com.maddox.rts.net.MsgParam)chMsgParam.get(netchannel);
        return msgparam.count;
    }

    public int messageSize(com.maddox.rts.NetChannel netchannel)
    {
        com.maddox.rts.net.MsgParam msgparam = (com.maddox.rts.net.MsgParam)chMsgParam.get(netchannel);
        return msgparam.size;
    }

    public void doRequest(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        new Request(netfilerequest, nextLocalId & 0xfffffff);
        nextLocalId += 2;
    }

    public void doCancel(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        com.maddox.rts.NetChannel netchannel = netfilerequest.owner().masterChannel();
        java.util.ArrayList arraylist = (java.util.ArrayList)chRequest.get(netchannel);
        int j = arraylist.size();
        for(int i = 0; i < j; i++)
        {
            com.maddox.rts.net.Request request = (com.maddox.rts.net.Request)arraylist.get(i);
            if(!request.nreq.equals(netfilerequest))
                continue;
            request.cancel();
            break;
        }

        bCheckDestroyed = true;
        requestPostMsg(netchannel);
    }

    private void doAnswer(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        if((netfilerequest.server() instanceof com.maddox.rts.Destroy) && ((com.maddox.rts.Destroy)netfilerequest.server()).isDestroyed())
        {
            return;
        } else
        {
            netfilerequest.server().doAnswer(netfilerequest);
            return;
        }
    }

    public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        com.maddox.rts.NetChannel netchannel = netmsginput.channel();
        if(chAnswer.get(netchannel) == null)
            return true;
        int i = netmsginput.readInt();
        int j = i >> 28 & 0xf;
        i &= 0xfffffff;
        boolean flag = (i & 1) == 1;
        i &= -2;
        if(flag)
        {
            DEBUGA(i, "input message...");
            if(j == 1)
            {
                DEBUG("spawn Answer");
                int k = netmsginput.readUnsignedByte();
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                com.maddox.rts.net.NetFileServer netfileserver = (com.maddox.rts.net.NetFileServer)netmsginput.readNetObj();
                int l1 = netmsginput.readUnsignedShort();
                new Answer(netchannel, k, netobj, netfileserver, i, l1);
            } else
            {
                java.util.ArrayList arraylist = (java.util.ArrayList)chAnswer.get(netchannel);
                if(arraylist == null)
                    return true;
                int l = arraylist.size();
                for(int j1 = 0; j1 < l; j1++)
                {
                    com.maddox.rts.net.Answer answer = (com.maddox.rts.net.Answer)arraylist.get(j1);
                    if(answer.remoteId != i)
                        continue;
                    answer.netInput(netmsginput, j);
                    break;
                }

            }
        } else
        {
            DEBUGR(i, "input message...");
            java.util.ArrayList arraylist1 = (java.util.ArrayList)chRequest.get(netchannel);
            if(arraylist1 == null)
                return true;
            int i1 = arraylist1.size();
            for(int k1 = 0; k1 < i1; k1++)
            {
                com.maddox.rts.net.Request request = (com.maddox.rts.net.Request)arraylist1.get(k1);
                if(request.localId != i)
                    continue;
                request.netInput(netmsginput, j);
                break;
            }

        }
        doCheckDestroyed();
        requestPostMsg(netmsginput.channel());
        return true;
    }

    private com.maddox.rts.NetMsgGuaranted getMsgOut(com.maddox.rts.NetChannel netchannel)
    {
        if(netchannel.isDestroying())
            return null;
        com.maddox.rts.net.MsgParam msgparam = (com.maddox.rts.net.MsgParam)chMsgParam.get(netchannel);
        _curMaxMsgSize = msgparam.size;
        int i = msgparam.count;
        java.util.ArrayList arraylist = (java.util.ArrayList)chMsg.get(netchannel);
        int j = arraylist.size() - i;
        if(j > 0)
        {
            for(int k = 0; k < arraylist.size(); k++)
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = (com.maddox.rts.NetMsgGuaranted)arraylist.get(k);
                if(netmsgguaranted.isLocked())
                    continue;
                arraylist.remove(k);
                k--;
                if(--j == 0)
                    break;
            }

        } else
        if(j < 0)
        {
            j = -j;
            for(int l = 0; l < j; l++)
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted(com.maddox.rts.net.NetFileTransport.MSG_FULL_SIZE(_curMaxMsgSize));
                netmsgguaranted1.setRequiredAsk(true);
                arraylist.add(netmsgguaranted1);
            }

        }
        j = arraylist.size();
        for(int i1 = 0; i1 < j; i1++)
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted2 = (com.maddox.rts.NetMsgGuaranted)arraylist.get(i1);
            if(!netmsgguaranted2.isLocked())
                return netmsgguaranted2;
        }

        return null;
    }

    private void requestPostMsg(com.maddox.rts.NetChannel netchannel)
    {
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = getMsgOut(netchannel);
        if(netmsgguaranted == null)
        {
            return;
        } else
        {
            new com.maddox.rts.MsgAction(true, netchannel) {

                public void doAction(java.lang.Object obj)
                {
                    com.maddox.rts.NetChannel netchannel1 = (com.maddox.rts.NetChannel)obj;
                    if(netchannel1.isDestroying())
                        return;
                    com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = getMsgOut(netchannel1);
                    if(netmsgguaranted1 == null)
                    {
                        return;
                    } else
                    {
                        doNetOutput(netmsgguaranted1, netchannel1);
                        return;
                    }
                }

            }
;
            return;
        }
    }

    private void doNetOutput(com.maddox.rts.NetMsgGuaranted netmsgguaranted, com.maddox.rts.NetChannel netchannel)
    {
        boolean flag;
        do
        {
            netmsgguaranted = getMsgOut(netchannel);
            if(netmsgguaranted == null)
                break;
            int i = _curMaxMsgSize;
            flag = false;
            try
            {
                netmsgguaranted.clear();
                java.util.ArrayList arraylist = (java.util.ArrayList)chRequest.get(netchannel);
                int j = arraylist.size();
                for(int i1 = 0; i1 < j; i1++)
                {
                    com.maddox.rts.net.Request request = (com.maddox.rts.net.Request)arraylist.get(i1);
                    if(flag = request.netOutput(netmsgguaranted, i))
                        break;
                }

                if(!flag)
                {
                    java.util.ArrayList arraylist1 = (java.util.ArrayList)chAnswer.get(netchannel);
                    int k = arraylist1.size();
                    for(int j1 = 0; j1 < k; j1++)
                    {
                        com.maddox.rts.net.Answer answer = (com.maddox.rts.net.Answer)arraylist1.get(j1);
                        if(!answer.nreq.server().isStateDataTransfer(answer.nreq) && (flag = answer.netOutput(netmsgguaranted, i)))
                            break;
                    }

                }
                if(!flag)
                {
                    java.util.ArrayList arraylist2 = (java.util.ArrayList)chAnswer.get(netchannel);
                    int l = arraylist2.size();
                    for(int k1 = 0; k1 < l; k1++)
                    {
                        com.maddox.rts.net.Answer answer1 = (com.maddox.rts.net.Answer)arraylist2.get(k1);
                        if(flag = answer1.netOutput(netmsgguaranted, i))
                            break;
                    }

                }
                if(flag)
                    postTo(netchannel, netmsgguaranted);
                doCheckDestroyed();
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
                requestPostMsg(netchannel);
                flag = false;
            }
        } while(flag);
    }

    public void msgNetAsk(com.maddox.rts.NetMsgGuaranted netmsgguaranted, com.maddox.rts.NetChannel netchannel)
    {
        doNetOutput(netmsgguaranted, netchannel);
    }

    public void msgNetNak(com.maddox.rts.NetMsgGuaranted netmsgguaranted, com.maddox.rts.NetChannel netchannel)
    {
    }

    public void msgNetDelChannel(com.maddox.rts.NetChannel netchannel)
    {
        delChannel(netchannel);
    }

    public void msgNetNewChannel(com.maddox.rts.NetChannel netchannel)
    {
        newChannel(netchannel);
    }

    private void delChannel(com.maddox.rts.NetChannel netchannel)
    {
        java.util.ArrayList arraylist = (java.util.ArrayList)chAnswer.get(netchannel);
        if(arraylist != null)
        {
            int i = arraylist.size();
            for(int k = 0; k < i; k++)
            {
                com.maddox.rts.net.Answer answer = (com.maddox.rts.net.Answer)arraylist.get(k);
                answer.delChannel();
            }

            arraylist.clear();
            chAnswer.remove(netchannel);
        }
        arraylist = (java.util.ArrayList)chRequest.get(netchannel);
        if(arraylist != null)
        {
            int j = arraylist.size();
            for(int l = 0; l < j; l++)
            {
                com.maddox.rts.net.Request request = (com.maddox.rts.net.Request)arraylist.get(l);
                request.delChannel();
            }

            arraylist.clear();
            chRequest.remove(netchannel);
        }
        chMsg.remove(netchannel);
        chMsgParam.remove(netchannel);
        doCheckDestroyed();
    }

    private void newChannel(com.maddox.rts.NetChannel netchannel)
    {
        if(chRequest.containsKey(netchannel))
            return;
        if(netchannel instanceof com.maddox.rts.NetChannelInStream)
        {
            return;
        } else
        {
            chRequest.put(netchannel, new ArrayList());
            chAnswer.put(netchannel, new ArrayList());
            messageSetParams(netchannel, MSG_DEFAULT_COUNT, MSG_DEFAULT_SIZE);
            chMsg.put(netchannel, new ArrayList());
            return;
        }
    }

    private void doCheckDestroyed()
    {
        if(!bCheckDestroyed)
            return;
        bCheckDestroyed = false;
        for(java.util.Map.Entry entry = chAnswer.nextEntry(null); entry != null; entry = chAnswer.nextEntry(entry))
        {
            java.util.ArrayList arraylist = (java.util.ArrayList)entry.getValue();
            for(int i = 0; i < arraylist.size(); i++)
            {
                com.maddox.rts.net.Answer answer = (com.maddox.rts.net.Answer)arraylist.get(i);
                if(answer.isDestroyed())
                {
                    arraylist.remove(i);
                    i--;
                }
            }

        }

        for(java.util.Map.Entry entry1 = chRequest.nextEntry(null); entry1 != null; entry1 = chRequest.nextEntry(entry1))
        {
            java.util.ArrayList arraylist1 = (java.util.ArrayList)entry1.getValue();
            for(int j = 0; j < arraylist1.size(); j++)
            {
                com.maddox.rts.net.Request request = (com.maddox.rts.net.Request)arraylist1.get(j);
                if(request.isDestroyed())
                {
                    arraylist1.remove(j);
                    j--;
                }
            }

        }

    }

    public void destroy()
    {
        super.destroy();
    }

    public void typeStates(java.io.PrintStream printstream)
    {
        for(java.util.Map.Entry entry = chAnswer.nextEntry(null); entry != null; entry = chAnswer.nextEntry(entry))
        {
            java.util.ArrayList arraylist = (java.util.ArrayList)entry.getValue();
            for(int i = 0; i < arraylist.size(); i++)
            {
                com.maddox.rts.net.Answer answer = (com.maddox.rts.net.Answer)arraylist.get(i);
                answer.typeState(printstream);
            }

        }

        for(java.util.Map.Entry entry1 = chRequest.nextEntry(null); entry1 != null; entry1 = chRequest.nextEntry(entry1))
        {
            java.util.ArrayList arraylist1 = (java.util.ArrayList)entry1.getValue();
            for(int j = 0; j < arraylist1.size(); j++)
            {
                com.maddox.rts.net.Request request = (com.maddox.rts.net.Request)arraylist1.get(j);
                request.typeState(printstream);
            }

        }

    }

    public NetFileTransport(int i)
    {
        super(null, i);
        nextLocalId = 0;
        chRequest = new HashMapExt();
        chAnswer = new HashMapExt();
        chMsg = new HashMapExt();
        chMsgParam = new HashMapExt();
        bCheckDestroyed = false;
    }

    private static final boolean bDEBUG = false;
    public static int MSG_DEFAULT_COUNT = 2;
    public static int MSG_DEFAULT_SIZE = 64;
    private int nextLocalId;
    private com.maddox.util.HashMapExt chRequest;
    private com.maddox.util.HashMapExt chAnswer;
    private com.maddox.util.HashMapExt chMsg;
    private com.maddox.util.HashMapExt chMsgParam;
    private boolean bCheckDestroyed;
    private static final int ID_RA_SPAWN_BEGIN = 1;
    private static final int ID_RA_SPAWN_NAME = 2;
    private static final int ID_RA_DATA = 0;
    private static final int ID_RA_CANCEL = 3;
    private static final int ID_AR_DATA = 0;
    private static final int ID_AR_EXT_DATA = 1;
    private static final int ID_AR_SUCCESS = 2;
    private static final int ID_AR_DISCONNECT = 3;
    private static final int ID_AR_NOT_FOUND = 4;
    private static final int ID_AR_IO = 5;
    private int _curMaxMsgSize;










}
