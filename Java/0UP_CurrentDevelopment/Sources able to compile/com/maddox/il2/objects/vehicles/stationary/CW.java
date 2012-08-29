package com.maddox.il2.objects.vehicles.stationary;


public abstract class CW
{
    public static class CWUnit extends com.maddox.il2.objects.vehicles.stationary.CWGeneric
    {

        public CWUnit()
        {
        }
    }


    public CW()
    {
    }

    static 
    {
        new CWGeneric.SPAWN(CWUnit.class);
    }
}
