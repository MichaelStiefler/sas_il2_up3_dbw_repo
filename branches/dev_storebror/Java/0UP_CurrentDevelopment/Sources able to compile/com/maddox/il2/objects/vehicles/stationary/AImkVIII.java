package com.maddox.il2.objects.vehicles.stationary;


public abstract class AImkVIII
{
    public static class AImkVIIIUnit extends com.maddox.il2.objects.vehicles.stationary.AImkVIIIGeneric
    {

        public AImkVIIIUnit()
        {
        }
    }


    public AImkVIII()
    {
    }

    static 
    {
        new AImkVIIIGeneric.SPAWN(AImkVIIIUnit.class);
    }
}
