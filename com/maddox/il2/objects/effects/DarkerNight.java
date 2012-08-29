package com.maddox.il2.objects.effects;

import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Main3D;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.gl;

public class DarkerNight extends Render
  implements MsgGLContextListener
{
  int _indx = 0;

  private float alpha = 1.0F;

  public void msgGLContext(int paramInt)
  {
  }

  protected void contextResize(int paramInt1, int paramInt2)
  {
  }

  public void preRender()
  {
    if (World.Sun().ToSun.z < 0.0F)
    {
      this.alpha = World.Sun().sunMultiplier;
      if (this.alpha < 0.35F)
        this.alpha = 0.35F;
    }
    else {
      this.alpha = 1.0F;
    }
  }

  public DarkerNight(int paramInt, float paramFloat) {
    super(paramFloat);
    this._indx = paramInt;
    useClearDepth(false);
    useClearColor(false);
    if (this._indx == 0)
      setName("DarkerNight");
    GLContext.getCurrent().msgAddListener(this, null);
    if (paramInt != 0)
      Main3D.cur3D()._getAspectViewPort(paramInt, this.viewPort);
  }

  public void render()
  {
    if (this.alpha < 1.0D)
    {
      Render.clearStates();
      gl.ShadeModel(7425);
      gl.Disable(2929);
      gl.Enable(3553);
      gl.Enable(3042);
      gl.AlphaFunc(516, 0.0F);
      gl.BlendFunc(774, 770);
      gl.Begin(6);
      gl.Color4f(0.0F, 0.0F, 0.0F, this.alpha);

      gl.Vertex2f(0.0F, 3.0F);
      gl.Vertex2f(-3.0F, -3.0F);
      gl.Vertex2f(3.0F, -3.0F);
      gl.End();
      gl.BlendFunc(770, 771);
    }
  }

  public void setShow(boolean paramBoolean)
  {
    if (this._indx == 0)
    {
      super.setShow(paramBoolean);
    }
  }

  public boolean isShow()
  {
    if (this._indx == 0)
      return super.isShow();
    return (Config.cur.isUse3Renders()) && (Main3D.cur3D().overLoad.isShow());
  }
}