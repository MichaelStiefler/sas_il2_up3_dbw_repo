package com.maddox.il2.objects.vehicles.stationary;


public abstract class RadioOperator
{
    public static class RadioOperatorUnit extends com.maddox.il2.objects.vehicles.stationary.RadioOperatorGeneric
    {

        public RadioOperatorUnit()
        {
        }
    }


    public RadioOperator()
    {
    }

    static 
    {
        new RadioOperatorGeneric.SPAWN(RadioOperatorUnit.class);
    }
}
