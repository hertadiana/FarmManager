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

public class TratamenteController implements Initializable {

    private Connection con;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    private int id = 0;

    @FXML
    private TableView<Tratament> tabelTratamente;
    @FXML
    private TableColumn<Tratament, String> colNume;
    @FXML
    private TableColumn<Tratament, String> colTip;
    @FXML
    private TextField textNume;
    @FXML
    private TextField textTip;
    @FXML
    private Button btnAddTratamente;
    @FXML
    private Button btnDeleteTratamente;

    public void addTratamente(ActionEvent actionEvent) {
        String insert = "INSERT INTO tratamente(nume, tip) VALUES(?, ?)";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, textNume.getText());
            st.setString(2, textTip.getText());
            st.executeUpdate();
            clear();
            showTratamente();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTratamente(ActionEvent event) {
        String delete = "DELETE FROM tratamente WHERE id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showTratamente();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void clear() {
        textNume.setText(null);
        textTip.setText(null);
        btnAddTratamente.setDisable(false);
    }

    public ObservableList<Tratament> getTratamente() {
        ObservableList<Tratament> tratamente = FXCollections.observableArrayList();
        String query = "SELECT * FROM tratamente";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Tratament tratament = new Tratament();
                tratament.setId(rs.getInt("id"));
                tratament.setNume(rs.getString("nume"));
                tratament.setTip(rs.getString("tip"));
                tratamente.add(tratament);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tratamente;
    }

    public void showTratamente() {
        ObservableList<Tratament> list = getTratamente();
        tabelTratamente.setItems(list);
        colNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        colTip.setCellValueFactory(new PropertyValueFactory<>("tip"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTratamente();
        tabelTratamente.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Tratament tratament = tabelTratamente.getSelectionModel().getSelectedItem();
                if (tratament != null) {
                    id = tratament.getId();
                    textNume.setText(tratament.getNume());
                    textTip.setText(tratament.getTip());
                    btnAddTratamente.setDisable(true);
                }
            }
        });
    }
}