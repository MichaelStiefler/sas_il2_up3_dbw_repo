package com.maddox.il2.engine;

public class ActorPosMoveInit extends ActorPosMove
{
  public void inValidate(boolean paramBoolean)
  {
    if (paramBoolean)
      this.jdField_flg_of_type_Int &= -2;
    this.jdField_flg_of_type_Int |= 2;
  }

  protected void validateRender() {
    this.renderTick = (RendersMain.frame() - 1);
    super.validateRender();
  }

  protected void setBase(Actor paramActor, Hook paramHook, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.jdField_base_of_type_ComMaddoxIl2EngineActor = paramActor;
    this.jdField_baseHook_of_type_ComMaddoxIl2EngineHook = paramHook;

    inValidate(true);
  }

  public void resetAsBase() {
    if (!Actor.isValid(this.jdField_base_of_type_ComMaddoxIl2EngineActor)) {
      reset();
      return;
    }
    if ((this.jdField_flg_of_type_Int & 0x1) == 0) validate();
    if (this.jdField_baseHook_of_type_ComMaddoxIl2EngineHook != null) {
      this.jdField_prevLabs_of_type_ComMaddoxIl2EngineLoc.set(this.jdField_L_of_type_ComMaddoxIl2EngineLoc);
      this.jdField_baseHook_of_type_ComMaddoxIl2EngineHook.computePos(this.jdField_base_of_type_ComMaddoxIl2EngineActor, this.jdField_base_of_type_ComMaddoxIl2EngineActor.pos.getCurrent(), this.jdField_prevLabs_of_type_ComMaddoxIl2EngineLoc);
    } else {
      this.jdField_prevLabs_of_type_ComMaddoxIl2EngineLoc.add(this.jdField_L_of_type_ComMaddoxIl2EngineLoc, this.jdField_base_of_type_ComMaddoxIl2EngineActor.pos.getCurrent());
    }
    this.jdField_curLabs_of_type_ComMaddoxIl2EngineLoc.set(this.jdField_prevLabs_of_type_ComMaddoxIl2EngineLoc);
    if (this.jdField_baseHook_of_type_ComMaddoxIl2EngineHook != null) {
      this.jdField_prevLabs_of_type_ComMaddoxIl2EngineLoc.set(this.jdField_L_of_type_ComMaddoxIl2EngineLoc);
      this.jdField_baseHook_of_type_ComMaddoxIl2EngineHook.computePos(this.jdField_base_of_type_ComMaddoxIl2EngineActor, this.jdField_base_of_type_ComMaddoxIl2EngineActor.pos.getPrev(), this.jdField_prevLabs_of_type_ComMaddoxIl2EngineLoc);
    } else {
      this.jdField_prevLabs_of_type_ComMaddoxIl2EngineLoc.add(this.jdField_L_of_type_ComMaddoxIl2EngineLoc, this.jdField_base_of_type_ComMaddoxIl2EngineActor.pos.getPrev());
    }
  }

  public void reset() {
    updateCurrent();
    this.jdField_prevLabs_of_type_ComMaddoxIl2EngineLoc.set(this.jdField_curLabs_of_type_ComMaddoxIl2EngineLoc);
  }

  protected void updateCurrent()
  {
    this.jdField_prevLabs_of_type_ComMaddoxIl2EngineLoc.set(this.jdField_curLabs_of_type_ComMaddoxIl2EngineLoc);
    if ((this.jdField_flg_of_type_Int & 0x2) != 0) {
      getAbs(this.jdField_curLabs_of_type_ComMaddoxIl2EngineLoc);
      this.jdField_flg_of_type_Int &= -3;
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