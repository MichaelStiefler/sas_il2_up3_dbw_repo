package com.maddox.il2.objects.vehicles.stationary;


public abstract class NGS1
{
    public static class NGS1Unit extends com.maddox.il2.objects.vehicles.stationary.NGS1Generic
    {

        public NGS1Unit()
        {
        }
    }


    public NGS1()
    {
    }

    static 
    {
        new NGS1Generic.SPAWN(NGS1Unit.class);
    }
}
