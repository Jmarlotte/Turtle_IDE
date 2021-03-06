package controller;

import controller.workspace.Workspace;
import controller.workspace.WorkspaceFactory;
import controller.workspace.WorkspaceLoadPreferences;
import dataStorage.DataStorageManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import view.SLogoView;
import xml.XmlManager;
import xml.XmlSaver;

import java.io.File;
import java.util.*;


/**
 * Main controller class responsible for handling which workspaces are currently available, and which one is being
 * displayed.
 *
 * @author Will Long and James Marlotte
 */
public class SLogoController {
    private static final String DEFAULT_DIRECTORY = "src/resources/xmlData";
    private static final String FILE_CHOOSER_TITLE = "Choose a workspace XML file";
    private SLogoView mySLogoView;
    private int myNextWorkspaceNum;
    private Stack<Integer> myPrevWorkspaceNumStack;
    private SimpleIntegerProperty myCurrentWorkspaceNum;
    private Map<Integer, Workspace> myWorkspaceMap;
    private ObservableList<Integer> myActiveWorkspaceNums;

    public SLogoController(double width, double height) {
        mySLogoView = new SLogoView(width, height);
        myWorkspaceMap = new HashMap<>();
        myActiveWorkspaceNums = FXCollections.observableArrayList();
        myPrevWorkspaceNumStack = new Stack<>();
        myCurrentWorkspaceNum = new SimpleIntegerProperty();
        launchStartScreen();
    }

    public SLogoView getSLogoView() {
        return mySLogoView;
    }

    private void launchStartScreen() {
        myCurrentWorkspaceNum.set(-1);
        myPrevWorkspaceNumStack.clear();
        myWorkspaceMap.clear();
        myActiveWorkspaceNums.clear();
        myNextWorkspaceNum = 1;
        double width = mySLogoView.getWidth();
        double height = mySLogoView.getHeight();
        Workspace workspace = WorkspaceFactory.createWorkspace(width, height, this, true);
        mySLogoView.setCurrentContentManager(workspace.getContentManager());
    }

    /**
     * Load a new workspace using the file specified.
     */
    public void loadWorkspace() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(FILE_CHOOSER_TITLE);
        fileChooser.setInitialDirectory(new File(DEFAULT_DIRECTORY));
        File xmlFile = fileChooser.showOpenDialog(mySLogoView.getScene().getWindow());
        WorkspaceLoadPreferences preferences;
        try {
            preferences = new XmlManager().loadWorkspacePreferences(xmlFile);
        } catch (Exception e) {
            ////System.out.println("Could not parse file... Loading defaults");
            newWorkspace();
            return;
        }
        double width = mySLogoView.getWidth();
        double height = mySLogoView.getHeight();
        Workspace workspace = WorkspaceFactory.createWorkspace(width, height, this, preferences);
        myActiveWorkspaceNums.add(myNextWorkspaceNum);
        myWorkspaceMap.put(myNextWorkspaceNum, workspace);
        setCurrentWorkspaceNum(myNextWorkspaceNum);
        myNextWorkspaceNum++;
    }

    /**
     * Create new default workspace.
     */
    public void newWorkspace() {
        double width = mySLogoView.getWidth();
        double height = mySLogoView.getHeight();
        Workspace workspace = WorkspaceFactory.createWorkspace(width, height, this, false);
        myActiveWorkspaceNums.add(myNextWorkspaceNum);
        myWorkspaceMap.put(myNextWorkspaceNum, workspace);
        setCurrentWorkspaceNum(myNextWorkspaceNum);
        myNextWorkspaceNum++;
    }

    /**
     * Close the current workspace.
     */
    public void removeWorkspace() {
        myActiveWorkspaceNums.remove(new Integer(myCurrentWorkspaceNum.get()));
        myWorkspaceMap.remove(myCurrentWorkspaceNum.get());
        while (!myPrevWorkspaceNumStack.empty()) {
            int num = myPrevWorkspaceNumStack.pop();
            if (myWorkspaceMap.containsKey(num)) {
                setCurrentWorkspaceNum(num);
                return;
            }
        }
        System.exit(0);
    }

    /**
     * Get an observable integer of the current workspace number.
     *
     * @return an observable integer.
     */
    public SimpleIntegerProperty getCurrentWorkspaceNum() {
        return myCurrentWorkspaceNum;
    }

    public void setCurrentWorkspaceNum(int num) {
        if (!myWorkspaceMap.containsKey(num)) {
            if (num == -1) {
                launchStartScreen();
            }
            return;
        }
        ////System.out.println("SETTING TO " + num);
        myPrevWorkspaceNumStack.push(myCurrentWorkspaceNum.get());
        myCurrentWorkspaceNum.set(num);
        ////System.out.println("PREVIOUS " + myPrevWorkspaceNumStack);
        ////System.out.println("CURRENT " + myCurrentWorkspaceNum);
        Workspace workspace = myWorkspaceMap.get(num);
        mySLogoView.setCurrentContentManager(workspace.getContentManager());
    }

    /**
     * @return an ObservableList of all active workspaces.
     */
    public ObservableList<Integer> getActiveWorkspaceNums() {
        return myActiveWorkspaceNums;
    }

    /**
     * Save the current variables in the workspace to file.
     */
    public void saveWorkspaceVariables() {
        Workspace workspace = myWorkspaceMap.get(myCurrentWorkspaceNum.get());
        try {
            new XmlSaver().saveCommandsVariables(workspace.getModel().getData());
        } catch (Exception e) {
            e.printStackTrace();
            ////System.out.println("Couldn't save file.");
        }
    }

    /**
     * Load variables from file into the workspace.
     */
    public void loadWorkspaceVariables() {
        Workspace workspace = myWorkspaceMap.get(myCurrentWorkspaceNum.get());
        DataStorageManager data = workspace.getModel().getData();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(FILE_CHOOSER_TITLE);
        fileChooser.setInitialDirectory(new File(DEFAULT_DIRECTORY));
        File xmlFile = fileChooser.showOpenDialog(mySLogoView.getScene().getWindow());
        try {
            new XmlManager().loadAndSetVariablesCommands(xmlFile, data);
        } catch (Exception e) {
            e.printStackTrace();
            ////System.out.println("Could not load variables from file.");
        }
    }
}
