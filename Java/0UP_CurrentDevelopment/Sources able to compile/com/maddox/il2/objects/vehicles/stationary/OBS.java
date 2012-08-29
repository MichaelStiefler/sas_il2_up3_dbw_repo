package com.maddox.il2.objects.vehicles.stationary;


public abstract class OBS
{
    public static class OBSUnit extends com.maddox.il2.objects.vehicles.stationary.OBSGeneric
    {

        public OBSUnit()
        {
        }
    }


    public OBS()
    {
    }

    static 
    {
        new OBSGeneric.SPAWN(OBSUnit.class);
    }
}
