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

public class AtributeController implements Initializable {

    private Connection con;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    private Integer idVaca;
    int idr=0;


    @FXML
    private TableView<Atribute> tabelReproductie;

    @FXML
    private TableColumn<Atribute, String> colMonta;
    @FXML
    private TableColumn<Atribute, String> colFatari;
    @FXML
    private TableColumn<Atribute, String> colMentiuni;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization happens after idVaca is set in setIdVaca
        tabelReproductie.setOnMouseClicked(this::getData);

    }

    public void showReproductie() {
        ObservableList<Atribute> list = getAtribute();
        tabelReproductie.setItems(list);
        colMonta.setCellValueFactory(new PropertyValueFactory<>("monta"));
        colFatari.setCellValueFactory(new PropertyValueFactory<>("fatari"));
        colMentiuni.setCellValueFactory(new PropertyValueFactory<>("mentiuni"));
    }

    public ObservableList<Atribute> getAtribute() {
        ObservableList<Atribute> atribute = FXCollections.observableArrayList();
        String query = "SELECT * FROM reproductie WHERE id_vaca = ?";
        con = DBConnexion.getCon();

        try {
            st = con.prepareStatement(query);
            st.setInt(1, idVaca);
            rs = st.executeQuery();

            while (rs.next()) {
                Atribute atributa = new Atribute(idVaca);
                atributa.setId_vaca(rs.getInt("id_vaca"));
                atributa.setId(rs.getInt("idr"));
                atributa.setMonta(rs.getString("monte"));
                atributa.setFatari(rs.getString("fatari"));
                atributa.setMentiuni(rs.getString("mentiuni"));
                atribute.add(atributa);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return atribute;
    }

    public void setIdVaca(int id) {
        this.idVaca = id;
        showReproductie();
    }

    @FXML
    TextField textMonte;
    @FXML
    TextField textFatari;
    @FXML
    TextField textMentiuni;

    @FXML
    private Button addButtonRepro;

    void clear(){
        textMonte.setText(null);
        textFatari.setText(null);
        textMentiuni.setText(null);
        addButtonRepro.setDisable(false);
    }

    public void addReproductie(ActionEvent event) {
        String insert= "insert into reproductie(id_vaca,monte,fatari,mentiuni) values(?,?,?,?)" ;
        con=DBConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setInt(1,idVaca);
            st.setString(2,textMonte.getText());
            st.setString(3, textFatari.getText());
            st.setString(4,textMentiuni.getText());

            st.executeUpdate();
            clear();
            showReproductie();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        public void getData(javafx.scene.input.MouseEvent mouseEvent) {
            Atribute selectedAtribute = tabelReproductie.getSelectionModel().getSelectedItem();
            if (selectedAtribute != null) {
                idr = selectedAtribute.getId();  // Store `idr` of the selected item
                textMonte.setText(selectedAtribute.getMonta());
                textFatari.setText(selectedAtribute.getFatari());
                textMentiuni.setText(selectedAtribute.getMentiuni());
                addButtonRepro.setDisable(true);  // Disable add button if updating
            }
    }
    void clearField(ActionEvent event){
        clear();
    }

    public void updateReproductie(ActionEvent event) {
        String update="update reproductie set monte = ?, fatari = ?, mentiuni = ? where idr = ?";
        con=DBConnexion.getCon();
        try {
            st=con.prepareStatement(update);
            st.setString(1,textMonte.getText());
            st.setString(2,textFatari.getText());
            st.setString(3, textMentiuni.getText());
            st.setInt(4,idr);
            st.executeUpdate();
            clear();
            showReproductie();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
}
