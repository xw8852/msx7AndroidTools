package com.msx7.animation;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class AnimationSeque {

    public static final Animation equenAnimation(Animation... args) {
        if (args.length <= 0)
            return null;
        for (int i = 0; i < args.length; i++) {
            if (i + 1 == args.length)
                break;
            args[i].setAnimationListener(new AnimationSequenListener(args[i + 1]));
        }

        return args[0];

    }

    private static final class AnimationSequenListener implements AnimationListener {
        Animation anim;

        public AnimationSequenListener(Animation animation) {
            super();
            this.anim = animation;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (anim != null)
                anim.start();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }

}
