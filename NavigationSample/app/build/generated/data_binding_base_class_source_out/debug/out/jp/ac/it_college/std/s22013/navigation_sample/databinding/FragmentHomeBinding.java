// Generated by view binder compiler. Do not edit!
package jp.ac.it_college.std.s22013.navigation_sample.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import jp.ac.it_college.std.s22013.navigation_sample.R;

public final class FragmentHomeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView homeTitle;

  @NonNull
  public final Button homeToNext;

  private FragmentHomeBinding(@NonNull ConstraintLayout rootView, @NonNull TextView homeTitle,
      @NonNull Button homeToNext) {
    this.rootView = rootView;
    this.homeTitle = homeTitle;
    this.homeToNext = homeToNext;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.home_title;
      TextView homeTitle = ViewBindings.findChildViewById(rootView, id);
      if (homeTitle == null) {
        break missingId;
      }

      id = R.id.home_to_next;
      Button homeToNext = ViewBindings.findChildViewById(rootView, id);
      if (homeToNext == null) {
        break missingId;
      }

      return new FragmentHomeBinding((ConstraintLayout) rootView, homeTitle, homeToNext);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
