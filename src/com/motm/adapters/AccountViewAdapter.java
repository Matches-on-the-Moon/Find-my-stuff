package com.motm.adapters;

import java.util.List;
import com.motm.R;
import com.motm.models.Account;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class AccountViewAdapter extends ArrayAdapter<Account> {
 
    Context context;
 
    /**
     * @param context
     * @param resourceId
     * @param rowAccounts
     */
    public AccountViewAdapter(Context context, int resourceId,
            List<Account> accounts) {
        super(context, resourceId, accounts);
        this.context = context;
    }
 
    /**
     * @author Holly
     *
     */
    private class ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView emailView;
    }
 
    /* (non-Javadoc)
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Account account = getItem(position);
 
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.account_find_list_rows, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.loginName);
            holder.emailView = (TextView) convertView.findViewById(R.id.email);
            holder.imageView = (ImageView) convertView.findViewById(R.id.accountImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameView.setText(account.getLoginName()); 
        holder.emailView.setText(account.getEmail()); 
        holder.imageView.setImageResource(R.drawable.question_mark); //rowAccount.getImageId()
        return convertView;

    }
}