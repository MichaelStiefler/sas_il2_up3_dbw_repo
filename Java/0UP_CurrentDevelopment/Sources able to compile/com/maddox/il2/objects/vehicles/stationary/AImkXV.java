package com.maddox.il2.objects.vehicles.stationary;


public abstract class AImkXV
{
    public static class AImkXVUnit extends com.maddox.il2.objects.vehicles.stationary.AImkXVGeneric
    {

        public AImkXVUnit()
        {
        }
    }


    public AImkXV()
    {
    }

    static 
    {
        new AImkXVGeneric.SPAWN(AImkXVUnit.class);
    }
}
