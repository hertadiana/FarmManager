package com.manager.farm;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FurajeController implements Initializable {

    private Connection con;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    private int id = 0;

    @FXML
    private TableView<Furaje> tabelFuraje;

    @FXML
    private TableColumn<Furaje, String> colTip;

    @FXML
    private TextField textTip;
    @FXML
    private Button btnAddFuraje;
    @FXML
    private Button btnDeleteFuraje;

    public void addFuraje(ActionEvent actionEvent) {
        String insert = "INSERT INTO furaje(tip) VALUES(?)";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, textTip.getText());
            st.executeUpdate();
            clear();
            showFuraje();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFuraje(ActionEvent event) {
        String delete = "DELETE FROM furaje WHERE id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showFuraje();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void clear() {
        textTip.setText(null);
        btnAddFuraje.setDisable(false);
    }

    public ObservableList<Furaje> getFuraje() {
        ObservableList<Furaje> tratamente = FXCollections.observableArrayList();
        String query = "SELECT * FROM furaje";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Furaje tratament = new Furaje();
                tratament.setId(rs.getInt("id"));
                tratament.setTip(rs.getString("tip"));
                tratamente.add(tratament);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tratamente;
    }

    public void showFuraje() {
        ObservableList<Furaje> list = getFuraje();
        tabelFuraje.setItems(list);
        colTip.setCellValueFactory(new PropertyValueFactory<>("tip"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showFuraje();
        tabelFuraje.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Furaje tratament = tabelFuraje.getSelectionModel().getSelectedItem();
                if (tratament != null) {
                    id = tratament.getId();
                    textTip.setText(tratament.getTip());
                    btnAddFuraje.setDisable(true);
                }
            }
        });
    }
}