import { Component, OnInit, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-dashboard',
  standalone: true,
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
        setTimeout(() => this.initChart(), 50); // Give view time to render canvas
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
    
    const ctx = this.salesChartRef.nativeElement.getContext('2d');
    
    // Create Premium Gradient
    const gradient = ctx.createLinearGradient(0, 0, 0, 400);
    gradient.addColorStop(0, 'rgba(79, 70, 229, 0.5)'); // Indigo 600
    gradient.addColorStop(1, 'rgba(79, 70, 229, 0.0)');

    this.chartInstance = new Chart(ctx, {
      type: 'line',
      data: {
        labels: ['Semana 1', 'Semana 2', 'Semana 3', 'Actual'],
        datasets: [{
          label: 'Ingresos Netos',
          data: [
            this.stats.gananciasMes * 0.4, 
            this.stats.gananciasMes * 0.6, 
            this.stats.gananciasMes * 0.85, 
            this.stats.gananciasMes
          ],
          borderColor: '#4f46e5',
          backgroundColor: gradient,
          borderWidth: 3,
          pointBackgroundColor: '#ffffff',
          pointBorderColor: '#4f46e5',
          pointBorderWidth: 2,
          pointRadius: 4,
          pointHoverRadius: 6,
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
          },
          tooltip: {
            backgroundColor: 'rgba(15, 23, 42, 0.9)',
            titleFont: { size: 13, family: "'Inter', sans-serif" },
            bodyFont: { size: 14, family: "'Inter', sans-serif", weight: 'bold' },
            padding: 12,
            cornerRadius: 8,
            displayColors: false,
            callbacks: {
              label: function(context: any) {
                let label = context.dataset.label || '';
                if (label) { label += ': '; }
                if (context.parsed.y !== null) {
                  label += new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(context.parsed.y);
                }
                return label;
              }
            }
          }
        },
        scales: {
          x: {
            grid: {
              display: false,
              drawOnChartArea: false
            },
            ticks: {
              color: '#64748b',
              font: { family: "'Inter', sans-serif", size: 12 }
            }
          },
          y: {
            grid: {
              color: '#f1f5f9',
              drawOnChartArea: true,
              tickColor: 'transparent'
            },
            border: {
              dash: [5, 5]
            },
            ticks: {
              color: '#64748b',
              font: { family: "'Inter', sans-serif", size: 12 },
              callback: function(value: any) {
                return '$' + value;
              }
            }
          }
        },
        interaction: {
          intersect: false,
          mode: 'index',
        },
      }
    });
  }
}
