// Source File Name: BombMk24Flare.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Chute;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class BombMk24Flare extends Bomb {

	private Chute chute;

	private boolean bOnChute;

	private static Vector3d v3d = new Vector3d();

	private float ttcurTM;

	static {
		Class class1 = com.maddox.il2.objects.weapons.BombMk24Flare.class;
		Property.set(class1, "mesh", "3DO/Arms/Mk24_Flare/mono.sim");
		Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 1.0F));
		Property.set(class1, "emitLen", 250F);
		Property.set(class1, "emitMax", 10F);
		Property.set(class1, "radius", 75F);
		Property.set(class1, "power", 0.0F);
		Property.set(class1, "powerType", 1);
		Property.set(class1, "kalibr", 0.2F);
		Property.set(class1, "massa", 8F);
		Property.set(class1, "sound", "weapon.bomb_phball");
	}

	public BombMk24Flare() {
		this.chute = null;
		this.bOnChute = false;
	}

	public void destroy() {
		if (this.chute != null) {
			this.chute.destroy();
		}
		super.destroy();
	}

	protected boolean haveSound() {
		return false;
	}

	public void interpolateTick() {
		super.interpolateTick();
		if (this.bOnChute) {
			this.getSpeed(v3d);
			v3d.scale(0.96999999999999997D);
			if (v3d.z < -2D) {
				v3d.z += 1.1F * Time.tickConstLenFs();
			}
			this.setSpeed(v3d);
		} else if (this.curTm > this.ttcurTM) {
			this.bOnChute = true;
			this.chute = new Chute(this);
			this.chute.collide(false);
			this.chute.mesh().setScale(0.5F);
			this.chute.pos.setRel(new Point3d(0.5D, 0.0D, 0.0D), new Orient(
					0.0F, 90F, 0.0F));
		}
	}

	public void msgCollision(Actor actor, String s, String s1) {
		if ((actor instanceof ActorLand) && (this.chute != null)) {
			this.chute.landing();
		}
		super.msgCollision(actor, s, s1);
	}

	public void start() {
		super.start();
		this.ttcurTM = World.Rnd().nextFloat(0.5F, 1.75F);
	}
}
