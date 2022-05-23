import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { RoleOptions } from '../model/RoleOptions';
import { RoleUpdateInfo } from '../model/RoleUpdateInfo';
import { User } from '../model/User';
import { UserRequest } from '../model/UserRequest';
import { CreateUserService } from './services/create-user.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css'],
  providers: [MessageService],
})
export class CreateUserComponent implements OnInit {
  updateId: number = 0;
  searchTerm: string = '';
  users: User[] = [];
  display: boolean = false;
  roles: RoleOptions[] = [
    {
      name: 'ROLE_OWNER',
      code: 'ROLE_OWNER',
    },
    {
      name: 'ROLE_RESIDENT',
      code: 'ROLE_RESIDENT',
    },
  ];
  selectedRoles: RoleOptions[] = [];
  PASSWORD_PATTERN: string = '^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$';
  userInfo: UserRequest = {
    username: '',
    password: '',
    firstname: '',
    lastname: '',
    email: '',
  };
  constructor(
    private createUserService: CreateUserService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.createUserService.getAll(this.searchTerm).subscribe((res) => {
      this.users = res;
    });
  }

  update() {
    let roleUpdateInfo: RoleUpdateInfo = {
      id: this.updateId,
      roles: this.selectedRoles.map((role) => role.name),
    };
    this.createUserService.update(roleUpdateInfo).subscribe(
      (res) => {
        this.messageService.add({
          key: 'tc',
          severity: 'success',
          summary: 'Success',
          detail: 'User succesfully updated',
        });
        this.getAll();
      },
      (err) => {
        this.messageService.add({
          key: 'tc',
          severity: 'error',
          summary: 'Warning',
          detail: err.error,
        });
      }
    );
  }

  showDialog(id: number) {
    this.display = true;
    this.updateId = id;
  }

  getAll() {
    if (this.searchTerm.trim() != '') {
      if (!/^[A-Za-z0-9_.]+$/.test(this.searchTerm)) {
        this.messageService.add({
          key: 'tc',
          severity: 'error',
          summary: 'Warning',
          detail: 'Search is in bad format',
        });
        return;
      }
    }
    this.createUserService.getAll(this.searchTerm).subscribe((res) => {
      this.users = res;
    });
  }

  delete(id: number) {
    this.createUserService.delete(id).subscribe(
      (res) => {
        this.messageService.add({
          key: 'tc',
          severity: 'success',
          summary: 'Success',
          detail: 'User succesfully deleted',
        });
        this.getAll();
      },
      (err) => {
        this.messageService.add({
          key: 'tc',
          severity: 'error',
          summary: 'Warning',
          detail: err.error,
        });
      }
    );
  }

  activate(id: number) {
    this.createUserService.activate(id).subscribe(
      (res) => {
        this.messageService.add({
          key: 'tc',
          severity: 'success',
          summary: 'Success',
          detail: 'User succesfully activated!',
        });
        this.getAll();
      },
      (err) => {
        this.messageService.add({
          key: 'tc',
          severity: 'error',
          summary: 'Warning',
          detail: err.error,
        });
      }
    );
  }
  createUser() {
    this.userInfo.roles = this.selectedRoles.map((role) => role.name);
    if (
      !/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.userInfo.email)
    ) {
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Warning',
        detail: 'User email is in bad format',
      });
      return;
    }
    //^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$
    if (!/^[A-Za-z0-9_.]+$/.test(this.userInfo.username)) {
      // Usernames can only use letters, numbers, underscores, and periods.
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Warning',
        detail:
          'Username is in bad format (Must contain only alphanumerical characters and special characters like ., - and _)',
      });
      return;
    }

    // ^(?=.{1,40}$)[a-zA-Z]+(?:[-'\s][a-zA-Z]+)*$
    if (
      !/^(?=.{1,40}$)[a-zA-Z]+(?:[-\s][a-zA-Z]+)*$/i.test(
        this.userInfo.firstname
      )
    ) {
      //First name can only use letters and hypen
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Warning',
        detail:
          'First name is in bad format (Must contain only alphabetic characters and hyphen)',
      });
      return;
    }
    // ^(?=.{1,40}$)[a-zA-Z]+(?:[-'\s][a-zA-Z]+)*$
    if (
      !/^(?=.{1,40}$)[a-zA-Z]+(?:[-\s][a-zA-Z]+)*$/i.test(
        this.userInfo.lastname
      )
    ) {
      //First name can only use letters and hypen
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Warning',
        detail:
          'Last name is in bad format (Must contain only alphabetic characters and hyphen)',
      });
      return;
    }

    // ^(?=.{1,40}$)[a-zA-Z]+(?:[-'\s][a-zA-Z]+)*$
    if (!this.userInfo.password.match(this.PASSWORD_PATTERN)) {
      //First name can only use letters and hypen
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Warning',
        detail:
          'Password is in bad format (Must contain at least 8 characters, with one small letter, one capital letter and at least 3 numbers)',
      });
      return;
    }

    this.createUserService.createUser(this.userInfo).subscribe(
      (res) => {
        this.messageService.add({
          key: 'tc',
          severity: 'success',
          summary: 'Success',
          detail: 'User succesfully created',
        });
        this.getAll();
      },
      (err) => {
        this.messageService.add({
          key: 'tc',
          severity: 'error',
          summary: 'Warning',
          detail: err.error,
        });
      }
    );

    this.userInfo = {
      username: '',
      password: '',
      firstname: '',
      lastname: '',
      email: '',
    };
  }

  isInfoValid() {
    if (this.userInfo.username == null || this.userInfo.username == '')
      return false;
    if (this.userInfo.firstname == null || this.userInfo.firstname == '')
      return false;
    if (this.userInfo.lastname == null || this.userInfo.lastname == '')
      return false;
    if (this.userInfo.email == null || this.userInfo.email == '') return false;
    //if (!this.userInfo.password.match(this.PASSWORD_PATTERN)) return false;

    return true;
  }
}
