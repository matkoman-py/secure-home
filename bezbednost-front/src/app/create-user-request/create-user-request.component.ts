import {
  Component,
  OnInit
} from '@angular/core';
import {
  MessageService
} from 'primeng/api';
import {
  UserRequestDTO
} from '../model/UserRequestDTO';
import {
  CreateUserRequestService
} from './services/create-user-request.service';

@Component({
  selector: 'app-create-user-request',
  templateUrl: './create-user-request.component.html',
  styleUrls: ['./create-user-request.component.css'],
  providers: [MessageService],
})
export class CreateUserRequestComponent implements OnInit {

  constructor(private createUserRequestService: CreateUserRequestService,
    private messageService: MessageService) {}

  PASSWORD_PATTERN: string = '^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$';
  userInfo: UserRequestDTO = {
    username: '',
    password: '',
    firstname: '',
    lastname: '',
    email: '',
  };
  ngOnInit(): void {}

  createUserRequest() {
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
        detail: 'Username is in bad format (Must contain only alphanumerical characters and special characters like ., - and _)',
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
        detail: 'First name is in bad format (Must contain only alphabetic characters and hyphen)',
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
        detail: 'Last name is in bad format (Must contain only alphabetic characters and hyphen)',
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
        detail: 'Password is in bad format (Must contain at least 8 characters, with one small letter, one capital letter and at least 3 numbers)',
      });
      return;
    }

    this.createUserRequestService.createUserRequest(this.userInfo).subscribe(
      (res) => {
        this.messageService.add({
          key: 'tc',
          severity: 'success',
          summary: 'Success',
          detail: 'User Request succesfully created',
        });
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

    return true;
  }
}
