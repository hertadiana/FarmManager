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
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class ViteiController implements Initializable {
    Connection con;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private TableView<Vitei> tabelVitei = new TableView<>();
    @FXML
    private TableColumn<Vitei, String> colNumeVitel = new TableColumn<>();
    @FXML
    private TableColumn<Vitei, Integer> colNumarVitel = new TableColumn<>();
    @FXML
    private TableColumn<Vitei, Date> colDataVitel = new TableColumn<>();
    @FXML
    private TableColumn<Vitei, Date> colSex = new TableColumn<>();
    @FXML
    private TextField textNumeVitel;
    @FXML
    private TextField textNumarVitel;
    @FXML
    private TextField textSex;
    @FXML
    private DatePicker textDataVitel;
    @FXML
    private Button btnAddVitel;

    private int id = 0;

    @FXML
    public void addVitel(ActionEvent actionEvent) {
        String insert = "INSERT INTO vitei(nnume, numar, data_nastere,sex) VALUES (?, ?,?, ?)";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, textNumeVitel.getText());
            st.setInt(2, Integer.parseInt(textNumarVitel.getText()));
            st.setDate(3, java.sql.Date.valueOf(textDataVitel.getValue()));
            st.setString(4, textSex.getText());

            st.executeUpdate();
            clearFields();
            showVitei();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteVitel(ActionEvent event) {
        String delete = "DELETE FROM vitei WHERE id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showVitei();
            clearFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void clearFields() {
        textNumeVitel.setText(null);
        textNumarVitel.setText(null);
        textDataVitel.setValue(null);
        textSex.setText(null);
        btnAddVitel.setDisable(false);
    }

    public ObservableList<Vitei> getVitei() {
        ObservableList<Vitei> viteiList = FXCollections.observableArrayList();
        String query = "SELECT * FROM vitei";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Vitei vitel = new Vitei();
                vitel.setId(rs.getInt("id"));
                vitel.setNume(rs.getString("nnume"));
                vitel.setNumar(rs.getInt("numar"));
                vitel.setSex(rs.getString("sex"));

                vitel.setDataNastere(rs.getDate("data_nastere"));
                viteiList.add(vitel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return viteiList;
    }

    public void showVitei() {
        ObservableList<Vitei> list = getVitei();
        tabelVitei.setItems(list);
        colNumeVitel.setCellValueFactory(new PropertyValueFactory<>("nume"));
        colNumarVitel.setCellValueFactory(new PropertyValueFactory<>("numar"));
        colDataVitel.setCellValueFactory(new PropertyValueFactory<>("dataNastere"));
        colSex.setCellValueFactory(new PropertyValueFactory<>("sex"));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showVitei();
        tabelVitei.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Vitei vaca = tabelVitei.getSelectionModel().getSelectedItem();
                id=vaca.getId();
                System.out.println("ID:"+id);
            }
            else if(event.getClickCount()==2){
                Vitei vaca = tabelVitei.getSelectionModel().getSelectedItem();
                id=vaca.getId();
                if (vaca != null) {
                    try {
                        openViteiAtributeWindow(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void openViteiAtributeWindow(MouseEvent mouseEvent) throws IOException {
        Vitei vitel = tabelVitei.getSelectionModel().getSelectedItem();
        if (vitel != null) {
            URL url = new File("src/main/resources/Fxml/VitelAtribute.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            VitelAtributeController attributeController = loader.getController();
            attributeController.setViteiId(vitel.getId());

            Stage attributeStage = new Stage();
            Scene scene = new Scene(parent);
            attributeStage.setTitle("Atribute " + vitel.getNume());
            attributeStage.setScene(scene);
            attributeStage.show();
        }
    }

//    @FXML
//    public void openViteiAtributeWindow(MouseEvent event) throws IOException {
//        Vitei vitel = tabelVitei.getSelectionModel().getSelectedItem();
//        if (vitel != null) {
//            URL url = new File("src/main/resources/Fxml/VitelAtribute.fxml").toURI().toURL();
//            FXMLLoader loader = new FXMLLoader(url);
//            Parent parent = loader.load();
//            VitelAtributeController attributeController = loader.getController();
//            attributeController.setVitelId(vitel.getId());
//
//            Stage attributeStage = new Stage();
//            Scene scene = new Scene(parent);
//            attributeStage.setTitle("Atribute " + vitel.getNume());
//            attributeStage.setScene(scene);
//            attributeStage.show();
//        }
   // }
}
