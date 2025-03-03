package com.groupeisi.gestionproduits.controller;

import com.groupeisi.gestionproduits.exception.ProductNotFoundException;
import com.groupeisi.gestionproduits.model.Product;
import com.groupeisi.gestionproduits.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductWebController {
    private final IProductService productService;

    public ProductWebController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        try {
            List<Product> products;
            if (keyword != null && !keyword.isEmpty()) {
                products = productService.getProductsByKeyword(keyword);
                model.addAttribute("keyword", keyword);
            } else {
                products = productService.getAllProducts();
            }

            if (!model.containsAttribute("product")) {
                model.addAttribute("product", new Product());
            }

            model.addAttribute("products", products);
            model.addAttribute("isEdit", false);
            return "index";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de la récupération des produits: " + e.getMessage());
            model.addAttribute("products", List.of());
            model.addAttribute("product", new Product());
            return "index";
        }
    }

    @PostMapping
    public String createProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (product.getPrice() < 100) {
            bindingResult.rejectValue("price", "error.price",
                    "Le prix doit être supérieur à 100 F CFA");
        }

        if (!bindingResult.hasFieldErrors("reference") &&
                productService.existsByReference(product.getReference())) {
            bindingResult.rejectValue("reference", "error.reference",
                    "Cette référence existe déjà");
        }

        if (!bindingResult.hasFieldErrors("designation") &&
                productService.existsByName(product.getDesignation())) {
            bindingResult.rejectValue("designation", "error.designation",
                    "Cet produit existe déjà");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("isEdit", false);
            return "index";
        }

        try {
            productService.addProduct(product);
            redirectAttributes.addFlashAttribute("successMessage", "Produit ajouté avec succès!");
            return "redirect:/products";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de l'ajout du produit: " + e.getMessage());
            model.addAttribute("products", productService.getAllProducts());
            return "index";
        }
    }

    @GetMapping("/{reference}/edit")
    public String showUpdateForm(@PathVariable String reference, Model model) {
        try {
            Product product = productService.getProductByReference(reference);
            model.addAttribute("product", product);
            model.addAttribute("isEdit", true);
            model.addAttribute("products", productService.getAllProducts());
            return "index";
        } catch (ProductNotFoundException e) {
            model.addAttribute("errorMessage", "Produit introuvable: " + e.getMessage());
            model.addAttribute("product", new Product());
            model.addAttribute("products", productService.getAllProducts());
            return "index";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de l'édition: " + e.getMessage());
            model.addAttribute("product", new Product());
            model.addAttribute("products", productService.getAllProducts());
            return "index";
        }
    }

    @PostMapping("/{reference}")
    public String updateProduct(
            @PathVariable String reference,
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (product.getPrice() < 100) {
            bindingResult.rejectValue("price", "error.price",
                    "Le prix doit être supérieur à 100 F CFA");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            model.addAttribute("products", productService.getAllProducts());
            return "index";
        }

        try {
            productService.updateProduct(reference, product);
            redirectAttributes.addFlashAttribute("successMessage", "Produit mis à jour avec succès!");
            return "redirect:/products";
        } catch (ProductNotFoundException e) {
            model.addAttribute("errorMessage", "Produit introuvable: " + e.getMessage());
            model.addAttribute("isEdit", true);
            model.addAttribute("products", productService.getAllProducts());
            return "index";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de la mise à jour: " + e.getMessage());
            model.addAttribute("isEdit", true);
            model.addAttribute("products", productService.getAllProducts());
            return "index";
        }
    }

    @GetMapping("/{reference}/delete")
    public String deleteProduct(
            @PathVariable String reference,
            RedirectAttributes redirectAttributes
    ) {
        try {
            productService.deleteProduct(reference);
            redirectAttributes.addFlashAttribute("successMessage", "Produit supprimé avec succès!");
            return "redirect:/products";
        } catch (ProductNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Impossible de supprimer: " + e.getMessage());
            return "redirect:/products";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression: " + e.getMessage());
            return "redirect:/products";
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception e, Model model) {
        model.addAttribute("errorMessage", "Une erreur est survenue: " + e.getMessage());
        model.addAttribute("product", new Product());
        model.addAttribute("products", List.of());
        model.addAttribute("isEdit", false);
        return "index";
    }
}