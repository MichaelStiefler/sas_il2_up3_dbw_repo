// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Bridge.java

package com.maddox.il2.objects.bridges;

import com.maddox.il2.engine.IconDraw;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.bridges:
//            LongBridge

public class Bridge extends com.maddox.il2.objects.bridges.LongBridge
{

    public Bridge(int i, int j, int k, int l, int i1, int j1, float f)
    {
        super(i, j, k, l, i1, j1, f);
        com.maddox.il2.engine.IconDraw.create(this);
        __indx = i;
        __type = j;
        __x1 = k;
        __y1 = l;
        __x2 = i1;
        __y2 = j1;
        __offsetK = f;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public int __indx;
    public int __type;
    public int __x1;
    public int __y1;
    public int __x2;
    public int __y2;
    public float __offsetK;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.bridges.Bridge.class, "iconName", "icons/Bridge.mat");
    }
}
