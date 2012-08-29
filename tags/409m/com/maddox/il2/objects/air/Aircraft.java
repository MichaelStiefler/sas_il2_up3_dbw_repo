// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Aircraft.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.CellAirPlane;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.ai.ground.Predator;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.FObj;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.fm.Squares;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.TimeSkip;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetWing;
import com.maddox.il2.objects.ActorCrater;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.Wreck;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.vehicles.artillery.RocketryRocket;
import com.maddox.il2.objects.weapons.BallisticProjectile;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.il2.objects.weapons.MGunAircraftGeneric;
import com.maddox.il2.objects.weapons.Pylon;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.KryptoInputFilter;
import com.maddox.rts.Message;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.MsgEndAction;
import com.maddox.rts.MsgEndActionListener;
import com.maddox.rts.NetChannel;
import com.maddox.rts.ObjIO;
import com.maddox.rts.ObjState;
import com.maddox.rts.Property;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import com.maddox.util.NumberTokenizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.objects.air:
//            NetAircraft, Paratrooper, TypeBomber, I_16, 
//            JU_88MSTL, PaintScheme, TypeFighter, IL_2, 
//            Cockpit, TypeStormovik

public abstract class Aircraft extends com.maddox.il2.objects.air.NetAircraft
    implements com.maddox.il2.engine.MsgCollisionListener, com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.rts.MsgEndActionListener, com.maddox.il2.ai.ground.Predator
{
    static class CacheItem
    {

        com.maddox.il2.engine.HierMesh mesh;
        boolean bExistTextures;
        int loaded;
        long time;

        CacheItem()
        {
        }
    }

    public static class _WeaponSlot
    {

        public int trigger;
        public java.lang.Class clazz;
        public int bullets;

        public _WeaponSlot(int i, java.lang.String s, int j)
            throws java.lang.Exception
        {
            trigger = i;
            clazz = com.maddox.rts.ObjIO.classForName("weapons." + s);
            bullets = j;
        }
    }

    private static class MsgExplosionPostVarSet
    {

        com.maddox.il2.engine.Actor THIS;
        java.lang.String chunkName;
        com.maddox.JGP.Point3d p;
        com.maddox.il2.engine.Actor initiator;
        float power;
        float radius;

        private MsgExplosionPostVarSet()
        {
            p = new Point3d();
        }

    }

    static class EndActionParam
    {

        com.maddox.il2.engine.Actor initiator;
        com.maddox.il2.engine.Eff3DActor smoke;

        public EndActionParam(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Eff3DActor eff3dactor)
        {
            initiator = actor;
            smoke = eff3dactor;
        }
    }


    public static java.lang.String[] partNames()
    {
        return partNames;
    }

    public int part(java.lang.String s)
    {
        if(s == null)
            return 43;
        int i = 0;
        for(long l = 1L; i < 44; l <<= 1)
        {
            if(s.startsWith(partNames[i]))
                return i;
            i++;
        }

        return 43;
    }

    public boolean cut(java.lang.String s)
    {
        FM.dryFriction = 1.0F;
        debugprintln("" + s + " goes off..");
        if(com.maddox.il2.ai.World.Rnd().nextFloat() < bailProbabilityOnCut(s))
        {
            debugprintln("BAILING OUT - " + s + " gone, can't keep on..");
            hitDaSilk();
        }
        if(!isChunkAnyDamageVisible(s))
        {
            debugprintln("" + s + " is already cut off - operation rejected..");
            return false;
        }
        int ai[] = hideSubTrees(s + "_D");
        if(ai == null)
            return false;
        for(int i = 0; i < ai.length; i++)
        {
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(this, ai[i]);
            for(int l = 0; l < 4; l++)
                if(hierMesh().chunkName().startsWith(FM.AS.astateEffectChunks[l + 0]))
                    FM.AS.changeTankEffectBase(l, wreckage);

            for(int i1 = 0; i1 < FM.EI.getNum(); i1++)
                if(hierMesh().chunkName().startsWith(FM.AS.astateEffectChunks[i1 + 4]))
                {
                    FM.AS.changeEngineEffectBase(i1, wreckage);
                    FM.AS.changeSootEffectBase(i1, wreckage);
                }

            for(int j1 = 0; j1 < 6; j1++)
                if(hierMesh().chunkName().startsWith(FM.AS.astateEffectChunks[j1 + 12]))
                    FM.AS.changeNavLightEffectBase(j1, wreckage);

            for(int k1 = 0; k1 < 4; k1++)
                if(hierMesh().chunkName().startsWith(FM.AS.astateEffectChunks[k1 + 18]))
                    FM.AS.changeLandingLightEffectBase(k1, wreckage);

            for(int l1 = 0; l1 < FM.EI.getNum(); l1++)
                if(hierMesh().chunkName().startsWith(FM.AS.astateEffectChunks[l1 + 22]))
                    FM.AS.changeOilEffectBase(l1, wreckage);

            if(hierMesh().chunkName().startsWith(s) && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 50)
                com.maddox.il2.engine.Eff3DActor.New(wreckage, null, null, 1.0F, com.maddox.il2.objects.Wreckage.SMOKE, 3F);
            Vd.set(FM.Vwld);
            wreckage.setSpeed(Vd);
        }

        ai = hierMesh().getSubTrees(s + "_D");
        for(int j = 0; j < ai.length; j++)
            detachGun(ai[j]);

        java.lang.String s1 = s + "_CAP";
        if(hierMesh().chunkFindCheck(s1) >= 0)
            hierMesh().chunkVisible(s1, true);
        for(int k = 0; k < ai.length; k++)
        {
            for(int i2 = 3; i2 < FM.Gears.pnti.length; i2++)
                try
                {
                    if(FM.Gears.pnti[i2] != -1 && ai[k] == hierMesh().chunkByHookNamed(FM.Gears.pnti[i2]))
                        FM.Gears.pnti[i2] = -1;
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println("FATAL ERROR: Gear pnti[] cut failed on tt[] = " + i2 + " - " + FM.Gears.pnti.length);
                }

        }

        hierMesh().setCurChunk(ai[0]);
        hierMesh().getChunkLocObj(tmpLoc1);
        sfxCrash(tmpLoc1.getPoint());
        return true;
    }

    public boolean cut_Subtrees(java.lang.String s)
    {
        debugprintln("" + s + " goes off..");
        if(com.maddox.il2.ai.World.Rnd().nextFloat() < bailProbabilityOnCut(s))
        {
            debugprintln("BAILING OUT - " + s + " gone, can't keep on..");
            hitDaSilk();
        }
        if(!isChunkAnyDamageVisible(s))
        {
            debugprintln("" + s + " is already cut off - operation rejected..");
            return false;
        }
        int i = hierMesh().chunkFindCheck(s + "_D0");
        if(i >= 0)
        {
            int j;
            for(j = 0; j <= 9; j++)
            {
                int k = hierMesh().chunkFindCheck(s + "_D" + j);
                if(k < 0)
                    continue;
                hierMesh().setCurChunk(k);
                if(hierMesh().isChunkVisible())
                    break;
            }

            if(j > 9)
                i = -1;
        }
        java.lang.Object obj = null;
        if(i >= 0)
        {
            obj = com.maddox.il2.objects.Wreckage.makeWreck(this, i);
            ((com.maddox.il2.engine.Actor) (obj)).setOwner(this, false, false, false);
        }
        int ai[] = hideSubTrees(s + "_D");
        if(ai == null)
            return false;
        for(int l = 0; l < ai.length; l++)
        {
            if(i < 0)
                obj = new Wreckage(this, ai[l]);
            else
                hierMesh().setCurChunk(ai[l]);
            for(int k1 = 0; k1 < 4; k1++)
                if(hierMesh().chunkName().startsWith(FM.AS.astateEffectChunks[k1 + 0]))
                    FM.AS.changeTankEffectBase(k1, ((com.maddox.il2.engine.Actor) (obj)));

            for(int l1 = 0; l1 < 4; l1++)
                if(hierMesh().chunkName().startsWith(FM.AS.astateEffectChunks[l1 + 4]))
                {
                    FM.AS.changeEngineEffectBase(l1, ((com.maddox.il2.engine.Actor) (obj)));
                    FM.AS.changeSootEffectBase(l1, ((com.maddox.il2.engine.Actor) (obj)));
                }

            if(hierMesh().chunkName().startsWith(s) && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 50)
                com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (obj)), null, null, 1.0F, com.maddox.il2.objects.Wreckage.SMOKE, 3F);
            Vd.set(FM.Vwld);
            if(i < 0)
                ((com.maddox.il2.objects.Wreckage)obj).setSpeed(Vd);
            else
                ((com.maddox.il2.objects.Wreck)obj).setSpeed(Vd);
        }

        ai = hierMesh().getSubTrees(s + "_D");
        for(int i1 = 0; i1 < ai.length; i1++)
            detachGun(ai[i1]);

        java.lang.String s1 = s + "_CAP";
        if(hierMesh().chunkFindCheck(s1) >= 0)
            hierMesh().chunkVisible(s1, true);
        for(int j1 = 0; j1 < ai.length; j1++)
        {
            for(int i2 = 3; i2 < FM.Gears.pnti.length; i2++)
                try
                {
                    if(FM.Gears.pnti[i2] != -1 && ai[j1] == hierMesh().chunkByHookNamed(FM.Gears.pnti[i2]))
                        FM.Gears.pnti[i2] = -1;
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println("FATAL ERROR: Gear pnti[] cut failed on tt[] = " + i2 + " - " + FM.Gears.pnti.length);
                }

        }

        hierMesh().setCurChunk(ai[0]);
        hierMesh().getChunkLocObj(tmpLoc1);
        sfxCrash(tmpLoc1.getPoint());
        return true;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        FM.dryFriction = 1.0F;
        switch(i)
        {
        default:
            break;

        case 2: // '\002'
            if(isEnablePostEndAction(0.0D))
                postEndAction(0.0D, actor, 2, null);
            return false;

        case 3: // '\003'
            if(FM.EI.engines.length > 0)
            {
                hitProp(0, j, actor);
                FM.EI.engines[0].setEngineStuck(actor);
            }
            break;

        case 4: // '\004'
            if(FM.EI.engines.length > 1)
            {
                hitProp(1, j, actor);
                FM.EI.engines[1].setEngineStuck(actor);
            }
            break;

        case 5: // '\005'
            if(FM.EI.engines.length > 2)
            {
                hitProp(2, j, actor);
                FM.EI.engines[2].setEngineStuck(actor);
            }
            break;

        case 6: // '\006'
            if(FM.EI.engines.length > 3)
            {
                hitProp(3, j, actor);
                FM.EI.engines[3].setEngineStuck(actor);
            }
            break;
        }
        return cut(partNames[i]);
    }

    protected int curDMGLevel(int i)
    {
        return curDMGLevel(partNames[i] + "_D0");
    }

    private int curDMGLevel(java.lang.String s)
    {
        int i = s.length() - 1;
        if(i < 2)
            return 0;
        boolean flag = s.charAt(i - 2) == '_' && java.lang.Character.toUpperCase(s.charAt(i - 1)) == 'D' && java.lang.Character.isDigit(s.charAt(i));
        if(!flag)
            return 0;
        com.maddox.il2.engine.HierMesh hiermesh = hierMesh();
        java.lang.String s1 = s.substring(0, i);
        int j;
        for(j = 0; j < 10; j++)
        {
            java.lang.String s2 = s1 + j;
            if(hiermesh.chunkFindCheck(s2) < 0)
                return 0;
            if(hiermesh.isChunkVisible(s2))
                break;
        }

        if(j == 10)
            return 0;
        else
            return j;
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        int j = s.length() - 1;
        com.maddox.il2.engine.HierMesh hiermesh = hierMesh();
        java.lang.String s3 = s;
        boolean flag = s.charAt(j - 2) == '_' && java.lang.Character.toUpperCase(s.charAt(j - 1)) == 'D' && java.lang.Character.isDigit(s.charAt(j));
        FM.dryFriction = 1.0F;
        java.lang.String s4;
        if(flag)
        {
            int k = s.charAt(j) - 48;
            java.lang.String s1 = s.substring(0, j);
            while(!hiermesh.isChunkVisible(s3)) 
            {
                if(k < 9)
                    k++;
                else
                    return;
                s3 = s1 + k;
                if(hiermesh.chunkFindCheck(s3) < 0)
                    return;
            }
            if(k < 9)
            {
                k++;
                s4 = s1 + k;
                if(hiermesh.chunkFindCheck(s4) < 0)
                    s4 = null;
            } else
            {
                s4 = null;
            }
            s1 = s.substring(0, j - 2);
        } else
        {
            if(!hiermesh.isChunkVisible(s3))
                return;
            s4 = null;
            java.lang.String s2 = s;
        }
        if(s4 == null)
        {
            if(!isNet() || isNetMaster())
                nextCUTLevel(s, i, actor);
            return;
        } else
        {
            int l = part(s);
            FM.hit(l);
            hiermesh.chunkVisible(s3, false);
            hiermesh.chunkVisible(s4, true);
            return;
        }
    }

    protected void nextDMGLevels(int i, int j, java.lang.String s, com.maddox.il2.engine.Actor actor)
    {
        if(i <= 0)
            return;
        if(i > 4)
            i = 4;
        if(this == com.maddox.il2.ai.World.getPlayerAircraft() && !com.maddox.il2.ai.World.cur().diffCur.Vulnerability)
            return;
        if(isNet())
        {
            if(isNetPlayer() && !com.maddox.il2.ai.World.cur().diffCur.Vulnerability)
                return;
            if(!com.maddox.il2.engine.Actor.isValid(actor))
                return;
            int k = part(s);
            if(!isNetMaster())
                netPutHits(true, null, i, j, k, actor);
            netPutHits(false, null, i, j, k, actor);
            if(actor != this && FM.isPlayers() && (actor instanceof com.maddox.il2.objects.air.Aircraft) && ((com.maddox.il2.objects.air.Aircraft)actor).isNetPlayer() && j != 0 && i > 3)
                if(s.startsWith("Wing"))
                {
                    if(!FM.isSentBuryNote())
                        com.maddox.il2.net.Chat.sendLogRnd(3, "gore_blowwing", (com.maddox.il2.objects.air.Aircraft)actor, this);
                    FM.setSentWingNote(true);
                } else
                if(s.startsWith("Tail") && !FM.isSentBuryNote())
                    com.maddox.il2.net.Chat.sendLogRnd(3, "gore_blowtail", (com.maddox.il2.objects.air.Aircraft)actor, this);
        }
        while(i-- > 0) 
            nextDMGLevel(s, j, actor);
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        FM.dryFriction = 1.0F;
        debugprintln("Detected NCL in " + s + "..");
        if(this == com.maddox.il2.ai.World.getPlayerAircraft() && !com.maddox.il2.ai.World.cur().diffCur.Vulnerability)
            return;
        int j = s.length() - 1;
        com.maddox.il2.engine.HierMesh hiermesh = hierMesh();
        java.lang.String s3 = s;
        boolean flag = s.charAt(j - 2) == '_' && java.lang.Character.toUpperCase(s.charAt(j - 1)) == 'D' && java.lang.Character.isDigit(s.charAt(j));
        java.lang.String s1;
        if(flag)
        {
            s1 = s.substring(0, j - 2);
        } else
        {
            if(!hiermesh.isChunkVisible(s3))
                return;
            java.lang.String s2 = s;
        }
        int k = part(s);
        if(cutFM(k, i, actor))
        {
            FM.cut(k, i, actor);
            netPutCut(k, i, actor);
            if(FM.isPlayers() && this != actor && (actor instanceof com.maddox.il2.objects.air.Aircraft) && ((com.maddox.il2.objects.air.Aircraft)actor).isNetPlayer() && i == 2 && !FM.isSentWingNote() && !FM.isSentBuryNote() && (k == 34 || k == 37 || k == 33 || k == 36))
            {
                com.maddox.il2.net.Chat.sendLogRnd(3, "gore_sawwing", (com.maddox.il2.objects.air.Aircraft)actor, this);
                FM.setSentWingNote(true);
            }
        }
    }

    public boolean isEnablePostEndAction(double d)
    {
        if(timePostEndAction < 0L)
            return true;
        long l = com.maddox.rts.Time.current() + (long)(int)(d * 1000D);
        return l < timePostEndAction;
    }

    public void postEndAction(double d, com.maddox.il2.engine.Actor actor, int i, com.maddox.il2.engine.Eff3DActor eff3dactor)
    {
        if(!isEnablePostEndAction(d))
        {
            return;
        } else
        {
            timePostEndAction = com.maddox.rts.Time.current() + (long)(int)(d * 1000D);
            com.maddox.rts.MsgEndAction.post(0, d, this, new EndActionParam(actor, eff3dactor), i);
            return;
        }
    }

    public void msgEndAction(java.lang.Object obj, int i)
    {
        com.maddox.il2.objects.air.EndActionParam endactionparam = (com.maddox.il2.objects.air.EndActionParam)obj;
        if(isAlive())
        {
            if(FM.isPlayers() && !FM.isSentBuryNote())
                switch(i)
                {
                case 2: // '\002'
                    if(com.maddox.il2.engine.Actor.isAlive(endactionparam.initiator) && (endactionparam.initiator instanceof com.maddox.il2.objects.air.Aircraft) && ((com.maddox.il2.objects.air.Aircraft)endactionparam.initiator).isNetPlayer() && FM.Loc.z - com.maddox.il2.engine.Engine.land().HQ_Air(FM.Loc.x, FM.Loc.y) > 100D)
                        com.maddox.il2.net.Chat.sendLogRnd(1, "gore_blowup", (com.maddox.il2.objects.air.Aircraft)endactionparam.initiator, this);
                    break;
                }
            int l;
            switch(i)
            {
            case 2: // '\002'
                netExplode();
                if(endactionparam.smoke != null)
                {
                    com.maddox.il2.engine.Eff3DActor.finish(endactionparam.smoke);
                    sfxSmokeState(0, 0, false);
                }
                doExplosion();
                for(int j = 0; j < FM.AS.astateEngineStates.length; j++)
                    FM.AS.hitEngine(this, j, 1000);

                for(int k = 0; k < FM.AS.astateTankStates.length; k++)
                    FM.AS.hitTank(this, k, 1000);

                float f = 50F;
                com.maddox.il2.engine.Actor actor = null;
                java.lang.String s = null;
                if(FM.Gears.onGround() && FM.Vrel.lengthSquared() < 70D)
                {
                    f = 0.0F;
                } else
                {
                    com.maddox.JGP.Point3d point3d = new Point3d(FM.Loc);
                    com.maddox.JGP.Point3d point3d1 = new Point3d(FM.Loc);
                    com.maddox.JGP.Point3d point3d2 = new Point3d();
                    FM.Vrel.set(FM.Vwld);
                    FM.Vrel.normalize();
                    FM.Vrel.scale(20D);
                    point3d1.add(FM.Vrel);
                    actor = com.maddox.il2.engine.Engine.collideEnv().getLine(point3d, point3d1, false, this, point3d2);
                    if(com.maddox.il2.engine.Actor.isAlive(actor) && (actor instanceof com.maddox.il2.engine.ActorHMesh))
                    {
                        com.maddox.il2.engine.Mesh mesh = ((com.maddox.il2.engine.ActorMesh)actor).mesh();
                        com.maddox.il2.engine.Loc loc = actor.pos.getAbs();
                        float f3 = mesh.detectCollisionLine(loc, point3d, point3d1);
                        if(f3 >= 0.0F)
                            s = com.maddox.il2.engine.Mesh.collisionChunk(0);
                        if((actor instanceof com.maddox.il2.objects.ships.BigshipGeneric) || (actor instanceof com.maddox.il2.objects.ships.ShipGeneric))
                        {
                            float f4 = 0.018F * (float)FM.Vwld.length();
                            if(f4 > 1.0F)
                                f4 = 1.0F;
                            if(f4 < 0.1F)
                                f4 = 0.1F;
                            float f5 = FM.M.fuel;
                            if(f5 > 300F)
                                f5 = 300F;
                            f = f4 * (50F + 0.7F * FM.CT.getWeaponMass() + 0.3F * f5);
                        }
                    }
                }
                float f1 = 0.5F * f;
                if(f1 < 50F)
                    f1 = 50F;
                if(f1 > 300F)
                    f1 = 300F;
                float f2 = 0.7F * f;
                if(f2 < 70F)
                    f2 = 70F;
                if(f2 > 350F)
                    f2 = 350F;
                com.maddox.il2.ai.MsgExplosion.send(actor, s, FM.Loc, this, f, 0.9F * f, 0, f1);
                com.maddox.il2.ai.MsgExplosion.send(actor, s, FM.Loc, this, 0.5F * f, 0.25F * f, 1, f2);
                // fall through

            case 3: // '\003'
                explode();
                // fall through

            default:
                l = 0;
                break;
            }
            for(; l < java.lang.Math.min(FM.crew, 9); l++)
                if(!FM.AS.isPilotDead(l))
                    FM.AS.hitPilot(FM.actor, l, 100);

            setDamager(endactionparam.initiator, 4);
            com.maddox.il2.ai.World.onActorDied(this, getDamager());
        }
        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current(), this);
    }

    protected void doExplosion()
    {
        if(FM.Loc.z < com.maddox.il2.engine.Engine.cur.land.HQ_Air(FM.Loc.x, FM.Loc.y) + 3D)
        {
            com.maddox.il2.ai.World.cur();
            if(com.maddox.il2.ai.World.land().isWater(FM.Loc.x, FM.Loc.y))
            {
                com.maddox.il2.objects.effects.Explosions.AirDrop_Water(FM.Loc);
            } else
            {
                com.maddox.il2.objects.effects.Explosions.AirDrop_Land(FM.Loc);
                com.maddox.il2.engine.Loc loc = new Loc(FM.Loc);
                loc.getPoint().z = com.maddox.il2.ai.World.cur().land().HQ(FM.Loc.x, FM.Loc.y);
                com.maddox.il2.engine.Eff3DActor.New(loc, 1.0F, "EFFECTS/Smokes/SmokeBoiling.eff", 1200F);
                com.maddox.il2.engine.Eff3DActor.New(loc, 1.0F, "3DO/Effects/Aircraft/FireGND.eff", 1200F);
                com.maddox.il2.engine.Eff3DActor.New(loc, 1.0F, "3DO/Effects/Aircraft/BlackHeavyGND.eff", 1200F);
            }
        } else
        {
            com.maddox.il2.objects.effects.Explosions.ExplodeFuel(FM.Loc);
        }
    }

    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        boolean flag = com.maddox.il2.engine.Engine.collideEnv().isDoCollision() && com.maddox.il2.ai.World.getPlayerAircraft() != this;
        if(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric)
        {
            FM.Gears.bFlatTopGearCheck = true;
            if(flag && (com.maddox.rts.Time.tickCounter() + hashCode() & 0xf) != 0)
                aflag[0] = false;
        } else
        if(flag && (com.maddox.rts.Time.tickCounter() & 0xf) != 0 && (actor instanceof com.maddox.il2.objects.air.Aircraft) && FM.Gears.isUnderDeck())
            aflag[0] = !((com.maddox.il2.objects.air.Aircraft)actor).FM.Gears.isUnderDeck();
        if(com.maddox.il2.engine.Engine.collideEnv().isDoCollision() && (actor instanceof com.maddox.il2.objects.air.Aircraft) && com.maddox.il2.game.Mission.isCoop() && actor.isNetMirror() && (isMirrorUnderDeck() || FM.Gears.isUnderDeck() || com.maddox.rts.Time.tickCounter() <= 2))
            aflag[0] = false;
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(isNet() && isNetMirror())
            return;
        if(actor instanceof com.maddox.il2.objects.ActorCrater)
        {
            if(!s.startsWith("Gear"))
                return;
            if(netUser() != null && netUser() == ((com.maddox.il2.objects.ActorCrater)actor).netOwner)
                return;
        }
        if(this == com.maddox.il2.ai.World.getPlayerAircraft())
            com.maddox.il2.game.TimeSkip.airAction(1);
        FM.dryFriction = 1.0F;
        if(s.startsWith("Pilot"))
            if(this == com.maddox.il2.ai.World.getPlayerAircraft() && !com.maddox.il2.ai.World.cur().diffCur.Vulnerability)
            {
                return;
            } else
            {
                int i = s.charAt(5) - 49;
                killPilot(this, i);
                return;
            }
        if(s.startsWith("Head"))
            if(this == com.maddox.il2.ai.World.getPlayerAircraft() && !com.maddox.il2.ai.World.cur().diffCur.Vulnerability)
            {
                return;
            } else
            {
                int j = s.charAt(4) - 49;
                killPilot(this, j);
                return;
            }
        if(actor instanceof com.maddox.il2.objects.Wreckage)
        {
            if(s.startsWith("CF_"))
                return;
            if(actor.getOwner() == this)
                return;
            if(netUser() != null && netUser() == ((com.maddox.il2.objects.Wreckage)actor).netOwner)
            {
                return;
            } else
            {
                actor.collide(false);
                nextDMGLevels(3, 0, s, this);
                return;
            }
        }
        if(actor instanceof com.maddox.il2.objects.air.Paratrooper)
        {
            FM.getSpeed(v1);
            actor.getSpeed(Vd);
            Vd.x -= v1.x;
            Vd.y -= v1.y;
            Vd.z -= v1.z;
            if(Vd.length() > 30D)
            {
                setDamager(actor, 4);
                nextDMGLevels(4, 0, s, actor);
            }
            return;
        }
        if((actor instanceof com.maddox.il2.objects.vehicles.artillery.RocketryRocket) && s1.startsWith("Wing"))
        {
            com.maddox.il2.objects.vehicles.artillery.RocketryRocket rocketryrocket = (com.maddox.il2.objects.vehicles.artillery.RocketryRocket)actor;
            com.maddox.il2.engine.Loc loc = new Loc();
            com.maddox.JGP.Point3d point3d = new Point3d();
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            com.maddox.JGP.Vector3d vector3d1 = new Vector3d();
            pos.getAbs(loc);
            point3d.set(actor.pos.getAbsPoint());
            loc.transformInv(point3d);
            boolean flag = point3d.y > 0.0D;
            vector3d.set(0.0D, flag ? hierMesh().collisionR() : -hierMesh().collisionR(), 0.0D);
            loc.transform(vector3d);
            point3d.set(FM.Loc);
            point3d.add(vector3d);
            actor.pos.getAbs(loc);
            loc.transformInv(point3d);
            vector3d.set(FM.Vwld);
            actor.pos.speed(vector3d1);
            vector3d.sub(vector3d1);
            loc.transformInv(vector3d);
            vector3d.z += (flag ? 1.0D : -1D) * FM.getW().x * (double)hierMesh().collisionR();
            if(vector3d.x * vector3d.x + vector3d.y * vector3d.y < 4D)
            {
                if(point3d.y * vector3d.z > 0.0D)
                    rocketryrocket.sendRocketStateChange('a', this);
                else
                    rocketryrocket.sendRocketStateChange('b', this);
                return;
            }
            rocketryrocket.sendRocketStateChange(flag ? 'l' : 'r', this);
        }
        if(FM.turnOffCollisions && (s.startsWith("Wing") || s.startsWith("Arone") || s.startsWith("Keel") || s.startsWith("Rudder") || s.startsWith("Stab") || s.startsWith("Vator") || s.startsWith("Nose") || s.startsWith("Tail")))
            return;
        if((actor instanceof com.maddox.il2.objects.air.Aircraft) && com.maddox.il2.engine.Actor.isValid(actor) && getArmy() == actor.getArmy())
        {
            double d = com.maddox.il2.engine.Engine.cur.land.HQ(FM.Loc.x, FM.Loc.y);
            com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
            if(FM.Loc.z - (double)(2.0F * FM.Gears.H) < d && aircraft.FM.Loc.z - (double)(2.0F * aircraft.FM.Gears.H) < d)
                setDamagerExclude(actor);
        }
        if(s != null && hierMesh().chunkFindCheck(s) != -1)
        {
            hierMesh().setCurChunk(s);
            hierMesh().getChunkLocObj(tmpLoc1);
            Vd.set(FM.Vwld);
            FM.Or.transformInv(Vd);
            Vd.normalize();
            Vd.negate();
            Vd.scale(2000F / FM.M.mass);
            Vd.cross(tmpLoc1.getPoint(), Vd);
            FM.getW().x += (float)Vd.x;
            FM.getW().y += (float)Vd.y;
            FM.getW().z += (float)Vd.z;
        }
        setDamager(actor, 4);
        nextDMGLevels(4, 0, s, actor);
    }

    private void splintersHit(com.maddox.il2.ai.Explosion explosion)
    {
        float af[] = new float[2];
        float f = mesh().collisionR();
        float f1 = 1.0F;
        pos.getTime(com.maddox.rts.Time.current(), tmpLocExp);
        tmpLocExp.get(Pd);
        explosion.computeSplintersHit(Pd, f, 1.0F, af);
        com.maddox.il2.ai.Shot shot = new Shot();
        shot.chunkName = "CF_D0";
        shot.initiator = explosion.initiator;
        shot.tickOffset = com.maddox.rts.Time.tickOffset();
        int i = (int)(af[0] * 2.0F + 0.5F);
        if(i <= 0)
            return;
        while(i > 192) 
        {
            i = (int)((float)i * 0.5F);
            f1 *= 2.0F;
        }
        for(int k = 0; k < i; k++)
        {
            tmpP1.set(explosion.p);
            tmpLocExp.get(tmpP2);
            double d = tmpP1.distance(tmpP2);
            tmpP2.add(com.maddox.il2.ai.World.Rnd().nextDouble(-f, f), com.maddox.il2.ai.World.Rnd().nextDouble(-f, f), com.maddox.il2.ai.World.Rnd().nextDouble(-f, f));
            if(d > (double)f)
                tmpP1.interpolate(tmpP1, tmpP2, 1.0D - (double)f / d);
            tmpP2.interpolate(tmpP1, tmpP2, 2D);
            int j = hierMesh().detectCollisionLineMulti(tmpLocExp, tmpP1, tmpP2);
            if(j > 0)
            {
                com.maddox.il2.ai.Explosion _tmp = explosion;
                shot.mass = 0.015F * com.maddox.il2.ai.World.Rnd().nextFloat(0.25F, 1.75F) * f1;
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                {
                    com.maddox.il2.ai.Explosion _tmp1 = explosion;
                    shot.mass = 0.015F * com.maddox.il2.ai.World.Rnd().nextFloat(0.1F, 10F) * f1;
                }
                float f2 = explosion.power * 10F;
                if(shot.mass > f2)
                    shot.mass = f2;
                shot.p.interpolate(tmpP1, tmpP2, hierMesh().collisionDistMulti(0));
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.333333F)
                    shot.powerType = 2;
                else
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    shot.powerType = 3;
                else
                    shot.powerType = 0;
                shot.v.x = (float)(tmpP2.x - tmpP1.x);
                shot.v.y = (float)(tmpP2.y - tmpP1.y);
                shot.v.z = (float)(tmpP2.z - tmpP1.z);
                shot.v.normalize();
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                    shot.v.scale(af[1] * com.maddox.il2.ai.World.Rnd().nextFloat(0.1F, 10F));
                else
                    shot.v.scale(af[1] * com.maddox.il2.ai.World.Rnd().nextFloat(0.9F, 1.1F));
                msgShot(shot);
            }
        }

    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if(this == com.maddox.il2.ai.World.getPlayerAircraft())
            com.maddox.il2.game.TimeSkip.airAction(3);
        setExplosion(explosion);
        FM.dryFriction = 1.0F;
        if(explosion.power <= 0.0F || explosion.chunkName != null && explosion.chunkName.equals(partNames[43]))
        {
            debugprintln("Splash hit from " + ((explosion.initiator instanceof com.maddox.il2.objects.air.Aircraft) ? ((com.maddox.il2.objects.air.Aircraft)explosion.initiator).typedName() : explosion.initiator.name()) + " in " + explosion.chunkName + " is Nill..");
            return;
        }
        com.maddox.il2.ai.Explosion _tmp = explosion;
        if(explosion.powerType == 1)
        {
            splintersHit(explosion);
            return;
        }
        float f = explosion.power;
        float f1 = 0.0F;
        com.maddox.il2.ai.Explosion _tmp1 = explosion;
        if(explosion.powerType == 0)
        {
            f *= 0.5F;
            f1 = f;
        }
        if(explosion.chunkName != null)
        {
            if(explosion.chunkName.startsWith("Wing") && explosion.chunkName.endsWith("_D3"))
                FM.setCapableOfACM(false);
            if(explosion.chunkName.startsWith("Wing") && explosion.power > 0.017F)
            {
                if(explosion.chunkName.startsWith("WingL"))
                {
                    debugprintln("Large Shockwave Hits the Left Wing - Wing Stalls.");
                    FM.AS.setFMSFX(explosion.initiator, 2, 20);
                }
                if(explosion.chunkName.startsWith("WingR"))
                {
                    debugprintln("Large Shockwave Hits the Right Wing - Wing Stalls.");
                    FM.AS.setFMSFX(explosion.initiator, 3, 20);
                }
            }
        }
        float f2;
        if(explosion.chunkName == null)
            f2 = explosion.receivedTNT_1meter(this);
        else
            f2 = f;
        if(f2 <= 5.000001E-007F)
            return;
        debugprintln("Splash hit from " + ((explosion.initiator instanceof com.maddox.il2.objects.air.Aircraft) ? ((com.maddox.il2.objects.air.Aircraft)explosion.initiator).typedName() : explosion.initiator.name()) + " in " + explosion.chunkName + " for " + (int)((100F * f2) / (0.01F + 3F * FM.Sq.getToughness(part(explosion.chunkName)))) + " % ( " + f2 + " kg)..");
        if(explosion.chunkName == null)
        {
            f2 /= 0.01F;
        } else
        {
            if(explosion.chunkName.endsWith("_D0") && !explosion.chunkName.startsWith("Gear"))
            {
                if(f2 > 0.01F)
                    f2 = 1.0F + (f2 - 0.01F) / FM.Sq.getToughness(part(explosion.chunkName));
                else
                    f2 /= 0.01F;
            } else
            {
                f2 /= FM.Sq.getToughness(part(explosion.chunkName));
            }
            f2 += FM.Sq.eAbsorber[part(explosion.chunkName)];
        }
        if(f2 >= 1.0F)
            setDamager(explosion.initiator, (int)f2);
        if(explosion.chunkName != null)
        {
            if((int)f2 > 0)
            {
                setDamager(explosion.initiator, 1);
                if(explosion.chunkName.startsWith("Pilot"))
                {
                    killPilot(explosion.initiator, explosion.chunkName.charAt(5) - 49);
                    return;
                }
                if(explosion.chunkName.startsWith("Head"))
                {
                    killPilot(explosion.initiator, explosion.chunkName.charAt(4) - 49);
                    return;
                }
            }
            nextDMGLevels((int)f2, 1, explosion.chunkName, explosion.initiator);
        } else
        {
            for(int i = 0; i < partNamesForAll.length; i++)
            {
                int l = com.maddox.il2.ai.World.Rnd().nextInt(partNamesForAll.length);
                if(!isChunkAnyDamageVisible(partNamesForAll[l]))
                    continue;
                nextDMGLevels((int)f2, 1, partNamesForAll[l] + "_D0", explosion.initiator);
                break;
            }

        }
        if(explosion.chunkName != null)
            FM.Sq.eAbsorber[part(explosion.chunkName)] = f2 - (float)(int)f2;
        if(f2 > 8F)
            if(f2 / (float)partNamesForAll.length > 1.5F)
            {
                for(int j = 0; j < partNamesForAll.length; j++)
                    if(isChunkAnyDamageVisible(partNamesForAll[j]))
                        nextDMGLevels(3, 1, partNamesForAll[j] + "_D0", explosion.initiator);

            } else
            {
                int k = (int)f2 / 3 - 1;
                if(k > partNamesForAll.length * 2)
                    k = partNamesForAll.length * 2;
                for(int i1 = 0; i1 < k; i1++)
                {
                    int j1 = com.maddox.il2.ai.World.Rnd().nextInt(partNamesForAll.length);
                    if(isChunkAnyDamageVisible(partNamesForAll[j1]))
                        nextDMGLevels(3, 1, partNamesForAll[j1] + "_D0", explosion.initiator);
                }

            }
        if(bWasAlive && FM.isTakenMortalDamage() && (getDamager() instanceof com.maddox.il2.objects.air.Aircraft) && FM.actor.getArmy() != getDamager().getArmy() && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 66)
        {
            if(!buried)
                com.maddox.il2.objects.sounds.Voice.speakNiceKill((com.maddox.il2.objects.air.Aircraft)getDamager());
            buried = true;
        }
        bWasAlive = true;
        if(f1 > 0.0F)
        {
            com.maddox.il2.objects.air.MsgExplosionPostVarSet msgexplosionpostvarset = new MsgExplosionPostVarSet();
            msgexplosionpostvarset.THIS = this;
            msgexplosionpostvarset.chunkName = explosion.chunkName;
            msgexplosionpostvarset.p.set(explosion.p);
            msgexplosionpostvarset.initiator = explosion.initiator;
            msgexplosionpostvarset.power = f1;
            msgexplosionpostvarset.radius = explosion.radius;
            new com.maddox.rts.MsgAction(false, msgexplosionpostvarset) {

                public void doAction(java.lang.Object obj)
                {
                    com.maddox.il2.objects.air.MsgExplosionPostVarSet msgexplosionpostvarset1 = (com.maddox.il2.objects.air.MsgExplosionPostVarSet)obj;
                    if(!com.maddox.il2.engine.Actor.isValid(msgexplosionpostvarset1.THIS))
                    {
                        return;
                    } else
                    {
                        com.maddox.il2.ai.MsgExplosion.send(msgexplosionpostvarset1.THIS, msgexplosionpostvarset1.chunkName, msgexplosionpostvarset1.p, msgexplosionpostvarset1.initiator, 48F * msgexplosionpostvarset1.power, msgexplosionpostvarset1.power, 1, java.lang.Math.max(msgexplosionpostvarset1.radius, 30F));
                        return;
                    }
                }

            }
;
        }
    }

    protected void doRicochet(com.maddox.il2.ai.Shot shot)
    {
        v1.x *= com.maddox.il2.ai.World.Rnd().nextFloat(0.25F, 1.0F);
        v1.y *= com.maddox.il2.ai.World.Rnd().nextFloat(-1F, -0.25F);
        v1.z *= com.maddox.il2.ai.World.Rnd().nextFloat(-1F, -0.25F);
        v1.normalize();
        v1.scale(com.maddox.il2.ai.World.Rnd().nextFloat(10F, 600F));
        FM.Or.transform(v1);
        doRicochet(shot.p, v1);
        shot.power = 0.0F;
    }

    protected void doRicochetBack(com.maddox.il2.ai.Shot shot)
    {
        v1.x *= -1D;
        v1.y *= -1D;
        v1.z *= -1D;
        v1.scale(com.maddox.il2.ai.World.Rnd().nextFloat(0.25F, 1.0F));
        FM.Or.transform(v1);
        doRicochet(shot.p, v1);
    }

    protected void doRicochet(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Vector3d vector3d)
    {
        com.maddox.il2.objects.weapons.BallisticProjectile ballisticprojectile = new BallisticProjectile(point3d, vector3d, 1.0F);
        com.maddox.il2.engine.Eff3DActor.New(ballisticprojectile, null, null, 4F, "3DO/Effects/Tracers/TrailRicochet.eff", 1.0F);
        pos.getAbs(tmpLoc1);
        tmpLoc1.transformInv(point3d);
        com.maddox.il2.engine.Eff3DActor.New(this, null, new Loc(point3d), 1.0F, "3DO/Effects/Fireworks/12mmRicochet.eff", 0.2F);
        com.maddox.il2.engine.Eff3DActor.New(this, null, new Loc(point3d), 0.5F, "3DO/Effects/Fireworks/20_Sparks.eff", -1F);
    }

    protected void setShot(com.maddox.il2.ai.Shot shot)
    {
        if((this == com.maddox.il2.ai.World.getPlayerAircraft() || isNetPlayer()) && !com.maddox.il2.ai.World.cur().diffCur.Vulnerability)
        {
            shot.chunkName = partNames[43];
            shot.power = 0.0F;
            shot.mass = 0.0F;
        }
        if(bWasAlive)
            bWasAlive = !FM.isTakenMortalDamage();
        v1.sub(shot.v, FM.Vwld);
        double d = v1.length();
        shot.power = (float)((double)shot.mass * d * d) * 0.5F;
        if(shot.powerType == 0)
            shot.power *= 0.666F;
        FM.Or.transformInv(v1);
        v1.normalize();
        tmpLoc1.set(shot.p);
        pos.getAbs(tmpLoc2);
        pos.getCurrent(tmpLoc3);
        tmpLoc3.interpolate(tmpLoc2, shot.tickOffset);
        tmpLoc1.sub(tmpLoc3);
        tmpLoc1.get(Pd);
        Vd.set(shot.v);
        Vd.normalize();
        Vd.scale(0.10000000149011612D);
        tmpP1.set(shot.p);
        tmpP1.sub(Vd);
        Vd.normalize();
        Vd.scale(48.900001525878906D);
        tmpP2.set(shot.p);
        tmpP2.add(Vd);
        tmpBonesHit = hierMesh().detectCollisionLineMulti(tmpLoc3, tmpP1, tmpP2);
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.ai.World.cur().isArcade())
        {
            com.maddox.il2.objects.ActorSimpleMesh actorsimplemesh = new ActorSimpleMesh("3DO/Arms/MatrixXX/mono.sim");
            actorsimplemesh.pos.setBase(this, null, false);
            tmpOr.setAT0(v1);
            actorsimplemesh.pos.setRel(Pd, tmpOr);
            float f = (float)java.lang.Math.sqrt(java.lang.Math.sqrt(shot.mass));
            actorsimplemesh.mesh().setScaleXYZ(0.75F * f, f, f);
            actorsimplemesh.drawing(true);
            actorsimplemesh.postDestroy(com.maddox.rts.Time.current() + 30000L);
        }
    }

    protected void setExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if((this == com.maddox.il2.ai.World.getPlayerAircraft() || isNetPlayer()) && !com.maddox.il2.ai.World.cur().diffCur.Vulnerability)
            explosion.chunkName = partNames[43];
        if(explosion.chunkName == null && !isChunkAnyDamageVisible("CF"))
            explosion.chunkName = partNames[43];
        if(bWasAlive)
            bWasAlive = !FM.isTakenMortalDamage();
    }

    protected void msgSndShot(float f, double d, double d1, double d2)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        com.maddox.il2.engine.Actor._tmpPoint.set(d, d1, d2);
        sfxHit(f, com.maddox.il2.engine.Actor._tmpPoint);
        if(isNet() && FM.isPlayers() && (FM instanceof com.maddox.il2.fm.RealFlightModel))
        {
            FM.dryFriction = 1.0F;
            ((com.maddox.il2.fm.RealFlightModel)FM).producedShakeLevel = 1.0F;
            float f1 = (2000F * f) / FM.M.mass;
            FM.getW().add(com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1), com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1), com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1));
        }
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        if(this == com.maddox.il2.ai.World.getPlayerAircraft())
            com.maddox.il2.game.TimeSkip.airAction(2);
        setShot(shot);
        if(!isNet())
        {
            FM.dryFriction = 1.0F;
            if(FM.isPlayers() && (FM instanceof com.maddox.il2.fm.RealFlightModel))
                ((com.maddox.il2.fm.RealFlightModel)FM).producedShakeLevel = 1.0F;
            float f = (2000F * shot.mass) / FM.M.mass;
            FM.getW().add(com.maddox.il2.ai.World.Rnd().nextFloat(-f, f), com.maddox.il2.ai.World.Rnd().nextFloat(-f, f), com.maddox.il2.ai.World.Rnd().nextFloat(-f, f));
        }
        if(shot.chunkName == null)
            return;
        if(shot.chunkName == partNames[43])
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                doRicochet(shot);
            return;
        }
        if(shot.chunkName.startsWith("Wing") && (shot.chunkName.endsWith("_D3") || shot.chunkName.endsWith("_D2") && FM.Skill >= 2))
            FM.setCapableOfACM(false);
        if((FM instanceof com.maddox.il2.ai.air.Pilot) && com.maddox.il2.ai.World.Rnd().nextInt(-1, 8) < FM.Skill)
            ((com.maddox.il2.ai.air.Pilot)FM).setAsDanger(shot.initiator);
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && (FM instanceof com.maddox.il2.fm.RealFlightModel))
        {
            com.maddox.il2.engine.Actor._tmpPoint.set(pos.getAbsPoint());
            com.maddox.il2.engine.Actor._tmpPoint.sub(shot.p);
            msgSndShot(shot.mass, com.maddox.il2.engine.Actor._tmpPoint.x, com.maddox.il2.engine.Actor._tmpPoint.y, com.maddox.il2.engine.Actor._tmpPoint.z);
        }
        shot.bodyMaterial = 2;
        if(isNetPlayer())
            sendMsgSndShot(shot);
        if(tmpBonesHit <= 0) goto _L2; else goto _L1
_L1:
        int i;
        debuggunnery("");
        debuggunnery("New Bullet: E = " + (int)shot.power + " [J], M = " + (int)(1000F * shot.mass) + " [g], Type = (" + sttp(shot.powerType) + ")");
        if(shot.powerType == 1)
            tmpBonesHit = java.lang.Math.min(tmpBonesHit, 2);
        i = 0;
          goto _L3
_L7:
        hierMesh();
        java.lang.String s = com.maddox.il2.engine.HierMesh.collisionNameMulti(i, 1);
        if(s.length() == 0)
        {
            hierMesh();
            s = com.maddox.il2.engine.HierMesh.collisionNameMulti(i, 0);
        }
        if(shot.power <= 0.0F)
            continue; /* Loop/switch isn't completed */
        Pd.interpolate(tmpP1, tmpP2, hierMesh().collisionDistMulti(i));
        tmpLoc3.transformInv(Pd);
        debuggunnery("Hit Bone [" + s + "], E = " + (int)shot.power);
        hitBone(s, shot, Pd);
        if(s.startsWith("xx"))
            continue; /* Loop/switch isn't completed */
        this;
        33.333F;
        if(i != tmpBonesHit - 1) goto _L5; else goto _L4
_L4:
        0.02F;
          goto _L6
_L5:
        hierMesh();
        com.maddox.il2.engine.HierMesh.collisionDistMulti(i + 1) - hierMesh().collisionDistMulti(i);
_L6:
        JVM INSTR fmul ;
        shot;
        getEnergyPastArmor();
        JVM INSTR pop ;
        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
        {
            shot.power = 0.0F;
            debuggunnery("Inner Ricochet");
        }
        i++;
_L3:
        if(i < tmpBonesHit) goto _L7; else goto _L2
_L2:
        boolean flag = false;
        for(int j = 0; j < tmpBonesHit; j++)
        {
            hierMesh();
            if(com.maddox.il2.engine.HierMesh.collisionNameMulti(j, 1) != null)
            {
                hierMesh();
                if(!com.maddox.il2.engine.HierMesh.collisionNameMulti(j, 1).equals(hierMesh().collisionNameMulti(j, 0)))
                    continue;
            }
            flag = true;
        }

        if(flag)
        {
            debuggunnery("[+++ PROCESS OLD +++]");
            shot.chunkName = hierMesh().collisionNameMulti(0, 0);
            if(shot.chunkName.startsWith("WingLOut") && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 20)
                shot.chunkName = "AroneL_D0";
            if(shot.chunkName.startsWith("WingROut") && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 20)
                shot.chunkName = "AroneR_D0";
            if(shot.chunkName.startsWith("StabL") && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 45)
                shot.chunkName = "VatorL_D0";
            if(shot.chunkName.startsWith("StabR") && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 45)
                shot.chunkName = "VatorR_D0";
            if(shot.chunkName.startsWith("Keel1") && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 33)
                shot.chunkName = "Rudder1_D0";
            if(shot.chunkName.startsWith("Keel2") && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 33)
                shot.chunkName = "Rudder2_D0";
            float f1 = shot.powerToTNT();
            debugprintln("Bullet hit from " + ((shot.initiator instanceof com.maddox.il2.objects.air.Aircraft) ? ((com.maddox.il2.objects.air.Aircraft)shot.initiator).typedName() : shot.initiator.name()) + " in " + shot.chunkName + " for " + (int)((100F * f1) / (0.01F + 3F * FM.Sq.getToughness(part(shot.chunkName)))) + " %..");
            shot.bodyMaterial = 2;
            if((FM instanceof com.maddox.il2.ai.air.Pilot) && com.maddox.il2.ai.World.Rnd().nextInt(-1, 8) < FM.Skill)
                ((com.maddox.il2.ai.air.Pilot)FM).setAsDanger(shot.initiator);
            if(f1 <= 5.000001E-007F)
                return;
            if(shot.chunkName.endsWith("_D0") && !shot.chunkName.startsWith("Gear"))
            {
                if(f1 > 0.01F)
                    f1 = 1.0F + (f1 - 0.01F) / FM.Sq.getToughness(part(shot.chunkName));
                else
                    f1 /= 0.01F;
            } else
            {
                f1 /= FM.Sq.getToughness(part(shot.chunkName));
            }
            f1 += FM.Sq.eAbsorber[part(shot.chunkName)];
            int k = (int)f1;
            FM.Sq.eAbsorber[part(shot.chunkName)] = f1 - (float)k;
            if(k > 0)
            {
                setDamager(shot.initiator, k);
                if(shot.chunkName.startsWith("Pilot"))
                {
                    killPilot(shot.initiator, shot.chunkName.charAt(5) - 49);
                    return;
                }
                if(shot.chunkName.startsWith("Head"))
                {
                    killPilot(shot.initiator, shot.chunkName.charAt(4) - 49);
                    return;
                }
            }
            nextDMGLevels(k, 2, shot.chunkName, shot.initiator);
        }
        if(bWasAlive && FM.isTakenMortalDamage() && (getDamager() instanceof com.maddox.il2.objects.air.Aircraft) && FM.actor.getArmy() != getDamager().getArmy() && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 66)
        {
            if(!buried)
                com.maddox.il2.objects.sounds.Voice.speakNiceKill((com.maddox.il2.objects.air.Aircraft)getDamager());
            buried = true;
        }
        bWasAlive = true;
        return;
    }

    private java.lang.String sttp(int i)
    {
        switch(i)
        {
        case 2: // '\002'
            return "AP";

        case 3: // '\003'
            return "API/APIT";

        case 1: // '\001'
            return "CUMULATIVE";

        case 0: // '\0'
            return "HE";
        }
        return null;
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
    }

    protected void hitChunk(java.lang.String s, com.maddox.il2.ai.Shot shot)
    {
        if(s.lastIndexOf("_") == -1)
            s = s + "_D" + chunkDamageVisible(s);
        float f = shot.powerToTNT();
        if(s.endsWith("_D0") && !s.startsWith("Gear"))
        {
            if(f > 0.01F)
                f = 1.0F + (f - 0.01F) / FM.Sq.getToughness(part(s));
            else
                f /= 0.01F;
        } else
        {
            f /= FM.Sq.getToughness(part(s));
        }
        f += FM.Sq.eAbsorber[part(s)];
        int i = (int)f;
        FM.Sq.eAbsorber[part(s)] = f - (float)i;
        if(i > 0)
            setDamager(shot.initiator, i);
        nextDMGLevels(i, 2, s, shot.initiator);
    }

    protected void hitFlesh(int i, com.maddox.il2.ai.Shot shot, int j)
    {
        int k = (int)(shot.power * 0.0035F * com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 1.5F));
        switch(j)
        {
        case 0: // '\0'
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                return;
            if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            k = (int)((float)k * 30F);
            break;

        case 2: // '\002'
            k = (int)((float)k / 3F);
            break;
        }
        debuggunnery("*** Pilot " + i + " hit for " + k + "% (" + (int)shot.power + " J)");
        FM.AS.hitPilot(shot.initiator, i, k);
        if(FM.AS.astatePilotStates[i] > 95 && j == 0)
            debuggunnery("*** Headshot!.");
    }

    protected float getEnergyPastArmor(float f, float f1, com.maddox.il2.ai.Shot shot)
    {
        shot.power -= (double)(shot.powerType != 0 ? 1.0F : 2.0F) * ((double)(f * 1700F) * java.lang.Math.cos(f1));
        return shot.power;
    }

    protected float getEnergyPastArmor(float f, com.maddox.il2.ai.Shot shot)
    {
        shot.power -= (shot.powerType != 0 ? 1.0F : 2.0F) * (f * 1700F);
        return shot.power;
    }

    public static boolean isArmorPenetrated(float f, com.maddox.il2.ai.Shot shot)
    {
        return shot.power > (shot.powerType != 0 ? 1.0F : 2.0F) * (f * 1700F);
    }

    protected float getEnergyPastArmor(double d, float f, com.maddox.il2.ai.Shot shot)
    {
        shot.power -= (double)(shot.powerType != 0 ? 1.0F : 2.0F) * (d * 1700D * java.lang.Math.cos(f));
        return shot.power;
    }

    protected float getEnergyPastArmor(double d, com.maddox.il2.ai.Shot shot)
    {
        shot.power -= (double)(shot.powerType != 0 ? 1.0F : 2.0F) * (d * 1700D);
        return shot.power;
    }

    public static boolean isArmorPenetrated(double d, com.maddox.il2.ai.Shot shot)
    {
        return (double)shot.power > (double)(shot.powerType != 0 ? 1.0F : 2.0F) * (d * 1700D);
    }

    protected void netHits(int i, int j, int k, com.maddox.il2.engine.Actor actor)
    {
        if(isNetMaster())
            setDamager(actor, i);
        while(i-- > 0) 
            nextDMGLevel(partNames[k] + "_D0", j, actor);
    }

    public int curDMGProp(int i)
    {
        java.lang.String s = "Prop" + (i + 1) + "_D1";
        com.maddox.il2.engine.HierMesh hiermesh = hierMesh();
        if(hiermesh.chunkFindCheck(s) < 0)
            return 0;
        return !hiermesh.isChunkVisible(s) ? 0 : 1;
    }

    protected void addGun(com.maddox.il2.ai.BulletEmitter bulletemitter, int i)
    {
        if(this == com.maddox.il2.ai.World.getPlayerAircraft() && !com.maddox.il2.ai.World.cur().diffCur.Limited_Ammo)
            bulletemitter.loadBullets(-1);
        java.lang.String s = bulletemitter.getHookName();
        if(s == null)
            return;
        com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[i];
        int j;
        if(abulletemitter == null)
            j = 0;
        else
            j = abulletemitter.length;
        com.maddox.il2.ai.BulletEmitter abulletemitter1[] = new com.maddox.il2.ai.BulletEmitter[j + 1];
        int k;
        for(k = 0; k < j; k++)
            abulletemitter1[k] = abulletemitter[k];

        abulletemitter1[k] = bulletemitter;
        FM.CT.Weapons[i] = abulletemitter1;
        if(bulletemitter.isEnablePause())
            bGunPodsExist = true;
    }

    public void detachGun(int i)
    {
        for(int j = 0; j < FM.CT.Weapons.length; j++)
        {
            com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[j];
            if(abulletemitter != null)
            {
                for(int k = 0; k < abulletemitter.length; k++)
                    abulletemitter[k] = abulletemitter[k].detach(hierMesh(), i);

            }
        }

    }

    public com.maddox.il2.objects.weapons.Gun getGunByHookName(java.lang.String s)
    {
        for(int i = 0; i < FM.CT.Weapons.length; i++)
        {
            com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[i];
            if(abulletemitter != null)
            {
                for(int j = 0; j < abulletemitter.length; j++)
                    if(abulletemitter[j] instanceof com.maddox.il2.objects.weapons.Gun)
                    {
                        com.maddox.il2.objects.weapons.Gun gun = (com.maddox.il2.objects.weapons.Gun)abulletemitter[j];
                        if(s.equals(gun.getHookName()))
                            return gun;
                    }

            }
        }

        return com.maddox.il2.objects.weapons.GunEmpty.get();
    }

    public com.maddox.il2.ai.BulletEmitter getBulletEmitterByHookName(java.lang.String s)
    {
        for(int i = 0; i < FM.CT.Weapons.length; i++)
        {
            com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[i];
            if(abulletemitter != null)
            {
                for(int j = 0; j < abulletemitter.length; j++)
                    if(s.equals(abulletemitter[j].getHookName()))
                        return abulletemitter[j];

            }
        }

        return com.maddox.il2.objects.weapons.GunEmpty.get();
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.Aircraft.moveGear(hierMesh(), f);
    }

    public void forceGear(float f)
    {
        moveGear(f);
    }

    public static void forceGear(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        try
        {
            java.lang.reflect.Method method = class1.getMethod("moveGear", new java.lang.Class[] {
                com.maddox.il2.engine.HierMesh.class, java.lang.Float.TYPE
            });
            method.invoke(null, new java.lang.Object[] {
                hiermesh, new Float(f)
            });
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void moveArrestorHook(float f)
    {
    }

    protected void moveWingFold(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
    }

    public void moveWingFold(float f)
    {
    }

    public void moveCockpitDoor(float f)
    {
    }

    protected void moveRudder(float f)
    {
    }

    protected void moveElevator(float f)
    {
    }

    protected void moveAileron(float f)
    {
    }

    protected void moveFlap(float f)
    {
    }

    protected void moveBayDoor(float f)
    {
    }

    protected void moveAirBrake(float f)
    {
    }

    public void moveSteering(float f)
    {
    }

    public void moveWheelSink()
    {
    }

    public void rareAction(float f, boolean flag)
    {
    }

    protected void moveFan(float f)
    {
        int i = 0;
        for(int j = 0; j < FM.EI.getNum(); j++)
        {
            if(oldProp[j] < 2)
            {
                i = java.lang.Math.abs((int)(FM.EI.engines[j].getw() * 0.06F));
                if(i >= 1)
                    i = 1;
                if(i != oldProp[j] && hierMesh().isChunkVisible(Props[j][oldProp[j]]))
                {
                    hierMesh().chunkVisible(Props[j][oldProp[j]], false);
                    oldProp[j] = i;
                    hierMesh().chunkVisible(Props[j][i], true);
                }
            }
            if(i == 0)
            {
                propPos[j] = (propPos[j] + 57.3F * FM.EI.engines[j].getw() * f) % 360F;
            } else
            {
                float f1 = 57.3F * FM.EI.engines[j].getw();
                f1 %= 2880F;
                f1 /= 2880F;
                if(f1 <= 0.5F)
                    f1 *= 2.0F;
                else
                    f1 = f1 * 2.0F - 2.0F;
                f1 *= 1200F;
                propPos[j] = (propPos[j] + f1 * f) % 360F;
            }
            hierMesh().chunkSetAngles(Props[j][0], 0.0F, -propPos[j], 0.0F);
        }

    }

    public void hitProp(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        if(i > FM.EI.getNum() - 1 || oldProp[i] == 2)
            return;
        super.hitProp(i, j, actor);
        FM.cut(part("Engine" + (i + 1)), j, actor);
        if(isChunkAnyDamageVisible("Prop" + (i + 1)) || isChunkAnyDamageVisible("PropRot" + (i + 1)))
        {
            hierMesh().chunkVisible(Props[i][0], false);
            hierMesh().chunkVisible(Props[i][1], false);
            hierMesh().chunkVisible(Props[i][2], true);
        }
        FM.EI.engines[i].setFricCoeffT(1.0F);
        oldProp[i] = 2;
    }

    public void updateLLights()
    {
        pos.getRender(com.maddox.il2.engine.Actor._tmpLoc);
        if(lLight == null)
        {
            if(com.maddox.il2.engine.Actor._tmpLoc.getX() < 1.0D)
                return;
            lLight = (new com.maddox.il2.engine.LightPointWorld[] {
                null, null, null, null
            });
            for(int i = 0; i < 4; i++)
            {
                lLight[i] = new LightPointWorld();
                lLight[i].setColor(0.4941176F, 0.9098039F, 0.9607843F);
                lLight[i].setEmit(0.0F, 0.0F);
                try
                {
                    lLightHook[i] = new HookNamed(this, "_LandingLight0" + i);
                }
                catch(java.lang.Exception exception) { }
            }

            return;
        }
        for(int j = 0; j < 4; j++)
            if(FM.AS.astateLandingLightEffects[j] != null)
            {
                lLightLoc1.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                lLightHook[j].computePos(this, com.maddox.il2.engine.Actor._tmpLoc, lLightLoc1);
                lLightLoc1.get(lLightP1);
                lLightLoc1.set(1000D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                lLightHook[j].computePos(this, com.maddox.il2.engine.Actor._tmpLoc, lLightLoc1);
                lLightLoc1.get(lLightP2);
                com.maddox.il2.engine.Engine.land();
                if(com.maddox.il2.engine.Landscape.rayHitHQ(lLightP1, lLightP2, lLightPL))
                {
                    lLightPL.z++;
                    lLightP2.interpolate(lLightP1, lLightPL, 0.95F);
                    lLight[j].setPos(lLightP2);
                    float f = (float)lLightP1.distance(lLightPL);
                    float f1 = f * 0.5F + 30F;
                    float f2 = 0.5F - (0.5F * f) / 1000F;
                    lLight[j].setEmit(f2, f1);
                } else
                {
                    lLight[j].setEmit(0.0F, 0.0F);
                }
            } else
            if(lLight[j].getR() != 0.0F)
                lLight[j].setEmit(0.0F, 0.0F);

    }

    public boolean isUnderWater()
    {
        com.maddox.JGP.Point3d point3d = pos.getAbsPoint();
        if(!com.maddox.il2.engine.Engine.land().isWater(point3d.x, point3d.y))
            return false;
        else
            return point3d.z < 0.0D;
    }

    public void update(float f)
    {
        super.update(f);
        if(this == com.maddox.il2.ai.World.getPlayerAircraft())
        {
            if(isUnderWater())
                com.maddox.il2.ai.World.doPlayerUnderWater();
            com.maddox.il2.ai.EventLog.flyPlayer(pos.getAbsPoint());
            if(this instanceof com.maddox.il2.objects.air.TypeBomber)
                ((com.maddox.il2.objects.air.TypeBomber)this).typeBomberUpdate(f);
        }
        com.maddox.il2.fm.Controls controls = FM.CT;
        moveFan(f);
        if(controls.bHasGearControl)
        {
            float f1 = controls.getGear();
            if(java.lang.Math.abs(Gear_ - f1) > EpsSmooth_)
            {
                if(!(this instanceof com.maddox.il2.objects.air.I_16))
                    if(java.lang.Math.abs(f1 - controls.GearControl) <= EpsSmooth_)
                        sfxGear(false);
                    else
                        sfxGear(true);
                moveGear(Gear_ = f1);
            }
        }
        if(controls.bHasArrestorControl)
        {
            float f2 = controls.getArrestor();
            if(java.lang.Math.abs(arrestor_ - f2) > EpsSmooth_)
                moveArrestorHook(arrestor_ = f2);
        }
        if(controls.bHasWingControl)
        {
            float f3 = controls.getWing();
            if(java.lang.Math.abs(wingfold_ - f3) > EpsVerySmooth_)
                moveWingFold(wingfold_ = f3);
        }
        if(controls.bHasCockpitDoorControl)
        {
            float f4 = controls.getCockpitDoor();
            if(java.lang.Math.abs(cockpitDoor_ - f4) > EpsVerySmooth_)
                moveCockpitDoor(cockpitDoor_ = f4);
        }
        if(controls.bHasFlapsControl)
        {
            float f5 = controls.getFlap();
            if(java.lang.Math.abs(Flap_ - f5) > EpsSmooth_)
            {
                if(java.lang.Math.abs(f5 - controls.FlapsControl) <= EpsSmooth_)
                    sfxFlaps(false);
                else
                    sfxFlaps(true);
                moveFlap(Flap_ = f5);
            }
        }
        float f6 = controls.getRudder();
        if(java.lang.Math.abs(Rudder_ - f6) > EpsCoarse_)
            moveRudder(Rudder_ = f6);
        f6 = controls.getElevator();
        if(java.lang.Math.abs(Elevator_ - f6) > EpsCoarse_)
            moveElevator(Elevator_ = f6);
        f6 = controls.getAileron();
        if(java.lang.Math.abs(Aileron_ - f6) > EpsCoarse_)
            moveAileron(Aileron_ = f6);
        f6 = controls.getBayDoor();
        if(java.lang.Math.abs(BayDoor_ - f6) > 0.025F)
        {
            BayDoor_ += 0.025F * (f6 <= BayDoor_ ? -1F : 2.0F);
            moveBayDoor(BayDoor_);
        }
        f6 = controls.getAirBrake();
        if(java.lang.Math.abs(AirBrake_ - f6) > EpsSmooth_)
        {
            moveAirBrake(AirBrake_ = f6);
            if(java.lang.Math.abs(AirBrake_ - 0.5F) >= 0.48F)
                sfxAirBrake();
        }
        f6 = FM.Gears.getSteeringAngle();
        if(java.lang.Math.abs(Steering_ - f6) > EpsSmooth_)
            moveSteering(Steering_ = f6);
        if(FM.Gears.nearGround())
            moveWheelSink();
    }

    public void setFM(int i, boolean flag)
    {
        setFM(com.maddox.rts.Property.stringValue(getClass(), "FlightModel", null), i, flag);
    }

    public void setFM(java.lang.String s, int i, boolean flag)
    {
        if(this instanceof com.maddox.il2.objects.air.JU_88MSTL)
            i = 1;
        switch(i)
        {
        case 0: // '\0'
        default:
            FM = new Pilot(s);
            break;

        case 1: // '\001'
            FM = new RealFlightModel(s);
            break;

        case 2: // '\002'
            FM = new FlightModel(s);
            FM.AP = new Autopilotage();
            break;
        }
        FM.actor = this;
        FM.AS.set(this, flag && !com.maddox.il2.net.NetMissionTrack.isPlaying());
        FM.EI.setNotMirror(flag && !com.maddox.il2.net.NetMissionTrack.isPlaying());
        com.maddox.rts.SectFile sectfile = com.maddox.il2.fm.FlightModelMain.sectFile(s);
        byte byte0 = 0;
        java.lang.String s1 = sectfile.get("SOUND", "FeedType", "PNEUMATIC");
        if(s1.compareToIgnoreCase("PNEUMATIC") == 0)
            byte0 = 0;
        else
        if(s1.compareToIgnoreCase("ELECTRIC") == 0)
            byte0 = 1;
        else
        if(s1.compareToIgnoreCase("HYDRAULIC") == 0)
            byte0 = 2;
        else
            java.lang.System.out.println("ERROR: Invalid feed type" + s1);
        FM.set(hierMesh());
        com.maddox.il2.objects.air.Aircraft.forceGear(getClass(), hierMesh(), 1.0F);
        FM.Gears.computePlaneLandPose(FM);
        com.maddox.il2.objects.air.Aircraft.forceGear(getClass(), hierMesh(), 0.0F);
        FM.EI.set(this);
        initSound(sectfile);
        sfxInit(byte0);
        interpPut(FM, "FlightModel", com.maddox.rts.Time.current(), null);
    }

    public void checkTurretSkill()
    {
    }

    public void destroy()
    {
        if(isAlive() && com.maddox.il2.game.Mission.isPlaying() && name().charAt(0) != ' ')
        {
            com.maddox.il2.ai.Front.checkAircraftCaptured(this);
            com.maddox.il2.ai.World.onActorDied(this, com.maddox.il2.ai.World.remover);
        }
        if(lLight != null)
        {
            for(int i = 0; i < 4; i++)
                com.maddox.rts.ObjState.destroy(lLight[i]);

        }
        if(com.maddox.il2.ai.World.getPlayerAircraft() == this)
            deleteCockpits();
        com.maddox.il2.ai.Wing wing = getWing();
        if(com.maddox.il2.engine.Actor.isValid(wing) && (wing instanceof com.maddox.il2.net.NetWing))
            wing.destroy();
        detachGun(-1);
        super.destroy();
        if(com.maddox.il2.ai.World.getPlayerAircraft() == this)
            com.maddox.il2.ai.World.setPlayerAircraft(null);
        _removeMesh();
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public Aircraft()
    {
        timePostEndAction = -1L;
        buried = false;
        EpsCoarse_ = 0.03F;
        EpsSmooth_ = 0.003F;
        EpsVerySmooth_ = 0.0005F;
        BayDoor_ = 0.0F;
        AirBrake_ = 0.0F;
        Steering_ = 0.0F;
        wingfold_ = 0.0F;
        cockpitDoor_ = 0.0F;
        arrestor_ = 0.0F;
        typedName = "UNKNOWN";
        checkLoadingCountry();
        if(_loadingCountry == null)
            _setMesh(com.maddox.rts.Property.stringValue(getClass(), "meshName", null));
        else
            _setMesh(com.maddox.rts.Property.stringValue(getClass(), "meshName_" + _loadingCountry, null));
        collide(true);
        drawing(true);
        dreamFire(true);
    }

    private void checkLoadingCountry()
    {
        _loadingCountry = null;
        if(com.maddox.il2.objects.air.NetAircraft.loadingCountry == null)
            return;
        java.lang.Class class1 = getClass();
        if(com.maddox.rts.Property.value(class1, "PaintScheme_" + com.maddox.il2.objects.air.NetAircraft.loadingCountry) != null && com.maddox.rts.Property.stringValue(class1, "meshName_" + com.maddox.il2.objects.air.NetAircraft.loadingCountry, null) != null)
            _loadingCountry = com.maddox.il2.objects.air.NetAircraft.loadingCountry;
    }

    public static java.lang.String getPropertyMeshDemo(java.lang.Class class1, java.lang.String s)
    {
        java.lang.String s1 = "meshNameDemo";
        java.lang.String s2 = com.maddox.rts.Property.stringValue(class1, s1, (java.lang.String)null);
        if(s2 != null)
            return s2;
        else
            return com.maddox.il2.objects.air.Aircraft.getPropertyMesh(class1, s);
    }

    public static java.lang.String getPropertyMesh(java.lang.Class class1, java.lang.String s)
    {
        java.lang.String s1 = "meshName";
        java.lang.String s2 = null;
        if(s != null)
            s2 = com.maddox.rts.Property.stringValue(class1, s1 + "_" + s, null);
        if(s2 == null)
            s2 = com.maddox.rts.Property.stringValue(class1, s1);
        return s2;
    }

    public static com.maddox.il2.objects.air.PaintScheme getPropertyPaintScheme(java.lang.Class class1, java.lang.String s)
    {
        java.lang.String s1 = "PaintScheme";
        com.maddox.il2.objects.air.PaintScheme paintscheme = null;
        if(s != null)
            paintscheme = (com.maddox.il2.objects.air.PaintScheme)com.maddox.rts.Property.value(class1, s1 + "_" + s, null);
        if(paintscheme == null)
            paintscheme = (com.maddox.il2.objects.air.PaintScheme)com.maddox.rts.Property.value(class1, s1);
        return paintscheme;
    }

    public java.lang.String typedName()
    {
        return typedName;
    }

    private void correctTypedName()
    {
        if(typedName != null && typedName.indexOf('_') >= 0)
        {
            java.lang.StringBuffer stringbuffer = new StringBuffer();
            int i = typedName.length();
            for(int j = 0; j < i; j++)
            {
                char c = typedName.charAt(j);
                if(c != '_')
                    stringbuffer.append(c);
            }

            typedName = stringbuffer.toString();
        }
    }

    public void preparePaintScheme()
    {
        com.maddox.il2.objects.air.PaintScheme paintscheme = com.maddox.il2.objects.air.Aircraft.getPropertyPaintScheme(getClass(), _loadingCountry);
        if(paintscheme != null)
        {
            paintscheme.prepare(this, bPaintShemeNumberOn);
            typedName = paintscheme.typedName(this);
            correctTypedName();
        }
    }

    public void preparePaintScheme(int i)
    {
        com.maddox.il2.objects.air.PaintScheme paintscheme = com.maddox.il2.objects.air.Aircraft.getPropertyPaintScheme(getClass(), _loadingCountry);
        if(paintscheme != null)
        {
            paintscheme.prepareNum(this, i, bPaintShemeNumberOn);
            typedName = paintscheme.typedNameNum(this, i);
            correctTypedName();
        }
    }

    public void prepareCamouflage()
    {
        java.lang.String s = com.maddox.il2.objects.air.Aircraft.getPropertyMesh(getClass(), _loadingCountry);
        com.maddox.il2.objects.air.Aircraft.prepareMeshCamouflage(s, hierMesh());
    }

    public static void prepareMeshCamouflage(java.lang.String s, com.maddox.il2.engine.HierMesh hiermesh)
    {
        com.maddox.il2.objects.air.Aircraft.prepareMeshCamouflage(s, hiermesh, null);
    }

    public static void prepareMeshCamouflage(java.lang.String s, com.maddox.il2.engine.HierMesh hiermesh, java.lang.String s1)
    {
        com.maddox.il2.objects.air.Aircraft.prepareMeshCamouflage(s, hiermesh, s1, null);
    }

    public static void prepareMeshCamouflage(java.lang.String s, com.maddox.il2.engine.HierMesh hiermesh, java.lang.String s1, com.maddox.il2.engine.Mat amat[])
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        java.lang.String s2 = s.substring(0, s.lastIndexOf('/') + 1);
        if(s1 == null)
        {
            java.lang.String s3;
            switch(com.maddox.il2.ai.World.cur().camouflage)
            {
            case 0: // '\0'
                s3 = "summer";
                break;

            case 1: // '\001'
                s3 = "winter";
                break;

            case 2: // '\002'
                s3 = "desert";
                break;

            default:
                s3 = "summer";
                break;
            }
            if(!com.maddox.il2.objects.air.Aircraft.existSFSFile(s2 + s3 + "/skin1o.tga"))
            {
                s3 = "summer";
                if(!com.maddox.il2.objects.air.Aircraft.existSFSFile(s2 + s3 + "/skin1o.tga"))
                    return;
            }
            s1 = s2 + s3;
        }
        java.lang.String as[] = {
            s1 + "/skin1o.tga", s1 + "/skin1p.tga", s1 + "/skin1q.tga"
        };
        int ai[] = new int[4];
        for(int i = 0; i < _skinMat.length; i++)
        {
            int j = hiermesh.materialFind(_skinMat[i]);
            if(j >= 0)
            {
                com.maddox.il2.engine.Mat mat = hiermesh.material(j);
                boolean flag = false;
                for(int k = 0; k < 4; k++)
                {
                    ai[k] = -1;
                    if(mat.isValidLayer(k))
                    {
                        mat.setLayer(k);
                        java.lang.String s4 = mat.get('\0');
                        for(int l = 0; l < 3; l++)
                        {
                            if(!s4.regionMatches(true, s4.length() - 10, _curSkin[l], 0, 10))
                                continue;
                            ai[k] = l;
                            flag = true;
                            break;
                        }

                    }
                }

                if(flag)
                {
                    java.lang.String s5 = s1 + "/" + _skinMat[i] + ".mat";
                    com.maddox.il2.engine.Mat mat1;
                    if(com.maddox.il2.engine.FObj.Exist(s5))
                    {
                        mat1 = (com.maddox.il2.engine.Mat)com.maddox.il2.engine.FObj.Get(s5);
                    } else
                    {
                        mat1 = (com.maddox.il2.engine.Mat)mat.Clone();
                        mat1.Rename(s5);
                        for(int i1 = 0; i1 < 4; i1++)
                            if(ai[i1] >= 0)
                            {
                                mat1.setLayer(i1);
                                mat1.set('\0', as[ai[i1]]);
                            }

                    }
                    if(amat != null)
                    {
                        for(int j1 = 0; j1 < 4; j1++)
                            if(ai[j1] >= 0)
                                amat[ai[j1]] = mat1;

                    }
                    hiermesh.materialReplace(_skinMat[i], mat1);
                }
            }
        }

    }

    public static void prepareMeshSkin(java.lang.String s, com.maddox.il2.engine.HierMesh hiermesh, java.lang.String s1, java.lang.String s2)
    {
        java.lang.String s3 = s;
        int i = s3.lastIndexOf('/');
        if(i >= 0)
            s3 = s3.substring(0, i + 1) + "summer";
        else
            s3 = s3 + "summer";
        try
        {
            java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s2, 0));
            if(!file.isDirectory())
                file.mkdir();
        }
        catch(java.lang.Exception exception)
        {
            return;
        }
        if(!com.maddox.il2.engine.BmpUtils.bmp8PalTo4TGA4(s1, s3, s2))
            return;
        if(s2 == null)
        {
            return;
        } else
        {
            com.maddox.il2.objects.air.Aircraft.prepareMeshCamouflage(s, hiermesh, s2, null);
            return;
        }
    }

    public static void prepareMeshPilot(com.maddox.il2.engine.HierMesh hiermesh, int i, java.lang.String s, java.lang.String s1)
    {
        com.maddox.il2.objects.air.Aircraft.prepareMeshPilot(hiermesh, i, s, s1, null);
    }

    public static void prepareMeshPilot(com.maddox.il2.engine.HierMesh hiermesh, int i, java.lang.String s, java.lang.String s1, com.maddox.il2.engine.Mat amat[])
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        java.lang.String s2 = "Pilot" + (1 + i);
        int j = hiermesh.materialFind(s2);
        if(j < 0)
            return;
        com.maddox.il2.engine.Mat mat;
        if(com.maddox.il2.engine.FObj.Exist(s))
        {
            mat = (com.maddox.il2.engine.Mat)com.maddox.il2.engine.FObj.Get(s);
        } else
        {
            com.maddox.il2.engine.Mat mat1 = hiermesh.material(j);
            mat = (com.maddox.il2.engine.Mat)mat1.Clone();
            mat.Rename(s);
            mat.setLayer(0);
            mat.set('\0', s1);
        }
        if(amat != null)
            amat[0] = mat;
        hiermesh.materialReplace(s2, mat);
    }

    public static void prepareMeshNoseart(com.maddox.il2.engine.HierMesh hiermesh, java.lang.String s, java.lang.String s1, java.lang.String s2, java.lang.String s3)
    {
        com.maddox.il2.objects.air.Aircraft.prepareMeshNoseart(hiermesh, s, s1, s2, s3, null);
    }

    public static void prepareMeshNoseart(com.maddox.il2.engine.HierMesh hiermesh, java.lang.String s, java.lang.String s1, java.lang.String s2, java.lang.String s3, com.maddox.il2.engine.Mat amat[])
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        java.lang.String s4 = "Overlay9";
        int i = hiermesh.materialFind(s4);
        if(i < 0)
            return;
        com.maddox.il2.engine.Mat mat;
        if(com.maddox.il2.engine.FObj.Exist(s))
        {
            mat = (com.maddox.il2.engine.Mat)com.maddox.il2.engine.FObj.Get(s);
        } else
        {
            com.maddox.il2.engine.Mat mat1 = hiermesh.material(i);
            mat = (com.maddox.il2.engine.Mat)mat1.Clone();
            mat.Rename(s);
            mat.setLayer(0);
            mat.set('\0', s2);
        }
        if(amat != null)
            amat[0] = mat;
        hiermesh.materialReplace(s4, mat);
        s4 = "OverlayA";
        i = hiermesh.materialFind(s4);
        if(i < 0)
            return;
        if(com.maddox.il2.engine.FObj.Exist(s1))
        {
            mat = (com.maddox.il2.engine.Mat)com.maddox.il2.engine.FObj.Get(s1);
        } else
        {
            com.maddox.il2.engine.Mat mat2 = hiermesh.material(i);
            mat = (com.maddox.il2.engine.Mat)mat2.Clone();
            mat.Rename(s1);
            mat.setLayer(0);
            mat.set('\0', s3);
        }
        if(amat != null)
            amat[1] = mat;
        hiermesh.materialReplace(s4, mat);
    }

    private static boolean existSFSFile(java.lang.String s)
    {
        com.maddox.rts.SFSInputStream sfsinputstream = new SFSInputStream(s);
        sfsinputstream.close();
        return true;
        java.lang.Exception exception;
        exception;
        return false;
    }

    public double getSpeed(com.maddox.JGP.Vector3d vector3d)
    {
        if(FM == null)
        {
            if(vector3d != null)
                vector3d.set(0.0D, 0.0D, 0.0D);
            return 0.0D;
        }
        if(vector3d != null)
            vector3d.set(FM.Vwld);
        return FM.Vwld.length();
    }

    public void setSpeed(com.maddox.JGP.Vector3d vector3d)
    {
        super.setSpeed(vector3d);
        FM.Vwld.set(vector3d);
    }

    public void setOnGround(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, com.maddox.JGP.Vector3d vector3d)
    {
        FM.CT.setLanded();
        com.maddox.il2.objects.air.Aircraft.forceGear(getClass(), hierMesh(), FM.CT.getGear());
        if(point3d != null && orient != null)
        {
            pos.setAbs(point3d, orient);
            pos.reset();
        }
        if(vector3d != null)
            setSpeed(vector3d);
    }

    public void load(com.maddox.rts.SectFile sectfile, java.lang.String s, int i, com.maddox.rts.NetChannel netchannel, int j)
        throws java.lang.Exception
    {
        if(this == com.maddox.il2.ai.World.getPlayerAircraft())
        {
            setFM(1, true);
            com.maddox.il2.ai.World.setPlayerFM();
        } else
        if(netchannel != null)
            setFM(2, false);
        else
            setFM(0, true);
        if(sectfile.exist(s, "Skill" + i))
            FM.setSkill(sectfile.get(s, "Skill" + i, 1));
        else
            FM.setSkill(sectfile.get(s, "Skill", 1));
        FM.M.fuel = sectfile.get(s, "Fuel", 100F, 0.0F, 100F) * 0.01F * FM.M.maxFuel;
        if(sectfile.exist(s, "numberOn" + i))
            bPaintShemeNumberOn = sectfile.get(s, "numberOn" + i, 1, 0, 1) == 1;
        FM.AS.bIsEnableToBailout = sectfile.get(s, "Parachute", 1, 0, 1) == 1;
        if(com.maddox.il2.game.Mission.isServer())
            createNetObject(null, 0);
        else
        if(netchannel != null)
            createNetObject(netchannel, j);
        if(net != null)
        {
            ((com.maddox.il2.objects.air.NetAircraft.AircraftNet)net).netName = name();
            ((com.maddox.il2.objects.air.NetAircraft.AircraftNet)net).netUser = null;
        }
        java.lang.String s1 = s + "_weapons";
        int k = sectfile.sectionIndex(s1);
        if(k >= 0)
        {
            int l = sectfile.vars(k);
            for(int i1 = 0; i1 < l; i1++)
            {
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(k, i1));
                int k1 = numbertokenizer.next(9, 0, 19);
                java.lang.String s2 = numbertokenizer.next();
                java.lang.String s3 = numbertokenizer.next();
                java.lang.Class class1 = com.maddox.rts.ObjIO.classForName("weapons." + s3);
                java.lang.Object obj = class1.newInstance();
                if(obj instanceof com.maddox.il2.ai.BulletEmitter)
                {
                    com.maddox.il2.ai.BulletEmitter bulletemitter = (com.maddox.il2.ai.BulletEmitter)obj;
                    bulletemitter.set(this, s2, com.maddox.il2.objects.air.Aircraft.dumpName(s2));
                    int j1 = numbertokenizer.next(-12345);
                    if(j1 == -12345)
                        bulletemitter.loadBullets();
                    else
                        bulletemitter._loadBullets(j1);
                    addGun(bulletemitter, k1);
                }
            }

        } else
        {
            thisWeaponsName = sectfile.get(s, "weapons", (java.lang.String)null);
            if(thisWeaponsName != null)
                com.maddox.il2.objects.air.Aircraft.weaponsLoad(this, thisWeaponsName);
        }
        if(this == com.maddox.il2.ai.World.getPlayerAircraft())
            createCockpits();
        onAircraftLoaded();
    }

    private static java.lang.String dumpName(java.lang.String s)
    {
        int i;
        for(i = s.length() - 1; i >= 0; i--)
            if(!java.lang.Character.isDigit(s.charAt(i)))
                break;

        i++;
        return s.substring(0, i) + "Dump" + s.substring(i);
    }

    public boolean turretAngles(int i, float af[])
    {
        for(int j = 0; j < 2; j++)
        {
            af[j] = (af[j] + 3600F) % 360F;
            if(af[j] > 180F)
                af[j] -= 360F;
        }

        af[2] = 0.0F;
        return true;
    }

    public int WeaponsMask()
    {
        return -1;
    }

    public int HitbyMask()
    {
        return FM.Vwld.length() >= 2D ? -25 : -1;
    }

    public int chooseBulletType(com.maddox.il2.engine.BulletProperties abulletproperties[])
    {
        if(FM.isTakenMortalDamage())
            return -1;
        if(abulletproperties.length == 1)
            return 0;
        if(abulletproperties.length <= 0)
            return -1;
        if(abulletproperties[0].power <= 0.0F)
            return 1;
        if(abulletproperties[1].power <= 0.0F)
            return 0;
        if(abulletproperties[0].powerType == 1)
            return 0;
        if(abulletproperties[1].powerType == 1)
            return 1;
        if(abulletproperties[0].powerType == 0)
            return 0;
        if(abulletproperties[1].powerType == 0)
            return 1;
        return abulletproperties[0].powerType != 2 ? 0 : 1;
    }

    public int chooseShotpoint(com.maddox.il2.engine.BulletProperties bulletproperties)
    {
        return !FM.isTakenMortalDamage() ? 0 : -1;
    }

    public boolean getShotpointOffset(int i, com.maddox.JGP.Point3d point3d)
    {
        if(FM.isTakenMortalDamage())
            return false;
        if(i != 0)
            return false;
        if(point3d != null)
            point3d.set(0.0D, 0.0D, 0.0D);
        return true;
    }

    public float AttackMaxDistance()
    {
        return 1500F;
    }

    private static int[] getSwTbl(int i)
    {
        if(i < 0)
            i = -i;
        int j = i % 16 + 11;
        int k = i % com.maddox.rts.Finger.kTable.length;
        if(j < 0)
            j = -j % 16;
        if(j < 10)
            j = 10;
        if(k < 0)
            k = -k % com.maddox.rts.Finger.kTable.length;
        int ai[] = new int[j];
        for(int l = 0; l < j; l++)
            ai[l] = com.maddox.rts.Finger.kTable[(k + l) % com.maddox.rts.Finger.kTable.length];

        return ai;
    }

    public static void weapons(java.lang.Class class1)
    {
        try
        {
            int i = com.maddox.rts.Finger.Int("ce" + class1.getName() + "vd");
            java.io.BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new KryptoInputFilter(new SFSInputStream(com.maddox.rts.Finger.LongFN(0L, "cod/" + com.maddox.rts.Finger.incInt(i, "adt"))), com.maddox.il2.objects.air.Aircraft.getSwTbl(i))));
            java.util.ArrayList arraylist = com.maddox.il2.objects.air.Aircraft.weaponsListProperty(class1);
            com.maddox.util.HashMapInt hashmapint = com.maddox.il2.objects.air.Aircraft.weaponsMapProperty(class1);
            do
            {
                java.lang.String s = bufferedreader.readLine();
                if(s == null)
                    break;
                java.util.StringTokenizer stringtokenizer = new StringTokenizer(s, ",");
                int j = stringtokenizer.countTokens() - 1;
                java.lang.String s1 = stringtokenizer.nextToken();
                com.maddox.il2.objects.air._WeaponSlot a_lweaponslot[] = new com.maddox.il2.objects.air._WeaponSlot[j];
                for(int k = 0; k < j; k++)
                {
                    java.lang.String s2 = stringtokenizer.nextToken();
                    if(s2 != null && s2.length() > 3)
                    {
                        com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(s2);
                        a_lweaponslot[k] = new _WeaponSlot(numbertokenizer.next(0), numbertokenizer.next(null), numbertokenizer.next(-12345));
                    }
                }

                arraylist.add(s1);
                hashmapint.put(com.maddox.rts.Finger.Int(s1), a_lweaponslot);
            } while(true);
            bufferedreader.close();
        }
        catch(java.lang.Exception exception) { }
    }

    public long finger(long l)
    {
        java.lang.Class class1 = getClass();
        l = com.maddox.il2.fm.FlightModelMain.finger(l, com.maddox.rts.Property.stringValue(class1, "FlightModel", null));
        l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.stringValue(class1, "meshName", null));
        java.lang.Object obj = com.maddox.rts.Property.value(class1, "cockpitClass", null);
        if(obj != null)
            if(obj instanceof java.lang.Class)
            {
                l = com.maddox.rts.Finger.incLong(l, ((java.lang.Class)obj).getName());
            } else
            {
                java.lang.Class aclass[] = (java.lang.Class[])obj;
                for(int j = 0; j < aclass.length; j++)
                    l = com.maddox.rts.Finger.incLong(l, aclass[j].getName());

            }
        for(int i = 0; i < FM.CT.Weapons.length; i++)
        {
            com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[i];
            if(abulletemitter != null)
            {
                for(int k = 0; k < abulletemitter.length; k++)
                {
                    com.maddox.il2.ai.BulletEmitter bulletemitter = abulletemitter[k];
                    l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.intValue(bulletemitter, "_count", 0));
                    if(bulletemitter instanceof com.maddox.il2.objects.weapons.Gun)
                    {
                        com.maddox.il2.engine.GunProperties gunproperties = ((com.maddox.il2.objects.weapons.Gun)bulletemitter).prop;
                        l = com.maddox.rts.Finger.incLong(l, gunproperties.shotFreq);
                        l = com.maddox.rts.Finger.incLong(l, gunproperties.shotFreqDeviation);
                        l = com.maddox.rts.Finger.incLong(l, gunproperties.maxDeltaAngle);
                        l = com.maddox.rts.Finger.incLong(l, gunproperties.bullets);
                        com.maddox.il2.engine.BulletProperties abulletproperties[] = gunproperties.bullet;
                        if(abulletproperties != null)
                        {
                            for(int i1 = 0; i1 < abulletproperties.length; i1++)
                            {
                                l = com.maddox.rts.Finger.incLong(l, abulletproperties[i1].massa);
                                l = com.maddox.rts.Finger.incLong(l, abulletproperties[i1].kalibr);
                                l = com.maddox.rts.Finger.incLong(l, abulletproperties[i1].speed);
                                l = com.maddox.rts.Finger.incLong(l, abulletproperties[i1].cumulativePower);
                                l = com.maddox.rts.Finger.incLong(l, abulletproperties[i1].power);
                                l = com.maddox.rts.Finger.incLong(l, abulletproperties[i1].powerType);
                                l = com.maddox.rts.Finger.incLong(l, abulletproperties[i1].powerRadius);
                                l = com.maddox.rts.Finger.incLong(l, abulletproperties[i1].timeLife);
                            }

                        }
                    } else
                    if(bulletemitter instanceof com.maddox.il2.objects.weapons.RocketGun)
                    {
                        com.maddox.il2.objects.weapons.RocketGun rocketgun = (com.maddox.il2.objects.weapons.RocketGun)bulletemitter;
                        java.lang.Class class2 = (java.lang.Class)com.maddox.rts.Property.value(rocketgun.getClass(), "bulletClass", null);
                        l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.intValue(rocketgun.getClass(), "bullets", 1));
                        l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.floatValue(rocketgun.getClass(), "shotFreq", 0.5F));
                        if(class2 != null)
                        {
                            l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.floatValue(class2, "radius", 1.0F));
                            l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.floatValue(class2, "timeLife", 1.0F));
                            l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.floatValue(class2, "timeFire", 1.0F));
                            l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.floatValue(class2, "force", 1.0F));
                            l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.floatValue(class2, "power", 1.0F));
                            l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.intValue(class2, "powerType", 1));
                            l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.floatValue(class2, "kalibr", 1.0F));
                            l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.floatValue(class2, "massa", 1.0F));
                            l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.floatValue(class2, "massaEnd", 1.0F));
                        }
                    } else
                    if(bulletemitter instanceof com.maddox.il2.objects.weapons.BombGun)
                    {
                        com.maddox.il2.objects.weapons.BombGun bombgun = (com.maddox.il2.objects.weapons.BombGun)bulletemitter;
                        java.lang.Class class3 = (java.lang.Class)com.maddox.rts.Property.value(bombgun.getClass(), "bulletClass", null);
                        l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.intValue(bombgun.getClass(), "bullets", 1));
                        l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.floatValue(bombgun.getClass(), "shotFreq", 0.5F));
                        if(class3 != null)
                        {
                            l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.floatValue(class3, "radius", 1.0F));
                            l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.floatValue(class3, "power", 1.0F));
                            l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.intValue(class3, "powerType", 1));
                            l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.floatValue(class3, "kalibr", 1.0F));
                            l = com.maddox.rts.Finger.incLong(l, com.maddox.rts.Property.floatValue(class3, "massa", 1.0F));
                        }
                    }
                }

            }
        }

        return l;
    }

    protected static void weaponTriggersRegister(java.lang.Class class1, int ai[])
    {
        com.maddox.rts.Property.set(class1, "weaponTriggers", ai);
    }

    public static int[] getWeaponTriggersRegistered(java.lang.Class class1)
    {
        return (int[])com.maddox.rts.Property.value(class1, "weaponTriggers", null);
    }

    protected static void weaponHooksRegister(java.lang.Class class1, java.lang.String as[])
    {
        if(as.length != com.maddox.il2.objects.air.Aircraft.getWeaponTriggersRegistered(class1).length)
        {
            throw new RuntimeException("Sizeof 'weaponHooks' != sizeof 'weaponTriggers'");
        } else
        {
            com.maddox.rts.Property.set(class1, "weaponHooks", as);
            return;
        }
    }

    public static java.lang.String[] getWeaponHooksRegistered(java.lang.Class class1)
    {
        return (java.lang.String[])com.maddox.rts.Property.value(class1, "weaponHooks", null);
    }

    protected static void weaponsRegister(java.lang.Class class1, java.lang.String s, java.lang.String as[])
    {
    }

    protected static void weaponsUnRegister(java.lang.Class class1, java.lang.String s)
    {
        java.util.ArrayList arraylist = com.maddox.il2.objects.air.Aircraft.weaponsListProperty(class1);
        com.maddox.util.HashMapInt hashmapint = com.maddox.il2.objects.air.Aircraft.weaponsMapProperty(class1);
        int i = arraylist.indexOf(s);
        if(i < 0)
        {
            return;
        } else
        {
            arraylist.remove(i);
            hashmapint.remove(com.maddox.rts.Finger.Int(s));
            return;
        }
    }

    public static java.lang.String[] getWeaponsRegistered(java.lang.Class class1)
    {
        java.util.ArrayList arraylist = com.maddox.il2.objects.air.Aircraft.weaponsListProperty(class1);
        com.maddox.util.HashMapInt hashmapint = com.maddox.il2.objects.air.Aircraft.weaponsMapProperty(class1);
        java.lang.String as[] = new java.lang.String[arraylist.size()];
        for(int i = 0; i < as.length; i++)
            as[i] = (java.lang.String)arraylist.get(i);

        return as;
    }

    public static com.maddox.il2.objects.air._WeaponSlot[] getWeaponSlotsRegistered(java.lang.Class class1, java.lang.String s)
    {
        com.maddox.util.HashMapInt hashmapint = com.maddox.il2.objects.air.Aircraft.weaponsMapProperty(class1);
        return (com.maddox.il2.objects.air._WeaponSlot[])hashmapint.get(com.maddox.rts.Finger.Int(s));
    }

    public static boolean weaponsExist(java.lang.Class class1, java.lang.String s)
    {
        java.lang.Object obj = com.maddox.rts.Property.value(class1, "weaponsMap", null);
        if(obj == null)
        {
            return false;
        } else
        {
            com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)obj;
            int i = com.maddox.rts.Finger.Int(s);
            return hashmapint.containsKey(i);
        }
    }

    protected void weaponsLoad(java.lang.String s)
        throws java.lang.Exception
    {
        com.maddox.il2.objects.air.Aircraft.weaponsLoad(this, s);
    }

    protected static void weaponsLoad(com.maddox.il2.objects.air.Aircraft aircraft, java.lang.String s)
        throws java.lang.Exception
    {
        java.lang.Class class1 = aircraft.getClass();
        com.maddox.util.HashMapInt hashmapint = com.maddox.il2.objects.air.Aircraft.weaponsMapProperty(class1);
        int i = com.maddox.rts.Finger.Int(s);
        if(!hashmapint.containsKey(i))
        {
            throw new RuntimeException("Weapon set '" + s + "' not registered in " + com.maddox.rts.ObjIO.classGetName(class1));
        } else
        {
            com.maddox.il2.objects.air.Aircraft.weaponsLoad(aircraft, i, hashmapint);
            return;
        }
    }

    protected static void weaponsLoad(com.maddox.il2.objects.air.Aircraft aircraft, int i)
        throws java.lang.Exception
    {
        com.maddox.util.HashMapInt hashmapint = com.maddox.il2.objects.air.Aircraft.weaponsMapProperty(aircraft.getClass());
        if(!hashmapint.containsKey(i))
        {
            throw new RuntimeException("Weapon set '" + i + "' not registered in " + com.maddox.rts.ObjIO.classGetName(aircraft.getClass()));
        } else
        {
            com.maddox.il2.objects.air.Aircraft.weaponsLoad(aircraft, i, hashmapint);
            return;
        }
    }

    protected static void weaponsLoad(com.maddox.il2.objects.air.Aircraft aircraft, int i, com.maddox.util.HashMapInt hashmapint)
        throws java.lang.Exception
    {
        java.lang.String as[] = com.maddox.il2.objects.air.Aircraft.getWeaponHooksRegistered(aircraft.getClass());
        com.maddox.il2.objects.air._WeaponSlot a_lweaponslot[] = (com.maddox.il2.objects.air._WeaponSlot[])hashmapint.get(i);
        for(int j = 0; j < as.length; j++)
            if(a_lweaponslot[j] != null)
                if(aircraft.mesh().hookFind(as[j]) != -1)
                {
                    com.maddox.il2.ai.BulletEmitter bulletemitter = (com.maddox.il2.ai.BulletEmitter)a_lweaponslot[j].clazz.newInstance();
                    bulletemitter.set(aircraft, as[j], com.maddox.il2.objects.air.Aircraft.dumpName(as[j]));
                    if(aircraft.isNet() && aircraft.isNetMirror())
                    {
                        if(!com.maddox.il2.ai.World.cur().diffCur.Limited_Ammo)
                            bulletemitter.loadBullets(-1);
                        else
                        if(a_lweaponslot[j].trigger == 2 || a_lweaponslot[j].trigger == 3 || a_lweaponslot[j].trigger >= 10)
                        {
                            if(a_lweaponslot[j].bullets == -12345)
                                bulletemitter.loadBullets();
                            else
                                bulletemitter._loadBullets(a_lweaponslot[j].bullets);
                        } else
                        {
                            bulletemitter.loadBullets(-1);
                        }
                    } else
                    if(a_lweaponslot[j].bullets == -12345)
                        bulletemitter.loadBullets();
                    else
                        bulletemitter.loadBullets(a_lweaponslot[j].bullets);
                    aircraft.addGun(bulletemitter, a_lweaponslot[j].trigger);
                    com.maddox.rts.Property.set(bulletemitter, "_count", a_lweaponslot[j].bullets);
                    switch(a_lweaponslot[j].trigger)
                    {
                    case 0: // '\0'
                        if(bulletemitter instanceof com.maddox.il2.objects.weapons.MGunAircraftGeneric)
                            if(com.maddox.il2.ai.World.getPlayerAircraft() == aircraft)
                                ((com.maddox.il2.objects.weapons.MGunAircraftGeneric)bulletemitter).setConvDistance(com.maddox.il2.ai.World.cur().userCoverMashineGun, com.maddox.rts.Property.floatValue(aircraft.getClass(), "LOSElevation", 0.75F));
                            else
                            if(aircraft.isNet() && aircraft.isNetPlayer())
                                ((com.maddox.il2.objects.weapons.MGunAircraftGeneric)bulletemitter).setConvDistance(400F, com.maddox.rts.Property.floatValue(aircraft.getClass(), "LOSElevation", 0.75F));
                            else
                                ((com.maddox.il2.objects.weapons.MGunAircraftGeneric)bulletemitter).setConvDistance(400F, 0.0F);
                        break;

                    case 1: // '\001'
                        if(bulletemitter instanceof com.maddox.il2.objects.weapons.MGunAircraftGeneric)
                            if(com.maddox.il2.ai.World.getPlayerAircraft() == aircraft)
                                ((com.maddox.il2.objects.weapons.MGunAircraftGeneric)bulletemitter).setConvDistance(com.maddox.il2.ai.World.cur().userCoverCannon, com.maddox.rts.Property.floatValue(aircraft.getClass(), "LOSElevation", 0.75F));
                            else
                            if(aircraft.isNet() && aircraft.isNetPlayer())
                                ((com.maddox.il2.objects.weapons.MGunAircraftGeneric)bulletemitter).setConvDistance(400F, com.maddox.rts.Property.floatValue(aircraft.getClass(), "LOSElevation", 0.75F));
                            else
                                ((com.maddox.il2.objects.weapons.MGunAircraftGeneric)bulletemitter).setConvDistance(400F, 0.0F);
                        break;

                    case 2: // '\002'
                        if(bulletemitter instanceof com.maddox.il2.objects.weapons.RocketGun)
                            if(com.maddox.il2.ai.World.getPlayerAircraft() == aircraft)
                            {
                                ((com.maddox.il2.objects.weapons.RocketGun)bulletemitter).setRocketTimeLife(com.maddox.il2.ai.World.cur().userRocketDelay);
                                ((com.maddox.il2.objects.weapons.RocketGun)bulletemitter).setConvDistance(com.maddox.il2.ai.World.cur().userCoverRocket, com.maddox.rts.Property.floatValue(aircraft.getClass(), "LOSElevation", 0.75F) - 2.81F);
                            } else
                            if(aircraft.isNet() && aircraft.isNetPlayer())
                                ((com.maddox.il2.objects.weapons.RocketGun)bulletemitter).setConvDistance(400F, com.maddox.rts.Property.floatValue(aircraft.getClass(), "LOSElevation", 0.75F) - 2.81F);
                            else
                            if(aircraft instanceof com.maddox.il2.objects.air.TypeFighter)
                                ((com.maddox.il2.objects.weapons.RocketGun)bulletemitter).setConvDistance(400F, -1.8F);
                            else
                            if(((com.maddox.il2.objects.weapons.RocketGun)bulletemitter).bulletMassa() > 10F)
                            {
                                if(aircraft instanceof com.maddox.il2.objects.air.IL_2)
                                    ((com.maddox.il2.objects.weapons.RocketGun)bulletemitter).setConvDistance(400F, -2F);
                                else
                                    ((com.maddox.il2.objects.weapons.RocketGun)bulletemitter).setConvDistance(400F, -1.65F);
                            } else
                            if(aircraft instanceof com.maddox.il2.objects.air.IL_2)
                                ((com.maddox.il2.objects.weapons.RocketGun)bulletemitter).setConvDistance(400F, -2.1F);
                            else
                                ((com.maddox.il2.objects.weapons.RocketGun)bulletemitter).setConvDistance(400F, -1.9F);
                        break;

                    case 3: // '\003'
                        if((bulletemitter instanceof com.maddox.il2.objects.weapons.BombGun) && com.maddox.il2.ai.World.getPlayerAircraft() == aircraft)
                            ((com.maddox.il2.objects.weapons.BombGun)bulletemitter).setBombDelay(com.maddox.il2.ai.World.cur().userBombDelay);
                        break;
                    }
                } else
                {
                    java.lang.System.err.println("Hook '" + as[j] + "' NOT found in mesh of " + aircraft.getClass());
                }

    }

    private static java.util.ArrayList weaponsListProperty(java.lang.Class class1)
    {
        java.lang.Object obj = com.maddox.rts.Property.value(class1, "weaponsList", null);
        if(obj != null)
        {
            return (java.util.ArrayList)obj;
        } else
        {
            java.util.ArrayList arraylist = new ArrayList();
            com.maddox.rts.Property.set(class1, "weaponsList", arraylist);
            return (java.util.ArrayList)arraylist;
        }
    }

    private static com.maddox.util.HashMapInt weaponsMapProperty(java.lang.Class class1)
    {
        java.lang.Object obj = com.maddox.rts.Property.value(class1, "weaponsMap", null);
        if(obj != null)
        {
            return (com.maddox.util.HashMapInt)obj;
        } else
        {
            com.maddox.util.HashMapInt hashmapint = new HashMapInt();
            com.maddox.rts.Property.set(class1, "weaponsMap", hashmapint);
            return (com.maddox.util.HashMapInt)hashmapint;
        }
    }

    public void hideWingWeapons(boolean flag)
    {
        for(int i = 0; i < FM.CT.Weapons.length; i++)
        {
            com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[i];
            if(abulletemitter != null)
            {
                for(int j = 0; j < abulletemitter.length; j++)
                    if(abulletemitter[j] instanceof com.maddox.il2.objects.weapons.BombGun)
                        ((com.maddox.il2.objects.weapons.BombGun)abulletemitter[j]).hide(flag);
                    else
                    if(abulletemitter[j] instanceof com.maddox.il2.objects.weapons.RocketGun)
                        ((com.maddox.il2.objects.weapons.RocketGun)abulletemitter[j]).hide(flag);
                    else
                    if(abulletemitter[j] instanceof com.maddox.il2.objects.weapons.Pylon)
                        ((com.maddox.il2.objects.weapons.Pylon)abulletemitter[j]).drawing(!flag);

            }
        }

    }

    public void createCockpits()
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        deleteCockpits();
        java.lang.Object obj = com.maddox.rts.Property.value(getClass(), "cockpitClass");
        if(obj == null)
            return;
        com.maddox.il2.objects.air.Cockpit._newAircraft = this;
        if(obj instanceof java.lang.Class)
        {
            java.lang.Class class1 = (java.lang.Class)obj;
            try
            {
                com.maddox.il2.game.Main3D.cur3D().cockpits = new com.maddox.il2.objects.air.Cockpit[1];
                com.maddox.il2.game.Main3D.cur3D().cockpits[0] = (com.maddox.il2.objects.air.Cockpit)class1.newInstance();
                com.maddox.il2.game.Main3D.cur3D().cockpitCur = com.maddox.il2.game.Main3D.cur3D().cockpits[0];
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        } else
        {
            java.lang.Class aclass[] = (java.lang.Class[])obj;
            try
            {
                com.maddox.il2.game.Main3D.cur3D().cockpits = new com.maddox.il2.objects.air.Cockpit[aclass.length];
                for(int i = 0; i < aclass.length; i++)
                    com.maddox.il2.game.Main3D.cur3D().cockpits[i] = (com.maddox.il2.objects.air.Cockpit)aclass[i].newInstance();

                com.maddox.il2.game.Main3D.cur3D().cockpitCur = com.maddox.il2.game.Main3D.cur3D().cockpits[0];
            }
            catch(java.lang.Exception exception1)
            {
                java.lang.System.out.println(exception1.getMessage());
                exception1.printStackTrace();
            }
        }
        com.maddox.il2.objects.air.Cockpit._newAircraft = null;
    }

    protected void deleteCockpits()
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        com.maddox.il2.objects.air.Cockpit acockpit[] = com.maddox.il2.game.Main3D.cur3D().cockpits;
        if(acockpit == null)
            return;
        for(int i = 0; i < acockpit.length; i++)
        {
            acockpit[i].destroy();
            acockpit[i] = null;
        }

        com.maddox.il2.game.Main3D.cur3D().cockpits = null;
        com.maddox.il2.game.Main3D.cur3D().cockpitCur = null;
    }

    private void explode()
    {
        if(FM.Wingman != null)
            FM.Wingman.Leader = FM.Leader;
        if(FM.Leader != null)
            FM.Leader.Wingman = FM.Wingman;
        FM.Wingman = null;
        FM.Leader = null;
        com.maddox.il2.engine.HierMesh hiermesh = hierMesh();
        int l = -1;
        float f = 30F;
        for(int i = 9; i >= 0; i--)
            if((l = hiermesh.chunkFindCheck("CF_D" + i)) >= 0)
                break;

        int ai1[] = hideSubTrees("");
        if(ai1 == null)
            return;
        int ai[] = ai1;
        ai1 = new int[ai.length + 1];
        int j;
        for(j = 0; j < ai.length; j++)
            ai1[j] = ai[j];

        ai1[j] = l;
        for(int k = 0; k < ai1.length; k++)
        {
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(this, ai1[k]);
            if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 20)
            {
                com.maddox.il2.engine.Eff3DActor.New(wreckage, null, null, 1.0F, com.maddox.il2.objects.Wreckage.FIRE, 2.5F);
                if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 50)
                    com.maddox.il2.engine.Eff3DActor.New(wreckage, null, null, 1.0F, com.maddox.il2.objects.Wreckage.SMOKE_EXPLODE, 3F);
            }
            getSpeed(Vd);
            Vd.x += (double)f * (com.maddox.il2.ai.World.Rnd().nextDouble(0.0D, 1.0D) - 0.5D);
            Vd.y += (double)f * (com.maddox.il2.ai.World.Rnd().nextDouble(0.0D, 1.0D) - 0.5D);
            Vd.z += (double)f * (com.maddox.il2.ai.World.Rnd().nextDouble(0.0D, 1.0D) - 0.5D);
            wreckage.setSpeed(Vd);
        }

    }

    public int aircNumber()
    {
        com.maddox.il2.ai.Wing wing = (com.maddox.il2.ai.Wing)getOwner();
        if(wing == null)
            return -1;
        else
            return wing.aircReady();
    }

    public int aircIndex()
    {
        com.maddox.il2.ai.Wing wing = (com.maddox.il2.ai.Wing)getOwner();
        if(wing == null)
            return -1;
        else
            return wing.aircIndex(this);
    }

    public boolean isInPlayerWing()
    {
        if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()))
            return false;
        else
            return getWing() == com.maddox.il2.ai.World.getPlayerAircraft().getWing();
    }

    public boolean isInPlayerSquadron()
    {
        if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()))
            return false;
        else
            return getSquadron() == com.maddox.il2.ai.World.getPlayerAircraft().getSquadron();
    }

    public boolean isInPlayerRegiment()
    {
        return getRegiment() == com.maddox.il2.ai.World.getPlayerRegiment();
    }

    public boolean isChunkAnyDamageVisible(java.lang.String s)
    {
        if(s.lastIndexOf("_") == -1)
            s = s + "_D";
        for(int i = 0; i < 4; i++)
            if(hierMesh().chunkFindCheck(s + i) != -1 && hierMesh().isChunkVisible(s + i))
                return true;

        return false;
    }

    protected int chunkDamageVisible(java.lang.String s)
    {
        if(s.lastIndexOf("_") == -1)
            s = s + "_D";
        for(int i = 0; i < 4; i++)
            if(hierMesh().chunkFindCheck(s + i) != -1 && hierMesh().isChunkVisible(s + i))
                return i;

        return 0;
    }

    public com.maddox.il2.ai.Wing getWing()
    {
        return (com.maddox.il2.ai.Wing)getOwner();
    }

    public com.maddox.il2.ai.Squadron getSquadron()
    {
        com.maddox.il2.ai.Wing wing = getWing();
        if(wing == null)
            return null;
        else
            return wing.squadron();
    }

    public com.maddox.il2.ai.Regiment getRegiment()
    {
        com.maddox.il2.ai.Wing wing = getWing();
        if(wing == null)
            return null;
        else
            return wing.regiment();
    }

    public void hitDaSilk()
    {
        FM.AS.hitDaSilk();
        FM.setReadyToDie(true);
        if(FM.Loc.z - com.maddox.il2.engine.Engine.land().HQ_Air(FM.Loc.x, FM.Loc.y) > 20D)
            com.maddox.il2.objects.sounds.Voice.speakBailOut(this);
    }

    protected void killPilot(com.maddox.il2.engine.Actor actor, int i)
    {
        FM.AS.hitPilot(actor, i, 100);
    }

    public void doKillPilot(int i)
    {
    }

    public void doMurderPilot(int i)
    {
    }

    public void doRemoveBodyFromPlane(int i)
    {
        doRemoveBodyChunkFromPlane("Pilot" + i);
        doRemoveBodyChunkFromPlane("Head" + i);
        doRemoveBodyChunkFromPlane("HMask" + i);
        doRemoveBodyChunkFromPlane("Pilot" + i + "a");
        doRemoveBodyChunkFromPlane("Head" + i + "a");
        doRemoveBodyChunkFromPlane("Pilot" + i + "FAK");
        doRemoveBodyChunkFromPlane("Head" + i + "FAK");
        doRemoveBodyChunkFromPlane("Pilot" + i + "FAL");
        doRemoveBodyChunkFromPlane("Head" + i + "FAL");
    }

    protected void doRemoveBodyChunkFromPlane(java.lang.String s)
    {
        if(hierMesh().chunkFindCheck(s + "_D0") != -1)
            hierMesh().chunkVisible(s + "_D0", false);
        if(hierMesh().chunkFindCheck(s + "_D1") != -1)
            hierMesh().chunkVisible(s + "_D1", false);
    }

    public void doSetSootState(int i, int j)
    {
        for(int k = 0; k < 2; k++)
        {
            if(FM.AS.astateSootEffects[i][k] != null)
                com.maddox.il2.engine.Eff3DActor.finish(FM.AS.astateSootEffects[i][k]);
            FM.AS.astateSootEffects[i][k] = null;
        }

        switch(j)
        {
        case 1: // '\001'
            FM.AS.astateSootEffects[i][0] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "ES_01"), null, 1.0F, "3DO/Effects/Aircraft/BlackSmallTSPD.eff", -1F);
            FM.AS.astateSootEffects[i][1] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "ES_02"), null, 1.0F, "3DO/Effects/Aircraft/BlackSmallTSPD.eff", -1F);
            break;

        case 3: // '\003'
            FM.AS.astateSootEffects[i][1] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/BlackMediumTSPD.eff", -1F);
            // fall through

        case 2: // '\002'
            FM.AS.astateSootEffects[i][0] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboZippo.eff", -1F);
            break;

        case 5: // '\005'
            FM.AS.astateSootEffects[i][0] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 3F, "3DO/Effects/Aircraft/TurboJRD1100F.eff", -1F);
            // fall through

        case 4: // '\004'
            FM.AS.astateSootEffects[i][1] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/BlackMediumTSPD.eff", -1F);
            break;
        }
    }

    public void onAircraftLoaded()
    {
        if(FM instanceof com.maddox.il2.ai.air.Maneuver)
        {
            com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)FM;
            com.maddox.il2.ai.air.Maneuver _tmp = maneuver;
            maneuver.takeIntoAccount[0] = 1.0F;
            com.maddox.il2.ai.air.Maneuver _tmp1 = maneuver;
            maneuver.takeIntoAccount[1] = 1.0F;
            com.maddox.il2.ai.air.Maneuver _tmp2 = maneuver;
            maneuver.takeIntoAccount[2] = 0.7F;
            if(this instanceof com.maddox.il2.objects.air.TypeFighter)
            {
                if(aircIndex() % 2 == 0)
                {
                    com.maddox.il2.ai.air.Maneuver _tmp3 = maneuver;
                    maneuver.takeIntoAccount[3] = 0.0F;
                    com.maddox.il2.ai.air.Maneuver _tmp4 = maneuver;
                    maneuver.takeIntoAccount[4] = 1.0F;
                } else
                {
                    com.maddox.il2.ai.air.Maneuver _tmp5 = maneuver;
                    maneuver.takeIntoAccount[2] = 0.1F;
                    com.maddox.il2.ai.air.Maneuver _tmp6 = maneuver;
                    maneuver.takeIntoAccount[3] = 1.0F;
                    com.maddox.il2.ai.air.Maneuver _tmp7 = maneuver;
                    maneuver.takeIntoAccount[4] = 0.0F;
                }
                com.maddox.il2.ai.air.Maneuver _tmp8 = maneuver;
                maneuver.takeIntoAccount[5] = 0.3F;
                com.maddox.il2.ai.air.Maneuver _tmp9 = maneuver;
                maneuver.takeIntoAccount[6] = 0.3F;
                com.maddox.il2.ai.air.Maneuver _tmp10 = maneuver;
                maneuver.takeIntoAccount[7] = 0.1F;
            } else
            if(this instanceof com.maddox.il2.objects.air.TypeStormovik)
            {
                if(aircIndex() != 0)
                {
                    com.maddox.il2.ai.air.Maneuver _tmp11 = maneuver;
                    maneuver.takeIntoAccount[2] = 0.5F;
                }
                com.maddox.il2.ai.air.Maneuver _tmp12 = maneuver;
                maneuver.takeIntoAccount[3] = 0.4F;
                com.maddox.il2.ai.air.Maneuver _tmp13 = maneuver;
                maneuver.takeIntoAccount[4] = 0.2F;
                com.maddox.il2.ai.air.Maneuver _tmp14 = maneuver;
                maneuver.takeIntoAccount[5] = 0.1F;
                com.maddox.il2.ai.air.Maneuver _tmp15 = maneuver;
                maneuver.takeIntoAccount[6] = 0.1F;
                com.maddox.il2.ai.air.Maneuver _tmp16 = maneuver;
                maneuver.takeIntoAccount[7] = 0.6F;
            } else
            {
                if(aircIndex() != 0)
                {
                    com.maddox.il2.ai.air.Maneuver _tmp17 = maneuver;
                    maneuver.takeIntoAccount[2] = 0.4F;
                }
                com.maddox.il2.ai.air.Maneuver _tmp18 = maneuver;
                maneuver.takeIntoAccount[3] = 0.2F;
                com.maddox.il2.ai.air.Maneuver _tmp19 = maneuver;
                maneuver.takeIntoAccount[4] = 0.0F;
                com.maddox.il2.ai.air.Maneuver _tmp20 = maneuver;
                maneuver.takeIntoAccount[5] = 0.0F;
                com.maddox.il2.ai.air.Maneuver _tmp21 = maneuver;
                maneuver.takeIntoAccount[6] = 0.0F;
                com.maddox.il2.ai.air.Maneuver _tmp22 = maneuver;
                maneuver.takeIntoAccount[7] = 1.0F;
            }
            int i = 0;
            do
            {
                com.maddox.il2.ai.air.Maneuver _tmp23 = maneuver;
                if(i < 7 + 1)
                {
                    maneuver.AccountCoeff[i] = 0.0F;
                    i++;
                } else
                {
                    break;
                }
            } while(true);
        }
    }

    public static float cvt(float f, float f1, float f2, float f3, float f4)
    {
        f = java.lang.Math.min(java.lang.Math.max(f, f1), f2);
        return f3 + ((f4 - f3) * (f - f1)) / (f2 - f1);
    }

    protected void debugprintln(java.lang.String s)
    {
        if(com.maddox.il2.ai.World.cur().isDebugFM())
            java.lang.System.out.println("<" + name() + "> (" + typedName() + ") " + s);
    }

    public static void debugprintln(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
        if(com.maddox.il2.ai.World.cur().isDebugFM())
        {
            if(com.maddox.il2.engine.Actor.isValid(actor))
            {
                java.lang.System.out.print("<" + actor.name() + ">");
                if(actor instanceof com.maddox.il2.objects.air.Aircraft)
                    java.lang.System.out.print(" (" + ((com.maddox.il2.objects.air.Aircraft)actor).typedName() + ")");
            } else
            {
                java.lang.System.out.print("<INVALIDACTOR>");
            }
            java.lang.System.out.println(" " + s);
        }
    }

    public void debuggunnery(java.lang.String s)
    {
        if(com.maddox.il2.ai.World.cur().isDebugFM())
            java.lang.System.out.println("<" + name() + "> (" + typedName() + ") *** BULLET *** : " + s);
    }

    protected float bailProbabilityOnCut(java.lang.String s)
    {
        if(s.startsWith("Nose"))
            return 0.5F;
        if(s.startsWith("Wing"))
            return 0.99F;
        if(s.startsWith("Aroone"))
            return 0.05F;
        if(s.startsWith("Tail"))
            return 0.99F;
        if(s.startsWith("StabL") && !isChunkAnyDamageVisible("VatorR"))
            return 0.99F;
        if(s.startsWith("StabR") && !isChunkAnyDamageVisible("VatorL"))
            return 0.99F;
        if(s.startsWith("Stab"))
            return 0.33F;
        if(s.startsWith("VatorL") && !isChunkAnyDamageVisible("VatorR"))
            return 0.99F;
        if(s.startsWith("VatorR") && !isChunkAnyDamageVisible("VatorL"))
            return 0.99F;
        if(s.startsWith("Vator"))
            return 0.01F;
        if(s.startsWith("Keel"))
            return 0.5F;
        if(s.startsWith("Rudder"))
            return 0.05F;
        return !s.startsWith("Engine") ? -0F : 0.12F;
    }

    private void _setMesh(java.lang.String s)
    {
        setMesh(s);
        com.maddox.il2.objects.air.CacheItem cacheitem = (com.maddox.il2.objects.air.CacheItem)meshCache.get(s);
        if(cacheitem == null)
        {
            cacheitem = new CacheItem();
            cacheitem.mesh = new HierMesh(s);
            com.maddox.il2.objects.air.Aircraft.prepareMeshCamouflage(s, cacheitem.mesh);
            cacheitem.bExistTextures = true;
            cacheitem.loaded = 1;
            meshCache.put(s, cacheitem);
        } else
        {
            cacheitem.loaded++;
            if(!cacheitem.bExistTextures)
            {
                cacheitem.mesh.destroy();
                cacheitem.mesh = new HierMesh(s);
                com.maddox.il2.objects.air.Aircraft.prepareMeshCamouflage(s, cacheitem.mesh);
                cacheitem.bExistTextures = true;
            }
        }
        airCache.put(this, cacheitem);
        com.maddox.il2.objects.air.Aircraft.checkMeshCache();
    }

    private void _removeMesh()
    {
        com.maddox.il2.objects.air.CacheItem cacheitem = (com.maddox.il2.objects.air.CacheItem)airCache.get(this);
        if(cacheitem == null)
            return;
        airCache.remove(this);
        cacheitem.loaded--;
        if(cacheitem.loaded == 0)
            cacheitem.time = com.maddox.rts.Time.real();
        com.maddox.il2.objects.air.Aircraft.checkMeshCache();
    }

    public static void checkMeshCache()
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        long l = com.maddox.rts.Time.real();
        for(java.util.Map.Entry entry = meshCache.nextEntry(null); entry != null; entry = meshCache.nextEntry(entry))
        {
            com.maddox.il2.objects.air.CacheItem cacheitem = (com.maddox.il2.objects.air.CacheItem)entry.getValue();
            if(cacheitem.loaded == 0 && cacheitem.bExistTextures && l - cacheitem.time > 0x2bf20L)
            {
                com.maddox.il2.engine.HierMesh hiermesh = cacheitem.mesh;
                int i = hiermesh.materials();
                com.maddox.il2.engine.Mat mat = com.maddox.il2.engine.Mat.New("3do/textures/clear.mat");
                for(int j = 0; j < i; j++)
                    hiermesh.materialReplace(j, mat);

                cacheitem.bExistTextures = false;
            }
        }

    }

    public static void resetGameClear()
    {
        meshCache.clear();
        airCache.clear();
    }

    public void setCockpitState(int i)
    {
        if(FM.isPlayers() && com.maddox.il2.ai.World.cur().diffCur.Vulnerability && com.maddox.il2.engine.Actor.isValid(com.maddox.il2.game.Main3D.cur3D().cockpitCur))
            com.maddox.il2.game.Main3D.cur3D().cockpitCur.doReflectCockitState();
    }

    protected void resetYPRmodifier()
    {
        ypr[0] = ypr[1] = ypr[2] = xyz[0] = xyz[1] = xyz[2] = 0.0F;
    }

    public com.maddox.il2.ai.air.CellAirPlane getCellAirPlane()
    {
        com.maddox.il2.ai.air.CellAirPlane cellairplane = (com.maddox.il2.ai.air.CellAirPlane)com.maddox.rts.Property.value(this, "CellAirPlane", (java.lang.Object)null);
        if(cellairplane != null)
            return cellairplane;
        cellairplane = (com.maddox.il2.ai.air.CellAirPlane)com.maddox.rts.Property.value(getClass(), "CellObject", (java.lang.Object)null);
        if(cellairplane == null)
        {
            tmpLocCell.set(0.0D, 0.0D, FM.Gears.H, 0.0F, FM.Gears.Pitch, 0.0F);
            cellairplane = new CellAirPlane(new com.maddox.il2.ai.air.CellObject[1][1], hierMesh(), tmpLocCell, 1.0D);
            cellairplane.blurSiluet8x();
            cellairplane.clampCells();
            com.maddox.rts.Property.set(getClass(), "CellObject", cellairplane);
        }
        cellairplane = (com.maddox.il2.ai.air.CellAirPlane)cellairplane.getClone();
        com.maddox.rts.Property.set(this, "CellObject", cellairplane);
        return cellairplane;
    }

    public static com.maddox.il2.ai.air.CellAirPlane getCellAirPlane(java.lang.Class class1)
    {
        com.maddox.il2.ai.air.CellAirPlane cellairplane = null;
        tmpLocCell.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        com.maddox.il2.engine.HierMesh hiermesh = new HierMesh(com.maddox.il2.objects.air.Aircraft.getPropertyMesh(class1, null));
        cellairplane = new CellAirPlane(new com.maddox.il2.ai.air.CellObject[1][1], hiermesh, tmpLocCell, 1.0D);
        cellairplane.blurSiluet8x();
        cellairplane.clampCells();
        return cellairplane;
    }

    private static com.maddox.il2.ai.RangeRandom dec_rnd = new RangeRandom();
    public long tmSearchlighted;
    public static final float MINI_HIT = 5.000001E-007F;
    public static final float defaultUnitHit = 0.01F;
    public static final float powerPerMM = 1700F;
    public static final int HIT_COLLISION = 0;
    public static final int HIT_EXPLOSION = 1;
    public static final int HIT_SHOT = 2;
    protected static float ypr[] = {
        0.0F, 0.0F, 0.0F
    };
    protected static float xyz[] = {
        0.0F, 0.0F, 0.0F
    };
    public static final int _AILERON_L = 0;
    public static final int _AILERON_R = 1;
    public static final int _FUSELAGE = 2;
    public static final int _ENGINE_1 = 3;
    public static final int _ENGINE_2 = 4;
    public static final int _ENGINE_3 = 5;
    public static final int _ENGINE_4 = 6;
    public static final int _GEAR_C = 7;
    public static final int _FLAP_R = 8;
    public static final int _GEAR_L = 9;
    public static final int _GEAR_R = 10;
    public static final int _VER_STAB_1 = 11;
    public static final int _VER_STAB_2 = 12;
    public static final int _NOSE = 13;
    public static final int _OIL = 14;
    public static final int _RUDDER_1 = 15;
    public static final int _RUDDER_2 = 16;
    public static final int _HOR_STAB_L = 17;
    public static final int _HOR_STAB_R = 18;
    public static final int _TAIL_1 = 19;
    public static final int _TAIL_2 = 20;
    public static final int _TANK_1 = 21;
    public static final int _TANK_2 = 22;
    public static final int _TANK_3 = 23;
    public static final int _TANK_4 = 24;
    public static final int _TURRET_1 = 25;
    public static final int _TURRET_2 = 26;
    public static final int _TURRET_3 = 27;
    public static final int _TURRET_4 = 28;
    public static final int _TURRET_5 = 29;
    public static final int _TURRET_6 = 30;
    public static final int _ELEVATOR_L = 31;
    public static final int _ELEVATOR_R = 32;
    public static final int _WING_ROOT_L = 33;
    public static final int _WING_MIDDLE_L = 34;
    public static final int _WING_END_L = 35;
    public static final int _WING_ROOT_R = 36;
    public static final int _WING_MIDDLE_R = 37;
    public static final int _WING_END_R = 38;
    public static final int _FLAP_01 = 39;
    public static final int _FLAP_02 = 40;
    public static final int _FLAP_03 = 41;
    public static final int _FLAP_04 = 42;
    public static final int _NULLPART = 43;
    public static final int _NOMOREPARTS = 44;
    private static final java.lang.String partNames[] = {
        "AroneL", "AroneR", "CF", "Engine1", "Engine2", "Engine3", "Engine4", "GearC2", "FlapR", "GearL2", 
        "GearR2", "Keel1", "Keel2", "Nose", "Oil", "Rudder1", "Rudder2", "StabL", "StabR", "Tail1", 
        "Tail2", "Tank1", "Tank2", "Tank3", "Tank4", "Turret1B", "Turret2B", "Turret3B", "Turret4B", "Turret5B", 
        "Turret6B", "VatorL", "VatorR", "WingLIn", "WingLMid", "WingLOut", "WingRIn", "WingRMid", "WingROut", "Flap01", 
        "Flap02", "Flap03", "Flap04", "NullPart", "EXPIRED"
    };
    private static final java.lang.String partNamesForAll[] = {
        "AroneL", "AroneR", "CF", "GearL2", "GearR2", "Keel1", "Oil", "Rudder1", "StabL", "StabR", 
        "Tail1", "VatorL", "VatorR", "WingLIn", "WingLMid", "WingLOut", "WingRIn", "WingRMid", "WingROut"
    };
    private static final long _HIT = 0x100000000000L;
    private static final long _TOMASTER = 0x200000000000L;
    public static final int END_EXPLODE = 2;
    public static final int END_FM_DESTROY = 3;
    public static final int END_DISAPPEAR = 4;
    private long timePostEndAction;
    public boolean buried;
    private float EpsCoarse_;
    private float EpsSmooth_;
    private float EpsVerySmooth_;
    private float Gear_;
    private float Rudder_;
    private float Elevator_;
    private float Aileron_;
    private float Flap_;
    private float BayDoor_;
    private float AirBrake_;
    private float Steering_;
    public float wingfold_;
    public float cockpitDoor_;
    public float arrestor_;
    protected float propPos[] = {
        0.0F, 21.6F, 45.9F, 66.9F, 45F, 9.2F
    };
    protected int oldProp[] = {
        0, 0, 0, 0, 0, 0
    };
    protected static final java.lang.String Props[][] = {
        {
            "Prop1_D0", "PropRot1_D0", "Prop1_D1"
        }, {
            "Prop2_D0", "PropRot2_D0", "Prop2_D1"
        }, {
            "Prop3_D0", "PropRot3_D0", "Prop3_D1"
        }, {
            "Prop4_D0", "PropRot4_D0", "Prop4_D1"
        }, {
            "Prop5_D0", "PropRot5_D0", "Prop5_D1"
        }, {
            "Prop6_D0", "PropRot6_D0", "Prop6_D1"
        }
    };
    private com.maddox.il2.engine.LightPointWorld lLight[];
    private com.maddox.il2.engine.Hook lLightHook[] = {
        null, null, null, null
    };
    private static com.maddox.il2.engine.Loc lLightLoc1 = new Loc();
    private static com.maddox.JGP.Point3d lLightP1 = new Point3d();
    private static com.maddox.JGP.Point3d lLightP2 = new Point3d();
    private static com.maddox.JGP.Point3d lLightPL = new Point3d();
    private java.lang.String _loadingCountry;
    private java.lang.String typedName;
    private static java.lang.String _skinMat[] = {
        "Gloss1D0o", "Gloss1D1o", "Gloss1D2o", "Gloss2D0o", "Gloss2D1o", "Gloss2D2o", "Gloss1D0p", "Gloss1D1p", "Gloss1D2p", "Gloss2D0p", 
        "Gloss2D1p", "Gloss2D2p", "Gloss1D0q", "Gloss1D1q", "Gloss1D2q", "Gloss2D0q", "Gloss2D1q", "Gloss2D2q", "Matt1D0o", "Matt1D1o", 
        "Matt1D2o", "Matt2D0o", "Matt2D1o", "Matt2D2o", "Matt1D0p", "Matt1D1p", "Matt1D2p", "Matt2D0p", "Matt2D1p", "Matt2D2p", 
        "Matt1D0q", "Matt1D1q", "Matt1D2q", "Matt2D0q", "Matt2D1q", "Matt2D2q"
    };
    private static final java.lang.String _curSkin[] = {
        "skin1o.tga", "skin1p.tga", "skin1q.tga"
    };
    private static com.maddox.util.HashMapExt meshCache = new HashMapExt();
    private static com.maddox.util.HashMapExt airCache = new HashMapExt();
    protected static com.maddox.il2.engine.Loc tmpLocCell = new Loc();
    protected static com.maddox.JGP.Vector3d v1 = new Vector3d();
    private static com.maddox.JGP.Vector3f v2 = new Vector3f();
    private static com.maddox.JGP.Vector3d Vd = new Vector3d();
    protected static com.maddox.JGP.Point3d Pd = new Point3d();
    protected static com.maddox.JGP.Point3d tmpP1 = new Point3d();
    protected static com.maddox.JGP.Point3d tmpP2 = new Point3d();
    public static com.maddox.il2.engine.Loc tmpLoc1 = new Loc();
    protected static com.maddox.il2.engine.Loc tmpLoc2 = new Loc();
    protected static com.maddox.il2.engine.Loc tmpLoc3 = new Loc();
    protected static com.maddox.il2.engine.Loc tmpLocExp = new Loc();
    public static com.maddox.il2.engine.Orient tmpOr = new Orient();
    private static int tmpBonesHit;
    private static boolean bWasAlive = true;

}
