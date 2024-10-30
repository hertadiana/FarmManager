package com.manager.farm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.FloatBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class VaciController implements Initializable {
    Connection con;
    PreparedStatement st=null;
    ResultSet rs =null;

    @FXML

    TableColumn<Vaci,String> colNume=new TableColumn<Vaci,String>();;
    @FXML

    TableColumn<Vaci,Integer> colNumar=new TableColumn<Vaci,Integer>();;
    @FXML

    TableColumn<Vaci, Date> colData=new TableColumn<Vaci,Date>();
    @FXML
    TableView<Vaci> tabelVaci = new TableView<Vaci>();

    @FXML
    TextField textNume;
    @FXML
    TextField textNumar;
    int id=0;

    @FXML
    public void openVaciWindow(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/resources/Fxml/Vaci.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(url);
        Stage vaciStage=new Stage();
        Scene scene = new Scene(parent);
        vaciStage.setTitle("Reproductie Vaci");
        vaciStage.setScene(scene);
        vaciStage.show();
    }

    @FXML
    private Button btnAddVaca;

    @FXML
    DatePicker textData;


    public void addVaca(ActionEvent actionEvent) {
        String insert= "insert into vaci(nume,numar,data_nastere,id) values(?,?,?,?)" ;
        con=DBConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1,textNume.getText());
            String stringNumar=textNumar.getText();
            Integer fieldNumar=Integer.parseInt(stringNumar);
            st.setInt(2,fieldNumar);
            st.setDate(3, java.sql.Date.valueOf(textData.getValue()));
            st.setInt(4,id);
            st.executeUpdate();
            clear();
            showVaci();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteVaca(ActionEvent event){

        String delete="delete from vaci where id = ?";
        con=DBConnexion.getCon();
        try {
            st=con.prepareStatement(delete);
            st.setInt(1,id);
            st.executeUpdate();
            showVaci();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void clear(){
        textNume.setText(null);
        textNumar.setText(null);
        textData.setValue(null);
        btnAddVaca.setDisable(false);
    }
    @FXML
    void clearField(ActionEvent event){
        clear();
    }
//    public void updateVaca(ActionEvent actionEvent){
//        String update="update vaci set Nume = ?, Numar = ?, Course = ? where id = ?";
//        con=DBConnexion.getCon();
//        try {
//            st=con.prepareStatement(update);
//            st.setString(1,textNume.getText());
//            String stringNumar=textNumar.getText();
//            Integer fieldNumar=Integer.parseInt(stringNumar);
//            st.setInt(2,fieldNumar);
//            st.setDate(3, java.sql.Date.valueOf(textData.getValue()));
//            st.setInt(4,id);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public ObservableList<Vaci> getVaci(){
        ObservableList<Vaci> vaci= FXCollections.observableArrayList();
        String query= "select * from vaci";
        con=DBConnexion.getCon();
        try {
            st=con.prepareStatement(query);
            rs=st.executeQuery();
            while(rs.next()){
                Vaci vaca= new Vaci();
                vaca.setId(rs.getInt("id"));
                vaca.setNume(rs.getString("nume"));
                vaca.setNumar(rs.getInt("numar"));
                vaca.setData_nastere(rs.getDate("data_nastere"));
                vaci.add(vaca);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vaci;
    }

    public void showVaci(){
        ObservableList<Vaci> list = getVaci();

        tabelVaci.setItems(list);
        colNume.setCellValueFactory(new PropertyValueFactory<Vaci,String>("nume"));

        colNumar.setCellValueFactory(new PropertyValueFactory<Vaci,Integer>("numar"));
        colData.setCellValueFactory(new PropertyValueFactory<Vaci,Date>("data_nastere"));

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showVaci();
        tabelVaci.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Vaci vaca = tabelVaci.getSelectionModel().getSelectedItem();
                id=vaca.getId();
                System.out.println("ID:"+id);
            }
            else if(event.getClickCount()==2){
                Vaci vaca = tabelVaci.getSelectionModel().getSelectedItem();
                id=vaca.getId();
                if (vaca != null) {
                    try {
                        openAtributeWindow(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    public void openAtributeWindow(MouseEvent mouseEvent) throws IOException {

            Vaci vaca=tabelVaci.getSelectionModel().getSelectedItem();
            URL url = new File("src/main/resources/Fxml/Atribute.fxml").toURI().toURL();
            FXMLLoader loader= new FXMLLoader(url);
            Parent parent = loader.load();

            System.out.println("hereeee");

            AtributeController attributeController = loader.getController();
            attributeController.setIdVaca(id);

            Stage attributeStage = new Stage();
            Scene scene = new Scene(parent);
            attributeStage.setTitle("Detalii Reproductie " + vaca.getNume());
            attributeStage.setScene(scene);
            attributeStage.show();

    }

    public void openViteiWindow(ActionEvent event) throws IOException {

        URL url = new File("src/main/resources/Fxml/Vitei.fxml").toURI().toURL();
        FXMLLoader loader= new FXMLLoader(url);
        Parent parent = loader.load();


        Stage vietiStage=new Stage();
        Scene scene = new Scene(parent);
        vietiStage.setTitle("Vitei");
        vietiStage.setScene(scene);
        vietiStage.show();
    }
    @FXML
    private Button buton_vaci;

    @FXML
    private Button buton_vitei;

    @FXML
    private Button btnTratamente;

    @FXML
    private Button btnFuraje;

    @FXML
    private Button btnVanzari;

    @FXML
    private Button btnUtilaje;

    @FXML
    private Button btnCombustibili;
    private void openWindow(String fxmlPath, String title) throws IOException {
        URL url = new File(fxmlPath).toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void openTratamenteWindow() throws IOException {
        openWindow("src/main/resources/Fxml/Tratamente.fxml", "Tratamente");
    }



    @FXML
    private void openFurajeWindow() throws IOException {
        openWindow("src/main/resources/Fxml/Furaje.fxml", "Furaje");
    }

    @FXML
    private void openTransfersWindow() throws IOException {
        openWindow("src/main/resources/Fxml/Transfer.fxml", "Vanzari/Achizitii");
    }

    @FXML
    private void openUtilajeWindow() throws IOException {
        openWindow("src/main/resources/Fxml/Utilaje.fxml", "Utilaje");
    }

    @FXML
    private void openCombustibiliWindow() throws IOException {
        openWindow("src/main/resources/Fxml/Combustibili.fxml", "Combustibili");
    }
//    public void getData(javafx.scene.input.MouseEvent mouseEvent) {
//        Vaci vaca=tabelVaci.getSelectionModel().getSelectedItem();
//        id=vaca.getId();
//        textNume.setText(vaca.getNume());
//        textNumar.setText(String.valueOf(vaca.getNumar()));
//
//        btnAddVaca.setDisable(true);
//    }
}
