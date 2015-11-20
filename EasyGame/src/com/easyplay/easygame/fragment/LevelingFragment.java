package com.easyplay.easygame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.easyplay.easygame.R;

/**
 * 首页-代练页
 * 
 * @author chuwe1
 * 
 */
public class LevelingFragment extends Fragment {

  private TextView textView;
  private Button button;
  private int count;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_leveling, null);

    textView = (TextView) view.findViewById(R.id.textView);
    button = (Button) view.findViewById(R.id.button);

    return view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    button.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        if (count % 2 == 0) {
          textView.setText("这是首页fragment");
        } else {
          textView.setText("点击了button");
        }
        count++;
      }
    });
  }
}
