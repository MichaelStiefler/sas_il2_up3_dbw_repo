package com.maddox.il2.objects.vehicles.stationary;


public abstract class OBOE
{
    public static class OBOEUnit extends com.maddox.il2.objects.vehicles.stationary.OBOEGeneric
    {

        public OBOEUnit()
        {
        }
    }


    public OBOE()
    {
    }

    static 
    {
        new OBOEGeneric.SPAWN(OBOEUnit.class);
    }
}
