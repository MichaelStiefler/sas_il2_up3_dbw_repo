// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MainWindow.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            MsgMainWindow, Listeners, MsgAddListenerListener, MsgRemoveListenerListener, 
//            RTSConf

public class MainWindow
    implements com.maddox.rts.MsgAddListenerListener, com.maddox.rts.MsgRemoveListenerListener
{

    public boolean isCreated()
    {
        return bCreated;
    }

    public boolean isFullScreen()
    {
        return bFullScreen;
    }

    public boolean isFocused()
    {
        return bFocused;
    }

    public int width()
    {
        return cx;
    }

    public int height()
    {
        return cy;
    }

    public int widthFull()
    {
        return cxFull;
    }

    public int heightFull()
    {
        return cyFull;
    }

    public int posX()
    {
        return posX;
    }

    public int posY()
    {
        return posY;
    }

    public static native int componentWnd(java.lang.Object obj);

    public int hWnd()
    {
        return hWnd;
    }

    public int hContextWnd()
    {
        return hWnd;
    }

    public void setFocus()
    {
    }

    public void setSize(int i, int j)
    {
    }

    public void setPosSize(int i, int j, int k, int l)
    {
    }

    public void setTitle(java.lang.String s)
    {
    }

    public boolean isIconic()
    {
        return false;
    }

    public void showIconic()
    {
    }

    public void showNormal()
    {
    }

    public static com.maddox.rts.MainWindow adapter()
    {
        return com.maddox.rts.RTSConf.cur.mainWindow;
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

    public void SendAction(int i)
    {
        if(!bEnableMessages)
            return;
        if(i == 2)
            com.maddox.rts.RTSConf.setRequestExitApp(true);
        java.lang.Object aobj[] = listeners.get();
        if(aobj == null)
            return;
        if(msg == null)
            msg = new MsgMainWindow();
        for(int j = 0; j < aobj.length; j++)
            msg.Send(i, aobj[j]);

    }

    protected MainWindow()
    {
        hWnd = 0;
        bEnableMessages = true;
        listeners = new Listeners();
        bCreated = false;
        bFullScreen = false;
    }

    protected int hWnd;
    protected int cx;
    protected int cy;
    protected int cxFull;
    protected int cyFull;
    protected int posX;
    protected int posY;
    protected boolean bFocused;
    protected boolean bFullScreen;
    protected boolean bCreated;
    private boolean bEnableMessages;
    private com.maddox.rts.Listeners listeners;
    private com.maddox.rts.MsgMainWindow msg;
}
