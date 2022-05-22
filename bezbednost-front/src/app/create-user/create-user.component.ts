import {
  Component,
  OnInit
} from '@angular/core';
import {
  MessageService
} from 'primeng/api';
import {
  RoleOptions
} from '../model/RoleOptions';
import { User } from '../model/user';
import {
  UserRequest
} from '../model/UserRequest';
import {
  CreateUserService
} from './services/create-user.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css'],
  providers: [MessageService],
})
export class CreateUserComponent implements OnInit {

    searchTerm:string = '';
  users: User[] = [];
  roles: RoleOptions[] = [{
      name: 'ROLE_OWNER',
      code: 'ROLE_OWNER'
    },
    {
      name: 'ROLE_RESIDENT',
      code: 'ROLE_RESIDENT'
    }
  ];
  selectedRoles: RoleOptions[] = [];
  PASSWORD_PATTERN: string = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
  userInfo: UserRequest = {
    username: '',
    password: '',
    firstname: '',
    lastname: '',
    email: ''
  };
  constructor(private createUserService: CreateUserService,
    private messageService: MessageService) {}

  ngOnInit(): void {
    this.createUserService.getAll(this.searchTerm).subscribe(res => {
      this.users = res;
    })
  }

  getAll() {
    this.createUserService.getAll(this.searchTerm).subscribe(res => {
        this.users = res;
      })
  }

  delete(id: number) {
    this.createUserService.delete(id).subscribe(res => {
      this.messageService.add({
        key: 'tc',
        severity: 'success',
        summary: 'Success',
        detail: 'User succesfully deleated',
      });
    }, err => {
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Warning',
        detail: 'Error during user deletion',
      });
    })
  }

  createUser() {
    this.userInfo.roles = this.selectedRoles.map(role => role.name);
    this.createUserService.createUser(this.userInfo).subscribe(res => {
      this.messageService.add({
        key: 'tc',
        severity: 'success',
        summary: 'Success',
        detail: 'User succesfully created',
      });
    }, err => {
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Warning',
        detail: 'Error during user creation',
      });
    })

    this.userInfo = {
      username: '',
      password: '',
      firstname: '',
      lastname: '',
      email: ''
    }
  }

  isInfoValid() {
    if (this.userInfo.username == null || this.userInfo.username == '') return false;
    if (this.userInfo.firstname == null || this.userInfo.firstname == '') return false;
    if (this.userInfo.lastname == null || this.userInfo.lastname == '') return false;
    if (this.userInfo.email == null || this.userInfo.email == '') return false;
    if (!this.userInfo.password.match(this.PASSWORD_PATTERN)) return false;

    return true;
  }
}
