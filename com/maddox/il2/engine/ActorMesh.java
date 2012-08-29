package com.maddox.il2.engine;

import com.maddox.JGP.Vector3f;

public abstract class ActorMesh extends Actor
{
  protected Mesh mesh = null;

  private static Loc L1 = new Loc();

  public Mesh mesh()
  {
    return this.mesh;
  }
  public Hook findHook(Object paramObject) {
    if ((paramObject instanceof String))
      return new HookNamed(this, (String)paramObject);
    if ((paramObject instanceof Loc))
      return new HookFace(this, (Loc)paramObject);
    throw new ActorException("Unknown type of hook ident");
  }

  public void getDimensions(Vector3f paramVector3f) {
    this.mesh.getDimensions(paramVector3f);
  }

  public float collisionR() {
    return this.mesh.collisionR();
  }

  public boolean isStaticPos()
  {
    return false;
  }
  public void visibilityAsBase(boolean paramBoolean) {
    if (((this.flags & 0x2) != 0) == paramBoolean) return;
    super.visibilityAsBase(paramBoolean);
    if ((this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos != null) && (this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.actor() != this) && (this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base() != null))
      if (paramBoolean) this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.addChildren(this); else
        this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.removeChildren(this);
  }

  public void destroy()
  {
    if (isDestroyed()) return;
    super.destroy();
    if (this.mesh != null) {
      this.mesh.destroy();
      this.mesh = null;
    }
  }

  protected ActorMesh() {
    if (isStaticPos()) this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosStatic(this); else
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this);
    this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw = new ActorMeshDraw();
  }
  protected ActorMesh(Loc paramLoc) {
    if (isStaticPos()) this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosStatic(this, paramLoc); else
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this, paramLoc);
    this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw = new ActorMeshDraw();
  }
  protected ActorMesh(ActorPos paramActorPos) {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = paramActorPos;
    this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw = new ActorMeshDraw();
  }
  public void setMesh(String paramString) {
    int i = (mesh() != null) && (this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos != null) ? 1 : 0;
    this.mesh = new Mesh(paramString);
    if (i != 0)
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.actorChanged(); 
  }

  public void setMesh(Mesh paramMesh) {
    int i = (mesh() != null) && (this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos != null) ? 1 : 0;
    this.mesh = paramMesh;
    if (i != 0)
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.actorChanged();
  }

  public ActorMesh(String paramString) {
    this();
    try {
      this.mesh = new Mesh(paramString);
    } catch (RuntimeException localRuntimeException) {
      super.destroy();
      throw localRuntimeException;
    }
  }

  public ActorMesh(String paramString, Loc paramLoc) {
    this(paramLoc);
    try {
      this.mesh = new Mesh(paramString);
    } catch (RuntimeException localRuntimeException) {
      super.destroy();
      throw localRuntimeException;
    }
  }

  public ActorMesh(String paramString, ActorPos paramActorPos) {
    this(paramActorPos);
    try {
      this.mesh = new Mesh(paramString);
    } catch (RuntimeException localRuntimeException) {
      super.destroy();
      throw localRuntimeException;
    }
  }

  public ActorMesh(ActorHMesh paramActorHMesh, int paramInt)
  {
    paramActorHMesh.hierMesh().setCurChunk(paramInt);
    paramActorHMesh.getChunkLocTimeAbs(L1);
    if (isStaticPos()) this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosStatic(this, L1); else
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this, L1);
    this.mesh = new Mesh(paramActorHMesh.hierMesh());
    this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw = new ActorMeshDraw();
  }
}