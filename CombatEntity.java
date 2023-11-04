/* Decompiler 91ms, total 427ms, lines 351 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

final class CombatEntity {
   public static int touchMe = 1;
   public static final int COMBATENTITY_MISS = 0;
   public static final int COMBATENTITY_HIT = 1;
   public static final int COMBATENTITY_CRIT = 2;
   private int[] stats = new int[8];
   public int weapon;

   public CombatEntity() {
   }

   public CombatEntity(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.setStat(1, var1);
      this.setStat(0, var1);
      this.setStat(2, var2);
      this.setStat(3, var3);
      this.setStat(4, var4);
      this.setStat(5, var5);
      this.setStat(6, var6);
   }

   public CombatEntity clone() {
      CombatEntity var1 = new CombatEntity(this.stats[1], this.stats[2], this.stats[3], this.stats[4], this.stats[5], this.stats[6]);
      var1.setStat(0, this.stats[0]);
      var1.weapon = this.weapon;
      return var1;
   }

   public int getStat(int var1) {
      return this.stats[var1];
   }

   public int getStatPercent(int var1) {
      return (this.stats[var1] << 8) / 100;
   }

   public int addStat(int var1, int var2) {
      var2 += this.stats[var1];
      this.setStat(var1, var2);
      return this.stats[var1];
   }

   public int setStat(int var1, int var2) {
      if (var2 < 0) {
         var2 = 0;
      }

      switch(var1) {
      case 0:
         var2 = Math.min(var2, this.getStat(1));
      case 1:
         break;
      default:
         var2 = Math.min(var2, 255);
      }

      return this.stats[var1] = var2;
   }

   public int calcXP() {
      int var1 = (this.stats[3] + this.stats[4]) * 5 + this.stats[5] * 6;
      int var2 = this.stats[1] * 5;
      return (var1 + var2 + 49) / 50;
   }

   public void loadState(DataInputStream var1, boolean var2) throws IOException {
      if (var2) {
         this.stats[0] = var1.readShort();
         this.stats[1] = var1.readShort();
         this.stats[2] = var1.readByte() & 255;
         this.stats[3] = var1.readByte() & 255;
         this.stats[4] = var1.readByte() & 255;
         this.stats[5] = var1.readByte() & 255;
         this.stats[7] = var1.readByte() & 255;
         this.weapon = var1.readByte() & 255;
      } else {
         this.stats[0] = var1.readShort();
      }

   }

   public void saveState(DataOutputStream var1, boolean var2) throws IOException {
      if (var2) {
         var1.writeShort(this.stats[0]);
         var1.writeShort(this.stats[1]);
         var1.writeByte(this.stats[2]);
         var1.writeByte(this.stats[3]);
         var1.writeByte(this.stats[4]);
         var1.writeByte(this.stats[5]);
         var1.writeByte(this.stats[7]);
         var1.writeByte(this.weapon);
      } else {
         var1.writeShort(this.stats[0]);
      }

   }

   public static void calcCombat(CombatEntity var0, Entity var1, boolean var2, int var3, int var4) {
      Combat.crDamage = 0;
      Combat.crArmorDamage = 0;
      CombatEntity var5;
      if (!var2) {
         var5 = var1.monster.ce;
      } else {
         var5 = Player.ce;
      }

      int var6 = calcHit(var0, var5, var2, var3, false);
      if ((var6 & 4103) != 0) {
         int var7 = 0;
         if (var0.weapon != 11) {
            var7 = calcDamage(var0, var1, var5, var2, var4);
            if (var7 == 0 && Combat.crArmorDamage == 0) {
               var6 |= 256;
            }
         }

         Combat.crFlags = var6;
         Combat.crDamage = var7;
      }
   }

   public static int calcHit(CombatEntity var0, CombatEntity var1, boolean var2, int var3, boolean var4) {
      int var5 = Combat.attackerWeapon;
      int var6 = Combat.attackerWeaponId;
      boolean var7 = false;
      Entity var8 = Combat.curTarget;
      byte var9;
      byte var10;
      if (var8 == null) {
         var9 = 1;
         var10 = 0;
      } else {
         var9 = var8.def.eType;
         var10 = var8.def.eSubType;
      }

      if ((!Combat.oneShotCheat || var2 || var6 == 11) && (var9 != 2 || var10 != 14 || var8.monster.goalType != 6 && var6 != 14)) {
         if (((long)(1 << var6) & 7L) != 0L && var9 == 2 && var10 == 9) {
            return Combat.crFlags;
         } else {
            int var11;
            int var12;
            int var13;
            if ((1L << var6 & 1536L) != 0L) {
               if (var9 == 2) {
                  var11 = var8.getSprite();
                  var12 = Canvas.zoomCollisionX - Render.mapSprites[Render.S_X + var11];
                  var13 = Canvas.zoomCollisionY - Render.mapSprites[Render.S_Y + var11];
                  int var20 = Canvas.zoomCollisionZ - Render.mapSprites[Render.S_Z + var11];
                  int var21 = Render.mapSpriteInfo[var11] >> 8 & 240;
                  int[][] var16 = Render.getImageFrameBounds(var8.def.tileIndex, 3, 2, 0);
                  int var17 = -1;

                  for(int var18 = 0; var18 < 3; ++var18) {
                     if (var12 > var16[var18][0] && var12 < var16[var18][1] && var13 > var16[var18][0] && var13 < var16[var18][1] && var20 > var16[var18][2] && var20 < var16[var18][3]) {
                        var17 = var18;
                        break;
                     }
                  }

                  if (var17 != -1) {
                     if (var17 != 0 && var21 != 16) {
                        if (var17 == 1) {
                           Combat.crFlags |= 1;
                        } else {
                           Combat.crFlags |= 5;
                        }
                     } else {
                        Combat.crFlags |= 2;
                     }
                  }
               }

               return Combat.crFlags;
            } else {
               var11 = Combat.WorldDistToTileDist(var3);
               int var19;
               if (var11 < Combat.weapons[var5 + 2]) {
                  var19 = Combat.weapons[var5 + 2] - var11;
               } else if (var11 > Combat.weapons[var5 + 3]) {
                  var19 = var11 - Combat.weapons[var5 + 3];
               } else {
                  var19 = 0;
               }

               if ((Combat.crFlags & 16) != 0) {
                  var19 = 0;
               } else if ((Combat.weapons[var5 + 2] == Combat.weapons[var5 + 3] || (Combat.crFlags & 64) != 0) && var19 > 0) {
                  Combat.crFlags |= 1024;
                  return Combat.crFlags;
               }

               if (var4) {
                  return Combat.crFlags |= 1;
               } else if (var6 == 11) {
                  return Combat.crFlags |= 4096;
               } else {
                  var12 = var0.getStat(5);
                  var13 = var1.getStat(6);
                  if ((1L << var6 & 36873L) == 0L) {
                     var13 = var13 * 96 >> 8;
                  }

                  var12 -= var13;
                  Combat.crHitChance = (var12 << 8) / 100;
                  Combat.crHitChance -= 16 * var19;
                  if (Combat.crHitChance < 1) {
                     Combat.crHitChance = 1;
                  }

                  short var14 = App.nextByte();
                  if (Combat.punchingMonster != 0) {
                     if (Combat.animLoopCount != 1 && Combat.punchMissed) {
                        if (Combat.punchMissed) {
                           Combat.playerMissRepetition = 0;
                           var14 = 255;
                        }
                     } else {
                        var14 = 0;
                     }
                  }

                  byte var15 = 1;
                  if (var9 == 2 && var10 == 14) {
                     var15 = 99;
                  }

                  if ((!var2 && Combat.playerMissRepetition < var15 || var2 && Combat.monsterMissRepetition < 2) && var14 > Combat.crHitChance && (1L << var0.weapon & 27007022792704L) == 0L && (var2 || Canvas.loadMapID < 8 || Combat.tileDist > 1 || var9 == 2 && var10 == 14)) {
                     if (var2) {
                        ++Combat.monsterMissRepetition;
                     } else {
                        ++Combat.playerMissRepetition;
                     }

                     if ((var9 != 2 || var10 != 7 && var10 != 8) && (var11 > 1 || Player.hasPurifyEffect() || Player.statusEffects[16] != 0 || Player.statusEffects[15] != 0)) {
                        return Combat.crFlags;
                     } else {
                        Combat.crFlags |= 128;
                        return Combat.crFlags;
                     }
                  } else {
                     if (var2 && Player.statusEffects[18] > 0) {
                        if (--Player.statusEffects[18] == 0) {
                           Player.removeStatusEffect(0);
                        }

                        Player.translateStatusEffects();
                        Combat.crFlags |= 256;
                     }

                     if ((1L << var6 & 7L) != 0L) {
                        if (!Combat.curTarget.hasHead()) {
                           return Combat.crFlags;
                        }

                        if (var9 == 2 && var10 == 4) {
                           return Combat.crFlags |= 2;
                        }
                     }

                     if (var2) {
                        Combat.monsterMissRepetition = 0;
                     } else {
                        Combat.playerMissRepetition = 0;
                     }

                     if (var2 && Game.difficulty != 4) {
                        Combat.crCritChance = 0;
                     } else {
                        Combat.crCritChance = Combat.crHitChance / 20;
                     }

                     var14 = App.nextByte();
                     return var14 < Combat.crCritChance ? (Combat.crFlags |= 2) : (Combat.crFlags |= 1);
                  }
               }
            }
         }
      } else {
         return Combat.crFlags |= 1;
      }
   }

   public static int calcDamage(CombatEntity var0, Entity var1, CombatEntity var2, boolean var3, int var4) {
      int var5 = var0.weapon * 9;
      int var6 = Combat.weapons[var5 + 0];
      byte var7 = Combat.weapons[var5 + 1];
      if (var1.def.eType == 2 && var1.def.eSubType == 14 && var0.weapon != 15) {
         Combat.crArmorDamage = 0;
         Combat.crDamage = 0;
         return 0;
      } else if (var3 && Player.buffs[0] > 0) {
         Combat.crDamage = var7;
         return var7;
      } else {
         if (Game.difficulty == 4) {
            var6 -= var6 >> 2;
         }

         if ((Combat.crFlags & 2) == 0 && (Combat.crFlags & 8192) == 0) {
            if ((Combat.crFlags & 4) != 0) {
               var6 = var7 / 2;
            } else if (var7 != var6) {
               var6 += App.nextByte() % (var7 - var6);
            }
         } else if (var1.def.eType == 2 && (1L << var0.weapon & 1536L) != 0L) {
            var6 = var1.monster.ce.getStat(1) * 3 / 2;
         } else {
            var6 = var7 * 2;
         }

         if ((Combat.crFlags & 32) == 0) {
            var6 += var0.getStatPercent(4) * var6 >> 8;
         }

         int var8 = 0;
         if (!var3) {
            if (Player.buffs[8] > 0) {
               int var9 = (Player.buffs[23] << 8) / 100;
               var6 += var9 * var6 >> 8;
            }

            short var10 = Combat.getWeaponWeakness(var0.weapon, var1.def.eSubType, var1.def.parm);
            var6 = var10 * var6 >> 8;
         } else {
            var8 = 87 * var6 >> 8;
            var8 = Math.min(var8, var2.getStat(2));
            var6 -= var8;
            if ((1L << var0.weapon & 1100686032896L) != 0L && Player.buffs[9] > 0) {
               var8 = 0;
               var6 = 0;
            }
         }

         var6 -= var2.getStatPercent(3) * var6 >> 8;
         if (Combat.oneShotCheat && !var3) {
            var6 = 999;
         }

         Combat.crArmorDamage = var8;
         Combat.crDamage = var6;
         return var6;
      }
   }
}
