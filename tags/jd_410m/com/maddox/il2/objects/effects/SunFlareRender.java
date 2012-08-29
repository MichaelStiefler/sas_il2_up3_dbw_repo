package com.maddox.il2.objects.effects;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Render;
import com.maddox.il2.game.Main3D;

class SunFlareRender extends Render
{
  private int _indx;
  private int resPreRender = 0;
  private Actor actor;

  public void setActor(Actor paramActor)
  {
    this.actor = paramActor;
  }
  public boolean isShow() {
    if (!Actor.isValid(this.actor)) return false;
    if (this._indx == 0) return super.isShow();
    return (Config.cur.isUse3Renders()) && (super.isShow());
  }

  public void preRender() {
    if (!Actor.isValid(this.actor)) {
      this.resPreRender = 0;
      return;
    }
    Main3D.cur3D().setRenderIndx(this._indx);
    this.resPreRender = this.actor.draw.preRender(this.actor);
    Main3D.cur3D().setRenderIndx(0);
  }

  public void render() {
    if (!Actor.isValid(this.actor)) return;
    if (this.resPreRender > 0) {
      Render.prepareStates();
      Main3D.cur3D().setRenderIndx(this._indx);
      this.actor.draw.render(this.actor);
      Main3D.cur3D().setRenderIndx(0);
    }
  }

  public SunFlareRender(int paramInt, float paramFloat) {
    super(paramFloat);
    this._indx = paramInt;
    useClearDepth(false);
    useClearColor(false);
    if (paramInt != 0)
      Main3D.cur3D()._getAspectViewPort(paramInt, this.viewPort);
  }
}