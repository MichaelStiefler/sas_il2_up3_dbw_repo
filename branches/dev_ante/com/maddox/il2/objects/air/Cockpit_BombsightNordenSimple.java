package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class Cockpit_BombsightNordenSimple extends CockpitPilot
{
  BombsightNordenCockpitLogic m_Logic = new BombsightNordenCockpitLogic(this);
  private boolean bEntered;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      enter();
      return true;
    }

    return false;
  }

  protected void doFocusLeave()
  {
    if (isFocused())
    {
      leave();
      super.doFocusLeave();
    }
  }

  private void enter()
  {
    this.m_Logic.enter();
    this.bEntered = true;
  }

  private void leave()
  {
    if (this.bEntered)
    {
      this.m_Logic.leave();
      this.bEntered = false;
    }
  }

  public void destroy()
  {
    super.destroy();
    leave();
  }

  public void doToggleAim(boolean paramBoolean)
  {
  }

  public Cockpit_BombsightNordenSimple()
  {
    super("3DO/Cockpit/B-25J-Bombardier/NordenSimple.him", "bf109");
    this.bEntered = false;
    try
    {
      Loc localLoc = new Loc();
      HookNamed localHookNamed = new HookNamed(this.mesh, "CAMERAAIM");
      localHookNamed.computePos(this, this.pos.getAbs(), localLoc);
      this.m_Logic.setKoeffs(localLoc.getOrient().getAzimut(), localLoc.getOrient().getTangage(), localLoc.getOrient().getKren());
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.m_Logic.reflectWorldToInstruments(this.bEntered);
  }

  static
  {
    Property.set(CLASS.THIS(), "astatePilotIndx", 0);
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      Cockpit_BombsightNordenSimple.this.m_Logic.OnTick(Cockpit_BombsightNordenSimple.this.bEntered);
      return true;
    }

    Interpolater()
    {
    }
  }
}