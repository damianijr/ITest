package br.com.itest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by damianijr on 10/25/16.
 */
@Entity
public class Restaurant implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String address;

    private String cnpj;
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<DeliveryArea> areas = new ArrayList<>();

    public Restaurant() {

    }

    /**
     * Constructor a restaurant by required fields.
     * @param id Restaurant ID.
     * @param name Restaurant name.
     * @param address Restaurant address.
     */
    public Restaurant(final Long id, final String name, final String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    /**
     * Add a new delivery area supported by restaurant.
     * @param deliveryArea Delivery area to add.
     */
    public void addDeliveryArea(DeliveryArea deliveryArea) {
        deliveryArea.setRestaurant(this);
        this.areas.add(deliveryArea);
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<DeliveryArea> getAreas() {
        return Collections.unmodifiableList(areas);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
