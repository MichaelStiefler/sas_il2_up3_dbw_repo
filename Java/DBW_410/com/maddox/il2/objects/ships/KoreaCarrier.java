// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 19-Jan-12 10:40:40 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   KoreaCarrier.java

package com.maddox.il2.objects.ships;


// Referenced classes of package com.maddox.il2.objects.ships:
//            Ship, BigshipGeneric

public abstract class KoreaCarrier extends Ship
{
    /* member class not found */
    class USSBoxerCV21 {}

    /* member class not found */
    class USSValleyForgeCV45 {}


    public KoreaCarrier()
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
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.KoreaCarrier$USSValleyForgeCV45.class);
        new BigshipGeneric.SPAWN(com.maddox.il2.objects.ships.KoreaCarrier$USSBoxerCV21.class);
    }
}