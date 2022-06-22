import {
  Component,
  OnInit
} from '@angular/core';
import {
  MessageService
} from 'primeng/api';
import { UserRequestDTO } from '../model/UserRequestDTO';
import {
  ApproveUserRequestService
} from './services/approve-user-request.service';

@Component({
  selector: 'app-approve-user-request',
  templateUrl: './approve-user-request.component.html',
  styleUrls: ['./approve-user-request.component.css'],
  providers: [MessageService],
})
export class ApproveUserRequestComponent implements OnInit {

  usersRequests: UserRequestDTO[] = [];
  constructor(private approveUserRequestService: ApproveUserRequestService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.getAll();
  }
  
  getAll() {
    this.approveUserRequestService.getAll().subscribe(res => {
      this.usersRequests = res;
    });
  }

  delete(id: number) {
    this.approveUserRequestService.delete(id).subscribe(res => {
      this.getAll();
    })
  }

  approve(id: number) {
    this.approveUserRequestService.approve(id).subscribe(
      (res) => {
        this.messageService.add({
          key: 'tc',
          severity: 'success',
          summary: 'Success',
          detail: 'User Request succesfully approveed',
        });
        this.delete(id);
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

}
