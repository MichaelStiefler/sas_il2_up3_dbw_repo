package com.maddox.il2.objects.vehicles.stationary;


public abstract class NGS
{
    public static class NGSUnit extends com.maddox.il2.objects.vehicles.stationary.NGSGeneric
    {

        public NGSUnit()
        {
        }
    }


    public NGS()
    {
    }

    static 
    {
        new NGSGeneric.SPAWN(NGSUnit.class);
    }
}
