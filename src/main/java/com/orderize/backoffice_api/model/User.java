package com.orderize.backoffice_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    @ManyToOne
    @JoinColumn(name = "fk_address")
    private Address address;
    @ManyToOne
    @JoinColumn(name = "fk_enterprise")
    private Enterprise enterprise;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "client")
    private List<Order> ordersClient;

    @OneToMany(mappedBy = "responsible")
    private List<Order> ordersResponsible;

    public User() {}

    public User(Address address, String phone, String password, String email, String name) {
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public User(Address address, String phone, String password, String email, String name, Long id) {
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.name = name;
        this.id = id;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(Enterprise enterprise, Address address, String phone, String password, String email, String name, Long id) {
        this.enterprise = enterprise;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.name = name;
        this.id = id;
    }

    public User(String name, String email, String password, String phone, Address address, Enterprise enterprise) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.enterprise = enterprise;
    }

    public User(Long id, String name, String email, String password, String phone, Address address, Enterprise enterprise, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.enterprise = enterprise;
        this.roles = roles;
    }

    //construtor para controle de pedido
    public User(Long id, String name, List<Order> ordersClient, List<Order> ordersResponsible){
        this.id = id;
        this.name = name;
        this.ordersClient = ordersClient;
        this.ordersResponsible = ordersResponsible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Order> getOrdersClient() {
        return ordersClient;
    }

    public void setOrdersClient(List<Order> ordersClient) {
        this.ordersClient = ordersClient;
    }

    public List<Order> getOrdersResponsible() {
        return ordersResponsible;
    }

    public void setOrdersResponsible(List<Order> ordersResponsible) {
        this.ordersResponsible = ordersResponsible;
    }

}
