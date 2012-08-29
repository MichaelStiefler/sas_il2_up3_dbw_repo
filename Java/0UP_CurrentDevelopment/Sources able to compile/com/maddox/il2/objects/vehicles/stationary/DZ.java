package com.maddox.il2.objects.vehicles.stationary;


public abstract class DZ
{
    public static class DZUnit extends com.maddox.il2.objects.vehicles.stationary.DZGeneric
    {

        public DZUnit()
        {
        }
    }


    public DZ()
    {
    }

    static 
    {
        new DZGeneric.SPAWN(DZUnit.class);
    }
}
