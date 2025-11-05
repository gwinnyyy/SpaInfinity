import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  
  features = [
    {
      image: 'assets/services/body_wrap.jpg',
      title: 'Relaxation Therapy',
      description: 'Experience ultimate relaxation with our expert therapists'
    },
    {
      image: 'assets/services/salt_glow.jpg',
      title: 'Premium Treatments',
      description: 'Luxury spa treatments using the finest products'
    },
    {
      image: 'assets/services/swedish_massage.jpg',
      title: 'Natural Products',
      description: 'Organic and eco-friendly products for your wellness'
    },
    {
      image: 'assets/services/thai_massage.jpg',
      title: 'Expert Staff',
      description: 'Highly trained professionals dedicated to your care'
    }
  ];

  constructor() { }

  ngOnInit(): void {
  }

}