// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   KI_21_I.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            KI_21, PaintSchemeBMPar00, PaintSchemeBCSPar01, TypeBomber, 
//            NetAircraft

public class KI_21_I extends com.maddox.il2.objects.air.KI_21
    implements com.maddox.il2.objects.air.TypeBomber
{

    public KI_21_I()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL10_D0", 0.0F, com.maddox.il2.objects.air.KI_21_I.cvt(f, 0.05F, 0.75F, 0.0F, -38F), 0.0F);
        hiermesh.chunkSetAngles("GearL11_D0", 0.0F, 0.0F, com.maddox.il2.objects.air.KI_21_I.cvt(f, 0.05F, 0.75F, 0.0F, -45F));
        hiermesh.chunkSetAngles("GearL13_D0", 0.0F, com.maddox.il2.objects.air.KI_21_I.cvt(f, 0.05F, 0.75F, 0.0F, -157F), 0.0F);
        hiermesh.chunkSetAngles("GearR10_D0", 0.0F, com.maddox.il2.objects.air.KI_21_I.cvt(f, 0.34F, 0.99F, 0.0F, -38F), 0.0F);
        hiermesh.chunkSetAngles("GearR11_D0", 0.0F, 0.0F, com.maddox.il2.objects.air.KI_21_I.cvt(f, 0.05F, 0.75F, 0.0F, -45F));
        hiermesh.chunkSetAngles("GearR13_D0", 0.0F, com.maddox.il2.objects.air.KI_21_I.cvt(f, 0.34F, 0.99F, 0.0F, -157F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.KI_21_I.moveGear(hierMesh(), f);
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(f < -35F)
            {
                f = -35F;
                flag = false;
            }
            if(f > 35F)
            {
                f = 35F;
                flag = false;
            }
            if(f1 < -25F)
            {
                f1 = -25F;
                flag = false;
            }
            if(f1 > 30F)
            {
                f1 = 30F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -45F)
            {
                f = -45F;
                flag = false;
            }
            if(f > 45F)
            {
                f = 45F;
                flag = false;
            }
            if(f1 < -5F)
            {
                f1 = -5F;
                flag = false;
            }
            if(f1 > 60F)
            {
                f1 = 60F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -5F)
            {
                f = -5F;
                flag = false;
            }
            if(f > 50F)
            {
                f = 50F;
                flag = false;
            }
            if(f1 < -35F)
            {
                f1 = -35F;
                flag = false;
            }
            if(f1 > 0.0F)
            {
                f1 = 0.0F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ki-21");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ki-21-I(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/Ki-21-I(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeBCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1937F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ki-21-I.fmd");
        com.maddox.il2.objects.air.KI_21_I.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 3, 3, 3, 3, 3, 3, 3, 
            3, 3
        });
        com.maddox.il2.objects.air.KI_21_I.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", 
            "_BombSpawn08", "_BombSpawn09"
        });
        com.maddox.il2.objects.air.KI_21_I.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.KI_21_I.weaponsRegister(class1, "20x15", new java.lang.String[] {
            "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 3", 
            "BombGun15kgJ 3", "BombGun15kgJ 2"
        });
        com.maddox.il2.objects.air.KI_21_I.weaponsRegister(class1, "14x50", new java.lang.String[] {
            "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 2", "BombGun50kgJ 2", "BombGun50kgJ 2", 
            "BombGun50kgJ 2", "BombGun50kgJ 2"
        });
        com.maddox.il2.objects.air.KI_21_I.weaponsRegister(class1, "6x100", new java.lang.String[] {
            "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", null, 
            null, null
        });
        com.maddox.il2.objects.air.KI_21_I.weaponsRegister(class1, "3x250", new java.lang.String[] {
            "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun250kgJ", null, null, null, "BombGun250kgJ", "BombGun250kgJ", null, 
            null, null
        });
        com.maddox.il2.objects.air.KI_21_I.weaponsRegister(class1, "1x500", new java.lang.String[] {
            "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", null, "BombGun500kgJ", null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.KI_21_I.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}
