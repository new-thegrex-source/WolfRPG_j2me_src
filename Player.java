/* Decompiler 1177ms, total 1376ms, lines 2280 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

final class Player {
   public static int touchMe = 1;
   private static final int EXPIRE_DURATION = 5;
   private static final int MAX_DISPLAY_BUFFS = 6;
   public static final int ICE_FOG_DIST = 1024;
   public static final int MAX_NOTEBOOK_INDEXES = 8;
   public static Entity facingEntity;
   public static final short[] inventory = new short[29];
   public static final short[] ammo = new short[10];
   public static long weapons;
   public static final short[] inventoryCopy = new short[29];
   public static final short[] ammoCopy = new short[10];
   public static int weaponsCopy;
   public static long disabledWeapons;
   public static int level;
   public static int currentXP;
   public static int nextLevelXP;
   public static int gold;
   public static CombatEntity baseCe = new CombatEntity();
   public static CombatEntity ce = new CombatEntity();
   public static EntityDef activeWeaponDef;
   public static boolean noclip;
   public static boolean god;
   public static int playTime;
   public static int totalTime;
   public static int moves;
   public static int totalMoves;
   public static int completedLevels;
   public static int killedMonstersLevels;
   public static int foundSecretsLevels;
   public static int xpGained;
   public static int totalDeaths;
   public static final short[] notebookIndexes = new short[8];
   public static final short[] notebookPositions = new short[8];
   public static byte questComplete;
   public static byte questFailed;
   public static int numNotebookIndexes;
   public static int helpBitmask;
   public static int invHelpBitmask;
   public static int ammoHelpBitmask;
   public static int weaponHelpBitmask;
   public static int armorHelpBitmask;
   public static int cocktailDiscoverMask;
   public static int gamePlayedMask;
   public static int lastCombatTurn;
   public static boolean inCombat;
   public static boolean enableHelp;
   private static int turnTime;
   public static int highestMap;
   public static int prevWeapon;
   public static boolean noDeathFlag = false;
   private static int numStatusEffects;
   public static final int[] statusEffects = new int[54];
   public static final int BUFF_TURNS = 0;
   public static final int BUFF_AMOUNT = 15;
   public static short[] buffs = new short[30];
   public static int numbuffs;
   public static int purchasedWeapons = 0;
   public static boolean gameCompleted;
   public static GameSprite playerSprite = null;
   public static int pickingStats = 0;
   public static int oldPickingStats = 0;
   public static byte[] allMedals = null;
   public static int[] medals = new int[2];
   public static final int MEDAL_INFO_FIELDS = 2;
   public static final int MEDAL_DATA = Math.max(8, 11) * 2;
   public static byte[] medalInfo;
   public static final int NUM_BOOK_FIELDS = 4;
   public static byte[] bookMap;
   public static byte[] foundBooks;
   public static boolean showBookStat;
   public static int bookIndex;
   public static int[] counters;
   public static final int[] monsterStats;
   public static final int DEF_STATUS_TURNS = 30;
   public static final int ANTI_FIRE_TURNS = 10;
   public static final int AGILITY_TURNS = 20;
   public static final int PURIFY_TURNS = 10;
   public static final int FEAR_TURNS = 6;
   public static final int COLD_TURNS = 5;
   private static final int BOX_X1 = 17;
   private static final int BOX_X2 = 27;
   private static final int BOX_X3 = 35;
   private static final int BOX_X4 = 43;
   static final int[] pos;
   private static final int MEDAL_FIELDS = 3;

   public static final boolean startup() {
      bookMap = App.TBL_BOOKS;
      allMedals = App.TBL_MEDALS;
      noclip = false;
      god = false;
      helpBitmask = 0;
      invHelpBitmask = 0;
      weaponHelpBitmask = 0;
      armorHelpBitmask = 0;
      enableHelp = true;
      totalDeaths = 0;
      reset();
      return true;
   }

   public static boolean modifyCollision(Entity var0) {
      if (null != var0 && var0.def.eType == 12) {
         int var1 = Render.mapSpriteInfo[(var0.info & '\uffff') - 1] & 255;
         if (var1 == 131) {
            return true;
         }
      }

      return false;
   }

   public static final void advanceTurn() {
      ++moves;
      ++totalMoves;
      if (Game.isUnderWater()) {
         if (statusEffects[48] == 0) {
            addStatusEffect(12, 1, 11);
            statusEffects[48] = 1;
         }

         removeStatusEffect(13);
      } else {
         removeStatusEffect(12);
      }

      Entity var0 = Game.findMapEntity(Canvas.viewX, Canvas.viewY, 16384);
      if (var0 != null && var0.def.tileIndex == 203) {
         addStatusEffect(12, 1, 11);
         statusEffects[48] = 1;
         Sound.playSound(1);
         Hud.addMessage((short)1, (short)124, 7);
      }

      updateStatusEffects();
      boolean var1 = false;
      if (buffs[12] == 0 && Game.isUnderWater()) {
         painEvent((Entity)null, true);
         pain(10, (Entity)null, true);
         Combat.totalDamage = 10;
         Hud.damageTime = App.time + 500;
         Hud.addMessage((short)1, (short)123, 3);
         var1 = true;
      }

      if (buffs[3] > 0) {
         addHealth(buffs[18], false);
      }

      if (statusEffects[53] > 0) {
         addHealth(-statusEffects[35]);
         var1 = true;
      }

      Text var2;
      if (statusEffects[14] > 0) {
         addHealth(buffs[29]);
         var2 = Hud.getMessageBuffer(0);
         Text.resetTextArgs();
         Text.addTextArg(-buffs[29]);
         Text.composeText((short)1, (short)107, var2);
         Hud.finishMessageBuffer();
         var1 = true;
         if (Canvas.state == 3) {
            Game.gsprite_allocAnim(251, 0, 0, 32);
            Canvas.blockInputTime = App.gameTime + 500;
         }
      }

      if (statusEffects[13] > 0) {
         addHealth(-3);
         var2 = Hud.getMessageBuffer(0);
         Text.composeText((short)1, (short)84, var2);
         var2.append(' ');
         Text.resetTextArgs();
         Text.addTextArg(3);
         Text.composeText((short)1, (short)73, var2);
         Hud.finishMessageBuffer();
         var1 = true;
      }

      if ((weapons & 16384L) != 0L) {
         give(2, 8, 17, true);
      }

      if (inCombat && totalMoves - lastCombatTurn >= 4) {
         inCombat = false;
      }

      if (statusEffects[15] > 0) {
         int var10002 = counters[3]++;
      }

      turnTime = App.time;
      if (var1 && Canvas.state == 6) {
         Canvas.setState(3);
      }

   }

   public static final void levelInit() {
      moves = 0;
      numNotebookIndexes = 0;
      questComplete = 0;
      questFailed = 0;
      turnTime = App.time;
      inCombat = false;
      if (ce.getStat(0) == 0) {
         ce.setStat(0, 1);
      }

   }

   public static final void fillMonsterStats() {
      int var0 = 0;
      int var1 = 0;

      for(int var2 = 0; var2 < Game.numEntities; ++var2) {
         Entity var3 = Game.entities[var2];
         if (null != var3.monster && (var3.monster.flags & 128) == 0) {
            ++var0;
            if ((Game.entities[var2].info & 16842752) != 0) {
               ++var1;
            }
         }
      }

      monsterStats[0] = var1;
      monsterStats[1] = var0;
   }

   public static final void readyWeapon() {
      Canvas.readyWeaponSound = 2;
   }

   public static final void selectWeapon(int var0) {
      long var1 = weapons & ~disabledWeapons;
      if ((var1 & 1L << var0) == 0L) {
         ce.weapon = var0;
         selectNextWeapon();
      }

      if (ce.weapon != var0) {
         Canvas.invalidateRect();
         prevWeapon = ce.weapon;
      }

      ce.weapon = var0;
      if (Canvas.state != 8 && Canvas.state != 18) {
         Canvas.drawPlayingSoftKeys();
      }

      if (var0 != prevWeapon) {
         readyWeapon();
      }

      activeWeaponDef = EntityDef.find(6, 1, var0);
      Canvas.updateFacingEntity = true;
      Hud.repaintFlags |= 4;
   }

   public static final void selectPrevWeapon() {
      if (!Canvas.isChickenKicking) {
         int var0 = ce.weapon;
         long var1 = weapons & ~disabledWeapons;

         int var3;
         int var4;
         for(var3 = var0 - 1; var3 >= 0; --var3) {
            if ((var1 & (long)(1 << (int)((long)var3))) != 0L) {
               var4 = var3 * 9;
               if (Combat.weapons[var4 + 5] == 0 || ammo[Combat.weapons[var4 + 4]] != 0) {
                  selectWeapon(var3);
                  return;
               }
            }
         }

         if (ce.weapon == var0 && var0 != 16) {
            for(var3 = 16; var3 > var0; --var3) {
               if ((var1 & (long)(1 << (int)((long)var3))) != 0L) {
                  var4 = var3 * 9;
                  if (Combat.weapons[var4 + 5] == 0 || ammo[Combat.weapons[var4 + 4]] != 0) {
                     selectWeapon(var3);
                     return;
                  }
               }
            }
         }

      }
   }

   public static final void selectNextWeapon() {
      if (!Canvas.isChickenKicking) {
         int var0 = ce.weapon;
         long var1 = weapons & ~disabledWeapons;

         int var3;
         int var4;
         for(var3 = var0 + 1; var3 < 17; ++var3) {
            if ((var1 & (long)(1 << (int)((long)var3))) != 0L) {
               var4 = var3 * 9;
               if (Combat.weapons[var4 + 5] == 0 || ammo[Combat.weapons[var4 + 4]] != 0) {
                  selectWeapon(var3);
                  return;
               }
            }
         }

         if (ce.weapon == var0 && var0 != 0) {
            for(var3 = 0; var3 < var0; ++var3) {
               if ((var1 & (long)(1 << (int)((long)var3))) != 0L) {
                  var4 = var3 * 9;
                  if (Combat.weapons[var4 + 5] == 0 || ammo[Combat.weapons[var4 + 4]] != 0) {
                     selectWeapon(var3);
                     return;
                  }
               }
            }
         }

      }
   }

   public static final void modifyStat(int var0, int var1) {
      if (var0 == 0) {
         addHealth(var1);
         if (var1 < 0) {
            painEvent((Entity)null, false);
         }
      } else {
         baseCe.addStat(var0, var1);
         updateStats();
      }

   }

   public static final boolean requireStat(int var0, int var1) {
      return ce.getStat(var0) >= var1;
   }

   public static final boolean requireItem(int var0, int var1, int var2, int var3) {
      int var4 = 1 << var1;
      if (var0 == 1) {
         if (var3 != 0) {
            return (weapons & (long)var4) != 0L;
         } else {
            return (weapons & (long)var4) == 0L;
         }
      } else if (var0 != 0) {
         return false;
      } else {
         return inventory[var1 - 0] >= var2 && inventory[var1 - 0] <= var3;
      }
   }

   public static final void addXP(int var0) {
      Text.resetTextArgs();
      Text.addTextArg(var0);
      if (var0 < 0) {
         Hud.addMessage((short)108);
      } else {
         Hud.addMessage((short)109);
      }

      currentXP += var0;
      xpGained += var0;

      while(currentXP >= nextLevelXP) {
         addLevel();
      }

      int[] var10000 = counters;
      var10000[5] += var0;
   }

   public static final void addLevel() {
      ++level;
      nextLevelXP = calcLevelXP(level);
      Text var2 = Text.getLargeBuffer();
      var2.setLength(0);
      Text.resetTextArgs();
      Text.addTextArg(level);
      Text.composeText((short)1, (short)110, var2);
      int var0 = 10;
      int var1 = baseCe.getStat(1);
      if (var1 + var0 > 999) {
         var0 = 999 - var1;
      }

      if (var0 != 0) {
         baseCe.setStat(1, var1 + var0);
         Text.resetTextArgs();
         Text.addTextArg(var0);
         Text.composeText((short)1, (short)111, var2);
      }

      var0 = 1;
      var1 = baseCe.getStat(3);
      if (var1 + var0 > 99) {
         var0 = 99 - var1;
      }

      if (var0 != 0) {
         baseCe.setStat(3, var1 + var0);
         Text.resetTextArgs();
         Text.addTextArg(var0);
         Text.composeText((short)1, (short)112, var2);
      }

      var0 = 2;
      var1 = baseCe.getStat(4);
      if (var1 + var0 > 99) {
         var0 = 99 - var1;
      }

      if (var0 != 0) {
         baseCe.setStat(4, var1 + var0);
         Text.resetTextArgs();
         Text.addTextArg(var0);
         Text.composeText((short)1, (short)113, var2);
      }

      var0 = 1;
      var1 = baseCe.getStat(5);
      if (var1 + var0 > 99) {
         var0 = 99 - var1;
      }

      if (var0 != 0) {
         baseCe.setStat(5, var1 + var0);
         Text.resetTextArgs();
         Text.addTextArg(var0);
         Text.composeText((short)1, (short)114, var2);
      }

      var0 = 3;
      var1 = baseCe.getStat(6);
      if (var1 + var0 > 99) {
         var0 = 99 - var1;
      }

      if (var0 != 0) {
         baseCe.setStat(6, var1 + var0);
         Text.resetTextArgs();
         Text.addTextArg(var0);
         Text.composeText((short)1, (short)115, var2);
      }

      updateStats();
      Text.composeText((short)1, (short)116, var2);
      ce.setStat(0, ce.getStat(1));
      Hud.repaintFlags |= 4;
      boolean var3 = true;
      if (Canvas.state != 1) {
         Sound.playSound(5);
         var3 = !Canvas.enqueueHelpDialog(var2, 0);
      }

      if (var3) {
         var2.dispose();
      }

   }

   public static final int calcLevelXP(int var0) {
      return 500 * var0 + 100 * ((var0 - 1) * (var0 - 1) * (var0 - 1) + (var0 - 1));
   }

   public static final int calcScore() {
      int var0 = 0;
      boolean var1 = false;
      boolean var2 = true;

      int var7;
      for(var7 = 0; var7 <= 8; ++var7) {
         if ((killedMonstersLevels & 1 << var7) != 0) {
            var0 += 1000;
         } else {
            var2 = false;
         }
      }

      if (var2) {
         var0 += 1000;
      }

      if (totalDeaths == 0) {
         var0 += 1000;
      } else if (totalDeaths < 10) {
         var0 += (5 - totalDeaths) * 50;
      } else {
         var0 -= 250;
      }

      int var3 = (totalTime + (App.gameTime - playTime)) / '\uea60';
      if (var3 < 120) {
         var0 += (120 - var3) * 15;
      }

      if (totalMoves < 5000) {
         var0 += (5000 - totalMoves) / 2;
      }

      boolean var4;
      for(var4 = true; var7 <= 8; ++var7) {
         if ((foundSecretsLevels & 1 << var7) != 0) {
            var0 += 1000;
         } else {
            var4 = false;
         }
      }

      if (var4) {
         var0 += 1000;
      }

      for(var7 = 0; var7 < medals.length; ++var7) {
         int var5 = medals[var7];

         for(int var6 = 0; var6 < 32; ++var6) {
            if ((var5 & 1 << var6) == 1) {
               var0 += 100;
            }
         }
      }

      return var0;
   }

   public static final boolean addHealth(int var0) {
      return addHealth(var0, true);
   }

   public static final boolean addHealth(int var0, boolean var1) {
      Hud.repaintFlags |= 4;
      int var2 = ce.getStat(0);
      int var3 = ce.getStat(1);
      if (var0 > 0) {
         if (var2 == var3) {
            return false;
         }
      } else if (god) {
         return false;
      }

      Hud.playerStartHealth = var2;
      ce.addStat(0, var0);
      var3 = ce.getStat(0);
      if (var1 && var3 > var2) {
         Text.resetTextArgs();
         Text.addTextArg(var3 - var2);
         Hud.addMessage((short)117);
      }

      return true;
   }

   public static final void reset() {
      Hud.msgCount = 0;
      numNotebookIndexes = 0;
      resetCounters();
      level = 1;
      currentXP = 0;
      nextLevelXP = calcLevelXP(level);
      facingEntity = null;
      noclip = false;
      questComplete = 0;
      questFailed = 0;
      purchasedWeapons = 0;
      pickingStats = 0;
      gold = 0;
      give(3, 0, 2, true);
      playerSprite = null;
      Canvas.prevX = 0;
      Canvas.saveX = 0;
      Canvas.destX = 0;
      Canvas.viewX = 0;
      Canvas.prevY = 0;
      Canvas.saveY = 0;
      Canvas.destY = 0;
      Canvas.viewY = 0;
      Canvas.destZ = 36;
      Canvas.viewZ = 36;
      Canvas.saveAngle = 0;
      Canvas.destAngle = 0;
      Canvas.viewAngle = 0;
      Canvas.destPitch = 0;
      Canvas.viewPitch = 0;
      boolean var0 = false;
      inCombat = false;

      int var1;
      for(var1 = 0; var1 < 10; ++var1) {
         ammo[var1] = 0;
      }

      for(var1 = 0; var1 < 29; ++var1) {
         inventory[var1] = 0;
      }

      numbuffs = 0;

      for(var1 = 0; var1 < 15; ++var1) {
         buffs[15 + var1] = buffs[0 + var1] = 0;
      }

      numStatusEffects = 0;

      for(var1 = 0; var1 < 18; ++var1) {
         statusEffects[18 + var1] = statusEffects[36 + var1] = statusEffects[0 + var1] = 0;
      }

      for(var1 = 0; var1 < 2; ++var1) {
         medals[var1] = 0;
      }

      for(var1 = 0; var1 < 4; ++var1) {
         foundBooks[var1] = 0;
      }

      weapons = 9L;
      foundSecretsLevels = 0;
      killedMonstersLevels = 0;
      baseCe.setStat(1, 100);
      baseCe.setStat(3, 10);
      baseCe.setStat(4, 10);
      baseCe.setStat(5, 95);
      baseCe.setStat(6, 8);
      if (Game.difficulty == 2) {
         baseCe.setStat(3, 0);
      }

      updateStats();
      ce.setStat(0, 100);
      ce.weapon = 0;
      totalTime = 0;
      totalMoves = 0;
      completedLevels = 0;
      highestMap = 1;
      gameCompleted = false;
      cocktailDiscoverMask = 0;
      gamePlayedMask = 0;
   }

   public static final int calcDamageDir(int var0, int var1, int var2, int var3, int var4) {
      int var6 = var3 - var0;
      int var7 = var4 - var1;
      var2 &= 1023;
      if (var6 == 0 && var7 == 0) {
         return 3;
      } else {
         short var5;
         if (var6 > 0) {
            if (var7 < 0) {
               var5 = 128;
            } else if (var7 > 0) {
               var5 = 896;
            } else {
               var5 = 0;
            }
         } else if (var6 < 0) {
            if (var7 < 0) {
               var5 = 384;
            } else if (var7 > 0) {
               var5 = 640;
            } else {
               var5 = 512;
            }
         } else if (var7 > 0) {
            var5 = 768;
         } else {
            var5 = 256;
         }

         int var8 = var5 - var2 & 1023;
         if (var8 > 512) {
            var8 = -(1024 - var8);
         }

         var8 = 3 + var8 / 128;
         if (var8 < 0) {
            var8 = 7;
         }

         return var8;
      }
   }

   public static final void painEvent(Entity var0, boolean var1) {
      if (!god) {
         if (var0 == null) {
            Hud.damageDir = 3;
         }

         Hud.damageCount = 1;
         if (!var1) {
            Canvas.startShake(500, 2, 150);
         }

      }
   }

   public static final void pain(int var0, Entity var1, boolean var2) {
      if (!god) {
         if (var2) {
            Text.resetTextArgs();
            if (var1 != null) {
               Text var5 = Text.getSmallBuffer();
               Text.composeText((short)2, (short)(var1.def.name & 1023), var5);
               Text.addTextArg(var5);
               Text.addTextArg(var0);
               Hud.addMessage((short)1, (short)118);
               var5.dispose();
            } else {
               Text.addTextArg(var0);
               Hud.addMessage((short)1, (short)73);
            }
         }

         if (var0 != 0) {
            int var6 = ce.getStat(0);
            if (var0 >= var6 && noDeathFlag) {
               var0 = var6 - 1;
            }

            int var3 = (var6 << 16) / (ce.getStat(1) << 8);
            int var4 = (var6 - var0 << 16) / (ce.getStat(1) << 8);
            if (var4 > 0) {
               if (var3 > 26 && var4 <= 26) {
                  Hud.addMessage((short)119, 3);
               } else if (var3 > 78 && var4 <= 78) {
                  Hud.addMessage((short)120, 3);
               } else if (var3 > 128 && var4 <= 128 && (helpBitmask & 24576) == 0) {
                  if (inventory[17] == 0 && inventory[16] == 0) {
                     showHelp((short)14, true);
                  } else {
                     showHelp((short)13, true);
                  }
               }
            }

            addHealth(-var0);
            if (Canvas.state == 6) {
               Canvas.setState(3);
            }

            if (ce.getStat(0) <= 0) {
               died();
            }

         }
      }
   }

   public static final void died() {
      if (Canvas.state != 13) {
         ++totalDeaths;
         Sound.playSound(7);
         Canvas.startShake(350, 5, 500);
         ce.setStat(0, 0);
         Canvas.setState(13);
         Game.combatMonsters = null;
      }
   }

   public static final boolean fireWeapon(Entity var0, int var1, int var2) {
      if (Combat.weaponDown || disabledWeapons != 0L && (weapons & 1L << ce.weapon) == 0L) {
         return false;
      } else {
         if (Combat.lerpingWeapon) {
            if (Combat.lerpWpDown) {
               return false;
            }

            Combat.lerpingWeapon = false;
            Combat.weaponDown = false;
         }

         if (var0.monster != null) {
            EntityMonster var10000 = var0.monster;
            var10000.flags = (short)(var10000.flags & -9);
         }

         int var3 = ce.weapon * 9;
         if (Combat.weapons[var3 + 4] != 0) {
            short var4 = ammo[Combat.weapons[var3 + 4]];
            if (Combat.weapons[var3 + 5] > 0 && var4 - Combat.weapons[var3 + 5] < 0) {
               if (ce.weapon == 14) {
                  Hud.addMessage((short)122, 3);
               } else {
                  Hud.addMessage((short)121, 3);
               }

               return false;
            }
         }

         Combat.performAttack((Entity)null, var0, var1, var2, false);
         return true;
      }
   }

   public static final boolean useItem(int var0) {
      if (inventory[var0] == 0) {
         return false;
      } else {
         boolean var1 = true;
         boolean var2;
         switch(var0) {
         case 0:
            if (!addStatusEffect(6, 20, 31)) {
               return false;
            }

            translateStatusEffects();
            break;
         case 1:
            if (!addStatusEffect(4, 20, 31)) {
               return false;
            }

            translateStatusEffects();
            break;
         case 2:
            if (!addStatusEffect(5, 20, 31)) {
               return false;
            }

            translateStatusEffects();
            break;
         case 3:
            if (!addStatusEffect(7, 5, 31)) {
               return false;
            }

            translateStatusEffects();
            break;
         case 4:
            if (!addStatusEffect(9, 0, 11)) {
               return false;
            }

            removeStatusEffect(13);
            translateStatusEffects();
            break;
         case 5:
            if (!addStatusEffect(8, 100, 31)) {
               return false;
            }

            translateStatusEffects();
            break;
         case 6:
            if (statusEffects[2] != 0) {
               return false;
            }

            var1 = false;
            addStatusEffect(2, 0, 21);
            translateStatusEffects();
            break;
         case 7:
            if (!addStatusEffect(10, 20, 31)) {
               return false;
            }

            translateStatusEffects();
            addHealth(20);
            break;
         case 8:
            if (!addStatusEffect(3, 5, 31)) {
               return false;
            }

            translateStatusEffects();
            break;
         case 9:
            if (statusEffects[1] != 0) {
               return false;
            }

            removeStatusEffect(15);
            removeStatusEffect(14);
            removeStatusEffect(16);
            removeStatusEffect(17);
            addStatusEffect(1, 0, 11);
            translateStatusEffects();
            break;
         case 10:
            if (!addStatusEffect(0, 5, 31)) {
               return false;
            }

            translateStatusEffects();
            break;
         case 11:
            var2 = false;
            if (addStatusEffect(10, 50, 31)) {
               var2 = true;
            }

            if (addStatusEffect(5, 25, 31)) {
               var2 = true;
            }

            if (addStatusEffect(4, 25, 31)) {
               var2 = true;
            }

            if (!var2) {
               return false;
            }

            translateStatusEffects();
            break;
         case 12:
            var2 = false;
            if (addStatusEffect(7, 15, 31)) {
               var2 = true;
            }

            if (addStatusEffect(8, 50, 31)) {
               var2 = true;
            }

            if (addStatusEffect(6, 15, 31)) {
               var2 = true;
            }

            if (!var2) {
               return false;
            }

            translateStatusEffects();
            break;
         case 13:
            if (statusEffects[47] != 0 || !addStatusEffect(11, 50, 7)) {
               return false;
            }

            translateStatusEffects();
            break;
         case 14:
            if (!addStatusEffect(8, 50, 31)) {
               return false;
            }

            translateStatusEffects();
            break;
         case 15:
         case 18:
         case 19:
         case 20:
         case 21:
         default:
            return false;
         case 16:
            if (!addHealth(20)) {
               return false;
            }
            break;
         case 17:
            if (!addHealth(80)) {
               return false;
            }
            break;
         case 22:
            var2 = false;
            if (addHealth(20)) {
               var2 = true;
            }

            if (addStatusEffect(15, 10, 31)) {
               var2 = true;
            }

            if (!var2) {
               return false;
            }

            translateStatusEffects();
            break;
         case 23:
            return true;
         }

         Sound.playSound(8);
         --inventory[var0];
         if (var1) {
            Game.advanceTurn();
         }

         return true;
      }
   }

   public static final void giveGold(int var0) {
      gold += var0;
      if (gold < 0) {
         gold = 0;
      }

   }

   public static final boolean give(int var0, int var1, int var2) {
      return give(var0, var1, var2, false);
   }

   public static final boolean give(int var0, int var1, int var2, boolean var3) {
      if (var2 == 0) {
         return false;
      } else {
         int var5 = 1 << var1;
         int[] var10000;
         int var4;
         switch(var0) {
         case 0:
            var4 = var2 + inventory[var1 - 0];
            if (var4 > 999) {
               var4 = 999;
            }

            if (var4 < 0) {
               return false;
            }

            inventory[var1 - 0] = (short)var4;
            if (var2 > 0 && var1 >= 25 && var1 <= 28) {
               var10000 = counters;
               var10000[0] += var2;
            }

            if (!var3) {
               showInvHelp(var1 - 0, false);
            }
            break;
         case 1:
            if (var1 == 4 && ((weapons & 16L) != 0L || (weapons & 32L) != 0L)) {
               var1 = 5;
               weapons &= (long)(~var5);
               var5 = 1 << var1;
            }

            boolean var6 = (weapons & (long)var5) == 0L;
            if (((long)var5 & 7L) != 0L) {
               var5 = Math.max(var5, (int)(weapons & 7L));
               weapons &= -8L;
            }

            if (var2 < 0) {
               weapons &= (long)(~var5);
               if (var1 == ce.weapon) {
                  selectNextWeapon();
               }

               return true;
            }

            if (((long)var5 & 1536L) != 0L) {
               var5 = Math.max(var5, (int)(weapons & 1536L));
               weapons &= -1537L;
            }

            weapons |= (long)var5;
            if (!var3) {
               showWeaponHelp(var1, false);
            }

            if (var6) {
               selectWeapon(var1);
            }
            break;
         case 2:
            var4 = var2 + ammo[var1];
            if (var4 > 100) {
               var4 = 100;
            }

            if (var4 < 0) {
               return false;
            }

            if (var1 == 7) {
               give(1, 11, 1, true);
            }

            ammo[var1] = (short)var4;
            if (!var3) {
               showAmmoHelp(var1, false);
            }

            Hud.repaintFlags |= 4;
            break;
         case 3:
            if (gold + var2 < 0) {
               return false;
            }

            gold += var2;
            if (var2 > 0) {
               var10000 = counters;
               var10000[0] += var2;
            }
            break;
         case 4:
            addHealth(var2);
            break;
         case 5:
         default:
            return false;
         case 6:
            addArmor(var1 * 50);
            if (!var3) {
               showArmorHelp(var1, false);
            }
         }

         return true;
      }
   }

   public static final void giveAmmoWeapon(int var0, boolean var1) {
      weapons |= 1L << var0;
      selectWeapon(var0);
      if (!var1) {
         showWeaponHelp(var0, false);
      }

   }

   public static final void updateQuests(short var0, int var1) {
      if (var1 == 0) {
         if (numNotebookIndexes == 8) {
            App.Error(39);
            return;
         }

         questComplete = (byte)(questComplete & ~(1 << numNotebookIndexes));
         questFailed = (byte)(questFailed & ~(1 << numNotebookIndexes));
         notebookIndexes[numNotebookIndexes++] = var0;
      } else {
         for(int var2 = 0; var2 < numNotebookIndexes; ++var2) {
            if (var0 == notebookIndexes[var2]) {
               if (var1 == 1) {
                  questComplete = (byte)(questComplete | 1 << var2);
               } else if (var1 == 2) {
                  questFailed = (byte)(questFailed | 1 << var2);
               }

               return;
            }
         }

         if (var1 == 1) {
            questComplete = (byte)(questComplete | 1 << numNotebookIndexes);
            questFailed = (byte)(questFailed & ~(1 << numNotebookIndexes));
         } else if (var1 == 2) {
            questComplete = (byte)(questComplete & ~(1 << numNotebookIndexes));
            questFailed = (byte)(questFailed | 1 << numNotebookIndexes);
         }

         notebookPositions[numNotebookIndexes] = 0;
         notebookIndexes[numNotebookIndexes++] = var0;
      }

   }

   public static final void setQuestTile(int var0, int var1, int var2) {
      for(int var3 = 0; var3 < numNotebookIndexes; ++var3) {
         if (var0 == notebookIndexes[var3]) {
            notebookPositions[var3] = (short)(var1 << 5 | var2);
            return;
         }
      }

   }

   public static final boolean isQuestDone(int var0) {
      return (questComplete & 1 << var0) != 0;
   }

   public static final boolean isQuestFailed(int var0) {
      return (questFailed & 1 << var0) != 0;
   }

   public static final void formatTime(int var0, Text var1) {
      var1.setLength(0);
      int var2 = var0 / 1000;
      int var3 = var2 / 60;
      int var4 = var3 / 60;
      int var5 = var3 % 60;
      var1.append(var4);
      var1.append(":");
      if (var5 < 10) {
         var1.append("0");
      }

      var1.append(var5);
      var1.append(":");
      var2 -= var3 * 60;
      if (var2 < 10) {
         var1.append("0");
      }

      var1.append(var2);
   }

   public static final void showInvHelp(int var0, boolean var1) {
      if (enableHelp || var1) {
         int var2 = var0 - 0;
         if ((invHelpBitmask & 1 << var2) == 0) {
            invHelpBitmask |= 1 << var2;
            EntityDef var3 = EntityDef.find(6, 0, var0);
            Canvas.enqueueHelpDialog(var3);
         }
      }
   }

   public static final void showAmmoHelp(int var0, boolean var1) {
      if (enableHelp || var1) {
         int var2 = 1 << var0;
         if ((ammoHelpBitmask & var2) == 0) {
            ammoHelpBitmask |= var2;
            EntityDef var3 = EntityDef.find(6, 2, var0);
            Canvas.enqueueHelpDialog(var3);
         }
      }
   }

   public static final void showHelp(short var0, boolean var1) {
      if (!Game.isCameraActive()) {
         if (enableHelp || var1) {
            if ((helpBitmask & 1 << var0) == 0 || var1) {
               helpBitmask |= 1 << var0;
               Canvas.enqueueHelpDialog(var0);
               if (var0 != 6 && Canvas.state == 3) {
                  Canvas.dequeueHelpDialog();
               }

            }
         }
      }
   }

   public static final void showWeaponHelp(int var0, boolean var1) {
      if (enableHelp || var1) {
         if ((weaponHelpBitmask & 1 << var0) == 0) {
            weaponHelpBitmask |= 1 << var0;
            EntityDef var2 = EntityDef.find(6, 1, var0);
            Canvas.enqueueHelpDialog(var2);
         }
      }
   }

   private static final void showArmorHelp(int var0, boolean var1) {
      if (enableHelp || var1) {
         if ((armorHelpBitmask & 1 << var0) == 0) {
            armorHelpBitmask |= 1 << var0;
            EntityDef var2 = EntityDef.find(6, 6, var0);
            Canvas.enqueueHelpDialog(var2);
         }
      }
   }

   public static final void drawBuffs(Graphics var0) {
      if (numbuffs != 0 && Canvas.state != 8) {
         int var1 = Canvas.viewRect[0] + Canvas.viewRect[2];
         int var2 = var1 - 17;
         int var3 = Canvas.viewRect[1] + 2;
         int var4 = 0;
         boolean var5 = false;
         int var6 = numbuffs;
         if (var6 > 6) {
            var6 = 6;
            var5 = true;
         }

         int var7 = var6 * 13 + 6;

         int var8;
         for(var8 = 0; var8 < 15 && var4 < 6; ++var8) {
            if (buffs[0 + var8] > 0 && (1 << var8 & 14855) == 0) {
               if (buffs[15 + var8] > 99 || buffs[15 + var8] < -99) {
                  var2 = var1 - 43;
                  break;
               }

               if (buffs[15 + var8] <= 9 && buffs[15 + var8] >= -9) {
                  if (var2 == var1 - 17) {
                     var2 = var1 - 27;
                  }
               } else {
                  var2 = var1 - 35;
               }
            }
         }

         var8 = var1 - var2 + 4;
         if (var5) {
            var7 += 5;
         }

         var0.setColor(0);
         var0.fillRect(var2 - 5, var3 - 2, var8, var7);
         var0.setColor(-5592406);
         var0.drawRect(var2 - 5, var3 - 2, var8, var7);
         int var9 = var1 - 18;

         for(int var10 = 0; var10 < 15 && var4 < 6; ++var10) {
            if (buffs[0 + var10] != 0) {
               ++var4;
               drawStatusEffectIcon(var0, var10, buffs[15 + var10], buffs[0 + var10], var9, var3);
               var3 += 13;
            }
         }

         if (var5) {
            Text var11 = Text.getSmallBuffer();
            var11.setLength(0);
            var11.append('\u0085');
            var0.drawString(var11, var2 + var8 / 2 - 4, var3 - 4, 1);
            var11.dispose();
         }

      }
   }

   public static final boolean loadState(DataInputStream var0) throws IOException {
      baseCe.loadState(var0, true);
      ce.loadState(var0, true);
      purchasedWeapons = var0.readInt();
      weapons = var0.readLong();
      weaponsCopy = var0.readInt();
      level = var0.readByte() & 255;
      currentXP = var0.readInt();
      nextLevelXP = calcLevelXP(level);
      gold = var0.readShort();
      totalTime = var0.readInt();
      totalMoves = var0.readInt();
      completedLevels = var0.readInt();
      killedMonstersLevels = var0.readInt();
      foundSecretsLevels = var0.readInt();
      disabledWeapons = var0.readLong();
      pickingStats = var0.readInt();
      prevWeapon = var0.readByte();
      cocktailDiscoverMask = var0.readInt();
      gamePlayedMask = var0.readInt();
      lastCombatTurn = var0.readInt();
      inCombat = var0.readBoolean();
      highestMap = var0.readShort();
      byte var2 = 10;

      int var1;
      for(var1 = 0; var1 < var2; ++var1) {
         ammo[var1] = var0.readShort();
      }

      for(var1 = 0; var1 < var2; ++var1) {
         ammoCopy[var1] = var0.readShort();
      }

      var2 = 29;

      for(var1 = 0; var1 < var2; ++var1) {
         inventory[var1] = var0.readShort();
      }

      for(var1 = 0; var1 < var2; ++var1) {
         inventoryCopy[var1] = var0.readShort();
      }

      numStatusEffects = var0.readByte();
      if (numStatusEffects == 0) {
         for(var1 = 0; var1 < 18; ++var1) {
            statusEffects[36 + var1] = 0;
            statusEffects[0 + var1] = 0;
            statusEffects[18 + var1] = 0;
         }
      } else {
         for(var1 = 0; var1 < 18; ++var1) {
            statusEffects[36 + var1] = var0.readShort();
            statusEffects[0 + var1] = var0.readShort();
            statusEffects[18 + var1] = var0.readShort();
         }
      }

      for(var1 = 0; var1 < 8; ++var1) {
         counters[var1] = var0.readInt();
      }

      gameCompleted = var0.readBoolean();

      for(var1 = 0; var1 < 2; ++var1) {
         medals[var1] = var0.readInt();
      }

      for(var1 = 0; var1 < 4; ++var1) {
         foundBooks[var1] = var0.readByte();
      }

      translateStatusEffects();
      updateStats();
      return true;
   }

   public static final boolean saveState(DataOutputStream var0) throws IOException {
      baseCe.saveState(var0, true);
      ce.saveState(var0, true);
      var0.writeInt(purchasedWeapons);
      var0.writeLong(weapons);
      var0.writeInt(weaponsCopy);
      var0.writeByte(level);
      var0.writeInt(currentXP);
      int var1 = App.gameTime;
      totalTime += var1 - playTime;
      playTime = var1;
      var0.writeShort(gold);
      var0.writeInt(totalTime);
      var0.writeInt(totalMoves);
      var0.writeInt(completedLevels);
      var0.writeInt(killedMonstersLevels);
      var0.writeInt(foundSecretsLevels);
      var0.writeLong(disabledWeapons);
      var0.writeInt(pickingStats);
      var0.writeByte(prevWeapon);
      var0.writeInt(cocktailDiscoverMask);
      var0.writeInt(gamePlayedMask);
      var0.writeInt(lastCombatTurn);
      var0.writeBoolean(inCombat);
      var0.writeShort(highestMap);
      byte var2 = 10;
      boolean var3 = false;

      int var4;
      for(var4 = 0; var4 < var2; ++var4) {
         var0.writeShort(ammo[var4]);
      }

      for(var4 = 0; var4 < var2; ++var4) {
         var0.writeShort(ammoCopy[var4]);
      }

      var2 = 29;

      for(var4 = 0; var4 < var2; ++var4) {
         var0.writeShort(inventory[var4]);
      }

      for(var4 = 0; var4 < var2; ++var4) {
         var0.writeShort(inventoryCopy[var4]);
      }

      var0.writeByte(numStatusEffects);
      if (numStatusEffects != 0) {
         for(var4 = 0; var4 < 18; ++var4) {
            var0.writeShort(statusEffects[36 + var4]);
            var0.writeShort(statusEffects[0 + var4]);
            var0.writeShort(statusEffects[18 + var4]);
         }
      }

      for(var4 = 0; var4 < 8; ++var4) {
         var0.writeInt(counters[var4]);
      }

      var0.writeBoolean(gameCompleted);

      for(var4 = 0; var4 < 2; ++var4) {
         var0.writeInt(medals[var4]);
      }

      for(var4 = 0; var4 < 4; ++var4) {
         var0.writeByte(foundBooks[var4]);
      }

      return true;
   }

   public static final void unpause(int var0) {
      if (var0 > 0) {
         ;
      }
   }

   public static final void relink() {
      unlink();
      link();
   }

   public static final void unlink() {
      Entity var0 = getPlayerEnt();
      if ((var0.info & 1048576) != 0) {
         Game.unlinkEntity(var0);
      }

   }

   public static final void link() {
      Entity var0 = getPlayerEnt();
      if (Canvas.destX >= 0 && Canvas.destX <= 2047 && Canvas.destY >= 0 && Canvas.destY <= 2047) {
         Game.linkEntity(var0, Canvas.destX >> 6, Canvas.destY >> 6);
      }

   }

   public static final void updateStats() {
      int var0 = baseCe.getStat(1) + buffs[25];
      ce.setStat(1, var0);
      ce.setStat(0, ce.getStat(0));
      ce.setStat(4, baseCe.getStat(4) + buffs[20]);
      ce.setStat(5, baseCe.getStat(5) + buffs[22] - buffs[28]);
      ce.setStat(3, baseCe.getStat(3) + buffs[19]);
      ce.setStat(6, baseCe.getStat(6) + buffs[21]);
   }

   private static final void updateStatusEffects() {
      if (numStatusEffects != 0) {
         for(int var0 = 0; var0 < 18; ++var0) {
            if (statusEffects[0 + var0] != 0) {
               if (statusEffects[0 + var0] <= 5 && var0 != 12 && statusEffects[0 + var0] == 1) {
                  removeStatusEffect(var0);
               } else if (statusEffects[0 + var0] != 0) {
                  int var10002 = statusEffects[0 + var0]--;
               }
            }
         }

         translateStatusEffects();
      }
   }

   public static final void translateStatusEffects() {
      int var0;
      for(var0 = 0; var0 < 15; ++var0) {
         buffs[0 + var0] = buffs[15 + var0] = 0;
      }

      numbuffs = 0;

      for(var0 = 0; var0 < 18; ++var0) {
         int var1 = statusEffects[0 + var0];
         int var2 = statusEffects[18 + var0];
         if (var1 != 0 && var1 > 0) {
            int var3;
            switch(var0) {
            case 14:
               var3 = buffs[20] - var2;
               buffs[20] = (short)var3;
               buffs[5] = (short)var1;
               var3 = buffs[19] - var2;
               buffs[19] = (short)var3;
               buffs[4] = (short)var1;
               var3 = buffs[29] - statusEffects[36 + var0] * 4;
               buffs[29] = (short)var3;
               buffs[14] = (short)var1;
               break;
            case 15:
            case 17:
               var3 = buffs[20] + var2;
               buffs[20] = (short)var3;
               buffs[5] = (short)var1;
               var3 = buffs[19] + var2;
               buffs[19] = (short)var3;
               buffs[4] = (short)var1;
               var3 = buffs[22] - (var2 + var2 / 2);
               buffs[22] = (short)var3;
               buffs[7] = (short)var1;
               break;
            case 16:
               var3 = buffs[22] - var2;
               buffs[22] = (short)var3;
               buffs[7] = (short)var1;
               break;
            default:
               var3 = buffs[15 + var0] + var2;
               buffs[15 + var0] = (short)var3;
               buffs[0 + var0] = (short)var1;
            }
         }
      }

      for(var0 = 0; var0 < 15; ++var0) {
         if (buffs[0 + var0] > 0) {
            if ((6663 & 1 << var0) == 0 && buffs[15 + var0] == 0) {
               buffs[0 + var0] = 0;
            } else {
               ++numbuffs;
            }
         }
      }

      updateStats();
   }

   public static final void removeStatusEffect(int var0) {
      if (var0 == 18) {
         numStatusEffects = 0;

         for(int var1 = 0; var1 < 18; ++var1) {
            statusEffects[18 + var1] = statusEffects[36 + var1] = statusEffects[0 + var1] = 0;
         }
      } else {
         if (statusEffects[36 + var0] == 0) {
            return;
         }

         if (var0 == 17) {
            Render.startFogLerp(1, 0, 2000);
         }

         statusEffects[18 + var0] = 0;
         statusEffects[36 + var0] = 0;
         statusEffects[0 + var0] = 0;
         --numStatusEffects;
      }

      translateStatusEffects();
   }

   public static final boolean addStatusEffect(int var0, int var1, int var2) {
      if (var0 == 13 && buffs[9] > 0) {
         return false;
      } else {
         int var3 = statusEffects[36 + var0] + 1;
         if (var3 > 3 || var0 == 9 && var3 > 1) {
            if (var0 == 14) {
               statusEffects[0 + var0] = var2;
            }

            return false;
         } else {
            if (var3 == 1) {
               ++numStatusEffects;
               if (var0 == 17) {
                  TinyGL.fogMin = 0;
                  if (TinyGL.fogRange > 0) {
                     TinyGL.fogRange = -1;
                  }

                  Render.startFogLerp(1024, 0, 2000);
               }
            }

            int[] var10000 = statusEffects;
            var10000[18 + var0] += var1;
            statusEffects[0 + var0] = var2;
            statusEffects[36 + var0] = var3;
            return true;
         }
      }
   }

   private static final void drawStatusEffectIcon(Graphics var0, int var1, int var2, int var3, int var4, int var5) {
      Text var6 = Text.getSmallBuffer();
      var6.setLength(0);
      if (var1 == 8) {
         var6.append('%');
         var6.append(var2);
      } else if ((1 << var1 & 14855) == 0) {
         if (var2 >= 0) {
            var6.append('+');
            var6.append(var2);
         } else {
            var6.append(var2);
         }
      }

      var0.drawString(var6, var4, var5 + 2, 24);
      var0.drawBuffIcon(var1, var4 + 3, var5 + 1, 0);
      if (App.time - turnTime < 600) {
         var6.setLength(0);
         var6.append(var3);
         var0.drawString(var6, var4 + 8, var5 + 2, 17);
         Canvas.forcePump = true;
      }

      var6.dispose();
   }

   private static final void resetCounters() {
      for(int var0 = 0; var0 < 8; ++var0) {
         counters[var0] = 0;
      }

   }

   public static final int[] GetPos() {
      pos[0] = Canvas.destX;
      pos[1] = Canvas.destY;
      pos[2] = Canvas.destAngle;
      return pos;
   }

   public static final Entity getPlayerEnt() {
      return Game.entities[1];
   }

   public static final void setPickUpWeapon(int var0) {
      EntityDef var1 = null;
      EntityDef var2 = EntityDef.find(6, 1, 16);
      if (var0 != 17) {
         var1 = EntityDef.lookup(var0);
      }

      if (null != var1) {
         var2.tileIndex = var1.tileIndex;
         var2.name = var1.name;
         var2.longName = var1.longName;
         var2.description = var1.description;
      } else {
         var2.tileIndex = 17;
         var2.name = 92;
         var2.longName = 92;
         var2.description = 92;
      }

   }

   public static final void giveAll() {
      weapons = 65535L;

      int var0;
      for(var0 = 0; var0 < 10; ++var0) {
         if (var0 != 9) {
            give(2, var0, 100);
         }
      }

      for(var0 = 0; var0 < 29; ++var0) {
         give(0, (byte)var0, 999);
         Canvas.numHelpMessages = 0;
      }

      for(var0 = 0; var0 < 28; ++var0) {
         byte[] var10000 = foundBooks;
         var10000[var0 / 8] = (byte)(var10000[var0 / 8] | 1 << (var0 & 7));
      }

      ce.setStat(0, ce.getStat(1));
   }

   public static void equipForLevel(int var0) {
      int var1 = Canvas.viewX;
      int var2 = Canvas.viewY;
      int var3 = Canvas.viewAngle;
      reset();
      Canvas.prevX = var1;
      Canvas.saveX = var1;
      Canvas.destX = var1;
      Canvas.viewX = var1;
      Canvas.prevY = var2;
      Canvas.saveY = var2;
      Canvas.destY = var2;
      Canvas.viewY = var2;
      Canvas.viewZ = Canvas.destZ = Render.getHeight(Canvas.viewX, Canvas.viewY) + 36;
      Canvas.saveAngle = var3;
      Canvas.destAngle = var3;
      Canvas.viewAngle = var3;
      Canvas.destPitch = 0;
      Canvas.viewPitch = 0;
      highestMap = var0;
      boolean var4 = enableHelp;
      enableHelp = false;
      int var5 = 1;
      weapons = 0L;
      switch(var0) {
      case 5:
      case 9:
      default:
         if (var0 == 9 && Game.difficulty == 4) {
            var5 = 0;
            nextLevelXP = 0;
            currentXP = 0;
            addXP(11297);
            ce.setStat(2, 50);
            ce.setStat(0, 150);
            give(0, 17, 7);
            give(0, 16, 3);
            give(0, 0, 4);
            give(0, 1, 7);
            give(0, 2, 5);
            give(0, 3, 5);
            give(0, 4, 5);
            give(0, 5, 1);
            give(0, 6, 1);
            give(0, 9, 3);
            give(0, 12, 1);
            give(0, 13, 1);
            give(0, 14, 1);
            give(0, 22, 14);
            give(1, 0, 1);
            give(1, 3, 1);
            give(1, 2, 1);
            give(1, 5, 1);
            give(1, 6, 1);
            give(1, 7, 1);
            give(2, 1, 62);
            give(2, 4, 61);
            give(2, 7, 1);
            give(0, 26, 19);
            give(0, 27, 22);
            give(0, 25, 29);
            gold = 12;
            break;
         } else {
            ++var5;
            give(1, 8, 1);
            give(2, 2, 10);
         }
      case 8:
         if (var0 == 8 && Game.difficulty == 4) {
            var5 = 0;
            nextLevelXP = 0;
            currentXP = 0;
            addXP(9462);
            ce.setStat(2, 50);
            ce.setStat(0, 150);
            give(0, 16, 12);
            give(0, 17, 13);
            give(0, 0, 5);
            give(0, 1, 6);
            give(0, 2, 5);
            give(0, 3, 7);
            give(0, 4, 5);
            give(0, 5, 1);
            give(0, 8, 3);
            give(0, 9, 1);
            give(0, 12, 2);
            give(0, 13, 1);
            give(0, 21, 1);
            give(0, 22, 10);
            give(1, 0, 1);
            give(1, 3, 1);
            give(1, 2, 1);
            give(1, 5, 1);
            give(1, 6, 1);
            give(1, 7, 1);
            give(2, 1, 80);
            give(2, 4, 88);
            give(2, 7, 1);
            give(0, 27, 21);
            give(0, 26, 19);
            give(0, 25, 29);
            gold = 12;
            break;
         } else {
            ++var5;
            give(1, 12, 1);
            give(2, 6, 10);
         }
      case 7:
         if (var0 == 7 && Game.difficulty == 4) {
            var5 = 0;
            nextLevelXP = 0;
            currentXP = 0;
            addXP(7617);
            ce.setStat(2, 0);
            ce.setStat(0, 140);
            give(0, 17, 18);
            give(0, 16, 6);
            give(0, 0, 5);
            give(0, 1, 7);
            give(0, 2, 7);
            give(0, 3, 8);
            give(0, 5, 2);
            give(0, 6, 1);
            give(0, 8, 5);
            give(0, 9, 1);
            give(0, 12, 1);
            give(0, 13, 1);
            give(0, 14, 2);
            give(0, 22, 6);
            give(1, 0, 1);
            give(1, 3, 1);
            give(1, 1, 1);
            give(1, 5, 1);
            give(1, 6, 1);
            give(2, 1, 10);
            give(2, 4, 39);
            give(2, 7, 3);
            give(0, 26, 19);
            give(0, 27, 19);
            give(0, 25, 28);
            gold = 12;
         } else {
            var5 = 0;
            nextLevelXP = 0;
            currentXP = 0;
            addXP(calcLevelXP(4));
            ce.setStat(2, 50);
            ce.setStat(0, 140);
            give(0, 17, 80);
            give(0, 16, 38);
            give(0, 0, 4);
            give(0, 1, 5);
            give(0, 2, 9);
            give(0, 3, 9);
            give(0, 6, 1);
            give(0, 8, 4);
            give(0, 9, 1);
            give(0, 10, 1);
            give(0, 12, 1);
            give(0, 13, 2);
            give(0, 14, 3);
            give(1, 0, 1);
            give(1, 3, 1);
            give(1, 1, 1);
            give(1, 5, 1);
            give(1, 6, 1);
            give(1, 10, 1);
            give(1, 13, 1);
            give(2, 1, 10);
            give(2, 4, 62);
            give(2, 7, 8);
            give(2, 3, 25);
            give(2, 5, 32);
            give(0, 26, 19);
            give(0, 27, 19);
            give(0, 25, 28);
            gold = 12;
         }
         break;
      case 6:
         ++var5;
         if (var0 == 6 && Game.difficulty == 4) {
            var5 = 0;
            addXP(6135);
            ce.setStat(0, 50);
            ce.setStat(2, 76);
            give(0, 17, 31);
            give(0, 16, 7);
            give(0, 22, 3);
            give(0, 0, 7);
            give(0, 1, 8);
            give(0, 2, 10);
            give(0, 3, 8);
            give(0, 4, 1);
            give(0, 8, 4);
            give(0, 13, 1);
            give(0, 14, 5);
            give(0, 26, 19);
            give(0, 27, 17);
            give(0, 25, 27);
            gold = 12;
            give(1, 0, 1);
            give(1, 3, 1);
            give(1, 1, 1);
            give(1, 5, 1);
            give(2, 1, 38);
            break;
         }
      case 4:
         if (var0 == 4 && Game.difficulty == 1) {
            var5 = 0;
            nextLevelXP = 0;
            currentXP = 0;
            addXP(calcLevelXP(3));
            ce.setStat(0, 115);
            ce.setStat(2, 100);
            give(0, 17, 45);
            give(0, 16, 11);
            give(0, 0, 4);
            give(0, 1, 4);
            give(0, 2, 4);
            give(0, 3, 4);
            give(0, 13, 2);
            give(0, 8, 2);
            give(0, 26, 3);
            give(0, 27, 5);
            give(0, 25, 13);
            gold = 1;
            give(1, 0, 1);
            give(1, 3, 1);
            give(1, 1, 1);
            give(1, 5, 1);
            give(1, 6, 1);
            give(2, 1, 90);
            give(2, 4, 90);
            give(2, 7, 13);
            break;
         } else {
            give(1, 1, 1);
            give(2, 4, 74);
            give(2, 7, 5);
            give(0, 21, 1);
            give(0, 25, 6);
            give(0, 26, 3);
            give(0, 27, 5);
            give(0, 0, 1);
            give(0, 2, 4);
            give(0, 14, 2);
            give(0, 13, 1);
            ++var5;
         }
      case 3:
         give(1, 6, 1);
         give(2, 4, 13);
         give(1, 11, 1);
         give(2, 7, 10);
         give(0, 17, 12);
         give(0, 21, 1);
         give(0, 25, 3);
         give(0, 26, 3);
         give(0, 27, 1);
         give(0, 0, 3);
         give(0, 1, 3);
         give(0, 2, 5);
         give(0, 3, 5);
         give(0, 4, 9);
         give(0, 14, 1);
         give(0, 8, 2);
         ++var5;
      case 2:
         give(1, 5, 1);
         give(2, 1, 80);
         give(0, 17, 10);
         give(0, 25, 6);
         give(0, 26, 1);
         give(0, 0, 1);
         give(0, 1, 1);
         give(0, 2, 1);
         give(0, 3, 1);
         give(0, 7, 2);
         addArmor(70);
         ++var5;
      case 1:
         give(1, 0, 1);
         give(1, 3, 1);
      }

      give(0, 18, 1);
      enableHelp = var4;
      selectNextWeapon();

      while(level <= var5) {
         addLevel();
      }

      Canvas.updateFacingEntity = true;
   }

   public static void addArmor(int var0) {
      Hud.repaintFlags |= 4;
      if (ce.addStat(2, var0) > 200) {
         ce.setStat(2, 200);
      }

   }

   public static int distFrom(Entity var0) {
      int[] var1 = GetPos();
      return var0.distFrom(var1[0], var1[1]);
   }

   public static void giveStandardMedal(int var0, int var1) {
      if (var0 <= 10) {
         giveMedal((var0 - 1) * 5 + var1, (ScriptThread)null);
      }

   }

   public static void giveMedal(int var0, ScriptThread var1) {
      int[] var10000 = medals;
      var10000[var0 / 32] |= 1 << var0 % 32;
      Text var2 = Text.getSmallBuffer();
      Text var3 = Text.getSmallBuffer();
      if (var0 < 50) {
         Text.composeText((short)4, (short)allMedals[var0 * 3 + 1], var3);
      } else {
         for(int var4 = 150; var4 < 159; var4 += 3) {
            if (allMedals[var4 + 2] == var0) {
               Text.composeText((short)4, (short)allMedals[var4 + 1], var3);
            }
         }
      }

      Text.resetTextArgs();
      Text.addTextArg(var3);
      var3.dispose();
      Sound.playSound(5);
      Text.composeText((short)1, (short)174, var2);
      if (!Canvas.enqueueHelpDialog(var2, 3)) {
         var2.dispose();
      }

   }

   public static void offerBook(int var0, ScriptThread var1) {
      Text var2 = Text.getSmallBuffer();
      Text.resetTextArgs();
      Text.addTextArg((short)0, (short)bookMap[var0 * 4]);
      Text.composeText((short)1, (short)178, var2);
      Canvas.startDialog(var1, var2, 4, 1, true);
      var2.dispose();
   }

   public static void giveBook(int var0, ScriptThread var1) {
      byte[] var10000 = foundBooks;
      var10000[var0 / 8] = (byte)(var10000[var0 / 8] | 1 << var0 % 8);
      showBookStat = true;
      MenuSystem.menuParam = (byte)var0;
      MenuSystem.setMenu(54);
   }

   public static byte[] getLevelMedalCount() {
      int var0 = 0;
      boolean var1 = false;
      boolean var2 = false;

      for(int var3 = 1; var3 < 11; ++var3) {
         int var4 = 0;
         int var5 = 0;

         byte var9;
         for(int var6 = 0; var6 < 5; ++var6) {
            int var7 = (var3 - 1) * 3 * 5 + var6 * 3;
            var9 = allMedals[var7 + 2];
            var2 = (medals[var9 / 32] & 1 << var9 % 32) != 0;
            if (var2) {
               ++var5;
            }

            ++var4;
            var0 += 2;
         }

         boolean var10 = false;
         byte[] var11 = allMedals;

         int var8;
         for(var8 = 150; var8 < 159; var8 += 3) {
            if (var11[var8] == var3) {
               var9 = var11[var8 + 2];
               var2 = (medals[var9 / 32] & 1 << var9 % 32) != 0;
               if (var2) {
                  ++var5;
               }

               ++var4;
               var0 += 2;
               var10 = true;
            } else if (var10) {
               break;
            }
         }

         var8 = var3 - 1 << 1;
         medalInfo[var8] = (byte)var5;
         medalInfo[var8 + 1] = (byte)var4;
      }

      return medalInfo;
   }

   public static boolean hasAllKills(int var0) {
      byte var1 = allMedals[var0 * 3 * 5 + 6 + 2];
      return (medals[var1 / 32] & 1 << var1 % 32) != 0;
   }

   public static byte[] getLevelMedals(int var0, boolean var1) {
      int var2;
      for(var2 = 0; var2 < MEDAL_DATA; ++var2) {
         medalInfo[var2] = -1;
      }

      boolean var3 = false;
      int var4 = 0;
      boolean var5 = false;
      boolean var6 = false;

      byte var8;
      for(var2 = 0; var2 < 5; ++var2) {
         int var7 = (var0 - 1) * 3 * 5 + var2 * 3;
         var8 = allMedals[var7 + 2];
         var6 = (medals[var8 / 32] & 1 << var8 % 32) != 0;
         if (!var1 || var6) {
            medalInfo[var4 + 0] = allMedals[var7 + 1];
            medalInfo[var4 + 1] = (byte)(var6 ? 1 : 0);
            var4 += 2;
         }
      }

      for(var2 = 150; var2 < 159; var2 += 3) {
         if (allMedals[var2] == var0) {
            var8 = allMedals[var2 + 2];
            var6 = (medals[var8 / 32] & 1 << var8 % 32) != 0;
            if (!var1 || var6) {
               medalInfo[var4 + 0] = allMedals[var2 + 1];
               medalInfo[var4 + 1] = (byte)(var6 ? 1 : 0);
               var4 += 2;
               var3 = true;
            }
         } else if (var3) {
            break;
         }
      }

      return medalInfo;
   }

   public static void statusToString(int var0, Text var1) {
      short var2 = 0;
      switch(var0) {
      case 0:
         var2 = 190;
         break;
      case 1:
         var2 = 191;
         break;
      case 2:
         var2 = 192;
         break;
      case 3:
         var2 = 193;
         break;
      case 4:
         var2 = 194;
         break;
      case 5:
         var2 = 195;
         break;
      case 6:
         var2 = 196;
         break;
      case 7:
         var2 = 197;
         break;
      case 8:
         var2 = 198;
         break;
      case 9:
         var2 = 199;
         break;
      case 10:
         var2 = 200;
         break;
      case 11:
         var2 = 201;
         break;
      case 12:
         var2 = 202;
         break;
      case 13:
         var2 = 203;
         break;
      case 14:
         var2 = 204;
         break;
      case 15:
         var2 = 205;
         break;
      case 16:
         var2 = 206;
      }

      Text.composeText((short)1, var2, var1);
   }

   public static boolean hasPurifyEffect() {
      return statusEffects[1] != 0;
   }

   static {
      medalInfo = new byte[MEDAL_DATA];
      bookMap = null;
      foundBooks = new byte[4];
      showBookStat = false;
      counters = new int[8];
      monsterStats = new int[2];
      pos = new int[3];
   }
}
