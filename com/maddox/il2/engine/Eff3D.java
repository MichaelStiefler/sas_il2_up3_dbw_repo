package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

public class Eff3D extends ActorDraw
{
  protected int cppObj = 0;

  private static float[] tmpFArray = new float[3];
  private static double[] tmpDArray = new double[3];
  private static Loc tmpL = new Loc();
  private static Point3d tmpP = new Point3d();
  private static Orient tmpO = new Orient();

  protected Eff3D()
  {
  }

  public Eff3D(int paramInt)
  {
    this.cppObj = paramInt;
  }
  protected void finalize() {
    if (this.cppObj != 0) GObj.Finalize(this.cppObj); 
  }
  protected Eff3DActor NewActor(Loc paramLoc) {
    return null; } 
  protected Eff3DActor NewActor(ActorPos paramActorPos) { return null; } 
  protected static native Eff3D New();

  public int preRender(Actor paramActor) { paramActor.pos.getRender(tmpL);
    lightUpdate(tmpL, true);
    soundUpdate(paramActor, tmpL);
    setPos(tmpL);

    return PreRender(this.cppObj);
  }

  public void render(Actor paramActor)
  {
    Render(this.cppObj);
  }

  protected void setPos(Point3d paramPoint3d)
  {
    paramPoint3d.get(tmpDArray);
    tmpFArray[0] = (float)tmpDArray[0];
    tmpFArray[1] = (float)tmpDArray[1];
    tmpFArray[2] = (float)tmpDArray[2];
    SetPos(this.cppObj, tmpFArray);
  }

  protected void setPos(Point3d paramPoint3d, Orient paramOrient)
  {
    SetXYZATK(this.cppObj, (float)paramPoint3d.jdField_x_of_type_Double, (float)paramPoint3d.jdField_y_of_type_Double, (float)paramPoint3d.jdField_z_of_type_Double, paramOrient.azimut(), paramOrient.tangage(), paramOrient.kren());
  }

  protected void setPos(Loc paramLoc)
  {
    SetXYZATK(this.cppObj, (float)paramLoc.P.jdField_x_of_type_Double, (float)paramLoc.P.jdField_y_of_type_Double, (float)paramLoc.P.jdField_z_of_type_Double, paramLoc.O.azimut(), paramLoc.O.tangage(), paramLoc.O.kren());
  }

  protected void setOrientation(Orient paramOrient) {
    paramOrient.get(tmpFArray);
    SetAnglesATK(this.cppObj, tmpFArray);
  }
  protected void pause(boolean paramBoolean) {
    Pause(this.cppObj, paramBoolean); } 
  protected boolean isPaused() { return IsPaused(this.cppObj); }

  protected void setIntesity(float paramFloat) {
    SetIntesity(this.cppObj, paramFloat);
  }
  protected float getIntesity() {
    return GetIntesity(this.cppObj);
  }

  protected void finish()
  {
    Finish(this.cppObj);
  }

  public boolean isDestroyed() {
    return this.cppObj == 0;
  }

  public void destroy() {
    super.destroy();
    if (this.cppObj != 0) { GObj.Finalize(this.cppObj); this.cppObj = 0; } 
  }

  protected float timeLife() {
    return TimeLife(this.cppObj); } 
  protected float timeFinish() { return TimeFinish(this.cppObj); } 
  protected boolean isTimeReal() { return IsTimeReal(this.cppObj); } 
  private native void SetPos(int paramInt, float[] paramArrayOfFloat);

  private native void SetAnglesATK(int paramInt, float[] paramArrayOfFloat);

  private native void SetXYZATK(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  private native int PreRender(int paramInt);

  private native void Render(int paramInt);

  private native void Pause(int paramInt, boolean paramBoolean);

  private native boolean IsPaused(int paramInt);

  private native void SetIntesity(int paramInt, float paramFloat);

  private native float GetIntesity(int paramInt);

  private native void Finish(int paramInt);

  private native float TimeLife(int paramInt);

  private native float TimeFinish(int paramInt);

  private native boolean IsTimeReal(int paramInt);

  protected static void spawnSetCommonFields(ActorSpawnArg paramActorSpawnArg, Loc paramLoc) { paramLoc.get(tmpP, tmpO);
    initSetPos((float)tmpP.jdField_x_of_type_Double, (float)tmpP.jdField_y_of_type_Double, (float)tmpP.jdField_z_of_type_Double);
    tmpO.get(tmpFArray);
    initSetAnglesATK(tmpFArray[0], tmpFArray[1], tmpFArray[2]);

    if (paramActorSpawnArg.sizeExist) {
      initSetSize(paramActorSpawnArg.size);
    }
    if (paramActorSpawnArg.matName != null) {
      initSetMaterialName(Pre.load(paramActorSpawnArg.matName));
    }
    if (paramActorSpawnArg.paramFileName != null) {
      initSetParamFileName(Pre.load(paramActorSpawnArg.paramFileName));
    }
    if (paramActorSpawnArg.timeLenExist) {
      initSetProcessTime(paramActorSpawnArg.timeLen);
    }
    if (paramActorSpawnArg.typeExist) {
      initSetSubType(paramActorSpawnArg.type);
    }
    if (paramActorSpawnArg.timeNativeExist)
      initSetTypeTimer(paramActorSpawnArg.timeNative); }

  protected static void initSetLocator(Loc paramLoc)
  {
    paramLoc.get(tmpP, tmpO);
    initSetPos((float)tmpP.jdField_x_of_type_Double, (float)tmpP.jdField_y_of_type_Double, (float)tmpP.jdField_z_of_type_Double);
    tmpO.get(tmpFArray);
    initSetAnglesATK(tmpFArray[0], tmpFArray[1], tmpFArray[2]); } 
  protected static native void initSetProcessTime(float paramFloat);

  public static native void initSetTypeTimer(boolean paramBoolean);

  public static native void initSetBoundBox(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  protected static native void initSetPos(float paramFloat1, float paramFloat2, float paramFloat3);

  protected static native void initSetSize(float paramFloat);

  protected static native void initSetAnglesATK(float paramFloat1, float paramFloat2, float paramFloat3);

  protected static native void initSetSubType(int paramInt);

  protected static native void initSetMaterialName(String paramString);

  protected static native void initSetParamFileName(String paramString);

  static { GObj.loadNative();
  }
}