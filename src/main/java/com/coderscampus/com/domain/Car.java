package com.coderscampus.com.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "motor_size")
    private String motorSize;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "wheel_size")
    private String wheelSize;

    @Column(name = "transmission")
    private String transmission;

    @Column(name = "color")
    private String color;

    @Column(name = "year_of")
    private Integer yearOf;

    @Column(name = "price")
    private Integer price;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Car id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotorSize() {
        return this.motorSize;
    }

    public Car motorSize(String motorSize) {
        this.setMotorSize(motorSize);
        return this;
    }

    public void setMotorSize(String motorSize) {
        this.motorSize = motorSize;
    }

    public String getModelName() {
        return this.modelName;
    }

    public Car modelName(String modelName) {
        this.setModelName(modelName);
        return this;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getWheelSize() {
        return this.wheelSize;
    }

    public Car wheelSize(String wheelSize) {
        this.setWheelSize(wheelSize);
        return this;
    }

    public void setWheelSize(String wheelSize) {
        this.wheelSize = wheelSize;
    }

    public String getTransmission() {
        return this.transmission;
    }

    public Car transmission(String transmission) {
        this.setTransmission(transmission);
        return this;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getColor() {
        return this.color;
    }

    public Car color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getYearOf() {
        return this.yearOf;
    }

    public Car yearOf(Integer yearOf) {
        this.setYearOf(yearOf);
        return this;
    }

    public void setYearOf(Integer yearOf) {
        this.yearOf = yearOf;
    }

    public Integer getPrice() {
        return this.price;
    }

    public Car price(Integer price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Car)) {
            return false;
        }
        return id != null && id.equals(((Car) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Car{" +
            "id=" + getId() +
            ", motorSize='" + getMotorSize() + "'" +
            ", modelName='" + getModelName() + "'" +
            ", wheelSize='" + getWheelSize() + "'" +
            ", transmission='" + getTransmission() + "'" +
            ", color='" + getColor() + "'" +
            ", yearOf=" + getYearOf() +
            ", price=" + getPrice() +
            "}";
    }
}
