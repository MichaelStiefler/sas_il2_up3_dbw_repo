// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AircraftHotKeys.java

package com.maddox.il2.game;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.hotkey.HookGunner;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIChatDialog;
import com.maddox.il2.gui.GUIPad;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.ActorViewPoint;
import com.maddox.il2.objects.air.A6M;
import com.maddox.il2.objects.air.A6M5C;
import com.maddox.il2.objects.air.A6M7_62;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.il2.objects.air.CockpitGunner;
import com.maddox.il2.objects.air.CockpitPilot;
import com.maddox.il2.objects.air.DO_335;
import com.maddox.il2.objects.air.Hurricane;
import com.maddox.il2.objects.air.MOSQUITO;
import com.maddox.il2.objects.air.P_51;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.il2.objects.air.SPITFIRE;
import com.maddox.il2.objects.air.TEMPEST;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeDockable;
import com.maddox.il2.objects.air.TypeFighterAceMaker;
import com.maddox.il2.objects.air.TypeX4Carrier;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyCmdMouseMove;
import com.maddox.rts.HotKeyCmdMove;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Joy;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import com.maddox.sound.AudioDevice;
import com.maddox.sound.CfgNpFlags;
import com.maddox.sound.RadioChannel;
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package com.maddox.il2.game:
//            HUD, Main3D, GameTrack, Mission, 
//            Selector, Main, GameState, TimeSkip

public class AircraftHotKeys
{
    class HotKeyCmdFireMove extends com.maddox.rts.HotKeyCmdMove
    {

        public void begin()
        {
            byte byte0 = ((byte)(name().charAt(0) != '-' ? 1 : -1));
            doCmdPilotMove(cmd, com.maddox.rts.Joy.normal(byte0 * move()));
        }

        public boolean isDisableIfTimePaused()
        {
            return true;
        }

        int cmd;

        public HotKeyCmdFireMove(java.lang.String s, java.lang.String s1, int i, int j)
        {
            super(true, s1, s);
            cmd = i;
            setRecordId(j);
        }
    }

    class HotKeyCmdFire extends com.maddox.rts.HotKeyCmd
    {

        public void begin()
        {
            doCmdPilot(cmd, true);
            time = com.maddox.rts.Time.tick();
        }

        public void tick()
        {
            if(com.maddox.rts.Time.tick() > time + 500L)
                doCmdPilotTick(cmd);
        }

        public boolean isTickInTime(boolean flag)
        {
            return !flag;
        }

        public void end()
        {
            doCmdPilot(cmd, false);
        }

        public boolean isDisableIfTimePaused()
        {
            return true;
        }

        int cmd;
        long time;

        public HotKeyCmdFire(java.lang.String s, java.lang.String s1, int i, int j)
        {
            super(true, s1, s);
            cmd = i;
            setRecordId(j);
        }
    }


    public boolean isAfterburner()
    {
        if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()))
            bAfterburner = false;
        return bAfterburner;
    }

    public void setAfterburner(boolean flag)
    {
        if(FM.EI.isSelectionHasControlAfterburner())
        {
            bAfterburner = flag;
            if(bAfterburner)
            {
                if((FM.actor instanceof com.maddox.il2.objects.air.Hurricane) || (FM.actor instanceof com.maddox.il2.objects.air.A6M) && !(FM.actor instanceof com.maddox.il2.objects.air.A6M7_62) && !(FM.actor instanceof com.maddox.il2.objects.air.A6M5C) || (FM.actor instanceof com.maddox.il2.objects.air.P_51) || (FM.actor instanceof com.maddox.il2.objects.air.SPITFIRE) || (FM.actor instanceof com.maddox.il2.objects.air.MOSQUITO) || (FM.actor instanceof com.maddox.il2.objects.air.TEMPEST))
                    com.maddox.il2.game.HUD.logRightBottom("BoostWepTP0");
                else
                    com.maddox.il2.game.HUD.logRightBottom("BoostWepTP" + FM.EI.getFirstSelected().getAfterburnerType());
            } else
            {
                com.maddox.il2.game.HUD.logRightBottom(null);
            }
        }
        FM.CT.setAfterburnerControl(bAfterburner);
    }

    public void setAfterburnerForAutoActivation(boolean flag)
    {
        bAfterburner = flag;
    }

    public boolean isPropAuto()
    {
        if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()))
            bPropAuto = false;
        return bPropAuto;
    }

    public void setPropAuto(boolean flag)
    {
        if(flag && !FM.EI.isSelectionAllowsAutoProp())
        {
            return;
        } else
        {
            bPropAuto = flag;
            return;
        }
    }

    public void resetGame()
    {
        FM = null;
        bAfterburner = false;
        bPropAuto = true;
        lastPower = -0.5F;
        lastProp = 1.5F;
    }

    public void resetUser()
    {
        resetGame();
    }

    private boolean setPilot()
    {
        FM = null;
        if(!com.maddox.il2.engine.Actor.isAlive(com.maddox.il2.ai.World.getPlayerAircraft()))
            return false;
        if(com.maddox.il2.ai.World.isPlayerParatrooper())
            return false;
        if(com.maddox.il2.ai.World.isPlayerDead())
            return false;
        com.maddox.il2.fm.FlightModel flightmodel = com.maddox.il2.ai.World.getPlayerFM();
        if(flightmodel == null)
            return false;
        if(flightmodel instanceof com.maddox.il2.fm.RealFlightModel)
        {
            FM = (com.maddox.il2.fm.RealFlightModel)flightmodel;
            return FM.isRealMode();
        } else
        {
            return false;
        }
    }

    private void setPowerControl(float f)
    {
        if(f < 0.0F)
            f = 0.0F;
        if(f > 1.1F)
            f = 1.1F;
        lastPower = f;
        FM.CT.setPowerControl(f);
        hudPower(FM.CT.PowerControl);
    }

    private void setPropControl(float f)
    {
        if(!com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement)
            return;
        if(f < 0.0F)
            f = 0.0F;
        if(f > 1.0F)
            f = 1.0F;
        lastProp = f;
        if(!FM.EI.isSelectionAllowsAutoProp())
            bPropAuto = false;
        if(!bPropAuto)
        {
            FM.CT.setStepControlAuto(false);
            FM.CT.setStepControl(f);
            com.maddox.il2.game.HUD.log(hudLogPowerId, "PropPitch", new java.lang.Object[] {
                new Integer(java.lang.Math.round(FM.CT.getStepControl() * 100F))
            });
        }
    }

    private void setMixControl(float f)
    {
        if(!com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement)
            return;
        if(f < 0.0F)
            f = 0.0F;
        if(f > 1.2F)
            f = 1.2F;
        if(FM.EI.getFirstSelected() != null)
        {
            FM.EI.setMix(f);
            f = FM.EI.getFirstSelected().getControlMix();
            FM.CT.setMixControl(f);
            com.maddox.il2.game.HUD.log(hudLogPowerId, "PropMix", new java.lang.Object[] {
                new Integer(java.lang.Math.round(FM.CT.getMixControl() * 100F))
            });
        }
    }

    private void hudPower(float f)
    {
        com.maddox.il2.game.HUD.log(hudLogPowerId, "Power", new java.lang.Object[] {
            new Integer(java.lang.Math.round(f * 100F))
        });
    }

    private void hudWeapon(boolean flag, int i)
    {
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[i];
        if(abulletemitter == null)
            return;
        for(int j = 0; j < abulletemitter.length; j++)
        {
            if(abulletemitter[j] == null || !abulletemitter[j].haveBullets())
                continue;
            flag1 = true;
            break;
        }

        if(!flag)
        {
            com.maddox.il2.objects.effects.ForceFeedback.fxTriggerShake(i, false);
            return;
        }
        if(flag1)
            com.maddox.il2.objects.effects.ForceFeedback.fxTriggerShake(i, true);
        else
            com.maddox.il2.game.HUD.log(hudLogWeaponId, "OutOfAmmo");
    }

    private void doCmdPilot(int i, boolean flag)
    {
        if(!setPilot())
            return;
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)FM.actor;
        switch(i)
        {
        case 16: // '\020'
            FM.CT.WeaponControl[0] = flag;
            hudWeapon(flag, 0);
            break;

        case 17: // '\021'
            FM.CT.WeaponControl[1] = flag;
            hudWeapon(flag, 1);
            break;

        case 18: // '\022'
            FM.CT.WeaponControl[2] = flag;
            hudWeapon(flag, 2);
            break;

        case 19: // '\023'
            FM.CT.WeaponControl[3] = flag;
            hudWeapon(flag, 3);
            break;

        case 64: // '@'
            FM.CT.WeaponControl[0] = flag;
            hudWeapon(flag, 0);
            FM.CT.WeaponControl[1] = flag;
            hudWeapon(flag, 1);
            break;
        }
        if(!flag)
        {
            switch(i)
            {
            default:
                break;

            case 71: // 'G'
                if((aircraft instanceof com.maddox.il2.objects.air.TypeBomber) || (aircraft instanceof com.maddox.il2.objects.air.DO_335))
                {
                    FM.CT.StabilizerControl = !FM.CT.StabilizerControl;
                    com.maddox.il2.game.HUD.log("Stabilizer" + (FM.CT.StabilizerControl ? "On" : "Off"));
                }
                return;

            case 15: // '\017'
                if(!aircraft.isGunPodsExist())
                    return;
                if(aircraft.isGunPodsOn())
                {
                    aircraft.setGunPodsOn(false);
                    com.maddox.il2.game.HUD.log("GunPodsOff");
                } else
                {
                    aircraft.setGunPodsOn(true);
                    com.maddox.il2.game.HUD.log("GunPodsOn");
                }
                return;

            case 1: // '\001'
            case 2: // '\002'
                if(!FM.CT.StabilizerControl)
                    FM.CT.RudderControl = 0.0F;
                break;

            case 0: // '\0'
                FM.CT.BrakeControl = 0.0F;
                break;

            case 3: // '\003'
            case 4: // '\004'
                if(!FM.CT.StabilizerControl)
                    FM.CT.ElevatorControl = 0.0F;
                break;

            case 5: // '\005'
            case 6: // '\006'
                if(!FM.CT.StabilizerControl)
                    FM.CT.AileronControl = 0.0F;
                break;

            case 54: // '6'
                if(FM.Gears.onGround() || FM.CT.GearControl <= 0.0F || !FM.Gears.isOperable() || FM.Gears.isHydroOperable())
                    break;
                FM.CT.GearControl -= 0.02F;
                if(FM.CT.GearControl <= 0.0F)
                {
                    FM.CT.GearControl = 0.0F;
                    com.maddox.il2.game.HUD.log("GearUp");
                }
                break;

            case 55: // '7'
                if(FM.Gears.onGround() || FM.CT.GearControl >= 1.0F || !FM.Gears.isOperable() || FM.Gears.isHydroOperable())
                    break;
                FM.CT.GearControl += 0.02F;
                if(FM.CT.GearControl >= 1.0F)
                {
                    FM.CT.GearControl = 1.0F;
                    com.maddox.il2.game.HUD.log("GearDown");
                }
                break;

            case 63: // '?'
                if(FM.CT.bHasAirBrakeControl)
                {
                    FM.CT.AirBrakeControl = FM.CT.AirBrakeControl <= 0.5F ? 1.0F : 0.0F;
                    com.maddox.il2.game.HUD.log("Divebrake" + (FM.CT.AirBrakeControl != 0.0F ? "ON" : "OFF"));
                }
                break;

            case 70: // 'F'
                if(com.maddox.il2.ai.World.cur().diffCur.SeparateEStart && FM.EI.getNumSelected() > 1 && FM.EI.getFirstSelected().getStage() == 0)
                    return;
                FM.EI.toggle();
                break;

            case 126: // '~'
                if(!(FM.actor instanceof com.maddox.il2.objects.air.TypeDockable))
                    break;
                if(((com.maddox.il2.objects.air.TypeDockable)FM.actor).typeDockableIsDocked())
                    ((com.maddox.il2.objects.air.TypeDockable)FM.actor).typeDockableAttemptDetach();
                else
                    ((com.maddox.il2.objects.air.TypeDockable)FM.actor).typeDockableAttemptAttach();
                break;
            }
            return;
        }
        switch(i)
        {
        case 8: // '\b'
        case 10: // '\n'
        case 11: // '\013'
        case 12: // '\f'
        case 13: // '\r'
        case 14: // '\016'
        case 15: // '\017'
        case 16: // '\020'
        case 17: // '\021'
        case 18: // '\022'
        case 19: // '\023'
        case 54: // '6'
        case 55: // '7'
        case 63: // '?'
        case 64: // '@'
        case 65: // 'A'
        case 66: // 'B'
        case 67: // 'C'
        case 68: // 'D'
        case 69: // 'E'
        case 70: // 'F'
        case 71: // 'G'
        case 100: // 'd'
        case 101: // 'e'
        case 111: // 'o'
        case 112: // 'p'
        case 126: // '~'
        default:
            break;

        case 7: // '\007'
            if(!FM.EI.isSelectionHasControlRadiator())
                break;
            if(FM.CT.getRadiatorControlAuto())
            {
                FM.CT.setRadiatorControlAuto(false, FM.EI);
                if(com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement)
                {
                    FM.CT.setRadiatorControl(0.0F);
                    com.maddox.il2.game.HUD.log("RadiatorControl" + (int)(FM.CT.getRadiatorControl() * 10F));
                } else
                {
                    FM.CT.setRadiatorControl(1.0F);
                    com.maddox.il2.game.HUD.log("RadiatorON");
                }
                break;
            }
            if(com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement)
            {
                if(FM.CT.getRadiatorControl() == 1.0F)
                {
                    if(FM.EI.isSelectionAllowsAutoRadiator())
                    {
                        FM.CT.setRadiatorControlAuto(true, FM.EI);
                        com.maddox.il2.game.HUD.log("RadiatorOFF");
                    } else
                    {
                        FM.CT.setRadiatorControl(0.0F);
                        com.maddox.il2.game.HUD.log("RadiatorControl" + (int)(FM.CT.getRadiatorControl() * 10F));
                    }
                    break;
                }
                if(FM.actor instanceof com.maddox.il2.objects.air.MOSQUITO)
                    FM.CT.setRadiatorControl(1.0F);
                else
                    FM.CT.setRadiatorControl(FM.CT.getRadiatorControl() + 0.2F);
                com.maddox.il2.game.HUD.log("RadiatorControl" + (int)(FM.CT.getRadiatorControl() * 10F));
            } else
            {
                FM.CT.setRadiatorControlAuto(true, FM.EI);
                com.maddox.il2.game.HUD.log("RadiatorOFF");
            }
            break;

        case 0: // '\0'
            FM.CT.BrakeControl = 1.0F;
            break;

        case 3: // '\003'
            if(!FM.CT.StabilizerControl)
                FM.CT.ElevatorControl = -1F;
            break;

        case 4: // '\004'
            if(!FM.CT.StabilizerControl)
                FM.CT.ElevatorControl = 1.0F;
            break;

        case 5: // '\005'
            if(!FM.CT.StabilizerControl)
                FM.CT.AileronControl = -1F;
            break;

        case 6: // '\006'
            if(!FM.CT.StabilizerControl)
                FM.CT.AileronControl = 1.0F;
            break;

        case 72: // 'H'
            if(FM.CT.bHasLockGearControl)
            {
                FM.Gears.bTailwheelLocked = !FM.Gears.bTailwheelLocked;
                com.maddox.il2.game.HUD.log("TailwheelLock" + (FM.Gears.bTailwheelLocked ? "ON" : "OFF"));
            }
            break;

        case 1: // '\001'
            if(!FM.CT.StabilizerControl)
                FM.CT.RudderControl = -1F;
            break;

        case 56: // '8'
            if(!FM.CT.StabilizerControl && FM.CT.RudderControl > -1F)
                FM.CT.RudderControl -= 0.1F;
            break;

        case 57: // '9'
            if(!FM.CT.StabilizerControl)
                FM.CT.RudderControl = 0.0F;
            break;

        case 2: // '\002'
            if(!FM.CT.StabilizerControl)
                FM.CT.RudderControl = 1.0F;
            break;

        case 58: // ':'
            if(!FM.CT.StabilizerControl && FM.CT.RudderControl < 1.0F)
                FM.CT.RudderControl += 0.1F;
            break;

        case 20: // '\024'
            setPowerControl(0.0F);
            break;

        case 21: // '\025'
            setPowerControl(0.1F);
            break;

        case 22: // '\026'
            setPowerControl(0.2F);
            break;

        case 23: // '\027'
            setPowerControl(0.3F);
            break;

        case 24: // '\030'
            setPowerControl(0.4F);
            break;

        case 25: // '\031'
            setPowerControl(0.5F);
            break;

        case 26: // '\032'
            setPowerControl(0.6F);
            break;

        case 27: // '\033'
            setPowerControl(0.7F);
            break;

        case 28: // '\034'
            setPowerControl(0.8F);
            break;

        case 29: // '\035'
            setPowerControl(0.9F);
            break;

        case 30: // '\036'
            setPowerControl(1.0F);
            break;

        case 59: // ';'
            setPowerControl(FM.CT.PowerControl + 0.05F);
            break;

        case 60: // '<'
            setPowerControl(FM.CT.PowerControl - 0.05F);
            break;

        case 61: // '='
            setAfterburner(!bAfterburner);
            break;

        case 31: // '\037'
            if(FM.EI.isSelectionHasControlProp())
                setPropControl(0.0F);
            break;

        case 32: // ' '
            if(FM.EI.isSelectionHasControlProp())
                setPropControl(0.1F);
            break;

        case 33: // '!'
            if(FM.EI.isSelectionHasControlProp())
                setPropControl(0.2F);
            break;

        case 34: // '"'
            if(FM.EI.isSelectionHasControlProp())
                setPropControl(0.3F);
            break;

        case 35: // '#'
            if(FM.EI.isSelectionHasControlProp())
                setPropControl(0.4F);
            break;

        case 36: // '$'
            if(FM.EI.isSelectionHasControlProp())
                setPropControl(0.5F);
            break;

        case 37: // '%'
            if(FM.EI.isSelectionHasControlProp())
                setPropControl(0.6F);
            break;

        case 38: // '&'
            if(FM.EI.isSelectionHasControlProp())
                setPropControl(0.7F);
            break;

        case 39: // '\''
            if(FM.EI.isSelectionHasControlProp())
                setPropControl(0.8F);
            break;

        case 40: // '('
            if(FM.EI.isSelectionHasControlProp())
                setPropControl(0.9F);
            break;

        case 41: // ')'
            if(FM.EI.isSelectionHasControlProp())
                setPropControl(1.0F);
            break;

        case 73: // 'I'
            if(FM.EI.isSelectionHasControlProp())
                setPropControl(lastProp + 0.05F);
            break;

        case 74: // 'J'
            if(FM.EI.isSelectionHasControlProp())
                setPropControl(lastProp - 0.05F);
            break;

        case 42: // '*'
            if(!FM.EI.isSelectionHasControlProp() || !com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement)
                break;
            setPropAuto(!bPropAuto);
            if(bPropAuto)
            {
                com.maddox.il2.game.HUD.log("PropAutoPitch");
                lastProp = FM.CT.getStepControl();
                FM.CT.setStepControlAuto(true);
            } else
            {
                FM.CT.setStepControlAuto(false);
                setPropControl(lastProp);
            }
            break;

        case 114: // 'r'
            if(FM.EI.isSelectionHasControlFeather() && com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement && FM.EI.getFirstSelected() != null)
                FM.EI.setFeather(FM.EI.getFirstSelected().getControlFeather() != 0 ? 0 : 1);
            break;

        case 75: // 'K'
            if(FM.EI.isSelectionHasControlMix())
                setMixControl(0.0F);
            break;

        case 76: // 'L'
            if(FM.EI.isSelectionHasControlMix())
                setMixControl(0.1F);
            break;

        case 77: // 'M'
            if(FM.EI.isSelectionHasControlMix())
                setMixControl(0.2F);
            break;

        case 78: // 'N'
            if(FM.EI.isSelectionHasControlMix())
                setMixControl(0.3F);
            break;

        case 79: // 'O'
            if(FM.EI.isSelectionHasControlMix())
                setMixControl(0.4F);
            break;

        case 80: // 'P'
            if(FM.EI.isSelectionHasControlMix())
                setMixControl(0.5F);
            break;

        case 81: // 'Q'
            if(FM.EI.isSelectionHasControlMix())
                setMixControl(0.6F);
            break;

        case 82: // 'R'
            if(FM.EI.isSelectionHasControlMix())
                setMixControl(0.7F);
            break;

        case 83: // 'S'
            if(FM.EI.isSelectionHasControlMix())
                setMixControl(0.8F);
            break;

        case 84: // 'T'
            if(FM.EI.isSelectionHasControlMix())
                setMixControl(0.9F);
            break;

        case 85: // 'U'
            if(FM.EI.isSelectionHasControlMix())
                setMixControl(1.0F);
            break;

        case 86: // 'V'
            if(FM.EI.isSelectionHasControlMix())
                setMixControl(FM.CT.getMixControl() + 0.2F);
            break;

        case 87: // 'W'
            if(FM.EI.isSelectionHasControlMix())
                setMixControl(FM.CT.getMixControl() - 0.2F);
            break;

        case 89: // 'Y'
            if(FM.EI.isSelectionHasControlMagnetos() && FM.EI.getFirstSelected() != null && FM.EI.getFirstSelected().getControlMagnetos() > 0)
            {
                FM.CT.setMagnetoControl(FM.EI.getFirstSelected().getControlMagnetos() - 1);
                com.maddox.il2.game.HUD.log("MagnetoSetup" + FM.CT.getMagnetoControl());
            }
            break;

        case 88: // 'X'
            if(FM.EI.isSelectionHasControlMagnetos() && FM.EI.getFirstSelected() != null && FM.EI.getFirstSelected().getControlMagnetos() < 3)
            {
                FM.CT.setMagnetoControl(FM.EI.getFirstSelected().getControlMagnetos() + 1);
                com.maddox.il2.game.HUD.log("MagnetoSetup" + FM.CT.getMagnetoControl());
            }
            break;

        case 116: // 't'
            if(FM.EI.isSelectionHasControlCompressor() && FM.EI.getFirstSelected() != null && com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement)
            {
                FM.CT.setCompressorControl(FM.EI.getFirstSelected().getControlCompressor() - 1);
                com.maddox.il2.game.HUD.log("CompressorSetup" + FM.CT.getCompressorControl());
            }
            break;

        case 115: // 's'
            if(FM.EI.isSelectionHasControlCompressor() && FM.EI.getFirstSelected() != null && com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement)
            {
                FM.CT.setCompressorControl(FM.EI.getFirstSelected().getControlCompressor() + 1);
                com.maddox.il2.game.HUD.log("CompressorSetup" + FM.CT.getCompressorControl());
            }
            break;

        case 90: // 'Z'
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            FM.EI.setCurControlAll(true);
            com.maddox.il2.game.HUD.log("EngineSelectAll");
            break;

        case 91: // '['
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            FM.EI.setCurControlAll(false);
            com.maddox.il2.game.HUD.log("EngineSelectNone");
            break;

        case 92: // '\\'
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            FM.EI.setCurControlAll(false);
            int ai[] = FM.EI.getSublist(FM.Scheme, 1);
            for(int k = 0; k < ai.length; k++)
                FM.EI.setCurControl(ai[k], true);

            com.maddox.il2.game.HUD.log("EngineSelectLeft");
            break;

        case 93: // ']'
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            FM.EI.setCurControlAll(false);
            int ai1[] = FM.EI.getSublist(FM.Scheme, 2);
            for(int l = 0; l < ai1.length; l++)
                FM.EI.setCurControl(ai1[l], true);

            com.maddox.il2.game.HUD.log("EngineSelectRight");
            break;

        case 94: // '^'
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            FM.EI.setCurControlAll(false);
            FM.EI.setCurControl(0, true);
            com.maddox.il2.game.HUD.log("EngineSelect1");
            break;

        case 95: // '_'
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            FM.EI.setCurControlAll(false);
            FM.EI.setCurControl(1, true);
            com.maddox.il2.game.HUD.log("EngineSelect2");
            break;

        case 96: // '`'
            if(FM.Scheme == 0 || FM.Scheme == 1 || FM.EI.getNum() < 3)
                return;
            FM.EI.setCurControlAll(false);
            FM.EI.setCurControl(2, true);
            com.maddox.il2.game.HUD.log("EngineSelect3");
            break;

        case 97: // 'a'
            if(FM.Scheme == 0 || FM.Scheme == 1 || FM.EI.getNum() < 4)
                return;
            FM.EI.setCurControlAll(false);
            FM.EI.setCurControl(3, true);
            com.maddox.il2.game.HUD.log("EngineSelect4");
            break;

        case 98: // 'b'
            if(FM.Scheme == 0 || FM.Scheme == 1 || FM.EI.getNum() < 5)
                return;
            FM.EI.setCurControlAll(false);
            FM.EI.setCurControl(4, true);
            com.maddox.il2.game.HUD.log("EngineSelect5");
            break;

        case 99: // 'c'
            if(FM.Scheme == 0 || FM.Scheme == 1 || FM.EI.getNum() < 6)
                return;
            FM.EI.setCurControlAll(false);
            FM.EI.setCurControl(5, true);
            com.maddox.il2.game.HUD.log("EngineSelect6");
            break;

        case 102: // 'f'
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            for(int j = 0; j < FM.EI.getNum(); j++)
                FM.EI.setCurControl(j, !FM.EI.getCurControl(j));

            com.maddox.il2.game.HUD.log("EngineToggleAll");
            break;

        case 103: // 'g'
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            int ai2[] = FM.EI.getSublist(FM.Scheme, 1);
            for(int j1 = 0; j1 < ai2.length; j1++)
                FM.EI.setCurControl(ai2[j1], !FM.EI.getCurControl(ai2[j1]));

            com.maddox.il2.game.HUD.log("EngineToggleLeft");
            break;

        case 104: // 'h'
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            int ai3[] = FM.EI.getSublist(FM.Scheme, 2);
            for(int k1 = 0; k1 < ai3.length; k1++)
                FM.EI.setCurControl(ai3[k1], !FM.EI.getCurControl(ai3[k1]));

            com.maddox.il2.game.HUD.log("EngineToggleRight");
            break;

        case 105: // 'i'
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            FM.EI.setCurControl(0, !FM.EI.getCurControl(0));
            com.maddox.il2.game.HUD.log("EngineSelect1" + (FM.EI.getCurControl(0) ? "" : "OFF"));
            break;

        case 106: // 'j'
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            FM.EI.setCurControl(1, !FM.EI.getCurControl(1));
            com.maddox.il2.game.HUD.log("EngineSelect2" + (FM.EI.getCurControl(1) ? "" : "OFF"));
            break;

        case 107: // 'k'
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            FM.EI.setCurControl(2, !FM.EI.getCurControl(2));
            com.maddox.il2.game.HUD.log("EngineSelect3" + (FM.EI.getCurControl(2) ? "" : "OFF"));
            break;

        case 108: // 'l'
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            FM.EI.setCurControl(3, !FM.EI.getCurControl(3));
            com.maddox.il2.game.HUD.log("EngineSelect4" + (FM.EI.getCurControl(3) ? "" : "OFF"));
            break;

        case 109: // 'm'
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            FM.EI.setCurControl(4, !FM.EI.getCurControl(4));
            com.maddox.il2.game.HUD.log("EngineSelect5" + (FM.EI.getCurControl(4) ? "" : "OFF"));
            break;

        case 110: // 'n'
            if(FM.Scheme == 0 || FM.Scheme == 1)
                return;
            FM.EI.setCurControl(5, !FM.EI.getCurControl(5));
            com.maddox.il2.game.HUD.log("EngineSelect6" + (FM.EI.getCurControl(5) ? "" : "OFF"));
            break;

        case 113: // 'q'
            if(!FM.EI.isSelectionHasControlExtinguisher())
                break;
            for(int i1 = 0; i1 < FM.EI.getNum(); i1++)
                if(FM.EI.getCurControl(i1))
                    FM.EI.engines[i1].setExtinguisherFire();

            break;

        case 53: // '5'
            if(!FM.CT.bHasFlapsControl)
                break;
            if(!FM.CT.bHasFlapsControlRed)
            {
                if(FM.CT.FlapsControl < 0.2F)
                {
                    FM.CT.FlapsControl = 0.2F;
                    com.maddox.il2.game.HUD.log("FlapsCombat");
                    break;
                }
                if(FM.CT.FlapsControl < 0.33F)
                {
                    FM.CT.FlapsControl = 0.33F;
                    com.maddox.il2.game.HUD.log("FlapsTakeOff");
                    break;
                }
                if(FM.CT.FlapsControl < 1.0F)
                {
                    FM.CT.FlapsControl = 1.0F;
                    com.maddox.il2.game.HUD.log("FlapsLanding");
                }
                break;
            }
            if(FM.CT.FlapsControl < 0.5F)
            {
                FM.CT.FlapsControl = 1.0F;
                com.maddox.il2.game.HUD.log("FlapsLanding");
            }
            break;

        case 52: // '4'
            if(!FM.CT.bHasFlapsControl)
                break;
            if(!FM.CT.bHasFlapsControlRed)
            {
                if(FM.CT.FlapsControl > 0.33F)
                {
                    FM.CT.FlapsControl = 0.33F;
                    com.maddox.il2.game.HUD.log("FlapsTakeOff");
                    break;
                }
                if(FM.CT.FlapsControl > 0.2F)
                {
                    FM.CT.FlapsControl = 0.2F;
                    com.maddox.il2.game.HUD.log("FlapsCombat");
                    break;
                }
                if(FM.CT.FlapsControl > 0.0F)
                {
                    FM.CT.FlapsControl = 0.0F;
                    com.maddox.il2.game.HUD.log("FlapsRaised");
                }
                break;
            }
            if(FM.CT.FlapsControl > 0.5F)
            {
                FM.CT.FlapsControl = 0.0F;
                com.maddox.il2.game.HUD.log("FlapsRaised");
            }
            break;

        case 9: // '\t'
            if(!FM.CT.bHasGearControl || FM.Gears.onGround() || !FM.Gears.isHydroOperable())
                break;
            if(FM.CT.GearControl > 0.5F && FM.CT.getGear() > 0.99F)
            {
                FM.CT.GearControl = 0.0F;
                com.maddox.il2.game.HUD.log("GearUp");
            } else
            if(FM.CT.GearControl < 0.5F && FM.CT.getGear() < 0.01F)
            {
                FM.CT.GearControl = 1.0F;
                com.maddox.il2.game.HUD.log("GearDown");
            }
            if(FM.Gears.isAnyDamaged())
                com.maddox.il2.game.HUD.log("GearDamaged");
            break;

        case 129: 
            if(!FM.CT.bHasArrestorControl)
                break;
            if(FM.CT.arrestorControl > 0.5F)
            {
                FM.AS.setArrestor(FM.actor, 0);
                com.maddox.il2.game.HUD.log("HookUp");
            } else
            {
                FM.AS.setArrestor(FM.actor, 1);
                com.maddox.il2.game.HUD.log("HookDown");
            }
            break;

        case 130: 
            if(!FM.canChangeBrakeShoe)
                break;
            if(FM.brakeShoe)
            {
                FM.brakeShoe = false;
                com.maddox.il2.game.HUD.log("BrakeShoeOff");
            } else
            {
                FM.brakeShoe = true;
                com.maddox.il2.game.HUD.log("BrakeShoeOn");
            }
            break;

        case 127: // '\177'
            if(!FM.CT.bHasWingControl)
                break;
            if(FM.CT.wingControl < 0.5F && FM.CT.getWing() < 0.01F)
            {
                FM.AS.setWingFold(aircraft, 1);
                com.maddox.il2.game.HUD.log("WingFold");
                break;
            }
            if(FM.CT.wingControl > 0.5F && FM.CT.getWing() > 0.99F)
            {
                FM.AS.setWingFold(aircraft, 0);
                com.maddox.il2.game.HUD.log("WingExpand");
            }
            break;

        case 128: 
            if(!FM.CT.bHasCockpitDoorControl)
                break;
            if(FM.CT.cockpitDoorControl < 0.5F && FM.CT.getCockpitDoor() < 0.01F)
            {
                FM.AS.setCockpitDoor(aircraft, 1);
                com.maddox.il2.game.HUD.log("CockpitDoorOPN");
                break;
            }
            if(FM.CT.cockpitDoorControl > 0.5F && FM.CT.getCockpitDoor() > 0.99F)
            {
                FM.AS.setCockpitDoor(aircraft, 0);
                com.maddox.il2.game.HUD.log("CockpitDoorCLS");
            }
            break;

        case 43: // '+'
            if(FM.CT.bHasElevatorTrim)
                FM.CT.setTrimElevatorControl(0.0F);
            break;

        case 44: // ','
            doCmdPilotTick(i);
            break;

        case 45: // '-'
            doCmdPilotTick(i);
            break;

        case 46: // '.'
            if(FM.CT.bHasAileronTrim)
                FM.CT.setTrimAileronControl(0.0F);
            break;

        case 47: // '/'
            doCmdPilotTick(i);
            break;

        case 48: // '0'
            doCmdPilotTick(i);
            break;

        case 49: // '1'
            if(FM.CT.bHasRudderTrim)
                FM.CT.setTrimRudderControl(0.0F);
            break;

        case 50: // '2'
            doCmdPilotTick(i);
            break;

        case 51: // '3'
            doCmdPilotTick(i);
            break;

        case 125: // '}'
            if(aircraft instanceof com.maddox.il2.objects.air.TypeBomber)
            {
                ((com.maddox.il2.objects.air.TypeBomber)aircraft).typeBomberToggleAutomation();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeDiveBomber)
            {
                ((com.maddox.il2.objects.air.TypeDiveBomber)aircraft).typeDiveBomberToggleAutomation();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeFighterAceMaker)
            {
                ((com.maddox.il2.objects.air.TypeFighterAceMaker)aircraft).typeFighterAceMakerToggleAutomation();
                toTrackSign(i);
            }
            break;

        case 117: // 'u'
            doCmdPilotTick(i);
            break;

        case 118: // 'v'
            doCmdPilotTick(i);
            break;

        case 119: // 'w'
            doCmdPilotTick(i);
            break;

        case 120: // 'x'
            doCmdPilotTick(i);
            break;

        case 121: // 'y'
            doCmdPilotTick(i);
            break;

        case 122: // 'z'
            doCmdPilotTick(i);
            break;

        case 123: // '{'
            doCmdPilotTick(i);
            break;

        case 124: // '|'
            doCmdPilotTick(i);
            break;

        case 62: // '>'
            FM.CT.dropFuelTanks();
            break;
        }
    }

    private void doCmdPilotTick(int i)
    {
        if(!setPilot())
            return;
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)FM.actor;
        switch(i)
        {
        default:
            break;

        case 44: // ','
            if(FM.CT.bHasElevatorTrim && FM.CT.getTrimElevatorControl() < 0.5F)
                FM.CT.setTrimElevatorControl(FM.CT.getTrimElevatorControl() + 0.00625F);
            break;

        case 45: // '-'
            if(FM.CT.bHasElevatorTrim && FM.CT.getTrimElevatorControl() > -0.5F)
                FM.CT.setTrimElevatorControl(FM.CT.getTrimElevatorControl() - 0.00625F);
            break;

        case 47: // '/'
            if(FM.CT.bHasAileronTrim && FM.CT.getTrimAileronControl() < 0.5F)
                FM.CT.setTrimAileronControl(FM.CT.getTrimAileronControl() + 0.00625F);
            break;

        case 48: // '0'
            if(FM.CT.bHasAileronTrim && FM.CT.getTrimAileronControl() > -0.5F)
                FM.CT.setTrimAileronControl(FM.CT.getTrimAileronControl() - 0.00625F);
            break;

        case 50: // '2'
            if(FM.CT.bHasRudderTrim && FM.CT.getTrimRudderControl() < 0.5F)
                FM.CT.setTrimRudderControl(FM.CT.getTrimRudderControl() + 0.00625F);
            break;

        case 51: // '3'
            if(FM.CT.bHasRudderTrim && FM.CT.getTrimRudderControl() > -0.5F)
                FM.CT.setTrimRudderControl(FM.CT.getTrimRudderControl() - 0.00625F);
            break;

        case 117: // 'u'
            if(aircraft instanceof com.maddox.il2.objects.air.TypeBomber)
            {
                ((com.maddox.il2.objects.air.TypeBomber)aircraft).typeBomberAdjDistancePlus();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeFighterAceMaker)
            {
                ((com.maddox.il2.objects.air.TypeFighterAceMaker)aircraft).typeFighterAceMakerAdjDistancePlus();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeDiveBomber)
            {
                ((com.maddox.il2.objects.air.TypeDiveBomber)aircraft).typeDiveBomberAdjDiveAnglePlus();
                toTrackSign(i);
            }
            break;

        case 118: // 'v'
            if(aircraft instanceof com.maddox.il2.objects.air.TypeBomber)
            {
                ((com.maddox.il2.objects.air.TypeBomber)aircraft).typeBomberAdjDistanceMinus();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeFighterAceMaker)
            {
                ((com.maddox.il2.objects.air.TypeFighterAceMaker)aircraft).typeFighterAceMakerAdjDistanceMinus();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeDiveBomber)
            {
                ((com.maddox.il2.objects.air.TypeDiveBomber)aircraft).typeDiveBomberAdjDiveAngleMinus();
                toTrackSign(i);
            }
            break;

        case 119: // 'w'
            if(aircraft instanceof com.maddox.il2.objects.air.TypeBomber)
            {
                ((com.maddox.il2.objects.air.TypeBomber)aircraft).typeBomberAdjSideslipPlus();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeFighterAceMaker)
            {
                ((com.maddox.il2.objects.air.TypeFighterAceMaker)aircraft).typeFighterAceMakerAdjSideslipPlus();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeX4Carrier)
            {
                ((com.maddox.il2.objects.air.TypeX4Carrier)aircraft).typeX4CAdjSidePlus();
                toTrackSign(i);
            }
            break;

        case 120: // 'x'
            if(aircraft instanceof com.maddox.il2.objects.air.TypeBomber)
            {
                ((com.maddox.il2.objects.air.TypeBomber)aircraft).typeBomberAdjSideslipMinus();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeFighterAceMaker)
            {
                ((com.maddox.il2.objects.air.TypeFighterAceMaker)aircraft).typeFighterAceMakerAdjSideslipMinus();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeX4Carrier)
            {
                ((com.maddox.il2.objects.air.TypeX4Carrier)aircraft).typeX4CAdjSideMinus();
                toTrackSign(i);
            }
            break;

        case 121: // 'y'
            if(aircraft instanceof com.maddox.il2.objects.air.TypeBomber)
            {
                ((com.maddox.il2.objects.air.TypeBomber)aircraft).typeBomberAdjAltitudePlus();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeDiveBomber)
            {
                ((com.maddox.il2.objects.air.TypeDiveBomber)aircraft).typeDiveBomberAdjAltitudePlus();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeX4Carrier)
            {
                ((com.maddox.il2.objects.air.TypeX4Carrier)aircraft).typeX4CAdjAttitudePlus();
                toTrackSign(i);
            }
            break;

        case 122: // 'z'
            if(aircraft instanceof com.maddox.il2.objects.air.TypeBomber)
            {
                ((com.maddox.il2.objects.air.TypeBomber)aircraft).typeBomberAdjAltitudeMinus();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeDiveBomber)
            {
                ((com.maddox.il2.objects.air.TypeDiveBomber)aircraft).typeDiveBomberAdjAltitudeMinus();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeX4Carrier)
            {
                ((com.maddox.il2.objects.air.TypeX4Carrier)aircraft).typeX4CAdjAttitudeMinus();
                toTrackSign(i);
            }
            break;

        case 123: // '{'
            if(aircraft instanceof com.maddox.il2.objects.air.TypeBomber)
            {
                ((com.maddox.il2.objects.air.TypeBomber)aircraft).typeBomberAdjSpeedPlus();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeDiveBomber)
            {
                ((com.maddox.il2.objects.air.TypeDiveBomber)aircraft).typeDiveBomberAdjVelocityPlus();
                toTrackSign(i);
            }
            break;

        case 124: // '|'
            if(aircraft instanceof com.maddox.il2.objects.air.TypeBomber)
            {
                ((com.maddox.il2.objects.air.TypeBomber)aircraft).typeBomberAdjSpeedMinus();
                toTrackSign(i);
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.TypeDiveBomber)
            {
                ((com.maddox.il2.objects.air.TypeDiveBomber)aircraft).typeDiveBomberAdjVelocityMinus();
                toTrackSign(i);
            }
            break;
        }
    }

    public void fromTrackSign(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        if(!com.maddox.il2.engine.Actor.isAlive(com.maddox.il2.ai.World.getPlayerAircraft()))
            return;
        if(com.maddox.il2.ai.World.isPlayerParatrooper())
            return;
        if(com.maddox.il2.ai.World.isPlayerDead())
            return;
        if(com.maddox.il2.ai.World.getPlayerAircraft() instanceof com.maddox.il2.objects.air.TypeBomber)
        {
            com.maddox.il2.objects.air.TypeBomber typebomber = (com.maddox.il2.objects.air.TypeBomber)com.maddox.il2.ai.World.getPlayerAircraft();
            int i = netmsginput.readUnsignedShort();
            switch(i)
            {
            case 125: // '}'
                typebomber.typeBomberToggleAutomation();
                break;

            case 117: // 'u'
                typebomber.typeBomberAdjDistancePlus();
                break;

            case 118: // 'v'
                typebomber.typeBomberAdjDistanceMinus();
                break;

            case 119: // 'w'
                typebomber.typeBomberAdjSideslipPlus();
                break;

            case 120: // 'x'
                typebomber.typeBomberAdjSideslipMinus();
                break;

            case 121: // 'y'
                typebomber.typeBomberAdjAltitudePlus();
                break;

            case 122: // 'z'
                typebomber.typeBomberAdjAltitudeMinus();
                break;

            case 123: // '{'
                typebomber.typeBomberAdjSpeedPlus();
                break;

            case 124: // '|'
                typebomber.typeBomberAdjSpeedMinus();
                break;

            default:
                return;
            }
        }
        if(com.maddox.il2.ai.World.getPlayerAircraft() instanceof com.maddox.il2.objects.air.TypeDiveBomber)
        {
            com.maddox.il2.objects.air.TypeDiveBomber typedivebomber = (com.maddox.il2.objects.air.TypeDiveBomber)com.maddox.il2.ai.World.getPlayerAircraft();
            int j = netmsginput.readUnsignedShort();
            switch(j)
            {
            case 125: // '}'
                typedivebomber.typeDiveBomberToggleAutomation();
                break;

            case 121: // 'y'
                typedivebomber.typeDiveBomberAdjAltitudePlus();
                break;

            case 122: // 'z'
                typedivebomber.typeDiveBomberAdjAltitudeMinus();
                break;

            case 123: // '{'
            case 124: // '|'
            default:
                return;
            }
        }
        if(com.maddox.il2.ai.World.getPlayerAircraft() instanceof com.maddox.il2.objects.air.TypeFighterAceMaker)
        {
            com.maddox.il2.objects.air.TypeFighterAceMaker typefighteracemaker = (com.maddox.il2.objects.air.TypeFighterAceMaker)com.maddox.il2.ai.World.getPlayerAircraft();
            int k = netmsginput.readUnsignedShort();
            switch(k)
            {
            case 125: // '}'
                typefighteracemaker.typeFighterAceMakerToggleAutomation();
                break;

            case 117: // 'u'
                typefighteracemaker.typeFighterAceMakerAdjDistancePlus();
                break;

            case 118: // 'v'
                typefighteracemaker.typeFighterAceMakerAdjDistanceMinus();
                break;

            case 119: // 'w'
                typefighteracemaker.typeFighterAceMakerAdjSideslipPlus();
                break;

            case 120: // 'x'
                typefighteracemaker.typeFighterAceMakerAdjSideslipMinus();
                break;

            case 121: // 'y'
            case 122: // 'z'
            case 123: // '{'
            case 124: // '|'
            default:
                return;
            }
        }
    }

    private void toTrackSign(int i)
    {
        if(com.maddox.il2.game.Main3D.cur3D().gameTrackRecord() != null)
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(5);
                netmsgguaranted.writeShort(i);
                com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().postTo(com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().channel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception) { }
    }

    private void doCmdPilotMove(int i, float f)
    {
        if(!setPilot())
            return;
        switch(i)
        {
        case 1: // '\001'
            float f1 = f * 0.55F + 0.55F;
            if(java.lang.Math.abs(f1 - lastPower) >= 0.01F)
                setPowerControl(f1);
            break;

        case 7: // '\007'
            float f2 = f * 0.5F + 0.5F;
            if(java.lang.Math.abs(f2 - lastProp) >= 0.02F && FM.EI.isSelectionHasControlProp())
                setPropControl(f2);
            break;

        case 2: // '\002'
            FM.CT.FlapsControl = f * 0.5F + 0.5F;
            break;

        case 3: // '\003'
            if(!FM.CT.StabilizerControl)
                FM.CT.AileronControl = f;
            break;

        case 4: // '\004'
            if(!FM.CT.StabilizerControl)
                FM.CT.ElevatorControl = f;
            break;

        case 5: // '\005'
            if(!FM.CT.StabilizerControl)
                FM.CT.RudderControl = f;
            break;

        case 6: // '\006'
            FM.CT.BrakeControl = f * 0.5F + 0.5F;
            break;

        case 8: // '\b'
            if(FM.CT.bHasAileronTrim)
                FM.CT.setTrimAileronControl(f * 0.5F);
            break;

        case 9: // '\t'
            if(FM.CT.bHasElevatorTrim)
                FM.CT.setTrimElevatorControl(f * 0.5F);
            break;

        case 10: // '\n'
            if(FM.CT.bHasRudderTrim)
                FM.CT.setTrimRudderControl(f * 0.5F);
            break;

        default:
            return;
        }
    }

    public void createPilotHotMoves()
    {
        java.lang.String s = "move";
        com.maddox.rts.HotKeyCmdEnv.setCurrentEnv(s);
        com.maddox.rts.HotKeyEnv.fromIni(s, com.maddox.il2.engine.Config.cur.ini, "HotKey " + s);
        com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = com.maddox.rts.HotKeyCmdEnv.currentEnv();
        com.maddox.rts.HotKeyCmdEnv _tmp = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("01", "power", 1, 1));
        com.maddox.rts.HotKeyCmdEnv _tmp1 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("02", "flaps", 2, 2));
        com.maddox.rts.HotKeyCmdEnv _tmp2 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("03", "aileron", 3, 3));
        com.maddox.rts.HotKeyCmdEnv _tmp3 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("04", "elevator", 4, 4));
        com.maddox.rts.HotKeyCmdEnv _tmp4 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("05", "rudder", 5, 5));
        com.maddox.rts.HotKeyCmdEnv _tmp5 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("06", "brakes", 6, 6));
        com.maddox.rts.HotKeyCmdEnv _tmp6 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("07", "pitch", 7, 7));
        com.maddox.rts.HotKeyCmdEnv _tmp7 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("08", "trimaileron", 8, 8));
        com.maddox.rts.HotKeyCmdEnv _tmp8 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("09", "trimelevator", 9, 9));
        com.maddox.rts.HotKeyCmdEnv _tmp9 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("10", "trimrudder", 10, 10));
        com.maddox.rts.HotKeyCmdEnv _tmp10 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-power", 1, 11));
        com.maddox.rts.HotKeyCmdEnv _tmp11 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-flaps", 2, 12));
        com.maddox.rts.HotKeyCmdEnv _tmp12 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-aileron", 3, 13));
        com.maddox.rts.HotKeyCmdEnv _tmp13 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-elevator", 4, 14));
        com.maddox.rts.HotKeyCmdEnv _tmp14 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-rudder", 5, 15));
        com.maddox.rts.HotKeyCmdEnv _tmp15 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-brakes", 6, 16));
        com.maddox.rts.HotKeyCmdEnv _tmp16 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-pitch", 7, 17));
        com.maddox.rts.HotKeyCmdEnv _tmp17 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-trimaileron", 8, 18));
        com.maddox.rts.HotKeyCmdEnv _tmp18 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-trimelevator", 9, 19));
        com.maddox.rts.HotKeyCmdEnv _tmp19 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-trimrudder", 10, 20));
    }

    public void createPilotHotKeys()
    {
        java.lang.String s = "pilot";
        com.maddox.rts.HotKeyCmdEnv.setCurrentEnv(s);
        com.maddox.rts.HotKeyEnv.fromIni(s, com.maddox.il2.engine.Config.cur.ini, "HotKey " + s);
        com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = com.maddox.rts.HotKeyCmdEnv.currentEnv();
        com.maddox.rts.HotKeyCmdEnv _tmp = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic01", "ElevatorUp", 3, 103));
        com.maddox.rts.HotKeyCmdEnv _tmp1 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic02", "ElevatorDown", 4, 104));
        com.maddox.rts.HotKeyCmdEnv _tmp2 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic03", "AileronLeft", 5, 105));
        com.maddox.rts.HotKeyCmdEnv _tmp3 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic04", "AileronRight", 6, 106));
        com.maddox.rts.HotKeyCmdEnv _tmp4 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic05", "RudderLeft", 1, 101));
        com.maddox.rts.HotKeyCmdEnv _tmp5 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic06", "RudderRight", 2, 102));
        com.maddox.rts.HotKeyCmdEnv _tmp6 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic07", "Stabilizer", 71, 165));
        com.maddox.rts.HotKeyCmdEnv _tmp7 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic08", "AIRCRAFT_RUDDER_LEFT_1", 56, 156));
        com.maddox.rts.HotKeyCmdEnv _tmp8 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic09", "AIRCRAFT_RUDDER_CENTRE", 57, 157));
        com.maddox.rts.HotKeyCmdEnv _tmp9 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic10", "AIRCRAFT_RUDDER_RIGHT_1", 58, 158));
        com.maddox.rts.HotKeyCmdEnv _tmp10 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic11", "AIRCRAFT_TRIM_V_PLUS", 44, 144));
        com.maddox.rts.HotKeyCmdEnv _tmp11 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic12", "AIRCRAFT_TRIM_V_0", 43, 143));
        com.maddox.rts.HotKeyCmdEnv _tmp12 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic13", "AIRCRAFT_TRIM_V_MINUS", 45, 145));
        com.maddox.rts.HotKeyCmdEnv _tmp13 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic14", "AIRCRAFT_TRIM_H_MINUS", 48, 148));
        com.maddox.rts.HotKeyCmdEnv _tmp14 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic15", "AIRCRAFT_TRIM_H_0", 46, 146));
        com.maddox.rts.HotKeyCmdEnv _tmp15 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic16", "AIRCRAFT_TRIM_H_PLUS", 47, 147));
        com.maddox.rts.HotKeyCmdEnv _tmp16 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic17", "AIRCRAFT_TRIM_R_MINUS", 51, 151));
        com.maddox.rts.HotKeyCmdEnv _tmp17 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic18", "AIRCRAFT_TRIM_R_0", 49, 149));
        com.maddox.rts.HotKeyCmdEnv _tmp18 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic19", "AIRCRAFT_TRIM_R_PLUS", 50, 150));
        com.maddox.rts.HotKeyCmdEnv _tmp19 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(false, "$$$1", "1basic20") {

        }
);
        hudLogPowerId = com.maddox.il2.game.HUD.makeIdLog();
        com.maddox.rts.HotKeyCmdEnv _tmp20 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine01", "AIRCRAFT_TOGGLE_ENGINE", 70, 164));
        com.maddox.rts.HotKeyCmdEnv _tmp21 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine02", "AIRCRAFT_POWER_PLUS_5", 59, 159));
        com.maddox.rts.HotKeyCmdEnv _tmp22 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine03", "AIRCRAFT_POWER_MINUS_5", 60, 160));
        com.maddox.rts.HotKeyCmdEnv _tmp23 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine04", "Boost", 61, 161));
        com.maddox.rts.HotKeyCmdEnv _tmp24 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine05", "Power0", 20, 120));
        com.maddox.rts.HotKeyCmdEnv _tmp25 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine06", "Power10", 21, 121));
        com.maddox.rts.HotKeyCmdEnv _tmp26 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine07", "Power20", 22, 122));
        com.maddox.rts.HotKeyCmdEnv _tmp27 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine08", "Power30", 23, 123));
        com.maddox.rts.HotKeyCmdEnv _tmp28 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine09", "Power40", 24, 124));
        com.maddox.rts.HotKeyCmdEnv _tmp29 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine10", "Power50", 25, 125));
        com.maddox.rts.HotKeyCmdEnv _tmp30 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine11", "Power60", 26, 126));
        com.maddox.rts.HotKeyCmdEnv _tmp31 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine12", "Power70", 27, 127));
        com.maddox.rts.HotKeyCmdEnv _tmp32 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine13", "Power80", 28, 128));
        com.maddox.rts.HotKeyCmdEnv _tmp33 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine14", "Power90", 29, 129));
        com.maddox.rts.HotKeyCmdEnv _tmp34 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine15", "Power100", 30, 130));
        com.maddox.rts.HotKeyCmdEnv _tmp35 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(false, "$$$2", "2engine16") {

        }
);
        com.maddox.rts.HotKeyCmdEnv _tmp36 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine17", "Step0", 31, 131));
        com.maddox.rts.HotKeyCmdEnv _tmp37 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine18", "Step10", 32, 132));
        com.maddox.rts.HotKeyCmdEnv _tmp38 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine19", "Step20", 33, 133));
        com.maddox.rts.HotKeyCmdEnv _tmp39 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine20", "Step30", 34, 134));
        com.maddox.rts.HotKeyCmdEnv _tmp40 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine21", "Step40", 35, 135));
        com.maddox.rts.HotKeyCmdEnv _tmp41 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine22", "Step50", 36, 136));
        com.maddox.rts.HotKeyCmdEnv _tmp42 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine23", "Step60", 37, 137));
        com.maddox.rts.HotKeyCmdEnv _tmp43 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine24", "Step70", 38, 138));
        com.maddox.rts.HotKeyCmdEnv _tmp44 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine25", "Step80", 39, 139));
        com.maddox.rts.HotKeyCmdEnv _tmp45 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine26", "Step90", 40, 140));
        com.maddox.rts.HotKeyCmdEnv _tmp46 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine27", "Step100", 41, 141));
        com.maddox.rts.HotKeyCmdEnv _tmp47 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine28", "StepAuto", 42, 142));
        com.maddox.rts.HotKeyCmdEnv _tmp48 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine29", "StepPlus5", 73, 290));
        com.maddox.rts.HotKeyCmdEnv _tmp49 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine30", "StepMinus5", 74, 291));
        com.maddox.rts.HotKeyCmdEnv _tmp50 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(false, "$$$3", "2engine31") {

        }
);
        com.maddox.rts.HotKeyCmdEnv _tmp51 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine32", "Mix0", 75, 292));
        com.maddox.rts.HotKeyCmdEnv _tmp52 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine33", "Mix10", 76, 293));
        com.maddox.rts.HotKeyCmdEnv _tmp53 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine34", "Mix20", 77, 294));
        com.maddox.rts.HotKeyCmdEnv _tmp54 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine35", "Mix30", 78, 295));
        com.maddox.rts.HotKeyCmdEnv _tmp55 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine36", "Mix40", 79, 296));
        com.maddox.rts.HotKeyCmdEnv _tmp56 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine37", "Mix50", 80, 297));
        com.maddox.rts.HotKeyCmdEnv _tmp57 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine38", "Mix60", 81, 298));
        com.maddox.rts.HotKeyCmdEnv _tmp58 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine39", "Mix70", 82, 299));
        com.maddox.rts.HotKeyCmdEnv _tmp59 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine40", "Mix80", 83, 300));
        com.maddox.rts.HotKeyCmdEnv _tmp60 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine41", "Mix90", 84, 301));
        com.maddox.rts.HotKeyCmdEnv _tmp61 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine42", "Mix100", 85, 302));
        com.maddox.rts.HotKeyCmdEnv _tmp62 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine43", "MixPlus20", 86, 303));
        com.maddox.rts.HotKeyCmdEnv _tmp63 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine44", "MixMinus20", 87, 304));
        com.maddox.rts.HotKeyCmdEnv _tmp64 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(false, "$$$4", "2engine45") {

        }
);
        com.maddox.rts.HotKeyCmdEnv _tmp65 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine46", "MagnetoPlus", 88, 305));
        com.maddox.rts.HotKeyCmdEnv _tmp66 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine47", "MagnetoMinus", 89, 306));
        com.maddox.rts.HotKeyCmdEnv _tmp67 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(false, "$$$5", "2engine48") {

        }
);
        com.maddox.rts.HotKeyCmdEnv _tmp68 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine49", "CompressorPlus", 115, 334));
        com.maddox.rts.HotKeyCmdEnv _tmp69 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine50", "CompressorMinus", 116, 335));
        com.maddox.rts.HotKeyCmdEnv _tmp70 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(false, "$$$6", "2engine51") {

        }
);
        com.maddox.rts.HotKeyCmdEnv _tmp71 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine52", "EngineSelectAll", 90, 307));
        com.maddox.rts.HotKeyCmdEnv _tmp72 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine53", "EngineSelectNone", 91, 318));
        com.maddox.rts.HotKeyCmdEnv _tmp73 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine54", "EngineSelectLeft", 92, 316));
        com.maddox.rts.HotKeyCmdEnv _tmp74 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine55", "EngineSelectRight", 93, 317));
        com.maddox.rts.HotKeyCmdEnv _tmp75 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine56", "EngineSelect1", 94, 308));
        com.maddox.rts.HotKeyCmdEnv _tmp76 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine57", "EngineSelect2", 95, 309));
        com.maddox.rts.HotKeyCmdEnv _tmp77 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine58", "EngineSelect3", 96, 310));
        com.maddox.rts.HotKeyCmdEnv _tmp78 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine59", "EngineSelect4", 97, 311));
        com.maddox.rts.HotKeyCmdEnv _tmp79 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine60", "EngineSelect5", 98, 312));
        com.maddox.rts.HotKeyCmdEnv _tmp80 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine61", "EngineSelect6", 99, 313));
        com.maddox.rts.HotKeyCmdEnv _tmp81 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine62", "EngineSelect7", 100, 314));
        com.maddox.rts.HotKeyCmdEnv _tmp82 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine63", "EngineSelect8", 101, 315));
        com.maddox.rts.HotKeyCmdEnv _tmp83 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine64", "EngineToggleAll", 102, 319));
        com.maddox.rts.HotKeyCmdEnv _tmp84 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine65", "EngineToggleLeft", 103, 328));
        com.maddox.rts.HotKeyCmdEnv _tmp85 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine66", "EngineToggleRight", 104, 329));
        com.maddox.rts.HotKeyCmdEnv _tmp86 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine67", "EngineToggle1", 105, 320));
        com.maddox.rts.HotKeyCmdEnv _tmp87 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine68", "EngineToggle2", 106, 321));
        com.maddox.rts.HotKeyCmdEnv _tmp88 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine69", "EngineToggle3", 107, 322));
        com.maddox.rts.HotKeyCmdEnv _tmp89 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine70", "EngineToggle4", 108, 323));
        com.maddox.rts.HotKeyCmdEnv _tmp90 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine71", "EngineToggle5", 109, 324));
        com.maddox.rts.HotKeyCmdEnv _tmp91 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine72", "EngineToggle6", 110, 325));
        com.maddox.rts.HotKeyCmdEnv _tmp92 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine73", "EngineToggle7", 111, 326));
        com.maddox.rts.HotKeyCmdEnv _tmp93 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine74", "EngineToggle8", 112, 327));
        com.maddox.rts.HotKeyCmdEnv _tmp94 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(false, "$$$7", "2engine75") {

        }
);
        com.maddox.rts.HotKeyCmdEnv _tmp95 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine76", "EngineExtinguisher", 113, 330));
        com.maddox.rts.HotKeyCmdEnv _tmp96 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine77", "EngineFeather", 114, 333));
        com.maddox.rts.HotKeyCmdEnv _tmp97 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(false, "$$$8", "2engine78") {

        }
);
        com.maddox.rts.HotKeyCmdEnv _tmp98 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced01", "AIRCRAFT_FLAPS_NOTCH_UP", 52, 152));
        com.maddox.rts.HotKeyCmdEnv _tmp99 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced02", "AIRCRAFT_FLAPS_NOTCH_DOWN", 53, 153));
        com.maddox.rts.HotKeyCmdEnv _tmp100 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced03", "Gear", 9, 109));
        com.maddox.rts.HotKeyCmdEnv _tmp101 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced04", "AIRCRAFT_GEAR_UP_MANUAL", 54, 154));
        com.maddox.rts.HotKeyCmdEnv _tmp102 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced05", "AIRCRAFT_GEAR_DOWN_MANUAL", 55, 155));
        com.maddox.rts.HotKeyCmdEnv _tmp103 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced06", "Radiator", 7, 107));
        com.maddox.rts.HotKeyCmdEnv _tmp104 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced07", "AIRCRAFT_TOGGLE_AIRBRAKE", 63, 163));
        com.maddox.rts.HotKeyCmdEnv _tmp105 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced08", "Brake", 0, 100));
        com.maddox.rts.HotKeyCmdEnv _tmp106 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced09", "AIRCRAFT_TAILWHEELLOCK", 72, 166));
        com.maddox.rts.HotKeyCmdEnv _tmp107 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced10", "AIRCRAFT_DROP_TANKS", 62, 162));
        com.maddox.rts.HotKeyCmdEnv _tmp108 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(false, "$$$9", "3advanced11") {

        }
);
        com.maddox.rts.HotKeyCmdEnv _tmp109 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced12", "AIRCRAFT_DOCK_UNDOCK", 126, 346));
        com.maddox.rts.HotKeyCmdEnv _tmp110 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced13", "WINGFOLD", 127, 347));
        com.maddox.rts.HotKeyCmdEnv _tmp111 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced14", "AIRCRAFT_CARRIERHOOK", 129, 349));
        com.maddox.rts.HotKeyCmdEnv _tmp112 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced15", "AIRCRAFT_BRAKESHOE", 130, 350));
        com.maddox.rts.HotKeyCmdEnv _tmp113 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced16", "COCKPITDOOR", 128, 348));
        com.maddox.rts.HotKeyCmdEnv _tmp114 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(false, "$$$10", "3advanced17") {

        }
);
        hudLogWeaponId = com.maddox.il2.game.HUD.makeIdLog();
        com.maddox.rts.HotKeyCmdEnv _tmp115 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("4basic0", "Weapon0", 16, 116));
        com.maddox.rts.HotKeyCmdEnv _tmp116 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("4basic1", "Weapon1", 17, 117));
        com.maddox.rts.HotKeyCmdEnv _tmp117 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("4basic2", "Weapon2", 18, 118));
        com.maddox.rts.HotKeyCmdEnv _tmp118 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("4basic3", "Weapon3", 19, 119));
        com.maddox.rts.HotKeyCmdEnv _tmp119 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("4basic4", "Weapon01", 64, 173));
        com.maddox.rts.HotKeyCmdEnv _tmp120 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("4basic5", "GunPods", 15, 115));
        com.maddox.rts.HotKeyCmdEnv _tmp121 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(false, "$$+SIGHTCONTROLS", "4basic6") {

        }
);
        com.maddox.rts.HotKeyCmdEnv _tmp122 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced01", "SIGHT_AUTO_ONOFF", 125, 344));
        com.maddox.rts.HotKeyCmdEnv _tmp123 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced02", "SIGHT_DIST_PLUS", 117, 336));
        com.maddox.rts.HotKeyCmdEnv _tmp124 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced03", "SIGHT_DIST_MINUS", 118, 337));
        com.maddox.rts.HotKeyCmdEnv _tmp125 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced04", "SIGHT_SIDE_RIGHT", 119, 338));
        com.maddox.rts.HotKeyCmdEnv _tmp126 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced05", "SIGHT_SIDE_LEFT", 120, 339));
        com.maddox.rts.HotKeyCmdEnv _tmp127 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced06", "SIGHT_ALT_PLUS", 121, 340));
        com.maddox.rts.HotKeyCmdEnv _tmp128 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced07", "SIGHT_ALT_MINUS", 122, 341));
        com.maddox.rts.HotKeyCmdEnv _tmp129 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced08", "SIGHT_SPD_PLUS", 123, 342));
        com.maddox.rts.HotKeyCmdEnv _tmp130 = hotkeycmdenv;
        com.maddox.rts.HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced09", "SIGHT_SPD_MINUS", 124, 343));
    }

    private com.maddox.il2.objects.air.CockpitGunner getActiveCockpitGuner()
    {
        if(!com.maddox.il2.engine.Actor.isAlive(com.maddox.il2.ai.World.getPlayerAircraft()))
            return null;
        if(com.maddox.il2.ai.World.isPlayerParatrooper())
            return null;
        if(com.maddox.il2.ai.World.isPlayerDead())
            return null;
        if(com.maddox.il2.game.Main3D.cur3D().cockpits == null)
            return null;
        int i = com.maddox.il2.ai.World.getPlayerAircraft().FM.AS.astatePlayerIndex;
        for(int j = 0; j < com.maddox.il2.game.Main3D.cur3D().cockpits.length; j++)
            if(com.maddox.il2.game.Main3D.cur3D().cockpits[j] instanceof com.maddox.il2.objects.air.CockpitGunner)
            {
                com.maddox.il2.objects.air.CockpitGunner cockpitgunner = (com.maddox.il2.objects.air.CockpitGunner)com.maddox.il2.game.Main3D.cur3D().cockpits[j];
                if(i == cockpitgunner.astatePilotIndx() && cockpitgunner.isRealMode())
                {
                    com.maddox.il2.fm.Turret turret = cockpitgunner.aiTurret();
                    if(!turret.bIsNetMirror)
                        return cockpitgunner;
                }
            }

        return null;
    }

    public void createGunnerHotKeys()
    {
        java.lang.String s = "gunner";
        com.maddox.rts.HotKeyCmdEnv.setCurrentEnv(s);
        com.maddox.rts.HotKeyEnv.fromIni(s, com.maddox.il2.engine.Config.cur.ini, "HotKey " + s);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmdMouseMove(true, "Mouse") {

            public void created()
            {
                setRecordId(51);
                sortingName = null;
            }

            public boolean isDisableIfTimePaused()
            {
                return true;
            }

            public void move(int i, int j, int k)
            {
                com.maddox.il2.objects.air.CockpitGunner cockpitgunner = getActiveCockpitGuner();
                if(cockpitgunner == null)
                {
                    return;
                } else
                {
                    cockpitgunner.hookGunner().mouseMove(i, j, k);
                    return;
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(true, "Fire") {

            public void created()
            {
                setRecordId(52);
            }

            public boolean isDisableIfTimePaused()
            {
                return true;
            }

            private boolean isExistAmmo(com.maddox.il2.objects.air.CockpitGunner cockpitgunner)
            {
                com.maddox.il2.fm.FlightModel flightmodel = com.maddox.il2.ai.World.getPlayerFM();
                com.maddox.il2.ai.BulletEmitter abulletemitter[] = flightmodel.CT.Weapons[cockpitgunner.weaponControlNum()];
                if(abulletemitter == null)
                    return false;
                for(int i = 0; i < abulletemitter.length; i++)
                    if(abulletemitter[i] != null && abulletemitter[i].haveBullets())
                        return true;

                return false;
            }

            public void begin()
            {
                coc = getActiveCockpitGuner();
                if(coc == null)
                    return;
                if(isExistAmmo(coc))
                    coc.hookGunner().gunFire(true);
                else
                    com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "OutOfAmmo");
            }

            public void end()
            {
                if(coc == null)
                    return;
                if(com.maddox.il2.engine.Actor.isValid(coc))
                    coc.hookGunner().gunFire(false);
                coc = null;
            }

            com.maddox.il2.objects.air.CockpitGunner coc;

            
            {
                coc = null;
            }
        }
);
    }

    public boolean isAutoAutopilot()
    {
        return bAutoAutopilot;
    }

    public void setAutoAutopilot(boolean flag)
    {
        bAutoAutopilot = flag;
    }

    public static boolean isCockpitRealMode(int i)
    {
        if(com.maddox.il2.game.Main3D.cur3D().cockpits[i] instanceof com.maddox.il2.objects.air.CockpitPilot)
        {
            com.maddox.il2.fm.RealFlightModel realflightmodel = (com.maddox.il2.fm.RealFlightModel)com.maddox.il2.ai.World.getPlayerFM();
            return realflightmodel.isRealMode();
        }
        if(com.maddox.il2.game.Main3D.cur3D().cockpits[i] instanceof com.maddox.il2.objects.air.CockpitGunner)
        {
            com.maddox.il2.objects.air.CockpitGunner cockpitgunner = (com.maddox.il2.objects.air.CockpitGunner)com.maddox.il2.game.Main3D.cur3D().cockpits[i];
            return cockpitgunner.isRealMode();
        } else
        {
            return false;
        }
    }

    public static void setCockpitRealMode(int i, boolean flag)
    {
        if(com.maddox.il2.game.Main3D.cur3D().cockpits[i] instanceof com.maddox.il2.objects.air.CockpitPilot)
        {
            if(com.maddox.il2.game.Mission.isNet())
                return;
            com.maddox.il2.fm.RealFlightModel realflightmodel = (com.maddox.il2.fm.RealFlightModel)com.maddox.il2.ai.World.getPlayerFM();
            if(realflightmodel.get_maneuver() == 44)
                return;
            if(realflightmodel.isRealMode() == flag)
                return;
            if(realflightmodel.isRealMode())
                com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.bAfterburner = false;
            realflightmodel.CT.resetControl(0);
            realflightmodel.CT.resetControl(1);
            realflightmodel.CT.resetControl(2);
            realflightmodel.EI.setCurControlAll(true);
            realflightmodel.setRealMode(flag);
            com.maddox.il2.game.HUD.log("PilotAI" + (realflightmodel.isRealMode() ? "OFF" : "ON"));
        } else
        if(com.maddox.il2.game.Main3D.cur3D().cockpits[i] instanceof com.maddox.il2.objects.air.CockpitGunner)
        {
            com.maddox.il2.objects.air.CockpitGunner cockpitgunner = (com.maddox.il2.objects.air.CockpitGunner)com.maddox.il2.game.Main3D.cur3D().cockpits[i];
            if(cockpitgunner.isRealMode() == flag)
                return;
            cockpitgunner.setRealMode(flag);
            if(!com.maddox.il2.net.NetMissionTrack.isPlaying())
            {
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                if(com.maddox.il2.ai.World.isPlayerGunner())
                    aircraft.netCockpitAuto(com.maddox.il2.ai.World.getPlayerGunner(), i, !cockpitgunner.isRealMode());
                else
                    aircraft.netCockpitAuto(aircraft, i, !cockpitgunner.isRealMode());
            }
            com.maddox.il2.fm.FlightModel flightmodel = com.maddox.il2.ai.World.getPlayerFM();
            com.maddox.il2.fm.AircraftState _tmp = flightmodel.AS;
            java.lang.String s = com.maddox.il2.fm.AircraftState.astateHUDPilotHits[flightmodel.AS.astatePilotFunctions[cockpitgunner.astatePilotIndx()]];
            com.maddox.il2.game.HUD.log(s + (cockpitgunner.isRealMode() ? "AIOFF" : "AION"));
        }
    }

    private boolean isMiscValid()
    {
        if(!com.maddox.il2.engine.Actor.isAlive(com.maddox.il2.ai.World.getPlayerAircraft()))
            return false;
        if(com.maddox.il2.ai.World.isPlayerParatrooper())
            return false;
        if(com.maddox.il2.ai.World.isPlayerDead())
            return false;
        return com.maddox.il2.game.Mission.isPlaying();
    }

    public void createMiscHotKeys()
    {
        java.lang.String s = "misc";
        com.maddox.rts.HotKeyCmdEnv.setCurrentEnv(s);
        com.maddox.rts.HotKeyEnv.fromIni(s, com.maddox.il2.engine.Config.cur.ini, "HotKey " + s);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "autopilot", "00") {

            public void created()
            {
                setRecordId(270);
            }

            public void begin()
            {
                if(!isMiscValid())
                    return;
                if(com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
                    return;
                if(com.maddox.il2.ai.World.getPlayerFM().AS.isPilotDead(com.maddox.il2.game.Main3D.cur3D().cockpitCur.astatePilotIndx()))
                    return;
                int j = com.maddox.il2.game.Main3D.cur3D().cockpitCurIndx();
                if(com.maddox.il2.game.AircraftHotKeys.isCockpitRealMode(j))
                    new com.maddox.rts.MsgAction(true, new Integer(j)) {

                        public void doAction(java.lang.Object obj)
                        {
                            int k = ((java.lang.Integer)obj).intValue();
                            com.maddox.rts.HotKeyCmd.exec("misc", "cockpitRealOff" + k);
                        }

                    }
;
                else
                    new com.maddox.rts.MsgAction(true, new Integer(j)) {

                        public void doAction(java.lang.Object obj)
                        {
                            int k = ((java.lang.Integer)obj).intValue();
                            com.maddox.rts.HotKeyCmd.exec("misc", "cockpitRealOn" + k);
                        }

                    }
;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "autopilotAuto", "01") {

            public void begin()
            {
                if(!isMiscValid())
                    return;
                if(com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
                {
                    return;
                } else
                {
                    new com.maddox.rts.MsgAction(true) {

                        public void doAction()
                        {
                            com.maddox.rts.HotKeyCmd.exec("misc", "autopilotAuto_");
                        }

                    }
;
                    return;
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "autopilotAuto_", null) {

            public void created()
            {
                setRecordId(271);
                com.maddox.rts.HotKeyEnv.currentEnv().remove(sName);
            }

            public void begin()
            {
                if(!isMiscValid())
                {
                    return;
                } else
                {
                    setAutoAutopilot(!isAutoAutopilot());
                    com.maddox.il2.game.HUD.log("AutopilotAuto" + (isAutoAutopilot() ? "ON" : "OFF"));
                    return;
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "target_", null) {

            public void created()
            {
                setRecordId(278);
                com.maddox.rts.HotKeyEnv.currentEnv().remove(sName);
            }

            public void begin()
            {
                com.maddox.il2.engine.Actor actor = null;
                if(com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
                    actor = com.maddox.il2.game.Selector._getTrackArg0();
                else
                    actor = com.maddox.il2.engine.hotkey.HookPilot.cur().getEnemy();
                com.maddox.il2.game.Selector.setTarget(com.maddox.il2.game.Selector.setCurRecordArg0(actor));
            }

        }
);
        for(int i = 0; i < 10; i++)
        {
            com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "cockpitRealOn" + i, null) {

                public void created()
                {
                    indx = java.lang.Character.getNumericValue(name().charAt(name().length() - 1)) - java.lang.Character.getNumericValue('0');
                    setRecordId(500 + indx);
                    com.maddox.rts.HotKeyEnv.currentEnv().remove(sName);
                }

                public void begin()
                {
                    if(!isMiscValid())
                    {
                        return;
                    } else
                    {
                        com.maddox.il2.game.AircraftHotKeys.setCockpitRealMode(indx, true);
                        return;
                    }
                }

                int indx;

            }
);
            com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "cockpitRealOff" + i, null) {

                public void created()
                {
                    indx = java.lang.Character.getNumericValue(name().charAt(name().length() - 1)) - java.lang.Character.getNumericValue('0');
                    setRecordId(510 + indx);
                    com.maddox.rts.HotKeyEnv.currentEnv().remove(sName);
                }

                public void begin()
                {
                    if(!isMiscValid())
                    {
                        return;
                    } else
                    {
                        com.maddox.il2.game.AircraftHotKeys.setCockpitRealMode(indx, false);
                        return;
                    }
                }

                int indx;

            }
);
            com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "cockpitEnter" + i, null) {

                public void created()
                {
                    indx = java.lang.Character.getNumericValue(name().charAt(name().length() - 1)) - java.lang.Character.getNumericValue('0');
                    setRecordId(520 + indx);
                    com.maddox.rts.HotKeyEnv.currentEnv().remove(sName);
                }

                public void begin()
                {
                    if(!isMiscValid())
                        return;
                    if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && indx < com.maddox.il2.game.Main3D.cur3D().cockpits.length)
                    {
                        com.maddox.il2.ai.World.getPlayerAircraft().FM.AS.astatePlayerIndex = com.maddox.il2.game.Main3D.cur3D().cockpits[indx].astatePilotIndx();
                        if(!com.maddox.il2.net.NetMissionTrack.isPlaying())
                        {
                            com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                            if(com.maddox.il2.ai.World.isPlayerGunner())
                                aircraft.netCockpitEnter(com.maddox.il2.ai.World.getPlayerGunner(), indx);
                            else
                                aircraft.netCockpitEnter(aircraft, indx);
                        }
                    }
                }

                int indx;

            }
);
            com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "cockpitLeave" + i, null) {

                public void created()
                {
                    indx = java.lang.Character.getNumericValue(name().charAt(name().length() - 1)) - java.lang.Character.getNumericValue('0');
                    setRecordId(530 + indx);
                    com.maddox.rts.HotKeyEnv.currentEnv().remove(sName);
                }

                public void begin()
                {
                    if(!isMiscValid())
                        return;
                    if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && indx < com.maddox.il2.game.Main3D.cur3D().cockpits.length && (com.maddox.il2.game.Main3D.cur3D().cockpits[indx] instanceof com.maddox.il2.objects.air.CockpitGunner) && com.maddox.il2.game.AircraftHotKeys.isCockpitRealMode(indx))
                        ((com.maddox.il2.objects.air.CockpitGunner)com.maddox.il2.game.Main3D.cur3D().cockpits[indx]).hookGunner().gunFire(false);
                }

                int indx;

            }
);
        }

        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "ejectPilot", "02") {

            public void created()
            {
                setRecordId(272);
            }

            public void begin()
            {
                if(!isMiscValid())
                    return;
                if(com.maddox.il2.ai.World.isPlayerGunner())
                    return;
                if(!(com.maddox.il2.ai.World.getPlayerFM() instanceof com.maddox.il2.fm.RealFlightModel))
                    return;
                com.maddox.il2.fm.RealFlightModel realflightmodel = (com.maddox.il2.fm.RealFlightModel)com.maddox.il2.ai.World.getPlayerFM();
                if(!realflightmodel.isRealMode())
                    return;
                if(realflightmodel.AS.bIsAboutToBailout)
                    return;
                if(!realflightmodel.AS.bIsEnableToBailout)
                {
                    return;
                } else
                {
                    com.maddox.il2.fm.AircraftState.bCheckPlayerAircraft = false;
                    ((com.maddox.il2.objects.air.Aircraft)realflightmodel.actor).hitDaSilk();
                    com.maddox.il2.fm.AircraftState.bCheckPlayerAircraft = true;
                    com.maddox.il2.objects.sounds.Voice.cur().SpeakBailOut[realflightmodel.actor.getArmy() - 1 & 1][((com.maddox.il2.objects.air.Aircraft)realflightmodel.actor).aircIndex()] = (int)(com.maddox.rts.Time.current() / 60000L) + 1;
                    new com.maddox.rts.MsgAction(true) {

                        public void doAction()
                        {
                            if(!com.maddox.il2.game.Main3D.cur3D().isDemoPlaying() || !com.maddox.rts.HotKeyEnv.isEnabled("aircraftView"))
                                com.maddox.rts.HotKeyCmd.exec("aircraftView", "OutsideView");
                        }

                    }
;
                    return;
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "cockpitDim", "03") {

            public void created()
            {
                setRecordId(274);
            }

            public void begin()
            {
                if(com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                    return;
                if(!isMiscValid())
                    return;
                if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.game.Main3D.cur3D().cockpitCur))
                {
                    return;
                } else
                {
                    com.maddox.il2.game.Main3D.cur3D().cockpitCur.doToggleDim();
                    return;
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "cockpitLight", "04") {

            public void created()
            {
                setRecordId(275);
            }

            public void begin()
            {
                if(com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                    return;
                if(!isMiscValid())
                    return;
                if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.game.Main3D.cur3D().cockpitCur))
                {
                    return;
                } else
                {
                    com.maddox.il2.game.Main3D.cur3D().cockpitCur.doToggleLight();
                    return;
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "toggleNavLights", "05") {

            public void created()
            {
                setRecordId(331);
            }

            public void begin()
            {
                if(!isMiscValid())
                    return;
                com.maddox.il2.fm.FlightModel flightmodel = com.maddox.il2.ai.World.getPlayerFM();
                if(flightmodel == null)
                    return;
                boolean flag = flightmodel.AS.bNavLightsOn;
                flightmodel.AS.setNavLightsState(!flightmodel.AS.bNavLightsOn);
                if(!flag && !flightmodel.AS.bNavLightsOn)
                {
                    return;
                } else
                {
                    com.maddox.il2.game.HUD.log("NavigationLights" + (flightmodel.AS.bNavLightsOn ? "ON" : "OFF"));
                    return;
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "toggleLandingLight", "06") {

            public void created()
            {
                setRecordId(345);
            }

            public void begin()
            {
                if(!isMiscValid())
                    return;
                com.maddox.il2.fm.FlightModel flightmodel = com.maddox.il2.ai.World.getPlayerFM();
                if(flightmodel == null)
                    return;
                boolean flag = flightmodel.AS.bLandingLightOn;
                flightmodel.AS.setLandingLightState(!flightmodel.AS.bLandingLightOn);
                if(!flag && !flightmodel.AS.bLandingLightOn)
                {
                    return;
                } else
                {
                    com.maddox.il2.game.HUD.log("LandingLight" + (flightmodel.AS.bLandingLightOn ? "ON" : "OFF"));
                    com.maddox.il2.ai.EventLog.onToggleLandingLight(flightmodel.actor, flightmodel.AS.bLandingLightOn);
                    return;
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "toggleSmokes", "07") {

            public void created()
            {
                setRecordId(273);
            }

            public void begin()
            {
                if(!isMiscValid())
                    return;
                com.maddox.il2.fm.FlightModel flightmodel = com.maddox.il2.ai.World.getPlayerFM();
                if(flightmodel == null)
                {
                    return;
                } else
                {
                    flightmodel.AS.setAirShowState(!flightmodel.AS.bShowSmokesOn);
                    com.maddox.il2.ai.EventLog.onToggleSmoke(flightmodel.actor, flightmodel.AS.bShowSmokesOn);
                    return;
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "pad", "08") {

            public void end()
            {
                int j = com.maddox.il2.game.Main.state().id();
                boolean flag = j == 5 || j == 29 || j == 63 || j == 49 || j == 50 || j == 42 || j == 43;
                if(com.maddox.il2.gui.GUI.pad.isActive())
                    com.maddox.il2.gui.GUI.pad.leave(!flag);
                else
                if(flag && !com.maddox.il2.game.Main3D.cur3D().guiManager.isMouseActive())
                    com.maddox.il2.gui.GUI.pad.enter();
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "chat", "09") {

            public void end()
            {
                com.maddox.il2.gui.GUI.chatActivate();
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "onlineRating", "10") {

            public void begin()
            {
                com.maddox.il2.game.Main3D.cur3D().hud.startNetStat();
            }

            public void end()
            {
                com.maddox.il2.game.Main3D.cur3D().hud.stopNetStat();
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "onlineRatingPage", "11") {

            public void end()
            {
                com.maddox.il2.game.Main3D.cur3D().hud.pageNetStat();
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "showPositionHint", "12") {

            public void begin()
            {
                com.maddox.il2.game.HUD.setDrawSpeed((com.maddox.il2.game.HUD.drawSpeed() + 1) % 4);
            }

            public void created()
            {
                setRecordId(277);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "iconTypes", "13") {

            public void end()
            {
                com.maddox.il2.game.Main3D.cur3D().changeIconTypes();
            }

            public void created()
            {
                setRecordId(279);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "showMirror", "14") {

            public void end()
            {
                com.maddox.il2.game.Main3D.cur3D().viewMirror = (com.maddox.il2.game.Main3D.cur3D().viewMirror + 1) % 3;
            }

            public void created()
            {
                setRecordId(280);
            }

        }
);
    }

    public void create_MiscHotKeys()
    {
        java.lang.String s = "$$$misc";
        com.maddox.rts.HotKeyCmdEnv.setCurrentEnv(s);
        com.maddox.rts.HotKeyEnv.fromIni(s, com.maddox.il2.engine.Config.cur.ini, "HotKey " + s);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "quickSaveNetTrack", "01") {

            public void end()
            {
                com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
                if(guiwindowmanager.isKeyboardActive())
                    return;
                if(com.maddox.il2.net.NetMissionTrack.isQuickRecording())
                    com.maddox.il2.net.NetMissionTrack.stopRecording();
                else
                    com.maddox.il2.net.NetMissionTrack.startQuickRecording();
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "radioMuteKey", "02") {

            public void begin()
            {
                com.maddox.sound.AudioDevice.setPTT(true);
            }

            public void end()
            {
                com.maddox.sound.AudioDevice.setPTT(false);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "radioChannelSwitch", "03") {

            public void end()
            {
                if(com.maddox.il2.gui.GUI.chatDlg == null)
                    return;
                if(com.maddox.il2.game.Main.cur().chat == null)
                    return;
                if(com.maddox.il2.gui.GUI.chatDlg.mode() == 2)
                    return;
                if(com.maddox.sound.RadioChannel.tstLoop)
                    return;
                if(!com.maddox.sound.AudioDevice.npFlags.get(0))
                    return;
                if(com.maddox.il2.net.NetMissionTrack.isPlaying())
                    return;
                com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
                java.lang.String s1 = null;
                java.lang.String s2 = null;
                if(netuser.isRadioPrivate())
                {
                    s1 = "radio NONE";
                    s2 = "radioNone";
                } else
                if(netuser.isRadioArmy())
                {
                    s1 = "radio NONE";
                    s2 = "radioNone";
                } else
                if(netuser.isRadioCommon())
                {
                    if(netuser.getArmy() != 0)
                    {
                        s1 = "radio ARMY";
                        s2 = "radioArmy";
                    } else
                    {
                        s1 = "radio NONE";
                        s2 = "radioNone";
                    }
                } else
                if(netuser.isRadioNone())
                {
                    s1 = "radio COMMON";
                    s2 = "radioCommon";
                }
                java.lang.System.out.println(com.maddox.rts.RTSConf.cur.console.getPrompt() + s1);
                com.maddox.rts.RTSConf.cur.console.getEnv().exec(s1);
                com.maddox.rts.RTSConf.cur.console.addHistoryCmd(s1);
                com.maddox.rts.RTSConf.cur.console.curHistoryCmd = -1;
                if(!com.maddox.rts.Time.isPaused())
                    com.maddox.il2.game.HUD.log(s2);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "soundMuteKey", "04") {

            public void end()
            {
                com.maddox.sound.AudioDevice.toggleMute();
            }

        }
);
    }

    private void switchToAIGunner()
    {
        if(!com.maddox.il2.game.Main3D.cur3D().isDemoPlaying() && (com.maddox.il2.game.Main3D.cur3D().cockpitCur instanceof com.maddox.il2.objects.air.CockpitGunner) && com.maddox.il2.game.Main3D.cur3D().isViewOutside() && isAutoAutopilot())
        {
            com.maddox.il2.objects.air.CockpitGunner cockpitgunner = (com.maddox.il2.objects.air.CockpitGunner)com.maddox.il2.game.Main3D.cur3D().cockpitCur;
            if(cockpitgunner.isRealMode())
                new com.maddox.rts.MsgAction(true, new Integer(com.maddox.il2.game.Main3D.cur3D().cockpitCurIndx())) {

                    public void doAction(java.lang.Object obj)
                    {
                        int i = ((java.lang.Integer)obj).intValue();
                        com.maddox.rts.HotKeyCmd.exec("misc", "cockpitRealOff" + i);
                    }

                }
;
        }
    }

    private boolean isValidCockpit(int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()))
            return false;
        if(!com.maddox.il2.game.Mission.isPlaying())
            return false;
        if(com.maddox.il2.ai.World.isPlayerParatrooper())
            return false;
        if(com.maddox.il2.game.Main3D.cur3D().cockpits == null)
            return false;
        if(i >= com.maddox.il2.game.Main3D.cur3D().cockpits.length)
            return false;
        if(com.maddox.il2.ai.World.getPlayerAircraft().isUnderWater())
            return false;
        com.maddox.il2.objects.air.Cockpit cockpit = com.maddox.il2.game.Main3D.cur3D().cockpits[i];
        if(!cockpit.isEnableFocusing())
            return false;
        int j = cockpit.astatePilotIndx();
        if(com.maddox.il2.ai.World.getPlayerFM().AS.isPilotParatrooper(j))
            return false;
        if(com.maddox.il2.ai.World.getPlayerFM().AS.isPilotDead(j))
            return false;
        if(com.maddox.il2.game.Mission.isNet())
        {
            if(com.maddox.il2.game.Mission.isCoop())
            {
                if(com.maddox.il2.ai.World.isPlayerGunner())
                {
                    if(cockpit instanceof com.maddox.il2.objects.air.CockpitPilot)
                        return false;
                } else
                if(cockpit instanceof com.maddox.il2.objects.air.CockpitPilot)
                    return true;
                if(com.maddox.rts.Time.current() == 0L)
                    return false;
                if(com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
                    return true;
                return !com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft().netCockpitGetDriver(i)) || com.maddox.il2.ai.World.isPlayerDead();
            }
            return com.maddox.il2.game.Mission.isDogfight();
        } else
        {
            return true;
        }
    }

    private void switchToCockpit(int i)
    {
        if(com.maddox.il2.game.Mission.isCoop() && (com.maddox.il2.game.Main3D.cur3D().cockpits[i] instanceof com.maddox.il2.objects.air.CockpitGunner) && !com.maddox.il2.game.Main3D.cur3D().isDemoPlaying() && !com.maddox.il2.ai.World.isPlayerDead())
        {
            java.lang.Object obj = com.maddox.il2.ai.World.getPlayerAircraft();
            if(com.maddox.il2.ai.World.isPlayerGunner())
                obj = com.maddox.il2.ai.World.getPlayerGunner();
            com.maddox.il2.engine.Actor actor = com.maddox.il2.ai.World.getPlayerAircraft().netCockpitGetDriver(i);
            if(obj != actor)
                if(com.maddox.il2.engine.Actor.isValid(actor))
                {
                    return;
                } else
                {
                    switchToCockpitRequest = i;
                    com.maddox.il2.ai.World.getPlayerAircraft().netCockpitDriverRequest(((com.maddox.il2.engine.Actor) (obj)), i);
                    return;
                }
        }
        doSwitchToCockpit(i);
    }

    public void netSwitchToCockpit(int i)
    {
        if(com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
            return;
        if(i == switchToCockpitRequest)
            new com.maddox.rts.MsgAction(true, new Integer(i)) {

                public void doAction(java.lang.Object obj)
                {
                    int j = ((java.lang.Integer)obj).intValue();
                    com.maddox.rts.HotKeyCmd.exec("aircraftView", "cockpitSwitch" + j);
                }

            }
;
    }

    private void doSwitchToCockpit(int i)
    {
        com.maddox.il2.game.Selector.setCurRecordArg0(com.maddox.il2.ai.World.getPlayerAircraft());
        if(!com.maddox.il2.ai.World.isPlayerDead() && !com.maddox.il2.ai.World.isPlayerParatrooper() && !com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
        {
            boolean flag = true;
            if((com.maddox.il2.game.Main3D.cur3D().cockpitCur instanceof com.maddox.il2.objects.air.CockpitPilot) && (com.maddox.il2.game.Main3D.cur3D().cockpits[i] instanceof com.maddox.il2.objects.air.CockpitPilot))
                flag = false;
            if(flag && isAutoAutopilot())
                new com.maddox.rts.MsgAction(true, new Integer(com.maddox.il2.game.Main3D.cur3D().cockpitCurIndx())) {

                    public void doAction(java.lang.Object obj)
                    {
                        int j = ((java.lang.Integer)obj).intValue();
                        com.maddox.rts.HotKeyCmd.exec("misc", "cockpitRealOff" + j);
                    }

                }
;
            new com.maddox.rts.MsgAction(true, new Integer(com.maddox.il2.game.Main3D.cur3D().cockpitCurIndx())) {

                public void doAction(java.lang.Object obj)
                {
                    int j = ((java.lang.Integer)obj).intValue();
                    com.maddox.rts.HotKeyCmd.exec("misc", "cockpitLeave" + j);
                }

            }
;
            new com.maddox.rts.MsgAction(true, new Integer(i)) {

                public void doAction(java.lang.Object obj)
                {
                    int j = ((java.lang.Integer)obj).intValue();
                    com.maddox.rts.HotKeyCmd.exec("misc", "cockpitEnter" + j);
                }

            }
;
            if(flag && isAutoAutopilot())
                new com.maddox.rts.MsgAction(true, new Integer(i)) {

                    public void doAction(java.lang.Object obj)
                    {
                        int j = ((java.lang.Integer)obj).intValue();
                        com.maddox.rts.HotKeyCmd.exec("misc", "cockpitRealOn" + j);
                    }

                }
;
        }
        com.maddox.il2.game.Main3D.cur3D().cockpitCur.focusLeave();
        com.maddox.il2.game.Main3D.cur3D().cockpitCur = com.maddox.il2.game.Main3D.cur3D().cockpits[i];
        com.maddox.il2.game.Main3D.cur3D().cockpitCur.focusEnter();
    }

    private int nextValidCockpit()
    {
        int i = com.maddox.il2.game.Main3D.cur3D().cockpitCurIndx();
        if(i < 0)
            return -1;
        int j = com.maddox.il2.game.Main3D.cur3D().cockpits.length;
        if(j < 2)
            return -1;
        for(int k = 0; k < j - 1; k++)
        {
            int l = (i + k + 1) % j;
            if(isValidCockpit(l))
                return l;
        }

        return -1;
    }

    public void setEnableChangeFov(boolean flag)
    {
        for(int i = 0; i < cmdFov.length; i++)
            cmdFov[i].enable(flag);

    }

    public void createViewHotKeys()
    {
        java.lang.String s = "aircraftView";
        com.maddox.rts.HotKeyCmdEnv.setCurrentEnv(s);
        com.maddox.rts.HotKeyEnv.fromIni(s, com.maddox.il2.engine.Config.cur.ini, "HotKey " + s);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "changeCockpit", "0") {

            public void begin()
            {
                int j = nextValidCockpit();
                if(j < 0)
                {
                    return;
                } else
                {
                    new com.maddox.rts.MsgAction(true, new Integer(j)) {

                        public void doAction(java.lang.Object obj)
                        {
                            int k = ((java.lang.Integer)obj).intValue();
                            com.maddox.rts.HotKeyCmd.exec("aircraftView", "cockpitSwitch" + k);
                        }

                    }
;
                    return;
                }
            }

        }
);
        for(int i = 0; i < 10; i++)
        {
            com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "cockpitView" + i, "0" + i) {

                public void created()
                {
                    indx = java.lang.Character.getNumericValue(name().charAt(name().length() - 1)) - java.lang.Character.getNumericValue('0');
                }

                public void begin()
                {
                    if(!isValidCockpit(indx))
                    {
                        return;
                    } else
                    {
                        new com.maddox.rts.MsgAction(true, new Integer(indx)) {

                            public void doAction(java.lang.Object obj)
                            {
                                int j = ((java.lang.Integer)obj).intValue();
                                com.maddox.rts.HotKeyCmd.exec("aircraftView", "cockpitSwitch" + j);
                            }

                        }
;
                        return;
                    }
                }

                int indx;

            }
);
            com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "cockpitSwitch" + i, null) {

                public void created()
                {
                    indx = java.lang.Character.getNumericValue(name().charAt(name().length() - 1)) - java.lang.Character.getNumericValue('0');
                    setRecordId(230 + indx);
                    com.maddox.rts.HotKeyEnv.currentEnv().remove(sName);
                }

                public void begin()
                {
                    if(com.maddox.il2.game.Main3D.cur3D().cockpitCurIndx() == indx && !com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                    {
                        return;
                    } else
                    {
                        switchToCockpit(indx);
                        return;
                    }
                }

                int indx;

            }
);
        }

        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[0] = new com.maddox.rts.HotKeyCmd(true, "fov90", "11") {

            public void begin()
            {
                com.maddox.rts.CmdEnv.top().exec("fov 90");
            }

            public void created()
            {
                setRecordId(216);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[1] = new com.maddox.rts.HotKeyCmd(true, "fov85", "12") {

            public void begin()
            {
                com.maddox.rts.CmdEnv.top().exec("fov 85");
            }

            public void created()
            {
                setRecordId(244);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[2] = new com.maddox.rts.HotKeyCmd(true, "fov80", "13") {

            public void begin()
            {
                com.maddox.rts.CmdEnv.top().exec("fov 80");
            }

            public void created()
            {
                setRecordId(243);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[3] = new com.maddox.rts.HotKeyCmd(true, "fov75", "14") {

            public void begin()
            {
                com.maddox.rts.CmdEnv.top().exec("fov 75");
            }

            public void created()
            {
                setRecordId(242);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[4] = new com.maddox.rts.HotKeyCmd(true, "fov70", "15") {

            public void begin()
            {
                com.maddox.rts.CmdEnv.top().exec("fov 70");
            }

            public void created()
            {
                setRecordId(215);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[5] = new com.maddox.rts.HotKeyCmd(true, "fov65", "16") {

            public void begin()
            {
                com.maddox.rts.CmdEnv.top().exec("fov 65");
            }

            public void created()
            {
                setRecordId(241);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[6] = new com.maddox.rts.HotKeyCmd(true, "fov60", "17") {

            public void begin()
            {
                com.maddox.rts.CmdEnv.top().exec("fov 60");
            }

            public void created()
            {
                setRecordId(240);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[7] = new com.maddox.rts.HotKeyCmd(true, "fov55", "18") {

            public void begin()
            {
                com.maddox.rts.CmdEnv.top().exec("fov 55");
            }

            public void created()
            {
                setRecordId(229);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[8] = new com.maddox.rts.HotKeyCmd(true, "fov50", "19") {

            public void begin()
            {
                com.maddox.rts.CmdEnv.top().exec("fov 50");
            }

            public void created()
            {
                setRecordId(228);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[9] = new com.maddox.rts.HotKeyCmd(true, "fov45", "20") {

            public void begin()
            {
                com.maddox.rts.CmdEnv.top().exec("fov 45");
            }

            public void created()
            {
                setRecordId(227);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[10] = new com.maddox.rts.HotKeyCmd(true, "fov40", "21") {

            public void begin()
            {
                com.maddox.rts.CmdEnv.top().exec("fov 40");
            }

            public void created()
            {
                setRecordId(226);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[11] = new com.maddox.rts.HotKeyCmd(true, "fov35", "22") {

            public void begin()
            {
                com.maddox.rts.CmdEnv.top().exec("fov 35");
            }

            public void created()
            {
                setRecordId(225);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[12] = new com.maddox.rts.HotKeyCmd(true, "fov30", "23") {

            public void begin()
            {
                com.maddox.rts.CmdEnv.top().exec("fov 30");
            }

            public void created()
            {
                setRecordId(214);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[13] = new com.maddox.rts.HotKeyCmd(true, "fovSwitch", "24") {

            public void begin()
            {
                float f = (com.maddox.il2.game.Main3D.FOVX - 30F) * (com.maddox.il2.game.Main3D.FOVX - 30F);
                float f1 = (com.maddox.il2.game.Main3D.FOVX - 70F) * (com.maddox.il2.game.Main3D.FOVX - 70F);
                float f2 = (com.maddox.il2.game.Main3D.FOVX - 90F) * (com.maddox.il2.game.Main3D.FOVX - 90F);
                byte byte0 = 0;
                if(f <= f1)
                    byte0 = 70;
                else
                if(f1 <= f2)
                    byte0 = 90;
                else
                    byte0 = 30;
                new com.maddox.rts.MsgAction(true, new Integer(byte0)) {

                    public void doAction(java.lang.Object obj)
                    {
                        int j = ((java.lang.Integer)obj).intValue();
                        com.maddox.rts.HotKeyCmd.exec("aircraftView", "fov" + j);
                    }

                }
;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[14] = new com.maddox.rts.HotKeyCmd(true, "fovInc", "25") {

            public void begin()
            {
                int j = ((int)((double)com.maddox.il2.game.Main3D.FOVX + 2.5D) / 5) * 5;
                if(j < 30)
                    j = 30;
                if(j > 85)
                    j = 85;
                j += 5;
                new com.maddox.rts.MsgAction(true, new Integer(j)) {

                    public void doAction(java.lang.Object obj)
                    {
                        int k = ((java.lang.Integer)obj).intValue();
                        com.maddox.rts.HotKeyCmd.exec("aircraftView", "fov" + k);
                    }

                }
;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(cmdFov[15] = new com.maddox.rts.HotKeyCmd(true, "fovDec", "26") {

            public void begin()
            {
                int j = ((int)((double)com.maddox.il2.game.Main3D.FOVX + 2.5D) / 5) * 5;
                if(j < 35)
                    j = 35;
                if(j > 90)
                    j = 90;
                j -= 5;
                new com.maddox.rts.MsgAction(true, new Integer(j)) {

                    public void doAction(java.lang.Object obj)
                    {
                        int k = ((java.lang.Integer)obj).intValue();
                        com.maddox.rts.HotKeyCmd.exec("aircraftView", "fov" + k);
                    }

                }
;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "CockpitView", "27") {

            public void begin()
            {
                if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()))
                    return;
                if(com.maddox.il2.ai.World.isPlayerParatrooper())
                    return;
                if(com.maddox.il2.ai.World.getPlayerAircraft().isUnderWater())
                    return;
                com.maddox.il2.game.Main3D.cur3D().setViewInside();
                com.maddox.il2.game.Selector.setCurRecordArg0(com.maddox.il2.ai.World.getPlayerAircraft());
                if(!com.maddox.il2.game.Main3D.cur3D().isDemoPlaying() && com.maddox.il2.ai.World.getPlayerAircraft().netCockpitGetDriver(com.maddox.il2.game.Main3D.cur3D().cockpitCurIndx()) == null)
                    new com.maddox.rts.MsgAction(true, new Integer(com.maddox.il2.game.Main3D.cur3D().cockpitCurIndx())) {

                        public void doAction(java.lang.Object obj)
                        {
                            int j = ((java.lang.Integer)obj).intValue();
                            com.maddox.rts.HotKeyCmd.exec("misc", "cockpitEnter" + j);
                        }

                    }
;
                if(!com.maddox.il2.game.Main3D.cur3D().isDemoPlaying() && !com.maddox.il2.game.Main3D.cur3D().isViewOutside() && isAutoAutopilot() && !com.maddox.il2.game.AircraftHotKeys.isCockpitRealMode(com.maddox.il2.game.Main3D.cur3D().cockpitCurIndx()))
                    new com.maddox.rts.MsgAction(true, new Integer(com.maddox.il2.game.Main3D.cur3D().cockpitCurIndx())) {

                        public void doAction(java.lang.Object obj)
                        {
                            int j = ((java.lang.Integer)obj).intValue();
                            com.maddox.rts.HotKeyCmd.exec("misc", "cockpitRealOn" + j);
                        }

                    }
;
            }

            public void created()
            {
                setRecordId(212);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "CockpitShow", "28") {

            public void created()
            {
                setRecordId(213);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.Cockpit_Always_On)
                    return;
                if(com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                    return;
                if(!(com.maddox.il2.game.Main3D.cur3D().cockpitCur instanceof com.maddox.il2.objects.air.CockpitPilot))
                    return;
                if(com.maddox.il2.game.Main3D.cur3D().isViewInsideShow())
                {
                    com.maddox.il2.game.Main3D.cur3D().hud.bDrawDashBoard = true;
                    com.maddox.il2.game.Main3D.cur3D().setViewInsideShow(false);
                    com.maddox.il2.game.Main3D.cur3D().cockpitCur.setEnableRenderingBall(true);
                } else
                if(com.maddox.il2.game.Main3D.cur3D().hud.bDrawDashBoard && com.maddox.il2.game.Main3D.cur3D().cockpitCur.isEnableRenderingBall())
                    com.maddox.il2.game.Main3D.cur3D().cockpitCur.setEnableRenderingBall(false);
                else
                if(com.maddox.il2.game.Main3D.cur3D().hud.bDrawDashBoard && !com.maddox.il2.game.Main3D.cur3D().cockpitCur.isEnableRenderingBall())
                {
                    com.maddox.il2.game.Main3D.cur3D().hud.bDrawDashBoard = false;
                    com.maddox.il2.game.Main3D.cur3D().cockpitCur.setEnableRenderingBall(true);
                } else
                if(com.maddox.il2.game.Main3D.cur3D().isEnableRenderingCockpit() && com.maddox.il2.game.Main3D.cur3D().cockpitCur.isEnableRenderingBall())
                    com.maddox.il2.game.Main3D.cur3D().cockpitCur.setEnableRenderingBall(false);
                else
                if(com.maddox.il2.game.Main3D.cur3D().isEnableRenderingCockpit() && !com.maddox.il2.game.Main3D.cur3D().cockpitCur.isEnableRenderingBall())
                {
                    com.maddox.il2.game.Main3D.cur3D().setEnableRenderingCockpit(false);
                } else
                {
                    com.maddox.il2.game.Main3D.cur3D().setEnableRenderingCockpit(true);
                    com.maddox.il2.game.Main3D.cur3D().setViewInsideShow(true);
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "OutsideView", "29") {

            public void created()
            {
                setRecordId(205);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views)
                    return;
                java.lang.Object obj = com.maddox.il2.ai.World.getPlayerAircraft();
                com.maddox.il2.game.Selector.setCurRecordArg0(((com.maddox.il2.engine.Actor) (obj)));
                if(!com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (obj))))
                    obj = getViewActor();
                if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (obj))))
                {
                    boolean flag = !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
                    com.maddox.il2.game.Main3D.cur3D().setViewFlow10(((com.maddox.il2.engine.Actor) (obj)), false);
                    if(flag)
                        switchToAIGunner();
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "NextView", "30") {

            public void created()
            {
                setRecordId(206);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views)
                    return;
                com.maddox.il2.engine.Actor actor = nextViewActor(false);
                if(com.maddox.il2.engine.Actor.isValid(actor))
                {
                    boolean flag = !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
                    com.maddox.il2.game.Main3D.cur3D().setViewFlow10(actor, false);
                    if(flag)
                        switchToAIGunner();
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "NextViewEnemy", "31") {

            public void created()
            {
                setRecordId(207);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views)
                    return;
                com.maddox.il2.engine.Actor actor = nextViewActor(true);
                if(com.maddox.il2.engine.Actor.isValid(actor))
                {
                    boolean flag = !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
                    com.maddox.il2.game.Main3D.cur3D().setViewFlow10(actor, false);
                    if(flag)
                        switchToAIGunner();
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "OutsideViewFly", "32") {

            public void created()
            {
                setRecordId(200);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views)
                    return;
                com.maddox.il2.engine.Actor actor = getViewActor();
                if(com.maddox.il2.engine.Actor.isValid(actor) && !(actor instanceof com.maddox.il2.objects.ActorViewPoint) && !(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
                {
                    boolean flag = !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
                    com.maddox.il2.game.Main3D.cur3D().setViewFly(actor);
                    if(flag)
                        switchToAIGunner();
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "PadlockView", "33") {

            public void created()
            {
                setRecordId(217);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Padlock)
                    return;
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                if(!com.maddox.il2.engine.Actor.isValid(aircraft) || com.maddox.il2.ai.World.isPlayerDead() || com.maddox.il2.ai.World.isPlayerParatrooper())
                    return;
                if(com.maddox.il2.game.Main3D.cur3D().isViewPadlock())
                {
                    com.maddox.il2.game.Main3D.cur3D().setViewEndPadlock();
                    com.maddox.il2.game.Selector.setCurRecordArg1(aircraft);
                } else
                {
                    if(com.maddox.il2.game.AircraftHotKeys.bFirstHotCmd && com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()) && !com.maddox.il2.ai.World.isPlayerParatrooper())
                        com.maddox.il2.game.Main3D.cur3D().setViewInside();
                    com.maddox.il2.game.Main3D.cur3D().setViewPadlock(false, false);
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "PadlockViewFriend", "34") {

            public void created()
            {
                setRecordId(218);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Padlock)
                    return;
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                if(!com.maddox.il2.engine.Actor.isValid(aircraft) || com.maddox.il2.ai.World.isPlayerDead() || com.maddox.il2.ai.World.isPlayerParatrooper())
                    return;
                if(com.maddox.il2.game.Main3D.cur3D().isViewPadlock())
                {
                    com.maddox.il2.game.Main3D.cur3D().setViewEndPadlock();
                    com.maddox.il2.game.Selector.setCurRecordArg1(aircraft);
                } else
                {
                    if(com.maddox.il2.game.AircraftHotKeys.bFirstHotCmd && com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()) && !com.maddox.il2.ai.World.isPlayerParatrooper())
                        com.maddox.il2.game.Main3D.cur3D().setViewInside();
                    com.maddox.il2.game.Main3D.cur3D().setViewPadlock(true, false);
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "PadlockViewGround", "35") {

            public void created()
            {
                setRecordId(221);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Padlock)
                    return;
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                if(!com.maddox.il2.engine.Actor.isValid(aircraft) || com.maddox.il2.ai.World.isPlayerDead() || com.maddox.il2.ai.World.isPlayerParatrooper())
                    return;
                if(com.maddox.il2.game.Main3D.cur3D().isViewPadlock())
                {
                    com.maddox.il2.game.Main3D.cur3D().setViewEndPadlock();
                    com.maddox.il2.game.Selector.setCurRecordArg1(aircraft);
                } else
                {
                    if(com.maddox.il2.game.AircraftHotKeys.bFirstHotCmd && com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()) && !com.maddox.il2.ai.World.isPlayerParatrooper())
                        com.maddox.il2.game.Main3D.cur3D().setViewInside();
                    com.maddox.il2.game.Main3D.cur3D().setViewPadlock(false, true);
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "PadlockViewFriendGround", "36") {

            public void created()
            {
                setRecordId(222);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Padlock)
                    return;
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                if(!com.maddox.il2.engine.Actor.isValid(aircraft) || com.maddox.il2.ai.World.isPlayerDead() || com.maddox.il2.ai.World.isPlayerParatrooper())
                    return;
                if(com.maddox.il2.game.Main3D.cur3D().isViewPadlock())
                {
                    com.maddox.il2.game.Main3D.cur3D().setViewEndPadlock();
                    com.maddox.il2.game.Selector.setCurRecordArg1(aircraft);
                } else
                {
                    if(com.maddox.il2.game.AircraftHotKeys.bFirstHotCmd && com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()) && !com.maddox.il2.ai.World.isPlayerParatrooper())
                        com.maddox.il2.game.Main3D.cur3D().setViewInside();
                    com.maddox.il2.game.Main3D.cur3D().setViewPadlock(true, true);
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "PadlockViewNext", "37") {

            public void created()
            {
                setRecordId(223);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Padlock)
                    return;
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                if(!com.maddox.il2.engine.Actor.isValid(aircraft) || com.maddox.il2.ai.World.isPlayerDead() || com.maddox.il2.ai.World.isPlayerParatrooper())
                    return;
                if(com.maddox.il2.game.AircraftHotKeys.bFirstHotCmd)
                {
                    com.maddox.il2.game.Main3D.cur3D().setViewInside();
                    if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.game.Main3D.cur3D().cockpitCur) && com.maddox.il2.game.Main3D.cur3D().cockpitCur.existPadlock())
                        com.maddox.il2.game.Main3D.cur3D().cockpitCur.startPadlock(com.maddox.il2.game.Selector._getTrackArg1());
                } else
                if(com.maddox.il2.game.Main3D.cur3D().isViewPadlock())
                    com.maddox.il2.game.Main3D.cur3D().setViewNextPadlock(true);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "PadlockViewPrev", "38") {

            public void created()
            {
                setRecordId(224);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Padlock)
                    return;
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                if(!com.maddox.il2.engine.Actor.isValid(aircraft) || com.maddox.il2.ai.World.isPlayerDead() || com.maddox.il2.ai.World.isPlayerParatrooper())
                    return;
                if(com.maddox.il2.game.AircraftHotKeys.bFirstHotCmd)
                {
                    com.maddox.il2.game.Main3D.cur3D().setViewInside();
                    if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.game.Main3D.cur3D().cockpitCur) && com.maddox.il2.game.Main3D.cur3D().cockpitCur.existPadlock())
                        com.maddox.il2.game.Main3D.cur3D().cockpitCur.startPadlock(com.maddox.il2.game.Selector._getTrackArg1());
                } else
                if(com.maddox.il2.game.Main3D.cur3D().isViewPadlock())
                    com.maddox.il2.game.Main3D.cur3D().setViewNextPadlock(false);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "PadlockViewForward", "39") {

            public void created()
            {
                setRecordId(220);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Padlock)
                    return;
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                if(!com.maddox.il2.engine.Actor.isValid(aircraft) || com.maddox.il2.ai.World.isPlayerDead() || com.maddox.il2.ai.World.isPlayerParatrooper())
                {
                    return;
                } else
                {
                    com.maddox.il2.game.Main3D.cur3D().setViewPadlockForward(true);
                    return;
                }
            }

            public void end()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Padlock)
                    return;
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                if(!com.maddox.il2.engine.Actor.isValid(aircraft) || com.maddox.il2.ai.World.isPlayerDead() || com.maddox.il2.ai.World.isPlayerParatrooper())
                {
                    return;
                } else
                {
                    com.maddox.il2.game.Main3D.cur3D().setViewPadlockForward(false);
                    return;
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "ViewEnemyAir", "40") {

            public void created()
            {
                setRecordId(203);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views)
                    return;
                com.maddox.il2.engine.Actor actor = getViewActor();
                if(com.maddox.il2.engine.Actor.isValid(actor) && !(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
                {
                    boolean flag = !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
                    com.maddox.il2.game.Main3D.cur3D().setViewEnemy(actor, false, false);
                    if(flag)
                        switchToAIGunner();
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "ViewFriendAir", "41") {

            public void created()
            {
                setRecordId(198);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views)
                    return;
                com.maddox.il2.engine.Actor actor = getViewActor();
                if(com.maddox.il2.engine.Actor.isValid(actor) && !(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
                {
                    boolean flag = !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
                    com.maddox.il2.game.Main3D.cur3D().setViewFriend(actor, false, false);
                    if(flag)
                        switchToAIGunner();
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "ViewEnemyDirectAir", "42") {

            public void created()
            {
                setRecordId(201);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views)
                    return;
                com.maddox.il2.engine.Actor actor = getViewActor();
                if(com.maddox.il2.engine.Actor.isValid(actor) && !(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
                {
                    boolean flag = !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
                    com.maddox.il2.game.Main3D.cur3D().setViewEnemy(actor, true, false);
                    if(flag)
                        switchToAIGunner();
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "ViewEnemyGround", "43") {

            public void created()
            {
                setRecordId(204);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views)
                    return;
                com.maddox.il2.engine.Actor actor = getViewActor();
                if(com.maddox.il2.engine.Actor.isValid(actor) && !(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
                {
                    boolean flag = !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
                    com.maddox.il2.game.Main3D.cur3D().setViewEnemy(actor, false, true);
                    if(flag)
                        switchToAIGunner();
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "ViewFriendGround", "44") {

            public void created()
            {
                setRecordId(199);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views)
                    return;
                com.maddox.il2.engine.Actor actor = getViewActor();
                if(com.maddox.il2.engine.Actor.isValid(actor) && !(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
                {
                    boolean flag = !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
                    com.maddox.il2.game.Main3D.cur3D().setViewFriend(actor, false, true);
                    if(flag)
                        switchToAIGunner();
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "ViewEnemyDirectGround", "45") {

            public void created()
            {
                setRecordId(202);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views)
                    return;
                com.maddox.il2.engine.Actor actor = getViewActor();
                if(com.maddox.il2.engine.Actor.isValid(actor) && !(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
                {
                    boolean flag = !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
                    com.maddox.il2.game.Main3D.cur3D().setViewEnemy(actor, true, true);
                    if(flag)
                        switchToAIGunner();
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "OutsideViewFollow", "46") {

            public void created()
            {
                setRecordId(208);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views)
                    return;
                java.lang.Object obj = com.maddox.il2.ai.World.getPlayerAircraft();
                com.maddox.il2.game.Selector.setCurRecordArg0(((com.maddox.il2.engine.Actor) (obj)));
                if(!com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (obj))))
                    obj = getViewActor();
                if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (obj))))
                {
                    boolean flag = !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
                    com.maddox.il2.game.Main3D.cur3D().setViewFlow10(((com.maddox.il2.engine.Actor) (obj)), true);
                    if(flag)
                        switchToAIGunner();
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "NextViewFollow", "47") {

            public void created()
            {
                setRecordId(209);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views)
                    return;
                com.maddox.il2.engine.Actor actor = nextViewActor(false);
                if(com.maddox.il2.engine.Actor.isValid(actor))
                {
                    boolean flag = !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
                    com.maddox.il2.game.Main3D.cur3D().setViewFlow10(actor, true);
                    if(flag)
                        switchToAIGunner();
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "NextViewEnemyFollow", "48") {

            public void created()
            {
                setRecordId(210);
            }

            public void begin()
            {
                if(com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views)
                    return;
                com.maddox.il2.engine.Actor actor = nextViewActor(true);
                if(com.maddox.il2.engine.Actor.isValid(actor))
                {
                    boolean flag = !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
                    com.maddox.il2.game.Main3D.cur3D().setViewFlow10(actor, true);
                    if(flag)
                        switchToAIGunner();
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "cockpitAim", "49") {

            public void created()
            {
                setRecordId(276);
            }

            public void begin()
            {
                if(com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                    return;
                if(!isMiscValid())
                    return;
                if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.game.Main3D.cur3D().cockpitCur))
                    return;
                if(com.maddox.il2.game.Main3D.cur3D().cockpitCur.isToggleUp())
                {
                    return;
                } else
                {
                    com.maddox.il2.game.Main3D.cur3D().cockpitCur.doToggleAim(!com.maddox.il2.game.Main3D.cur3D().cockpitCur.isToggleAim());
                    return;
                }
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "cockpitUp", "50") {

            public void created()
            {
                setRecordId(281);
            }

            public void begin()
            {
                if(com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                    return;
                if(!isMiscValid())
                    return;
                if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.game.Main3D.cur3D().cockpitCur))
                    return;
                if(com.maddox.il2.game.Main3D.cur3D().cockpitCur.isToggleAim())
                    return;
                if(!com.maddox.il2.ai.World.getPlayerFM().CT.bHasCockpitDoorControl)
                    return;
                if(!com.maddox.il2.game.Main3D.cur3D().cockpitCur.isToggleUp() && (com.maddox.il2.ai.World.getPlayerFM().CT.cockpitDoorControl < 0.5F || com.maddox.il2.ai.World.getPlayerFM().CT.getCockpitDoor() < 0.99F))
                {
                    return;
                } else
                {
                    com.maddox.il2.game.Main3D.cur3D().cockpitCur.doToggleUp(!com.maddox.il2.game.Main3D.cur3D().cockpitCur.isToggleUp());
                    return;
                }
            }

        }
);
    }

    public void createTimeHotKeys()
    {
        java.lang.String s = "timeCompression";
        com.maddox.rts.HotKeyCmdEnv.setCurrentEnv(s);
        com.maddox.rts.HotKeyEnv.fromIni(s, com.maddox.il2.engine.Config.cur.ini, "HotKey " + s);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "timeSpeedUp", "0") {

            public void begin()
            {
                if(com.maddox.il2.game.TimeSkip.isDo())
                    return;
                if(com.maddox.rts.Time.isEnableChangeSpeed())
                {
                    float f = com.maddox.rts.Time.nextSpeed() * 2.0F;
                    if(f <= 8F)
                    {
                        com.maddox.rts.Time.setSpeed(f);
                        showTimeSpeed(f);
                    }
                }
            }

            public void created()
            {
                setRecordId(25);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "timeSpeedNormal", "1") {

            public void begin()
            {
                if(com.maddox.il2.game.TimeSkip.isDo())
                    return;
                if(com.maddox.rts.Time.isEnableChangeSpeed())
                {
                    com.maddox.rts.Time.setSpeed(1.0F);
                    showTimeSpeed(1.0F);
                }
            }

            public void created()
            {
                setRecordId(24);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "timeSpeedDown", "2") {

            public void begin()
            {
                if(com.maddox.il2.game.TimeSkip.isDo())
                    return;
                if(com.maddox.rts.Time.isEnableChangeSpeed())
                {
                    float f = com.maddox.rts.Time.nextSpeed() / 2.0F;
                    if(f >= 0.25F)
                    {
                        com.maddox.rts.Time.setSpeed(f);
                        showTimeSpeed(f);
                    }
                }
            }

            public void created()
            {
                setRecordId(26);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "timeSpeedPause", "3") {

            public void begin()
            {
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "timeSkip", "4") {

            public void begin()
            {
                if(com.maddox.il2.game.TimeSkip.isDo())
                    com.maddox.il2.game.Main3D.cur3D().timeSkip.stop();
                else
                    com.maddox.il2.game.Main3D.cur3D().timeSkip.start();
            }

        }
);
    }

    private void showTimeSpeed(float f)
    {
        int i = java.lang.Math.round(f * 4F);
        switch(i)
        {
        case 4: // '\004'
            com.maddox.il2.game.Main3D.cur3D().hud._log(0, "TimeSpeedNormal");
            break;

        case 8: // '\b'
            com.maddox.il2.game.Main3D.cur3D().hud._log(0, "TimeSpeedUp2");
            break;

        case 16: // '\020'
            com.maddox.il2.game.Main3D.cur3D().hud._log(0, "TimeSpeedUp4");
            break;

        case 32: // ' '
            com.maddox.il2.game.Main3D.cur3D().hud._log(0, "TimeSpeedUp8");
            break;

        case 2: // '\002'
            com.maddox.il2.game.Main3D.cur3D().hud._log(0, "TimeSpeedDown2");
            break;

        case 1: // '\001'
            com.maddox.il2.game.Main3D.cur3D().hud._log(0, "TimeSpeedDown4");
            break;
        }
    }

    public AircraftHotKeys()
    {
        bPropAuto = true;
        bAfterburner = false;
        lastPower = -0.5F;
        lastProp = 1.5F;
        cptdmg = 1;
        bAutoAutopilot = false;
        switchToCockpitRequest = -1;
        cmdFov = new com.maddox.rts.HotKeyCmd[16];
        createPilotHotKeys();
        createPilotHotMoves();
        createGunnerHotKeys();
        createMiscHotKeys();
        create_MiscHotKeys();
        createViewHotKeys();
        createTimeHotKeys();
    }

    private com.maddox.il2.engine.Actor getViewActor()
    {
        if(com.maddox.il2.game.Selector.isEnableTrackArgs())
            return com.maddox.il2.game.Selector.setCurRecordArg0(com.maddox.il2.game.Selector.getTrackArg0());
        com.maddox.il2.engine.Actor actor = com.maddox.il2.game.Main3D.cur3D().viewActor();
        if(isViewed(actor))
            return com.maddox.il2.game.Selector.setCurRecordArg0(actor);
        else
            return com.maddox.il2.game.Selector.setCurRecordArg0(com.maddox.il2.ai.World.getPlayerAircraft());
    }

    private com.maddox.il2.engine.Actor nextViewActor(boolean flag)
    {
        if(com.maddox.il2.game.Selector.isEnableTrackArgs())
            return com.maddox.il2.game.Selector.setCurRecordArg0(com.maddox.il2.game.Selector.getTrackArg0());
        int i = com.maddox.il2.ai.World.getPlayerArmy();
        namedAircraft.clear();
        com.maddox.il2.engine.Actor actor = com.maddox.il2.game.Main3D.cur3D().viewActor();
        if(isViewed(actor))
            namedAircraft.put(actor.name(), null);
        for(java.util.Map.Entry entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(null); entry != null; entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)entry.getValue();
            if(isViewed(actor1) && actor1 != actor)
                if(flag)
                {
                    if(actor1.getArmy() != i)
                        namedAircraft.put(actor1.name(), null);
                } else
                if(actor1.getArmy() == i)
                    namedAircraft.put(actor1.name(), null);
        }

        if(namedAircraft.size() == 0)
            return com.maddox.il2.game.Selector.setCurRecordArg0(null);
        if(!isViewed(actor))
            return com.maddox.il2.game.Selector.setCurRecordArg0((com.maddox.il2.engine.Actor)com.maddox.il2.engine.Engine.name2Actor().get((java.lang.String)namedAircraft.firstKey()));
        if(namedAircraft.size() == 1 && isViewed(actor))
            return com.maddox.il2.game.Selector.setCurRecordArg0(null);
        namedAll = namedAircraft.keySet().toArray(namedAll);
        int j = 0;
        java.lang.String s = actor.name();
        for(; namedAll[j] != null; j++)
            if(s.equals(namedAll[j]))
                break;

        if(namedAll[j] == null)
            return com.maddox.il2.game.Selector.setCurRecordArg0(null);
        j++;
        if(namedAll.length == j || namedAll[j] == null)
            j = 0;
        return com.maddox.il2.game.Selector.setCurRecordArg0((com.maddox.il2.engine.Actor)com.maddox.il2.engine.Engine.name2Actor().get((java.lang.String)namedAll[j]));
    }

    private boolean isViewed(com.maddox.il2.engine.Actor actor)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return false;
        else
            return (actor instanceof com.maddox.il2.objects.air.Aircraft) || (actor instanceof com.maddox.il2.objects.air.Paratrooper) || (actor instanceof com.maddox.il2.objects.ActorViewPoint) || (actor instanceof com.maddox.il2.objects.ships.BigshipGeneric) && ((com.maddox.il2.objects.ships.BigshipGeneric)actor).getAirport() != null;
    }

    public static boolean bFirstHotCmd = false;
    private com.maddox.il2.fm.RealFlightModel FM;
    private boolean bPropAuto;
    private boolean bAfterburner;
    private float lastPower;
    private float lastProp;
    private static final int MOVE_POWER = 1;
    private static final int MOVE_FLAPS = 2;
    private static final int MOVE_AILERON = 3;
    private static final int MOVE_ELEVATOR = 4;
    private static final int MOVE_RUDDER = 5;
    private static final int MOVE_BRAKE = 6;
    private static final int MOVE_STEP = 7;
    private static final int MOVE_TRIMHOR = 8;
    private static final int MOVE_TRIMVER = 9;
    private static final int MOVE_TRIMRUD = 10;
    private int cptdmg;
    private static final int BRAKE = 0;
    private static final int RUDDER_LEFT = 1;
    private static final int RUDDER_RIGHT = 2;
    private static final int ELEVATOR_UP = 3;
    private static final int ELEVATOR_DOWN = 4;
    private static final int AILERON_LEFT = 5;
    private static final int AILERON_RIGHT = 6;
    private static final int RADIATOR = 7;
    private static final int GEAR = 9;
    private static final int GUNPODS = 15;
    private static final int WEAPON0 = 16;
    private static final int WEAPON1 = 17;
    private static final int WEAPON2 = 18;
    private static final int WEAPON3 = 19;
    private static final int WEAPON01 = 64;
    private static final int POWER_0 = 20;
    private static final int POWER_1 = 21;
    private static final int POWER_2 = 22;
    private static final int POWER_3 = 23;
    private static final int POWER_4 = 24;
    private static final int POWER_5 = 25;
    private static final int POWER_6 = 26;
    private static final int POWER_7 = 27;
    private static final int POWER_8 = 28;
    private static final int POWER_9 = 29;
    private static final int POWER_10 = 30;
    private static final int STEP_0 = 31;
    private static final int STEP_1 = 32;
    private static final int STEP_2 = 33;
    private static final int STEP_3 = 34;
    private static final int STEP_4 = 35;
    private static final int STEP_5 = 36;
    private static final int STEP_6 = 37;
    private static final int STEP_7 = 38;
    private static final int STEP_8 = 39;
    private static final int STEP_9 = 40;
    private static final int STEP_10 = 41;
    private static final int STEP_A = 42;
    private static final int AIRCRAFT_TRIM_V_0 = 43;
    private static final int AIRCRAFT_TRIM_V_PLUS = 44;
    private static final int AIRCRAFT_TRIM_V_MINUS = 45;
    private static final int AIRCRAFT_TRIM_H_0 = 46;
    private static final int AIRCRAFT_TRIM_H_PLUS = 47;
    private static final int AIRCRAFT_TRIM_H_MINUS = 48;
    private static final int AIRCRAFT_TRIM_R_0 = 49;
    private static final int AIRCRAFT_TRIM_R_PLUS = 50;
    private static final int AIRCRAFT_TRIM_R_MINUS = 51;
    private static final int FLAPS_NOTCH_UP = 52;
    private static final int FLAPS_NOTCH_DOWN = 53;
    private static final int GEAR_UP_MANUAL = 54;
    private static final int GEAR_DOWN_MANUAL = 55;
    private static final int RUDDER_LEFT_1 = 56;
    private static final int RUDDER_CENTRE = 57;
    private static final int RUDDER_RIGHT_1 = 58;
    private static final int POWER_PLUS_5 = 59;
    private static final int POWER_MINUS_5 = 60;
    private static final int BOOST = 61;
    private static final int AIRCRAFT_DROP_TANKS = 62;
    private static final int AIRCRAFT_TOGGLE_AIRBRAKE = 63;
    private static final int AIRCRAFT_TOGGLE_ENGINE = 70;
    private static final int AIRCRAFT_STABILIZER = 71;
    private static final int AIRCRAFT_TAILWHEELLOCK = 72;
    private static final int STEP_PLUS_5 = 73;
    private static final int STEP_MINUS_5 = 74;
    private static final int MIX_0 = 75;
    private static final int MIX_1 = 76;
    private static final int MIX_2 = 77;
    private static final int MIX_3 = 78;
    private static final int MIX_4 = 79;
    private static final int MIX_5 = 80;
    private static final int MIX_6 = 81;
    private static final int MIX_7 = 82;
    private static final int MIX_8 = 83;
    private static final int MIX_9 = 84;
    private static final int MIX_10 = 85;
    private static final int MIX_PLUS_20 = 86;
    private static final int MIX_MINUS_20 = 87;
    private static final int MAGNETO_PLUS_1 = 88;
    private static final int MAGNETO_MINUS_1 = 89;
    private static final int ENGINE_SELECT_ALL = 90;
    private static final int ENGINE_SELECT_NONE = 91;
    private static final int ENGINE_SELECT_LEFT = 92;
    private static final int ENGINE_SELECT_RIGHT = 93;
    private static final int ENGINE_SELECT_1 = 94;
    private static final int ENGINE_SELECT_2 = 95;
    private static final int ENGINE_SELECT_3 = 96;
    private static final int ENGINE_SELECT_4 = 97;
    private static final int ENGINE_SELECT_5 = 98;
    private static final int ENGINE_SELECT_6 = 99;
    private static final int ENGINE_SELECT_7 = 100;
    private static final int ENGINE_SELECT_8 = 101;
    private static final int ENGINE_TOGGLE_ALL = 102;
    private static final int ENGINE_TOGGLE_LEFT = 103;
    private static final int ENGINE_TOGGLE_RIGHT = 104;
    private static final int ENGINE_TOGGLE_1 = 105;
    private static final int ENGINE_TOGGLE_2 = 106;
    private static final int ENGINE_TOGGLE_3 = 107;
    private static final int ENGINE_TOGGLE_4 = 108;
    private static final int ENGINE_TOGGLE_5 = 109;
    private static final int ENGINE_TOGGLE_6 = 110;
    private static final int ENGINE_TOGGLE_7 = 111;
    private static final int ENGINE_TOGGLE_8 = 112;
    private static final int ENGINE_EXTINGUISHER = 113;
    private static final int ENGINE_FEATHER = 114;
    private static final int COMPRESSORSTEP_PLUS = 115;
    private static final int COMPRESSORSTEP_MINUS = 116;
    private static final int SIGHT_DIST_PLUS = 117;
    private static final int SIGHT_DIST_MINUS = 118;
    private static final int SIGHT_SIDE_RIGHT = 119;
    private static final int SIGHT_SIDE_LEFT = 120;
    private static final int SIGHT_ALT_PLUS = 121;
    private static final int SIGHT_ALT_MINUS = 122;
    private static final int SIGHT_SPD_PLUS = 123;
    private static final int SIGHT_SPD_MINUS = 124;
    private static final int SIGHT_AUTO_ONOFF = 125;
    private static final int AIRCRAFT_DOCK_UNDOCK = 126;
    private static final int WINGFOLD = 127;
    private static final int COCKPITDOOR = 128;
    private static final int AIRCRAFT_CARRIERHOOK = 129;
    private static final int AIRCRAFT_BRAKESHOE = 130;
    public static int hudLogPowerId;
    public static int hudLogWeaponId;
    private boolean bAutoAutopilot;
    private int switchToCockpitRequest;
    private com.maddox.rts.HotKeyCmd cmdFov[];
    private static java.lang.Object namedAll[] = new java.lang.Object[1];
    private static java.util.TreeMap namedAircraft = new TreeMap();













}
