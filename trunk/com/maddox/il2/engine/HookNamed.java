package com.maddox.il2.engine;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;

public class HookNamed extends Hook
{
  private int iHook;
  private Mesh mesh;
  private static final float PI = 3.141593F;
  private static Matrix4d m1 = new Matrix4d();
  private static Matrix4d m2 = new Matrix4d();
  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static double[] tmp = new double[3];
  private String name;
  private int chunkNum;

  private static float RAD2DEG(float paramFloat)
  {
    return paramFloat * 57.295776F;
  }

  public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2) {
    this.mesh.hookMatrix(this.iHook, m1);
    paramLoc1.getMatrix(m2);
    m2.mul(m1);
    paramLoc2.getMatrix(m1);
    m2.mul(m1);
    m2.getEulers(tmp);
    o.setYPR(RAD2DEG((float)tmp[0]), 360.0F - RAD2DEG((float)tmp[1]), 360.0F - RAD2DEG((float)tmp[2]));
    p.set(m2.m03, m2.m13, m2.m23);
    paramLoc2.set(p, o);
  }

  public String chunkName()
  {
    if ((this.mesh instanceof HierMesh)) {
      HierMesh localHierMesh = (HierMesh)this.mesh;
      int i = ((HierMesh)this.mesh).chunkByHookNamed(this.iHook);
      localHierMesh.setCurChunk(i);
      return localHierMesh.chunkName();
    }
    return super.chunkName();
  }

  public void baseChanged(Actor paramActor)
  {
    Mesh localMesh = ((ActorMesh)paramActor).mesh();
    if (localMesh == this.mesh)
      return;
    if (localMesh == null)
      throw new ActorException("HookFace not found");
    if (Mesh.isSimilar(localMesh, this.mesh)) {
      this.mesh = localMesh;
    } else {
      int i = localMesh.hookFind(this.name);
      if (i == -1)
        throw new ActorException("Hook: " + this.name + " not found");
      this.mesh = localMesh;
      this.iHook = i;
      getChunkNum();
    }
  }

  public String name() {
    return this.name;
  }
  public int chunkNum() {
    return this.chunkNum;
  }

  private void getChunkNum() {
    if ((this.mesh instanceof HierMesh)) {
      HierMesh localHierMesh = (HierMesh)this.mesh;
      this.chunkNum = localHierMesh.chunkByHookNamed(this.iHook);
    } else {
      this.chunkNum = -1;
    }
  }

  public HookNamed(ActorMesh paramActorMesh, String paramString) {
    this.mesh = paramActorMesh.mesh();
    this.iHook = this.mesh.hookFind(paramString);
    if (this.iHook == -1)
      throw new ActorException("Hook: " + paramString + " not found");
    this.name = paramString;
    getChunkNum();
  }
  public HookNamed(Mesh paramMesh, String paramString) {
    this.mesh = paramMesh;
    this.iHook = paramMesh.hookFind(paramString);
    if (this.iHook == -1)
      throw new ActorException("Hook: " + paramString + " not found");
    this.name = paramString;
    getChunkNum();
  }
}