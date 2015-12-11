package me.fernandodominguez.galerist.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.fernandodominguez.galerist.R;
import me.fernandodominguez.galerist.activities.ImageActivity;
import me.fernandodominguez.pixels.models.Image;

public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView name;
    public ImageView imageView;
    private PhotosAdapter adapter;

    public ImageViewHolder(View itemView, PhotosAdapter adapter) {
        super(itemView);
        itemView.setOnClickListener(this);
        name = (TextView) itemView.findViewById(R.id.country_name);
        imageView = (ImageView) itemView.findViewById(R.id.country_photo);
        this.adapter = adapter;

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        Image image = adapter.getImage(getAdapterPosition());
        Intent intent = new Intent(adapter.getContext(), ImageActivity.class);
        intent.putExtra("image", image);
        adapter.getContext().startActivity(intent);
    }
}
