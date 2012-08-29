package com.maddox.il2.objects.vehicles.stationary;


public abstract class ASD
{
    public static class ASDUnit extends com.maddox.il2.objects.vehicles.stationary.ASDGeneric
    {

        public ASDUnit()
        {
        }
    }


    public ASD()
    {
    }

    static 
    {
        new ASDGeneric.SPAWN(ASDUnit.class);
    }
}
