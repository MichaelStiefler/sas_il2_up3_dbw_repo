// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgMainWindow.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgMainWindowListener

public class MsgMainWindow extends com.maddox.rts.Message
{

    protected void Send(int i, java.lang.Object obj)
    {
        id = i;
        send(obj);
    }

    protected MsgMainWindow()
    {
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgMainWindowListener)
        {
            ((com.maddox.rts.MsgMainWindowListener)obj).msgMainWindow(id);
            return true;
        } else
        {
            return false;
        }
    }

    public static final int CREATED = 1;
    public static final int DESTROYED = 2;
    public static final int RESIZED = 4;
    public static final int MOVED = 8;
    public static final int FOCUSCHANGED = 16;
    protected int id;
}
