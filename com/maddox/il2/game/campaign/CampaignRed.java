package com.maddox.il2.game.campaign;

import com.maddox.rts.ObjIO;

public class CampaignRed extends Campaign
{
  private static Class _cls = CampaignRed.class;

  protected int rankStep() {
    return 2000;
  }
  public int army() { return 1;
  }

  static
  {
    ObjIO.fieldsAllSuperclasses(_cls);
  }
}