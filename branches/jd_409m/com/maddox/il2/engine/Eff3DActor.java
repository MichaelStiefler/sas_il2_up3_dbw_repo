package com.maddox.il2.engine;

import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.Spawn;
import com.maddox.rts.State;
import com.maddox.rts.States;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class Eff3DActor extends Actor
  implements MsgBaseListener
{
  public static final int STATE_READY = 0;
  public static final int STATE_FINISH = 1;
  protected boolean bUseIntensityAsSwitchDraw = false;

  protected Object syncObj = null;

  private static Loc lres = new Loc();
  private static Loc lempty = new Loc();
  private static ActorPosMoveInit apos = new ActorPosMoveInit();

  protected static boolean _isStaticPos = true;

  public boolean isUseIntensityAsSwitchDraw()
  {
    return this.bUseIntensityAsSwitchDraw;
  }

  public void setUseIntensityAsSwitchDraw(boolean paramBoolean) {
    this.bUseIntensityAsSwitchDraw = paramBoolean;
  }
  public void msgBaseAttach(Actor paramActor) {
  }
  public void msgBaseDetach(Actor paramActor) {
  }
  public void msgBaseChange(Actor paramActor1, Hook paramHook1, Actor paramActor2, Hook paramHook2) { if ((paramActor1 == null) && (paramActor2 != null) && (paramActor2.isDestroyed()))
      _finish(); }

  public void _finish()
  {
    ((Eff3D)this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw).setIntesity(0.0F);
    this.jdField_states_of_type_ComMaddoxRtsStates.setState(1);
  }

  public static void finish(Eff3DActor paramEff3DActor) {
    if (Actor.isValid(paramEff3DActor)) paramEff3DActor._finish(); 
  }

  public void _setIntesity(float paramFloat)
  {
    if (this.jdField_states_of_type_ComMaddoxRtsStates.getState() == 0) {
      ((Eff3D)this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw).setIntesity(paramFloat);
      if (this.bUseIntensityAsSwitchDraw) {
        if (paramFloat != 0.0F) {
          drawing(true);
          this.syncObj = null;
          return;
        }
        float f = ((Eff3D)this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw).timeFinish();
        if (f <= 0.0F) {
          drawing(false);
        } else {
          boolean bool = ((Eff3D)this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw).isTimeReal();
          this.syncObj = new Object();
          new MsgAction(bool ? 64 : 0, (bool ? Time.currentReal() : Time.current()) + ()(f * 1000.0F), this.syncObj) {
            public void doAction(Object paramObject) {
              if ((paramObject != Eff3DActor.this.syncObj) || (Eff3DActor.this.states.getState() != 0))
              {
                return;
              }Eff3DActor.this.drawing(false);
            }
          };
        }
      }
    }
  }

  public static void setIntesity(Eff3DActor paramEff3DActor, float paramFloat) {
    if (Actor.isValid(paramEff3DActor)) paramEff3DActor._setIntesity(paramFloat); 
  }

  public float _getIntesity()
  {
    if (this.jdField_states_of_type_ComMaddoxRtsStates.getState() == 0) {
      return ((Eff3D)this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw).getIntesity();
    }
    return 0.0F;
  }

  public static float getIntesity(Eff3DActor paramEff3DActor) {
    if (Actor.isValid(paramEff3DActor)) return paramEff3DActor._getIntesity();
    return 0.0F;
  }

  public static Eff3DActor New(Actor paramActor, Hook paramHook, Loc paramLoc, float paramFloat1, String paramString, float paramFloat2)
  {
    return New(paramActor, paramHook, paramLoc, paramFloat1, paramString, paramFloat2, false);
  }

  public static Eff3DActor New(Actor paramActor, Hook paramHook, Loc paramLoc, float paramFloat1, String paramString, float paramFloat2, boolean paramBoolean)
  {
    if (!Config.isUSE_RENDER()) return null;
    apos.setBase(paramActor, paramHook, false);
    if (paramLoc != null) apos.setRel(paramLoc);
    apos.resetAsBase();
    apos.getRender(lres);
    apos.setBase(null, null, false);
    if (paramLoc != null) apos.setRel(lempty);
    Eff3DActor localEff3DActor = NewPosMove(lres, paramFloat1, paramString, paramFloat2);
    localEff3DActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(paramActor, paramHook, false);
    if (paramLoc != null) localEff3DActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setRel(paramLoc); else
      localEff3DActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setRel(lempty);
    localEff3DActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.resetAsBase();
    if (paramBoolean) {
      localEff3DActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.changeHookToRel();
      localEff3DActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setUpdateEnable(false);
    }
    return localEff3DActor;
  }

  public static Eff3DActor NewPosMove(Loc paramLoc, float paramFloat1, String paramString, float paramFloat2)
  {
    if (!Config.isUSE_RENDER()) return null;
    Eff3D.initSetLocator(paramLoc);
    Eff3D.initSetSize(paramFloat1);
    Eff3D.initSetParamFileName(paramString);
    Eff3D.initSetProcessTime(paramFloat2);

    Eff3D localEff3D = Eff3D.New();

    _isStaticPos = false;
    Eff3DActor localEff3DActor = localEff3D.NewActor(paramLoc);
    _isStaticPos = true;
    return localEff3DActor;
  }

  public static Eff3DActor New(Loc paramLoc, float paramFloat1, String paramString, float paramFloat2)
  {
    if (!Config.isUSE_RENDER()) return null;
    Eff3D.initSetLocator(paramLoc);
    Eff3D.initSetSize(paramFloat1);
    Eff3D.initSetParamFileName(paramString);
    Eff3D.initSetProcessTime(paramFloat2);

    Eff3D localEff3D = Eff3D.New();

    return localEff3D.NewActor(paramLoc);
  }

  public static Eff3DActor New(ActorPos paramActorPos, float paramFloat1, String paramString, float paramFloat2)
  {
    if (!Config.isUSE_RENDER()) return null;
    Eff3D.initSetLocator(paramActorPos.getAbs());
    Eff3D.initSetSize(paramFloat1);
    Eff3D.initSetParamFileName(paramString);
    Eff3D.initSetProcessTime(paramFloat2);

    Eff3D localEff3D = Eff3D.New();

    return localEff3D.NewActor(paramActorPos);
  }

  protected Eff3DActor(Eff3D paramEff3D, Loc paramLoc)
  {
    this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw = paramEff3D;
    if (_isStaticPos)
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosStaticEff3D(this, paramLoc);
    else
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this, paramLoc);
    this.jdField_states_of_type_ComMaddoxRtsStates = new States(new Object[] { new Ready(this), new Finish(this) });
    this.jdField_states_of_type_ComMaddoxRtsStates.setState(0);
    drawing(true);
    Engine.cur.allEff3DActors.put(this, null);
  }
  protected Eff3DActor(Eff3D paramEff3D, ActorPos paramActorPos) {
    this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw = paramEff3D;
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = paramActorPos;
    this.jdField_states_of_type_ComMaddoxRtsStates = new States(new Object[] { new Ready(this), new Finish(this) });
    this.jdField_states_of_type_ComMaddoxRtsStates.setState(0);
    this.flags |= 3;
    paramActorPos.base().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.addChildren(this);
    Engine.cur.allEff3DActors.put(this, null);
  }
  protected Eff3DActor() {
  }
  protected void createActorHashCode() { makeActorRealHashCode(); }

  public void destroy()
  {
    if (isDestroyed()) return;
    Engine.cur.allEff3DActors.remove(this);
    super.destroy();
    this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw = null;
    if ((this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos instanceof ActorPosStaticEff3D))
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = null;
    this.syncObj = null;
  }

  static
  {
    Spawn.add(Eff3DActor.class, new SPAWN());
  }

  public static class SPAWN
    implements ActorSpawn
  {
    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg)
    {
      if (!Config.isUSE_RENDER()) return null;
      Loc localLoc = paramActorSpawnArg.getAbsLoc();
      Eff3D.spawnSetCommonFields(paramActorSpawnArg, localLoc);

      Eff3D localEff3D = Eff3D.New();

      Eff3DActor localEff3DActor = localEff3D.NewActor(localLoc);
      paramActorSpawnArg.set(localEff3DActor);
      return localEff3DActor;
    }
  }

  public class Finish extends State
  {
    public Finish(Object arg2)
    {
      super();
    }
    public void begin(int paramInt) {
      float f = ((Eff3D)Eff3DActor.this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw).timeFinish();
      boolean bool = ((Eff3D)Eff3DActor.this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw).isTimeReal();
      if (f <= 0.0F) {
        Eff3DActor.this.destroy();
        return;
      }
      ((Eff3D)Eff3DActor.this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw).finish();
      MsgDestroy.Post(bool ? 64 : 0, (bool ? Time.currentReal() : Time.current()) + ()(f * 1000.0F), superObj());
    }
  }

  public class Ready extends State
  {
    public Ready(Object arg2)
    {
      super();
    }
    public void begin(int paramInt) {
      float f = ((Eff3D)Eff3DActor.this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw).timeLife();
      boolean bool = ((Eff3D)Eff3DActor.this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw).isTimeReal();
      if (f >= 0.0F)
        new Eff3DActor.2(this, bool ? 64 : 0, (bool ? Time.currentReal() : Time.current()) + ()(f * 1000.0F));
    }
  }
}