package com.maddox.il2.objects.vehicles.radios;

public class BlindLandingData
{
  public boolean isOnOuterMarker = false;
  public boolean isOnInnerMarker = false;
  public float blindLandingAzimuthPB = 0.0F;
  public float blindLandingAzimuthBP = 0.0F;
  public float blindLandingRange = 0.0F;
  public float signalStrength = 0.0F;
  private final float markerFanLength = 85.0F;
  public float runwayLength = 1700.0F;

  public void reset()
  {
    this.isOnOuterMarker = false;
    this.isOnInnerMarker = false;
    this.blindLandingAzimuthPB = 0.0F;
    this.blindLandingAzimuthBP = 0.0F;
    this.blindLandingRange = 50000.0F;
    this.signalStrength = 0.0F;
  }

  public void addSignal(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.blindLandingAzimuthPB = paramFloat2;
    this.blindLandingAzimuthBP = paramFloat1;
    this.blindLandingRange = paramFloat3;
    this.signalStrength = paramFloat4;
    if (paramBoolean)
    {
      this.runwayLength = 1700.0F;
      if (paramFloat6 > 500.0F)
      {
        this.runwayLength = 2300.0F;
      }
    }
    else
    {
      this.isOnInnerMarker = false;
      this.isOnOuterMarker = false;
      this.runwayLength = 0.0F;
      return;
    }

    if ((Math.abs(this.blindLandingAzimuthBP) < 10.0F) && (paramFloat3 > paramFloat6 - 85.0F + this.runwayLength) && (paramFloat3 < paramFloat6 + 85.0F + this.runwayLength))
    {
      this.isOnInnerMarker = true;
    }
    else {
      this.isOnInnerMarker = false;
    }
    if ((Math.abs(this.blindLandingAzimuthBP) < 10.0F) && (paramFloat3 > paramFloat5 - 85.0F + this.runwayLength) && (paramFloat3 < paramFloat5 + 85.0F + this.runwayLength))
    {
      this.isOnOuterMarker = true;
    }
    else
      this.isOnOuterMarker = false;
  }
}