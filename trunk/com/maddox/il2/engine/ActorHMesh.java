package com.maddox.il2.engine;

import com.maddox.rts.Time;

public abstract class ActorHMesh extends ActorMesh
{
  private HierMesh hmesh = null;

  private static Loc _L = new Loc();

  public HierMesh hierMesh()
  {
    return this.hmesh; } 
  public Mesh mesh() { return this.hmesh == null ? super.mesh() : this.hmesh; }

  public float collisionR() {
    return mesh().collisionR();
  }
  public int[] hideSubTrees(String paramString) {
    return this.hmesh.hideSubTrees(paramString);
  }

  public void destroyChildFiltered(Class paramClass)
  {
    Object[] arrayOfObject = getOwnerAttached();
    for (int i = 0; i < arrayOfObject.length; i++)
      if ((arrayOfObject[i] != null) && (paramClass.isInstance(arrayOfObject[i])))
        ((Actor)arrayOfObject[i]).destroy();
  }

  public void getChunkLoc(Loc paramLoc)
  {
    this.hmesh.getChunkLocObj(paramLoc);
  }

  public void getChunkLocAbs(Loc paramLoc)
  {
    this.hmesh.getChunkLocObj(paramLoc);
    paramLoc.add(this.pos.getAbs());
  }

  public void getChunkLocTimeAbs(Loc paramLoc)
  {
    this.hmesh.getChunkLocObj(paramLoc);
    this.pos.getTime(Time.current(), _L);
    paramLoc.add(_L);
  }

  public float getChunkMass()
  {
    return this.hmesh.getChunkMass();
  }

  public void destroy() {
    if (isDestroyed()) return;
    super.destroy();
    if (this.hmesh != null) {
      this.hmesh.destroy();
      this.hmesh = null;
    }
  }

  protected ActorHMesh() {
  }

  protected ActorHMesh(Loc paramLoc) {
    super(paramLoc);
  }
  protected ActorHMesh(ActorPos paramActorPos) {
    super(paramActorPos);
  }
  public void setMesh(String paramString) {
    int i = (mesh() != null) && (this.pos != null) ? 1 : 0;
    if (paramString.endsWith(".sim")) {
      this.hmesh = null;
      super.setMesh(paramString);
    } else {
      this.mesh = null;
      this.hmesh = new HierMesh(paramString);
    }
    if (i != 0)
      this.pos.actorChanged(); 
  }

  public void setMesh(Mesh paramMesh) {
    int i = (mesh() != null) && (this.pos != null) ? 1 : 0;
    this.mesh = paramMesh;
    this.hmesh = null;
    if (i != 0)
      this.pos.actorChanged(); 
  }

  protected void setMesh(HierMesh paramHierMesh) {
    int i = (mesh() != null) && (this.pos != null) ? 1 : 0;
    this.hmesh = paramHierMesh;
    this.mesh = null;
    if (i != 0)
      this.pos.actorChanged();
  }

  public ActorHMesh(String paramString) {
    try {
      setMesh(paramString);
    } catch (RuntimeException localRuntimeException) {
      super.destroy();
      throw localRuntimeException;
    }
  }

  public ActorHMesh(String paramString, Loc paramLoc) {
    super(paramLoc);
    try {
      setMesh(paramString);
    } catch (RuntimeException localRuntimeException) {
      super.destroy();
      throw localRuntimeException;
    }
  }

  public ActorHMesh(String paramString, ActorPos paramActorPos) {
    super(paramActorPos);
    try {
      setMesh(paramString);
    } catch (RuntimeException localRuntimeException) {
      super.destroy();
      throw localRuntimeException;
    }
  }

  public ActorHMesh(HierMesh paramHierMesh, Loc paramLoc) {
    super(paramLoc);
    try {
      setMesh(paramHierMesh);
    } catch (RuntimeException localRuntimeException) {
      super.destroy();
      throw localRuntimeException;
    }
  }
}