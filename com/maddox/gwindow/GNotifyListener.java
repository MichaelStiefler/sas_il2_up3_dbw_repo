// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GNotifyListener.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GNotifyListenerDef, GWindow

public abstract class GNotifyListener
    implements com.maddox.gwindow.GNotifyListenerDef
{

    public GNotifyListener()
    {
    }

    public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
    {
        return false;
    }
}
