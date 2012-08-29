package com.maddox.il2.objects.vehicles.stationary;


public abstract class SPR
{
    public static class SPRUnit extends com.maddox.il2.objects.vehicles.stationary.SPRGeneric
    {

        public SPRUnit()
        {
        }
    }


    public SPR()
    {
    }

    static 
    {
        new SPRGeneric.SPAWN(SPRUnit.class);
    }
}
