// Source File Name: PaintSchemeBMPar03B25.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.Regiment;

public class PaintSchemeBMPar03B25 extends PaintScheme {

	public PaintSchemeBMPar03B25() {
	}

	public void prepareNum(Class class1, HierMesh hiermesh, Regiment regiment,
			int i, int j, int k) {
		super.prepareNum(class1, hiermesh, regiment, i, j, k);
		int l = regiment.gruppeNumber() - 1;
		if (regiment.country() == PaintScheme.countryGermany) {
			k = this.clampToLiteral(k);
			this.changeMat(hiermesh, "Overlay1",
					"psBM03GERREGI" + regiment.id(), "German/"
							+ regiment.aid()[0] + ".tga",
					"German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F,
					0.1F, 0.1F, 0.1F);
			this.changeMat(hiermesh, "Overlay3",
					"psBM03GERREGI" + regiment.id(), "German/"
							+ regiment.aid()[0] + ".tga",
					"German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F,
					0.1F, 0.1F, 0.1F);
			this.changeMat(hiermesh, "Overlay2", "psBM03GERCNUM" + l + i
					+ (k < 10 ? "0" + k : "" + k), "German/"
					+ (char) (65 + (k - 1)) + ".tga", "German/"
					+ PaintScheme.psGermanBomberLetter[l][i] + ".tga",
					PaintScheme.psGermanBomberColor[i][0],
					PaintScheme.psGermanBomberColor[i][1],
					PaintScheme.psGermanBomberColor[i][2], 0.1F, 0.1F, 0.1F);
			this.changeMat(hiermesh, "Overlay4", "psBM03GERCNUM" + l + i
					+ (k < 10 ? "0" + k : "" + k), "German/"
					+ (char) (65 + (k - 1)) + ".tga", "German/"
					+ PaintScheme.psGermanBomberLetter[l][i] + ".tga",
					PaintScheme.psGermanBomberColor[i][0],
					PaintScheme.psGermanBomberColor[i][1],
					PaintScheme.psGermanBomberColor[i][2], 0.1F, 0.1F, 0.1F);
			this.changeMat(class1, hiermesh, "Overlay6", "balken2",
					"German/balken2.tga", 1.0F, 1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay7", "balken2",
					"German/balken2.tga", 1.0F, 1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay8", "haken3", "German/"
					+ (World.cur().isHakenAllowed() ? "haken3.tga"
							: "hakenfake.tga"), 0.945F, 0.929F, 0.886F);
			return;
		}
		if (regiment.country() == PaintScheme.countryNetherlands) {
			this.changeMat(class1, hiermesh, "Overlay6", "DutchTriangle",
					"Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay7", "DutchTriangle",
					"Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
			this.changeMat(hiermesh, "Overlay1", "psBM00DUTCNUM"
					+ (k < 10 ? "0" + k : "" + k), "German/" + (k / 10)
					+ ".tga", "German/" + (k % 10) + ".tga", 0.1F, 0.1F, 0.1F,
					0.1F, 0.1F, 0.1F);
			this.changeMat(hiermesh, "Overlay4", "psBM00DUTCNUM"
					+ (k < 10 ? "0" + k : "" + k), "German/" + (k / 10)
					+ ".tga", "German/" + (k % 10) + ".tga", 0.1F, 0.1F, 0.1F,
					0.1F, 0.1F, 0.1F);
			return;
		}
		if (regiment.country() == PaintScheme.countryFinland) {
			char c = (char) (48 + (k % 10));
			this.changeMat(class1, hiermesh, "Overlay6", "FAFhaken", "Finnish/"
					+ (World.cur().isHakenAllowed() ? "FAFhaken.tga"
							: "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay7", "FAFhaken", "Finnish/"
					+ (World.cur().isHakenAllowed() ? "FAFhaken.tga"
							: "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
			if (k < 10) {
				this.changeMat(class1, hiermesh, "Overlay8", "psBM03FINANUM"
						+ l + i + "0" + k, "Finnish/0" + k + ".tga",
						PaintScheme.psFinnishFighterColor[i][0],
						PaintScheme.psFinnishFighterColor[i][1],
						PaintScheme.psFinnishFighterColor[i][2]);
			} else {
				this.changeMat(hiermesh, "Overlay8", "psBM03FINCNUM" + l + i
						+ k, "Finnish/" + (k / 10) + ".tga", "Finnish/"
						+ (k % 10) + ".tga",
						PaintScheme.psFinnishFighterColor[i][0],
						PaintScheme.psFinnishFighterColor[i][1],
						PaintScheme.psFinnishFighterColor[i][2],
						PaintScheme.psFinnishFighterColor[i][0],
						PaintScheme.psFinnishFighterColor[i][1],
						PaintScheme.psFinnishFighterColor[i][2]);
			}
			String s = this.getFAFACCode(class1, i);
			this.changeMat(hiermesh, "Overlay2", "psBM03FINACOD" + s + c,
					"Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F,
					0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
			this.changeMat(hiermesh, "Overlay3", "psBM03FINACOD" + s + c,
					"Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F,
					0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
			return;
		}
		if (regiment.country() == PaintScheme.countryFrance) {
			if (k < 10) {
				this.changeMat(class1, hiermesh, "Overlay2", "psFB03FRALNUM"
						+ l + i + k, "Finnish/" + (k % 10) + ".tga", 1.0F,
						1.0F, 1.0F);
				this.changeMat(hiermesh, "Overlay3", "psFB03FRARNUM" + l + i
						+ k, "null.tga", "Finnish/" + (k % 10) + ".tga", 1.0F,
						1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
			} else {
				this.changeMat(hiermesh, "Overlay2", "psFB03FRACNUM" + l + i
						+ k, "Finnish/" + (k / 10) + ".tga", "Finnish/"
						+ (k % 10) + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
				this.changeMat(hiermesh, "Overlay3", "psFB03FRACNUM" + l + i
						+ k, "Finnish/" + (k / 10) + ".tga", "Finnish/"
						+ (k % 10) + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
			}
			this.changeMat(class1, hiermesh, "Overlay6", "frenchroundel",
					"French/roundel.tga", 1.0F, 1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay7", "frenchroundel",
					"French/roundel.tga", 1.0F, 1.0F, 1.0F);
		}
		if (regiment.country() == PaintScheme.countryBritain) {
			if ("ra".equals((regiment.branch()))
					|| "rz".equals((regiment.branch()))
					|| "rn".equals((regiment.branch()))) {
				k = this.clampToLiteral(k);
				this.changeMat(class1, hiermesh, "Overlay1", "null",
						"null.tga", 1.0F, 1.0F, 1.0F);
				this.changeMat(class1, hiermesh, "Overlay4", "null",
						"null.tga", 1.0F, 1.0F, 1.0F);
			} else {
				k = this.clampToLiteral(k);
				this.changeMat(class1, hiermesh, "Overlay1", "null",
						"null.tga", 1.0F, 1.0F, 1.0F);
				this.changeMat(class1, hiermesh, "Overlay4", "null",
						"null.tga", 1.0F, 1.0F, 1.0F);
			}
			return;
		}
		if (regiment.country() == PaintScheme.countryHungary) {
			this.changeMat(hiermesh, "Overlay1",
					"psBM03HUNREGI" + regiment.id(), "German/"
							+ regiment.aid()[0] + ".tga",
					"German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F,
					0.1F, 0.1F, 0.1F);
			this.changeMat(hiermesh, "Overlay3",
					"psBM03HUNREGI" + regiment.id(), "German/"
							+ regiment.aid()[0] + ".tga",
					"German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F,
					0.1F, 0.1F, 0.1F);
			this.changeMat(hiermesh, "Overlay2", "psBM03HUNCNUM" + l + i
					+ (k < 10 ? "0" + k : "" + k), "German/" + (k / 10)
					+ ".tga", "German/" + (k % 10) + ".tga", 0.1F, 0.1F, 0.1F,
					0.1F, 0.1F, 0.1F);
			this.changeMat(hiermesh, "Overlay4", "psBM03HUNCNUM" + l + i
					+ (k < 10 ? "0" + k : "" + k), "German/" + (k / 10)
					+ ".tga", "German/" + (k % 10) + ".tga", 0.1F, 0.1F, 0.1F,
					0.1F, 0.1F, 0.1F);
			this.changeMat(class1, hiermesh, "Overlay6",
					"hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F,
					1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay7",
					"hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F,
					1.0F, 1.0F);
			return;
		}
		if (regiment.country() == PaintScheme.countryItaly) {
			if (k < 10) {
				this.changeMat(class1, hiermesh, "Overlay2", "psFB03ITALNUM"
						+ l + i + k, "Russian/1" + (k % 10) + ".tga", 1.0F,
						1.0F, 1.0F);
				this.changeMat(hiermesh, "Overlay3", "psFB03ITARNUM" + l + i
						+ k, "null.tga", "Russian/1" + (k % 10) + ".tga", 1.0F,
						1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
			} else {
				this.changeMat(hiermesh, "Overlay2", "psFB03ITACNUM" + l + i
						+ k, "Russian/1" + (k / 10) + ".tga", "Russian/1"
						+ (k % 10) + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
				this.changeMat(hiermesh, "Overlay3", "psFB03ITACNUM" + l + i
						+ k, "Russian/1" + (k / 10) + ".tga", "Russian/1"
						+ (k % 10) + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
			}
			this.changeMat(class1, hiermesh, "Overlay6", "italian3",
					"Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
		}
		if (regiment.country() == PaintScheme.countryJapan) {
			this.changeMat(hiermesh, "Overlay2", "psBM03JAPCNUM" + l + i
					+ (k < 10 ? "0" + k : "" + k), "Russian/1" + (k / 10)
					+ ".tga", "Russian/1" + (k % 10) + ".tga",
					PaintScheme.psRussianBomberColor[0][0],
					PaintScheme.psRussianBomberColor[0][1],
					PaintScheme.psRussianBomberColor[0][2],
					PaintScheme.psRussianBomberColor[0][0],
					PaintScheme.psRussianBomberColor[0][1],
					PaintScheme.psRussianBomberColor[0][2]);
			this.changeMat(hiermesh, "Overlay3", "psBM03JAPCNUM" + l + i
					+ (k < 10 ? "0" + k : "" + k), "Russian/1" + (k / 10)
					+ ".tga", "Russian/1" + (k % 10) + ".tga",
					PaintScheme.psRussianBomberColor[0][0],
					PaintScheme.psRussianBomberColor[0][1],
					PaintScheme.psRussianBomberColor[0][2],
					PaintScheme.psRussianBomberColor[0][0],
					PaintScheme.psRussianBomberColor[0][1],
					PaintScheme.psRussianBomberColor[0][2]);
			this.changeMat(class1, hiermesh, "Overlay6", "JAR1",
					"Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay7", "JAR1",
					"Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
		}
		if (regiment.country() == PaintScheme.countryPoland) {
			this.changeMat(hiermesh, "Overlay1", "psBM03POLCNUM" + l + i
					+ (k < 10 ? "0" + k : "" + k), "Russian/1" + (k / 10)
					+ ".tga", "Russian/1" + (k % 10) + ".tga", 1.0F, 1.0F,
					1.0F, 1.0F, 1.0F, 1.0F);
			this.changeMat(hiermesh, "Overlay4", "psBM03POLCNUM" + l + i
					+ (k < 10 ? "0" + k : "" + k), "Russian/1" + (k / 10)
					+ ".tga", "Russian/1" + (k % 10) + ".tga", 1.0F, 1.0F,
					1.0F, 1.0F, 1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay7", "polishcheckerboard",
					"Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay8", "polishcheckerboard",
					"Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
			return;
		}
		if (regiment.country() == PaintScheme.countryRomania) {
			this.changeMat(hiermesh, "Overlay8", "psFB03ROMCNUM" + l + i
					+ (k < 10 ? "0" + k : "" + k), "Russian/1" + (k / 10)
					+ ".tga", "Russian/1" + (k % 10) + ".tga", 1.0F, 1.0F,
					1.0F, 1.0F, 1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay6", "romaniancross",
					"Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay7", "romaniancross",
					"Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
			return;
		}
		if (regiment.country() == PaintScheme.countryRussia) {
			if (k < 10) {
				this.changeMat(class1, hiermesh, "Overlay2", "psBM03RUSLNUM"
						+ l + i + "0" + k, "Russian/1" + k + ".tga",
						PaintScheme.psRussianBomberColor[i][0],
						PaintScheme.psRussianBomberColor[i][1],
						PaintScheme.psRussianBomberColor[i][2]);
				this.changeMat(hiermesh, "Overlay3", "psBM03RUSRNUM" + l + i
						+ "0" + k, "null.tga", "Russian/1" + k + ".tga", 1.0F,
						1.0F, 1.0F, PaintScheme.psRussianBomberColor[i][0],
						PaintScheme.psRussianBomberColor[i][1],
						PaintScheme.psRussianBomberColor[i][2]);
			} else {
				this.changeMat(hiermesh, "Overlay2", "psBM03RUSCNUM" + l + i
						+ k, "Russian/1" + (k / 10) + ".tga", "Russian/1"
						+ (k % 10) + ".tga",
						PaintScheme.psRussianBomberColor[i][0],
						PaintScheme.psRussianBomberColor[i][1],
						PaintScheme.psRussianBomberColor[i][2],
						PaintScheme.psRussianBomberColor[i][0],
						PaintScheme.psRussianBomberColor[i][1],
						PaintScheme.psRussianBomberColor[i][2]);
				this.changeMat(hiermesh, "Overlay3", "psBM03RUSCNUM" + l + i
						+ k, "Russian/1" + (k / 10) + ".tga", "Russian/1"
						+ (k % 10) + ".tga",
						PaintScheme.psRussianBomberColor[i][0],
						PaintScheme.psRussianBomberColor[i][1],
						PaintScheme.psRussianBomberColor[i][2],
						PaintScheme.psRussianBomberColor[i][0],
						PaintScheme.psRussianBomberColor[i][1],
						PaintScheme.psRussianBomberColor[i][2]);
			}
			this.changeMat(class1, hiermesh, "Overlay7", "redstar2",
					"Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay8", "redstar2",
					"Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
			return;
		}
		if (regiment.country() == PaintScheme.countryNewZealand) {
			k = this.clampToLiteral(k);
			this.changeMat(class1, hiermesh, "Overlay1", "null", "null.tga",
					1.0F, 1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay4", "null", "null.tga",
					1.0F, 1.0F, 1.0F);
			return;
		}
		if (regiment.country() == PaintScheme.countrySlovakia) {
			if (k < 10) {
				this.changeMat(class1, hiermesh, "Overlay4", "psBM03SLVKLNUM"
						+ l + i + "0" + k, "Finnish/" + k + ".tga", 1.0F, 1.0F,
						1.0F);
				this.changeMat(hiermesh, "Overlay1", "psBM03SLVKRNUM" + l + i
						+ "0" + k, "null.tga", "Finnish/" + k + ".tga", 1.0F,
						1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
			} else {
				this.changeMat(hiermesh, "Overlay1", "psBM03SLVKCNUM" + l + i
						+ k, "Finnish/" + (k / 10) + ".tga", "Finnish/"
						+ (k % 10) + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
				this.changeMat(hiermesh, "Overlay4", "psBM03SLVKCNUM" + l + i
						+ k, "Finnish/" + (k / 10) + ".tga", "Finnish/"
						+ (k % 10) + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
			}
			this.changeMat(class1, hiermesh, "Overlay6", "slovakiancross1",
					"Slovakian/cross1.tga", 1.0F, 1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay7", "slovakiancross2",
					"Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
			return;
		}
		if ("um".equals((regiment.branch()))) {
			this.changeMat(hiermesh, "Overlay1", "psBM00USACNUM" + l + i
					+ (k < 10 ? "0" + k : "" + k), "States/" + (k / 10)
					+ ".tga", "States/" + (k % 10) + ".tga", 1.0F, 1.0F, 1.0F,
					1.0F, 1.0F, 1.0F);
			this.changeMat(hiermesh, "Overlay4", "psBM00USACNUM" + l + i
					+ (k < 10 ? "0" + k : "" + k), "States/" + (k / 10)
					+ ".tga", "States/" + (k % 10) + ".tga", 1.0F, 1.0F, 1.0F,
					1.0F, 1.0F, 1.0F);
		} else if ("un".equals((regiment.branch()))) {
			this.changeMat(hiermesh, "Overlay1", "psBM00USACNUM" + l + i
					+ (k < 10 ? "0" + k : "" + k), "States/" + (k / 10)
					+ ".tga", "States/" + (k % 10) + ".tga", 1.0F, 1.0F, 1.0F,
					1.0F, 1.0F, 1.0F);
			this.changeMat(hiermesh, "Overlay4", "psBM00USACNUM" + l + i
					+ (k < 10 ? "0" + k : "" + k), "States/" + (k / 10)
					+ ".tga", "States/" + (k % 10) + ".tga", 1.0F, 1.0F, 1.0F,
					1.0F, 1.0F, 1.0F);
		} else if ("us".equals((regiment.branch()))) {
			this.changeMat(class1, hiermesh, "Overlay1", "null", "null.tga",
					1.0F, 1.0F, 1.0F);
			this.changeMat(class1, hiermesh, "Overlay4", "null", "null.tga",
					1.0F, 1.0F, 1.0F);
		} else {
			return;
		}
	}

	public String typedNameNum(Class class1, Regiment regiment, int i, int j,
			int k) {
		if (regiment.country() == PaintScheme.countryGermany) {
			int l = regiment.gruppeNumber() - 1;
			if (k < 1) {
				k = 1;
			}
			return "" + regiment.id() + " + " + (char) (65 + ((k % 26) - 1))
					+ PaintScheme.psGermanBomberLetter[l][i];
		}
		if (regiment.country() == PaintScheme.countryNetherlands) {
			return "" + k;
		}
		if (regiment.country() == PaintScheme.countryFinland) {
			return PaintScheme.psFinnishFighterString[1][i] + k;
		}
		if (regiment.country() == PaintScheme.countryFrance) {
			return "o " + k;
		}
		if (regiment.country() == PaintScheme.countryBritain) {
			k = this.clampToLiteral(k);
			return "" + regiment.id() + " - " + (char) (65 + (k - 1));
		}
		if (regiment.country() == PaintScheme.countryBritain) {
			return "" + regiment.id() + " + " + (k >= 10 ? "" + k : "0" + k);
		}
		if (regiment.country() == PaintScheme.countryItaly) {
			return "" + k;
		}
		if (regiment.country() == PaintScheme.countryJapan) {
			return "" + k;
		}
		if (regiment.country() == PaintScheme.countryPoland) {
			return "" + (k >= 10 ? "" + k : "0" + k);
		}
		if (regiment.country() == PaintScheme.countryRomania) {
			return "+ " + (k >= 10 ? "" + k : "0" + k);
		}
		if (regiment.country() == PaintScheme.countryRussia) {
			return "* " + PaintScheme.psRussianBomberString[i] + " " + k;
		}
		if (regiment.country() == PaintScheme.countryNewZealand) {
			k = this.clampToLiteral(k);
			return "" + regiment.id() + " - " + (char) (65 + (k - 1));
		}
		if (regiment.country() == PaintScheme.countrySlovakia) {
			return "" + k + " +";
		}
		if (regiment.country() == PaintScheme.countryUSA) {
			return "" + (k >= 10 ? "" + k : "0" + k) + "*";
		} else {
			return super.typedNameNum(class1, regiment, i, j, k);
		}
	}
}
