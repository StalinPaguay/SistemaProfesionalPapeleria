import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { AuthService } from './services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, SidebarComponent, CommonModule],
  template: `
    <div [ngClass]="authService.isLoggedIn() ? 'app-layout' : 'auth-layout'">
      <app-sidebar *ngIf="authService.isLoggedIn()"></app-sidebar>
      <main [ngClass]="authService.isLoggedIn() ? 'main-content' : 'auth-content'">
        <router-outlet></router-outlet>
      </main>
    </div>
  `,
  styles: [`
    .app-layout {
      display: flex;
      min-height: 100vh;
    }
    .main-content {
      flex: 1;
      margin-left: var(--sidebar-width);
      padding: 32px;
      background: var(--bg);
      animation: fadeIn 0.3s ease;
    }
    .auth-layout {
      min-height: 100vh;
      background: var(--bg);
    }
    .auth-content {
      width: 100%;
      height: 100%;
    }
  `]
})
export class AppComponent {
  title = 'PaperDS';
  constructor(public authService: AuthService) {}
}
