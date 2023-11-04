/* Decompiler 2517ms, total 2747ms, lines 1808 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

final class ScriptThread {
   public static int touchMe = 1;
   public static final int MAX_SCRIPT_THREADS = 20;
   public static final int MAX_STACK_SIZE = 16;
   public static final int DEFAULT_TIME_SCALE = 100;
   public int unpauseTime;
   public int state = 2;
   public boolean inuse = false;
   public int IP = 0;
   public int FP = 0;
   public int flags;
   public int type;
   public boolean throwAwayLoot;
   private int[] scriptStack = new int[16];
   public int stackPtr = 0;
   private Text debugString = null;
   public static int lootSource = -1;

   public ScriptThread() {
   }

   public void saveState(DataOutputStream var1) throws IOException {
      if (this.unpauseTime != -1 && this.unpauseTime != 0) {
         var1.writeInt(this.unpauseTime - App.gameTime);
      } else {
         var1.writeInt(this.unpauseTime);
      }

      var1.writeByte(this.state);
      var1.writeInt(this.IP);
      var1.writeInt(this.FP);

      for(int var2 = 0; var2 < this.FP; ++var2) {
         var1.writeInt(this.scriptStack[var2]);
      }

   }

   public void loadState(DataInputStream var1) throws IOException {
      this.init();
      this.unpauseTime = var1.readInt();
      if (this.unpauseTime != 0 && this.unpauseTime != -1) {
         this.unpauseTime += App.gameTime;
      }

      this.state = var1.readByte();
      this.IP = var1.readInt();
      this.FP = var1.readInt();

      for(int var2 = 0; var2 < this.FP; ++var2) {
         this.scriptStack[var2] = var1.readInt();
      }

   }

   public int executeTile(int var1, int var2, int var3, boolean var4) {
      if (var1 >= 0 && var1 < 32 && var2 >= 0 && var2 < 32) {
         Game.skipAdvanceTurn = false;
         int var5 = var2 * 32 + var1;
         if ((Render.mapFlags[var5] & 64) == 0) {
            this.state = 0;
            return 0;
         } else {
            int var6 = Render.findEventIndex(var5);

            int var7;
            for(var7 = 0; var6 != -1; var6 = Render.getNextEventIndex()) {
               int var8 = Render.tileEvents[var6 + 1];
               int var9 = var8 & var3;
               if ((var8 & 524288) == 0 && (var9 & 15) != 0 && (var9 & 4080) != 0 && ((var8 & 28672) == 0 && (var3 & 28672) == 0 || (var9 & 28672) != 0)) {
                  if ((var8 & 262144) != 0) {
                     Game.skipAdvanceTurn = true;
                     Game.queueAdvanceTurn = false;
                  }

                  this.alloc(var6, var3, var4);
                  var7 = this.run();
               }
            }

            return var7;
         }
      } else {
         this.state = 0;
         return 0;
      }
   }

   int queueTile(int var1, int var2, int var3) {
      return this.queueTile(var1, var2, var3, false);
   }

   int queueTile(int var1, int var2, int var3, boolean var4) {
      if (var1 >= 0 && var1 < 32 && var2 >= 0 && var2 < 32) {
         Game.skipAdvanceTurn = false;
         int var5 = var2 * 32 + var1;
         if ((Render.mapFlags[var5] & 64) != 0) {
            int var6 = Render.findEventIndex(var5);
            if (var6 != -1) {
               this.alloc(var6, var3, var4);
               this.flags |= 2;
               return 2;
            }
         }

         this.state = 0;
         return this.state;
      } else {
         this.state = 0;
         return 0;
      }
   }

   private int evWait(int var1) {
      if (Game.skippingCinematic) {
         return 1;
      } else {
         this.unpauseTime = App.gameTime + var1;
         if ((this.flags & 1) != 0) {
            if (Canvas.state != 18) {
               Canvas.blockInputTime = this.unpauseTime;
            }

            if (Canvas.state == 6 || Canvas.state == 1) {
               Canvas.setState(3);
            }

            if (Canvas.state == 3) {
               Canvas.clearSoftKeys();
            }
         }

         return 2;
      }
   }

   private boolean evReturn() {
      while(this.FP < this.stackPtr - 2) {
         this.pop();
      }

      this.FP = this.pop();
      int var1 = this.pop();
      if (var1 == -1) {
         if (this.stackPtr != 0) {
            App.Error(new Exception("The frame pointer should be zero if the script has completed."), 102);
         }

         return true;
      } else {
         this.IP = var1;
         return false;
      }
   }

   private void alloc(int var1, int var2, boolean var3) {
      this.IP = (Render.tileEvents[var1] & -65536) >> 16;
      this.FP = 0;
      this.stackPtr = 0;
      this.push(-1);
      this.push(0);
      this.type = var2;
      this.flags = 0;
      if (var3) {
         this.flags = 1;
      }

   }

   public void alloc(int var1) {
      this.IP = var1;
      this.FP = 0;
      this.stackPtr = 0;
      this.push(-1);
      this.push(0);
      this.type = 0;
      this.flags = 1;
   }

   private final int peekNextCmd() {
      return Render.mapByteCode[this.IP + 1];
   }

   public static void setupCamera(int var0) {
      Game.cinUnpauseTime = App.gameTime + 1000;
      Game.activeCameraView = true;
      MayaCamera var1 = Game.mayaCameras[var0];
      Game.activeCameraKey = -1;
      Game.activeCamera = var1;
      var1.complete = false;
      Game.activeCameraTime = App.gameTime;
      Game.camPlayerX = Canvas.destX << 0;
      Game.camPlayerY = Canvas.destY << 0;
      Game.camPlayerZ = Canvas.destZ << 0;
      Game.camPlayerYaw = Canvas.destAngle & 1023;
      Game.camPlayerPitch = Canvas.viewPitch;
      var1.x = Game.mayaCameraKeys[Game.OFS_MAYAKEY_X + var1.keyOffset];
      if (var1.x == -2) {
         var1.x = Game.camPlayerX;
      }

      var1.y = Game.mayaCameraKeys[Game.OFS_MAYAKEY_Y + var1.keyOffset];
      if (var1.y == -2) {
         var1.y = Game.camPlayerY;
      }

      var1.z = Game.mayaCameraKeys[Game.OFS_MAYAKEY_Z + var1.keyOffset];
      if (var1.z == -2) {
         var1.z = Game.camPlayerZ;
      }

      var1.x <<= 4;
      var1.y <<= 4;
      var1.z <<= 4;
      var1.pitch = Game.mayaCameraKeys[Game.OFS_MAYAKEY_PITCH + var1.keyOffset];
      if (var1.pitch == -2) {
         var1.pitch = Game.camPlayerPitch;
      }

      var1.yaw = Game.mayaCameraKeys[Game.OFS_MAYAKEY_YAW + var1.keyOffset];
      if (var1.yaw == -2) {
         var1.yaw = Game.camPlayerYaw;
      }

      var1.roll = Game.mayaCameraKeys[Game.OFS_MAYAKEY_ROLL + var1.keyOffset];
   }

   public int run() {
      Game.updateScriptVars();
      if (this.stackPtr == 0) {
         return 1;
      } else {
         int var1 = 1;
         boolean var2 = false;
         boolean var3 = true;

         for(byte[] var4 = Render.mapByteCode; this.IP < Render.mapByteCodeSize && var1 != 2; ++this.IP) {
            byte var15;
            var3 = true;
            var15 = 0;
            int var5;
            int var6;
            int var7;
            int var8;
            int var9;
            int var10;
            int var11;
            LerpSprite var12;
            short var13;
            byte var16;
            Entity var10000;
            short var17;
            int var10001;
            short var18;
            byte var19;
            short var20;
            boolean var21;
            Entity var23;
            short var26;
            Entity var27;
            short var31;
            short var33;
            Entity var34;
            byte var37;
            EntityDef var40;
            boolean var43;
            byte var45;
            int[] var49;
            label886:
            switch(var4[this.IP]) {
            case -1:
               if (this.stackPtr != 0) {
                  App.Error(new Exception("The frame pointer should be zero if the script has completed."), 102);
               }

               return 1;
            case 0:
               var17 = (short)this.getByteArg();
               var3 = false;

               while(true) {
                  while(true) {
                     --var17;
                     if (var17 < 0) {
                        var18 = this.getUByteArg();
                        if (this.pop() == 0) {
                           this.IP += var18;
                        }
                        break label886;
                     }

                     var19 = this.getByteArg();
                     if ((var19 & 64) != 0) {
                        var6 = (var19 & 63) << 8;
                        var6 |= this.getByteArg();
                        var6 = var6 << 18 >> 18;
                        this.push(var6);
                     } else if ((var19 & 128) != 0) {
                        this.push(Game.scriptStateVars[var19 & 127]);
                     } else {
                        switch(var19) {
                        case 0:
                           this.push(this.pop() == 1 && this.pop() == 1);
                           break;
                        case 1:
                           this.push(this.pop() == 1 || this.pop() == 1);
                           break;
                        case 2:
                           var7 = this.pop();
                           var8 = this.pop();
                           this.push(var8 <= var7);
                           break;
                        case 3:
                           var7 = this.pop();
                           var8 = this.pop();
                           this.push(var8 < var7);
                           break;
                        case 4:
                           this.push(this.pop() == this.pop());
                           break;
                        case 5:
                           this.push(this.pop() != this.pop());
                           break;
                        case 6:
                           var7 = this.pop();
                           if (var7 == 0) {
                              this.push(1);
                           } else {
                              this.push(0);
                           }
                        }
                     }
                  }
               }
            case 1:
               var5 = this.getUShortArg();
               this.IP += var5;
               break;
            case 2:
               if (this.evReturn()) {
                  return 1;
               }
               break;
            case 3:
               var5 = this.getUShortArg();
               var18 = (short)(var5 & 32767);
               var7 = (var5 & '耀') >> 15;
               if (var7 == 1) {
                  Hud.addMessage(Canvas.loadMapStringID, var18, 3);
               } else {
                  if (Canvas.state == 18) {
                     Hud.msgCount = 0;
                  }

                  Hud.addMessage(Canvas.loadMapStringID, var18);
               }
               break;
            case 4:
               var33 = this.getUByteArg();
               var11 = var33 | this.getUByteArg() << 8;
               var11 |= this.getUByteArg() << 16;
               var5 = var11 >> 14 & 255;
               var7 = var11 >> 9 & 31;
               var8 = var11 >> 4 & 31;
               var10 = var11 & 15;
               if ((var10 & 8) == 0) {
                  var9 = this.getUByteArg() - 48;
               } else {
                  var9 = 32;
               }

               if ((var10 & 4) == 0) {
                  var6 = this.getUByteArg() * 100;
               } else {
                  var6 = 0;
               }

               boolean var39 = (var10 & 1) != 0;
               LerpSprite var42 = Game.allocLerpSprite(this, var5, (var10 & 2) != 0);
               if (var42 == null) {
                  return 0;
               }

               var42.dstX = 32 + (var7 << 6);
               var42.dstY = 32 + (var8 << 6);
               short var14 = Render.mapSprites[Render.S_ENT + var5];
               if (var14 != -1) {
                  var10000 = Game.entities[var14];
                  var10000.info |= 4194304;
               }

               var42.dstZ = Render.getHeight(var42.dstX, var42.dstY) + var9;
               var42.srcX = Render.mapSprites[Render.S_X + var5];
               var42.srcY = Render.mapSprites[Render.S_Y + var5];
               var42.srcZ = Render.mapSprites[Render.S_Z + var5];
               var42.srcScale = var42.dstScale = Render.mapSprites[Render.S_SCALEFACTOR + var5];
               var42.startTime = App.gameTime;
               var42.travelTime = var6;
               var42.flags = var10 & 3;
               var42.calcDist();
               if (var6 == 0) {
                  if ((Game.updateLerpSprite(var42) & 1) != 0) {
                     Canvas.invalidateRect();
                  } else {
                     var1 = 0;
                  }
               } else if (!var39) {
                  Game.skipAdvanceTurn = true;
                  Game.queueAdvanceTurn = false;
                  this.unpauseTime = -1;
                  var1 = 2;
               }
               break;
            case 5:
               var16 = this.getByteArg();
               setupCamera(var16);
               Game.activeCamera.cameraThread = this;
               if (Canvas.state != 1 && Canvas.state != 18) {
                  Canvas.setState(18);
               }

               Game.skipAdvanceTurn = true;
               Game.queueAdvanceTurn = false;
               break;
            case 6:
               var17 = (short)this.getByteArg();
               var18 = this.getShortArg();
               Game.scriptStateVars[var17] = var18;
               if (var17 == 6 && var18 == 1) {
                  Player.removeStatusEffect(13);
               }
               break;
            case 7:
               var5 = this.getUShortArg();
               var6 = this.FP;
               this.FP = this.stackPtr;
               this.push(this.IP);
               this.push(var6);
               this.IP = var5 - 1;
               break;
            case 8:
               var5 = this.getUShortArg();
               var6 = var5 & 31;
               var7 = (var5 & 992) >> 5;
               var31 = 0;
               if (var6 == 0) {
                  var31 = Player.inventory[var7];
               } else if (var6 == 1) {
                  if ((Player.weapons & 1L << var7) != 0L) {
                     var31 = 1;
                  }
               } else if (var6 == 2) {
                  var31 = Player.ammo[var7];
               }

               Game.scriptStateVars[(var5 & 'ﰀ') >> 10] = (short)var31;
               break;
            case 9:
               var5 = this.getUShortArg();
               var6 = var5 & 31;
               var7 = var5 >> 5 & 31;
               var8 = var5 >> 10 & 63;
               var45 = 1;
               Entity var35 = Game.entityDb[var6 + 32 * var7];
               if (var35 != null) {
                  while(var35 != null) {
                     if ((1 << var35.def.eType & 25152) == 0) {
                        var45 = 0;
                        break;
                     }

                     var35 = var35.nextOnTile;
                  }
               }

               Game.scriptStateVars[var8] = var45;
               break;
            case 10:
               var16 = this.getByteArg();
               Game.scriptStateVars[var16 & 63] = (short)Player.ce.weapon;
               break;
            case 11:
               var17 = this.getUByteArg();
               var6 = this.getUShortArg();
               var43 = true;
               var31 = (short)(Canvas.loadMapID - 1);
               Player.completedLevels |= 1 << var31;
               Game.spawnParam = (var17 >> 4 & 7) << 10 | var6 & 1023;
               MenuSystem.LEVEL_STATS_nextMap = (short)(var17 & 15);
               if (MenuSystem.LEVEL_STATS_nextMap < Canvas.loadMapID) {
                  var43 = false;
               }

               Player.removeStatusEffect(12);
               Game.snapAllMovers();
               if ((var17 & 128) != 0) {
                  var45 = 1;
                  if (var43) {
                     var9 = var45 | 8;
                  } else {
                     var9 = var45 | 4;
                  }

                  if (this.state == 6) {
                     Canvas.setState(3);
                  }

                  Render.startFade(1000, var9);
               } else if (var43) {
                  Canvas.saveState(51, (short)5, (short)179);
               } else {
                  Canvas.loadMap(MenuSystem.LEVEL_STATS_nextMap, false);
               }

               Canvas.changeMapStarted = true;
               break;
            case 12:
               var5 = this.getUShortArg();
               var6 = this.getUShortArg();
               if (!Game.skippingCinematic) {
                  var20 = (short)(var5 & 16383);
                  var8 = (var5 & '耀') >> 15;
                  Hud.showCinPlayer = (var5 & 16384) != 0;
                  if (var8 == 1) {
                     Hud.showCinPlayer = false;
                  }

                  if (var8 == 0) {
                     Hud.subTitleID = Text.STRINGID(Canvas.loadMapStringID, var20);
                     Hud.subTitleTime = App.gameTime + var6;
                  } else {
                     Hud.cinTitleID = Text.STRINGID(Canvas.loadMapStringID, var20);
                     Hud.cinTitleTime = App.gameTime + var6;
                  }

                  Canvas.repaintFlags |= 16;
                  Hud.repaintFlags = 16;
               }
               break;
            case 13:
               var17 = this.getUByteArg();
               var18 = this.getUByteArg();
               var7 = var18 >> 4;
               var6 = var18 & 15;
               if (Canvas.state == 6) {
                  Canvas.setState(3);
                  Canvas.invalidateRect();
               }

               if (!Game.skipDialog) {
                  if (var6 == 6 || var6 == 7 || var6 == 1) {
                     Player.inCombat = false;
                  }

                  if (var6 == 2) {
                     boolean var47 = false;
                     Player.prevWeapon = Player.ce.weapon;

                     for(byte var44 = 0; var44 < 20; ++var44) {
                        if (Game.scriptThreads[var44] == this) {
                           var47 = Canvas.enqueueHelpDialog(Canvas.loadMapStringID, (short)var17, var44);
                           break;
                        }
                     }

                     if (!var47) {
                        break;
                     }
                  } else {
                     if (var6 == 4) {
                        Player.prevWeapon = Player.ce.weapon;
                     }

                     Canvas.startDialog(this, Canvas.loadMapStringID, (short)var17, var6, var7, true);
                  }

                  Game.skipAdvanceTurn = true;
                  Game.queueAdvanceTurn = false;
                  this.unpauseTime = -1;
                  var1 = 2;
               }
               break;
            case 14:
               var5 = this.getUByteArg() * 100;
               var1 = this.evWait(var5);
               break;
            case 15:
               if (Game.interpolatingMonsters) {
                  Game.snapMonsters(true);
               }

               while(Game.combatMonsters != null) {
                  Game.combatMonsters.undoAttack();
               }

               Game.endMonstersTurn();
               var5 = this.getUShortArg();
               var21 = (var5 & 16384) != 0;
               Canvas.destX = (var5 >> 5 & 31) * 64 + 32;
               Canvas.destY = (var5 & 31) * 64 + 32;
               Canvas.destZ = Render.getHeight(Canvas.destX, Canvas.destY) + 36;
               var7 = var5 >> 10 & 15;
               Canvas.destPitch = 0;
               Canvas.viewPitch = 0;
               Canvas.destRoll = 0;
               Canvas.viewRoll = 0;
               Canvas.knockbackDist = 0;
               if (var21) {
                  if (var7 != 15) {
                     var8 = Canvas.viewAngle & 1023;
                     var9 = var7 << 7;
                     if (var9 - var8 > 512) {
                        var9 -= 1024;
                     } else if (var9 - var8 < -512) {
                        var9 += 1024;
                     }

                     Canvas.viewAngle = var8;
                     Canvas.destAngle = var9;
                  }

                  Canvas.startRotation(false);
                  Canvas.zStep = (Math.abs(Canvas.destZ - Canvas.viewZ) + Canvas.animFrames - 1) / Canvas.animFrames;
                  if (Canvas.destX == Canvas.viewX && Canvas.destY == Canvas.viewY && Canvas.viewAngle == Canvas.destAngle) {
                     Canvas.viewPitch = Canvas.destPitch;
                  } else {
                     Canvas.gotoThread = this;
                     this.unpauseTime = -1;
                     var1 = 2;
                  }
               } else {
                  Canvas.viewX = Canvas.destX;
                  Canvas.viewY = Canvas.destY;
                  Canvas.viewZ = Canvas.destZ;
                  if (var7 != 15) {
                     Canvas.destAngle = Canvas.viewAngle = var7 << 7;
                     Canvas.finishRotation(true);
                  }

                  if ((var5 & '耀') != 0) {
                     Game.advanceTurn();
                  }

                  if (Canvas.state != 18) {
                     Canvas.startRotation(false);
                     Canvas.viewPitch = Canvas.destPitch;
                  } else {
                     Canvas.destPitch = 0;
                     Canvas.viewPitch = 0;
                  }

                  Game.gotoTriggered = true;
                  Canvas.automapDrawn = false;
               }

               Player.relink();
               Canvas.clearEvents();
               Canvas.updateFacingEntity = true;
               Canvas.invalidateRect();
               break;
            case 16:
               Canvas.abortMove = true;
               break;
            case 17:
               var17 = this.getUByteArg();
               var18 = this.getUByteArg();
               var7 = this.getUByteArg() * 100;
               var31 = Render.mapSprites[Render.S_ENT + var17];
               var9 = Render.mapSpriteInfo[var17] & 255;
               var10 = (Render.mapSpriteInfo[var17] & '\uff00') >> 8;
               Render.mapSpriteInfo[var17] = Render.mapSpriteInfo[var17] & -65281 | var18 << 8;
               Canvas.staleView = true;
               if (var31 != -1) {
                  var10000 = Game.entities[var31];
                  var10000.info |= 4194304;
                  if (Game.entities[var31].monster != null) {
                     Game.entities[var31].monster.frameTime = Integer.MAX_VALUE;
                  }
               }

               if (var9 == 173) {
                  var49 = Render.mapSpriteInfo;
                  var49[var17] &= -262145;
                  var49 = Render.mapSpriteInfo;
                  var49[var17] |= var18 << 18;
                  short[] var51;
                  if (var10 == 0 && var18 == 1) {
                     var51 = Render.mapSprites;
                     var10001 = Render.S_Z + var17;
                     var51[var10001] = (short)(var51[var10001] - 11);
                  } else if (var10 == 1 && var18 == 0) {
                     var51 = Render.mapSprites;
                     var10001 = Render.S_Z + var17;
                     var51[var10001] = (short)(var51[var10001] + 11);
                  }
               }

               if (var7 > 0) {
                  var1 = this.evWait(var7);
               }
               break;
            case 18:
               if (Canvas.state == 18) {
                  Game.activeCamera.keyThreadResumeCount = this.getUByteArg();
                  Game.activeCamera.keyThread = this;
                  Game.activeCamera.NextKey();
                  this.unpauseTime = -1;
                  var1 = 2;
               } else {
                  this.getUByteArg();
               }
               break;
            case 19:
               var17 = this.getUByteArg();
               var19 = this.getByteArg();
               var20 = Render.mapSprites[Render.S_ENT + var17];
               if (var20 != -1) {
                  var23 = Game.entities[var20];
                  if (var23.monster != null) {
                     var23.info |= 131072;
                     var23.pain(var19, (Entity)null);
                     if (var23.monster.ce.getStat(0) <= 0) {
                        var23.died(false, (Entity)null);
                     }
                  } else {
                     var23.died(false, (Entity)null);
                  }
               }

               Canvas.staleView = true;
               break;
            case 20:
               var16 = this.getByteArg();
               var19 = this.getByteArg();
               var37 = this.getByteArg();
               if (var16 > 0) {
                  Player.painEvent((Entity)null, false);
                  Hud.damageTime = App.time + 1000;
                  if (var37 != -1) {
                     var8 = 256 - (Canvas.viewAngle & 1023) >> 7;
                     var7 = var37 + var8 + 1 & 7;
                     Hud.damageDir = var7;
                  }

                  Combat.totalDamage = 1;
                  Player.pain(var16, (Entity)null, true);
               } else if (var16 < 0) {
                  Player.addHealth(-var16);
               }

               Player.addArmor(-var19);
               break;
            case 21:
               var5 = this.getUShortArg();
               var6 = var5 >> 10;
               var43 = (var6 & 4) == 0 && Canvas.state != 6;
               var6 &= 3;
               var5 &= 1023;
               var31 = Render.mapSprites[Render.S_ENT + var5];
               if (var31 != -1) {
                  var27 = Game.entities[var31];
                  if (var6 != 1 && var6 != 0) {
                     if (var6 == 2) {
                        Game.setLineLocked(var27, true);
                        Sound.playSound(14);
                     } else {
                        Game.setLineLocked(var27, false);
                        Sound.playSound(14);
                     }
                  } else {
                     if (var6 == 1 && var27.def.eType == 5) {
                        Game.setLineLocked(var27, false);
                     }

                     if (Game.performDoorEvent(var6, var43 ? this : null, var27, var43 ? 1 : 0, false) && var43) {
                        this.unpauseTime = -1;
                        var1 = 2;
                     }
                  }
               }
               break;
            case 22:
               var17 = this.getUByteArg();
               var18 = this.getUByteArg();
               var7 = var18 >> 6 & 3;
               var6 = 1 << (var18 & 63);
               var31 = Render.mapSprites[Render.S_ENT + var17];
               if (var31 != -1) {
                  var27 = Game.entities[var31];
                  if (var27.monster != null) {
                     EntityMonster var52;
                     if (var7 == 0) {
                        var52 = var27.monster;
                        var52.flags = (short)(var52.flags | var6);
                        if (var6 == 1 && var27.def.eSubType == 14) {
                           var49 = Render.mapSpriteInfo;
                           var10001 = var27.getSprite();
                           var49[var10001] |= 131072;
                        }
                     } else if (var7 == 1) {
                        var52 = var27.monster;
                        var52.flags = (short)(var52.flags & ~var6);
                     } else {
                        var27.monster.flags = (short)var6;
                     }

                     if (var6 == 2048 && (var7 == 0 || var7 == 2)) {
                        var27.info &= -131073;
                     } else if (var6 == 2048) {
                        var27.info |= 131072;
                     }

                     var27.info |= 4194304;
                  }
               }
               break;
            case 23:
               var5 = this.getUShortArg();
               var6 = (var5 & '耀') >> 15 << 19;
               var5 &= 32767;
               var7 = Render.tileEvents[var5 * 2 + 1] & -524289;
               Render.tileEvents[var5 * 2 + 1] = var7 | var6;
               break;
            case 24:
               var17 = this.getUByteArg();
               var49 = Render.mapSpriteInfo;
               var49[var17] |= 65536;
               var18 = Render.mapSprites[Render.S_ENT + var17];
               if (var18 != -1) {
                  var34 = Game.entities[var18];
                  var34.info |= 4194304;
                  Game.unlinkEntity(var34);
                  var40 = var34.def;
                  if (var40.eType == 10) {
                     if (var40.eSubType != 8 && var40.eSubType != 10) {
                        Game.destroyedObject(var17);
                     }
                  } else if (var40.eType != 6 || var40.eSubType != 1 && var40.eSubType != 2 && (var40.eSubType != 0 || var40.parm != 21)) {
                     if (var40.eType == 2) {
                        corpsifyMonster(var34.linkIndex % 32, var34.linkIndex / 32, var34);
                        Game.remove(var34);
                        var34.info |= 4194304;
                     }
                  } else {
                     Game.foundLoot(var17, 1);
                  }
               }

               Canvas.updateFacingEntity = true;
               break;
            case 25:
               var5 = this.getUShortArg();
               var18 = this.getUByteArg();
               var20 = (short)(var5 & 31);
               var31 = (short)(var5 >> 5 & 31);
               var26 = (short)(var5 >> 10 & 31);
               EntityDef var30 = EntityDef.lookup(var18);
               if (var30 == null) {
                  App.Error(new Exception("Cannot find an entity to drop"), 109);
               }

               Game.spawnDropItem((var20 << 6) + 32, (var31 << 6) + 32, var18, var30.eType, var30.eSubType, var30.parm, var26, true);
               Canvas.staleView = true;
               break;
            case 26:
               var17 = (short)this.getByteArg();
               --Game.scriptStateVars[var17];
               if (var17 == 6 && Game.scriptStateVars[var17] == 1) {
                  Player.removeStatusEffect(13);
               }
               break;
            case 27:
               var17 = (short)this.getByteArg();
               ++Game.scriptStateVars[var17];
               if (var17 == 6 && Game.scriptStateVars[var17] == 1) {
                  Player.removeStatusEffect(13);
               }
               break;
            case 28:
               var17 = this.getUByteArg();
               var18 = Render.mapSprites[Render.S_ENT + var17];
               if (var18 == -1 || Game.entities[var18].monster == null) {
                  App.Error(23);
               }

               var34 = Game.entities[var18];
               if (var34.def.eType == 2) {
                  var8 = var34.getSprite();
                  var34.monster.frameTime = 0;
                  Render.mapSpriteInfo[var8] = Render.mapSpriteInfo[var8] & -65281 | 0;
                  Game.activate(var34, true, false, false, true);
               }
               break;
            case 29:
               Game.cinematicWeapon = this.getByteArg();
               if (!Game.skippingCinematic && Canvas.state == 18) {
                  var5 = Game.cinematicWeapon * 9;
                  Combat.animLoopCount = Combat.weapons[var5 + 7] - 1;
                  Combat.animTime = Combat.weapons[var5 + 8];
                  Combat.animTime *= 10;
                  Combat.animStartTime = App.gameTime;
                  Combat.animEndTime = Combat.animStartTime + Combat.animTime;
                  Combat.flashTime = Game.cinematicWeapon == 3 ? 200 : 0;
                  Combat.flashDoneTime = Combat.animStartTime + Combat.flashTime;
               }
               break;
            case 30:
               var17 = this.getUByteArg();
               if (Render.mapSprites[Render.S_ENT + var17] != -1) {
                  Entity var48 = Game.entities[Render.mapSprites[Render.S_ENT + var17]];
                  ParticleSystem.spawnMonsterBlood(var48, false);
               }
               break;
            case 31:
               var17 = this.getUByteArg();
               var6 = var17 >> 4 & 7;
               var7 = var17 & 15;
               var8 = this.getUShortArg();
               if ((var17 & 128) != 0) {
                  var9 = ((var8 >> 11 & 31) << 6) + 32;
                  var10 = ((var8 >> 6 & 31) << 6) + 32;
                  var11 = (var8 & 63) + Render.getHeight(var9, var10);
                  ParticleSystem.spawnParticles(var6, ParticleSystem.levelColors[var7], var9, var10, var11);
               } else {
                  ParticleSystem.spawnParticles(var6, ParticleSystem.levelColors[var7], var8);
               }
               break;
            case 32:
               var5 = this.getUShortArg();
               var21 = (var5 & '耀') == 32768;
               if (Game.skippingCinematic) {
                  if (var21) {
                     Render.endFade();
                  }
               } else if (var21) {
                  Render.startFade(var5 & 32767, 2);
               } else {
                  Render.startFade(var5, 1);
               }
               break;
            case 33:
               var17 = this.getUByteArg();
               var18 = this.getUByteArg();
               var37 = this.getByteArg();
               if (this.throwAwayLoot) {
                  var15 = 1;
                  Game.foundLoot(Canvas.viewX + Canvas.viewStepX, Canvas.viewY + Canvas.viewStepY, Canvas.viewZ, 1);
               } else if (var37 == 0) {
                  var17 = (short)(var17 << 8 | var18);
                  var31 = Render.mapSprites[Render.S_ENT + var17];
                  if (var31 == -1) {
                     App.Error(new Exception("Sprite index " + var17 + " error."), 16);
                  }

                  if (!Game.entities[var31].touched()) {
                     var15 = 1;
                  }
               } else {
                  var40 = EntityDef.lookup(var17);
                  if (var40 == null) {
                     App.Error(new Exception("Cannot find an entity to give"), 109);
                  }

                  var18 = (short)((byte)var18);
                  if (var18 < 0) {
                     if (!Player.give(var40.eSubType, var40.parm, var18, true)) {
                        var15 = 1;
                     }
                  } else {
                     var27 = Game.spawnDropItem(0, 0, var17, var40, var18, false);
                     byte var29 = var27.def.eType;
                     if (!var27.touched()) {
                        Game.remove(var27);
                        var15 = 1;
                     } else if (var29 != 4 && var18 > 0) {
                        Game.foundLoot(Canvas.viewX + Canvas.viewStepX, Canvas.viewY + Canvas.viewStepY, Canvas.viewZ, 1);
                     }
                  }
               }
               break;
            case 34:
               var17 = this.getUByteArg();
               var18 = this.getUByteArg();
               var20 = Render.mapSprites[Render.S_ENT + var17];
               if (var20 != -1) {
                  var23 = Game.entities[var20];
                  var23.info |= 4194304;
                  var23.name = (short)(var18 | Canvas.loadMapStringID << 10);
               }
               break;
            case 35:
               boolean var46 = false;
               var6 = this.getUShortArg();
               var7 = this.getUByteArg();
               if ((var6 & '耀') != 0) {
                  var7 <<= 8;
                  var7 |= this.getUByteArg();
                  var6 &= 32767;
                  var46 = true;
               }

               var31 = this.getUByteArg();
               var26 = Render.mapSprites[Render.S_X + var6];
               short var28 = Render.mapSprites[Render.S_Y + var6];
               if (!var46) {
                  EntityDef var32 = EntityDef.lookup(var7);
                  if (var32 == null) {
                     App.Error(new Exception("Cannot find an entity to drop"), 109);
                  }

                  Game.spawnDropItem(var26, var28, var7, var32, var31, true);
               } else {
                  var33 = Render.mapSprites[Render.S_ENT + var7];
                  Entity var38 = Game.entities[var33];
                  int var41 = Render.getHeight(var26, var28);
                  Render.mapSprites[Render.S_X + var7] = (short)var26;
                  Render.mapSprites[Render.S_Y + var7] = (short)var28;
                  Render.mapSprites[Render.S_Z + var7] = (short)(32 + var41);
                  Render.relinkSprite(var7);
                  Game.unlinkEntity(var38);
                  Game.linkEntity(var38, var26 >> 6, var28 >> 6);
                  Game.throwDropItem(var26, var28, var41, var38);
                  var38.info |= 4194304;
               }

               Canvas.staleView = true;
               break;
            case 36:
               var17 = this.getUByteArg();
               var18 = this.getShortArg();
               var20 = Render.mapSprites[Render.S_ENT + var17];
               if (var20 != -1) {
                  var23 = Game.entities[var20];
                  if (var18 != -1) {
                     Game.addEntityDeathFunc(var23, var18);
                  } else {
                     Game.removeEntityFunc(var23);
                  }
               }
               break;
            case 37:
               var17 = this.getUByteArg();
               var18 = this.getUByteArg();
               Sound.playSound(var17, 0);
               break;
            case 38:
               var5 = this.getUShortArg();
               var6 = var5 >> 14 & 3;
               var5 &= 16383;
               var34 = null;
               if (Render.mapSprites[Render.S_ENT + var5] != -1) {
                  var34 = Game.entities[Render.mapSprites[Render.S_ENT + var5]];
                  if (var34.def.eType == 3) {
                     var34.param = var6;
                     break;
                  }
               }

               App.Error(14);
            case 41:
               if (Canvas.showingLoot) {
                  this.unpauseTime = 1;
                  return 2;
               }

               this.composeLootDialog();
               if (!this.throwAwayLoot) {
                  Game.skipAdvanceTurn = true;
                  Game.queueAdvanceTurn = false;
                  this.unpauseTime = -1;
                  var1 = 2;
               }
               break;
            case 39:
               var17 = this.getUByteArg();
               var18 = this.getUByteArg();
               var20 = this.getUByteArg();
               var31 = this.getUByteArg();
               var9 = Game.getMixingIndex(var17);
               if (var9 != -1) {
                  Game.mixingStations[var9 + 1] = var18;
                  Game.mixingStations[var9 + 2] = var20;
                  Game.mixingStations[var9 + 3] = var31;
               }
               break;
            case 40:
               var17 = this.getUByteArg();
               var6 = this.getUShortArg();
               break;
            case 42:
               var5 = this.getUShortArg();
               var6 = var5 & '밀';
               var43 = (var6 & '耀') != 0;
               var8 = var5 >> 5 & 31;
               var9 = var5 & 31;
               var6 &= -32769;
               if ((var6 & 1024) != 0) {
                  if (var43) {
                     var49 = Entity.baseVisitedTiles;
                     var49[var9] &= ~(1 << var8);
                  } else {
                     var49 = Entity.baseVisitedTiles;
                     var49[var9] |= 1 << var8;
                  }
               }

               var6 &= -1025;
               var6 >>= 8;
               byte[] var50;
               if (var43) {
                  var50 = Render.mapFlags;
                  var50[var9 * 32 + var8] = (byte)(var50[var9 * 32 + var8] & ~var6);
               } else {
                  var50 = Render.mapFlags;
                  var50[var9 * 32 + var8] = (byte)(var50[var9 * 32 + var8] | var6);
               }
               break;
            case 43:
               var17 = this.getUByteArg();
               var18 = this.getUByteArg();
               Player.updateQuests(var17, var18);
               if (var18 == 0) {
                  Player.showHelp((short)6, true);
               }
               break;
            case 44:
               var5 = this.getUShortArg();
               byte var36 = 0;
               if (Render.mapSprites[Render.S_ENT + var5] != -1) {
                  if (Player.give(0, 25, -1, true)) {
                     var36 = 25;
                  } else if (Player.give(0, 26, -1, true)) {
                     var36 = 26;
                  } else if (Player.give(0, 27, -1, true)) {
                     var36 = 27;
                  }
               }

               if (var36 == 0) {
                  Game.scriptStateVars[4] = 0;
               } else {
                  Game.scriptStateVars[4] = 2;
               }
               break;
            case 45:
               var17 = this.getUByteArg();
               Player.baseCe.addStat(var17 >> 5 & 7, (var17 & 31) << 27 >> 27);
               Player.updateStats();
               break;
            case 46:
               var17 = this.getUByteArg();
               Player.cocktailDiscoverMask |= 1 << var17 - 5;
               break;
            case 47:
               var5 = this.getUShortArg();
               var18 = this.getUByteArg();
               var20 = this.getUByteArg();
               var23 = null;
               if (Render.mapSprites[Render.S_ENT + var5] != -1) {
                  var23 = Game.entities[Render.mapSprites[Render.S_ENT + var5]];
                  if ((var23.info & 16842752) != 0) {
                     var27 = Game.findMapEntity(var18, var20, 1030);
                     if (null == var27) {
                        var23.resurrect((var18 << 6) + 32, (var20 << 6) + 32, 32);
                        break;
                     }
                  }
               }

               var15 = -1;
               break;
            case 48:
               var5 = this.getUShortArg();
               var6 = var5 >> 14 & 3;
               if (var6 > 0) {
                  ++var6;
               }

               var7 = var5 >> 7 & 127;
               if (var7 > 0) {
                  var7 = var7 + 1 << 4;
               }

               var8 = var5 & 127;
               if (var8 > 0) {
                  var8 = var8 + 1 << 4;
               }

               Canvas.startShake(var7, var6, var8);
               break;
            case 49:
               Hud.showSpeechBubble(this.getUShortArg());
               break;
            case 50:
               Game.awardSecret();
               break;
            case 51:
               var5 = this.getUShortArg();
               var6 = var5 >> 12 & 15;
               var37 = this.getByteArg();
               var5 &= 4095;
               if (Render.mapSprites[Render.S_ENT + var5] != -1) {
                  var23 = Game.entities[Render.mapSprites[Render.S_ENT + var5]];
                  setAIGoal(var23, var6, var37);
               } else {
                  App.Error(76);
               }
               break;
            case 52:
               if (Game.interpolatingMonsters) {
                  Game.snapMonsters(true);
               }

               while(Game.combatMonsters != null) {
                  Game.combatMonsters.undoAttack();
               }

               Game.endMonstersTurn();
               Canvas.clearEvents();
               Game.advanceTurn();
               break;
            case 53:
               var16 = this.getByteArg();
               var6 = (var16 & 248) >> 3;
               var5 = var16 & 7;
               if (var5 == 1) {
                  Canvas.startKicking(false);
                  Canvas.kickingDir = var6 << 7;
               } else {
                  CardGames.initGame(var5, this, true);
                  this.unpauseTime = -1;
                  var1 = 2;
               }
               break;
            case 54:
               if (Canvas.isChickenKicking) {
                  Canvas.endKickingGame();
                  if (Canvas.kickingFromMenu) {
                     Canvas.backToMain(false);
                     MenuSystem.setMenu(24);
                     return 1;
                  }

                  Player.selectPrevWeapon();
               }
               break;
            case 55:
               if (Canvas.isChickenKicking) {
                  Canvas.endKickingRound();
               }
               break;
            case 56:
               var6 = this.getUShortArg();
               var5 = var6 >> 12 & 15;
               var6 &= 4095;
               if (var5 == 0) {
                  if ((Player.weapons & 2L) != 0L) {
                     var5 = 1;
                  } else if ((Player.weapons & 4L) != 0L) {
                     var5 = 2;
                  }
               }

               if (Render.mapSprites[Render.S_ENT + var6] != -1) {
                  var34 = Game.entities[Render.mapSprites[Render.S_ENT + var6]];
                  Player.ce.weapon = var5;
                  Combat.performAttack((Entity)null, var34, 0, 0, true);
               }

               var1 = this.evWait(1);
               break;
            case 57:
               Render.buildFogTables(this.getIntArg());
               break;
            case 58:
               var5 = this.getIntArg();
               Render.startFogLerp(var5 & 2047, var5 >> 11 & 2047, (var5 >> 22 & 255) * 100);
               break;
            case 59:
               var17 = this.getUByteArg();
               var10 = this.getUByteArg() * 100;
               var11 = this.getIntArg();
               var7 = var11 & 2047;
               var6 = var11 >> 11 & 2047;
               var9 = var11 >> 22 & 3;
               var8 = (var11 >> 24 & 255) - 48;
               var12 = Game.allocLerpSprite(this, var17, (var9 & 2) != 0);
               if (var12 == null) {
                  return 0;
               }

               var12.dstX = var6;
               var12.dstY = var7;
               var13 = Render.mapSprites[Render.S_ENT + var17];
               if (var13 != -1) {
                  var10000 = Game.entities[var13];
                  var10000.info |= 4194304;
               }

               var12.dstZ = Render.getHeight(var12.dstX, var12.dstY) + var8;
               var12.srcX = Render.mapSprites[Render.S_X + var17];
               var12.srcY = Render.mapSprites[Render.S_Y + var17];
               var12.srcZ = Render.mapSprites[Render.S_Z + var17];
               var12.srcScale = var12.dstScale = Render.mapSprites[Render.S_SCALEFACTOR + var17];
               var12.startTime = App.gameTime;
               var12.travelTime = var10;
               var12.calcDist();
               var12.flags = var9 & 3;
               if (var10 == 0) {
                  if ((Game.updateLerpSprite(var12) & 1) != 0) {
                     Canvas.invalidateRect();
                  } else {
                     var1 = 0;
                  }
               } else if ((var12.flags & 1) == 0) {
                  Game.skipAdvanceTurn = true;
                  Game.queueAdvanceTurn = false;
                  this.unpauseTime = -1;
                  var1 = 2;
               }
               break;
            case 60:
               Player.disabledWeapons = (long)this.getShortArg();
               if ((Player.disabledWeapons & 1L << Player.ce.weapon) != 0L) {
                  Player.selectNextWeapon();
               }
               break;
            case 61:
               var5 = this.getUShortArg();
               var6 = this.getUShortArg();
               var20 = this.getUByteArg();
               LerpSprite var25 = Game.allocLerpSprite(this, var5, false);
               if (var25 == null) {
                  return 0;
               }

               var25.srcX = var25.dstX = Render.mapSprites[Render.S_X + var5];
               var25.srcY = var25.dstY = Render.mapSprites[Render.S_Y + var5];
               var25.srcZ = var25.dstZ = Render.mapSprites[Render.S_Z + var5];
               var25.srcScale = Render.mapSprites[Render.S_SCALEFACTOR + var5];
               var25.dstScale = var20 << 1;
               var26 = Render.mapSprites[Render.S_ENT + var5];
               if (var26 != -1) {
                  var10000 = Game.entities[var26];
                  var10000.info |= 4194304;
               }

               var25.startTime = App.gameTime;
               var25.travelTime = var6;
               if (var6 == 0) {
                  if ((Game.updateLerpSprite(var25) & 1) != 0) {
                     Canvas.invalidateRect();
                  } else {
                     var1 = 0;
                  }
               } else if ((var25.flags & 1) == 0) {
                  Game.skipAdvanceTurn = true;
                  Game.queueAdvanceTurn = false;
                  this.unpauseTime = -1;
                  var1 = 2;
               }
               break;
            case 62:
               Player.giveMedal(this.getByteArg(), this);
               this.unpauseTime = 0;
               var1 = 2;
               break;
            case 63:
               Player.giveBook(this.getByteArg(), this);
               this.unpauseTime = 0;
               var1 = 2;
               break;
            case 64:
               Player.offerBook(this.getByteArg(), this);
               this.unpauseTime = -1;
               var1 = 2;
               break;
            case 65:
               var5 = Game.getMixingIndex(this.getUByteArg());
               if (var5 != -1 && !Game.mixingStationEmpty(var5)) {
                  Canvas.curStation = var5;
                  Canvas.setState(20);
               } else {
                  Hud.addMessage((short)1, (short)142, 2);
               }
               break;
            case 66:
               if (this.debugString == null) {
                  this.debugString = Text.getLargeBuffer();
               }

               var16 = this.getByteArg();
               if (var16 == 0) {
                  for(char var24 = (char)this.getUByteArg(); var24 != 0; var24 = (char)this.getUByteArg()) {
                     this.debugString.append(var24);
                  }
               } else if (var16 == 1) {
                  var18 = this.getUByteArg();
                  this.debugString.append(Game.scriptStateVars[var18]);
               }

               if (this.peekNextCmd() != 66) {
                  this.debugString.dispose();
                  this.debugString = null;
               }
               break;
            case 67:
               MenuSystem.setMenu(this.getUByteArg());
               break;
            case 68:
               var17 = this.getUByteArg();
               var21 = (var17 & 128) != 0;
               setupCamera(var17 & 127);
               Game.activeCamera.cameraThread = this;
               break;
            case 69:
               var16 = this.getByteArg();
               var6 = Canvas.viewAngle & 1023;
               var7 = var16 << 7;
               if (var7 - var6 > 512) {
                  var7 -= 1024;
               } else if (var7 - var6 < -512) {
                  var7 += 1024;
               }

               if (var6 != var7) {
                  Canvas.viewAngle = var6;
                  Canvas.destAngle = var7;
                  Canvas.startRotation(false);
                  Canvas.gotoThread = this;
                  this.unpauseTime = -1;
                  var1 = 2;
               }
               break;
            case 70:
               var17 = this.getUByteArg();
               if ((var17 & 128) != 0) {
                  Player.removeStatusEffect(var17 & 127);
               } else {
                  var19 = this.getByteArg();
                  byte var22 = 30;
                  if (var17 == 2) {
                     var22 = 20;
                  } else if (var17 == 9) {
                     var22 = 10;
                  } else if (var17 == 1) {
                     var22 = 10;
                  } else if (var17 == 11) {
                     var22 = 6;
                  } else if (var17 == 17) {
                     var22 = 5;
                  }

                  Player.addStatusEffect(var17, var19, var22);
                  Player.translateStatusEffects();
               }
               break;
            case 71:
               var17 = this.getUByteArg();
               var6 = this.getUByteArg() & 31;
               var7 = this.getUByteArg() & 31;
               Player.setQuestTile(var17, var6, var7);
               break;
            case 72:
               var5 = this.getUShortArg();
               var18 = (short)((this.getUByteArg() << 6) + 32);
               var20 = (short)((this.getUByteArg() << 6) + 32);
               var23 = Game.entities[Render.mapSprites[Render.S_ENT + var5]];
               if (var23.monster != null) {
                  corpsifyMonster(var18, var20, var23);
               }
               break;
            case 73:
               var16 = this.getByteArg();
               if (var16 == 0) {
                  stripInventory();
               } else if (var16 == 1) {
                  restoreInventory();
               }
               break;
            case 74:
               Player.gameCompleted = true;
               Canvas.endingGame = true;
               Canvas.setState(14);
               break;
            case 75:
               var5 = this.getIntArg();
               var6 = this.getUShortArg();
               var7 = var5 >> 22 & 1023;
               var8 = var5 >> 17 & 31;
               var9 = var5 >> 12 & 31;
               var11 = (var5 >> 4 & 255) - 48;
               var10 = var5 & 15;
               var12 = Game.allocLerpSprite(this, var7, (var10 & 2) != 0);
               if (var12 == null) {
                  return 0;
               }

               var12.dstX = 32 + (var8 << 6);
               var12.dstY = 32 + (var9 << 6);
               var13 = Render.mapSprites[Render.S_ENT + var7];
               if (var13 != -1) {
                  var10000 = Game.entities[var13];
                  var10000.info |= 4194304;
               }

               var12.dstZ = Render.getHeight(var12.dstX, var12.dstY);
               var12.srcX = Render.mapSprites[Render.S_X + var7];
               var12.srcY = Render.mapSprites[Render.S_Y + var7];
               var12.srcZ = Render.mapSprites[Render.S_Z + var7];
               var12.srcScale = var12.dstScale = Render.mapSprites[Render.S_SCALEFACTOR + var7];
               var12.height = var11;
               var12.startTime = App.gameTime;
               var12.travelTime = var6;
               var12.calcDist();
               var12.flags = var10 & 3;
               var12.flags |= 4;
               if (var6 == 0) {
                  if ((Game.updateLerpSprite(var12) & 1) != 0) {
                     Canvas.invalidateRect();
                  } else {
                     var1 = 0;
                  }
               } else if ((var12.flags & 1) == 0) {
                  Game.skipAdvanceTurn = true;
                  Game.queueAdvanceTurn = false;
                  this.unpauseTime = -1;
                  var1 = 2;
               }
               break;
            default:
               App.Error(new Exception("Cannot handle event: " + var4[this.IP]), 2);
            }

            if (var3) {
               Game.scriptStateVars[7] = var15;
            }
         }

         return var1;
      }
   }

   public void init() {
      this.FP = this.IP = this.stackPtr = 0;
      this.unpauseTime = 0;
      this.state = 2;
      this.throwAwayLoot = false;
   }

   public void reset() {
      this.inuse = false;
      this.init();
   }

   public int attemptResume(int var1) {
      if (this.stackPtr == 0) {
         return 1;
      } else if (this.unpauseTime != -1 && var1 >= this.unpauseTime) {
         this.unpauseTime = 0;
         return this.run();
      } else {
         return 2;
      }
   }

   public int getIndex() {
      for(int var1 = 0; var1 < Game.scriptThreads.length; ++var1) {
         if (this == Game.scriptThreads[var1]) {
            return var1;
         }
      }

      return -1;
   }

   private int getStackVar(int var1) {
      return this.scriptStack[this.stackPtr - var1];
   }

   private int pop() {
      return this.scriptStack[--this.stackPtr];
   }

   private void push(boolean var1) {
      if (var1) {
         this.push(1);
      } else {
         this.push(0);
      }

   }

   private void push(int var1) {
      this.scriptStack[this.stackPtr++] = var1;
   }

   private short getUByteArg() {
      return (short)(Render.mapByteCode[++this.IP] & 255);
   }

   private byte getByteArg() {
      return Render.mapByteCode[++this.IP];
   }

   private int getUShortArg() {
      int var1 = (Render.mapByteCode[this.IP + 1] & 255) << 8 | Render.mapByteCode[this.IP + 2] & 255;
      this.IP += 2;
      return var1;
   }

   private short getShortArg() {
      short var1 = (short)(Render.mapByteCode[this.IP + 1] << 8 | Render.mapByteCode[this.IP + 2] & 255);
      this.IP += 2;
      return var1;
   }

   private int getIntArg() {
      int var1 = Render.mapByteCode[this.IP + 1] << 24 | (Render.mapByteCode[this.IP + 2] & 255) << 16 | (Render.mapByteCode[this.IP + 3] & 255) << 8 | Render.mapByteCode[this.IP + 4] & 255;
      this.IP += 4;
      return var1;
   }

   private void composeLootDialog() {
      Text var1 = Text.getLargeBuffer();
      if (lootSource != -1) {
         Text.composeTextField(lootSource, var1);
         Text.composeText((short)1, (short)145, var1);
         lootSource = -1;
      } else {
         Text.composeText((short)1, (short)146, var1);
      }

      if (!this.throwAwayLoot) {
         Canvas.showingLoot = true;
         Canvas.setState(8);
      }

      int var2 = 0;
      byte var3 = this.getByteArg();

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = this.getUShortArg();
         int var6 = var5 >> 12 & 15;
         short var11;
         if (var6 == 8) {
            var11 = (short)(var5 & 4095);
            var1.append('\u0088');
            Text.composeText(Canvas.loadMapStringID, var11, var1);
            var1.append("|");
         } else if (var6 == 7) {
            var11 = (short)(var5 & 4095);
            Player.updateQuests(var11, 0);
         } else if (var6 == 9) {
            var11 = (short)(var5 & 4095);
            var1.append('\u0088');
            Text.composeText((short)1, (short)177, var1);
            Text.composeText((short)0, (short)Player.bookMap[var11 * 4], var1);
            var1.append("|");
         } else {
            int var7 = (var5 & 4032) >> 6;
            int var8 = var5 & 63;
            ++var2;
            if (!this.throwAwayLoot) {
               Text.resetTextArgs();
               Text.addTextArg('\u0088');
               EntityDef var9;
               switch(var6) {
               case 0:
               case 4:
               case 6:
                  Player.give(var6, var7, var8, false);
                  var9 = EntityDef.find(6, var6, var7);
                  Text.addTextArg(var8);
                  Text.addTextArg((short)2, var9.longName);
                  Text.composeText((short)1, (short)92, var1);
                  break;
               case 1:
                  Player.give(1, var7, var8, true);
                  int var10 = var7 * 9;
                  if (Combat.weapons[var10 + 5] != 0) {
                     Player.give(2, Combat.weapons[var10 + 4], 10, true);
                  }

                  var9 = EntityDef.find(6, var6, var7);
                  Text.addTextArg((short)2, var9.longName);
                  Text.composeText((short)1, (short)93, var1);
                  break;
               case 2:
                  if (Game.difficulty != 4 || (1 << var7 & 274) != 0) {
                     Player.give(2, var7, var8, false);
                     var9 = EntityDef.find(6, var6, var7);
                     Text.addTextArg(var8);
                     Text.addTextArg((short)2, var9.longName);
                     Text.composeText((short)1, (short)92, var1);
                  }
               case 3:
               case 5:
               }
            }
         }
      }

      if (!this.throwAwayLoot) {
         var1.setLength(var1.length() - 1);
         Canvas.startDialog(this, var1, 4, 0, true);
      } else {
         var1.setLength(0);
         Text.resetTextArgs();
         Text.addTextArg(var3);
         Text.composeText((short)1, (short)186, var1);
         Hud.addMessage(var1, 3);
      }

      var1.dispose();
      Game.foundLoot(Canvas.viewX + Canvas.viewStepX, Canvas.viewY + Canvas.viewStepY, Canvas.viewZ, var2);
   }

   private static final void setAIGoal(Entity var0, int var1, int var2) {
      var0.monster.resetGoal();
      var0.monster.goalType = (byte)var1;
      if (var1 != 2 && var1 != 3) {
         if (var1 == 4 || var1 == 6) {
            var0.monster.goalParam = var2;
         }
      } else {
         var0.monster.goalParam = 1;
      }

      if (!Player.noclip) {
         if ((var0.info & 262144) == 0) {
            Game.activate(var0, true, false, false, true);
         }

         var0.aiThink(true);
      }

      if (var1 == 3) {
         EntityMonster var10000 = var0.monster;
         var10000.goalFlags &= -9;
         if (Game.combatMonsters != null) {
            Combat.performAttack(Game.combatMonsters, Game.combatMonsters.monster.target, 0, 0, false);
         }
      }

   }

   private static final void corpsifyMonster(int var0, int var1, Entity var2) {
      int var3 = var2.getSprite();
      Game.snapLerpSprites(var3);
      var2.monster.resetGoal();
      var2.monster.clearEffects();
      var2.undoAttack();
      Game.deactivate(var2);
      Render.mapSpriteInfo[var3] = Render.mapSpriteInfo[var3] & -130817 | 28672;
      Render.mapSprites[Render.S_X + var3] = (short)var0;
      Render.mapSprites[Render.S_Y + var3] = (short)var1;
      Render.mapSprites[Render.S_Z + var3] = (short)(Render.getHeight(var0, var1) + 32);
      Render.relinkSprite(var3);
      var2.info = var2.info & '\uffff' | 16777216 | 131072 | 4194304;
      var2.def = EntityDef.find(9, var2.def.eSubType, var2.def.parm);
      Game.unlinkEntity(var2);
      Game.linkEntity(var2, var0 >> 6, var1 >> 6);
      var2.checkMonsterDeath(false);
   }

   private static final void stripInventory() {
      Player.selectWeapon(0);
      System.arraycopy(Player.inventory, 0, Player.inventoryCopy, 0, Player.inventory.length);
      System.arraycopy(Player.ammo, 0, Player.ammoCopy, 0, Player.ammo.length);
      Player.weaponsCopy = (int)(Player.weapons & -1L);

      int var0;
      for(var0 = 0; var0 < 29; ++var0) {
         Player.inventory[var0] = 0;
      }

      for(var0 = 0; var0 < 10; ++var0) {
         Player.ammo[var0] = 0;
      }

      Player.weapons = 9L;
   }

   private static final void restoreInventory() {
      int var0;
      for(var0 = 0; var0 < 29; ++var0) {
         Player.give(0, var0, Player.inventoryCopy[var0], true);
      }

      for(var0 = 0; var0 < 10; ++var0) {
         Player.give(2, var0, Player.ammoCopy[var0], true);
      }

      for(var0 = 1; var0 < 17; ++var0) {
         int var1 = 1 << var0;
         if ((var1 & Player.weaponsCopy) != 0) {
            Player.give(1, var0, 1, true);
         }
      }

   }
}
