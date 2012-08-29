// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetFileServer.java

package com.maddox.rts.net;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

// Referenced classes of package com.maddox.rts.net:
//            NetFileRequest

public interface NetFileServer
{

    public abstract void doRequest(com.maddox.rts.net.NetFileRequest netfilerequest);

    public abstract void doCancel(com.maddox.rts.net.NetFileRequest netfilerequest);

    public abstract void doAnswer(com.maddox.rts.net.NetFileRequest netfilerequest);

    public abstract java.lang.String getStateInInfo(com.maddox.rts.net.NetFileRequest netfilerequest);

    public abstract java.lang.String getStateOutInfo(com.maddox.rts.net.NetFileRequest netfilerequest);

    public abstract boolean isStateDataTransfer(com.maddox.rts.net.NetFileRequest netfilerequest);

    public abstract void destroyIn(com.maddox.rts.net.NetFileRequest netfilerequest);

    public abstract void destroyOut(com.maddox.rts.net.NetFileRequest netfilerequest);

    public abstract boolean sendRequestData(com.maddox.rts.net.NetFileRequest netfilerequest, com.maddox.rts.NetMsgGuaranted netmsgguaranted, int i, int j)
        throws java.io.IOException;

    public abstract void getRequestData(com.maddox.rts.net.NetFileRequest netfilerequest, com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException;

    public abstract int getAnswerState(com.maddox.rts.net.NetFileRequest netfilerequest, int i);

    public abstract boolean sendAnswerData(com.maddox.rts.net.NetFileRequest netfilerequest, com.maddox.rts.NetMsgGuaranted netmsgguaranted, int i, int j)
        throws java.io.IOException;

    public abstract int getAnswerData(com.maddox.rts.net.NetFileRequest netfilerequest, com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException;

    public abstract int getAnswerExtData(com.maddox.rts.net.NetFileRequest netfilerequest, com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException;

    public static final int ANSWER_NONE = 0;
    public static final int ANSWER_DATA = 1;
    public static final int ANSWER_EXT_DATA = 2;
    public static final int ANSWER_SUCCESS = 3;
    public static final int ANSWER_IO = 4;
}
