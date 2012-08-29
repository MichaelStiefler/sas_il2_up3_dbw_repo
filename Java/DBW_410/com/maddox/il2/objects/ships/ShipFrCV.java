// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 19-Jan-12 10:42:02 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ShipFrCV.java

package com.maddox.il2.objects.ships;


// Referenced classes of package com.maddox.il2.objects.ships:
//            Ship, BigshipGeneric

public abstract class ShipFrCV extends Ship
{
    /* member class not found */
    class NAeSaoPaulo_A12_Br {}

    /* member class not found */
    class FSFochR99CV_1980 {}

    /* member class not found */
    class FSFochR99CV_1964 {}

    /* member class not found */
    class FSClemenceauR98CV_1980 {}

    /* member class not found */
    public static class FSClemenceauR98CV_1962 extends com.maddox.il2.objects.ships.BigshipGeneric
    implements com.maddox.il2.ai.ground.TgtShip
{

    public FSClemenceauR98CV_1962()
    {
    }

    public FSClemenceauR98CV_1962(java.lang.String s, int i, com.maddox.rts.SectFile sectfile, java.lang.String s1, com.maddox.rts.SectFile sectfile1, java.lang.String s2)
    {
        super(s, i, sectfile, s1, sectfile1, s2);
    }
}


    public ShipFrCV()
    {
    }

    static 
    {
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipFrCV$FSClemenceauR98CV_1962.class);
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipFrCV$FSClemenceauR98CV_1980.class);
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipFrCV$FSFochR99CV_1964.class);
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipFrCV$FSFochR99CV_1980.class);
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.ShipFrCV$NAeSaoPaulo_A12_Br.class);
    }
}