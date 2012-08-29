package com.maddox.rts;

public class States
  implements MessageProxy, Destroy
{
  private int indxState;
  private int indxPrevState;
  private Object[] states;
  private Object state;

  public int getState()
  {
    return this.indxState;
  }

  public int getPrevState()
  {
    return this.indxPrevState;
  }

  public Object getListener()
  {
    return this.state;
  }

  public boolean isDestroyed()
  {
    return this.indxState == -2;
  }

  public void destroy() {
    for (int i = 0; i < this.states.length; i++)
      if (this.states[i] != null) {
        ((State)this.states[i]).setParentListener(null);
        this.states[i] = null;
      }
    this.states = null;
    this.state = null;
  }

  public Object getListener(Message paramMessage)
  {
    return this.state;
  }

  public Object getListener(int paramInt)
  {
    if (paramInt < 0)
      return null;
    return this.states[paramInt];
  }

  public void setListener(int paramInt, Object paramObject)
  {
    if (paramInt >= 0)
      if (paramInt == this.indxState) {
        int i = this.indxPrevState;
        setState(-1);
        this.states[paramInt] = paramObject;
        setState(paramInt);
        this.indxPrevState = i;
      } else {
        this.states[paramInt] = paramObject;
      }
  }

  public void setState(int paramInt)
  {
    if (this.indxState == -2)
      throw new RTSException("Destroyd object try change state");
    if (this.indxState == paramInt)
      return;
    Object localObject1;
    if (paramInt >= 0)
      localObject1 = this.states[paramInt];
    else {
      localObject1 = null;
    }
    Object localObject2 = getListener();
    if ((localObject2 != null) && ((localObject2 instanceof State)))
      ((State)localObject2).end(paramInt);
    this.indxPrevState = this.indxState;
    this.indxState = paramInt;
    this.state = localObject1;
    if ((this.state != null) && ((this.state instanceof State)))
      ((State)this.state).begin(this.indxPrevState);
  }

  public String getStateName()
  {
    if (this.state != null)
      return this.state.getClass().getName();
    if (this.indxState == -2)
      return "DESTROYED";
    return "UNKNOWN";
  }

  public States(Object[] paramArrayOfObject)
  {
    this.states = paramArrayOfObject;
    this.state = null;
    this.indxState = -1;
    this.indxPrevState = -1;
  }

  public States()
  {
  }
}