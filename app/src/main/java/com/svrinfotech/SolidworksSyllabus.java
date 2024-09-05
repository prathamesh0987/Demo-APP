package com.svrinfotech;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.svrinfotech.pojo.Admission;
import com.svrinfotech.pojo.EndUser;

import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SolidworksSyllabus extends Fragment {
    private CheckBox swIntro,designConcept,parametricDesign,designSketeches,
            sheetMetalModeling,weldments,holeWizard,formingTool,moldTool,
            standardMates,advanceMates,mechanicalMates,
            mechanismSimulation,billsOfMaterial,productAnimation,
            baseFlange,edgeFlange,miterFlange,hem,corner,foldUnfold,flatten,vent,massProperties,gussets,
            swProjectionMethod,swTemplateCreation,detailing,swGeometricDimension,swTolerence,limitsFits,swSaveAs;
    private String status;
    private DatabaseReference syllabusReference,revisionReference;
    private ProvideAlertDialogInterface provideAlertDialogInterface;
    private Admission admission;
    String studentName;
    public SolidworksSyllabus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_solidworks_syllabus, container, false);
        init(rootView);
//        String sharedPreferenceName="SHARED_PREFERENCE";
//        SharedPreferences sharedPreferences=getActivity().getApplicationContext().getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String user=firebaseAuth.getCurrentUser().getEmail();

        List<EndUser> loggedUserList=EndUser.find(EndUser.class,"email=?",user);

        EndUser loggedUser=loggedUserList.get(0);

        String status=loggedUser.getStatus();
                //getArguments().getString("status");
        String name=loggedUser.getName();
                //sharedPreferences.getString("employeeName",null);
        switch (status) {
            case "student":
                syllabusReference= FirebaseDatabase.getInstance()
                    .getReference()
                    .child("syllabus")
                    .child(name);
                break;
            case "employee":
                if(!name.equals(null)) {
                    Admission admission=(Admission) getArguments().getSerializable("admission");
                    studentName=admission.getName();
                    syllabusReference= FirebaseDatabase.getInstance()
                            .getReference()
                            .child("syllabus")
                            .child(name)
                            .child(studentName);
                }
                break;
        }
        syllabusReference.keepSynced(true);
        // Inflate the layout for this fragment
        revisionReference=FirebaseDatabase.getInstance().getReference()
                .child("revision")
                .child("SOLIDWORKS")
                .child(name);
        revisionReference.keepSynced(true);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        syllabusReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot keyValue=(DataSnapshot) iterator.next();
                    String value=keyValue.getKey();
                    switch (value) {
                        case "swIntro":swIntro.setChecked(true);
                            break;
                        case "designConcept":designConcept.setChecked(true);
                            break;
                        case "parametricDesign":parametricDesign.setChecked(true);
                            break;
                        case "designSketeches":designSketeches.setChecked(true);
                            break;
                        case "sheetMetalModeling":sheetMetalModeling.setChecked(true);
                            break;
                        case "weldments":weldments.setChecked(true);
                            break;
                        case "holeWizard":holeWizard.setChecked(true);
                            break;
                        case "formingTool":formingTool.setChecked(true);
                            break;
                        case "moldTool":moldTool.setChecked(true);
                            break;
                        case "standardMates":standardMates.setChecked(true);
                            break;
                        case "advanceMates":advanceMates.setChecked(true);
                            break;
                        case "mechanicalMates":mechanicalMates.setChecked(true);
                            break;
                        case "mechanismSimulation":mechanismSimulation.setChecked(true);
                            break;
                        case "billsOfMaterial":billsOfMaterial.setChecked(true);
                            break;
                        case "productAnimation":productAnimation.setChecked(true);
                            break;
                        case "baseFlange":baseFlange.setChecked(true);
                            break;
                        case "edgeFlange":edgeFlange.setChecked(true);
                            break;
                        case "miterFlange":miterFlange.setChecked(true);
                            break;
                        case "hem":hem.setChecked(true);
                            break;
                        case "corner":corner.setChecked(true);
                            break;
                        case "foldUnfold":foldUnfold.setChecked(true);
                            break;
                        case "flatten":flatten.setChecked(true);
                            break;
                        case "vent":vent.setChecked(true);
                            break;
                        case "massProperties":massProperties.setChecked(true);
                            break;
                        case "gussets":gussets.setChecked(true);
                            break;
                        case "swProjectionMethod":swProjectionMethod.setChecked(true);
                            break;
                        case "swTemplateCreation":swTemplateCreation.setChecked(true);
                            break;
                        case "detailing":detailing.setChecked(true);
                            break;
                        case "swGeometricDimension":swGeometricDimension.setChecked(true);
                            break;
                        case "swTolerence":swTolerence.setChecked(true);
                            break;
                        case "limitsFits":limitsFits.setChecked(true);
                            break;
                        case "swSaveAs":swSaveAs.setChecked(true);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        swIntro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("swIntro").setValue("Introduction To CAD/CAM/CAE");
                    provideAlertDialogInterface.removeReason("Introduction To CAD/CAM/CAE",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Introduction To CAD/CAM/CAE",revisionReference,getActivity());
                    syllabusReference.child("swIntro").removeValue();
                }
            }
        });

        designConcept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("designConcept").setValue("Design Concept & Procedure");
                    provideAlertDialogInterface.removeReason("Design Concept & Procedure",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Design Concept & Procedure",revisionReference,getActivity());
                    syllabusReference.child("designConcept").removeValue();
                }
            }
        });

        parametricDesign.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("parametricDesign").setValue("Concept Of Parameteric Design");
                    provideAlertDialogInterface.removeReason("Concept Of Parameteric Design",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Concept Of Parameteric Design",revisionReference,getActivity());
                    syllabusReference.child("parametricDesign").removeValue();
                }
            }
        });

        designSketeches.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("designSketeches").setValue("Fully Design Sketches");
                    provideAlertDialogInterface.removeReason("Fully Design Sketches",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Fully Design Sketches",revisionReference,getActivity());
                    syllabusReference.child("designSketeches").removeValue();
                }
            }
        });

        sheetMetalModeling.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("sheetMetalModeling").setValue("Sheet Metal Modeling");
                    provideAlertDialogInterface.removeReason("Sheet Metal Modeling",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Sheet Metal Modeling",revisionReference,getActivity());
                    syllabusReference.child("sheetMetalModeling").removeValue();
                }
            }
        });

        weldments.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("weldments").setValue("Weldments");
                    provideAlertDialogInterface.removeReason("Weldments",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Weldments",revisionReference,getActivity());
                    syllabusReference.child("weldments").removeValue();
                }
            }
        });

        holeWizard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("holeWizard").setValue("Hole Wizard");
                    provideAlertDialogInterface.removeReason("Hole Wizard",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Hole Wizard",revisionReference,getActivity());
                    syllabusReference.child("holeWizard").removeValue();
                }
            }
        });

        formingTool.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("formingTool").setValue("Forming Tool");
                    provideAlertDialogInterface.removeReason("Forming Tool",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Forming Tool",revisionReference,getActivity());
                    syllabusReference.child("formingTool").removeValue();
                }
            }
        });

        moldTool.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("moldTool").setValue("Mold Tool");
                    provideAlertDialogInterface.removeReason("Mold Tool",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Mold Tool",revisionReference,getActivity());
                    syllabusReference.child("moldTool").removeValue();
                }
            }
        });

        standardMates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("standardMates").setValue("Standard Mates");
                    provideAlertDialogInterface.removeReason("Standard Mates",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Standard Mates",revisionReference,getActivity());
                    syllabusReference.child("standardMates").removeValue();
                }
            }
        });

        advanceMates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("advanceMates").setValue("Advance Mates");
                    provideAlertDialogInterface.removeReason("Advance Mates",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Advance Mates",revisionReference,getActivity());
                    syllabusReference.child("advanceMates").removeValue();
                }
            }
        });

        mechanicalMates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("mechanicalMates").setValue("Mechanical Mates");
                    provideAlertDialogInterface.removeReason("Mechanical Mates",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Mechanical Mates",revisionReference,getActivity());
                    syllabusReference.child("mechanicalMates").removeValue();
                }
            }
        });

        mechanismSimulation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("mechanismSimulation").setValue("Mechanism Simulation");
                    provideAlertDialogInterface.removeReason("Mechanism Simulation",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Mechanism Simulation",revisionReference,getActivity());
                    syllabusReference.child("mechanismSimulation").removeValue();
                }
            }
        });

        billsOfMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("billsOfMaterial").setValue("Bills Of Material");
                    provideAlertDialogInterface.removeReason("Bills Of Material",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Bills Of Material",revisionReference,getActivity());
                    syllabusReference.child("billsOfMaterial").removeValue();
                }
            }
        });

        productAnimation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("productAnimation").setValue("Product Animation");
                    provideAlertDialogInterface.removeReason("Product Animation",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Product Animation",revisionReference,getActivity());
                    syllabusReference.child("productAnimation").removeValue();
                }
            }
        });

        baseFlange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("baseFlange").setValue("Base Flange");
                    provideAlertDialogInterface.removeReason("Base Flange",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Base Flange",revisionReference,getActivity());
                    syllabusReference.child("baseFlange").removeValue();
                }
            }
        });

        edgeFlange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("edgeFlange").setValue("Edge Flange");
                    provideAlertDialogInterface.removeReason("Edge Flange",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Edge Flange",revisionReference,getActivity());
                    syllabusReference.child("edgeFlange").removeValue();
                }
            }
        });

        miterFlange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("miterFlange").setValue("Miter Flange");
                    provideAlertDialogInterface.removeReason("Miter Flange",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Miter Flange",revisionReference,getActivity());
                    syllabusReference.child("miterFlange").removeValue();
                }
            }
        });

        hem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("hem").setValue("Hem");
                    provideAlertDialogInterface.removeReason("Hem",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Hem",revisionReference,getActivity());
                    syllabusReference.child("hem").removeValue();
                }
            }
        });

        corner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("corner").setValue("Corner");
                    provideAlertDialogInterface.removeReason("Corner",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Corner",revisionReference,getActivity());
                    syllabusReference.child("corner").removeValue();
                }
            }
        });

        foldUnfold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("foldUnfold").setValue("Fold-Unfold");
                    provideAlertDialogInterface.removeReason("Fold-Unfold",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Fold-Unfold",revisionReference,getActivity());
                    syllabusReference.child("foldUnfold").removeValue();
                }
            }
        });

        flatten.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("flatten").setValue("Flatten");
                    provideAlertDialogInterface.removeReason("Flatten",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Flatten",revisionReference,getActivity());
                    syllabusReference.child("flatten").removeValue();
                }
            }
        });

        vent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("vent").setValue("Vent");
                    provideAlertDialogInterface.removeReason("Vent",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Vent",revisionReference,getActivity());
                    syllabusReference.child("vent").removeValue();
                }
            }
        });

        massProperties.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("massProperties").setValue("Mass Properties");
                    provideAlertDialogInterface.removeReason("Mass Properties",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Mass Properties",revisionReference,getActivity());
                    syllabusReference.child("massProperties").removeValue();
                }
            }
        });

        gussets.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("gussets").setValue("Gussets");
                    provideAlertDialogInterface.removeReason("Gussets",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Gussets",revisionReference,getActivity());
                    syllabusReference.child("gussets").removeValue();
                }
            }
        });

        swProjectionMethod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("swProjectionMethod").setValue("Projection Method");
                    provideAlertDialogInterface.removeReason("Projection Method",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Projection Method",revisionReference,getActivity());
                    syllabusReference.child("swProjectionMethod").removeValue();
                }
            }
        });

        swTemplateCreation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("swTemplateCreation").setValue("Template Creation");
                    provideAlertDialogInterface.removeReason("Template Creation",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Template Creation",revisionReference,getActivity());
                    syllabusReference.child("swTemplateCreation").removeValue();
                }
            }
        });

        detailing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("detailing").setValue("Detailing Drawing");
                    provideAlertDialogInterface.removeReason("Detailing Drawing",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Detailing Drawing",revisionReference,getActivity());
                    syllabusReference.child("detailing").removeValue();
                }
            }
        });

        swGeometricDimension.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("swGeometricDimension").setValue("Geometric Dimensions & Tolerence");
                    provideAlertDialogInterface.removeReason("Geometric Dimensions & Tolerence",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Geometric Dimensions & Tolerence",revisionReference,getActivity());
                    syllabusReference.child("swGeometricDimension").removeValue();
                }
            }
        });

        swTolerence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("swTolerence").setValue("Tolerence");
                    provideAlertDialogInterface.removeReason("Tolerence",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Tolerence",revisionReference,getActivity());
                    syllabusReference.child("swTolerence").removeValue();
                }
            }
        });

        limitsFits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("limitsFits").setValue("Limits & Fits");
                    provideAlertDialogInterface.removeReason("Limits & Fits",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Limits & Fits",revisionReference,getActivity());
                    syllabusReference.child("limitsFits").removeValue();
                }
            }
        });

        swSaveAs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("swSaveAs").setValue("Save As PDF, IGES, STEP, DWG");
                    provideAlertDialogInterface.removeReason("Save As PDF, IGES, STEP, DWG",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Save As PDF, IGES, STEP, DWG",revisionReference,getActivity());
                    syllabusReference.child("swSaveAs").removeValue();
                }
            }
        });
    }

    private void init(View rootView) {
        swIntro=rootView.findViewById(R.id.swIntro);
        designConcept=rootView.findViewById(R.id.designConcept);
        parametricDesign=rootView.findViewById(R.id.parametricDesign);
        designSketeches=rootView.findViewById(R.id.designSketeches);
        sheetMetalModeling=rootView.findViewById(R.id.sheetMetalModeling);
        weldments=rootView.findViewById(R.id.weldments);
        holeWizard=rootView.findViewById(R.id.holeWizard);
        formingTool=rootView.findViewById(R.id.formingTool);
        moldTool=rootView.findViewById(R.id.moldTool);
        standardMates=rootView.findViewById(R.id.standardMates);
        advanceMates=rootView.findViewById(R.id.advanceMates);
        mechanicalMates=rootView.findViewById(R.id.mechanicalMates);
        mechanismSimulation=rootView.findViewById(R.id.mechanismSimulation);
        billsOfMaterial=rootView.findViewById(R.id.billsOfMaterial);
        productAnimation=rootView.findViewById(R.id.productAnimation);
        baseFlange=rootView.findViewById(R.id.baseFlange);
        edgeFlange=rootView.findViewById(R.id.edgeFlange);
        miterFlange=rootView.findViewById(R.id.miterFlange);
        hem=rootView.findViewById(R.id.hem);
        corner=rootView.findViewById(R.id.corner);
        foldUnfold=rootView.findViewById(R.id.foldUnfold);
        flatten=rootView.findViewById(R.id.flatten);
        vent=rootView.findViewById(R.id.vent);
        massProperties=rootView.findViewById(R.id.massProperties);
        gussets=rootView.findViewById(R.id.gussets);
        swProjectionMethod=rootView.findViewById(R.id.swProjectionMethod);
        swTemplateCreation=rootView.findViewById(R.id.swTemplateCreation);
        detailing=rootView.findViewById(R.id.detailing);
        swGeometricDimension=rootView.findViewById(R.id.swGeometricDimension);
        swTolerence=rootView.findViewById(R.id.swTolerence);
        limitsFits=rootView.findViewById(R.id.limitsFits);
        swSaveAs=rootView.findViewById(R.id.swSaveAs);
        provideAlertDialogInterface=new ProvideAlertDialogInterface();
    }
}