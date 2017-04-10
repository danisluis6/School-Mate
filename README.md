# School-Mate
School Mate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_fee, container, false);

        details = (FrameLayout)view.findViewById(R.id.fragment_container);

        if(establishFragmentsAndroid()){
            listOfFee = (ListView)view.findViewById(R.id.listOfFee);
            adapterAnnouncement = new AdapterAnnounceFee(mainActivity,arrayAnnouncementSchoolFees);
            listOfFee.setAdapter(adapterAnnouncement);

            // Set event of ListView
            listOfFee.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Announcement announcement = (Announcement) parent.getItemAtPosition(position);
//                    AlertDialogFee.onCreateDialog(mainActivity,announcement).show();

                    /**
                     *
                     */
                    switchFragment(feefragmentHome, false, R.id.fragment_container);
                }
            });
        }
        return view;
    }
    
    
        /** Initialize fragment */
    private boolean establishFragmentsAndroid() {
        boolean valid = true;
        try{
            feefragmentHome = new FragmentFeeHome();
        }catch (Exception ex){
            valid = false;
            ex.printStackTrace();
        }
        return valid;
    }

    /** Initialize object FragmentManger to manager fragment */
    private void switchFragment(Fragment fragment, boolean addToBackStack, int id) {
        FragmentManager fm = mainActivity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(id,fragment);
        ft.addToBackStack("");
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        ft.commit();
    }
