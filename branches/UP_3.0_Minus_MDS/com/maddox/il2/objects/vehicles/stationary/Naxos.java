// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Naxos.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            NaxosGeneric

public abstract class Naxos
{
    public static class NaxosUnit extends com.maddox.il2.objects.vehicles.stationary.NaxosGeneric
    {

        public NaxosUnit()
        {
        }
    }


    public Naxos()
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
        new NaxosGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.Naxos$NaxosUnit.class);
    }
}
