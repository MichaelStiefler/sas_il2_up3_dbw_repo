package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.bridges.Bridge;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.rts.Message;
import java.util.AbstractCollection;

public class ActorTarget extends Actor
  implements ActorAlign
{
  public int importance;
  public int type;
  public Actor target = null;
  public int timeout;
  public boolean bTimeout;
  public boolean bLanding;
  public int destructLevel = 50;
  public int r;
  private static Actor _Actor = null;
  private static double _Len2;
  private static double _maxLen2;
  private static SelectBridge _selectBridge = new SelectBridge();

  private static SelectAir _selectAir = new SelectAir();

  private static SelectChief _selectChief = new SelectChief();

  private static SelectMoved _selectMoved = new SelectMoved();

  public Actor getTarget()
  {
    return this.target;
  }

  public void align() {
    alignPosToLand(0.0D, true);
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public ActorTarget(Point3d paramPoint3d, int paramInt1, String paramString, int paramInt2) {
    this.flags |= 8192;
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(paramPoint3d);
    align();
    drawing(true);
    this.type = paramInt1;

    if ((paramInt1 == 3) || (paramInt1 == 6) || (paramInt1 == 7))
    {
      this.bTimeout = true;
      this.timeout = 30;
    }
    if ((paramInt1 != 1) && (paramInt1 != 6))
    {
      if (paramString == null) {
        if ((paramInt1 == 2) || (paramInt1 == 7))
        {
          this.target = selectBridge(10000.0D);
        } else if (paramInt1 == 4)
          this.target = selectAir(10000.0D);
        else if (paramInt1 == 5)
          this.target = selectChief(10000.0D);
        else if (paramInt1 == 3)
          this.target = selectChief(1000.0D);
        else
          this.target = selectMoved(10000.0D);
        if ((this.target == null) && (paramInt1 != 3))
          throw new RuntimeException("target NOT found");
      }
      else {
        this.target = Actor.getByName(paramString);
        if (this.target == null)
          throw new RuntimeException("target NOT found");
        if ((paramInt1 == 2) || (paramInt1 == 7))
        {
          if (!(this.target instanceof Bridge))
            throw new RuntimeException("target NOT found");
        } else if (paramInt1 == 4) {
          if (!(this.target instanceof PathAir))
            throw new RuntimeException("target NOT found");
          this.target = ((Path)this.target).point(paramInt2);
        } else if (paramInt1 == 5) {
          if (!(this.target instanceof PathChief))
            throw new RuntimeException("target NOT found");
          this.target = ((Path)this.target).point(paramInt2);
        } else if (paramInt1 == 3) {
          if (!(this.target instanceof PathChief))
            throw new RuntimeException("target NOT found");
          this.target = ((Path)this.target).point(paramInt2);
        } else {
          if (!(this.target instanceof Path))
            throw new RuntimeException("target NOT found");
          this.target = ((Path)this.target).point(paramInt2);
        }
      }
    }
    switch (paramInt1) { case 0:
      if ((this.target instanceof PAir)) this.jdField_icon_of_type_ComMaddoxIl2EngineMat = IconDraw.get("icons/tdestroyair.mat"); else
        this.jdField_icon_of_type_ComMaddoxIl2EngineMat = IconDraw.get("icons/tdestroychief.mat");
      break;
    case 1:
      this.jdField_icon_of_type_ComMaddoxIl2EngineMat = IconDraw.get("icons/tdestroyground.mat");
      this.r = 500;
      break;
    case 2:
      this.jdField_icon_of_type_ComMaddoxIl2EngineMat = IconDraw.get("icons/tdestroybridge.mat"); break;
    case 3:
      this.jdField_icon_of_type_ComMaddoxIl2EngineMat = IconDraw.get("icons/tinspect.mat");
      this.r = 500;
      break;
    case 4:
      this.jdField_icon_of_type_ComMaddoxIl2EngineMat = IconDraw.get("icons/tescort.mat"); break;
    case 5:
      this.jdField_icon_of_type_ComMaddoxIl2EngineMat = IconDraw.get("icons/tdefence.mat"); break;
    case 6:
      this.jdField_icon_of_type_ComMaddoxIl2EngineMat = IconDraw.get("icons/tdefenceground.mat");
      this.r = 500;
      break;
    case 7:
      this.jdField_icon_of_type_ComMaddoxIl2EngineMat = IconDraw.get("icons/tdefencebridge.mat"); }
  }

  protected void createActorHashCode() {
    makeActorRealHashCode();
  }

  private Actor selectBridge(double paramDouble)
  {
    _Actor = null; _maxLen2 = paramDouble * paramDouble;
    Point3d localPoint3d = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    Engine.drawEnv().getFiltered((AbstractCollection)null, localPoint3d.jdField_x_of_type_Double - paramDouble, localPoint3d.jdField_y_of_type_Double - paramDouble, localPoint3d.jdField_x_of_type_Double + paramDouble, localPoint3d.jdField_y_of_type_Double + paramDouble, 2, _selectBridge);

    Actor localActor = _Actor; _Actor = null;
    return localActor;
  }

  private Actor selectAir(double paramDouble)
  {
    _Actor = null; _maxLen2 = paramDouble * paramDouble;
    Point3d localPoint3d = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    Engine.drawEnv().getFiltered((AbstractCollection)null, localPoint3d.jdField_x_of_type_Double - paramDouble, localPoint3d.jdField_y_of_type_Double - paramDouble, localPoint3d.jdField_x_of_type_Double + paramDouble, localPoint3d.jdField_y_of_type_Double + paramDouble, 12, _selectAir);

    Actor localActor = _Actor; _Actor = null;
    return localActor;
  }

  private Actor selectChief(double paramDouble)
  {
    _Actor = null; _maxLen2 = paramDouble * paramDouble;
    Point3d localPoint3d = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    Engine.drawEnv().getFiltered((AbstractCollection)null, localPoint3d.jdField_x_of_type_Double - paramDouble, localPoint3d.jdField_y_of_type_Double - paramDouble, localPoint3d.jdField_x_of_type_Double + paramDouble, localPoint3d.jdField_y_of_type_Double + paramDouble, 12, _selectChief);

    Actor localActor = _Actor; _Actor = null;
    return localActor;
  }

  private Actor selectMoved(double paramDouble)
  {
    _Actor = null; _maxLen2 = paramDouble * paramDouble;
    Point3d localPoint3d = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    Engine.drawEnv().getFiltered((AbstractCollection)null, localPoint3d.jdField_x_of_type_Double - paramDouble, localPoint3d.jdField_y_of_type_Double - paramDouble, localPoint3d.jdField_x_of_type_Double + paramDouble, localPoint3d.jdField_y_of_type_Double + paramDouble, 12, _selectMoved);

    Actor localActor = _Actor; _Actor = null;
    return localActor;
  }

  static class SelectMoved
    implements ActorFilter
  {
    public boolean isUse(Actor paramActor, double paramDouble)
    {
      if (paramDouble <= ActorTarget._maxLen2) {
        if (!(paramActor instanceof PPoint))
          return true;
        if ((ActorTarget._Actor == null) || (paramDouble < ActorTarget._Len2)) {
          ActorTarget.access$102(paramActor); ActorTarget.access$202(paramDouble);
        }
      }
      return true;
    }
  }

  static class SelectChief
    implements ActorFilter
  {
    public boolean isUse(Actor paramActor, double paramDouble)
    {
      if (paramDouble <= ActorTarget._maxLen2) {
        if (!(paramActor instanceof PNodes))
          return true;
        if ((ActorTarget._Actor == null) || (paramDouble < ActorTarget._Len2)) {
          ActorTarget.access$102(paramActor); ActorTarget.access$202(paramDouble);
        }
      }
      return true;
    }
  }

  static class SelectAir
    implements ActorFilter
  {
    public boolean isUse(Actor paramActor, double paramDouble)
    {
      if (paramDouble <= ActorTarget._maxLen2) {
        if (!(paramActor instanceof PAir))
          return true;
        if ((ActorTarget._Actor == null) || (paramDouble < ActorTarget._Len2)) {
          ActorTarget.access$102(paramActor); ActorTarget.access$202(paramDouble);
        }
      }
      return true;
    }
  }

  static class SelectBridge
    implements ActorFilter
  {
    public boolean isUse(Actor paramActor, double paramDouble)
    {
      if (paramDouble <= ActorTarget._maxLen2) {
        if ((paramActor instanceof BridgeSegment)) paramActor = paramActor.getOwner(); else
          return true;
        if ((ActorTarget._Actor == null) || (paramDouble < ActorTarget._Len2)) {
          ActorTarget.access$102(paramActor); ActorTarget.access$202(paramDouble);
        }
      }
      return true;
    }
  }
}