package com.example.synup.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.Toolbar;

import com.example.synup.BuildConfig;
import com.example.synup.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.NORMAL;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "Base Activity";
    public LayoutInflater inflaterBase;
    public FrameLayout headerLayout;
    public ImageView ivleft;
    public TextView tvScreenName, tvLogout;
    public Typeface tfRegular, tfBold;
    private Dialog dialogLoader;
    private AlertDialog alertDialog;

    public static final boolean DEBUG = BuildConfig.DEBUG;

//    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

//    @BindView(R.id.llBase)
    protected LinearLayout llBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        inflaterBase = this.getLayoutInflater();
        llBase = (LinearLayout) findViewById(R.id.llBase);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        ButterKnife.bind(this);
    }

    public void showLoader(boolean isCancelable, String is_from) {
        try {
            runOnUiThread(new RunProgressDialog(isCancelable));
        } catch (Exception e) {
        }
    }

    /**
     * Shows Indefinite Progress Dialog.
     *
     * @param title
     * @param message
     * @param isCancelable
     */
    public void showLoader(final String title, final String message, boolean isCancelable, String is_from) {
        try {
            runOnUiThread(new RunProgressDialog(title, message, isCancelable));
        } catch (Exception e) {
        }
    }

    /**
     * Shows Indefinite Progress Dialog.
     *
     * @param title
     * @param message
     * @param progress
     * @param isCancelable
     */
    public void showLoader(final String title, final String message, final int progress, boolean isCancelable) {
        runOnUiThread(new RunProgressDialog(title, message, progress, isCancelable));
    }

    public void hideLoader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (dialogLoader != null && dialogLoader.isShowing())
                        dialogLoader.dismiss();

                    dialogLoader = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class RunProgressDialog implements Runnable {
        private String strTitle;// FarmName of the materialDialog
        private String strMessage;// Message to be shown in materialDialog
        private boolean isCancelable = false;
        private int progress;

        public RunProgressDialog(boolean isCancelable) {
            this.strTitle = null;
            this.strMessage = null;
            this.progress = 0;
            this.isCancelable = isCancelable;
        }

        public RunProgressDialog(String strTitle, String strMessage, boolean isCancelable) {
            this.strTitle = strTitle;
            this.strMessage = strMessage;
            this.progress = 0;
            this.isCancelable = isCancelable;
        }

        public RunProgressDialog(String strTitle, String strMessage, int progress, boolean isCancelable) {
            this.strTitle = strTitle;
            this.strMessage = strMessage;
            this.progress = progress;
            this.isCancelable = isCancelable;
        }

        @Override
        public void run() {
            try {
                boolean show = false;
                View view = LayoutInflater.from(BaseActivity.this).inflate(R.layout.custom_loader, null);

                if (dialogLoader == null) {
                    dialogLoader = new Dialog(BaseActivity.this);
                    dialogLoader.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogLoader.setContentView(view);
                    dialogLoader.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    show = true;
                } else if (dialogLoader != null && dialogLoader.isShowing()) {
                    dialogLoader.dismiss();
                    show = true;
                } else {
                    Log.i("showLoader", "none");
                }

                if (show) {
                    dialogLoader.setCancelable(isCancelable);

                    TextView tvLoadHead = dialogLoader.findViewById(R.id.tvLoadHead);
                    TextView tvLoadMsg = dialogLoader.findViewById(R.id.tvLoadMsg);

                    if (!TextUtils.isEmpty(strTitle))
                        tvLoadHead.setText(strTitle);
                    else
                        tvLoadHead.setText(getString(R.string.loading));

//                    TextView tvLoading = (TextView) dialogLoader.findViewById(R.id.tvLoading);
                    if (!TextUtils.isEmpty(strMessage)) {
                        tvLoadMsg.setText(strMessage);
                        tvLoadMsg.setVisibility(View.VISIBLE);
                    } else
                        tvLoadMsg.setVisibility(View.GONE);

                    tvLoadHead.setTypeface(tfBold, BOLD);
                    tvLoadMsg.setTypeface(tfRegular, NORMAL);
                    try {
                        dialogLoader.show();
                    }catch (Exception ex){
                        ex.printStackTrace();
                        finish();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
//                pbDialogLoader = null;
                dialogLoader = null;
                alertDialog = null;
            }
        }
    }

    /**
     * Shows Dialog with user defined buttons.
     *
     * @param title
     * @param message
     * @param okButton
     * @param noButton
     * @param from
     * @param isCancelable
     */
    public void showCustomDialog(final String title, final String message, final String okButton, final String noButton, final String from, boolean isCancelable, String is_from) {
        runOnUiThread(new RunShowDialog(title, message, okButton, noButton, from, isCancelable));
    }

    public void hideCustomDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (alertDialog != null && alertDialog.isShowing())
                    alertDialog.dismiss();
            }
        });
    }

    class RunShowDialog implements Runnable {
        private String strTitle;// FarmName of the materialDialog
        private String strMessage;// Message to be shown in materialDialog
        private String firstBtnName;
        private String secondBtnName;
        private String from;
        private boolean isCancelable = false;

        public RunShowDialog(String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, boolean isCancelable) {
            try {
                this.strTitle = strTitle;
                this.strMessage = strMessage;
                this.firstBtnName = firstBtnName;
                this.secondBtnName = secondBtnName;
                this.isCancelable = isCancelable;
                if (from != null)
                    this.from = from;
                else
                    this.from = "";
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            showCustomDialog();
        }

        private void showCustomDialog() {
            try {
                boolean show = false;

                if (alertDialog == null) {
                    alertDialog = new AlertDialog.Builder(BaseActivity.this).create();
                    show = setDialog();
                } else if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                    show = setDialog();
                } else
                    show = setDialog();
                if (show)
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.show();
                        }
                    });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private boolean setDialog() {
            try {
                alertDialog.setTitle(strTitle);
                alertDialog.setMessage(strMessage);
                alertDialog.setCanceledOnTouchOutside(isCancelable);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, firstBtnName,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialogYesClick(from);
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, secondBtnName,

                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialogNoClick(from);
                                dialog.dismiss();
                            }
                        });
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            } finally {
                return true;
            }
        }
    }

    /**
     * Single click selection
     */
    public static class SingleClickDialog extends AppCompatDialogFragment {
        public interface OnSelectedListener {
            void onSelect(int position);
        }

        OnSelectedListener mListener;

        public void setOnSelectedListener(OnSelectedListener listener) {
            mListener = listener;
        }

        public static SingleClickDialog newInstance(String title, ArrayList<String> data) {
            Bundle args = new Bundle();
            args.putSerializable("title", title);
            args.putStringArrayList("data", data);
            SingleClickDialog fragment = new SingleClickDialog();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            String title = getArguments().getString("title");
            ArrayList<String> data = getArguments().getStringArrayList("data");

            CharSequence[] charSequences = new CharSequence[data.size()];
            for (int i = 0; i < data.size(); i++) {
                charSequences[i] = data.get(i);//String.valueOf(i + 1);
            }

            return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setItems(charSequences, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mListener.onSelect(which);
                        }
                    })
                    .create();
        }
    }

    public void dialogYesClick(String from) {
    }

    public void dialogNoClick(String from) {
        if (from.equalsIgnoreCase("")) {

        }
    }
}
