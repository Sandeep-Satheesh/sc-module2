import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/user';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  isLoggedIn: boolean = false;
  currentUser!: User;
  navLinks: any;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.getLoggedIn().subscribe((val) => {
      this.isLoggedIn = val;
      if (this.isLoggedIn) {
        this.currentUser = this.authService.getUserDetails();
        if (this.currentUser === null || typeof this.currentUser == 'undefined')
          throw new Error('Unable to fetch user details!!');

        this.navLinks = [
          { name: 'Portfolio', url: '/portfolio' },
          { name: 'Trade History', url: '/trade-history' },
          { name: 'Market Place', url: '/market-place' },
          { name: 'Preferences', url: '/preferences' },
        ];
      } else {
        this.navLinks = [{ name: 'Sign In', url: '/login' }];
      }
    });
  }

  logout() {
    console.log('logged out');
    this.authService.setLoggedIn(false);
    this.router.navigateByUrl('/login');
  }
}
