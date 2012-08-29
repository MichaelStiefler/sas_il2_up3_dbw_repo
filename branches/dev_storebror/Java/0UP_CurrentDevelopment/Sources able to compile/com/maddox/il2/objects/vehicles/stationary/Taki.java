package com.maddox.il2.objects.vehicles.stationary;


public abstract class Taki
{
    public static class TakiUnit extends com.maddox.il2.objects.vehicles.stationary.TakiGeneric
    {

        public TakiUnit()
        {
        }
    }


    public Taki()
    {
    }

    static 
    {
        new TakiGeneric.SPAWN(TakiUnit.class);
    }
}
