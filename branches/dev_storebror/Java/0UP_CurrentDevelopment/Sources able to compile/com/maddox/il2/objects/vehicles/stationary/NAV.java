package com.maddox.il2.objects.vehicles.stationary;


public abstract class NAV
{
    public static class NAVUnit extends com.maddox.il2.objects.vehicles.stationary.NAVGeneric
    {

        public NAVUnit()
        {
        }
    }


    public NAV()
    {
    }

    static 
    {
        new NAVGeneric.SPAWN(NAVUnit.class);
    }
}
