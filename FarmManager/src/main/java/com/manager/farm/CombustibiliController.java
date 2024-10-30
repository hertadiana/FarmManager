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

public class CombustibiliController implements Initializable {

    private Connection con;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    private int id = 0;

    @FXML
    private TableView<Combustibil> tabelCombustibil;

    @FXML
    private TableColumn<Combustibil, String> colInformatii;

    @FXML
    private TextField textInformatii;
    @FXML
    private Button btnAddCombustibil;
    @FXML
    private Button btnDeleteCombustibil;

    public void addCombustibil(ActionEvent actionEvent) {
        String insert = "INSERT INTO combustibil(informatii) VALUES(?)";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, textInformatii.getText());
            st.executeUpdate();
            clear();
            showCombustibil();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCombustibil(ActionEvent event) {
        String delete = "DELETE FROM combustibil WHERE id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showCombustibil();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void clear() {
        textInformatii.setText(null);
        btnAddCombustibil.setDisable(false);
    }

    public ObservableList<Combustibil> getCombustibil() {
        ObservableList<Combustibil> utilaje = FXCollections.observableArrayList();
        String query = "SELECT * FROM combustibil";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Combustibil utilaj = new Combustibil();
                utilaj.setId(rs.getInt("id"));
                utilaj.setInformatii(rs.getString("informatii"));
                utilaje.add(utilaj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utilaje;
    }

    public void showCombustibil() {
        ObservableList<Combustibil> list = getCombustibil();
        tabelCombustibil.setItems(list);
        colInformatii.setCellValueFactory(new PropertyValueFactory<>("informatii"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCombustibil();
        tabelCombustibil.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Combustibil utilaj = tabelCombustibil.getSelectionModel().getSelectedItem();
                if (utilaj != null) {
                    id = utilaj.getId();
                    textInformatii.setText(utilaj.getInformatii());
                    btnAddCombustibil.setDisable(true);
                }
            }
        });
    }
}