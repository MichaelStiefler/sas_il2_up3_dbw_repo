package com.maddox.il2.engine;

import com.maddox.rts.Message;
import com.maddox.rts.MsgInvokeMethod;
import com.maddox.rts.Time;

public class Animator extends Interpolate
{
  protected Animates moves;
  protected Animate move;
  protected Hook landHook;
  protected double lenToEnd;
  protected double lenStep;
  protected double countMoves;
  protected double speed;
  protected long time0;
  protected Loc loc0 = new Loc();

  public boolean isRun() {
    return this.jdField_bExecuted_of_type_Boolean;
  }

  public String getCur()
  {
    return this.jdField_bExecuted_of_type_Boolean ? this.move.name : null;
  }

  public AnimatedActor animatedActor()
  {
    return (AnimatedActor)this.jdField_actor_of_type_ComMaddoxIl2EngineActor;
  }

  public Hook getLandHook()
  {
    if (this.landHook == null)
      this.landHook = this.jdField_actor_of_type_ComMaddoxIl2EngineActor.findHook("ground_level");
    return this.landHook;
  }

  public void start(String paramString, double paramDouble1, double paramDouble2)
  {
    start(paramString, paramDouble1, paramDouble2, (Message)null, 1.0D);
  }

  public void start(String paramString1, double paramDouble1, double paramDouble2, String paramString2)
  {
    start(paramString1, paramDouble1, paramDouble2, paramString2, 1.0D);
  }

  public void start(String paramString, double paramDouble1, double paramDouble2, Message paramMessage)
  {
    start(paramString, paramDouble1, paramDouble2, paramMessage, 1.0D);
  }

  public void start(String paramString1, double paramDouble1, double paramDouble2, String paramString2, double paramDouble3)
  {
    MsgInvokeMethod localMsgInvokeMethod = null;
    if (paramString2 != null)
      localMsgInvokeMethod = new MsgInvokeMethod(paramString2);
    start(paramString1, paramDouble1, paramDouble2, localMsgInvokeMethod, paramDouble3);
  }

  public void start(String paramString, double paramDouble1, double paramDouble2, Message paramMessage, double paramDouble3)
  {
    if (isRun()) stop(false);
    this.jdField_msgEnd_of_type_ComMaddoxRtsMessage = paramMessage;
    this.speed = paramDouble3;
    this.move = this.moves.get(paramString);
    this.jdField_bExecuted_of_type_Boolean = true;
    this.countMoves = 0.0D;
    this.time0 = timeCurrent();
    this.jdField_actor_of_type_ComMaddoxIl2EngineActor.pos.getAbs(this.loc0);
    this.lenStep = this.move.fullStepLen(this, paramDouble3);
    this.lenToEnd = this.move.setup(this, this.loc0, paramDouble1, paramDouble2, paramDouble3);
    if ((this.lenToEnd > 0.001D) && (this.lenStep > 0.001D)) {
      tick();
    } else {
      this.countMoves = 1.0D;
      tick();
      stop(true);
    }
  }

  public void start(String paramString, double paramDouble)
  {
    start(paramString, paramDouble, (Message)null, 1.0D);
  }

  public void start(String paramString1, double paramDouble, String paramString2)
  {
    start(paramString1, paramDouble, paramString2, 1.0D);
  }

  public void start(String paramString, double paramDouble, Message paramMessage)
  {
    start(paramString, paramDouble, paramMessage, 1.0D);
  }

  public void start(String paramString1, double paramDouble1, String paramString2, double paramDouble2)
  {
    MsgInvokeMethod localMsgInvokeMethod = null;
    if (paramString2 != null)
      localMsgInvokeMethod = new MsgInvokeMethod(paramString2);
    start(paramString1, paramDouble1, localMsgInvokeMethod, paramDouble2);
  }

  public void start(String paramString, double paramDouble1, Message paramMessage, double paramDouble2)
  {
    if (isRun()) stop(false);
    this.jdField_msgEnd_of_type_ComMaddoxRtsMessage = paramMessage;
    this.speed = paramDouble2;
    this.move = this.moves.get(paramString);
    this.jdField_bExecuted_of_type_Boolean = true;
    this.countMoves = paramDouble1;
    this.time0 = timeCurrent();
    this.jdField_actor_of_type_ComMaddoxIl2EngineActor.pos.getAbs(this.loc0);
    if (paramDouble1 != 0.0D) {
      tick();
    } else {
      this.countMoves = 1.0D;
      tick();
      stop(true);
    }
  }

  public void stop()
  {
    stop(false);
  }

  public void stop(boolean paramBoolean)
  {
    if (!this.jdField_bExecuted_of_type_Boolean) return;
    this.jdField_bExecuted_of_type_Boolean = false;
    if ((paramBoolean) && (this.jdField_msgEnd_of_type_ComMaddoxRtsMessage != null)) {
      if ((!this.jdField_msgEnd_of_type_ComMaddoxRtsMessage.busy()) && (Actor.isValid(this.jdField_actor_of_type_ComMaddoxIl2EngineActor))) {
        this.jdField_msgEnd_of_type_ComMaddoxRtsMessage.setListener(this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        this.jdField_msgEnd_of_type_ComMaddoxRtsMessage.setTime(timeCurrent());
        this.jdField_msgEnd_of_type_ComMaddoxRtsMessage.setSender(this);
        this.jdField_msgEnd_of_type_ComMaddoxRtsMessage.post();
      }
      this.jdField_msgEnd_of_type_ComMaddoxRtsMessage = null;
    }
  }

  protected long timeCurrent() {
    return this.jdField_actor_of_type_ComMaddoxIl2EngineActor.isRealTime() ? Time.currentReal() : Time.current();
  }

  public boolean tick() {
    long l = timeCurrent();
    if (l < this.time0) l = this.time0;
    double d = (l - this.time0) / this.move.time;

    if (this.countMoves != 0.0D) {
      if ((this.countMoves > 0.0D) && (d >= this.countMoves)) {
        d = this.countMoves;
        stop(true);
      }
      while (d > 1.0D) {
        d -= 1.0D;
        this.time0 += this.move.time;
        if (this.countMoves > 0.0D)
          this.countMoves -= 1.0D;
        this.move.fullStep(this, this.loc0, this.speed);
      }
    }
    else {
      if (d * this.lenStep >= this.lenToEnd) {
        d = this.lenToEnd / this.lenStep;
        stop(true);
      }
      while (d > 1.0D) {
        d -= 1.0D;
        this.time0 += this.move.time;
        this.lenToEnd -= this.lenStep;
        this.move.fullStep(this, this.loc0, this.speed);
      }
    }
    this.move.step(this, this.loc0, getLandHook(), this.speed, d);
    return true;
  }

  public Animator(AnimatedActor paramAnimatedActor, Animates paramAnimates)
  {
    this(paramAnimatedActor, paramAnimates, "AnimatorMoves");
  }

  public Animator(AnimatedActor paramAnimatedActor, Animates paramAnimates, String paramString)
  {
    if (paramAnimatedActor.getAnimator() != null)
      return;
    this.moves = paramAnimates;
    ((Actor)paramAnimatedActor).interpPut(this, paramString, -1L, null);
  }
}