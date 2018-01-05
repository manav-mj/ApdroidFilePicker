package com.manavjain.apdroidfilepickerlibrary.ui;

import android.app.Fragment;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manavjain.apdroidfilepickerlibrary.R;
import com.manavjain.apdroidfilepickerlibrary.utils.FilePickerUtils;

import java.io.File;

/**
 * Created by YourFather on 02-01-2018.
 */

public class FileFragment extends Fragment {

    private static final String FILE_PATH_KEY = "file_path";
    private RecyclerView mFolderRecyclerView;
    private FileAdapter mFileAdapter;

    private TextView mPathTextView;

    private FileClickListener mFileClickListener;

    private String mPath;

    public static FileFragment createInstance(String path, FileClickListener listener) {

        FileFragment instance = new FileFragment();

        Bundle args = new Bundle();
        args.putString(FILE_PATH_KEY, path);
        instance.setArguments(args);
        instance.setFileClickListener(listener);

        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        init();
    }

    private void init() {
        if (getArguments().getString(FILE_PATH_KEY) != null) {
            mPath = getArguments().getString(FILE_PATH_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder, container, false);
        mFolderRecyclerView = view.findViewById(R.id.folder_list_view);
        mPathTextView = view.findViewById(R.id.path_text_view);
        mPathTextView.setText(mPath);
        mPathTextView.setTypeface(FilePickerUtils.getProximaNoveTypeface(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFilesList();
    }

    private void initFilesList() {
        mFileAdapter = new FileAdapter(getActivity(),
                FilePickerUtils.getFileListByPath(mPath));

        mFileAdapter.setFileItemClickListener((view, position) -> mFileClickListener.onFileClick(mFileAdapter.getItem(position)));

        mFolderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFolderRecyclerView.setAdapter(mFileAdapter);
        mFolderRecyclerView.addItemDecoration(new FilePickerDecorator());
    }

    public void setFileClickListener(FileClickListener fileClickListener) {
        mFileClickListener = fileClickListener;
    }

    interface FileClickListener {
        void onFileClick(File clickedFile);
    }

    public class FilePickerDecorator extends RecyclerView.ItemDecoration{

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0,-1,0,-1);
        }
    }
}
