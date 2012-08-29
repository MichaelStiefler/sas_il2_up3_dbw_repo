package com.maddox.il2.objects.vehicles.stationary;


public abstract class GCI
{
    public static class GCIUnit extends com.maddox.il2.objects.vehicles.stationary.GCIGeneric
    {

        public GCIUnit()
        {
        }
    }


    public GCI()
    {
    }

    static 
    {
        new GCIGeneric.SPAWN(GCIUnit.class);
    }
}
