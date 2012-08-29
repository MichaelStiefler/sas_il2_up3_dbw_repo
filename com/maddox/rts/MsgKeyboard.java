// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgKeyboard.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgKeyboardListener

public class MsgKeyboard extends com.maddox.rts.Message
{

    public int id()
    {
        return id;
    }

    public int button()
    {
        return key;
    }

    public int key()
    {
        return key;
    }

    public int getchar()
    {
        return key;
    }

    public void setPress(int i)
    {
        id = 0;
        key = i;
    }

    public void setRelease(int i)
    {
        id = 1;
        key = i;
    }

    public void setChar(int i)
    {
        id = 2;
        key = i;
    }

    public MsgKeyboard()
    {
        id = -1;
        key = -1;
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgKeyboardListener)
        {
            switch(id)
            {
            case 0: // '\0'
            case 1: // '\001'
                ((com.maddox.rts.MsgKeyboardListener)obj).msgKeyboardKey(key, id == 0);
                return true;

            case 2: // '\002'
                ((com.maddox.rts.MsgKeyboardListener)obj).msgKeyboardChar((char)key);
                return true;
            }
            return true;
        } else
        {
            return false;
        }
    }

    public static final int PRESS = 0;
    public static final int RELEASE = 1;
    public static final int CHAR = 2;
    public static final int UNKNOWN = -1;
    protected int id;
    protected int key;
}
