package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.PaintScheme;

public class PathAir extends Path
{
  public String typedName = "UNKNOWN";
  public String sRegiment;
  public int iRegiment;
  public int iSquadron = 0;
  public int iWing = 0;
  public boolean bOnlyAI = false;
  public int planes = 1;
  public int fuel = 100;
  public int skill = 1;
  public int[] skills = { 1, 1, 1, 1 };
  public String[] skins = new String[4];
  public String[] noseart = new String[4];
  public String[] pilots = new String[4];
  public boolean[] bNumberOn = { true, true, true, true };
  public String weapons = "default";
  public boolean bParachute = true;
  public int _iType;
  public int _iItem;
  private ResSquadron resSquadron;

  public void computeTimes()
  {
    computeTimes(true);
  }

  public void computeTimes(boolean paramBoolean) {
    int i = points();
    if (i == 0) return;
    Object localObject = (PAir)point(0);

    PlMisAir localPlMisAir = (PlMisAir)Plugin.getPlugin("MisAir");
    for (int j = 1; j < i; j++) {
      PAir localPAir = (PAir)point(j);
      double d1 = localPAir.speed;
      if (localPAir.type() == 2)
        d1 = localPlMisAir.type[this._iType].item[this._iItem].speedRunway;
      if (d1 == 0.0D)
        d1 = localPlMisAir.type[this._iType].item[this._iItem].speedRunway;
      double d2 = ((PAir)localObject).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().distance(localPAir.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
      d1 *= 0.2777777777777778D;
      double d3 = d2 / d1;
      localPAir.jdField_time_of_type_Double = (((PAir)localObject).jdField_time_of_type_Double + d3);
      localObject = localPAir;
    }
    if (paramBoolean)
      Plugin.builder.doUpdateSelector();
  }

  public ResSquadron squadron()
  {
    return this.resSquadron;
  }

  public Regiment regiment() {
    return squadron().regiment();
  }

  public void setName(String paramString) {
    ResSquadron localResSquadron = ResSquadron.New(paramString.substring(0, paramString.length() - 1));
    if (localResSquadron != this.resSquadron) {
      if (this.resSquadron != null)
        this.resSquadron.detach(this);
      this.resSquadron = localResSquadron;
      this.resSquadron.attach(this);
    }
    super.setName(paramString);
    updateTypedName();
  }

  public void updateTypedName() {
    PlMisAir localPlMisAir = (PlMisAir)Plugin.getPlugin("MisAir");
    PaintScheme localPaintScheme = Aircraft.getPropertyPaintScheme(localPlMisAir.type[this._iType].item[this._iItem].clazz, this.resSquadron.regiment().country());

    if (localPaintScheme != null)
      this.typedName = localPaintScheme.typedName(localPlMisAir.type[this._iType].item[this._iItem].clazz, this.resSquadron.regiment(), this.iSquadron, this.iWing, 0);
    else
      this.typedName = "UNKNOWN";
  }

  public void destroy()
  {
    if (Actor.isValid(this.resSquadron))
      this.resSquadron.detach(this);
    super.destroy();
  }

  public PathAir(Pathes paramPathes, int paramInt1, int paramInt2) {
    super(paramPathes);
    this._iType = paramInt1;
    this._iItem = paramInt2;
  }
}