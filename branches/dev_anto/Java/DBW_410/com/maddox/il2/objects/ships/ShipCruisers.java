// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 19-Jan-12 10:41:15 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ShipCruisers.java

package com.maddox.il2.objects.ships;


// Referenced classes of package com.maddox.il2.objects.ships:
//            Ship, BigshipGeneric

public abstract class ShipCruisers extends Ship
{
    /* member class not found */
    class USSPortlandCA33 {}

    /* member class not found */
    class USSChesterCA27 {}

    /* member class not found */
    class USSHoustonCA30 {}


    public ShipCruisers()
    {
    }

    static Class _mthclass$(String s)
    {
        try
        {
            return Class.forName(s);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static 
    {
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipCruisers$USSHoustonCA30.class);
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipCruisers$USSChesterCA27.class);
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipCruisers$USSPortlandCA33.class);
    }
}