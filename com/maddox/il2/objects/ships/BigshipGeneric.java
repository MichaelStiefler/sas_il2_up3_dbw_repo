// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BigshipGeneric.java

package com.maddox.il2.objects.ships;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Line2d;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Aimer;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.AnglesForkExtended;
import com.maddox.il2.ai.AnglesRange;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.StrengthProperties;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.CellAirField;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.ai.ground.Aim;
import com.maddox.il2.ai.ground.HunterInterface;
import com.maddox.il2.ai.ground.NearestEnemies;
import com.maddox.il2.ai.ground.Predator;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateAdapter;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.engine.VisibilityLong;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiStayPoint;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.weapons.CannonMidrangeGeneric;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.objects.ships:
//            WeakBody, ShipGeneric

public class BigshipGeneric extends com.maddox.il2.engine.ActorHMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.engine.MsgCollisionListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.il2.ai.ground.Predator, com.maddox.il2.objects.ActorAlign, com.maddox.il2.ai.ground.HunterInterface, com.maddox.il2.engine.VisibilityLong
{
    private static class TowStringMeshDraw extends com.maddox.il2.engine.ActorMeshDraw
    {

        public void render(com.maddox.il2.engine.Actor actor)
        {
            super.render(actor);
            com.maddox.il2.objects.ships.BigshipGeneric bigshipgeneric = (com.maddox.il2.objects.ships.BigshipGeneric)actor;
            if(bigshipgeneric.prop.propAirport == null)
                return;
            com.maddox.JGP.Point3d apoint3d[] = bigshipgeneric.prop.propAirport.towPRel;
            if(apoint3d == null)
                return;
            actor.pos.getRender(lRender);
            int i = apoint3d.length / 2;
            for(int j = 0; j < i; j++)
            {
                if(j != bigshipgeneric.towPortNum)
                {
                    lRender.transform(apoint3d[j * 2], p0);
                    lRender.transform(apoint3d[j * 2 + 1], p1);
                    renderTow(bigshipgeneric.prop.propAirport.towString);
                    continue;
                }
                if(com.maddox.il2.engine.Actor.isValid(bigshipgeneric.towAircraft))
                {
                    lRender.transform(apoint3d[j * 2], p0);
                    l.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                    bigshipgeneric.towHook.computePos(bigshipgeneric.towAircraft, bigshipgeneric.towAircraft.pos.getRender(), l);
                    p1.set(l.getPoint());
                    renderTow(bigshipgeneric.prop.propAirport.towString);
                    lRender.transform(apoint3d[j * 2 + 1], p0);
                    l.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                    bigshipgeneric.towHook.computePos(bigshipgeneric.towAircraft, bigshipgeneric.towAircraft.pos.getRender(), l);
                    p1.set(l.getPoint());
                    renderTow(bigshipgeneric.prop.propAirport.towString);
                }
            }

        }

        private void renderTow(com.maddox.il2.engine.Mesh mesh)
        {
            tmpVector.sub(p1, p0);
            mesh.setScaleXYZ((float)tmpVector.length(), 1.0F, 1.0F);
            tmpVector.normalize();
            com.maddox.il2.engine.Orient orient = l.getOrient();
            orient.setAT0(tmpVector);
            l.set(p0);
            mesh.setPos(l);
            mesh.render();
        }

        private static com.maddox.il2.engine.Loc lRender = new Loc();
        private static com.maddox.il2.engine.Loc l = new Loc();
        private static com.maddox.JGP.Vector3d tmpVector = new Vector3d();
        private static com.maddox.JGP.Point3d p0 = new Point3d();
        private static com.maddox.JGP.Point3d p1 = new Point3d();


        public TowStringMeshDraw(com.maddox.il2.engine.ActorDraw actordraw)
        {
            super(actordraw);
        }
    }

    public static class AirportProperties
    {

        public void firstInit(com.maddox.il2.objects.ships.BigshipGeneric bigshipgeneric)
        {
            if(bInited)
                return;
            bInited = true;
            com.maddox.il2.engine.HierMesh hiermesh = bigshipgeneric.hierMesh();
            findHook(hiermesh, "_RWY_TO", rwy[0]);
            findHook(hiermesh, "_RWY_LDG", rwy[1]);
            towString = new Mesh("3DO/Arms/ArrestorCable/mono.sim");
            java.util.ArrayList arraylist = new ArrayList();
            int i = 0;
            do
            {
                java.lang.String s = i <= 9 ? "0" + i : "" + i;
                if(!findHook(hiermesh, "_TOW" + s + "A", loc))
                    break;
                arraylist.add(new Point3d(loc.getPoint()));
                findHook(hiermesh, "_TOW" + s + "B", loc);
                arraylist.add(new Point3d(loc.getPoint()));
                i++;
            } while(true);
            if(i > 0)
            {
                i *= 2;
                towPRel = new com.maddox.JGP.Point3d[i];
                for(int j = 0; j < i; j++)
                    towPRel[j] = (com.maddox.JGP.Point3d)arraylist.get(j);

            }
            fillParks(bigshipgeneric, hiermesh, "_Park", arraylist);
            if(arraylist.size() > 0)
                cellTO = new CellAirField(new com.maddox.il2.ai.air.CellObject[1][1], arraylist, 1.0D);
            fillParks(bigshipgeneric, hiermesh, "_LPark", arraylist);
            if(arraylist.size() > 0)
                cellLDG = new CellAirField(new com.maddox.il2.ai.air.CellObject[1][1], arraylist, 1.0D);
        }

        private void fillParks(com.maddox.il2.objects.ships.BigshipGeneric bigshipgeneric, com.maddox.il2.engine.HierMesh hiermesh, java.lang.String s, java.util.ArrayList arraylist)
        {
            arraylist.clear();
            int i = 0;
            do
            {
                java.lang.String s1 = s + (i <= 9 ? "0" + i : "" + i);
                if(findHook(hiermesh, s1, loc))
                {
                    arraylist.add(new Point3d(-p.y, p.x, p.z));
                    i++;
                } else
                {
                    return;
                }
            } while(true);
        }

        private boolean findHook(com.maddox.il2.engine.HierMesh hiermesh, java.lang.String s, com.maddox.il2.engine.Loc loc1)
        {
            int i = hiermesh.hookFind(s);
            if(i == -1)
            {
                return false;
            } else
            {
                hiermesh.hookMatrix(i, m1);
                m1.getEulers(tmp);
                o.setYPR(com.maddox.JGP.Geom.RAD2DEG((float)tmp[0]), 360F - com.maddox.JGP.Geom.RAD2DEG((float)tmp[1]), 360F - com.maddox.JGP.Geom.RAD2DEG((float)tmp[2]));
                p.set(m1.m03, m1.m13, m1.m23);
                loc1.set(p, o);
                return true;
            }
        }

        public com.maddox.il2.engine.Loc rwy[] = {
            new Loc(), new Loc()
        };
        public com.maddox.il2.engine.Mesh towString;
        public com.maddox.JGP.Point3d towPRel[];
        public com.maddox.il2.ai.air.CellAirField cellTO;
        public com.maddox.il2.ai.air.CellAirField cellLDG;
        private boolean bInited;
        private static com.maddox.il2.engine.Loc loc = new Loc();
        private static com.maddox.JGP.Point3d p = new Point3d();
        private static com.maddox.il2.engine.Orient o = new Orient();
        private static com.maddox.JGP.Matrix4d m1 = new Matrix4d();
        private static double tmp[] = new double[3];


        public AirportProperties(java.lang.Class class1)
        {
            bInited = false;
            com.maddox.rts.Property.set(class1, "IsAirport", "true");
        }
    }

    public static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

        private static float getF(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, float f, float f1)
        {
            float f2 = sectfile.get(s, s1, -9865.345F);
            if(f2 == -9865.345F || f2 < f || f2 > f1)
            {
                if(f2 == -9865.345F)
                    java.lang.System.out.println("Ship: Value of [" + s + "]:<" + s1 + "> " + "not found");
                else
                    java.lang.System.out.println("Ship: Value of [" + s + "]:<" + s1 + "> (" + f2 + ")" + " is out of range (" + f + ";" + f1 + ")");
                throw new RuntimeException("Can't set property");
            } else
            {
                return f2;
            }
        }

        private static java.lang.String getS(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1)
        {
            java.lang.String s2 = sectfile.get(s, s1);
            if(s2 == null || s2.length() <= 0)
            {
                java.lang.System.out.print("Ship: Value of [" + s + "]:<" + s1 + "> not found");
                throw new RuntimeException("Can't set property");
            } else
            {
                return new String(s2);
            }
        }

        private static java.lang.String getS(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, java.lang.String s2)
        {
            java.lang.String s3 = sectfile.get(s, s1);
            if(s3 == null || s3.length() <= 0)
                return s2;
            else
                return new String(s3);
        }

        private static void tryToReadGunProperties(com.maddox.rts.SectFile sectfile, java.lang.String s, com.maddox.il2.objects.ships.ShipPartProperties shippartproperties)
        {
            if(sectfile.exist(s, "Gun"))
            {
                java.lang.String s1 = "com.maddox.il2.objects.weapons." + com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s, "Gun");
                try
                {
                    shippartproperties.gunClass = java.lang.Class.forName(s1);
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println("BigShip: Can't find gun class '" + s1 + "'");
                    throw new RuntimeException("Can't register Ship object");
                }
            }
            if(sectfile.exist(s, "AttackMaxDistance"))
                shippartproperties.ATTACK_MAX_DISTANCE = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "AttackMaxDistance", 6F, 50000F);
            if(sectfile.exist(s, "AttackMaxRadius"))
                shippartproperties.ATTACK_MAX_RADIUS = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "AttackMaxRadius", 6F, 50000F);
            if(sectfile.exist(s, "AttackMaxHeight"))
                shippartproperties.ATTACK_MAX_HEIGHT = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "AttackMaxHeight", 6F, 15000F);
            if(sectfile.exist(s, "TrackingOnly"))
                shippartproperties.TRACKING_ONLY = true;
            if(sectfile.exist(s, "FireFastTargets"))
            {
                float f = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "FireFastTargets", 0.0F, 2.0F);
                shippartproperties.ATTACK_FAST_TARGETS = (int)(f + 0.5F);
                if(shippartproperties.ATTACK_FAST_TARGETS > 2)
                    shippartproperties.ATTACK_FAST_TARGETS = 2;
            }
            if(sectfile.exist(s, "FastTargetsAngleError"))
            {
                float f1 = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "FastTargetsAngleError", 0.0F, 45F);
                shippartproperties.FAST_TARGETS_ANGLE_ERROR = f1;
            }
            if(sectfile.exist(s, "HeadMinYaw"))
                shippartproperties._HEAD_MIN_YAW = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "HeadMinYaw", -360F, 360F);
            if(sectfile.exist(s, "HeadMaxYaw"))
                shippartproperties._HEAD_MAX_YAW = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "HeadMaxYaw", -360F, 360F);
            if(sectfile.exist(s, "GunMinPitch"))
                shippartproperties.GUN_MIN_PITCH = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "GunMinPitch", -15F, 85F);
            if(sectfile.exist(s, "GunMaxPitch"))
                shippartproperties.GUN_MAX_PITCH = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "GunMaxPitch", 0.0F, 89.9F);
            if(sectfile.exist(s, "HeadMaxYawSpeed"))
                shippartproperties.HEAD_MAX_YAW_SPEED = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "HeadMaxYawSpeed", 0.1F, 999F);
            if(sectfile.exist(s, "GunMaxPitchSpeed"))
                shippartproperties.GUN_MAX_PITCH_SPEED = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "GunMaxPitchSpeed", 0.1F, 999F);
            if(sectfile.exist(s, "DelayAfterShoot"))
                shippartproperties.DELAY_AFTER_SHOOT = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "DelayAfterShoot", 0.0F, 999F);
            if(sectfile.exist(s, "ChainfireTime"))
                shippartproperties.CHAINFIRE_TIME = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "ChainfireTime", 0.0F, 600F);
            if(sectfile.exist(s, "GunHeadChunk"))
                shippartproperties.headChunkName = com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s, "GunHeadChunk");
            if(sectfile.exist(s, "GunBarrelChunk"))
                shippartproperties.gunChunkName = com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s, "GunBarrelChunk");
            if(sectfile.exist(s, "GunShellStartHook"))
                shippartproperties.gunShellStartHookName = com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s, "GunShellStartHook");
        }

        private static com.maddox.il2.objects.ships.ShipProperties LoadShipProperties(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.Class class1)
        {
            com.maddox.il2.objects.ships.ShipProperties shipproperties = new ShipProperties();
            shipproperties.meshName = com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s, "Mesh");
            shipproperties.soundName = com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s, "SoundMove");
            if(shipproperties.soundName.equalsIgnoreCase("none"))
                shipproperties.soundName = null;
            shipproperties.SLIDER_DIST = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "SliderDistance", 5F, 1000F);
            shipproperties.SPEED = com.maddox.il2.objects.ships.BigshipGeneric.KmHourToMSec(com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "Speed", 0.5F, 200F));
            shipproperties.DELAY_RESPAWN_MIN = 15F;
            shipproperties.DELAY_RESPAWN_MAX = 30F;
            com.maddox.rts.Property.set(class1, "iconName", "icons/" + com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s, "Icon") + ".mat");
            com.maddox.rts.Property.set(class1, "meshName", shipproperties.meshName);
            com.maddox.rts.Property.set(class1, "speed", shipproperties.SPEED);
            int i;
            for(i = 0; sectfile.sectionIndex(s + ":Part" + i) >= 0; i++);
            if(i <= 0)
            {
                java.lang.System.out.println("BigShip: No part sections for '" + s + "'");
                throw new RuntimeException("Can't register BigShip object");
            }
            if(i >= 255)
            {
                java.lang.System.out.println("BigShip: Too many parts in " + s + ".");
                throw new RuntimeException("Can't register BigShip object");
            }
            shipproperties.propparts = new com.maddox.il2.objects.ships.ShipPartProperties[i];
            shipproperties.nGuns = 0;
            for(int j = 0; j < i; j++)
            {
                java.lang.String s1 = s + ":Part" + j;
                com.maddox.il2.objects.ships.ShipPartProperties shippartproperties = new ShipPartProperties();
                shipproperties.propparts[j] = shippartproperties;
                shippartproperties.baseChunkName = com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s1, "BaseChunk");
                int l;
                for(l = 0; sectfile.exist(s1, "AdditionalCollisionChunk" + l); l++);
                if(l > 4)
                {
                    java.lang.System.out.println("BigShip: Too many addcollischunks in '" + s1 + "'");
                    throw new RuntimeException("Can't register BigShip object");
                }
                shippartproperties.additCollisChunkName = new java.lang.String[l];
                for(int i1 = 0; i1 < l; i1++)
                    shippartproperties.additCollisChunkName[i1] = com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s1, "AdditionalCollisionChunk" + i1);

                java.lang.String s2 = null;
                if(sectfile.exist(s1, "strengthBasedOnThisSection"))
                    s2 = com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s1, "strengthBasedOnThisSection");
                if(!shippartproperties.stre.read("Bigship", sectfile, s2, s1))
                    throw new RuntimeException("Can't register Bigship object");
                if(sectfile.exist(s1, "Vital"))
                {
                    shippartproperties.dmgDepth = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "damageDepth", 0.0F, 99F);
                    shippartproperties.dmgPitch = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "damagePitch", -89F, 89F);
                    shippartproperties.dmgRoll = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "damageRoll", 0.0F, 89F);
                    shippartproperties.dmgTime = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "damageTime", 1.0F, 1200F);
                    shippartproperties.BLACK_DAMAGE = 0.6666667F;
                } else
                {
                    shippartproperties.dmgDepth = -1F;
                    shippartproperties.BLACK_DAMAGE = 1.0F;
                }
                if(!sectfile.exist(s1, "Gun") && !sectfile.exist(s1, "gunBasedOnThisSection"))
                {
                    shippartproperties.gun_idx = -1;
                    continue;
                }
                if(shippartproperties.isItLifeKeeper())
                {
                    java.lang.System.out.println("*** ERROR: bigship: vital with gun");
                    shippartproperties.gun_idx = -1;
                    continue;
                }
                shippartproperties.gun_idx = shipproperties.nGuns++;
                if(shipproperties.nGuns > 256)
                {
                    java.lang.System.out.println("BigShip: Too many guns in " + s + ".");
                    throw new RuntimeException("Can't register BigShip object");
                }
                shippartproperties.gunClass = null;
                shippartproperties.ATTACK_MAX_DISTANCE = -1000F;
                shippartproperties.ATTACK_MAX_RADIUS = -1000F;
                shippartproperties.ATTACK_MAX_HEIGHT = -1000F;
                shippartproperties.TRACKING_ONLY = false;
                shippartproperties.ATTACK_FAST_TARGETS = 1;
                shippartproperties.FAST_TARGETS_ANGLE_ERROR = 0.0F;
                shippartproperties._HEAD_MIN_YAW = -1000F;
                shippartproperties._HEAD_MAX_YAW = -1000F;
                shippartproperties.GUN_MIN_PITCH = -1000F;
                shippartproperties.GUN_STD_PITCH = -1000F;
                shippartproperties.GUN_MAX_PITCH = -1000F;
                shippartproperties.HEAD_MAX_YAW_SPEED = -1000F;
                shippartproperties.GUN_MAX_PITCH_SPEED = -1000F;
                shippartproperties.DELAY_AFTER_SHOOT = -1000F;
                shippartproperties.CHAINFIRE_TIME = -1000F;
                shippartproperties.headChunkName = null;
                shippartproperties.gunChunkName = null;
                shippartproperties.gunShellStartHookName = null;
                if(sectfile.exist(s1, "gunBasedOnThisSection"))
                {
                    java.lang.String s3 = com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s1, "gunBasedOnThisSection");
                    com.maddox.il2.objects.ships.SPAWN.tryToReadGunProperties(sectfile, s3, shippartproperties);
                }
                com.maddox.il2.objects.ships.SPAWN.tryToReadGunProperties(sectfile, s1, shippartproperties);
                if(shippartproperties.gunClass == null || shippartproperties.ATTACK_MAX_DISTANCE <= -1000F || shippartproperties.ATTACK_MAX_RADIUS <= -1000F || shippartproperties.ATTACK_MAX_HEIGHT <= -1000F || shippartproperties._HEAD_MIN_YAW <= -1000F || shippartproperties._HEAD_MAX_YAW <= -1000F || shippartproperties.GUN_MIN_PITCH <= -1000F || shippartproperties.GUN_MAX_PITCH <= -1000F || shippartproperties.HEAD_MAX_YAW_SPEED <= -1000F || shippartproperties.GUN_MAX_PITCH_SPEED <= -1000F || shippartproperties.DELAY_AFTER_SHOOT <= -1000F || shippartproperties.CHAINFIRE_TIME <= -1000F || shippartproperties.headChunkName == null || shippartproperties.gunChunkName == null || shippartproperties.gunShellStartHookName == null)
                {
                    java.lang.System.out.println("BigShip: Not enough 'gun' data  in '" + s1 + "'");
                    throw new RuntimeException("Can't register BigShip object");
                }
                shippartproperties.WEAPONS_MASK = com.maddox.il2.objects.weapons.Gun.getProperties(shippartproperties.gunClass).weaponType;
                if(shippartproperties.WEAPONS_MASK == 0)
                {
                    java.lang.System.out.println("BigShip: Undefined weapon type in gun class '" + shippartproperties.gunClass.getName() + "'");
                    throw new RuntimeException("Can't register BigShip object");
                }
                if(shippartproperties._HEAD_MIN_YAW > shippartproperties._HEAD_MAX_YAW)
                {
                    java.lang.System.out.println("BigShip: Wrong yaw angles in gun " + s1 + ".");
                    throw new RuntimeException("Can't register BigShip object");
                }
                shippartproperties.HEAD_STD_YAW = 0.0F;
                shippartproperties.HEAD_YAW_RANGE.set(shippartproperties._HEAD_MIN_YAW, shippartproperties._HEAD_MAX_YAW);
            }

            shipproperties.WEAPONS_MASK = 0;
            shipproperties.ATTACK_MAX_DISTANCE = 1.0F;
            for(int k = 0; k < shipproperties.propparts.length; k++)
            {
                if(!shipproperties.propparts[k].haveGun())
                    continue;
                shipproperties.WEAPONS_MASK |= shipproperties.propparts[k].WEAPONS_MASK;
                if(shipproperties.ATTACK_MAX_DISTANCE < shipproperties.propparts[k].ATTACK_MAX_DISTANCE)
                    shipproperties.ATTACK_MAX_DISTANCE = shipproperties.propparts[k].ATTACK_MAX_DISTANCE;
            }

            if(sectfile.get(s, "IsAirport", false))
                shipproperties.propAirport = new AirportProperties(class1);
            return shipproperties;
        }

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            com.maddox.il2.objects.ships.BigshipGeneric bigshipgeneric = null;
            try
            {
                com.maddox.il2.objects.ships.BigshipGeneric.constr_arg1 = proper;
                com.maddox.il2.objects.ships.BigshipGeneric.constr_arg2 = actorspawnarg;
                bigshipgeneric = (com.maddox.il2.objects.ships.BigshipGeneric)cls.newInstance();
                com.maddox.il2.objects.ships.BigshipGeneric.constr_arg1 = null;
                com.maddox.il2.objects.ships.BigshipGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.ships.BigshipGeneric.constr_arg1 = null;
                com.maddox.il2.objects.ships.BigshipGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create Ship object [class:" + cls.getName() + "]");
                return null;
            }
            return bigshipgeneric;
        }

        public java.lang.Class cls;
        public com.maddox.il2.objects.ships.ShipProperties proper;

        public SPAWN(java.lang.Class class1)
        {
            try
            {
                java.lang.String s = class1.getName();
                int i = s.lastIndexOf('.');
                int j = s.lastIndexOf('$');
                if(i < j)
                    i = j;
                java.lang.String s1 = s.substring(i + 1);
                proper = com.maddox.il2.objects.ships.SPAWN.LoadShipProperties(com.maddox.il2.objects.Statics.getShipsFile(), s1, class1);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("Problem in spawn: " + class1.getName());
            }
            cls = class1;
            com.maddox.rts.Spawn.add(cls, this);
        }
    }

    class Mirror extends com.maddox.il2.engine.ActorNet
    {

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(netmsginput.isGuaranted())
            {
                int j1 = netmsginput.readUnsignedByte();
                java.lang.System.out.println("MIRROR NETINPUT: " + j1);
                switch(j1)
                {
                case 93: // ']'
                    double d = netmsginput.readDouble();
                    double d1 = netmsginput.readDouble();
                    double d2 = netmsginput.readDouble();
                    float f1 = netmsginput.readFloat();
                    float f2 = netmsginput.readFloat();
                    float f3 = netmsginput.readFloat();
                    com.maddox.il2.engine.Loc loc = new Loc(d, d1, d2, f1, f2, f3);
                    if(airport != null)
                        airport.setClientLoc(loc);
                    return true;

                case 73: // 'I'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(netmsginput, 0);
                        post(netmsgguaranted);
                    }
                    timeOfDeath = netmsginput.readLong();
                    if(timeOfDeath < 0L)
                    {
                        if(dying == 0)
                        {
                            makeLive();
                            setDefaultLivePose();
                            forgetAllAiming();
                        }
                    } else
                    if(dying == 0)
                        Die(null, timeOfDeath, false, true);
                    return true;

                case 82: // 'R'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted(netmsginput, 0);
                        post(netmsgguaranted1);
                    }
                    makeLive();
                    setDefaultLivePose();
                    forgetAllAiming();
                    setDiedFlag(false);
                    tmInterpoStart = tmInterpoEnd = 0L;
                    bodyDepth = bodyPitch = bodyRoll = 0.0F;
                    bodyDepth0 = bodyPitch0 = bodyRoll0 = 0.0F;
                    bodyDepth1 = bodyPitch1 = bodyRoll1 = 0.0F;
                    setPosition();
                    pos.reset();
                    return true;

                case 83: // 'S'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted2 = new NetMsgGuaranted(netmsginput, 0);
                        post(netmsgguaranted2);
                    }
                    int i2 = netmsginput.available();
                    if(i2 > 0 && !com.maddox.il2.game.Mission.isDogfight())
                    {
                        int l2 = netmsginput.readUnsignedByte();
                        float f = l2;
                        if(path != null && l2 != 127 && f < CURRSPEED)
                        {
                            CURRSPEED = f;
                            computeNewPath();
                        }
                        i2--;
                    }
                    int i3 = (parts.length + 3) / 4;
                    if(i2 != i3)
                    {
                        java.lang.System.out.println("*** net bigship S");
                        return true;
                    }
                    if(i3 <= 0)
                    {
                        java.lang.System.out.println("*** net bigship S0");
                        return true;
                    }
                    int l3 = 0;
                    for(int j4 = 0; j4 < i2; j4++)
                    {
                        int i5 = netmsginput.readUnsignedByte();
                        for(int k5 = 0; k5 < 4 && l3 < parts.length; k5++)
                        {
                            int i6 = i5 >>> k5 * 2 & 3;
                            if(i6 <= parts[l3].state)
                            {
                                l3++;
                                continue;
                            }
                            if(i6 == 2)
                            {
                                parts[l3].damage = 0.0F;
                                parts[l3].mirror_initiator = null;
                            }
                            parts[l3].state = i6;
                            visualsInjurePart(l3, true);
                            l3++;
                        }

                    }

                    return true;

                case 100: // 'd'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted3 = new NetMsgGuaranted(netmsginput, 0);
                        post(netmsgguaranted3);
                    }
                    int j2 = netmsginput.available();
                    if(j2 != 8)
                    {
                        java.lang.System.out.println("*** net bigship d");
                        return true;
                    }
                    if(dying != 0)
                    {
                        return true;
                    } else
                    {
                        computeInterpolatedDPR(com.maddox.il2.net.NetServerParams.getServerTime());
                        bodyDepth0 = bodyDepth;
                        bodyPitch0 = bodyPitch;
                        bodyRoll0 = bodyRoll;
                        bodyDepth1 = (float)(1000D * ((double)(netmsginput.readUnsignedShort() & 0x7fff) / 32767D));
                        bodyPitch1 = (float)(90D * ((double)netmsginput.readShort() / 32767D));
                        bodyRoll1 = (float)(90D * ((double)netmsginput.readShort() / 32767D));
                        tmInterpoStart = tmInterpoEnd = com.maddox.il2.net.NetServerParams.getServerTime();
                        tmInterpoEnd+= = (long)(1000D * (1200D * ((double)(netmsginput.readUnsignedShort() & 0x7fff) / 32767D)));
                        computeInterpolatedDPR(com.maddox.il2.net.NetServerParams.getServerTime());
                        return true;
                    }

                case 68: // 'D'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted4 = new NetMsgGuaranted(netmsginput, 1);
                        post(netmsgguaranted4);
                    }
                    int j3 = netmsginput.available();
                    boolean flag;
                    if(j3 == 8 + netmsginput.netObjReferenceLen() + 8 + 8)
                        flag = false;
                    else
                    if(j3 == 8 + netmsginput.netObjReferenceLen() + 8 + 8 + 8)
                    {
                        flag = true;
                    } else
                    {
                        java.lang.System.out.println("*** net bigship D");
                        return true;
                    }
                    if(dying != 0)
                        return true;
                    timeOfDeath = netmsginput.readLong();
                    if(com.maddox.il2.game.Mission.isDeathmatch())
                        timeOfDeath = com.maddox.il2.net.NetServerParams.getServerTime();
                    if(timeOfDeath < 0L)
                    {
                        java.lang.System.out.println("*** net bigship D tm");
                        return true;
                    }
                    com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                    com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                    computeInterpolatedDPR(com.maddox.il2.net.NetServerParams.getServerTime());
                    bodyDepth0 = bodyDepth;
                    bodyPitch0 = bodyPitch;
                    bodyRoll0 = bodyRoll;
                    bodyDepth1 = (float)(1000D * ((double)(netmsginput.readUnsignedShort() & 0x7fff) / 32767D));
                    bodyPitch1 = (float)(90D * ((double)netmsginput.readShort() / 32767D));
                    bodyRoll1 = (float)(90D * ((double)netmsginput.readShort() / 32767D));
                    tmInterpoStart = tmInterpoEnd = com.maddox.il2.net.NetServerParams.getServerTime();
                    tmInterpoEnd+= = (long)(1000D * (1200D * ((double)(netmsginput.readUnsignedShort() & 0x7fff) / 32767D)));
                    computeInterpolatedDPR(com.maddox.il2.net.NetServerParams.getServerTime());
                    sink2Depth = (float)(1000D * ((double)(netmsginput.readUnsignedShort() & 0x7fff) / 32767D));
                    sink2Pitch = (float)(90D * ((double)netmsginput.readShort() / 32767D));
                    sink2Roll = (float)(90D * ((double)netmsginput.readShort() / 32767D));
                    sink2timeWhenStop = tmInterpoEnd;
                    sink2timeWhenStop+= = (long)(1000D * (1200D * ((double)(netmsginput.readUnsignedShort() & 0x7fff) / 32767D)));
                    if(flag)
                    {
                        long l5 = netmsginput.readLong();
                        if(l5 > 0L)
                        {
                            tmInterpoStart-= = l5;
                            tmInterpoEnd-= = l5;
                            sink2timeWhenStop-= = l5;
                            computeInterpolatedDPR(com.maddox.il2.net.NetServerParams.getServerTime());
                        }
                    }
                    Die(actor, timeOfDeath, true, false);
                    return true;
                }
                java.lang.System.out.println("**net bigship unknown cmd " + j1);
                return false;
            }
            int i = netmsginput.readUnsignedByte();
            if((i & 0xe0) == 224)
            {
                int j = 1 + netmsginput.netObjReferenceLen() + 1;
                int l = 2 + netmsginput.netObjReferenceLen() + 1;
                int k1 = netmsginput.available();
                int k2 = i & 0x1f;
                int k3 = k1 - k2 * j;
                int i4 = k3 / l;
                if(i4 < 0 || i4 > 31 || k2 > 31 || k3 % l != 0)
                {
                    java.lang.System.out.println("*** net big0 code:" + i + " szT:" + j + " szF:" + l + " len:" + k1 + " nT:" + k2 + " lenF:" + k3 + " nF:" + i4);
                    return true;
                }
                if(isMirrored())
                {
                    out.unLockAndSet(netmsginput, k2 + i4);
                    out.setIncludeTime(true);
                    postReal(com.maddox.rts.Message.currentRealTime(), out);
                }
                while(--k2 >= 0) 
                {
                    int k4 = netmsginput.readUnsignedByte();
                    com.maddox.rts.NetObj netobj1 = netmsginput.readNetObj();
                    com.maddox.il2.engine.Actor actor1 = netobj1 != null ? ((com.maddox.il2.engine.ActorNet)netobj1).actor() : null;
                    int j6 = netmsginput.readUnsignedByte();
                    Track_Mirror(k4, actor1, j6);
                }
                while(--i4 >= 0) 
                {
                    int l4 = netmsginput.readUnsignedByte();
                    int j5 = netmsginput.readUnsignedByte();
                    com.maddox.rts.NetObj netobj2 = netmsginput.readNetObj();
                    com.maddox.il2.engine.Actor actor2 = netobj2 != null ? ((com.maddox.il2.engine.ActorNet)netobj2).actor() : null;
                    double d3 = -2D + (((double)l4 / 255D) * 7000D) / 1000D;
                    double d4 = 0.001D * (double)(com.maddox.rts.Message.currentGameTime() - com.maddox.il2.net.NetServerParams.getServerTime()) + d3;
                    int k6 = netmsginput.readUnsignedByte();
                    Fire_Mirror(j5, actor2, k6, (float)d4);
                }
                return true;
            }
            if(i == 80)
            {
                int k = 2 + netmsginput.netObjReferenceLen();
                int i1 = netmsginput.available();
                int l1 = i1 / k;
                if(l1 <= 0 || l1 > 256 || i1 % k != 0)
                {
                    java.lang.System.out.println("*** net bigship2 n:" + l1);
                    return true;
                } else
                {
                    out.unLockAndSet(netmsginput, l1);
                    out.setIncludeTime(false);
                    postRealTo(com.maddox.rts.Message.currentRealTime(), masterChannel(), out);
                    return true;
                }
            } else
            {
                java.lang.System.out.println("**net bigship unknown ng cmd " + i);
                return true;
            }
        }

        com.maddox.rts.NetMsgFiltered out;

        public Mirror(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i)
        {
            super(actor, netchannel, i);
            out = new NetMsgFiltered();
        }
    }

    class Master extends com.maddox.il2.engine.ActorNet
    {

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(netmsginput.isGuaranted())
            {
                int i = netmsginput.readUnsignedByte();
                java.lang.System.out.println("MASTER NETINPUT: " + i);
                if(i == 93)
                {
                    com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)netmsginput.readNetObj();
                    java.lang.String s = netmsginput.readUTF();
                    handleLocationRequest(netuser, s);
                    return true;
                }
                if(i != 86)
                    return false;
                i = netmsginput.readUnsignedByte();
                float f = i;
                if(path != null && i != 127 && f < CURRSPEED)
                {
                    CURRSPEED = f;
                    if(com.maddox.il2.game.Mission.isCoop())
                    {
                        computeNewPath();
                        netsendPartsState_needtosend = true;
                    }
                }
                return true;
            }
            if(netmsginput.readUnsignedByte() != 80)
                return false;
            if(dying != 0)
                return true;
            int j = 2 + netmsginput.netObjReferenceLen();
            int k = netmsginput.available();
            int l = k / j;
            if(l <= 0 || l > 256 || k % j != 0)
            {
                java.lang.System.out.println("*** net bigship1 len:" + k);
                return true;
            }
            do
            {
                if(--l < 0)
                    break;
                int i1 = netmsginput.readUnsignedByte();
                if(i1 < 0 || i1 >= parts.length)
                    return true;
                int j1 = netmsginput.readUnsignedByte();
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                if(parts[i1].state != 2)
                {
                    parts[i1].damage+= = (float)((j1 & 0x7f) + 1) / 128F;
                    parts[i1].damageIsFromRight = (j1 & 0x80) != 0;
                    InjurePart(i1, actor, true);
                }
            } while(true);
            return true;
        }

        public Master(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
        }
    }

    class Move extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            validateTowAircraft();
            if(dying == 0)
            {
                long l = com.maddox.rts.Time.tickNext();
                if(com.maddox.il2.game.Mission.isCoop() || com.maddox.il2.game.Mission.isDogfight())
                    l = com.maddox.il2.net.NetServerParams.getServerTime() + (long)com.maddox.rts.Time.tickLen();
                if(path != null)
                {
                    computeInterpolatedDPR(l);
                    setMovablePosition(l);
                } else
                if(computeInterpolatedDPR(l))
                    setPosition();
                boolean flag = false;
                if(wakeupTmr == 0L)
                {
                    for(int j = 0; j < prop.nGuns; j++)
                        if(parts[arms[j].part_idx].state == 0)
                        {
                            arms[j].aime.tick_();
                            flag = true;
                        }

                } else
                {
                    int k = 0;
                    do
                    {
                        if(k >= prop.nGuns)
                            break;
                        if(parts[arms[k].part_idx].state == 0)
                        {
                            flag = true;
                            break;
                        }
                        k++;
                    } while(true);
                    if(wakeupTmr > 0L)
                        wakeupTmr--;
                    else
                    if(++wakeupTmr == 0L)
                        if(isAnyEnemyNear())
                            wakeupTmr = com.maddox.il2.objects.ships.BigshipGeneric.SecsToTicks(com.maddox.il2.objects.ships.BigshipGeneric.Rnd(DELAY_WAKEUP, DELAY_WAKEUP * 1.2F));
                        else
                            wakeupTmr = -com.maddox.il2.objects.ships.BigshipGeneric.SecsToTicks(com.maddox.il2.objects.ships.BigshipGeneric.Rnd(4F, 7F));
                }
                if(flag)
                    send_bufferized_FireCommand();
                if(isNetMirror())
                {
                    mirror_send_bufferized_Damage();
                    if(com.maddox.il2.game.Mission.isCoop() && mustSendSpeedToNet)
                    {
                        mirror_send_speed();
                        mustSendSpeedToNet = false;
                    }
                } else
                if(netsendPartsState_needtosend)
                    send_bufferized_PartsState();
                zutiRefreshBornPlace();
                return true;
            }
            if(dying == 3)
            {
                zutiRefreshBornPlace();
                if(path != null)
                {
                    eraseGuns();
                    return false;
                }
                if(respawnDelay-- > 0L)
                    return true;
                if(!isNetMaster())
                {
                    respawnDelay = 10000L;
                    return true;
                } else
                {
                    wakeupTmr = 0L;
                    makeLive();
                    forgetAllAiming();
                    setDefaultLivePose();
                    setDiedFlag(false);
                    tmInterpoStart = tmInterpoEnd = 0L;
                    bodyDepth = bodyPitch = bodyRoll = 0.0F;
                    bodyDepth0 = bodyPitch0 = bodyRoll0 = 0.0F;
                    bodyDepth1 = bodyPitch1 = bodyRoll1 = 0.0F;
                    setPosition();
                    pos.reset();
                    send_RespawnCommand();
                    return true;
                }
            }
            if(netsendPartsState_needtosend)
                send_bufferized_PartsState();
            long l1 = com.maddox.il2.net.NetServerParams.getServerTime();
            if(dying == 1)
            {
                if(l1 >= tmInterpoEnd)
                {
                    bodyDepth0 = bodyDepth1;
                    bodyPitch0 = bodyPitch1;
                    bodyRoll0 = bodyRoll1;
                    bodyDepth1 = sink2Depth;
                    bodyPitch1 = sink2Pitch;
                    bodyRoll1 = sink2Roll;
                    tmInterpoStart = tmInterpoEnd;
                    tmInterpoEnd = sink2timeWhenStop;
                    dying = 2;
                }
            } else
            if(l1 >= tmInterpoEnd)
            {
                bodyDepth0 = bodyDepth1 = sink2Depth;
                bodyPitch0 = bodyPitch1 = sink2Pitch;
                bodyRoll0 = bodyRoll1 = sink2Roll;
                tmInterpoStart = tmInterpoEnd = 0L;
                dying = 3;
            }
            if((com.maddox.rts.Time.tickCounter() & 0x63) == 0 && dsmoks != null)
            {
                for(int i = 0; i < dsmoks.length; i++)
                    if(dsmoks[i] != null && dsmoks[i].pipe != null && dsmoks[i].pipe.pos.getAbsPoint().z < -4.891D)
                    {
                        com.maddox.il2.engine.Eff3DActor.finish(dsmoks[i].pipe);
                        dsmoks[i].pipe = null;
                    }

            }
            computeInterpolatedDPR(l1);
            if(path != null)
                setMovablePosition(timeOfDeath);
            else
                setPosition();
            zutiRefreshBornPlace();
            return true;
        }

        Move()
        {
        }
    }

    static class HookNamedZ0 extends com.maddox.il2.engine.HookNamed
    {

        public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
        {
            super.computePos(actor, loc, loc1);
            loc1.getPoint().z = 0.25D;
        }

        public HookNamedZ0(com.maddox.il2.engine.ActorMesh actormesh, java.lang.String s)
        {
            super(actormesh, s);
        }

        public HookNamedZ0(com.maddox.il2.engine.Mesh mesh, java.lang.String s)
        {
            super(mesh, s);
        }
    }

    public static class Pipe
    {

        private com.maddox.il2.engine.Eff3DActor pipe;
        private int part_idx;





        public Pipe()
        {
            pipe = null;
            part_idx = -1;
        }
    }

    private static class Segment
    {

        public com.maddox.JGP.Point3d posIn;
        public com.maddox.JGP.Point3d posOut;
        public float length;
        public long timeIn;
        public long timeOut;
        public float speedIn;
        public float speedOut;
        public boolean slidersOn;

        private Segment()
        {
        }

    }

    public static class Part
    {

        private float damage;
        private com.maddox.il2.engine.Actor mirror_initiator;
        private com.maddox.JGP.Point3d shotpointOffs;
        private boolean damageIsFromRight;
        private int state;
        com.maddox.il2.objects.ships.ShipPartProperties pro;











        public Part()
        {
            shotpointOffs = new Point3d();
            damageIsFromRight = false;
        }
    }

    public static class FiringDevice
    {

        private int gun_idx;
        private int part_idx;
        private com.maddox.il2.objects.weapons.Gun gun;
        private com.maddox.il2.ai.ground.Aim aime;
        private float headYaw;
        private float gunPitch;
        private com.maddox.il2.engine.Actor enemy;
        private double timeWhenFireS;
        private int shotpointIdx;



















        public FiringDevice()
        {
        }
    }

    public static class TmpTrackOrFireInfo
    {

        private int gun_idx;
        private com.maddox.il2.engine.Actor enemy;
        private double timeWhenFireS;
        private int shotpointIdx;









        public TmpTrackOrFireInfo()
        {
        }
    }

    public static class ShipProperties
    {

        public java.lang.String meshName;
        public java.lang.String soundName;
        public int WEAPONS_MASK;
        public int HITBY_MASK;
        public float ATTACK_MAX_DISTANCE;
        public float SLIDER_DIST;
        public float SPEED;
        public float DELAY_RESPAWN_MIN;
        public float DELAY_RESPAWN_MAX;
        public com.maddox.il2.objects.ships.ShipPartProperties propparts[];
        public int nGuns;
        public com.maddox.il2.objects.ships.AirportProperties propAirport;

        public ShipProperties()
        {
            meshName = null;
            soundName = null;
            WEAPONS_MASK = 4;
            HITBY_MASK = -2;
            ATTACK_MAX_DISTANCE = 1.0F;
            SLIDER_DIST = 1.0F;
            SPEED = 1.0F;
            DELAY_RESPAWN_MIN = 15F;
            DELAY_RESPAWN_MAX = 30F;
            propparts = null;
            propAirport = null;
        }
    }

    public static class ShipPartProperties
    {

        public boolean isItLifeKeeper()
        {
            return dmgDepth >= 0.0F;
        }

        public boolean haveGun()
        {
            return gun_idx >= 0;
        }

        public java.lang.String baseChunkName;
        public int baseChunkIdx;
        public com.maddox.JGP.Point3f partOffs;
        public float partR;
        public java.lang.String additCollisChunkName[];
        public int additCollisChunkIdx[];
        public com.maddox.il2.ai.StrengthProperties stre;
        public float dmgDepth;
        public float dmgPitch;
        public float dmgRoll;
        public float dmgTime;
        public float BLACK_DAMAGE;
        public int gun_idx;
        public java.lang.Class gunClass;
        public int WEAPONS_MASK;
        public boolean TRACKING_ONLY;
        public float ATTACK_MAX_DISTANCE;
        public float ATTACK_MAX_RADIUS;
        public float ATTACK_MAX_HEIGHT;
        public int ATTACK_FAST_TARGETS;
        public float FAST_TARGETS_ANGLE_ERROR;
        public com.maddox.il2.ai.AnglesRange HEAD_YAW_RANGE;
        public float HEAD_STD_YAW;
        public float _HEAD_MIN_YAW;
        public float _HEAD_MAX_YAW;
        public float GUN_MIN_PITCH;
        public float GUN_STD_PITCH;
        public float GUN_MAX_PITCH;
        public float HEAD_MAX_YAW_SPEED;
        public float GUN_MAX_PITCH_SPEED;
        public float DELAY_AFTER_SHOOT;
        public float CHAINFIRE_TIME;
        public java.lang.String headChunkName;
        public java.lang.String gunChunkName;
        public int headChunkIdx;
        public int gunChunkIdx;
        public com.maddox.JGP.Point3d fireOffset;
        public com.maddox.il2.engine.Orient fireOrient;
        public java.lang.String gunShellStartHookName;

        public ShipPartProperties()
        {
            baseChunkName = null;
            baseChunkIdx = -1;
            partOffs = null;
            partR = 1.0F;
            additCollisChunkName = null;
            additCollisChunkIdx = null;
            stre = new StrengthProperties();
            dmgDepth = -1F;
            dmgPitch = 0.0F;
            dmgRoll = 0.0F;
            dmgTime = 1.0F;
            BLACK_DAMAGE = 0.0F;
            gunClass = null;
            WEAPONS_MASK = 4;
            TRACKING_ONLY = false;
            ATTACK_MAX_DISTANCE = 1.0F;
            ATTACK_MAX_RADIUS = 1.0F;
            ATTACK_MAX_HEIGHT = 1.0F;
            ATTACK_FAST_TARGETS = 1;
            FAST_TARGETS_ANGLE_ERROR = 0.0F;
            HEAD_YAW_RANGE = new AnglesRange(-1F, 1.0F);
            HEAD_STD_YAW = 0.0F;
            _HEAD_MIN_YAW = -1F;
            _HEAD_MAX_YAW = -1F;
            GUN_MIN_PITCH = -20F;
            GUN_STD_PITCH = -18F;
            GUN_MAX_PITCH = -15F;
            HEAD_MAX_YAW_SPEED = 720F;
            GUN_MAX_PITCH_SPEED = 60F;
            DELAY_AFTER_SHOOT = 1.0F;
            CHAINFIRE_TIME = 0.0F;
            headChunkName = null;
            gunChunkName = null;
            headChunkIdx = -1;
            gunChunkIdx = -1;
            gunShellStartHookName = null;
        }
    }


    public com.maddox.il2.objects.ships.ShipProperties getShipProp()
    {
        return prop;
    }

    public static final double Rnd(double d, double d1)
    {
        return com.maddox.il2.ai.World.Rnd().nextDouble(d, d1);
    }

    public static final float Rnd(float f, float f1)
    {
        return com.maddox.il2.ai.World.Rnd().nextFloat(f, f1);
    }

    public static final int Rnd(int i, int j)
    {
        return com.maddox.il2.ai.World.Rnd().nextInt(i, j);
    }

    private boolean RndB(float f)
    {
        return com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < f;
    }

    public static final float KmHourToMSec(float f)
    {
        return f / 3.6F;
    }

    private static final long SecsToTicks(float f)
    {
        long l = (long)(0.5D + (double)(f / com.maddox.rts.Time.tickLenFs()));
        return l >= 1L ? l : 1L;
    }

    protected final boolean Head360(com.maddox.il2.objects.ships.FiringDevice firingdevice)
    {
        return parts[firingdevice.part_idx].pro.HEAD_YAW_RANGE.fullcircle();
    }

    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        if(actor instanceof com.maddox.il2.objects.bridges.BridgeSegment)
        {
            if(dying != 0)
                aflag[0] = false;
            return;
        }
        if(path == null && (actor instanceof com.maddox.il2.engine.ActorMesh) && ((com.maddox.il2.engine.ActorMesh)actor).isStaticPos())
        {
            aflag[0] = false;
            return;
        } else
        {
            return;
        }
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(dying != 0)
            return;
        if(isNetMirror())
            return;
        if(actor instanceof com.maddox.il2.objects.ships.WeakBody)
            return;
        if((actor instanceof com.maddox.il2.objects.ships.ShipGeneric) || (actor instanceof com.maddox.il2.objects.ships.BigshipGeneric) || (actor instanceof com.maddox.il2.objects.bridges.BridgeSegment))
            Die(null, -1L, true, true);
    }

    private int findNotDeadPartByShotChunk(java.lang.String s)
    {
        if(s == null || s == "")
            return -2;
        int i = hierMesh().chunkFindCheck(s);
        if(i < 0)
            return -2;
label0:
        for(int j = 0; j < parts.length; j++)
        {
            if(parts[j].state == 2)
                continue;
            if(i == parts[j].pro.baseChunkIdx)
                return j;
            int k = 0;
            do
            {
                if(k >= parts[j].pro.additCollisChunkIdx.length)
                    continue label0;
                if(i == parts[j].pro.additCollisChunkIdx[k])
                    return j;
                k++;
            } while(true);
        }

        return -1;
    }

    private void computeNewPath()
    {
        if(path == null || dying != 0 || com.maddox.il2.game.Mission.isDogfight())
            return;
        printPath("Before:");
        com.maddox.il2.objects.ships.Segment segment = (com.maddox.il2.objects.ships.Segment)path.get(cachedSeg);
        long l = 0L;
        long l1 = com.maddox.rts.Time.tickNext();
        if(com.maddox.il2.game.Mission.isCoop() || com.maddox.il2.game.Mission.isDogfight())
            l1 = com.maddox.il2.net.NetServerParams.getServerTime();
        if((segment.timeIn > l1 || !isTurning) && (segment.speedIn > CURRSPEED || segment.speedOut > CURRSPEED))
        {
            if(com.maddox.il2.game.Mission.isCoop())
                mustSendSpeedToNet = true;
            float f = 0.0F;
            if(l1 >= segment.timeIn)
            {
                long l2 = segment.timeOut - segment.timeIn;
                long l4 = l1 - segment.timeIn;
                float f1 = segment.speedOut - segment.speedIn;
                f = segment.speedIn + f1 * (float)((double)l4 / (double)l2);
            }
            if(f > CURRSPEED)
                segment.speedIn = CURRSPEED;
            else
                segment.speedIn = f;
            if(segment.speedOut > CURRSPEED)
                segment.speedOut = CURRSPEED;
            com.maddox.JGP.Point3d point3d = new Point3d();
            point3d.x = initLoc.getX();
            point3d.y = initLoc.getY();
            point3d.z = initLoc.getZ();
            segment.posIn.set(point3d);
            if(segment.timeIn < l1)
                segment.timeIn = l1;
            double d = segment.posIn.distance(segment.posOut);
            l = segment.timeOut;
            segment.timeOut = segment.timeIn + (long)(1000D * ((2D * d) / (double)java.lang.Math.abs(segment.speedOut + segment.speedIn)));
            segment.length = (float)d;
            segment.slidersOn = false;
        } else
        {
            l = segment.timeOut;
        }
        if(isTurningBackward && (segment.speedIn > CURRSPEED || segment.speedOut > CURRSPEED))
            mustRecomputePath = true;
        int i = cachedSeg;
        for(i++; i <= path.size() - 1; i++)
        {
            com.maddox.il2.objects.ships.Segment segment1 = (com.maddox.il2.objects.ships.Segment)path.get(i);
            long l3 = segment1.timeIn - l;
            segment1.timeIn = segment.timeOut + l3;
            segment1.posIn = segment.posOut;
            if(segment1.speedIn > CURRSPEED)
            {
                if(com.maddox.il2.game.Mission.isCoop())
                    mustSendSpeedToNet = true;
                segment1.speedIn = CURRSPEED;
            }
            if(segment1.speedOut > CURRSPEED)
            {
                if(com.maddox.il2.game.Mission.isCoop())
                    mustSendSpeedToNet = true;
                segment1.speedOut = CURRSPEED;
            }
            l = segment1.timeOut;
            segment1.timeOut = segment1.timeIn + (long)(1000D * ((2D * (double)segment1.length) / (double)java.lang.Math.abs(segment1.speedOut + segment1.speedIn)));
            segment = segment1;
        }

        printPath("After:");
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        shot.bodyMaterial = 2;
        if(dying != 0)
            return;
        if(shot.power <= 0.0F)
            return;
        if(isNetMirror() && shot.isMirage())
            return;
        if(wakeupTmr < 0L)
            wakeupTmr = com.maddox.il2.objects.ships.BigshipGeneric.SecsToTicks(com.maddox.il2.objects.ships.BigshipGeneric.Rnd(DELAY_WAKEUP, DELAY_WAKEUP * 1.2F));
        int i = findNotDeadPartByShotChunk(shot.chunkName);
        if(i < 0)
            return;
        float f;
        float f1;
        if(shot.powerType == 1)
        {
            f = parts[i].pro.stre.EXPLHIT_MAX_TNT;
            f1 = parts[i].pro.stre.EXPLHIT_MAX_TNT;
        } else
        {
            f = parts[i].pro.stre.SHOT_MIN_ENERGY;
            f1 = parts[i].pro.stre.SHOT_MAX_ENERGY;
        }
        float f2 = shot.power * com.maddox.il2.objects.ships.BigshipGeneric.Rnd(1.0F, 1.1F);
        if(f2 < f)
            return;
        tmpvd.set(shot.v);
        pos.getAbs().transformInv(tmpvd);
        parts[i].damageIsFromRight = tmpvd.y > 0.0D;
        float f3 = f2 / f1;
        parts[i].damage+= = f3;
        if(isNetMirror() && shot.initiator != null)
            parts[i].mirror_initiator = shot.initiator;
        InjurePart(i, shot.initiator, true);
        if(!com.maddox.il2.game.Mission.isDogfight() && path != null && parts[i].pro.isItLifeKeeper() && parts[i].damage > 0.2F)
        {
            computeSpeedReduction(parts[i].damage);
            computeNewPath();
        }
    }

    private void computeSpeedReduction(float f)
    {
        int i = (int)(f * 128F);
        if(--i < 0)
            i = 0;
        else
        if(i > 127)
            i = 127;
        f = (float)i / 128F;
        float f1 = 0.4F * prop.SPEED + (1.0F - f) * 2.0F * prop.SPEED;
        int j = java.lang.Math.round(f1);
        f1 = j;
        if(f1 < CURRSPEED)
            CURRSPEED = f1;
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if(dying != 0)
            return;
        if(isNetMirror() && explosion.isMirage())
            return;
        if(wakeupTmr < 0L)
            wakeupTmr = com.maddox.il2.objects.ships.BigshipGeneric.SecsToTicks(com.maddox.il2.objects.ships.BigshipGeneric.Rnd(DELAY_WAKEUP, DELAY_WAKEUP * 1.2F));
        float f = explosion.power;
        com.maddox.il2.ai.Explosion _tmp = explosion;
        if(explosion.powerType == 2 && explosion.chunkName != null)
            f *= 0.45F;
        int i = -1;
        if(explosion.chunkName != null)
        {
            int j = findNotDeadPartByShotChunk(explosion.chunkName);
            if(j >= 0)
            {
                float f1 = f;
                f1 *= com.maddox.il2.objects.ships.BigshipGeneric.Rnd(1.0F, 1.1F) * com.maddox.il2.game.Mission.BigShipHpDiv();
                if(f1 >= parts[j].pro.stre.EXPLHIT_MIN_TNT)
                {
                    i = j;
                    p1.set(explosion.p);
                    pos.getAbs().transformInv(p1);
                    parts[j].damageIsFromRight = p1.y < 0.0D;
                    float f2 = f1 / parts[j].pro.stre.EXPLHIT_MAX_TNT;
                    parts[j].damage+= = f2;
                    if(isNetMirror() && explosion.initiator != null)
                        parts[j].mirror_initiator = explosion.initiator;
                    InjurePart(j, explosion.initiator, true);
                    if(!com.maddox.il2.game.Mission.isDogfight() && path != null && parts[j].pro.isItLifeKeeper() && parts[j].damage > 0.2F)
                    {
                        computeSpeedReduction(parts[j].damage);
                        computeNewPath();
                    }
                }
            }
        }
        com.maddox.il2.engine.Loc loc = pos.getAbs();
        p1.set(explosion.p);
        pos.getAbs().transformInv(p1);
        boolean flag = p1.y < 0.0D;
        for(int k = 0; k < parts.length; k++)
        {
            if(k == i || parts[k].state == 2)
                continue;
            p1.set(parts[k].pro.partOffs);
            loc.transform(p1);
            float f3 = parts[k].pro.partR;
            float f4 = (float)(p1.distance(explosion.p) - (double)f3);
            float f5 = explosion.receivedTNT_1meter(p1, f3);
            f5 *= com.maddox.il2.objects.ships.BigshipGeneric.Rnd(1.0F, 1.1F) * com.maddox.il2.game.Mission.BigShipHpDiv();
            if(f5 < parts[k].pro.stre.EXPLNEAR_MIN_TNT)
                continue;
            parts[k].damageIsFromRight = flag;
            float f6 = f5 / parts[k].pro.stre.EXPLNEAR_MAX_TNT;
            parts[k].damage+= = f6;
            if(isNetMirror() && explosion.initiator != null)
                parts[k].mirror_initiator = explosion.initiator;
            InjurePart(k, explosion.initiator, true);
            if(!com.maddox.il2.game.Mission.isDogfight() && path != null && parts[k].pro.isItLifeKeeper() && parts[k].damage > 0.2F)
            {
                computeSpeedReduction(parts[k].damage);
                computeNewPath();
            }
        }

    }

    private void recomputeShotpoints()
    {
        if(shotpoints == null || shotpoints.length < 1 + parts.length)
            shotpoints = new int[1 + parts.length];
        numshotpoints = 0;
        if(dying != 0)
            return;
        numshotpoints = 1;
        shotpoints[0] = 0;
        for(int i = 0; i < parts.length; i++)
        {
            if(parts[i].state == 2)
                continue;
            int j;
            if(parts[i].pro.isItLifeKeeper())
            {
                j = parts[i].pro.baseChunkIdx;
            } else
            {
                if(!parts[i].pro.haveGun())
                    continue;
                j = parts[i].pro.gunChunkIdx;
            }
            shotpoints[numshotpoints] = i + 1;
            hierMesh().setCurChunk(j);
            hierMesh().getChunkLocObj(tmpL);
            parts[i].shotpointOffs.set(tmpL.getPoint());
            numshotpoints++;
        }

    }

    private boolean visualsInjurePart(int i, boolean flag)
    {
        if(!flag)
        {
            if(parts[i].state == 2)
            {
                parts[i].damage = 1.0F;
                return false;
            }
            if(parts[i].damage < parts[i].pro.BLACK_DAMAGE)
                return false;
            netsendDrown_nparts = 0;
            netsendDrown_depth = 0.0F;
            netsendDrown_pitch = 0.0F;
            netsendDrown_roll = 0.0F;
            netsendDrown_timeS = 0.0F;
            if(parts[i].damage < 1.0F)
            {
                if(parts[i].state == 1)
                    return false;
                parts[i].state = 1;
            } else
            {
                parts[i].damage = 1.0F;
                parts[i].state = 2;
            }
            if(parts[i].pro.isItLifeKeeper())
            {
                netsendDrown_nparts++;
                netsendDrown_depth += com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0.8F, 1.0F) * parts[i].pro.dmgDepth;
                netsendDrown_pitch += com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0.8F, 1.0F) * parts[i].pro.dmgPitch;
                netsendDrown_roll = (float)((double)netsendDrown_roll + (double)(com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0.8F, 1.0F) * parts[i].pro.dmgRoll) * (parts[i].damageIsFromRight ? -1D : 1.0D));
                netsendDrown_timeS += com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0.7F, 1.3F) * parts[i].pro.dmgTime;
            }
        }
        if(parts[i].pro.haveGun())
        {
            arms[parts[i].pro.gun_idx].aime.forgetAiming();
            arms[parts[i].pro.gun_idx].enemy = null;
        }
        int ai[] = hierMesh().getSubTreesSpec(parts[i].pro.baseChunkName);
        for(int j = 0; j < ai.length; j++)
        {
            hierMesh().setCurChunk(ai[j]);
            if(!hierMesh().isChunkVisible())
                continue;
            for(int l = 0; l < parts.length; l++)
            {
                if(l == i || parts[l].state == 2 || ai[j] != parts[l].pro.baseChunkIdx)
                    continue;
                if(!flag && parts[l].state == 0 && parts[l].pro.isItLifeKeeper())
                {
                    netsendDrown_nparts++;
                    netsendDrown_depth += com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0.8F, 1.0F) * parts[l].pro.dmgDepth;
                    netsendDrown_pitch += com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0.8F, 1.0F) * parts[l].pro.dmgPitch;
                    netsendDrown_roll = (float)((double)netsendDrown_roll + (double)(com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0.8F, 1.0F) * parts[l].pro.dmgRoll) * (parts[l].damageIsFromRight ? -1D : 1.0D));
                    netsendDrown_timeS += com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0.7F, 1.3F) * parts[l].pro.dmgTime;
                }
                parts[l].damage = flag ? 0.0F : 1.0F;
                parts[l].mirror_initiator = null;
                parts[l].state = 2;
                if(parts[l].pro.haveGun())
                {
                    arms[parts[l].pro.gun_idx].aime.forgetAiming();
                    arms[parts[l].pro.gun_idx].enemy = null;
                }
            }

            if(hierMesh().chunkName().endsWith("_x") || hierMesh().chunkName().endsWith("_X"))
            {
                hierMesh().chunkVisible(false);
                continue;
            }
            java.lang.String s = hierMesh().chunkName() + "_dmg";
            int l1 = hierMesh().chunkFindCheck(s);
            if(l1 >= 0)
            {
                hierMesh().chunkVisible(false);
                hierMesh().chunkVisible(s, true);
            }
        }

        if(pipes != null)
        {
            boolean flag1 = false;
            for(int i1 = 0; i1 < pipes.length; i1++)
            {
                if(pipes[i1] == null)
                    continue;
                if(pipes[i1].pipe == null)
                {
                    pipes[i1] = null;
                    continue;
                }
                int i2 = pipes[i1].part_idx;
                if(parts[i2].state == 0)
                {
                    flag1 = true;
                } else
                {
                    pipes[i1].pipe._finish();
                    pipes[i1].pipe = null;
                    pipes[i1] = null;
                }
            }

            if(!flag1)
            {
                for(int j1 = 0; j1 < pipes.length; j1++)
                    if(pipes[j1] != null)
                        pipes[j1] = null;

                pipes = null;
            }
        }
        if(dsmoks != null)
        {
            for(int k = 0; k < dsmoks.length; k++)
            {
                if(dsmoks[k] == null || dsmoks[k].pipe != null)
                    continue;
                int k1 = dsmoks[k].part_idx;
                if(parts[k1].state == 0)
                    continue;
                java.lang.String s1 = parts[k1].pro.baseChunkName;
                com.maddox.il2.engine.Loc loc = new Loc();
                hierMesh().setCurChunk(s1);
                hierMesh().getChunkLocObj(loc);
                float f = parts[k1].pro.stre.EXPLNEAR_MIN_TNT;
                java.lang.String s2 = "Effects/Smokes/Smoke";
                if(parts[k1].pro.haveGun())
                {
                    s2 = s2 + "Gun";
                    if(f < 4F)
                        s2 = s2 + "Tiny";
                    else
                    if(f < 24F)
                        s2 = s2 + "Small";
                    else
                    if(f < 32F)
                        s2 = s2 + "Medium";
                    else
                    if(f < 45F)
                        s2 = s2 + "Large";
                    else
                        s2 = s2 + "Huge";
                    dsmoks[k].pipe = com.maddox.il2.engine.Eff3DActor.New(this, null, loc, 1.0F, s2 + ".eff", 600F);
                    com.maddox.il2.engine.Eff3DActor.New(this, null, loc, 1.0F, s2 + "Fire.eff", 120F);
                    continue;
                }
                s2 = s2 + "Ship";
                if(f < 24F)
                    s2 = s2 + "Tiny";
                else
                if(f < 49F)
                    s2 = s2 + "Small";
                else
                if(f < 70F)
                    s2 = s2 + "Medium";
                else
                if(f == 70F)
                    s2 = s2 + "Large";
                else
                if(f < 130F)
                    s2 = s2 + "Huge";
                else
                if(f < 3260F)
                    s2 = s2 + "Enormous";
                else
                    s2 = s2 + "Invulnerable";
                dsmoks[k].pipe = com.maddox.il2.engine.Eff3DActor.New(this, null, loc, 1.1F, s2 + ".eff", -1F);
            }

        }
        recomputeShotpoints();
        return true;
    }

    void master_sendDrown(float f, float f1, float f2, float f3)
    {
        if(!net.isMirrored())
            return;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(100);
            float f4 = f / 1000F;
            if(f4 <= 0.0F)
                f4 = 0.0F;
            if(f4 >= 1.0F)
                f4 = 1.0F;
            int i = (int)(f4 * 32767F);
            if(i > 32767)
                i = 32767;
            if(i < 0)
                i = 0;
            netmsgguaranted.writeShort(i);
            f4 = f1 / 90F;
            if(f4 <= -1F)
                f4 = -1F;
            if(f4 >= 1.0F)
                f4 = 1.0F;
            i = (int)(f4 * 32767F);
            if(i > 32767)
                i = 32767;
            if(i < -32767)
                i = -32767;
            netmsgguaranted.writeShort(i);
            f4 = f2 / 90F;
            if(f4 <= -1F)
                f4 = -1F;
            if(f4 >= 1.0F)
                f4 = 1.0F;
            i = (int)(f4 * 32767F);
            if(i > 32767)
                i = 32767;
            if(i < -32767)
                i = -32767;
            netmsgguaranted.writeShort(i);
            f4 = f3 / 1200F;
            if(f4 <= 0.0F)
                f4 = 0.0F;
            if(f4 >= 1.0F)
                f4 = 1.0F;
            i = (int)(f4 * 32767F);
            if(i > 32767)
                i = 32767;
            if(i < 0)
                i = 0;
            netmsgguaranted.writeShort(i);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void InjurePart(int i, com.maddox.il2.engine.Actor actor, boolean flag)
    {
        if(isNetMirror())
            return;
        if(!visualsInjurePart(i, false))
            return;
        if(dying != 0)
            return;
        boolean flag1 = false;
        int j = 0;
        do
        {
            if(j >= parts.length)
                break;
            if(parts[j].pro.isItLifeKeeper() && parts[j].state == 2)
            {
                flag1 = true;
                break;
            }
            j++;
        } while(true);
        netsendPartsState_needtosend = true;
        if(netsendDrown_nparts > 0)
        {
            netsendDrown_depth += bodyDepth1;
            netsendDrown_pitch += bodyPitch1;
            netsendDrown_roll += bodyRoll1;
            netsendDrown_timeS /= netsendDrown_nparts;
            if(netsendDrown_timeS >= 1200F)
                netsendDrown_timeS = 1200F;
            tmInterpoStart = com.maddox.il2.net.NetServerParams.getServerTime();
            tmInterpoEnd = tmInterpoStart + (long)(netsendDrown_timeS * 1000F);
            bodyDepth0 = bodyDepth;
            bodyPitch0 = bodyPitch;
            bodyRoll0 = bodyRoll;
            bodyDepth1 = netsendDrown_depth;
            bodyPitch1 = netsendDrown_pitch;
            bodyRoll1 = netsendDrown_roll;
            master_sendDrown(netsendDrown_depth, netsendDrown_pitch, netsendDrown_roll, netsendDrown_timeS);
        }
        if(!flag1)
        {
            return;
        } else
        {
            Die(actor, -1L, flag, true);
            return;
        }
    }

    private float computeSeaDepth(com.maddox.JGP.Point3d point3d)
    {
        for(float f = 5F; f <= 355F; f += 10F)
        {
            for(float f1 = 0.0F; f1 < 360F; f1 += 30F)
            {
                float f2 = f * com.maddox.JGP.Geom.cosDeg(f1);
                float f3 = f * com.maddox.JGP.Geom.sinDeg(f1);
                f2 += (float)point3d.x;
                f3 += (float)point3d.y;
                if(!com.maddox.il2.ai.World.land().isWater(f2, f3))
                    return 150F * (f / 355F);
            }

        }

        return 1000F;
    }

    private void computeSinkingParams(long l)
    {
        if(path != null)
            setMovablePosition(l);
        else
            setPosition();
        pos.reset();
        float f = computeSeaDepth(pos.getAbsPoint()) * com.maddox.il2.objects.ships.BigshipGeneric.Rnd(1.0F, 1.25F);
        if(f >= 400F)
            f = 400F;
        float f1 = com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0.2F, 0.25F);
        float f2;
        float f3;
        float f4;
        float f5;
        if(f >= 200F)
        {
            f2 = com.maddox.il2.objects.ships.BigshipGeneric.Rnd(90F, 110F);
            f3 = f2 * f1;
            f4 = 50F - com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0.0F, 20F);
            f5 = com.maddox.il2.objects.ships.BigshipGeneric.Rnd(15F, 32F);
            f1 *= 1.6F;
        } else
        {
            f2 = com.maddox.il2.objects.ships.BigshipGeneric.Rnd(30F, 40F);
            f3 = f2 * f1;
            f4 = 4.5F - com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0.0F, 2.5F);
            f5 = com.maddox.il2.objects.ships.BigshipGeneric.Rnd(6F, 13F);
        }
        float f6 = (f - f3) / f1;
        if(f6 < 1.0F)
            f6 = 1.0F;
        float f7 = f6 * f1;
        computeInterpolatedDPR(l);
        bodyDepth0 = bodyDepth;
        bodyPitch0 = bodyPitch;
        bodyRoll0 = bodyRoll;
        bodyDepth1 += f3;
        bodyPitch1 += ((double)bodyPitch1 <= 0.0D ? -1F : 1.0F) * f4;
        bodyRoll1 += ((double)bodyRoll1 <= 0.0D ? -1F : 1.0F) * f5;
        if(bodyPitch1 > 80F)
            bodyPitch1 = 80F;
        if(bodyPitch1 < -80F)
            bodyPitch1 = -80F;
        if(bodyRoll1 > 80F)
            bodyRoll1 = 80F;
        if(bodyRoll1 < -80F)
            bodyRoll1 = -80F;
        tmInterpoStart = l;
        tmInterpoEnd = tmInterpoStart + (long)(f2 * 1000F) * 10L;
        sink2Depth = bodyDepth1 + f7;
        sink2Pitch = bodyPitch1;
        sink2Roll = bodyRoll1;
        sink2timeWhenStop = tmInterpoEnd + (long)(f6 * 1000F);
    }

    private void showExplode()
    {
        com.maddox.il2.objects.effects.Explosions.Antiaircraft_Explode(pos.getAbsPoint());
    }

    private void Die(com.maddox.il2.engine.Actor actor, long l, boolean flag, boolean flag1)
    {
        if(dying != 0)
            return;
        if(l < 0L)
        {
            if(isNetMirror())
                return;
            l = com.maddox.il2.net.NetServerParams.getServerTime();
        }
        dying = 1;
        com.maddox.il2.ai.World.onActorDied(this, actor);
        recomputeShotpoints();
        forgetAllAiming();
        SetEffectsIntens(-1F);
        if(flag1)
            computeSinkingParams(l);
        computeInterpolatedDPR(l);
        if(path != null)
            setMovablePosition(l);
        else
            setPosition();
        pos.reset();
        timeOfDeath = l;
        if(flag)
            showExplode();
        if(flag && isNetMaster())
            send_DeathCommand(actor, null);
        if(airport != null)
            airport.disableBornPlace();
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        eraseGuns();
        if(parts != null)
        {
            for(int i = 0; i < parts.length; i++)
            {
                parts[i].mirror_initiator = null;
                parts[i] = null;
            }

            parts = null;
        }
        super.destroy();
    }

    private boolean isAnyEnemyNear()
    {
        com.maddox.il2.ai.ground.NearestEnemies.set(WeaponsMask());
        com.maddox.il2.engine.Actor actor = com.maddox.il2.ai.ground.NearestEnemies.getAFoundEnemy(pos.getAbsPoint(), 2000D, getArmy());
        return actor != null;
    }

    private final com.maddox.il2.objects.ships.FiringDevice GetFiringDevice(com.maddox.il2.ai.ground.Aim aim)
    {
        for(int i = 0; i < prop.nGuns; i++)
            if(arms[i] != null && arms[i].aime == aim)
                return arms[i];

        java.lang.System.out.println("Internal error 1: Can't find ship gun.");
        return null;
    }

    private final com.maddox.il2.objects.ships.ShipPartProperties GetGunProperties(com.maddox.il2.ai.ground.Aim aim)
    {
        for(int i = 0; i < prop.nGuns; i++)
            if(arms[i].aime == aim)
                return parts[arms[i].part_idx].pro;

        java.lang.System.out.println("Internal error 2: Can't find ship gun.");
        return null;
    }

    private void setGunAngles(com.maddox.il2.objects.ships.FiringDevice firingdevice, float f, float f1)
    {
        firingdevice.headYaw = f;
        firingdevice.gunPitch = f1;
        com.maddox.il2.objects.ships.ShipPartProperties shippartproperties = parts[firingdevice.part_idx].pro;
        tmpYPR[1] = 0.0F;
        tmpYPR[2] = 0.0F;
        hierMesh().setCurChunk(shippartproperties.headChunkIdx);
        tmpYPR[0] = firingdevice.headYaw;
        hierMesh().chunkSetAngles(tmpYPR);
        hierMesh().setCurChunk(shippartproperties.gunChunkIdx);
        tmpYPR[0] = -(firingdevice.gunPitch - shippartproperties.GUN_STD_PITCH);
        hierMesh().chunkSetAngles(tmpYPR);
    }

    private void eraseGuns()
    {
        if(arms != null)
        {
            for(int i = 0; i < prop.nGuns; i++)
            {
                if(arms[i] == null)
                    continue;
                if(arms[i].aime != null)
                {
                    arms[i].aime.forgetAll();
                    arms[i].aime = null;
                }
                if(arms[i].gun != null)
                {
                    com.maddox.il2.objects.ships.BigshipGeneric.destroy(arms[i].gun);
                    arms[i].gun = null;
                }
                arms[i].enemy = null;
                arms[i] = null;
            }

            arms = null;
        }
    }

    private void forgetAllAiming()
    {
        if(arms != null)
        {
            for(int i = 0; i < prop.nGuns; i++)
                if(arms[i] != null && arms[i].aime != null)
                {
                    arms[i].aime.forgetAiming();
                    arms[i].enemy = null;
                }

        }
    }

    private void CreateGuns()
    {
        arms = new com.maddox.il2.objects.ships.FiringDevice[prop.nGuns];
        for(int i = 0; i < parts.length; i++)
        {
            if(!parts[i].pro.haveGun())
                continue;
            com.maddox.il2.objects.ships.ShipPartProperties shippartproperties = parts[i].pro;
            int j = shippartproperties.gun_idx;
            arms[j] = new FiringDevice();
            arms[j].gun_idx = j;
            arms[j].part_idx = i;
            arms[j].gun = null;
            try
            {
                arms[j].gun = (com.maddox.il2.objects.weapons.Gun)shippartproperties.gunClass.newInstance();
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("BigShip: Can't create gun '" + shippartproperties.gunClass.getName() + "'");
            }
            arms[j].gun.set(this, shippartproperties.gunShellStartHookName);
            arms[j].gun.loadBullets(-1);
            arms[j].aime = new Aim(this, isNetMirror(), SLOWFIRE_K * shippartproperties.DELAY_AFTER_SHOOT);
            arms[j].enemy = null;
        }

    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    private void initMeshBasedProperties()
    {
        for(int i = 0; i < prop.propparts.length; i++)
        {
            com.maddox.il2.objects.ships.ShipPartProperties shippartproperties = prop.propparts[i];
            if(shippartproperties.baseChunkIdx >= 0)
                continue;
            shippartproperties.baseChunkIdx = hierMesh().chunkFind(shippartproperties.baseChunkName);
            hierMesh().setCurChunk(shippartproperties.baseChunkIdx);
            hierMesh().getChunkLocObj(tmpL);
            tmpL.get(p1);
            shippartproperties.partOffs = new Point3f();
            shippartproperties.partOffs.set(p1);
            shippartproperties.partR = hierMesh().getChunkVisibilityR();
            int j = shippartproperties.additCollisChunkName.length;
            for(int k = 0; k < shippartproperties.additCollisChunkName.length; k++)
                if(hierMesh().chunkFindCheck(shippartproperties.additCollisChunkName[k] + "_dmg") >= 0)
                    j++;

            if(hierMesh().chunkFindCheck(shippartproperties.baseChunkName + "_dmg") >= 0)
                j++;
            shippartproperties.additCollisChunkIdx = new int[j];
            j = 0;
            for(int l = 0; l < shippartproperties.additCollisChunkName.length; l++)
            {
                shippartproperties.additCollisChunkIdx[j++] = hierMesh().chunkFind(shippartproperties.additCollisChunkName[l]);
                int j1 = hierMesh().chunkFindCheck(shippartproperties.additCollisChunkName[l] + "_dmg");
                if(j1 >= 0)
                    shippartproperties.additCollisChunkIdx[j++] = j1;
            }

            int i1 = hierMesh().chunkFindCheck(shippartproperties.baseChunkName + "_dmg");
            if(i1 >= 0)
                shippartproperties.additCollisChunkIdx[j++] = i1;
            if(j != shippartproperties.additCollisChunkIdx.length)
                java.lang.System.out.println("*** bigship: collis internal error");
            if(shippartproperties.haveGun())
            {
                shippartproperties.headChunkIdx = hierMesh().chunkFind(shippartproperties.headChunkName);
                shippartproperties.gunChunkIdx = hierMesh().chunkFind(shippartproperties.gunChunkName);
                hierMesh().setCurChunk(shippartproperties.headChunkIdx);
                hierMesh().getChunkLocObj(tmpL);
                shippartproperties.fireOffset = new Point3d();
                tmpL.get(shippartproperties.fireOffset);
                shippartproperties.fireOrient = new Orient();
                tmpL.get(shippartproperties.fireOrient);
                com.maddox.JGP.Vector3d vector3d = new Vector3d();
                com.maddox.JGP.Vector3d vector3d1 = new Vector3d();
                vector3d.set(1.0D, 0.0D, 0.0D);
                vector3d1.set(1.0D, 0.0D, 0.0D);
                tmpL.transform(vector3d);
                hierMesh().setCurChunk(shippartproperties.gunChunkIdx);
                hierMesh().getChunkLocObj(tmpL);
                tmpL.transform(vector3d1);
                shippartproperties.GUN_STD_PITCH = com.maddox.JGP.Geom.RAD2DEG((float)vector3d.angle(vector3d1));
            }
        }

        initMeshMats();
    }

    private void initMeshMats()
    {
        if(com.maddox.il2.engine.Config.cur.b3dgunners)
        {
            return;
        } else
        {
            hierMesh().materialReplaceToNull("Sailor");
            hierMesh().materialReplaceToNull("Sailor1o");
            hierMesh().materialReplaceToNull("Sailor2p");
            return;
        }
    }

    private void makeLive()
    {
        dying = 0;
        for(int i = 0; i < parts.length; i++)
        {
            parts[i].damage = 0.0F;
            parts[i].state = 0;
            parts[i].pro = prop.propparts[i];
        }

        for(int j = 0; j < hierMesh().chunks(); j++)
        {
            hierMesh().setCurChunk(j);
            boolean flag = !hierMesh().chunkName().endsWith("_dmg");
            if(hierMesh().chunkName().startsWith("ShdwRcv"))
                flag = false;
            hierMesh().chunkVisible(flag);
        }

        recomputeShotpoints();
    }

    private void setDefaultLivePose()
    {
        int i = hierMesh().hookFind("Ground_Level");
        if(i != -1)
        {
            com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
            hierMesh().hookMatrix(i, matrix4d);
        }
        for(int j = 0; j < arms.length; j++)
        {
            int k = arms[j].part_idx;
            setGunAngles(arms[j], parts[k].pro.HEAD_STD_YAW, parts[k].pro.GUN_STD_PITCH);
        }

        bodyDepth = 0.0F;
        align();
    }

    protected BigshipGeneric()
    {
        this(constr_arg1, constr_arg2);
    }

    private BigshipGeneric(com.maddox.il2.objects.ships.ShipProperties shipproperties, com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(shipproperties.meshName);
        CURRSPEED = 1.0F;
        isTurning = false;
        isTurningBackward = false;
        mustRecomputePath = false;
        mustSendSpeedToNet = false;
        prop = null;
        netsendPartsState_lasttimeMS = 0L;
        netsendPartsState_needtosend = false;
        netsendFire_lasttimeMS = 0L;
        netsendFire_armindex = 0;
        arms = null;
        parts = null;
        shotpoints = null;
        netsendDmg_lasttimeMS = 0L;
        netsendDmg_partindex = 0;
        cachedSeg = 0;
        timeOfDeath = 0L;
        dying = 0;
        respawnDelay = 0L;
        wakeupTmr = 0L;
        DELAY_WAKEUP = 0.0F;
        SKILL_IDX = 2;
        SLOWFIRE_K = 1.0F;
        pipes = null;
        dsmoks = null;
        noseW = null;
        nose = null;
        tail = null;
        o = new Orient();
        rollAmp = 0.7F * (float)com.maddox.il2.game.Mission.curCloudsType();
        rollPeriod = 12345;
        rollWAmp = ((double)rollAmp * 19739.208802178713D) / (double)(180 * rollPeriod);
        pitchAmp = 0.1F * (float)com.maddox.il2.game.Mission.curCloudsType();
        pitchPeriod = 23456;
        pitchWAmp = ((double)pitchAmp * 19739.208802178713D) / (double)(180 * pitchPeriod);
        W = new Vector3d(0.0D, 0.0D, 0.0D);
        N = new Vector3d(0.0D, 0.0D, 1.0D);
        tmpV = new Vector3d();
        initOr = new Orient();
        initLoc = new Loc();
        airport = null;
        towPortNum = -1;
        zutiBornPlace = null;
        zutiIsClassBussy = false;
        zutiIsShipChief = false;
        zutiPosition = null;
        prop = shipproperties;
        CURRSPEED = prop.SPEED;
        initMeshBasedProperties();
        actorspawnarg.setStationary(this);
        path = null;
        collide(true);
        drawing(true);
        tmInterpoStart = tmInterpoEnd = 0L;
        bodyDepth = bodyPitch = bodyRoll = 0.0F;
        bodyDepth0 = bodyPitch0 = bodyRoll0 = 0.0F;
        bodyDepth1 = bodyPitch1 = bodyRoll1 = 0.0F;
        shipYaw = actorspawnarg.orient.getYaw();
        setPosition();
        pos.reset();
        parts = new com.maddox.il2.objects.ships.Part[prop.propparts.length];
        for(int i = 0; i < parts.length; i++)
            parts[i] = new Part();

        makeLive();
        createNetObject(actorspawnarg.netChannel, actorspawnarg.netIdRemote);
        SKILL_IDX = com.maddox.il2.ai.Chief.new_SKILL_IDX;
        SLOWFIRE_K = com.maddox.il2.ai.Chief.new_SLOWFIRE_K;
        DELAY_WAKEUP = com.maddox.il2.ai.Chief.new_DELAY_WAKEUP;
        wakeupTmr = 0L;
        CreateGuns();
        int j = 0;
        for(int k = 0; k < parts.length; k++)
            if(parts[k].pro.isItLifeKeeper() || parts[k].pro.haveGun())
                j++;

        if(j <= 0)
        {
            dsmoks = null;
        } else
        {
            dsmoks = new com.maddox.il2.objects.ships.Pipe[j];
            j = 0;
            for(int l = 0; l < parts.length; l++)
                if(parts[l].pro.isItLifeKeeper() || parts[l].pro.haveGun())
                {
                    dsmoks[j] = new Pipe();
                    dsmoks[j].part_idx = l;
                    dsmoks[j].pipe = null;
                    j++;
                }

        }
        setDefaultLivePose();
        if(!isNetMirror() && prop.nGuns > 0 && DELAY_WAKEUP > 0.0F)
            wakeupTmr = -com.maddox.il2.objects.ships.BigshipGeneric.SecsToTicks(com.maddox.il2.objects.ships.BigshipGeneric.Rnd(2.0F, 7F));
        createAirport();
        if(!interpEnd("move"))
        {
            interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
            com.maddox.il2.engine.InterpolateAdapter.forceListener(this);
        }
    }

    public BigshipGeneric(java.lang.String s, int i, com.maddox.rts.SectFile sectfile, java.lang.String s1, com.maddox.rts.SectFile sectfile1, java.lang.String s2)
    {
        CURRSPEED = 1.0F;
        isTurning = false;
        isTurningBackward = false;
        mustRecomputePath = false;
        mustSendSpeedToNet = false;
        prop = null;
        netsendPartsState_lasttimeMS = 0L;
        netsendPartsState_needtosend = false;
        netsendFire_lasttimeMS = 0L;
        netsendFire_armindex = 0;
        arms = null;
        parts = null;
        shotpoints = null;
        netsendDmg_lasttimeMS = 0L;
        netsendDmg_partindex = 0;
        cachedSeg = 0;
        timeOfDeath = 0L;
        dying = 0;
        respawnDelay = 0L;
        wakeupTmr = 0L;
        DELAY_WAKEUP = 0.0F;
        SKILL_IDX = 2;
        SLOWFIRE_K = 1.0F;
        pipes = null;
        dsmoks = null;
        noseW = null;
        nose = null;
        tail = null;
        o = new Orient();
        rollAmp = 0.7F * (float)com.maddox.il2.game.Mission.curCloudsType();
        rollPeriod = 12345;
        rollWAmp = ((double)rollAmp * 19739.208802178713D) / (double)(180 * rollPeriod);
        pitchAmp = 0.1F * (float)com.maddox.il2.game.Mission.curCloudsType();
        pitchPeriod = 23456;
        pitchWAmp = ((double)pitchAmp * 19739.208802178713D) / (double)(180 * pitchPeriod);
        W = new Vector3d(0.0D, 0.0D, 0.0D);
        N = new Vector3d(0.0D, 0.0D, 1.0D);
        tmpV = new Vector3d();
        initOr = new Orient();
        initLoc = new Loc();
        airport = null;
        towPortNum = -1;
        zutiBornPlace = null;
        zutiIsClassBussy = false;
        zutiIsShipChief = false;
        zutiPosition = null;
        zutiIsShipChief = true;
        try
        {
            int j = sectfile.sectionIndex(s1);
            java.lang.String s3 = sectfile.var(j, 0);
            java.lang.Object obj = com.maddox.rts.Spawn.get(s3);
            if(obj == null)
                throw new ActorException("Ship: Unknown class of ship (" + s3 + ")");
            prop = ((com.maddox.il2.objects.ships.SPAWN)obj).proper;
            try
            {
                setMesh(prop.meshName);
            }
            catch(java.lang.RuntimeException runtimeexception)
            {
                super.destroy();
                throw runtimeexception;
            }
            initMeshBasedProperties();
            if(prop.soundName != null)
                newSound(prop.soundName, true);
            setName(s);
            setArmy(i);
            LoadPath(sectfile1, s2);
            cachedSeg = 0;
            tmInterpoStart = tmInterpoEnd = 0L;
            bodyDepth = bodyPitch = bodyRoll = 0.0F;
            bodyDepth0 = bodyPitch0 = bodyRoll0 = 0.0F;
            bodyDepth1 = bodyPitch1 = bodyRoll1 = 0.0F;
            CURRSPEED = 2.0F * prop.SPEED;
            setMovablePosition(com.maddox.il2.net.NetServerParams.getServerTime());
            pos.reset();
            collide(true);
            drawing(true);
            parts = new com.maddox.il2.objects.ships.Part[prop.propparts.length];
            for(int k = 0; k < parts.length; k++)
                parts[k] = new Part();

            makeLive();
            int l = 0;
            for(int i1 = 0; i1 <= 10; i1++)
            {
                java.lang.String s4 = "Vapor";
                if(i1 > 0)
                    s4 = s4 + (i1 - 1);
                if(mesh().hookFind(s4) >= 0)
                    l++;
            }

            if(l <= 0)
            {
                pipes = null;
            } else
            {
                pipes = new com.maddox.il2.objects.ships.Pipe[l];
                l = 0;
                for(int j1 = 0; j1 <= 10; j1++)
                {
                    java.lang.String s5 = "Vapor";
                    if(j1 > 0)
                        s5 = s5 + (j1 - 1);
                    if(mesh().hookFind(s5) < 0)
                        continue;
                    pipes[l] = new Pipe();
                    int j2 = hierMesh().hookParentChunk(s5);
                    if(j2 < 0)
                    {
                        java.lang.System.out.println(" *** Bigship: unexpected error in vapor hook " + s5);
                        pipes = null;
                        break;
                    }
                    int k2;
                    for(k2 = 0; k2 < parts.length && parts[k2].pro.baseChunkIdx != j2; k2++);
                    if(k2 >= parts.length)
                    {
                        java.lang.System.out.println(" *** Bigship: vapor hook '" + s5 + "' MUST be linked to baseChunk");
                        pipes = null;
                        break;
                    }
                    pipes[l].part_idx = k2;
                    com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(this, s5);
                    pipes[l].pipe = com.maddox.il2.engine.Eff3DActor.New(this, hooknamed, null, 1.0F, "Effects/Smokes/SmokePipeShip.eff", -1F);
                    l++;
                }

            }
            wake[2] = wake[1] = wake[0] = null;
            tail = null;
            noseW = null;
            nose = null;
            boolean flag = prop.SLIDER_DIST / 2.5F < 90F;
            if(mesh().hookFind("_Prop") >= 0)
            {
                com.maddox.il2.objects.ships.HookNamedZ0 hooknamedz0 = new HookNamedZ0(this, "_Prop");
                tail = com.maddox.il2.engine.Eff3DActor.New(this, hooknamedz0, null, 1.0F, flag ? "3DO/Effects/Tracers/ShipTrail/PropWakeBoat.eff" : "3DO/Effects/Tracers/ShipTrail/PropWake.eff", -1F);
            }
            if(mesh().hookFind("_Centre") >= 0)
            {
                com.maddox.il2.engine.Loc loc = new Loc();
                com.maddox.il2.engine.Loc loc1 = new Loc();
                com.maddox.il2.engine.HookNamed hooknamed1 = new HookNamed(this, "_Left");
                hooknamed1.computePos(this, new Loc(), loc);
                com.maddox.il2.engine.HookNamed hooknamed2 = new HookNamed(this, "_Right");
                hooknamed2.computePos(this, new Loc(), loc1);
                float f = (float)loc.getPoint().distance(loc1.getPoint());
                com.maddox.il2.objects.ships.HookNamedZ0 hooknamedz0_1 = new HookNamedZ0(this, "_Centre");
                if(mesh().hookFind("_Prop") >= 0)
                {
                    com.maddox.il2.objects.ships.HookNamedZ0 hooknamedz0_3 = new HookNamedZ0(this, "_Prop");
                    com.maddox.il2.engine.Loc loc2 = new Loc();
                    hooknamedz0_1.computePos(this, new Loc(), loc2);
                    com.maddox.il2.engine.Loc loc3 = new Loc();
                    hooknamedz0_3.computePos(this, new Loc(), loc3);
                    float f1 = (float)loc2.getPoint().distance(loc3.getPoint());
                    wake[0] = com.maddox.il2.engine.Eff3DActor.New(this, hooknamedz0_3, new Loc((double)(-f1) * 0.33000000000000002D, 0.0D, 0.0D, 0.0F, 30F, 0.0F), f, flag ? "3DO/Effects/Tracers/ShipTrail/WakeBoat.eff" : "3DO/Effects/Tracers/ShipTrail/Wake.eff", -1F);
                    wake[1] = com.maddox.il2.engine.Eff3DActor.New(this, hooknamedz0_1, new Loc((double)f1 * 0.14999999999999999D, 0.0D, 0.0D, 0.0F, 30F, 0.0F), f, flag ? "3DO/Effects/Tracers/ShipTrail/WakeBoatS.eff" : "3DO/Effects/Tracers/ShipTrail/WakeS.eff", -1F);
                    wake[2] = com.maddox.il2.engine.Eff3DActor.New(this, hooknamedz0_1, new Loc((double)(-f1) * 0.14999999999999999D, 0.0D, 0.0D, 0.0F, 30F, 0.0F), f, flag ? "3DO/Effects/Tracers/ShipTrail/WakeBoatS.eff" : "3DO/Effects/Tracers/ShipTrail/WakeS.eff", -1F);
                } else
                {
                    wake[0] = com.maddox.il2.engine.Eff3DActor.New(this, hooknamedz0_1, new Loc((double)(-f) * 0.29999999999999999D, 0.0D, 0.0D, 0.0F, 30F, 0.0F), f, (double)prop.SLIDER_DIST / 2.5D >= 50D ? "3DO/Effects/Tracers/ShipTrail/Wake.eff" : "3DO/Effects/Tracers/ShipTrail/WakeBoat.eff", -1F);
                }
            }
            if(mesh().hookFind("_Nose") >= 0)
            {
                com.maddox.il2.objects.ships.HookNamedZ0 hooknamedz0_2 = new HookNamedZ0(this, "_Nose");
                noseW = com.maddox.il2.engine.Eff3DActor.New(this, hooknamedz0_2, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 30F, 0.0F), 1.0F, "3DO/Effects/Tracers/ShipTrail/SideWave.eff", -1F);
                nose = com.maddox.il2.engine.Eff3DActor.New(this, hooknamedz0_2, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 30F, 0.0F), 1.0F, flag ? "3DO/Effects/Tracers/ShipTrail/FrontPuffBoat.eff" : "3DO/Effects/Tracers/ShipTrail/FrontPuff.eff", -1F);
            }
            SetEffectsIntens(0.0F);
            int k1 = com.maddox.il2.game.Mission.cur().getUnitNetIdRemote(this);
            com.maddox.rts.NetChannel netchannel = com.maddox.il2.game.Mission.cur().getNetMasterChannel();
            if(netchannel == null)
                net = new Master(this);
            else
            if(k1 != 0)
                net = new Mirror(this, netchannel, k1);
            SKILL_IDX = com.maddox.il2.ai.Chief.new_SKILL_IDX;
            SLOWFIRE_K = com.maddox.il2.ai.Chief.new_SLOWFIRE_K;
            DELAY_WAKEUP = com.maddox.il2.ai.Chief.new_DELAY_WAKEUP;
            wakeupTmr = 0L;
            CreateGuns();
            k1 = 0;
            for(int l1 = 0; l1 < parts.length; l1++)
                if(parts[l1].pro.isItLifeKeeper() || parts[l1].pro.haveGun())
                    k1++;

            if(k1 <= 0)
            {
                dsmoks = null;
            } else
            {
                dsmoks = new com.maddox.il2.objects.ships.Pipe[k1];
                k1 = 0;
                for(int i2 = 0; i2 < parts.length; i2++)
                    if(parts[i2].pro.isItLifeKeeper() || parts[i2].pro.haveGun())
                    {
                        dsmoks[k1] = new Pipe();
                        dsmoks[k1].part_idx = i2;
                        dsmoks[k1].pipe = null;
                        k1++;
                    }

            }
            setDefaultLivePose();
            if(!isNetMirror() && prop.nGuns > 0 && DELAY_WAKEUP > 0.0F)
                wakeupTmr = -com.maddox.il2.objects.ships.BigshipGeneric.SecsToTicks(com.maddox.il2.objects.ships.BigshipGeneric.Rnd(2.0F, 7F));
            createAirport();
            if(!interpEnd("move"))
            {
                interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
                com.maddox.il2.engine.InterpolateAdapter.forceListener(this);
            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Ship creation failure:");
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            throw new ActorException();
        }
    }

    private void SetEffectsIntens(float f)
    {
        if(dying != 0)
            f = -1F;
        if(pipes != null)
        {
            boolean flag = false;
            for(int j = 0; j < pipes.length; j++)
            {
                if(pipes[j] == null)
                    continue;
                if(pipes[j].pipe == null)
                {
                    pipes[j] = null;
                    continue;
                }
                if(f >= 0.0F)
                {
                    pipes[j].pipe._setIntesity(f);
                    flag = true;
                } else
                {
                    pipes[j].pipe._finish();
                    pipes[j].pipe = null;
                    pipes[j] = null;
                }
            }

            if(!flag)
            {
                for(int k = 0; k < pipes.length; k++)
                    if(pipes[k] != null)
                        pipes[k] = null;

                pipes = null;
            }
        }
        for(int i = 0; i < 3; i++)
        {
            if(wake[i] == null)
                continue;
            if(f >= 0.0F)
            {
                wake[i]._setIntesity(f);
            } else
            {
                wake[i]._finish();
                wake[i] = null;
            }
        }

        if(noseW != null)
            if(f >= 0.0F)
            {
                noseW._setIntesity(f);
            } else
            {
                noseW._finish();
                noseW = null;
            }
        if(nose != null)
            if(f >= 0.0F)
            {
                nose._setIntesity(f);
            } else
            {
                nose._finish();
                nose = null;
            }
        if(tail != null)
            if(f >= 0.0F)
            {
                tail._setIntesity(f);
            } else
            {
                tail._finish();
                tail = null;
            }
    }

    private void LoadPath(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
        int i = sectfile.sectionIndex(s);
        if(i < 0)
            throw new ActorException("Ship path: Section [" + s + "] not found");
        int j = sectfile.vars(i);
        if(j < 1)
            throw new ActorException("Ship path must contain at least 2 nodes");
        path = new ArrayList();
        for(int k = 0; k < j; k++)
        {
            java.util.StringTokenizer stringtokenizer = new StringTokenizer(sectfile.line(i, k));
            float f1 = java.lang.Float.valueOf(stringtokenizer.nextToken()).floatValue();
            float f2 = java.lang.Float.valueOf(stringtokenizer.nextToken()).floatValue();
            float f4 = java.lang.Float.valueOf(stringtokenizer.nextToken()).floatValue();
            double d = 0.0D;
            float f8 = 0.0F;
            if(stringtokenizer.hasMoreTokens())
            {
                d = java.lang.Double.valueOf(stringtokenizer.nextToken()).doubleValue();
                if(stringtokenizer.hasMoreTokens())
                {
                    java.lang.Double.valueOf(stringtokenizer.nextToken()).doubleValue();
                    if(stringtokenizer.hasMoreTokens())
                    {
                        f8 = java.lang.Float.valueOf(stringtokenizer.nextToken()).floatValue();
                        if(f8 <= 0.0F)
                            f8 = prop.SPEED;
                    }
                }
            }
            if(f8 <= 0.0F && (k == 0 || k == j - 1))
                f8 = prop.SPEED;
            if(k >= j - 1)
                d = -1D;
            com.maddox.il2.objects.ships.Segment segment12 = new Segment();
            segment12.posIn = new Point3d(f1, f2, 0.0D);
            if(java.lang.Math.abs(d) < 0.10000000000000001D)
            {
                segment12.timeIn = 0L;
            } else
            {
                segment12.timeIn = (long)(d * 60D * 1000D + (d <= 0.0D ? -0.5D : 0.5D));
                if(k == 0 && segment12.timeIn < 0L)
                    segment12.timeIn = -segment12.timeIn;
            }
            segment12.speedIn = f8;
            segment12.slidersOn = true;
            path.add(segment12);
        }

        for(int l = 0; l < path.size() - 1; l++)
        {
            com.maddox.il2.objects.ships.Segment segment2 = (com.maddox.il2.objects.ships.Segment)path.get(l);
            com.maddox.il2.objects.ships.Segment segment5 = (com.maddox.il2.objects.ships.Segment)path.get(l + 1);
            segment2.length = (float)segment2.posIn.distance(segment5.posIn);
        }

        int i1 = 0;
        float f = ((com.maddox.il2.objects.ships.Segment)path.get(i1)).length;
        int l1;
        for(; i1 < path.size() - 1; i1 = l1)
        {
            l1 = i1 + 1;
            do
            {
                com.maddox.il2.objects.ships.Segment segment8 = (com.maddox.il2.objects.ships.Segment)path.get(l1);
                if(segment8.speedIn > 0.0F)
                    break;
                f += segment8.length;
                l1++;
            } while(true);
            if(l1 - i1 <= 1)
                continue;
            float f3 = ((com.maddox.il2.objects.ships.Segment)path.get(i1)).length;
            float f5 = ((com.maddox.il2.objects.ships.Segment)path.get(i1)).speedIn;
            float f6 = ((com.maddox.il2.objects.ships.Segment)path.get(l1)).speedIn;
            for(int j2 = i1 + 1; j2 < l1; j2++)
            {
                com.maddox.il2.objects.ships.Segment segment11 = (com.maddox.il2.objects.ships.Segment)path.get(j2);
                float f10 = f3 / f;
                segment11.speedIn = f5 * (1.0F - f10) + f6 * f10;
                f += segment11.length;
            }

        }

        for(int j1 = 0; j1 < path.size() - 1; j1++)
        {
            com.maddox.il2.objects.ships.Segment segment3 = (com.maddox.il2.objects.ships.Segment)path.get(j1);
            com.maddox.il2.objects.ships.Segment segment6 = (com.maddox.il2.objects.ships.Segment)path.get(j1 + 1);
            if(segment3.timeIn > 0L && segment6.timeIn > 0L)
            {
                com.maddox.il2.objects.ships.Segment segment9 = new Segment();
                segment9.posIn = new Point3d(segment3.posIn);
                segment9.posIn.add(segment6.posIn);
                segment9.posIn.scale(0.5D);
                segment9.timeIn = 0L;
                segment9.speedIn = (segment3.speedIn + segment6.speedIn) * 0.5F;
                path.add(j1 + 1, segment9);
            }
        }

        for(int k1 = 0; k1 < path.size() - 1; k1++)
        {
            com.maddox.il2.objects.ships.Segment segment4 = (com.maddox.il2.objects.ships.Segment)path.get(k1);
            com.maddox.il2.objects.ships.Segment segment7 = (com.maddox.il2.objects.ships.Segment)path.get(k1 + 1);
            segment4.length = (float)segment4.posIn.distance(segment7.posIn);
        }

        com.maddox.il2.objects.ships.Segment segment = (com.maddox.il2.objects.ships.Segment)path.get(0);
        boolean flag = segment.timeIn != 0L;
        long l2 = segment.timeIn;
        for(int i2 = 0; i2 < path.size() - 1; i2++)
        {
            com.maddox.il2.objects.ships.Segment segment1 = (com.maddox.il2.objects.ships.Segment)path.get(i2);
            com.maddox.il2.objects.ships.Segment segment10 = (com.maddox.il2.objects.ships.Segment)path.get(i2 + 1);
            segment1.posOut = new Point3d(segment10.posIn);
            segment10.posIn = segment1.posOut;
            float f7 = segment1.speedIn;
            float f9 = segment10.speedIn;
            float f11 = (f7 + f9) * 0.5F;
            if(flag)
            {
                segment1.speedIn = 0.0F;
                segment1.speedOut = f9;
                float f12 = ((2.0F * segment1.length) / f9) * 1000F + 0.5F;
                segment1.timeIn = l2;
                segment1.timeOut = segment1.timeIn + (long)(int)f12;
                l2 = segment1.timeOut;
                flag = false;
                continue;
            }
            if(segment10.timeIn == 0L)
            {
                segment1.speedIn = f7;
                segment1.speedOut = f9;
                float f13 = (segment1.length / f11) * 1000F + 0.5F;
                segment1.timeIn = l2;
                segment1.timeOut = segment1.timeIn + (long)(int)f13;
                l2 = segment1.timeOut;
                flag = false;
                continue;
            }
            if(segment10.timeIn > 0L)
            {
                float f14 = (segment1.length / f11) * 1000F + 0.5F;
                long l3 = l2 + (long)(int)f14;
                if(l3 >= segment10.timeIn)
                {
                    segment10.timeIn = 0L;
                } else
                {
                    segment1.speedIn = f7;
                    segment1.speedOut = 0.0F;
                    float f15 = ((2.0F * segment1.length) / f7) * 1000F + 0.5F;
                    segment1.timeIn = l2;
                    segment1.timeOut = segment1.timeIn + (long)(int)f15;
                    l2 = segment10.timeIn;
                    flag = true;
                    continue;
                }
            }
            if(segment10.timeIn == 0L)
            {
                segment1.speedIn = f7;
                segment1.speedOut = f9;
                float f16 = (segment1.length / f11) * 1000F + 0.5F;
                segment1.timeIn = l2;
                segment1.timeOut = segment1.timeIn + (long)(int)f16;
                l2 = segment1.timeOut;
                flag = false;
            } else
            {
                segment1.speedIn = f7;
                segment1.speedOut = 0.0F;
                float f17 = ((2.0F * segment1.length) / f7) * 1000F + 0.5F;
                segment1.timeIn = l2;
                segment1.timeOut = segment1.timeIn + (long)(int)f17;
                l2 = segment1.timeOut + -segment10.timeIn;
                flag = true;
            }
        }

        path.remove(path.size() - 1);
    }

    private void printPath(java.lang.String s)
    {
        java.lang.System.out.println("------------ Path: " + s + "  #:" + path.size());
        for(int i = 0; i < path.size(); i++)
        {
            com.maddox.il2.objects.ships.Segment segment = (com.maddox.il2.objects.ships.Segment)path.get(i);
            java.lang.System.out.println(" " + i + ":  len=" + segment.length + " spdIn=" + segment.speedIn + " spdOut=" + segment.speedOut + " tmIn=" + segment.timeIn + " tmOut=" + segment.timeOut);
            java.lang.System.out.println("posIn=" + segment.posIn + " posOut=" + segment.posOut);
        }

        java.lang.System.out.println("------------");
    }

    public void align()
    {
        pos.getAbs(p);
        p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y) - (double)bodyDepth;
        pos.setAbs(p);
    }

    private boolean computeInterpolatedDPR(long l)
    {
        if(tmInterpoStart >= tmInterpoEnd || l >= tmInterpoEnd)
        {
            bodyDepth = bodyDepth1;
            bodyPitch = bodyPitch1;
            bodyRoll = bodyRoll1;
            return false;
        }
        if(l <= tmInterpoStart)
        {
            bodyDepth = bodyDepth0;
            bodyPitch = bodyPitch0;
            bodyRoll = bodyRoll0;
            return true;
        } else
        {
            float f = (float)(l - tmInterpoStart) / (float)(tmInterpoEnd - tmInterpoStart);
            bodyDepth = bodyDepth0 + (bodyDepth1 - bodyDepth0) * f;
            bodyPitch = bodyPitch0 + (bodyPitch1 - bodyPitch0) * f;
            bodyRoll = bodyRoll0 + (bodyRoll1 - bodyRoll0) * f;
            return true;
        }
    }

    private void setMovablePosition(long l)
    {
        if(cachedSeg < 0)
            cachedSeg = 0;
        else
        if(cachedSeg >= path.size())
            cachedSeg = path.size() - 1;
        com.maddox.il2.objects.ships.Segment segment = (com.maddox.il2.objects.ships.Segment)path.get(cachedSeg);
        if(segment.timeIn <= l && l <= segment.timeOut)
        {
            SetEffectsIntens(1.0F);
            setMovablePosition((float)(l - segment.timeIn) / (float)(segment.timeOut - segment.timeIn));
            return;
        }
        if(l > segment.timeOut)
        {
            while(cachedSeg + 1 < path.size()) 
            {
                com.maddox.il2.objects.ships.Segment segment1 = (com.maddox.il2.objects.ships.Segment)path.get(++cachedSeg);
                if(l <= segment1.timeIn)
                {
                    SetEffectsIntens(0.0F);
                    setMovablePosition(0.0F);
                    return;
                }
                if(l <= segment1.timeOut)
                {
                    SetEffectsIntens(1.0F);
                    setMovablePosition((float)(l - segment1.timeIn) / (float)(segment1.timeOut - segment1.timeIn));
                    return;
                }
            }
            SetEffectsIntens(-1F);
            setMovablePosition(1.0F);
            return;
        }
        while(cachedSeg > 0) 
        {
            com.maddox.il2.objects.ships.Segment segment2 = (com.maddox.il2.objects.ships.Segment)path.get(--cachedSeg);
            if(l >= segment2.timeOut)
            {
                SetEffectsIntens(0.0F);
                setMovablePosition(1.0F);
                return;
            }
            if(l >= segment2.timeIn)
            {
                SetEffectsIntens(1.0F);
                setMovablePosition((float)(l - segment2.timeIn) / (float)(segment2.timeOut - segment2.timeIn));
                return;
            }
        }
        SetEffectsIntens(0.0F);
        setMovablePosition(0.0F);
    }

    private void setMovablePosition(float f)
    {
        com.maddox.il2.objects.ships.Segment segment = (com.maddox.il2.objects.ships.Segment)path.get(cachedSeg);
        float f1 = (float)(segment.timeOut - segment.timeIn) * 0.001F;
        float f2 = segment.speedIn;
        float f3 = segment.speedOut;
        float f4 = (f3 - f2) / f1;
        f *= f1;
        float f5 = f2 * f + f4 * f * f * 0.5F;
        isTurning = false;
        isTurningBackward = false;
        int i = cachedSeg;
        float f6 = prop.SLIDER_DIST - (segment.length - f5);
        if(f6 <= 0.0F)
        {
            p1.interpolate(segment.posIn, segment.posOut, (f5 + prop.SLIDER_DIST) / segment.length);
        } else
        {
            isTurning = true;
            do
            {
                if(i + 1 >= path.size())
                {
                    p1.interpolate(segment.posIn, segment.posOut, 1.0F + f6 / segment.length);
                    break;
                }
                segment = (com.maddox.il2.objects.ships.Segment)path.get(++i);
                if(f6 <= segment.length)
                {
                    p1.interpolate(segment.posIn, segment.posOut, f6 / segment.length);
                    break;
                }
                f6 -= segment.length;
            } while(true);
        }
        i = cachedSeg;
        segment = (com.maddox.il2.objects.ships.Segment)path.get(i);
        f6 = prop.SLIDER_DIST - f5;
        if(f6 <= 0.0F || !segment.slidersOn)
        {
            p2.interpolate(segment.posIn, segment.posOut, (f5 - prop.SLIDER_DIST) / segment.length);
        } else
        {
            isTurning = true;
            isTurningBackward = true;
            do
            {
                if(i <= 0)
                {
                    p2.interpolate(segment.posIn, segment.posOut, 0.0F - f6 / segment.length);
                    break;
                }
                segment = (com.maddox.il2.objects.ships.Segment)path.get(--i);
                if(f6 <= segment.length)
                {
                    p2.interpolate(segment.posIn, segment.posOut, 1.0F - f6 / segment.length);
                    break;
                }
                f6 -= segment.length;
            } while(true);
        }
        if(!com.maddox.il2.game.Mission.isDogfight() && !isTurning && mustRecomputePath && (double)f6 < -1.5D * (double)prop.SLIDER_DIST)
        {
            computeNewPath();
            mustRecomputePath = false;
        }
        p.interpolate(p1, p2, 0.5F);
        tmpvd.sub(p1, p2);
        if(tmpvd.lengthSquared() < 0.0010000000474974513D)
        {
            com.maddox.il2.objects.ships.Segment segment1 = (com.maddox.il2.objects.ships.Segment)path.get(cachedSeg);
            tmpvd.sub(segment1.posOut, segment1.posIn);
        }
        float f7 = (float)(java.lang.Math.atan2(tmpvd.y, tmpvd.x) * 57.295779513082323D);
        setPosition(p, f7);
    }

    public void addRockingSpeed(com.maddox.JGP.Vector3d vector3d, com.maddox.JGP.Vector3d vector3d1, com.maddox.JGP.Point3d point3d)
    {
        tmpV.sub(point3d, pos.getAbsPoint());
        o.transformInv(tmpV);
        tmpV.cross(W, tmpV);
        o.transform(tmpV);
        vector3d.add(tmpV);
        vector3d1.set(N);
    }

    private void setPosition(com.maddox.JGP.Point3d point3d, float f)
    {
        shipYaw = f;
        float f1 = (float)(com.maddox.il2.net.NetServerParams.getServerTime() % (long)rollPeriod) / (float)rollPeriod;
        float f2 = 0.05F * (20F - java.lang.Math.abs(bodyPitch));
        if(f2 < 0.0F)
            f2 = 0.0F;
        float f3 = rollAmp * f2 * (float)java.lang.Math.sin((double)(f1 * 2.0F) * 3.1415926535897931D);
        W.x = -rollWAmp * (double)f2 * java.lang.Math.cos((double)(f1 * 2.0F) * 3.1415926535897931D);
        f1 = (float)(com.maddox.il2.net.NetServerParams.getServerTime() % (long)pitchPeriod) / (float)pitchPeriod;
        float f4 = pitchAmp * f2 * (float)java.lang.Math.sin((double)(f1 * 2.0F) * 3.1415926535897931D);
        W.y = -pitchWAmp * (double)f2 * java.lang.Math.cos((double)(f1 * 2.0F) * 3.1415926535897931D);
        o.setYPR(shipYaw, bodyPitch + f4, bodyRoll + f3);
        N.set(0.0D, 0.0D, 1.0D);
        o.transform(N);
        initOr.setYPR(shipYaw, bodyPitch, bodyRoll);
        point3d.z = -bodyDepth;
        pos.setAbs(point3d, o);
        initLoc.set(point3d, initOr);
    }

    private void setPosition()
    {
        o.setYPR(shipYaw, bodyPitch, bodyRoll);
        N.set(0.0D, 0.0D, 1.0D);
        o.transform(N);
        pos.setAbs(o);
        align();
        initLoc.set(pos.getAbs());
    }

    public int WeaponsMask()
    {
        return prop.WEAPONS_MASK;
    }

    public int HitbyMask()
    {
        return prop.HITBY_MASK;
    }

    public int chooseBulletType(com.maddox.il2.engine.BulletProperties abulletproperties[])
    {
        if(dying != 0)
            return -1;
        if(abulletproperties.length == 1)
            return 0;
        if(abulletproperties.length <= 0)
            return -1;
        if(abulletproperties[0].power <= 0.0F)
            return 0;
        if(abulletproperties[1].power <= 0.0F)
            return 1;
        if(abulletproperties[0].cumulativePower > 0.0F)
            return 0;
        if(abulletproperties[1].cumulativePower > 0.0F)
            return 1;
        if(abulletproperties[0].powerType == 0)
            return 0;
        if(abulletproperties[1].powerType == 0)
            return 1;
        return abulletproperties[0].powerType != 1 ? 0 : 1;
    }

    public int chooseShotpoint(com.maddox.il2.engine.BulletProperties bulletproperties)
    {
        if(dying != 0)
            return -1;
        if(numshotpoints <= 0)
            return -1;
        else
            return shotpoints[com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0, numshotpoints - 1)];
    }

    public boolean getShotpointOffset(int i, com.maddox.JGP.Point3d point3d)
    {
        if(dying != 0)
            return false;
        if(numshotpoints <= 0)
            return false;
        if(i == 0)
        {
            if(point3d != null)
                point3d.set(0.0D, 0.0D, 0.0D);
            return true;
        }
        int j = i - 1;
        if(j >= parts.length || j < 0)
            return false;
        if(parts[j].state == 2)
            return false;
        if(!parts[j].pro.isItLifeKeeper() && !parts[j].pro.haveGun())
            return false;
        if(point3d != null)
            point3d.set(parts[j].shotpointOffs);
        return true;
    }

    public float AttackMaxDistance()
    {
        return prop.ATTACK_MAX_DISTANCE;
    }

    private void send_DeathCommand(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel)
    {
        if(!isNetMaster())
            return;
        if(netchannel == null)
            if(com.maddox.il2.game.Mission.isDeathmatch())
            {
                float f = com.maddox.il2.game.Mission.respawnTime("Bigship");
                respawnDelay = com.maddox.il2.objects.ships.BigshipGeneric.SecsToTicks(com.maddox.il2.objects.ships.BigshipGeneric.Rnd(f, f * 1.2F));
            } else
            {
                respawnDelay = 0L;
            }
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(68);
            netmsgguaranted.writeLong(timeOfDeath);
            netmsgguaranted.writeNetObj(actor != null ? ((com.maddox.rts.NetObj) (actor.net)) : null);
            long l = com.maddox.rts.Time.tickNext();
            long l1 = 0L;
            boolean flag = dying == 1;
            double d = (double)(flag ? bodyDepth1 : bodyDepth0) / 1000D;
            if(d <= 0.0D)
                d = 0.0D;
            if(d >= 1.0D)
                d = 1.0D;
            int i = (int)(d * 32767D);
            if(i > 32767)
                i = 32767;
            if(i < 0)
                i = 0;
            netmsgguaranted.writeShort(i);
            d = (double)(flag ? bodyPitch1 : bodyPitch0) / 90D;
            if(d <= -1D)
                d = -1D;
            if(d >= 1.0D)
                d = 1.0D;
            i = (int)(d * 32767D);
            if(i > 32767)
                i = 32767;
            if(i < -32767)
                i = -32767;
            netmsgguaranted.writeShort(i);
            d = (double)(flag ? bodyRoll1 : bodyRoll0) / 90D;
            if(d <= -1D)
                d = -1D;
            if(d >= 1.0D)
                d = 1.0D;
            i = (int)(d * 32767D);
            if(i > 32767)
                i = 32767;
            if(i < -32767)
                i = -32767;
            netmsgguaranted.writeShort(i);
            d = (double)(tmInterpoEnd - tmInterpoStart) / 1000D / 1200D;
            if(flag)
                l1 = l - tmInterpoStart;
            else
                d = 0.0D;
            if(d <= 0.0D)
                d = 0.0D;
            if(d >= 1.0D)
                d = 1.0D;
            i = (int)(d * 32767D);
            if(i > 32767)
                i = 32767;
            if(i < 0)
                i = 0;
            netmsgguaranted.writeShort(i);
            d = (double)sink2Depth / 1000D;
            if(d <= 0.0D)
                d = 0.0D;
            if(d >= 1.0D)
                d = 1.0D;
            i = (int)(d * 32767D);
            if(i > 32767)
                i = 32767;
            if(i < 0)
                i = 0;
            netmsgguaranted.writeShort(i);
            d = (double)sink2Pitch / 90D;
            if(d <= -1D)
                d = -1D;
            if(d >= 1.0D)
                d = 1.0D;
            i = (int)(d * 32767D);
            if(i > 32767)
                i = 32767;
            if(i < -32767)
                i = -32767;
            netmsgguaranted.writeShort(i);
            d = (double)sink2Roll / 90D;
            if(d <= -1D)
                d = -1D;
            if(d >= 1.0D)
                d = 1.0D;
            i = (int)(d * 32767D);
            if(i > 32767)
                i = 32767;
            if(i < -32767)
                i = -32767;
            netmsgguaranted.writeShort(i);
            d = (double)(sink2timeWhenStop - tmInterpoEnd) / 1000D / 1200D;
            if(!flag)
            {
                d = (double)(tmInterpoEnd - tmInterpoStart) / 1000D / 1200D;
                l1 = l - tmInterpoStart;
            }
            if(d <= 0.0D)
                d = 0.0D;
            if(d >= 1.0D)
                d = 1.0D;
            i = (int)(d * 32767D);
            if(i > 32767)
                i = 32767;
            if(i < 0)
                i = 0;
            netmsgguaranted.writeShort(i);
            if(netchannel != null)
                netmsgguaranted.writeLong(l1);
            if(netchannel == null)
                net.post(netmsgguaranted);
            else
                net.postTo(netchannel, netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void send_RespawnCommand()
    {
        if(!isNetMaster() || !com.maddox.il2.game.Mission.isDeathmatch())
            return;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(82);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        netsendPartsState_needtosend = false;
    }

    private void send_bufferized_FireCommand()
    {
        long l;
        int j;
        int k;
        if(!isNetMaster())
            return;
        l = com.maddox.il2.net.NetServerParams.getServerTime();
        long l1 = com.maddox.il2.objects.ships.BigshipGeneric.Rnd(40, 85);
        if(java.lang.Math.abs(l - netsendFire_lasttimeMS) < l1)
            return;
        netsendFire_lasttimeMS = l;
        if(!net.isMirrored())
        {
            for(int i = 0; i < arms.length; i++)
                arms[i].enemy = null;

            netsendFire_armindex = 0;
            return;
        }
        j = 0;
        k = 0;
        int i1;
        for(i1 = 0; i1 < arms.length; i1++)
        {
            int i2 = netsendFire_armindex + i1;
            if(i2 >= arms.length)
                i2 -= arms.length;
            if(arms[i2].enemy == null)
                continue;
            if(parts[arms[i2].part_idx].state != 0)
            {
                java.lang.System.out.println("*** BigShip internal error #0");
                arms[i2].enemy = null;
                continue;
            }
            if(!com.maddox.il2.engine.Actor.isValid(arms[i2].enemy) || !arms[i2].enemy.isNet())
            {
                arms[i2].enemy = null;
                continue;
            }
            if(j >= 15)
                break;
            netsendFire_tmpbuff[j].gun_idx = i2;
            netsendFire_tmpbuff[j].enemy = arms[i2].enemy;
            netsendFire_tmpbuff[j].timeWhenFireS = arms[i2].timeWhenFireS;
            netsendFire_tmpbuff[j].shotpointIdx = arms[i2].shotpointIdx;
            arms[i2].enemy = null;
            if(arms[i2].timeWhenFireS < 0.0D)
                k++;
            j++;
        }

        for(netsendFire_armindex += i1; netsendFire_armindex >= arms.length; netsendFire_armindex -= arms.length);
        if(j <= 0)
            return;
        com.maddox.rts.NetMsgFiltered netmsgfiltered;
        netmsgfiltered = new NetMsgFiltered();
        netmsgfiltered.writeByte(224 + k);
        for(int j1 = 0; j1 < j; j1++)
        {
            double d = netsendFire_tmpbuff[j1].timeWhenFireS;
            if(d < 0.0D)
            {
                netmsgfiltered.writeByte(netsendFire_tmpbuff[j1].gun_idx);
                netmsgfiltered.writeNetObj(netsendFire_tmpbuff[j1].enemy.net);
                netmsgfiltered.writeByte(netsendFire_tmpbuff[j1].shotpointIdx);
                k--;
            }
        }

        if(k != 0)
        {
            java.lang.System.out.println("*** BigShip internal error #5");
            return;
        }
        try
        {
            for(int k1 = 0; k1 < j; k1++)
            {
                double d1 = netsendFire_tmpbuff[k1].timeWhenFireS;
                if(d1 < 0.0D)
                    continue;
                double d2 = (double)l * 0.001D;
                double d3 = (d1 - d2) * 1000D;
                if(d3 <= -2000D)
                    d3 = -2000D;
                if(d3 >= 5000D)
                    d3 = 5000D;
                d3 = (d3 - -2000D) / 7000D;
                int j2 = (int)(d3 * 255D);
                if(j2 < 0)
                    j2 = 0;
                if(j2 > 255)
                    j2 = 255;
                netmsgfiltered.writeByte(j2);
                netmsgfiltered.writeByte(netsendFire_tmpbuff[k1].gun_idx);
                netmsgfiltered.writeNetObj(netsendFire_tmpbuff[k1].enemy.net);
                netmsgfiltered.writeByte(netsendFire_tmpbuff[k1].shotpointIdx);
                netsendFire_tmpbuff[k1].enemy = null;
            }

            netmsgfiltered.setIncludeTime(true);
            net.post(l, netmsgfiltered);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return;
    }

    private void send_bufferized_PartsState()
    {
        if(!isNetMaster())
            return;
        if(!netsendPartsState_needtosend)
            return;
        long l = com.maddox.il2.net.NetServerParams.getServerTime();
        long l1 = com.maddox.il2.objects.ships.BigshipGeneric.Rnd(300, 500);
        if(java.lang.Math.abs(l - netsendPartsState_lasttimeMS) < l1)
            return;
        netsendPartsState_lasttimeMS = l;
        netsendPartsState_needtosend = false;
        if(!net.isMirrored())
            return;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(83);
            if(!com.maddox.il2.game.Mission.isDogfight())
            {
                int i = 127;
                if(path != null && CURRSPEED < prop.SPEED)
                {
                    i = java.lang.Math.round(CURRSPEED);
                    if(i < 0)
                        i = 0;
                    if(i > 126)
                        i = 126;
                }
                netmsgguaranted.writeByte(i);
            }
            int j = (parts.length + 3) / 4;
            int k = 0;
            for(int i1 = 0; i1 < j; i1++)
            {
                int j1 = 0;
                for(int k1 = 0; k1 < 4; k1++)
                {
                    if(k < parts.length)
                    {
                        int i2 = parts[k].state;
                        j1 |= i2 << k1 * 2;
                    }
                    k++;
                }

                netmsgguaranted.writeByte(j1);
            }

            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void bufferize_FireCommand(int i, com.maddox.il2.engine.Actor actor, int j, float f)
    {
        if(!isNetMaster())
            return;
        if(!net.isMirrored())
            return;
        if(!com.maddox.il2.engine.Actor.isValid(actor) || !actor.isNet())
            return;
        if(arms[i].enemy != null && arms[i].timeWhenFireS >= 0.0D)
            return;
        j &= 0xff;
        arms[i].enemy = actor;
        arms[i].shotpointIdx = j;
        if(f < 0.0F)
            arms[i].timeWhenFireS = -1D;
        else
            arms[i].timeWhenFireS = (double)f + (double)com.maddox.il2.net.NetServerParams.getServerTime() * 0.001D;
    }

    private void mirror_send_speed()
    {
        if(!isNetMirror())
            return;
        if(net.masterChannel() instanceof com.maddox.rts.NetChannelInStream)
            return;
        if(!com.maddox.il2.game.Mission.isCoop())
            return;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(86);
            int i = 127;
            if(path != null && CURRSPEED < prop.SPEED)
            {
                i = java.lang.Math.round(CURRSPEED);
                if(i < 0)
                    i = 0;
                if(i > 126)
                    i = 126;
            }
            netmsgguaranted.writeByte(i);
            net.postTo(net.masterChannel(), netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void mirror_send_bufferized_Damage()
    {
        if(!isNetMirror())
            return;
        if(net.masterChannel() instanceof com.maddox.rts.NetChannelInStream)
            return;
        long l = com.maddox.il2.net.NetServerParams.getServerTime();
        long l1 = com.maddox.il2.objects.ships.BigshipGeneric.Rnd(65, 115);
        if(java.lang.Math.abs(l - netsendDmg_lasttimeMS) < l1)
            return;
        netsendDmg_lasttimeMS = l;
        try
        {
            int i = 0;
            com.maddox.rts.NetMsgFiltered netmsgfiltered = null;
            int j;
            for(j = 0; j < parts.length; j++)
            {
                int k = netsendDmg_partindex + j;
                if(k >= parts.length)
                    k -= parts.length;
                if(parts[k].state == 2 || (double)parts[k].damage < 0.0078125D)
                    continue;
                int i1 = (int)(parts[k].damage * 128F);
                if(--i1 < 0)
                    i1 = 0;
                else
                if(i1 > 127)
                    i1 = 127;
                if(parts[k].damageIsFromRight)
                    i1 |= 0x80;
                if(i <= 0)
                {
                    netmsgfiltered = new NetMsgFiltered();
                    netmsgfiltered.writeByte(80);
                }
                com.maddox.il2.engine.Actor actor = parts[k].mirror_initiator;
                if(!com.maddox.il2.engine.Actor.isValid(actor) || !actor.isNet())
                    actor = null;
                parts[k].mirror_initiator = null;
                parts[k].damage = 0.0F;
                netmsgfiltered.writeByte(k);
                netmsgfiltered.writeByte(i1);
                netmsgfiltered.writeNetObj(actor != null ? ((com.maddox.rts.NetObj) (actor.net)) : null);
                if(++i >= 28)
                    break;
            }

            for(netsendDmg_partindex += j; netsendDmg_partindex >= parts.length; netsendDmg_partindex -= parts.length);
            if(i > 0)
            {
                netmsgfiltered.setIncludeTime(false);
                net.postTo(l, net.masterChannel(), netmsgfiltered);
            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void createNetObject(com.maddox.rts.NetChannel netchannel, int i)
    {
        if(netchannel == null)
            net = new Master(this);
        else
            net = new Mirror(this, netchannel, i);
    }

    public void requestLocationOnCarrierDeck(com.maddox.il2.net.NetUser netuser, java.lang.String s)
    {
        if(!isNetMirror())
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(93);
            netmsgguaranted.writeNetObj(netuser);
            netmsgguaranted.writeUTF(s);
            net.postTo(net.masterChannel(), netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void handleLocationRequest(com.maddox.il2.net.NetUser netuser, java.lang.String s)
    {
        try
        {
            java.lang.Class class1 = com.maddox.rts.ObjIO.classForName(s);
            com.maddox.il2.objects.air.NetAircraft netaircraft = (com.maddox.il2.objects.air.NetAircraft)class1.newInstance();
            com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)netaircraft;
            if(aircraft.FM != null);
            aircraft.setFM(1, false);
            if(airport != null)
            {
                com.maddox.il2.engine.Loc loc = airport.requestCell(aircraft);
                postLocationToMirror(netuser, loc);
            }
            aircraft = null;
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void postLocationToMirror(com.maddox.il2.net.NetUser netuser, com.maddox.il2.engine.Loc loc)
    {
        com.maddox.rts.NetChannel netchannel;
        netchannel = null;
        java.util.List list = com.maddox.rts.NetEnv.channels();
        int i = 0;
        do
        {
            if(i >= list.size())
                break;
            netchannel = (com.maddox.rts.NetChannel)list.get(i);
            com.maddox.rts.NetObj netobj = netchannel.getMirror(netuser.idRemote());
            if(netuser == netobj)
                break;
            netchannel = null;
            i++;
        } while(true);
        if(netchannel == null)
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.write(93);
            netmsgguaranted.writeDouble(loc.getX());
            netmsgguaranted.writeDouble(loc.getY());
            netmsgguaranted.writeDouble(loc.getZ());
            netmsgguaranted.writeFloat(loc.getAzimut());
            netmsgguaranted.writeFloat(loc.getTangage());
            netmsgguaranted.writeFloat(loc.getKren());
            net.postTo(netchannel, netmsgguaranted);
        }
        catch(java.io.IOException ioexception)
        {
            ioexception.printStackTrace();
        }
        return;
    }

    public void netFirstUpdate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        netmsgguaranted.writeByte(73);
        netmsgguaranted.writeLong(-1L);
        net.postTo(netchannel, netmsgguaranted);
        if(dying == 0)
            master_sendDrown(bodyDepth1, bodyPitch1, bodyRoll1, (float)(tmInterpoEnd - com.maddox.il2.net.NetServerParams.getServerTime()) * 1000F);
        else
            send_DeathCommand(null, netchannel);
        netsendPartsState_needtosend = true;
    }

    public float getReloadingTime(com.maddox.il2.ai.ground.Aim aim)
    {
        return SLOWFIRE_K * GetGunProperties(aim).DELAY_AFTER_SHOOT;
    }

    public float chainFireTime(com.maddox.il2.ai.ground.Aim aim)
    {
        float f = GetGunProperties(aim).CHAINFIRE_TIME;
        return f > 0.0F ? f * com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0.75F, 1.25F) : 0.0F;
    }

    public float probabKeepSameEnemy(com.maddox.il2.engine.Actor actor)
    {
        return 0.75F;
    }

    public float minTimeRelaxAfterFight()
    {
        return 0.1F;
    }

    public void gunStartParking(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
        com.maddox.il2.objects.ships.ShipPartProperties shippartproperties = parts[firingdevice.part_idx].pro;
        aim.setRotationForParking(firingdevice.headYaw, firingdevice.gunPitch, shippartproperties.HEAD_STD_YAW, shippartproperties.GUN_STD_PITCH, shippartproperties.HEAD_YAW_RANGE, shippartproperties.HEAD_MAX_YAW_SPEED, shippartproperties.GUN_MAX_PITCH_SPEED);
    }

    public void gunInMove(boolean flag, com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
        float f = aim.t();
        float f1 = aim.anglesYaw.getDeg(f);
        float f2 = aim.anglesPitch.getDeg(f);
        setGunAngles(firingdevice, f1, f2);
        pos.inValidate(false);
    }

    public com.maddox.il2.engine.Actor findEnemy(com.maddox.il2.ai.ground.Aim aim)
    {
        if(isNetMirror())
            return null;
        com.maddox.il2.objects.ships.ShipPartProperties shippartproperties = GetGunProperties(aim);
        com.maddox.il2.engine.Actor actor = null;
        switch(shippartproperties.ATTACK_FAST_TARGETS)
        {
        case 0: // '\0'
            com.maddox.il2.ai.ground.NearestEnemies.set(shippartproperties.WEAPONS_MASK, -9999.9F, com.maddox.il2.objects.ships.BigshipGeneric.KmHourToMSec(100F));
            break;

        case 1: // '\001'
            com.maddox.il2.ai.ground.NearestEnemies.set(shippartproperties.WEAPONS_MASK);
            break;

        default:
            com.maddox.il2.ai.ground.NearestEnemies.set(shippartproperties.WEAPONS_MASK, com.maddox.il2.objects.ships.BigshipGeneric.KmHourToMSec(100F), 9999.9F);
            break;
        }
        actor = com.maddox.il2.ai.ground.NearestEnemies.getAFoundEnemy(pos.getAbsPoint(), shippartproperties.ATTACK_MAX_RADIUS, getArmy());
        if(actor == null)
            return null;
        if(!(actor instanceof com.maddox.il2.ai.ground.Prey))
        {
            java.lang.System.out.println("bigship: nearest enemies: non-Prey");
            return null;
        }
        com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
        com.maddox.il2.engine.BulletProperties bulletproperties = null;
        if(firingdevice.gun.prop != null)
        {
            int i = ((com.maddox.il2.ai.ground.Prey)actor).chooseBulletType(firingdevice.gun.prop.bullet);
            if(i < 0)
                return null;
            bulletproperties = firingdevice.gun.prop.bullet[i];
        }
        int j = ((com.maddox.il2.ai.ground.Prey)actor).chooseShotpoint(bulletproperties);
        if(j < 0)
        {
            return null;
        } else
        {
            aim.shotpoint_idx = j;
            return actor;
        }
    }

    public boolean enterToFireMode(int i, com.maddox.il2.engine.Actor actor, float f, com.maddox.il2.ai.ground.Aim aim)
    {
        if(!isNetMirror())
        {
            com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
            bufferize_FireCommand(firingdevice.gun_idx, actor, aim.shotpoint_idx, i != 0 ? f : -1F);
        }
        return true;
    }

    private void Track_Mirror(int i, com.maddox.il2.engine.Actor actor, int j)
    {
        if(actor == null)
            return;
        if(arms == null || i < 0 || i >= arms.length || arms[i].aime == null)
            return;
        if(parts[arms[i].part_idx].state != 0)
        {
            return;
        } else
        {
            arms[i].aime.passive_StartFiring(0, actor, j, 0.0F);
            return;
        }
    }

    private void Fire_Mirror(int i, com.maddox.il2.engine.Actor actor, int j, float f)
    {
        if(actor == null)
            return;
        if(arms == null || i < 0 || i >= arms.length || arms[i].aime == null)
            return;
        if(parts[arms[i].part_idx].state != 0)
            return;
        if(f <= 0.15F)
            f = 0.15F;
        if(f >= 7F)
            f = 7F;
        arms[i].aime.passive_StartFiring(1, actor, j, f);
    }

    public int targetGun(com.maddox.il2.ai.ground.Aim aim, com.maddox.il2.engine.Actor actor, float f, boolean flag)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor) || !actor.isAlive() || actor.getArmy() == 0)
            return 0;
        com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
        if(firingdevice.gun instanceof com.maddox.il2.objects.weapons.CannonMidrangeGeneric)
        {
            int i = ((com.maddox.il2.ai.ground.Prey)actor).chooseBulletType(firingdevice.gun.prop.bullet);
            if(i < 0)
                return 0;
            ((com.maddox.il2.objects.weapons.CannonMidrangeGeneric)firingdevice.gun).setBulletType(i);
        }
        boolean flag1 = ((com.maddox.il2.ai.ground.Prey)actor).getShotpointOffset(aim.shotpoint_idx, p1);
        if(!flag1)
            return 0;
        com.maddox.il2.objects.ships.ShipPartProperties shippartproperties = parts[firingdevice.part_idx].pro;
        float f1 = f * com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0.8F, 1.2F);
        if(!com.maddox.il2.ai.Aimer.Aim((com.maddox.il2.ai.BulletAimer)firingdevice.gun, actor, this, f1, p1, shippartproperties.fireOffset))
            return 0;
        com.maddox.JGP.Point3d point3d = new Point3d();
        com.maddox.il2.ai.Aimer.GetPredictedTargetPosition(point3d);
        com.maddox.JGP.Point3d point3d1 = com.maddox.il2.ai.Aimer.GetHunterFirePoint();
        float f2 = 0.05F;
        double d = point3d.distance(point3d1);
        double d1 = point3d.z;
        point3d.sub(point3d1);
        point3d.scale(com.maddox.il2.objects.ships.BigshipGeneric.Rnd(0.995D, 1.0049999999999999D));
        point3d.add(point3d1);
        if(f1 > 0.001F)
        {
            com.maddox.JGP.Point3d point3d2 = new Point3d();
            actor.pos.getAbs(point3d2);
            tmpvd.sub(point3d, point3d2);
            double d2 = tmpvd.length();
            if(d2 > 0.001D)
            {
                float f7 = (float)d2 / f1;
                if(f7 > 200F)
                    f7 = 200F;
                float f8 = f7 * 0.01F;
                point3d2.sub(point3d1);
                double d3 = point3d2.x * point3d2.x + point3d2.y * point3d2.y + point3d2.z * point3d2.z;
                if(d3 > 0.01D)
                {
                    float f9 = (float)tmpvd.dot(point3d2);
                    f9 /= (float)(d2 * java.lang.Math.sqrt(d3));
                    f9 = (float)java.lang.Math.sqrt(1.0F - f9 * f9);
                    f8 *= 0.4F + 0.6F * f9;
                }
                f8 *= 1.3F;
                f8 *= com.maddox.il2.ai.ground.Aim.AngleErrorKoefForSkill[SKILL_IDX];
                int k = com.maddox.il2.game.Mission.curCloudsType();
                if(k > 2)
                {
                    float f10 = k <= 4 ? 800F : 400F;
                    float f11 = (float)(d / (double)f10);
                    if(f11 > 1.0F)
                    {
                        if(f11 > 10F)
                            return 0;
                        f11 = (f11 - 1.0F) / 9F;
                        f8 *= f11 + 1.0F;
                    }
                }
                if(k >= 3 && d1 > (double)com.maddox.il2.game.Mission.curCloudsHeight())
                    f8 *= 1.25F;
                f2 += f8;
            }
        }
        if(com.maddox.il2.ai.World.Sun().ToSun.z < -0.15F)
        {
            float f4 = (-com.maddox.il2.ai.World.Sun().ToSun.z - 0.15F) / 0.13F;
            if(f4 >= 1.0F)
                f4 = 1.0F;
            if((actor instanceof com.maddox.il2.objects.air.Aircraft) && com.maddox.il2.net.NetServerParams.getServerTime() - ((com.maddox.il2.objects.air.Aircraft)actor).tmSearchlighted < 1000L)
                f4 = 0.0F;
            f2 += 10F * f4;
        }
        float f5 = (float)actor.getSpeed(null) - 10F;
        if(f5 > 0.0F)
        {
            float f6 = 83.33334F;
            f5 = f5 < f6 ? f5 / f6 : 1.0F;
            f2 += f5 * shippartproperties.FAST_TARGETS_ANGLE_ERROR;
        }
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        if(!((com.maddox.il2.ai.BulletAimer)firingdevice.gun).FireDirection(point3d1, point3d, vector3d))
            return 0;
        float f3;
        if(flag)
        {
            f3 = 99999F;
            d1 = 99999F;
        } else
        {
            f3 = shippartproperties.HEAD_MAX_YAW_SPEED;
            d1 = shippartproperties.GUN_MAX_PITCH_SPEED;
        }
        o.add(shippartproperties.fireOrient, pos.getAbs().getOrient());
        int j = aim.setRotationForTargeting(this, o, point3d1, firingdevice.headYaw, firingdevice.gunPitch, vector3d, f2, f1, shippartproperties.HEAD_YAW_RANGE, shippartproperties.GUN_MIN_PITCH, shippartproperties.GUN_MAX_PITCH, f3, d1, 0.0F);
        return j;
    }

    public void singleShot(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
        if(!parts[firingdevice.part_idx].pro.TRACKING_ONLY)
            firingdevice.gun.shots(1);
    }

    public void startFire(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
        if(!parts[firingdevice.part_idx].pro.TRACKING_ONLY)
            firingdevice.gun.shots(-1);
    }

    public void continueFire(com.maddox.il2.ai.ground.Aim aim)
    {
    }

    public void stopFire(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
        if(!parts[firingdevice.part_idx].pro.TRACKING_ONLY)
            firingdevice.gun.shots(0);
    }

    public boolean isVisibilityLong()
    {
        return true;
    }

    private void createAirport()
    {
        if(prop.propAirport != null)
        {
            prop.propAirport.firstInit(this);
            draw = new TowStringMeshDraw(draw);
            if(prop.propAirport.cellTO != null)
                cellTO = (com.maddox.il2.ai.air.CellAirField)prop.propAirport.cellTO.getClone();
            if(prop.propAirport.cellLDG != null)
                cellLDG = (com.maddox.il2.ai.air.CellAirField)prop.propAirport.cellLDG.getClone();
            airport = new AirportCarrier(this, prop.propAirport.rwy);
        }
    }

    public com.maddox.il2.ai.AirportCarrier getAirport()
    {
        return airport;
    }

    public com.maddox.il2.ai.air.CellAirField getCellTO()
    {
        return cellTO;
    }

    public com.maddox.il2.ai.air.CellAirField getCellLDR()
    {
        return cellLDG;
    }

    private void validateTowAircraft()
    {
        if(towPortNum < 0)
            return;
        if(!com.maddox.il2.engine.Actor.isValid(towAircraft))
        {
            requestDetowAircraft(towAircraft);
            return;
        }
        if(pos.getAbsPoint().distance(towAircraft.pos.getAbsPoint()) > (double)hierMesh().visibilityR())
        {
            requestDetowAircraft(towAircraft);
            return;
        }
        if(!towAircraft.FM.CT.bHasArrestorControl)
        {
            requestDetowAircraft(towAircraft);
            return;
        } else
        {
            return;
        }
    }

    public void forceTowAircraft(com.maddox.il2.objects.air.Aircraft aircraft, int i)
    {
        if(towPortNum >= 0)
        {
            return;
        } else
        {
            towPortNum = i;
            towAircraft = aircraft;
            towHook = new HookNamed(aircraft, "_ClipAGear");
            return;
        }
    }

    public void requestTowAircraft(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(towPortNum >= 0 || prop.propAirport.towPRel == null)
            return;
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(aircraft, "_ClipAGear");
        com.maddox.JGP.Point3d apoint3d[] = prop.propAirport.towPRel;
        com.maddox.JGP.Point3d point3d = new Point3d();
        com.maddox.JGP.Point3d point3d1 = new Point3d();
        com.maddox.JGP.Point3d point3d2 = new Point3d();
        com.maddox.JGP.Point3d point3d3 = new Point3d();
        com.maddox.il2.engine.Loc loc = new Loc();
        com.maddox.il2.engine.Loc loc1 = new Loc();
        for(int i = 0; i < apoint3d.length / 2; i++)
        {
            pos.getCurrent(loc);
            point3d2.set(apoint3d[i + i]);
            loc.transform(point3d2);
            point3d3.set(apoint3d[i + i + 1]);
            loc.transform(point3d3);
            aircraft.pos.getCurrent(loc1);
            loc.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            hooknamed.computePos(aircraft, loc1, loc);
            point3d.set(loc.getPoint());
            aircraft.pos.getPrev(loc1);
            loc.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            hooknamed.computePos(aircraft, loc1, loc);
            point3d1.set(loc.getPoint());
            if(point3d1.z >= point3d2.z + 0.5D * (point3d3.z - point3d2.z) + 0.20000000000000001D)
                continue;
            com.maddox.JGP.Line2d line2d = new Line2d(new Point2d(point3d2.x, point3d2.y), new Point2d(point3d3.x, point3d3.y));
            com.maddox.JGP.Line2d line2d1 = new Line2d(new Point2d(point3d.x, point3d.y), new Point2d(point3d1.x, point3d1.y));
            try
            {
                com.maddox.JGP.Point2d point2d = line2d.crossPRE(line2d1);
                double d = java.lang.Math.min(point3d2.x, point3d3.x);
                double d1 = java.lang.Math.max(point3d2.x, point3d3.x);
                double d2 = java.lang.Math.min(point3d2.y, point3d3.y);
                double d3 = java.lang.Math.max(point3d2.y, point3d3.y);
                if(point2d.x <= d || point2d.x >= d1 || point2d.y <= d2 || point2d.y >= d3)
                    continue;
                d = java.lang.Math.min(point3d.x, point3d1.x);
                d1 = java.lang.Math.max(point3d.x, point3d1.x);
                d2 = java.lang.Math.min(point3d.y, point3d1.y);
                d3 = java.lang.Math.max(point3d.y, point3d1.y);
                if(point2d.x > d && point2d.x < d1 && point2d.y > d2 && point2d.y < d3)
                {
                    towPortNum = i;
                    towAircraft = aircraft;
                    towHook = new HookNamed(aircraft, "_ClipAGear");
                    return;
                }
            }
            catch(java.lang.Exception exception) { }
        }

    }

    public void requestDetowAircraft(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(aircraft == towAircraft)
        {
            towAircraft = null;
            towPortNum = -1;
        }
    }

    public boolean isTowAircraft(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        return towAircraft == aircraft;
    }

    public double getSpeed(com.maddox.JGP.Vector3d vector3d)
    {
        if(path == null)
            return super.getSpeed(vector3d);
        long l = com.maddox.il2.net.NetServerParams.getServerTime();
        if(l > (long)(com.maddox.rts.Time.tickLen() * 4))
            return super.getSpeed(vector3d);
        com.maddox.il2.objects.ships.Segment segment = (com.maddox.il2.objects.ships.Segment)path.get(0);
        tmpDir.sub(segment.posOut, segment.posIn);
        tmpDir.normalize();
        tmpDir.scale(segment.speedIn);
        if(vector3d != null)
            vector3d.set(tmpDir);
        return tmpDir.length();
    }

    private void zutiRefreshBornPlace()
    {
        if(zutiBornPlace == null || zutiIsClassBussy)
            return;
        zutiIsClassBussy = true;
        if(dying == 0)
        {
            com.maddox.JGP.Point3d point3d = pos.getAbsPoint();
            zutiBornPlace.place.set(point3d.x, point3d.y);
            if(zutiBornPlace.zutiBpStayPoints != null)
            {
                for(int i = 0; i < zutiBornPlace.zutiBpStayPoints.size(); i++)
                {
                    com.maddox.il2.game.ZutiStayPoint zutistaypoint = (com.maddox.il2.game.ZutiStayPoint)zutiBornPlace.zutiBpStayPoints.get(i);
                    zutistaypoint.PsVsShipRefresh(point3d.x, point3d.y, initOr.getYaw());
                }

            }
        } else
        if(dying > 0)
        {
            com.maddox.il2.game.ZutiSupportMethods.removeBornPlace(zutiBornPlace);
            zutiBornPlace = null;
        }
        zutiIsClassBussy = false;
    }

    private void zutiAssignStayPointsToBp()
    {
        double d;
        double d1;
        java.util.ArrayList arraylist;
        int j;
        java.lang.String s;
        int l;
        if(zutiBornPlace == null)
            return;
        d = pos.getAbsPoint().x;
        d1 = pos.getAbsPoint().y;
        zutiBornPlace.zutiBpStayPoints = new ArrayList();
        double d2 = 22500D;
        com.maddox.il2.ai.air.Point_Stay apoint_stay[][] = com.maddox.il2.ai.World.cur().airdrome.stay;
        arraylist = new ArrayList();
        for(int i = 0; i < apoint_stay.length; i++)
        {
            if(apoint_stay[i] == null)
                continue;
            com.maddox.il2.ai.air.Point_Stay point_stay = apoint_stay[i][apoint_stay[i].length - 1];
            double d3 = ((double)point_stay.x - d) * ((double)point_stay.x - d) + ((double)point_stay.y - d1) * ((double)point_stay.y - d1);
            if(d3 <= d2)
                arraylist.add(point_stay);
        }

        j = arraylist.size();
        s = toString();
        if(s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[0]) > 0 || s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[1]) > 0 || s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[2]) > 0)
            j -= com.maddox.il2.game.Mission.cur().zutiCarrierSpawnPoints_CV2;
        else
        if(s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[3]) > 0 || s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[4]) > 0)
            j -= com.maddox.il2.game.Mission.cur().zutiCarrierSpawnPoints_CV9;
        else
        if(s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[5]) > 0 || s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[6]) > 0 || s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[18]) > 0)
            j -= com.maddox.il2.game.Mission.cur().zutiCarrierSpawnPoints_CVE;
        else
        if(s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[7]) > 0)
            j -= com.maddox.il2.game.Mission.cur().zutiCarrierSpawnPoints_CVL;
        else
        if(s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[8]) > 0 || s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[9]) > 0 || s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[13]) > 0 || s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[14]) > 0)
            j -= com.maddox.il2.game.Mission.cur().zutiCarrierSpawnPoints_HMS;
        else
        if(s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[10]) > 0 || s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[15]) > 0)
            j -= com.maddox.il2.game.Mission.cur().zutiCarrierSpawnPoints_Akagi;
        else
        if(s.indexOf(ZUTI_CARRIER_SUBCLASS_STRING[11]) > 0)
            j -= com.maddox.il2.game.Mission.cur().zutiCarrierSpawnPoints_IJN;
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.ai.air.Point_Stay point_stay1 = (com.maddox.il2.ai.air.Point_Stay)arraylist.get(k);
            point_stay1.set(-1000000F, -1000000F);
        }

        if(j < 0)
            return;
        l = j;
_L3:
        if(l >= arraylist.size()) goto _L2; else goto _L1
_L1:
        com.maddox.il2.game.ZutiStayPoint zutistaypoint;
        zutistaypoint = new ZutiStayPoint();
        zutistaypoint.pointStay = (com.maddox.il2.ai.air.Point_Stay)arraylist.get(l);
        zutistaypoint.PsVsShip(d, d1, initOr.getYaw(), l, s);
        if(zutiBornPlace == null)
            return;
        try
        {
            zutiBornPlace.zutiBpStayPoints.add(zutistaypoint);
            continue; /* Loop/switch isn't completed */
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("BigshipGeneric zutiAssignStayPointsToBp error: " + exception.toString());
            exception.printStackTrace();
            l++;
        }
          goto _L3
_L2:
        zutiBornPlace.zutiSetBornPlaceStayPointsNumber(arraylist.size() - j);
        return;
    }

    public void zutiAssignBornPlace()
    {
        double d = pos.getAbsPoint().x;
        double d1 = pos.getAbsPoint().y;
        double d2 = 1000000D;
        com.maddox.il2.net.BornPlace bornplace = null;
        java.util.ArrayList arraylist = com.maddox.il2.ai.World.cur().bornPlaces;
        for(int i = 0; i < arraylist.size(); i++)
        {
            com.maddox.il2.net.BornPlace bornplace1 = (com.maddox.il2.net.BornPlace)arraylist.get(i);
            if(bornplace1.zutiAlreadyAssigned)
                continue;
            double d3 = java.lang.Math.sqrt(java.lang.Math.pow(bornplace1.place.x - d, 2D) + java.lang.Math.pow(bornplace1.place.y - d1, 2D));
            if(d3 < d2 && bornplace1.army == getArmy())
            {
                d2 = d3;
                bornplace = bornplace1;
            }
        }

        if(d2 < 1000D)
        {
            zutiBornPlace = bornplace;
            bornplace.zutiAlreadyAssigned = true;
            zutiAssignStayPointsToBp();
        }
    }

    public int zutiGetDying()
    {
        return dying;
    }

    public boolean zutiIsStatic()
    {
        return path == null || path.size() <= 0;
    }

    public void hideTransparentRunwayRed()
    {
        hierMesh().chunkVisible("Red", false);
    }

    private static final int MAX_PARTS = 255;
    private static final int MAX_GUNS = 255;
    private static final int MAX_USER_ADDITIONAL_COLLISION_CHUNKS = 4;
    public float CURRSPEED;
    public boolean isTurning;
    public boolean isTurningBackward;
    public boolean mustRecomputePath;
    public boolean mustSendSpeedToNet;
    private final int REQUEST_LOC = 93;
    private com.maddox.il2.objects.ships.ShipProperties prop;
    private static final int NETSEND_MIN_DELAY_MS_PARTSSTATE = 300;
    private static final int NETSEND_MAX_DELAY_MS_PARTSSTATE = 500;
    private long netsendPartsState_lasttimeMS;
    private boolean netsendPartsState_needtosend;
    private static float netsendDrown_pitch = 0.0F;
    private static float netsendDrown_roll = 0.0F;
    private static float netsendDrown_depth = 0.0F;
    private static float netsendDrown_timeS = 0.0F;
    private static int netsendDrown_nparts = 0;
    private static final int NETSEND_MIN_DELAY_MS_FIRE = 40;
    private static final int NETSEND_MAX_DELAY_MS_FIRE = 85;
    private static final long NETSEND_MIN_BYTECODEDDELTATIME_MS_FIRE = -2000L;
    private static final long NETSEND_MAX_BYTECODEDDELTATIME_MS_FIRE = 5000L;
    private static final int NETSEND_ABSLIMIT_NUMITEMS_FIRE = 31;
    private static final int NETSEND_MAX_NUMITEMS_FIRE = 15;
    private long netsendFire_lasttimeMS;
    private int netsendFire_armindex;
    private static com.maddox.il2.objects.ships.TmpTrackOrFireInfo netsendFire_tmpbuff[];
    private com.maddox.il2.objects.ships.FiringDevice arms[];
    private static final int STPART_LIVE = 0;
    private static final int STPART_BLACK = 1;
    private static final int STPART_DEAD = 2;
    private com.maddox.il2.objects.ships.Part parts[];
    private int shotpoints[];
    int numshotpoints;
    private static final int NETSEND_MIN_DELAY_MS_DMG = 65;
    private static final int NETSEND_MAX_DELAY_MS_DMG = 115;
    private static final int NETSEND_ABSLIMIT_NUMITEMS_DMG = 256;
    private static final int NETSEND_MAX_NUMITEMS_DMG = 28;
    private long netsendDmg_lasttimeMS;
    private int netsendDmg_partindex;
    private java.util.ArrayList path;
    private int cachedSeg;
    private float bodyDepth;
    private float bodyPitch;
    private float bodyRoll;
    private float shipYaw;
    private long tmInterpoStart;
    private long tmInterpoEnd;
    private float bodyDepth0;
    private float bodyPitch0;
    private float bodyRoll0;
    private float bodyDepth1;
    private float bodyPitch1;
    private float bodyRoll1;
    private long timeOfDeath;
    private long sink2timeWhenStop;
    private float sink2Depth;
    private float sink2Pitch;
    private float sink2Roll;
    public int dying;
    static final int DYING_NONE = 0;
    static final int DYING_SINK1 = 1;
    static final int DYING_SINK2 = 2;
    static final int DYING_DEAD = 3;
    private long respawnDelay;
    private long wakeupTmr;
    public float DELAY_WAKEUP;
    public int SKILL_IDX;
    public float SLOWFIRE_K;
    private com.maddox.il2.objects.ships.Pipe pipes[];
    private com.maddox.il2.objects.ships.Pipe dsmoks[];
    private com.maddox.il2.engine.Eff3DActor wake[] = {
        null, null, null
    };
    private com.maddox.il2.engine.Eff3DActor noseW;
    private com.maddox.il2.engine.Eff3DActor nose;
    private com.maddox.il2.engine.Eff3DActor tail;
    private static com.maddox.il2.objects.ships.ShipProperties constr_arg1 = null;
    private static com.maddox.il2.engine.ActorSpawnArg constr_arg2 = null;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.JGP.Point3d p1 = new Point3d();
    private static com.maddox.JGP.Point3d p2 = new Point3d();
    private com.maddox.il2.engine.Orient o;
    private static com.maddox.JGP.Vector3f tmpvf = new Vector3f();
    private static com.maddox.JGP.Vector3d tmpvd = new Vector3d();
    private static float tmpYPR[] = new float[3];
    private static float tmpf6[] = new float[6];
    private static com.maddox.il2.engine.Loc tmpL = new Loc();
    private static byte tmpBitsState[] = new byte[32];
    private float rollAmp;
    private int rollPeriod;
    private double rollWAmp;
    private float pitchAmp;
    private int pitchPeriod;
    private double pitchWAmp;
    private com.maddox.JGP.Vector3d W;
    private com.maddox.JGP.Vector3d N;
    private com.maddox.JGP.Vector3d tmpV;
    public com.maddox.il2.engine.Orient initOr;
    public com.maddox.il2.engine.Loc initLoc;
    private com.maddox.il2.ai.AirportCarrier airport;
    private com.maddox.il2.ai.air.CellAirField cellTO;
    private com.maddox.il2.ai.air.CellAirField cellLDG;
    public com.maddox.il2.objects.air.Aircraft towAircraft;
    public int towPortNum;
    public com.maddox.il2.engine.HookNamed towHook;
    private static com.maddox.JGP.Vector3d tmpDir = new Vector3d();
    public static java.lang.String ZUTI_RADAR_SHIPS[] = {
        "CV", "Marat", "Kirov", "BB", "Niobe", "Illmarinen", "Vainamoinen", "Tirpitz", "Aurora", "Carrier0", 
        "Carrier1"
    };
    public static java.lang.String ZUTI_RADAR_SHIPS_SMALL[] = {
        "Destroyer", "DD", "USSMcKean", "Italia0", "Italia1"
    };
    public static java.lang.String ZUTI_CARRIER_STRING[] = {
        "CV", "Carrier"
    };
    public static java.lang.String ZUTI_CARRIER_SUBCLASS_STRING[] = {
        "USSCVGeneric", "CV3", "CV2", "CV9", "CV11", "CVE", "Carrier1", "CVL", "HMS", "Carrier0", 
        "Akagi", "IJN", "Generic", "Formidable", "Indomitable", "Hiryu", "Kaga", "Soryu", "IJNCVLGeneric"
    };
    public com.maddox.il2.net.BornPlace zutiBornPlace;
    private boolean zutiIsClassBussy;
    private boolean zutiIsShipChief;
    private com.maddox.JGP.Point3d zutiPosition;

    static 
    {
        netsendFire_tmpbuff = new com.maddox.il2.objects.ships.TmpTrackOrFireInfo[31];
        for(int i = 0; i < netsendFire_tmpbuff.length; i++)
            netsendFire_tmpbuff[i] = new TmpTrackOrFireInfo();

    }








































































}
