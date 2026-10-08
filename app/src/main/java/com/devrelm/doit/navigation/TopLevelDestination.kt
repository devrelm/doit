package com.devrelm.doit.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.devrelm.doit.R

enum class TopLevelDestination(
    @StringRes val labelRes: Int,
    @DrawableRes val selectedIconRes: Int,
    @DrawableRes val unselectedIconRes: Int,
) {
    NOW(
        labelRes = R.string.now_tab_label,
        selectedIconRes = R.drawable.ic_bolt_filled,
        unselectedIconRes = R.drawable.ic_bolt_outlined,
    ),
    TASKS(
        labelRes = R.string.tasks_tab_label,
        selectedIconRes = R.drawable.ic_checklist,
        unselectedIconRes = R.drawable.ic_checklist,
    ),
    NUDGES(
        labelRes = R.string.nudges_tab_label,
        selectedIconRes = R.drawable.ic_notifications_filled,
        unselectedIconRes = R.drawable.ic_notifications_outlined,
    ),
    ;

    companion object {
        val START_DESTINATION = NOW
    }
}
