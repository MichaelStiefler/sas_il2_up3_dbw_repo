/*4.10.1 class*/
package com.maddox.il2.builder;
import java.util.ArrayList;
import java.util.HashMap;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.il2.ai.Army;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.objects.buildings.House;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.util.NumberTokenizer;

public class PlMisHouse extends Plugin
{
	//TODO: Edit by |ZUTI|: changed access modifier from private to protected
	protected ArrayList allActors = new ArrayList();
	
	private HashMap allTypes = new HashMap();
	private Mat houseIcon = null;
	Type[] type;
	private Point2d p2d = new Point2d();
	private Point3d p = new Point3d();
	private Orient o = new Orient();
	private ActorSpawnArg spawnArg = new ActorSpawnArg();
	private PlMission pluginMission;
	private int startComboBox1;
	private boolean bView = true;
	private ViewType viewCheckBox;
	HashMap viewClasses = new HashMap();
	private String[] _actorInfo = new String[1];
	GWindowTabDialogClient.Tab tabActor;
	GWindowLabel wName;
	GWindowComboControl wArmy;
	/* synthetic */static Class class$com$maddox$il2$builder$PlMisHouse;
	
	class ViewType extends GWindowMenuItem
	{
		public void execute()
		{
			bChecked = !bChecked;
			viewTypeAll(bChecked);
		}
		
		public ViewType(GWindowMenu gwindowmenu, String string, String string_0_)
		{
			super(gwindowmenu, string, string_0_);
		}
	}
	
	static class Type
	{
		public int indx;
		public String name;
		public ActorSpawn spawn;
		public String fullClassName;
		public String shortClassName;
		
		public Type(int i, String string, String string_1_)
		{
			indx = i;
			name = string;
			fullClassName = string_1_;
			shortClassName = fullClassName.substring("com.maddox.il2.objects.buildings.".length());
			spawn = (ActorSpawn)Spawn.get_WithSoftClass(fullClassName);
		}
	}
	
	public void mapLoaded()
	{
		deleteAll();
	}
	
	public void deleteAll()
	{
		for (int i = 0; i < allActors.size(); i++)
		{
			Actor actor = (Actor)allActors.get(i);
			if (Actor.isValid(actor))
				actor.destroy();
		}
		allActors.clear();
	}
	
	public void delete(Actor actor)
	{
		allActors.remove(actor);
		actor.destroy();
	}
	
	public void renderMap2D()
	{
		if (!builder.isFreeView() && bView)
		{
			Actor actor = builder.selectedActor();
			Render.prepareStates();
			for (int i = 0; i < allActors.size(); i++)
			{
				Actor actor_2_ = (Actor)allActors.get(i);
				if (Actor.isValid(actor_2_) && actor_2_.icon != null && builder.project2d(actor_2_.pos.getAbsPoint(), p2d))
				{
					int i_3_ = actor_2_.getArmy();
					boolean bool = builder.conf.bShowArmy[i_3_];
					if (bool)
					{
						int i_4_;
						if (builder.isMiltiSelected(actor_2_))
							i_4_ = (Builder.colorMultiSelected(Army.color(actor_2_.getArmy())));
						else if (actor_2_ == actor)
							i_4_ = Builder.colorSelected();
						else
							i_4_ = Army.color(actor_2_.getArmy());
						IconDraw.setColor(i_4_);
						IconDraw.render(actor_2_, p2d.x, p2d.y);
					}
				}
			}
		}
	}
	
	public void load(SectFile sectfile)
	{
		int i = sectfile.sectionIndex("Buildings");
		if (i >= 0)
		{
			int i_5_ = sectfile.vars(i);
			for (int i_6_ = 0; i_6_ < i_5_; i_6_++)
			{
				NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, i_6_));
				numbertokenizer.next("");
				insert(("com.maddox.il2.objects.buildings." + numbertokenizer.next("")), numbertokenizer.next(1) == 1, numbertokenizer.next(0.0), numbertokenizer.next(0.0), numbertokenizer.next(0.0F), false);
			}
		}
	}
	
	public boolean save(SectFile sectfile)
	{
		Orient orient = new Orient();
		int i = sectfile.sectionAdd("Buildings");
		for (int i_7_ = 0; i_7_ < allActors.size(); i_7_++)
		{
			Actor actor = (Actor)allActors.get(i_7_);
			if (Actor.isValid(actor))
			{
				Point3d point3d = actor.pos.getAbsPoint();
				Orient orient_8_ = actor.pos.getAbsOrient();
				orient.set(orient_8_);
				orient.wrap360();
				Type type = (Type)Property.value(actor, "builderType", null);
				sectfile.lineAdd(i, i_7_ + "_bld", (type.shortClassName + " " + (actor.isAlive() ? "1 " : "0 ") + formatPos(point3d.x, point3d.y, orient.azimut())));
			}
		}
		return true;
	}
	
	private String formatPos(double d, double d_9_, float f)
	{
		return (formatValue(d) + " " + formatValue(d_9_) + " " + formatValue((double)f));
	}
	
	private String formatValue(double d)
	{
		boolean bool = d < 0.0;
		if (bool)
			d = -d;
		double d_10_ = d + 0.0050 - (double)(int)d;
		if (d_10_ >= 0.1)
			return (bool ? "-" : "") + (int)d + "." + (int)(d_10_ * 100.0);
		return (bool ? "-" : "") + (int)d + ".0" + (int)(d_10_ * 100.0);
	}
	
	private Actor insert(String string, boolean bool, double d, double d_11_, float f, boolean bool_12_)
	{
		ActorSpawn actorspawn = (ActorSpawn)Spawn.get_WithSoftClass(string, false);
		if (actorspawn == null)
		{
			builder.tipErr("PlMisHouse: ActorSpawn for '" + string + "' not found");
			return null;
		}
		spawnArg.clear();
		p.set(d, d_11_, 0.0);
		spawnArg.point = p;
		o.set(f, 0.0F, 0.0F);
		spawnArg.orient = o;
		Actor actor;
		try
		{
			Actor actor_13_ = actorspawn.actorSpawn(spawnArg);
			if (!bool)
				actor_13_.setDiedFlag(true);
			Property.set(actor_13_, "builderSpawn", "");
			Property.set(actor_13_, "builderPlugin", this);
			Type type = (Type)allTypes.get(string);
			Property.set(actor_13_, "builderType", type);
			actor_13_.icon = houseIcon;
			allActors.add(actor_13_);
			builder.align(actor_13_);
			if (bool_12_)
				builder.setSelected(actor_13_);
			PlMission.setChanged();
			actor = actor_13_;
		}
		catch (Exception exception)
		{
			System.out.println(exception.getMessage());
			exception.printStackTrace();
			return null;
		}
		return actor;
	}
	
	private Actor insert(Type type, Loc loc, boolean bool)
	{
		spawnArg.clear();
		spawnArg.point = loc.getPoint();
		spawnArg.orient = loc.getOrient();
		return insert(type.fullClassName, true, loc.getPoint().x, loc.getPoint().y, loc.getOrient().getAzimut(), bool);
	}
	
	public void insert(Loc loc, boolean bool)
	{
		int i = builder.wSelect.comboBox1.getSelected();
		int i_14_ = builder.wSelect.comboBox2.getSelected();
		if (i == startComboBox1 && (i_14_ >= 0 && i_14_ < type.length))
			insert(type[i_14_], loc, bool);
	}
	
	public void changeType()
	{
		int i_15_ = builder.wSelect.comboBox2.getSelected();
		Actor actor = builder.selectedActor();
		Loc loc = actor.pos.getAbs();
		insert(type[i_15_], loc, true);
		if (builder.selectedActor() != actor)
		{
			allActors.remove(actor);
			actor.destroy();
			PlMission.setChanged();
		}
	}
	
	public void changeType(boolean bool, boolean bool_17_)
	{
		if (!bool_17_ && builder.wSelect.comboBox1.getSelected() == startComboBox1)
		{
			int i = builder.wSelect.comboBox2.getSelected();
			if (bool)
			{
				if (++i >= type.length)
					i = 0;
			}
			else if (--i < 0)
				i = type.length - 1;
			Actor actor = builder.selectedActor();
			Loc loc = actor.pos.getAbs();
			insert(type[i], loc, true);
			if (builder.selectedActor() != actor)
			{
				allActors.remove(actor);
				actor.destroy();
				PlMission.setChanged();
			}
			fillComboBox2(startComboBox1, i);
		}
	}
	
	public void configure()
	{
		if (getPlugin("Mission") == null)
			throw new RuntimeException("PlMisHouse: plugin 'Mission' not found");
		pluginMission = (PlMission)getPlugin("Mission");
		if (sectFile == null)
			throw new RuntimeException("PlMisHouse: field 'sectFile' not defined");
		SectFile sectfile = new SectFile(sectFile, 0);
		int i = sectfile.sections();
		if (i <= 0)
			throw new RuntimeException("PlMisHouse: file '" + sectFile + "' is empty");
		Type[] types = new Type[i];
		int i_19_ = 0;
		for (int i_20_ = 0; i_20_ < i; i_20_++)
		{
			String string = sectfile.sectionName(i_20_);
			if (string.indexOf("House$") >= 0)
			{
				String string_21_ = string;
				String string_22_ = "";
				int i_23_ = string.lastIndexOf('$');
				if (i_23_ >= 0)
				{
					string_21_ = string.substring(0, i_23_);
					string_22_ = string.substring(i_23_ + 1);
				}
				Class var_class;
				try
				{
					var_class = ObjIO.classForName(string_21_);
				}
				catch (Exception exception)
				{
					throw new RuntimeException("PlMisHouse: class '" + string_21_ + "' not found");
				}
				if (i_23_ >= 0)
					string = var_class.getName() + "$" + string_22_;
				else
					string = var_class.getName();
				types[i_19_] = new Type(i_19_, i18n("building") + " " + i_19_, string);
				allTypes.put(string, types[i_19_]);
				i_19_++;
			}
		}
		type = new Type[i_19_];
		for (int i_24_ = 0; i_24_ < i_19_; i_24_++)
		{
			type[i_24_] = types[i_24_];
			types[i_24_] = null;
		}
		houseIcon = IconDraw.get("icons/objHouse.mat");
	}
	
	void viewUpdate()
	{
		for (int i = 0; i < allActors.size(); i++)
		{
			Actor actor = (Actor)allActors.get(i);
			if (Actor.isValid(actor))
				actor.drawing(bView);
		}
		if (Actor.isValid(builder.selectedActor()) && !builder.selectedActor().isDrawing())
			builder.setSelected(null);
		if (!builder.isFreeView())
			builder.repaint();
	}
	
	public void viewTypeAll(boolean bool)
	{
		bView = bool;
		viewCheckBox.bChecked = bView;
		viewUpdate();
	}
	
	private void fillComboBox1()
	{
		startComboBox1 = builder.wSelect.comboBox1.size();
		builder.wSelect.comboBox1.add(i18n("buildings"));
		if (startComboBox1 == 0)
			builder.wSelect.comboBox1.setSelected(0, true, false);
	}
	
	private void fillComboBox2(int i, int i_25_)
	{
		if (i == startComboBox1)
		{
			if (builder.wSelect.curFilledType != i)
			{
				builder.wSelect.curFilledType = i;
				builder.wSelect.comboBox2.clear(false);
				for (int i_26_ = 0; i_26_ < type.length; i_26_++)
					builder.wSelect.comboBox2.add(type[i_26_].name);
				builder.wSelect.comboBox1.setSelected(i, true, false);
			}
			builder.wSelect.comboBox2.setSelected(i_25_, true, false);
			fillComboBox2Render(i, i_25_);
		}
	}
	
	private void fillComboBox2Render(int i, int i_27_)
	{
		try
		{
			Type type = this.type[i_27_];
			House.SPAWN spawn = (House.SPAWN)type.spawn;
			builder.wSelect.setMesh(spawn.prop.MESH0_NAME, true);
		}
		catch (Exception exception)
		{
			builder.wSelect.setMesh(null, true);
		}
	}
	
	public String[] actorInfo(Actor actor)
	{
		Type type = (Type)Property.value(actor, "builderType", null);
		if (type != null)
		{
			_actorInfo[0] = type.name;
			return _actorInfo;
		}
		return null;
	}
	
	public void syncSelector()
	{
		Actor actor = builder.selectedActor();
		Type type = (Type)Property.value(actor, "builderType", null);
		if (type != null)
			fillComboBox2(startComboBox1, type.indx);
	}
	
	public void createGUI()
	{
		fillComboBox1();
		fillComboBox2(0, 0);
		builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener()
		{
			public boolean notify(GWindow gwindow, int i, int i_29_)
			{
				int i_30_ = Plugin.builder.wSelect.comboBox1.getSelected();
				if (i_30_ >= 0 && i == 2)
					PlMisHouse.this.fillComboBox2(i_30_, 0);
				return false;
			}
		});
		builder.wSelect.comboBox2.addNotifyListener(new GNotifyListener()
		{
			public boolean notify(GWindow gwindow, int i, int i_32_)
			{
				if (i != 2)
					return false;
				int i_33_ = Plugin.builder.wSelect.comboBox1.getSelected();
				if (i_33_ != startComboBox1)
					return false;
				int i_34_ = Plugin.builder.wSelect.comboBox2.getSelected();
				PlMisHouse.this.fillComboBox2Render(i_33_, i_34_);
				return false;
			}
		});
		int i;
		for (i = builder.mDisplayFilter.subMenu.size() - 1; i >= 0 && (pluginMission.viewBridge != builder.mDisplayFilter.subMenu.getItem(i)); i--)
		{
			/* empty */
		}
		if (--i >= 0)
		{
			if ("de".equals(RTSConf.cur.locale.getLanguage()))
				viewCheckBox = ((ViewType)(builder.mDisplayFilter.subMenu.addItem(i, new ViewType(builder.mDisplayFilter.subMenu, i18n("buildings") + " " + i18n("show"), null))));
			else
				viewCheckBox = ((ViewType)(builder.mDisplayFilter.subMenu.addItem(i, new ViewType(builder.mDisplayFilter.subMenu, i18n("show") + " " + i18n("buildings"), null))));
			viewTypeAll(true);
		}
	}
	
	public String mis_getProperties(Actor actor)
	{
		String string = "";
		int i = builder.wSelect.comboBox1.getSelected();
		int i_36_ = builder.wSelect.comboBox2.getSelected();
		if (i != startComboBox1)
			return string;
		if (i_36_ < 0 || i_36_ >= this.type.length)
			return string;
		Orient orient = new Orient();
		Point3d point3d = actor.pos.getAbsPoint();
		Orient orient_37_ = actor.pos.getAbsOrient();
		orient.set(orient_37_);
		orient.wrap360();
		Type type = (Type)Property.value(actor, "builderType", null);
		string = ("1_bld " + type.shortClassName + " " + (actor.isAlive() ? "1 " : "0 ") + formatPos(point3d.x, point3d.y, orient.azimut()));
		return string;
	}
	
	public Actor mis_insert(Loc loc, String string)
	{
		int i = builder.wSelect.comboBox1.getSelected();
		int i_38_ = builder.wSelect.comboBox2.getSelected();
		if (i != startComboBox1)
			return null;
		if (i_38_ < 0 || i_38_ >= type.length)
			return null;
		NumberTokenizer numbertokenizer = new NumberTokenizer(string);
		numbertokenizer.next("");
		String string_39_ = numbertokenizer.next("");
		i = numbertokenizer.next(1);
		numbertokenizer.next(0.0);
		numbertokenizer.next(0.0);
		Actor actor = insert("com.maddox.il2.objects.buildings." + string_39_, i == 1, loc.getPoint().x, loc.getPoint().y, numbertokenizer.next(0.0F), false);
		return actor;
	}
	
	public boolean mis_validateSelected(int i, int i_40_)
	{
		if (i != startComboBox1)
			return false;
		if (i_40_ < 0 || i_40_ >= type.length)
			return false;
		return true;
	}
	
	static Class class$ZutiPlMisHouse(String string)
	{
		Class var_class;
		try
		{
			var_class = Class.forName(string);
		}
		catch (ClassNotFoundException classnotfoundexception)
		{
			throw new NoClassDefFoundError(classnotfoundexception.getMessage());
		}
		return var_class;
	}
	
	static
	{
		Property.set((class$com$maddox$il2$builder$PlMisHouse == null ? (class$com$maddox$il2$builder$PlMisHouse = class$ZutiPlMisHouse("com.maddox.il2.builder.PlMisHouse")) : class$com$maddox$il2$builder$PlMisHouse), "name", "MisHouse");
	}
}
