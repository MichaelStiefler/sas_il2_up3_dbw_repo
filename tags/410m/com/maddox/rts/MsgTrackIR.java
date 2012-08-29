// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgTrackIR.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgMouseListener, MsgTrackIRListener

public class MsgTrackIR extends com.maddox.rts.Message
{

    public int id()
    {
        return id;
    }

    public float yaw()
    {
        return yaw;
    }

    public float pitch()
    {
        return pitch;
    }

    public float roll()
    {
        return roll;
    }

    public MsgTrackIR()
    {
        id = -1;
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgMouseListener)
            switch(id)
            {
            case 0: // '\0'
                ((com.maddox.rts.MsgTrackIRListener)obj).msgTrackIRAngles(yaw, pitch, roll);
                return true;
            }
        return false;
    }

    public static final int ANGLES = 0;
    public static final int UNKNOWN = -1;
    protected int id;
    protected float yaw;
    protected float pitch;
    protected float roll;
}
