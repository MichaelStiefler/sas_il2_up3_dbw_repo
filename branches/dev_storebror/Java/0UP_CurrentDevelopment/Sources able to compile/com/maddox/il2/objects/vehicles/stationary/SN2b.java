package com.maddox.il2.objects.vehicles.stationary;


public abstract class SN2b
{
    public static class SN2bUnit extends com.maddox.il2.objects.vehicles.stationary.SN2bGeneric
    {

        public SN2bUnit()
        {
        }
    }


    public SN2b()
    {
    }

    static 
    {
        new SN2bGeneric.SPAWN(SN2bUnit.class);
    }
}
