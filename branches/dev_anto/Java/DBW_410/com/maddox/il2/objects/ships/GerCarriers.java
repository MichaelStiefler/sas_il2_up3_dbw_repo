// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 19-Jan-12 10:40:16 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GerCarriers.java

package com.maddox.il2.objects.ships;


// Referenced classes of package com.maddox.il2.objects.ships:
//            Ship, BigshipGeneric

public abstract class GerCarriers extends Ship
{
    /* member class not found */
    class GrafZeppelin {}

    /* member class not found */
    class PeterStrasser {}

    /* member class not found */
    class Aquila {}


    public GerCarriers()
    {
    }

    static 
    {
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.GerCarriers$GrafZeppelin.class);
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.GerCarriers$PeterStrasser.class);
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.GerCarriers$Aquila.class);
    }
}