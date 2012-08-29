package com.maddox.il2.objects.vehicles.stationary;


public abstract class RESCAP
{
    public static class RESCAPUnit extends com.maddox.il2.objects.vehicles.stationary.RESCAPGeneric
    {

        public RESCAPUnit()
        {
        }
    }


    public RESCAP()
    {
    }

    static 
    {
        new RESCAPGeneric.SPAWN(RESCAPUnit.class);
    }
}
