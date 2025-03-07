/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package materi4contoh1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;


/**
 *
 * @author Taofik Dimas
 */
public class MainController implements Initializable {
    
    @FXML
    private TableColumn<User, String> colFullname;

    @FXML
    private TableColumn<User, String> colPassword;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private TableView<User> tblUsers;
    
    @FXML private Button btnAdd;
    @FXML private Button btnDelete;
    @FXML private Button btnUpdate;
    
    @FXML private TextField txtFullname;
    @FXML private TextField txtPassword;
    @FXML private TextField txtUsername;
    
    private void loadDataUsers(){
        
        ObservableList<User> userList = UserDAO.getUsers();
        
        tblUsers.setItems(userList);
    }
    private User selectedUser;
    
    private void clearFields(){
        txtUsername.clear();
        txtPassword.clear();
        txtFullname.clear();
        selectedUser = null;
    }
    
    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void selectUser(User user){
        if (user != null) {
            selectedUser = user;
            txtUsername.setText(user.getUsername());
            txtPassword.setText(user.getPassword());
            txtFullname.setText(user.getFullname());
        }
    }
    
    @FXML
    private void addUser(){
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String fullname  = txtFullname.getText();
        
        if (username.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
            showAlert("input error", "Field Tidak boleh kosong!");
            return;
        }
        
        User newUser = new User(username, password, fullname);
        UserDAO.addUser(newUser);
        loadDataUsers();
        clearFields();
    }
    
    @FXML
    private void updateUser() {
        if (selectedUser == null){
            showAlert("Selection Error", "Tidak ada user dipilih");
            return;
        }
        
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String fullname  = txtFullname.getText();
        
        if (username.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
            showAlert("input error", "Field Tidak boleh kosong!");
            return;
        }
        
        selectedUser.setUsername(username);
        selectedUser.setPassword(password);
        selectedUser.setFullname(fullname);
        
        UserDAO.updateUser(selectedUser);
        loadDataUsers();
        clearFields();
        
    }
    
    @FXML
    private void deleteUser() {
        if (selectedUser == null) {
            showAlert("selection Error", "Tidak ada user yang di pilih!");
            return;
        }
        
        UserDAO.deleteUser(selectedUser.getUsername());
        loadDataUsers();
        clearFields();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    colUsername.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
    colPassword.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
    colFullname.setCellValueFactory(new PropertyValueFactory<User, String>("fullname"));
   
    
    loadDataUsers();
    
    tblUsers.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectUser(newValue)
    );
    }   
    
    

}
