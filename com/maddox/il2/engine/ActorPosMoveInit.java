package com.maddox.il2.engine;

public class ActorPosMoveInit extends ActorPosMove
{
  public void inValidate(boolean paramBoolean)
  {
    if (paramBoolean)
      this.flg &= -2;
    this.flg |= 2;
  }

  protected void validateRender() {
    this.renderTick = (RendersMain.frame() - 1);
    super.validateRender();
  }

  protected void setBase(Actor paramActor, Hook paramHook, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.base = paramActor;
    this.baseHook = paramHook;

    inValidate(true);
  }

  public void resetAsBase() {
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
    this.curLabs.set(this.prevLabs);
    if (this.baseHook != null) {
      this.prevLabs.set(this.L);
      this.baseHook.computePos(this.base, this.base.pos.getPrev(), this.prevLabs);
    } else {
      this.prevLabs.add(this.L, this.base.pos.getPrev());
    }
  }

  public void reset() {
    updateCurrent();
    this.prevLabs.set(this.curLabs);
  }

  protected void updateCurrent()
  {
    this.prevLabs.set(this.curLabs);
    if ((this.flg & 0x2) != 0) {
      getAbs(this.curLabs);
      this.flg &= -3;
    }
  }

  protected void drawingChange(boolean paramBoolean)
  {
  }

  protected void collideChange(boolean paramBoolean)
  {
  }

  protected void dreamFireChange(boolean paramBoolean)
  {
  }

  protected void addChildren(Actor paramActor)
  {
  }

  protected void removeChildren(Actor paramActor)
  {
  }

  public void destroy()
  {
  }
}