package com.maddox.il2.objects.vehicles.stationary;


public abstract class ASV
{
    public static class ASVUnit extends com.maddox.il2.objects.vehicles.stationary.ASVGeneric
    {

        public ASVUnit()
        {
        }
    }


    public ASV()
    {
    }

    static 
    {
        new ASVGeneric.SPAWN(ASVUnit.class);
    }
}
