package com.example.taazakhabar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context;
    private List<Headline> headlines;
    public Adapter(Context context, List<Headline> headlines) {
        this.context=context;
        this.headlines=headlines;
    }

    @NonNull
    @NotNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter.ViewHolder holder, int position) {
        Headline cur=headlines.get(position);

        holder.Title.setText(cur.getTitle());
        if(cur.getDescription().equals("null"))
            holder.Description.setText("");
        else
            holder.Description.setText(cur.getDescription());
        holder.Time.setText(timeAgo(cur.getTime()));
        Glide.with(context).load(cur.getImageUrl()).into(holder.Image);

        holder.Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=cur.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });
        holder.Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=cur.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });
        holder.Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=cur.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }

    public String timeAgo(String t){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
        try {
            long time= sdf.parse(t).getTime();
            long now=System.currentTimeMillis();
            CharSequence ago= DateUtils.getRelativeTimeSpanString(time,now, DateUtils.MINUTE_IN_MILLIS);
            return ago+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Title, Description, Time;
        public ImageView Image;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.title_textView);
            Description=itemView.findViewById(R.id.description_textView);
            Time=itemView.findViewById(R.id.time_textView);
            Image=itemView.findViewById(R.id.imageView);
        }
    }


}
