// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombPara.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombPara extends com.maddox.il2.objects.weapons.Bomb
{

    public BombPara()
    {
    }

    public void start()
    {
        com.maddox.il2.engine.Loc loc = new Loc();
        com.maddox.il2.engine.Orient orient = new Orient();
        java.lang.Class class1 = getClass();
        init(com.maddox.rts.Property.floatValue(class1, "kalibr", 0.082F), com.maddox.rts.Property.floatValue(class1, "massa", 6.8F));
        setOwner(pos.base(), false, false, false);
        pos.setBase(null, null, true);
        pos.setAbs(pos.getCurrent());
        pos.getAbs(loc);
        com.maddox.il2.objects.air.Paratrooper paratrooper = new Paratrooper(getOwner(), getOwner().getArmy(), 255, loc, ((com.maddox.il2.objects.air.Aircraft)getOwner()).FM.Vwld);
        destroy();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombPara.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Null/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 0.0F);
        com.maddox.rts.Property.set(class1, "power", 0.0F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.5F);
        com.maddox.rts.Property.set(class1, "massa", 100F);
    }
}
