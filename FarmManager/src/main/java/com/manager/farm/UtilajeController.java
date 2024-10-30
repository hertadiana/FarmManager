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

public class UtilajeController implements Initializable {

    private Connection con;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    private int id = 0;

    @FXML
    private TableView<Utilaj> tabelUtilaje;

    @FXML
    private TableColumn<Utilaj, String> colTip;

    @FXML
    private TextField textTip;
    @FXML
    private Button btnAddUtilaje;
    @FXML
    private Button btnDeleteUtilaje;

    public void addUtilaj(ActionEvent actionEvent) {
        String insert = "INSERT INTO utilaje(tip) VALUES(?)";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, textTip.getText());
            st.executeUpdate();
            clear();
            showUtilaje();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUtilaj(ActionEvent event) {
        String delete = "DELETE FROM utilaje WHERE id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showUtilaje();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void clear() {
        textTip.setText(null);
        btnAddUtilaje.setDisable(false);
    }

    public ObservableList<Utilaj> getUtilaje() {
        ObservableList<Utilaj> utilaje = FXCollections.observableArrayList();
        String query = "SELECT * FROM utilaje";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Utilaj utilaj = new Utilaj();
                utilaj.setId(rs.getInt("id"));
                utilaj.setTip(rs.getString("tip"));
                utilaje.add(utilaj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utilaje;
    }

    public void showUtilaje() {
        ObservableList<Utilaj> list = getUtilaje();
        tabelUtilaje.setItems(list);
        colTip.setCellValueFactory(new PropertyValueFactory<>("tip"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showUtilaje();
        tabelUtilaje.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Utilaj utilaj = tabelUtilaje.getSelectionModel().getSelectedItem();
                if (utilaj != null) {
                    id = utilaj.getId();
                    textTip.setText(utilaj.getTip());
                    btnAddUtilaje.setDisable(true);
                }
            }
        });
    }
}