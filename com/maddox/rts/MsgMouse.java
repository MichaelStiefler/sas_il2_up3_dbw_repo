// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgMouse.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgMouseListener

public class MsgMouse extends com.maddox.rts.Message
{

    public int id()
    {
        return id;
    }

    public int button()
    {
        return id == 2 ? -1 : data0;
    }

    public int dx()
    {
        return id != 2 ? 0 : data0;
    }

    public int dy()
    {
        return id != 2 ? 0 : data1;
    }

    public int dz()
    {
        return id != 2 ? 0 : data2;
    }

    public int x()
    {
        return id != 3 ? 0 : data0;
    }

    public int y()
    {
        return id != 3 ? 0 : data1;
    }

    public int z()
    {
        return id != 3 ? 0 : data2;
    }

    public void setPress(int i)
    {
        id = 0;
        data0 = i;
    }

    public void setRelease(int i)
    {
        id = 1;
        data0 = i;
    }

    public void setMove(int i, int j, int k)
    {
        id = 2;
        data0 = i;
        data1 = j;
        data2 = k;
    }

    public void setAbsMove(int i, int j, int k)
    {
        id = 3;
        data0 = i;
        data1 = j;
        data2 = k;
    }

    public MsgMouse()
    {
        id = -1;
        data0 = -1;
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgMouseListener)
        {
            switch(id)
            {
            case 0: // '\0'
            case 1: // '\001'
                ((com.maddox.rts.MsgMouseListener)obj).msgMouseButton(data0, id == 0);
                return true;

            case 2: // '\002'
                ((com.maddox.rts.MsgMouseListener)obj).msgMouseMove(data0, data1, data2);
                return true;

            case 3: // '\003'
                ((com.maddox.rts.MsgMouseListener)obj).msgMouseAbsMove(data0, data1, data2);
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
    public static final int MOVE = 2;
    public static final int ABSMOVE = 3;
    public static final int UNKNOWN = -1;
    protected int id;
    protected int data0;
    protected int data1;
    protected int data2;
}
