package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.A6M;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.B5N;
import com.maddox.il2.objects.air.BF_109;
import com.maddox.il2.objects.air.BF_110;
import com.maddox.il2.objects.air.B_17;
import com.maddox.il2.objects.air.B_24;
import com.maddox.il2.objects.air.B_25;
import com.maddox.il2.objects.air.B_29;
import com.maddox.il2.objects.air.D3A;
import com.maddox.il2.objects.air.FW_190;
import com.maddox.il2.objects.air.G4M;
import com.maddox.il2.objects.air.H8K;
import com.maddox.il2.objects.air.HE_111H2;
import com.maddox.il2.objects.air.Hurricane;
import com.maddox.il2.objects.air.IL_2;
import com.maddox.il2.objects.air.JU_88;
import com.maddox.il2.objects.air.KI_43;
import com.maddox.il2.objects.air.KI_46;
import com.maddox.il2.objects.air.KI_61;
import com.maddox.il2.objects.air.KI_84;
import com.maddox.il2.objects.air.ME_323;
import com.maddox.il2.objects.air.N1K;
import com.maddox.il2.objects.air.PE_2;
import com.maddox.il2.objects.air.PE_8;
import com.maddox.il2.objects.air.P_38;
import com.maddox.il2.objects.air.P_47;
import com.maddox.il2.objects.air.P_51;
import com.maddox.il2.objects.air.SBD;
import com.maddox.il2.objects.air.SPITFIRE;
import com.maddox.il2.objects.air.Scheme1;
import com.maddox.il2.objects.air.TA_152H1;
import com.maddox.il2.objects.air.TBF;
import com.maddox.il2.objects.air.TB_3;
import com.maddox.il2.objects.air.TEMPEST;
import com.maddox.il2.objects.air.TU_2;
import com.maddox.il2.objects.air.YAK;

public class Formation
{
  public static final byte F_DEFAULT = 0;
  public static final byte F_PREVIOUS = 1;
  public static final byte F_ECHELONRIGHT = 2;
  public static final byte F_ECHELONLEFT = 3;
  public static final byte F_LINEABREAST = 4;
  public static final byte F_LINEASTERN = 5;
  public static final byte F_VIC = 6;
  public static final byte F_FINGERFOUR = 7;
  public static final byte F_DIAMOND = 8;
  public static final byte F_LINE = 9;
  private static final Vector3f WR = new Vector3f(100.0F, 100.0F, 0.0F);

  private static final Vector3d dd = new Vector3d();
  private static final Point3d Pd = new Point3d();

  public static final void generate(Aircraft[] paramArrayOfAircraft)
  {
    gen(paramArrayOfAircraft, WR);
  }

  private static final float scaleCoeff(Aircraft paramAircraft)
  {
    if ((paramAircraft instanceof ME_323)) return 5.0F;
    if (((paramAircraft instanceof PE_8)) || ((paramAircraft instanceof TB_3)) || ((paramAircraft instanceof B_17)) || ((paramAircraft instanceof B_29))) return 3.5F;
    if ((paramAircraft instanceof TA_152H1)) return 2.0F;
    if ((paramAircraft instanceof SBD)) return 1.8F;
    if ((paramAircraft instanceof TBF)) return 1.9F;
    if ((paramAircraft instanceof FW_190)) return 1.4F;
    if (!(paramAircraft instanceof Scheme1)) return 2.2F;
    return 1.2F;
  }

  public static final void gather(FlightModel paramFlightModel, byte paramByte) {
    gather(paramFlightModel, paramByte, paramFlightModel.Offset);
  }

  public static final void gather(FlightModel paramFlightModel, byte paramByte, Vector3d paramVector3d)
  {
    gather(paramFlightModel, paramByte, paramVector3d, Mission.curYear());
  }

  public static final void gather(FlightModel paramFlightModel, byte paramByte, Vector3d paramVector3d, int paramInt)
  {
    Aircraft localAircraft = (Aircraft)paramFlightModel.actor;
    FlightModel localFlightModel = paramFlightModel;
    int i = ((Maneuver)paramFlightModel).Group.numInGroup(localAircraft);
    float f = paramFlightModel.formationScale;
    switch (paramByte) {
    case 0:
      if (((localAircraft instanceof BF_109)) || ((localAircraft instanceof BF_110)) || ((localAircraft instanceof FW_190)) || (((localAircraft instanceof SPITFIRE)) && (paramInt > 1941)) || (((localAircraft instanceof Hurricane)) && (paramInt > 1941)) || ((localAircraft instanceof P_38)) || ((localAircraft instanceof P_47)) || ((localAircraft instanceof P_51)) || ((localAircraft instanceof TEMPEST)))
      {
        gather(paramFlightModel, 7, paramVector3d);
        return;
      }
      if (((localAircraft instanceof IL_2)) || ((localAircraft instanceof YAK)) || ((localAircraft instanceof PE_2)) || ((localAircraft instanceof TU_2)))
      {
        gather(paramFlightModel, 6, paramVector3d);
        return;
      }
      if (((localAircraft instanceof A6M)) || ((localAircraft instanceof B5N)) || ((localAircraft instanceof D3A)) || ((localAircraft instanceof G4M)) || ((localAircraft instanceof KI_43)) || ((localAircraft instanceof KI_46)) || ((localAircraft instanceof KI_84)) || ((localAircraft instanceof N1K)) || ((localAircraft instanceof H8K)) || ((localAircraft instanceof KI_61)))
      {
        gather(paramFlightModel, 6, paramVector3d);
        return;
      }
      if (((localAircraft instanceof JU_88)) || ((localAircraft instanceof HE_111H2))) {
        gather(paramFlightModel, 8, paramVector3d);
        return;
      }
      if ((localAircraft instanceof ME_323)) {
        gather(paramFlightModel, 8, paramVector3d);
        return;
      }
      if (((localAircraft instanceof PE_8)) || ((localAircraft instanceof B_17)) || ((localAircraft instanceof B_25)) || ((localAircraft instanceof B_29))) {
        gather(paramFlightModel, 6, paramVector3d);
        return;
      }

      gather(paramFlightModel, 2, paramVector3d);
      return;
    case 1:
      gather(paramFlightModel, paramFlightModel.formationType, paramVector3d);
      return;
    case 2:
      paramFlightModel.formationType = paramByte;
      paramVector3d.set(25.0D, 25.0D, 0.0D);
      break;
    case 3:
      paramFlightModel.formationType = paramByte;
      paramVector3d.set(25.0D, -25.0D, 0.0D);
      break;
    case 4:
      paramFlightModel.formationType = paramByte;
      if (i == 0)
        paramVector3d.set(25.0D, 75.0D, 0.0D);
      else {
        paramVector3d.set(1.0D, 33.0D, 0.0D);
      }
      break;
    case 5:
      paramFlightModel.formationType = paramByte;
      if (i == 0)
        paramVector3d.set(120.0D, 0.0D, 15.0D);
      else {
        paramVector3d.set(80.0D, 0.0D, 10.0D);
      }
      paramVector3d.scale(f);
      return;
    case 6:
      paramFlightModel.formationType = paramByte;
      switch (i) {
      case 0:
        paramVector3d.set(55.0D, 55.0D, 0.0D);
        break;
      case 1:
        paramVector3d.set(25.0D, 25.0D, 0.0D);
        break;
      case 2:
        paramVector3d.set(0.0D, -50.0D, 0.0D);
        break;
      case 3:
        paramVector3d.set(25.0D, -25.0D, 0.0D);
      }

      break;
    case 7:
      paramFlightModel.formationType = paramByte;
      switch (i) {
      case 0:
        paramVector3d.set(25.0D, 25.0D, 0.0D);
        break;
      case 1:
        paramVector3d.set(15.0D, 30.0D, 0.0D);
        break;
      case 2:
        paramVector3d.set(25.0D, -60.0D, 0.0D);
        break;
      case 3:
        paramVector3d.set(15.0D, -20.0D, 0.0D);
      }

      break;
    case 8:
      paramFlightModel.formationType = paramByte;
      switch (i) {
      case 0:
        paramVector3d.set(75.0D, 30.0D, 0.0D);
        break;
      case 1:
        paramVector3d.set(25.0D, 25.0D, 0.0D);
        break;
      case 2:
        paramVector3d.set(0.0D, -50.0D, 0.0D);
        break;
      case 3:
        paramVector3d.set(25.0D, 25.0D, 0.0D);
      }

      break;
    case 9:
      paramFlightModel.formationType = paramByte;
      if (i == 0)
        paramVector3d.set(120.0D, 0.0D, 0.0D);
      else {
        paramVector3d.set(80.0D, 0.0D, 0.0D);
      }
      paramVector3d.scale(f);
      return;
    }

    paramVector3d.scale(f * scaleCoeff(localAircraft));
  }

  public static final void leaderOffset(FlightModel paramFlightModel, byte paramByte, Vector3d paramVector3d)
  {
    Aircraft localAircraft = (Aircraft)paramFlightModel.actor;
    Wing localWing = (Wing)paramFlightModel.actor.getOwner();
    int i;
    if (localWing != null) i = localWing.indexInSquadron(); else i = 0;
    if (((localAircraft instanceof B_17)) || ((localAircraft instanceof B_24)) || ((localAircraft instanceof B_29))) {
      switch (i) {
      case 0:
        paramVector3d.set(300.0D, -150.0D, 0.0D);
        break;
      case 1:
        paramVector3d.set(100.0D, -80.0D, -30.0D);
        break;
      case 2:
        paramVector3d.set(100.0D, 80.0D, 25.0D);
        break;
      case 3:
        paramVector3d.set(200.0D, 0.0D, 50.0D);
      }
    }
    else {
      switch (i) {
      case 0:
        paramVector3d.set(300.0D, -150.0D, 0.0D);
        break;
      case 1:
        if (paramByte != 2)
          paramVector3d.set(100.0D, 100.0D, 0.0D);
        else
          paramVector3d.set(200.0D, 200.0D, 0.0D);
        break;
      case 2:
        if ((paramByte != 3) && (paramByte != 6))
          paramVector3d.set(150.0D, -150.0D, 0.0D);
        else
          paramVector3d.set(210.0D, -210.0D, 0.0D);
        break;
      case 3:
        if (paramByte != 5)
          paramVector3d.set(150.0D, 0.0D, 0.0D);
        else {
          paramVector3d.set(300.0D, 0.0D, 0.0D);
        }
      }
    }
    paramVector3d.scale(0.7D * scaleCoeff(localAircraft));
  }

  private static final void gen(Aircraft[] paramArrayOfAircraft, Vector3f paramVector3f)
  {
    dd.set(paramVector3f);
    paramArrayOfAircraft[0].pos.getAbsOrient().transform(dd);
    int j = 0;
    for (int i = 1; i < paramArrayOfAircraft.length; i++)
      if (Actor.isValid(paramArrayOfAircraft[i])) {
        paramArrayOfAircraft[i].FM.Offset.set(paramVector3f);

        paramArrayOfAircraft[i].FM.Leader = paramArrayOfAircraft[j].FM;
        paramArrayOfAircraft[j].FM.Wingman = paramArrayOfAircraft[i].FM;
        paramArrayOfAircraft[j].pos.getAbs(Pd);
        Pd.sub(dd);
        paramArrayOfAircraft[i].pos.setAbs(Pd);
        j = i;
      }
  }
}