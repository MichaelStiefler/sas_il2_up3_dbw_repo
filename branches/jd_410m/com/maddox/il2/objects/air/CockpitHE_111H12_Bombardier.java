package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class CockpitHE_111H12_Bombardier extends CockpitHE_111H6_Bombardier
{
  public CockpitHE_111H12_Bombardier()
  {
    super("3DO/Cockpit/He-111H-6-Bombardier/hier_H12.him");
  }

  static
  {
    Property.set(CockpitHE_111H12_Bombardier.class, "astatePilotIndx", 0);
  }
}