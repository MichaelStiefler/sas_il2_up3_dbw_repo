// Source File Name: BombMk25Marker.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.objects.ActorLand;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class BombMk25Marker extends Bomb {

	private long t1;

	private long t2;

	static {
		Class class1 = com.maddox.il2.objects.weapons.BombMk25Marker.class;
		Property.set(class1, "mesh", "3DO/Arms/Mk25_Marker/mono.sim");
		Property.set(class1, "radius", 0.0F);
		Property.set(class1, "power", 0.0F);
		Property.set(class1, "powerType", 2);
		Property.set(class1, "kalibr", 0.15F);
		Property.set(class1, "massa", 10F);
		Property.set(class1, "sound", "weapon.bomb_mid");
	}

	public BombMk25Marker() {
	}

	public void msgCollision(Actor actor, String s, String s1) {
		if (actor instanceof ActorLand) {
			if (Time.current() <= ((this.t2 + this.t1) / 2L)) {
				Point3d point3d = new Point3d();
				this.pos.getTime(Time.current(), point3d);
				Class class1 = this.getClass();
				float f = Property.floatValue(class1, "power", 0.0F);
				int i = Property.intValue(class1, "powerType", 0);
				float f1 = Property.floatValue(class1, "radius", 1.0F);
				MsgExplosion.send(actor, s1, point3d, this.getOwner(), this.M,
						f, i, f1);
				Vector3d vector3d = new Vector3d();
				this.getSpeed(vector3d);
				vector3d.x *= 0.5D;
				vector3d.y *= 0.5D;
				vector3d.z = 1.0D;
				this.setSpeed(vector3d);
			}
		} else {
			super.msgCollision(actor, s, s1);
		}
	}

	public void start() {
		super.start();
		this.drawing(false);
		this.t1 = Time.current() + 0x1c3a90L + World.Rnd().nextLong(0L, 850L);
		this.t2 = Time.current() + 0x1c3a90L + World.Rnd().nextLong(0L, 3800L);
		Eff3DActor.New(this, null, new Loc(), 1.0F,
				"3DO/Effects/Smoke/OrangeSmoke.eff",
				(this.t1 - Time.current()) / 1000F);
	}
}
