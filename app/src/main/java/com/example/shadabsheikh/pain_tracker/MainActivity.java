package com.example.shadabsheikh.pain_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        Boolean cag = false;
         ImageView iv_reading,iv_TroubleCode,iv_diagnose,iv_settings;
         Boolean back= false;
         int click_count = 0 ;
          Fragment fragment;
          boolean count_fragment = false;
   public boolean doubleBackToExitPressedOnce = false;

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    private Toast toast = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = this.getIntent();
        cag = i.getBooleanExtra("cag",false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.content_main, null, false);
        iv_reading = (ImageView) findViewById(R.id.iv_reading);
        iv_reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Fragment fragment = new Readings();
                if(fragment != null)
                {

                    ConstraintLayout rl = (ConstraintLayout) findViewById(R.id.main_content);
                  //rl.removeAllViews();
                    int ft = getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragment).addToBackStack("reading").commit();
                }
            }
        });
        iv_TroubleCode = (ImageView) findViewById(R.id.iv_cloud);
        iv_TroubleCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TroubleCodes();
                if(fragment != null)


                {


                    ConstraintLayout rl = (ConstraintLayout) findViewById(R.id.main_content);
               //   rl.removeAllViews();
                    int ft = getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragment).addToBackStack("touble_code").commit();

                }
            }
        });



        iv_diagnose = (ImageView) findViewById(R.id.iv_diagnose);

        iv_diagnose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Diagnose();
                if(fragment != null)


                {


                    ConstraintLayout rl = (ConstraintLayout) findViewById(R.id.main_content);
                    //   rl.removeAllViews();
                    int ft = getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragment).addToBackStack("touble_code").commit();

                }
            }
        });

        iv_settings = (ImageView) findViewById(R.id.iv_settings);

        iv_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new setting_fragment();
                if(fragment != null)


                {


                    ConstraintLayout rl = (ConstraintLayout) findViewById(R.id.main_content);
                    //   rl.removeAllViews();
                    int ft = getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragment).addToBackStack("settings").commit();

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (count_fragment == false) {
            super.onBackPressed();

        }
        if (doubleBackToExitPressedOnce) {

            //super.onBackPressed();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
           if (drawer.isDrawerOpen(GravityCompat.START)) {
              drawer.closeDrawer(GravityCompat.START);

          }
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack(getSupportFragmentManager().getBackStackEntryAt(0).getId(), getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
            }
            // System.exit(0);

            return;
        }
      //  count_fragment = true;
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit",
                Toast.LENGTH_SHORT).show();

    }

//      //  this.doubleBackToExitPressedOnce = true;
//
//       if (doubleBackToExitPressedOnce) {
//           super.onBackPressed();
//           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//           if (drawer.isDrawerOpen(GravityCompat.START)) {
//               drawer.closeDrawer(GravityCompat.START);
//
//           }
//            return;
//        }
//
//    this.doubleBackToExitPressedOnce = true;
//   // Toast.makeText(MainActivity.this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//        //     else {
//
////            super.onBackPressed();
////
////
////            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
////                alert.setTitle("Exit");
////                alert.setMessage("Are you sure you want to Exit?");
////                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        int pid = android.os.Process.myPid();
////                        android.os.Process.killProcess(pid);
////                        dialog.dismiss();
////                    }
////                });
//
////                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
////
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        dialog.dismiss();
////                    }
////                });
////
////                alert.show();
//

  //  }



 //   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (cag)
        {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_registration) {
            Intent i = new Intent(this,Register.class);
            startActivity(i);
            this.finishActivity(0);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




      public void displaySelectedScreen(int id)
      {

          fragment = null;


          switch (id){


              case R.id.nav_home:

                  if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                      getSupportFragmentManager().popBackStack(getSupportFragmentManager().getBackStackEntryAt(0).getId(), getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
                  }

//
//                 ConstraintLayout rl = (ConstraintLayout) findViewById(R.id.main_content);
// rl.removeAllViews();
             ///     View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.content_main, null, false);
                 // LayoutInflater layoutInflater = (LayoutInflater)getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
               //   View activityView = layoutInflater.inflate(R.layout.content_main, null,false);
                  // add the custom layout of this activity to frame layout.
                //  rl.addView(v);
//
//                  Toast.makeText(MainActivity.this, "This is my Toast message!",
//                          Toast.LENGTH_LONG).show();
//
//
//                  fragment = new Home_Fragment();
break;
              case R.id.nav_diagnose:
                  this.click_count++;
                 // super.onBackPressed();
                  //ConstraintLayout rl = (ConstraintLayout) findViewById(R.id.main_content);
                 // rl.removeAllViews();


                 fragment = new Diagnose();
                  break;
              case R.id.nav_TroubleCodes:
                  this.click_count++;
                  fragment = new TroubleCodes();

                  break;
              case R.id.nav_connection:
                  this.click_count++;
                  fragment = new Connection();
                  break;
              case R.id.nav_reading:
                  this.click_count++;
                  fragment = new Readings();
                  break;
              case R.id.action_registration:
                  this.click_count++;
                  Intent inten= new Intent(MainActivity.this, MainActivity.class);
                  startActivity(inten);
                  this.finish();
                  break;
              case R.id.nav_help:
                  this.click_count++;
                  break;

              case R.id.nav_register:
                  this.click_count++;
                  Intent intent_register = new Intent(MainActivity.this, Register.class);
                  startActivity(intent_register);
              case R.id.nav_setting:
                  this.click_count++;
                  fragment = new setting_fragment();
                  break;

          }
         if(fragment != null)
      {

          ConstraintLayout rl = (ConstraintLayout) findViewById(R.id.main_content);
         // rl.removeAllViews();

              int ft = getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragment).addToBackStack("tag").commit();
          }
          DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
          drawer.closeDrawer(GravityCompat.START);
      }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

displaySelectedScreen(id);

        return true;
    }

}
