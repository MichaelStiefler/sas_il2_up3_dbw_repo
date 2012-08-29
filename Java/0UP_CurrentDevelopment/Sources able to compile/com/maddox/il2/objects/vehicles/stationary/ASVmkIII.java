package com.maddox.il2.objects.vehicles.stationary;


public abstract class ASVmkIII
{
    public static class ASVmkIIIUnit extends com.maddox.il2.objects.vehicles.stationary.ASVmkIIIGeneric
    {

        public ASVmkIIIUnit()
        {
        }
    }


    public ASVmkIII()
    {
    }

    static 
    {
        new ASVmkIIIGeneric.SPAWN(ASVmkIIIUnit.class);
    }
}
