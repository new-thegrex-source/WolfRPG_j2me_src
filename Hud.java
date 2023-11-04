/* Decompiler 187ms, total 350ms, lines 822 */
import javax.microedition.lcdui.Image;

final class Hud {
   public static int touchMe = 1;
   private static final int MSG_DISPLAY_TIME = 700;
   private static final int MSG_FLASH_TIME = 100;
   private static final int SCROLL_START_DELAY = 750;
   private static final int MS_PER_CHAR = 50;
   private static final int MAX_MESSAGES = 5;
   public static final int REPAINT_EFFECTS = 1;
   public static final int REPAINT_TOP_BAR = 2;
   public static final int REPAINT_BOTTOM_BAR = 4;
   public static final int REPAINT_BUBBLE_TEXT = 8;
   public static final int REPAINT_SUBTITLES = 16;
   public static final int REPAINT_PLAYING_FLAGS = 11;
   public static final int REPAINT_CAMERA_FLAGS = 16;
   public static int repaintFlags = 0;
   public static final int MSG_FLAG_NONE = 0;
   public static final int MSG_FLAG_FORCE = 1;
   public static final int MSG_FLAG_CENTER = 2;
   public static final int MSG_FLAG_BLUE = 4;
   private static final int STATUSBAR_ICON_PICKUP = 0;
   private static final int STATUSBAR_ICON_ATTACK = 1;
   private static final int STATUSBAR_ICON_CHAT = 2;
   private static final int STATUSBAR_ICON_USE = 3;
   public static final int HUDARROWS_SIZE = 12;
   private static final int BUBBLE_TEXT_TIME = 1500;
   public static Image imgScope;
   public static Image imgActions;
   public static Image imgAttArrow;
   public static Image imgDamageVignette;
   public static Image imgBottomBarIcons;
   public static Image imgAmmoIcons;
   public static Image imgSoftKeyFill;
   public static final int ACTION_ICON_SIZE = 18;
   public static Image imgPortraitsSM;
   public static Image imgPlayerFaces;
   public static Image imgHudFill;
   public static Image imgIce;
   private static Text[] messages = new Text[5];
   static int[] messageFlags = new int[5];
   public static int msgCount;
   static int msgTime;
   private static int msgDuration;
   public static int subTitleID = -1;
   public static int subTitleTime = 0;
   public static int cinTitleID = -1;
   public static int cinTitleTime = 0;
   private static Text bubbleText = null;
   private static int bubbleTextTime = 0;
   public static int damageTime;
   public static int damageCount;
   public static int damageDir;
   public static Entity lastTarget;
   private static int monsterStartHealth;
   private static int monsterDestHealth;
   public static int playerStartHealth;
   public static int playerDestHealth;
   private static int monsterHealthChangeTime;
   public static int playerHealthChangeTime;
   public static boolean showCinPlayer = false;
   public static int drawTime;
   private static final int DEF_MESSAGE_TIME = 1500;
   private static final int FILLER_BEVEL_COLOR1 = -6645369;
   private static final int FILLER_BEVEL_COLOR2 = -14868980;
   static final int IMG_FACE_WIDTH = 25;
   static final int IMG_FACE_HALF_WIDTH = 12;
   static final int ARROW_WIDTH = 20;
   public static final int BTM_ICON_W = 30;
   public static final int BTM_ICON_H = 15;
   public static final int BTM_AMMO_H = 14;
   public static final int CHAT_HOOK_HEIGHT = 6;
   public static final int CHAT_HOOK_WIDTH = 10;

   public static final boolean startup() {
      for(int var0 = 0; var0 < messages.length; ++var0) {
         messages[var0] = new Text(64);
      }

      msgCount = 0;
      return true;
   }

   private static final void shiftMsgs() {
      if ((messageFlags[0] & 2) != 0) {
         Canvas.invalidateRect();
      }

      for(int var0 = 0; var0 < msgCount - 1; ++var0) {
         messages[var0].setLength(0);
         messages[var0].append(messages[var0 + 1]);
         messageFlags[var0] = messageFlags[var0 + 1];
      }

      --msgCount;
      messages[msgCount].setLength(0);
      messageFlags[msgCount] = 0;
      if (msgCount > 0) {
         calcMsgTime();
      }

   }

   private static final void calcMsgTime() {
      msgTime = App.time;
      int var0 = messages[0].length();
      if (var0 <= Canvas.menuHelpMaxChars) {
         msgDuration = 700;
      } else {
         msgDuration = var0 * 50;
         if ((messageFlags[0] & 2) != 0 && msgDuration > 1500) {
            msgDuration = 1500;
         }
      }

   }

   public static final void addMessage(short var0) {
      addMessage((short)1, var0, 0);
   }

   public static final void addMessage(short var0, short var1) {
      addMessage(var0, var1, 0);
   }

   public static final void addMessage(short var0, int var1) {
      addMessage((short)1, var0, var1);
   }

   public static final void addMessage(short var0, short var1, int var2) {
      Text var3 = Text.getSmallBuffer();
      Text.composeText(var0, var1, var3);
      addMessage(var3, var2);
      var3.dispose();
   }

   public static final void addMessage(Text var0) {
      addMessage(var0, 0);
   }

   public static final void addMessage(Text var0, int var1) {
      if (var0 != null) {
         if ((var1 & 1) != 0) {
            msgCount = 0;
         }

         if (msgCount <= 0 || !var0.equals(messages[msgCount - 1])) {
            if (msgCount == 5) {
               shiftMsgs();
            }

            messages[msgCount].setLength(0);
            messages[msgCount].append(var0);
            if ((var1 & 2) != 0) {
               messages[msgCount].wrapText((Canvas.viewRect[2] - 7) / 7);
            } else {
               messages[msgCount].dehyphenate();
            }

            messageFlags[msgCount] = var1;
            ++msgCount;
            if (msgCount == 1) {
               calcMsgTime();
               if ((var1 & 1) != 0) {
                  msgDuration *= 2;
               }
            }

         }
      }
   }

   public static final Text getMessageBuffer() {
      return getMessageBuffer(0);
   }

   public static final Text getMessageBuffer(int var0) {
      if ((var0 & 1) != 0) {
         msgCount = 0;
      }

      if (msgCount == 5) {
         shiftMsgs();
      }

      messages[msgCount].setLength(0);
      messageFlags[msgCount] = var0;
      return messages[msgCount];
   }

   public static final void finishMessageBuffer() {
      messages[msgCount].dehyphenate();
      ++msgCount;
      if (msgCount == 1) {
         calcMsgTime();
      }

   }

   private static final void drawFiller(Graphics var0) {
      int[] var1 = Canvas.screenRect;
      int[] var2 = Canvas.viewRect;
      int var3 = (var1[2] - var2[2]) / 2;
      int var4 = var1[3] - 50;
      if (var3 >= 1) {
         var0.fillRegion(imgHudFill, var1[0], 25, var3, var4);
         var0.drawBevel(-6645369, -14868980, var1[0], 25, var3, var4);
         var0.fillRegion(imgHudFill, var2[0] + var2[2], 25, var3, var4);
         var0.drawBevel(-6645369, -14868980, var2[0] + var2[2], 25, var3, var4);
         var0.eraseRgn(var1[0] + var3, 25, var2[2], (var4 - var2[3]) / 2);
         var0.eraseRgn(var1[0] + var3, var2[1] + var2[3], var2[2], (var4 - var2[3]) / 2);
      }
   }

   public static boolean isShiftingCenterMsg() {
      return msgCount > 0 && App.time - msgTime > msgDuration && (messageFlags[0] & 2) != 0;
   }

   private static final void drawTopBar(Graphics var0) {
      Text var1 = Text.getSmallBuffer();
      int var2 = 2;
      Entity var3 = Player.facingEntity;
      var0.fillRegion(imgHudFill, -Canvas.screenRect[0], 0, Canvas.hudRect[2], 25);
      var0.drawBevel(-6645369, -14868980, -Canvas.screenRect[0], 0, Canvas.hudRect[2], 25);
      if (Canvas.state != 13) {
         drawMonsterHealth(var0);
      }

      if (msgCount > 0 && App.time - msgTime > msgDuration + 100) {
         shiftMsgs();
      }

      byte var4 = -1;
      boolean var5 = false;
      boolean var6 = false;
      boolean var7 = false;
      int var8 = 0;
      int var9;
      int var10;
      if (msgCount > 0) {
         var1.setLength(0);
         var1.append(messages[0]);
         var6 = (messageFlags[0] & 2) != 0;
         var7 = (messageFlags[0] & 4) != 0;
         if (App.time - msgTime > msgDuration) {
            var5 = true;
         } else {
            var9 = var1.length() - Canvas.menuHelpMaxChars;
            if (var9 > 0) {
               var10 = App.time - msgTime;
               if (var10 > 750) {
                  var8 = (var10 - 750) / 50;
                  if (var8 > var9) {
                     var8 = var9;
                  }
               }
            }
         }
      } else if (Canvas.state != 1 && var3 != null && var3.def.eType != 0 && (var3.def.eType != 12 || (var3.info & 262144) != 0)) {
         var1.setLength(0);
         var9 = (var3.info & '\uffff') - 1;
         var10 = Render.mapSpriteInfo[var9] & 255;
         byte var11 = var3.def.eType;
         if ((var3.name & 1023) == var3.def.name && var3.def.longName != 92) {
            Text.composeTextField(2048 | var3.def.longName, var1);
         } else {
            Text.composeTextField(var3.name, var1);
         }

         var1.dehyphenate();
         if (var11 == 3) {
            var4 = 2;
         } else if (var11 == 2) {
            var1.setLength(0);
            if ((var3.name & 1023) != var3.def.name) {
               Text.composeTextField(var3.name, var1);
            } else {
               Text.composeTextField(var3.def.longName | 2048, var1);
            }

            if (var11 == 2 && (var3.monster.flags & 2048) != 0) {
               var4 = 2;
            } else {
               var4 = 1;
            }

            var1.dehyphenate();
         } else if (var11 == 6) {
            var4 = 0;
         } else if (var11 == 5) {
            var4 = 3;
            if (var11 == 5 && (var3.def.parm & 2) != 0) {
               var6 = true;
            }
         } else if (var11 == 12) {
            var6 = true;
         } else if (var11 == 7 && var3.def.eSubType == 1) {
            var6 = true;
         } else if (var11 == 7) {
            byte var12 = var3.def.eSubType;
            if (var12 == 3) {
               var4 = 1;
            } else if (var10 == 153 && (Render.mapSpriteInfo[var9] & '\uff00') >> 8 == 0) {
               var4 = 3;
            } else if (var3.def.parm == 1) {
               var4 = 3;
            } else if (var3.def.parm == 2) {
               byte var13 = 0;
               if ((Render.mapSpriteInfo[var9] & 255) == 158) {
                  var13 = 1;
               }

               if ((Render.mapSpriteInfo[var9] & '\uff00') >> 8 == var13) {
                  var4 = 3;
               }
            }
         } else if (var11 == 14) {
            if (var10 == 62) {
               var6 = true;
            } else if (var10 == 63) {
               var4 = 3;
            } else if (var10 == 61) {
               var4 = 1;
            } else if (var3.def.eSubType == 4) {
               var4 = 0;
            }
         } else if (var11 == 10) {
            if (var3.def.eSubType == 10) {
               var4 = 0;
            } else {
               var4 = 1;
            }
         }
      } else if (var3 != null && var3.def.eType == 12) {
         var9 = (var3.info & '\uffff') - 1;
         if ((Render.mapSpriteInfo[var9] & 255) == 153 && (Render.mapSpriteInfo[var9] & '\uff00') >> 8 == 0) {
            var4 = 3;
         } else {
            var5 = true;
         }
      } else {
         var5 = true;
      }

      if (!var5) {
         if (var6 && var1.length() > 0) {
            drawCenterMessage(var0, var1, var7 ? -16777097 : -16777216);
         } else {
            if (var4 != -1) {
               var0.drawRegion(imgActions, 0, var4 * 18, 18, 18, var2 - Canvas.screenRect[0], Canvas.screenRect[1] + 3, 20, 0);
               var2 += 20;
            }

            var9 = var1.length();
            if (var2 + 9 * (var9 + 1) > Canvas.hudRect[2]) {
               var9 = (Canvas.hudRect[2] - var2) / 7 - 1;
            }

            var0.drawString(var1, var2 - Canvas.screenRect[0], 7, 0, var8, var9);
         }
      }

      var1.dispose();
   }

   private static final void drawCenterMessage(Graphics var0, Text var1, int var2) {
      int var3 = var1.length();
      int var4 = var1.getStringWidth() + 8;
      if (var4 > Canvas.hudRect[2]) {
         var4 = Canvas.hudRect[2];
      }

      int var5 = -Canvas.screenRect[1] + 50;
      int var6 = -Canvas.screenRect[0] + Canvas.hudRect[2] / 2;
      int var7 = var1.getNumLines();
      var0.setColor(var2);
      var0.fillRect(var6 - var4 / 2, var5, var4 - 1, 12 * var7 + 3);
      var0.setColor(-5592406);
      var0.drawRect(var6 - var4 / 2, var5, var4 - 1, 12 * var7 + 3);
      int var8 = var5 + 3;
      int var9 = 0;

      int var10;
      Text var11;
      for(var11 = Text.getSmallBuffer(); (var10 = var1.findFirstOf('|', var9)) >= 0; var9 = var10 + 1) {
         var11.setLength(0);
         var1.substring(var11, var9, var10);
         var0.drawString(var11, var6, var8, 17, 0, var10 - var9);
         var8 += 12;
      }

      var11.setLength(0);
      var1.substring(var11, var9);
      var0.drawString(var11, var6, var8, 17, 0, var1.length() - var9);
      var11.dispose();
   }

   public static final void drawCinematicText(Graphics var0) {
      int var1 = Canvas.SCR_CX;
      byte var2 = 1;
      int var3 = imgPlayerFaces.getWidth();
      int[] var4 = Canvas.displayRect;
      int[] var5 = Canvas.cinRect;
      int[] var6 = Canvas.viewRect;
      var0.eraseRgn(0, 0, var4[2], var5[1] + var6[1]);
      var0.eraseRgn(0, var5[1] + var5[3] + var6[1], var4[2], Canvas.softKeyY - (var6[1] + var5[1] + var5[3]));
      int var7 = showCinPlayer ? Canvas.subtitleMaxChars - 5 : Canvas.subtitleMaxChars;
      Text var8 = Text.getLargeBuffer();
      if (cinTitleID != -1 && cinTitleTime > App.gameTime) {
         var8.setLength(0);
         Text.composeText(cinTitleID, var8);
         var8.wrapText(var7, 1, '\n');
         var0.drawString(var8, var1, 1, 1);
      }

      if (subTitleID != -1 && subTitleTime > App.gameTime) {
         int var9 = Canvas.viewRect[1] + Canvas.cinRect[1] + Canvas.cinRect[3];
         var9 += Canvas.screenRect[3] - var9 - 24 >> 1;
         var8.setLength(0);
         Text.composeText(subTitleID, var8);
         var8.wrapText(var7, 2, '\n');
         int var10 = var8.findFirstOf('\n', 0);
         if (var10 == -1) {
            if (showCinPlayer) {
               var1 = var3 + 5;
               var2 = 4;
               var0.drawRegion(imgPlayerFaces, 0, 0, 25, 25, var1 - var3, var9);
               var1 += 5;
            }

            var0.drawString(var8, var1, var9, var2);
         } else {
            if (showCinPlayer) {
               var1 = var3 + 5;
               var2 = 4;
               if (var10 > var8.length() - var10) {
                  var0.drawRegion(imgPlayerFaces, 0, 0, 25, 25, var1 - var3, var9);
               } else {
                  var0.drawRegion(imgPlayerFaces, 0, 0, 25, 25, var1 - var3, var9);
               }

               var1 += 5;
            }

            var0.drawString(var8, var1, var9, var2, 0, var10);
            var9 += 12;
            var0.drawString(var8, var1, var9, var2, var10 + 1, 9999);
         }
      }

      var8.dispose();
      drawBubbleText(var0);
   }

   private static final void drawEffects(Graphics var0) {
      int var1;
      int var2;
      if (Canvas.loadMapID >= 8 && TinyGL.fogRange < -1) {
         var1 = TinyGL.fogRange >> 4;
         var2 = (1024 + var1 << 8) / 1024;
         int var3 = imgIce.getHeight();
         byte var4 = 0;
         int var5 = -(var2 * var3 >> 8);
         var0.setScreenSpace(Canvas.viewRect);
         var0.drawImage(imgIce, var4, var5, 0, 0);
         var0.drawImage(imgIce, var4 + Canvas.viewRect[2], var5, 24, 2);
         var0.resetScreenSpace();
      }

      if (Canvas.state != 13) {
         Player.drawBuffs(var0);
      }

      if (App.time < damageTime && damageCount > 0 && Combat.totalDamage > 0) {
         if ((1 << damageDir & 193) != 0) {
            boolean var6 = false;
            var2 = Canvas.screenRect[3] - 25 - 25;
            byte var7 = 0;
            if (damageDir == 0) {
               var1 = Canvas.screenRect[2] - 20;
               var7 = 24;
            } else if (damageDir == 6) {
               var1 = 20;
               var7 = 12;
            } else {
               var1 = Canvas.screenRect[2] >> 1;
            }

            var0.drawRegion(imgAttArrow, 0, var7, 12, 12, var1, var2, 3, 0);
         }
      } else if (damageTime != 0) {
         damageTime = 0;
      }

   }

   public static final void drawDamageVignette(Graphics var0) {
      if (App.time < damageTime && damageCount >= 0 && Combat.totalDamage > 0) {
         byte var1 = 0;
         switch(damageDir) {
         case 0:
         case 6:
         case 7:
            var1 = 8;
            break;
         case 1:
            var1 = 2;
            break;
         case 2:
            var1 = 3;
            break;
         case 3:
            var1 = 15;
            break;
         case 4:
            var1 = 5;
            break;
         case 5:
            var1 = 4;
         }

         int var2 = imgDamageVignette.getWidth();
         int[] var3 = Canvas.viewRect;
         if ((var1 & 1) != 0) {
            var0.fillRegion(imgDamageVignette, var3[0], var3[1], var3[2], var2, 5);
         }

         if ((var1 & 2) != 0) {
            var0.fillRegion(imgDamageVignette, var3[0] + (var3[2] - var2), var3[1], var2, var3[3], 2);
         }

         if ((var1 & 4) != 0) {
            var0.fillRegion(imgDamageVignette, var3[0], var3[1], var2, var3[3], 0);
         }

         if ((var1 & 8) != 0) {
            var0.fillRegion(imgDamageVignette, var3[0], var3[1] + (var3[3] - var2), var3[2], var2, 6);
         }
      }

   }

   private static final void drawBottomBar(Graphics var0) {
      Text var1 = Text.getSmallBuffer();
      int var2 = Canvas.SCR_CX;
      int var3 = Canvas.hudRect[3] - 25;
      var0.fillRegion(imgHudFill, -Canvas.screenRect[0], var3, Canvas.hudRect[2], 25);
      int var4 = (var2 - 12) / 4 + 1;
      var0.drawRegion(imgBottomBarIcons, 0, 0, 30, 14, var4, var3 + 1, 17, 0);
      var1.setLength(0);
      var1.append(Player.ce.getStat(2));
      var0.drawString(var1, var4 - 2, var3 + 25 + 2, 33);
      var4 = (var2 - 12) / 4 * 3 + 1;
      var0.drawRegion(imgBottomBarIcons, 0, 16, 30, 14, var4, var3 + 1, 17, 0);
      var1.setLength(0);
      var1.append(Player.ce.getStat(0));
      var0.drawString(var1, var4 - 2, var3 + 25 + 2, 33);
      int var5 = 5 - Player.ce.getStat(0) * 5 / Player.ce.getStat(1);
      var0.drawRegion(imgPlayerFaces, 0, var5 * 25, 25, 25, var2, var3, 17, 0);
      var4 = var2 + 12 + (var2 - 12) / 4 + 1;
      byte var6 = 0;
      if (Player.ce.weapon == 3) {
         var0.drawRegion(imgAmmoIcons, 0, 126, 30, 14, var4, var3 + 1, 17, 0);
      } else if (Player.ce.weapon != 16) {
         var6 = Combat.weapons[Player.ce.weapon * 9 + 4];
         var0.drawRegion(imgAmmoIcons, 0, 14 * var6, 30, 14, var4, var3 + 1, 17, 0);
      }

      var1.setLength(0);
      if (var6 == 0) {
         var1.append("--");
      } else if (var6 == 8 && Player.ammo[var6] < 50) {
         var1.append("...");
         var3 -= 4;
      } else {
         var1.append(Player.ammo[var6]);
         if (Player.ce.weapon == 14) {
            var1.append('%');
         }
      }

      var0.drawString(var1, var4 - 2, var3 + 25 + 2, 33);
      var3 = Canvas.hudRect[3] - 25;
      var0.setColor(-6645369);
      var0.drawLine(Canvas.screenRect[0], var3, Canvas.screenRect[2], var3);
      var0.drawLine(Canvas.screenRect[0], var3, Canvas.screenRect[0], var3 + 25);
      var0.drawLine(var2 - 12, var3, var2 - 12, var3 + 25);
      var0.drawLine(var2 + 13, var3, var2 + 13, var3 + 25);
      var4 = (var2 - 12) / 2 + 1;
      var0.drawLine(var4, var3, var4, var3 + 25);
      var4 = var2 + 12 + (var2 - 12) / 2 + 1;
      if (Player.inventory[20] > 0) {
         var0.drawRegion(imgBottomBarIcons, 0, 30, 30, 15, var4 + (Canvas.screenRect[2] - var4) / 2, var3, 17, 0);
      }

      if (Player.inventory[19] > 0) {
         var0.drawRegion(imgBottomBarIcons, 0, 45, 30, 15, var4 + (Canvas.screenRect[2] - var4) / 2, var3 + 12 + 1, 17, 0);
      }

      var0.drawLine(var4, var3, var4, var3 + 25);
      var0.drawLine(var4, var3 + 12, Canvas.screenRect[2], var3 + 12);
      var0.setColor(-14868980);
      var0.drawLine(var4, var3 + 12 + 1, Canvas.screenRect[2], var3 + 12 + 1);
      var0.drawLine(Canvas.screenRect[0], var3 + 25, Canvas.screenRect[2], var3 + 25);
      var0.drawLine(Canvas.screenRect[2] - 1, var3, Canvas.screenRect[2] - 1, var3 + 25);
      var0.drawLine(var2 - 13, var3, var2 - 13, var3 + 25);
      var0.drawLine(var2 + 12, var3, var2 + 12, var3 + 25);
      var4 = (var2 - 12) / 2;
      var0.drawLine(var4, var3, var4, var3 + 25);
      var4 = var2 + 12 + (var2 - 12) / 2;
      var0.drawLine(var4, var3, var4, var3 + 25);
      var1.dispose();
   }

   public static final void draw(Graphics var0) {
      drawTime = App.getUpTimeMs();
      if ((repaintFlags & 1) != 0) {
         repaintFlags &= -2;
         if (Canvas.isZoomedIn) {
            int[] var1 = Canvas.viewRect;
            int var2 = imgScope.getWidth();
            int var3 = imgScope.getHeight();
            int var4 = var1[0] + (var1[2] - 2 * var2) / 2;
            int var5 = var1[1] + (var1[3] - 2 * var3) / 2;
            var0.drawRegion(imgScope, 0, 0, var2, var3, var4, var5, 0, 0);
            var0.drawRegion(imgScope, 0, 0, var2, var3, var4 + (var2 - 1), var5, 0, 2);
            var0.drawRegion(imgScope, 0, 0, var2, var3, var4 + (var2 - 1), var5 + (var3 - 1), 0, 3);
            var0.drawRegion(imgScope, 0, 0, var2, var3, var4, var5 + (var3 - 1), 0, 6);
            var0.setColor(0);
            int var6 = var4 - var1[0];
            int var7 = var1[0] + var1[2] - (var4 + (2 * var2 - 1));
            var0.fillRect(var1[0], var1[1], var6, var1[3]);
            var0.fillRect(var4 + (2 * var2 - 1), var1[1], var7, var1[3]);
            int var8 = var5 - var1[1];
            int var9 = var1[1] + var1[3] - (var5 + (2 * var3 - 1));
            var0.fillRect(var1[0] + var6, var1[1], var1[2] - (var6 + var7), var8);
            var0.fillRect(var1[0] + var6, var5 + (2 * var3 - 1), var1[2] - (var6 + var7), var9);
         }

         drawEffects(var0);
      }

      if ((repaintFlags & 2) != 0) {
         repaintFlags &= -3;
         Canvas.checkFacingEntity();
         drawTopBar(var0);
      }

      if ((repaintFlags & 8) != 0) {
         repaintFlags &= -9;
         drawBubbleText(var0);
      }

      if ((repaintFlags & 4) != 0) {
         repaintFlags &= -5;
         drawFiller(var0);
         drawBottomBar(var0);
      }

      if (cinTitleID != -1 && cinTitleTime < App.gameTime) {
         cinTitleID = -1;
         if (Canvas.state == 18) {
            repaintFlags |= 16;
         }
      }

      if (subTitleID != -1 && subTitleTime < App.gameTime) {
         subTitleID = -1;
         if (Canvas.state == 18) {
            repaintFlags |= 16;
         }
      }

      if ((repaintFlags & 16) != 0) {
         repaintFlags &= -17;
         drawCinematicText(var0);
      }

      drawTime = App.getUpTimeMs() - drawTime;
   }

   private static final void drawMonsterHealth(Graphics var0) {
      Entity var3 = Player.facingEntity;
      if (var3 != null && var3.monster != null) {
         if (var3.def.eType != 3 && (var3.def.eType != 2 || (var3.info & 131072) != 0)) {
            int var2 = var3.monster.ce.getStat(1);
            int var1 = var3.monster.ce.getStat(0);
            if (var3 != lastTarget) {
               if (lastTarget != null) {
                  Canvas.invalidateRect();
               }

               lastTarget = var3;
               monsterStartHealth = var1;
               monsterDestHealth = var1;
               monsterHealthChangeTime = 0;
            } else if (var1 != monsterDestHealth) {
               monsterStartHealth = monsterDestHealth;
               monsterDestHealth = var1;
               monsterHealthChangeTime = App.time;
            }

            if (monsterStartHealth > var2) {
               monsterStartHealth = var2;
            }

            if (App.time - monsterHealthChangeTime > 250) {
               monsterStartHealth = var1;
            } else {
               var1 = monsterStartHealth - (monsterStartHealth - monsterDestHealth) * (App.time - monsterHealthChangeTime) / 250;
            }

            int var4 = 25;
            if (var3.def.eSubType == 13) {
               var4 = 3072 * ((var2 << 16) / '저') >> 8;
               var4 = var4 + 256 - 1 >> 8;
            } else if (var2 <= 200 && var3.def.eSubType != 11) {
               var4 = 12800 * ((var2 << 16) / '저') >> 8;
               var4 = var4 + 256 - 1 >> 8;
            }

            int var5 = (var4 << 8) * ((var1 << 16) / (var2 << 8)) >> 8;
            var5 = var5 + 256 - 1 >> 8;
            if (var5 == 0 && var1 > 0) {
               var5 = 1;
            }

            int var6 = 2 * (Canvas.screenRect[2] << 8) / 128 >> 8;
            if ((var6 & 1) != 0) {
               ++var6;
            }

            int var7 = 2 + var6 * var4;
            int var8 = Canvas.SCR_CX - (var7 >> 1);
            byte var9 = 6;
            if (var3.def.eSubType == 14) {
               var9 = 0;
            }

            var0.setColor(-16777216);
            var0.fillRect(var8, Canvas.viewRect[1] + var9, var7, var6 * 2 + 1);
            var0.setColor(-5592406);
            var0.drawRect(var8, Canvas.viewRect[1] + var9, var7, var6 * 2 + 1);
            if (var5 <= var4 / 3) {
               var0.setColor(-65536);
            } else if (var4 - var5 <= var4 / 3) {
               var0.setColor(-16711936);
            } else {
               var0.setColor(-30720);
            }

            var8 += 2;

            for(int var10 = 0; var10 < var5; ++var10) {
               var0.fillRect(var8, Canvas.viewRect[1] + var9 + 2, var6 - 1, var6 * 2 - 2);
               var8 += var6;
            }

         }
      } else {
         if (lastTarget != null) {
            Canvas.invalidateRect();
         }

         lastTarget = null;
      }
   }

   public static final void showSpeechBubble(int var0) {
      if (bubbleText == null) {
         bubbleText = Text.getSmallBuffer();
      }

      Text.composeText(Canvas.loadMapStringID, (short)var0, bubbleText);
      bubbleText.dehyphenate();
      bubbleTextTime = App.time + 1500;
   }

   public static final void drawBubbleText(Graphics var0) {
      if (bubbleText != null) {
         if (App.time >= bubbleTextTime) {
            bubbleTextTime = 0;
            bubbleText.dispose();
            bubbleText = null;
         } else {
            int var1 = Canvas.viewRect[1] + 1;
            int var2 = Canvas.SCR_CX + 5;
            byte var3 = 6;
            int var4;
            if (Canvas.state == 18) {
               var1 = Canvas.cinRect[1] + 1;
            } else if (Player.facingEntity != null) {
               var4 = Player.facingEntity.distFrom(Canvas.destX, Canvas.destY);
               if (var4 <= Combat.tileDistances[0]) {
                  var1 += 10;
               } else {
                  var1 += 25;
               }
            }

            var4 = bubbleText.length() * 7 + 6;
            byte var5 = 16;
            int var6 = Math.max(0, var4 + 2 - (Canvas.viewRect[2] - var2));
            var2 -= var6;
            if (var2 + 15 < Canvas.SCR_CX) {
               var3 = 12;
            }

            var0.setColor(-8388608);
            var0.fillRect(var2, var1, var4, var5);
            var0.setColor(-1);
            var0.drawLine(var2, var1, var2 + var4, var1);
            var0.drawLine(var2, var1, var2, var1 + var5);
            var0.drawLine(var2 + var4, var1, var2 + var4, var1 + var5);
            var0.drawLine(var2, var1 + var5, var2 + var4, var1 + var5);
            var0.drawString(bubbleText, var2 + 2, var1 + 3, 4);
            var0.drawRegion(Canvas.imgUIImages, 0, var3, 10, 6, var2 + 5, var1 + var5);
         }
      }
   }
}
