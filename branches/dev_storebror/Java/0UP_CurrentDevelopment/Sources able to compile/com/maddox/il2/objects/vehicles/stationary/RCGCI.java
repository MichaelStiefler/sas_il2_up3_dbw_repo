package com.maddox.il2.objects.vehicles.stationary;


public abstract class RCGCI
{
    public static class RCGCIUnit extends com.maddox.il2.objects.vehicles.stationary.RCGCIGeneric
    {

        public RCGCIUnit()
        {
        }
    }


    public RCGCI()
    {
    }

    static 
    {
        new RCGCIGeneric.SPAWN(RCGCIUnit.class);
    }
}
