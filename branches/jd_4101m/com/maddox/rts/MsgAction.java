package com.maddox.rts;

public class MsgAction extends Message
{
  public void doAction()
  {
  }

  public void doAction(Object paramObject)
  {
    doAction();
  }

  public MsgAction(int paramInt, long paramLong, Object paramObject)
  {
    post(paramInt, this, paramLong, paramObject);
  }

  public MsgAction(int paramInt, double paramDouble, Object paramObject)
  {
    post(paramInt, this, paramDouble, paramObject);
  }

  public MsgAction(int paramInt, long paramLong)
  {
    post(paramInt, this, paramLong);
  }

  public MsgAction(int paramInt, double paramDouble)
  {
    post(paramInt, this, paramDouble);
  }

  public MsgAction(long paramLong, Object paramObject)
  {
    post(0, this, paramLong, paramObject);
  }

  public MsgAction(double paramDouble, Object paramObject)
  {
    post(0, this, paramDouble, paramObject);
  }

  public MsgAction(boolean paramBoolean, Object paramObject)
  {
    post(paramBoolean ? 64 : 0, this, 0.0D, paramObject);
  }

  public MsgAction(boolean paramBoolean)
  {
    post(paramBoolean ? 64 : 0, this, 0.0D);
  }

  public MsgAction(long paramLong)
  {
    post(0, this, paramLong);
  }

  public MsgAction(double paramDouble)
  {
    post(0, this, paramDouble);
  }

  public MsgAction()
  {
  }

  public boolean invokeListener(Object paramObject)
  {
    doAction(this._sender);
    return true;
  }
}