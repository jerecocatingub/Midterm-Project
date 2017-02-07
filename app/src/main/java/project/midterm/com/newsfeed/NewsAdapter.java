package project.midterm.com.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;



public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> newsfeed) {
        super(context, 0, newsfeed);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.card_layout, parent, false);
        }

        News current = getItem(position);

        TextView author = (TextView) listItemView.findViewById(R.id.txtAuthor);
        TextView title = (TextView) listItemView.findViewById(R.id.txtTitle);
        TextView description = (TextView) listItemView.findViewById(R.id.txtDescription);
        TextView date = (TextView) listItemView.findViewById(R.id.txtDate);
        ImageView image = (ImageView) listItemView.findViewById(R.id.imageView);

        if(current.getAuthor()=="null"){
            author.setText("Unknown");
        } else {
            author.setText(current.getAuthor());
        }

        if(current.getPublished()=="null"){
            date.setText("Current");
        } else {
            date.setText(formatDate(current.getPublished()));
        }
        title.setText(current.getTitle());
        description.setText(current.getDescription());

        Picasso.with(getContext())
                .load(current.getImage())
                .into(image);

        return listItemView;
    }

    public String formatDate(String date){
        String dateString = date;
        String[] separated = dateString.split("T");
        String[] sepTime = separated[1].split(":");
        String[] ymd = separated[0].split("-");
        String ampm;

        switch (ymd[1]){
            case "01":
                ymd[1] = "January"; break;
            case "02":
                ymd[1] = "February"; break;
            case "03":
                ymd[1] = "March"; break;
            case "04":
                ymd[1] = "April"; break;
            case "05":
                ymd[1] = "May"; break;
            case "06":
                ymd[1] = "June"; break;
            case "07":
                ymd[1] = "July"; break;
            case "08":
                ymd[1] = "August"; break;
            case "09":
                ymd[1] = "September"; break;
            case "10":
                ymd[1] = "October"; break;
            case "11":
                ymd[1] = "November"; break;
            case "12":
                ymd[1] = "December"; break;
        }

        separated[0]=ymd[1]+" "+ymd[2]+", "+ymd[0];

        if(Integer.parseInt(sepTime[0])<=12){
            if(sepTime[0]=="00"){
                sepTime[0]="12";
            }
            ampm = "AM";
        }else{
            int a = Integer.parseInt(sepTime[0])-12;
            if (a<10){
                sepTime[0] = "0"+a;
            } else{
                sepTime[0] = a+"";
            }
            ampm = "PM";
        }

        dateString = separated[0]+"  "+sepTime[0]+":"+sepTime[1]+" "+ampm;
        return dateString;
    }
}
