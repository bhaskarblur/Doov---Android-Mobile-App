package com.blurspace.doov;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.blurspace.doov.Adapters.homeCourseAdapter;
import com.blurspace.doov.Adapters.homeDreamAdapter;
import com.blurspace.doov.Adapters.homePlatformAdapter;
import com.blurspace.doov.Adapters.searchfieldAdapter;
import com.blurspace.doov.Adapters.searchresAdapter;
import com.blurspace.doov.Models.CourseModel;
import com.blurspace.doov.Models.DreamsModel;
import com.blurspace.doov.Models.PlatformModel;
import com.blurspace.doov.Models.Userauth;
import com.blurspace.doov.ViewModels.AdminPortalViewModel;
import com.blurspace.doov.ViewModels.HomeViewModel;
import com.blurspace.doov.customdialogues.nocondialog;
import com.blurspace.doov.customdialogues.notidialog;
import com.blurspace.doov.databinding.FragmentHomefragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class homefragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth mauth;
    private FragmentHomefragmentBinding hmbinding;
    private com.blurspace.doov.Adapters.searchresAdapter searchresAdapter;
    private com.blurspace.doov.ViewModels.HomeViewModel homeViewModel;
    private com.blurspace.doov.Adapters.homeDreamAdapter dreamAdapter;
    private com.blurspace.doov.Adapters.homePlatformAdapter platformAdapter;
    private com.blurspace.doov.Adapters.homeCourseAdapter courseAdapter;
    private String mParam1;
    private String mParam2;
    private String filterfield="";
    private  com.blurspace.doov.Adapters.searchfieldAdapter searchfieldAdapter;
    private MainArea mainArea;
    public homefragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static homefragment newInstance(String param1, String param2) {
        homefragment fragment = new homefragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mauth = FirebaseAuth.getInstance();
       homeViewModel= new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getActivity().getApplication())).get(HomeViewModel.class);
        homeViewModel.initwork(getContext());
        mainArea=new MainArea();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        hmbinding = FragmentHomefragmentBinding.inflate(inflater, container, false);
        String headline="Find your Dream and accomplish it!";
        SpannableString ss= new SpannableString(headline);
        ForegroundColorSpan yellowfs=new ForegroundColorSpan(Color.parseColor("#FFD107"));
        ss.setSpan(yellowfs,10,16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        hmbinding.headline.setText(ss);

        homeViewModel.getdreamModel().observe(getActivity(), new Observer<List<DreamsModel>>() {
            @Override
            public void onChanged(List<DreamsModel> dreamsModels) {
                if(homeViewModel.getdreamModel().getValue().size()>0) {

                    hmbinding.homedreamshimmer.setVisibility(View.VISIBLE);
                    List<DreamsModel> shimmeralerter= homeViewModel.getdreamModel().getValue();
                    if(shimmeralerter.get(shimmeralerter.size()-1).getDreamimgurl()!=null) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                hmbinding.homedreamshimmer.stopShimmer();
                                hmbinding.homedreamshimmer.hideShimmer();
                                hmbinding.randomdreams.setVisibility(View.VISIBLE);
                                hmbinding.homedreamshimmer.setVisibility(View.INVISIBLE);
                            }
                        },1000);

                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dreamAdapter.notifyDataSetChanged();
                    }
                },100);

            }
        });
        homeViewModel.getplatformModel().observe(getActivity(), new Observer<List<PlatformModel>>() {
            @Override
            public void onChanged(List<PlatformModel> dreamsModels) {
                if(homeViewModel.getplatformModel().getValue().size()>0) {

                    hmbinding.homeplatformshimmer.setVisibility(View.VISIBLE);
                    List<PlatformModel> shimmeralerter= homeViewModel.getplatformModel().getValue();
                    if(shimmeralerter.get(shimmeralerter.size()-1).getPlatformimgurl()!=null) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                hmbinding.homeplatformshimmer.stopShimmer();
                                hmbinding.homeplatformshimmer.hideShimmer();
                                hmbinding.randomplatforms.setVisibility(View.VISIBLE);
                                hmbinding.homeplatformshimmer.setVisibility(View.INVISIBLE);
                            }
                        },1000);

                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        platformAdapter.notifyDataSetChanged();
                    }
                },100);

            }
        });
        homeViewModel.getcourseModel().observe(getActivity(), new Observer<List<CourseModel>>() {
            @Override
            public void onChanged(List<CourseModel> courseModels) {
                if(homeViewModel.getcourseModel().getValue().size()>0) {

                    hmbinding.randomcourseshimmer.setVisibility(View.VISIBLE);
                    List<CourseModel> shimmeralerter= homeViewModel.getcourseModel().getValue();
                    if(shimmeralerter.get(shimmeralerter.size()-1).getCourseimgurl()!=null) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                hmbinding.randomcourseshimmer.stopShimmer();
                                hmbinding.randomcourseshimmer.hideShimmer();
                                hmbinding.randomcourse.setVisibility(View.VISIBLE);
                                hmbinding.randomcourseshimmer.setVisibility(View.INVISIBLE);
                            }
                        },1000);

                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                       courseAdapter.notifyDataSetChanged();
                    }
                },100);

            }
        });
        homeViewModel.getsearchfieldModel().observe(getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchfieldAdapter.notifyDataSetChanged();
                    }
                },100);

            }
        });

        homeViewModel.getsearchresModel().observe(getActivity(), new Observer<List<DreamsModel>>() {
            @Override
            public void onChanged(List<DreamsModel> dreamsModels) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchresAdapter.notifyDataSetChanged();
                    }
                },100);

            }
        });
        viewfunction();
        loadrandomDreams();
        loadrandomCourses();
        loadrandomPlatforms();
        searchlayFunctions();
        getauth();


        return hmbinding.getRoot();
    }

    private void loadrandomCourses() {
        courseAdapter=new homeCourseAdapter(getContext(),homeViewModel.getcourseModel().getValue());
        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        llm.setOrientation(RecyclerView.HORIZONTAL);
        hmbinding.randomcourse.setLayoutManager(llm);
        hmbinding.randomcourse.setAdapter(courseAdapter);
    }

    private void loadrandomPlatforms() {
        platformAdapter=new homePlatformAdapter(getContext(),homeViewModel.getplatformModel().getValue());
        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        llm.setOrientation(RecyclerView.HORIZONTAL);
        hmbinding.randomplatforms.setLayoutManager(llm);
        hmbinding.randomplatforms.setAdapter(platformAdapter);
    }

    private void searchlayFunctions() {
        loadfieldfilterrec();
        loadsearchresrec();
        hmbinding.applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(filterfield!=null && filterfield!="null" && filterfield!="" &&
                !filterfield.equals("All")) {

                    hmbinding.filteron.setVisibility(View.VISIBLE);
                    hmbinding.filterlay.setVisibility(View.GONE);
                    // send the filter to ViewModel
                }
                else {

                    hmbinding.filteron.setVisibility(View.GONE);
                    hmbinding.filterlay.setVisibility(View.GONE);
                }

            }
        });
    }



    private void searchfun(String query) {
        List<DreamsModel> searchedList=new ArrayList<>();
        for(DreamsModel model:homeViewModel.getsearchresModel().getValue()){

            if(filterfield!=null && filterfield!="null" && filterfield!="" && !filterfield.equals("All")) {
                if(model.getDreamname().toLowerCase().contains(query.toLowerCase()) &&
                        model.getDreamfield().contains(filterfield)) {

                    searchedList.add(model);
                }
            }
             else{
                if(model.getDreamname().toLowerCase().contains(query.toLowerCase())) {

                    searchedList.add(model);
                }
            }


        }
        searchresAdapter.searchList(searchedList);
    }
    private void loadsearchresrec() {
      searchresAdapter=new searchresAdapter(getContext(), homeViewModel.getsearchresModel().getValue());
        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        llm.setOrientation(RecyclerView.VERTICAL);
        hmbinding.searchresultrec.setLayoutManager(llm);
        hmbinding.searchresultrec.setAdapter(searchresAdapter);


    }

    private void loadfieldfilterrec() {
        searchfieldAdapter=new searchfieldAdapter(getContext(),homeViewModel.getsearchfieldModel().getValue());
        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        llm.setOrientation(RecyclerView.HORIZONTAL);
        hmbinding.fieldrec.setLayoutManager(llm);
        hmbinding.fieldrec.setAdapter(searchfieldAdapter);
        searchfieldAdapter.setoncardclicklistener(new searchfieldAdapter.oncardclicklistener() {
            @Override
            public void oncardclick(String catname) {
                filterfield=catname;

            }
        });
    }

    private void viewfunction() {
        hmbinding.searchlayout.setVisibility(View.INVISIBLE);

        hmbinding.searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (hmbinding.searchlayout.getVisibility() == View.INVISIBLE) {
                    hmbinding.nestedScrollView2.setSmoothScrollingEnabled(true);
                    hmbinding.nestedScrollView2.scrollTo(0,0);
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_downalt);
                    hmbinding.searchlayout.setAnimation(animation);
                    hmbinding.searchlayout.setVisibility(View.VISIBLE);
                    hmbinding.nestedScrollView2.setNestedScrollingEnabled(false);

                    hmbinding.nestedScrollView2.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                }
                hmbinding.searchresultrec.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hmbinding.searchresshimmer.setVisibility(View.VISIBLE);
                hmbinding.notfound.setVisibility(View.INVISIBLE);
                hmbinding.searchresultrec.setVisibility(View.INVISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchfun(s.toString());
                if(s.toString().equals(null) || s.toString().equals("")) {
                    hmbinding.notfound.setVisibility(View.INVISIBLE);
                    hmbinding.searchresultrec.setVisibility(View.INVISIBLE);
                    hmbinding.searchresshimmer.setVisibility(View.INVISIBLE);
                }
                else if(searchresAdapter.getItemCount()==0) {
                    hmbinding.searchresshimmer.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hmbinding.searchresshimmer.setVisibility(View.INVISIBLE);
                            hmbinding.notfound.setVisibility(View.VISIBLE);
                        }
                    },1000);

                }
                else {
                    hmbinding.searchresshimmer.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hmbinding.searchresshimmer.setVisibility(View.INVISIBLE);
                            hmbinding.notfound.setVisibility(View.INVISIBLE);
                            hmbinding.searchresultrec.setVisibility(View.VISIBLE);
                        }
                    },1000);

                }
            }
        });
        hmbinding.searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hmbinding.searchlayout.getVisibility() == View.INVISIBLE) {
                    hmbinding.nestedScrollView2.scrollTo(0,0);
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_downalt);
                    hmbinding.searchlayout.setAnimation(animation);
                    hmbinding.filterlay.setVisibility(View.GONE);
                    hmbinding.searchlayout.setVisibility(View.VISIBLE);
                    hmbinding.nestedScrollView2.setNestedScrollingEnabled(false);
                    hmbinding.nestedScrollView2.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                }
            }
        });
        hmbinding.filterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hmbinding.searchlayout.getVisibility() == View.INVISIBLE) {
                    hmbinding.nestedScrollView2.setSmoothScrollingEnabled(true);
                    hmbinding.nestedScrollView2.scrollTo(0,0);
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_downalt);
                    hmbinding.searchlayout.setAnimation(animation);
                    hmbinding.searchlayout.setVisibility(View.VISIBLE);
                    hmbinding.nestedScrollView2.setNestedScrollingEnabled(false);
                    hmbinding.nestedScrollView2.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });

                }
                if(hmbinding.filterlay.getVisibility()==View.VISIBLE){
                    hmbinding.filterlay.setVisibility(View.GONE);
                }
                else if(hmbinding.filterlay.getVisibility()==View.GONE){
                    hmbinding.filterlay.setVisibility(View.VISIBLE);
                }

            }
        });


        hmbinding.userNotibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.blurspace.doov.customdialogues.notidialog nocondialog= new notidialog();
                nocondialog.setEnterTransition(R.anim.slide_in_right);
                nocondialog.show(getActivity().getSupportFragmentManager(),"notidialog");
            }
        });

        hmbinding.nestedScrollView2.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY>700) {
                    hmbinding.searchtopbtn.setVisibility(View.VISIBLE);

                }
                else{
                    hmbinding.searchtopbtn.setVisibility(View.INVISIBLE);

                }
            }
        });
        hmbinding.searchtopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hmbinding.searchlayout.getVisibility() == View.INVISIBLE) {
                    hmbinding.nestedScrollView2.setSmoothScrollingEnabled(true);
                    hmbinding.nestedScrollView2.scrollTo(0,0);
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_downalt);
                    hmbinding.searchlayout.setAnimation(animation);
                    hmbinding.filterlay.setVisibility(View.GONE);
                    hmbinding.searchlayout.setVisibility(View.VISIBLE);
                    hmbinding.nestedScrollView2.setNestedScrollingEnabled(false);
                    hmbinding.nestedScrollView2.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                }
            }
        });
        hmbinding.searchcloser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_down);
                hmbinding.searchlayout.setAnimation(animation);
                hmbinding.searchlayout.setVisibility(View.INVISIBLE);
                hmbinding.filteron.setVisibility(View.INVISIBLE);
                hmbinding.filterlay.setVisibility(View.GONE);
                hmbinding.searchresshimmer.setVisibility(View.INVISIBLE);
                hmbinding.nestedScrollView2.setNestedScrollingEnabled(true);
                hmbinding.nestedScrollView2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
            }
        });




        }

    private void loadrandomDreams() {

        dreamAdapter=new homeDreamAdapter(getContext(), homeViewModel.getdreamModel().getValue());
        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        llm.setOrientation(RecyclerView.HORIZONTAL);
        hmbinding.randomdreams.setLayoutManager(llm);
        hmbinding.randomdreams.setAdapter(dreamAdapter);
    }

    private void getauth() {
        if (mauth == null) {
            startActivity(new Intent(getActivity(), Loginact.class));
            getActivity().finish();
        }

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("Users");
        mref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Userauth userauth = snapshot.getValue(Userauth.class);
                hmbinding.usernamehome.setText("Hello " + userauth.getUsername().toString() +"!");

                if (hmbinding.usernamehome.getText().length() > 12) {
                    hmbinding.usernamehome.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    hmbinding.usernamehome.setSelected(true);
                    hmbinding.usernamehome.setSingleLine(true);
                }

                if (userauth.getAvatar().toString().equals("yellowmale")) {
                    Picasso.get().load(R.drawable.yellowmale_dp).into(hmbinding.useravatarhome);
                } else if (userauth.getAvatar().toString().equals("blackmale")) {
                    Picasso.get().load(R.drawable.blackmale_dp).into(hmbinding.useravatarhome);
                } else if (userauth.getAvatar().toString().equals("yellowfemale")) {
                    Picasso.get().load(R.drawable.yellowfemale_dp).into(hmbinding.useravatarhome);
                } else if (userauth.getAvatar().toString().equals("blackfemale")) {
                    Picasso.get().load(R.drawable.blackfemale_dp).into(hmbinding.useravatarhome);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
  }