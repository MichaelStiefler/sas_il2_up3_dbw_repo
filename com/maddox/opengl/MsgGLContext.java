// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgGLContext.java

package com.maddox.opengl;

import com.maddox.rts.Message;

// Referenced classes of package com.maddox.opengl:
//            MsgGLContextListener

public class MsgGLContext extends com.maddox.rts.Message
{

    protected void Send(int i, java.lang.Object obj)
    {
        id = i;
        send(obj);
    }

    protected MsgGLContext()
    {
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.opengl.MsgGLContextListener)
        {
            ((com.maddox.opengl.MsgGLContextListener)obj).msgGLContext(id);
            return true;
        } else
        {
            return false;
        }
    }

    public static final int CREATED = 1;
    public static final int DESTROYED = 2;
    public static final int RESIZED = 4;
    public static final int RECREATED = 8;
    protected int id;
}
