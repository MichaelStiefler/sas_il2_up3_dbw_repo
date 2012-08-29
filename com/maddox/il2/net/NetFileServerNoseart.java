// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetFileServerNoseart.java

package com.maddox.il2.net;

import com.maddox.il2.engine.Config;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetObj;
import com.maddox.rts.net.NetFileRequest;
import com.maddox.rts.net.NetFileServerDef;

public class NetFileServerNoseart extends com.maddox.rts.net.NetFileServerDef
{

    public int compressMethod()
    {
        return 2;
    }

    public int compressBlockSize()
    {
        return 32768;
    }

    public java.lang.String primaryPath()
    {
        return "PaintSchemes/Noseart";
    }

    public java.lang.String alternativePath()
    {
        return "PaintSchemes/NetCache";
    }

    public void doRequest(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        if(com.maddox.il2.engine.Config.cur.netSkinDownload || netfilerequest.owner() != null && (netfilerequest.owner().masterChannel() instanceof com.maddox.rts.NetChannelInStream))
        {
            super.doRequest(netfilerequest);
        } else
        {
            netfilerequest.setState(-2);
            netfilerequest.doAnswer();
        }
    }

    public NetFileServerNoseart(int i)
    {
        super(i);
    }
}
