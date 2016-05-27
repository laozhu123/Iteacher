package com.li.zjut.iteacher.widget.curimlum;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.widget.mylesson.view.FromToPicker;
import com.li.zjut.iteacher.widget.mylesson.view.NumberPickerEx;


public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        public static final int TIMESHOW = 0, WEEKSHOW = 1;

        private DialogInterface.OnClickListener negativeButtonClickListener;

        private OnClickListener mOnClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }


        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {

            this.positiveButtonText = positiveButtonText;
            this.mOnClickListener = listener;
            return this;
        }


        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /*
        * 重构了该方法，是设置周数的dialog和节数的dialog能够使用同一个
        * */
        public CustomDialog create(final int array, final int type, int from, int to, int time) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_normal_layout, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            final NumberPickerEx npWeekTime = (NumberPickerEx) layout.findViewById(R.id.npWeekTime);
            npWeekTime.setDisplayedValues(context.getResources().getStringArray(array));
            npWeekTime.setValue(time - 1);
            final FromToPicker npWeek = (FromToPicker) layout.findViewById(R.id.npWeek);
            final FromToPicker npTime = (FromToPicker) layout.findViewById(R.id.npTime);
            if (type == WEEKSHOW) {
                npWeek.setFromVal(from);
                npWeek.setToVal(to);
                npWeek.setVisibility(View.VISIBLE);
            } else {
                npTime.setFromVal(from);
                npTime.setToVal(to);
                npTime.setVisibility(View.VISIBLE);
            }

            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (mOnClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
//                                    positiveButtonClickListener.onClick(dialog,
//                                            DialogInterface.BUTTON_POSITIVE);
//                                    mOnClickListener.onClick(dialog,);
                                    if (type == WEEKSHOW) {
                                        if (npWeek.getFromVal() > npWeek.getToVal()) {
                                            Toast.makeText(context, "周数设置不对", Toast.LENGTH_SHORT).show();
                                        } else {
                                            mOnClickListener.onClick(dialog, npWeek.getFromVal() + "-" + npWeek.getToVal()
                                                    , context.getResources().getStringArray(array)[npWeekTime.getValue()]);
                                        }
                                    } else {
                                        if (npTime.getFromVal() > npTime.getToVal()) {
                                            Toast.makeText(context, "节数设置不对", Toast.LENGTH_SHORT).show();
                                        } else {
                                            mOnClickListener.onClick(dialog, npTime.getFromVal() + "-" + npTime.getToVal()
                                                    , context.getResources().getStringArray(array)[npWeekTime.getValue()]);
                                        }
                                    }

                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }

    public interface OnClickListener {
        public void onClick(DialogInterface dialog, String fromToWeek, String singledouble);
    }
}


