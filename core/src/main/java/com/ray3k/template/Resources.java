package com.ray3k.template;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
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

    public static Sound sfx_logoCrash;

    public static Sound sfx_logoLand;

    public static Sound sfx_logoLine;

    public static Sound sfx_logoMove;

    public static Music bgm_audioSample;

    public static Music bgm_menu;

    public static void loadResources(AssetManager assetManager) {
        textures_textures = assetManager.get("textures/textures.atlas");
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
        skin_skin = assetManager.get("skin/skin.json");
        SkinSkinStyles.bClose = skin_skin.get("close", Button.ButtonStyle.class);
        SkinSkinStyles.bDefault = skin_skin.get("default", Button.ButtonStyle.class);
        SkinSkinStyles.bPause = skin_skin.get("pause", Button.ButtonStyle.class);
        SkinSkinStyles.bToggle = skin_skin.get("toggle", Button.ButtonStyle.class);
        SkinSkinStyles.bStop = skin_skin.get("stop", Button.ButtonStyle.class);
        SkinSkinStyles.bPlay = skin_skin.get("play", Button.ButtonStyle.class);
        SkinSkinStyles.cbDefault = skin_skin.get("default", CheckBox.CheckBoxStyle.class);
        SkinSkinStyles.ibDefault = skin_skin.get("default", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.itbDefault = skin_skin.get("default", ImageTextButton.ImageTextButtonStyle.class);
        SkinSkinStyles.itbRadio = skin_skin.get("radio", ImageTextButton.ImageTextButtonStyle.class);
        SkinSkinStyles.lSmall = skin_skin.get("small", Label.LabelStyle.class);
        SkinSkinStyles.lDefault = skin_skin.get("default", Label.LabelStyle.class);
        SkinSkinStyles.lstSelectBox = skin_skin.get("select-box", List.ListStyle.class);
        SkinSkinStyles.pDefaultVertical = skin_skin.get("default-vertical", ProgressBar.ProgressBarStyle.class);
        SkinSkinStyles.pDefaultHorizontal = skin_skin.get("default-horizontal", ProgressBar.ProgressBarStyle.class);
        SkinSkinStyles.spSelectBox = skin_skin.get("select-box", ScrollPane.ScrollPaneStyle.class);
        SkinSkinStyles.spDefault = skin_skin.get("default", ScrollPane.ScrollPaneStyle.class);
        SkinSkinStyles.sbDefault = skin_skin.get("default", SelectBox.SelectBoxStyle.class);
        SkinSkinStyles.sScrubber = skin_skin.get("scrubber", Slider.SliderStyle.class);
        SkinSkinStyles.sDefaultVertical = skin_skin.get("default-vertical", Slider.SliderStyle.class);
        SkinSkinStyles.sDefaultHorizontal = skin_skin.get("default-horizontal", Slider.SliderStyle.class);
        SkinSkinStyles.spltDefaultVertical = skin_skin.get("default-vertical", SplitPane.SplitPaneStyle.class);
        SkinSkinStyles.spltDefaultHorizontal = skin_skin.get("default-horizontal", SplitPane.SplitPaneStyle.class);
        SkinSkinStyles.tbToggle = skin_skin.get("toggle", TextButton.TextButtonStyle.class);
        SkinSkinStyles.tbDefault = skin_skin.get("default", TextButton.TextButtonStyle.class);
        SkinSkinStyles.tfDefault = skin_skin.get("default", TextField.TextFieldStyle.class);
        SkinSkinStyles.ttDefault = skin_skin.get("default", TextTooltip.TextTooltipStyle.class);
        SkinSkinStyles.tsDefault = skin_skin.get("default", Touchpad.TouchpadStyle.class);
        SkinSkinStyles.tDefault = skin_skin.get("default", Tree.TreeStyle.class);
        SkinSkinStyles.wDefault = skin_skin.get("default", Window.WindowStyle.class);
        sfx_click = assetManager.get("sfx/click.mp3");
        sfx_logoCrash = assetManager.get("sfx/logo/crash.mp3");
        sfx_logoLand = assetManager.get("sfx/logo/land.mp3");
        sfx_logoLine = assetManager.get("sfx/logo/line.mp3");
        sfx_logoMove = assetManager.get("sfx/logo/move.mp3");
        bgm_audioSample = assetManager.get("bgm/audio-sample.mp3");
        bgm_menu = assetManager.get("bgm/menu.mp3");
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

    public static class SkinSkinStyles {
        public static Button.ButtonStyle bClose;

        public static Button.ButtonStyle bDefault;

        public static Button.ButtonStyle bPause;

        public static Button.ButtonStyle bToggle;

        public static Button.ButtonStyle bStop;

        public static Button.ButtonStyle bPlay;

        public static CheckBox.CheckBoxStyle cbDefault;

        public static ImageButton.ImageButtonStyle ibDefault;

        public static ImageTextButton.ImageTextButtonStyle itbDefault;

        public static ImageTextButton.ImageTextButtonStyle itbRadio;

        public static Label.LabelStyle lSmall;

        public static Label.LabelStyle lDefault;

        public static List.ListStyle lstSelectBox;

        public static ProgressBar.ProgressBarStyle pDefaultVertical;

        public static ProgressBar.ProgressBarStyle pDefaultHorizontal;

        public static ScrollPane.ScrollPaneStyle spSelectBox;

        public static ScrollPane.ScrollPaneStyle spDefault;

        public static SelectBox.SelectBoxStyle sbDefault;

        public static Slider.SliderStyle sScrubber;

        public static Slider.SliderStyle sDefaultVertical;

        public static Slider.SliderStyle sDefaultHorizontal;

        public static SplitPane.SplitPaneStyle spltDefaultVertical;

        public static SplitPane.SplitPaneStyle spltDefaultHorizontal;

        public static TextButton.TextButtonStyle tbToggle;

        public static TextButton.TextButtonStyle tbDefault;

        public static TextField.TextFieldStyle tfDefault;

        public static TextTooltip.TextTooltipStyle ttDefault;

        public static Touchpad.TouchpadStyle tsDefault;

        public static Tree.TreeStyle tDefault;

        public static Window.WindowStyle wDefault;
    }

    public static class Values {
        public static float jumpVelocity = 10.0f;

        public static String name = "Raeleus";

        public static boolean godMode = true;

        public static int id = 10;
    }
}
