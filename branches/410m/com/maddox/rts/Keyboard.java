// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Keyboard.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            MsgKeyboard, Listeners, MessageCache, MsgAddListenerListener, 
//            MsgRemoveListenerListener, RTSConf, Message, Time

public class Keyboard
    implements com.maddox.rts.MsgAddListenerListener, com.maddox.rts.MsgRemoveListenerListener
{

    public static com.maddox.rts.Keyboard adapter()
    {
        return com.maddox.rts.RTSConf.cur.keyboard;
    }

    public boolean isPressed(int i)
    {
        if(i < 0 || i >= 524)
            return false;
        else
            return buttons[i];
    }

    public java.lang.Object[] getListeners()
    {
        return listeners.get();
    }

    public java.lang.Object[] getRealListeners()
    {
        return realListeners.get();
    }

    public void msgAddListener(java.lang.Object obj, java.lang.Object obj1)
    {
        if(obj1 != null)
        {
            if(com.maddox.rts.Message.current().isRealTime())
                realListeners.insListener(obj);
            else
                listeners.insListener(obj);
        } else
        if(com.maddox.rts.Message.current().isRealTime())
            realListeners.addListener(obj);
        else
            listeners.addListener(obj);
    }

    public void msgRemoveListener(java.lang.Object obj, java.lang.Object obj1)
    {
        if(com.maddox.rts.Message.current().isRealTime())
            realListeners.removeListener(obj);
        else
            listeners.removeListener(obj);
    }

    public java.lang.Object focus()
    {
        return focus;
    }

    public void setFocus(java.lang.Object obj)
    {
        focus = obj;
    }

    public void setEnable(boolean flag)
    {
        bEnabled = flag;
    }

    public boolean isEnable()
    {
        return bEnabled;
    }

    public void setKeyEnable(int i)
    {
        buttonEnable = i;
    }

    public void setPress(long l, int i)
    {
        if(i == buttonEnable || bEnabled)
            _setPress(true, l, i);
    }

    protected void _setPress(boolean flag, long l, int i)
    {
        if(i < 0 || i >= 524)
            return;
        if(buttons[i])
        {
            return;
        } else
        {
            buttons[i] = true;
            post(flag, l, -1, i, true);
            return;
        }
    }

    public void setRelease(long l, int i)
    {
        if(i == buttonEnable || bEnabled)
            _setRelease(true, l, i);
    }

    protected void _setRelease(boolean flag, long l, int i)
    {
        if(i < 0 || i >= 524)
            return;
        if(!buttons[i])
        {
            return;
        } else
        {
            buttons[i] = false;
            post(flag, l, -1, i, false);
            return;
        }
    }

    public void setChar(long l, int i)
    {
        if(!bEnabled)
        {
            return;
        } else
        {
            _setChar(true, l, i);
            return;
        }
    }

    public void _setChar(boolean flag, long l, int i)
    {
        post(flag, l, i, 0, false);
    }

    private void msgSet(com.maddox.rts.MsgKeyboard msgkeyboard, boolean flag, boolean flag1, long l, int i, int j, 
            boolean flag2)
    {
        msgkeyboard.setTickPos(0x7ffffffe);
        if(flag1)
        {
            msgkeyboard.setFlags(64);
            if(!flag)
                l = com.maddox.rts.Time.toReal(l);
            msgkeyboard.setTime(l);
        } else
        {
            msgkeyboard.setFlags(0);
            if(flag)
                l = com.maddox.rts.Time.current();
            msgkeyboard.setTime(l);
        }
        if(i >= 0)
            msgkeyboard.setChar(i);
        else
        if(flag2)
            msgkeyboard.setPress(j);
        else
            msgkeyboard.setRelease(j);
    }

    private void post(boolean flag, long l, int i, int j, boolean flag1)
    {
        if(focus != null)
        {
            com.maddox.rts.MsgKeyboard msgkeyboard = (com.maddox.rts.MsgKeyboard)cache.get();
            msgSet(msgkeyboard, flag, true, l, i, j, flag1);
            msgkeyboard.post(focus);
            return;
        }
        java.lang.Object aobj[] = realListeners.get();
        if(aobj != null)
        {
            com.maddox.rts.MsgKeyboard msgkeyboard1 = (com.maddox.rts.MsgKeyboard)cache.get();
            msgSet(msgkeyboard1, flag, true, l, i, j, flag1);
            msgkeyboard1.post(((java.lang.Object) (aobj)));
        }
        if(!com.maddox.rts.Time.isPaused())
        {
            java.lang.Object aobj1[] = listeners.get();
            if(aobj1 != null)
            {
                com.maddox.rts.MsgKeyboard msgkeyboard2 = (com.maddox.rts.MsgKeyboard)cache.get();
                msgSet(msgkeyboard2, flag, false, l, i, j, flag1);
                msgkeyboard2.post(((java.lang.Object) (aobj1)));
            }
        }
    }

    public void clear()
    {
        if(!bEnabled)
        {
            return;
        } else
        {
            _clear();
            return;
        }
    }

    protected void _clear()
    {
        long l = com.maddox.rts.Time.currentReal();
        for(int i = 0; i < 524; i++)
            if(buttons[i])
                _setRelease(true, l, i);

    }

    protected Keyboard()
    {
        buttonEnable = -1;
        listeners = new Listeners();
        realListeners = new Listeners();
        cache = new MessageCache(com.maddox.rts.MsgKeyboard.class);
        buttons = new boolean[524];
        for(int i = 0; i < 524; i++)
            buttons[i] = false;

        bEnabled = true;
    }

    public static final int PRESS = 0;
    public static final int RELEASE = 1;
    public static final int CHAR = 2;
    public static final int UNKNOWN = -1;
    public static final int BUTTONS = 524;
    private int buttonEnable;
    private boolean bEnabled;
    private com.maddox.rts.Listeners listeners;
    private com.maddox.rts.Listeners realListeners;
    private java.lang.Object focus;
    private com.maddox.rts.MessageCache cache;
    private boolean buttons[];
}
