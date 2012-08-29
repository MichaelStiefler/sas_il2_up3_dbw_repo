// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LocomotiveUSSR.java

package com.maddox.il2.objects.trains;

import com.maddox.il2.ai.World;
import com.maddox.rts.Spawn;

// Referenced classes of package com.maddox.il2.objects.trains:
//            LocomotiveVerm, Train, WagonSpawn, Wagon

public class LocomotiveUSSR extends com.maddox.il2.objects.trains.LocomotiveVerm
{
    public static class SPAWN
        implements com.maddox.il2.objects.trains.WagonSpawn
    {

        public com.maddox.il2.objects.trains.Wagon wagonSpawn(com.maddox.il2.objects.trains.Train train)
        {
            return new LocomotiveUSSR(train);
        }

        public SPAWN()
        {
        }
    }


    public LocomotiveUSSR(com.maddox.il2.objects.trains.Train train)
    {
        super(train, com.maddox.il2.objects.trains.LocomotiveUSSR.getMeshName(0), com.maddox.il2.objects.trains.LocomotiveUSSR.getMeshName(1));
    }

    private static java.lang.String getMeshName(int i)
    {
        java.lang.String s;
        switch(com.maddox.il2.ai.World.cur().camouflage)
        {
        case 0: // '\0'
            s = "summer";
            break;

        case 1: // '\001'
            s = "winter";
            break;

        case 2: // '\002'
            s = "desert";
            break;

        default:
            s = "summer";
            break;
        }
        return "3do/Trains/Prvz" + (i == 1 ? "_Dmg" : "") + "/" + s + "/hier.him";
    }

    public static java.lang.String getMeshNameForEditor()
    {
        return com.maddox.il2.objects.trains.LocomotiveUSSR.getMeshName(0);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static java.lang.Class cls;

    static 
    {
        cls = com.maddox.il2.objects.trains.LocomotiveUSSR.class;
        com.maddox.rts.Spawn.add(cls, new SPAWN());
    }
}
