// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Searchlight.java

package com.maddox.il2.objects.vehicles.lights;

import com.maddox.il2.ai.ground.TgtFlak;
import com.maddox.il2.objects.vehicles.artillery.AAA;

// Referenced classes of package com.maddox.il2.objects.vehicles.lights:
//            SearchlightGeneric

public abstract class Searchlight
{
    public static class SL_ManualBlue extends com.maddox.il2.objects.vehicles.lights.SearchlightGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public SL_ManualBlue()
        {
        }
    }


    public Searchlight()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        new SearchlightGeneric.SPAWN(com.maddox.il2.objects.vehicles.lights.Searchlight$SL_ManualBlue.class);
    }
}
