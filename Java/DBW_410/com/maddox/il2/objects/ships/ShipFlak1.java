// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 19-Jan-12 10:41:46 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ShipFlak1.java

package com.maddox.il2.objects.ships;


// Referenced classes of package com.maddox.il2.objects.ships:
//            Ship, BigshipGeneric

public abstract class ShipFlak1 extends Ship
{
    /* member class not found */
    class HMSColossusCV {}


    public ShipFlak1()
    {
    }

    static Class _mthclass$(String s)
    {
        return Class.forName(s);
        ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipFlak1$HMSColossusCV.class);
    }
}