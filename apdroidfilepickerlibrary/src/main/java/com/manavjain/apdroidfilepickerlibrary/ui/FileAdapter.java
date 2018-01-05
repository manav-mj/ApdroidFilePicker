package com.manavjain.apdroidfilepickerlibrary.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manavjain.apdroidfilepickerlibrary.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by YourFather on 02-01-2018.
 */

class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    private Context mContext;
    private FileItemClickListener mFileItemClickListener;
    private ArrayList<File>  mFiles;
    FileAdapter(Context context, ArrayList<File> files){
        mFiles = files;
        mContext = context;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_file, parent, false);
        if (mFileItemClickListener == null)
            Log.e(getClass().getName(), "FileItemClickListener null exception");
        return new FileViewHolder(view, mFileItemClickListener::onItemClick);
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
        File file = getItem(position);
        holder.mNameTextView.setText(file.getName());
        if (file.isDirectory()){
            holder.mFileIconView.setImageResource(R.drawable.ic_folder_black_48dp);
            holder.mFileCountTextView.setText(file.listFiles().length + " files");
        }else {
            holder.mFileCountTextView.setText(Integer.parseInt(String.valueOf(file.length()/1024))+ " kb");
            holder.mFileIconView.setImageResource(R.drawable.ic_insert_drive_file_black_48dp);
        }
    }

    public File getItem(int position){
        return mFiles.get(position);
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public void setFileItemClickListener(FileItemClickListener fileItemClickListener){
        mFileItemClickListener = fileItemClickListener;
    }

    public class FileViewHolder extends RecyclerView.ViewHolder{

        public ImageView mFileIconView;
        public TextView mNameTextView;
        public TextView mFileCountTextView;

        FileItemClickListener listener;

        public FileViewHolder(View itemView, FileItemClickListener listener) {
            super(itemView);

            mFileIconView = itemView.findViewById(R.id.item_image);
            mNameTextView = itemView.findViewById(R.id.item_text_view);
            mFileCountTextView = itemView.findViewById(R.id.file_count_text_view);
            this.listener = listener;

            itemView.setOnClickListener(v->this.listener.onItemClick(v, getAdapterPosition()));
        }
    }

    public interface FileItemClickListener {
        void onItemClick(View view, int position);
    }
}
