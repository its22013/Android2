package jp.ac.it_college.std.s22013.navigation_sample.fragment

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import jp.ac.it_college.std.s22013.navigation_sample.R

public class HomeFragmentDirections private constructor() {
  public companion object {
    public fun actionHomeFragmentToSecondFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_homeFragment_to_secondFragment)
  }
}
