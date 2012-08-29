package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

public abstract interface TypeHasKnobs
{
  public abstract void typeHasKnobsSet(int paramInt, float paramFloat);

  public abstract void typeHasKnobsDoSet(int paramInt, float paramFloat);

  public abstract void typeHasKnobsPlus(int paramInt);

  public abstract void typeHasKnobsMinus(int paramInt);

  public abstract void typeHasKnobsReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException;

  public abstract void typeHasKnobsReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException;
}