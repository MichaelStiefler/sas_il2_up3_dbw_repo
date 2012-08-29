package com.maddox.il2.objects.vehicles.stationary;


public abstract class GTW
{
    public static class GTWUnit extends com.maddox.il2.objects.vehicles.stationary.GTWGeneric
    {

        public GTWUnit()
        {
        }
    }


    public GTW()
    {
    }

    static 
    {
        new GTWGeneric.SPAWN(GTWUnit.class);
    }
}
