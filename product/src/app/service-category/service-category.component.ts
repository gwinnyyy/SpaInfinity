import { Component, OnInit } from '@angular/core';
import { ProductCategory } from '../model/product-category';
import { ProductService } from '../service/spa-service.service';

@Component({
  selector: 'app-product-category',
  templateUrl: './product-category.component.html',
  styleUrls: ['./product-category.component.css'],
  standalone: false
})
export class ProductCategoryComponent implements OnInit {
  public productsCategory: ProductCategory[] = [];
 
  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    console.log("Loading spa services...");
    this.productService.getData().subscribe({
      next: (data: ProductCategory[]) => {
        this.productsCategory = data;
        console.log("Services loaded:", this.productsCategory);
      },
      error: (error: any) => {
        console.error("Error loading services:", error);
      }
    });
  }

  // Handle image loading errors
  handleImageError(event: any): void {
    // Set a placeholder gradient background when image fails to load
    event.target.style.display = 'none';
    event.target.parentElement.style.background = 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)';
  }
}