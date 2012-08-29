// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgHotKeyCmd.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgHotKeyCmdListener, HotKeyCmd

public class MsgHotKeyCmd extends com.maddox.rts.Message
{

    public MsgHotKeyCmd()
    {
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgHotKeyCmdListener)
        {
            ((com.maddox.rts.MsgHotKeyCmdListener)obj).msgHotKeyCmd((com.maddox.rts.HotKeyCmd)_sender, bStart, bBefore);
            return true;
        } else
        {
            return false;
        }
    }

    protected boolean bStart;
    protected boolean bBefore;
}
