package com.maddox.il2.objects.vehicles.cars;

import com.maddox.il2.ai.ground.TgtVehicle;

public abstract class Car
{
  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try
    {
      localClass = Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
    return localClass;
  }

  static
  {
    new CarGeneric.SPAWN(Car.BikeBMW.class);
    new CarGeneric.SPAWN(Car.OpelKadett.class);
    new CarGeneric.SPAWN(Car.VW82.class);
    new CarGeneric.SPAWN(Car.VW82t.class);
    new CarGeneric.SPAWN(Car.Kurogane.class);
    new CarGeneric.SPAWN(Car.HoHa.class);
    new CarGeneric.SPAWN(Car.Truck_Type94.class);
    new CarGeneric.SPAWN(Car.OpelBlitz36S.class);
    new CarGeneric.SPAWN(Car.OpelBlitz6700A.class);
    new CarGeneric.SPAWN(Car.OpelBlitz6700A_medic.class);
    new CarGeneric.SPAWN(Car.OpelBlitz6700A_radio.class);
    new CarGeneric.SPAWN(Car.OpelBlitz6700A_fuel.class);
    new CarGeneric.SPAWN(Car.Kettenkrad.class);
    new CarGeneric.SPAWN(Car.RSO.class);
    new CarGeneric.SPAWN(Car.OpelBlitzMaultier.class);
    new CarGeneric.SPAWN(Car.DemagD7.class);
    new CarGeneric.SPAWN(Car.SdKfz6.class);
    new CarGeneric.SPAWN(Car.Bicycle.class);
    new CarGeneric.SPAWN(Car.Motorcycle.class);
    new CarGeneric.SPAWN(Car.GAZ_M1.class);
    new CarGeneric.SPAWN(Car.GAZ67.class);
    new CarGeneric.SPAWN(Car.GAZ67t.class);
    new CarGeneric.SPAWN(Car.WillisMB.class);
    new CarGeneric.SPAWN(Car.WillisMB_US.class);
    new CarGeneric.SPAWN(Car.WillisMBt.class);
    new CarGeneric.SPAWN(Car.WillisMBt_US.class);
    new CarGeneric.SPAWN(Car.WillisMBtc_US.class);
    new CarGeneric.SPAWN(Car.StudebeckerTruck.class);
    new CarGeneric.SPAWN(Car.ZIS5_PC.class);
    new CarGeneric.SPAWN(Car.ZIS5_medic.class);
    new CarGeneric.SPAWN(Car.ZIS5_radio.class);
    new CarGeneric.SPAWN(Car.ZIS6_fuel.class);
    new CarGeneric.SPAWN(Car.Chevrolet_flatbed_US.class);
    new CarGeneric.SPAWN(Car.Chevrolet_medic_US.class);
    new CarGeneric.SPAWN(Car.Chevrolet_radio_US.class);
    new CarGeneric.SPAWN(Car.DiamondT_US.class);
    new CarGeneric.SPAWN(Car.M3_Halftrack.class);
    new CarGeneric.SPAWN(Car.M3_Halftrack_ppl.class);
    new CarGeneric.SPAWN(Car.Tractor_US.class);
    new CarGeneric.SPAWN(Car.DUKW.class);
    new CarGeneric.SPAWN(Car.Bulldozer_US.class);
    new CarGeneric.SPAWN(Car.Bus_US.class);
    new CarGeneric.SPAWN(Car.GAZ_55.class);
    new CarGeneric.SPAWN(Car.GAZ_Bus.class);
    new CarGeneric.SPAWN(Car.GAZ_AA.class);
    new CarGeneric.SPAWN(Car.GAZ_AAA.class);
    new CarGeneric.SPAWN(Car.Katyusha.class);
    new CarGeneric.SPAWN(Car.StudebeckerRocket.class);
    new CarGeneric.SPAWN(Bus_NAAFI.class);
    new CarGeneric.SPAWN(Camion_SK.class);
    new CarGeneric.SPAWN(RAF_Bus.class);
    new CarGeneric.SPAWN(US_Bus.class);
    new CarGeneric.SPAWN(Citerne_POMP.class);
    new CarGeneric.SPAWN(Fourgon_POMP.class);
    new CarGeneric.SPAWN(GuyOtterBl.class);
    new CarGeneric.SPAWN(GuyOtterBr.class);
    new CarGeneric.SPAWN(Voit2P_Beige.class);
    new CarGeneric.SPAWN(Voit4P_Bleue.class);
    new CarGeneric.SPAWN(Voit4P_Noire_R.class);
  }

  public static class Voit4P_Noire_R extends CarGeneric
    implements TgtVehicle
  {
  }

  public static class Voit4P_Bleue extends CarGeneric
    implements TgtVehicle
  {
  }

  public static class Voit2P_Beige extends CarGeneric
    implements TgtVehicle
  {
  }

  public static class GuyOtterBr extends CarGeneric
    implements TgtVehicle
  {
  }

  public static class GuyOtterBl extends CarGeneric
    implements TgtVehicle
  {
  }

  public static class Fourgon_POMP extends CarGeneric
    implements TgtVehicle
  {
  }

  public static class Citerne_POMP extends CarGeneric
    implements TgtVehicle
  {
  }

  public static class US_Bus extends CarGeneric
    implements TgtVehicle
  {
  }

  public static class Bus_NAAFI extends CarGeneric
    implements TgtVehicle
  {
  }

  public static class RAF_Bus extends CarGeneric
    implements TgtVehicle
  {
  }

  public static class Camion_SK extends CarGeneric
    implements TgtVehicle
  {
  }
}