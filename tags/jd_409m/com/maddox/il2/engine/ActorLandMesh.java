package com.maddox.il2.engine;

public abstract class ActorLandMesh extends ActorMesh
  implements LandPlate
{
  protected float cHQ(double paramDouble1, double paramDouble2)
  {
    return mesh().heightMapMeshGetHeight(paramDouble1, paramDouble2);
  }
  protected boolean cNormal(double paramDouble1, double paramDouble2, float[] paramArrayOfFloat) {
    return mesh().heightMapMeshGetNormal(paramDouble1, paramDouble2, paramArrayOfFloat);
  }
  protected boolean cPlane(double paramDouble1, double paramDouble2, double[] paramArrayOfDouble) {
    return mesh().heightMapMeshGetPlane(paramDouble1, paramDouble2, paramArrayOfDouble);
  }
  protected float cRayHit(double[] paramArrayOfDouble) {
    return mesh().heightMapMeshGetRayHit(paramArrayOfDouble);
  }
  public boolean isStaticPos() {
    return true;
  }
  protected ActorLandMesh() {
  }

  protected ActorLandMesh(Loc paramLoc) {
    super(paramLoc);
  }
  protected ActorLandMesh(ActorPos paramActorPos) {
    super(paramActorPos);
  }
  public ActorLandMesh(String paramString) {
    super(paramString);
  }
  public ActorLandMesh(String paramString, Loc paramLoc) {
    super(paramString, paramLoc);
  }
  public ActorLandMesh(String paramString, ActorPos paramActorPos) {
    super(paramString, paramActorPos);
  }
}