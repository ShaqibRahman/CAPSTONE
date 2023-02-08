// Generated by view binder compiler. Do not edit!
package com.example.deafen_prototype.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.deafen_prototype.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySettingsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton BackButton;

  @NonNull
  public final SeekBar PauseMuteDeafenBar;

  @NonNull
  public final TextView Title;

  @NonNull
  public final Switch actionSwitch;

  @NonNull
  public final TextView actiontext;

  @NonNull
  public final TextView deafentext;

  private ActivitySettingsBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageButton BackButton, @NonNull SeekBar PauseMuteDeafenBar, @NonNull TextView Title,
      @NonNull Switch actionSwitch, @NonNull TextView actiontext, @NonNull TextView deafentext) {
    this.rootView = rootView;
    this.BackButton = BackButton;
    this.PauseMuteDeafenBar = PauseMuteDeafenBar;
    this.Title = Title;
    this.actionSwitch = actionSwitch;
    this.actiontext = actiontext;
    this.deafentext = deafentext;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_settings, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySettingsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.BackButton;
      ImageButton BackButton = ViewBindings.findChildViewById(rootView, id);
      if (BackButton == null) {
        break missingId;
      }

      id = R.id.Pause_Mute_Deafen_bar;
      SeekBar PauseMuteDeafenBar = ViewBindings.findChildViewById(rootView, id);
      if (PauseMuteDeafenBar == null) {
        break missingId;
      }

      id = R.id.Title;
      TextView Title = ViewBindings.findChildViewById(rootView, id);
      if (Title == null) {
        break missingId;
      }

      id = R.id.action_switch;
      Switch actionSwitch = ViewBindings.findChildViewById(rootView, id);
      if (actionSwitch == null) {
        break missingId;
      }

      id = R.id.actiontext;
      TextView actiontext = ViewBindings.findChildViewById(rootView, id);
      if (actiontext == null) {
        break missingId;
      }

      id = R.id.deafentext;
      TextView deafentext = ViewBindings.findChildViewById(rootView, id);
      if (deafentext == null) {
        break missingId;
      }

      return new ActivitySettingsBinding((ConstraintLayout) rootView, BackButton,
          PauseMuteDeafenBar, Title, actionSwitch, actiontext, deafentext);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}