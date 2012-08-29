package com.maddox.il2.objects.vehicles.stationary;


public abstract class SN2
{
    public static class SN2Unit extends com.maddox.il2.objects.vehicles.stationary.SN2Generic
    {

        public SN2Unit()
        {
        }
    }


    public SN2()
    {
    }

    static 
    {
        new SN2Generic.SPAWN(SN2Unit.class);
    }
}
