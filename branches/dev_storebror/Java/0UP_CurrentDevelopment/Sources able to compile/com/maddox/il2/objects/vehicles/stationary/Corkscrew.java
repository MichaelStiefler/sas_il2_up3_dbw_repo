package com.maddox.il2.objects.vehicles.stationary;


public abstract class Corkscrew
{
    public static class CorkscrewUnit extends com.maddox.il2.objects.vehicles.stationary.CorkscrewGeneric
    {

        public CorkscrewUnit()
        {
        }
    }


    public Corkscrew()
    {
    }

    static 
    {
        new CorkscrewGeneric.SPAWN(CorkscrewUnit.class);
    }
}
