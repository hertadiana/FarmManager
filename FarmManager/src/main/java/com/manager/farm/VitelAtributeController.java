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

public class VitelAtributeController implements Initializable {

    private Connection con;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    private Integer viteiId;
    private int attributeId = 0;

    @FXML
    private TableView<VitelAtribute> tabelAtribute;

    @FXML
    private TableColumn<VitelAtribute, String> colParinti;
    @FXML
    private TableColumn<VitelAtribute, Double> colGreutateFatare;
    @FXML
    private TableColumn<VitelAtribute, Double> colSporCrestere;
    @FXML
    private TableColumn<VitelAtribute, String> colMentiuni;

    @FXML
    private TextField textParinti;
    @FXML
    private TextField textGreutateFatare;
    @FXML
    private TextField textSporCrestere;
    @FXML
    private TextField textMentiuni;

    @FXML
    private Button addButtonAtribute;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabelAtribute.setOnMouseClicked(this::getData);
    }

    public void showAtribute() {
        ObservableList<VitelAtribute> list = getAtribute();
        tabelAtribute.setItems(list);
        colParinti.setCellValueFactory(new PropertyValueFactory<>("parinti"));
        colGreutateFatare.setCellValueFactory(new PropertyValueFactory<>("greutateFatare"));
        colSporCrestere.setCellValueFactory(new PropertyValueFactory<>("sporCrestere"));
        colMentiuni.setCellValueFactory(new PropertyValueFactory<>("mentiuni"));
    }

    public ObservableList<VitelAtribute> getAtribute() {
        ObservableList<VitelAtribute> atribute = FXCollections.observableArrayList();
        String query = "SELECT * FROM vitel_atribute WHERE vitei_id = ?";
        con = DBConnexion.getCon();

        try {
            st = con.prepareStatement(query);
            st.setInt(1, viteiId);
            rs = st.executeQuery();

            while (rs.next()) {
                VitelAtribute attribute = new VitelAtribute();
                attribute.setAttributeId(rs.getInt("attribute_id"));
                attribute.setViteiId(rs.getInt("vitei_id"));
                attribute.setParinti(rs.getString("parinti"));
                attribute.setGreutateFatare(rs.getDouble("greutate_fatare"));
                attribute.setSporCrestere(rs.getDouble("spor_crestere"));
                attribute.setMentiuni(rs.getString("mentiuni"));
                atribute.add(attribute);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return atribute;
    }

    public void setViteiId(int id) {
        this.viteiId = id;
        showAtribute();
    }

    void clear() {
        textParinti.setText(null);
        textGreutateFatare.setText(null);
        textSporCrestere.setText(null);
        textMentiuni.setText(null);
        addButtonAtribute.setDisable(false);
    }

    @FXML
    public void addAtribute(ActionEvent event) {
        String insert = "INSERT INTO vitel_atribute(vitei_id, parinti, greutate_fatare, spor_crestere, mentiuni) VALUES (?, ?, ?, ?, ?)";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setInt(1, viteiId);
            st.setString(2, textParinti.getText());
            st.setDouble(3, Double.parseDouble(textGreutateFatare.getText()));
            st.setDouble(4, Double.parseDouble(textSporCrestere.getText()));
            st.setString(5, textMentiuni.getText());
            st.executeUpdate();
            clear();
            showAtribute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getData(javafx.scene.input.MouseEvent mouseEvent) {
        VitelAtribute selectedAttribute = tabelAtribute.getSelectionModel().getSelectedItem();
        if (selectedAttribute != null) {
            attributeId = selectedAttribute.getAttributeId();
            textParinti.setText(selectedAttribute.getParinti());
            textGreutateFatare.setText(String.valueOf(selectedAttribute.getGreutateFatare()));
            textSporCrestere.setText(String.valueOf(selectedAttribute.getSporCrestere()));
            textMentiuni.setText(selectedAttribute.getMentiuni());
            addButtonAtribute.setDisable(true);
        }
    }

    @FXML
    public void updateAtribute(ActionEvent event) {
        String update = "UPDATE vitel_atribute SET parinti = ?, greutate_fatare = ?, spor_crestere = ?, mentiuni = ? WHERE attribute_id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(update);
            st.setString(1, textParinti.getText());
            st.setDouble(2, Double.parseDouble(textGreutateFatare.getText()));
            st.setDouble(3, Double.parseDouble(textSporCrestere.getText()));
            st.setString(4, textMentiuni.getText());
            st.setInt(5, attributeId);
            st.executeUpdate();
            clear();
            showAtribute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void clearField(ActionEvent event) {
        clear();
    }
}
