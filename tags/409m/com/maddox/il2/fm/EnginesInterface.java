// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   EnginesInterface.java

package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.il2.fm:
//            FMMath, Motor, FlightModel

public class EnginesInterface extends com.maddox.il2.fm.FMMath
{

    public EnginesInterface()
    {
        num = 0;
        producedF = new Vector3d();
        producedM = new Vector3d();
        reference = null;
    }

    public void load(com.maddox.il2.fm.FlightModel flightmodel, com.maddox.rts.SectFile sectfile)
    {
        reference = flightmodel;
        java.lang.String s = "Engine";
        for(num = 0; sectfile.get(s, "Engine" + num + "Family") != null; num++);
        engines = new com.maddox.il2.fm.Motor[num];
        for(tmpI = 0; tmpI < num; tmpI++)
            engines[tmpI] = new Motor();

        bCurControl = new boolean[num];
        com.maddox.il2.objects.air.Aircraft.debugprintln(reference.actor, "Loading " + num + " engine(s) from '" + sectfile.toString() + "....");
        for(tmpI = 0; tmpI < num; tmpI++)
        {
            java.lang.String s1 = sectfile.get(s, "Engine" + tmpI + "Family");
            java.lang.String s2 = sectfile.get(s, "Engine" + tmpI + "SubModel");
            com.maddox.il2.objects.air.Aircraft.debugprintln(reference.actor, "Loading engine model from '" + s1 + ".emd', submodel '" + s2 + "'....");
            engines[tmpI].load(flightmodel, "FlightModels/" + s1 + ".emd", s2, tmpI);
        }

        if(sectfile.get(s, "Position0x", -99999F) != -99999F)
        {
            com.maddox.JGP.Point3d point3d = new Point3d();
            com.maddox.JGP.Vector3f vector3f = new Vector3f();
            for(tmpI = 0; tmpI < num; tmpI++)
            {
                point3d.x = sectfile.get(s, "Position" + tmpI + "x", 0.0F);
                point3d.y = sectfile.get(s, "Position" + tmpI + "y", 0.0F);
                point3d.z = sectfile.get(s, "Position" + tmpI + "z", 0.0F);
                engines[tmpI].setPos(point3d);
                vector3f.x = sectfile.get(s, "Vector" + tmpI + "x", 0.0F);
                vector3f.y = sectfile.get(s, "Vector" + tmpI + "y", 0.0F);
                vector3f.z = sectfile.get(s, "Vector" + tmpI + "z", 0.0F);
                engines[tmpI].setVector(vector3f);
                point3d.x = sectfile.get(s, "PropPosition" + tmpI + "x", 0.0F);
                point3d.y = sectfile.get(s, "PropPosition" + tmpI + "y", 0.0F);
                point3d.z = sectfile.get(s, "PropPosition" + tmpI + "z", 0.0F);
                engines[tmpI].setPropPos(point3d);
            }

        }
        setCurControlAll(true);
    }

    public void setNotMirror(boolean flag)
    {
        for(int i = 0; i < getNum(); i++)
            engines[i].setMaster(flag);

    }

    public void set(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.JGP.Point3d point3d = new Point3d(0.0D, 0.0D, 0.0D);
        com.maddox.il2.engine.Loc loc = new Loc();
        if(engines[0].getPropPos().distanceSquared(new Point3f(0.0F, 0.0F, 0.0F)) > 0.0F)
            return;
        com.maddox.JGP.Vector3f vector3f = new Vector3f(1.0F, 0.0F, 0.0F);
        float af[][] = new float[4][3];
        float af1[][] = new float[num][3];
        for(tmpI = 0; tmpI < 4; tmpI++)
        {
            com.maddox.il2.engine.Hook hook = actor.findHook("_Clip0" + tmpI);
            loc.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            hook.computePos(actor, actor.pos.getAbs(), loc);
            loc.get(point3d);
            actor.pos.getAbs().transformInv(point3d);
            af[tmpI][0] = (float)point3d.x;
            af[tmpI][1] = (float)point3d.y;
            af[tmpI][2] = (float)point3d.z;
        }

        for(tmpI = 0; tmpI < num; tmpI++)
        {
            com.maddox.il2.engine.Hook hook1 = actor.findHook("_Engine" + (tmpI + 1) + "Smoke");
            loc.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            hook1.computePos(actor, actor.pos.getAbs(), loc);
            loc.get(point3d);
            actor.pos.getAbs().transformInv(point3d);
            af1[tmpI][0] = (float)point3d.x;
            af1[tmpI][1] = (float)point3d.y;
            af1[tmpI][2] = (float)point3d.z - 0.7F;
        }

        switch(reference.Scheme)
        {
        case 0: // '\0'
            point3d.set(0.0D, 0.0D, 0.0D);
            engines[0].setPos(point3d);
            engines[0].setPropPos(point3d);
            engines[0].setVector(vector3f);
            break;

        case 1: // '\001'
            point3d.x = 0.25F * (af[0][0] + af[1][0] + af[2][0] + af[3][0]);
            point3d.y = 0.0D;
            point3d.z = 0.25F * (af[0][2] + af[1][2] + af[2][2] + af[3][2]);
            engines[0].setPropPos(point3d);
            point3d.x = af1[0][0];
            point3d.y = 0.0D;
            point3d.z = af1[0][2];
            engines[0].setPos(point3d);
            engines[0].setVector(vector3f);
            break;

        case 2: // '\002'
        case 3: // '\003'
            point3d.x = 0.25F * (af[0][0] + af[1][0] + af[2][0] + af[3][0]);
            point3d.y = 0.5F * (af[0][1] + af[1][1]);
            point3d.z = 0.25F * (af[0][2] + af[1][2] + af[2][2] + af[3][2]);
            engines[0].setPropPos(point3d);
            point3d.y = 0.5F * (af[2][1] + af[3][1]);
            engines[1].setPropPos(point3d);
            point3d.x = 0.5F * (af1[0][0] + af1[1][0]);
            point3d.y = af1[0][1];
            point3d.z = 0.5F * (af1[0][2] + af1[1][2]);
            engines[0].setPos(point3d);
            point3d.y = af1[1][1];
            engines[1].setPos(point3d);
            engines[0].setVector(vector3f);
            engines[1].setVector(vector3f);
            break;

        case 4: // '\004'
            point3d.x = 0.25F * (af[0][0] + af[1][0] + af[2][0] + af[3][0]);
            point3d.y = af[0][1];
            point3d.z = 0.25F * (af[0][2] + af[1][2] + af[2][2] + af[3][2]);
            engines[0].setPropPos(point3d);
            point3d.y = af[1][1];
            engines[1].setPropPos(point3d);
            point3d.y = af[2][1];
            engines[2].setPropPos(point3d);
            point3d.y = af[3][1];
            engines[3].setPropPos(point3d);
            point3d.x = 0.25F * (af1[0][0] + af1[1][0] + af1[2][0] + af1[3][0]);
            point3d.y = af1[0][1];
            point3d.z = 0.25F * (af1[0][2] + af1[1][2] + af1[2][2] + af1[3][2]);
            engines[0].setPos(point3d);
            point3d.y = af1[1][1];
            engines[1].setPos(point3d);
            point3d.y = af1[2][1];
            engines[2].setPos(point3d);
            point3d.y = af1[3][1];
            engines[3].setPos(point3d);
            engines[0].setVector(vector3f);
            engines[1].setVector(vector3f);
            engines[2].setVector(vector3f);
            engines[3].setVector(vector3f);
            break;

        case 5: // '\005'
            point3d.x = 0.25F * (af[0][0] + af[1][0] + af[2][0] + af[3][0]);
            point3d.y = af[0][1];
            point3d.z = 0.25F * (af[0][2] + af[1][2] + af[2][2] + af[3][2]);
            engines[0].setPropPos(point3d);
            point3d.y = af[1][1];
            engines[1].setPropPos(point3d);
            point3d.y = 0.0D;
            engines[2].setPropPos(point3d);
            point3d.y = af[2][1];
            engines[3].setPropPos(point3d);
            point3d.y = af[3][1];
            engines[4].setPropPos(point3d);
            point3d.x = 0.2F * (af1[0][0] + af1[1][0] + af1[2][0] + af1[3][0] + af1[4][0]);
            point3d.y = af1[0][1];
            point3d.z = 0.2F * (af1[0][2] + af1[1][2] + af1[2][2] + af1[3][2] + af1[4][2]);
            engines[0].setPos(point3d);
            point3d.y = af1[1][1];
            engines[1].setPos(point3d);
            point3d.y = af1[2][1];
            engines[2].setPos(point3d);
            point3d.y = af1[3][1];
            engines[3].setPos(point3d);
            point3d.y = af1[4][1];
            engines[4].setPos(point3d);
            engines[0].setVector(vector3f);
            engines[1].setVector(vector3f);
            engines[2].setVector(vector3f);
            engines[3].setVector(vector3f);
            engines[4].setVector(vector3f);
            break;

        case 6: // '\006'
            point3d.x = 0.3333333F * (af[0][0] + af[1][0] + af[2][0]);
            point3d.y = af[0][1];
            point3d.z = 0.3333333F * (af[0][2] + af[1][2] + af[2][2]);
            engines[0].setPropPos(point3d);
            point3d.y = af[1][1];
            engines[1].setPropPos(point3d);
            point3d.y = af[2][1];
            engines[2].setPropPos(point3d);
            point3d.x = 0.3333333F * (af1[0][0] + af1[1][0] + af1[2][0]);
            point3d.y = af1[0][1];
            point3d.z = 0.3333333F * (af1[0][2] + af1[1][2] + af1[2][2]);
            engines[0].setPos(point3d);
            point3d.y = af1[1][1];
            engines[1].setPos(point3d);
            point3d.y = af1[2][1];
            engines[2].setPos(point3d);
            engines[0].setVector(vector3f);
            engines[1].setVector(vector3f);
            engines[2].setVector(vector3f);
            break;

        case 7: // '\007'
            point3d.x = 0.25F * (af[0][0] + af[1][0] + af[2][0] + af[3][0]);
            point3d.y = af[0][1];
            point3d.z = 0.25F * (af[0][2] + af[1][2] + af[2][2] + af[3][2]);
            engines[0].setPropPos(point3d);
            point3d.y = 0.0D;
            engines[1].setPropPos(point3d);
            point3d.y = af[1][1];
            engines[2].setPropPos(point3d);
            point3d.y = af[2][1];
            engines[3].setPropPos(point3d);
            point3d.y = 0.0D;
            engines[4].setPropPos(point3d);
            point3d.y = af[3][1];
            engines[5].setPropPos(point3d);
            point3d.x = 0.1666667F * (af1[0][0] + af1[1][0] + af1[2][0] + af1[3][0] + af1[4][0] + af1[5][0]);
            point3d.y = af1[0][1];
            point3d.z = 0.1666667F * (af1[0][2] + af1[1][2] + af1[2][2] + af1[3][2] + af1[4][2] + af1[5][2]);
            engines[0].setPos(point3d);
            point3d.y = af1[1][1];
            engines[1].setPos(point3d);
            point3d.y = af1[2][1];
            engines[2].setPos(point3d);
            point3d.y = af1[3][1];
            engines[3].setPos(point3d);
            point3d.y = af1[4][1];
            engines[4].setPos(point3d);
            point3d.y = af1[5][1];
            engines[5].setPos(point3d);
            engines[0].setVector(vector3f);
            engines[1].setVector(vector3f);
            engines[2].setVector(vector3f);
            engines[3].setVector(vector3f);
            engines[4].setVector(vector3f);
            engines[5].setVector(vector3f);
            break;

        default:
            throw new RuntimeException("UNIDENTIFIED ENGINE DISTRIBUTION.");
        }
    }

    public void update(float f)
    {
        producedF.set(0.0D, 0.0D, 0.0D);
        producedM.set(0.0D, 0.0D, 0.0D);
        for(int i = 0; i < num; i++)
        {
            engines[i].update(f);
            producedF.x += engines[i].getEngineForce().x;
            producedF.y += engines[i].getEngineForce().y;
            producedF.z += engines[i].getEngineForce().z;
            producedM.x += engines[i].getEngineTorque().x;
            producedM.y += engines[i].getEngineTorque().y;
            producedM.z += engines[i].getEngineTorque().z;
        }

    }

    public void netupdate(float f, boolean flag)
    {
        for(int i = 0; i < num; i++)
            engines[i].netupdate(f, flag);

    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int i)
    {
        num = i;
    }

    public void toggle()
    {
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                engines[tmpI].toggle();

    }

    public void setCurControl(int i, boolean flag)
    {
        bCurControl[i] = flag;
    }

    public void setCurControlAll(boolean flag)
    {
        for(tmpI = 0; tmpI < num; tmpI++)
            bCurControl[tmpI] = flag;

    }

    public boolean getCurControl(int i)
    {
        return bCurControl[i];
    }

    public com.maddox.il2.fm.Motor getFirstSelected()
    {
        for(int i = 0; i < num; i++)
            if(bCurControl[i])
                return engines[i];

        return null;
    }

    public int getNumSelected()
    {
        int i = 0;
        for(int j = 0; j < num; j++)
            if(bCurControl[j])
                i++;

        return i;
    }

    public float getPropDirSign()
    {
        float f = 0.0F;
        for(int i = 0; i < getNum(); i++)
            if(engines[i].getPropDir() == 0)
                f++;
            else
                f--;

        return f / (float)getNum();
    }

    public float getRadiatorPos()
    {
        float f = 0.0F;
        for(int i = 0; i < getNum(); i++)
            f += engines[i].getControlRadiator();

        return f / (float)getNum();
    }

    public int[] getSublist(int i, int j)
    {
        int ai[] = null;
        if(j == 1)
            switch(i)
            {
            case 2: // '\002'
            case 3: // '\003'
                ai = (new int[] {
                    0
                });
                break;

            case 6: // '\006'
                ai = (new int[] {
                    0
                });
                break;

            case 4: // '\004'
                ai = (new int[] {
                    0, 1
                });
                break;

            case 5: // '\005'
                ai = (new int[] {
                    0, 1
                });
                break;

            case 7: // '\007'
                ai = (new int[] {
                    0, 1, 2
                });
                break;
            }
        else
        if(j == 2)
            switch(i)
            {
            case 2: // '\002'
            case 3: // '\003'
                ai = (new int[] {
                    1
                });
                break;

            case 6: // '\006'
                ai = (new int[] {
                    2
                });
                break;

            case 4: // '\004'
                ai = (new int[] {
                    2, 3
                });
                break;

            case 5: // '\005'
                ai = (new int[] {
                    3, 4
                });
                break;

            case 7: // '\007'
                ai = (new int[] {
                    3, 4, 5
                });
                break;
            }
        return ai;
    }

    public boolean isSelectionHasControlThrottle()
    {
        boolean flag = false;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                flag |= engines[tmpI].isHasControlThrottle();

        return flag;
    }

    public boolean isSelectionHasControlProp()
    {
        boolean flag = false;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                flag |= engines[tmpI].isHasControlProp();

        return flag;
    }

    public boolean isSelectionAllowsAutoProp()
    {
        com.maddox.il2.ai.World.cur();
        if(reference != com.maddox.il2.ai.World.getPlayerFM())
            return true;
        boolean flag = false;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                flag |= engines[tmpI].isAllowsAutoProp();

        return flag;
    }

    public boolean isSelectionHasControlMix()
    {
        boolean flag = false;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                flag |= engines[tmpI].isHasControlMix();

        return flag;
    }

    public boolean isSelectionHasControlMagnetos()
    {
        boolean flag = false;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                flag |= engines[tmpI].isHasControlMagnetos();

        return flag;
    }

    public boolean isSelectionHasControlCompressor()
    {
        boolean flag = false;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                flag |= engines[tmpI].isHasControlCompressor();

        return flag;
    }

    public boolean isSelectionHasControlFeather()
    {
        boolean flag = false;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                flag |= engines[tmpI].isHasControlFeather();

        return flag;
    }

    public boolean isSelectionHasControlExtinguisher()
    {
        boolean flag = false;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                flag |= engines[tmpI].getExtinguishers() > 0;

        return flag;
    }

    public boolean isSelectionHasControlAfterburner()
    {
        boolean flag = false;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                flag |= engines[tmpI].isHasControlAfterburner();

        return flag;
    }

    public boolean isSelectionAllowsAutoRadiator()
    {
        com.maddox.il2.ai.World.cur();
        if(reference != com.maddox.il2.ai.World.getPlayerFM())
            return true;
        boolean flag = false;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                flag |= engines[tmpI].isAllowsAutoRadiator();

        return flag;
    }

    public boolean isSelectionHasControlRadiator()
    {
        boolean flag = false;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                flag |= engines[tmpI].isHasControlRadiator();

        return flag;
    }

    public float getPowerOutput()
    {
        float f = 0.0F;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            f += engines[tmpI].getPowerOutput();

        return f / (float)getNum();
    }

    public float getThrustOutput()
    {
        float f = 0.0F;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            f += engines[tmpI].getThrustOutput();

        return f / (float)getNum();
    }

    public float getReadyness()
    {
        float f = 0.0F;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            f += engines[tmpI].getReadyness();

        return f / (float)getNum();
    }

    public float getBoostFactor()
    {
        float f = 0.0F;
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            f += engines[tmpI].getBoostFactor();

        return f / (float)getNum();
    }

    public com.maddox.JGP.Vector3d getGyro()
    {
        tmpV3d.set(0.0D, 0.0D, 0.0D);
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            tmpV3d.add(engines[tmpI].getEngineGyro());

        return tmpV3d;
    }

    public void setThrottle(float f)
    {
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                engines[tmpI].setControlThrottle(f);

    }

    public void setAfterburnerControl(boolean flag)
    {
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                engines[tmpI].setControlAfterburner(flag);

    }

    public void setProp(float f)
    {
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                engines[tmpI].setControlProp(f);

    }

    public void setPropAuto(boolean flag)
    {
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                engines[tmpI].setControlPropAuto(flag);

    }

    public void setFeather(int i)
    {
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                engines[tmpI].setControlFeather(i);

    }

    public void setMix(float f)
    {
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                engines[tmpI].setControlMix(f);

    }

    public void setMagnetos(int i)
    {
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                engines[tmpI].setControlMagneto(i);

    }

    public void setCompressorStep(int i)
    {
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                engines[tmpI].setControlCompressor(i);

    }

    public void setRadiator(float f)
    {
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                engines[tmpI].setControlRadiator(f);

    }

    public void updateRadiator(float f)
    {
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            engines[tmpI].updateRadiator(f);

    }

    public void setEngineStops()
    {
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                engines[tmpI].setEngineStops(reference.actor);

    }

    public void setEngineRunning()
    {
        for(tmpI = 0; tmpI < getNum(); tmpI++)
            if(bCurControl[tmpI])
                engines[tmpI].setEngineRunning(reference.actor);

    }

    public float forcePropAOA(float f, float f1, float f2, boolean flag)
    {
        float f3 = 0.0F;
        for(int i = 0; i < getNum(); i++)
            f3 += engines[i].forcePropAOA(f, f1, f2, flag);

        com.maddox.il2.objects.air.Aircraft.debugprintln(reference.actor, "Computed thrust at " + f + " m/s and " + f1 + " m is " + f3 + " N..");
        return f3;
    }

    public com.maddox.il2.fm.Motor engines[];
    public boolean bCurControl[];
    private int num;
    public com.maddox.JGP.Vector3d producedF;
    public com.maddox.JGP.Vector3d producedM;
    private com.maddox.il2.fm.FlightModel reference;
    private static com.maddox.JGP.Vector3d tmpV3d = new Vector3d();
    private static int tmpI;

}
