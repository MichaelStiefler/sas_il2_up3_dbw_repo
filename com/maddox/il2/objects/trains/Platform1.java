// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Platform1.java

package com.maddox.il2.objects.trains;

import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.rts.Spawn;

// Referenced classes of package com.maddox.il2.objects.trains:
//            Wagon, Train, WagonSpawn

public class Platform1 extends com.maddox.il2.objects.trains.Wagon
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener
{
    public static class SPAWN
        implements com.maddox.il2.objects.trains.WagonSpawn
    {

        public com.maddox.il2.objects.trains.Wagon wagonSpawn(com.maddox.il2.objects.trains.Train train)
        {
            return new Platform1(train);
        }

        public SPAWN()
        {
        }
    }


    public Platform1(com.maddox.il2.objects.trains.Train train)
    {
        super(train, com.maddox.il2.objects.trains.Platform1.getMeshName(0), com.maddox.il2.objects.trains.Platform1.getMeshName(1));
        life = 0.012F;
        ignoreTNT = 0.4F;
        killTNT = 3F;
        bodyMaterial = 2;
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
        return "3do/Trains/Platform1" + (i == 1 ? "_Dmg" : "") + "/" + s + "/hier.him";
    }

    public static java.lang.String getMeshNameForEditor()
    {
        return com.maddox.il2.objects.trains.Platform1.getMeshName(0);
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
        cls = com.maddox.il2.objects.trains.Platform1.class;
        com.maddox.rts.Spawn.add(cls, new SPAWN());
    }
}
