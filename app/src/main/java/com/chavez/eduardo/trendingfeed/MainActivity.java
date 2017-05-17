package com.chavez.eduardo.trendingfeed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

public class MainActivity extends AppCompatActivity implements ItemMain.OnFragmentInteractionListener, LoadAllItems.OnFragmentInteractionListener, TwitterListFrg.OnFragmentInteractionListener, ViewRoutineFragment.OnFragmentInteractionListener, UserTweetFragment.OnFragmentInteractionListener {
    private TwitterSession session;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private LoadAllItems loadAllItems;
    private ItemMain itemMain;
    private TwitterListFrg twitterListFrg;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (loadAllItems.isVisible()){
                        return true;
                    }

                    fragment = loadAllItems;
                    break;
                case R.id.navigation_add_element:
                    if (itemMain.isVisible()){
                        return true;
                    }
                    fragment = itemMain;
                    break;
                case R.id.navigation_tweet_feed:
                    if (twitterListFrg.isVisible()){
                        return true;
                    }
                    fragment = twitterListFrg;
                    break;
            }

            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content,fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().show();

        session = Twitter.getSessionManager().getActiveSession();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadAllItems = new LoadAllItems();
        itemMain = new ItemMain();
        twitterListFrg = new TwitterListFrg();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, loadAllItems).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.userTweets){
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, new UserTweetFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }
        else if (id == R.id.logoutButton){
            generateLogoutIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void generateLogoutIntent() {
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        Twitter.getSessionManager().clearActiveSession();
        Twitter.logOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_twitter,menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void prepareTweet(){
        TweetComposer.Builder builder = new TweetComposer.Builder(MainActivity.this)
                .text("just setting up my Fabric.");
        builder.show();
    }

    @Override
    public void onBackPressed() {

            }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
