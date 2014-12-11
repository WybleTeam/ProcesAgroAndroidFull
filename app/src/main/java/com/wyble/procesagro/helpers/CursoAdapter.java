package com.wyble.procesagro.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wyble.procesagro.R;
import com.wyble.procesagro.models.CursoVirtual;

import java.util.ArrayList;

/**
 * Created by david on 12/8/14.
 */
public class CursoAdapter extends ArrayAdapter<CursoVirtual> {

    private ArrayList<CursoVirtual> mData = new ArrayList();
    private LayoutInflater mInflater;
    private int textViewResourceId;

    public CursoAdapter(Context context, int textViewResourceId, ArrayList<CursoVirtual> cursosvirtuales) {
        super(context, textViewResourceId, cursosvirtuales);
        this.textViewResourceId = textViewResourceId;
        this.mData.addAll(cursosvirtuales);
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
            holder.tituloTextView = (TextView) convertView.findViewById(R.id.textViewTitulocurso);
            holder.descTextView = (TextView) convertView.findViewById(R.id.textViewDesccurso);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        CursoVirtual curso = mData.get(position);

        holder.tituloTextView.setText(curso.getNombreCurso());
        holder.descTextView.setText(curso.getDescripcionCurso());
        return convertView;
    }
}
