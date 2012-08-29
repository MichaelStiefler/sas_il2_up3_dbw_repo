// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Eff3D.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

// Referenced classes of package com.maddox.il2.engine:
//            ActorDraw, Loc, Orient, GObj, 
//            Actor, ActorPos, ActorSpawnArg, Pre, 
//            Eff3DActor

public class Eff3D extends com.maddox.il2.engine.ActorDraw
{

    protected Eff3D()
    {
        cppObj = 0;
    }

    public Eff3D(int i)
    {
        cppObj = 0;
        cppObj = i;
    }

    protected void finalize()
    {
        if(cppObj != 0)
            com.maddox.il2.engine.GObj.Finalize(cppObj);
    }

    protected com.maddox.il2.engine.Eff3DActor NewActor(com.maddox.il2.engine.Loc loc)
    {
        return null;
    }

    protected com.maddox.il2.engine.Eff3DActor NewActor(com.maddox.il2.engine.ActorPos actorpos)
    {
        return null;
    }

    protected static native com.maddox.il2.engine.Eff3D New();

    public int preRender(com.maddox.il2.engine.Actor actor)
    {
        actor.pos.getRender(tmpL);
        lightUpdate(tmpL, true);
        soundUpdate(actor, tmpL);
        setPos(tmpL);
        return PreRender(cppObj);
    }

    public void render(com.maddox.il2.engine.Actor actor)
    {
        Render(cppObj);
    }

    protected void setPos(com.maddox.JGP.Point3d point3d)
    {
        point3d.get(tmpDArray);
        tmpFArray[0] = (float)tmpDArray[0];
        tmpFArray[1] = (float)tmpDArray[1];
        tmpFArray[2] = (float)tmpDArray[2];
        SetPos(cppObj, tmpFArray);
    }

    protected void setPos(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        SetXYZATK(cppObj, (float)point3d.x, (float)point3d.y, (float)point3d.z, orient.azimut(), orient.tangage(), orient.kren());
    }

    protected void setPos(com.maddox.il2.engine.Loc loc)
    {
        SetXYZATK(cppObj, (float)loc.P.x, (float)loc.P.y, (float)loc.P.z, loc.O.azimut(), loc.O.tangage(), loc.O.kren());
    }

    protected void setOrientation(com.maddox.il2.engine.Orient orient)
    {
        orient.get(tmpFArray);
        SetAnglesATK(cppObj, tmpFArray);
    }

    protected void pause(boolean flag)
    {
        Pause(cppObj, flag);
    }

    protected boolean isPaused()
    {
        return IsPaused(cppObj);
    }

    protected void setIntesity(float f)
    {
        SetIntesity(cppObj, f);
    }

    protected float getIntesity()
    {
        return GetIntesity(cppObj);
    }

    protected void finish()
    {
        Finish(cppObj);
    }

    public boolean isDestroyed()
    {
        return cppObj == 0;
    }

    public void destroy()
    {
        super.destroy();
        if(cppObj != 0)
        {
            com.maddox.il2.engine.GObj.Finalize(cppObj);
            cppObj = 0;
        }
    }

    protected float timeLife()
    {
        return TimeLife(cppObj);
    }

    protected float timeFinish()
    {
        return TimeFinish(cppObj);
    }

    protected boolean isTimeReal()
    {
        return IsTimeReal(cppObj);
    }

    private native void SetPos(int i, float af[]);

    private native void SetAnglesATK(int i, float af[]);

    private native void SetXYZATK(int i, float f, float f1, float f2, float f3, float f4, float f5);

    private native int PreRender(int i);

    private native void Render(int i);

    private native void Pause(int i, boolean flag);

    private native boolean IsPaused(int i);

    private native void SetIntesity(int i, float f);

    private native float GetIntesity(int i);

    private native void Finish(int i);

    private native float TimeLife(int i);

    private native float TimeFinish(int i);

    private native boolean IsTimeReal(int i);

    protected static void spawnSetCommonFields(com.maddox.il2.engine.ActorSpawnArg actorspawnarg, com.maddox.il2.engine.Loc loc)
    {
        loc.get(tmpP, tmpO);
        com.maddox.il2.engine.Eff3D.initSetPos((float)tmpP.x, (float)tmpP.y, (float)tmpP.z);
        tmpO.get(tmpFArray);
        com.maddox.il2.engine.Eff3D.initSetAnglesATK(tmpFArray[0], tmpFArray[1], tmpFArray[2]);
        if(actorspawnarg.sizeExist)
            com.maddox.il2.engine.Eff3D.initSetSize(actorspawnarg.size);
        if(actorspawnarg.matName != null)
            com.maddox.il2.engine.Eff3D.initSetMaterialName(com.maddox.il2.engine.Pre.load(actorspawnarg.matName));
        if(actorspawnarg.paramFileName != null)
            com.maddox.il2.engine.Eff3D.initSetParamFileName(com.maddox.il2.engine.Pre.load(actorspawnarg.paramFileName));
        if(actorspawnarg.timeLenExist)
            com.maddox.il2.engine.Eff3D.initSetProcessTime(actorspawnarg.timeLen);
        if(actorspawnarg.typeExist)
            com.maddox.il2.engine.Eff3D.initSetSubType(actorspawnarg.type);
        if(actorspawnarg.timeNativeExist)
            com.maddox.il2.engine.Eff3D.initSetTypeTimer(actorspawnarg.timeNative);
    }

    protected static void initSetLocator(com.maddox.il2.engine.Loc loc)
    {
        loc.get(tmpP, tmpO);
        com.maddox.il2.engine.Eff3D.initSetPos((float)tmpP.x, (float)tmpP.y, (float)tmpP.z);
        tmpO.get(tmpFArray);
        com.maddox.il2.engine.Eff3D.initSetAnglesATK(tmpFArray[0], tmpFArray[1], tmpFArray[2]);
    }

    protected static native void initSetProcessTime(float f);

    public static native void initSetTypeTimer(boolean flag);

    public static native void initSetBoundBox(float f, float f1, float f2, float f3, float f4, float f5);

    protected static native void initSetPos(float f, float f1, float f2);

    protected static native void initSetSize(float f);

    protected static native void initSetAnglesATK(float f, float f1, float f2);

    protected static native void initSetSubType(int i);

    protected static native void initSetMaterialName(java.lang.String s);

    protected static native void initSetParamFileName(java.lang.String s);

    protected int cppObj;
    private static float tmpFArray[] = new float[3];
    private static double tmpDArray[] = new double[3];
    private static com.maddox.il2.engine.Loc tmpL = new Loc();
    private static com.maddox.JGP.Point3d tmpP = new Point3d();
    private static com.maddox.il2.engine.Orient tmpO = new Orient();

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}
