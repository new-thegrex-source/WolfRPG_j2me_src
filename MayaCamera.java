/* Decompiler 121ms, total 354ms, lines 386 */
final class MayaCamera {
   public static int touchMe = 1;
   public int keyOffset;
   public int numKeys;
   private int curTweenTime = 0;
   public int curTween = 0;
   public ScriptThread cameraThread;
   public ScriptThread keyThread;
   public int keyThreadResumeCount = 0;
   public boolean complete;
   public int x;
   public int y;
   public int z;
   public int pitch;
   public int yaw;
   public int roll;
   public short[] aggComponents = new short[6];
   public int sampleRate;
   static final int CAM_TINYGL_SHIFT = 4;
   public boolean isTableCam = false;
   private boolean inheritYaw = false;
   private boolean inheritPitch = false;
   private boolean inheritX = false;
   private boolean inheritY = false;
   private boolean inheritZ = false;
   static int posShift = 0;
   static int angleShift = 0;
   static final int CAM_DEFAULT_FOVX = 250;

   public void NextKey() {
      Game.activeCameraTime = App.gameTime;
      ++Game.activeCameraKey;
      this.curTween = -1;
      this.curTweenTime = 0;
      this.resetTweenBase(this.keyOffset + Game.activeCameraKey);
   }

   public void Update(int var1, int var2) {
      short[] var3 = Game.mayaCameraKeys;
      byte[] var4 = Game.mayaCameraTweens;
      if (!this.complete) {
         this.complete = false;
         int var5 = this.keyOffset + var1;
         if (Game.cinematicWeapon != -1 && App.time > Combat.animEndTime && Combat.numActiveMissiles == 0) {
            if (Combat.animLoopCount <= 0) {
               Game.cinematicWeapon = -1;
            } else {
               --Combat.animLoopCount;
               Combat.animStartTime = App.gameTime;
               Combat.animEndTime = Combat.animStartTime + Combat.animTime;
               Combat.flashDone = false;
               Combat.flashDoneTime = Combat.animStartTime + Combat.flashTime;
               Combat.nextStageTime = Combat.animEndTime;
            }
         }

         if (var2 >= (var3[Game.OFS_MAYAKEY_MS + var5] & '\uffff')) {
            if (this.keyThreadResumeCount > 0) {
               this.Snap(var1);
            }

         } else {
            int var7;
            if (!this.hasTweens(var5)) {
               this.x = var3[Game.OFS_MAYAKEY_X + var5];
               this.y = var3[Game.OFS_MAYAKEY_Y + var5];
               this.z = var3[Game.OFS_MAYAKEY_Z + var5];
               this.pitch = var3[Game.OFS_MAYAKEY_PITCH + var5];
               this.yaw = var3[Game.OFS_MAYAKEY_YAW + var5];
               this.roll = var3[Game.OFS_MAYAKEY_ROLL + var5];
               if (this.x == -2) {
                  this.x = Game.camPlayerX;
               }

               if (this.y == -2) {
                  this.y = Game.camPlayerY;
               }

               if (this.z == -2) {
                  this.z = Game.camPlayerZ;
               }

               this.x <<= 4;
               this.y <<= 4;
               this.z <<= 4;
               if (this.yaw == -2) {
                  this.yaw = Game.camPlayerYaw;
               }

               if (this.pitch == -2) {
                  this.pitch = Game.camPlayerPitch;
               }
            } else {
               short[] var6 = null;
               var7 = this.sampleRate;
               if (this.curTween == -1) {
                  if (var2 >= this.curTweenTime + this.sampleRate) {
                     this.curTween = 0;
                     this.curTweenTime += this.sampleRate;
                     this.updateTweenBase(var5, 0);
                  } else {
                     var6 = this.getTweenData(var4, var5, 0);
                  }
               }

               if (var6 == null) {
                  int var8 = this.estNumTweens(var5) - 1;

                  while(this.curTween != var8 && var2 >= this.curTweenTime + this.sampleRate) {
                     this.curTweenTime += this.sampleRate;
                     ++this.curTween;
                     this.updateTweenBase(var5, this.curTween);
                  }

                  if (this.curTween == var8) {
                     var6 = this.getKeyOfs(var3, var5 + 1);
                     var7 = (var3[Game.OFS_MAYAKEY_MS + var5] & '\uffff') - this.curTweenTime;
                  } else {
                     var6 = this.getTweenData(var4, var5, this.curTween + 1);
                  }
               }

               this.Interpolate(var6, (var2 - this.curTweenTime << 16) / var7);
            }

            int var9 = (var2 << 16) / (var3[Game.OFS_MAYAKEY_MS + var5] & '\uffff');
            if (this.inheritX && var3[Game.OFS_MAYAKEY_X + var5 + 1] != -2) {
               this.x = (this.aggComponents[0] << 20) + var9 * (var3[Game.OFS_MAYAKEY_X + var5 + 1] - this.aggComponents[0] << 4) + '耀' >> 16;
            }

            if (this.inheritY && var3[Game.OFS_MAYAKEY_Y + var5 + 1] != -2) {
               this.y = (this.aggComponents[1] << 20) + var9 * (var3[Game.OFS_MAYAKEY_Y + var5 + 1] - this.aggComponents[1] << 4) + '耀' >> 16;
            }

            if (this.inheritZ && var3[Game.OFS_MAYAKEY_Z + var5 + 1] != -2) {
               this.z = (this.aggComponents[2] << 20) + var9 * (var3[Game.OFS_MAYAKEY_Z + var5 + 1] - this.aggComponents[2] << 4) + '耀' >> 16;
            }

            if (this.inheritYaw && var3[Game.OFS_MAYAKEY_YAW + var5 + 1] != -2) {
               var7 = this.getAngleDifference(this.aggComponents[4], var3[Game.OFS_MAYAKEY_YAW + var5 + 1]);
               this.yaw = (this.aggComponents[4] << 16) + var9 * var7 + '耀' >> 16;
            }

            if (this.inheritPitch && var3[Game.OFS_MAYAKEY_PITCH + var5 + 1] != -2) {
               var7 = this.getAngleDifference(this.aggComponents[3], var3[Game.OFS_MAYAKEY_PITCH + var5 + 1]);
               this.pitch = (this.aggComponents[3] << 16) + var9 * var7 + '耀' >> 16;
            }

         }
      }
   }

   private int getAngleDifference(int var1, int var2) {
      if (var2 - var1 > 512) {
         var2 -= 1024;
      } else if (var2 - var1 < -512) {
         var2 += 1024;
      }

      return var2 - var1;
   }

   public boolean hasTweens(int var1) {
      for(int var3 = 0; var3 < 6; ++var3) {
         short var2 = Game.mayaTweenIndices[var1 * 6 + var3];
         if (var2 != -1 && var2 != -2) {
            return true;
         }
      }

      return false;
   }

   public int estNumTweens(int var1) {
      return var1 + 1 == Game.totalMayaCameraKeys ? 0 : ((Game.mayaCameraKeys[Game.OFS_MAYAKEY_MS + var1] & '\uffff') - 1) / this.sampleRate;
   }

   private short[] getTweenData(byte[] var1, int var2, int var3) {
      short[] var4 = new short[6];
      short[] var5 = Game.ofsMayaTween;
      boolean var6 = false;

      for(int var7 = 0; var7 < 6; ++var7) {
         short var8 = Game.mayaTweenIndices[var2 * 6 + var7];
         if (var8 != -1 && var8 != -2) {
            var4[var7] = (short)var1[var5[var7] + var8 + var3];
         } else {
            var4[var7] = 0;
         }
      }

      return var4;
   }

   private short[] getKeyOfs(short[] var1, int var2) {
      short[] var3 = new short[6];
      if (this.inheritX) {
         var3[0] = 0;
      } else {
         var3[0] = (short)(var1[Game.OFS_MAYAKEY_X + var2] - this.aggComponents[0]);
      }

      if (this.inheritY) {
         var3[1] = 0;
      } else {
         var3[1] = (short)(var1[Game.OFS_MAYAKEY_Y + var2] - this.aggComponents[1]);
      }

      if (this.inheritZ) {
         var3[2] = 0;
      } else {
         var3[2] = (short)(var1[Game.OFS_MAYAKEY_Z + var2] - this.aggComponents[2]);
      }

      if (this.inheritPitch) {
         var3[3] = 0;
      } else {
         var3[3] = (short)this.getAngleDifference(this.aggComponents[3] & 1023, var1[Game.OFS_MAYAKEY_PITCH + var2]);
      }

      if (this.inheritYaw) {
         var3[4] = 0;
      } else {
         var3[4] = (short)this.getAngleDifference(this.aggComponents[4] & 1023, var1[Game.OFS_MAYAKEY_YAW + var2]);
      }

      var3[5] = (short)this.getAngleDifference(this.aggComponents[5] & 1023, var1[Game.OFS_MAYAKEY_ROLL + var2]);
      return var3;
   }

   private void Interpolate(short[] var1, int var2) {
      this.pitch = (this.aggComponents[3] << 16) + var2 * var1[3] + '耀' >> 16;
      this.yaw = (this.aggComponents[4] << 16) + var2 * var1[4] + '耀' >> 16;
      this.roll = (this.aggComponents[5] << 16) + var2 * var1[5] + '耀' >> 16;
      this.x = (this.aggComponents[0] << 20) + var2 * (var1[0] << 4) + '耀' >> 16;
      this.y = (this.aggComponents[1] << 20) + var2 * (var1[1] << 4) + '耀' >> 16;
      this.z = (this.aggComponents[2] << 20) + var2 * (var1[2] << 4) + '耀' >> 16;
   }

   private void resetTweenBase(int var1) {
      short[] var2 = Game.mayaCameraKeys;
      short[] var3 = this.aggComponents;
      var3[0] = var2[Game.OFS_MAYAKEY_X + var1];
      var3[1] = var2[Game.OFS_MAYAKEY_Y + var1];
      var3[2] = var2[Game.OFS_MAYAKEY_Z + var1];
      var3[3] = var2[Game.OFS_MAYAKEY_PITCH + var1];
      var3[4] = var2[Game.OFS_MAYAKEY_YAW + var1];
      var3[5] = var2[Game.OFS_MAYAKEY_ROLL + var1];
      if (var3[0] == -2) {
         var3[0] = (short)Game.camPlayerX;
         this.inheritX = true;
      } else {
         this.inheritX = false;
      }

      if (var3[1] == -2) {
         var3[1] = (short)Game.camPlayerY;
         this.inheritY = true;
      } else {
         this.inheritY = false;
      }

      if (var3[2] == -2) {
         var3[2] = (short)Game.camPlayerZ;
         this.inheritZ = true;
      } else {
         this.inheritZ = false;
      }

      if (var3[3] == -2) {
         var3[3] = (short)Game.camPlayerPitch;
         this.inheritPitch = true;
      } else {
         this.inheritPitch = false;
      }

      if (var3[4] == -2) {
         var3[4] = (short)Game.camPlayerYaw;
         this.inheritYaw = true;
      } else {
         this.inheritYaw = false;
      }

   }

   private void updateTweenBase(int var1, int var2) {
      byte[] var3 = Game.mayaCameraTweens;
      boolean var4 = false;

      for(int var5 = 0; var5 < 6; ++var5) {
         short var8 = Game.mayaTweenIndices[var1 * 6 + var5];
         if (var8 != -1 && var8 != -2) {
            int var6 = Game.ofsMayaTween[var5] + var8 + var2;
            byte var7 = var3[var6];
            short[] var10000 = this.aggComponents;
            var10000[var5] = (short)(var10000[var5] + var7);
         }
      }

   }

   public void Render() {
      if (Canvas.state == 8) {
         Render.render(this.x, this.y, this.z, this.yaw, this.pitch, this.roll, 250);
      } else {
         Render.render(this.x, this.y, this.z, this.yaw, this.pitch, this.roll, 250);
      }

      if (Game.cinematicWeapon != -1) {
         Combat.drawWeapon(0, 0);
      }

      if (Render.isFading()) {
         Render.fadeScene();
      }

      Canvas.repaintFlags |= 64;
   }

   public void Snap(int var1) {
      short[] var2 = Game.mayaCameraKeys;
      if (!this.complete) {
         int var3 = this.keyOffset + var1;
         if (var3 + 1 < this.keyOffset + this.numKeys) {
            int var6 = var3 + 1;
            this.x = var2[Game.OFS_MAYAKEY_X + var6] << 4;
            this.y = var2[Game.OFS_MAYAKEY_Y + var6] << 4;
            this.z = var2[Game.OFS_MAYAKEY_Z + var6] << 4;
            this.pitch = var2[Game.OFS_MAYAKEY_PITCH + var6];
            this.yaw = var2[Game.OFS_MAYAKEY_YAW + var6];
            this.roll = var2[Game.OFS_MAYAKEY_ROLL + var6];
            if (this.inheritX && var2[Game.OFS_MAYAKEY_X + var6] == -2) {
               this.x = Game.camPlayerX << 4;
            }

            if (this.inheritY && var2[Game.OFS_MAYAKEY_Y + var6] == -2) {
               this.y = Game.camPlayerY << 4;
            }

            if (this.inheritZ && var2[Game.OFS_MAYAKEY_Z + var6] == -2) {
               this.z = Game.camPlayerZ << 4;
            }

            if (this.inheritPitch && var2[Game.OFS_MAYAKEY_PITCH + var6] == -2) {
               this.pitch = (short)Game.camPlayerPitch;
            }

            if (this.inheritYaw && var2[Game.OFS_MAYAKEY_YAW + var6] == -2) {
               this.yaw = (short)Game.camPlayerYaw;
            }

            if (null != this.keyThread && --this.keyThreadResumeCount == 0) {
               ScriptThread var5 = this.keyThread;
               this.keyThread = null;
               var5.run();
            } else if (null != this.keyThread && this.keyThreadResumeCount > 0) {
               this.NextKey();
            } else if (this.isTableCam) {
               this.NextKey();
            }

         } else {
            this.complete = true;
            Game.skippingCinematic = false;
            Game.cinUnpauseTime = 0;
            Game.activeCameraView = false;
            if (Canvas.state == 18) {
               TinyGL.resetViewPort();
               Canvas.setState(3);
               Canvas.updateFacingEntity = true;
               if (null != this.keyThread) {
                  ScriptThread var4 = this.keyThread;
                  this.keyThread = null;
                  var4.run();
               }

               this.cameraThread = null;
               Canvas.destPitch = 0;
               Canvas.viewPitch = 0;
               Canvas.startRotation(true);
            }
         }
      }
   }
}
