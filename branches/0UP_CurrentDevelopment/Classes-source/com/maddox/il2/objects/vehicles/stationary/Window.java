// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Window.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            WindowGeneric

public abstract class Window
{
    public static class WindowUnit extends com.maddox.il2.objects.vehicles.stationary.WindowGeneric
    {

        public WindowUnit()
        {
        }
    }


    public Window()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static 
    {
        new WindowGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.Window$WindowUnit.class);
    }
}
