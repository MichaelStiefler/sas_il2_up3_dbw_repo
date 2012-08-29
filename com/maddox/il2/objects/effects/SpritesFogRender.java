package com.maddox.il2.objects.effects;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Render;

class SpritesFogRender extends Render
{
  private int resPreRender = 0;

  public void preRender() { if (!Actor.isValid(SpritesFog.actor)) {
      this.resPreRender = 0;
      return;
    }
    this.resPreRender = SpritesFog.actor.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw.preRender(SpritesFog.actor); }

  public void render()
  {
    if (this.resPreRender > 0)
    {
      Render.prepareStates();
      SpritesFog.actor.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw.render(SpritesFog.actor);
    }
  }

  public void getViewPort(int[] paramArrayOfInt)
  {
    super.getViewPort(paramArrayOfInt);
  }

  public SpritesFogRender(float paramFloat) {
    super(paramFloat);
    setSaveAspect(Config.cur.windowSaveAspect);
    useClearDepth(false);
    useClearColor(false);
    contextResized();
  }
}