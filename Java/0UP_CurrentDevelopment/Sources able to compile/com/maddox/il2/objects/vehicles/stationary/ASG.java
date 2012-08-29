package com.maddox.il2.objects.vehicles.stationary;


public abstract class ASG
{
    public static class ASGUnit extends com.maddox.il2.objects.vehicles.stationary.ASGGeneric
    {

        public ASGUnit()
        {
        }
    }


    public ASG()
    {
    }

    static 
    {
        new ASGGeneric.SPAWN(ASGUnit.class);
    }
}
