package hr.ferit.coolschool.activity;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import hr.ferit.coolschool.R;

public class SplashScreenActivity extends AwesomeSplash {
    @Override
    public void initSplash(ConfigSplash configSplash) {

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.white); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(5); //TODO - change duration
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.logo); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1000); //TODO - change duration
        configSplash.setAnimLogoSplashTechnique(Techniques.Tada);

        //Customize Path
        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(5); //TODO - change duration
        configSplash.setPathSplashStrokeSize(1); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(5); //TODO - change duration
        configSplash.setPathSplashFillColor(R.color.colorPrimary); //path object filling color

    }

    @Override
    public void animationsFinished() {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}