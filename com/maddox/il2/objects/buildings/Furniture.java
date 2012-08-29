// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Furniture.java

package com.maddox.il2.objects.buildings;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
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
import com.maddox.rts.Finger;
import com.maddox.rts.Message;
import com.maddox.rts.SectFile;
import com.maddox.rts.SoftClass;
import com.maddox.rts.Spawn;
import java.io.PrintStream;

public final class Furniture extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.objects.ActorAlign, com.maddox.rts.SoftClass
{
    private static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

        private static float getF(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, float f, float f1)
        {
            float f2 = sectfile.get(s, s1, -9865.345F);
            if(f2 == -9865.345F || f2 < f || f2 > f1)
            {
                if(f2 == -9865.345F)
                    java.lang.System.out.println("Furniture: Value of [" + s + "]:<" + s1 + "> " + "not found");
                else
                    java.lang.System.out.println("Furniture: Value of [" + s + "]:<" + s1 + "> (" + f2 + ")" + " is out of range (" + f + ";" + f1 + ")");
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
                java.lang.System.out.println("Furniture: Value of [" + s + "]:<" + s1 + "> (" + f3 + ")" + " is out of range (" + f1 + ";" + f2 + ")");
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
                java.lang.System.out.print("Furniture: Parameter [" + s + "]:<" + s1 + "> ");
                java.lang.System.out.println(s2 != null ? "is empty" : "not found");
                throw new RuntimeException("Can't set property");
            } else
            {
                return s2;
            }
        }

        private static com.maddox.il2.objects.buildings.Properties LoadProperties(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, java.lang.String s2)
        {
            com.maddox.il2.objects.buildings.Properties properties = new Properties();
            properties.SoftClassInnerName = s2;
            properties.FingerOfFullClassName = com.maddox.rts.Finger.Int(s1);
            java.lang.String s3 = sectfile.get(s, "equals");
            if(s3 == null || s3.length() <= 0)
                s3 = s;
            properties.MESH_NAME = com.maddox.il2.objects.buildings.SPAWN.getS(sectfile, s, "Mesh");
            properties.ADD_HEIGHT = com.maddox.il2.objects.buildings.SPAWN.getF(sectfile, s3, "AddHeight", 0.0F, -100F, 100F);
            properties.ALIGN_TO_LAND_NORMAL = com.maddox.il2.objects.buildings.SPAWN.getF(sectfile, s3, "AlignToLand", 0.0F, 0.0F, 1.0F) > 0.0F;
            return properties;
        }

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            com.maddox.il2.objects.buildings.Furniture furniture = null;
            try
            {
                furniture = (com.maddox.il2.objects.buildings.Furniture)cls.newInstance();
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                return null;
            }
            furniture.prop = prop;
            if(!actorspawnarg.armyExist)
            {
                actorspawnarg.armyExist = true;
                actorspawnarg.army = 0;
            }
            actorspawnarg.setStationaryNoIcon(furniture);
            try
            {
                furniture.activateMesh();
            }
            catch(java.lang.RuntimeException runtimeexception)
            {
                furniture.destroy();
                throw runtimeexception;
            }
            return furniture;
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
            for(int j = 0; j < i; j++)
            {
                if(!sectfile.sectionName(j).endsWith(s3))
                    continue;
                s5 = sectfile.sectionName(j);
                break;
            }

            if(s5 == null)
            {
                java.lang.System.out.println("Furniture: Section " + s3 + " not found");
                throw new RuntimeException("Can't register spawner");
            }
            try
            {
                prop = com.maddox.il2.objects.buildings.SPAWN.LoadProperties(sectfile, s5, s4, s);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("Problem in Furniture spawn registration: " + s4);
            }
            com.maddox.rts.Spawn.add_SoftClass(s4, this);
        }
    }

    public static class Properties
    {

        public java.lang.String SoftClassInnerName;
        public int FingerOfFullClassName;
        public java.lang.String MESH_NAME;
        public boolean ALIGN_TO_LAND_NORMAL;
        public float ADD_HEIGHT;
        public boolean bInitHeight;
        public float heightAboveLandSurface;

        public Properties()
        {
            SoftClassInnerName = null;
            FingerOfFullClassName = 0;
            MESH_NAME = "3do/BuildingsGeneral/NameNotSpecified.sim";
            ALIGN_TO_LAND_NORMAL = false;
            ADD_HEIGHT = 0.0F;
            bInitHeight = false;
        }
    }


    public Furniture()
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

    public void setDiedFlag(boolean flag)
    {
        super.setDiedFlag(flag);
        activateMesh();
    }

    private void activateMesh()
    {
        setMesh(com.maddox.il2.engine.MeshShared.get(prop.MESH_NAME));
        if(!prop.bInitHeight)
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
            prop.heightAboveLandSurface = f;
            prop.bInitHeight = true;
        }
        mesh().setFastShadowVisibility(1);
        align();
        drawing(true);
    }

    public void align()
    {
        pos.getAbs(p);
        p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y);
        p.z += prop.ADD_HEIGHT + prop.heightAboveLandSurface;
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
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();



}
