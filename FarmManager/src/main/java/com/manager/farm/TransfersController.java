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

public class TransfersController implements Initializable {

    private Connection con;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    private int id = 0;

    @FXML
    private TableView<Transfer> tableTransfer;

    @FXML
    private TableColumn<Transfer, String> colV;
    @FXML
    private TableColumn<Transfer, String> colA;

    @FXML
    private TextField textVanzare;
    @FXML
    private TextField textAchizitie;
   

    public void addTransfer(ActionEvent actionEvent) {
        String insert = "INSERT INTO transfer(vanzari,achizitii) VALUES(?,?)";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, textVanzare.getText());
            st.setString(2, textAchizitie.getText());

            st.executeUpdate();
            clear();
            showTransfer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTransfer(ActionEvent event) {
        String delete = "DELETE FROM transfer WHERE id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showTransfer();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void clear() {
        textAchizitie.setText(null);
        textVanzare.setText(null);
    }

    public ObservableList<Transfer> getTransfer() {
        ObservableList<Transfer> utilaje = FXCollections.observableArrayList();
        String query = "SELECT * FROM transfer";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Transfer utilaj = new Transfer();
                utilaj.setId(rs.getInt("id"));
                utilaj.setVanzzare(rs.getString("vanzari"));
                utilaj.setAchizitie(rs.getString("achizitii"));
                utilaje.add(utilaj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utilaje;
    }

    public void showTransfer() {
        ObservableList<Transfer> list = getTransfer();
        tableTransfer.setItems(list);
        colV.setCellValueFactory(new PropertyValueFactory<>("vanzzare"));
        colA.setCellValueFactory(new PropertyValueFactory<>("achizitie"));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTransfer();
        tableTransfer.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Transfer utilaj = tableTransfer.getSelectionModel().getSelectedItem();
                if (utilaj != null) {
                    id = utilaj.getId();
                    textVanzare.setText(utilaj.getVanzzare());
                    textAchizitie.setText(utilaj.getAchizitie());
                }
            }
        });
    }
}