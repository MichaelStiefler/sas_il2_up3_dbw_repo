package com.maddox.il2.objects.vehicles.stationary;


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

    static 
    {
        new NaxosGeneric.SPAWN(NaxosUnit.class);
    }
}
