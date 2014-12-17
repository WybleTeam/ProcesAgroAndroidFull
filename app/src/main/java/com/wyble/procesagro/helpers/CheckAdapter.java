package com.wyble.procesagro.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wyble.procesagro.R;
import com.wyble.procesagro.StepsDetailsActivity;
import com.wyble.procesagro.models.PasoOferta;

import java.util.ArrayList;

/**
 * Created by david on 10/3/14.
 */
public class CheckAdapter extends ArrayAdapter<PasoOferta> {

    private DB db;
    private static final String PASOS_OFERTAS_TABLE = "pasos_ofertas";
    private Activity activity;
    private ArrayList<PasoOferta> mData = new ArrayList();
    private LayoutInflater mInflater;
    private Context context;

    public CheckAdapter(Context context, int textViewResourceId, ArrayList<PasoOferta> pasosOferta) {
        super(context, textViewResourceId, pasosOferta);
        this.context=context;
        this.mData.addAll(pasosOferta);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ArrayList tables = new ArrayList();
        db = new DB(context, tables);
    }

    public static class ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        final PasoOferta pasoOferta = mData.get(position);
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.check_list_item, null);

            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.checkTextItem);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxItem);

            holder.textView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                  //  CheckBox cb = (CheckBox) v ;
                   // PasoOferta pasoOferta = (PasoOferta) cb.getTag();

                    Intent intent = new Intent(context, StepsDetailsActivity.class);
                    intent.putExtra("PASO_OFERTA", pasoOferta);
                    context.startActivity(intent);//Toast.makeText(, " Button clicked", Toast.LENGTH_SHORT).show();
                    Log.d("Presion", "si presiono");
                }
            });

            convertView.setTag(holder);


            holder.checkBox.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    PasoOferta pasoOferta = (PasoOferta) cb.getTag();
                    pasoOferta.setIsChecked(cb.isChecked());

                    db.updateData(PASOS_OFERTAS_TABLE, pasoOferta.toJSONArray(), pasoOferta.getId());
                }
            });

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.textView.setText(pasoOferta.getTitulo());
        //holder.checkBox.setText(pasoOferta.getTitulo());
        holder.checkBox.setChecked(pasoOferta.getIsChecked());
        holder.checkBox.setTag(pasoOferta);
        holder.checkBox.setMovementMethod(new ScrollingMovementMethod());
        return convertView;

    }

}
