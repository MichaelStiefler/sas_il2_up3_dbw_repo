// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorSpawnArg.java

package com.maddox.il2.engine;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Cmd;
import com.maddox.rts.NetChannel;
import com.maddox.rts.Spawn;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.engine:
//            ActorException, Loc, ActorPosMoveInit, Actor, 
//            ActorPos, Mat, IconDraw, Orient

public class ActorSpawnArg
{

    public ActorSpawnArg()
    {
        bNumberOn = true;
    }

    public ActorSpawnArg(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        bNumberOn = true;
        name = actorspawnarg.name;
        armyExist = actorspawnarg.armyExist;
        army = actorspawnarg.army;
        point = actorspawnarg.point;
        orient = actorspawnarg.orient;
        baseActor = actorspawnarg.baseActor;
        hookName = actorspawnarg.hookName;
        ownerActor = actorspawnarg.ownerActor;
        iconName = actorspawnarg.iconName;
        meshName = actorspawnarg.meshName;
        matName = actorspawnarg.matName;
        paramFileName = actorspawnarg.paramFileName;
        sizeExist = actorspawnarg.sizeExist;
        size = actorspawnarg.size;
        timeLenExist = actorspawnarg.timeLenExist;
        timeLen = actorspawnarg.timeLen;
        timeNativeExist = actorspawnarg.timeNativeExist;
        timeNative = actorspawnarg.timeNative;
        typeExist = actorspawnarg.typeExist;
        type = actorspawnarg.type;
        path = actorspawnarg.path;
        target = actorspawnarg.target;
        acoustic = actorspawnarg.acoustic;
        sound = actorspawnarg.sound;
        preload = actorspawnarg.preload;
        color3f = actorspawnarg.color3f;
        light = actorspawnarg.light;
        Z0 = actorspawnarg.Z0;
        Z0Exist = actorspawnarg.Z0Exist;
        FM = actorspawnarg.FM;
        FM_Type = actorspawnarg.FM_Type;
        netChannel = actorspawnarg.netChannel;
        netIdRemote = actorspawnarg.netIdRemote;
        weapons = actorspawnarg.weapons;
        fuel = actorspawnarg.fuel;
        speed = actorspawnarg.speed;
        skill = actorspawnarg.skill;
        bPlayer = actorspawnarg.bPlayer;
        bornPlaceExist = actorspawnarg.bornPlaceExist;
        bornPlace = actorspawnarg.bornPlace;
        stayPlaceExist = actorspawnarg.stayPlaceExist;
        stayPlace = actorspawnarg.stayPlace;
        bNumberOn = actorspawnarg.bNumberOn;
        rawData = actorspawnarg.rawData;
        country = actorspawnarg.country;
    }

    public void clear()
    {
        name = null;
        armyExist = false;
        army = 0;
        point = null;
        orient = null;
        baseActor = null;
        hookName = null;
        ownerActor = null;
        iconName = null;
        meshName = null;
        matName = null;
        paramFileName = null;
        sizeExist = false;
        timeLenExist = false;
        timeNativeExist = false;
        typeExist = false;
        path = null;
        target = null;
        acoustic = null;
        sound = null;
        preload = null;
        color3f = null;
        light = null;
        Z0 = 0.0F;
        Z0Exist = false;
        FM = null;
        FM_Type = 0;
        netChannel = null;
        netIdRemote = 0;
        weapons = null;
        fuel = 100F;
        speed = null;
        skill = 1;
        bPlayer = false;
        bornPlaceExist = false;
        bornPlace = -1;
        stayPlaceExist = false;
        stayPlace = -1;
        bNumberOn = true;
        rawData = null;
        country = null;
    }

    public com.maddox.il2.engine.Actor set(com.maddox.il2.engine.Actor actor)
    {
        if(name != null)
            actor.setName(name);
        if(armyExist)
            actor.setArmy(army);
        if(baseActor != null)
        {
            com.maddox.il2.engine.Hook hook = null;
            if(hookName != null)
                hook = baseActor.findHook(hookName);
            actor.pos.setBase(baseActor, hook, false);
            if(point != null && orient != null)
                actor.pos.setRel(point, orient);
            else
            if(point != null)
                actor.pos.setRel(point);
            else
            if(orient != null)
                actor.pos.setRel(orient);
            else
                actor.pos.setRel(lempty);
            actor.pos.reset();
        } else
        {
            if(point != null && orient != null)
                actor.pos.setAbs(point, orient);
            else
            if(point != null)
                actor.pos.setAbs(point);
            else
            if(orient != null)
                actor.pos.setAbs(orient);
            else
                actor.pos.setAbs(lempty);
            actor.pos.reset();
        }
        if(ownerActor != null)
            actor.setOwner(ownerActor);
        if(speed != null)
        {
            actor.setSpeed(speed);
            if(actor.pos != null)
                actor.pos.reset();
        }
        if(iconName != null)
        {
            try
            {
                actor.icon = com.maddox.il2.engine.Mat.New(iconName);
            }
            catch(java.lang.Exception exception) { }
            if(actor.icon == null)
                ERR_SOFT("Icon : " + iconName + " not loaded");
        } else
        {
            com.maddox.il2.engine.IconDraw.create(actor);
        }
        if(bPlayer && (actor instanceof com.maddox.il2.objects.air.Aircraft))
            com.maddox.il2.ai.World.setPlayerAircraft((com.maddox.il2.objects.air.Aircraft)actor);
        return actor;
    }

    public com.maddox.il2.engine.Actor setStationary(com.maddox.il2.engine.Actor actor)
    {
        if(name != null)
            actor.setName(name);
        if(!armyExist)
            throw new ActorException(actor.getClass().getName() + ": missing army");
        actor.setArmy(army);
        if(point == null || orient == null)
            throw new ActorException(actor.getClass().getName() + ": missing pos or orient");
        actor.pos.setAbs(point, orient);
        actor.pos.reset();
        if(iconName != null)
        {
            try
            {
                actor.icon = com.maddox.il2.engine.Mat.New(iconName);
            }
            catch(java.lang.Exception exception) { }
            if(actor.icon == null)
                ERR_SOFT(actor.getClass().getName() + ": icon '" + iconName + "' not loaded");
        } else
        {
            com.maddox.il2.engine.IconDraw.create(actor);
        }
        return actor;
    }

    public com.maddox.il2.engine.Actor setStationaryNoIcon(com.maddox.il2.engine.Actor actor)
    {
        if(name != null)
            actor.setName(name);
        if(!armyExist)
            throw new ActorException(actor.getClass().getName() + ": missing army");
        actor.setArmy(army);
        if(point == null || orient == null)
        {
            throw new ActorException(actor.getClass().getName() + ": missing pos or orient");
        } else
        {
            actor.pos.setAbs(point, orient);
            actor.pos.reset();
            return actor;
        }
    }

    public com.maddox.il2.engine.Actor setNameOwnerIcon(com.maddox.il2.engine.Actor actor)
    {
        if(name != null)
            actor.setName(name);
        if(armyExist)
            actor.setArmy(army);
        if(ownerActor != null)
            actor.setOwner(ownerActor);
        if(iconName != null)
        {
            try
            {
                actor.icon = com.maddox.il2.engine.Mat.New(iconName);
            }
            catch(java.lang.Exception exception) { }
            if(actor.icon == null)
                ERR_SOFT("Icon : " + iconName + " not loaded");
        } else
        {
            com.maddox.il2.engine.IconDraw.create(actor);
        }
        return actor;
    }

    public com.maddox.il2.engine.Loc getAbsLoc()
    {
        if(baseActor != null)
        {
            com.maddox.il2.engine.Hook hook = null;
            if(hookName != null)
                hook = baseActor.findHook(hookName);
            apos.setBase(baseActor, hook, false);
            if(point != null && orient != null)
                apos.setRel(point, orient);
            else
            if(point != null)
                apos.setRel(point);
            else
            if(orient != null)
                apos.setRel(orient);
            else
                apos.setRel(lempty);
        } else
        if(point != null && orient != null)
            apos.setAbs(point, orient);
        else
        if(point != null)
            apos.setAbs(point);
        else
        if(orient != null)
            apos.setAbs(orient);
        else
            apos.setAbs(lempty);
        com.maddox.il2.engine.Loc loc = new Loc();
        apos.getAbs(loc);
        apos.setBase(null, null, false);
        return loc;
    }

    public void ERR_HARD(java.lang.String s)
    {
        if(com.maddox.rts.Cmd.ERR_HARD)
            java.lang.System.err.println("spawn " + com.maddox.rts.Spawn.getLastClassName() + ": " + s);
    }

    public void ERR_SOFT(java.lang.String s)
    {
        if(com.maddox.rts.Cmd.ERR_SOFT)
            java.lang.System.err.println("spawn " + com.maddox.rts.Spawn.getLastClassName() + ": " + s);
    }

    public boolean isNoExistHARD(java.lang.Object obj, java.lang.String s)
    {
        if(obj == null)
        {
            ERR_HARD(s);
            return true;
        } else
        {
            return false;
        }
    }

    public void INFO_HARD(java.lang.String s)
    {
        if(com.maddox.rts.Cmd.INFO_HARD)
            java.lang.System.out.println(s);
    }

    public void INFO_SOFT(java.lang.String s)
    {
        if(com.maddox.rts.Cmd.INFO_SOFT)
            java.lang.System.out.println(s);
    }

    public java.lang.String name;
    public boolean armyExist;
    public int army;
    public com.maddox.JGP.Point3d point;
    public com.maddox.il2.engine.Orient orient;
    public com.maddox.il2.engine.Actor baseActor;
    public java.lang.String hookName;
    public com.maddox.il2.engine.Actor ownerActor;
    public java.lang.String iconName;
    public java.lang.String meshName;
    public java.lang.String matName;
    public java.lang.String paramFileName;
    public boolean sizeExist;
    public float size;
    public boolean timeLenExist;
    public float timeLen;
    public boolean timeNativeExist;
    public boolean timeNative;
    public boolean typeExist;
    public int type;
    public java.lang.String path;
    public java.lang.String target;
    public java.lang.String acoustic;
    public java.lang.String sound;
    public java.lang.String preload;
    public com.maddox.JGP.Color3f color3f;
    public float light[];
    public boolean Z0Exist;
    public float Z0;
    public java.lang.String FM;
    public int FM_Type;
    public com.maddox.rts.NetChannel netChannel;
    public int netIdRemote;
    public java.lang.String weapons;
    public float fuel;
    public com.maddox.JGP.Vector3d speed;
    public int skill;
    public boolean bPlayer;
    public boolean bornPlaceExist;
    public int bornPlace;
    public boolean stayPlaceExist;
    public int stayPlace;
    public boolean bNumberOn;
    public java.lang.String rawData;
    public java.lang.String country;
    private static com.maddox.il2.engine.Loc lempty = new Loc();
    private static com.maddox.il2.engine.ActorPosMoveInit apos = new ActorPosMoveInit();

}
