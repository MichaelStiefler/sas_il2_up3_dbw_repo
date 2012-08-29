package com.maddox.il2.engine;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;

public class HookFace extends Hook
{
  private int iFace;
  private int iChunk;
  private Mesh mesh;
  private Matrix4d L2F = new Matrix4d();
  private Matrix4d mRel = new Matrix4d();
  private static final float PI = 3.141593F;
  private static Matrix4d m1 = new Matrix4d();
  private static Matrix4d m2 = new Matrix4d();
  private static Loc l2 = new Loc();
  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static double[] tmp = new double[3];
  private static int[] i = new int[1];

  private static float RAD2DEG(float paramFloat)
  {
    return paramFloat * 57.295776F;
  }

  public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2) {
    this.mesh.hookFaceMatrix(this.iFace, this.iChunk, m1);
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
      localHierMesh.setCurChunk(this.iChunk);
      return localHierMesh.chunkName();
    }
    return super.chunkName();
  }

  public int chunkNum()
  {
    return this.iChunk;
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
      int j = localMesh.hookFaceFind(this.mRel, i, m1);
      if (j == -1)
        throw new ActorException("HookFace not found");
      this.mesh = localMesh;
      this.iChunk = i[0];
      this.iFace = j;
      this.L2F.set(m1);
    }
  }

  public HookFace(ActorMesh paramActorMesh, Loc paramLoc)
  {
    Loc localLoc = paramActorMesh.pos.getAbs();
    l2.sub(paramLoc, localLoc);
    l2.getMatrix(this.mRel);
    this.mesh = paramActorMesh.mesh();
    this.iFace = this.mesh.hookFaceFind(this.mRel, i, this.L2F);
    if (this.iFace == -1)
      throw new ActorException("HookFace not found");
    this.iChunk = i[0];
  }

  public HookFace(ActorMesh paramActorMesh, Loc paramLoc, boolean paramBoolean)
  {
    Loc localLoc = paramActorMesh.pos.getAbs();
    l2.sub(paramLoc, localLoc);
    l2.getMatrix(this.mRel);
    this.mesh = paramActorMesh.mesh();
    if (paramBoolean) {
      this.iFace = this.mesh.hookFaceFind(this.mRel, this.L2F);
      i[0] = this.mesh.getCurChunk();
    }
    else {
      this.iFace = this.mesh.hookFaceFind(this.mRel, i, this.L2F);
    }
    if (this.iFace == -1)
      throw new ActorException("HookFace not found");
    this.iChunk = i[0];
  }
}