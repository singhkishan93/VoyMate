package com.opiant.voymate.fragments;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opiant.voymate.R;
import com.opiant.voymate.adapters.CustomAdapter;
import com.opiant.voymate.utils.RecyclerViewClass;
import java.util.List;
import static android.content.Context.MODE_PRIVATE;


public class ExploreScreen extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView,recyclerView2,recyclerView3,recyclerView4,recyclerView5,recyclerView6;
    private CustomAdapter mAdapter;
    private CustomAdapter mAdapter1;
    private List<RecyclerViewClass> mItems;
    TextView Monuments,ReligiousPlace,Mixed,Market;
    ImageView monuments1,monuments2,monuments3,religious1,religious2,religious3,mixed1,mixed2,mixed3,market1,market2,market3;
    View view;
    private OnFragmentInteractionListener mListener;
    int id;
    String prefLanguage;

    public ExploreScreen() {
        // Required empty public constructor
    }

    public static ExploreScreen newInstance(String param1, String param2) {
        ExploreScreen fragment = new ExploreScreen();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_explore_screen, container, false);

        initViews();

        //Recyclerview
        /*recyclerView = (RecyclerView) view.findViewById(R.id.hrlist_recycler_view);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.Books_recycler_view);
        recyclerView3 = (RecyclerView) view.findViewById(R.id.literature_recycler_view);
        recyclerView4 = (RecyclerView) view.findViewById(R.id.exam_recycler_view);


        //Recyclerview orientation
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),  LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        Bitmap bitmap = getRoundedShape(BitmapFactory.decodeResource(getResources(),R.drawable.igate));
        Drawable d = new BitmapDrawable(getResources(), bitmap);
        mItems = new ArrayList<>();
        //adding test items to the list
        for(int i=0; i<5; i++){
            mItems.add(i, new RecyclerViewClass("", "", d));
        }
        mAdapter = new CustomAdapter(getContext(), mItems);
        recyclerView.setAdapter(mAdapter);

        Bitmap bitmap1 = getRoundedShape(BitmapFactory.decodeResource(getResources(),R.drawable.aksharsham));
        Drawable d1 = new BitmapDrawable(getResources(), bitmap1);
        mItems = new ArrayList<>();
        //adding test items to the list
        for(int i=0; i<5; i++){
            mItems.add(i, new RecyclerViewClass("", "", d1));
        }
        mAdapter1 = new CustomAdapter(getContext(), mItems);
        recyclerView2.setAdapter(mAdapter1);*/
        /*recyclerView3.setAdapter(mAdapter);
        recyclerView4.setAdapter(mAdapter);*/

        return view;
    }

    public void initViews(){

        Monuments = view.findViewById(R.id.magazines);
        ReligiousPlace = view.findViewById(R.id.textView2);
        Mixed = view.findViewById(R.id.textView3);
        Market = view.findViewById(R.id.textView4);
        monuments1 = view.findViewById(R.id.imageView2);
        monuments2 = view.findViewById(R.id.imageView3);
        monuments3 = view.findViewById(R.id.imageView5);
        religious1 = view.findViewById(R.id.imageView6);
        religious2 = view.findViewById(R.id.imageView7);
        religious3 = view.findViewById(R.id.imageView9);
        mixed1 = view.findViewById(R.id.imageView10);
        mixed2 = view.findViewById(R.id.imageView11);
        mixed3 = view.findViewById(R.id.imageView13);
        market1 = view.findViewById(R.id.imageView14);
        market2 = view.findViewById(R.id.imageView15);
        market3 = view.findViewById(R.id.imageView17);

        Monuments.setOnClickListener(this);
        ReligiousPlace.setOnClickListener(this);
        Mixed.setOnClickListener(this);
        Market.setOnClickListener(this);
        monuments1.setOnClickListener(this);
        monuments2.setOnClickListener(this);
        monuments3.setOnClickListener(this);
        religious1.setOnClickListener(this);
        religious2.setOnClickListener(this);
        religious3.setOnClickListener(this);
        mixed1.setOnClickListener(this);
        mixed2.setOnClickListener(this);
        mixed3.setOnClickListener(this);
        market1.setOnClickListener(this);
        market2.setOnClickListener(this);
        market3.setOnClickListener(this);

        Monuments.setText(R.string.emonument);
        ReligiousPlace.setText(R.string.ereligious);
        Mixed.setText(R.string.emixed);
        Market.setText(R.string.emarket);

        SharedPreferences IdShared = getActivity().getSharedPreferences("VoyMate", MODE_PRIVATE);

        if (IdShared!=null) {
            prefLanguage = IdShared.getString("language", "");

            if (prefLanguage.equals("hi")) {

                Monuments.setText(R.string.monument);
                ReligiousPlace.setText(R.string.religious);
                Mixed.setText(R.string.mixed);
                Market.setText(R.string.market);
            }
            else if (prefLanguage.equals("fr")){

                Monuments.setText(R.string.fmonument);
                ReligiousPlace.setText(R.string.freligious);
                Mixed.setText(R.string.fmixed);
                Market.setText(R.string.fmarket);
            }
            else if (prefLanguage.equals("ES")){

                Monuments.setText(R.string.smonument);
                ReligiousPlace.setText(R.string.sreligious);
                Mixed.setText(R.string.smixed);
                Market.setText(R.string.sshoping);
            }
        }

       /* Display mdisp = getActivity().getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        int maxX = mdispSize.x;
        int maxY = mdispSize.y;
        Toast.makeText(getContext(),"Co:"+maxX+" "+maxY,Toast.LENGTH_SHORT).show();*/
    }


    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        //setting up height and width of the profile image
        int targetWidth = (int)getResources().getDimension(R.dimen.profile_pic_size);
        int targetHeight = (int)getResources().getDimension(R.dimen.profile_pic_size);
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        canvas.drawBitmap(scaleBitmapImage,
                new Rect(0, 0, scaleBitmapImage.getWidth(),
                        scaleBitmapImage.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public  void reToDetails(int Id){

        Bundle b = new Bundle();
        b.putInt("ID",id);
        Fragment placedetailsScreen = new PlaceListScreen();
        placedetailsScreen.setArguments(b);
        FragmentTransaction placedetailsScreenTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        placedetailsScreenTransaction.replace(R.id.containerView1, placedetailsScreen);
        placedetailsScreenTransaction.addToBackStack(null);
        placedetailsScreenTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.magazines:
                id=1;
                reToDetails(id);
                break;
            case R.id.imageView2:
                id=1;
                reToDetails(id);
                break;
            case R.id.imageView3:
                id=1;
                reToDetails(id);
                break;
            case R.id.imageView5:
                id=1;
                reToDetails(id);
                break;
            case R.id.textView2:
                id=2;
                reToDetails(id);
                break;
            case R.id.imageView6:
                id=2;
                reToDetails(id);
                break;
            case R.id.imageView7:
                id=2;
                reToDetails(id);
                break;
            case R.id.imageView9:
                id=2;
                reToDetails(id);
                break;
            case R.id.textView3:
                id=3;
                reToDetails(id);
                break;
            case R.id.imageView10:
                id=3;
                reToDetails(id);
                break;
            case R.id.imageView11:
                id=3;
                reToDetails(id);
                break;
            case R.id.imageView13:
                id=3;
                reToDetails(id);
                break;
            case R.id.textView4:
                id=4;
                reToDetails(id);
                break;
            case R.id.imageView14:
                id=4;
                reToDetails(id);
                break;
            case R.id.imageView15:
                id=4;
                reToDetails(id);
                break;
            case R.id.imageView17:
                id=4;
                reToDetails(id);
                break;

        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
