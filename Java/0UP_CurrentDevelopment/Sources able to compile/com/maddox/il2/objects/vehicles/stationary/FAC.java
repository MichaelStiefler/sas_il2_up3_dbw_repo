package com.maddox.il2.objects.vehicles.stationary;


public abstract class FAC
{
    public static class FACUnit extends com.maddox.il2.objects.vehicles.stationary.FACGeneric
    {

        public FACUnit()
        {
        }
    }


    public FAC()
    {
    }

    static 
    {
        new FACGeneric.SPAWN(FACUnit.class);
    }
}
