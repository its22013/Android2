package jp.ac.it_college.std.s22013.navigation_sample.fragment

import android.os.Bundle
import androidx.navigation.NavDirections
import jp.ac.it_college.std.s22013.navigation_sample.R
import kotlin.Int

public class SecondFragmentDirections private constructor() {
  private data class ActionSecondFragmentToThradFragment(
    public val choice: Int = 0,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_secondFragment_to_thradFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("choice", this.choice)
        return result
      }
  }

  public companion object {
    public fun actionSecondFragmentToThradFragment(choice: Int = 0): NavDirections =
        ActionSecondFragmentToThradFragment(choice)
  }
}
