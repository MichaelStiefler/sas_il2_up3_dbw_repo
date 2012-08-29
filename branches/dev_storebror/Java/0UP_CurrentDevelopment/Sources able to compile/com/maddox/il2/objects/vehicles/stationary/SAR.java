package com.maddox.il2.objects.vehicles.stationary;


public abstract class SAR
{
    public static class SARUnit extends com.maddox.il2.objects.vehicles.stationary.SARGeneric
    {

        public SARUnit()
        {
        }
    }


    public SAR()
    {
    }

    static 
    {
        new SARGeneric.SPAWN(SARUnit.class);
    }
}
