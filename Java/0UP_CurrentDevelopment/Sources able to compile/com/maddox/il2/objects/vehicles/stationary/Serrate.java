package com.maddox.il2.objects.vehicles.stationary;


public abstract class Serrate
{
    public static class SerrateUnit extends com.maddox.il2.objects.vehicles.stationary.SerrateGeneric
    {

        public SerrateUnit()
        {
        }
    }


    public Serrate()
    {
    }

    static 
    {
        new SerrateGeneric.SPAWN(SerrateUnit.class);
    }
}
