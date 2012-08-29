package com.maddox.il2.objects.vehicles.stationary;


public abstract class ANAPS4
{
    public static class ANAPS4Unit extends com.maddox.il2.objects.vehicles.stationary.ANAPS4Generic
    {

        public ANAPS4Unit()
        {
        }
    }


    public ANAPS4()
    {
    }

    static 
    {
        new ANAPS4Generic.SPAWN(ANAPS4Unit.class);
    }
}
