package de.remotelight.mobile.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.updatePadding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import de.remotelight.mobile.R

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set up toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolBarMain)
        setSupportActionBar(toolbar)

        // set up navigation controller
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_main, R.id.nav_colors, R.id.nav_animations, R.id.nav_scenes, R.id.nav_musicsync), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)

        // set up edge-to-edge ui
        drawerLayout.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        // navigation view insets: add system bars padding to padding of the navigation view
        val initialNavViewPadding = arrayOf(navigationView.paddingLeft, navigationView.paddingTop, navigationView.paddingRight, navigationView.paddingBottom)
        drawerLayout.setOnApplyWindowInsetsListener { view, windowInsets ->
            navigationView.updatePadding(windowInsets.systemWindowInsetLeft + initialNavViewPadding[0],
                    windowInsets.systemWindowInsetTop + initialNavViewPadding[1],
                    initialNavViewPadding[2],
                    windowInsets.systemWindowInsetBottom + initialNavViewPadding[3])
            windowInsets
        }

        // toolbar insets
        val initialToolbarPadding = toolbar.paddingTop
        val initialToolbarHeight = toolbar.layoutParams.height
        toolbar.setOnApplyWindowInsetsListener { view, windowInsets ->
            view.updatePadding(top = windowInsets.systemWindowInsetTop + initialToolbarPadding)
            view.layoutParams.height = initialToolbarHeight + view.paddingTop
            windowInsets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * Override method to close nav drawer on back pressed.
     */
    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}