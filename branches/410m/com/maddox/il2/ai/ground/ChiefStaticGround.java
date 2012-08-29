// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ChiefStaticGround.java

package com.maddox.il2.ai.ground;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.rts.Finger;
import com.maddox.rts.Message;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.ai.ground:
//            StaticUnitInPackedForm, StaticUnitInterface, StaticUnitSpawn

public class ChiefStaticGround extends com.maddox.il2.ai.Chief
{
    public static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            return actorspawnarg.set(new ChiefStaticGround(actorspawnarg.paramFileName, actorspawnarg.point));
        }

        public SPAWN()
        {
        }
    }


    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public ChiefStaticGround()
    {
        waitTime = -1L;
        behaviour = 0;
        unitsPacked = new ArrayList();
        pos = new ActorPosMove(this);
    }

    public ChiefStaticGround(java.lang.String s, com.maddox.JGP.Point3d point3d)
    {
        this();
        if(point3d != null)
            pos.setAbs(point3d);
        com.maddox.rts.SectFile sectfile = new SectFile(s);
        int i = sectfile.sectionIndex("StUnits");
        int j = sectfile.vars(i);
        if(j <= 0)
            j /= 0;
        for(int k = 0; k < j; k++)
        {
            java.util.StringTokenizer stringtokenizer = new StringTokenizer(sectfile.line(i, k));
            java.lang.String s1 = stringtokenizer.nextToken();
            float f = java.lang.Float.valueOf(stringtokenizer.nextToken()).floatValue();
            float f1 = java.lang.Float.valueOf(stringtokenizer.nextToken()).floatValue();
            float f2 = java.lang.Float.valueOf(stringtokenizer.nextToken()).floatValue();
            java.lang.Object obj = com.maddox.rts.Spawn.get(s1);
            if(obj == null)
            {
                java.lang.System.out.println("No such type of object: " + s1);
                java.lang.System.exit(0);
            }
            int l = com.maddox.rts.Finger.Int(s1);
            int i1 = 0;
            unitsPacked.add(new StaticUnitInPackedForm(k, l, i1, f, f1, f2));
        }

        unpackUnits();
    }

    public void packUnits()
    {
        if(unitsPacked.size() > 0)
            return;
        java.lang.Object aobj[] = getOwnerAttached();
        if(aobj == null)
            return;
        int i = aobj.length;
        for(int j = 0; j < i; j++)
        {
            unitsPacked.add(((com.maddox.il2.ai.ground.StaticUnitInterface)aobj[j]).Pack());
            ((com.maddox.il2.engine.Actor)aobj[j]).destroy();
        }

    }

    public void unpackUnits()
    {
        int i = unitsPacked.size();
        if(i <= 0)
            return;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.ground.StaticUnitInPackedForm staticunitinpackedform = (com.maddox.il2.ai.ground.StaticUnitInPackedForm)unitsPacked.get(j);
            java.lang.Object obj = com.maddox.rts.Spawn.get(staticunitinpackedform.CodeType());
            ((com.maddox.il2.ai.ground.StaticUnitSpawn)obj).unitSpawn(staticunitinpackedform.CodeName(), staticunitinpackedform.State(), this, staticunitinpackedform.X(), staticunitinpackedform.Y(), staticunitinpackedform.Yaw());
        }

        unitsPacked.clear();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public java.util.ArrayList unitsPacked;
    public long waitTime;
    public int behaviour;

    static 
    {
        com.maddox.rts.Spawn.add(com.maddox.il2.ai.ground.ChiefStaticGround.class, new SPAWN());
    }
}
