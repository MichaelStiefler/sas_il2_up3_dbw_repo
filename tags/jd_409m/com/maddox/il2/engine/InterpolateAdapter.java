package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.War;
import com.maddox.rts.Message;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

public final class InterpolateAdapter
  implements MsgTimeOutListener
{
  public static final int TICK_POS = -1000;
  private Actor currentListener;
  private ArrayList stackListeners = new ArrayList();
  private ArrayList curListListeners;
  private int iCur = 0;
  private MsgTimeOut ticker;
  private MsgTimeOut realTicker;
  private ArrayList listeners;
  private ArrayList realListeners;
  private boolean bProcess;
  private boolean bActive;
  private int stepStamp = 0;

  public static void getSphere(AbstractCollection paramAbstractCollection, Point3d paramPoint3d, double paramDouble)
  {
    adapter()._getSphere(paramAbstractCollection, paramPoint3d, paramDouble);
  }

  public static void getFiltered(AbstractCollection paramAbstractCollection, Point3d paramPoint3d, double paramDouble, ActorFilter paramActorFilter)
  {
    adapter()._getFiltered(paramAbstractCollection, paramPoint3d, paramDouble, paramActorFilter);
  }

  public static void getNearestEnemies(Point3d paramPoint3d, double paramDouble, int paramInt, Accumulator paramAccumulator)
  {
    adapter()._getNearestEnemies(paramPoint3d, paramDouble, paramInt, paramAccumulator);
  }

  public static void getNearestEnemiesCyl(Point3d paramPoint3d, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, Accumulator paramAccumulator)
  {
    adapter()._getNearestEnemiesCyl(paramPoint3d, paramDouble1, paramDouble2, paramDouble3, paramInt, paramAccumulator);
  }

  public static InterpolateAdapter adapter()
  {
    return Engine.cur.interpolateAdapter;
  }
  public static int step() {
    return Engine.cur.interpolateAdapter.stepStamp;
  }

  public static boolean isActive()
  {
    return Engine.cur.interpolateAdapter.bActive;
  }

  public static void active(boolean paramBoolean)
  {
    Engine.cur.interpolateAdapter.bActive = paramBoolean;
  }

  public static boolean isProcess()
  {
    return Engine.cur.interpolateAdapter.bProcess;
  }
  public static boolean containsListener(Actor paramActor) {
    return Engine.cur.interpolateAdapter.listeners.contains(paramActor);
  }

  public static void forceInterpolate(Actor paramActor)
  {
    Engine.cur.interpolateAdapter._forceInterpolate(paramActor);
  }
  private void _forceInterpolate(Actor paramActor) {
    if (!this.bProcess) return;
    if ((this.currentListener == paramActor) || (this.stackListeners.contains(paramActor)))
    {
      System.err.println("ERROR: Cycle reference interpolate position");
      int i = 0;
      if (this.currentListener != paramActor)
        i = this.stackListeners.indexOf(paramActor) + 1;
      int j = this.stackListeners.size();
      System.err.println("  " + paramActor);
      for (; i < j; i++)
        System.err.println("  " + this.stackListeners.get(i));
      return;
    }
    if (this.curListListeners.contains(paramActor)) {
      this.stackListeners.add(paramActor);
      paramActor.interpolateTick();
      this.stackListeners.remove(this.stackListeners.size() - 1);
    }
  }

  private void updatePos()
  {
    ArrayList localArrayList = Engine.cur.posChanged;
    int i = localArrayList.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)localArrayList.get(j);
      if (Actor.isValid(localActor))
        localActor.pos.updateCurrent();
    }
    localArrayList.clear();
    Engine.cur.dreamEnv.doChanges();
  }

  public void msgTimeOut(Object paramObject)
  {
    boolean bool = Message.current().isRealTime();
    if (bool) this.realTicker.post(); else {
      this.ticker.post();
    }
    this.curListListeners = (bool ? this.realListeners : this.listeners);
    Engine.processPostDestroyActors();
    GObj.DeleteCppObjects();
    if (this.bActive) {
      this.bProcess = true;

      if (!bool) {
        War.cur().interpolateTick();
      }

      if (!bool) {
        updatePos();
      } else {
        if (Time.isRealOnly())
          updatePos();
        Engine.cur.profile.endFrame();
      }

      this.iCur = 0;

      while (this.iCur < this.curListListeners.size())
      {
        this.currentListener = ((Actor)this.curListListeners.get(this.iCur));
        this.currentListener.interpolateTick();
        this.iCur += 1;
      }
      this.currentListener = null;
      this.bProcess = false;

      if (!bool) {
        Engine.cur.collideEnv.doCollision(Engine.cur.posChanged);
        Engine.cur.collideEnv.doBulletMoveAndCollision();
      }

      Engine.processPostDestroyActors();
      this.stepStamp += 1;
    }
  }

  public void addListener(Actor paramActor) {
    ArrayList localArrayList = paramActor.isRealTime() ? this.realListeners : this.listeners;
    if (!localArrayList.contains(paramActor)) {
      if (!Actor.isValid(paramActor))
        return;
      localArrayList.add(paramActor);
    }
  }

  public void removeListener(Actor paramActor) {
    ArrayList localArrayList = paramActor.isRealTime() ? this.realListeners : this.listeners;
    int i = localArrayList.indexOf(paramActor);
    if (i >= 0) {
      localArrayList.remove(i);
      if ((this.bProcess) && 
        (i <= this.iCur)) this.iCur -= 1;
    }
  }

  public static void forceListener(Actor paramActor)
  {
    Engine.cur.interpolateAdapter._forceListener(paramActor);
  }

  private void _forceListener(Actor paramActor) {
    ArrayList localArrayList = paramActor.isRealTime() ? this.realListeners : this.listeners;
    int i = localArrayList.indexOf(paramActor);
    if (i >= 0) {
      localArrayList.remove(i);
      localArrayList.add(0, paramActor);
    }
  }

  public List listeners() {
    return this.listeners; } 
  public List realListeners() { return this.realListeners; }

  private void clearDestroyedListeners(List paramList) {
    int i = paramList.size();
    for (int j = 0; j < paramList.size(); j++) {
      Actor localActor = (Actor)paramList.get(j);
      if (!Actor.isValid(localActor))
        paramList.remove(j);
    }
  }

  protected void resetGameClear() {
    ArrayList localArrayList = new ArrayList(this.realListeners);
    Engine.destroyListGameActors(localArrayList);
    localArrayList.addAll(this.listeners);
    Engine.destroyListGameActors(localArrayList);

    clearDestroyedListeners(this.listeners);
    clearDestroyedListeners(this.realListeners);
  }

  protected void resetGameCreate() {
    this.ticker.post();
  }

  protected InterpolateAdapter() {
    this.ticker = new MsgTimeOut(null);
    this.ticker.setTickPos(-1000);
    this.ticker.setNotCleanAfterSend();
    this.ticker.setFlags(8);
    this.ticker.setListener(this);
    this.ticker.post();
    this.realTicker = new MsgTimeOut(null);
    this.realTicker.setTickPos(-1000);
    this.realTicker.setNotCleanAfterSend();
    this.realTicker.setFlags(72);
    this.realTicker.setListener(this);
    this.realTicker.post();
    this.listeners = new ArrayList();
    this.realListeners = new ArrayList();
    this.bProcess = false;
    this.bActive = true;
  }

  private void _getSphere(AbstractCollection paramAbstractCollection, Point3d paramPoint3d, double paramDouble)
  {
    double d1 = paramDouble * paramDouble;
    int i = this.listeners.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)this.listeners.get(j);
      if (localActor.pos != null) {
        Point3d localPoint3d = localActor.pos.getAbsPoint();
        double d2 = (paramPoint3d.jdField_x_of_type_Double - localPoint3d.jdField_x_of_type_Double) * (paramPoint3d.jdField_x_of_type_Double - localPoint3d.jdField_x_of_type_Double) + (paramPoint3d.jdField_y_of_type_Double - localPoint3d.jdField_y_of_type_Double) * (paramPoint3d.jdField_y_of_type_Double - localPoint3d.jdField_y_of_type_Double) + (paramPoint3d.jdField_z_of_type_Double - localPoint3d.jdField_z_of_type_Double) * (paramPoint3d.jdField_z_of_type_Double - localPoint3d.jdField_z_of_type_Double);

        if (d2 <= d1)
          paramAbstractCollection.add(localActor);
      }
    }
  }

  private void _getFiltered(AbstractCollection paramAbstractCollection, Point3d paramPoint3d, double paramDouble, ActorFilter paramActorFilter)
  {
    double d1 = paramDouble * paramDouble;
    int i = this.listeners.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)this.listeners.get(j);
      if (localActor.pos != null) {
        Point3d localPoint3d = localActor.pos.getAbsPoint();
        double d2 = (paramPoint3d.jdField_x_of_type_Double - localPoint3d.jdField_x_of_type_Double) * (paramPoint3d.jdField_x_of_type_Double - localPoint3d.jdField_x_of_type_Double) + (paramPoint3d.jdField_y_of_type_Double - localPoint3d.jdField_y_of_type_Double) * (paramPoint3d.jdField_y_of_type_Double - localPoint3d.jdField_y_of_type_Double) + (paramPoint3d.jdField_z_of_type_Double - localPoint3d.jdField_z_of_type_Double) * (paramPoint3d.jdField_z_of_type_Double - localPoint3d.jdField_z_of_type_Double);

        if ((d2 > d1) || 
          (!paramActorFilter.isUse(localActor, d2)) || 
          (paramAbstractCollection == null)) continue;
        paramAbstractCollection.add(localActor);
      }
    }
  }

  private void _getNearestEnemies(Point3d paramPoint3d, double paramDouble, int paramInt, Accumulator paramAccumulator)
  {
    double d1 = paramDouble * paramDouble;
    int i = this.listeners.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)this.listeners.get(j);
      if (localActor.pos != null) {
        int k = localActor.getArmy();
        if ((k != 0) && (k != paramInt)) {
          Point3d localPoint3d = localActor.pos.getAbsPoint();
          double d2 = (paramPoint3d.jdField_x_of_type_Double - localPoint3d.jdField_x_of_type_Double) * (paramPoint3d.jdField_x_of_type_Double - localPoint3d.jdField_x_of_type_Double) + (paramPoint3d.jdField_y_of_type_Double - localPoint3d.jdField_y_of_type_Double) * (paramPoint3d.jdField_y_of_type_Double - localPoint3d.jdField_y_of_type_Double) + (paramPoint3d.jdField_z_of_type_Double - localPoint3d.jdField_z_of_type_Double) * (paramPoint3d.jdField_z_of_type_Double - localPoint3d.jdField_z_of_type_Double);

          if (d2 <= d1)
            paramAccumulator.add(localActor, d2);
        }
      }
    }
  }

  private void _getNearestEnemiesCyl(Point3d paramPoint3d, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, Accumulator paramAccumulator)
  {
    double d1 = paramDouble1 * paramDouble1;
    int i = this.listeners.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)this.listeners.get(j);
      if (localActor.pos != null) {
        int k = localActor.getArmy();
        if ((k != 0) && (k != paramInt)) {
          Point3d localPoint3d = localActor.pos.getAbsPoint();
          double d2 = (paramPoint3d.jdField_x_of_type_Double - localPoint3d.jdField_x_of_type_Double) * (paramPoint3d.jdField_x_of_type_Double - localPoint3d.jdField_x_of_type_Double) + (paramPoint3d.jdField_y_of_type_Double - localPoint3d.jdField_y_of_type_Double) * (paramPoint3d.jdField_y_of_type_Double - localPoint3d.jdField_y_of_type_Double);

          if (d2 <= d1) {
            double d3 = localPoint3d.jdField_z_of_type_Double - paramPoint3d.jdField_z_of_type_Double;
            if ((d3 <= paramDouble3) && (d3 >= paramDouble2))
              paramAccumulator.add(localActor, d2 + d3 * d3);
          }
        }
      }
    }
  }
}