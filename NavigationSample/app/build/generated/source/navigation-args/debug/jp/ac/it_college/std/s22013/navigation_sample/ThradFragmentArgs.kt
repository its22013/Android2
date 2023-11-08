package jp.ac.it_college.std.s22013.navigation_sample

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class ThradFragmentArgs(
  public val choice: Int = 0,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("choice", this.choice)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("choice", this.choice)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): ThradFragmentArgs {
      bundle.setClassLoader(ThradFragmentArgs::class.java.classLoader)
      val __choice : Int
      if (bundle.containsKey("choice")) {
        __choice = bundle.getInt("choice")
      } else {
        __choice = 0
      }
      return ThradFragmentArgs(__choice)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): ThradFragmentArgs {
      val __choice : Int?
      if (savedStateHandle.contains("choice")) {
        __choice = savedStateHandle["choice"]
        if (__choice == null) {
          throw IllegalArgumentException("Argument \"choice\" of type integer does not support null values")
        }
      } else {
        __choice = 0
      }
      return ThradFragmentArgs(__choice)
    }
  }
}
