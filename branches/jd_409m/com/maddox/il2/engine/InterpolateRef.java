package com.maddox.il2.engine;

public abstract class InterpolateRef extends Interpolate
{
  public void invokeRef()
  {
    if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor.pos != null) && (this.jdField_actor_of_type_ComMaddoxIl2EngineActor.pos.base() != null))
      InterpolateAdapter.forceInterpolate(this.jdField_actor_of_type_ComMaddoxIl2EngineActor.pos.base());
  }
}