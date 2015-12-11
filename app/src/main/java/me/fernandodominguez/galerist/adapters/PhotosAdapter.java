package me.fernandodominguez.galerist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import me.fernandodominguez.galerist.R;
import me.fernandodominguez.pixels.models.Image;

public class PhotosAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private List<Image> itemList;
    private Context context;

    public PhotosAdapter(Context context, List<Image> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    public void insertImage(Image image){
        itemList.add(image);
        notifyItemInserted(itemList.size());
    }

    public void deleteAll(){
        itemList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public Image getImage(int position){
        return itemList.get(position);
    }

    public Context getContext() {
        return context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, null);
        ImageViewHolder rcv = new ImageViewHolder(layoutView, this);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Image image = itemList.get(position);
        final int MAX_LENGHT = 30;
        holder.setIsRecyclable(false);

        if (image.getName().length() > MAX_LENGHT) {
            holder.name.setText(image.getName().substring(0, MAX_LENGHT) + "...");
        } else {
            holder.name.setText(image.getName());
        }
        //holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
        Ion.with(holder.imageView)
                //.placeholder(R.drawable.download)
                .error(R.drawable.broken_link)
                .load(image.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}