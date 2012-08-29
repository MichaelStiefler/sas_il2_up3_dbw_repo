package com.maddox.il2.objects.vehicles.stationary;


public abstract class FUG218
{
    public static class FUG218Unit extends com.maddox.il2.objects.vehicles.stationary.FUG218Generic
    {

        public FUG218Unit()
        {
        }
    }


    public FUG218()
    {
    }

    static 
    {
        new FUG218Generic.SPAWN(FUG218Unit.class);
    }
}
