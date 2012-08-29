// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   House.java

package com.maddox.il2.objects.buildings;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MeshShared;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Finger;
import com.maddox.rts.Message;
import com.maddox.rts.SectFile;
import com.maddox.rts.SoftClass;
import com.maddox.rts.Spawn;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.buildings:
//            HouseManager

public final class House extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.il2.objects.ActorAlign, com.maddox.rts.SoftClass
{
    public static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

        private static float getF(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, float f, float f1)
        {
            float f2 = sectfile.get(s, s1, -9865.345F);
            if(f2 == -9865.345F || f2 < f || f2 > f1)
            {
                if(f2 == -9865.345F)
                    java.lang.System.out.println("House: Value of [" + s + "]:<" + s1 + "> " + "not found");
                else
                    java.lang.System.out.println("House: Value of [" + s + "]:<" + s1 + "> (" + f2 + ")" + " is out of range (" + f + ";" + f1 + ")");
                throw new RuntimeException("Can't set property");
            } else
            {
                return f2;
            }
        }

        private static float getF(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, float f, float f1, float f2)
        {
            float f3 = sectfile.get(s, s1, -9865.345F);
            if(f3 == -9865.345F)
                return f;
            if(f3 < f1 || f3 > f2)
            {
                java.lang.System.out.println("House: Value of [" + s + "]:<" + s1 + "> (" + f3 + ")" + " is out of range (" + f1 + ";" + f2 + ")");
                throw new RuntimeException("Can't set property");
            } else
            {
                return f3;
            }
        }

        private static java.lang.String getS(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1)
        {
            java.lang.String s2 = sectfile.get(s, s1);
            if(s2 == null || s2.length() <= 0)
            {
                java.lang.System.out.print("House: Parameter [" + s + "]:<" + s1 + "> ");
                java.lang.System.out.println(s2 != null ? "is empty" : "not found");
                throw new RuntimeException("Can't set property");
            } else
            {
                return s2;
            }
        }

        private static com.maddox.il2.objects.buildings.Properties LoadHouseProperties(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, java.lang.String s2)
        {
            com.maddox.il2.objects.buildings.Properties properties = new Properties();
            properties.SoftClassInnerName = s2;
            properties.FingerOfFullClassName = com.maddox.rts.Finger.Int(s1);
            java.lang.String s3 = sectfile.get(s, "equals");
            if(s3 == null || s3.length() <= 0)
                s3 = s;
            properties.MESH0_NAME = com.maddox.il2.objects.buildings.SPAWN.getS(sectfile, s, "MeshLive");
            properties.MESH1_NAME = sectfile.get(s, "MeshDead");
            if(properties.MESH1_NAME == null)
                com.maddox.il2.objects.buildings.SPAWN.getS(sectfile, s, "MeshDead");
            if(properties.MESH1_NAME.equals(""))
                properties.MESH1_NAME = null;
            properties.ADD_HEIGHT_0 = com.maddox.il2.objects.buildings.SPAWN.getF(sectfile, s3, "AddHeightLive", 0.0F, -100F, 100F);
            properties.ADD_HEIGHT_1 = com.maddox.il2.objects.buildings.SPAWN.getF(sectfile, s3, "AddHeightDead", 0.0F, -100F, 100F);
            properties.ALIGN_TO_LAND_NORMAL = com.maddox.il2.objects.buildings.SPAWN.getF(sectfile, s3, "AlignToLand", 0.0F, 0.0F, 1.0F) > 0.0F;
            properties.ALIGN_TO_LAND_NORMAL = com.maddox.il2.objects.buildings.SPAWN.getF(sectfile, s3, "AlignToLand", 0.0F, 0.0F, 1.0F) > 0.0F;
            properties.IGNORE_SHADOW_DATA = false;
            if(sectfile.get(s, "IgnoreShadowData") != null)
                properties.IGNORE_SHADOW_DATA = true;
            properties.PANZER = com.maddox.il2.objects.buildings.SPAWN.getF(sectfile, s3, "Panzer", 0.0001F, 50F);
            java.lang.String s4 = com.maddox.il2.objects.buildings.SPAWN.getS(sectfile, s3, "Body");
            if(s4.equalsIgnoreCase("WoodSmall"))
            {
                properties.MAT_TYPE = 0;
                properties.EFF_BODY_MATERIAL = 3;
                properties.EXPL_TYPE = 0;
                properties.MIN_TNT = 0.25F * (properties.PANZER / 0.025F);
                properties.MAX_TNT = properties.MIN_TNT * 2.0F;
                properties.PROBAB_DEATH_WHEN_EXPLOSION = 0.01F;
            } else
            if(s4.equalsIgnoreCase("WoodMiddle"))
            {
                properties.MAT_TYPE = 0;
                properties.EFF_BODY_MATERIAL = 3;
                properties.EXPL_TYPE = 1;
                properties.MIN_TNT = 5F * (properties.PANZER / 0.15F);
                properties.MAX_TNT = properties.MIN_TNT * 1.7F;
                properties.PROBAB_DEATH_WHEN_EXPLOSION = 0.01F;
            } else
            if(s4.equalsIgnoreCase("RockMiddle"))
            {
                properties.MAT_TYPE = 1;
                properties.EFF_BODY_MATERIAL = 4;
                properties.EXPL_TYPE = 2;
                properties.MIN_TNT = 6F * (properties.PANZER / 0.12F);
                properties.MAX_TNT = properties.MIN_TNT * 1.7F;
                properties.PROBAB_DEATH_WHEN_EXPLOSION = 0.01F;
            } else
            if(s4.equalsIgnoreCase("RockBig"))
            {
                properties.MAT_TYPE = 1;
                properties.EFF_BODY_MATERIAL = 4;
                properties.EXPL_TYPE = 3;
                properties.MIN_TNT = 12F * (properties.PANZER / 0.24F);
                properties.MAX_TNT = properties.MIN_TNT * 1.7F;
                properties.PROBAB_DEATH_WHEN_EXPLOSION = 0.05F;
            } else
            if(s4.equalsIgnoreCase("RockHuge"))
            {
                properties.MAT_TYPE = 1;
                properties.EFF_BODY_MATERIAL = 4;
                properties.EXPL_TYPE = 4;
                properties.MIN_TNT = 30F * (properties.PANZER / 0.48F);
                properties.MAX_TNT = properties.MIN_TNT * 1.7F;
                properties.PROBAB_DEATH_WHEN_EXPLOSION = 0.05F;
            } else
            if(s4.equalsIgnoreCase("FuelSmall"))
            {
                properties.MAT_TYPE = 2;
                properties.EFF_BODY_MATERIAL = 2;
                properties.EXPL_TYPE = 5;
                properties.MIN_TNT = 0.2F * (properties.PANZER / 0.002F);
                properties.MAX_TNT = properties.MIN_TNT * 1.7F;
                properties.PROBAB_DEATH_WHEN_EXPLOSION = 0.01F;
            } else
            if(s4.equalsIgnoreCase("FuelBig"))
            {
                properties.MAT_TYPE = 2;
                properties.EFF_BODY_MATERIAL = 2;
                properties.EXPL_TYPE = 6;
                properties.MIN_TNT = 0.8F * (properties.PANZER / 0.008F);
                properties.MAX_TNT = properties.MIN_TNT * 1.7F;
                properties.PROBAB_DEATH_WHEN_EXPLOSION = 0.01F;
            } else
            {
                java.lang.System.out.println("House: Undefined Body type in class '" + s1 + "'");
                java.lang.System.out.println("Allowed body types are:WoodSmall,WoodMiddle,RockMiddle,RockBig,RockHuge,FuelSmall,FuelBig");
                throw new RuntimeException("Can't register house object");
            }
            return properties;
        }

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            com.maddox.il2.objects.buildings.House house = null;
            try
            {
                house = (com.maddox.il2.objects.buildings.House)cls.newInstance();
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                return null;
            }
            house.prop = prop;
            if(!actorspawnarg.armyExist)
            {
                actorspawnarg.armyExist = true;
                actorspawnarg.army = 0;
            }
            actorspawnarg.setStationaryNoIcon(house);
            try
            {
                house.activateMesh();
            }
            catch(java.lang.RuntimeException runtimeexception)
            {
                house.destroy();
                throw runtimeexception;
            }
            if(!house.prop.meshTested && house.mesh().collisionR() <= 0.0F)
                java.lang.System.out.println("##### House without collision (" + house.prop.MESH0_NAME + ")");
            house.prop.meshTested = true;
            return house;
        }

        public java.lang.Class cls;
        public com.maddox.il2.objects.buildings.Properties prop;

        protected SPAWN(java.lang.String s)
        {
            cls = getClass().getDeclaringClass();
            java.lang.String s1 = cls.getName();
            java.lang.String s2 = s1.substring(s1.lastIndexOf('.') + 1);
            java.lang.String s3 = s2 + '$' + s;
            java.lang.String s4 = s1 + '$' + s;
            com.maddox.rts.SectFile sectfile = com.maddox.il2.objects.Statics.getBuildingsFile();
            java.lang.String s5 = null;
            int i = sectfile.sections();
            int j = 0;
            do
            {
                if(j >= i)
                    break;
                if(sectfile.sectionName(j).endsWith(s3))
                {
                    s5 = sectfile.sectionName(j);
                    break;
                }
                j++;
            } while(true);
            if(s5 == null)
            {
                java.lang.System.out.println("House: Section " + s3 + " not found");
                throw new RuntimeException("Can't register spawner");
            }
            try
            {
                prop = com.maddox.il2.objects.buildings.SPAWN.LoadHouseProperties(sectfile, s5, s4, s);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("Problem in house spawn registration: " + s4);
            }
            com.maddox.rts.Spawn.add_SoftClass(s4, this);
        }
    }

    public static class Properties
    {

        public java.lang.String SoftClassInnerName;
        public int FingerOfFullClassName;
        public java.lang.String MESH0_NAME;
        public java.lang.String MESH1_NAME;
        private boolean meshTested;
        public float ADD_HEIGHT_0;
        public float ADD_HEIGHT_1;
        public boolean ALIGN_TO_LAND_NORMAL;
        public boolean IGNORE_SHADOW_DATA;
        public int MAT_TYPE;
        public int EFF_BODY_MATERIAL;
        public int EXPL_TYPE;
        public float PANZER;
        public float MIN_TNT;
        public float MAX_TNT;
        public float PROBAB_DEATH_WHEN_EXPLOSION;
        public boolean bInitHeight0;
        public boolean bInitHeight1;
        public float heightAboveLandSurface0;
        public float heightAboveLandSurface1;



        public Properties()
        {
            SoftClassInnerName = null;
            FingerOfFullClassName = 0;
            MESH0_NAME = "3do/BuildingsGeneral/NameNotSpecified.sim";
            MESH1_NAME = "3do/BuildingsGeneral/DMG/NameNotSpecified.sim";
            meshTested = false;
            ADD_HEIGHT_0 = 0.0F;
            ADD_HEIGHT_1 = 0.0F;
            ALIGN_TO_LAND_NORMAL = false;
            IGNORE_SHADOW_DATA = false;
            MAT_TYPE = -1;
            EFF_BODY_MATERIAL = -1;
            EXPL_TYPE = -1;
            PANZER = 0.001F;
            MIN_TNT = 0.07F;
            MAX_TNT = 0.071F;
            PROBAB_DEATH_WHEN_EXPLOSION = 0.4F;
            bInitHeight0 = false;
            bInitHeight1 = false;
        }
    }


    public House()
    {
        prop = null;
    }

    public static void registerSpawner(java.lang.String s)
    {
        new SPAWN(s);
    }

    public java.lang.String fullClassName()
    {
        return getClass().getName() + "$" + prop.SoftClassInnerName;
    }

    public int fingerOfFullClassName()
    {
        return prop.FingerOfFullClassName;
    }

    public java.lang.String getMeshLiveName()
    {
        return prop.MESH0_NAME;
    }

    private static final boolean RndB(float f)
    {
        return com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < f;
    }

    private static final float RndF(float f, float f1)
    {
        return com.maddox.il2.ai.World.Rnd().nextFloat(f, f1);
    }

    private static final void internalerrror(int i)
    {
        java.lang.System.out.println("*** Internal error#" + i + " in House ***");
        throw new RuntimeException("Can't initialize House");
    }

    private static final void InitTablesOfEnergyToKill()
    {
        PenetrateEnergyToKill = new float[3][][];
        PenetrateThickness = new float[3][];
        PenetrateThickness[0] = (new float[] {
            0.025F, 0.15F
        });
        PenetrateEnergyToKill[0] = (new float[][] {
            new float[] {
                23400F, 131400F, 252000F
            }, new float[] {
                131400F, 369000F, 1224000F
            }
        });
        PenetrateThickness[1] = (new float[] {
            0.12F, 0.24F
        });
        PenetrateEnergyToKill[1] = (new float[][] {
            new float[] {
                131400F, 252000F, 3295500F
            }, new float[] {
                369000F, 3295500F, 5120000F
            }
        });
        PenetrateThickness[2] = (new float[] {
            0.002F, 0.008F
        });
        PenetrateEnergyToKill[2] = (new float[][] {
            new float[] {
                511.225F, 2453.88F, 23400F
            }, new float[] {
                2453.88F, 10140F, 23400F
            }
        });
        for(int i = 0; i < 3; i++)
        {
            if((double)java.lang.Math.abs(PenetrateThickness[i][0] - PenetrateThickness[i][1]) < 0.001D)
                com.maddox.il2.objects.buildings.House.internalerrror(1);
            for(int j = 0; j <= 1; j++)
            {
                float af[] = PenetrateEnergyToKill[i][j];
                if((double)(af[1] - af[0]) < 0.001D || (double)(af[2] - af[1]) < 0.001D)
                    com.maddox.il2.objects.buildings.House.internalerrror(2);
            }

            float f = java.lang.Math.min(PenetrateEnergyToKill[i][0][0], PenetrateEnergyToKill[i][1][0]);
            float f1 = java.lang.Math.max(PenetrateEnergyToKill[i][0][2], PenetrateEnergyToKill[i][1][2]);
            for(int k = 0; k <= 100; k++)
            {
                float f2 = f + ((f1 - f) * (float)k) / 100F;
                float f3 = com.maddox.il2.objects.buildings.House.ComputeProbabOfPenetrateKill(i, 0, f2);
                float f4 = com.maddox.il2.objects.buildings.House.ComputeProbabOfPenetrateKill(i, 1, f2);
                if(f3 < f4)
                {
                    java.lang.System.out.println(i + " i,e0,e1,e:" + k + " " + f + " " + f1 + " " + f2 + " prob0,1: " + f3 + " " + f4);
                    com.maddox.il2.objects.buildings.House.internalerrror(3);
                }
            }

        }

    }

    private static final float ComputeProbabOfPenetrateKill(int i, int j, float f)
    {
        float af[] = PenetrateEnergyToKill[i][j];
        float f1;
        float f2;
        if(f < af[1])
        {
            if(f < af[0])
                return 0.0F;
            f1 = 0.2F / (af[1] - af[0]);
            f2 = 0.1F - af[0] * f1;
        } else
        {
            if(f >= af[2])
                return 1.0F;
            f1 = 0.7F / (af[2] - af[1]);
            f2 = 0.3F - af[1] * f1;
        }
        return f * f1 + f2;
    }

    private final float ComputeProbabOfPenetrateKill(float f, int i)
    {
        if(i <= 0)
            return 0.0F;
        float f1 = com.maddox.il2.objects.buildings.House.ComputeProbabOfPenetrateKill(prop.MAT_TYPE, 0, f);
        float f2 = com.maddox.il2.objects.buildings.House.ComputeProbabOfPenetrateKill(prop.MAT_TYPE, 1, f);
        float af[] = PenetrateThickness[prop.MAT_TYPE];
        float f3 = (f2 - f1) / (af[1] - af[0]);
        float f4 = f1 - af[0] * f3;
        float f5 = prop.PANZER * f3 + f4;
        if(f5 < 0.1F)
            f5 = 0.0F;
        else
        if(f5 >= 1.0F)
            f5 = 1.0F;
        else
        if(i > 1)
            f5 = 1.0F - (float)java.lang.Math.pow(1.0F - f5, i);
        return f5;
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        shot.bodyMaterial = prop.EFF_BODY_MATERIAL;
        if(!isAlive())
            return;
        if(shot.power <= 0.0F)
            return;
        if(shot.powerType == 1)
        {
            if(prop.MAT_TYPE != 2)
                return;
            float f = shot.power * com.maddox.il2.objects.buildings.House.RndF(0.75F, 1.15F);
            float f2 = 0.256F * (float)java.lang.Math.sqrt(java.lang.Math.sqrt(f));
            if(prop.PANZER > f2)
            {
                return;
            } else
            {
                die(shot.initiator, true);
                return;
            }
        }
        float f1 = ComputeProbabOfPenetrateKill(shot.power, 1);
        if(!com.maddox.il2.objects.buildings.House.RndB(f1))
        {
            return;
        } else
        {
            die(shot.initiator, true);
            return;
        }
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if(!isAlive())
            return;
        if(explosion.power <= 0.0F)
            return;
        com.maddox.il2.ai.Explosion _tmp = explosion;
        if(explosion.powerType == 1)
        {
            float af[] = new float[6];
            mesh().getBoundBox(af);
            pos.getAbs(p);
            p.x = (p.x - (double)af[0]) + (double)(af[3] - af[0]);
            p.y = (p.y - (double)af[1]) + (double)(af[4] - af[1]);
            p.z = (p.z - (double)af[2]) + (double)(af[5] - af[2]);
            float af1[] = new float[2];
            explosion.computeSplintersHit(p, mesh().collisionR(), 0.7F, af1);
            com.maddox.il2.ai.Explosion _tmp1 = explosion;
            float f = 0.015F * af1[1] * af1[1] * 0.5F;
            float f1 = ComputeProbabOfPenetrateKill(f, (int)(af1[0] + 0.5F));
            if(com.maddox.il2.objects.buildings.House.RndB(f1))
                die(explosion.initiator, true);
            return;
        }
        com.maddox.il2.ai.Explosion _tmp2 = explosion;
        if(explosion.powerType == 0)
        {
            if(com.maddox.il2.ai.Explosion.killable(this, explosion.receivedPower(this), prop.MIN_TNT, prop.MAX_TNT, prop.PROBAB_DEATH_WHEN_EXPLOSION))
                die(explosion.initiator, true);
            return;
        }
        if(prop.MAT_TYPE == 1)
        {
            return;
        } else
        {
            die(explosion.initiator, true);
            return;
        }
    }

    protected void runDeathShow()
    {
        float af[] = new float[6];
        mesh().getBoundBox(af);
        com.maddox.il2.objects.effects.Explosions.HouseExplode(prop.EXPL_TYPE, pos.getAbs(), af);
    }

    private void die(com.maddox.il2.engine.Actor actor, boolean flag)
    {
        if(!isAlive())
            return;
        if(flag)
            runDeathShow();
        com.maddox.il2.ai.World.onActorDied(this, actor);
        if(getOwner() instanceof com.maddox.il2.objects.buildings.HouseManager)
            ((com.maddox.il2.objects.buildings.HouseManager)getOwner()).onHouseDie(this, actor);
        else
        if(com.maddox.il2.ai.World.cur().statics != null)
            com.maddox.il2.ai.World.cur().statics.onHouseDied(this, actor);
    }

    public void doDieShow()
    {
        runDeathShow();
    }

    public void setDiedFlag(boolean flag)
    {
        super.setDiedFlag(flag);
        activateMesh();
    }

    private void activateMesh()
    {
        boolean flag = getDiedFlag();
        java.lang.String s = flag ? prop.MESH1_NAME : prop.MESH0_NAME;
        if(s == null)
        {
            collide(false);
            drawing(false);
            return;
        }
        setMesh(com.maddox.il2.engine.MeshShared.get(s));
        if(flag ? !prop.bInitHeight1 : !prop.bInitHeight0)
        {
            int i = mesh().hookFind("Ground_Level");
            float f = 0.0F;
            if(i != -1)
            {
                com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
                mesh().hookMatrix(i, matrix4d);
                f = (float)(-matrix4d.m23);
            } else
            {
                float af[] = new float[6];
                mesh().getBoundBox(af);
                f = -af[2];
            }
            if(!flag)
            {
                prop.bInitHeight0 = true;
                prop.heightAboveLandSurface0 = f;
            } else
            {
                prop.bInitHeight1 = true;
                prop.heightAboveLandSurface1 = f;
            }
        }
        if(prop.IGNORE_SHADOW_DATA)
            mesh().setFastShadowVisibility(2);
        else
            mesh().setFastShadowVisibility(1);
        align();
        collide(!flag);
        drawing(true);
    }

    public void align()
    {
        pos.getAbs(p);
        p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y);
        if(!getDiedFlag())
            p.z += prop.ADD_HEIGHT_0 + prop.heightAboveLandSurface0;
        else
            p.z += prop.ADD_HEIGHT_1 + prop.heightAboveLandSurface1;
        o.setYPR(pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
        if(prop.ALIGN_TO_LAND_NORMAL)
        {
            com.maddox.il2.engine.Engine.land().N(p.x, p.y, n);
            o.orient(n);
        }
        pos.setAbs(p, o);
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public boolean isStaticPos()
    {
        return true;
    }

    public float futurePosition(float f, com.maddox.JGP.Point3d point3d)
    {
        pos.getAbs(point3d);
        if(f <= 0.0F)
            return 0.0F;
        else
            return f;
    }

    private com.maddox.il2.objects.buildings.Properties prop;
    private static final int MAT_WOOD = 0;
    private static final int MAT_BRICK = 1;
    private static final int MAT_STEEL = 2;
    private static final int N_MAT_TYPES = 3;
    private static float PenetrateEnergyToKill[][][] = (float[][][])null;
    private static float PenetrateThickness[][] = (float[][])null;
    private static final float KinEnergy_4 = 511.225F;
    private static final float KinEnergy_7_62 = 2453.88F;
    private static final float KinEnergy_12_7 = 10140F;
    private static final float KinEnergy_20 = 23400F;
    private static final float KinEnergy_37 = 131400F;
    private static final float KinEnergy_45 = 252000F;
    private static final float KinEnergy_50 = 369000F;
    private static final float KinEnergy_75 = 1224000F;
    private static final float KinEnergy_100 = 3295500F;
    private static final float KinEnergy_203 = 5120000F;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();
    private static float probab[] = new float[2];

    static 
    {
        com.maddox.il2.objects.buildings.House.InitTablesOfEnergyToKill();
    }



}
