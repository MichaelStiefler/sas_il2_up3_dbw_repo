// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GLContext.java

package com.maddox.opengl;

import com.maddox.rts.Listeners;
import com.maddox.rts.MainWindow;
import com.maddox.rts.MsgAddListenerListener;
import com.maddox.rts.MsgMainWindowListener;
import com.maddox.rts.MsgRemoveListenerListener;
import com.maddox.rts.RTSConf;

// Referenced classes of package com.maddox.opengl:
//            MsgGLContext, GLContextException, Provider, GLCaps, 
//            gl, GLInitCaps

public class GLContext
    implements com.maddox.rts.MsgAddListenerListener, com.maddox.rts.MsgRemoveListenerListener, com.maddox.rts.MsgMainWindowListener
{

    public static boolean isValid(com.maddox.opengl.GLContext glcontext)
    {
        if(glcontext != null)
            return glcontext.isCreated();
        else
            return false;
    }

    public GLContext(com.maddox.opengl.GLInitCaps glinitcaps)
    {
        bEnableMessages = true;
        listeners = new Listeners();
        msg = new MsgGLContext();
        caps = glinitcaps;
        bCreated = false;
        width = 0;
        height = 0;
    }

    public static java.lang.Object lockObject()
    {
        return com.maddox.opengl.Provider.lockObject();
    }

    public static boolean makeCurrent(com.maddox.opengl.GLContext glcontext)
    {
        if(glcontext != null)
        {
            if(glcontext != current)
                if(glcontext.isCreated() && glcontext.MakeCurrent())
                {
                    current = glcontext;
                } else
                {
                    if(current != null)
                    {
                        com.maddox.opengl.GLContext.MakeNotCurrent();
                        current = null;
                    }
                    return false;
                }
        } else
        if(current != null)
        {
            com.maddox.opengl.GLContext.MakeNotCurrent();
            current = null;
        }
        return true;
    }

    public static com.maddox.opengl.GLContext getCurrent()
    {
        return current;
    }

    public com.maddox.opengl.GLCaps getCaps()
    {
        return caps;
    }

    public boolean isCreated()
    {
        return bCreated;
    }

    public boolean isMain()
    {
        return bMain;
    }

    public int width()
    {
        return width;
    }

    public int height()
    {
        return height;
    }

    public int hWnd()
    {
        return iWND;
    }

    public void createWin32(int i, boolean flag, int j, int k)
        throws com.maddox.opengl.GLContextException
    {
        synchronized(com.maddox.opengl.GLContext.lockObject())
        {
            com.maddox.opengl.GLContext.makeCurrent(null);
            if(isCreated())
                destroy();
            width = j;
            height = k;
            int ai[] = caps.getCaps();
            CreateWin32(i, ai);
            com.maddox.opengl.Provider.contextCreated();
            caps.setCaps(ai);
            bCreated = true;
            bDoubleBuffer = caps.isDoubleBuffered();
            if(flag)
                com.maddox.rts.RTSConf.cur.mainWindow.msgAddListener(this, null);
            bMain = bMain;
            com.maddox.opengl.GLContext.makeCurrent(this);
            int ai1[] = new int[1];
            ai1[0] = 0;
            com.maddox.opengl.gl.GetIntegerv(3379, ai1);
            if(ai1[0] <= 256)
                caps.stencilBits = 0;
            sendAction(1);
        }
    }

    public void changeWin32(com.maddox.opengl.GLInitCaps glinitcaps, int i, boolean flag, int j, int k)
        throws com.maddox.opengl.GLContextException
    {
        if(bCreated)
            destroy();
        caps = glinitcaps;
        createWin32(i, flag, j, k);
    }

    public void destroy()
    {
label0:
        {
            synchronized(com.maddox.opengl.GLContext.lockObject())
            {
                com.maddox.opengl.GLContext.makeCurrent(null);
                if(isCreated())
                    break label0;
            }
            return;
        }
        Destroy();
        com.maddox.opengl.Provider.contextDestroyed();
        bCreated = false;
        current = null;
        com.maddox.rts.RTSConf.cur.mainWindow.msgRemoveListener(this, null);
        sendAction(2);
        obj;
        JVM INSTR monitorexit ;
          goto _L1
        exception;
        throw exception;
_L1:
    }

    public boolean swapBuffers()
    {
        if(isCreated() && com.maddox.opengl.GLContext.getCurrent() == this)
        {
            if(bDoubleBuffer)
            {
                return SwapBuffers();
            } else
            {
                com.maddox.opengl.gl.Flush();
                return true;
            }
        } else
        {
            return false;
        }
    }

    public void setSize(int i, int j)
    {
        width = i;
        height = j;
        sendAction(4);
    }

    public void msgMainWindow(int i)
    {
        switch(i)
        {
        case 2: // '\002'
            destroy();
            break;

        case 4: // '\004'
            setSize(com.maddox.rts.RTSConf.cur.mainWindow.width(), com.maddox.rts.RTSConf.cur.mainWindow.height());
            break;
        }
    }

    public java.lang.Object[] getListeners()
    {
        return listeners.get();
    }

    public void msgAddListener(java.lang.Object obj, java.lang.Object obj1)
    {
        listeners.addListener(obj);
    }

    public void msgRemoveListener(java.lang.Object obj, java.lang.Object obj1)
    {
        listeners.removeListener(obj);
    }

    public boolean isMessagesEnable()
    {
        return bEnableMessages;
    }

    public void setMessagesEnable(boolean flag)
    {
        bEnableMessages = flag;
    }

    public void sendAction(int i)
    {
        if(!bEnableMessages)
            return;
        java.lang.Object aobj[] = listeners.get();
        if(aobj == null)
            return;
        for(int j = 0; j < aobj.length; j++)
            msg.Send(i, aobj[j]);

    }

    private GLContext()
    {
        bEnableMessages = true;
        listeners = new Listeners();
        msg = new MsgGLContext();
    }

    private native void CreateWin32(int i, int ai[])
        throws com.maddox.opengl.GLContextException;

    private native void Change(int ai[])
        throws com.maddox.opengl.GLContextException;

    private native void Destroy();

    private native boolean SwapBuffers();

    private native boolean MakeCurrent();

    private static native void MakeNotCurrent();

    private static com.maddox.opengl.GLContext current = null;
    private com.maddox.opengl.GLCaps caps;
    private boolean bEnableMessages;
    private boolean bCreated;
    private boolean bDoubleBuffer;
    private int width;
    private int height;
    private int iWND;
    private int iDC;
    private int iRC;
    private com.maddox.rts.Listeners listeners;
    private com.maddox.opengl.MsgGLContext msg;
    private boolean bMain;

    static 
    {
        com.maddox.opengl.gl.loadNative();
    }
}
