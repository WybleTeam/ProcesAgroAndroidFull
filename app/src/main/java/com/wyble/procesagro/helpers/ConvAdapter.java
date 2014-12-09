package com.wyble.procesagro.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wyble.procesagro.R;
import com.wyble.procesagro.models.Convocatoria;

import java.util.ArrayList;

/**
 * Created by david on 12/8/14.
 */
public class ConvAdapter extends ArrayAdapter<Convocatoria> {

    private ArrayList<Convocatoria> mData = new ArrayList();
    private LayoutInflater mInflater;
    private int textViewResourceId;

    public ConvAdapter(Context context, int textViewResourceId, ArrayList<Convocatoria> convocatorias) {
        super(context, textViewResourceId, convocatorias);
        this.textViewResourceId = textViewResourceId;
        this.mData.addAll(convocatorias);
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder {
        public TextView tituloTextView;
        public TextView descTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {

            convertView = mInflater.inflate(textViewResourceId, null);

            holder = new ViewHolder();
            holder.tituloTextView = (TextView) convertView.findViewById(R.id.textViewTituloConv);
            holder.descTextView = (TextView) convertView.findViewById(R.id.textViewDescConv);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Convocatoria conv = mData.get(position);

        holder.tituloTextView.setText(conv.getTitulo());
        holder.descTextView.setText(conv.getDescripcionCorta());
        return convertView;
    }
}
