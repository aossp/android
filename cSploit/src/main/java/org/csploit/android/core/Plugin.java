/*
 * This file is part of the dSploit.
 *
 * Copyleft of Simone Margaritelli aka evilsocket <evilsocket@gmail.com>
 *
 * dSploit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * dSploit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with dSploit.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.csploit.android.core;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;

import org.csploit.android.R;
import org.csploit.android.net.Target;
import org.csploit.android.net.metasploit.RPCClient;

public abstract class Plugin extends Fragment {
  public static final int NO_LAYOUT = -1;

  private int mNameStringId = -1;
  private int mDescriptionStringId = -1;
  private Target.Type[] mAllowedTargetTypes = null;
  private int mLayoutId = 0;
  private int mIconId = 0;
  protected Child mProcess = null;

  public Plugin(int nameStringId, int descStringId, Target.Type[] allowedTargetTypes, int layoutId, int iconResourceId){
    mNameStringId = nameStringId;
    mDescriptionStringId = descStringId;

    mAllowedTargetTypes = allowedTargetTypes;
    mLayoutId = layoutId;
    mIconId = iconResourceId;
  }

  public Plugin(int nameStringId, int descStringId, Target.Type[] allowedTargetTypes, int layoutId){
    this(nameStringId, descStringId, allowedTargetTypes, layoutId, R.drawable.action_plugin);
  }

  public int getName(){
    return mNameStringId;
  }

  public int getDescription(){
    return mDescriptionStringId;
  }

  public Target.Type[] getAllowedTargetTypes(){
    return mAllowedTargetTypes;
  }

  public int getIconResourceId(){
    return mIconId;
  }

  public boolean isAllowedTarget(Target target){
    for(Target.Type type : mAllowedTargetTypes)
      if(type == target.getType())
        return true;

    return false;
  }

  public boolean hasLayoutToShow(){
    return mLayoutId != -1;
  }

  public void onActionClick(Context context){

  }

  protected View findViewById(int id) {
    return getActivity().findViewById(id);
  }

  protected Drawable getDrawable(int id) {
    return ContextCompat.getDrawable(getActivity(), id);
  }

  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    getActivity().setTitle(System.getCurrentTarget() + " > " + getString( mNameStringId ) );
    getActivity().setContentView(mLayoutId);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item){
    switch(item.getItemId()){
      case android.R.id.home:

        onDetach();

        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    getActivity().overridePendingTransition(R.anim.fadeout, R.anim.fadein);
  }

  public void onRpcChange(RPCClient currentValue) { }
}
