package com.maddox.il2.objects.vehicles.stationary;


public abstract class AImkX
{
    public static class AImkXUnit extends com.maddox.il2.objects.vehicles.stationary.AImkXGeneric
    {

        public AImkXUnit()
        {
        }
    }


    public AImkX()
    {
    }

    static 
    {
        new AImkXGeneric.SPAWN(AImkXUnit.class);
    }
}
