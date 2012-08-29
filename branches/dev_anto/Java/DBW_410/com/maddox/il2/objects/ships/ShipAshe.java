// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 19-Jan-12 10:41:07 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ShipAshe.java

package com.maddox.il2.objects.ships;


// Referenced classes of package com.maddox.il2.objects.ships:
//            Ship, BigshipGeneric

public abstract class ShipAshe extends Ship
{
    /* member class not found */
    class HMSWarspite {}

    /* member class not found */
    class HMSTartar {}

    /* member class not found */
    class HMSNubian {}

    /* member class not found */
    class HMSMatabele {}

    /* member class not found */
    class HMSJupiter {}

    /* member class not found */
    class HMSCossack {}


    public ShipAshe()
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
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipAshe$HMSCossack.class);
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipAshe$HMSJupiter.class);
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipAshe$HMSMatabele.class);
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipAshe$HMSNubian.class);
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipAshe$HMSTartar.class);
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipAshe$HMSWarspite.class);
    }
}