package com.maddox.il2.objects.air;

public class SEAFIRE15 extends SEAFIRE3
{

    public SEAFIRE15()
    {
        flapps = 0.0F;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.6F, 0.0F, -95F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 1.0F, 0.0F, -95F), 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -75F), 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.09F, 0.0F, -75F), 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.09F, 0.0F, -75F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.SEAFIRE15.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.247F, 0.0F, -0.247F);
        hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.247F, 0.0F, 0.247F);
        hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    public void moveCockpitDoor(float f)
    {
     	resetYPRmodifier();
    	com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.55F);
    	hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    	float f1 = (float)java.lang.Math.sin(com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 3.141593F));
    	hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9F * f1);
    	hierMesh().chunkSetAngles("Head1_D0", 12F * f1, 0.0F, 0.0F);
    	if(com.maddox.il2.engine.Config.isUSE_RENDER())
    		{
    		 if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
    		com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
    		setDoorSnd(f);
    		}
    }

    public void update(float f)
    {
        super.update(f);
        float f1 = FM.EI.engines[0].getControlRadiator();
        if(java.lang.Math.abs(flapps - f1) > 0.01F)
        {
            flapps = f1;
            hierMesh().chunkSetAngles("Oil1_D0", 0.0F, -20F * f1, 0.0F);
            hierMesh().chunkSetAngles("Oil2_D0", 0.0F, -20F * f1, 0.0F);
        }
    }

    static java.lang.Class class$com$maddox$il2$objects$air$SEAFIRE15;
    private float flapps;

    static 
    {
        java.lang.Class var_class = com.maddox.il2.objects.air.SEAFIRE15.class;
        new NetAircraft.SPAWN(var_class);
        com.maddox.rts.Property.set(var_class, "iconFar_shortClassName", "Spit");
        com.maddox.rts.Property.set(var_class, "meshName", "3DO/Plane/SeafireMkXV(Multi1)/hier.him");
        com.maddox.rts.Property.set(var_class, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(var_class, "yearService", 1942.11F);
        com.maddox.rts.Property.set(var_class, "yearExpired", 1944.12F);
        com.maddox.rts.Property.set(var_class, "FlightModel", "FlightModels/SeafireXV.fmd");
        com.maddox.rts.Property.set(var_class, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitSea3.class
        });
        com.maddox.rts.Property.set(var_class, "LOSElevation", 0.5926F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(var_class, new int[] {
                0, 0, 0, 0, 1, 1, 9, 3, 9, 9, 
                3, 3, 9, 9, 9, 9, 2, 2, 2, 2, 
                9
            });
            com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(var_class, new java.lang.String[] {
                "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02", "_ExternalDev03", 
                "_ExternalBomb02", "_ExternalBomb03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", 
                "_ExternalDev08"
            });
        }
}