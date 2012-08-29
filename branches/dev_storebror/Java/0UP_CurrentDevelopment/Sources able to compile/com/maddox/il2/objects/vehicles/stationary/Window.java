package com.maddox.il2.objects.vehicles.stationary;


public abstract class Window
{
    public static class WindowUnit extends com.maddox.il2.objects.vehicles.stationary.WindowGeneric
    {

        public WindowUnit()
        {
        }
    }


    public Window()
    {
    }

    static 
    {
        new WindowGeneric.SPAWN(WindowUnit.class);
    }
}
