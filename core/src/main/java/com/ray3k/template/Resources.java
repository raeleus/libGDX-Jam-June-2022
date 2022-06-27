package com.ray3k.template;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.BoneData;
import com.esotericsoftware.spine.EventData;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SlotData;
import java.lang.String;

public class Resources {
    public static TextureAtlas textures_textures;

    public static Skin skin_skin;

    public static Sound sfx_click;

    public static Sound sfx_gameBlessing;

    public static Sound sfx_gameBow;

    public static Sound sfx_gameBurn;

    public static Sound sfx_gameDash;

    public static Sound sfx_gameExplosionBig;

    public static Sound sfx_gameExplosion;

    public static Sound sfx_gamePlayerDie;

    public static Sound sfx_gamePlayerHurt;

    public static Sound sfx_gameSatanHurt;

    public static Sound sfx_gameSlash;

    public static Sound sfx_gameTeleport;

    public static Sound sfx_gameWalk;

    public static Sound sfx_logoCrash;

    public static Sound sfx_logoLand;

    public static Sound sfx_logoLine;

    public static Sound sfx_logoMove;

    public static Music bgm_01a;

    public static Music bgm_01b;

    public static Music bgm_01c;

    public static Music bgm_02a;

    public static Music bgm_02b;

    public static Music bgm_02c;

    public static Music bgm_03a;

    public static Music bgm_03b;

    public static Music bgm_03c;

    public static Music bgm_04a;

    public static Music bgm_04b;

    public static Music bgm_05a;

    public static Music bgm_06a;

    public static Music bgm_06b;

    public static Music bgm_07a;

    public static Music bgm_07b;

    public static Music bgm_07c;

    public static Music bgm_08a;

    public static Music bgm_08b;

    public static Music bgm_09a;

    public static Music bgm_09b;

    public static Music bgm_10a;

    public static Music bgm_10b;

    public static Music bgm_audioSample;

    public static Music bgm_ending01a;

    public static Music bgm_ending01b;

    public static Music bgm_ending01c;

    public static Music bgm_ending01d;

    public static Music bgm_ending01e;

    public static Music bgm_ending01f;

    public static Music bgm_ending01g;

    public static Music bgm_ending01h;

    public static Music bgm_ending02;

    public static Music bgm_ending03a;

    public static Music bgm_ending03b;

    public static Music bgm_final01a;

    public static Music bgm_final01b;

    public static Music bgm_final01c;

    public static Music bgm_final02a;

    public static Music bgm_final02b;

    public static Music bgm_final03a;

    public static Music bgm_final03b;

    public static Music bgm_final04a;

    public static Music bgm_final04b;

    public static Music bgm_final05a;

    public static Music bgm_final05b;

    public static Music bgm_final06a;

    public static Music bgm_final06b;

    public static Music bgm_final07a;

    public static Music bgm_final07b;

    public static Music bgm_game;

    public static Music bgm_menu;

    public static Music bgm_second01;

    public static Music bgm_second02;

    public static Music bgm_second03;

    public static Music bgm_second04;

    public static Music bgm_second05;

    public static void loadResources(AssetManager assetManager) {
        textures_textures = assetManager.get("textures/textures.atlas");
        SpineArrow.skeletonData = assetManager.get("spine/arrow.json");
        SpineArrow.animationData = assetManager.get("spine/arrow.json-animation");
        SpineArrow.animationAnimation = SpineArrow.skeletonData.findAnimation("animation");
        SpineArrow.boneRoot = SpineArrow.skeletonData.findBone("root");
        SpineArrow.slotGameArrow = SpineArrow.skeletonData.findSlot("game/arrow");
        SpineArrow.skinDefault = SpineArrow.skeletonData.findSkin("default");
        SpineBlood.skeletonData = assetManager.get("spine/blood.json");
        SpineBlood.animationData = assetManager.get("spine/blood.json-animation");
        SpineBlood.animationAnimation = SpineBlood.skeletonData.findAnimation("animation");
        SpineBlood.boneRoot = SpineBlood.skeletonData.findBone("root");
        SpineBlood.slotGameBlood01 = SpineBlood.skeletonData.findSlot("game/blood-01");
        SpineBlood.skinDefault = SpineBlood.skeletonData.findSkin("default");
        SpineExplosion.skeletonData = assetManager.get("spine/explosion.json");
        SpineExplosion.animationData = assetManager.get("spine/explosion.json-animation");
        SpineExplosion.animationAnimation = SpineExplosion.skeletonData.findAnimation("animation");
        SpineExplosion.boneRoot = SpineExplosion.skeletonData.findBone("root");
        SpineExplosion.slotGameExplosion01 = SpineExplosion.skeletonData.findSlot("game/explosion-01");
        SpineExplosion.skinDefault = SpineExplosion.skeletonData.findSkin("default");
        SpineFlame.skeletonData = assetManager.get("spine/flame.json");
        SpineFlame.animationData = assetManager.get("spine/flame.json-animation");
        SpineFlame.animationAnimation = SpineFlame.skeletonData.findAnimation("animation");
        SpineFlame.animationFlames = SpineFlame.skeletonData.findAnimation("flames");
        SpineFlame.boneRoot = SpineFlame.skeletonData.findBone("root");
        SpineFlame.boneGameFlame01 = SpineFlame.skeletonData.findBone("game/flame-01");
        SpineFlame.boneGameFlame02 = SpineFlame.skeletonData.findBone("game/flame-02");
        SpineFlame.boneGameFlame03 = SpineFlame.skeletonData.findBone("game/flame-03");
        SpineFlame.slotGameFlame01 = SpineFlame.skeletonData.findSlot("game/flame-01");
        SpineFlame.slotGameFlame02 = SpineFlame.skeletonData.findSlot("game/flame-02");
        SpineFlame.slotGameFlame03 = SpineFlame.skeletonData.findSlot("game/flame-03");
        SpineFlame.skinDefault = SpineFlame.skeletonData.findSkin("default");
        SpineGlutton.skeletonData = assetManager.get("spine/glutton.json");
        SpineGlutton.animationData = assetManager.get("spine/glutton.json-animation");
        SpineGlutton.animationAnimation = SpineGlutton.skeletonData.findAnimation("animation");
        SpineGlutton.boneRoot = SpineGlutton.skeletonData.findBone("root");
        SpineGlutton.boneGameGluttonBody = SpineGlutton.skeletonData.findBone("game/glutton-body");
        SpineGlutton.boneGameGluttonHand = SpineGlutton.skeletonData.findBone("game/glutton-hand");
        SpineGlutton.boneGameGluttonHand2 = SpineGlutton.skeletonData.findBone("game/glutton-hand2");
        SpineGlutton.boneGameGluttonHead = SpineGlutton.skeletonData.findBone("game/glutton-head");
        SpineGlutton.slotGameGluttonBody = SpineGlutton.skeletonData.findSlot("game/glutton-body");
        SpineGlutton.slotGameGluttonHand = SpineGlutton.skeletonData.findSlot("game/glutton-hand");
        SpineGlutton.slotGameGluttonHand2 = SpineGlutton.skeletonData.findSlot("game/glutton-hand2");
        SpineGlutton.slotGameGluttonHead = SpineGlutton.skeletonData.findSlot("game/glutton-head");
        SpineGlutton.skinDefault = SpineGlutton.skeletonData.findSkin("default");
        SpineGoat.skeletonData = assetManager.get("spine/goat.json");
        SpineGoat.animationData = assetManager.get("spine/goat.json-animation");
        SpineGoat.animationAnimation = SpineGoat.skeletonData.findAnimation("animation");
        SpineGoat.boneRoot = SpineGoat.skeletonData.findBone("root");
        SpineGoat.boneGameGoatBody = SpineGoat.skeletonData.findBone("game/goat-body");
        SpineGoat.boneGameGoatHand = SpineGoat.skeletonData.findBone("game/goat-hand");
        SpineGoat.boneGameGoatHand2 = SpineGoat.skeletonData.findBone("game/goat-hand2");
        SpineGoat.boneGameGoatHead = SpineGoat.skeletonData.findBone("game/goat-head");
        SpineGoat.slotGameGoatBody = SpineGoat.skeletonData.findSlot("game/goat-body");
        SpineGoat.slotGameGoatStaff = SpineGoat.skeletonData.findSlot("game/goat-staff");
        SpineGoat.slotGameGoatHand = SpineGoat.skeletonData.findSlot("game/goat-hand");
        SpineGoat.slotGameGoatHand2 = SpineGoat.skeletonData.findSlot("game/goat-hand2");
        SpineGoat.slotGameGoatHead = SpineGoat.skeletonData.findSlot("game/goat-head");
        SpineGoat.skinDefault = SpineGoat.skeletonData.findSkin("default");
        SpineGrenade.skeletonData = assetManager.get("spine/grenade.json");
        SpineGrenade.animationData = assetManager.get("spine/grenade.json-animation");
        SpineGrenade.animationAnimation = SpineGrenade.skeletonData.findAnimation("animation");
        SpineGrenade.boneRoot = SpineGrenade.skeletonData.findBone("root");
        SpineGrenade.slotGameGrenade = SpineGrenade.skeletonData.findSlot("game/grenade");
        SpineGrenade.skinDefault = SpineGrenade.skeletonData.findSkin("default");
        SpineHeaven.skeletonData = assetManager.get("spine/heaven.json");
        SpineHeaven.animationData = assetManager.get("spine/heaven.json-animation");
        SpineHeaven.animationAnimation = SpineHeaven.skeletonData.findAnimation("animation");
        SpineHeaven.boneRoot = SpineHeaven.skeletonData.findBone("root");
        SpineHeaven.boneLogoWhite = SpineHeaven.skeletonData.findBone("logo/white");
        SpineHeaven.boneGameHeavenStairs = SpineHeaven.skeletonData.findBone("game/heaven-stairs");
        SpineHeaven.boneGameHeavenCity = SpineHeaven.skeletonData.findBone("game/heaven-city");
        SpineHeaven.slotClip = SpineHeaven.skeletonData.findSlot("clip");
        SpineHeaven.slotLogoWhite = SpineHeaven.skeletonData.findSlot("logo/white");
        SpineHeaven.slotGameHeavenCity = SpineHeaven.skeletonData.findSlot("game/heaven-city");
        SpineHeaven.slotGameHeavenClouds = SpineHeaven.skeletonData.findSlot("game/heaven-clouds");
        SpineHeaven.slotGameHeavenStairs = SpineHeaven.skeletonData.findSlot("game/heaven-stairs");
        SpineHeaven.skinDefault = SpineHeaven.skeletonData.findSkin("default");
        SpineImp.skeletonData = assetManager.get("spine/imp.json");
        SpineImp.animationData = assetManager.get("spine/imp.json-animation");
        SpineImp.animationAnimation = SpineImp.skeletonData.findAnimation("animation");
        SpineImp.boneRoot = SpineImp.skeletonData.findBone("root");
        SpineImp.boneGameImpBody = SpineImp.skeletonData.findBone("game/imp-body");
        SpineImp.boneGameImpWing = SpineImp.skeletonData.findBone("game/imp-wing");
        SpineImp.boneGameImpWing2 = SpineImp.skeletonData.findBone("game/imp-wing2");
        SpineImp.boneGameImpHead = SpineImp.skeletonData.findBone("game/imp-head");
        SpineImp.slotGameImpWing = SpineImp.skeletonData.findSlot("game/imp-wing");
        SpineImp.slotGameImpWing2 = SpineImp.skeletonData.findSlot("game/imp-wing2");
        SpineImp.slotGameImpBody = SpineImp.skeletonData.findSlot("game/imp-body");
        SpineImp.slotGameImpHead = SpineImp.skeletonData.findSlot("game/imp-head");
        SpineImp.skinDefault = SpineImp.skeletonData.findSkin("default");
        SpineLavaBubble.skeletonData = assetManager.get("spine/lava-bubble.json");
        SpineLavaBubble.animationData = assetManager.get("spine/lava-bubble.json-animation");
        SpineLavaBubble.animationAnimation = SpineLavaBubble.skeletonData.findAnimation("animation");
        SpineLavaBubble.boneRoot = SpineLavaBubble.skeletonData.findBone("root");
        SpineLavaBubble.slotGameLavaBubble01 = SpineLavaBubble.skeletonData.findSlot("game/lava-bubble-01");
        SpineLavaBubble.skinDefault = SpineLavaBubble.skeletonData.findSkin("default");
        SpineLogoLibgdx.skeletonData = assetManager.get("spine/logo-libgdx.json");
        SpineLogoLibgdx.animationData = assetManager.get("spine/logo-libgdx.json-animation");
        SpineLogoLibgdx.animationAnimation = SpineLogoLibgdx.skeletonData.findAnimation("animation");
        SpineLogoLibgdx.animationStand = SpineLogoLibgdx.skeletonData.findAnimation("stand");
        SpineLogoLibgdx.eventLogoLand = SpineLogoLibgdx.skeletonData.findEvent("logo/land");
        SpineLogoLibgdx.eventLogoLine = SpineLogoLibgdx.skeletonData.findEvent("logo/line");
        SpineLogoLibgdx.eventLogoMove = SpineLogoLibgdx.skeletonData.findEvent("logo/move");
        SpineLogoLibgdx.boneRoot = SpineLogoLibgdx.skeletonData.findBone("root");
        SpineLogoLibgdx.boneTemplate = SpineLogoLibgdx.skeletonData.findBone("template");
        SpineLogoLibgdx.boneL = SpineLogoLibgdx.skeletonData.findBone("l");
        SpineLogoLibgdx.boneI = SpineLogoLibgdx.skeletonData.findBone("i");
        SpineLogoLibgdx.boneB = SpineLogoLibgdx.skeletonData.findBone("b");
        SpineLogoLibgdx.boneG = SpineLogoLibgdx.skeletonData.findBone("g");
        SpineLogoLibgdx.boneD = SpineLogoLibgdx.skeletonData.findBone("d");
        SpineLogoLibgdx.boneX = SpineLogoLibgdx.skeletonData.findBone("x");
        SpineLogoLibgdx.boneNext = SpineLogoLibgdx.skeletonData.findBone("next");
        SpineLogoLibgdx.slotBackground = SpineLogoLibgdx.skeletonData.findSlot("background");
        SpineLogoLibgdx.slotB = SpineLogoLibgdx.skeletonData.findSlot("b");
        SpineLogoLibgdx.slotD = SpineLogoLibgdx.skeletonData.findSlot("d");
        SpineLogoLibgdx.slotG = SpineLogoLibgdx.skeletonData.findSlot("g");
        SpineLogoLibgdx.slotI = SpineLogoLibgdx.skeletonData.findSlot("i");
        SpineLogoLibgdx.slotX = SpineLogoLibgdx.skeletonData.findSlot("x");
        SpineLogoLibgdx.slotL = SpineLogoLibgdx.skeletonData.findSlot("l");
        SpineLogoLibgdx.slotLogoLibGDXNext = SpineLogoLibgdx.skeletonData.findSlot("logo/libGDX-next");
        SpineLogoLibgdx.slotNext = SpineLogoLibgdx.skeletonData.findSlot("next");
        SpineLogoLibgdx.skinDefault = SpineLogoLibgdx.skeletonData.findSkin("default");
        SpineLogoRay3k.skeletonData = assetManager.get("spine/logo-ray3k.json");
        SpineLogoRay3k.animationData = assetManager.get("spine/logo-ray3k.json-animation");
        SpineLogoRay3k.animationAnimation = SpineLogoRay3k.skeletonData.findAnimation("animation");
        SpineLogoRay3k.animationStand = SpineLogoRay3k.skeletonData.findAnimation("stand");
        SpineLogoRay3k.eventLogoCrash = SpineLogoRay3k.skeletonData.findEvent("logo/crash");
        SpineLogoRay3k.boneRoot = SpineLogoRay3k.skeletonData.findBone("root");
        SpineLogoRay3k.boneSpiral = SpineLogoRay3k.skeletonData.findBone("spiral");
        SpineLogoRay3k.boneLogo = SpineLogoRay3k.skeletonData.findBone("logo");
        SpineLogoRay3k.slotBackground = SpineLogoRay3k.skeletonData.findSlot("background");
        SpineLogoRay3k.slotSpiral = SpineLogoRay3k.skeletonData.findSlot("spiral");
        SpineLogoRay3k.slotCompany = SpineLogoRay3k.skeletonData.findSlot("company");
        SpineLogoRay3k.slotLogo = SpineLogoRay3k.skeletonData.findSlot("logo");
        SpineLogoRay3k.skinDefault = SpineLogoRay3k.skeletonData.findSkin("default");
        SpineOrb.skeletonData = assetManager.get("spine/orb.json");
        SpineOrb.animationData = assetManager.get("spine/orb.json-animation");
        SpineOrb.animationAnimation = SpineOrb.skeletonData.findAnimation("animation");
        SpineOrb.boneRoot = SpineOrb.skeletonData.findBone("root");
        SpineOrb.slotGameOrb = SpineOrb.skeletonData.findSlot("game/orb");
        SpineOrb.skinDefault = SpineOrb.skeletonData.findSkin("default");
        SpinePentagram.skeletonData = assetManager.get("spine/pentagram.json");
        SpinePentagram.animationData = assetManager.get("spine/pentagram.json-animation");
        SpinePentagram.animationAnimation = SpinePentagram.skeletonData.findAnimation("animation");
        SpinePentagram.boneRoot = SpinePentagram.skeletonData.findBone("root");
        SpinePentagram.slotGamePentagram = SpinePentagram.skeletonData.findSlot("game/pentagram");
        SpinePentagram.skinDefault = SpinePentagram.skeletonData.findSkin("default");
        SpinePlayer.skeletonData = assetManager.get("spine/player.json");
        SpinePlayer.animationData = assetManager.get("spine/player.json-animation");
        SpinePlayer.animationAnimation = SpinePlayer.skeletonData.findAnimation("animation");
        SpinePlayer.animationDeshield = SpinePlayer.skeletonData.findAnimation("deshield");
        SpinePlayer.animationDie = SpinePlayer.skeletonData.findAnimation("die");
        SpinePlayer.animationShield = SpinePlayer.skeletonData.findAnimation("shield");
        SpinePlayer.animationSlash = SpinePlayer.skeletonData.findAnimation("slash");
        SpinePlayer.animationSmack = SpinePlayer.skeletonData.findAnimation("smack");
        SpinePlayer.animationSpawn = SpinePlayer.skeletonData.findAnimation("spawn");
        SpinePlayer.animationTeleport = SpinePlayer.skeletonData.findAnimation("teleport");
        SpinePlayer.animationTrident = SpinePlayer.skeletonData.findAnimation("trident");
        SpinePlayer.boneRoot = SpinePlayer.skeletonData.findBone("root");
        SpinePlayer.boneGamePlayerBody = SpinePlayer.skeletonData.findBone("game/player-body");
        SpinePlayer.boneGamePlayerHand = SpinePlayer.skeletonData.findBone("game/player-hand");
        SpinePlayer.boneGamePlayerHand2 = SpinePlayer.skeletonData.findBone("game/player-hand2");
        SpinePlayer.boneGamePlayerHead = SpinePlayer.skeletonData.findBone("game/player-head");
        SpinePlayer.boneGameTeleport01 = SpinePlayer.skeletonData.findBone("game/teleport-01");
        SpinePlayer.boneGameBloodPool = SpinePlayer.skeletonData.findBone("game/blood-pool");
        SpinePlayer.slotGameBloodPool = SpinePlayer.skeletonData.findSlot("game/blood-pool");
        SpinePlayer.slotClip = SpinePlayer.skeletonData.findSlot("clip");
        SpinePlayer.slotGamePlayerBody = SpinePlayer.skeletonData.findSlot("game/player-body");
        SpinePlayer.slotGamePlayerSword = SpinePlayer.skeletonData.findSlot("game/player-sword");
        SpinePlayer.slotGamePlayerTrident = SpinePlayer.skeletonData.findSlot("game/player-trident");
        SpinePlayer.slotGamePlayerHand = SpinePlayer.skeletonData.findSlot("game/player-hand");
        SpinePlayer.slotGamePlayerHand2 = SpinePlayer.skeletonData.findSlot("game/player-hand2");
        SpinePlayer.slotGamePlayerHead = SpinePlayer.skeletonData.findSlot("game/player-head");
        SpinePlayer.slotGameTeleport01 = SpinePlayer.skeletonData.findSlot("game/teleport-01");
        SpinePlayer.slotGameShield = SpinePlayer.skeletonData.findSlot("game/shield");
        SpinePlayer.skinDefault = SpinePlayer.skeletonData.findSkin("default");
        SpineSatan.skeletonData = assetManager.get("spine/satan.json");
        SpineSatan.animationData = assetManager.get("spine/satan.json-animation");
        SpineSatan.animationAnimation = SpineSatan.skeletonData.findAnimation("animation");
        SpineSatan.animationDie = SpineSatan.skeletonData.findAnimation("die");
        SpineSatan.animationLand = SpineSatan.skeletonData.findAnimation("land");
        SpineSatan.animationLeap = SpineSatan.skeletonData.findAnimation("leap");
        SpineSatan.boneRoot = SpineSatan.skeletonData.findBone("root");
        SpineSatan.boneGameSatanBody = SpineSatan.skeletonData.findBone("game/satan-body");
        SpineSatan.boneGameSatanHand2 = SpineSatan.skeletonData.findBone("game/satan-hand2");
        SpineSatan.boneGameSatanHand = SpineSatan.skeletonData.findBone("game/satan-hand");
        SpineSatan.boneGameSatanHead = SpineSatan.skeletonData.findBone("game/satan-head");
        SpineSatan.boneGameSatanWing = SpineSatan.skeletonData.findBone("game/satan-wing");
        SpineSatan.boneGameSatanWing2 = SpineSatan.skeletonData.findBone("game/satan-wing2");
        SpineSatan.boneGameBloodPool = SpineSatan.skeletonData.findBone("game/blood-pool");
        SpineSatan.slotGameBloodPool = SpineSatan.skeletonData.findSlot("game/blood-pool");
        SpineSatan.slotClip = SpineSatan.skeletonData.findSlot("clip");
        SpineSatan.slotGameSatanWing = SpineSatan.skeletonData.findSlot("game/satan-wing");
        SpineSatan.slotGameSatanWing2 = SpineSatan.skeletonData.findSlot("game/satan-wing2");
        SpineSatan.slotGameSatanBody = SpineSatan.skeletonData.findSlot("game/satan-body");
        SpineSatan.slotGameSatanHand = SpineSatan.skeletonData.findSlot("game/satan-hand");
        SpineSatan.slotGameSatanHand2 = SpineSatan.skeletonData.findSlot("game/satan-hand2");
        SpineSatan.slotGameSatanHead = SpineSatan.skeletonData.findSlot("game/satan-head");
        SpineSatan.skinDefault = SpineSatan.skeletonData.findSkin("default");
        SpineShrine.skeletonData = assetManager.get("spine/shrine.json");
        SpineShrine.animationData = assetManager.get("spine/shrine.json-animation");
        SpineShrine.animationAnimation = SpineShrine.skeletonData.findAnimation("animation");
        SpineShrine.boneRoot = SpineShrine.skeletonData.findBone("root");
        SpineShrine.slotGameShrine = SpineShrine.skeletonData.findSlot("game/shrine");
        SpineShrine.skinDefault = SpineShrine.skeletonData.findSkin("default");
        SpineSkeleton.skeletonData = assetManager.get("spine/skeleton.json");
        SpineSkeleton.animationData = assetManager.get("spine/skeleton.json-animation");
        SpineSkeleton.animationAnimation = SpineSkeleton.skeletonData.findAnimation("animation");
        SpineSkeleton.boneRoot = SpineSkeleton.skeletonData.findBone("root");
        SpineSkeleton.boneGameSkeletonBody = SpineSkeleton.skeletonData.findBone("game/skeleton-body");
        SpineSkeleton.boneGameSkeletonHand = SpineSkeleton.skeletonData.findBone("game/skeleton-hand");
        SpineSkeleton.boneGameSkeletonHand2 = SpineSkeleton.skeletonData.findBone("game/skeleton-hand2");
        SpineSkeleton.boneGameSkeletonHead = SpineSkeleton.skeletonData.findBone("game/skeleton-head");
        SpineSkeleton.slotGameSkeletonBody = SpineSkeleton.skeletonData.findSlot("game/skeleton-body");
        SpineSkeleton.slotGameSkeletonBow = SpineSkeleton.skeletonData.findSlot("game/skeleton-bow");
        SpineSkeleton.slotGameSkeletonHand = SpineSkeleton.skeletonData.findSlot("game/skeleton-hand");
        SpineSkeleton.slotGameSkeletonHand2 = SpineSkeleton.skeletonData.findSlot("game/skeleton-hand2");
        SpineSkeleton.slotGameSkeletonHead = SpineSkeleton.skeletonData.findSlot("game/skeleton-head");
        SpineSkeleton.skinDefault = SpineSkeleton.skeletonData.findSkin("default");
        SpineSlam.skeletonData = assetManager.get("spine/slam.json");
        SpineSlam.animationData = assetManager.get("spine/slam.json-animation");
        SpineSlam.animationAnimation = SpineSlam.skeletonData.findAnimation("animation");
        SpineSlam.boneRoot = SpineSlam.skeletonData.findBone("root");
        SpineSlam.slotGameSlam01 = SpineSlam.skeletonData.findSlot("game/slam-01");
        SpineSlam.skinDefault = SpineSlam.skeletonData.findSkin("default");
        SpineSnake.skeletonData = assetManager.get("spine/snake.json");
        SpineSnake.animationData = assetManager.get("spine/snake.json-animation");
        SpineSnake.animationAnimation = SpineSnake.skeletonData.findAnimation("animation");
        SpineSnake.boneRoot = SpineSnake.skeletonData.findBone("root");
        SpineSnake.boneGameSnake = SpineSnake.skeletonData.findBone("game/snake");
        SpineSnake.boneGameSnake2 = SpineSnake.skeletonData.findBone("game/snake2");
        SpineSnake.boneGameSnake3 = SpineSnake.skeletonData.findBone("game/snake3");
        SpineSnake.boneGameSnake4 = SpineSnake.skeletonData.findBone("game/snake4");
        SpineSnake.boneGameSnake5 = SpineSnake.skeletonData.findBone("game/snake5");
        SpineSnake.boneGameSnake6 = SpineSnake.skeletonData.findBone("game/snake6");
        SpineSnake.boneGameSnake7 = SpineSnake.skeletonData.findBone("game/snake7");
        SpineSnake.boneGameSnake8 = SpineSnake.skeletonData.findBone("game/snake8");
        SpineSnake.boneGameSnake9 = SpineSnake.skeletonData.findBone("game/snake9");
        SpineSnake.boneGameSnake10 = SpineSnake.skeletonData.findBone("game/snake10");
        SpineSnake.slotGameSnake = SpineSnake.skeletonData.findSlot("game/snake");
        SpineSnake.skinDefault = SpineSnake.skeletonData.findSkin("default");
        SpineSuccubus.skeletonData = assetManager.get("spine/succubus.json");
        SpineSuccubus.animationData = assetManager.get("spine/succubus.json-animation");
        SpineSuccubus.animationAnimation = SpineSuccubus.skeletonData.findAnimation("animation");
        SpineSuccubus.animationGrenade = SpineSuccubus.skeletonData.findAnimation("grenade");
        SpineSuccubus.boneRoot = SpineSuccubus.skeletonData.findBone("root");
        SpineSuccubus.boneGameSuccubusBody = SpineSuccubus.skeletonData.findBone("game/succubus-body");
        SpineSuccubus.boneGameSuccubusHand = SpineSuccubus.skeletonData.findBone("game/succubus-hand");
        SpineSuccubus.boneGameSuccubusHand2 = SpineSuccubus.skeletonData.findBone("game/succubus-hand2");
        SpineSuccubus.boneGameSuccubusHead = SpineSuccubus.skeletonData.findBone("game/succubus-head");
        SpineSuccubus.boneGameSuccubusHair = SpineSuccubus.skeletonData.findBone("game/succubus-hair");
        SpineSuccubus.slotGameSuccubusTail = SpineSuccubus.skeletonData.findSlot("game/succubus-tail");
        SpineSuccubus.slotGameSuccubusHair = SpineSuccubus.skeletonData.findSlot("game/succubus-hair");
        SpineSuccubus.slotGameSuccubusNeck = SpineSuccubus.skeletonData.findSlot("game/succubus-neck");
        SpineSuccubus.slotGameSuccubusBody = SpineSuccubus.skeletonData.findSlot("game/succubus-body");
        SpineSuccubus.slotGameSuccubusHand = SpineSuccubus.skeletonData.findSlot("game/succubus-hand");
        SpineSuccubus.slotGameSuccubusHand2 = SpineSuccubus.skeletonData.findSlot("game/succubus-hand2");
        SpineSuccubus.slotGameSuccubusHead = SpineSuccubus.skeletonData.findSlot("game/succubus-head");
        SpineSuccubus.slotGameGrenade = SpineSuccubus.skeletonData.findSlot("game/grenade");
        SpineSuccubus.skinDefault = SpineSuccubus.skeletonData.findSkin("default");
        SpineTileCliff.skeletonData = assetManager.get("spine/tile-cliff.json");
        SpineTileCliff.animationData = assetManager.get("spine/tile-cliff.json-animation");
        SpineTileCliff.animationAnimation = SpineTileCliff.skeletonData.findAnimation("animation");
        SpineTileCliff.boneRoot = SpineTileCliff.skeletonData.findBone("root");
        SpineTileCliff.slotGameTileCliff = SpineTileCliff.skeletonData.findSlot("game/tile-cliff");
        SpineTileCliff.slotGameTileGround = SpineTileCliff.skeletonData.findSlot("game/tile-ground");
        SpineTileCliff.skinDefault = SpineTileCliff.skeletonData.findSkin("default");
        SpineTileGround.skeletonData = assetManager.get("spine/tile-ground.json");
        SpineTileGround.animationData = assetManager.get("spine/tile-ground.json-animation");
        SpineTileGround.animationAnimation = SpineTileGround.skeletonData.findAnimation("animation");
        SpineTileGround.boneRoot = SpineTileGround.skeletonData.findBone("root");
        SpineTileGround.slotGameTileGround = SpineTileGround.skeletonData.findSlot("game/tile-ground");
        SpineTileGround.skinDefault = SpineTileGround.skeletonData.findSkin("default");
        SpineTileMagma.skeletonData = assetManager.get("spine/tile-magma.json");
        SpineTileMagma.animationData = assetManager.get("spine/tile-magma.json-animation");
        SpineTileMagma.animationAnimation = SpineTileMagma.skeletonData.findAnimation("animation");
        SpineTileMagma.boneRoot = SpineTileMagma.skeletonData.findBone("root");
        SpineTileMagma.slotGameTileMagma = SpineTileMagma.skeletonData.findSlot("game/tile-magma");
        SpineTileMagma.skinDefault = SpineTileMagma.skeletonData.findSkin("default");
        SpineTrident.skeletonData = assetManager.get("spine/trident.json");
        SpineTrident.animationData = assetManager.get("spine/trident.json-animation");
        SpineTrident.animationAnimation = SpineTrident.skeletonData.findAnimation("animation");
        SpineTrident.boneRoot = SpineTrident.skeletonData.findBone("root");
        SpineTrident.slotGamePlayerTrident = SpineTrident.skeletonData.findSlot("game/player-trident");
        SpineTrident.skinDefault = SpineTrident.skeletonData.findSkin("default");
        SpineTutorial.skeletonData = assetManager.get("spine/tutorial.json");
        SpineTutorial.animationData = assetManager.get("spine/tutorial.json-animation");
        SpineTutorial.animationAnimation = SpineTutorial.skeletonData.findAnimation("animation");
        SpineTutorial.boneRoot = SpineTutorial.skeletonData.findBone("root");
        SpineTutorial.slotGameTutorial01 = SpineTutorial.skeletonData.findSlot("game/tutorial01");
        SpineTutorial.skinTutorial01 = SpineTutorial.skeletonData.findSkin("tutorial01");
        SpineTutorial.skinTutorial02 = SpineTutorial.skeletonData.findSkin("tutorial02");
        SpineTutorial.skinTutorial03 = SpineTutorial.skeletonData.findSkin("tutorial03");
        SpineTutorial.skinTutorial04 = SpineTutorial.skeletonData.findSkin("tutorial04");
        SpineTutorial.skinTutorial05 = SpineTutorial.skeletonData.findSkin("tutorial05");
        SpineTutorial.skinTutorial06 = SpineTutorial.skeletonData.findSkin("tutorial06");
        SpineTutorial.skinTutorial07 = SpineTutorial.skeletonData.findSkin("tutorial07");
        SpineTutorial.skinTutorial08 = SpineTutorial.skeletonData.findSkin("tutorial08");
        SpineTutorial.skinTutorial09 = SpineTutorial.skeletonData.findSkin("tutorial09");
        SpineTutorial.skinTutorial10 = SpineTutorial.skeletonData.findSkin("tutorial10");
        SpineTutorial.skinTutorial11 = SpineTutorial.skeletonData.findSkin("tutorial11");
        SpineTutorial.skinTutorial12 = SpineTutorial.skeletonData.findSkin("tutorial12");
        skin_skin = assetManager.get("skin/skin.json");
        SkinSkinStyles.bDefault = skin_skin.get("default", Button.ButtonStyle.class);
        SkinSkinStyles.ibDefault = skin_skin.get("default", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibThrow = skin_skin.get("throw", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibWolf = skin_skin.get("wolf", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibStrike = skin_skin.get("strike", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibWait = skin_skin.get("wait", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibDash = skin_skin.get("dash", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.lDefault = skin_skin.get("default", Label.LabelStyle.class);
        SkinSkinStyles.lTitle = skin_skin.get("title", Label.LabelStyle.class);
        SkinSkinStyles.lstDefault = skin_skin.get("default", List.ListStyle.class);
        SkinSkinStyles.pDefaultHorizontal = skin_skin.get("default-horizontal", ProgressBar.ProgressBarStyle.class);
        SkinSkinStyles.spDefault = skin_skin.get("default", ScrollPane.ScrollPaneStyle.class);
        SkinSkinStyles.sbDefault = skin_skin.get("default", SelectBox.SelectBoxStyle.class);
        SkinSkinStyles.sDefaultHorizontal = skin_skin.get("default-horizontal", Slider.SliderStyle.class);
        SkinSkinStyles.tbToggle = skin_skin.get("toggle", TextButton.TextButtonStyle.class);
        SkinSkinStyles.tbDefault = skin_skin.get("default", TextButton.TextButtonStyle.class);
        SkinSkinStyles.tbMenu = skin_skin.get("menu", TextButton.TextButtonStyle.class);
        SkinSkinStyles.tfDefault = skin_skin.get("default", TextField.TextFieldStyle.class);
        SkinSkinStyles.ttDefault = skin_skin.get("default", TextTooltip.TextTooltipStyle.class);
        SkinSkinStyles.wDialog = skin_skin.get("dialog", Window.WindowStyle.class);
        SkinSkinStyles.wDefault = skin_skin.get("default", Window.WindowStyle.class);
        SkinSkinStyles.wAbility = skin_skin.get("ability", Window.WindowStyle.class);
        sfx_click = assetManager.get("sfx/click.mp3");
        sfx_gameBlessing = assetManager.get("sfx/game/blessing.mp3");
        sfx_gameBow = assetManager.get("sfx/game/bow.mp3");
        sfx_gameBurn = assetManager.get("sfx/game/burn.mp3");
        sfx_gameDash = assetManager.get("sfx/game/dash.mp3");
        sfx_gameExplosionBig = assetManager.get("sfx/game/explosion-big.mp3");
        sfx_gameExplosion = assetManager.get("sfx/game/explosion.mp3");
        sfx_gamePlayerDie = assetManager.get("sfx/game/player-die.mp3");
        sfx_gamePlayerHurt = assetManager.get("sfx/game/player-hurt.mp3");
        sfx_gameSatanHurt = assetManager.get("sfx/game/satan-hurt.mp3");
        sfx_gameSlash = assetManager.get("sfx/game/slash.mp3");
        sfx_gameTeleport = assetManager.get("sfx/game/teleport.mp3");
        sfx_gameWalk = assetManager.get("sfx/game/walk.mp3");
        sfx_logoCrash = assetManager.get("sfx/logo/crash.mp3");
        sfx_logoLand = assetManager.get("sfx/logo/land.mp3");
        sfx_logoLine = assetManager.get("sfx/logo/line.mp3");
        sfx_logoMove = assetManager.get("sfx/logo/move.mp3");
        bgm_01a = assetManager.get("bgm/01a.mp3");
        bgm_01b = assetManager.get("bgm/01b.mp3");
        bgm_01c = assetManager.get("bgm/01c.mp3");
        bgm_02a = assetManager.get("bgm/02a.mp3");
        bgm_02b = assetManager.get("bgm/02b.mp3");
        bgm_02c = assetManager.get("bgm/02c.mp3");
        bgm_03a = assetManager.get("bgm/03a.mp3");
        bgm_03b = assetManager.get("bgm/03b.mp3");
        bgm_03c = assetManager.get("bgm/03c.mp3");
        bgm_04a = assetManager.get("bgm/04a.mp3");
        bgm_04b = assetManager.get("bgm/04b.mp3");
        bgm_05a = assetManager.get("bgm/05a.mp3");
        bgm_06a = assetManager.get("bgm/06a.mp3");
        bgm_06b = assetManager.get("bgm/06b.mp3");
        bgm_07a = assetManager.get("bgm/07a.mp3");
        bgm_07b = assetManager.get("bgm/07b.mp3");
        bgm_07c = assetManager.get("bgm/07c.mp3");
        bgm_08a = assetManager.get("bgm/08a.mp3");
        bgm_08b = assetManager.get("bgm/08b.mp3");
        bgm_09a = assetManager.get("bgm/09a.mp3");
        bgm_09b = assetManager.get("bgm/09b.mp3");
        bgm_10a = assetManager.get("bgm/10a.mp3");
        bgm_10b = assetManager.get("bgm/10b.mp3");
        bgm_audioSample = assetManager.get("bgm/audio-sample.mp3");
        bgm_ending01a = assetManager.get("bgm/ending01a.mp3");
        bgm_ending01b = assetManager.get("bgm/ending01b.mp3");
        bgm_ending01c = assetManager.get("bgm/ending01c.mp3");
        bgm_ending01d = assetManager.get("bgm/ending01d.mp3");
        bgm_ending01e = assetManager.get("bgm/ending01e.mp3");
        bgm_ending01f = assetManager.get("bgm/ending01f.mp3");
        bgm_ending01g = assetManager.get("bgm/ending01g.mp3");
        bgm_ending01h = assetManager.get("bgm/ending01h.mp3");
        bgm_ending02 = assetManager.get("bgm/ending02.mp3");
        bgm_ending03a = assetManager.get("bgm/ending03a.mp3");
        bgm_ending03b = assetManager.get("bgm/ending03b.mp3");
        bgm_final01a = assetManager.get("bgm/final01a.mp3");
        bgm_final01b = assetManager.get("bgm/final01b.mp3");
        bgm_final01c = assetManager.get("bgm/final01c.mp3");
        bgm_final02a = assetManager.get("bgm/final02a.mp3");
        bgm_final02b = assetManager.get("bgm/final02b.mp3");
        bgm_final03a = assetManager.get("bgm/final03a.mp3");
        bgm_final03b = assetManager.get("bgm/final03b.mp3");
        bgm_final04a = assetManager.get("bgm/final04a.mp3");
        bgm_final04b = assetManager.get("bgm/final04b.mp3");
        bgm_final05a = assetManager.get("bgm/final05a.mp3");
        bgm_final05b = assetManager.get("bgm/final05b.mp3");
        bgm_final06a = assetManager.get("bgm/final06a.mp3");
        bgm_final06b = assetManager.get("bgm/final06b.mp3");
        bgm_final07a = assetManager.get("bgm/final07a.mp3");
        bgm_final07b = assetManager.get("bgm/final07b.mp3");
        bgm_game = assetManager.get("bgm/game.ogg");
        bgm_menu = assetManager.get("bgm/menu.ogg");
        bgm_second01 = assetManager.get("bgm/second01.mp3");
        bgm_second02 = assetManager.get("bgm/second02.mp3");
        bgm_second03 = assetManager.get("bgm/second03.mp3");
        bgm_second04 = assetManager.get("bgm/second04.mp3");
        bgm_second05 = assetManager.get("bgm/second05.mp3");
    }

    public static class SpineArrow {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameArrow;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBlood {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBlood01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineExplosion {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameExplosion01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineFlame {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationFlames;

        public static BoneData boneRoot;

        public static BoneData boneGameFlame01;

        public static BoneData boneGameFlame02;

        public static BoneData boneGameFlame03;

        public static SlotData slotGameFlame01;

        public static SlotData slotGameFlame02;

        public static SlotData slotGameFlame03;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineGlutton {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameGluttonBody;

        public static BoneData boneGameGluttonHand;

        public static BoneData boneGameGluttonHand2;

        public static BoneData boneGameGluttonHead;

        public static SlotData slotGameGluttonBody;

        public static SlotData slotGameGluttonHand;

        public static SlotData slotGameGluttonHand2;

        public static SlotData slotGameGluttonHead;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineGoat {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameGoatBody;

        public static BoneData boneGameGoatHand;

        public static BoneData boneGameGoatHand2;

        public static BoneData boneGameGoatHead;

        public static SlotData slotGameGoatBody;

        public static SlotData slotGameGoatStaff;

        public static SlotData slotGameGoatHand;

        public static SlotData slotGameGoatHand2;

        public static SlotData slotGameGoatHead;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineGrenade {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameGrenade;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineHeaven {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneLogoWhite;

        public static BoneData boneGameHeavenStairs;

        public static BoneData boneGameHeavenCity;

        public static SlotData slotClip;

        public static SlotData slotLogoWhite;

        public static SlotData slotGameHeavenCity;

        public static SlotData slotGameHeavenClouds;

        public static SlotData slotGameHeavenStairs;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineImp {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameImpBody;

        public static BoneData boneGameImpWing;

        public static BoneData boneGameImpWing2;

        public static BoneData boneGameImpHead;

        public static SlotData slotGameImpWing;

        public static SlotData slotGameImpWing2;

        public static SlotData slotGameImpBody;

        public static SlotData slotGameImpHead;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineLavaBubble {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameLavaBubble01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineLogoLibgdx {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationStand;

        public static EventData eventLogoLand;

        public static EventData eventLogoLine;

        public static EventData eventLogoMove;

        public static BoneData boneRoot;

        public static BoneData boneTemplate;

        public static BoneData boneL;

        public static BoneData boneI;

        public static BoneData boneB;

        public static BoneData boneG;

        public static BoneData boneD;

        public static BoneData boneX;

        public static BoneData boneNext;

        public static SlotData slotBackground;

        public static SlotData slotB;

        public static SlotData slotD;

        public static SlotData slotG;

        public static SlotData slotI;

        public static SlotData slotX;

        public static SlotData slotL;

        public static SlotData slotLogoLibGDXNext;

        public static SlotData slotNext;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineLogoRay3k {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationStand;

        public static EventData eventLogoCrash;

        public static BoneData boneRoot;

        public static BoneData boneSpiral;

        public static BoneData boneLogo;

        public static SlotData slotBackground;

        public static SlotData slotSpiral;

        public static SlotData slotCompany;

        public static SlotData slotLogo;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineOrb {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameOrb;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpinePentagram {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGamePentagram;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpinePlayer {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationDeshield;

        public static Animation animationDie;

        public static Animation animationShield;

        public static Animation animationSlash;

        public static Animation animationSmack;

        public static Animation animationSpawn;

        public static Animation animationTeleport;

        public static Animation animationTrident;

        public static BoneData boneRoot;

        public static BoneData boneGamePlayerBody;

        public static BoneData boneGamePlayerHand;

        public static BoneData boneGamePlayerHand2;

        public static BoneData boneGamePlayerHead;

        public static BoneData boneGameTeleport01;

        public static BoneData boneGameBloodPool;

        public static SlotData slotGameBloodPool;

        public static SlotData slotClip;

        public static SlotData slotGamePlayerBody;

        public static SlotData slotGamePlayerSword;

        public static SlotData slotGamePlayerTrident;

        public static SlotData slotGamePlayerHand;

        public static SlotData slotGamePlayerHand2;

        public static SlotData slotGamePlayerHead;

        public static SlotData slotGameTeleport01;

        public static SlotData slotGameShield;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSatan {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationDie;

        public static Animation animationLand;

        public static Animation animationLeap;

        public static BoneData boneRoot;

        public static BoneData boneGameSatanBody;

        public static BoneData boneGameSatanHand2;

        public static BoneData boneGameSatanHand;

        public static BoneData boneGameSatanHead;

        public static BoneData boneGameSatanWing;

        public static BoneData boneGameSatanWing2;

        public static BoneData boneGameBloodPool;

        public static SlotData slotGameBloodPool;

        public static SlotData slotClip;

        public static SlotData slotGameSatanWing;

        public static SlotData slotGameSatanWing2;

        public static SlotData slotGameSatanBody;

        public static SlotData slotGameSatanHand;

        public static SlotData slotGameSatanHand2;

        public static SlotData slotGameSatanHead;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShrine {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameShrine;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSkeleton {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameSkeletonBody;

        public static BoneData boneGameSkeletonHand;

        public static BoneData boneGameSkeletonHand2;

        public static BoneData boneGameSkeletonHead;

        public static SlotData slotGameSkeletonBody;

        public static SlotData slotGameSkeletonBow;

        public static SlotData slotGameSkeletonHand;

        public static SlotData slotGameSkeletonHand2;

        public static SlotData slotGameSkeletonHead;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSlam {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSlam01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSnake {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameSnake;

        public static BoneData boneGameSnake2;

        public static BoneData boneGameSnake3;

        public static BoneData boneGameSnake4;

        public static BoneData boneGameSnake5;

        public static BoneData boneGameSnake6;

        public static BoneData boneGameSnake7;

        public static BoneData boneGameSnake8;

        public static BoneData boneGameSnake9;

        public static BoneData boneGameSnake10;

        public static SlotData slotGameSnake;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSuccubus {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationGrenade;

        public static BoneData boneRoot;

        public static BoneData boneGameSuccubusBody;

        public static BoneData boneGameSuccubusHand;

        public static BoneData boneGameSuccubusHand2;

        public static BoneData boneGameSuccubusHead;

        public static BoneData boneGameSuccubusHair;

        public static SlotData slotGameSuccubusTail;

        public static SlotData slotGameSuccubusHair;

        public static SlotData slotGameSuccubusNeck;

        public static SlotData slotGameSuccubusBody;

        public static SlotData slotGameSuccubusHand;

        public static SlotData slotGameSuccubusHand2;

        public static SlotData slotGameSuccubusHead;

        public static SlotData slotGameGrenade;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineTileCliff {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameTileCliff;

        public static SlotData slotGameTileGround;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineTileGround {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameTileGround;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineTileMagma {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameTileMagma;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineTrident {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGamePlayerTrident;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineTutorial {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameTutorial01;

        public static com.esotericsoftware.spine.Skin skinTutorial01;

        public static com.esotericsoftware.spine.Skin skinTutorial02;

        public static com.esotericsoftware.spine.Skin skinTutorial03;

        public static com.esotericsoftware.spine.Skin skinTutorial04;

        public static com.esotericsoftware.spine.Skin skinTutorial05;

        public static com.esotericsoftware.spine.Skin skinTutorial06;

        public static com.esotericsoftware.spine.Skin skinTutorial07;

        public static com.esotericsoftware.spine.Skin skinTutorial08;

        public static com.esotericsoftware.spine.Skin skinTutorial09;

        public static com.esotericsoftware.spine.Skin skinTutorial10;

        public static com.esotericsoftware.spine.Skin skinTutorial11;

        public static com.esotericsoftware.spine.Skin skinTutorial12;
    }

    public static class SkinSkinStyles {
        public static Button.ButtonStyle bDefault;

        public static ImageButton.ImageButtonStyle ibDefault;

        public static ImageButton.ImageButtonStyle ibThrow;

        public static ImageButton.ImageButtonStyle ibWolf;

        public static ImageButton.ImageButtonStyle ibStrike;

        public static ImageButton.ImageButtonStyle ibWait;

        public static ImageButton.ImageButtonStyle ibDash;

        public static Label.LabelStyle lDefault;

        public static Label.LabelStyle lTitle;

        public static List.ListStyle lstDefault;

        public static ProgressBar.ProgressBarStyle pDefaultHorizontal;

        public static ScrollPane.ScrollPaneStyle spDefault;

        public static SelectBox.SelectBoxStyle sbDefault;

        public static Slider.SliderStyle sDefaultHorizontal;

        public static TextButton.TextButtonStyle tbToggle;

        public static TextButton.TextButtonStyle tbDefault;

        public static TextButton.TextButtonStyle tbMenu;

        public static TextField.TextFieldStyle tfDefault;

        public static TextTooltip.TextTooltipStyle ttDefault;

        public static Window.WindowStyle wDialog;

        public static Window.WindowStyle wDefault;

        public static Window.WindowStyle wAbility;
    }

    public static class Values {
        public static float bubbleDelayMin = 0.5f;

        public static float bubbleDelayMax = 1.0f;

        public static int offsetX = 395;

        public static int offsetY = -425;

        public static String name = "Raeleus";

        public static boolean godMode = true;

        public static int id = 10;
    }
}
