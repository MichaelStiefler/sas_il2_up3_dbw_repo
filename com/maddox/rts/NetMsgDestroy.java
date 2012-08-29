// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetMsgDestroy.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            NetMsgGuaranted, NetObj

public class NetMsgDestroy extends com.maddox.rts.NetMsgGuaranted
{

    public NetMsgDestroy(com.maddox.rts.NetObj netobj)
    {
        super(null);
        try
        {
            writeNetObj(netobj);
        }
        catch(java.lang.Exception exception) { }
    }
}
