package com.maddox.il2.objects.air;

public class SPITFIRE11 extends SPITFIRE9
{

    public SPITFIRE11()
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
        com.maddox.il2.objects.air.SPITFIRE11.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[0], 0.0F, 0.247F, 0.0F, -0.247F);
        hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[1], 0.0F, 0.247F, 0.0F, 0.247F);
        hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    public void update(float f)
    {
        super.update(f);
        float f_0_ = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getControlRadiator();
        if(java.lang.Math.abs(flapps - f_0_) > 0.01F)
        {
            flapps = f_0_;
            hierMesh().chunkSetAngles("Oil1_D0", 0.0F, -20F * f_0_, 0.0F);
            hierMesh().chunkSetAngles("Oil2_D0", 0.0F, -20F * f_0_, 0.0F);
        }
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
    
    static java.lang.Class class$com$maddox$il2$objects$air$SPITFIRE11;
    private float flapps;

    static 
    {
        java.lang.Class var_class = com.maddox.il2.objects.air.SPITFIRE11.class;
        new NetAircraft.SPAWN(var_class);
        com.maddox.rts.Property.set(var_class, "iconFar_shortClassName", "Spit");
        com.maddox.rts.Property.set(var_class, "meshName", "3DO/Plane/SpitfireMkXI(Multi1)/hier.him");
        com.maddox.rts.Property.set(var_class, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(var_class, "yearService", 1942.11F);
        com.maddox.rts.Property.set(var_class, "yearExpired", 1944.12F);
        com.maddox.rts.Property.set(var_class, "FlightModel", "FlightModels/SpitfirePRXI.fmd");
        com.maddox.rts.Property.set(var_class, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitSpitPR.class
        });
        com.maddox.rts.Property.set(var_class, "LOSElevation", 0.5926F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(var_class, new int[] {
            9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(var_class, new java.lang.String[] {
            "_ExternalDev08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "default", new java.lang.String[] {
             null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "1x30dt", new java.lang.String[] {
        	"FuelTankGun_TankSpit30 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "1x60dt", new java.lang.String[] {
            "FuelTankGun_TankSpit60 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "1x90dt", new java.lang.String[] {
            "FuelTankGun_TankSpit90 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "1x170dt", new java.lang.String[] {
            "FuelTankGun_TankSpit170 1"
        });
    }
}