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
public class AutocadSyllabus extends Fragment {
    private CheckBox projectionMethod,autocadWindows,unit,toolbarCustomization,ucsIcon,shortcutKeys,
            draw,modify,properties,workspace,layer,dimensions,format,
            designCenter,options,rasterImage,objectSnap,viewCB,dimensionStyle,commandLine,
            modeling,visualStyle,orbit,solidEditing,booleanOperation,
            detailingDrawing,templateCreation,plotting,printing,displayImage,saveFile,cadSetting,
            tolerence,limits,fits,surfaceFinishing,weldingSymbol;
    private DatabaseReference syllabusReference,revisionReference;
    private String studentName;

    ProvideAlertDialogInterface provideAlertDialogInterface;

    public AutocadSyllabus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_autocad_syllabus, container, false);
        init(rootView);
        //String sharedPreferenceName="SHARED_PREFERENCE";
        //SharedPreferences sharedPreferences=getActivity().getApplicationContext().getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
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
                    .child("AUTOCAD")
                    .child(name);
                break;
            case "employee":
                if(!name.equals(null)) {
                Admission admission=(Admission) getArguments().getSerializable("admission");
                studentName=admission.getName();
                syllabusReference= FirebaseDatabase.getInstance()
                        .getReference()
                        .child("syllabus")
                        .child("AUTOCAD")
                        .child(name)
                        .child(studentName);
            }
                break;
        }
        syllabusReference.keepSynced(true);
        revisionReference=FirebaseDatabase.getInstance().getReference()
                .child("revision")
                .child("AUTOCAD")
                .child(name);
        revisionReference.keepSynced(true);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        //String status=getArguments().getString("status");
        syllabusReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot keyValue=(DataSnapshot) iterator.next();
                    String name=keyValue.getKey();
                    switch (name) {
                        case "projectionMethod":projectionMethod.setChecked(true);
                            break;
                        case "autocadWindows":autocadWindows.setChecked(true);
                            break;
                        case "unit":unit.setChecked(true);
                            break;
                        case "toolbarCustomization":toolbarCustomization.setChecked(true);
                            break;
                        case "ucsIcon":ucsIcon.setChecked(true);
                            break;
                        case "shortcutKeys":shortcutKeys.setChecked(true);
                            break;
                        case "draw":draw.setChecked(true);
                            break;
                        case "modify":modify.setChecked(true);
                            break;
                        case "workspace":workspace.setChecked(true);
                            break;
                        case "layer":layer.setChecked(true);
                            break;
                        case "dimensions":dimensions.setChecked(true);
                            break;
                        case "format":format.setChecked(true);
                            break;
                        case "designCenter":designCenter.setChecked(true);
                            break;
                        case "options":options.setChecked(true);
                            break;
                        case "rasterImage":rasterImage.setChecked(true);
                            break;
                        case "objectSnap":objectSnap.setChecked(true);
                            break;
                        case "viewCB":viewCB.setChecked(true);
                            break;
                        case "dimensionStyle":dimensionStyle.setChecked(true);
                            break;
                        case "commandLine":commandLine.setChecked(true);
                            break;
                        case "modeling":modeling.setChecked(true);
                            break;
                        case "visualStyle":visualStyle.setChecked(true);
                            break;
                        case "orbit":orbit.setChecked(true);
                            break;
                        case "solidEditing":solidEditing.setChecked(true);
                            break;
                        case "booleanOperation":booleanOperation.setChecked(true);
                            break;
                        case "detailingDrawing":detailingDrawing.setChecked(true);
                            break;
                        case "templateCreation":templateCreation.setChecked(true);
                            break;
                        case "plotting":plotting.setChecked(true);
                            break;
                        case "printing":printing.setChecked(true);
                            break;
                        case "displayImage":displayImage.setChecked(true);
                            break;
                        case "saveFile":saveFile.setChecked(true);
                            break;
                        case "cadSetting":cadSetting.setChecked(true);
                            break;
                        case "tolerence":tolerence.setChecked(true);
                            break;
                        case "limits":limits.setChecked(true);
                            break;
                        case "fits":fits.setChecked(true);
                            break;
                        case "surfaceFinishing":surfaceFinishing.setChecked(true);
                            break;
                        case "weldingSymbol":weldingSymbol.setChecked(true);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        projectionMethod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("projectionMethod").setValue("Projection Method");
                    provideAlertDialogInterface.removeReason("Projection Method",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Projection Method",revisionReference,getActivity());
                    syllabusReference.child("projectionMethod").removeValue();
                }
            }
        });

        autocadWindows.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("autocadWindows").setValue("Autocad Windows");
                    provideAlertDialogInterface.removeReason("Autocad Windows",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Autocad Windows",revisionReference,getActivity());
                    syllabusReference.child("autocadWindows").removeValue();
                }
            }
        });

        unit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("unit").setValue("Unit");
                    provideAlertDialogInterface.removeReason("Unit",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Unit",revisionReference,getActivity());
                    syllabusReference.child("unit").removeValue();
                }
            }
        });

        toolbarCustomization.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("toolbarCustomization").setValue("Toolbar Customization");
                    provideAlertDialogInterface.removeReason("Toolbar Customization",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Toolbar Customization",revisionReference,getActivity());
                    syllabusReference.child("toolbarCustomization").removeValue();
                }
            }
        });

        ucsIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("ucsIcon").setValue("UCS Icon");
                    provideAlertDialogInterface.removeReason("UCS Icon",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("UCS Icon",revisionReference,getActivity());
                    syllabusReference.child("ucsIcon").removeValue();
                }
            }
        });

        shortcutKeys.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("shortcutKeys").setValue("Shortcut Keys Method");
                    provideAlertDialogInterface.removeReason("Shortcut Keys Method",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Shortcut Keys Method",revisionReference,getActivity());
                    syllabusReference.child("shortcutKeys").removeValue();
                }
            }
        });

        draw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("draw").setValue("Draw");
                    provideAlertDialogInterface.removeReason("Draw",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Draw",revisionReference,getActivity());
                    syllabusReference.child("draw").removeValue();
                }
            }
        });

        modify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("modify").setValue("Modify");
                    provideAlertDialogInterface.removeReason("Modify",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Modify",revisionReference,getActivity());
                    syllabusReference.child("modify").removeValue();
                }
            }
        });

        properties.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("properties").setValue("Properties");
                    provideAlertDialogInterface.removeReason("Properties",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Properties",revisionReference,getActivity());
                    syllabusReference.child("properties").removeValue();
                }
            }
        });

        workspace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("workspace").setValue("Workspace");
                    provideAlertDialogInterface.removeReason("Workspace",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Workspace",revisionReference,getActivity());
                    syllabusReference.child("workspace").removeValue();
                }
            }
        });

        layer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("layer").setValue("Layer");
                    provideAlertDialogInterface.removeReason("Layer",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Layer",revisionReference,getActivity());
                    syllabusReference.child("layer").removeValue();
                }
            }
        });

        dimensions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("dimensions").setValue("Dimensions");
                    provideAlertDialogInterface.removeReason("Dimensions",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Dimensions",revisionReference,getActivity());
                    syllabusReference.child("dimensions").removeValue();
                }
            }
        });

        format.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("format").setValue("Format");
                    provideAlertDialogInterface.removeReason("Format",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Format",revisionReference,getActivity());
                    syllabusReference.child("format").removeValue();
                }
            }
        });

        designCenter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("designCenter").setValue("Design Center");
                    provideAlertDialogInterface.removeReason("Design Center",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Design Center",revisionReference,getActivity());
                    syllabusReference.child("designCenter").removeValue();
                }
            }
        });

        options.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("options").setValue("Option");
                    provideAlertDialogInterface.removeReason("Option",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Option",revisionReference,getActivity());
                    syllabusReference.child("options").removeValue();
                }
            }
        });

        rasterImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("rasterImage").setValue("Raster Image");
                    provideAlertDialogInterface.removeReason("Raster Image",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Raster Image",revisionReference,getActivity());
                    syllabusReference.child("rasterImage").removeValue();
                }
            }
        });

        objectSnap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("objectSnap").setValue("Object Snap");
                    provideAlertDialogInterface.removeReason("Object Snap",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Object Snap",revisionReference,getActivity());
                    syllabusReference.child("objectSnap").removeValue();
                }
            }
        });

        viewCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("viewCB").setValue("View");
                    provideAlertDialogInterface.removeReason("View",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("View",revisionReference,getActivity());
                    syllabusReference.child("viewCB").removeValue();
                }
            }
        });

        dimensionStyle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("dimensionStyle").setValue("Dimension Style");
                    provideAlertDialogInterface.removeReason("Dimension Style",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Dimension Style",revisionReference,getActivity());
                    syllabusReference.child("dimensionStyle").removeValue();
                }
            }
        });

        commandLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("commandLine").setValue("Command Line Windows");
                    provideAlertDialogInterface.removeReason("Command Line Windows",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Command Line Windows",revisionReference,getActivity());
                    syllabusReference.child("commandLine").removeValue();
                }
            }
        });

        modeling.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("modeling").setValue("Modeling");
                    provideAlertDialogInterface.removeReason("Modeling",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Modeling",revisionReference,getActivity());
                    syllabusReference.child("modeling").removeValue();
                }
            }
        });

        visualStyle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("visualStyle").setValue("Visual Style");
                    provideAlertDialogInterface.removeReason("Visual Style",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Visual Style",revisionReference,getActivity());
                    syllabusReference.child("visualStyle").removeValue();
                }
            }
        });

        orbit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("orbit").setValue("Orbit");
                    provideAlertDialogInterface.removeReason("Orbit",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Orbit",revisionReference,getActivity());
                    syllabusReference.child("orbit").removeValue();
                }
            }
        });

        solidEditing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("solidEditing").setValue("Solid Editing");
                    provideAlertDialogInterface.removeReason("Solid Editing",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Solid Editing",revisionReference,getActivity());
                    syllabusReference.child("solidEditing").removeValue();
                }
            }
        });

        booleanOperation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("booleanOperation").setValue("Boolean Operation");
                    provideAlertDialogInterface.removeReason("Boolean Operation",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Boolean Operation",revisionReference,getActivity());
                    syllabusReference.child("booleanOperation").removeValue();
                }
            }
        });

        detailingDrawing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("detailingDrawing").setValue("Detailing Drawing");
                    provideAlertDialogInterface.removeReason("Detailing Drawing",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Detailing Drawing",revisionReference,getActivity());
                    syllabusReference.child("detailingDrawing").removeValue();
                }
            }
        });

        templateCreation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("templateCreation").setValue("Template Creation");
                    provideAlertDialogInterface.removeReason("Template Creation",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Template Creation",revisionReference,getActivity());
                    syllabusReference.child("templateCreation").removeValue();
                }
            }
        });

        plotting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("plotting").setValue("Plotting");
                    provideAlertDialogInterface.removeReason("Plotting",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Plotting",revisionReference,getActivity());
                    syllabusReference.child("plotting").removeValue();
                }
            }
        });

        printing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("printing").setValue("Printing");
                    provideAlertDialogInterface.removeReason("Printing",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Printing",revisionReference,getActivity());
                    syllabusReference.child("printing").removeValue();
                }
            }
        });

        displayImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("displayImage").setValue("Display Image");
                    provideAlertDialogInterface.removeReason("Display Image",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Display Image",revisionReference,getActivity());
                    syllabusReference.child("displayImage").removeValue();
                }
            }
        });

        saveFile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("saveFile").setValue("Save as PDF & DWG");
                    provideAlertDialogInterface.removeReason("Save as PDF & DWG",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Save as PDF & DWG",revisionReference,getActivity());
                    syllabusReference.child("saveFile").removeValue();
                }
            }
        });

        cadSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("cadSetting").setValue("CAD Setting & Customization");
                    provideAlertDialogInterface.removeReason("CAD Setting & Customization",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("CAD Setting & Customization",revisionReference,getActivity());
                    syllabusReference.child("cadSetting").removeValue();
                }
            }
        });

        tolerence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("tolerence").setValue("Tolerance");
                    provideAlertDialogInterface.removeReason("Tolerance",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Tolerance",revisionReference,getActivity());
                    syllabusReference.child("tolerence").removeValue();
                }
            }
        });

        limits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("limits").setValue("Limits");
                    provideAlertDialogInterface.removeReason("Limits",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Limits",revisionReference,getActivity());
                    syllabusReference.child("limits").removeValue();
                }
            }
        });

        fits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("fits").setValue("Fits");
                    provideAlertDialogInterface.removeReason("Fits",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Fits",revisionReference,getActivity());
                    syllabusReference.child("fits").removeValue();
                }
            }
        });

        surfaceFinishing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("surfaceFinishing").setValue("Surface Finishing");
                    provideAlertDialogInterface.removeReason("Surface Finishing",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Surface Finishing",revisionReference,getActivity());
                    syllabusReference.child("surfaceFinishing").removeValue();
                }
            }
        });

        weldingSymbol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("weldingSymbol").setValue("Welding Symbol");
                    provideAlertDialogInterface.removeReason("Welding Symbol",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Welding Symbol",revisionReference,getActivity());
                    syllabusReference.child("weldingSymbol").removeValue();
                }
            }
        });

    }

    private void init(View rootView) {
        projectionMethod=rootView.findViewById(R.id.projectionMethod);
        autocadWindows=rootView.findViewById(R.id.autocadWindows);
        toolbarCustomization=rootView.findViewById(R.id.toolbarCustomization);
        unit=rootView.findViewById(R.id.unit);
        ucsIcon=rootView.findViewById(R.id.ucsIcon);
        shortcutKeys=rootView.findViewById(R.id.shortcutKeys);
        draw=rootView.findViewById(R.id.draw);
        modify=rootView.findViewById(R.id.modify);
        properties=rootView.findViewById(R.id.properties);
        workspace=rootView.findViewById(R.id.workspace);
        layer=rootView.findViewById(R.id.layer);
        dimensions=rootView.findViewById(R.id.dimensions);
        format=rootView.findViewById(R.id.format);
        designCenter=rootView.findViewById(R.id.designCenter);
        options=rootView.findViewById(R.id.options);
        rasterImage=rootView.findViewById(R.id.rasterImage);
        objectSnap=rootView.findViewById(R.id.objectSnap);
        viewCB=rootView.findViewById(R.id.viewCB);
        dimensionStyle=rootView.findViewById(R.id.dimensionStyle);
        commandLine=rootView.findViewById(R.id.commandLine);
        modeling=rootView.findViewById(R.id.modeling);
        visualStyle=rootView.findViewById(R.id.visualStyle);
        orbit=rootView.findViewById(R.id.orbit);
        solidEditing=rootView.findViewById(R.id.solidEditing);
        booleanOperation=rootView.findViewById(R.id.booleanOperation);
        detailingDrawing=rootView.findViewById(R.id.detailingDrawing);
        templateCreation=rootView.findViewById(R.id.templateCreation);
        plotting=rootView.findViewById(R.id.plotting);
        printing=rootView.findViewById(R.id.printing);
        displayImage=rootView.findViewById(R.id.displayImage);
        saveFile=rootView.findViewById(R.id.saveFile);
        cadSetting=rootView.findViewById(R.id.cadSetting);
        tolerence=rootView.findViewById(R.id.tolerence);
        limits=rootView.findViewById(R.id.limits);
        fits=rootView.findViewById(R.id.fits);
        surfaceFinishing=rootView.findViewById(R.id.surfaceFinishing);
        weldingSymbol=rootView.findViewById(R.id.weldingSymbol);
        provideAlertDialogInterface=new ProvideAlertDialogInterface();
    }
}