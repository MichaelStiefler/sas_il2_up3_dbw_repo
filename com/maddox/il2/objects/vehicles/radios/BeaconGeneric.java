// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BeaconGeneric.java

package com.maddox.il2.objects.vehicles.radios;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.TableFunctions;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Obstacle;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.util.TableFunction2;
import java.io.IOException;
import java.io.PrintStream;

public abstract class BeaconGeneric extends com.maddox.il2.engine.ActorHMesh
    implements com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.il2.ai.ground.Obstacle, com.maddox.il2.objects.ActorAlign
{
    public static class BeaconProperties
    {

        public java.lang.String meshName;
        public java.lang.String meshName1;
        public java.lang.String meshSummer;
        public java.lang.String meshDesert;
        public java.lang.String meshWinter;
        public java.lang.String meshSummer1;
        public java.lang.String meshDesert1;
        public java.lang.String meshWinter1;
        public com.maddox.util.TableFunction2 fnShotPanzer;
        public com.maddox.util.TableFunction2 fnExplodePanzer;
        public float PANZER_BODY_FRONT;
        public float PANZER_BODY_BACK;
        public float PANZER_BODY_SIDE;
        public float PANZER_BODY_TOP;
        public float PANZER_HEAD;
        public float PANZER_HEAD_TOP;
        public float PANZER_TNT_TYPE;
        public int HITBY_MASK;
        public java.lang.String explodeName;
        public float innerMarkerDist;
        public float outerMarkerDist;

        public BeaconProperties()
        {
            meshName = null;
            meshName1 = null;
            meshSummer = null;
            meshDesert = null;
            meshWinter = null;
            meshSummer1 = null;
            meshDesert1 = null;
            meshWinter1 = null;
            fnShotPanzer = null;
            fnExplodePanzer = null;
            PANZER_BODY_FRONT = 0.001F;
            PANZER_BODY_BACK = 0.001F;
            PANZER_BODY_SIDE = 0.001F;
            PANZER_BODY_TOP = 0.001F;
            PANZER_HEAD = 0.001F;
            PANZER_HEAD_TOP = 0.001F;
            PANZER_TNT_TYPE = 1.0F;
            HITBY_MASK = -2;
            explodeName = null;
            innerMarkerDist = 0.0F;
            outerMarkerDist = 0.0F;
        }
    }

    class Master extends com.maddox.il2.engine.ActorNet
    {

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(netmsginput.isGuaranted())
            {
                if(netmsginput.readByte() != 100)
                    return false;
            } else
            {
                return false;
            }
            if(dying == 1)
            {
                return true;
            } else
            {
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                Die(actor, (short)0, true);
                return true;
            }
        }

        public Master(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
        }
    }

    class Mirror extends com.maddox.il2.engine.ActorNet
    {

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(netmsginput.isGuaranted())
            {
                switch(netmsginput.readByte())
                {
                case 73: // 'I'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(netmsginput, 0);
                        post(netmsgguaranted);
                    }
                    short word0 = netmsginput.readShort();
                    if(word0 > 0 && dying != 1)
                        Die(null, (short)1, false);
                    return true;

                case 68: // 'D'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted(netmsginput, 1);
                        post(netmsgguaranted1);
                    }
                    if(dying != 1)
                    {
                        com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                        com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                        Die(actor, (short)1, true);
                    }
                    return true;

                case 100: // 'd'
                    com.maddox.rts.NetMsgGuaranted netmsgguaranted2 = new NetMsgGuaranted(netmsginput, 1);
                    postTo(masterChannel(), netmsgguaranted2);
                    return true;
                }
                return false;
            } else
            {
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

    public static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

        private static float getF(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, float f, float f1)
        {
            float f2 = sectfile.get(s, s1, -9865.345F);
            if(f2 == -9865.345F || f2 < f || f2 > f1)
            {
                if(f2 == -9865.345F)
                    java.lang.System.out.println("Stationary: Parameter [" + s + "]:<" + s1 + "> " + "not found");
                else
                    java.lang.System.out.println("Stationary: Value of [" + s + "]:<" + s1 + "> (" + f2 + ")" + " is out of range (" + f + ";" + f1 + ")");
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
                java.lang.System.out.print("Stationary: Parameter [" + s + "]:<" + s1 + "> ");
                java.lang.System.out.println(s2 != null ? "is empty" : "not found");
                throw new RuntimeException("Can't set property");
            } else
            {
                return s2;
            }
        }

        private static java.lang.String getS(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, java.lang.String s2)
        {
            java.lang.String s3 = sectfile.get(s, s1);
            if(s3 == null || s3.length() <= 0)
                return s2;
            else
                return s3;
        }

        public static com.maddox.il2.objects.vehicles.radios.BeaconProperties LoadStationaryProperties(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.Class class1)
        {
            com.maddox.il2.objects.vehicles.radios.BeaconProperties beaconproperties = new BeaconProperties();
            java.lang.String s1 = com.maddox.il2.objects.vehicles.radios.SPAWN.getS(sectfile, s, "PanzerType", null);
            if(s1 == null)
                s1 = "Tank";
            beaconproperties.fnShotPanzer = com.maddox.il2.ai.TableFunctions.GetFunc2(s1 + "ShotPanzer");
            beaconproperties.fnExplodePanzer = com.maddox.il2.ai.TableFunctions.GetFunc2(s1 + "ExplodePanzer");
            beaconproperties.PANZER_TNT_TYPE = com.maddox.il2.objects.vehicles.radios.SPAWN.getF(sectfile, s, "PanzerSubtype", 0.0F, 100F);
            beaconproperties.meshSummer = com.maddox.il2.objects.vehicles.radios.SPAWN.getS(sectfile, s, "MeshSummer");
            beaconproperties.meshDesert = com.maddox.il2.objects.vehicles.radios.SPAWN.getS(sectfile, s, "MeshDesert", beaconproperties.meshSummer);
            beaconproperties.meshWinter = com.maddox.il2.objects.vehicles.radios.SPAWN.getS(sectfile, s, "MeshWinter", beaconproperties.meshSummer);
            beaconproperties.meshSummer1 = com.maddox.il2.objects.vehicles.radios.SPAWN.getS(sectfile, s, "MeshSummerDamage", null);
            beaconproperties.meshDesert1 = com.maddox.il2.objects.vehicles.radios.SPAWN.getS(sectfile, s, "MeshDesertDamage", beaconproperties.meshSummer1);
            beaconproperties.meshWinter1 = com.maddox.il2.objects.vehicles.radios.SPAWN.getS(sectfile, s, "MeshWinterDamage", beaconproperties.meshSummer1);
            int i = (beaconproperties.meshSummer1 != null ? 0 : 1) + (beaconproperties.meshDesert1 != null ? 0 : 1) + (beaconproperties.meshWinter1 != null ? 0 : 1);
            if(i != 0 && i != 3)
            {
                java.lang.System.out.println("Stationary: Uncomplete set of damage meshes for '" + s + "'");
                throw new RuntimeException("Can't register beacon object");
            }
            beaconproperties.explodeName = com.maddox.il2.objects.vehicles.radios.SPAWN.getS(sectfile, s, "Explode", "Stationary");
            beaconproperties.PANZER_BODY_FRONT = com.maddox.il2.objects.vehicles.radios.SPAWN.getF(sectfile, s, "PanzerBodyFront", 0.001F, 9.999F);
            if(sectfile.get(s, "PanzerBodyBack", -9865.345F) == -9865.345F)
            {
                beaconproperties.PANZER_BODY_BACK = beaconproperties.PANZER_BODY_FRONT;
                beaconproperties.PANZER_BODY_SIDE = beaconproperties.PANZER_BODY_FRONT;
                beaconproperties.PANZER_BODY_TOP = beaconproperties.PANZER_BODY_FRONT;
            } else
            {
                beaconproperties.PANZER_BODY_BACK = com.maddox.il2.objects.vehicles.radios.SPAWN.getF(sectfile, s, "PanzerBodyBack", 0.001F, 9.999F);
                beaconproperties.PANZER_BODY_SIDE = com.maddox.il2.objects.vehicles.radios.SPAWN.getF(sectfile, s, "PanzerBodySide", 0.001F, 9.999F);
                beaconproperties.PANZER_BODY_TOP = com.maddox.il2.objects.vehicles.radios.SPAWN.getF(sectfile, s, "PanzerBodyTop", 0.001F, 9.999F);
            }
            if(sectfile.get(s, "PanzerHead", -9865.345F) == -9865.345F)
                beaconproperties.PANZER_HEAD = beaconproperties.PANZER_BODY_FRONT;
            else
                beaconproperties.PANZER_HEAD = com.maddox.il2.objects.vehicles.radios.SPAWN.getF(sectfile, s, "PanzerHead", 0.001F, 9.999F);
            if(sectfile.get(s, "PanzerHeadTop", -9865.345F) == -9865.345F)
                beaconproperties.PANZER_HEAD_TOP = beaconproperties.PANZER_BODY_TOP;
            else
                beaconproperties.PANZER_HEAD_TOP = com.maddox.il2.objects.vehicles.radios.SPAWN.getF(sectfile, s, "PanzerHeadTop", 0.001F, 9.999F);
            float f = java.lang.Math.min(java.lang.Math.min(beaconproperties.PANZER_BODY_BACK, beaconproperties.PANZER_BODY_TOP), java.lang.Math.min(beaconproperties.PANZER_BODY_SIDE, beaconproperties.PANZER_HEAD_TOP));
            beaconproperties.HITBY_MASK = f <= 0.015F ? -1 : -2;
            com.maddox.rts.Property.set(class1, "iconName", "icons/" + com.maddox.il2.objects.vehicles.radios.SPAWN.getS(sectfile, s, "Icon") + ".mat");
            com.maddox.rts.Property.set(class1, "meshName", beaconproperties.meshSummer);
            try
            {
                if(class1 == (com.maddox.il2.objects.vehicles.radios.BeaconGeneric.class$com$maddox$il2$objects$vehicles$radios$Beacon$LorenzBLBeacon != null ? com.maddox.il2.objects.vehicles.radios.BeaconGeneric.class$com$maddox$il2$objects$vehicles$radios$Beacon$LorenzBLBeacon : (com.maddox.il2.objects.vehicles.radios.BeaconGeneric.class$com$maddox$il2$objects$vehicles$radios$Beacon$LorenzBLBeacon = com.maddox.il2.objects.vehicles.radios.BeaconGeneric._mthclass$("com.maddox.il2.objects.vehicles.radios.Beacon$LorenzBLBeacon"))) || class1 == (com.maddox.il2.objects.vehicles.radios.BeaconGeneric.class$com$maddox$il2$objects$vehicles$radios$Beacon$LorenzBLBeacon_LongRunway != null ? com.maddox.il2.objects.vehicles.radios.BeaconGeneric.class$com$maddox$il2$objects$vehicles$radios$Beacon$LorenzBLBeacon_LongRunway : (com.maddox.il2.objects.vehicles.radios.BeaconGeneric.class$com$maddox$il2$objects$vehicles$radios$Beacon$LorenzBLBeacon_LongRunway = com.maddox.il2.objects.vehicles.radios.BeaconGeneric._mthclass$("com.maddox.il2.objects.vehicles.radios.Beacon$LorenzBLBeacon_LongRunway"))) || class1 == (com.maddox.il2.objects.vehicles.radios.BeaconGeneric.class$com$maddox$il2$objects$vehicles$radios$Beacon$LorenzBLBeacon_AAIAS != null ? com.maddox.il2.objects.vehicles.radios.BeaconGeneric.class$com$maddox$il2$objects$vehicles$radios$Beacon$LorenzBLBeacon_AAIAS : (com.maddox.il2.objects.vehicles.radios.BeaconGeneric.class$com$maddox$il2$objects$vehicles$radios$Beacon$LorenzBLBeacon_AAIAS = com.maddox.il2.objects.vehicles.radios.BeaconGeneric._mthclass$("com.maddox.il2.objects.vehicles.radios.Beacon$LorenzBLBeacon_AAIAS"))))
                {
                    beaconproperties.innerMarkerDist = com.maddox.il2.objects.vehicles.radios.SPAWN.getF(sectfile, s, "InnerMarkerDist", 1.0F, 3000F);
                    beaconproperties.outerMarkerDist = com.maddox.il2.objects.vehicles.radios.SPAWN.getF(sectfile, s, "OuterMarkerDist", 3000F, 30000F);
                }
            }
            catch(java.lang.Exception exception) { }
            return beaconproperties;
        }

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            switch(com.maddox.il2.ai.World.cur().camouflage)
            {
            case 1: // '\001'
                proper.meshName = proper.meshWinter;
                proper.meshName1 = proper.meshWinter1;
                break;

            case 2: // '\002'
                proper.meshName = proper.meshDesert;
                proper.meshName1 = proper.meshDesert1;
                break;

            default:
                proper.meshName = proper.meshSummer;
                proper.meshName1 = proper.meshSummer1;
                break;
            }
            Object obj = null;
            com.maddox.il2.objects.vehicles.radios.BeaconGeneric beacongeneric;
            try
            {
                com.maddox.il2.objects.vehicles.radios.BeaconGeneric.constr_arg1 = proper;
                com.maddox.il2.objects.vehicles.radios.BeaconGeneric.constr_arg2 = actorspawnarg;
                beacongeneric = (com.maddox.il2.objects.vehicles.radios.BeaconGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.radios.BeaconGeneric.constr_arg1 = null;
                com.maddox.il2.objects.vehicles.radios.BeaconGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.radios.BeaconGeneric.constr_arg1 = null;
                com.maddox.il2.objects.vehicles.radios.BeaconGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create Stationary object [class:" + cls.getName() + "]");
                return null;
            }
            return beacongeneric;
        }

        public java.lang.Class cls;
        public com.maddox.il2.objects.vehicles.radios.BeaconProperties proper;

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
                proper = com.maddox.il2.objects.vehicles.radios.SPAWN.LoadStationaryProperties(com.maddox.il2.objects.Statics.getTechnicsFile(), s1, class1);
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


    public static float getConeOfSilenceMultiplier(double d, double d1)
    {
        float f = 57.32484F * (float)java.lang.Math.atan2(d - d1, d1);
        return com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt(f, 20F, 40F, 0.0F, 1.0F);
    }

    public static float getTerrainAndNightError(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        float f = aircraft.FM.Or.getYaw();
        float f1 = -45F;
        float f2 = mntNE;
        if(mntSE > f2)
        {
            f2 = mntSE;
            f1 = 45F;
        }
        if(mntSW > f2)
        {
            f2 = mntSW;
            f1 = 135F;
        }
        if(mntNW > f2)
        {
            f2 = mntNW;
            f1 = -135F;
        }
        float f3 = f - f1;
        f2 = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt(f2, 15F, 1400F, 0.0F, 1.0F);
        float f4 = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt((float)aircraft.FM.Loc.z, 3000F, 15000F, 1.0F, 0.0F);
        f2 *= f4;
        float f5 = (float)java.lang.Math.random();
        if(f3 > 0.0F)
            terErrDirection = -1;
        else
            terErrDirection = 1;
        if((double)f5 < 0.01D)
            terErrDirection = 0;
        if((double)f5 < 0.0070000000000000001D)
            ngtErrDirection *= -1;
        else
        if((double)f5 < 0.13D)
            ngtErrDirection = 1;
        else
        if((double)f5 > 0.96999999999999997D)
            ngtErrDirection = 0;
        float f6 = (float)terErrDirection * (f2 * 30F + com.maddox.il2.ai.World.Rnd().nextFloat(-f2 * 8F, f2 * 8F));
        float f7 = (float)ngtErrDirection * (nightError * 30F + com.maddox.il2.ai.World.Rnd().nextFloat(-nightError * 5F, nightError * 5F));
        return f6 + f7;
    }

    private static void sampleMountains(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        float f = java.lang.Math.abs(currentMntSampleCol - 25);
        for(int i = 0; i < 10; i++)
        {
            float f1 = -20000F + (float)currentMntSampleRow * 800F;
            float f2 = -20000F + (float)currentMntSampleCol * 800F;
            float f3 = com.maddox.il2.engine.Landscape.HQ_Air((float)aircraft.FM.Loc.x + f1, (float)aircraft.FM.Loc.y + f2);
            float f4 = java.lang.Math.abs(currentMntSampleRow - 25);
            float f5 = java.lang.Math.max(f4, f);
            f3 *= com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt(f5, 0.0F, 25F, 1.0F, 0.5F);
            mountainErrorSamples[currentMntSampleRow][currentMntSampleCol] = f3;
            currentMntSampleRow++;
            if(currentMntSampleRow != 50)
                continue;
            currentMntSampleRow = 0;
            currentMntSampleCol++;
            f = java.lang.Math.abs(currentMntSampleCol - 25);
            if(currentMntSampleCol == 50)
                currentMntSampleCol = 0;
        }

        int j = 625;
        for(int k = 0; k < 25; k++)
        {
            for(int k1 = 0; k1 < 25; k1++)
                mntSW += mountainErrorSamples[k][k1];

        }

        mntSW /= j;
        for(int l = 0; l < 25; l++)
        {
            for(int l1 = 25; l1 < 50; l1++)
                mntNW += mountainErrorSamples[l][l1];

        }

        mntNW /= j;
        for(int i1 = 25; i1 < 50; i1++)
        {
            for(int i2 = 25; i2 < 50; i2++)
                mntNE += mountainErrorSamples[i1][i2];

        }

        mntNE /= j;
        for(int j1 = 25; j1 < 50; j1++)
        {
            for(int j2 = 0; j2 < 25; j2++)
                mntSE += mountainErrorSamples[j1][j2];

        }

        mntSE /= j;
    }

    public static float getSignalAttenuation(com.maddox.JGP.Point3d point3d, com.maddox.il2.objects.air.Aircraft aircraft, boolean flag, boolean flag1, boolean flag2, boolean flag3)
    {
        V.sub(aircraft.FM.Loc, point3d);
        double d = V.length();
        double d1 = 0.0D;
        double d2 = d / 500D;
        for(int i = 0; i < 20; i++)
        {
            double d3 = d2 * (double)currentAttSampleSlot;
            V.normalize();
            V.scale(d3);
            float f6 = com.maddox.il2.engine.Landscape.HQ_Air((float)(point3d.x + V.x), (float)(point3d.y + V.y));
            double d5 = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.getCurvatureCorrectedHeight((float)(d3 / d), d, point3d.z, aircraft.FM.getAltitude());
            float f10 = (float)(d5 - (double)f6);
            if(f10 < 0.0F)
                attenuationSamples[currentAttSampleSlot - 1] = -f10;
            else
                attenuationSamples[currentAttSampleSlot - 1] = 0.0F;
            currentAttSampleSlot++;
            if(currentAttSampleSlot > 500)
                currentAttSampleSlot = 1;
        }

        float f = 0.0F;
        for(int j = 0; j < 500; j++)
        {
            if(attenuationSamples[j] > f && f > 0.0F)
            {
                float f2 = attenuationSamples[j] - f;
                d1 += (double)attenuationSamples[j] * d2 + d2 * (double)f2;
            }
            f = attenuationSamples[j];
        }

        d1 *= 0.16666600000000001D;
        if(flag3)
            return 0.0F;
        if(!flag2)
            com.maddox.il2.objects.vehicles.radios.BeaconGeneric.sampleMountains(aircraft);
        float f1 = 0.0F;
        float f3 = 0.0F;
        double d4 = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.lineOfSightDelta(point3d.z, aircraft.FM.getAltitude(), d);
        if(flag)
        {
            float f7 = 0.0F;
            if(d4 < 0.0D)
                f7 = (float)(-2D * d4);
            double d6 = (double)aircraft.FM.getAltitude() - point3d.z;
            float f4 = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt(com.maddox.il2.ai.World.Sun().ToSun.z, -0.2F, 0.1F, 0.75F, 1.0F);
            if(com.maddox.il2.ai.World.Sun().ToSun.z > -0.1F && com.maddox.il2.ai.World.Sun().ToSun.z < 0.1F && java.lang.Math.random() < 0.10000000000000001D)
                f4 += (float)java.lang.Math.random() * 0.2F;
            float f11 = 1.0F - com.maddox.il2.objects.vehicles.radios.BeaconGeneric.getConeOfSilenceMultiplier(d, d6);
            f1 = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt((float)(d1 + (double)f7) * f4, 0.0F, 7000000F, 0.0F, 1.0F);
            f1 = f1 + f11 + com.maddox.il2.objects.vehicles.radios.BeaconGeneric.floatindex(com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt((float)d * f4, 0.0F, 270000F, 0.0F, 6F), signalPropagationScale);
            if(f4 < 1.0F)
                nightError = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt(f1, 0.65F, 1.0F, 0.0F, 1.0F);
            else
                nightError = 0.0F;
        } else
        if(flag2)
        {
            f1 = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt((float)d1, 0.0F, 500000F, 0.0F, 1.0F);
            float f8 = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt((float)d4, -10000F, 0.0F, 1.0F, 0.0F);
            f1 = f1 + f8 + com.maddox.il2.objects.vehicles.radios.BeaconGeneric.floatindex(com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt((float)d, 0.0F, 190000F, 0.0F, 6F), signalPropagationScale);
        } else
        if(flag1)
        {
            float f9 = 0.0F;
            if(d4 < 0.0D)
                f9 = (float)(-3D * d4);
            double d7 = (double)aircraft.FM.getAltitude() - point3d.z;
            float f5 = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt(com.maddox.il2.ai.World.Sun().ToSun.z, -0.2F, 0.2F, 0.4F, 1.0F);
            if(com.maddox.il2.ai.World.Sun().ToSun.z > -0.1F && com.maddox.il2.ai.World.Sun().ToSun.z < 0.1F && java.lang.Math.random() < 0.20000000000000001D)
                f5 += (float)java.lang.Math.random() * 0.3F;
            float f12 = 1.0F - com.maddox.il2.objects.vehicles.radios.BeaconGeneric.getConeOfSilenceMultiplier(d, d7);
            f1 = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt((float)(d1 + (double)f9) * f5, 0.0F, 6000000F, 0.0F, 1.0F);
            f1 = f1 + f12 + com.maddox.il2.objects.vehicles.radios.BeaconGeneric.floatindex(com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt((float)d * f5, 0.0F, 300000F, 0.0F, 6F), signalPropagationScale);
            if(f5 < 1.0F)
                nightError = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt(f1, 0.65F, 1.0F, 0.0F, 1.0F);
            else
                nightError = 0.0F;
        }
        if(f1 > 1.0F)
            f1 = 1.0F;
        return f1;
    }

    private static double getCurvatureCorrectedHeight(float f, double d, double d1, float f1)
    {
        double d2 = (double)f * java.lang.Math.sin(d / 6371000D) * (double)(6371000F + f1);
        double d3 = d1 + 6371000D + (double)f * (java.lang.Math.cos(d / 6371000D) * (double)(6371000F + f1) - d1 - 6371000D);
        double d4 = java.lang.Math.sqrt(d2 * d2 + d3 * d3) - 6371000D;
        if(d4 > 0.0D)
            return d4;
        else
            return 0.0D;
    }

    public static double lineOfSightDelta(double d, double d1, double d2)
    {
        return (java.lang.Math.sqrt(12.47599983215332D * d) + java.lang.Math.sqrt(12.47599983215332D * d1)) * 1000D - d2;
    }

    protected static float floatindex(float f, float af[])
    {
        int i = (int)f;
        if(i >= af.length - 1)
            return af[af.length - 1];
        if(i < 0)
            return af[0];
        if(i == 0)
        {
            if(f > 0.0F)
                return af[0] + f * (af[1] - af[0]);
            else
                return af[0];
        } else
        {
            return af[i] + (f % (float)i) * (af[i + 1] - af[i]);
        }
    }

    public static final double Rnd(double d, double d1)
    {
        return com.maddox.il2.ai.World.Rnd().nextDouble(d, d1);
    }

    public static final float Rnd(float f, float f1)
    {
        return com.maddox.il2.ai.World.Rnd().nextFloat(f, f1);
    }

    private boolean RndB(float f)
    {
        return com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < f;
    }

    private static final long SecsToTicks(float f)
    {
        long l = (long)(0.5D + (double)(f / com.maddox.rts.Time.tickLenFs()));
        return l >= 1L ? l : 1L;
    }

    public boolean isStaticPos()
    {
        return true;
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        shot.bodyMaterial = 2;
        if(dying == 0 && shot.power > 0.0F && (!isNetMirror() || !shot.isMirage()))
            if(shot.powerType == 1)
            {
                if(!RndB(0.15F))
                    Die(shot.initiator, (short)0, true);
            } else
            {
                float f = com.maddox.il2.ai.Shot.panzerThickness(pos.getAbsOrient(), shot.v, shot.chunkName.equalsIgnoreCase("Head"), prop.PANZER_BODY_FRONT, prop.PANZER_BODY_SIDE, prop.PANZER_BODY_BACK, prop.PANZER_BODY_TOP, prop.PANZER_HEAD, prop.PANZER_HEAD_TOP);
                f *= com.maddox.il2.objects.vehicles.radios.BeaconGeneric.Rnd(0.93F, 1.07F);
                float f1 = prop.fnShotPanzer.Value(shot.power, f);
                if(f1 < 1000F && (f1 <= 1.0F || RndB(1.0F / f1)))
                    Die(shot.initiator, (short)0, true);
            }
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if(dying == 0 && (!isNetMirror() || !explosion.isMirage()) && explosion.power > 0.0F)
        {
            int i = explosion.powerType;
            if(explosion == null);
            if(i == 1)
            {
                if(com.maddox.il2.objects.vehicles.tanks.TankGeneric.splintersKill(explosion, prop.fnShotPanzer, com.maddox.il2.objects.vehicles.radios.BeaconGeneric.Rnd(0.0F, 1.0F), com.maddox.il2.objects.vehicles.radios.BeaconGeneric.Rnd(0.0F, 1.0F), this, 0.7F, 0.25F, prop.PANZER_BODY_FRONT, prop.PANZER_BODY_SIDE, prop.PANZER_BODY_BACK, prop.PANZER_BODY_TOP, prop.PANZER_HEAD, prop.PANZER_HEAD_TOP))
                    Die(explosion.initiator, (short)0, true);
            } else
            {
                int j = explosion.powerType;
                if(explosion == null);
                if(j == 2 && explosion.chunkName != null)
                {
                    Die(explosion.initiator, (short)0, true);
                } else
                {
                    float f;
                    if(explosion.chunkName != null)
                        f = 0.5F * explosion.power;
                    else
                        f = explosion.receivedTNTpower(this);
                    f *= com.maddox.il2.objects.vehicles.radios.BeaconGeneric.Rnd(0.95F, 1.05F);
                    float f1 = prop.fnExplodePanzer.Value(f, prop.PANZER_TNT_TYPE);
                    if(f1 < 1000F && (f1 <= 1.0F || RndB(1.0F / f1)))
                        Die(explosion.initiator, (short)0, true);
                }
            }
        }
    }

    private void ShowExplode(float f, com.maddox.il2.engine.Actor actor)
    {
        if(f > 0.0F)
            f = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.Rnd(f, f * 1.6F);
        com.maddox.il2.objects.effects.Explosions.runByName(prop.explodeName, this, "Smoke", "SmokeHead", f, actor);
    }

    private void Die(com.maddox.il2.engine.Actor actor, short word0, boolean flag)
    {
        if(dying == 0)
        {
            if(word0 <= 0)
            {
                if(isNetMirror())
                {
                    send_DeathRequest(actor);
                    return;
                }
                boolean flag1 = true;
            }
            dying = 1;
            com.maddox.il2.ai.World.onActorDied(this, actor);
            if(prop.meshName1 == null)
                mesh().makeAllMaterialsDarker(0.22F, 0.35F);
            else
                setMesh(prop.meshName1);
            int i = mesh().hookFind("Ground_Level");
            if(i != -1)
            {
                com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
                mesh().hookMatrix(i, matrix4d);
                heightAboveLandSurface = (float)(-matrix4d.m23);
            }
            Align();
            if(flag)
                ShowExplode(15F, actor);
            if(flag)
                send_DeathCommand(actor);
        }
    }

    public void destroy()
    {
        if(!isDestroyed())
            super.destroy();
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    protected BeaconGeneric()
    {
        this(constr_arg1, constr_arg2);
    }

    private BeaconGeneric(com.maddox.il2.objects.vehicles.radios.BeaconProperties beaconproperties, com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(beaconproperties.meshName);
        prop = null;
        dying = 0;
        prop = beaconproperties;
        actorspawnarg.setStationary(this);
        collide(true);
        drawing(true);
        createNetObject(actorspawnarg.netChannel, actorspawnarg.netIdRemote);
        heightAboveLandSurface = 0.0F;
        int i = mesh().hookFind("Ground_Level");
        if(i != -1)
        {
            com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
            mesh().hookMatrix(i, matrix4d);
            heightAboveLandSurface = (float)(-matrix4d.m23);
        } else
        {
            java.lang.System.out.println("Stationary " + getClass().getName() + ": hook Ground_Level not found");
        }
        Align();
    }

    private void Align()
    {
        pos.getAbs(p);
        p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y) + (double)heightAboveLandSurface;
        o.setYPR(pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
        pos.setAbs(p, o);
    }

    public void align()
    {
        Align();
    }

    public boolean unmovableInFuture()
    {
        return true;
    }

    public void collisionDeath()
    {
        if(!isNet())
        {
            ShowExplode(-1F, null);
            destroy();
        }
    }

    public float futurePosition(float f, com.maddox.JGP.Point3d point3d)
    {
        pos.getAbs(point3d);
        return f > 0.0F ? f : 0.0F;
    }

    private void send_DeathCommand(com.maddox.il2.engine.Actor actor)
    {
        if(isNetMaster())
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            try
            {
                netmsgguaranted.writeByte(68);
                netmsgguaranted.writeNetObj(actor != null ? ((com.maddox.rts.NetObj) (actor.net)) : ((com.maddox.rts.NetObj) ((com.maddox.il2.engine.ActorNet)null)));
                net.post(netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }
    }

    private void send_DeathRequest(com.maddox.il2.engine.Actor actor)
    {
        if(isNetMirror() && !(net.masterChannel() instanceof com.maddox.rts.NetChannelInStream))
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(100);
                netmsgguaranted.writeNetObj(actor != null ? ((com.maddox.rts.NetObj) (actor.net)) : ((com.maddox.rts.NetObj) ((com.maddox.il2.engine.ActorNet)null)));
                net.postTo(net.masterChannel(), netmsgguaranted);
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

    public void netFirstUpdate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        netmsgguaranted.writeByte(73);
        if(dying == 0)
            netmsgguaranted.writeShort(0);
        else
            netmsgguaranted.writeShort(1);
        net.postTo(netchannel, netmsgguaranted);
    }

    protected static float cvt(float f, float f1, float f2, float f3, float f4)
    {
        f = java.lang.Math.min(java.lang.Math.max(f, f1), f2);
        return f3 + ((f4 - f3) * (f - f1)) / (f2 - f1);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private com.maddox.il2.objects.vehicles.radios.BeaconProperties prop;
    private float heightAboveLandSurface;
    private int dying;
    static final int DYING_NONE = 0;
    static final int DYING_DEAD = 1;
    public static com.maddox.il2.objects.vehicles.radios.BeaconProperties constr_arg1 = null;
    private static com.maddox.il2.engine.ActorSpawnArg constr_arg2 = null;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    protected static com.maddox.JGP.Vector3d V = new Vector3d();
    private static final int numberOfSamplePoints = 500;
    private static final int attSamplesPerCycle = 20;
    private static float attenuationSamples[] = new float[500];
    private static int currentAttSampleSlot = 1;
    private static final int mountainSamplesPerRow = 50;
    private static final int mountainSamplesPerCycle = 10;
    private static int currentMntSampleCol = 0;
    private static int currentMntSampleRow = 0;
    private static float mountainErrorSamples[][] = new float[50][50];
    private static final float mntSampleRadius = 20000F;
    private static final float mntSingleSampleLen = 800F;
    private static final int EARTH_RADIUS = 0x6136b8;
    private static float nightError = 0.0F;
    private static int terErrDirection = 1;
    private static int ngtErrDirection = -1;
    private static float mntNE = 0.0F;
    private static float mntSE = 0.0F;
    private static float mntSW = 0.0F;
    private static float mntNW = 0.0F;
    private static final float signalPropagationScale[] = {
        0.0F, 0.4F, 0.6F, 0.77F, 0.89F, 0.97F, 1.0F
    };
    static java.lang.Class class$com$maddox$il2$objects$vehicles$radios$Beacon$LorenzBLBeacon; /* synthetic field */
    static java.lang.Class class$com$maddox$il2$objects$vehicles$radios$Beacon$LorenzBLBeacon_LongRunway; /* synthetic field */
    static java.lang.Class class$com$maddox$il2$objects$vehicles$radios$Beacon$LorenzBLBeacon_AAIAS; /* synthetic field */




}
