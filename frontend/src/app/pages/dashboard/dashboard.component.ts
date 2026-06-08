import { Component, OnInit, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, AfterViewInit {
  @ViewChild('salesChart') salesChartRef!: ElementRef;
  
  stats: any = {
    totalVentasMes: 0,
    totalClientes: 0,
    productosEnRiesgo: 0,
    gananciasMes: 0
  };

  loading = true;
  chartInstance: any;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get('http://localhost:8080/api/dashboard/stats').subscribe({
      next: (data: any) => {
        this.stats = data;
        this.loading = false;
        setTimeout(() => this.initChart(), 0); // Give view time to render canvas
      },
      error: (err) => {
        console.error('Error fetching dashboard stats', err);
        this.loading = false;
      }
    });
  }

  ngAfterViewInit() {
    // Canvas might not be rendered yet due to *ngIf="!loading" in HTML
  }

  initChart() {
    if (!this.salesChartRef) return;
    
    if (this.chartInstance) {
      this.chartInstance.destroy();
    }
    
    this.chartInstance = new Chart(this.salesChartRef.nativeElement, {
      type: 'line',
      data: {
        labels: ['Semana 1', 'Semana 2', 'Semana 3', 'Semana 4'],
        datasets: [{
          label: 'Ventas Semanales',
          data: [1200, 2500, 1800, this.stats.gananciasMes > 3000 ? 3000 : this.stats.gananciasMes],
          borderColor: '#3b82f6',
          backgroundColor: 'rgba(59, 130, 246, 0.1)',
          tension: 0.4,
          fill: true
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: false
          }
        },
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }
}
