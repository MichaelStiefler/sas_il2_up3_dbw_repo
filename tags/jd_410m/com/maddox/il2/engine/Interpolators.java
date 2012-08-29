package com.maddox.il2.engine;

import com.maddox.rts.Message;
import com.maddox.rts.Time;

public final class Interpolators
{
  private long timeSleep;
  private ArrayInterpolators interp;
  private int stepStamp = -1;
  private boolean bForceRef = false;

  private boolean bDoTick = false;

  private void checkFlagForceRef() {
    this.bForceRef = false;
    int i = this.interp.size();
    for (int j = 0; j < i; j++)
      if ((this.interp.get(j) instanceof InterpolateRef)) {
        this.bForceRef = true;
        break;
      }
  }

  public boolean isSleep()
  {
    return this.timeSleep != 0L;
  }

  public boolean sleep()
  {
    if (isSleep())
      return false;
    int i = this.interp.modCount();
    int j = this.interp.size();
    for (int k = 0; k < j; k++) {
      Interpolate localInterpolate = (Interpolate)this.interp.get(k);
      localInterpolate.sleep();
      if (i != this.interp.modCount())
        throw new ActorException("Interpolators changed in 'sleep'");
    }
    this.timeSleep = Time.current();
    return true;
  }

  public boolean wakeup()
  {
    if (!isSleep())
      return false;
    int i = this.interp.modCount();
    int j = this.interp.size();
    for (int k = 0; k < j; k++) {
      Interpolate localInterpolate = (Interpolate)this.interp.get(k);
      localInterpolate.wakeup(this.timeSleep);
      if (i != this.interp.modCount())
        throw new ActorException("Interpolators changed in 'wakeup'");
    }
    this.timeSleep = 0L;
    return true;
  }

  public Interpolate get(Object paramObject)
  {
    int i = this.interp.size();
    for (int j = 0; j < i; j++) {
      Interpolate localInterpolate = (Interpolate)this.interp.get(j);
      if ((localInterpolate.id == paramObject) || ((paramObject != null) && (paramObject.equals(localInterpolate.id))))
        return localInterpolate;
    }
    return null;
  }

  public boolean end(Object paramObject)
  {
    int i = this.interp.size();
    for (int j = 0; j < i; j++) {
      Interpolate localInterpolate = (Interpolate)this.interp.get(j);
      if ((localInterpolate.id == paramObject) || ((paramObject != null) && (paramObject.equals(localInterpolate.id)))) {
        this.interp.remove(j);
        if (localInterpolate.bExecuted)
          localInterpolate.end();
        interplateClean(localInterpolate);
        checkFlagForceRef();
        return true;
      }
    }
    return false;
  }

  public boolean end(Interpolate paramInterpolate)
  {
    int i = this.interp.indexOf(paramInterpolate);
    if (i >= 0) {
      this.interp.remove(i);
      if (paramInterpolate.bExecuted)
        paramInterpolate.end();
      interplateClean(paramInterpolate);
      checkFlagForceRef();
      return true;
    }
    return false;
  }

  public void endAll()
  {
    while (this.interp.size() > 0) {
      Interpolate localInterpolate = (Interpolate)this.interp.get(0);
      this.interp.remove(0);
      if (localInterpolate.bExecuted)
        localInterpolate.end();
      interplateClean(localInterpolate);
    }
    checkFlagForceRef();
  }

  public boolean cancel(Object paramObject)
  {
    int i = this.interp.size();
    for (int j = 0; j < i; j++) {
      Interpolate localInterpolate = (Interpolate)this.interp.get(j);
      if ((localInterpolate.id == paramObject) || ((paramObject != null) && (paramObject.equals(localInterpolate.id)))) {
        this.interp.remove(j);
        if (localInterpolate.bExecuted)
          localInterpolate.cancel();
        interplateClean(localInterpolate);
        checkFlagForceRef();
        return true;
      }
    }
    return false;
  }

  public boolean cancel(Interpolate paramInterpolate)
  {
    int i = this.interp.indexOf(paramInterpolate);
    if (i >= 0) {
      this.interp.remove(i);
      if (paramInterpolate.bExecuted)
        paramInterpolate.cancel();
      interplateClean(paramInterpolate);
      checkFlagForceRef();
      return true;
    }
    return false;
  }

  public void cancelAll()
  {
    while (this.interp.size() > 0) {
      Interpolate localInterpolate = (Interpolate)this.interp.get(0);
      this.interp.remove(0);
      if (localInterpolate.bExecuted)
        localInterpolate.cancel();
      interplateClean(localInterpolate);
    }
    checkFlagForceRef();
  }

  public void put(Interpolate paramInterpolate, Object paramObject, long paramLong, Message paramMessage, Actor paramActor)
  {
    if ((paramObject != null) && (get(paramObject) != null)) {
      throw new ActorException("Interpolator: '" + paramObject + "' alredy exist");
    }
    paramInterpolate.actor = paramActor;
    paramInterpolate.timeBegin = paramLong;
    paramInterpolate.id = paramObject;
    paramInterpolate.msgEnd = paramMessage;
    paramInterpolate.bExecuted = false;
    this.interp.add(paramInterpolate);
    checkFlagForceRef();
  }

  public int size()
  {
    return this.interp.size();
  }

  public void tick(long paramLong)
  {
    if (this.timeSleep == 0L) {
      if (this.stepStamp == InterpolateAdapter.step()) return;
      this.bDoTick = true;
      int i = this.interp.size();
      if (this.bForceRef) {
        for (j = 0; j < i; j++) {
          Interpolate localInterpolate1 = (Interpolate)this.interp.get(j);
          if ((localInterpolate1 instanceof InterpolateRef))
            ((InterpolateRef)localInterpolate1).invokeRef();
        }
      }
      int j = this.interp.modCount();
      for (int k = 0; k < i; ) {
        Interpolate localInterpolate2 = (Interpolate)this.interp.get(k);
        if ((!localInterpolate2.bExecuted) && 
          (localInterpolate2.timeBegin != -1L) && (localInterpolate2.timeBegin <= paramLong)) {
          localInterpolate2.bExecuted = true;
          localInterpolate2.begin();
          if (j != this.interp.modCount()) {
            this.bDoTick = false;
            throw new ActorException("Interpolators changed in 'begin'");
          }
        }

        if (localInterpolate2.bExecuted) {
          if (!localInterpolate2.tick()) {
            if (j != this.interp.modCount()) {
              this.bDoTick = false;
              throw new ActorException("Interpolators changed in 'tick'");
            }
            this.interp.remove(k);
            j = this.interp.modCount();
            localInterpolate2.end();
            if (j != this.interp.modCount()) {
              this.bDoTick = false;
              throw new ActorException("Interpolators changed in 'end'");
            }
            interplateClean(localInterpolate2);
            checkFlagForceRef();
            k--;
            i--;
          }
          else if (j != this.interp.modCount()) {
            this.bDoTick = false;
            throw new ActorException("Interpolators changed in 'tick'");
          }
        }

        k++;
      }
      this.stepStamp = InterpolateAdapter.step();
      this.bDoTick = false;
    }
  }

  private void interplateClean(Interpolate paramInterpolate) {
    if (paramInterpolate.actor == null)
      return;
    paramInterpolate.doDestroy();
    paramInterpolate.actor = null;
    paramInterpolate.id = null;
    paramInterpolate.msgEnd = null;
  }

  public void destroy() {
    if (this.bDoTick)
      throw new ActorException("Interpolators destroying in invoked method 'tick' ");
    cancelAll();
    this.interp = null;
  }

  public Interpolators() {
    this.timeSleep = 0L;
    this.interp = new ArrayInterpolators(2);
  }
}