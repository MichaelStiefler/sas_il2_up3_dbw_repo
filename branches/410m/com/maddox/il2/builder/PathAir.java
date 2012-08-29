// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PathAir.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.PaintScheme;

// Referenced classes of package com.maddox.il2.builder:
//            Path, PAir, PlMisAir, Plugin, 
//            Builder, ResSquadron, Pathes

public class PathAir extends com.maddox.il2.builder.Path
{

    public void computeTimes()
    {
        computeTimes(true);
    }

    public void computeTimes(boolean flag)
    {
        int i = points();
        if(i == 0)
            return;
        com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)point(0);
        com.maddox.il2.builder.PlMisAir plmisair = (com.maddox.il2.builder.PlMisAir)com.maddox.il2.builder.Plugin.getPlugin("MisAir");
        for(int j = 1; j < i; j++)
        {
            com.maddox.il2.builder.PAir pair1 = (com.maddox.il2.builder.PAir)point(j);
            double d = pair1.speed;
            if(pair1.type() == 2)
                d = plmisair.type[_iType].item[_iItem].speedRunway;
            if(d == 0.0D)
                d = plmisair.type[_iType].item[_iItem].speedRunway;
            double d1 = pair.pos.getAbsPoint().distance(pair1.pos.getAbsPoint());
            d *= 0.27777777777777779D;
            double d2 = d1 / d;
            pair1.time = pair.time + d2;
            pair = pair1;
        }

        if(flag)
            com.maddox.il2.builder.Plugin.builder.doUpdateSelector();
    }

    public com.maddox.il2.builder.ResSquadron squadron()
    {
        return resSquadron;
    }

    public com.maddox.il2.ai.Regiment regiment()
    {
        return squadron().regiment();
    }

    public void setName(java.lang.String s)
    {
        com.maddox.il2.builder.ResSquadron ressquadron = com.maddox.il2.builder.ResSquadron.New(s.substring(0, s.length() - 1));
        if(ressquadron != resSquadron)
        {
            if(resSquadron != null)
                resSquadron.detach(this);
            resSquadron = ressquadron;
            resSquadron.attach(this);
        }
        super.setName(s);
        updateTypedName();
    }

    public void updateTypedName()
    {
        com.maddox.il2.builder.PlMisAir plmisair = (com.maddox.il2.builder.PlMisAir)com.maddox.il2.builder.Plugin.getPlugin("MisAir");
        com.maddox.il2.objects.air.PaintScheme paintscheme = com.maddox.il2.objects.air.Aircraft.getPropertyPaintScheme(plmisair.type[_iType].item[_iItem].clazz, resSquadron.regiment().country());
        if(paintscheme != null)
            typedName = paintscheme.typedName(plmisair.type[_iType].item[_iItem].clazz, resSquadron.regiment(), iSquadron, iWing, 0);
        else
            typedName = "UNKNOWN";
    }

    public void destroy()
    {
        if(com.maddox.il2.engine.Actor.isValid(resSquadron))
            resSquadron.detach(this);
        super.destroy();
    }

    public PathAir(com.maddox.il2.builder.Pathes pathes, int i, int j)
    {
        super(pathes);
        typedName = "UNKNOWN";
        iSquadron = 0;
        iWing = 0;
        bOnlyAI = false;
        planes = 1;
        fuel = 100;
        skill = 1;
        skins = new java.lang.String[4];
        noseart = new java.lang.String[4];
        pilots = new java.lang.String[4];
        weapons = "default";
        bParachute = true;
        _iType = i;
        _iItem = j;
    }

    public java.lang.String typedName;
    public java.lang.String sRegiment;
    public int iRegiment;
    public int iSquadron;
    public int iWing;
    public boolean bOnlyAI;
    public int planes;
    public int fuel;
    public int skill;
    public int skills[] = {
        1, 1, 1, 1
    };
    public java.lang.String skins[];
    public java.lang.String noseart[];
    public java.lang.String pilots[];
    public boolean bNumberOn[] = {
        true, true, true, true
    };
    public java.lang.String weapons;
    public boolean bParachute;
    public int _iType;
    public int _iItem;
    private com.maddox.il2.builder.ResSquadron resSquadron;
}
