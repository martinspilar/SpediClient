/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fei.prepravnispolecnost.model;

/**
 *
 * @author Spilda
 */
public class UserDTO implements java.io.Serializable {

    private Integer id;
    private AddressDTO address;
    private UserRoleDTO userRole;
    private DeviceDTO device;
    private String login;
    private String password;
    private String email;
    private Integer tel;
    private String name;
    private String surname;
    private int idDepo;

    public UserDTO() {
    }

    public UserDTO(AddressDTO address, UserRoleDTO userRole, String login, String password, String email, String name, String surname, int idDepo) {
        this.address = address;
        this.userRole = userRole;
        this.login = login;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.idDepo = idDepo;
    }

    public UserDTO(AddressDTO address, UserRoleDTO userRole, DeviceDTO device,
            String login, String password, String email, Integer tel,
            String name, String surname, int idDepo) {
        this.address = address;
        this.userRole = userRole;
        this.device = device;
        this.login = login;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.name = name;
        this.surname = surname;
        this.idDepo = idDepo;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AddressDTO getAddress() {
        return this.address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public UserRoleDTO getUserRole() {
        return this.userRole;
    }

    public void setUserRole(UserRoleDTO userRole) {
        this.userRole = userRole;
    }

    public DeviceDTO getDevice() {
        return this.device;
    }

    public void setDevice(DeviceDTO device) {
        this.device = device;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTel() {
        return this.tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getIdDepo() {
        return this.idDepo;
    }

    public void setIdDepo(int idDepo) {
        this.idDepo = idDepo;
    }
}