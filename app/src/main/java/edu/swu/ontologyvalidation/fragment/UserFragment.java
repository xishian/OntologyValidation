package edu.swu.ontologyvalidation.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import edu.swu.ontologyvalidation.R;
import edu.swu.ontologyvalidation.context.activity.AboutActivity;
import edu.swu.ontologyvalidation.context.activity.LoginActivity;


/**
 * Created by Tuister on 2016/8/16.
 */
public class UserFragment extends Fragment implements View.OnClickListener {
	private static final String TAG = "UserFragment";
	private static final int REQUEST_CODE_LOGIN=11;

	private RoundedImageView mHeadIcon;
	private TextView mTvUsername;
	private TextView mTvSign;
	private RelativeLayout mBtnAbout,mBtnClearCache,mBtnChangeWallpaper,mBtnCheckUpdate,mBtnSettings;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

		mHeadIcon = (RoundedImageView)view.findViewById(R.id.user_avatar);
		mHeadIcon.setOnClickListener(this);
		mTvUsername = (TextView)view.findViewById(R.id.tv_fragment_user_username);
		mTvSign = (TextView)view.findViewById(R.id.tv_fragment_user_statement);
		mBtnAbout = (RelativeLayout)view.findViewById(R.id.btn_fragment_user_about);
		mBtnAbout.setOnClickListener(this);
		mBtnChangeWallpaper = (RelativeLayout)view.findViewById(R.id.btn_fragment_user_change_wallpaper);
		mBtnChangeWallpaper.setOnClickListener(this);
		mBtnCheckUpdate = (RelativeLayout)view.findViewById(R.id.btn_fragment_user_check_update);
		mBtnCheckUpdate.setOnClickListener(this);
		mBtnClearCache = (RelativeLayout)view.findViewById(R.id.btn_fragment_user_clear_cache);
		mBtnClearCache.setOnClickListener(this);
		mBtnSettings = (RelativeLayout)view.findViewById(R.id.btn_fragment_user_settings);
		mBtnSettings.setOnClickListener(this);
        return view;
    }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode)
		{
			case REQUEST_CODE_LOGIN:
//				if(resultCode==getActivity().RESULT_OK)
//				{
//					Bitmap bitmap = data.getParcelableExtra(LoginActivity.EXTRA_BITMAP_HEAD);
//					mHeadIcon.setImageBitmap(bitmap);
//				}
				break;
		}
	}

	@Override
	public void onClick(View v) {
		Resources r = getResources();
		switch (v.getId())
		{
			case R.id.btn_fragment_user_clear_cache:
				Toast.makeText(getActivity(),r.getString(R.string.clear_cache_result),Toast.LENGTH_SHORT).show();
				break;
			case R.id.btn_fragment_user_about:
				startActivity(new Intent(getActivity(), AboutActivity.class));
				break;
			case R.id.btn_fragment_user_change_wallpaper:
				startActivity(new Intent(getActivity(), AboutActivity.class));
				break;
			case R.id.btn_fragment_user_check_update:
				Toast.makeText(getActivity(),r.getString(R.string.check_update_result),Toast.LENGTH_SHORT).show();
				break;
			case R.id.btn_fragment_user_settings:
				startActivity(new Intent(getActivity(), AboutActivity.class));
				break;
			case R.id.user_avatar:
				Intent i = new Intent(getActivity(), LoginActivity.class);
				startActivityForResult(i,REQUEST_CODE_LOGIN);
				break;
		}
	}
}
