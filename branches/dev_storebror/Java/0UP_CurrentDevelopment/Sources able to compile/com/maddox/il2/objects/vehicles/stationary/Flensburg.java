package com.maddox.il2.objects.vehicles.stationary;


public abstract class Flensburg
{
    public static class FlensburgUnit extends com.maddox.il2.objects.vehicles.stationary.FlensburgGeneric
    {

        public FlensburgUnit()
        {
        }
    }


    public Flensburg()
    {
    }

    static 
    {
        new FlensburgGeneric.SPAWN(FlensburgUnit.class);
    }
}
