package com.blurspace.doov.Repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blurspace.doov.Adapters.AdminDreamAdapter;
import com.blurspace.doov.AdminPortal;
import com.blurspace.doov.Models.CourseModel;
import com.blurspace.doov.Models.DreamsModel;
import com.blurspace.doov.Models.PlatformModel;
import com.blurspace.doov.R;
import com.blurspace.doov.ViewModels.AdminPortalViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.platform.Platform;

public class AdminPortalRepository {

    private AdminPortalRepository instance;
    private ArrayList<DreamsModel> dreamlist= new ArrayList<>();
    private ArrayList<PlatformModel> platformlist= new ArrayList<>();
    private ArrayList<CourseModel> courselist= new ArrayList<>();
    private MutableLiveData<List<DreamsModel>> dreamdata= new MutableLiveData<>();
    private MutableLiveData<List<PlatformModel>> platformdata= new MutableLiveData<>();
    private MutableLiveData<List<CourseModel>> coursedata= new MutableLiveData<>();
    private DatabaseReference mdbref;
    private DatabaseReference mdb;
    private StorageReference mst;
    private Context mcontext;


    public AdminPortalRepository(Context mcontext) {
        this.mcontext=mcontext;
        mdb=FirebaseDatabase.getInstance().getReference("AppData");
        mdbref=FirebaseDatabase.getInstance().getReference("AppData");
        mst=FirebaseStorage.getInstance().getReference("AppData");
    }
    public AdminPortalRepository getInstance() {

        if(instance==null) {
            instance=new AdminPortalRepository(mcontext);

        }
        return instance;
    }


    public MutableLiveData<List<DreamsModel>> returnDreamData() {
        getDreamDatafromSource();
        if(dreamlist==null) {
            dreamdata.setValue(null);
        }
        dreamdata.setValue(dreamlist);
        return dreamdata;
    }
    public MutableLiveData<List<PlatformModel>> returnPlatformData() {
        getPlatformDatafromSource();
        if(platformlist==null) {
            platformdata.setValue(null);
        }
        platformdata.setValue(platformlist);
        return platformdata;
    }
    public MutableLiveData<List<CourseModel>> returnCourseData() {
        getCourseDatafromSource();
        if(coursedata==null) {
            coursedata.setValue(null);
        }
        coursedata.setValue(courselist);
        return coursedata;
    }

    private void getDreamDatafromSource() {
        mdbref= FirebaseDatabase.getInstance().getReference("AppData");
        Query query=mdbref.child("DreamList") ;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    for (DataSnapshot subsnapshot:snapshot1.getChildren()) {
                        dreamlist.add(subsnapshot.getValue(DreamsModel.class));
                    }
                    dreamdata.postValue(dreamlist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getPlatformDatafromSource() {
        mdbref= FirebaseDatabase.getInstance().getReference("AppData");
        Query query=mdbref.child("PlatformList") ;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    for (DataSnapshot subsnapshot:snapshot1.getChildren()) {
                        platformlist.add(subsnapshot.getValue(PlatformModel.class));
                    }
                    platformdata.postValue(platformlist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCourseDatafromSource() {
        mdbref= FirebaseDatabase.getInstance().getReference("AppData");
        Query query=mdbref.child("CourseList") ;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    for (DataSnapshot subsnapshot:snapshot1.getChildren()) {
                        courselist.add(subsnapshot.getValue(CourseModel.class));
                    }
                    coursedata.postValue(courselist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private String getFileExtension(Uri uri){
        ContentResolver cr=mcontext.getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    public void AddDreamtoDB(Uri imgurl,String uri,String name,String field,Integer pos) {
        //imgurl is the url of the url which is picked from gallery!
        List<DreamsModel> dreamsModels=dreamdata.getValue();
        StorageReference drref=mst.child("DreamList").child(field).child(name+"."+getFileExtension(imgurl));

        if(pos==-1) {
            drref.putFile(imgurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    drref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            Uri uri= Uri.parse(task.getResult().toString());
                            mdb.child("DreamList").child(field).child(mdb.push().getKey()).setValue(new DreamsModel(uri.toString(),name,field));
                            Toast.makeText(mcontext.getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                            mcontext.startActivity(new Intent(mcontext, AdminPortal.class));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Error:",e.getMessage().toString());
                }
            });
        }

        else if(dreamdata.getValue().toString().contains(name) && pos==-1){
            Toast.makeText(mcontext, "Already Added!", Toast.LENGTH_SHORT).show();

        }
        else if(pos!=-1) {
            mdb.child("DreamList").child(field).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot mds : snapshot.getChildren()) {
                        if (mds.getValue().toString().contains(dreamsModels.get(pos).getDreamname()) && mds.getValue().toString().contains(dreamsModels.get(pos).getDreamfield())) {


                            StorageReference delref = FirebaseStorage.getInstance().getReferenceFromUrl(dreamsModels.get(pos).getDreamimgurl());
                            delref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    drref.putFile(imgurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            drref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {

                                                    Uri uri = Uri.parse(task.getResult().toString());
                                                    mdb.child("DreamList").child(field).child(mds.getKey().toString()).setValue(new DreamsModel(uri.toString(), name, field));
                                                    Toast.makeText(mcontext.getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                                    mcontext.startActivity(new Intent(mcontext, AdminPortal.class));
                                                    return;
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("Error:", e.getMessage().toString());
                                        }
                                    });
                                }
                            });

                        } else {
                            drref.putFile(imgurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    drref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {

                                            Uri uri = Uri.parse(task.getResult().toString());
                                            mdb.child("DreamList").child(field).child(mdb.push().getKey()).setValue(new DreamsModel(uri.toString(), name, field));
                                            Toast.makeText(mcontext.getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                                            mcontext.startActivity(new Intent(mcontext, AdminPortal.class));
                                            return;
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Error:", e.getMessage().toString());
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    public void RemoveDreamFromDB(String imgurl1,String name1,String field1) {
        final String[] imgurl = {imgurl1};
        final String[] name = {name1};
        final String[] field = {field1};
        StorageReference delref=FirebaseStorage.getInstance().getReferenceFromUrl(imgurl[0]);

        delref.delete().addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mcontext.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference deldbref= mdb.child("DreamList").child(field[0]);
        mdb.child("DreamList").child(field[0]).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    if(dataSnapshot.getValue().toString().contains("dreamname="+ name[0]) && dataSnapshot.getValue().toString().contains("dreamfield="+ field[0])) {
                        String keyname=dataSnapshot.getKey().toString();
                        deldbref.child(keyname).removeValue();
                        Toast.makeText(mcontext.getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();

                        imgurl[0] =null;
                        name[0] =null;
                        field[0] =null;

                        mcontext.startActivity(new Intent(mcontext,AdminPortal.class));
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void AddPlatformtoDB(Uri imgurl,String uri,String name,String field,Integer pos,String fordreams,String link) {
        //imgurl is the url of the url which is picked from gallery!
        List<PlatformModel> platformsModels=platformdata.getValue();
        StorageReference drref=mst.child("PlatformList").child(field).child(name+"."+getFileExtension(imgurl));

        if(pos==-1) {
            drref.putFile(imgurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    drref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            Uri uri= Uri.parse(task.getResult().toString());
                            mdb.child("PlatformList").child(field).child(mdb.push().getKey()).setValue(new PlatformModel(uri.toString(),name,field,fordreams,link));
                            Toast.makeText(mcontext.getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                            mcontext.startActivity(new Intent(mcontext,AdminPortal.class));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Error:",e.getMessage().toString());
                }
            });
        }

        else if(platformdata.getValue().toString().contains(name) && pos==-1){
            Toast.makeText(mcontext, "Already Added!", Toast.LENGTH_SHORT).show();

        }
        else if(pos!=-1) {
            mdb.child("PlatformList").child(field).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot mds : snapshot.getChildren()) {
                        if (mds.getValue().toString().contains(platformsModels.get(pos).getPlatformname()) && mds.getValue().toString().contains(platformsModels.get(pos).getPlatformfield())) {


                            StorageReference delref = FirebaseStorage.getInstance().getReferenceFromUrl(platformsModels.get(pos).getPlatformimgurl());
                            delref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    drref.putFile(imgurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            drref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {

                                                    Uri uri = Uri.parse(task.getResult().toString());
                                                    mdb.child("PlatformList").child(field).child(mds.getKey().toString()).setValue(new PlatformModel(uri.toString(),name,field,fordreams,link));
                                                    Toast.makeText(mcontext.getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                                    mcontext.startActivity(new Intent(mcontext, AdminPortal.class));
                                                    return;
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("Error:", e.getMessage().toString());
                                        }
                                    });
                                }
                            });

                        } else {
                            drref.putFile(imgurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    drref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {

                                            Uri uri = Uri.parse(task.getResult().toString());
                                            mdb.child("PlatformList").child(field).child(mdb.push().getKey()).setValue(new PlatformModel(uri.toString(),name,field,fordreams,link));
                                            Toast.makeText(mcontext.getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                                            mcontext.startActivity(new Intent(mcontext, AdminPortal.class));
                                            return;
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Error:", e.getMessage().toString());
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    public void RemovePlatformFromDB(String imgurl1,String name1,String field1) {
        final String[] imgurl = {imgurl1};
        final String[] name = {name1};
        final String[] field = {field1};
        StorageReference delref=FirebaseStorage.getInstance().getReferenceFromUrl(imgurl[0]);

        delref.delete().addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mcontext.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference deldbref= mdb.child("PlatformList").child(String.valueOf(field[0]));
        mdb.child("PlatformList").child(String.valueOf(field[0])).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    if(dataSnapshot.getValue().toString().contains("platformname="+ name[0]) && dataSnapshot.getValue().toString().contains("platformfield="+ field[0])) {
                        String keyname=dataSnapshot.getKey().toString();
                        deldbref.child(keyname).removeValue();
                        Toast.makeText(mcontext.getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();

                        imgurl[0] =null;
                        name[0] =null;
                        field[0] =null;
                        mcontext.startActivity(new Intent(mcontext,AdminPortal.class));
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void AddCoursetoDB(Uri imgurl,String uri,String name,String field,Integer pos, String fordreams,String link) {
        //imgurl is the url of the url which is picked from gallery!
        List<CourseModel> coursesModels=coursedata.getValue();
        StorageReference drref=mst.child("CourseList").child(field).child(name+"."+getFileExtension(imgurl));

        if(pos==-1) {
            drref.putFile(imgurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    drref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            Uri uri= Uri.parse(task.getResult().toString());
                            mdb.child("CourseList").child(field).child(mdb.push().getKey()).setValue(new CourseModel(uri.toString(),name,field,fordreams,link));
                            Toast.makeText(mcontext.getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                            mcontext.startActivity(new Intent(mcontext,AdminPortal.class));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Error:",e.getMessage().toString());
                }
            });
        }

        else if(coursedata.getValue().toString().contains(name) && pos==-1){
            Toast.makeText(mcontext, "Already Added!", Toast.LENGTH_SHORT).show();

        }
        else if(pos!=-1) {
            mdb.child("CourseList").child(field).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot mds : snapshot.getChildren()) {
                        if (mds.getValue().toString().contains(coursesModels.get(pos).getCoursename()) && mds.getValue().toString().contains(coursesModels.get(pos).getCoursefield())) {


                            StorageReference delref = FirebaseStorage.getInstance().getReferenceFromUrl(coursesModels.get(pos).getCourseimgurl());
                            delref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    drref.putFile(imgurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            drref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {

                                                    Uri uri = Uri.parse(task.getResult().toString());
                                                    mdb.child("CourseList").child(field).child(mds.getKey().toString()).setValue(new CourseModel(uri.toString(),name,field,fordreams,link));
                                                    Toast.makeText(mcontext.getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                                    mcontext.startActivity(new Intent(mcontext, AdminPortal.class));
                                                    return;
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("Error:", e.getMessage().toString());
                                        }
                                    });
                                }
                            });

                        } else {
                            drref.putFile(imgurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    drref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {

                                            Uri uri = Uri.parse(task.getResult().toString());
                                            mdb.child("CourseList").child(field).child(mdb.push().getKey()).setValue(new CourseModel(uri.toString(),name,field,fordreams,link));
                                            Toast.makeText(mcontext.getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                                            mcontext.startActivity(new Intent(mcontext, AdminPortal.class));
                                            return;
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Error:", e.getMessage().toString());
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    public void RemoveCourseFromDB(String imgurl1,String name1,String field1) {
        final String[] imgurl = {imgurl1};
        final String[] name = {name1};
        final String[] field = {field1};
        StorageReference delref=FirebaseStorage.getInstance().getReferenceFromUrl(imgurl[0]);

        delref.delete().addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mcontext.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference deldbref= mdb.child("CourseList").child(field[0]);
        mdb.child("CourseList").child(field[0]).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    if(dataSnapshot.getValue().toString().contains("coursename="+ name[0]) && dataSnapshot.getValue().toString().contains("coursefield="+ field[0])) {
                        String keyname=dataSnapshot.getKey().toString();
                        deldbref.child(keyname).removeValue();
                        Toast.makeText(mcontext.getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();

                        imgurl[0] =null;
                        name[0] =null;
                        field[0] =null;
                        mcontext.startActivity(new Intent(mcontext,AdminPortal.class));
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
