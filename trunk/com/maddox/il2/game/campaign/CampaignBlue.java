package com.maddox.il2.game.campaign;

import com.maddox.rts.ObjIO;

public class CampaignBlue extends Campaign
{
  private static Class _cls = CampaignBlue.class;

  protected int rankStep() {
    return 3000;
  }
  public int army() { return 2;
  }

  static
  {
    ObjIO.fieldsAllSuperclasses(_cls);
  }
}