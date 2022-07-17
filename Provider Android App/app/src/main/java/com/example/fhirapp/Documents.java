package com.example.fhirapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;


public class Documents extends AppCompatActivity implements DocumentsAdapter.OnImageClick {


    DrawerLayout drawerLayout;
    LinearLayout linearLayout;
    ImageView add_files;
    //byte[] byteArray, pdfBytrArray;
    String pdfAddress;
    Context context;
    RealmResults<FileNotes> notesList;
    DocumentsAdapter documentsAdapter;
    Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        initVar();
        add_files();
        get_details();


    }

    private void get_details() {

       Realm.init(getApplicationContext());
       Realm realm = Realm.getDefaultInstance();

//        //New code
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
//        //New code

        notesList = realm.where(FileNotes.class).findAll().sort("createdtime", Sort.DESCENDING);

        RecyclerView recyclerView = findViewById(R.id.recyclerviewlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         documentsAdapter = new DocumentsAdapter(getApplicationContext(), notesList, this);
         new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(documentsAdapter);



        notesList.addChangeListener(new RealmChangeListener<RealmResults<FileNotes>>() {
            @Override
            public void onChange(RealmResults<FileNotes> fileNotes) {
                documentsAdapter.notifyDataSetChanged();
            }
        });


    }

    private void initVar() {

        drawerLayout = findViewById(R.id.drawer_layout_documents);
        linearLayout = findViewById(R.id.main_toolbar_documents);
        add_files = (ImageView) linearLayout.findViewById(R.id.add_files);


    }

    private void add_files() {

        add_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Inside_Onclick", "Inside_Onclick");
                Intent intent = new Intent(Documents.this, AddDocumentImage.class);
                startActivity(intent);
            }
        });
    }


    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void clickMenu(View v) {
        openDrawer(drawerLayout);
    }

    public void clickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public void clickProfile(View v) {
        closeDrawer(drawerLayout);
    }

    public void clickLogout(View view) {
        logout(this);
    }

    public void logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Documents.this,
                        MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public void onClickEncounter(View view) {
        Intent intent = new Intent(Documents.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataE);
        intent.putExtra("class", "Encounter");
        startActivity(intent);
    }

    public void onClickDiagnosticReport(View view) {
        Intent intent = new Intent(Documents.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataDR);
        intent.putExtra("class", "DiagnosticReport");
        startActivity(intent);
    }

    public void onClickAllergies(View view) {
        Intent intent = new Intent(Documents.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataA);
        intent.putExtra("class", "Allergies");
        startActivity(intent);
    }

    public void onClickSlotDetails(View view) {
        Intent intent = new Intent(Documents.this, SlotActivity.class);
//        intent.putExtra("divTextEncounter", jsonDataApmnt);
//        intent.putExtra("class", "Appointment");
        startActivity(intent);


    }


    public void onClickMedications(View view) {
        Intent intent = new Intent(Documents.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataM);
        intent.putExtra("class", "Medications");
        startActivity(intent);
    }

    public void onClickUploadDocuments(View v) {
        closeDrawer(drawerLayout);
    }

    public void clickSetEmergencyMessage(View view) {
        Intent intent = new Intent(Documents.this, EmergencyMessage.class);
        startActivity(intent);
    }

    @Override
    public void displayFullScreenImage(RealmResults<FileNotes> notesList, String pdfAddress, int clickType, String docType, byte[] diagnosticreportByte) {

        Log.d("Inside_Activity_the3","displayFullScreenImage");
        Log.d("DocuType", docType);
//        Log.d("addresspdf",pdfAddress);
//        Log.d("diagnosticreportByte", String.valueOf(diagnosticreportByte));

       if (pdfAddress != null || diagnosticreportByte !=null ) {
           if (pdfAddress !=null){
                mUri = Uri.parse(pdfAddress);
           }


           // ContentResolver cr = this.getContentResolver();
            //String docType = cr.getType(mUri);
            Log.d("DocumentType",docType);


            if (( docType.contains("image") || docType.equals("application/pdf") || docType.equals("text/plain")) && clickType == 0 ) {
                Intent intent = new Intent(this, DocumentFullscreen.class);
                if (pdfAddress != null){
                    intent.putExtra("DocumentAddress", pdfAddress);
                }
                if (diagnosticreportByte != null){
                    intent.putExtra("diagnosticreportByte",diagnosticreportByte);
                }
                intent.putExtra("DocType", docType);

                startActivity(intent);

                //Will ask options to open in different app
                //Intent shareIntent = new Intent(Intent.ACTION_VIEW,mUri);
                //startActivity(shareIntent);

            }
            else if ( clickType == 0)
            {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_VIEW,mUri);
                    startActivity(shareIntent);
                    Log.e("Inside","try");

                } catch (Exception e) {
                    //Toast.makeText(Documents.this, "No app available", Toast.LENGTH_SHORT).show();
                    //Log.e("Inside","catch");
                    AlertDialog.Builder alert= new AlertDialog.Builder(Documents.this);
                    alert.setTitle("No app available");
                    alert.setMessage("Do you want to install the application?");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                             String appPackageName = "https://play.google.com/store/apps/";
                             if (docType.contains("wordprocessingml"))
                             {
                                 appPackageName = "https://play.google.com/store/apps/details?id=com.microsoft.office.word";
                             }else if (docType.contains("spreadsheetml"))
                             {
                                 appPackageName= "https://play.google.com/store/apps/details?id=com.microsoft.office.excel";
                             }
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Do nothing.
                        }
                    });
                    alert.create().show();

                    e.printStackTrace();
                }


            }
            else if ( clickType == 1) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, mUri);
                shareIntent.setType("*/*");
                startActivity(Intent.createChooser(shareIntent, null));


            }
//            else if (docType.contains("application/pdf") && clickType == 1) {
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_STREAM, mUri);
//                shareIntent.setType("application/pdf");
//                startActivity(Intent.createChooser(shareIntent, null));
//
//
//            }

            else {
                //Toast.makeText(Documents.this, "Not a Image or Pdf File", Toast.LENGTH_SHORT).show();
            }
            Log.e("URITYPE", docType);


        } else {
            Toast.makeText(Documents.this, "No documents selected", Toast.LENGTH_SHORT).show();
        }


    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            AlertDialog.Builder alert= new AlertDialog.Builder(Documents.this);
            alert.setTitle("Delete document");
            alert.setMessage("Do you want to delete the document?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    notesList.deleteFromRealm(viewHolder.getAdapterPosition());
                    realm.commitTransaction();
                    documentsAdapter.notifyDataSetChanged();




                }
            });

            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Do nothing
                    documentsAdapter.notifyDataSetChanged();

                }
            });
            alert.create().show();




        }
    };

    public void showAlertDialog() {




    }

}

//This is a test commit to test github through android studio