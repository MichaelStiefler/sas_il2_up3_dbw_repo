package com.maddox.il2.objects.vehicles.stationary;


public abstract class FUG
{
    public static class FUGUnit extends com.maddox.il2.objects.vehicles.stationary.FUGGeneric
    {

        public FUGUnit()
        {
        }
    }


    public FUG()
    {
    }

    static 
    {
        new FUGGeneric.SPAWN(FUGUnit.class);
    }
}
