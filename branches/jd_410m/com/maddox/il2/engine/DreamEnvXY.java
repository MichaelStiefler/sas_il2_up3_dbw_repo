package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapXY16Hash;
import com.maddox.util.IntHashtable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class DreamEnvXY extends DreamEnv
{
  private MsgDream msgDream = new MsgDream();

  private DreamEnvXY_IntArray xIndx = new DreamEnvXY_IntArray(32);
  private DreamEnvXY_IntArray yIndx = new DreamEnvXY_IntArray(32);
  private MsgDreamGlobal msgDreamGlobal = new MsgDreamGlobal();

  private Object wakeuped = new Object();
  private HashMapExt listenerChanges = new HashMapExt();
  private HashMapXY16Hash listenerXY = new HashMapXY16Hash(7);
  private HashMapExt fires = new HashMapExt();
  private IntHashtable fireXY = new IntHashtable(21, 0);
  private IntHashtable fireCenterXY = new IntHashtable(21, 0);
  private IntHashtable fireUpdateXY = new IntHashtable(21, 0);
  private int fireSquares;
  private int updateTicks;
  private int updateTickCounter;
  private List globalListener = new ArrayList();
  private List resetGlobalListener = new ArrayList();

  private DreamEnvXY_IntArray squareSleep = new DreamEnvXY_IntArray(32);
  private DreamEnvXY_IntArray squareWakeup = new DreamEnvXY_IntArray(32);

  public boolean isSleep(double paramDouble1, double paramDouble2)
  {
    return isSleep((int)(paramDouble1 / 200.0D), (int)(paramDouble2 / 200.0D));
  }

  public boolean isSleep(Point3d paramPoint3d) {
    return isSleep(paramPoint3d.x, paramPoint3d.y);
  }

  public boolean isSleep(int paramInt1, int paramInt2) {
    return this.fireXY.get(keyXY(paramInt1, paramInt2)) <= 0;
  }

  private void doSquareLocalListeners(int paramInt, int[] paramArrayOfInt, boolean paramBoolean) {
    for (int i = 0; i < paramInt; i++) {
      int j = paramArrayOfInt[i];
      HashMapExt localHashMapExt = this.listenerXY.get(j >> 16, (short)(j & 0xFFFF));
      if (localHashMapExt != null) {
        Map.Entry localEntry = localHashMapExt.nextEntry(null);
        while (localEntry != null) {
          Actor localActor = (Actor)localEntry.getKey();
          if ((Actor.isValid(localActor)) && (!this.listenerChanges.containsKey(localActor)))
            this.msgDream.send(paramBoolean, localActor);
          localEntry = localHashMapExt.nextEntry(localEntry);
        }
      }
    }
  }

  private void makeArrayIndx(DreamEnvXY_IntArray paramDreamEnvXY_IntArray)
  {
    int i = paramDreamEnvXY_IntArray.size();
    int[] arrayOfInt = paramDreamEnvXY_IntArray.array();
    this.xIndx.clear();
    this.yIndx.clear();
    for (int j = 0; j < i; j++) {
      int k = arrayOfInt[j];
      this.xIndx.add((short)(k & 0xFFFF));
      this.yIndx.add(k >> 16);
    }
  }

  protected void doChanges()
  {
    this.updateTickCounter -= 1;
    if (this.updateTickCounter > 0) {
      if (this.globalListener.size() > 0) {
        for (i = 0; i < this.globalListener.size(); i++) {
          Actor localActor1 = (Actor)this.globalListener.get(i);
          if (Actor.isValid(localActor1))
            this.msgDreamGlobal.sendTick(localActor1, this.updateTicks, this.updateTickCounter);
        }
      }
      return;
    }
    this.updateTickCounter = this.updateTicks;

    updateFire();
    int i = this.squareSleep.size() + this.squareWakeup.size();

    if (i > 0) {
      int j = this.squareSleep.size();
      if (j > 0)
        doSquareLocalListeners(j, this.squareSleep.array(), false);
      j = this.squareWakeup.size();
      if (j > 0)
        doSquareLocalListeners(j, this.squareWakeup.array(), true);
    }
    Actor localActor2;
    if (this.listenerChanges.size() > 0) {
      HashMapExt localHashMapExt = this.listenerChanges;
      if (localHashMapExt != null) {
        Map.Entry localEntry = localHashMapExt.nextEntry(null);
        while (localEntry != null) {
          localActor2 = (Actor)localEntry.getKey();
          if (Actor.isValid(localActor2)) {
            int i1 = localEntry.getValue() == this.wakeuped ? 1 : 0;
            int i2 = keyXY(localActor2.pos.getCurrentPoint());
            boolean bool = this.fireXY.get(i2) > 0;
            if (bool != i1)
              this.msgDream.send(bool, localActor2);
          }
          localEntry = localHashMapExt.nextEntry(localEntry);
        }
      }
      this.listenerChanges.clear();
    }
    int m;
    if (i > 0) {
      if (this.globalListener.size() > 0) {
        int k = this.squareSleep.size();
        if (k > 0) {
          makeArrayIndx(this.squareSleep);
          for (m = 0; m < this.globalListener.size(); m++) {
            localActor2 = (Actor)this.globalListener.get(m);
            if (Actor.isValid(localActor2))
              this.msgDreamGlobal.send(localActor2, false, k, this.xIndx.array(), this.yIndx.array());
          }
        }
        k = this.squareWakeup.size();
        if (k > 0) {
          makeArrayIndx(this.squareWakeup);
          for (m = 0; m < this.globalListener.size(); m++) {
            localActor2 = (Actor)this.globalListener.get(m);
            if (Actor.isValid(localActor2))
              this.msgDreamGlobal.send(localActor2, true, k, this.xIndx.array(), this.yIndx.array());
          }
        }
      }
      this.squareSleep.clear();
      this.squareWakeup.clear();
    }

    if (this.resetGlobalListener.size() > 0) {
      int[] arrayOfInt = this.fireXY.keyList();
      for (m = 0; m < arrayOfInt.length; m++) {
        int n = arrayOfInt[m];
        if (n > -2147483647) {
          this.squareWakeup.add(n);
        }
      }
      makeArrayIndx(this.squareWakeup);
      this.squareWakeup.clear();

      for (m = 0; m < this.resetGlobalListener.size(); m++) {
        Actor localActor3 = (Actor)this.resetGlobalListener.get(m);
        if (Actor.isValid(localActor3)) {
          this.msgDreamGlobal.send(localActor3, true, this.xIndx.size(), this.xIndx.array(), this.yIndx.array());
          this.globalListener.add(localActor3);
        }
      }
      this.resetGlobalListener.clear();
      this.xIndx.clear();
      this.yIndx.clear();
    }
  }

  protected void changedListenerPos(Actor paramActor, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    int i = (int)(paramPoint3d1.x / 200.0D);
    int j = (int)(paramPoint3d1.y / 200.0D);
    int k = (int)(paramPoint3d2.x / 200.0D);
    int m = (int)(paramPoint3d2.y / 200.0D);
    if ((i != k) || (j != m)) {
      this.listenerXY.remove(j, i, paramActor);
      this.listenerXY.put(m, k, paramActor, null);
      if (!this.listenerChanges.containsKey(paramActor)) {
        int n = keyXY(i, j);
        if (this.fireXY.get(n) > 0)
          this.listenerChanges.put(paramActor, this.wakeuped);
        else
          this.listenerChanges.put(paramActor, null);
      }
    }
  }

  protected void addListener(Actor paramActor) {
    Point3d localPoint3d = paramActor.pos.getCurrentPoint();
    int i = (int)(localPoint3d.x / 200.0D);
    int j = (int)(localPoint3d.y / 200.0D);
    this.listenerXY.put(j, i, paramActor, null);
    this.listenerChanges.put(paramActor, null);
  }

  protected void removeListener(Actor paramActor) {
    Point3d localPoint3d = paramActor.pos.getCurrentPoint();
    int i = (int)(localPoint3d.x / 200.0D);
    int j = (int)(localPoint3d.y / 200.0D);
    this.listenerXY.remove(j, i, paramActor);
    this.listenerChanges.remove(paramActor);
  }

  protected void addGlobalListener(Actor paramActor)
  {
    this.resetGlobalListener.add(paramActor);
  }

  protected void removeGlobalListener(Actor paramActor) {
    int i = this.globalListener.indexOf(paramActor);
    if (i >= 0) this.globalListener.remove(i);
    i = this.resetGlobalListener.indexOf(paramActor);
    if (i >= 0) this.resetGlobalListener.remove(i); 
  }

  public void resetGlobalListener(Actor paramActor)
  {
    removeGlobalListener(paramActor);
    addGlobalListener(paramActor);
  }

  private void updateFire() {
    if (!this.fireCenterXY.isEmpty()) {
      int[] arrayOfInt1 = this.fireCenterXY.keyList();
      int[] arrayOfInt2 = this.fireCenterXY.values();
      int k;
      int m;
      int n;
      int i1;
      for (int i = 0; i < arrayOfInt1.length; i++) {
        int j = arrayOfInt1[i];
        if (j > -2147483647) {
          k = arrayOfInt2[i];
          if (k != 0) {
            m = (j >> 16) - this.fireSquares / 2;
            n = (short)(j & 0xFFFF) - this.fireSquares / 2;
            for (i1 = 0; i1 < this.fireSquares; i1++) {
              for (int i2 = 0; i2 < this.fireSquares; i2++) {
                int i3 = keyXY(i2 + n, i1 + m);
                int i4 = this.fireUpdateXY.getIndex(i3);
                if (i4 >= 0)
                  this.fireUpdateXY.setByIndex(i4, this.fireUpdateXY.getByIndex(i4) + k);
                else {
                  this.fireUpdateXY.put(i3, this.fireXY.get(i3) + k);
                }
              }
            }
          }
        }
      }
      this.fireCenterXY.clear();

      if (!this.fireUpdateXY.isEmpty()) {
        int[] arrayOfInt3 = this.fireUpdateXY.keyList();
        int[] arrayOfInt4 = this.fireUpdateXY.values();
        for (k = 0; k < arrayOfInt3.length; k++) {
          m = arrayOfInt3[k];
          if (m > -2147483647) {
            n = arrayOfInt4[k];
            i1 = this.fireXY.getIndex(m);
            if (i1 >= 0) {
              if (n == 0)
              {
                this.fireXY.removeByIndex(i1);
                this.fireXY.validate();

                this.squareSleep.add(m);
              } else {
                this.fireXY.setByIndex(i1, n);
              }
            }
            else if (n > 0) {
              this.fireXY.put(m, n);

              this.squareWakeup.add(m);
            }
          }
        }

        this.fireUpdateXY.clear();
      }
    }
  }

  protected void changedFirePos(Actor paramActor, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    int i = keyXY(paramPoint3d2);
    if (!this.fires.containsKey(paramActor)) {
      this.fires.put(paramActor, null);
      this.fireCenterXY.put(i, this.fireCenterXY.get(i) + 1);
      return;
    }
    int j = keyXY(paramPoint3d1);
    if (j == i) return;
    this.fireCenterXY.put(j, this.fireCenterXY.get(j) - 1);
    this.fireCenterXY.put(i, this.fireCenterXY.get(i) + 1);
  }

  protected void addFire(Actor paramActor) {
    if (this.fires.containsKey(paramActor)) return;
    this.fires.put(paramActor, null);
    int i = keyXY(paramActor.pos.getCurrentPoint());
    int j = this.fireCenterXY.get(i) + 1;
    this.fireCenterXY.put(i, j);
    if (j == 1)
      this.updateTickCounter = 0;
  }

  protected void removeFire(Actor paramActor) {
    if (!this.fires.containsKey(paramActor)) return;
    this.fires.remove(paramActor);
    int i = keyXY(paramActor.pos.getCurrentPoint());
    int j = this.fireCenterXY.get(i) - 1;
    this.fireCenterXY.put(i, j);
    if (j == 0)
      this.updateTickCounter = 0;
  }

  public void resetGameClear() {
    ArrayList localArrayList1 = new ArrayList(this.fires.keySet());
    Engine.destroyListGameActors(localArrayList1);
    localArrayList1.addAll(this.globalListener);
    Engine.destroyListGameActors(localArrayList1);
    localArrayList1.addAll(this.resetGlobalListener);
    Engine.destroyListGameActors(localArrayList1);
    this.resetGlobalListener.addAll(this.globalListener);
    this.globalListener.clear();

    ArrayList localArrayList2 = new ArrayList();
    this.listenerXY.allValues(localArrayList2);
    for (int i = 0; i < localArrayList2.size(); i++) {
      HashMapExt localHashMapExt = (HashMapExt)localArrayList2.get(i);
      localArrayList1.addAll(localHashMapExt.keySet());
      Engine.destroyListGameActors(localArrayList1);
    }
    localArrayList2.clear();
  }
  public void resetGameCreate() {
    this.listenerChanges.clear();
    clearFire();
  }

  protected void clearFire() {
    this.fires.clear();
    this.fireXY.clear();
    this.fireCenterXY.clear();
    this.updateTickCounter = 0;
  }

  private int keyXY(Point3d paramPoint3d) {
    return (int)(paramPoint3d.x / 200.0D) & 0xFFFF | (int)(paramPoint3d.y / 200.0D) << 16;
  }
  private int keyXY(int paramInt1, int paramInt2) {
    return paramInt1 & 0xFFFF | paramInt2 << 16;
  }

  public DreamEnvXY() {
    this.fireSquares = 38;
    if (this.fireSquares < 3) this.fireSquares = 3;
    this.fireSquares |= 1;
    this.updateTicks = (int)(1.0D / Time.tickConstLenFs());
    if (this.updateTicks < 1) this.updateTicks = 1;
    this.updateTickCounter = 0;
  }
}