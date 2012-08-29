// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Plate.java

package com.maddox.il2.objects.buildings;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.LandPlate;
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

public final class Plate extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.objects.ActorAlign, com.maddox.il2.engine.LandPlate, com.maddox.rts.SoftClass
{
    private static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

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

        private static com.maddox.il2.objects.buildings.Properties LoadProperties(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, java.lang.String s2)
        {
            com.maddox.il2.objects.buildings.Properties properties = new Properties();
            properties.SoftClassInnerName = s2;
            properties.FingerOfFullClassName = com.maddox.rts.Finger.Int(s1);
            properties.MeshName = com.maddox.il2.objects.buildings.SPAWN.getS(sectfile, s, "Mesh");
            properties.bGround = s2.indexOf("Ground") >= 0;
            return properties;
        }

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            com.maddox.il2.objects.buildings.Plate plate = null;
            try
            {
                plate = (com.maddox.il2.objects.buildings.Plate)cls.newInstance();
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                return null;
            }
            plate.prop = prop;
            try
            {
                plate.setMesh(new MeshShared(prop.MeshName));
            }
            catch(java.lang.RuntimeException runtimeexception)
            {
                plate.destroy();
                throw runtimeexception;
            }
            actorspawnarg.set(plate);
            plate.align();
            plate.drawing(true);
            return plate;
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
                java.lang.System.out.println("Plate: Section " + s3 + " not found");
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
                java.lang.System.out.println("Problem in plate spawn registration: " + s4);
            }
            com.maddox.rts.Spawn.add_SoftClass(s4, this);
        }
    }

    public static class Properties
    {

        public java.lang.String SoftClassInnerName;
        public int FingerOfFullClassName;
        public java.lang.String MeshName;
        public boolean bGround;

        public Properties()
        {
            SoftClassInnerName = null;
            FingerOfFullClassName = 0;
            MeshName = null;
            bGround = false;
        }
    }


    public Plate()
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

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public boolean isStaticPos()
    {
        return true;
    }

    public boolean isGround()
    {
        return prop.bGround;
    }

    public void setDiedFlag(boolean flag)
    {
        super.setDiedFlag(flag);
        align();
        drawing(true);
    }

    public void align()
    {
        mesh().setFastShadowVisibility(2);
        pos.getAbs(p);
        p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y) + 0.25D;
        o.setYPR(pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
        com.maddox.il2.engine.Engine.land().N(p.x, p.y, n);
        o.orient(n);
        pos.setAbs(p, o);
    }

    public float futurePosition(float f, com.maddox.JGP.Point3d point3d)
    {
        pos.getAbs(point3d);
        if(f <= 0.0F)
            return 0.0F;
        else
            return f;
    }

    private static final float heightAboveLandSurface = 0.25F;
    private com.maddox.il2.objects.buildings.Properties prop;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();


}
