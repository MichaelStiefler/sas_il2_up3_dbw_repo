// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PAirdrome.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.IconDraw;

// Referenced classes of package com.maddox.il2.builder:
//            PPoint, Path

public class PAirdrome extends com.maddox.il2.builder.PPoint
{

    public void setType(int i)
    {
        type = i;
        setIcon();
    }

    public int type()
    {
        return type;
    }

    public PAirdrome(com.maddox.il2.builder.Path path, com.maddox.il2.builder.PPoint ppoint, com.maddox.JGP.Point3d point3d, int i)
    {
        super(path, ppoint, point3d);
        setType(i);
    }

    private void setIcon()
    {
        java.lang.String s = null;
        switch(type)
        {
        case 0: // '\0'
            s = "takeoff";
            break;

        case 1: // '\001'
            s = "normfly";
            break;

        case 2: // '\002'
            s = "gattack";
            break;

        default:
            return;
        }
        icon = com.maddox.il2.engine.IconDraw.get("icons/" + s + ".mat");
    }

    public static final int RUNAWAY = 0;
    public static final int TAXI = 1;
    public static final int STAY = 2;
    public static final java.lang.String types[] = {
        "RUNAWAY", "TAXI", "STAY"
    };
    private int type;

}
