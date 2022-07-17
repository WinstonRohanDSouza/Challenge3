package com.example.fhirapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriPermission;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.document.DocumentFileCompat;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddDocumentImage extends AppCompatActivity {

    EditText titleinput;
    EditText descriptioniput;
    Button savebutton, addDocument;//, addImage;
    ImageView image, image_preview;
    TextView add_image_text;
    byte[] byteArray, pdfByteArray;
    String pdfAddress,  docType;
    PDFView pdfView_preview;
    Uri uri2;
    String[] docTypeSplit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document_image);

        titleinput = findViewById(R.id.titleinput);
        descriptioniput = findViewById(R.id.descriptioninput);
        savebutton = findViewById(R.id.savebtn);
        //image_preview = findViewById(R.id.image_preview);
        addDocument = findViewById(R.id.add_image_button);

//        //RealM migration code
//        Realm.init(getApplicationContext());
//
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        int versionCode = sharedPreferences.getInt("VERSION_CODE", 0);
//        String versionName = sharedPreferences.getString("VERSION_NAME", "0");
//        int realmVersion = 0;
//        if(versionCode != BuildConfig.VERSION_CODE || versionName.compareToIgnoreCase(BuildConfig.VERSION_NAME) != 0) {
//            realmVersion++;
//        } else if (realmVersion < 0) {
//            realmVersion = 0;
//        }
//        sharedPreferences.edit()
//                .putInt("VERSION_CODE", BuildConfig.VERSION_CODE)
//                .putString("VERSION_NAME", BuildConfig.VERSION_NAME)
//                .apply();
//        RealmConfiguration config = new RealmConfiguration.Builder().name("FileNotes.realm")
//                .migration(new RealmMigrations())
//                .schemaVersion(realmVersion)
//                .build();
//
//        Realm.setDefaultConfiguration(config);
//
//        Realm realm = Realm.getInstance(config);
//
//
//        //Realm Migration code



        //Initialize Realm and get Instance
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();


        //click on Save Button
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        String title =titleinput.getText().toString();
                        String description = descriptioniput.getText().toString();
                        long createdTime = System.currentTimeMillis();

                        realm.beginTransaction();

                        FileNotes fileNotes =realm.createObject(FileNotes.class);

                        fileNotes.setTitle(title);
                        fileNotes.setDescription(description);
                        fileNotes.setCreatedtime(createdTime);
                        if (docType != null)
                        {
                            fileNotes.setDocType(docType);
                        }else
                        {
                            fileNotes.setDocType("NoFile");
                        }

                        //if(byteArray != null) { fileNotes.setByteArray(byteArray);}
                        if (pdfAddress != null) { fileNotes.setPdfAddress(pdfAddress); }


                        realm.commitTransaction();

                        Toast.makeText(AddDocumentImage.this, "Details Saved", Toast.LENGTH_SHORT).show();
                        finish();
            }
        });

        //Onclick for selecting files
        addDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(Intent.createChooser(intent,"Select a  File"),1);

            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,  @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            uri2 = data.getData() ;

            pdfAddress =  uri2.toString();

            ContentResolver cr = this.getContentResolver();
            docType = cr.getType(uri2);

            boolean weHaveDurablePermission=obtainDurablePermission(uri2);

            if (!weHaveDurablePermission)
            {
                uri2=makeLocalCopy(uri2);
            }

            if (weHaveDurablePermission || uri2!=null)
            {
                Log.d(getClass().getSimpleName(), uri2.toString());

                DocumentFileCompat docFile=buildDocFileForUri(uri2);

                Log.d(getClass().getSimpleName(),
                        "Display name: "+docFile.getName());
                Log.d(getClass().getSimpleName(),
                        "Size: "+Long.toString(docFile.length()));

                // EventBus.getDefault().post(new ContentReadyEvent(docFile));
            }

        }
        else {
            Toast.makeText(AddDocumentImage.this, "No document Selected", Toast.LENGTH_SHORT).show();
        }


    }

    private Uri makeLocalCopy(Uri uri2) {

        DocumentFileCompat docFile=buildDocFileForUri(uri2);
        Uri result=null;

        if (docFile.getName()!=null) {
            try {
                String ext=
                        MimeTypeMap.getSingleton().getExtensionFromMimeType(docFile.getType());

                if (ext!=null) {
                    ext="."+ext;
                }

                File f=File.createTempFile("cw_", ext, getFilesDir());

                docFile.copyTo(f);
                result=Uri.fromFile(f);
            }
            catch (Exception e) {
                Log.e(getClass().getSimpleName(),
                        "Exception copying content to file", e);
            }
        }

        return(result);


    }

    public DocumentFileCompat buildDocFileForUri(Uri uri2) {


        DocumentFileCompat docFile;

        if (uri2.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            docFile=DocumentFileCompat.fromSingleUri(this, uri2);
        }
        else {
            docFile=DocumentFileCompat.fromFile(new File(uri2.getPath()));
        }

        return(docFile);


    }

    public boolean obtainDurablePermission(Uri uri2) {

        boolean weHaveDurablePermission=false;

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            int perms=Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

            try {
                getContentResolver()
                        .takePersistableUriPermission(uri2, perms);

                for (UriPermission perm :
                        getContentResolver().getPersistedUriPermissions()) {
                    if (perm.getUri().equals(uri2)) {
                        weHaveDurablePermission=true;
                    }
                }
            }
            catch (SecurityException e) {
                // OK, we were not offered any persistable permissions
            }
        }

        return(weHaveDurablePermission);
    }


}