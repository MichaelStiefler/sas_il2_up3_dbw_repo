package com.maddox.il2.objects.vehicles.stationary;


public abstract class AGCI
{
    public static class AGCIUnit extends com.maddox.il2.objects.vehicles.stationary.AGCIGeneric
    {

        public AGCIUnit()
        {
        }
    }


    public AGCI()
    {
    }

    static 
    {
        new AGCIGeneric.SPAWN(AGCIUnit.class);
    }
}
