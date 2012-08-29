package com.maddox.il2.objects.vehicles.stationary;


public abstract class Monica
{
    public static class MonicaUnit extends com.maddox.il2.objects.vehicles.stationary.MonicaGeneric
    {

        public MonicaUnit()
        {
        }
    }


    public Monica()
    {
    }

    static 
    {
        new MonicaGeneric.SPAWN(MonicaUnit.class);
    }
}
