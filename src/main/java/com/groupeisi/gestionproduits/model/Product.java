package com.groupeisi.gestionproduits.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @NotBlank(message = "La référence ne peut pas être vide")
    @Size(min = 4, max = 12, message = "La référence doit contenir entre 4 et 12 caractères")
    private String reference;

    @NotBlank(message = "La désignation ne peut pas être vide")
    private String designation;

    @Min(value = 100, message = "Le prix doit être supérieur à 100")
    private double price;

    @ColumnDefault("0")
    private int quantity;
}