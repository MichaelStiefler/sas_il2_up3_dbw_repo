package com.maddox.il2.objects.vehicles.stationary;


public abstract class AImkIV
{
    public static class AImkIVUnit extends com.maddox.il2.objects.vehicles.stationary.AImkIVGeneric
    {

        public AImkIVUnit()
        {
        }
    }


    public AImkIV()
    {
    }

    static 
    {
        new AImkIVGeneric.SPAWN(AImkIVUnit.class);
    }
}
