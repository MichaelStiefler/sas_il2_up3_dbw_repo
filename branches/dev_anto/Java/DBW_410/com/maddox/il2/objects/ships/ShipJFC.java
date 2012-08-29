// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 19-Jan-12 10:42:24 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ShipJFC.java

package com.maddox.il2.objects.ships;


// Referenced classes of package com.maddox.il2.objects.ships:
//            Ship, BigshipGeneric

public abstract class ShipJFC extends Ship
{
    /* member class not found */
    public static class USSEssexCV_ead_SCB125 extends com.maddox.il2.objects.ships.BigshipGeneric
    implements com.maddox.il2.ai.ground.TgtShip
{

    public USSEssexCV_ead_SCB125()
    {
    }

    public USSEssexCV_ead_SCB125(java.lang.String s, int i, com.maddox.rts.SectFile sectfile, java.lang.String s1, com.maddox.rts.SectFile sectfile1, java.lang.String s2)
    {
        super(s, i, sectfile, s1, sectfile1, s2);
    }
}

    public ShipJFC()
    {
    }

    static 
    {
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipJFC$USSEssexCV_ead_SCB125.class);
    }
}