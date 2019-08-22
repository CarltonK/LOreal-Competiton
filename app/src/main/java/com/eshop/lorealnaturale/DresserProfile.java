package com.eshop.lorealnaturale;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class DresserProfile extends Fragment {
    private ImageView ImgUser;
    private TextView TextName, TextEmail, TextPhone;
    private StorageReference storageReference;
    private FirebaseStorage storage;


    public DresserProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dresser_profile, container, false);

        TextName = view.findViewById(R.id.tvusername1);
        TextEmail = view.findViewById(R.id.tvuseremail1);
        TextPhone = view.findViewById(R.id.tvuserphone1);
        ImgUser = view.findViewById(R.id.imguser1);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://lorealglam-59020.appspot.com").child("images").child("pic.jpg");

        ImgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Dp.class));
            }
        });

        beginDownload();
        return view;
    }

    private void beginDownload() {
        try {
            final File file = File.createTempFile("images", "jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    ImgUser.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"No Profile Photo Uploaded",Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
