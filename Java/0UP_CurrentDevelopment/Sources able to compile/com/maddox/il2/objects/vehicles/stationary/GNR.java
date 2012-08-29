package com.maddox.il2.objects.vehicles.stationary;


public abstract class GNR
{
    public static class GNRUnit extends com.maddox.il2.objects.vehicles.stationary.GNRGeneric
    {

        public GNRUnit()
        {
        }
    }


    public GNR()
    {
    }

    static 
    {
        new GNRGeneric.SPAWN(GNRUnit.class);
    }
}
