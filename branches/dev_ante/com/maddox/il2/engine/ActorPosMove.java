package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.rts.Time;
import java.util.ArrayList;
import java.util.List;

public class ActorPosMove extends ActorPos
{
  private static final boolean DEBUG_REFERENCE = false;
  private static final int SIZE_REFERENCES = 256;
  private static int curReference = 0;
  private static Loc[] bufReference = new Loc[256];

  private Actor actor = null;

  protected Actor base = null;

  protected Hook baseHook = null;

  private List baseAttached = null;

  protected Loc L = new Loc();
  protected Loc Labs = new Loc();
  public static final int ABS_VALID = 1;
  public static final int ABS_CHANGED = 2;
  public static final int REL_CHANGED = 4;
  public static final int UPDATE_ENV = 8;
  public static final int UPDATE_DISABLED = 16;
  protected int flg = 0;

  protected int curTick = 0;

  protected Loc curLabs = new Loc();

  protected Loc prevLabs = new Loc();

  protected Loc renderLabs = new Loc();
  protected int renderTick = -1;

  protected static Point3d tmpBaseP = new Point3d();
  protected static Orient tmpBaseO = new Orient();

  private static Loc tmpAbsL = new Loc();
  private static Point3d tmpAbsP = new Point3d();
  private static Orient tmpAbsO = new Orient();

  private Loc nextReference()
  {
    curReference = (curReference + 1) % 256;
    return bufReference[curReference];
  }

  public void setUpdateEnable(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.flg &= -17;
      if ((this.flg & 0x2) != 0) Engine.cur.posChanged.add(this.actor); 
    }
    else {
      this.flg |= 16;
    }
  }

  public boolean isUpdateEnable() {
    return (this.flg & 0x10) == 0;
  }

  public void inValidate(boolean paramBoolean)
  {
    int j;
    int i;
    Actor localActor;
    if (paramBoolean) {
      if ((this.flg & 0x2) == 0) {
        if ((this.flg & 0x10) == 0) Engine.cur.posChanged.add(this.actor);
        this.flg |= 2;
      }
      this.flg &= -2;
      if (this.baseAttached != null) {
        j = this.baseAttached.size();
        for (i = 0; i < j; i++) {
          localActor = (Actor)this.baseAttached.get(i);
          if (localActor == localActor.pos.actor())
            localActor.pos.inValidate(true);
        }
      }
    }
    else if (this.baseAttached != null) {
      j = this.baseAttached.size();
      for (i = 0; i < j; i++) {
        localActor = (Actor)this.baseAttached.get(i);
        if ((localActor == localActor.pos.actor()) && (localActor.pos.baseHook() != null))
          localActor.pos.inValidate(true);
      }
    }
  }

  private void inValidateValid(boolean paramBoolean)
  {
    if ((this.flg & 0x2) == 0) {
      if ((this.flg & 0x10) == 0) Engine.cur.posChanged.add(this.actor);
      this.flg |= 2;
    }
    if ((paramBoolean) && (this.baseAttached != null)) {
      int j = this.baseAttached.size();
      for (int i = 0; i < j; i++) {
        Actor localActor = (Actor)this.baseAttached.get(i);
        if (localActor == localActor.pos.actor())
          localActor.pos.inValidate(true);
      }
    }
  }

  protected void validate()
  {
    if (this.base == null) {
      this.Labs.set(this.L);
    }
    else if (this.baseHook != null) {
      this.Labs.set(this.L);
      this.baseHook.computePos(this.base, this.base.pos.getAbs(), this.Labs);
    } else {
      this.Labs.add(this.L, this.base.pos.getAbs());
    }

    if (!InterpolateAdapter.isProcess())
      this.flg |= 1;
  }

  protected void validatePrev() {
    int i = Time.tickCounter();
    if (i == this.curTick) return;
    this.curTick = i;
    this.prevLabs.set(this.curLabs);
  }

  protected void validateRender() {
    if (this.renderTick == RendersMain.frame())
      return;
    if ((this.base != null) && (this.baseHook != null) && ((this.baseHook instanceof HookRender)) && ((this.flg & 0x4) == 0))
    {
      if ((this.flg & 0x1) == 0) validate();
      this.renderLabs.set(this.L);
      if (!((HookRender)this.baseHook).computeRenderPos(this.base, this.base.pos.getRender(), this.renderLabs))
        this.renderLabs.interpolate(this.curLabs, this.Labs, Time.tickOffset());
    }
    else if (Time.isPaused()) {
      if ((this.flg & 0x1) == 0) validate();
      this.renderLabs.set(this.Labs);
    }
    else if ((this.flg & 0x2) != 0) {
      if ((this.base != null) && (this.baseHook == null) && ((this.flg & 0x4) == 0)) {
        this.renderLabs.add(this.L, this.base.pos.getRender());
      } else {
        if ((this.flg & 0x1) == 0) validate();
        this.renderLabs.interpolate(this.curLabs, this.Labs, Time.tickOffset());
      }
    } else {
      this.renderLabs.set(this.curLabs);
    }

    if ((this.flg & 0x1) != 0)
      this.renderTick = RendersMain.frame();
  }

  public Actor actor() {
    return this.actor;
  }

  public Actor homeBase() {
    Object localObject = this.base;
    if (localObject == null)
      return null;
    while (true) {
      Actor localActor = ((Actor)localObject).pos.base();
      if (localActor == null)
        break;
      localObject = localActor;
    }
    return (Actor)localObject;
  }

  public Actor base() {
    return this.base;
  }
  public Object baseHook() {
    return this.baseHook;
  }

  public Object[] getBaseAttached() {
    if (this.baseAttached != null)
      return this.baseAttached.toArray();
    return null;
  }

  public Object[] getBaseAttached(Object[] paramArrayOfObject)
  {
    if (this.baseAttached != null)
      return this.baseAttached.toArray(paramArrayOfObject);
    return null;
  }

  protected List getListBaseAttached() {
    return this.baseAttached;
  }

  protected void setBase(Actor paramActor, Hook paramHook, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if ((paramActor != this.base) || (paramHook != this.baseHook)) {
      if ((paramActor != null) && (!Actor.isValid(paramActor))) {
        throw new ActorException("new base is destroyed");
      }
      Actor localActor = this.base;
      Hook localHook = this.baseHook;
      if ((paramBoolean1) && 
        ((this.flg & 0x1) == 0)) validate();
      if ((localActor != paramActor) && (Actor.isValid(localActor))) {
        localActor.pos.removeChildren(this.actor);
        MsgBase.detach(localActor, this.actor);
      }

      this.base = paramActor;
      this.baseHook = paramHook;

      if (paramBoolean1) {
        this.Labs.get(tmpBaseP, tmpBaseO);
        setAbs(tmpBaseP, tmpBaseO);
      } else {
        inValidate(true);
      }

      if ((localActor != paramActor) && (paramActor != null)) {
        paramActor.pos.addChildren(this.actor);
        MsgBase.attach(paramActor, this.actor);
      }
      if (paramBoolean2) {
        MsgBase.change(this.actor, paramActor, paramHook, localActor, localHook);
      }
      int i = this.actor.flags;
      if ((i & 0x3) == 3) {
        if ((localActor == null) && (paramActor != null))
          Engine.cur.drawEnv.remove(this.actor);
        else if ((localActor != null) && (paramActor == null) && (!paramBoolean3))
          Engine.cur.drawEnv.add(this.actor);
        setUpdateFlag();
      }
    }
  }

  protected void setBase(Actor paramActor, Hook paramHook, boolean paramBoolean1, boolean paramBoolean2)
  {
    setBase(paramActor, paramHook, paramBoolean1, paramBoolean2, false);
  }

  public void setBase(Actor paramActor, Hook paramHook, boolean paramBoolean)
  {
    setBase(paramActor, paramHook, paramBoolean, false);
  }

  public void changeBase(Actor paramActor, Hook paramHook, boolean paramBoolean)
  {
    setBase(paramActor, paramHook, paramBoolean, true);
  }

  public void changeHookToRel()
  {
    if (!Actor.isValid(this.base))
      throw new ActorException("base is empty or destroyed");
    if (this.baseHook == null)
      throw new ActorException("hook is empty");
    if ((this.flg & 0x1) == 0) validate();
    this.baseHook.computePos(this.base, this.base.pos.getAbs(), this.L);
    this.L.sub(this.base.pos.getAbs());
    this.baseHook = null;
  }

  public void getRel(Loc paramLoc)
  {
    paramLoc.set(this.L);
  }

  public void getRel(Point3d paramPoint3d, Orient paramOrient)
  {
    this.L.get(paramPoint3d, paramOrient);
  }

  public void getRel(Point3d paramPoint3d)
  {
    this.L.get(paramPoint3d);
  }

  public void getRel(Orient paramOrient)
  {
    this.L.get(paramOrient);
  }

  public Loc getRel()
  {
    return this.L;
  }

  public Point3d getRelPoint()
  {
    return this.L.getPoint();
  }

  public Orient getRelOrient()
  {
    return this.L.getOrient();
  }

  public void setRel(Loc paramLoc)
  {
    this.L.set(paramLoc);
    inValidate(true);
    this.flg |= 4;
  }

  public void setRel(Point3d paramPoint3d, Orient paramOrient)
  {
    this.L.set(paramPoint3d, paramOrient);
    inValidate(true);
    this.flg |= 4;
  }

  public void setRel(Point3d paramPoint3d)
  {
    this.L.set(paramPoint3d);
    inValidate(true);
    this.flg |= 4;
  }

  public void setRel(Orient paramOrient)
  {
    this.L.set(paramOrient);
    inValidate(true);
    this.flg |= 4;
  }

  public void getAbs(Loc paramLoc)
  {
    if ((this.flg & 0x1) == 0) validate();
    paramLoc.set(this.Labs);
  }

  public void getAbs(Point3d paramPoint3d, Orient paramOrient)
  {
    if ((this.flg & 0x1) == 0) validate();
    this.Labs.get(paramPoint3d, paramOrient);
  }

  public void getAbs(Point3d paramPoint3d)
  {
    if ((this.flg & 0x1) == 0) validate();
    this.Labs.get(paramPoint3d);
  }

  public void getAbs(Orient paramOrient)
  {
    if ((this.flg & 0x1) == 0) validate();
    this.Labs.get(paramOrient);
  }

  public Loc getAbs()
  {
    if ((this.flg & 0x1) == 0) validate();

    return this.Labs;
  }

  public Point3d getAbsPoint()
  {
    if ((this.flg & 0x1) == 0) validate();
    return this.Labs.getPoint();
  }

  public Orient getAbsOrient()
  {
    if ((this.flg & 0x1) == 0) validate();
    return this.Labs.getOrient();
  }

  private void setAbsBased(Loc paramLoc, boolean paramBoolean)
  {
    if (this.baseHook != null) {
      this.Labs.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
      this.baseHook.computePos(this.base, this.base.pos.getAbs(), this.Labs);
    } else {
      this.base.pos.getAbs(this.Labs);
    }
    this.L.sub(paramLoc, this.Labs);
    this.flg |= 4;
    this.Labs.set(paramLoc);
    inValidateValid(paramBoolean);
  }

  public void setAbs(Loc paramLoc)
  {
    if (this.base == null) {
      setRel(paramLoc);
    } else {
      boolean bool = (this.flg & 0x1) != 0;
      if (!bool) validate();
      setAbsBased(paramLoc, bool);
    }
  }

  public void setAbs(Point3d paramPoint3d, Orient paramOrient)
  {
    if (this.base == null) {
      setRel(paramPoint3d, paramOrient);
    } else {
      boolean bool = (this.flg & 0x1) != 0;
      if (!bool) validate();
      tmpAbsL.set(paramPoint3d, paramOrient);
      setAbsBased(tmpAbsL, bool);
    }
  }

  public void setAbs(Point3d paramPoint3d)
  {
    if (this.base == null) {
      setRel(paramPoint3d);
    } else {
      boolean bool = (this.flg & 0x1) != 0;
      if (!bool) validate();
      this.Labs.get(tmpAbsO);
      tmpAbsL.set(paramPoint3d, tmpAbsO);
      setAbsBased(tmpAbsL, bool);
    }
  }

  public void setAbs(Orient paramOrient)
  {
    if (this.base == null) {
      setRel(paramOrient);
    } else {
      boolean bool = (this.flg & 0x1) != 0;
      if (!bool) validate();
      this.Labs.get(tmpAbsP);
      tmpAbsL.set(tmpAbsP, paramOrient);
      setAbsBased(tmpAbsL, bool);
    }
  }

  public boolean isChanged() {
    return (this.flg & 0x2) != 0;
  }

  public void getCurrent(Loc paramLoc) {
    paramLoc.set(this.curLabs);
  }

  public void getCurrent(Point3d paramPoint3d)
  {
    this.curLabs.get(paramPoint3d);
  }

  public Loc getCurrent()
  {
    return this.curLabs;
  }

  public Point3d getCurrentPoint()
  {
    return this.curLabs.getPoint();
  }

  public Orient getCurrentOrient()
  {
    return this.curLabs.getOrient();
  }

  public void getPrev(Loc paramLoc)
  {
    validatePrev();
    paramLoc.set(this.prevLabs);
  }

  public Loc getPrev()
  {
    validatePrev();

    return this.prevLabs;
  }

  public Loc getRender()
  {
    validateRender();
    return this.renderLabs;
  }

  public void getRender(Loc paramLoc)
  {
    validateRender();
    paramLoc.set(this.renderLabs);
  }

  public void getRender(Point3d paramPoint3d, Orient paramOrient)
  {
    validateRender();
    this.renderLabs.get(paramPoint3d, paramOrient);
  }

  public void getRender(Point3d paramPoint3d)
  {
    validateRender();
    this.renderLabs.get(paramPoint3d);
  }

  public void getTime(long paramLong, Loc paramLoc)
  {
    if (InterpolateAdapter.isProcess()) {
      validatePrev();
      paramLoc.interpolate(this.prevLabs, this.curLabs, ((float)(paramLong - Time.tick()) + Time.tickLenFms()) / Time.tickLenFms());
    }
    else if (paramLong == Time.tick()) {
      paramLoc.set(this.curLabs);
    } else if (paramLong < Time.tick()) {
      validatePrev();
      paramLoc.interpolate(this.prevLabs, this.curLabs, ((float)(paramLong - Time.tick()) + Time.tickLenFms()) / Time.tickLenFms());
    } else if (paramLong == Time.tickNext()) {
      if ((this.flg & 0x1) == 0) validate();
      paramLoc.set(this.Labs);
    } else {
      if ((this.flg & 0x1) == 0) validate();
      paramLoc.interpolate(this.curLabs, this.Labs, (float)(paramLong - Time.tick()) / Time.tickLenFms());
    }
  }

  public void getTime(long paramLong, Point3d paramPoint3d)
  {
    if (InterpolateAdapter.isProcess()) {
      validatePrev();
      paramPoint3d.interpolate(this.prevLabs.getPoint(), this.curLabs.getPoint(), ((float)(paramLong - Time.tick()) + Time.tickLenFms()) / Time.tickLenFms());
    }
    else if (paramLong == Time.tick()) {
      this.curLabs.get(paramPoint3d);
    } else if (paramLong < Time.tick()) {
      validatePrev();
      paramPoint3d.interpolate(this.prevLabs.getPoint(), this.curLabs.getPoint(), ((float)(paramLong - Time.tick()) + Time.tickLenFms()) / Time.tickLenFms());
    } else if (paramLong == Time.tickNext()) {
      if ((this.flg & 0x1) == 0) validate();
      this.Labs.get(paramPoint3d);
    } else {
      if ((this.flg & 0x1) == 0) validate();
      paramPoint3d.interpolate(this.curLabs.getPoint(), this.Labs.getPoint(), (float)(paramLong - Time.tick()) / Time.tickLenFms());
    }
  }

  public void resetAsBase()
  {
    if (!Actor.isValid(this.base)) {
      reset();
      return;
    }
    if ((this.flg & 0x1) == 0) validate();
    if (this.baseHook != null) {
      this.prevLabs.set(this.L);
      this.baseHook.computePos(this.base, this.base.pos.getCurrent(), this.prevLabs);
    } else {
      this.prevLabs.add(this.L, this.base.pos.getCurrent());
    }
    if ((this.flg & 0x8) != 0) {
      int i = this.actor.flags;
      if (((i & 0x1) != 0) && (((i & 0x2) == 0) || (this.base == null)))
        Engine.cur.drawEnv.changedPos(this.actor, this.curLabs.getPoint(), this.prevLabs.getPoint());
      if ((i & 0x30) == 16)
        Engine.cur.collideEnv.changedPos(this.actor, this.curLabs.getPoint(), this.prevLabs.getPoint());
      if ((i & 0x200) == 512)
        Engine.cur.dreamEnv.changedListenerPos(this.actor, this.curLabs.getPoint(), this.prevLabs.getPoint());
      if ((i & 0x100) == 256)
        Engine.cur.dreamEnv.changedFirePos(this.actor, this.curLabs.getPoint(), this.prevLabs.getPoint());
    }
    this.curLabs.set(this.prevLabs);
    if (this.baseHook != null) {
      this.prevLabs.set(this.L);
      this.baseHook.computePos(this.base, this.base.pos.getPrev(), this.prevLabs);
    } else {
      this.prevLabs.add(this.L, this.base.pos.getPrev());
    }
    this.curTick = Time.tickCounter();
    this.renderTick = 0;
  }

  public void reset() {
    updateCurrent();
    this.prevLabs.set(this.curLabs);
    int i = Engine.cur.posChanged.indexOf(this.actor);
    if (i >= 0)
      Engine.cur.posChanged.remove(i);
    this.renderTick = 0;
  }

  protected void updateCurrent()
  {
    Loc localLoc = this.prevLabs;
    this.prevLabs = this.curLabs;
    this.curLabs = localLoc;
    if ((this.flg & 0x2) != 0) {
      getAbs(this.curLabs);
      if ((this.flg & 0x8) != 0) {
        int i = this.actor.flags;
        if (((i & 0x1) != 0) && (((i & 0x2) == 0) || (this.base == null)))
          Engine.cur.drawEnv.changedPos(this.actor, this.prevLabs.getPoint(), this.curLabs.getPoint());
        if ((i & 0x30) == 16)
          Engine.cur.collideEnv.changedPos(this.actor, this.prevLabs.getPoint(), this.curLabs.getPoint());
        if ((i & 0x200) == 512)
          Engine.cur.dreamEnv.changedListenerPos(this.actor, this.prevLabs.getPoint(), this.curLabs.getPoint());
        if ((i & 0x100) == 256)
          Engine.cur.dreamEnv.changedFirePos(this.actor, this.prevLabs.getPoint(), this.curLabs.getPoint());
      }
      this.flg &= -7;
    } else {
      this.curLabs.set(this.prevLabs);
    }
    this.curTick = Time.tickCounter();
  }

  private void setUpdateFlag() {
    int i = 0;
    int j = this.actor.flags;
    if (((j & 0x1) != 0) && (((j & 0x2) == 0) || (this.base == null)))
      i = 1;
    if ((j & 0x30) == 16)
      i = 1;
    if ((j & 0x200) == 512)
      i = 1;
    if ((j & 0x100) == 256)
      i = 1;
    if (i != 0) this.flg |= 8; else
      this.flg &= -9;
  }

  public double speed(Vector3d paramVector3d)
  {
    if (InterpolateAdapter.isProcess()) {
      validatePrev();
      localPoint3d1 = this.prevLabs.getPoint();
      localPoint3d2 = this.curLabs.getPoint();
      d1 = localPoint3d2.jdField_x_of_type_Double - localPoint3d1.jdField_x_of_type_Double;
      d2 = localPoint3d2.jdField_y_of_type_Double - localPoint3d1.jdField_y_of_type_Double;
      d3 = localPoint3d2.jdField_z_of_type_Double - localPoint3d1.jdField_z_of_type_Double;
      d4 = 1.0F / Time.tickLenFs();
      if (paramVector3d != null)
        paramVector3d.set(d1 * d4, d2 * d4, d3 * d4);
      return Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3) * d4;
    }
    if ((this.flg & 0x1) == 0) validate();
    Point3d localPoint3d1 = this.Labs.getPoint();
    Point3d localPoint3d2 = this.curLabs.getPoint();
    double d1 = localPoint3d1.jdField_x_of_type_Double - localPoint3d2.jdField_x_of_type_Double;
    double d2 = localPoint3d1.jdField_y_of_type_Double - localPoint3d2.jdField_y_of_type_Double;
    double d3 = localPoint3d1.jdField_z_of_type_Double - localPoint3d2.jdField_z_of_type_Double;
    double d4 = 1.0F / Time.tickLenFs();
    if (paramVector3d != null)
      paramVector3d.set(d1 * d4, d2 * d4, d3 * d4);
    return Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3) * d4;
  }

  protected void drawingChange(boolean paramBoolean)
  {
    if (paramBoolean) {
      if ((this.actor.isVisibilityAsBase()) && (this.base != null))
        Engine.cur.drawEnv.remove(this.actor);
      else
        Engine.cur.drawEnv.add(this.actor);
    }
    else Engine.cur.drawEnv.remove(this.actor);

    setUpdateFlag();
  }

  protected void collideChange(boolean paramBoolean)
  {
    if (paramBoolean) Engine.cur.collideEnv.add(this.actor); else
      Engine.cur.collideEnv.remove(this.actor);
    setUpdateFlag();
  }

  protected void dreamFireChange(boolean paramBoolean)
  {
    if (paramBoolean) Engine.dreamEnv().addFire(this.actor); else
      Engine.dreamEnv().removeFire(this.actor);
    setUpdateFlag();
  }

  protected void addChildren(Actor paramActor)
  {
    if (this.baseAttached == null)
      this.baseAttached = new ArrayList();
    this.baseAttached.add(paramActor);
  }

  protected void removeChildren(Actor paramActor)
  {
    this.baseAttached.remove(this.baseAttached.indexOf(paramActor));
  }

  public void destroy() {
    if (this.actor == null) return;
    clearEnvs(this.actor);
    if (this.baseAttached != null) {
      while (this.baseAttached.size() > 0) {
        Actor localActor = (Actor)this.baseAttached.get(0);
        if (localActor == localActor.pos.actor())
          localActor.pos.changeBase(null, null, true);
        else
          this.baseAttached.remove(0);
      }
    }
    setBase(null, null, true, true, true);
    this.actor = null;
  }
  protected ActorPosMove() {
  }

  public ActorPosMove(Actor paramActor) {
    this.actor = paramActor;
    initEnvs(this.actor);
    setUpdateFlag();
  }

  public ActorPosMove(Actor paramActor, Point3d paramPoint3d, Orient paramOrient) {
    this.actor = paramActor;
    this.L.set(paramPoint3d, paramOrient);
    this.curLabs.set(this.L);
    this.prevLabs.set(this.L);
    initEnvs(this.actor);
    setUpdateFlag();
  }

  public ActorPosMove(Actor paramActor, Loc paramLoc) {
    this.actor = paramActor;
    this.L.set(paramLoc);
    this.curLabs.set(this.L);
    this.prevLabs.set(this.L);
    initEnvs(this.actor);
    setUpdateFlag();
  }

  public ActorPosMove(ActorPos paramActorPos) {
    this(paramActorPos.actor(), paramActorPos.getAbs());
    Object[] arrayOfObject = paramActorPos.getBaseAttached();
    if ((arrayOfObject != null) && (arrayOfObject.length > 0)) {
      this.baseAttached = new ArrayList();
      for (int i = 0; i < arrayOfObject.length; i++)
        this.baseAttached.add(arrayOfObject[i]);
    }
    setUpdateFlag();
  }
}